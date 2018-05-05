package main;

import java.util.ArrayList;
import java.util.TreeMap;
import org.junit.Test;
import controller.component.Exam;
import controller.component.Grade;
import static org.junit.Assert.*;

public class Exam_Tests {
    @Test public void correctDefaults() {
        Grade grade = new Grade(1,5,1);
        Exam exam = new Exam(10,20,grade, 1);
        TreeMap<Double, Double> thresholds = exam.getThresholds();
        
        assertEquals("Default grade Thresholds for Grade(1,5,1) & Exam (10,20,grade) should be [1,10], [2,12], [3,14], [4,16], [5,18]", 10, thresholds.firstEntry().getValue(), 0.01);
        assertEquals("Default grade Thresholds for Grade(1,5,1) & Exam (10,20,grade) should be [1,10], [2,12], [3,14], [4,16], [5,18]", 18, thresholds.lastEntry().getValue(), 0.01);
        assertEquals("Default grade Thresholds for Grade(1,5,1) & Exam (10,20,grade) should be [1,10], [2,12], [3,14], [4,16], [5,18]", 12, thresholds.higherEntry(1.0).getValue(), 0.01);
    
        grade = new Grade(4,10,0.5);
        exam = new Exam(10,23,grade, 1);
        thresholds = exam.getThresholds();
        
        assertEquals("Default grade Thresholds for Grade(4,10,0.5) & Exam (10,23,grade) should be min 10 max 22", 10, thresholds.firstEntry().getValue(), 0.01);
        assertEquals("Default grade Thresholds for Grade(4,10,0.5) & Exam (10,23,grade) should be min 10 max 22", 22, thresholds.lastEntry().getValue(), 0.01);
    }
    
    @Test public void presetsWork() {
        Grade grade = new Grade(1,5,0.5);
        ArrayList<Exam> exams = new ArrayList<>();
        for (int i = 1; i<5; i++)
            exams.add(new Exam(10,30,grade,i));
        
        assertEquals(exams.size(), 4);
        
        for (Exam exam : exams)
            assertNotNull(exam);
    }
    
    @Test public void updateThresholds() {
        Grade grade = new Grade(1,5,0.5);
        Exam exam1 = new Exam(10,30,grade,1);
        Exam exam2 = new Exam(10,30,grade,1);
        assertEquals(exam1.getThresholds(), exam2.getThresholds());
        
        exam1.updateConfig(10, 50, grade, 3);
        assertTrue(exam1.getThresholds() != exam2.getThresholds());
        exam2.updateConfig(10, 50, grade, 3);
        assertEquals(exam1.getThresholds(), exam2.getThresholds());
        exam1.updateConfig(10, 50, grade, 2);
        assertTrue(exam1.getThresholds() != exam2.getThresholds());
    }
    
    @Test public void multipleTimesAndValues() {
        Grade grade = new Grade(4,10,0.5);
        for (int i = 10, j = 50; i < 500; i+=20, j+=20)
            for (int k = 1; k<5; k++)
                assertNotNull(new Exam(i,j,grade,2));
    }
    
    @Test public void reverseMap() 
    {
        Grade grade = new Grade(1,5,0.5);
        Exam exam = new Exam(40,80,grade,1);
        for (int i = 10, j = 50; i < 500; i+=20, j+=20)
            for (int k = 1; k<5; k++)
            {
                exam.updateConfig(i, j, grade, k);
                assertEquals(exam.getReverseMap().lastKey(), exam.getThresholds().lastEntry().getValue());
                assertEquals(exam.getReverseMap().firstKey(), exam.getThresholds().firstEntry().getValue());
            }
    }
}
