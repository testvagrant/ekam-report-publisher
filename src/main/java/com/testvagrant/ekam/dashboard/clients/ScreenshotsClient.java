package com.testvagrant.ekam.dashboard.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.dashboard.models.dashboard.Screenshot;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ScreenshotsClient extends RetrofitBaseClient {

  private final ScreenshotsService screenshotsService;

  public ScreenshotsClient(String baseUrl) {
    super(baseUrl);
    screenshotsService = httpClient.getService(ScreenshotsService.class);
  }

  public String storeScreenshot(Screenshot screenshot) {
    return httpClient.execute(screenshotsService.storeScreenShot(screenshot));
  }

  private interface ScreenshotsService {
    @POST("/screenshots")
    Call<String> storeScreenShot(@Body Screenshot screenshot);
  }
}
