package com.testvagrant.optimus.dashboard;

import com.testvagrant.optimus.dashboard.models.TestCase;

import java.nio.file.Paths;

public class OptimusExecutionTimelinePaths {

    public static final String BASE_PATH = Paths.get(System.getProperty("user.dir"),
            "build",
            "optimus-execution-timeline").toString();
    public static final String BUILD_INFO = Paths.get(BASE_PATH,
            "build-info.json").toString();

    public static String getStepsPath(TestCase testCase) {
        return Paths.get(BASE_PATH,
                testCase.getFeatureFileName(),
                testCase.getName(),
                "steps.json"
        ).toString();
    }

    public static String getScreenshotsPath(TestCase testCase) {
        return Paths.get(BASE_PATH,
                testCase.getFeatureFileName(),
                testCase.getName(),
                "screenshots").toString();
    }
}
