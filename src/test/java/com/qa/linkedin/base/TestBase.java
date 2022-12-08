package com.qa.linkedin.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.qa.linkedin.pages.LinkedinFeedPage;
import com.qa.linkedin.pages.LinkedinHomePage;
import com.qa.linkedin.pages.LinkedinLoginPage;
import com.qa.linkedin.pages.LinkedinSearchResultsPage;
import com.qa.linkedin.util.Constants;

import org.apache.logging.log4j.Logger;

public class TestBase {

	public static WebDriver driver;
	public WebDriverWait wait;
	protected LinkedinLoginPage loginPg;
	protected LinkedinHomePage homePg;
	protected LinkedinFeedPage feedPg;
	protected LinkedinSearchResultsPage searchPg;
	private static final Logger log = LogManager.getLogger(TestBase.class.getName());

	/**
	 * This method reads the property value from properties file
	 * 
	 * @param key
	 * @return
	 */

	public String readPropertyValue(String key) throws IOException {
		log.info("Create Object for Properties class");
		Properties prop = new Properties();
		log.info("Read the properties file");
		try {
			FileInputStream fis = new FileInputStream(new File(Constants.CONFIG_DIRECTORY));
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}

	@BeforeClass
	@Parameters({ "browser" })
	public void commonSetUp(String browser) throws IOException {
		log.debug("setting the driver");
		driver = WebDriverFactory.getInstance().getDriver(browser);
		log.info("create object for WebDriverWait class");
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		log.debug("open the application url :" + readPropertyValue("AppUrl"));
		driver.get(readPropertyValue("AppUrl"));

	}

	@AfterClass
	public void commonTearDown() {
		log.debug("close the browser");
		if (driver != null) {
			WebDriverFactory.getInstance().quitDriver();

		}

	}
}
