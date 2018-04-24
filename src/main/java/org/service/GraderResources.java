package org.service;

import java.util.List;
import org.classes.Grader;
import org.classes.Threshold;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraderResources {

    @RequestMapping("/getThresholds")
    public List<Threshold> greeting(@RequestParam(value="gradeMin") double gradeMin, @RequestParam(value="gradeMax") double gradeMax,
            @RequestParam(value="gradeInterval") double gradeInterval, @RequestParam(value="examMin") double examMin, 
            @RequestParam(value="examMax") double examMax) {
        Grader grader = new Grader(gradeMin, gradeMax, gradeInterval, examMin, examMax);
        return grader.getThresholds();
    }
}