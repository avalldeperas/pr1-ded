# Data Structures - PR1

## Overview
This assignment (PR1) consists of a programming exercise that will be reuszable for Practice 2 (PR2), where we needed 
to apply the acquired knowledge. Specifically, in this activity we will work on the implementation of data structures that 
have been defined and designed with the information volume constraints specified in PAC1. These structures should allow 
us to store information about the university's technology and telecommunications center.

## Requisites
- [Java 8](https://www.oracle.com/es/java/technologies/javase/javase8-archive-downloads.html) or more
- [JUnit 4](https://junit.org/junit4/)

## Model
In this PR1 we'll basically deal with the following entities and concepts found in the 
[model](src/main/java/uoc/ds/pr/model) package:
- **Worker**: Workers that exist in the system
- **Company**: Existing companies in the system
- **JobOffer**: Job offers that are requested by companies and signed up by workers
- **Request**: Requests done by companies and that are approved or discarded by the system
- **Enrollment**: A sign-up by a Worker to a job offer.
- **Rating**: JobOffers are rated by enrolled workers


## Functionality
All operations required for PR1 are defined in the [CTTCompaniesJobs.java](src/main/java/uoc/ds/pr/CTTCompaniesJobs.java) 
interface. Additionally, constants are provided to define fixed containers, and enumerations are provided to define the
states of requests, qualifications, and expected results in a record.

The requirements for this PR1 are the following:
- **addWorker**(id, name, surname, dateOfBirth, qualification)
- **addCompany**(id, name, description)
- **addRequest**(id, jobOfferId, companyId, description, minQualification, type, maxWorkers, startDate, endDate)
- **updateRequest**(status, date, description)
- **signUpJobOffer**(workerId, jobOfferId)
- **getPercentageRejectedRequests**(): double
- **getJobOffersByCompany**(companyId): Iterator
- **getAllJobOffers**(): Iterator
- **getJobOffersByWorker**(workerId): Iterator
- **addRating**(workerId, jobOfferId, value, message)
- **getRatingsByJobOffer**(jobOfferId): Iterator
- **getMostActiveWorker**(): Worker
- **getBestJobOffer**(): JobOffer


Additionally, there are these new operations that allow us to inspect the data structures:
- **getWorker**(String id): Worker
- **getCompany**(String id): Company
- **getJobOffer**(String jobOfferId): JobOffer
- **numWorkers**(): int
- **numCompanies**(): int
- **numJobOffers**(): int
- **numPendingRequests**(): int
- **numTotalRequests**(): int
- **numRejectedRequests**(): int


## Changes Done
### Data structures
- **Dictionary**: this data structure has been chosen from DSLib repository using implementation **DictionaryArrayImpl**
which holds a pair of key value (KeyValue<id, Object>), to store workers, companies and jobOffers. 
- **Queue**: also a data structure from DSLib repository using QueueLinkedList implementation for the object that 
require a FIFO behaviour. Those objects are (pending) requests and also enrollments and substitutes for a given JobOffer.
- **OrderedVector**: we have used this structure to store the best job offers (top 10) in the system. More details in
utils section.
- **LinkedList**: used in Worker and Company to store their associated job offers. Also used on JobOffer class,
to store its ratings.

### Exceptions
The Exceptions below have been added to the exceptions folder, all extending [OrderedVector.java](src/main/java/uoc/ds/pr/utils/OrderedVector.java) 
[DSException.java](src/main/java/uoc/ds/pr/exceptions/DSException.java):

- [CompanyNotFoundException.java](src/main/java/uoc/ds/pr/exceptions/CompanyNotFoundException.java)
- [JobOfferNotFoundException.java](src/main/java/uoc/ds/pr/exceptions/JobOfferNotFoundException.java)
- [NOJobOffersException.java](src/main/java/uoc/ds/pr/exceptions/NOJobOffersException.java)
- [NORatingsException.java](src/main/java/uoc/ds/pr/exceptions/NORatingsException.java)
- [NoRequestException.java](src/main/java/uoc/ds/pr/exceptions/NoRequestException.java)
- [NoWorkerException.java](src/main/java/uoc/ds/pr/exceptions/NoWorkerException.java)
- [WorkerAlreadyEnrolledException.java](src/main/java/uoc/ds/pr/exceptions/WorkerAlreadyEnrolledException.java)
- [WorkerNOEnrolledException.java](src/main/java/uoc/ds/pr/exceptions/WorkerNOEnrolledException.java)
- [WorkerNotFoundException.java](src/main/java/uoc/ds/pr/exceptions/WorkerNotFoundException.java)

### Utils
A couple of data structures have been implemented in the utils package:
- [OrderedVector.java](src/main/java/uoc/ds/pr/utils/OrderedVector.java): all the implementation has been done from 
scratch, which consists on having an array that handles updates and deletes to add elements in a certain order 
(bigger > smaller) and with the ability to define a given Comparator we need to determine the order.
- [QueueLinkedList.java](src/main/java/uoc/ds/pr/utils/QueueLinkedList.java): it consists on reusing the functionality 
that LinkedList already provides and adds the queue operations (add, poll and peek).

### Additional tests
In order to complete the coverage of the source code two additional tests have been added:
- [OrderedVectorAdditionalTest.java](src/test/java/uoc/ds/pr/util/OrderedVectorAdditionalTest.java) with the tests:
    - getElementAt: ensures that values are returned as expected, as well as the exceptions thrown.
    - delete: added 4 tests that checks we clean array values when using delete in different cases.
    - isEmpty: also added tests for this method.
- [CTTCompaniesJobsImplAdditionalTests.java](uoc/ds/pr/CTTCompaniesJobsImplAdditionalTests.java) with some missing
  cases from initial tests:
    - Max capacity test exceptions for workers, companies and job offers. There is the exception to bestJobOffers max
  capacity which has not been tested because is an impossible case, as we always remove an element if array is full.
    - Given an update request with disabled status, the job offer requested is not stored to the companies.
    - Call updateRequest when there are no pending requests which throws NoRequestException.

### Other considerations
- Removed `public` modifier on the methods from CTTCompaniesJobs interface as it's redundant.

### Out of scope
There are some cases that are left out of scope:
- Only enrolled workers —and not substitutes— should be able to add jobOffer ratings, as these haven't tried it
yet.
- Same worker can add infinite ratings to the same job offer, but should just be one.
- Return own exceptions that would require changes to the signature of the methods. For example, accessing an incorrect
index of the OrderedVector with the getElementAt().
