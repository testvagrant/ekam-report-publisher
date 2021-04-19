package com.testvagrant.optimus.dashboard.models.dashboard;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Getter @Setter
public class Scenario {
    private String id;
    private String scenarioName;
    private Integer dataRowNumber;
    private Integer location;
    private Collection<String> tags;
    private String startTime;
    private String buildId;
    private String deviceId;
    private String status;
    private Boolean completed;
    private String endTime;
    private Integer timeTaken;
  private String scenarioTimeline;
  private String steps;
    private byte[] failedOnScreen;
    private String stacktrace;
    private String activity;
    private String featureName;
    private String featureFileName;

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ", \"scenarioName\":\"" + scenarioName + "\""
                + ", \"dataRowNumber\":\"" + dataRowNumber + "\""
                + ", \"location\":\"" + location + "\""
                + ", \"tags\":" + tags
                + ", \"startTime\":" + startTime
                + ", \"buildId\":\"" + buildId + "\""
                + ", \"deviceId\":\"" + deviceId + "\""
                + ", \"status\":\"" + status + "\""
                + ", \"completed\":\"" + completed + "\""
                + ", \"endTime\":" + endTime
                + ", \"timeTaken\":\"" + timeTaken + "\""
                + ", \"scenarioTimeline\":\"" + scenarioTimeline + "\""
                + ", \"steps\":\"" + steps + "\""
                + ", \"failedOnScreen\":" + failedOnScreen
                + ", \"stacktrace\":\"" + stacktrace + "\""
                + ", \"activity\":\"" + activity + "\""
                + ", \"featureName\":\"" + featureName + "\""
                + ", \"featureFileName\":\"" + featureFileName + "\""
                + "}";
    }
}
