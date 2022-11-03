package com.qa.linkedin.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.linkedin.util.BasePageWebActions;

public class LinkedinHomePage extends BasePageWebActions{

	private Logger log= LogManager.getLogger(LinkedinHomePage.class);
	
	//create a Constructor
	public LinkedinHomePage() {
		
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="a[class*='nav__logo-link']")
	private WebElement linkedinLogo;
	
	@FindBy(css="a.nav__button-secondary.btn-md.btn-secondary-emphasis")
	private WebElement signinLink;
	
	public String getLinkedinHomePagetitle() {
		log.info("fetching the LinkedinHomePage title");
		return driver.getTitle();
	}
	
	public boolean isLinkedinLogoPresent() {
		log.info("checking linkedinlogo is present or not");
		return linkedinLogo.isDisplayed();
	}
	
	public LinkedinLoginPage clickOnSigninLink() throws InterruptedException {
		log.info("click on signin link in linkedin home page");
		click(signinLink);
		return new LinkedinLoginPage();
	}
	
}
