package com.testvagrant.optimus.dashboard.publishers;

import com.testvagrant.optimus.dashboard.OptimusExecutionTimelinePaths;
import com.testvagrant.optimus.dashboard.clients.BuildsClient;
import com.testvagrant.optimus.dashboard.clients.DevicesClient;
import com.testvagrant.optimus.dashboard.clients.ScenariosClient;
import com.testvagrant.optimus.dashboard.clients.ScreenshotsClient;
import com.testvagrant.optimus.dashboard.io.GsonParser;
import com.testvagrant.optimus.dashboard.io.ScreenshotLoader;
import com.testvagrant.optimus.dashboard.io.StepFinder;
import com.testvagrant.optimus.dashboard.io.TargetFinder;
import com.testvagrant.optimus.dashboard.models.DistinctScenarios;
import com.testvagrant.optimus.dashboard.models.OptimusTestNGBuild;
import com.testvagrant.optimus.dashboard.models.ScenarioTimeline;
import com.testvagrant.optimus.dashboard.models.builders.ScenarioBuilder;
import com.testvagrant.optimus.dashboard.models.dashboard.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class OptimusReportPublisher implements OptimusDashboardPublisher {

  private String dashboardServer;
  private BuildOptions buildOptions;
  private Build build;
  private BuildsClient buildsClient;
  private DevicesClient devicesClient;
  private ScenariosClient scenariosClient;
  private ScreenshotsClient screenshotsClient;
  private OptimusTestNGBuild optimusTestNGBuild;

  public OptimusReportPublisher() {
    this("http://localhost:8090");
  }

  public OptimusReportPublisher(String dashboardServer) {
    this(dashboardServer, new BuildOptions());
  }

  private OptimusReportPublisher(String dashboardServer, BuildOptions buildOptions) {
    this.dashboardServer = dashboardServer;
    this.buildOptions = buildOptions; // TODO: For futute use to support additional build options

    buildsClient = new BuildsClient(dashboardServer);
    build = buildsClient.startBuild();
    devicesClient = new DevicesClient(dashboardServer);
    scenariosClient = new ScenariosClient(dashboardServer);
    screenshotsClient = new ScreenshotsClient(dashboardServer);
    optimusTestNGBuild = OptimusTestNGBuild.builder().build();
    optimusTestNGBuild =
        GsonParser.toInstance()
            .deserialize(OptimusExecutionTimelinePaths.BUILD_INFO, OptimusTestNGBuild.class);
  }

  @Override
  public void publish() {
    List<Scenario> scenariosToPublish = new CopyOnWriteArrayList<>();
    optimusTestNGBuild.getTestCases().stream()
        .forEach(
            testCase -> {
              ScreenshotLoader screenshotLoader = new ScreenshotLoader();
              Device deviceDetails = TargetFinder.findTargetDevice(testCase);
              Device device = devicesClient.insertDevice(build.getId(), deviceDetails);
              Scenario scenario =
                  new ScenarioBuilder(testCase)
                      .withBuildId(this.build.getId())
                      .withScenarioTimeline(
                          getScenarioTimeline(
                              screenshotLoader.loadScreenshots(
                                  testCase)))
                      .withDeviceId(device.getId())
                      .withSteps(StepFinder.getSteps(testCase))
                      .withFailedOnScreen(screenshotLoader.getFailedOnScreen())
                      .build();
              scenariosToPublish.add(scenario);
            });
    scenariosClient.insertScenarios(scenariosToPublish);
    DistinctScenarios distinctScenariosCount = scenariosClient.getDistinctScenariosCount();
    build.setBuildScenarios(scenariosToPublish.size());
    build.setBuildStartTime(optimusTestNGBuild.getBuildStartTime());
    build.setBuildEndTime(optimusTestNGBuild.getBuildEndTime());
    build.setScenarioSuccessRate(distinctScenariosCount.getPassPercentage());
    build.setScenariosCount(distinctScenariosCount.getDistinctScenariosCount());
    buildsClient.stopBuild(build);
  }

  public List<ScenarioTimeline> getScenarioTimeline(List<Screenshot> screenshots) {
    List<ScenarioTimeline> scenarioTimelines = new ArrayList<>();
    for (int interval = 0; interval < screenshots.size(); interval++) {
      Screenshot screenshot = screenshots.get(interval);
      screenshot.setFileName(UUID.randomUUID().toString() + ".png");
      String scenarioFileName = screenshotsClient.storeScreenshot(screenshot);
      ScenarioTimeline scenarioTimeline =
          ScenarioTimeline.builder()
              .interval(interval + 1)
              .screenshotFileName(scenarioFileName)
              .activity("")
              .build();
      scenarioTimelines.add(scenarioTimeline);
    }
    scenarioTimelines.sort(Comparator.comparingInt(ScenarioTimeline::getInterval));
    return scenarioTimelines;
  }
}
