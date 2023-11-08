package uoc.ds.pr.model;

import uoc.ds.pr.CTTCompaniesJobs;

import java.time.LocalDate;

public class Worker {

    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;
    private final CTTCompaniesJobs.Qualification qualification;
    private final String id;
    private int workingDays;

    public Worker(String id, String name, String surname, LocalDate dateOfBirth, CTTCompaniesJobs.Qualification qualification) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.qualification = qualification;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public CTTCompaniesJobs.Qualification getQualification() {
        return qualification;
    }

    public String getId() {
        return id;
    }

    public int getWorkingDays() {
        return workingDays;
    }
}
