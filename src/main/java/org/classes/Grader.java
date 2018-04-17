package org.classes;

import java.util.Iterator;
import java.util.TreeSet;

public class Grader {
    private Grade grade;
    private Exam exam;
    
    public Grader(double minGrade, double maxGrade, double intervalGrade, double minExam, double maxExam)
    {
        this.grade = new Grade(minGrade, maxGrade, intervalGrade);
        this.exam = new Exam(minExam, maxExam, grade);
    }
    
    public TreeSet<Threshold> getExam() {
        return exam.getThresholds();
    }
    
    /***
     * Sets the target grade to % of points received from the test.
     * (70% of points to get 3 for example)
     * 
     * Checks higher & lower value in TreeSet to main order
     * 
     * @param grade
     * @param percentage 
     */
    public void setByPercentage(double grade, double percentage)
    {
        Threshold toChange = new Threshold(exam.getPoints()*percentage, grade);
        Iterator<Threshold> i = exam.getThresholds().iterator();  

        while (i.hasNext())
        {
            Threshold t = i.next();
            if (t.compareTo(toChange) == 0)
            {
                if (exam.getThresholds().higher(t).getPercentage() < percentage
                && exam.getThresholds().lower(t).getPercentage() > percentage)
                {
                    t.setPercentage(percentage);
                    t.setPoints(exam.getMax()*percentage);
                }
            break;
            }
        }
    }
    
    public static void main(String[] args) {
        Grader grader = new Grader(1, 5, 0.5, 10, 30);
        System.out.println(grader.getExam());
        grader.setByPercentage(3, 0.9);
        System.out.println(grader.getExam());
        grader.setByPercentage(3, 0.66);
        System.out.println(grader.getExam());
    }
}
