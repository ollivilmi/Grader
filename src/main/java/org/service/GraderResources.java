package org.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.classes.Grader;
import org.classes.Threshold;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraderResources {

    // Gets the grade thresholds currently in the Grader object
    @RequestMapping("/getThresholds")
    public List<Threshold> getThresholds(HttpSession session) {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
            return grader.getThresholds();
        else return null;
    }

    // Creates a new Grader object in the session
    @RequestMapping(value="/updateSession") 
    public void updateSession(HttpServletRequest request, HttpSession session,HttpServletResponse response,
            @RequestParam(value="gradeMin") double gradeMin, @RequestParam(value="gradeMax") double gradeMax,
            @RequestParam(value="gradeInterval") double gradeInterval, @RequestParam(value="examMin") double examMin, 
            @RequestParam(value="examMax") double examMax) throws SQLException, IOException{
        
            Grader grader = new Grader(gradeMin, gradeMax, gradeInterval, examMin, examMax);
            session.setAttribute("Grader", grader);
    }
    
}