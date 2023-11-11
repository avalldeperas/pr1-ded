package uoc.ds.pr.utils;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;

public class OrderedVector<T> implements FiniteContainer<T> {

    private T[] data;
    private int len;
    private final Comparator<T> cmp;

    public OrderedVector(int maxLen, Comparator<T> cmp) {
        this.len = 0;
        this.cmp = cmp;
        data = (T[]) new Object[maxLen];
    }

    public void update(T i) {
        data[len++] = i;
    }

    @Override
    public boolean isEmpty() {
        return this.len == 0;
    }

    public int size() {
        return this.len;
    }

    public boolean isFull() {
        return this.len == this.data.length;
    }

    public void delete(T i) {
        for (T t: data){

        }
    }

    public Iterator<T> values() {
        return new IteratorArrayImpl<>(this.data, this.len,0);
    }
}
