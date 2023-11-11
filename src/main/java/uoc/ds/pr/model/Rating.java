package uoc.ds.pr.model;

public class Rating {
    private final int value;
    private final String message;

    public Rating(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
