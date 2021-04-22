package com.testvagrant.optimus.dashboard.models.builders;

import com.google.gson.Gson;
import com.testvagrant.optimus.dashboard.models.ScenarioTimeline;
import com.testvagrant.optimus.dashboard.models.Step;
import com.testvagrant.optimus.dashboard.models.dashboard.Scenario;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScenarioBuilder {
  private final Scenario scenario;
  private ITestResult iTestResult;

  public ScenarioBuilder() {
    scenario = new Scenario();
  }

  public ScenarioBuilder(ITestResult iTestResult) {
    this.iTestResult = iTestResult;
    scenario = new Scenario();
    scenario.setScenarioName(iTestResult.getName());
    scenario.setFeatureName(iTestResult.getTestClass().getName());
    scenario.setFeatureFileName(iTestResult.getTestClass().getTestName());
    scenario.setTags(Arrays.asList(iTestResult.getMethod().getGroups()));
    scenario.setTimeTaken((int) iTestResult.getEndMillis());
    scenario.setEndTime(LocalDateTime.now().toString());
  }

  public ScenarioBuilder withBuildId(String buildId) {
    scenario.setBuildId(buildId);
    return this;
  }

  public ScenarioBuilder withDeviceId(String deviceId) {
    scenario.setDeviceId(deviceId);
    return this;
  }

  public ScenarioBuilder withStatus(String status) {
    scenario.setStatus(status);
    scenario.setSteps(getSteps(status, iTestResult));
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

  public String getSteps(String status, ITestResult iTestResult) {
    List<Step> steps = new ArrayList<>();
    Step description =
        Step.builder()
            .duration(iTestResult.getEndMillis() - iTestResult.getStartMillis())
            .error_message(getMessage(iTestResult))
            .status(status)
            .name(getDescription(iTestResult))
            .keyword("description")
            .build();
    steps.add(description);
    return new Gson().toJson(steps);
  }

  private String getMessage(ITestResult iTestResult) {
    Throwable throwable = iTestResult.getThrowable();
    String message = throwable == null ? "" : throwable.getMessage();
    return message;
  }

  private String getDescription(ITestResult iTestResult) {
    String description = iTestResult.getMethod().getDescription();
    if (description == null || description.isEmpty()) {
      return iTestResult.getName();
    }
    return description;
  }

  public Scenario build() {
    return scenario;
  }
}
