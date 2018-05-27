package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import controller.Grader;
import controller.model.Statistic;
import controller.component.Student;
import controller.model.Threshold;
import java.util.ArrayList;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraderResources {
    
    @Autowired
    private UserRepository userRepository;

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
            @RequestParam(value="examMax") double examMax, @RequestParam(value="preset") int preset) throws SQLException, IOException
    {
        
            Grader grader = (Grader) session.getAttribute("Grader");
            if (grader != null)
                grader.updateConfig(gradeMin, gradeMax, gradeInterval, examMin, examMax, preset);
            else
            {
            grader = new Grader(gradeMin, gradeMax, gradeInterval, examMin, examMax, preset);
            session.setAttribute("Grader", grader);
            }
    }
    
    // If the opens site site again with a previous session active, load previous
    // configurations
    @RequestMapping(value="/loadSession")
    public Grader loadSession(HttpServletRequest request, HttpSession session,HttpServletResponse response)
            throws SQLException, IOException
    {
            Grader grader = (Grader) session.getAttribute("Grader");
            if (grader != null)
                return grader;
            else return null;
    }
    
    // Set threshold points with Grader object
    @RequestMapping("/setByPoints")
    public List<Threshold> updateThresholdPoints(HttpSession session, @RequestParam(value="grade") 
            double grade, @RequestParam(value="points") double points) 
    {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
        {
            grader.setByPoints(grade, points);
            return grader.getThresholds();
        }
        else return null;
    }
    
    // Set threshold points by percentage, handled by Grader object
    @RequestMapping("/setByPercentage")
    public List<Threshold> updateThresholdPercentage(HttpSession session, @RequestParam(value="grade") 
            double grade, @RequestParam(value="percentage") double percentage) 
    {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
        {
            grader.setByPercentage(grade, percentage);
            return grader.getThresholds();
        }
        else return null;
    }
    
    // Get student results for current Exam
    @RequestMapping("/getResults")
    public Pair<ArrayList<Student>,Statistic> getResults(HttpSession session) 
    {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
        {
            return grader.getExamResults();
        }
        else return null;
    }
    
    @RequestMapping("/addStudent")
    public void addStudent(HttpSession session, @RequestParam(value="studentId") 
            int studentId, @RequestParam(value="studentName") String studentName)
    {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
            grader.addStudent(studentId, studentName);
    }
    
    @RequestMapping("/removeStudent")
    public void removeStudent(HttpSession session, @RequestParam(value="studentId") 
            int studentId)
    {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
            grader.removeStudent(studentId);
    }
    
    @RequestMapping("/addResult")
    public void addResult(HttpSession session, @RequestParam(value="studentId")
    int studentId, @RequestParam(value="studentResult") double studentResult)
    {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
            grader.addResult(studentId, studentResult);
    }
    
    @RequestMapping("/peerDistribution")
    public void peerDistribution(HttpSession session)
    {
        Grader grader = (Grader) session.getAttribute("Grader");
        if (grader != null)
            grader.peerDistribution();
    }
    
    @RequestMapping("/resetConfig")
    public void resetConfig(HttpSession session)
    {
        session.invalidate();
    }
    
    @GetMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name
                    , @RequestParam String hash) {

            GraderUser n = new GraderUser();
            n.setUsername(name);
            n.setPasswordHash(hash);
            userRepository.save(n);
            return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<GraderUser> getAllUsers() {
            return userRepository.findAll();
    }
}