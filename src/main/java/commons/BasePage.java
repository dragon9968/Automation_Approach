package commons;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import javax.management.RuntimeErrorException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BasePage {
	
	public static BasePage getBasePage() {
		return new BasePage();
		
	}

	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backtoPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardtoPage(WebDriver driver, String pageUrl) {
		driver.navigate().forward();
	}
	
	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public void setCookies(WebDriver driver,Set<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
		sleepInSecond(3);
	}
	
	public Set<Cookie> getAllCookies(WebDriver driver) {
		return driver.manage().getCookies();
	}

	public Alert waitForAlertPresence(WebDriver driver) {
		//WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT); Use for Selenium 3 only, Selenium 4 use Duratio.of Second now
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptToAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
		// driver.switchTo().alert().accept();
	}

	public void cancelToAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}

	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}

	public void sendKeyToAlert(WebDriver driver, String textValue) {
		waitForAlertPresence(driver).sendKeys(textValue);
	}

	public void switchToWindowByID(WebDriver driver, String windowID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			if (!id.equals(windowID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}
	
	public void switchToWindowByTitle(WebDriver driver, String tabTitle) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if (actualTitle.equals(tabTitle)) {
				break;
			}
		}
	}
	
	public void closeAllTabWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
			driver.switchTo().window(parentID);
		}
	}
	
	/*
	 * private By getByXpath(String locatorType) 
	 * { return By.xpath(locatorType); }
	 */
	
	// locatorType: id=/ css=/ name=/ xpath=/ class=
	public By getByLocator(String locatorType) {
		By by ;
		if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=") ) {
			by = By.id(locatorType.substring(3));
			
		} else if (locatorType.startsWith("class=") || locatorType.startsWith("CLASS=") || locatorType.startsWith("Class=")) {
			by = By.className(locatorType.substring(6));
			
		} else if (locatorType.startsWith("name=") || locatorType.startsWith("Name=") || locatorType.startsWith("NAME=")) {
			by = By.name(locatorType.substring(5));
			
		} else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
			by = By.cssSelector(locatorType.substring(4));
			
		} else if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("XPath=")) {
			by = By.xpath(locatorType.substring(6));
		}
		else {
			throw new RuntimeException("Locator Type is not supported");
		}
		
		return by;
	}
	
	
	public String getDynamicXpath(String locatorType, String... dynamicValues) {
		if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("XPath="))
		{
			locatorType = String.format(locatorType, (Object[])dynamicValues);
		}
		return locatorType;
		
	}
	
	
	public WebElement getWebElement(WebDriver driver, String locatorType) {
		return driver.findElement(getByLocator(locatorType));
	}
	
	public List<WebElement> getListWebElement(WebDriver driver, String locatorType) {
		return driver.findElements(getByLocator(locatorType));
	}
	
	public void clickToElement(WebDriver driver, String locatorType) {
		if(driver.toString().contains("InternetExplorerDriver"))
		{
			clickToElementByJS(driver, locatorType);
			sleepInSecond(SHORT_TIMEOUT);
		}
		else {
			this.getWebElement(driver,locatorType).click();

		}
		
	}
	
	public void clickToElement(WebDriver driver, String locatorType , String... dynamicValues) {
		if(driver.toString().contains("InternetExplorerDriver"))
		{
			clickToElementByJS(driver, locatorType);
			sleepInSecond(SHORT_TIMEOUT);
		}
		else {
			this.getWebElement(driver,getDynamicXpath(locatorType, dynamicValues)).click();

		}
		
	}
	
	public void sendkeyToElement(WebDriver driver, String locatorType, String textValue) {
		WebElement element = getWebElement(driver,locatorType);
		element.clear();
		element.sendKeys(textValue);	
	}
	
	public void sendkeyToElement(WebDriver driver, String locatorType, String valueToSendKey, String... dynamicValues) {
		WebElement element = getWebElement(driver,getDynamicXpath(locatorType, dynamicValues));
		element.clear();
		element.sendKeys(valueToSendKey);	
	}
	
	public String getElementText(WebDriver driver, String locatorType) {
		return getWebElement(driver,locatorType).getText();
		
	}
	
	public String getElementText(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver,getDynamicXpath(locatorType, dynamicValues)).getText();

	}
	
	public void selectItemInDefaultDropDown(WebDriver driver, String locatorType, String textItem) {
		Select select = new Select(getWebElement(driver,locatorType));
		select.selectByVisibleText(textItem);
	}
	
	public void selectItemInDefaultDropDown(WebDriver driver, String locatorType, String textItem, String... dynamicValues) {
		Select select = new Select(getWebElement(driver,getDynamicXpath(locatorType, dynamicValues)));
		select.selectByVisibleText(textItem);
	}
	
	public String getSelectItemDefaultDropDown(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver,locatorType));
		return select.getFirstSelectedOption().getText();
	}
	
	public String getSelectItemDefaultDropDown(WebDriver driver, String locatorType , String... dynamicValues) {
		Select select = new Select(getWebElement(driver,getDynamicXpath(locatorType, dynamicValues)));
		return select.getFirstSelectedOption().getText();
	}
	
	public boolean isDropDownMultiple(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver,locatorType));
		return select.isMultiple();
	}
	
	
	public void selectItemInCustomDropDown(WebDriver driver , String parentXpath , String childXpath , String expectedTextItem){
		getWebElement(driver,parentXpath).click();
		sleepInSecond(1);
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));

		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childXpath)));  
		List<WebElement> alldropdownItems = driver.findElements(getByLocator(childXpath));
		for (WebElement item : alldropdownItems) {
			if(item.getText().trim().equals(expectedTextItem)) 
			{
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);
				item.click();
				break;
			}
		}
	}
	
	public void selectItemInCustomDropDown(WebDriver driver , String parentXpath , String childXpath , String expectedTextItem,String... dynamicValues ){
		getWebElement(driver,getDynamicXpath(expectedTextItem, dynamicValues)).click();
        sleepInSecond(1);
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
    	JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    	
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(getDynamicXpath(expectedTextItem, dynamicValues))));  
        List<WebElement> alldropdownItems = driver.findElements(getByLocator(getDynamicXpath(expectedTextItem, dynamicValues)));
        for (WebElement item : alldropdownItems) {
        	if(item.getText().trim().equals(expectedTextItem)) 
        	{
        	   jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
               sleepInSecond(1);
        	   item.click();
        	   break;
        	}
		}
	}
	
	public String getElementAttributeValue(WebDriver driver, String locatorType , String attributeName) {
		return getWebElement(driver, locatorType).getAttribute(attributeName);
	}
	
	public String getElementAttributeValue(WebDriver driver, String locatorType , String attributeName, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeName);
	}
	
	public String getElementCssValue(WebDriver driver, String locatorType , String cssValueName) {
		return getWebElement(driver, locatorType).getCssValue(cssValueName);
	}
	
	public String getElementCssValue(WebDriver driver, String locatorType , String cssValueName, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getCssValue(cssValueName);
	}
	
	public String getHexaColorFromRGBA(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
		
	}
	
	public int getElementSize(WebDriver driver, String locatorType) {
		return getListWebElement(driver, locatorType).size();
	}
	
	public int getElementSize(WebDriver driver, String locatorType, String... dynamicValues) {
		return getListWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).size();
	}
	
	
	public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if(!element.isSelected()) {
			element.click();
		}
	}
	
	public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if(!element.isSelected()) {
			element.click();
		}
	}
	
	public void uncheckToDefaultCheckboxRadio(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if(element.isSelected()) {
			element.click();
		}
	}
	
	public void uncheckToDefaultCheckboxRadio(WebDriver driver, String locatorType ,String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if(element.isSelected()) {
			element.click();
		}
	}
	
	
	public void overrideImplicitTimeout(WebDriver driver , long timeOut) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
	}
	
	
	
	public boolean isElementDisplayed(WebDriver driver, String locatorType) {
			return getWebElement(driver, locatorType).isDisplayed();

	}
	
	public boolean isElementUndisplayed(WebDriver driver, String locatorType) {
		System.out.println("Start Time = " + new Date().toString());
		overrideImplicitTimeout(driver,SHORT_TIMEOUT);
		
		List<WebElement> elements = getListWebElement(driver, locatorType);
		overrideImplicitTimeout(driver,LONG_TIMEOUT);

		if (elements.size() == 0) {
			System.out.println("Case 3 - Element ko co trong DOM");
			System.out.println("End Time = " + new Date().toString());
			return true;

		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			System.out.println("Case 2 - Element co trong DOM nhung ko hien thi tren UI");
			System.out.println("End Time = " + new Date());
			return true;
		}
		else {
			System.out.println("Case 1 - Element co trong DOM va hien thi tren UI");
			return false;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	public boolean isElementDisplayed(WebDriver driver, String locatorType ,String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
	}
	
	
	public boolean isElementEnabled(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isEnabled();
	}
	
	
	public void switchToFrameIframe(WebDriver driver, String locatorType) {
		driver.switchTo().frame(getWebElement(driver, locatorType));
	}
	
 	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
 	public void hoverMouseToElement(WebDriver driver, String locatorType) {
 		Actions action =  new Actions(driver);
 		action.moveToElement(getWebElement(driver, locatorType)).perform();
 	}
 	
 	public void doubleClickToElement(WebDriver driver, String locatorType) {
 		Actions action =  new Actions(driver);
 		action.doubleClick(getWebElement(driver, locatorType)).perform();
 	}
 	
 	public void pressKeyToElement(WebDriver driver, String locatorType, Keys key) {
 		Actions action =  new Actions(driver);
 		action.sendKeys(getWebElement(driver, locatorType),key).perform();
 	}
 	public void pressKeyToElement_Action(WebDriver driver, String locatorType, Keys key,String... dynamicValues) {
 		Actions action =  new Actions(driver);
 		action.sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)),key).perform();
 	}
 	
	public void pressKeyToElement(WebDriver driver, String locatorType, Keys key,String... dynamicValues) {
		WebElement element = getWebElement(driver,getDynamicXpath(locatorType, dynamicValues));
		element.sendKeys(key);
	}
	
	

	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	

	public void highlightElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, locatorType);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locatorType));
	}

	public void scrollToElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
	}



	public void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, locatorType));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(driver, locatorType));
	}

	public boolean isImageLoaded(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getWebElement(driver, locatorType));
		if (status) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public void waitForElementVisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
	}
	
	public void waitForElementVisible(WebDriver driver, String locatorType,String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	public void waitForAllElementVisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
	}
	
	public void waitForAllElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	public void waitForElementInvisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
	}
	
   // Wait for Element undisplayed in DOM or not in DOM and override implicit timeout
	public void waitForElementUndisplayed(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		overrideImplicitTimeout(driver, SHORT_TIMEOUT);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
		overrideImplicitTimeout(driver, LONG_TIMEOUT);
	}
	
	
	public void waitForElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	public void waitForAllElementInvisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver,locatorType)));
	}
	
	public void waitForAllElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver,getDynamicXpath(locatorType, dynamicValues))));
	}
	
	public void waitForElementClickable(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
	}
	
	public void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(LONG_TIMEOUT));
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	private long LONG_TIMEOUT = GlobalConstants.LONG_TIMEOUT;
	private long SHORT_TIMEOUT = GlobalConstants.SHORT_TIMEOUT;
}

