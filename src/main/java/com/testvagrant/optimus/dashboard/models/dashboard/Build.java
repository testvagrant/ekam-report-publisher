package com.testvagrant.optimus.dashboard.models.dashboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Build {
  private String id;
  private String runMode;
  private String buildStartTime;
  private String buildEndTime;
  private int buildScenarios;
  private int buildSuccessRate;
  private int scenariosCount;
  private String scenarioSuccessRate;
  private String crashlytics;
  private boolean complete;

  @Override
  public String toString() {
    return "{"
        + "\"id\":\""
        + id
        + "\""
        + ", \"runMode\":\""
        + runMode
        + "\""
        + ", \"buildStartTime\":\""
        + buildStartTime
        + "\""
        + ", \"buildEndTime\":\""
        + buildEndTime
        + "\""
        + ", \"buildScenarios\":\""
        + buildScenarios
        + "\""
        + ", \"buildSuccessRate\":\""
        + buildSuccessRate
        + "\""
        + ", \"scenariosCount\":\""
        + scenariosCount
        + "\""
        + ", \"scenarioSuccessRate\":\""
        + scenarioSuccessRate
        + "\""
        + ", \"crashlytics\":\""
        + crashlytics
        + "\""
        + ", \"complete\":\""
        + complete
        + "\""
        + "}";
  }
}
