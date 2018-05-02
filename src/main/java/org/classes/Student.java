package org.classes;

import java.util.HashMap;

public class Student {
    
    private int id;
    private String name;
    private double points;
    private HashMap<Exam, Double> pointsByExam;
    
    public Student(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.pointsByExam = new HashMap<>();
        this.points = 0;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getPoints()
    {
        return points;
    }
    
    public void setPoints(double value)
    {
        this.points = value;
    }
    
    public void addResult(Exam exam, double points)
    {
        pointsByExam.put(exam, points);
    }
    
    public Double getResult(Exam exam)
    {
        Double result = pointsByExam.get(exam);
        if (result != null)
            return result;
        else return 0.0;
    }
}
