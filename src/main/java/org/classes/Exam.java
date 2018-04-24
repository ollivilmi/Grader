package org.classes;

import java.util.Map;
import java.util.TreeMap;

public class Exam {
    private double min, max;
    private Grade grade;
    private TreeMap<Double, Double> thresholds;
    
    public Exam(double min, double max, Grade grade)
    {
        this.min = min;
        this.max = max;
        this.grade = grade;
        
        // calculate standard interval for grade thresholds
        double interval = (max-min) / grade.getAmount();
        
        // Ordered TreeMap
        // Threshold contains Grade - Points
        thresholds = new TreeMap<>();
        int j = 0;
        for (double i = min; j<grade.getAmount(); i += interval, j++)
            thresholds.put(grade.getDistribution().get(j), i);
    }
    
    public TreeMap<Double, Double> getThresholds()
    {
        return thresholds;
    }
    
    public void setThresholds(TreeMap<Double, Double> newThresholds)
    {
        thresholds = newThresholds;
    }
    
    public double getPoints()
    {
        return max-min;
    }
    
    public double getMax()
    {
        return max;
    }
    
    public Grade getGrade()
    {
        return grade;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Double, Double> t : thresholds.entrySet())
            sb.append(t.getKey()).append(" ").append(t.getValue());
        return sb.toString();
    }
}
