package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.CTTCompaniesJobs;
import uoc.ds.pr.utils.QueueLinkedList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class JobOffer {
    private final String jobOfferId;
    private final Company company;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int workingDays;
    private final CTTCompaniesJobs.Qualification minQualification;
    private int numWorkers;
    private int maxWorkers;
    private QueueLinkedList<Enrollment> enrollments;
    private int numEnrollments;
    private QueueLinkedList<Enrollment> substituteEnrollments;
    private int numSubstitutes;
    private LinkedList<Rating> ratings;
    private double totalRating;

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
        return null;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public void addSubstituteEnrollment(Enrollment enrollment) {
        substituteEnrollments.add(enrollment);
        numSubstitutes++;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        numEnrollments++;
    }

    public LinkedList<Rating> getRatings() {
        return ratings;
    }

    public void addRating(Rating rating){
        // TODO add rating
    }
}
