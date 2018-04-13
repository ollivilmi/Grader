package org.gradle.main;

import java.util.ArrayList;
import java.util.List;

public class Grade {
    private List<Double> gradeList;
    private int amount;
    
    /***
     * For unique grading systems. Creates a List of 
     * all the possible grades from the minimum to maximum,
     * by using the interval set by the user.
     * 
     * @param min Minimum grade
     * @param max Maximum grade
     * @param interval The numeric difference between grades.
     * For example: Grades 1-2 with the interval of 0.5 = 1 - 1.5 - 2
     */
    public Grade(double min, double max, double interval)
    {   
        gradeList = new ArrayList<>();
        for (double i = min; i<=max; i+= interval)
            gradeList.add(i);
        
        amount = gradeList.size();
    }
    
    /***
     * Get the list of possible grades
     * @return 
     */
    public List<Double> getDistribution()
    {
        return gradeList;
    }
    
    /***
     * Get the amount of possible grades (Used for calculating
     * thresholds)
     * @return 
     */
    public int getAmount()
    {
        return amount;
    }
    
    public double getMin()
    {
        return gradeList.get(0);
    }
    
    public double getMax()
    {
        return gradeList.get(amount-1);
    }
}
