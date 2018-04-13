package org.classes;

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
}
