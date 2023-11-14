package uoc.ds.pr.model;

import edu.uoc.ds.adt.sequential.LinkedList;

public class Company {

    private final String id;
    private final LinkedList<JobOffer> jobOffers;
    private String name;
    private String description;

    public Company(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.jobOffers = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public LinkedList<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void addJobOffer(JobOffer jobOffer) {
        jobOffers.insertEnd(jobOffer);
    }
}
