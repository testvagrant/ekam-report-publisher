package com.testvagrant.optimus.dashboard.io;

import com.google.gson.reflect.TypeToken;
import com.testvagrant.optimus.dashboard.OptimusExecutionTimelinePaths;
import com.testvagrant.optimus.dashboard.models.Step;
import com.testvagrant.optimus.dashboard.models.TestCase;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StepFinder {

    public static String getSteps(TestCase testCase) {
        List<Step> steps = new ArrayList<>();
        String stepsPath = OptimusExecutionTimelinePaths.getStepsPath(testCase);

        try {
            steps = GsonParser.toInstance().deserialize(stepsPath, new TypeToken<List<Step>>() {}.getType());
            return GsonParser.toInstance().serialize(steps);
        } catch (Exception e) {
            Step step = Step.builder()
                    .name(testCase.getName())
                    .keyword("test")
                    .error_message("")
                    .duration(0L)
                    .status(testCase.getStatus())
                    .build();
            steps.add(step);
            return GsonParser.toInstance().serialize(steps);
        }
    }

}
