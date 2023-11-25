# Data Structures - PR1

## Overview
This assignment (PR1) consists of a programming exercise that will be reusable for Practice 2 (PR2), where we needed 
to apply the acquired knowledge. Specifically, in this activity we will work on the implementation of data structures that have been defined and 
designed with the information volume constraints specified in PAC1. These structures should allow us to store information about the university's technology and telecommunications center.

## Requisites
- [Java 8](https://www.oracle.com/es/java/technologies/javase/javase8-archive-downloads.html) or more
- [JUnit 4](https://junit.org/junit4/)

## Model
In this PR1 we'll basically deal with the following entities and concepts found in the [model](src/main/java/uoc/ds/pr/model) package:
- **Worker**: Workers that exist in the system
- **Company**: Existing companies in the system
- **JobOffer**: Job offers that are requested by companies and signed up by workers
- **Request**: Requests done by companies and that are approved or discarded by the system
- **Enrollment**: A sign-up by a Worker to a job offer.
- **Rating**: JobOffers are rated by enrolled workers


## Functionality
All operations required for PR1 are defined in the [CTTCompaniesJobs.java](src/main/java/uoc/ds/pr/CTTCompaniesJobs.java) interface. Additionally, 
constants are provided to define fixed containers, and enumerations are provided to define the states of requests, qualifications, and expected results in a record.

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


Additionally, there's these new operations that allow to inspect the data structures:
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
- LinkedList<JobOffer> jobOffers

### Exceptions
The Exceptions below have been added to the exceptions folder, all extend[OrderedVector(1).java](..%2F..%2FDownloads%2FOrderedVector%281%29.java)ing 
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


### Other changes
- public class OrderedVector<T> extends Vector<T> per poder tenir elementAt?
- ús de DictionaryArrayImpl<> per a key value de workers, company i job offers per a reutilitzar l'implementació.
- Removed 'public' modifier from CTTCompaniesJobs as it's redundant for
- instantiation java defaults to 0 and nulls...

### Additional tests
- polling enrollments and substitutes (worker ends job offer)
- add ratings by just enrolled workers, not substitutes
- add ratings until the capacity is out, least rated offer is removed from the list.
- added new tests for OrderedVector to add coverage
- updateRequestTest_whenDiscardedStatus_jobOffersNotStoredToCompanies

### Out of scope
- not tested max values for each data structure.
- Only enrolled workers (NOT susbstitutes) be able to add ratings?
