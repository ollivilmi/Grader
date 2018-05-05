package main;

import controller.model.Threshold;
import org.junit.Test;
import static org.junit.Assert.*;

public class Threshold_Tests {
    
    public Threshold_Tests() {
    }
    /**
     * Test of compareTo method, of class Threshold.
     */
    @Test(expected=NullPointerException.class)
    public void testCompareTo() {
        System.out.println("compareTo");
        Threshold t = null;
        Threshold instance = null;
        int expResult = 0;
        int result = instance.compareTo(t);
        assertEquals(expResult, result);
    }

    
    @Test
    public void testPerc() {
        Threshold thresh = new Threshold(0.33, 30, 1);
        double get = thresh.getPercentage();
        double expResult = 0.33;
        assertEquals(expResult, get, 0.0);
    }
    
    
        @Test
    public void testGrade() {
        Threshold thresh = new Threshold(0.33, 30, 1);
        double get = thresh.getGrade();
        double expResult = 1;
        assertEquals(expResult, get, 0.0);
    }
    
    
        @Test
    public void testPoints() {
        Threshold thresh = new Threshold(0.33, 30, 1);
        double get = thresh.getPoints();
        double expResult = 30;
        assertEquals(expResult, get, 0.0);
    }    
    
}
