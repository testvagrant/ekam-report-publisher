package com.testvagrant.optimus.dashboard;

import com.testvagrant.optimus.dashboard.converters.TestCaseConverter;
import com.testvagrant.optimus.dashboard.io.JsonWriter;
import com.testvagrant.optimus.dashboard.models.OptimusTestNGBuild;
import com.testvagrant.optimus.dashboard.models.TestCase;
import com.testvagrant.optimus.dashboard.models.dashboard.Device;
import org.testng.ITestResult;

import java.time.LocalDateTime;

public class OptimusTestNGBuildGenerator {

    private OptimusTestNGBuild optimusTestNGBuild;
    private TestCaseConverter testCaseConverter;

    public OptimusTestNGBuildGenerator() {
        this.optimusTestNGBuild = OptimusTestNGBuild.builder().build();
        this.testCaseConverter = new TestCaseConverter();
    }

    public OptimusTestNGBuildGenerator startBuild() {
        optimusTestNGBuild.setBuildEndTime(LocalDateTime.now().toString());
        return this;
    }

    public OptimusTestNGBuildGenerator endBuild() {
        optimusTestNGBuild.setBuildEndTime(LocalDateTime.now().toString());
        return this;
    }

    public OptimusTestNGBuildGenerator addTestCase(ITestResult iTestResult, String status) {
        TestCase testCase = testCaseConverter.convert(iTestResult,status);
        optimusTestNGBuild.addTestCase(testCase);
        return this;
    }

    public OptimusTestNGBuild generate() {
        new JsonWriter().write(optimusTestNGBuild, OptimusExecutionTimelinePaths.BUILD_INFO);
        return optimusTestNGBuild;
    }
}
