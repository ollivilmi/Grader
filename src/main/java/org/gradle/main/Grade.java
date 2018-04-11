package org.gradle.main;

import java.util.TreeSet;

public class Grade {
    private TreeSet<Double> gradeTree;
    private int amount;
    
    /***
     * For unique grading systems. Creates a TreeSet 
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
        gradeTree = new TreeSet<>();
        for (double i = min; i<=max; i+= interval)
            gradeTree.add(i);
        
        amount = gradeTree.size();
    }
    
    /***
     * Get the list of possible grades
     * @return 
     */
    public TreeSet<Double> getDistribution()
    {
        return gradeTree;
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
        return gradeTree.first();
    }
    
    public double getMax()
    {
        return gradeTree.last();
    }
}
