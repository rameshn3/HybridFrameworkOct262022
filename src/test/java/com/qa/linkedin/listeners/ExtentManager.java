package com.qa.linkedin.listeners;
import java.io.File;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.linkedin.util.Constants;
import com.qa.linkedin.util.TestUtil;


public class ExtentManager {
	  private static final Logger log = LogManager.getLogger(ExtentManager.class.getName());
	    private static ExtentReports extent;

	    public static ExtentReports getInstance() {
	        if (extent == null) {
	            createInstance();
	        }
	        return extent;
	    }
	public static synchronized ExtentReports createInstance() {
        String fileName = TestUtil.getReportName();
        String reportsDirectory = Constants.REPORTS_DIRECTORY;
        new File(reportsDirectory).mkdirs();
        String path = reportsDirectory + fileName;
        log.info("*********** Report Path ***********");
        log.info(path);
        log.info("*********** Report Path ***********");
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(path);    htmlReporter.config().setTheme(Theme.STANDARD);
    htmlReporter.config().setDocumentTitle("Linkedin Automation Test Run");
    htmlReporter.config().setEncoding("utf-8");
    htmlReporter.config().setReportName(fileName);
    extent = new ExtentReports();
    extent.setSystemInfo("Organization", "RameshQaAutomationPlatform");
    extent.setSystemInfo("Automation Framework", "Selenium Webdriver");
    extent.setSystemInfo("Automation Tester", "Ramesh");
    extent.setSystemInfo("Build no", "QA-1234");
    extent.attachReporter(htmlReporter);
    
    return extent;
}

}
