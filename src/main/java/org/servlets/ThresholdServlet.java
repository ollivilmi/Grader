package org.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.classes.Grader;
import org.classes.Threshold;

@WebServlet(name = "Thresholds", urlPatterns = {"thresholds"}, loadOnStartup = 1) 
public class ThresholdServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.getWriter().print("Hello, World!");  
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        double minGrade = Double.parseDouble(request.getParameter("minGrade"));
        double maxGrade = Double.parseDouble(request.getParameter("maxGrade"));
        double intervalGrade = Double.parseDouble(request.getParameter("intervalGrade"));
        
        double minExam = Double.parseDouble(request.getParameter("minExam"));
        double maxExam = Double.parseDouble(request.getParameter("maxExam"));
        
        Grader grader = new Grader(minGrade, maxGrade, intervalGrade, minExam, maxExam);
        StringBuilder sb = new StringBuilder();
        
        for (Threshold t : grader.getExam())
        {
            sb.append("<br>Points: ");
            sb.append(t.getPoints());
            sb.append(" Grade: ");
            sb.append(t.getGrade());
        }
        
        request.setAttribute("thresholds", sb.toString());     
        request.getRequestDispatcher("response.jsp").forward(request, response); 
    }
}