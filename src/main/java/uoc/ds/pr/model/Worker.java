package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;
import uoc.ds.pr.CTTCompaniesJobs;

import java.time.LocalDate;

public class Worker {

    private final String id;
    private final LinkedList<JobOffer> jobOffers;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private CTTCompaniesJobs.Qualification qualification;
    private int workingDays;

    public Worker(String id, String name, String surname, LocalDate dateOfBirth, CTTCompaniesJobs.Qualification qualification) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.qualification = qualification;
        this.jobOffers = new LinkedList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CTTCompaniesJobs.Qualification getQualification() {
        return qualification;
    }

    public void setQualification(CTTCompaniesJobs.Qualification qualification) {
        this.qualification = qualification;
    }

    public String getId() {
        return id;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void addWorkingDays(int workingDays) {
        this.workingDays += workingDays;
    }

    public LinkedList<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void addJobOffer(JobOffer jobOffer) {
        jobOffers.insertEnd(jobOffer);
    }
}
