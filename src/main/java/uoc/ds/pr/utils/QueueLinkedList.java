package uoc.ds.pr.utils;

import edu.uoc.ds.adt.sequential.LinkedList;
import edu.uoc.ds.adt.sequential.Queue;
import edu.uoc.ds.traversal.Iterator;

public class QueueLinkedList<T> extends LinkedList<T> implements Queue<T> {

    protected LinkedList<T> elems;

    public QueueLinkedList() {
        elems = new LinkedList<>();
    }

    @Override
    public void add(T t) {
        elems.insertEnd(t);
    }

    @Override
    public T poll() {
        return elems.deleteFirst();
    }

    @Override
    public T peek() {
        Iterator<T> values = elems.values();
        return values.hasNext() ? values.next() : null;
    }

    @Override
    public Iterator<T> values() {
        return elems.values();
    }

    @Override
    public int size() {
        return elems.size();
    }
}
