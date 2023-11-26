package uoc.ds.pr;

import uoc.ds.pr.exceptions.*;

import static uoc.ds.pr.util.DateUtils.createLocalDate;


public class FactoryCTTCCompaniesJobsAdditional extends FactoryCTTCCompaniesJobs {

    private static final int MAX_NUM_COMPANIES = 26;
    private static final int MAX_NUM_WORKERS = 250;
    private static final int MAX_NUM_JOBOFFERS = 350;

    public static CTTCompaniesJobs getCTTCompaniesJobs() throws Exception {
        CTTCompaniesJobs cttCompaniesJobs;
        cttCompaniesJobs = new CTTCompaniesJobsImpl();

        // CREATE MAX workers
        fillWorkers(cttCompaniesJobs);

        // CREATE MAX companies
        fillCompanies(cttCompaniesJobs);

        // CREATE MAX jobOffers
        fillJobOffers(cttCompaniesJobs);

        return cttCompaniesJobs;
    }

    private static void fillJobOffers(CTTCompaniesJobs cttCompaniesJobs) throws CompanyNotFoundException, NoRequestException {
        int i = 0;
        while (i < MAX_NUM_JOBOFFERS) {
            cttCompaniesJobs.addRequest("requestId" + i, "jobOfferId" + i, "companyId1",
                    "The description - request " + i, CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                    500, createLocalDate("25-11-2023"), createLocalDate("31-12-2023"));
            cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                    createLocalDate("13-11-2023"), "OK - id" + i);
            i++;
        }
    }

    private static void fillCompanies(CTTCompaniesJobs cttCompaniesJobs) {
        int i = 0;
        while (i < MAX_NUM_COMPANIES) {
            cttCompaniesJobs.addCompany("companyId" + i, "cName" + i, "cDescription" + i);
            i++;
        }
    }

    private static void fillWorkers(CTTCompaniesJobs cttCompaniesJobs) {
        int i = 0;
        while (i < MAX_NUM_WORKERS) {
            cttCompaniesJobs.addWorker("workerId" + i, "wName" + i, "wSuname" + i, createLocalDate("23-10-1992"), CTTCompaniesJobs.Qualification.VOCATIONAL_TRAINING);
            i++;
        }
    }
}