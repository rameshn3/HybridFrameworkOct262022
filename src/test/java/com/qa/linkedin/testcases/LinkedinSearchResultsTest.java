package com.qa.linkedin.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.qa.linkedin.base.TestBase;
import com.qa.linkedin.pages.LinkedinFeedPage;
import com.qa.linkedin.pages.LinkedinHomePage;
import com.qa.linkedin.pages.LinkedinLoginPage;
import com.qa.linkedin.pages.LinkedinSearchResultsPage;
import com.qa.linkedin.util.Constants;
import com.qa.linkedin.util.ExcelUtils;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class LinkedinSearchResultsTest extends TestBase{

	private static String filePath=Constants.EXCEL_DIRECTORY;

	private Logger log= LogManager.getLogger(LinkedinSearchResultsTest.class.getName());
 
  
  @BeforeClass(alwaysRun=true)
  public void beforeClass() throws InterruptedException {
	  log.info("creating login page object");
	  homePg=new LinkedinHomePage();
	  loginPg=new LinkedinLoginPage();
	  feedPg=new LinkedinFeedPage();
	  searchPg=new LinkedinSearchResultsPage();
	  loginPg= homePg.clickOnSigninLink();
  }
	
  @Test(description="login to linkedin",priority=1)
  public void doLoginTest() throws InterruptedException, IOException {
	log.debug("starts---doLoginTest()-----");
	feedPg=loginPg.doLogin(readPropertyValue("uname"), readPropertyValue("pwd"));
	log.info("Login is Successful");
  }
	
 @Test(dependsOnMethods= {"doLoginTest"},dataProvider="getTestData",priority=2)
  public void doPeopleSearchTest(String keyword) throws InterruptedException {
		log.debug("perofrming the doPeopleSearchTest() for:"+keyword);
		searchPg=feedPg.doPeopleSearch(keyword);
		Thread.sleep(1000);
		log.info("click on seeAllpeopleResultsLink");
		searchPg.clickOnSeeAllPeopleResultsLink();
		Thread.sleep(1000);
		log.info("verify the search results page title");
		Assert.assertTrue(searchPg.getSearchResultsPageTitle().contains("Search | LinkedIn"));
		log.info("fetching search results count for:"+keyword);
		long count=searchPg.getResultCount();
		log.info("count for "+keyword+" is:"+count);
		log.info("click on Home tab");
		searchPg.clickOnHomeTab();
  }
 
	@DataProvider
	public Object[][] getTestData() throws InvalidFormatException, IOException{
		Object[][] data=new ExcelUtils().getTestData(filePath, "Sheet1");
		return data;
	}
	
	@Test(priority=3)
	
	public void doLogoutTest() throws InterruptedException {
		log.info("performing the logout from linkedin");
		Thread.sleep(2000);
		feedPg.doLogout();
	}

}
