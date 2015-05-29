package org.ericsson.selenium.example;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class SeleniumExport5 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    //driver = new FirefoxDriver();
	System.setProperty("webdriver.chrome.driver", "/usr/lib64/chromium/chromedriver");
	driver = new ChromeDriver(DesiredCapabilities.chrome());
	  
    baseUrl = "https://www.google.ca/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSeleniumExport5() throws Exception {
    driver.get(baseUrl + "/?gfe_rd=cr&ei=nCnpU9-hNqPE8geq24HQBA&gws_rd=ssl");
    driver.findElement(By.id("gbqfq")).clear();
    driver.findElement(By.id("gbqfq")).sendKeys("game of thrones");
    driver.findElement(By.cssSelector("h3.r > a > em")).click();
    driver.findElement(By.cssSelector("area[alt=\"HBO Canada\"]")).click();
    driver.findElement(By.linkText("Game of Thrones")).click();
    driver.findElement(By.linkText("about the show")).click();
    Assert.assertEquals("Game of Thrones - About - HBO Canada", driver.getTitle());
  }

  @Test
  public void testSeleniumExport51() throws Exception {
    driver.get(baseUrl + "/?gfe_rd=cr&ei=nCnpU9-hNqPE8geq24HQBA&gws_rd=ssl");
    driver.findElement(By.id("gbqfq")).clear();
    driver.findElement(By.id("gbqfq")).sendKeys("game of thrones");
    driver.findElement(By.cssSelector("h3.r > a > em")).click();
    driver.findElement(By.cssSelector("area[alt=\"HBO Canada\"]")).click();
    driver.findElement(By.linkText("Game of Thrones")).click();
    driver.findElement(By.linkText("characters")).click();
    Assert.assertNotEquals("Game of Thrones - About - HBO Canada", driver.getTitle());
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
