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
    private List<Double> pointStats, gradeStats, suggestedPoints;
    private TreeMap<Double,Double> suggestedThresholds;
    
    public Statistic (Exam exam)
    {
        TreeMap<Integer, Student> students = exam.getStudents();
        if (students.size() < 1)
            return;
        
        double [] allGrades = new double[students.size()];
        double [] allPoints = new double[students.size()];
        TreeMap<Double, Double> examThresholds = exam.getReverseMap();
        
        int i = 0; double mostPoints = 0, leastPoints = 0;
        for (Map.Entry<Integer,Student> entry : students.entrySet())
        {
            Student student = entry.getValue();
            Double pointsFromExam = student.getPoints(), gradeFromExam;
            
            // Get grade from Exam thresholds
            if (pointsFromExam > examThresholds.firstKey())
                gradeFromExam = examThresholds.floorEntry(pointsFromExam).getValue();
            else
                gradeFromExam = 0.0;
            
            if (pointsFromExam > mostPoints)
                mostPoints = pointsFromExam;
            if (pointsFromExam < leastPoints)
                leastPoints = pointsFromExam;
            
            student.setPoints(pointsFromExam);
            student.setGrade(gradeFromExam);
             
            allPoints[i] = pointsFromExam;
            allGrades[i] = gradeFromExam;
            i++;
        }
        
        pointStats = new ArrayList<>();
        gradeStats = new ArrayList<>();
        suggestedPoints = new ArrayList<>();

        Mean mean = new Mean();
        pointStats.add(mean.evaluate(allPoints));
        gradeStats.add(mean.evaluate(allGrades));
        
        Median md = new Median();
        pointStats.add(md.evaluate(allPoints));
        gradeStats.add(md.evaluate(allGrades));
        
        StandardDeviation sd = new StandardDeviation();
        pointStats.add(sd.evaluate(allPoints));
        gradeStats.add(sd.evaluate(allGrades));
        
        if (pointStats.get(2) != 0) // If deviation != 0
        {
            NormalDistribution nd = new NormalDistribution(pointStats.get(0), pointStats.get(2));
            int amountOfThresholds = exam.getGrade().getAmount();
            
            TreeMap<Double,Double> pointsAndProbability = new TreeMap<>();
            
            for (double points = exam.getMin(); points<exam.getMax(); points += 0.5)
                pointsAndProbability.put(nd.cumulativeProbability(points), points);
            
            double probability = 0.1, interval = 0.8 / amountOfThresholds; i = 0;
            
            suggestedThresholds = new TreeMap<>();
            for (Double grade : exam.getGrade().getDistribution())
            {
                suggestedThresholds.put(grade,pointsAndProbability.ceilingEntry(probability).getValue());
                probability += interval;
            }   
            
            suggestedPoints.addAll(suggestedThresholds.values());
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
    
    public TreeMap<Double,Double> getSuggestedThresholds()
    {
        return suggestedThresholds;
    }
    
    public List<Double> getSuggestedPoints()
    {
        return suggestedPoints;
    }
    
}
