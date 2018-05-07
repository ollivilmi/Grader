package controller.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class Exam {
    private double minPoints, maxPoints, rangeOfPoints;
    private int thresholdPreset = 0;
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
        boolean gradeChanged = false;
        if (this.grade == null)
            this.grade = grade;
        else if (grade.compareTo(this.grade) == -1)
        {
            this.grade = grade;
            gradeChanged = true;
        }
        
        this.rangeOfPoints = max-min;
        
        // Only generate Thresholds if configurations are changed
        // (To avoid unnecessarily resetting them)
        if (rangeOfPoints > 0 && preset != thresholdPreset || gradeChanged
                || minPoints != min || maxPoints != max)
        {
            this.minPoints = min;
            this.maxPoints = max;
            this.thresholdPreset = preset;
            generateThresholds();
        }
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
                
                Iterator iterator = randomGaussianDistribution().iterator();
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
    private ArrayList<Double> randomGaussianDistribution()
    {   
        // Generate random values for gaussian distribution
        double[] randomValues = new double[100];
        Random random = new Random();
        for (int i=0;i<100;i++)
            randomValues[i] = maxPoints*random.nextDouble();
        
        Mean mean = new Mean();
        StandardDeviation sd = new StandardDeviation();
        // Use mean & deviation from randomized values to generate thresholds
        NormalDistribution normalDistribution = new NormalDistribution(mean.evaluate(randomValues),sd.evaluate(randomValues));
        
        double interval = rangeOfPoints/grade.getAmount(), iteration = minPoints;
        ArrayList<Double> grades = new ArrayList<>();
        
        for (int i=0;i<grade.getAmount();i++, iteration+=interval)
        {
            double value = normalDistribution.cumulativeProbability(iteration) * maxPoints;
            if (value < minPoints)
                value = minPoints;
            grades.add(roundToHalf(value));
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
     * @param newPoints
     * @param previousPoints 
     */
    public void setThreshold(double grade, double newPoints, double previousPoints)
    {
        if (newPoints < minPoints)
            newPoints = minPoints;
        else if (newPoints > maxPoints)
            newPoints = maxPoints;
        thresholds.replace(grade, newPoints);
        // If the points were increased, check the above grades and increase
        // their points to the new threshold + 0.5
        if (newPoints > previousPoints)
        {
            Map<Double,Double> higherGrades = thresholds.tailMap(grade);
            for (Map.Entry<Double, Double> threshold : higherGrades.entrySet())
            {
                if (threshold.getValue() <= newPoints)
                {
                    // Iterates the values of the tailMap
                    // (From *Threshold being changed -> Last threshold)
                    threshold.setValue(newPoints);
                    if (newPoints < maxPoints)
                        newPoints += 0.5;
                }
            }
        }
        // If the points were lower than before check the grades below
        else if (newPoints < previousPoints)
        {
            Map<Double,Double> lowerGrades = thresholds.headMap(grade);
            int amountOfThresholds = lowerGrades.entrySet().size();
            for (Map.Entry<Double, Double> threshold : lowerGrades.entrySet())
            {
                // Iterates the values of the headMap
                // (From First threshold -> *Threshold being changed)
                // Lowers grade thresholds to be lower than the changed threshold
                double newValue = newPoints-(amountOfThresholds--*0.5);
                if (threshold.getValue() >= newPoints)
                {
                    if (newValue < minPoints)
                        newValue = minPoints;
                    threshold.setValue(newValue);
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
