package org.classes;

import java.util.Map;
import java.util.TreeMap;

public class Grader {
    private Grade grade;
    private Exam exam;
    private double maxPoints;
    
    public Grader(double minGrade, double maxGrade, double intervalGrade, double minPoints, double maxPoints)
    {
        this.grade = new Grade(minGrade, maxGrade, intervalGrade);
        this.exam = new Exam(minPoints, maxPoints, grade);
        this.maxPoints = maxPoints;
    }
    
    public TreeMap<Double, Double> getThresholds()
    {
        return exam.getThresholds();
    }
    /***
     * Sets the target grade to % of points received from the test.
     * (70% of points to get 3 for example)
     * 
     * Checks higher & lower value in TreeSet to main order
     * 
     * @param grade
     * @param percentage 
     */
    public void setByPercentage(double grade, double percentage)
    {
        double points = percentage * maxPoints;
        exam.getThresholds().replace(grade, points);
    }
    
    public void setByPoints(double grade, double points)
    {
        exam.getThresholds().replace(grade, points);
    }
}
