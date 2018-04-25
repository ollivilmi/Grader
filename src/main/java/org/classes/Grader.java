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
    
    public void updateConfig(double minGrade, double maxGrade, double intervalGrade, double minPoints, double maxPoints)
    {
        Grade newGrade = new Grade(minGrade, maxGrade, intervalGrade);
        if (grade.compareTo(newGrade) == -1)
        {
            this.grade = newGrade;
            this.exam = new Exam(minPoints, maxPoints, grade);
        }
        else exam.updateConfig(minPoints, maxPoints);
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
        setPoints(grade, points, previousPoints);
    }
    
    // Change by points
    public void setByPoints(double grade, double points)
    {
        double previousPoints = exam.getThresholds().ceilingEntry(grade).getValue();
        points = Math.round(points * 2) / 2.0;
        setPoints(grade, points, previousPoints);
    }
    
    /***
     * When changing a single Threshold, check other Thresholds to maintain
     * order by adjusting their points
     * 
     * @param grade
     * @param points
     * @param previousPoints 
     */
    public void setPoints(double grade, double points, double previousPoints)
    {
        exam.getThresholds().replace(grade, points);
                
        // If the points were increased, check the above grades and increase
        // their points to the new threshold + 0.5
        if (points > previousPoints)
        {
            Map<Double,Double> higherGrades = exam.getThresholds().tailMap(grade);
            for (Map.Entry<Double, Double> t : higherGrades.entrySet())
            {
                if (t.getValue() < points)
                {
                    points += 0.5;
                    t.setValue(points);
                }
            }
        }
        // If the points were lower than before check the grades below
        else if (points < previousPoints)
        {
            Map<Double,Double> lowerGrades = exam.getThresholds().headMap(grade);
            // Amount of elements left to iterate
            int i = lowerGrades.entrySet().size();
            for (Map.Entry<Double, Double> t : lowerGrades.entrySet())
            {
                if (t.getValue() > points)
                {
                    t.setValue(points-(i--*0.5));
                }
                else
                    i--;
            }
        }
    }
}
