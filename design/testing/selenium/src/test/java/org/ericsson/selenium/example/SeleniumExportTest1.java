package org.ericsson.selenium.example;

import static org.junit.Assert.fail;
import io.selendroid.SelendroidCapabilities;
import io.selendroid.SelendroidDriver;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.selendroid.SelendroidCapabilities;

public class SeleniumExportTest1 {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		
		DesiredCapabilities caps = SelendroidCapabilities.android();
		
		//Chromedriver
		//URL url = new URL("http://localhost:9515/wd/hub");
		//driver = new RemoteWebDriver(url, caps);
		
		//Emulator
		URL url = new URL("http://localhost:4444/wd/hub");
		driver = new SelendroidDriver(url, caps);
				
		baseUrl = "https://mim.t-mobile.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// driver.switchTo().window("WEBVIEW");
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
		
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("verifypassword")).clear();
		driver.findElement(By.id("verifypassword")).sendKeys(password);


		driver.findElement(By.id("edit-info-submit")).click();
		driver.findElement(By.id("edit-info-agree")).click();
		/*
		 * driver.findElement(By.xpath(
		 * "//div[@id='congratulations-screen']/div/div[2]/div/div/span[2]"
		 * )).click();
		 * driver.findElement(By.xpath("//body[@id='subPage']/div[2]/div"
		 * )).click();
		 */
		driver.get(baseUrl + "primary/");
		Thread.sleep(2000);
		System.out.println(driver.getPageSource());
		System.out.println(driver.getTitle());
		//Assert.assertTrue(driver.getPageSource().contains("Munishab"));
		Assert.assertTrue(driver.getTitle().contains("T-Mobile"));

	}

	/*
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
*/
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
	private String password = "Test1234@";

}
