package com.qa.linkedin.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.qa.linkedin.base.TestBase;
import com.qa.linkedin.pages.LinkedinFeedPage;
import com.qa.linkedin.pages.LinkedinHomePage;
import com.qa.linkedin.pages.LinkedinLoginPage;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class LinkedinFeedPageTest extends TestBase{
	private Logger log= LogManager.getLogger(LinkedinFeedPageTest.class.getName());
	
	  @BeforeClass(alwaysRun=true)
	  public void beforeClass() throws InterruptedException {
		  log.info("creating login page object");
		  homePg=new LinkedinHomePage();
		  loginPg=new LinkedinLoginPage();
		  feedPg=new LinkedinFeedPage();
		  loginPg= homePg.clickOnSigninLink();
	  }


	  @Test(description="login to linkedin")
	  public void doLoginTest() throws InterruptedException, IOException {
		log.debug("starts---doLoginTest()-----");
		feedPg=loginPg.doLogin(readPropertyValue("uname"), readPropertyValue("pwd"));
		log.info("Login is Successful");
	  }
	  
	  
  @Test(description="verifying profile railcard element",dependsOnMethods= {"doLoginTest"})
  public void verifyProfileRailcardTest() {
	  log.debug("***Starts verifyProfileRailcardTest()****");
	Assert.assertTrue(feedPg.isProfileRailCardPresent(),"profilerailcard element is not present");
	 log.debug("***end verifyProfileRailcardTest()****");
	  
  }
  
  @Test(dependsOnMethods= {"doLoginTest"})
  public void verifyFeedPageTitleTest() {
	  log.debug("***Starts verifyFeedPageTitleTest()****");
		Assert.assertTrue(feedPg.getLinkedinFeedPageTitle().contains("Feed | LinkedIn"),"Feed | LinkedIn is not present");
		 log.debug("***end verifyFeedPageTitleTest()****");
		   
  }
  
  @Test(dependsOnMethods= {"doLoginTest"})
  public void doLogoutTest() throws InterruptedException {
	  log.debug("***Starts doLogoutTest()****");
	  feedPg.doLogout();
	  
  }
}
