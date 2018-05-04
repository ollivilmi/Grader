package controller;

import controller.model.Statistic;
import controller.model.Threshold;
import controller.component.Student;
import controller.component.Grade;
import controller.component.Exam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.util.Pair;

public class Grader {
    private Grade grade;
    private Exam exam;
    private double maxPoints;
    
    public Grader(double minGrade, double maxGrade, double intervalGrade, double minPoints, double maxPoints, int preset)
    {
        this.grade = new Grade(minGrade, maxGrade, intervalGrade);
        this.exam = new Exam(minPoints, maxPoints, grade, preset);
        this.maxPoints = maxPoints;
    }
    
    public Grader(Grade grade, Exam exam)
    {
        this.grade = grade;
        this.exam = exam;
        this.maxPoints = exam.getMax();
    }
    
    public void updateConfig(double minGrade, double maxGrade, double intervalGrade, double minPoints, double maxPoints, int preset)
    {
        Grade newGrade = new Grade(minGrade, maxGrade, intervalGrade);
        this.maxPoints = maxPoints;
        if (grade.compareTo(newGrade) == -1)
        {
            this.grade = newGrade;
            this.exam = new Exam(minPoints, maxPoints, grade, preset);
        }
        else exam.updateConfig(minPoints, maxPoints, preset);
    }
    
    public void addStudent(int id, String name)
    {
        exam.getStudents().put(id, new Student(id, name));
    }
    
    public void removeStudent(int id)
    {
        exam.getStudents().remove(id);
    }
    
    public void addResult(int id, double result)
    {
        exam.getStudents().get(id).setPoints(result);
    }
    
    public Pair<ArrayList<Student>,Statistic> getExamResults()
    {
        return new Pair(new ArrayList<>(exam.getStudents().values()), new Statistic(exam));
    }
    
    /***
     * Returns the TreeMap in a List<Threshold> format so that the
     * REST API prints them as proper JSON objects
     * @return 
     */
    public List<Threshold> getThresholds()
    {
        List<Threshold> thresholds = new ArrayList<>();
        
        for (Map.Entry<Double, Double> t : exam.getThresholds().entrySet())
            thresholds.add(new Threshold(t.getValue()/maxPoints, t.getValue(), t.getKey()));
        
        return thresholds;
    }
    /***
     * Sets the target grade to % of points received from the test.
     * (70% of points to get 3 for example)
     * 
     * @param grade
     * @param percentage 
     */
    public void setByPercentage(double grade, double percentage)
    {
        double points = percentage/100 * maxPoints, 
        previousPoints = exam.getThresholds().ceilingEntry(grade).getValue();
        points = Math.round(points * 2) / 2.0;
        exam.setThreshold(grade, points, previousPoints);
    }
    
    // Change by points
    public void setByPoints(double grade, double points)
    {
        double previousPoints = exam.getThresholds().ceilingEntry(grade).getValue();
        points = Math.round(points * 2) / 2.0;
        exam.setThreshold(grade, points, previousPoints);
    }
    
    public Exam getExam()
    {
        return exam;
    }
    
    public Grade getGrade()
    {
        return grade;
    }
}
