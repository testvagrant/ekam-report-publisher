package com.testvagrant.ekam.dashboard;

import org.testng.Assert;
import org.testng.annotations.Test;

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

  @Test(groups = "checkRates", description = "To country should display valid convertion rate", dependsOnMethods = "checkRatesNegative" )
  public void checkRatesNegativeDependent() throws InterruptedException {
    Thread.sleep(8000);
    Assert.assertFalse(true, "To country does not display valid conversion rates");
  }


}
