package com.qa.linkedin.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.qa.linkedin.base.TestBase;
import com.qa.linkedin.pages.LinkedinHomePage;
import com.qa.linkedin.pages.LinkedinLoginPage;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class LinkedinLoginPageTest extends TestBase{
    private Logger log= LogManager.getLogger(LinkedinLoginPageTest.class);
  
  @BeforeClass
  public void beforeClass() throws InterruptedException {
	  log.info("creating login page object");
	  homePg=new LinkedinHomePage();
	  loginPg=new LinkedinLoginPage();
	  loginPg= homePg.clickOnSigninLink();
  }

  @Test
  public void verifySigninHeaderText() {
	  log.debug("*****Start verifySigninHeaderText()********");
	  Assert.assertTrue(loginPg.isSigninHeaderTextPresent(),"signin headertext is not present");
	  
	  log.debug("*****end of verifySigninHeaderText()********");
  }
  
  @Test
  public void verifyLoginPageTitle() {
	  log.debug("*****Start verifyLoginPageTitle()********");
	  Assert.assertTrue(loginPg.getLinkedinSigninpageTitle().contains("Login, Sign in"),"signin page title is not correct");
	  
	  log.debug("*****end of verifyLoginPageTitle()********");
  }
  
}
