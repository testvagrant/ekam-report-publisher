package com.testvagrant.ekam.dashboard.publishers;

import com.testvagrant.ekam.dashboard.EkamExecutionTimelinePaths;
import com.testvagrant.ekam.dashboard.clients.ScenariosClient;
import com.testvagrant.ekam.dashboard.clients.ScreenshotsClient;
import com.testvagrant.ekam.dashboard.io.GsonParser;
import com.testvagrant.ekam.dashboard.io.ScreenshotLoader;
import com.testvagrant.ekam.dashboard.io.StepFinder;
import com.testvagrant.ekam.dashboard.io.TargetFinder;
import com.testvagrant.ekam.dashboard.models.DistinctScenarios;
import com.testvagrant.ekam.dashboard.models.EkamTestNGBuild;
import com.testvagrant.ekam.dashboard.models.ScenarioTimeline;
import com.testvagrant.ekam.dashboard.models.builders.ScenarioBuilder;
import com.testvagrant.ekam.dashboard.models.dashboard.*;
import com.testvagrant.ekam.dashboard.clients.BuildsClient;
import com.testvagrant.ekam.dashboard.clients.DevicesClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class EkamReportPublisher implements EkamDashboardPublisher {

  private String dashboardServer;
  private BuildOptions buildOptions;
  private Build build;
  private BuildsClient buildsClient;
  private DevicesClient devicesClient;
  private ScenariosClient scenariosClient;
  private ScreenshotsClient screenshotsClient;
  private EkamTestNGBuild ekamTestNGBuild;

  public EkamReportPublisher() {
    this("http://localhost:8090");
  }

  public EkamReportPublisher(String dashboardServer) {
    this(dashboardServer, new BuildOptions("mobile"));
  }

  public EkamReportPublisher(String dashboardServer, BuildOptions buildOptions) {
    this.dashboardServer = dashboardServer;
    this.buildOptions = buildOptions;
    buildsClient = new BuildsClient(dashboardServer);
    build = buildsClient.startBuild();
    devicesClient = new DevicesClient(dashboardServer);
    scenariosClient = new ScenariosClient(dashboardServer);
    screenshotsClient = new ScreenshotsClient(dashboardServer);
    ekamTestNGBuild = EkamTestNGBuild.builder().build();
    ekamTestNGBuild =
        GsonParser.toInstance()
            .deserialize(EkamExecutionTimelinePaths.BUILD_INFO, EkamTestNGBuild.class);
  }

  @Override
  public void publish() {
    List<Scenario> scenariosToPublish = new CopyOnWriteArrayList<>();
    ekamTestNGBuild.getTestCases().stream()
        .forEach(
            testCase -> {
              ScreenshotLoader screenshotLoader = new ScreenshotLoader();
              Device deviceDetails = TargetFinder.findTargetDevice(buildOptions.getTarget(), testCase);
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
    updateBuild(scenariosToPublish, distinctScenariosCount);
  }

  private void updateBuild(List<Scenario> scenariosToPublish, DistinctScenarios distinctScenariosCount) {
    build.setBuildScenarios(scenariosToPublish.size());
    build.setBuildStartTime(ekamTestNGBuild.getBuildStartTime());
    build.setBuildEndTime(ekamTestNGBuild.getBuildEndTime());
    build.setScenarioSuccessRate(distinctScenariosCount.getPassPercentage());
    build.setScenariosCount(distinctScenariosCount.getDistinctScenariosCount());
    build.setCommitId(buildOptions.getCommitId());
    build.setCommitUrl(buildOptions.getCommitUrl());
    build.setTarget(buildOptions.getTarget());
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
