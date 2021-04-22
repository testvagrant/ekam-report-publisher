package com.testvagrant.optimus.dashboard.models.dashboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Device {
  private String id;

  @Builder.Default private String platform = "android";

  @Builder.Default private String status = "available";

  @Builder.Default private String deviceName = "test device";

  @Builder.Default private String runsOn = "device";

  @Builder.Default private String platformVersion = "10";

  @Builder.Default private String udid = "123456";

  private String buildId;

  @Override
  public String toString() {
    return "Device{"
        + "platform:\""
        + platform
        + '\"'
        + ", deviceName:\""
        + deviceName
        + '\"'
        + ", runsOn:\""
        + runsOn
        + '\"'
        + ", platformVersion:\""
        + platformVersion
        + '\"'
        + ", udid:\""
        + udid
        + '\"'
        + '}';
  }
}
