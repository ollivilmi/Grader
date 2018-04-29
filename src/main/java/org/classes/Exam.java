package org.classes;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class Exam {
    private double min, max;
    private int preset;
    private Grade grade;
    private TreeMap<Double, Double> thresholds;
    
    public Exam(double min, double max, Grade grade, int preset)
    {
        this.min = min;
        this.max = max;
        this.grade = grade;
        this.preset = preset;
        generateThresholds();
    }
    
    public void updateConfig(double min, double max, int preset)
    {
        this.min = min;
        this.max = max;
        this.preset = preset;
        generateThresholds();
    }

    private void generateThresholds()
    {
        
        // Ordered TreeMap
        // Threshold contains Grade - Points
        thresholds = new TreeMap<>();
        double mean = (max+min)/2;
                
        // preset: configuration selected in <select id="preset">
        switch (preset)
        {
            case 1:
                // Equal spread of grades where the deviation between grades is
                // equal
                
                // calculate standard interval for grade thresholds
                double deviation = (max-min) / grade.getAmount();
                
                double g = min;
                for (int i = 0; i<grade.getAmount(); g += deviation, i++)
                    thresholds.put(grade.getDistribution().get(i), roundToHalf(g));
                break;
                
            case 2:
                // Uses a variance of max-min/4 for random gaussian distribution
                double variance = (max-min)/4;
                Iterator iterator = randomGaussianDistribution(mean, variance).iterator();
                thresholds.put(grade.getDistribution().get(0), min);    
                
                for (int i = 1, n = grade.getAmount(); i<n; i++)
                    thresholds.put(grade.getDistribution().get(i), (double) iterator.next());
                break;
                
            case 3:
                // Easy exam - deviation between grades increases towards higher grades
                
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }
    
    private TreeSet<Double> randomGaussianDistribution(Double mean, double variance)
    {
        double gaussian = 0;
        TreeSet<Double> grades = new TreeSet<>();
        Random random = new Random();

        while (grades.size() < grade.getAmount()-1)
        {
            gaussian = mean + random.nextGaussian() * variance;
            gaussian = roundToHalf(gaussian);
            if (gaussian > max*0.95)
                gaussian = max*0.95;
            if (gaussian > min)
                grades.add(roundToHalf(gaussian));
        }
        return grades;
    }
    
    private double roundToHalf(double value)
    {
        return Math.round(value * 2) / 2.0;
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
    
    public double getMin()
    {
        return min;
    }
    
    public int getPreset()
    {
        return preset;
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
