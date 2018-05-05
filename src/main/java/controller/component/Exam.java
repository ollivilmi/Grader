package controller.component;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class Exam {
    private double minPoints, maxPoints, rangeOfPoints;
    private int thresholdPreset;
    private Grade grade;
    private TreeMap<Double, Double> thresholds;
    private TreeMap<Integer, Student> students;
    
    public Exam(double min, double max, Grade grade, int preset)
    {
        updateConfig(min, max, grade, preset);
        this.students = new TreeMap<>();
    }
    
    public void updateConfig(double min, double max, Grade grade, int preset)
    {
        if (this.grade == null)
            this.grade = grade;
        else if (grade.compareTo(this.grade) == -1)
            this.grade = grade;
        
        this.minPoints = min;
        this.maxPoints = max;
        this.thresholdPreset = preset;
        this.rangeOfPoints = max-min;
        generateThresholds();
    }

    public void generateThresholds()
    {     
        // Ordered TreeMap
        // Threshold contains Grade - Points
        thresholds = new TreeMap<>();
                
        // preset: int value in <select id="preset"></select>
        switch (thresholdPreset)
        {
            case 1:
                // calculate standard interval for grade thresholds
                double interval = (rangeOfPoints) / grade.getAmount(), points = minPoints;
                
                // distributes Points from Min -> Max by adding the interval
                // for each iteration
                for (int i = 0; i<grade.getAmount(); points += interval, i++)
                    thresholds.put(grade.getDistribution().get(i), roundToHalf(points));
                break;
                
            case 2:
                // Uses a variance of max-min/4 for random gaussian distribution
                double variance = (rangeOfPoints)/4, mean = (maxPoints+minPoints)/2;
                
                Iterator iterator = randomGaussianDistribution(mean, variance).iterator();
                thresholds.put(grade.getDistribution().get(0), minPoints);    
                
                for (int i = 1, n = grade.getAmount(); i<n; i++)
                    thresholds.put(grade.getDistribution().get(i), (double) iterator.next());
                break;
                
            // Easy exam - most thresholds near max points
            case 3:
                // Get N:th root = amount of grades total for exponential growth
                // Value range = total points - 5% of total points
                double growthMultiplier = nthRoot(grade.getAmount(), (rangeOfPoints)-(0.05*rangeOfPoints));
                thresholds.put(grade.getDistribution().get(0), minPoints);
                
                // We receive growth value, which is used in the function:
                // growth ^ x
                for (int i = 1, n = grade.getAmount(); i<n; i++)
                    thresholds.put(grade.getDistribution().get(i), roundToHalf(minPoints+Math.pow(growthMultiplier, i+1)));
                break;
            // Hard exam - most thresholds near min points
            case 4:
                growthMultiplier = nthRoot(grade.getAmount(), (rangeOfPoints)-(0.05*rangeOfPoints));
                thresholds.put(grade.getDistribution().get(0), minPoints);
                
                // Same principle as case 3 with reverse order (max - value)
                for (int i = grade.getAmount()-1, j = 1; i>0; i--, j++)
                    thresholds.put(grade.getDistribution().get(j), roundToHalf(maxPoints-Math.pow(growthMultiplier, i+1)));
                break;
        }
    }
    
    /***
     * Randomizes values between the mean by using the variance value
     * 
     * For standard normal distribution, (max-min)/4 is used for variance.
     * 
     * @param mean
     * @param variance
     * @return 
     */
    private TreeSet<Double> randomGaussianDistribution(Double mean, double variance)
    {
        double randomGaussian = 0;
        TreeSet<Double> grades = new TreeSet<>();
        Random random = new Random();

        while (grades.size() < grade.getAmount()-1)
        {
            randomGaussian = roundToHalf(mean + random.nextGaussian() * variance);
            if (randomGaussian > maxPoints*0.95)
                grades.add(roundToHalf(maxPoints*0.95));
            else if (randomGaussian > minPoints)
                grades.add(randomGaussian);
        }
        return grades;
    }
    
    /***
     * Gets the n:th root for value x
     * 
     * @param n root
     * @param x value
     * @return n:th root
     */
    private double nthRoot(int n, double x) 
    {
        double x1 = x, x2 = x / n;
        while (Math.abs(x1 - x2) > 0.001) 
        {
            x1 = x2;
            x2 = ((n - 1.0) * x2 + x / Math.pow(x2, n - 1.0)) / n;
        }
        return x2;
    }
    
    /***
     * Rounds value to the nearest 0.5
     * @param value to round
     * @return rounded value
     */
    private double roundToHalf(double value)
    {
        return Math.round(value * 2) / 2.0;
    }
    
    public TreeMap<Double, Double> getThresholds()
    {
        return thresholds;
    }
    
    public TreeMap<Double, Double> getReverseMap()
    {
        TreeMap<Double,Double> reverse = new TreeMap<>();
        for (Map.Entry<Double,Double> e : thresholds.entrySet())
            reverse.put(e.getValue(), e.getKey());
        return reverse;
    }
    
        /***
     * When changing a single Threshold, check other Thresholds to maintain
     * order by adjusting their points
     * 
     * @param grade
     * @param points
     * @param previousPoints 
     */
    public void setThreshold(double grade, double points, double previousPoints)
    {
        if (points < minPoints)
            points = minPoints;
        else if (points > maxPoints)
            points = maxPoints;
        thresholds.replace(grade, points);
        // If the points were increased, check the above grades and increase
        // their points to the new threshold + 0.5
        if (points > previousPoints)
        {
            Map<Double,Double> higherGrades = thresholds.tailMap(grade);
            for (Map.Entry<Double, Double> t : higherGrades.entrySet())
            {
                if (t.getValue() <= points)
                {
                    t.setValue(points);
                    if (points < maxPoints)
                        points += 0.5;
                }
            }
        }
        // If the points were lower than before check the grades below
        else if (points < previousPoints)
        {
            Map<Double,Double> lowerGrades = thresholds.headMap(grade);
            // Amount of elements left to iterate
            int i = lowerGrades.entrySet().size();
            for (Map.Entry<Double, Double> t : lowerGrades.entrySet())
            {
                double newValue = points-(i--*0.5);
                if (t.getValue() >= points)
                {
                    if (newValue < minPoints)
                        newValue = minPoints;
                    t.setValue(newValue);
                }
            }
        }
    }
    
    public void setThresholds(TreeMap<Double, Double> newThresholds)
    {
        thresholds = newThresholds;
    }
    
    public double getRangeOfPoints()
    {
        return rangeOfPoints;
    }
    
    public double getMax()
    {
        return maxPoints;
    }
    
    public double getMin()
    {
        return minPoints;
    }
    
    public int getPreset()
    {
        return thresholdPreset;
    }
    
    public Grade getGrade()
    {
        return grade;
    }
    
    public TreeMap<Integer, Student> getStudents()
    {
        return students;
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
