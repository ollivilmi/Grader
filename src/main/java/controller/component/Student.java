package controller.component;

public class Student {
    
    private int id;
    private String name;
    private double points, grade;
    
    public Student(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.points = 0;
        this.grade = 0;
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
    
    public double getGrade()
    {
        return grade;
    }
    
    public void setPoints(double points)
    {
        this.points = points;
    }
    
    public void setGrade(double grade)
    {
        this.grade = grade;
    }
}
