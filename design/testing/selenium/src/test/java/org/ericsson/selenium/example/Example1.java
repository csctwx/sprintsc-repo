package org.ericsson.selenium.example;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.DesiredCapabilities;

//import com.drutt.pmm.runtime.context.UserExecutionContext;
//import com.drutt.pmm.userprofile.core.UserContext;

public class Example1 {

	public static void main(String[] args) {
		// Create a new instance of the html unit driver
		// Notice that the remainder of the code relies on the interface,
		// not the implementation.
		System.out.println("Starting test...");
		System.setProperty("webdriver.chrome.driver", "/usr/lib64/chromium/chromedriver");
		WebDriver driver = new ChromeDriver(DesiredCapabilities.chrome());

		// And now use this to visit Google
		driver.get("http://www.google.com");

		// Find the text input element by its name
		WebElement element = driver.findElement(By.name("q"));

		// Enter something to search for
		element.sendKeys("Cheese!");

		// Now submit the form. WebDriver will find the form for us from the
		// element
		element.submit();

		// Check the title of the page
		System.out.println("Page title is: " + driver.getTitle());

		driver.quit();
		System.out.println("Test ended...");
		/*
		UserExecutionContext ctx;
		UserContext arg;
		String str = ctx.getRequest().getServletRequest().toString();
		Map <String, Object> map=ctx.getScope();
		map.put("bodyIMEI", bodyIMEI);
		*/
	}

}
