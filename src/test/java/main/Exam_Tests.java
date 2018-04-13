package main;

import java.util.TreeSet;
import org.junit.Test;
import org.classes.Exam;
import org.classes.Grade;
import org.classes.Threshold;
import static org.junit.Assert.*;

public class Exam_Tests {
    @Test public void correctDefaults() {
        Grade grade = new Grade(1,5,1);
        Exam exam = new Exam(10,20,grade);
        TreeSet<Threshold> thresholds = exam.getThresholds();
        
        assertEquals("Default grade Thresholds for Grade(1,5,1) & Exam (10,20,grade) should be [1,10], [2,12], [3,14], [4,16], [5,18]", 10, thresholds.last().getPoints(), 0.01);
        assertEquals("Default grade Thresholds for Grade(1,5,1) & Exam (10,20,grade) should be [1,10], [2,12], [3,14], [4,16], [5,18]", 18, thresholds.first().getPoints(), 0.01);
        assertEquals("Default grade Thresholds for Grade(1,5,1) & Exam (10,20,grade) should be [1,10], [2,12], [3,14], [4,16], [5,18]", 12, thresholds.lower(thresholds.last()).getPoints(), 0.01);
    
        grade = new Grade(4,10,0.5);
        exam = new Exam(10,23,grade);
        thresholds = exam.getThresholds();
        
        assertEquals("Default grade Thresholds for Grade(4,10,0.5) & Exam (10,23,grade) should be min 10 max 22", 10, thresholds.last().getPoints(), 0.01);
        assertEquals("Default grade Thresholds for Grade(4,10,0.5) & Exam (10,23,grade) should be min 10 max 22", 22, thresholds.first().getPoints(), 0.01);
    }
}
