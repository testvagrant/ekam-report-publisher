package com.testvagrant.optimus.dashboard.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TargetDetails {
  private String name;
  private String platform;
  private String platformVersion;
  private String runsOn;
  private String udid;

  @Override
  public String toString() {
    return "{"
        + "\"deviceName\":\""
        + name
        + "\""
        + ", \"platform\":\""
        + platform
        + "\""
        + ", \"platformVersion\":\""
        + platformVersion
        + "\""
        + ", \"runsOn\":\""
        + runsOn
        + "\""
        + ", \"udid\":\""
        + udid
        + "\""
        + "}}";
  }
}
