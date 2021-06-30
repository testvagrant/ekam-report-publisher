package com.testvagrant.ekam.dashboard;

import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.commons.path.PathBuilder;
import com.testvagrant.ekam.dashboard.models.TestCase;

public class EkamExecutionTimelinePaths {

    public static final String BASE_PATH = ResourcePaths.EKAM_EXECUTION_TIMELINE;
    public static final String BUILD_INFO = new PathBuilder(BASE_PATH).append(
            "build-info.json").toString();

    public static String getStepsPath(TestCase testCase) {
        return new PathBuilder(ResourcePaths.getTestPath(testCase.getFeatureFileName(), testCase.getName()))
                .append("steps.json").toString();
    }

    public static String getStepsPath(String featureName, String testName) {
        return new PathBuilder(ResourcePaths.getTestPath(featureName, testName))
                .append("steps.json").toString();
    }

    public static String getScreenshotsPath(TestCase testCase) {
        return new PathBuilder(ResourcePaths.getTestPath(testCase.getFeatureFileName(), testCase.getName()))
                .append("screenshots").toString();
    }

    public static String getTargetsPath(TestCase testCase) {
        return new PathBuilder(ResourcePaths.getTestPath(testCase.getFeatureFileName(), testCase.getName()))
                .append("target.json").toString();
    }
}
