package com.testvagrant.ekam.dashboard.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import com.testvagrant.ekam.dashboard.models.dashboard.Device;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class DevicesClient extends RetrofitBaseClient {
  private DevicesService devicesService;

  public DevicesClient(String baseUrl) {
    super(baseUrl);
    this.devicesService = httpClient.getService(DevicesService.class);
  }

  public DevicesClient(RetrofitClient httpClient) {
    super(httpClient);
    this.devicesService = httpClient.getService(DevicesService.class);
  }

  public Device insertDevice(String buildId, Device device) {
    device.setBuildId(buildId);
    if (!isDevicePresent(device.getBuildId().toString(), device.getUdid())) {
      Call<Device> deviceCall = devicesService.insertDevice(device);
      return httpClient.execute(deviceCall);
    }
    return httpClient.execute(devicesService.findDeviceByBuildIdAndUdid(buildId, device.getUdid()));
  }

  public boolean isDevicePresent(String buildId, String udid) {
    Device deviceResponse =
        httpClient.execute(devicesService.findDeviceByBuildIdAndUdid(buildId, udid));
    return deviceResponse.getId() != null;
  }

  private interface DevicesService {
    @POST("/devices/insert")
    Call<Device> insertDevice(@Body Device device);

    @GET("/devices/findByBuildIdAndUdid")
    Call<Device> findDeviceByBuildIdAndUdid(
        @Query("buildId") String buildId, @Query("udid") String udid);
  }
}
