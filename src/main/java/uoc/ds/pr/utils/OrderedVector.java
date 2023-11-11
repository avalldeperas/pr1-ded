package uoc.ds.pr.utils;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;

public class OrderedVector<T> implements FiniteContainer<T> {

    private T[] elements;
    private int len;
    private final Comparator<T> cmp;

    public OrderedVector(int maxLen, Comparator<T> cmp) {
        this.len = 0;
        this.cmp = cmp;
        elements = (T[]) new Object[maxLen];
    }

    public void update(T newElement) {
        elements[len++] = newElement;
    }

    @Override
    public boolean isEmpty() {
        return this.len == 0;
    }

    public int size() {
        return this.len;
    }

    public boolean isFull() {
        return this.len == this.elements.length;
    }

    // TODO - implement this...
    public void delete(T i) {
        return;
    }

    public Iterator<T> values() {
        return new IteratorArrayImpl<>(this.elements, this.len,0);
    }
}
