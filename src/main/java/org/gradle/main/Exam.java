package org.gradle.main;

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
        for (double i = min; i<max; i += interval, j++)
            thresholds.add(new Threshold(i, grade.getDistribution().get(j)));
    }
    
    public TreeSet<Threshold> getThresholds()
    {
        return thresholds;
    }
    
    public void setThresholds(TreeSet<Threshold> newThresholds)
    {
        thresholds = newThresholds;
    }
}
