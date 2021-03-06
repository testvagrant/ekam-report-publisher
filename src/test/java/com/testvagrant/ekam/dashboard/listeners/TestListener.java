package com.testvagrant.ekam.dashboard.listeners;

import com.testvagrant.ekam.dashboard.EkamTestNGBuildGenerator;
import com.testvagrant.ekam.dashboard.models.dashboard.Device;
import org.testng.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestListener implements ITestListener, ISuiteListener {
  private EkamTestNGBuildGenerator ekamTestNGBuildGenerator;


  @Override
  public void onTestStart(ITestResult result) {
    result.setAttribute("deviceDetails", getDevice());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    ekamTestNGBuildGenerator.addTestCase(result, "passed");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ekamTestNGBuildGenerator.addTestCase(result, "failed");
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    ekamTestNGBuildGenerator.addTestCase(result, "skipped");
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    ekamTestNGBuildGenerator.addTestCase(result,"passed");
  }

  @Override
  public void onStart(ITestContext context) {}

  @Override
  public void onFinish(ITestContext context) {}

  @Override
  public void onStart(ISuite suite) {
    ekamTestNGBuildGenerator = new EkamTestNGBuildGenerator();
    ekamTestNGBuildGenerator.startBuild();
  }

  @Override
  public void onFinish(ISuite suite) {
    ekamTestNGBuildGenerator.endBuild();
    ekamTestNGBuildGenerator.generate();
  }

  private List<Device> getDevices() {
    return Arrays.asList(
        Device.builder().deviceName("Samsung J12").platformVersion("9").udid("123456").build(),
        Device.builder().deviceName("Oppo x12").platformVersion("12").udid("xu7tejg3").build(),
        Device.builder().deviceName("One Plus 8").platformVersion("7.1").udid("xu7tejg3").build(),
        Device.builder()
            .deviceName("iPhone X Max")
            .platform("iOS")
            .platformVersion("13.4")
            .udid("fhEU193NI310")
            .build(),
        Device.builder().deviceName("Samsung J12").platformVersion("9").udid("123456").build(),
        Device.builder().deviceName("Oppo x12").platformVersion("12").udid("xu7tejg3").build(),
        Device.builder().deviceName("One Plus 8").platform("7.1").udid("xu7tejg3").build(),
        Device.builder()
            .deviceName("iPhone X Max Pro")
            .platform("iOS")
            .platformVersion("13.4")
            .udid("fhEU193NI310")
            .build(),
        Device.builder().deviceName("Samsung J12").platformVersion("9").udid("123456").build(),
        Device.builder().deviceName("Oppo x12").platformVersion("12").udid("xu7tejg3").build(),
        Device.builder().deviceName("One Plus 8").platformVersion("7.1").udid("xu7tejg3").build(),
        Device.builder()
            .deviceName("iPhone X Max")
            .platformVersion("13.4")
            .udid("fhEU193NI310")
            .build(),
        Device.builder().deviceName("Samsung J12").platformVersion("9").udid("123456").build(),
        Device.builder().deviceName("Oppo x12").platformVersion("12").udid("xu7tejg3").build(),
        Device.builder().deviceName("One Plus 8").platformVersion("7.1").udid("xu7tejg3").build(),
        Device.builder()
            .deviceName("iPhone X Max")
            .platformVersion("13.4")
            .udid("fhEU193NI310")
            .build());
  }

  private Device getDevice() {
    Random rand = new Random();
    Device randomDevice =
        getDevices().get(ThreadLocalRandom.current().nextInt(getDevices().size()));
    System.out.println(randomDevice);
    return randomDevice;
  }
}
