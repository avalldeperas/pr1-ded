package uoc.ds.pr;

import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.adt.sequential.QueueArrayImpl;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.utils.OrderedVector;
import uoc.ds.pr.utils.QueueLinkedList;

import java.time.LocalDate;
import java.util.Objects;

public class CTTCompaniesJobsImpl implements CTTCompaniesJobs {
    /**
     * treballadors ------------------------------------------ vector estàtic (x)   - Worker[] x
     * empreses ---------------------------------------------- vector estàtic (x)   - Company[] x
     * ofertes de feina d'una empresa  ----------------------- llista encadenada    - LinkedList x
     * sol·licituds ------------------------------------------ cua no fitada        - QueueLinkedList x
     * ofertes de feina -------------------------------------- vector (x)           - JobOffer[] x
     * ofertes de feina en què un treballador és contractat--- llista encadenada    - LinkedList. x
     * valoracions ------------------------------------------- llista encadenada    - LinkedList. x
     * inscripcions ------------------------------------------ cua no fitada        - QueueLinkedList.
     * inscripcions suplents --------------------------------- cua no fitada        - QueueLinkedList.
     * total de sol·licituds --------------------------------- enter                - Integer x
     * total de sol·licituds rebutjades ---------------------- enter                - Integer x
     * treballador més actiu --------------------------------- apuntador            - Worker. x
     * l'oferta de treball millor valorada ------------------- vector ordenat       - OrderedVector. ????
     */

    private final Worker[] workers;
    private int numWorkers;

    private final Company[] companies;
    private int numCompanies;

    private final Queue<Request> requests;

    private final JobOffer[] jobOffers;
    private int numJobOffers;

    private int numPendingRequests;
    private int totalRejectedRequests;
    private int totalRequests;
    private Worker mostActiveWorker;
    private OrderedVector<JobOffer> bestJobOffers;

    public CTTCompaniesJobsImpl() {
        workers = new Worker[MAX_NUM_WORKERS];
        numWorkers = 0;
        companies = new Company[MAX_NUM_COMPANIES];
        numCompanies = 0;
        jobOffers = new JobOffer[MAX_NUM_JOBOFFERS];
        numJobOffers = 0;
        requests = new QueueArrayImpl<>();
        totalRequests = 0;
        numPendingRequests = 0;
        totalRejectedRequests = 0;
        mostActiveWorker = null;
        // TODO how to instantiate this?
//        bestJobOffers = new OrderedVector<>();
    }

    @Override
    public void addWorker(String id, String name, String surname, LocalDate dateOfBirth, Qualification qualification) {
        Worker worker = getWorker(id);
        if (Objects.nonNull(worker)) {
            worker.setName(name);
            worker.setSurname(surname);
            worker.setDateOfBirth(dateOfBirth);
            worker.setQualification(qualification);
        } else {
            worker = new Worker(id, name, surname, dateOfBirth, qualification);
            workers[numWorkers++] = worker;
        }
    }

    @Override
    public void addCompany(String id, String name, String description) {
        Company company = getCompany(id);
        if (Objects.nonNull(company)) {
            company.setName(name);
            company.setDescription(description);
        } else {
            company = new Company(id, name, description);
            companies[numCompanies++] = company;
        }
    }

    @Override
    public void addRequest(String id, String jobOfferId, String companyId, String description, Qualification minQualification, int maxWorkers, LocalDate startDate, LocalDate endDate) throws CompanyNotFoundException {
        Company company = getCompany(companyId);
        if (company == null)
            throw new CompanyNotFoundException(String.format("Company %s not found.", companyId));

        JobOffer jobOffer = new JobOffer(jobOfferId, company, maxWorkers, minQualification, startDate, endDate);

        Request request = new Request(id, jobOffer, description);
        requests.add(request);
        totalRequests++;
    }

    @Override
    public Request updateRequest(Status status, LocalDate date, String description) throws NoRequestException {
        Request request = this.requests.poll();
        if (request == null)
            throw new NoRequestException("There are no requests to update.");
        request.setStatus(status);
        request.setDateStatus(date);
        request.setDescriptionStatus(description);

        if (Status.ENABLED.equals(status)) {
            jobOffers[numJobOffers++] = request.getJobOffer();
        } else if (Status.DISABLED.equals(status)) {
            totalRejectedRequests++;
        }

        return request;
    }

    @Override
    public Response signUpJobOffer(String workerId, String jobOfferId) throws JobOfferNotFoundException, WorkerNotFoundException, WorkerAlreadyEnrolledException {
        Worker worker = getWorker(workerId);
        if (worker == null)
            throw new WorkerNotFoundException(String.format("Worker with id '%s' not found.", workerId));

        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null)
            throw new JobOfferNotFoundException(String.format("Job offer with id '%s' not found.", jobOfferId));

        // TODO - check if worker already in job offer to throw WorkerAlreadyEnrolledException
        if (false)
            throw new WorkerAlreadyEnrolledException(String.format("Worker '%s' already enrolled in job offer '%s'", workerId, jobOfferId));

        if (worker.getQualification().getValue() < jobOffer.getMinQualification().getValue())
            return Response.REJECTED;

        Enrollment enrollment = new Enrollment(worker);
        if (jobOffer.getNumWorkers() >= jobOffer.getMaxWorkers()) {
            jobOffer.addSubstituteEnrollment(enrollment);
            return Response.SUBSTITUTE;
        }

        jobOffer.addEnrollment(enrollment);
        worker.addJobOffer(jobOffer);
        worker.addWorkingDays(jobOffer.getWorkingDays());

        if (mostActiveWorker == null || worker.getWorkingDays() > mostActiveWorker.getWorkingDays())
            mostActiveWorker = worker;

        return Response.ACCEPTED;
    }

    @Override
    public double getPercentageRejectedRequests() {
        return (double) totalRejectedRequests / totalRequests;
    }

    @Override
    public Iterator<JobOffer> getJobOffersByCompany(String companyId) throws NOJobOffersException {
        Company company = getCompany(companyId);

        if (company.getJobOffers().isEmpty())
            throw new NOJobOffersException(String.format("There are no job offers for this company id '%s'", companyId));

        return company.getJobOffers().values();
    }

    @Override
    public Iterator<JobOffer> getAllJobOffers() throws NOJobOffersException {
        if (numJobOffers() == 0)
            throw new NOJobOffersException("There are no job offers currently.");

        return new IteratorArrayImpl<>(this.jobOffers, this.numJobOffers, 0);
    }

    @Override
    public Iterator<JobOffer> getJobOffersByWorker(String workerId) throws NOJobOffersException {
        Worker worker = getWorker(workerId);

        if (worker.getJobOffers().isEmpty())
            throw new NOJobOffersException(String.format("There are no job offers for worker id '%s'", workerId));

        return worker.getJobOffers().values();
    }

    @Override
    public void addRating(String workerId, String jobOfferId, int value, String message) throws WorkerNotFoundException, JobOfferNotFoundException, WorkerNOEnrolledException {
        Worker worker = getWorker(workerId);
        if (worker == null)
            throw new WorkerNotFoundException(String.format("Worker %s not found.", workerId));

        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null)
            throw new JobOfferNotFoundException(String.format("Job offer %s", jobOfferId));

        // TODO - if worker not enrolled throw WorkerNOEnrolledException

        Rating rating = new Rating(value, message);
        jobOffer.addRating(rating);
    }

    @Override
    public Iterator<Rating> getRatingsByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NORatingsException {
        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null)
            throw new JobOfferNotFoundException(String.format("Job offer '%s' does not exist.", jobOfferId));

        if (jobOffer.getRatings().isEmpty())
            throw new NORatingsException(String.format("Job offer '%s' has no ratings yet.", jobOfferId));

        return jobOffer.getRatings().values();
    }

    @Override
    public Worker getMostActiveWorker() throws NoWorkerException {
        if (mostActiveWorker == null)
            throw new NoWorkerException("There are no workers with any activity yet.");

        // TODO shall we do some logic here?
        return mostActiveWorker;
    }

    @Override
    public JobOffer getBestJobOffer() throws NOJobOffersException {
        // TODO this needs to be reviewed.
        if (bestJobOffers.size() == 0)
            throw new NOJobOffersException("There are no rated job offers yet.");

        return bestJobOffers.values().next();
    }

    @Override
    public Worker getWorker(String id) {
        for (Worker worker : workers) {
            if (worker == null) {
                return null;
            } else if (worker.getId().equals(id)) {
                return worker;
            }
        }
        return null;
    }

    @Override
    public Company getCompany(String id) {
        for (Company company : companies) {
            if (company == null) {
                return null;
            } else if (company.getId().equals(id)) {
                return company;
            }
        }
        return null;
    }

    @Override
    public JobOffer getJobOffer(String jobOfferId) {
        for (JobOffer jobOffer : jobOffers) {
            if (jobOffer == null) {
                return null;
            } else if (jobOffer.getJobOfferId().equals(jobOfferId)) {
                return jobOffer;
            }
        }
        return null;
    }

    @Override
    public int numWorkers() {
        return numWorkers;
    }

    @Override
    public int numCompanies() {
        return numCompanies;
    }

    @Override
    public int numJobOffers() {
        return numJobOffers;
    }

    @Override
    public int numPendingRequests() {
        return requests.size();
    }

    @Override
    public int numTotalRequests() {
        return totalRequests;
    }

    @Override
    public int numRejectedRequests() {
        return totalRejectedRequests;
    }
}
