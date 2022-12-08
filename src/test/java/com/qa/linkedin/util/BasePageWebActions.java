package com.qa.linkedin.util;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.qa.linkedin.base.TestBase;
public class BasePageWebActions extends TestBase {

	private Logger log= LogManager.getLogger(BasePageWebActions.class.getName());
	Actions act;
	 private JavascriptExecutor js;
	// public WebDriver driver;
	    
	// Constructor
	public BasePageWebActions() {
		
		wait = new WebDriverWait(driver, Duration.ofSeconds(45));
		act = new Actions(driver);
		js=(JavascriptExecutor) driver;
		PageFactory.initElements(driver, this);
	}
	 /**
     * Refresh the current browser session
     */
    public void refresh() {
        driver.navigate().refresh();
        log.info("The Current Browser location was Refreshed...");
        //Util.sleep(3000, "The Current Browser location was Refreshed...");
    }

    /**
     * @return Returns the Current Page Title
     */
    public String getTitle() {
        String title = driver.getTitle();
        log.info("Title of the page is :: " + title);
        return title;
    }

    /**
     * @return Current Browser URL
     */
    public String getURL() {
        String url = driver.getCurrentUrl();
        log.info("Current URL is :: " + url);
        return url;
    }

    /**
     * Navigate browser back
     */
    public void navigateBrowserBack() {
        driver.navigate().back();
        log.info("Navigate back");
    }

    /**
     * Navigate browser forward
     */
    public void navigateBrowserForward() {
        driver.navigate().back();
        log.info("Navigate back");
    }

    /***
     * Builds the By type with given locator strategy
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @return Returns By Type
     */
    public By getByType(String locator) {
        By by = null;
        String locatorType = locator.split("=>")[0];
        locator = locator.split("=>")[1];
        try {
            if (locatorType.contains("id")) {
                by = By.id(locator);
            } else if (locatorType.contains("name")) {
                by = By.name(locator);
            } else if (locatorType.contains("xpath")) {
                by = By.xpath(locator);
            } else if (locatorType.contains("css")) {
                by = By.cssSelector(locator);
            } else if (locatorType.contains("class")) {
                by = By.className(locator);
            } else if (locatorType.contains("tag")) {
                by = By.tagName(locator);
            } else if (locatorType.contains("link")) {
                by = By.linkText(locator);
            } else if (locatorType.contains("partiallink")) {
                by = By.partialLinkText(locator);
            } else {
                log.info("Locator type not supported");
            }
        } catch (Exception e) {
            log.error("By type not found with: " + locatorType);
        }
        return by;
    }

    /**
     * Builds The WebElement By given locator strategy
     *
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *                tag=>example, xpath=>//example, link=>example
     * @param info - Information about element, usually text on element
     * @return WebElement
     */
    public WebElement getElement(String locator, String info) {
        WebElement element = null;
        By byType = getByType(locator);
        try {
            element = driver.findElement(byType);
        } catch (Exception e) {
            log.error("Element not found with: " + locator);
            e.printStackTrace();
        }
        return element;
    }

    /***
     *
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param info - Information about element, usually text on element
     * @return
     */
    public List<WebElement> getElementList(String locator, String info) {
        List<WebElement> elementList = new ArrayList<WebElement>();
        By byType = getByType(locator);
        try {
            elementList = driver.findElements(byType);
            if (elementList.size() > 0) {
                log.info("Element List found with: " + locator);
            } else {
                log.info("Element List not found with: " + locator);
            }
        } catch (Exception e) {
            log.error("Element List not found with: " + locator);
            e.printStackTrace();
        }
        return elementList;
    }

    /***
     * Check if element is present
     * @param locator locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @return boolean if element is present or not
     */
    public boolean isElementPresent(String locator, String info) {
        List<WebElement> elementList = getElementList(locator, info);
        int size = elementList.size();
        if (size > 0) {
            log.info("Element " + info + " Present with locator " + locator);
            return true;
        } else {
            log.info("Element " + info + " Not Present with locator " + locator);
            return false;
        }
    }

    /**
     * Click element with information about element and
     * time to wait in seconds after click
     *
     * @param element - WebElement to perform Click operation
     * @param info    - information about element
     */
    public void elementClick(WebElement element, String info, long timeToWait) {
        try {
            element.click();
            if (timeToWait == 0) {
                log.info("Clicked On :: " + info);
            } else {
                TestUtil.sleep(timeToWait, "Clicked on :: " + info);
            }
        } catch (Exception e) {
            log.error("Cannot click on :: " + info);
            takeScreenshot("Click ERROR", "");
        }
    }

    /**
     * Click element with no time to wait after click
     *
     * @param element - WebElement to perform Click operation
     * @param info    - information about element
     */
    public void elementClick(WebElement element, String info) {
        elementClick(element, info, 0);
    }

    /**
     * Click element with locator
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param info
     * @param timeToWait
     * @return
     */
    public void elementClick(String locator, String info, long timeToWait) {
        WebElement element = this.getElement(locator, info);
        elementClick(element, info, timeToWait);
    }

    /**
     * Click element with locator and no time to wait
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param info - Information about element
     * @return
     */
    public void elementClick(String locator, String info) {
        WebElement element = getElement(locator, info);
        elementClick(element, info, 0);
    }

    /***
     * Click element using JavaScript
     * @param element - WebElement to perform Click operation
     * @param info - Information about element
     */
    public void javascriptClick(WebElement element, String info) {
        try {
            js.executeScript("arguments[0].click();", element);
            log.info("Clicked on :: " + info);
        } catch (Exception e) {
            log.error("Cannot click on :: " + info);
        }
    }

    /***
     * Click element using JavaScript
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param info - Information about element
     */
    public void javascriptClick(String locator, String info) {
        WebElement element = getElement(locator, info);
        try {
            js.executeScript("arguments[0].click();", element);
            log.info("Clicked on :: " + info);
        } catch (Exception e) {
            log.error("Cannot click on :: " + info);
        }
    }

    /***
     * Click element when element is clickable
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param timeout - Duration to try before timeout
     */
    public void clickWhenReady(By locator, int timeout) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            WebElement element = null;
            log.info("Waiting for max:: " + timeout + " seconds for element to be clickable");

            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(Constants.EXPLICIT_WAIT));
            element = wait.until(
                    ExpectedConditions.elementToBeClickable(locator));
            element.click();
            log.info("Element clicked on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        } catch (Exception e) {
            log.error("Element not appeared on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        }
    }

    /***
     * Send Keys to element
     * @param element - WebElement to send data
     * @param data - Data to send
     * @param info - Information about element
     * @param clear - True if you want to clear the field before sending data
     */
    public void sendData(WebElement element, String data, String info, Boolean clear) {
        try {
            if (clear) {
                element.clear();
            }
            //Util.sleep(1000, "Waiting Before Entering Data");
            element.sendKeys(data);
            log.info("Send Keys on element :: " + info + " with data :: " + data);
        } catch (Exception e) {
            log.error("Cannot send keys on element :: " + info + " with data :: " + data);
        }
    }

    /***
     * Send Keys to element with locator
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param data - Data to send
     * @param info - Information about element
     * @param clear - True if you want to clear the field before sending data
     */
    public void sendData(String locator, String data, String info, Boolean clear) {
        WebElement element = this.getElement(locator, info);
        sendData(element, data, info, clear);
    }

    /***
     * Send Keys to element with clear
     * @param element - WebElement to send data
     * @param data - Data to send
     * @param info - Information about element
     */
    public void sendData(WebElement element, String data, String info) {
        sendData(element, data, info, true);
    }

    /***
     * Send Keys to element with locator and clear
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param data - Data to send
     * @param info - Information about element
     */
    public void sendData(String locator, String data, String info) {
        WebElement element = getElement(locator, info);
        sendData(element, data, info, true);
    }

    /**
     * Get text of a web element
     *
     * @param element - WebElement to perform click action
     * @param info    - Information about element
     */
    public String getText(WebElement element, String info) {
        System.out.println("Getting Text on element :: " + info);
        String text = null;
        text = element.getText();
        if (text.length() == 0) {
            text = element.getAttribute("innerText");
        }
        if (!text.isEmpty()) {
            log.info("The text is : " + text);
        } else {
            log.error("Text Not Found");
        }
        return text.trim();
    }

    /**
     * Get text of a web element with locator
     * @param locator
     * @param info
     * @return
     */
    public String getText(String locator, String info) {
        WebElement element = this.getElement(locator, info);
        return this.getText(element, info);
    }

    /**
     * Check if element is enabled
     * @param element
     * @param info
     * @return
     */
    public Boolean isEnabled(WebElement element, String info) {
        Boolean enabled = false;
        if (element != null) {
            enabled = element.isEnabled();
            if (enabled)
                log.info("Element :: " + info + " is Enabled");
            else
                log.info("Element :: " + info + " is Disabled");
        }
        return enabled;
    }

    /***
     * Check if element is enabled with locator
     * @param locator
     * @param info
     * @return
     */
    public Boolean isEnabled(String locator, String info) {
        WebElement element = getElement(locator, info);
        return this.isEnabled(element, info);
    }

    /**
     * Check if element is displayed
     * @param element
     * @param info
     * @return
     */
    public Boolean isDisplayed(WebElement element, String info) {
        Boolean displayed = false;
        if (element != null) {
            displayed = element.isDisplayed();
            if (displayed)
                log.info("Element :: " + info + " is displayed");
            else
                log.info("Element :: " + info + " is NOT displayed");
        }
        return displayed;
    }

    /***
     * Check if element is displayed with locator
     * @param locator
     * @param info
     * @return
     */
    public Boolean isDisplayed(String locator, String info) {
        WebElement element = getElement(locator, info);
        return this.isDisplayed(element, info);
    }

    /**
     * @param element
     * @param info
     * @return
     */
    public Boolean isSelected(WebElement element, String info) {
        Boolean selected = false;
        if (element != null) {
            selected = element.isSelected();
            if (selected)
                log.info("Element :: " + info + " is selected");
            else
                log.info("Element :: " + info + " is already selected");
        }
        return selected;
    }

    /**
     * @param locator
     * @param info
     * @return
     */
    public Boolean isSelected(String locator, String info) {
        WebElement element = getElement(locator, info);
        return isSelected(element, info);
    }

    /**
     * Selects a check box irrespective of its state
     *
     * @param element
     * @param info
     */
    public void Check(WebElement element, String info) {
        if (!isSelected(element, info)) {
            elementClick(element, info);
            log.info("Element :: " + info + " is checked");
        } else
            log.info("Element :: " + info + " is already checked");
    }

    /**
     * Selects a check box irrespective of its state, using locator
     *
     * @param locator
     * @param info
     * @return
     */
    public void Check(String locator, String info) {
        WebElement element = getElement(locator, info);
        Check(element, info);
    }

    /**
     * UnSelects a check box irrespective of its state
     *
     * @param element
     * @param info
     * @return
     */
    public void UnCheck(WebElement element, String info) {
        if (isSelected(element, info)) {
            elementClick(element, info);
            log.info("Element :: " + info + " is unchecked");
        } else
            log.info("Element :: " + info + " is already unchecked");
    }

    /**
     * UnSelects a check box irrespective of its state, using locator
     *
     * @param locator
     * @param info
     * @return
     */
    public void UnCheck(String locator, String info) {
        WebElement element = getElement(locator, info);
        UnCheck(element, info);
    }

    /**
     * @param element
     * @param info
     * @return
     */
    public Boolean Submit(WebElement element, String info) {
        if (element != null) {
            element.submit();
            log.info("Element :: " + info + " is submitted");
            return true;
        } else
            return false;
    }

    /**
     * @param locator
     * @param attribute
     * @return
     */
    public String getElementAttributeValue(String locator, String attribute) {
        WebElement element = getElement(locator, "info");
        return element.getAttribute(attribute);
    }

    /**
     * @param element
     * @param attribute
     * @return
     */
    public String getElementAttributeValue(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    /**
     * @param locator
     * @param timeout
     * @return
     */
    public WebElement waitForElement(String locator, long timeout) {
        By byType = getByType(locator);
        WebElement element = null;
        try {
        	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            log.info("Waiting for max:: " + timeout + " seconds for element to be available");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(byType));
            log.info("Element appeared on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        } catch (Exception e) {
            log.error("Element not appeared on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        }
        return element;
    }

    /***
     * Wait for element to be clickable
     * @param locator - locator strategy, id=>example, name=>example, css=>#example,
     *      *                tag=>example, xpath=>//example, link=>example
     * @param timeout - Duration to try before timeout
     */
    public WebElement waitForElementToBeClickable(String locator, int timeout) {
        By byType = getByType(locator);
        WebElement element = null;
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            log.info("Waiting for max:: " + timeout + " seconds for element to be clickable");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
            element = wait.until(
                    ExpectedConditions.elementToBeClickable(byType));
            log.info("Element is clickable on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        } catch (Exception e) {
            log.error("Element not appeared on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        }
        return element;
    }

    /**
     *
     */
    public boolean waitForLoading(String locator, long timeout) {
        By byType = getByType(locator);
        boolean elementInvisible = false;
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            log.info("Waiting for max:: " + timeout + " seconds for element to be available");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            elementInvisible = wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(byType));
            log.info("Element appeared on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        } catch (Exception e) {
            log.error("Element not appeared on the web page");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        }
        return elementInvisible;
    }

    /**
     * Mouse Hovers to an element
     *
     * @param locator
     */
    public void mouseHover(String locator, String info) {
        WebElement element = getElement(locator, info);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
        //Util.sleep(5000);
    }

    /**
     * @param element
     * @param optionToSelect
     */
    public void selectOption(WebElement element, String optionToSelect) {
        Select sel = new Select(element);
        sel.selectByVisibleText(optionToSelect);
        log.info("Selected option : " + optionToSelect);
    }

    /**
     * Selects a given option in list box
     *
     * @param locator
     * @param optionToSelect
     */
    public void selectOption(String locator, String optionToSelect, String info) {
        WebElement element = getElement(locator, info);
        this.selectOption(element, optionToSelect);
    }

    /**
     * get Selected drop down value
     *
     * @param element
     * @return
     */
    public String getSelectDropDownValue(WebElement element) {
        Select sel = new Select(element);
        return sel.getFirstSelectedOption().getText();
    }

    /**
     * @param element
     * @param optionToVerify
     */
    public boolean isOptionExists(WebElement element, String optionToVerify) {
        Select sel = new Select(element);
        boolean exists = false;
        List<WebElement> optList = sel.getOptions();
        for (int i = 0; i < optList.size(); i++) {
            String text = getText(optList.get(i), "Option Text");
            if (text.matches(optionToVerify)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            log.info("Selected Option : " + optionToVerify + " exist");
        } else {
            log.info("Selected Option : " + optionToVerify + " does not exist");
        }
        return exists;
    }

    /***
     *
     * @param methodName
     * @param browserName
     * @return
     */
    public String takeScreenshot(String methodName, String browserName) {
        String fileName = TestUtil.getScreenshotName(methodName, browserName);
        String screenshotDir = System.getProperty("user.dir") + "//" + "test-output/screenshots";
        new File(screenshotDir).mkdirs();
        String path = screenshotDir + "//" + fileName;

        try {
            File screenshot = ((TakesScreenshot)driver).
                    getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(path));
            log.info("Screen Shot Was Stored at: "+ path);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public void DoubleClick(WebElement element, String info) {
        Actions action = new Actions(driver);
        action.doubleClick(element);
        log.info("Double Clicked on :: " + info);
        action.perform();
    }

    /**
     * Right Click a WebElement
     *
     * @param locator
     */
    public void rightClick(String locator, String info) {
        WebElement element = getElement(locator, info);
        Actions action = new Actions(driver);
        action.contextClick(element).build().perform();
        log.info("Double Clicked on :: " + info);
    }

    /**
     * Right click a WebElement and select the option
     *
     * @param elementLocator
     * @param itemLocator
     */
    public void selectItemRightClick(String elementLocator, String itemLocator) {
        WebElement element = getElement(elementLocator, "info");
        Actions action = new Actions(driver);
        action.contextClick(element).build().perform();
        WebElement itemElement = getElement(itemLocator, "info");
        elementClick(itemElement, "Selected Item");
    }

    /**
     * @param key
     */
    public void keyPress(Keys key, String info) {
        Actions action = new Actions(driver);
        action.keyDown(key).build().perform();
        log.info("Key Pressed :: " + info);
    }
    
    /**
     * Switch to iframe with name or id
     *
     * @param frameNameId - Name or Id of the iframe
     */
    public void switchFrame(String frameNameId) {
        try {
            driver.switchTo().frame(frameNameId);
            log.info("Switched to iframe");
        } catch (Exception e) {
            log.error("Cannot switch to iframe");
        }
    }

    /**
     * Switch to iframe with name or id and find element
     *
     * @param frameNameId - Name or Id of the iframe
     * @param locator - Locator of element
     * @param info    - Info about the element
     */
    public WebElement findElementInFrame(String frameNameId, String locator, String info) {
        WebElement element = null;
        try {
            driver.switchTo().frame(frameNameId);
            log.info("Switched to iframe");
            element = getElement(locator, info);
        } catch (Exception e) {
            log.error("Cannot switch to iframe");
        }
        return element;
    }
    
    
    
	public void click(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.debug("click on the given element");
		element.click();

	}

	public void clickByActions(WebElement element) {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.debug("click an element by Actions class click()");
		act.moveToElement(element).click().build().perform();
	}

	public void clickUsingJsExecutor(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		log.debug("click an element using javascript click");
		executor.executeScript("arguments[0].click();", element);
	}

	public boolean isClickable(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		return element.isDisplayed();
	}

	public String getText(WebElement element) throws InterruptedException {
		log.debug("wait for the visibility of an element");
		wait.until(ExpectedConditions.visibilityOf(element));
		log.debug("fetch the text for an element");
		return element.getText();
	}

	public void clearText(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.debug("clear the content in given editbox element");
		element.clear();

	}

	public void clearTextWithBackSapce(WebElement element) throws InterruptedException {
		log.debug("wait for the element to be clickable or not");
		wait.until(ExpectedConditions.elementToBeClickable(element));
		while (!element.getAttribute("value").toString().contentEquals("")) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	public boolean isCkeckedElement(WebElement element, String attribute) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		try {
			return element.getAttribute(attribute).toString().contentEquals("true");
		} catch (NullPointerException e) {
			return false;
		}
	}

	boolean isEnableElement(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.isEnabled();
	}

	public void isVisible(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	boolean isDisplayedElement(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.isDisplayed();
	}

	boolean isPresentElement(WebElement element) {

		try {
			return element.isDisplayed();
			// return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	boolean selectOptionCarousel(List<WebElement> element, String option, WebElement nextBtn)
			throws InterruptedException {

		for (WebElement webElement : element) {
			if (webElement.getText().equalsIgnoreCase("")) {
				while (!webElement.isDisplayed()) {
					click(nextBtn);
				}
				if (webElement.getText().equalsIgnoreCase(option)) {
					// click(webElement);
					return true;
				}
			}

		}
		return false;
	}

	boolean isNotDisplayedElement(WebElement element) throws InterruptedException {
		try {
			return !element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	boolean selectOptionList(List<WebElement> element, String option) throws InterruptedException {
		for (WebElement webElement : element) {
			log.debug("header name is:" + webElement.getText());
			if (webElement.getText().equalsIgnoreCase(option)) {
				click(webElement);
				return true;

			}
		}

		return false;
	}

	boolean selectOption(List<WebElement> element, String option) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
		for (WebElement webElement : element) {
			if (webElement.getAttribute("value").equalsIgnoreCase(option)) {
				click(webElement);
				return true;
			}
		}
		return false;
	}

	void selectOption(Select selector, String value) {
		selector.selectByValue(value);
	}

	boolean isDisableElement(WebElement element) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		return !element.isEnabled();
	}

	public void addNewTab() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open('');");
		Thread.sleep(5000);
	}

	void assertText(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertEquals(element.getText().toLowerCase(), text.toLowerCase(),
				"The text " + text + " is not equals to " + element.getText());

	}

	void assertContainsText(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertTrue(element.getText().toString().contains(text),
				"The text " + text + " is not equals to " + element.getText().toString());
	}

	void assertTextContainsElementText(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertTrue(text.contains(element.getText().toString()),
				"The text " + text + " is not equals to " + element.getText().toString());
	}

	void assertTextIsNotEquals(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element));
		Assert.assertFalse(element.getText().equals(text), "The text " + text + " is equals to " + element.getText());
	}

	void assertSubstring(WebElement element, String text) throws InterruptedException {

		Assert.assertTrue(element.getText().contains(text),
				"The text " + element.getText() + "doesn't contain the string " + text);
	}

	 protected void type(WebElement element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.clear();
		element.sendKeys(text);

	}

	boolean selectByText(List<WebElement> elements, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		for (WebElement webElement : elements) {
			if (webElement.getText().equalsIgnoreCase(text)) {
				click(webElement);
				return true;
			}
		}
		return false;
	}

	void assertNumbersNotEquals(int actual, int expected) {

		Assert.assertNotEquals(actual, expected, "The values [" + actual + "] and [" + expected + "] are equals.");
	}

	int getSize(List<WebElement> elements) throws InterruptedException {
		if (!elements.isEmpty()) {
			wait.until(ExpectedConditions.visibilityOf(elements.get(0)));
			return elements.size();
		}
		return 0;
	}

	void assertTextIgnoreCase(WebElement element, String text) throws InterruptedException {
		Assert.assertTrue(element.getText().equalsIgnoreCase(text),
				"The values [" + element.getText() + "] and [" + text + "] are not equals.");
	}

	boolean assertTextNotPresentInList(List<WebElement> element, String text) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(element.get(0)));
		for (WebElement webElement : element) {
			if (webElement.getText().equalsIgnoreCase(text)) {
				return false;
			}
		}
		return true;
	}

	boolean selectOptionListByAttribute(List<WebElement> element, String option, String attrib)
			throws InterruptedException {

		for (WebElement webElement : element) {
			if (webElement.getAttribute(attrib).equalsIgnoreCase(option)) {
				click(webElement);
				return true;
			}
		}
		return false;
	}

	boolean selectOptionCarouselByAttribute(List<WebElement> element, String option, WebElement nextBtn, String attrib)
			throws InterruptedException {

		for (WebElement webElement : element) {
			if (webElement.getAttribute(attrib).equalsIgnoreCase(option)) {
				while (!webElement.isDisplayed()) {
					click(nextBtn);
					Thread.sleep(1000);
				}
				if (webElement.getAttribute(attrib).equalsIgnoreCase(option)) {
					click(webElement);
					return true;
				}
			}
		}
		return false;
	}

	boolean isAttributePresent(WebElement element, String attribute) {

		Boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
		}

		return result;
	}

	public void selectOptionByLabelText(WebElement element, String text) {
		Select sel = new Select(element);
		wait.until(ExpectedConditions.visibilityOf(element));
		sel.selectByVisibleText(text);
	}

	public void selectOptionByAttribute(WebElement element, String attriVal) {
		Select sel = new Select(element);
		sel.selectByValue(attriVal);
	}

	public void selectOptionByIndex(WebElement element, int index) {
		Select sel = new Select(element);
		sel.selectByIndex(index);
	}

	public boolean isVisibleElement(WebElement element) throws InterruptedException {

		return element.isDisplayed();
		// return (element.getAttribute("visible").toString().equalsIgnoreCase("true"))
		// ? true : false;
	}

	public boolean isChecked(WebElement element) {
		return element.isSelected();
	}

	public boolean isUnChecked(WebElement element) {
		return !(element.isSelected());
	}

	public void flash(WebElement element) {

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 8; i++) {
			changeColor("rgb(0,200,0)", element);// 1
			changeColor(bgcolor, element);// 2
		}
	}

	public void scrollForElement(WebElement element) {
		JavascriptExecutor jsx = (JavascriptExecutor) driver;
		jsx.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {

			return false;

		}

	}

	public static String getAlphaNumericString(int n) {
		// length is bounded by 256 Character
		byte[] array = new byte[256];
		new Random().nextBytes(array);

		String randomString = new String(array, Charset.forName("UTF-8"));

		// Create a StringBuffer to store the result
		StringBuffer r = new StringBuffer();

		// Append first 20 alphanumeric characters
		// from the generated random String into the result
		for (int k = 0; k < randomString.length(); k++) {

			char ch = randomString.charAt(k);

			if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {

				r.append(ch);
				n--;
			}
		}

		// return the resultant string
		return r.toString();
	}

	public WebElement waitUntilClickable(WebDriver driver, By by) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
		return element;
	}

	public void click(WebDriver driver, WebElement element) {
		wait.until(ExpectedConditions.visibilityOfAllElements(Arrays.asList(element)));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		/*
		 * log.debug("press RETURN key"); element.sendKeys(Keys.RETURN);
		 */
		log.debug("pess ENTER key");
		element.sendKeys(Keys.ENTER);
	}

	public void clickByActions(WebDriver driver, WebElement element) {
		wait.until(ExpectedConditions.visibilityOfAllElements(Arrays.asList(element)));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Actions act = new Actions(driver);
		act.moveToElement(element).click(element).build().perform();
	}

	public void clickByPosition(WebDriver driver, WebElement element) {

		Point p = element.getLocation();

		Actions act = new Actions(driver);

		act.moveToElement(element).moveByOffset(p.x, p.y).click().perform();
	}

	public WebElement waitForVisibility(WebDriver driver, By by) {
		return waitForVisibility(driver, by, 45);
	}

	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement toReturn = driver.findElement(locator);
				if (toReturn.isDisplayed()) {
					return toReturn;
				}
				return null;
			}
		};
	}

	public WebElement waitForVisibility(WebDriver driver, By by, int waitTime) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		WebElement divElement = wait.until(visibilityOfElementLocated(by));
		return divElement;
	}

	public static String dateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		String dateist = sdf.format(new Date(0, 0, 0));
		// dateist=dateist.replace(" ", "-").replace(":", "-");
		return dateist;
	}

	public static String getCurrentDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("Today Int: " + todayInt + "\n");

		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		System.out.println("Today Str: " + todayStr + "\n");
		return todayStr;
	}

	public boolean isTextPresent(String str) {
		WebElement bodyElement = driver.findElement(By.tagName("body"));
		return bodyElement.getText().contains(str);
	}

	public void switchWindow(int windowCount) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(windowCount));

	}
}
