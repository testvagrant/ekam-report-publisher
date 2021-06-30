package com.testvagrant.ekam.dashboard.converters;

import com.testvagrant.ekam.dashboard.models.TestCase;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Objects;

public class TestCaseConverter {

    public TestCase convert(ITestResult testResult, String status) {
        return TestCase.builder()
                .name(testResult.getName())
                .featureFileName(testResult.getTestClass().getName())
                .status(status)
                .tags(Arrays.asList(testResult.getMethod().getGroups()))
                .stackTrace(getStackTrace(testResult))
                .build();
    }

    private String getStackTrace(ITestResult testResult) {
        if(Objects.isNull(testResult.getThrowable())) return "";
        return ExceptionUtils.getStackTrace(testResult.getThrowable());
    }

}
