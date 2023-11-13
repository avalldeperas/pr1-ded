package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.List;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.CTTCompaniesJobs;
import uoc.ds.pr.utils.QueueLinkedList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class JobOffer implements Comparable<JobOffer>{
    private final String jobOfferId;
    private final Company company;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int workingDays;
    private final CTTCompaniesJobs.Qualification minQualification;
    private final int maxWorkers;
    private int numWorkers;
    private final Queue<Enrollment> enrollments;
    private final Queue<Enrollment> substituteEnrollments;
    private int numSubstitutes;
    private List<Rating> ratings;
    private double sumRatings;

    public JobOffer(String jobOfferId, Company company, int maxWorkers, CTTCompaniesJobs.Qualification minQualification, LocalDate startDate, LocalDate endDate) {
        this.jobOfferId = jobOfferId;
        this.company = company;
        this.maxWorkers = maxWorkers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workingDays = (int) ChronoUnit.DAYS.between(startDate, endDate);
        this.minQualification = minQualification;
        this.enrollments = new QueueLinkedList<>();
        this.substituteEnrollments = new QueueLinkedList<>();
        this.ratings = new LinkedList<>();
        this.sumRatings = 0;
    }


    public String getJobOfferId() {
        return this.jobOfferId;
    }

    public Company getCompany() {
        return company;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public CTTCompaniesJobs.Qualification getMinQualification() {
        return minQualification;
    }

    public int getNumWorkers() {
        return numWorkers;
    }

    public int getNumSubstitutes() {
        return numSubstitutes;
    }

    public Iterator<Enrollment> substitutes() {
        return substituteEnrollments.values();
    }

    public double getTotalRating() {
        if (ratings.size() == 0)
            return 0;

        return sumRatings / ratings.size();
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public void addSubstituteEnrollment(Enrollment enrollment) {
        substituteEnrollments.add(enrollment);
        numSubstitutes++;
    }

    public Iterator<Enrollment> getEnrollments() {
        return enrollments.values();
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        numWorkers++;
    }

    public Iterator<Rating> getRatings() {
        return ratings.values();
    }

    public void addRating(Rating rating){
        this.ratings.insertEnd(rating);
        sumRatings += rating.getValue();
    }

    public boolean hasWorkerId(String workerId) {
        if (findWorker(workerId, getEnrollments()))
            return true;

        // We also need to check if worker is in substitutes.
        return findWorker(workerId, substitutes());
    }

    private static boolean findWorker(String workerId, Iterator<Enrollment> enrollments) {
        if (enrollments == null)
            return false;

        while (enrollments.hasNext()){
            Enrollment next = enrollments.next();
            if (workerId.equals(next.getWorker().getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(JobOffer offer2) {
        return Double.compare(this.getTotalRating(), offer2.getTotalRating());
    }
}
