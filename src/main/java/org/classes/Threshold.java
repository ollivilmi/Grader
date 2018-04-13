package org.classes;

public class Threshold implements Comparable<Threshold> {
    private double points, grade;
    
    public Threshold(double points, double grade)
    {
        this.points = points;
        this.grade = grade;
    }
    
    @Override
    public int compareTo(Threshold t) {
        if (t.grade > this.grade)
            return 1;
        else return -1;
    }

    public double getPoints()
    {
        return points;
    }
    
    public void setPoints(double points)
    {
        this.points = points;
    }
    
    public double getGrade()
    {
        return grade;
    }
}
