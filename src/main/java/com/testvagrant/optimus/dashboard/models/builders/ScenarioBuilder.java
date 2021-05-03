package com.testvagrant.optimus.dashboard.models.builders;

import com.google.gson.Gson;
import com.testvagrant.optimus.dashboard.io.ScreenshotLoader;
import com.testvagrant.optimus.dashboard.models.ScenarioTimeline;
import com.testvagrant.optimus.dashboard.models.Step;
import com.testvagrant.optimus.dashboard.models.TestCase;
import com.testvagrant.optimus.dashboard.models.dashboard.Scenario;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScenarioBuilder {
    private Scenario scenario;
    private ITestResult iTestResult;

    public ScenarioBuilder() {
        scenario = new Scenario();
    }

    public ScenarioBuilder(TestCase testCase) {
        this.iTestResult = iTestResult;
        scenario = new Scenario();
        scenario.setScenarioName(testCase.getName());
        scenario.setFeatureName(testCase.getFeatureFileName());
        scenario.setFeatureFileName(testCase.getFeatureFileName());
        scenario.setTags(testCase.getTags());
        scenario.setTimeTaken(testCase.getTimeTaken());
        scenario.setEndTime(LocalDateTime.now().toString());
        scenario.setSteps(new Gson().toJson(testCase.getSteps()));
        scenario.setStatus(testCase.getStatus());
    }

    public ScenarioBuilder withBuildId(String buildId) {
        scenario.setBuildId(buildId);
        return this;
    }

    public ScenarioBuilder withDeviceId(String deviceId) {
        scenario.setDeviceId(deviceId);
        return this;
    }

    public ScenarioBuilder withSteps(String steps) {
        scenario.setSteps(steps);
        return this;
    }

    public ScenarioBuilder withScenarioTimeline(List<ScenarioTimeline> scenarioTimelines) {
        scenario.setScenarioTimeline(new Gson().toJson(scenarioTimelines));
        return this;
    }

    public ScenarioBuilder withFailedOnScreen(byte[] failedOnScreen) {
        scenario.setFailedOnScreen(failedOnScreen);
        return this;
    }

    public Scenario build() {
        return scenario;
    }
}
