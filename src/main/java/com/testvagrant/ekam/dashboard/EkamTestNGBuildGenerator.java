package com.testvagrant.ekam.dashboard;

import com.testvagrant.ekam.dashboard.converters.TestCaseConverter;
import com.testvagrant.ekam.dashboard.io.JsonWriter;
import com.testvagrant.ekam.dashboard.models.EkamTestNGBuild;
import com.testvagrant.ekam.dashboard.models.TestCase;
import org.testng.ITestResult;

import java.time.LocalDateTime;

public class EkamTestNGBuildGenerator {

    private EkamTestNGBuild ekamTestNGBuild;
    private TestCaseConverter testCaseConverter;

    public EkamTestNGBuildGenerator() {
        this.ekamTestNGBuild = EkamTestNGBuild.builder().build();
        this.testCaseConverter = new TestCaseConverter();
    }

    public EkamTestNGBuildGenerator startBuild() {
        ekamTestNGBuild.setBuildEndTime(LocalDateTime.now().toString());
        return this;
    }

    public EkamTestNGBuildGenerator endBuild() {
        ekamTestNGBuild.setBuildEndTime(LocalDateTime.now().toString());
        return this;
    }

    public EkamTestNGBuildGenerator addTestCase(ITestResult iTestResult, String status) {
        TestCase testCase = testCaseConverter.convert(iTestResult,status);
        ekamTestNGBuild.addTestCase(testCase);
        return this;
    }

    public EkamTestNGBuild generate() {
        new JsonWriter().write(ekamTestNGBuild, EkamExecutionTimelinePaths.BUILD_INFO);
        return ekamTestNGBuild;
    }
}
