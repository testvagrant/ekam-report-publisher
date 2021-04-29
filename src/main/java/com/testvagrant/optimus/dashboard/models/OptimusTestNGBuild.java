package com.testvagrant.optimus.dashboard.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.testng.ITestResult;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Builder
public class OptimusTestNGBuild {
    @Builder.Default
    private String buildStartTime = LocalDateTime.now().toString();

    @Builder.Default
    private String buildEndTime = LocalDateTime.now().toString();

    @Builder.Default
    private List<TestCase> testCases = new ArrayList<>();


    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }
}
