package com.qa.linkedin.pages;

import com.qa.linkedin.util.BasePageWebActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigateToProfilePage extends BasePageWebActions {

	private Logger log= LogManager.getLogger(NavigateToProfilePage.class);
	
	public NavigateToProfilePage() {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath = "//a[@class='app-aware-link'][contains(.,'See all people results')]")
	private WebElement seeAllPeopleResultsLink;
    
  }
