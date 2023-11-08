package uoc.ds.pr.model;

import uoc.ds.pr.CTTCompaniesJobs;

import java.time.LocalDate;

public class Request {

    private JobOffer jobOffer;
    private CTTCompaniesJobs.Status status;
    private LocalDate dateStatus;
    private String descriptionStatus;

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public CTTCompaniesJobs.Status getStatus() {
        return status;
    }

    public LocalDate getDateStatus() {
        return dateStatus;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }
}
