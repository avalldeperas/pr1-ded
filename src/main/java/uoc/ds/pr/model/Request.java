package uoc.ds.pr.model;

import uoc.ds.pr.CTTCompaniesJobs;

import java.time.LocalDate;

public class Request {

    private final String id;
    private final String description;
    private JobOffer jobOffer;
    private CTTCompaniesJobs.Status status;
    private LocalDate dateStatus;
    private String descriptionStatus;

    public Request(String id, JobOffer jobOffer, String description) {
        this.id = id;
        this.jobOffer = jobOffer;
        this.description = description;
        this.status = CTTCompaniesJobs.Status.PENDING;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public CTTCompaniesJobs.Status getStatus() {
        return status;
    }

    public void setStatus(CTTCompaniesJobs.Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescriptionStatus(String descriptionStatus) {
        this.descriptionStatus = descriptionStatus;
    }

    public LocalDate getDateStatus() {
        return dateStatus;
    }

    public String getDescriptionStatus() {
        return descriptionStatus;
    }

    public void setDateStatus(LocalDate date) {
        this.dateStatus = date;
    }
}
