package com.qa.linkedin.pages;
import com.qa.linkedin.testcases.LinkedinFeedPageTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.qa.linkedin.util.BasePageWebActions;
/**
*This class has all the objects and methods after login to the linkedin
*
*/
public class LinkedinFeedPage extends BasePageWebActions{

	private Logger log= LogManager.getLogger(LinkedinFeedPageTest.class);
	
	public LinkedinFeedPage() {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(css="div[class*='feed-identity-module']")
	private WebElement profileRailCard;
	
	@FindBy(css="img[class*='global-nav__me-photo ember-view']")
	private WebElement profileMeIcon;
	
	@FindBy(css="input[class*='search-global-typeahead__input']")
	private WebElement searchEditbox;
	
	@FindBy(xpath = "//a[@class='global-nav__secondary-link mv1'][contains(.,'Sign Out')]")
	private WebElement signoutLink;
	//@FindAll(xpath="//ul[contains(@class,'global-nav__primary-items')]/li/a//span")
	
	public String getLinkedinFeedPageTitle() {
		log.info("fethcing the Linkedin feed page title");
		return driver.getTitle();
	}
	
	public boolean isProfileRailCardPresent() {
		log.info("checking the profile rail card availability");
		return profileRailCard.isDisplayed();
	}
	
	public void doLogout() throws InterruptedException {
		log.info("click on profilemeicon");
		click(profileMeIcon);
		Thread.sleep(1000);
		click(signoutLink);
		
	}
	
	public LinkedinSearchResultsPage doPeopleSearch(String keyword) throws InterruptedException {
		log.debug("clear & type the keyword "+keyword+" in searchEditbox");
		clearText(searchEditbox);
		type(searchEditbox,keyword);
		searchEditbox.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		return new LinkedinSearchResultsPage();
		
	}
	
}
