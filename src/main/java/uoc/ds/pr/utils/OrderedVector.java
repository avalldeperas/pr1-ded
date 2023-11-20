package uoc.ds.pr.utils;

import edu.uoc.ds.adt.sequential.FiniteContainer;
import edu.uoc.ds.traversal.Iterator;
import edu.uoc.ds.traversal.IteratorArrayImpl;

import java.util.Comparator;
import java.util.Objects;

public class OrderedVector<T> implements FiniteContainer<T> {

    private final T[] elems;
    private final Comparator<T> cmp;
    private int len;

    public OrderedVector(int maxLen, Comparator<T> cmp) {
        this.cmp = cmp;
        this.elems = (T[]) new Object[maxLen];
    }

    @Override
    public boolean isEmpty() {
        return len == 0;
    }

    public void update(T newElem) {
        delete(newElem);

        if (isFull()) {
            T lastElem = last();
            if (cmp.compare(lastElem, newElem) < 0) {
                delete(lastElem);
                update(newElem);
            }
            return;
        }

        int i = 0;
        while (i < len && Objects.nonNull(elems[i]) && cmp.compare(elems[i], newElem) >= 0)
            i++;

        rightShift(i);
        elems[i] = newElem;
        len++;
    }

    public void delete(T elem) {
        int i = 0;
        boolean found = false;

        while (!found && i < len)
            found = cmp.compare(elems[i++], elem) == 0;

        if (found) {
            leftShift(i - 1);
            len--;
        }
    }

    public void rightShift(int i) {
        int pos = len - 1;
        while (pos >= i) {
            elems[pos + 1] = elems[pos];
            pos--;
        }
    }

    public void leftShift(int i) {
        int pos = i;
        while (pos < len - 1) {
            elems[pos] = elems[pos + 1];
            pos++;
        }
    }

    private T last() {
        return elems[len - 1];
    }

    public int size() {
        return len;
    }

    public boolean isFull() {
        return len == elems.length;
    }

    public Iterator<T> values() {
        return new IteratorArrayImpl<>(elems, len, 0);
    }

    public T getElementAt(int pos) {
        return elems[pos];
    }
}
