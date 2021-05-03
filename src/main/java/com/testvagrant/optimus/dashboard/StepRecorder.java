package com.testvagrant.optimus.dashboard;

import com.testvagrant.optimus.dashboard.io.GsonParser;
import com.testvagrant.optimus.dashboard.models.Step;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StepRecorder {

    private List<Step> steps;
    private String featureName;
    private String testName;

    public StepRecorder(String featureName, String testName) {
        this.featureName = featureName;
        this.testName = testName;
        steps = new ArrayList<>();
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void generateSteps() {
        String stepsPath = Paths.get(System.getProperty("user.dir"),
                "build",
                "optimus-execution-timeline",
                featureName,
                testName,
                "steps.json").toString();
        String serialize = GsonParser.toInstance().serialize(steps);
        FileWriter file = null;
        try {
            file = new FileWriter(stepsPath);
            file.write(serialize);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
