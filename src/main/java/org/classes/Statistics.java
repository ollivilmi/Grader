package org.classes;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class Statistics {
    private List<Pair<String, Double>> pointStats, gradeStats;
    
    public Statistics (List<Student> students)
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
        pointStats.add(new Pair("mean", mean.evaluate(points)));
        gradeStats.add(new Pair("mean", mean.evaluate(grades)));
        
        Median md = new Median();
        pointStats.add(new Pair("median", md.evaluate(points)));
        gradeStats.add(new Pair("median", md.evaluate(grades)));
        
        StandardDeviation sd = new StandardDeviation();
        pointStats.add(new Pair("deviation", sd.evaluate(points)));
        gradeStats.add(new Pair("deviation", sd.evaluate(grades)));
    }
    
    public List<Pair<String,Double>> getPoints()
    {
        return pointStats;
    }
    
    public List<Pair<String,Double>> getGrades()
    {
        return gradeStats;
    }
}
