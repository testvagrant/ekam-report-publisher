package com.testvagrant.optimus.dashboard.converters;

import com.testvagrant.optimus.dashboard.models.Step;
import com.testvagrant.optimus.dashboard.models.TestCase;
import com.testvagrant.optimus.dashboard.models.dashboard.Device;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCaseConverter {

  public TestCase convert(ITestResult testResult, String status) {
    return TestCase.builder()
        .name(testResult.getName())
        .featureFileName(testResult.getTestClass().getName())
        .status(status)
        .tags(Arrays.asList(testResult.getMethod().getGroups()))
        .steps(getSteps(status, testResult))
        .build();
  }

  public List<Step> getSteps(String status, ITestResult iTestResult) {
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
    return steps;
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
}
