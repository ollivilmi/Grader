package controller.model;

import controller.component.Exam;
import controller.component.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class Statistic {
    private List<Double> pointStats, gradeStats, resultPercentages;
    
    public Statistic (Exam exam)
    {
        TreeMap<Integer, Student> students = exam.getStudents();
        if (students.size() < 1)
            return;
        
        double [] grades = new double[students.size()];
        double [] points = new double[students.size()];
        TreeMap<Double, Double> thresholds = exam.getReverseMap();
        
        int i = 0;
        for (Map.Entry<Integer,Student> entry : students.entrySet())
        {
            Student student = entry.getValue();
            Double p = student.getPoints(), result;
            
            if (p > thresholds.firstKey())
                result = thresholds.floorEntry(p).getValue();
            else
                result = 0.0;
            
            student.setPoints(p);
            student.setGrade(result);
            points[i] = p;
            grades[i] = result;
            i++;
        }
        
        pointStats = new ArrayList<>();
        gradeStats = new ArrayList<>();
        resultPercentages = new ArrayList<>();

        Mean mean = new Mean();
        pointStats.add(mean.evaluate(points));
        gradeStats.add(mean.evaluate(grades));
        
        Median md = new Median();
        pointStats.add(md.evaluate(points));
        gradeStats.add(md.evaluate(grades));
        
        StandardDeviation sd = new StandardDeviation();
        pointStats.add(sd.evaluate(points));
        gradeStats.add(sd.evaluate(grades));
        
        if (pointStats.get(2) != 0)
        {
            NormalDistribution nd = new NormalDistribution(pointStats.get(0), pointStats.get(2));
            int tAmount = exam.getGrade().getAmount();
            double dev = pointStats.get(0) /  tAmount; 
            for (double resP = exam.getMin(), j=0; j<tAmount; resP+=dev, j++)
                resultPercentages.add(1-nd.cumulativeProbability(resP));
        }
    }
    
    public List<Double> getPoints()
    {
        return pointStats;
    }
    
    public List<Double> getGrades()
    {
        return gradeStats;
    }
    
    public List<Double> getResultPercentages()
    {
        return resultPercentages;
    }
}
