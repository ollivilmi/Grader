package controller.component;

import java.util.ArrayList;
import java.util.List;

public class Grade implements Comparable<Grade> {
    private final List<Double> gradeList;
    private final int amount;
    private final double min, max, interval;
    
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
        this.min = min;
        this.max = max;
        this.interval = interval;
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
        return min;
    }
    
    public double getMax()
    {
        return max;
    }
    
    public double getInterval()
    {
        return interval;
    }

    @Override
    public int compareTo(Grade comparison) {
        if (this.max == comparison.max && this.min == comparison.min 
                && this.interval == comparison.interval)
            return 0;
        else return -1;
    }
}
