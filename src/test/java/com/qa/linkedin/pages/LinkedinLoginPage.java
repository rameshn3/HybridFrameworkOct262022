package com.qa.linkedin.pages;

import com.qa.linkedin.testcases.LinkedinFeedPageTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.linkedin.util.BasePageWebActions;

public class LinkedinLoginPage extends BasePageWebActions{

	private Logger log= LogManager.getLogger(LinkedinLoginPage.class);
	
	//create a Constructor
	public LinkedinLoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="h1[class*='header__content__heading']")
	private WebElement signinHeaderText;
	
	@FindBy(id="username")
	private WebElement emailEditbox;
	
	@FindBy(name="session_password")
	private WebElement passwordEditbox;
	
	@FindBy(xpath="//button[@type='submit']")
	private WebElement signinBtn;
	
	public String getLinkedinSigninpageTitle() {
		log.info("fetching the linkedin signin page title");
		return driver.getTitle();
	}
	
	public boolean isSigninHeaderTextPresent() {
		log.info("check signin header text is present or not");
		return signinHeaderText.isDisplayed();
	}
	

	public void clickSigninBtn() throws InterruptedException {
		log.info("click on signinBtn in login page");
		click(signinBtn);
	}
	
	public LinkedinFeedPage doLogin(String uname,String pwd) throws InterruptedException {
		log.debug("perform the login action ");
		log.info("clear the content and type in emailEditbox");
		clearText(emailEditbox);
		type(emailEditbox,uname);
		log.info("clear the content and type in passwordEditbox");
		clearText(passwordEditbox);
		type(passwordEditbox,pwd);
		clickSigninBtn();
		return new LinkedinFeedPage();
	}
	
}
