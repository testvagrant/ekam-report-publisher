package com.testvagrant.ekam.dashboard;

import org.testng.annotations.Test;

public class TransferGoCheckRatesTestFromTo {

  @Test(groups = "checkRates")
  public void checkRatesFrom() throws InterruptedException {
    Thread.sleep(10000);
  }

  @Test(groups = "checkRates", description = "To country should display valid convertion rate")
  public void checkRatesTo() throws InterruptedException {
    Thread.sleep(8000);
  }
}
