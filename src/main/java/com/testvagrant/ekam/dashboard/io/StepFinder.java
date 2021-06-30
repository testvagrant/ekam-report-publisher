package com.testvagrant.ekam.dashboard.io;

import com.google.gson.reflect.TypeToken;
import com.testvagrant.ekam.commons.io.GsonParser;
import com.testvagrant.ekam.dashboard.EkamExecutionTimelinePaths;
import com.testvagrant.ekam.dashboard.models.Step;
import com.testvagrant.ekam.dashboard.models.TestCase;

import java.util.ArrayList;
import java.util.List;

public class StepFinder {

    public static String getSteps(TestCase testCase) {
        List<Step> steps = new ArrayList<>();
        String stepsPath = EkamExecutionTimelinePaths.getStepsPath(testCase);
        GsonParser gsonParser = new GsonParser();
        try {
            steps = gsonParser.deserialize(stepsPath, new TypeToken<List<Step>>() {}.getType());
            addFailedStep(testCase, steps);
            return gsonParser.serialize(steps);
        } catch (Exception e) {
            Step step = Step.builder()
                    .name(testCase.getName())
                    .keyword("test")
                    .error_message("")
                    .duration(0L)
                    .status(testCase.getStatus())
                    .build();
            steps.add(step);
            addFailedStep(testCase, steps);
            return gsonParser.serialize(steps);
        }
    }


    private static List<Step> addFailedStep(TestCase testCase, List<Step> steps) {
        if(testCase.getStatus().equalsIgnoreCase("failed")
                &&
                steps.stream().noneMatch(step -> step.getStatus().equalsIgnoreCase("failed"))
        ) {
            Step step = Step.builder().name("Failed Step")
                    .status("failed")
                    .error_message(testCase.getStackTrace())
                    .duration(0L)
                    .keyword("Failure")
                    .build();
            steps.add(step);
        }
        return steps;
    }

}
