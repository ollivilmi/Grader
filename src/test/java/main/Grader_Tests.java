package main;

import java.util.Map;
import java.util.Random;
import org.junit.Test;
import controller.component.Exam;
import controller.component.Grade;
import controller.Grader;
import static org.junit.Assert.*;

public class Grader_Tests {
    @Test public void testPoints() {
        Grader grader = testObject();
        for (Map.Entry<Double, Double> t : grader.getExam().getThresholds().entrySet())
        {
            double previousValue = t.getValue();
            grader.setByPoints(t.getKey(), previousValue+1);
            assertTrue(previousValue < t.getValue());
            grader.setByPoints(t.getKey(), previousValue);
            assertTrue(previousValue == t.getValue());
        }  
            grader.setByPoints(1.0, 15.0);
            assertTrue(grader.getExam().getThresholds().get(1.0) == 15);
            grader.setByPoints(1.0, 10.0);
            assertTrue(grader.getExam().getThresholds().get(1.0) == 10);
    }
    
    @Test public void testPercentages() {
        Grader grader = testObject();
        for (Map.Entry<Double, Double> t : grader.getExam().getThresholds().entrySet())
        {
            grader.setByPercentage(t.getKey(), 50);
            assertTrue(t.getValue() == 15);
        }  
            grader.setByPercentage(1.0, 0);
            assertTrue(grader.getExam().getThresholds().get(1.0) == 10);
            grader.setByPercentage(1.0, 120);
            assertTrue(grader.getExam().getThresholds().get(1.0) == 30);
    }
    
    @Test public void addStudents() {
        Grader grader = testObject();
        Random random = new Random();
        for (int i = 0; i<100; i++)
        {
            grader.addStudent(i, "student");
            grader.addResult(i, 30 * random.nextDouble());
        }
        assertNotNull(grader.getExamResults());
    }
    
    @Test public void getStatistics() {
        Grader grader = testObject();
        grader.addStudent(1, "student");
        assertNotNull(grader.getExamResults());
    }
    
    @Test public void addAndRemoveStudents() {
        Grader grader = testObject();
        for (int i = 0; i<100; i++)
            grader.addStudent(i, "student");
        assertTrue(grader.getExam().getStudents().size() == 100);
        for (int i = 0; i<100; i++)
            grader.removeStudent(i);
        assertTrue(grader.getExam().getStudents().isEmpty());
    }
    
    @Test public void addNegativeResults() {
        Grader grader = testObject();
        Random random = new Random();
        for (int i = 0; i<100; i++)
        {
            grader.addStudent(i, "student");
            grader.addResult(i, -30*random.nextDouble());
        }
        assertNotNull(grader.getExamResults());
    }
    
    private Grader testObject()
    {
        Grade grade = new Grade(1,5,0.5);
        Exam exam = new Exam(10,30,grade,1);
        return new Grader(grade, exam);
    }
}
