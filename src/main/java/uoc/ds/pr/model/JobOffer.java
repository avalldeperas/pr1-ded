package uoc.ds.pr.model;

import edu.uoc.ds.traversal.Iterator;
import uoc.ds.pr.CTTCompaniesJobs;

import java.time.LocalDate;

public class JobOffer {
    private String jobOfferId;
    private Company company;
    private LocalDate startDate;
    private LocalDate endDate;
    private int workingDays;
    private CTTCompaniesJobs.Qualification minQualification;
    private int numWorkers;
    private int numSubstitutes;
    private double totalRating;

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
}
