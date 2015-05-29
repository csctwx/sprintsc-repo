package org.ericsson.selenium.example;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumExportTMOODP1 {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {

		// Appium
		URL url = new URL("http://localhost:4723/wd/hub");
		DesiredCapabilities caps = DesiredCapabilities.android();

		// for Appium with Selendroid
		// caps.setCapability("automationName", "Selendroid");

		caps.setJavascriptEnabled(true);
		caps.setPlatform(Platform.ANDROID);
		caps.setCapability("device", "android");

		caps.setCapability("deviceName", "m7");
		caps.setCapability("platformName", "Android");
		// caps.setCapability("automationName","Selendroid");
		caps.setCapability("platformVersion", "4.4");

		caps.setBrowserName("Chrome");
		caps.setCapability("androidPackage", "com.android.chrome");
		caps.setCapability("androidDeviceSerial", "SH3AAW900773");
		
		driver = new RemoteWebDriver(url, caps);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSeleniumExportTMOODP1() throws Exception {
		baseUrl = "https://mim.t-mobile.com/";
		driver.get(baseUrl + "primary/openPage");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("M29@pre.com");
		driver.findElement(By.id("emailPass")).clear();
		driver.findElement(By.id("emailPass")).sendKeys(password);

		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(
				"/home/arthur/TEMP/screenshot_login.png"));

		driver.findElement(By.id("submit")).click();
		driver.findElement(By.name("msisdnChoice")).click();
		driver.findElement(By.id("edit-payment-information-button")).click();
		// driver.findElement(By.xpath("//div"))
		driver.findElement(By.id("firstname")).clear();
		driver.findElement(By.id("firstname")).sendKeys("Munishab");
		driver.findElement(By.id("edit-info-submit")).click();
		driver.findElement(By.id("edit-info-agree")).click();

		driver.get(baseUrl + "primary/");
		Thread.sleep(2000);
		Assert.assertTrue(driver.getPageSource().contains("Munishab"));

	}

	@Test
	public void testSeleniumExport5() throws Exception {
		baseUrl = "http://www.centurymedia.com";
		driver.get(baseUrl + "/");
		driver.findElement(By.cssSelector("img[alt=\"Artist\"]")).click();
		driver.findElement(
				By.id("ctl00_CenterContent_ArtistFlowOverview_Repeater1_ctl17_Image1"))
				.click();
		Assert.assertEquals("Century Media Records - Dark Tranquillity",
				driver.getTitle());
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
	private String password="Test1234@";
}
