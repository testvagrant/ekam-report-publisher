package com.testvagrant.optimus.dashboard;

import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import com.testvagrant.optimus.dashboard.clients.BuildsClient;
import com.testvagrant.optimus.dashboard.clients.DevicesClient;
import com.testvagrant.optimus.dashboard.clients.ScenariosClient;
import com.testvagrant.optimus.dashboard.models.ScenarioTimeline;
import com.testvagrant.optimus.dashboard.models.Step;
import com.testvagrant.optimus.dashboard.models.dashboard.Build;
import com.testvagrant.optimus.dashboard.models.dashboard.Device;
import com.testvagrant.optimus.dashboard.models.dashboard.Scenario;
import org.testng.Assert;
import org.testng.ITestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransferGoCheckRatesTest {

  @Test(groups = "checkRates")
  public void checkRatesPositive() throws InterruptedException {
    Thread.sleep(10000);
  }

  @Test(groups = "checkRates", description = "To country should display valid convertion rate")
  public void checkRatesNegative() throws InterruptedException {
    Thread.sleep(8000);
    Assert.assertFalse(true, "To country does not display valid conversion rates");
  }


}
