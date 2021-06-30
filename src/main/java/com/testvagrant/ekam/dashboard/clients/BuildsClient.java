package com.testvagrant.ekam.dashboard.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import com.testvagrant.ekam.dashboard.models.dashboard.Build;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class BuildsClient extends RetrofitBaseClient {

  private final BuildsService buildsService;

  public BuildsClient(String baseUrl) {
    super(baseUrl);
    this.buildsService = httpClient.getService(BuildsService.class);
  }

  public BuildsClient(RetrofitClient retrofitClient) {
    super(retrofitClient);
    this.buildsService = httpClient.getService(BuildsService.class);
  }

  public Build startBuild() {
    Call<Build> newBuild = buildsService.createNewBuild();
    return httpClient.executeAsObj(newBuild);
  }

  public Build stopBuild(Build build) {
    build.setComplete(true);
    return httpClient.execute(buildsService.updateBuild(build.getId(), build));
  }

  private interface BuildsService {
    @POST("/builds/new")
    Call<Build> createNewBuild();

    @PATCH("/builds/{id}")
    Call<Build> updateBuild(@Path("id") String buildId, @Body Build build);
  }
}
