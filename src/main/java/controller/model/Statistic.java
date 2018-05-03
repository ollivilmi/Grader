package controller.model;

import controller.component.Student;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class Statistic {
    private List<Double> pointStats, gradeStats;
    
    public Statistic (List<Student> students)
    {
        pointStats = new ArrayList<>();
        gradeStats = new ArrayList<>();
        double [] grades = new double[students.size()];
        double [] points = new double[students.size()];
        
        for (int i = 0; i<students.size(); i++)
        {
            grades[i] = students.get(i).getGrade();
            points[i] = students.get(i).getPoints();
        }
        Mean mean = new Mean();
        pointStats.add(mean.evaluate(points));
        gradeStats.add(mean.evaluate(grades));
        
        Median md = new Median();
        pointStats.add(md.evaluate(points));
        gradeStats.add(md.evaluate(grades));
        
        StandardDeviation sd = new StandardDeviation();
        pointStats.add(sd.evaluate(points));
        gradeStats.add(sd.evaluate(grades));
    }
    
    public List<Double> getPoints()
    {
        return pointStats;
    }
    
    public List<Double> getGrades()
    {
        return gradeStats;
    }
}
