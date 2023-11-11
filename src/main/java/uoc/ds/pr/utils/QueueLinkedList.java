package uoc.ds.pr.utils;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

public class QueueLinkedList<T> extends LinkedList<T> implements Queue<T> {

    private T first;
    private T last;
    private Queue<T> data;

    @Override
    public void add(T t) {
        data.add(t);
    }

    @Override
    public T poll() {
        return data.poll();
    }

    @Override
    public T peek() {
        return data.peek();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Iterator<T> values() {
        return this.data.values();
    }
}
