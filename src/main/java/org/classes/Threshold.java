package org.classes;

public class Threshold implements Comparable<Threshold> {
    private double grade, percentage, points;
    
    /***
     * Class used for Spring REST API to build JSON automatically
     * @param percentage % of total points
     * @param points points for Grade
     * @param grade grade for the amount of points
     */
    public Threshold(double percentage, double points, double grade)
    {
        this.points = points;
        this.percentage = percentage;
        this.grade = grade;
    }
    
    public Threshold(double percentage, double grade)
    {
        this.percentage = percentage;
        this.grade = grade;
    }
    
    @Override
    public int compareTo(Threshold t) {
        if (t.grade > this.grade)
            return 1;
        else if (t.grade == this.grade)
            return 0;
        else return -1;
    }

    public double getPercentage()
    {
        return percentage;
    }
        
    public double getPoints()
    {
        return points;
    }
    
    public double getGrade()
    {
        return grade;
    }
}