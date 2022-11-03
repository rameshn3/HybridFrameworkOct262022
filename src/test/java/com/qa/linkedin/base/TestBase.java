package com.qa.linkedin.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class TestBase {

	public static WebDriver driver;
	public WebDriverWait wait;
	private static final Logger log = LogManager.getLogger(TestBase.class);
	
	/**
	 * This method reads the property value from properties file
	 * @param key
	 * @return
	 */
	
	public String readPropertyValue(String key) throws IOException {
		log.info("Create Object for Properties class");
		Properties prop = new Properties();
		log.info("Read the properties file");
		try {
			FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")
					+ "\\src\\test\\java\\com\\qa\\linkedin\\config\\config.properties"));
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	@BeforeClass
	public void setup() throws IOException {
		log.debug("reading the browser value from properties file");
		String browserName=readPropertyValue("browser");
		log.info("Launching the browser:"+browserName);
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions opt=new FirefoxOptions();
			opt.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			
			//interface refvar=new implementedclass();
			 driver=new FirefoxDriver(opt);
	
		}else if(browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
		 driver = new EdgeDriver();
		}
	  
		log.debug(" maximize the window");
		 driver.manage().window().maximize();
		log.info("implicitwait");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 log.info("create object for WebDriverWait class");
		 	wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		 log.debug("open the application url :"+readPropertyValue("AppUrl"));
		 driver.get(readPropertyValue("AppUrl"));
		
	}
	
	@AfterClass
	public void tearDown() {
		log.debug("close the browser");
	if(driver!=null) {
		driver.quit();
	}
	
	}
	
	
}
