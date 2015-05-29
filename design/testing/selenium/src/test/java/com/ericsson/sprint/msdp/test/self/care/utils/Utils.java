package com.ericsson.sprint.msdp.test.self.care.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Utils {
  
  public static void takeScreenshot(RemoteWebDriver remoteWebDriver, String screenShotDirectory, 
      String methodName, String screenshotSuffix) {

    final File screenshotFile = ((TakesScreenshot) remoteWebDriver).getScreenshotAs(OutputType.FILE);
       
    String screenshotFilename = "";

    if (screenshotSuffix.length() > 1) {
      screenshotFilename = methodName + screenshotSuffix + ".png";
    } else {
      screenshotFilename = methodName + ".png";
    }

    try {
      FileUtils.copyFile(screenshotFile, new File(screenShotDirectory + screenshotFilename));
    } catch (final IOException e) {
      System.out.println("Error while creating screenshot file.");
    }
  }
  
  public static void scrollPageDown(final RemoteWebDriver remoteWebDriver, int amountToScrollDown){
    JavascriptExecutor jse = (JavascriptExecutor)remoteWebDriver;
    jse.executeScript("window.scrollBy(0, " + amountToScrollDown + ")", "");
    System.out.println("After scrolling.");
  }

}
