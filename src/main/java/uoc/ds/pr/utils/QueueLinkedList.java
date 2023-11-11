package uoc.ds.pr.utils;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;

public class QueueLinkedList<T> extends LinkedList<T> implements Queue<T> {

    private T first;
    private T last;



    @Override
    public void add(T t) {

    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<T> values() {
        return null;
    }
}
