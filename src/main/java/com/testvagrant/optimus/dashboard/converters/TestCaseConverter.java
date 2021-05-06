package com.testvagrant.optimus.dashboard.converters;

import com.testvagrant.optimus.dashboard.models.TestCase;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestCaseConverter {

    public TestCase convert(ITestResult testResult, String status) {
        return TestCase.builder()
                .name(testResult.getName())
                .featureFileName(testResult.getTestClass().getName())
                .status(status)
                .tags(Arrays.asList(testResult.getMethod().getGroups()))
                .stackTrace(ExceptionUtils.getStackTrace(testResult.getThrowable()))
                .build();
    }

}
