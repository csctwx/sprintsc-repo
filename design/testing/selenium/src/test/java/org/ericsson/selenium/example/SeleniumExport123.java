package org.ericsson.selenium.example;

import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

import java.net.URL;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumExport123 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    // driver = new FirefoxDriver();
	//Chromedriver
	  DesiredCapabilities caps = SelendroidCapabilities.android();
	URL url = new URL("http://localhost:9515/wd/hub");
	//driver = new RemoteWebDriver(url, caps);
		//Emulator
		//URL url = new URL("http://localhost:4444/wd/hub");
		driver = new SelendroidDriver(url, caps);
	  
    
    baseUrl = "http://www.centurymedia.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSeleniumExport123() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.cssSelector("img[alt=\"Artist\"]")).click();
    driver.findElement(By.id("ctl00_CenterContent_ArtistFlowOverview_Repeater1_ctl18_Image1")).click();
    Assert.assertTrue(driver.getTitle().contains("Dark"));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
