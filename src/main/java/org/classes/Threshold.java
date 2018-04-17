package org.classes;

public class Threshold implements Comparable<Threshold> {
    private double grade, percentage, points;
    
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
    
    public void setPercentage(double percentage)
    {
        this.percentage = percentage;
    }
    
    public void setPoints(double points)
    {
        this.points = points;
    }
        
    public double getPoints()
    {
        return points;
    }
    
    public double getGrade()
    {
        return grade;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Grade: ");
        sb.append(grade);
        sb.append(" Percentage: ");
        sb.append(percentage);
        sb.append(" Points: ");
        sb.append(points);
        return sb.toString();
    }
}
