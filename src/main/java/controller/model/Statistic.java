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
    
    /***
     * Analyzes results from the Exam object.
     * Points & Grades
     * - Mean, Median, Deviation
     * 
     * Then uses normal distribution to assess results
     * and suggests a distribution where:
     * - The worst grade has a probability of at least 10%
     * - The best grade has a probability of roughly ~10%
     * - Mean has a probability of ~ 50%
     * 
     * @param exam 
     */
    public Statistic (Exam exam)
    {
        TreeMap<Integer, Student> students = exam.getStudents();
        if (students.size() < 1)
            return;
        
        // Saves point & Grade results for analysis
        double [] allGrades = new double[students.size()];
        double [] allPoints = new double[students.size()];
        
        // Reverse map to access Thresholds by Point -> Grade
        // - Checks & sets the grade for a student
        TreeMap<Double, Double> examThresholds = exam.getReverseMap();
        
        // numberOfStudents = saves value to array (allGrades, allPoints)
        // mostPoints = best result of points in exam
        // leastPoints = worst result of points in exam
        int numberOfStudents = 0; double mostPoints = 0, leastPoints = 0;
        
        for (Map.Entry<Integer,Student> entry : students.entrySet())
        {
            Student student = entry.getValue();
            Double pointsFromExam = student.getPoints(), gradeFromExam;
            
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
             
            allPoints[numberOfStudents] = pointsFromExam;
            allGrades[numberOfStudents] = gradeFromExam;
            numberOfStudents++;
        }
        
        pointStats = new ArrayList<>();
        gradeStats = new ArrayList<>();
        suggestedPoints = new ArrayList<>();

        // Apache Math Library to assess results
        // http://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/index.html
        
        Mean mean = new Mean();
        pointStats.add(mean.evaluate(allPoints));
        gradeStats.add(mean.evaluate(allGrades));
        
        Median md = new Median();
        pointStats.add(md.evaluate(allPoints));
        gradeStats.add(md.evaluate(allGrades));
        
        StandardDeviation sd = new StandardDeviation();
        pointStats.add(sd.evaluate(allPoints));
        gradeStats.add(sd.evaluate(allGrades));
        
        // If the deviation of results is not 0 (all the results are not the same)
        // Use normal distribution to suggest grade redistribution
        
        if (pointStats.get(2) != 0)
        {
            // Normal Distribution uses Mean & Deviation from the student points
            NormalDistribution nd = new NormalDistribution(pointStats.get(0), pointStats.get(2));
            
            // Assess the probability for each possible Threshold in the exam,
            // considering the results.
            
            // Probability -> Points
            TreeMap<Double,Double> pointsAndProbability = new TreeMap<>();
            
            for (double points = exam.getMin(); points<exam.getMax(); points += 0.5)
                pointsAndProbability.put(nd.cumulativeProbability(points), points);
            
            // To redistribute the thresholds, we need to know the amount of thresholds.
            int amountOfThresholds = exam.getGrade().getAmount();
            // It is used to split the interval of probabilities.
            // - 0.1 cumulative probability up to -> 0.9 cumulative probability
            double probability = 0.1, interval = 0.8 / amountOfThresholds;
            suggestedThresholds = new TreeMap<>();
                        
            for (Double grade : exam.getGrade().getDistribution())
            {
                // For each iteration, we check which points are closest to the probability
                // - 0.1, 0,2, ..., 0.9 (for example)
                suggestedThresholds.put(grade,pointsAndProbability.ceilingEntry(probability).getValue());
                probability += interval;
            }   
            
            suggestedPoints.addAll(suggestedThresholds.values());
        }
    }
    
    /***
     * List which contains Mean, Median, Deviation
     * @return 
     */
    public List<Double> getPoints()
    {
        return pointStats;
    }
    
    /***
     * List which contains Mean, Median, Deviation
     * @return 
     */
    public List<Double> getGrades()
    {
        return gradeStats;
    }
    
    /***
     * TreeMap of Thresholds, may be used to change Thresholds
     * @return 
     */
    public TreeMap<Double,Double> getSuggestedThresholds()
    {
        return suggestedThresholds;
    }
    
    /***
     * List which contains the suggested point distribution in order
     * @return 
     */
    public List<Double> getSuggestedPoints()
    {
        return suggestedPoints;
    }
    
}
