package uoc.ds.pr;

import edu.uoc.ds.exceptions.FullContainerException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.exceptions.CompanyNotFoundException;
import uoc.ds.pr.exceptions.NoRequestException;
import uoc.ds.pr.exceptions.WorkerNotFoundException;
import uoc.ds.pr.model.Company;

import static uoc.ds.pr.util.DateUtils.createLocalDate;

public class CTTCompaniesJobsImplAdditionalTests {

    private CTTCompaniesJobs cttCompaniesJobs;

    @Before
    public void setUp() throws Exception {
        this.cttCompaniesJobs = FactoryCTTCCompaniesJobsAdditional.getCTTCompaniesJobs();
    }

    @After
    public void tearDown() {
        this.cttCompaniesJobs = null;
    }

    @Test
    public void addWorkerTest_maxCapacity() {
        Assert.assertEquals(250, this.cttCompaniesJobs.numWorkers());

        Assert.assertThrows(FullContainerException.class, () ->
                cttCompaniesJobs.addWorker("wId251", "wName251", "wSuname251",
                        createLocalDate("23-10-1992"), CTTCompaniesJobs.Qualification.VOCATIONAL_TRAINING));
    }

    @Test
    public void addCompanyTest_maxCapacity() {
        Assert.assertEquals(26, this.cttCompaniesJobs.numCompanies());
        Assert.assertThrows(FullContainerException.class, () ->
                cttCompaniesJobs.addCompany("cId251", "cName251", "cDescription251"));
    }

    @Test
    public void addJobOfferTest_maxCapacity() throws CompanyNotFoundException {
        Assert.assertEquals(350, this.cttCompaniesJobs.numJobOffers());
        cttCompaniesJobs.addRequest("requestId351", "jobOfferIdB351", "companyId1",
                "The description - request351", CTTCompaniesJobs.Qualification.HIGH_SCHOOL,
                500, createLocalDate("25-11-2023"), createLocalDate("31-12-2023"));

        Assert.assertThrows(FullContainerException.class, () ->
                cttCompaniesJobs.updateRequest(CTTCompaniesJobs.Status.ENABLED,
                        createLocalDate("13-11-2023"), "OK - id351"));
    }
}