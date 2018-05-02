package main;

import java.util.Map;
import org.junit.Test;
import org.classes.Exam;
import org.classes.Grade;
import org.classes.Grader;
import static org.junit.Assert.*;

public class Grader_Tests {
    @Test public void testPoints() {
        Grade grade = new Grade(1,5,0.5);
        Exam exam = new Exam(10,30,grade,1);
        Grader grader = new Grader(grade, exam);
        for (Map.Entry<Double, Double> t : grader.getExam().getThresholds().entrySet())
        {
            double previousValue = t.getValue();
            grader.setByPoints(t.getKey(), previousValue+1);
            assertTrue(previousValue < t.getValue());
            grader.setByPoints(t.getKey(), previousValue);
            assertTrue(previousValue == t.getValue());
        }
        
    }
}
