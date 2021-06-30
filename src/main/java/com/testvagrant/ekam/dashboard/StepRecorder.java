package com.testvagrant.ekam.dashboard;

import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.dashboard.models.Step;

import java.io.FileWriter;
import java.io.IOException;
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
        String stepsPath = EkamExecutionTimelinePaths.getStepsPath(featureName, testName);
        String serialize = new GsonParser().serialize(steps);
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
