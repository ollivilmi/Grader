package main;

import org.junit.Test;
import static org.junit.Assert.*;
import org.classes.Grade;

public class Grade_Tests {
    @Test public void correctGradeThresholds() {
        Grade grade = new Grade(1,5, 0.5);
        assertEquals("There should be 9 thresholds for grades 1-5 with an interval of 0.5", 9, grade.getAmount(), 0.01);
        grade = new Grade(1,5,1);
        assertEquals("There should be 5 thresholds for grades 1-5 with an interval of 1", 5, grade.getAmount(), 0.01);
        grade = new Grade(4,10, 0.5);
        assertEquals("There should be 13 thresholds for grades 4-10 with an interval of 0.5", 13, grade.getAmount(), 0.01);
    }
    
    @Test public void correctGradeMinMax() {
        Grade grade = new Grade(1,100, 0.5);
        assertEquals("The min grade should be 1", 1, grade.getMin(), 0.01);
        assertEquals("The max grade should be 100", 100, grade.getMax(), 0.01);
    }
}