package uoc.ds.pr;

import edu.uoc.ds.traversal.Iterator;

import java.util.Comparator;

public class OrderedVector<T> {

    private final T len;
    private final Comparator<T> cmp;

    public OrderedVector(T len, Comparator<T> cmp) {
        this.len = len;
        this.cmp = cmp;
    }

    public void update(T i) {

    }

    public int size() {
        return 0;
    }

    public boolean isFull() {
        return false;
    }

    public void delete(T i) {

    }

    public Iterator<T> values() {
        return null;
    }
}
