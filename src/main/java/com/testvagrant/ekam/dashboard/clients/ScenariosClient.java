package com.testvagrant.ekam.dashboard.clients;

import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import com.testvagrant.ekam.dashboard.models.DistinctScenarios;
import com.testvagrant.ekam.dashboard.models.dashboard.Scenario;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public class ScenariosClient extends RetrofitBaseClient {
  private final ScenariosService scenariosService;

  public ScenariosClient(String baseUrl) {
    super(baseUrl);
    this.scenariosService = httpClient.getService(ScenariosService.class);
  }

  public ScenariosClient(RetrofitClient httpClient) {
    super(httpClient);
    this.scenariosService = httpClient.getService(ScenariosService.class);
  }

  public Scenario insertScenario(Scenario scenario) {
    return httpClient.execute(scenariosService.insertScenario(scenario));
  }

  public List<Scenario> insertScenarios(List<Scenario> scenarios) {
    return httpClient.execute(scenariosService.insertScenarios(scenarios));
  }

  public DistinctScenarios getDistinctScenariosCount() {
    return httpClient.execute(scenariosService.distinctScenariosCount());
  }

  public List<Scenario> getDistinctScenarios() {
    return httpClient.execute(scenariosService.distinctScenarios());
  }

  public Scenario updateSceanario(Scenario scenario) {
    return httpClient.execute(scenariosService.updateScenario(scenario.getId(), scenario));
  }

  private interface ScenariosService {
    @POST("/scenarios/insert")
    Call<Scenario> insertScenario(@Body Scenario scenario);

    @POST("/scenarios/insert/bulk")
    Call<List<Scenario>> insertScenarios(@Body List<Scenario> scenario);

    @GET("/scenarios/distinct/count")
    Call<DistinctScenarios> distinctScenariosCount();

    @GET("/scenarios/distinct")
    Call<List<Scenario>> distinctScenarios();

    @PATCH("/scenarios/{id}")
    Call<Scenario> updateScenario(@Path("id") String scenarioId, @Body Scenario scenario);
  }
}
