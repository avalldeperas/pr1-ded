package uoc.ds.pr;

import edu.uoc.ds.adt.nonlinear.Dictionary;
import edu.uoc.ds.adt.sequential.DictionaryArrayImpl;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.exceptions.*;
import uoc.ds.pr.model.*;
import uoc.ds.pr.utils.OrderedVector;
import uoc.ds.pr.utils.QueueLinkedList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

public class CTTCompaniesJobsImpl implements CTTCompaniesJobs {

    private final Dictionary<String, Worker> workers;
    private final Dictionary<String, Company> companies;
    private final Dictionary<String, JobOffer> jobOffers;
    private final Queue<Request> requests;
    private int totalRequests;
    private int totalRejectedRequests;
    private OrderedVector<JobOffer> bestJobOffers;
    private Worker mostActiveWorker;

    public CTTCompaniesJobsImpl() {
        workers = new DictionaryArrayImpl<>(MAX_NUM_WORKERS);
        companies = new DictionaryArrayImpl<>(MAX_NUM_COMPANIES);
        jobOffers = new DictionaryArrayImpl<>(MAX_NUM_JOBOFFERS);
        requests = new QueueLinkedList<>();
        bestJobOffers = new OrderedVector<>(MAX_NUM_RATINGS, Comparator.comparingDouble(JobOffer::getTotalRating));
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
            workers.put(worker.getId(), worker);
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
            companies.put(id, company);
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
            JobOffer jobOffer = request.getJobOffer();
            jobOffers.put(jobOffer.getJobOfferId(), jobOffer);
            jobOffer.getCompany().addJobOffer(jobOffer);
        } else if (Status.DISABLED.equals(status)) {
            totalRejectedRequests++;
            // TODO missing test for checking that for DISABLED, we don't store job offers in companies?
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

        if (jobOffer.hasWorkerId(workerId))
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

        return jobOffers.values();
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

        // TODO - do only enrolled workers (NOT susbstitutes) be able to add ratings?
        if (!jobOffer.hasWorkerId(workerId))
            throw new WorkerNOEnrolledException(String.format("Worker id %s not enrolled to job offer %s yet.", workerId, jobOfferId));

        Rating rating = new Rating(value, message);
        jobOffer.addRating(rating);

        // TODO - update ordered vector of best offers
        this.bestJobOffers.delete(jobOffer);
        this.bestJobOffers.update(jobOffer);
    }

    @Override
    public Iterator<Rating> getRatingsByJobOffer(String jobOfferId) throws JobOfferNotFoundException, NORatingsException {
        JobOffer jobOffer = getJobOffer(jobOfferId);
        if (jobOffer == null)
            throw new JobOfferNotFoundException(String.format("Job offer '%s' does not exist.", jobOfferId));

        if (!jobOffer.getRatings().hasNext())
            throw new NORatingsException(String.format("Job offer '%s' has no ratings yet.", jobOfferId));

        return jobOffer.getRatings();
    }

    @Override
    public Worker getMostActiveWorker() throws NoWorkerException {
        if (mostActiveWorker == null)
            throw new NoWorkerException("There are no workers with any activity yet.");

        return mostActiveWorker;
    }

    @Override
    public JobOffer getBestJobOffer() throws NOJobOffersException {
        if (bestJobOffers.size() == 0)
            throw new NOJobOffersException("There are no rated job offers yet.");

        return bestJobOffers.getFirstElement();
    }

    @Override
    public Worker getWorker(String id) {
        return workers.get(id);
    }

    @Override
    public Company getCompany(String id) {
        return companies.get(id);
    }

    @Override
    public JobOffer getJobOffer(String jobOfferId) {
        return jobOffers.get(jobOfferId);
    }

    @Override
    public int numWorkers() {
        return workers.size();
    }

    @Override
    public int numCompanies() {
        return companies.size();
    }

    @Override
    public int numJobOffers() {
        return jobOffers.size();
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
