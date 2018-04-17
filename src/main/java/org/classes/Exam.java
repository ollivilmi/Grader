package org.classes;

import java.util.TreeSet;

public class Exam {
    private double min, max;
    private Grade grade;
    private TreeSet<Threshold> thresholds;
    
    public Exam(double min, double max, Grade grade)
    {
        this.min = min;
        this.max = max;
        this.grade = grade;
        
        // calculate standard interval for grade thresholds
        double interval = (max-min) / grade.getAmount();
        
        // Ordered TreeSet
        // Threshold contains Points - Grade
        thresholds = new TreeSet<>();
        int j = 0;
        for (double i = min; j<grade.getAmount(); i += interval, j++)
            thresholds.add(new Threshold(i/max, i, grade.getDistribution().get(j)));
    }
    
    public TreeSet<Threshold> getThresholds()
    {
        return thresholds;
    }
    
    public void setThresholds(TreeSet<Threshold> newThresholds)
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
        for (Threshold t : thresholds)
            sb.append(t);
        return sb.toString();
    }
}
