package com.qa.linkedin.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;

import com.qa.linkedin.util.BasePageWebActions;

public class LinkedinSearchResultsPage extends BasePageWebActions{

	private Logger log= LogManager.getLogger(LinkedinSearchResultsPage.class);
	
	public LinkedinSearchResultsPage() {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath = "//div[contains(@class,'search-results')]/a[1]")
	private WebElement seeAllPeopleResultsLink;
	
	@FindBy(xpath="//h2[@class='pb2 t-black--light t-14']")
	private WebElement searchResultsText;
	
	@FindBy(xpath="//*[@class='global-nav__nav']/ul/li[1]/a")
	private WebElement homeTab;
	
	public String getSearchResultsPageTitle() {
		log.debug("fetching searchResults page title");
		return driver.getTitle();
	}
	
	public String getResultsCountText() throws InterruptedException {
		log.info("get the results count text in results page");
		return getText(searchResultsText);
	}
	
	public void clickOnSeeAllPeopleResultsLink() throws InterruptedException {
		log.info("click on seeAllPeopleResultsLink");
		if(isElementPresent(By.xpath("//div[contains(@class,'search-results')]/a[1]"))) {
			click(seeAllPeopleResultsLink);
		}

	}
	
	
	public void clickOnHomeTab() throws InterruptedException {
		log.info("click on HomeTab");
		click(homeTab);
	}
	
	public long getResultCount() throws InterruptedException {
		//About 308,000 results
		String str=getResultsCountText();
		String[] strArray=str.split(" ");
		
		log.debug("resuls count is:"+strArray[1]);
		log.debug("convert the string into long format");
		long count=Long.parseLong(strArray[1].replace(",", ""));
		return count;
		
	}
	
	
}
