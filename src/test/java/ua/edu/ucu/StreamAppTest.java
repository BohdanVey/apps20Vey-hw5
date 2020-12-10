package ua.edu.ucu;

import ua.edu.ucu.stream.*;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

/**
 * @author andrii
 */
public class StreamAppTest {

    private IntStream intStream;

    @Before
    public void init() {
        int[] intArr = {-1, 0, 1, 2, 3};
        intStream = AsIntStream.of(intArr);
    }

    @Test
    public void testStreamOperations() {
        System.out.println("streamOperations");
        int expResult = 42;
        int result = StreamApp.streamOperations(intStream);
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamToArray() {
        System.out.println("streamToArray");
        int[] expResult = {-1, 0, 1, 2, 3};
        int[] result = StreamApp.streamToArray(intStream);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testStreamForEach() {
        System.out.println("streamForEach");
        String expResult = "-10123";
        String result = StreamApp.streamForEach(intStream);
        assertEquals(expResult, result);
    }

    @Test
    public void testStreamMinMax() {
        int res = intStream.filter(x -> x > 0).max();
        assertEquals(3, res);
        int res2 = intStream.filter(x -> x > 0).min();
        assertEquals(1, res2);
    }

    @Test
    public void testStreamAverage() {
        double res = intStream.map(x -> x * x).average();
        assertEquals(3.0, res, 0.0);
        assertEquals(intStream.count(), 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamEmpty(){
        AsIntStream emptyList = new AsIntStream();
        emptyList.sum();
    }
}
