package com.qa.linkedin.base;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.qa.linkedin.util.Constants;

import java.time.Duration;


public class WebDriverFactory {
	 private static final Logger log = LogManager.getLogger(WebDriverFactory.class.getName());
	// Singleton
    // Only one instance of the class can exist at a time
    private static final WebDriverFactory instance = new WebDriverFactory();

    private WebDriverFactory() {
    }

    public static WebDriverFactory getInstance() {
        return instance;
    }

    private static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<String> threadedBrowser = new ThreadLocal<String>();
    /***
     * Get driver instance based on the browser type
     * @param browser
     * @return
     */
    public WebDriver getDriver(String browser) {
        WebDriver driver = null;
        threadedBrowser.set(browser);
        if (threadedDriver.get() == null) {
            try {
                if (browser.equalsIgnoreCase(Constants.FIREFOX)) {
                    FirefoxOptions ffOptions = setFFOptions();
                    driver = new FirefoxDriver(ffOptions);
                    threadedDriver.set(driver);
                }
                if (browser.equalsIgnoreCase(Constants.CHROME)) {
                    ChromeOptions chromeOptions = setChromeOptions();
                    driver = new ChromeDriver(chromeOptions);
                    threadedDriver.set(driver);
                }
                if (browser.equalsIgnoreCase(Constants.IE)) {
                    InternetExplorerOptions ieOptions = setIEOptions();
                    driver = new InternetExplorerDriver(ieOptions);
                    threadedDriver.set(driver);
                }if(browser.equalsIgnoreCase(Constants.EDGE)) {
                	EdgeOptions edgeopt=setEdgeOptions();
                	driver = new EdgeDriver(edgeopt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            threadedDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
            threadedDriver.get().manage().window().maximize();
        }
        return threadedDriver.get();
    }

    public String getBrowser() {
        return threadedBrowser.get();
    }

    
    /***
     * Quit driver instance
     */
    public void quitDriver() {
        threadedDriver.get().quit();
        threadedDriver.set(null);
    }

    /***
     * Set Chrome Options
     * @return options
     */
    private ChromeOptions setChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.setAcceptInsecureCerts(true);
        return options;
    }

    /***
     * Set Firefox Options
     * @return options
     */
    private FirefoxOptions setFFOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("disable-infobars");
        options.setAcceptInsecureCerts(true);
        return options;
    }

    /***
     * Set EdgeOptions
     * @return options
     */
    private EdgeOptions setEdgeOptions() {
    	EdgeOptions options = new EdgeOptions();
        options.addArguments("disable-infobars");
        options.setAcceptInsecureCerts(true);
        return options;
    }
    
    /***
     * Set Internet Explorer Options
     * @return options
     */
    private InternetExplorerOptions setIEOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
        options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
        options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
        options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        options.setCapability(InternetExplorerDriver.
                INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return options;
    }
}
