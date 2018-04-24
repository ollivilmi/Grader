package org.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * Checks higher & lower value in TreeSet to main order
     * 
     * @param grade
     * @param percentage 
     */
    public void setByPercentage(double grade, double percentage)
    {
        double points = percentage * maxPoints, previousPoints = exam.getThresholds().floorKey(grade);
        // Round to nearest 0.5 points
        points = Math.round(points * 2) / 2.0;
        exam.getThresholds().replace(grade, points);
        
        if (points > previousPoints)
        {
            Map<Double,Double> higherGrades = exam.getThresholds().tailMap(grade);
            for (Map.Entry<Double, Double> t : higherGrades.entrySet())
                if (t.getValue() < points)
                {
                    points += 0.5;
                    t.setValue(points);
                }
                else if (t.getValue() > points)
                    break;
        }
        else if (points < previousPoints)
        {
            Map<Double,Double> lowerGrades = exam.getThresholds().headMap(points);
            for (Map.Entry<Double, Double> t : lowerGrades.entrySet())
                if (t.getValue() > points)
                {
                    points -= 0.5;
                    t.setValue(points);
                }
                else if (t.getValue() < points)
                    break;
        }
    }
    
    public void setByPoints(double grade, double points)
    {
        exam.getThresholds().replace(grade, points);
    }
}
