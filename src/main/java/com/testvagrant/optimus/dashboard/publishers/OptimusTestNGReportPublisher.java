package com.testvagrant.optimus.dashboard.publishers;

import com.testvagrant.optimus.dashboard.clients.BuildsClient;
import com.testvagrant.optimus.dashboard.clients.DevicesClient;
import com.testvagrant.optimus.dashboard.clients.ScenariosClient;
import com.testvagrant.optimus.dashboard.clients.ScreenshotsClient;
import com.testvagrant.optimus.dashboard.io.ScreenshotLoader;
import com.testvagrant.optimus.dashboard.models.DistinctScenarios;
import com.testvagrant.optimus.dashboard.models.ScenarioTimeline;
import com.testvagrant.optimus.dashboard.models.builders.ScenarioBuilder;
import com.testvagrant.optimus.dashboard.models.dashboard.*;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class OptimusTestNGReportPublisher implements OptimusDashboardPublisher {

  private final List<ISuite> suites;
  private final Build build;
  private final BuildsClient buildsClient;
  private final DevicesClient devicesClient;
  private final ScenariosClient scenariosClient;
  private final ScreenshotsClient screenshotsClient;

  public OptimusTestNGReportPublisher(List<ISuite> suites) {
    this("http://localhost:8090", suites);
  }

  public OptimusTestNGReportPublisher(String dashboardServer, List<ISuite> suites) {
    this(dashboardServer, suites, new BuildOptions());
  }

  private OptimusTestNGReportPublisher(
      String dashboardServer, List<ISuite> suites, BuildOptions buildOptions) {
    this.suites = suites;

    buildsClient = new BuildsClient(dashboardServer);
    build = buildsClient.startBuild();
    devicesClient = new DevicesClient(dashboardServer);
    scenariosClient = new ScenariosClient(dashboardServer);
    screenshotsClient = new ScreenshotsClient(dashboardServer);
  }

  @Override
  public void publish() {
    List<Scenario> scenariosToPublish = new CopyOnWriteArrayList<>();
    AtomicReference<String> buildStartTime = new AtomicReference<>();
    AtomicReference<String> buildEndTime = new AtomicReference<>();
    suites.parallelStream()
        .forEach(
            iSuite -> {
              buildStartTime.set((String) iSuite.getAttribute("buildStartTime"));
              buildEndTime.set((String) iSuite.getAttribute("buildEndTime"));
              iSuite
                  .getResults()
                  .forEach(
                      (key, value) -> {
                        ITestContext testContext = value.getTestContext();
                        // Passed
                        testContext
                            .getPassedTests()
                            .getAllResults()
                            .forEach(
                                iTestResult -> {
                                  Device deviceDetails =
                                      (Device) iTestResult.getAttribute("deviceDetails");
                                  Device device =
                                      devicesClient.insertDevice(build.getId(), deviceDetails);
                                  Scenario scenario =
                                      new ScenarioBuilder(iTestResult)
                                          .withStatus("passed")
                                          .withBuildId(this.build.getId())
                                          .withScenarioTimeline(getScenarioTimeline(iTestResult))
                                          .withDeviceId(device.getId())
                                          .build();
                                  scenariosToPublish.add(scenario);
                                });

                        // Failed
                        testContext
                            .getFailedTests()
                            .getAllResults()
                            .forEach(
                                iTestResult -> {
                                  Device deviceDetails =
                                      (Device) iTestResult.getAttribute("deviceDetails");
                                  Device device =
                                      devicesClient.insertDevice(build.getId(), deviceDetails);
                                  Scenario scenario =
                                      new ScenarioBuilder(iTestResult)
                                          .withStatus("failed")
                                          .withBuildId(this.build.getId())
                                          .withScenarioTimeline(getScenarioTimeline(iTestResult))
                                          .withDeviceId(device.getId())
                                          .withFailedOnScreen(
                                              new ScreenshotLoader()
                                                  .getLastScreen(
                                                      iTestResult.getTestClass().getName(),
                                                      iTestResult.getName()))
                                          .build();
                                  scenariosToPublish.add(scenario);
                                });

                        // Skipped
                        testContext
                            .getSkippedTests()
                            .getAllResults()
                            .forEach(
                                iTestResult -> {
                                  Device deviceDetails =
                                      (Device)
                                          iTestResult
                                              .getTestContext()
                                              .getAttribute("deviceDetails");
                                  if (deviceDetails == null) {
                                    deviceDetails = Device.builder().buildId(build.getId()).build();
                                  }
                                  Device device =
                                      devicesClient.insertDevice(build.getId(), deviceDetails);
                                  Scenario scenario =
                                      new ScenarioBuilder(iTestResult)
                                          .withStatus("skipped")
                                          .withBuildId(this.build.getId())
                                          .withDeviceId(device.getId())
                                          .build();
                                  scenariosToPublish.add(scenario);
                                });
                      });
            });
    scenariosClient.insertScenarios(scenariosToPublish);
    DistinctScenarios distinctScenariosCount = scenariosClient.getDistinctScenariosCount();
    build.setBuildScenarios(scenariosToPublish.size());
    build.setBuildStartTime(buildStartTime.get());
    build.setBuildEndTime(buildEndTime.get());
    build.setScenarioSuccessRate(distinctScenariosCount.getPassPercentage());
    build.setScenariosCount(distinctScenariosCount.getDistinctScenariosCount());
    buildsClient.stopBuild(build);
  }

  public List<ScenarioTimeline> getScenarioTimeline(ITestResult testResult) {
    List<ScenarioTimeline> scenarioTimelines = new ArrayList<>();
    List<Screenshot> screenshots =
        new ScreenshotLoader()
            .loadScreenshots(testResult.getTestClass().getName(), testResult.getName());
    screenshots.forEach(
        screenshot -> {
          String scenarioFileName = screenshotsClient.storeScreenshot(screenshot);
          if (!scenarioFileName.contains("last_screen")) {
            ScenarioTimeline scenarioTimeline =
                ScenarioTimeline.builder()
                    .interval(Integer.parseInt(scenarioFileName.replace(".png", "").trim()))
                    .screenshotFileName(scenarioFileName)
                    .activity("")
                    .build();
            scenarioTimelines.add(scenarioTimeline);
          }
        });
    scenarioTimelines.sort(Comparator.comparingInt(ScenarioTimeline::getInterval));
    return scenarioTimelines;
  }
}
