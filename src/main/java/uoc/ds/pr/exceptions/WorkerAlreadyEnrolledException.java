package uoc.ds.pr.exceptions;

public class WorkerAlreadyEnrolledException extends DSException {

    private static final long serialVersionUID = -2577150645305791318L;

    public WorkerAlreadyEnrolledException() {
        super();
    }

    public WorkerAlreadyEnrolledException(String msg) {
        super(msg);
    }

}
