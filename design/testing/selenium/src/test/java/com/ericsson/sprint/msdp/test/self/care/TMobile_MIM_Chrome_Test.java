package com.ericsson.sprint.msdp.test.self.care;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ericsson.sprint.msdp.test.self.care.utils.TestingSshClient;
import com.ericsson.sprint.msdp.test.self.care.utils.Utils;

public class TMobile_MIM_Chrome_Test{

  public TMobile_MIM_Chrome_Test() throws MalformedURLException {}

  // URL of the Chromedriver server.
  final URL appiumUrl = new URL("http://127.0.0.1:9515");
  
  // Create a new RemoteWebDriver acting as a Chrome browser.
  RemoteWebDriver   remoteWebDriver;

  // Local folder containing the test report files.
  final static String reportDirectory  = "./target/";
  
  // URL of the portal to test.
  final String portalUrl        = "http://5.232.9.94:8080/primary/openPage";

  static TestingSshClient sshClient;
  WebElement webElement;
  String testCaseName;
  
  /*
    @BeforeClass - oneTimeSetUp
    @Before - setUp
    @Test - testEmptyCollection
    @After - tearDown
    @Before - setUp
    @Test - testOneItemCollection
    @After - tearDown
    @AfterClass - oneTimeTearDown
   */
  
  /**
   * Sets up the test fixture. 
   * (Called before every test case method.)
   */
  @Before
  public void setUp() throws MalformedURLException {
    // SSH client to tail/download logs.
    sshClient = new TestingSshClient();
    remoteWebDriver = new RemoteWebDriver(appiumUrl, DesiredCapabilities.chrome());
  }
  
  /**
   * Tears down the test fixture. 
   * (Called after every test case method.)
   */
  @After
  public void tearDown() {
    // Close the webdriver.
    remoteWebDriver.quit();
    // Close the SSH session.
    sshClient.closeSSH();
  }
  
  /**
   * Download logs from test suite.
   * (Called after end of all test cases.)
   */
  @AfterClass
  public static void downloadLogs() {
    sshClient = new TestingSshClient();
    sshClient.downloadZippedLogs(reportDirectory);
  }
  
  @Test
  public void test_TC_1_0_testWifiPage() {
    testCaseName = Thread.currentThread().getStackTrace()[1].getMethodName();
    sshClient.followLog(testCaseName);
    remoteWebDriver.get(portalUrl);
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "");
    Boolean wifiPresent = remoteWebDriver.findElements(By.className("wifiLogin")).size() > 0;
    Assert.assertEquals(wifiPresent, true);
  }

  @Test
  public void test_TC_1_1_testWifiPageShouldFail() {
    testCaseName = Thread.currentThread().getStackTrace()[1].getMethodName();
    sshClient.followLog(testCaseName);
    remoteWebDriver.get(portalUrl);
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "");
    Boolean wifiPresent = remoteWebDriver.findElements(By.className("wifiLoginZZZ")).size() > 0;
    Assert.assertEquals(wifiPresent, true);
  }
  
  
  @Test
  public void test_TC_1_2_testWifiPageShouldFailLogin() {
    testCaseName = Thread.currentThread().getStackTrace()[1].getMethodName();
    sshClient.followLog(testCaseName);
    remoteWebDriver.get(portalUrl);
    
    // Find the email input element by its id.
    webElement = remoteWebDriver.findElement(By.id("email"));
   
    // Enter email address.
    webElement.sendKeys("test@test.com");
   
    // Find the password input element by its id.
    webElement = remoteWebDriver.findElement(By.id("emailPass"));
   
    // Enter the password.
    webElement.sendKeys("Test1234");
    
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "before");
   
    // Submit the form. WebDriver will find the form for us from the element.
    webElement.submit();
   
    //Scroll page down so the error message is visible.
    Utils.scrollPageDown(remoteWebDriver, 250);
   
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "after");
    
    Boolean loginErrorMsg = remoteWebDriver.findElements(By.className("error")).size() > 0;
    Assert.assertEquals(loginErrorMsg, true);
  }
  
  @Test
  public void test_TC_1_3_testWifiPageRecoverPassword() {
    testCaseName = Thread.currentThread().getStackTrace()[1].getMethodName();
    sshClient.followLog(testCaseName);
    remoteWebDriver.get(portalUrl);
    
    // Find the email input element by its id.
    webElement = remoteWebDriver.findElement(By.id("email"));
    
    // Enter email address.
    webElement.sendKeys("test45@test.com");
    
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "before");

    // Find password recovery link by its id, then click it to initiate the password recovery.
    webElement = remoteWebDriver.findElement(By.id("initial-flow-recover-password"));
    webElement.click();
    
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "after");
    webElement = remoteWebDriver.findElement(By.className("info-container"));
    
    Boolean passwdRecoveryMessage = webElement.getText().contains("Your password has been sent");
    Assert.assertEquals(passwdRecoveryMessage, true);
  }
  
  // Account exists in ATL1.
  @Test
  public void test_TC_1_4_testWifiPageShouldPassLogin() {
    testCaseName = Thread.currentThread().getStackTrace()[1].getMethodName();
    sshClient.followLog(testCaseName);
    remoteWebDriver.get(portalUrl);
   
    // Find the email input element by its id.
    webElement = remoteWebDriver.findElement(By.id("email"));
   
    // Enter email address.
    webElement.sendKeys("test45@test.com");
   
    // Find the password input element by its id.
    webElement = remoteWebDriver.findElement(By.id("emailPass"));
   
    // Enter the password.
    webElement.sendKeys("Test123@");
   
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "beforeLogin");
    
    // Submit the form. WebDriver will find the form for us from the element.
    webElement.submit();
    
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "afterLogin");
    
    final Boolean listOfDevicesPresent = 
        remoteWebDriver.findElements(By.className("listOfDevices")).size() > 0;
    Assert.assertEquals(listOfDevicesPresent, true);
    
    // Find the msisdnChoice by its name.
    webElement = remoteWebDriver.findElement(By.name("msisdnChoice"));
    
    // Click the MSISDN choice.
    webElement.click();
    
    Utils.takeScreenshot(remoteWebDriver, reportDirectory, testCaseName, "dashboard");
    
    Boolean dashboardPresent = 
        remoteWebDriver.findElements(By.className("remaining-data")).size() > 0;
    Assert.assertEquals(dashboardPresent, true);
  }
  
}
