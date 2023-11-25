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
    public void isEmpty_test() {
        Assert.assertTrue(v.isEmpty());

        v.update(9);
        Assert.assertFalse(v.isEmpty());
    }
}
