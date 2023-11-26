package uoc.ds.pr.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uoc.ds.pr.utils.OrderedVector;

import java.util.Comparator;


public class OrderedVectorAdditionalTest {
    private static final int LEN = 10;
    private OrderedVector<Integer> v;
    private final Comparator<Integer> CMP = Comparator.naturalOrder();

    @Before
    public void setUp() {
        v = new OrderedVector<>(LEN, CMP);
    }

    @Test
    public void getElementAt_test() {
        Assert.assertNull(v.getElementAt(0));

        v.update(5);
        v.update(9);
        int result = v.getElementAt(0);
        Assert.assertEquals(9, result);

        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> v.getElementAt(-1));
        Assert.assertThrows(ArrayIndexOutOfBoundsException.class, () -> v.getElementAt(LEN));
    }

    @Test
    public void delete_whenNotFull_notLastValue_isNull() {
        v.update(8);
        v.update(10);
        v.update(5);
        Assert.assertFalse(v.isFull());

        v.delete(8);

        Assert.assertNull(v.getElementAt(2));
    }

    @Test
    public void delete_whenNotFull_lastValue_isNull() {
        v.update(8);
        v.update(10);
        v.update(5);
        Assert.assertFalse(v.isFull());

        v.delete(5);

        Assert.assertNull(v.getElementAt(2));
    }

    @Test
    public void delete_whenFull_deleteLastValue_lastValueIsNull() {
        fillVector();
        Assert.assertTrue(v.isFull());

        v.delete(1);

        Assert.assertNull(v.getElementAt(LEN - 1));
    }

    @Test
    public void delete_whenFull_deleteNotLastValue_lastValueIsNull() {
        fillVector();
        Assert.assertTrue(v.isFull());

        v.delete(5);

        Assert.assertNull(v.getElementAt(LEN - 1));
    }

    @Test
    public void isEmpty_test() {
        Assert.assertTrue(v.isEmpty());

        v.update(9);
        Assert.assertFalse(v.isEmpty());
    }

    private void fillVector(){
        v.update(7);
        v.update(9);
        v.update(5);
        v.update(2);
        v.update(3);
        v.update(1);
        v.update(4);
        v.update(6);
        v.update(11);
        v.update(12);
    }
}
