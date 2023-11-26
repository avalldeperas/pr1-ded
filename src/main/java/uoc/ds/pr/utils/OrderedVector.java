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

    /**
     * Add new element to the ordered vector. If vector is full, we delete last element if value is smaller to
     * give space, then update again. If not full, we just add the value the expected position.
     * @param newElem element to add to the vector.
     */
    public void update(T newElem) {
        if (isFull()) {
            T lastElem = last();
            if (cmp.compare(lastElem, newElem) < 0) {
                delete(lastElem);
                update(newElem);
            }
        } else {
            add(newElem);
        }
    }

    /**
     * Remove new element from the ordered vector. If element is found, we left shift all elements until that position
     * and also remove the element.
     * @param elem Element to remove.
     */
    public void delete(T elem) {
        for (int i = 0; i < len; i++) {
            if (cmp.compare(elems[i], elem) == 0){
                leftShift(i);
                elems[len - 1] = null; // clear last element
                len--;
                return;
            }
        }
    }

    /**
     * Add element to the ordered vector. First we find the position to add, then right shift elements to the right
     * and finally insert the element.
     * @param newElem Element to add to the vector.
     */
    private void add(T newElem) {
        int addPos = 0;

        for (int i = 0; i < len; i++){
            if (Objects.nonNull(elems[i]) && cmp.compare(elems[i], newElem) >= 0)
                addPos++;
            else
                break;
        }

        rightShift(addPos);
        elems[addPos] = newElem;
        len++;
    }

    /**
     * Right shifts all elements by a given position
     * @param i given position from which we need to shift
     */
    private void rightShift(int i) {
        int pos = len - 1;
        while (pos >= i) {
            elems[pos + 1] = elems[pos];
            pos--;
        }
    }

    /**
     * Left shifts all elements by a given position
     * @param i given position from which we need to shift
     */
    private void leftShift(int i) {
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
