package utils;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ElementRepository.RepoEucr;
import utils.ProjectDataVariables;
import clima.start.Setuper;
import eu.ets.eucr.accmgmt.AM_AccountOperations;
import eu.ets.eucr.login.LoginToEUCR;
import eu.ets.eucr.menunavigation.EUCR_MenuNavigation;
import eu.ets.eucr.transactions.TransactionPage;
import eu.ets.eucr.workflow.ApprovingRequests;
import eu.ets.sql.AppSqlQueries;
import po.SeleniumDriver;

public class ApplicationPageUtils {
    public static String loginPairNa1Na2					= "NA1:NA2";
    public static String loginPairAr1Aar2					= "AR1:AAR2";
    private static int screenShotCounter = 1;
	
	public static String GET_PANEL_BOX(String HeaderTitle, String ReturnedPiece /*[panel|header|content]*/){
		String UI_PANEL = "//div[contains(concat(' ',@class,' '),'ui-panel')]/div[contains(concat(' ',@class,' '),'ui-panel-titlebar')]/span[@class='ui-panel-title' and contains(normalize-space(),'"+HeaderTitle+"')]/../..";
		String UI_HEADER 	= UI_PANEL + "//div[contains(@id,'_header')]";
		String UI_CONTENT 	= UI_PANEL + "//div[contains(@id,'_content')]";
		switch (ReturnedPiece) {
		case "panel"	:  	return UI_PANEL;
		case "header"	:  	return UI_HEADER;
		case "content"	:  	return UI_CONTENT;
		}
		return null;
	}
	
	public static String GET_PANEL_BOX_TITLE(WebDriver driver){
		String UI_PANEL_TITLE_BAR = "//div[contains(concat(' ',@class,' '),'ui-panel')]/div[contains(concat(' ',@class,' '),'ui-panel-titlebar')]/span[@class='ui-panel-title']";
		By by = By.xpath(UI_PANEL_TITLE_BAR);
		if(ax.isDisplayedBy(driver, by)){
			return ax.getText(driver, by).trim();
		}
		return null;
	}	
	
	public static String GET_POPUP_BOX(String PopupHeaderTitle, String ReturnedPiece /*[panel|header|dialog]*/){
		String UI_DIALOG = "//div[contains(concat(' ',@class,' '),'ui-dialog')]/div[contains(concat(' ',@class,' '),'ui-dialog-titlebar')]/span[@class='ui-dialog-title' and contains(normalize-space(),'"+PopupHeaderTitle+"')]/../..";
		String UI_DIALOG_HEADER 	= UI_DIALOG + "//div[contains(concat(' ',@class,' '),'ui-dialog-titlebar')]";
		String UI_DIALOG_CONTENT 	= UI_DIALOG + "//div[contains(concat(' ',@class,' '),'ui-dialog-content')]";
		switch (ReturnedPiece) {
		case "dialog"	:  	return UI_DIALOG;
		case "header"	:  	return UI_DIALOG_HEADER;
		case "content"	:  	return UI_DIALOG_CONTENT;
		}
		return null;
	}
	
	public static String GET_FIELDSET_BOX(String PathToElement, String FieldsetHeaderTitle, String ReturnedPiece /*[fieldset|content]*/){
		String UI_FIELDSET 			= PathToElement 	+ "//fieldset[contains(concat(' ',@class,' '),'ui-fieldset')]/legend[contains(concat(' ',@class,' '),'ui-fieldset-legend') and contains(normalize-space(),'"+FieldsetHeaderTitle+"')]/..";
		String UI_FIELDSET_CONTENT 	= UI_FIELDSET 		+ "//div[contains(concat(' ',@class,' '),'ui-fieldset-content')]";
		switch (ReturnedPiece) {
		case "fieldset"	:  	return UI_FIELDSET;
		case "content"	:  	return UI_FIELDSET_CONTENT;
		}
		return null;
	}
	public static By GET_FIELDSET_ITEM_VALUE(String PathToFieldsetElement, String FieldSetLabel, String DL_Item){
		String UI_FIELDSET_DL 				= PathToFieldsetElement 	+ "//fieldset[contains(concat(' ',@class,' '),'ui-fieldset')]/legend[contains(normalize-space(),'"+FieldSetLabel+"')]/../div[contains(concat(' ',@class,' '),'ui-fieldset-content')]/dl[@class='detailsList']";
		String UI_FIELDSET_CONTENT_ITEM 	= UI_FIELDSET_DL			+ "//dt[normalize-space()='"+DL_Item+"']/following-sibling::dd[1]";
		return By.xpath(UI_FIELDSET_CONTENT_ITEM);
	}	
	
	public static String GET_BUTTON_STRING(String buttonID_part, String buttonTitle){
		return  "//button[contains(concat(' ',@class,' '),'ui-button') and contains(@id,'"+buttonID_part+"') ]/span[@class='ui-button-text' and contains(normalize-space(),'"+buttonTitle+"')]/..";		
	}
	
	public static By BUTTON_ON_BUTTONCONTAINER(String PathToButtonContainerElement, String ButtonText){
		return By.xpath( PathToButtonContainerElement+  "//div[@class='buttonContainer']//button[contains(concat(' ',@class,' '),'ui-button')]/span[@class='ui-button-text' and normalize-space()='"+ButtonText+"']/.." );
	}
	public static By BUTTON_ON_PARENTCONTAINER(String PathToButtonContainerElement, String buttonIdContains, String ButtonText){
		return By.xpath( PathToButtonContainerElement+  "//button[contains(@id,'"+buttonIdContains+"') and contains(concat(' ',@class,' '),'ui-button')]/span[@class='ui-button-text' and normalize-space()='"+ButtonText+"']/.." );
	}
	
	public static By GET_BY_ELEMENT_ON_PANEL(String PANEL_PATH, String tagName, String ID_ofElement){
		return By.xpath(PANEL_PATH + "//"+tagName+"[@id='"+ID_ofElement+"']");
	}
	public static By GET_BY_OF_BUTTON_ELEMENT_ON_PANEL(String PANEL_PATH, String textOnButton){
		return By.xpath(PANEL_PATH + "//button[contains(concat(' ',@class,' '),'ui-button')]/span[@class='ui-button-text' and contains(normalize-space(),'"+textOnButton+"')]/..");
	}
	public static String GET_CURRENT_USERNAME(WebDriver driver){ // THIS IS USERNAME ON TOP BAR, next to "Logged in as {username}"
		return PageCanvas.getApplicationLoggedIn_Username(driver);
	}
	public static String GET_CURRENT_USER_NAMES(WebDriver driver){ // THIS IS FIRST AND LAST NAME !!!
		return PageCanvas.getAuthGrid_NAME(driver);
	}
	public static String GET_CURRENT_USER_URID(WebDriver driver){
		return PageCanvas.getAuthGrid_URID(driver);
	}
	
	public static String GRAB_REQUEST_NR_FROM_GREEN_BOX(WebDriver driver, String TEXT_BEFORE_NUMBER){
		return VerifyPresenceOfGreenMonit_AndGetRequestNumber_Multiwords(driver, TEXT_BEFORE_NUMBER);
	}
	
	// POM common elements
	public static By PaginatorCurrentBy = By.id("yui-pg0-0-page-report");
	
	
	public static String GET_FULL_ACCOUNT_NUMBER(WebDriver driver, String AccountIdentifier){
		if(AccountIdentifier.contains("-")){return AccountIdentifier;}
		
		
		// we check project configuration for using sql queries
		if(ax.isSQLEnabled()){
			return AppSqlQueries.get_FULL_ACCOUNT_NUMBER_ByAccountIdentifier(AccountIdentifier);
		}else{
			if(!(Setuper.driver2 !=null && LoginToEUCR.IS_LOGGED_EUCR_WINDOW_OPENED(Setuper.driver2))){
				driver = ax.LOGIN_NA2(Setuper.driver2);
				}else{
					driver = Setuper.driver2;
				}
			if(!AM_AccountOperations.confirmAccountSearchPageOpened(driver)){
				AM_AccountOperations.openAccountSearchPage(driver);}
			if(!AM_AccountOperations.filterSearchAccount(driver, AccountIdentifier)) return null;
			return AM_AccountOperations.GET_ACCOUNT_NUMBER_FROM_CELL_ACCOUNT_1x1(driver);
		}
	}
	
	public static boolean OPEN_TAB_ON_TABSUL(WebDriver driver, By UL_TAB_ELEMENT, String TabTitle){
		String STYLE_FOR_CLICKED_LI;
		WebElement UL_ELEMTS_TABS = driver.findElement(UL_TAB_ELEMENT);
		List<WebElement> LI_ELEMTS_TABS = UL_ELEMTS_TABS.findElements(By.tagName("li")); 
		int t=1;
		for (WebElement liTab : LI_ELEMTS_TABS) {
			if( liTab.findElement(By.tagName("em")).getText().contains(TabTitle)){
				ax.click_webElement(driver, liTab.findElement(By.tagName("a")));
				STYLE_FOR_CLICKED_LI = ax.GET_ATTRIB_STRING_FOR_ELEMENT_WEBELEMENT(driver, liTab, "class");
				int i=1;
				while (!STYLE_FOR_CLICKED_LI.contains("ui-tabs-selected")){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					i++;
					STYLE_FOR_CLICKED_LI = ax.GET_ATTRIB_STRING_FOR_ELEMENT_WEBELEMENT(driver, liTab, "class");
					if(i > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
						ax.t_error("Cannot set this tab "+TabTitle+" active..");
						return false;
					}
					
				}
				if(STYLE_FOR_CLICKED_LI.contains("ui-tabs-selected")){
					ax.log("Found tab "+TabTitle+" as active..");
					return true;
				}
			}else{
				System.out.print("[not this tab["+TabTitle+"], trying next tab..  ]");
			}
		t++;
			if( t > LI_ELEMTS_TABS.size() ){
				ax.t_error("Could not found tab "+TabTitle+" in this Tab set..");
				return false;
			}
		}
		return false;
	}	
	
	
//#[HEADER CHECKS ]############################################################	  
	public static boolean verifyPageHeaderPresent(WebDriver driver, String headerId, String headerText, String additionalInfo){
		String XPATH_HEADER_STRING = "//div[contains(@id,'"+headerId+"') and contains(concat(' ',@class,' '),'ui-panel-titlebar')]/span[@class='ui-panel-title' and contains(normalize-space(),'"+headerText+"')]"; 
		WebElement w=SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath(XPATH_HEADER_STRING), ProjectDataVariables.TIME_FOR_CHECKING_ELEMENT);
		if (w==null){
			SeleniumWebUtils.reportFail("Cannot find Page Header with [id: "+headerId+" and text: "+headerText+"] from function: verifyPageHeaderPresent");
			return false;
		}
		if (!SeleniumWebUtils.isElementHiddenNow(driver, By.xpath(XPATH_HEADER_STRING))){
			ax.infoDEBUG("I'm on page: [["+ApplicationPageUtils.GET_PANEL_BOX_TITLE(driver)+"]], "+additionalInfo);
			return true;
		} return false;
	}
	public static Boolean verifyPageHeaderPresentBoolean(WebDriver driver, String headerId, String headerText, String additionalInfo) {
		String header_element_string_xpath = "//*[contains(@id,'"+headerId+"')]//span[@class='ui-panel-title' and (contains(normalize-space(),'"+headerText+"'))]"; 
		SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath(header_element_string_xpath), ProjectDataVariables.TIME_FOR_CHECKING_ELEMENT);
		WebElement ex = driver.findElement(By.xpath(header_element_string_xpath));
		String tx=ex.getText().trim().toUpperCase();
		String ht=headerText.trim().toUpperCase();
		if(tx.contains(ht)){		return true;
		}else{	SeleniumWebUtils.reportFAIL(driver, "No such Page Header ["+headerText+"] displayed"); return false;
		}
	}	
	public static Boolean verifyPageHeader_Any_Title_PresentBoolean(WebDriver driver, String headerId) throws InterruptedException{
		
		String header_element_string_xpath = "//*[contains(@id,'"+headerId+"')]//span[@class='ui-panel-title']"; 
		
		SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath(header_element_string_xpath), ProjectDataVariables.TIME_FOR_CHECKING_ELEMENT);
		WebElement ex = driver.findElement(By.xpath(header_element_string_xpath));
		String tx=ex.getText().trim();
		if(!tx.isEmpty()){
			ax.infoDEBUG("I'm on page: " + tx);
			return true;
		}else{
			SeleniumWebUtils.reportFAIL(driver, "No such Page Header displayed, found empty header..");
			return false;
		}
	}	
	
	public static Boolean verify_PAGE_TITLE_BY_TEXT(WebDriver driver, String TEXT_IN_PAGE_TITLE){
		
		  WebDriverWait wait = new WebDriverWait(driver, 15);
		  wait.until(ExpectedConditions.titleContains(TEXT_IN_PAGE_TITLE));
		
		List<WebElement> PageTitleElements = driver.findElements(By.tagName("title")); 
		
		int n = 0;
		while (PageTitleElements.size() <= 0){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			PageTitleElements = driver.findElements(By.tagName("title"));
			n++;
			if(n > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
				ax.t_error(driver, "Witing too long for the Page "+TEXT_IN_PAGE_TITLE);
				return false;
			}
		}
		String PageTitleString = driver.getTitle(); 
//				driver.findElement(By.tagName("title")).getText();
		
		
		if(PageTitleString.contains(TEXT_IN_PAGE_TITLE)){
			ax.log("Page title ["+TEXT_IN_PAGE_TITLE+"] displayed");
			return true;
		}
		else{
			ax.log("Page title ["+TEXT_IN_PAGE_TITLE+"] NOT displayed!");
			return false;
			
		}
	}
	
	
	
	public static boolean VERIFY_PAGE_HEADER_TEXT_ONLY(WebDriver driver, String TITLE_TEXT){
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, By.xpath("//div[contains(@id,'_header')]/span[contains(concat(' ',@class,' '),'ui-panel-title') and normalize-space()='"+TITLE_TEXT+"']"));
		if(ApplicationPageUtils.verifyPageHeaderPresentBoolean(driver, "_header", TITLE_TEXT, "")){
			ax.debug("I'm on "+TITLE_TEXT+" Page.");
			return true;
		}else{
			SeleniumWebUtils.reportFAIL(driver, "FAIL:: I'm NOT on "+TITLE_TEXT+" Page !!!");
			return false;
		}
	}
	
	
	
	public static Boolean verifyPRESENCE_OF_ELEMENT_BY(WebDriver driver, By by){
		String label = "No such Element BY ["+by+"] displayed";
		if(SeleniumWaiter.waitForPresenceOfElement(driver, by, ProjectDataVariables.TIME_FOR_CHECKING_ELEMENT)==null){
			SeleniumWebUtils.reportFAIL(driver, label);
			ax.takePhoto(driver, label);
				return false;
			}
		
		WebElement ex = driver.findElement(by);
		if(ex.isDisplayed()){
			return true;
		}else{
			
			SeleniumWebUtils.reportFAIL(driver, label);
			ax.takePhoto(driver, label);
			return false;
		}
	}
	
	public static boolean IS_ERROR_BOX_DISPLAYED(WebDriver driver){
		By ERRBOX = RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_BY;
		if(!SeleniumWebUtils.isElementHiddenNow(driver, ERRBOX)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean IS_GREEN_BOX_DISPLAYED(WebDriver driver){
		By GREENBOX = RepoEucr.INFO_GREEN_BOX_UNV_XPATH;
		if(!SeleniumWebUtils.isElementHiddenNow(driver, GREENBOX)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean IS_GREEN_MESSAGE(WebDriver driver, String InfoOnGreenBox){
		By GREENBOX = PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH(InfoOnGreenBox);
		if(!SeleniumWebUtils.isElementHiddenNow(driver, GREENBOX)){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public static boolean waitForHeaderPresence(WebDriver driver, String headerText){
		if(SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath("//*[contains(@id,'_header')]/span[@class='ui-panel-title']"), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT)!=null){
			SeleniumWebUtils.reportInfo("PAGE  [["+headerText+"]] DISPLAYED.");
			return true;
		}else{
			ax.t_error(driver, "Problem with waitForHeaderPresence with headerText " + headerText);
			return false;
		}
	}
	public static boolean waitForHeaderPresence_SpecificId(WebDriver driver, String SpacialHeaderId, String headerText){
		if(SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath("//*[contains(@id,'"+SpacialHeaderId+"')]/span[@class='ui-panel-title']"), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT)!=null){
			SeleniumWebUtils.reportInfo("PAGE  [["+headerText+"]] DISPLAYED.");	
			return true;
		}else{
			ax.t_error(driver, "Problem with waitForHeaderPresence_SpecificId "+SpacialHeaderId+" and headerText " + headerText);
			return false;
		}
	}
	public static boolean waitForHeaderPresence_Universal(WebDriver driver, String headerText){
		if(SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath("//*[contains(concat(' ',@class,' '),'ui-panel-titlebar') and contains(concat(' ',@class,' '),'ui-widget-header')]/span[@class='ui-panel-title' and ( contains(text(),'"+headerText+"') )]"), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT)!=null){
			SeleniumWebUtils.reportInfo("PAGE  [["+headerText+"]] DISPLAYED.");
			return true;
		}else{
			ax.t_error(driver, "Problem with waitForHeaderPresence_Universal with headerText " + headerText);
			return false;
		}
	}
	
	
	public static boolean waitAndVerifyPagePresent(WebDriver driver, By byElementOnTopOfThePage){
		if(ax.wait_elm(driver, byElementOnTopOfThePage)){
			return ax.x_VerifyByObjectPresent(driver, byElementOnTopOfThePage);
		}
		return ax.t_error_false(driver, "Problem with waiting and verifying if Page os presented "+byElementOnTopOfThePage.toString());
	}

	
	public static boolean WAIT_FOR_PAGE_HEADER(WebDriver driver, String PAGE_HEADER_TEXT){
		int WaitingTime = 15;
		ax.debug("Waiting for Page Header ["+PAGE_HEADER_TEXT+"] ");
		ax.wait(1);
		By BY_ELEMENT = By.xpath("//*[contains(concat(' ',@class,' '),'ui-panel-titlebar') and contains(concat(' ',@class,' '),'ui-widget-header')]/span[@class='ui-panel-title' and ( contains(text(),'"+PAGE_HEADER_TEXT+"') )]");
		WebElement e = ax.wait4element(driver, BY_ELEMENT, WaitingTime);
//		WebElement e = SeleniumWaiter.waitForPresenceOfElement(driver, BY_ELEMENT, 				WaitingTime);
		if(ax.IS_NULL_OR_EMPTY_WEBELEMENT(e)){
			ax.t_error(driver, "Web Header "+PAGE_HEADER_TEXT+" is not displayed after waiting: "+WaitingTime); 
			return false;
		}	
		if(e.isDisplayed()){
				return true;
			}else{
				ax.t_error(driver, "Web Header "+PAGE_HEADER_TEXT+" is not displayed after waiting: "+WaitingTime);
				return false;
			}
	}
	public static void waitForDialogTitlePopupDIVWindowsAndHeaderPresence(WebDriver driver, String headerText){
		SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath("//*[contains(concat(' ',@class,' '),' ui-dialog-titlebar ')]/span[contains(concat(' ',@class,' '),' ui-dialog-title ') and contains(text(),'"+headerText+"') ]"), 
				ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
		SeleniumWebUtils.reportInfo("POPUP BOX:  [["+headerText+"]] DISPLAYED.");
	}
	public static boolean waitForHeaderLessPagePresence_PF_BOX(WebDriver driver, String PFBOX_headerTitle){
//		SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath("//*[contains(@id,'_header')]/span[@class='ui-panel-title' and normalize-space()='"+PFBOX_headerTitle+"']"), ProjectDataVariables.TIME_FOR_CHECKING_ELEMENT);
		WebElement h = ax.getStaleElementByLocator(driver, By.xpath("//*[contains(@id,'_header')]/span[@class='ui-panel-title' and normalize-space()='"+PFBOX_headerTitle+"']"));
		if(h!=null){
			SeleniumWebUtils.reportInfo("PAGE [["+PFBOX_headerTitle+"]] TOP BOX DISPLAYED.");
			return true;
		}else{
			ax.t_error("Something wrong with displaying this header element: "+PFBOX_headerTitle);
			return false;
		}
		
	}

	public static boolean WAIT_FOR_PANEL_BOX(WebDriver driver, String PanelTitle){
		return ax.wait_for_element(driver, By.xpath(ApplicationPageUtils.GET_PANEL_BOX(PanelTitle, "content")));
	}
	
	  public static void CheckHeaderPfThePage(WebDriver driver, String headerText)
	  {
		if( SeleniumWebUtils.isDisplayedBy(driver, By.xpath("//span[@class='ui-panel-title']"))  	&& 		driver.findElement(By.xpath("//span[@class='ui-panel-title']")).getText().trim().equals(headerText))
		{
			ax.infoDEBUG("I'm on page: "+headerText);
		} 		else 		{
			SeleniumWebUtils.reportFAIL(driver, "Im not on the page: [["+headerText+"]]");
		}
	  }

	public static void verifyPF_BoxPresent(WebDriver driver, String PF_Box_header_text){
//		PANEL: header: div class=' ui-panel '/div class=' ui-panel-titlebar ' and text()='Appointed Verifier'
//      PANEL: text=There is already a pending appointment request with id: 15612
		
		SeleniumWaiter.waitForPresenceOfElement(driver, By.xpath("//div[contains(concat(' ',@class,' '),' ui-panel ')]/div[contains(concat(' ',@class,' '),' ui-panel-titlebar ')]/span[contains(concat(' ',@class,' '),' ui-panel-title ') and contains(text(),'"+PF_Box_header_text+"')]"), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
		List<WebElement> list = driver.findElements(By.xpath("//div[contains(concat(' ',@class,' '),' ui-panel ')]/div[contains(concat(' ',@class,' '),' ui-panel-titlebar ')]/span[contains(concat(' ',@class,' '),' ui-panel-title ') and contains(text(),'"+PF_Box_header_text+"')]"));
		org.testng.Assert.assertTrue(list.size() > 0, "Text ["+PF_Box_header_text+"] not found!");
		
	}
	
	public static String verifyContentTextPresentForPFBoxWithHeader(WebDriver driver, String PFBOX_Header){
		
		WebElement el = SeleniumWebUtils.findElement(driver, By.xpath("//*[contains( concat(' ',@class,' '),'ui-panel-title'  ) and  contains(text(), '"+PFBOX_Header+"')]"));
		WebElement parent1 		= el.findElement(By.xpath("..")); 
		// element for whole panel box
		WebElement parent2 		= parent1.findElement(By.xpath("..")); 
		
		String panelContentText=parent2.findElement(By.xpath("//*[contains(concat(' ',@class,' '),' ui-panel-content ')]")).getText();
		return panelContentText;
		
	}

	public static Boolean verifyContentTextPresentInAnyElement(WebDriver driver, By by){
		WebElement el = driver.findElement(by);
		if (el.isDisplayed()){
			return true;
		}else{
			return false;
			}
	}	
	
	
	  
	public static void verifyMonitTextPresent(WebDriver driver, String monitId, String monitTextPart){
		// [xyshio]
//		List<WebElement> list = driver.findElements(By.xpath("//*[@id='"+monitId+"' and contains(text(),'" + monitTextPart + "')]")); 
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(concat(' ',@id,' '),' "+monitId+" ')  and  contains(text(),'" + monitTextPart + "')]"));
		org.testng.Assert.assertTrue( list.size() > 0, "Text ["+monitTextPart+"] not found!");
		SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
	}

	public static void verifyGreenInfoMonitByTextPresented(WebDriver driver, String monitTextPart){
		List<WebElement> list = driver.findElements(PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH(monitTextPart));
//		Assert.assertTrue("Text ["+monitTextPart+"] not found!", list.size() > 0);
		org.testng.Assert.assertTrue(list.size() > 0, "Text ["+monitTextPart+"] not found!");
		SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
	}
	public static boolean verifyYellowWarningMonitByTextPresented(WebDriver driver, String monitTextPart){
		By byElementWithText = PF_PageElements.WARN_YELLOW_BOX_WITH_TEXT_XPATH(monitTextPart);
		
		if(SeleniumWebUtils.isElementDisplayed(driver, byElementWithText)){
			SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
			return true;
		} return false;
		
//		List<WebElement> list = driver.findElements(PF_PageElements.WARN_YELLOW_BOX_WITH_TEXT_XPATH(monitTextPart));
//		Assert.assertTrue("Text ["+monitTextPart+"] not found!", list.size() > 0);
//		SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
	
	
	}
	public static boolean verifyOrangeErrorMonitByTextPresented(WebDriver driver, String monitTextPart){
		By byElementWithText_E = PF_PageElements.ERR_ORANGE_BOX_WITH_TEXT_XPATH(monitTextPart);
		if(SeleniumWebUtils.isElementDisplayed(driver, byElementWithText_E)){
			SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
			return true;
		} return false;
//		List<WebElement> list = driver.findElements(PF_PageElements.ERR_ORANGE_BOX_WITH_TEXT_XPATH(monitTextPart));
//		Assert.assertTrue("Text ["+monitTextPart+"] not found!", list.size() > 0);
//		SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
	}

	
	
	public static Boolean verifyTextPresentOnWarningYellowMonit(WebDriver driver, String monitTextPart){
		String WarningMonitConent=SeleniumWebUtils.getTextForElement(driver, RepoEucr.WARN_YELLOW_BOX_UNV_XPATH);
		if (WarningMonitConent.contains(monitTextPart)){
			SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
			return true;
		}else{ 
			SeleniumWebUtils.reportFAIL(driver, "###### Text ["+monitTextPart+"] was not found.");
			return false;
			}
	}

	public static Boolean verifyPresenceOfOrangeErrorBoxWithText(WebDriver driver, String monitTextContent){
		if (SeleniumWebUtils.isElementDisplayed(driver, By.xpath("//div[contains(concat(' ',@class,' '),'ui-messages-error')]//*[contains(text(),'"+monitTextContent+"')]"))){
			SeleniumWebUtils.reportFAIL(driver, SeleniumWebUtils.getTextForElement(driver, By.xpath("//div[contains(concat(' ',@class,' '),'ui-messages-error')]//*[contains(text(),'"+monitTextContent+"')]")));
			return true;
		}else{
			return false;
		}
	}	
	
	
	
	public static Boolean verifyTextPresentOnSuccessGreenMonit(WebDriver driver, String monitTextPart){
//		String GreenMonitConent=SeleniumWebUtils.getTextForElement(driver, By.xpath("//div[@id='mainForm:errors']//div[contains(concat(' ',@class,' '),'ui-messages-info')]//span[@class='ui-messages-info-detail']"));
		String GreenMonitConent=SeleniumWebUtils.getTextForElement(driver,RepoEucr.INFO_GREEN_BOX_UNV_XPATH).trim();
		if (GreenMonitConent.contains(monitTextPart.trim())){
			SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
			return true;
		}else{ 
			SeleniumWebUtils.reportFAIL(driver, "###### Text ["+monitTextPart+"] was not found.");
			return false;
			}
	}
	
	
	public static void verifyMonitTextPresentByClass(WebDriver driver, String monitClassId, String monitTextPart){
//		List<WebElement> list = driver.findElements(By.xpath("//*[@id='"+monitId+"' and contains(text(),'" + monitTextPart + "')]")); 
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(concat(' ',@class,' '),' "+monitClassId+" ')  and  contains(text(),'" + monitTextPart + "')]"));
		org.testng.Assert.assertTrue(list.size() > 0, "Text ["+monitTextPart+"] not found!" );
		SeleniumWebUtils.reportInfo("###### Text ["+monitTextPart+"] was displayed.");
	}

	
	public static boolean checkIfFoundRecordsInPaginatorCurrent(WebDriver driver){
		if(!ax.wait_for_element(driver, PaginatorCurrentBy)) return false; 
		String rowReturned = SeleniumWebUtils.getTextFromElement(driver, PaginatorCurrentBy);
		int intReturnedRows = ApplicationPageUtils.howManyRowsFound_PFTable(rowReturned);
		if (intReturnedRows>0){
			SeleniumWebUtils.reportInfo("Results for searching in Paginator found "+intReturnedRows+" records");
			return true;
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Results for searching in Paginator found "+intReturnedRows+" records");
			return false;
		}
	}	
	
	public static int howManyRowsFound_PFTable(String returnedString){
		String s =returnedString.substring(returnedString.indexOf("(")+1, returnedString.indexOf(" ", returnedString.indexOf("(")));
		if(ax.getProp("TEST_MODE").equals("NORMAL")){
			ax.debug("Returned rows: >>"+s+"<<");	
		}
		if(ax.IS_NOT_NULLEMPTY(s)){
			return Integer.parseInt(s);	
		}
		return -1;
	}	
	public static int GetNumberOfPagesFound_Paginator_Current(String PG_CURREN_TEXT){
		String[] a = PG_CURREN_TEXT.split(" ");
		if(a[0].equals("Page") &&  a[2].equals("of")){
			return ToolFunctions.StringToInteger(a[3]);
		}else{
			return -1;
		}
	}
	public static int GetNumberOfCurrentPageFound_Paginator_Current(String PG_CURREN_TEXT){
		String[] a = PG_CURREN_TEXT.split(" ");
		if(a[0].equals("Page") &&  a[2].equals("of")){
			return ToolFunctions.StringToInteger(a[1]);
		}else{
			return -1;
		}
	}

	public static String getStatusOfTheAccountUppercase(WebDriver driver, String AccountIdentifier){
		if(ax.isSQLEnabled()){
			return AppSqlQueries.getStatusOfTheAccountByIdentifier(AccountIdentifier).toUpperCase().trim();	
		}else{
			return AM_AccountOperations.getStatusOfTheAccount(driver, AccountIdentifier).toUpperCase().trim();	
		}
	}
	
	
	public static int getNumberOfRowsFoundPaginatorCurrent(WebDriver driver){
		return ApplicationPageUtils.GetNumberOfRowsFound_Paginator_Current(driver, "");
	}
	
	public static int GetNumberOfRowsFound_Paginator_Current(WebDriver driver, String TopBox_Container){
		String CurrLabel;
		ax.wait(1);
		String PaginatorTopBy = TopBox_Container +  PF_PageElements.UI_PAGINATOR_CURRENT_UNIVERSAL_STRING;
		if(!ax.wait_elm(driver, PaginatorTopBy, 10)){
			return -1;
		}
		WebElement curr=ax.getStaleElementByLocator2(driver, By.xpath( TopBox_Container + PF_PageElements.UI_PAGINATOR_CURRENT_UNIVERSAL_STRING));
		
		if (curr!=null){
			CurrLabel = curr.getText();
			if(CurrLabel.isEmpty()){
				SeleniumWebUtils.reportFAIL(driver, "No Current element text found");
				return 0;
			}
			int ret=ApplicationPageUtils.howManyRowsFound_PFTable(CurrLabel);
			return ret;
		}else{
			SeleniumWebUtils.reportFAIL(driver, "No Current element found");
			return 0;
		}
	}
	public static int GetNumberOfRowsFoundPaginatorCurrentByWebElement(WebDriver driver, WebElement elm){
		String CurrLabel;
		ax.wait(1);
		if (elm!=null){
			CurrLabel = elm.getText();
			if(CurrLabel.isEmpty()){
				SeleniumWebUtils.reportFAIL(driver, "No Current element text found");
				return 0;
			}
			int ret=ApplicationPageUtils.howManyRowsFound_PFTable(CurrLabel);
			return ret;
		}else{
			SeleniumWebUtils.reportFAIL(driver, "No Current element found");
			return 0;
		}
	}
	
	public static int CountResult_FromPaginator(String PaginatorString){
		//PaginatorString like "Page 1 of 1 (1 rows found)";
		int r = PaginatorString.indexOf("(");
		String[] ar = PaginatorString.substring(r).substring(1).split(" ");
		return Integer.parseInt(ar[0]);
	}
	
	public static String returnStatusfromTheAccountRowInTable(WebDriver driver, int rowNumber){
		String AccountStatus = SeleniumWebUtils.simpleGetText(driver, By.xpath("//*[@id='mainForm:accountSearchResultsTable_row_"+rowNumber+"']/td[7]"));
		return AccountStatus;
	}
	
	
	public static String getNumberFromGreenMonit(WebDriver driver, String idDiva, String wordBeforeNumber){
		ax.debug("Enter Function getNumberFromGreenMonit");			
		String RequestNumberFromMonit = null;
		try {
	    	   SeleniumWaiter.waitForMe(driver, By.xpath("//*[@id='"+idDiva+"']/div/ul"), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
	    	    WebElement el = SeleniumWebUtils.findElementIgnoreExceptions(driver, By.xpath("//*[@id='"+idDiva+"']/div/ul"));
	    		ax.log("RECOGNIZED IDENTIFIER: " + el.getText());
	    	    String MonitWithRequestNr = el.getText();
	    	    RequestNumberFromMonit = SeleniumWebUtils.getRequestNumberFromMonit(MonitWithRequestNr, wordBeforeNumber);
	    		ax.log("RECOGNIZED IDENTIFIER: " + RequestNumberFromMonit);
		} catch  (Exception e){
	    	   SeleniumWebUtils.reportInfo("Element [ "+By.xpath("//*[@id='"+idDiva+"']/div/ul").toString()+" ]] was not found!");
	    	   TakePhoto.takePhoto(driver, true, "Not Found Element with monit");			
		}
		ax.debug("Exit Function getNumberFromGreenMonit");			
		return RequestNumberFromMonit;
	}
	
	
	public static String getRequestNumberFromGreenMonit(WebDriver driver, String ByXPATHOfGreenBox, String wordBeforeNumber){
		return getRequestNumberFromGreenMonit(driver, By.xpath(ByXPATHOfGreenBox), wordBeforeNumber);
	}
	
	public static String getRequestNumberFromGreenMonit(WebDriver driver, By ByXPATHOfGreenBox, String wordBeforeNumber){
		SeleniumWebUtils.reportInfo("Enter Function getNumberFromGreenMonit");			
		String RequestNumberFromMonit = null;
		try {
	    	   SeleniumWaiter.waitForMe(driver, /*By.xpath(ByXPATHOfGreenBox)*/ ByXPATHOfGreenBox, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
	    	    WebElement el = SeleniumWebUtils.findElementIgnoreExceptions(driver, ByXPATHOfGreenBox);
	    		ax.log("RECOGNIZED IDENTIFIER: " + el.getText());
	    	    String MonitWithRequestNr = el.getText();
	    	    RequestNumberFromMonit = SeleniumWebUtils.getRequestNumberOnlyFromMonit(MonitWithRequestNr, wordBeforeNumber);
//	    	    		getRequestNumberFromMonit(MonitWithRequestNr, wordBeforeNumber);
	    		ax.log("RECOGNIZED IDENTIFIER: " + RequestNumberFromMonit);
		} catch  (Exception e){
	    	   SeleniumWebUtils.reportInfo("Element [ "+ByXPATHOfGreenBox.toString()+" ]] was not found!");
	    	   TakePhoto.takePhoto(driver, true, "Not Found Element with monit");			
		}
		SeleniumWebUtils.reportInfo("Exit Function getNumberFromGreenMonit");			
		return RequestNumberFromMonit;
	}

	
	public static ArrayList<String> getRequestNumbersMULTIFromGreenMonit(WebDriver driver, String ByXPATHOfGreenBox, String wordBeforeNumber){
		SeleniumWebUtils.reportInfo("Enter Function getNumberFromGreenMonit");			
		ArrayList<String> RequestNumberFromMonit = null;
		try {
	    	   SeleniumWaiter.waitForMe(driver, By.xpath(ByXPATHOfGreenBox), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
	    	    WebElement el = SeleniumWebUtils.findElementIgnoreExceptions(driver, By.xpath(ByXPATHOfGreenBox));
	    		ax.log("RECOGNIZED IDENTIFIER: " + el.getText());
	    	    String MonitWithRequestNr = el.getText();
//	    		String r = "Your account opening requests have been recorded with identifiers 280469,280470,280471,280472,280473,280474,280475.";
//	    		String w = "identifiers";
	    		int i = MonitWithRequestNr.indexOf(wordBeforeNumber) + wordBeforeNumber.length() + 1;
	    		String requestyString = MonitWithRequestNr.substring(i).replace(".", "");
	    		String[] rq = requestyString.split(",");
	    		ArrayList<String> requestList = new ArrayList<String>(); 
	    		Collections.addAll(requestList, rq); 
	    	    RequestNumberFromMonit = requestList;
	    		ax.log("RECOGNIZED IDENTIFIER: " + RequestNumberFromMonit);
		} catch  (Exception e){
	    	   SeleniumWebUtils.reportInfo("Element [ "+By.xpath(ByXPATHOfGreenBox).toString()+" ]] was not found!");
	    	   TakePhoto.takePhoto(driver, true, "Not Found Element with monit");			
		}
		SeleniumWebUtils.reportInfo("Exit Function getNumberFromGreenMonit");			
		return RequestNumberFromMonit;
	}	
	
	public static String getNumberForRequestOnlyFromGreenMonit(WebDriver driver, String idDiva, String wordBeforeNumber){
		SeleniumWebUtils.reportInfo("Enter Function getNumberForRequestOnlyFromGreenMonit");			
		String RequestNumberFromMonit = null;
		try {
	    	   SeleniumWaiter.waitForMe(driver, By.xpath("//*[@id='"+idDiva+"']/div/ul"), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
	    	    WebElement el = SeleniumWebUtils.findElementIgnoreExceptions(driver, By.xpath("//*[@id='"+idDiva+"']/div/ul"));
	    		ax.log("RECOGNIZED IDENTIFIER: " + el.getText());
	    	    String MonitWithRequestNr = el.getText();
//	    	    xyshio
	    	    RequestNumberFromMonit = SeleniumWebUtils.getRequestNumberOnlyFromMonit(MonitWithRequestNr, wordBeforeNumber);
	    		ax.log("RECOGNIZED IDENTIFIER: " + RequestNumberFromMonit);
		} catch  (Exception e){
	    	   SeleniumWebUtils.reportInfo("Element [ "+By.xpath("//*[@id='"+idDiva+"']/div/ul").toString()+" ]] was not found!");
	    	   TakePhoto.takePhoto(driver, true, "Not Found Element with monit");			
		}
		SeleniumWebUtils.reportInfo("Exit Function getNumberForRequestOnlyFromGreenMonit");			
		return RequestNumberFromMonit;
	}
	
	public static String VerifyPresenceOfGreenMonit_AndGetRequestNumber(WebDriver driver, String wordBeforeNumber){
		SeleniumWebUtils.reportInfo("Enter Function VerifyPresenceOfGreenMonit_AndGetRequestNumber");			
		String RequestNumberFromMonit = null;
		try {
	    	SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, RepoEucr.INFO_GREEN_BOX_UNV_XPATH);   
	    	    WebElement el = SeleniumWebUtils.findElementIgnoreExceptions(driver, RepoEucr.INFO_GREEN_BOX_UNV_XPATH);
	    		ax.debug("RECOGNIZED TEXT ON GREEN MONIT: " + el.getText());
	    	    String MonitWithRequestNr = el.getText();
	    	    RequestNumberFromMonit = SeleniumWebUtils.getRequestNumberOnlyFromMonit(MonitWithRequestNr, wordBeforeNumber);
	    		ax.log("RECOGNIZED IDENTIFIER/REFERENCE: " + RequestNumberFromMonit);
		} catch  (Exception e){
	    	   SeleniumWebUtils.reportInfo("Element [ green monit ] was not found!");
	    	   TakePhoto.takePhoto(driver, true, "Element [ green monit ] was not found!");
	    	   RequestNumberFromMonit = null;
		}
		SeleniumWebUtils.reportInfo("Exit Function VerifyPresenceOfGreenMonit_AndGetRequestNumber");			
		return RequestNumberFromMonit;
	}
	public static String VerifyPresenceOfGreenMonit_AndGetRequestNumber_Multiwords(WebDriver driver, String wordsBeforeNumber){
//		SeleniumWebUtils.reportInfo("Enter Function VerifyPresenceOfGreenMonit_AndGetRequestNumber_Multiwords");			
		String RequestNumberFromMonit = null;
		try {
	    		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, RepoEucr.INFO_GREEN_BOX_UNV_XPATH);   
	    	    WebElement el = SeleniumWebUtils.findElementIgnoreExceptions(driver, RepoEucr.INFO_GREEN_BOX_UNV_XPATH);
	    		ax.log("RECOGNIZED TEXT ON GREEN MONIT: " + el.getText());
	    	    String MonitWithRequestNr = el.getText();
	    	    RequestNumberFromMonit = SeleniumWebUtils.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(MonitWithRequestNr, wordsBeforeNumber);
	    		ax.log("RECOGNIZED IDENTIFIER/REFERENCE: " + RequestNumberFromMonit);
		} catch  (Exception e){
	    	   SeleniumWebUtils.reportInfo("Element [ green monit ] was not found!");
	    	   TakePhoto.takePhoto(driver, true, "Element [ green monit ] was not found!");
	    	   RequestNumberFromMonit = null;
		}
//		SeleniumWebUtils.reportInfo("Exit Function VerifyPresenceOfGreenMonit_AndGetRequestNumber_Multiwords");			
		return RequestNumberFromMonit;
	}	

	public static boolean HANDLING_SUCCESS_MESSAGE(WebDriver driver, String MessagePart){
			return HandlingGreenBox_SuccessMessage(driver, MessagePart, "", 0, "", "");
	}
	
	
	public static boolean HandlingGreenBox_SuccessMessage(WebDriver driver, String PartOfMessageSupposeToBeOnGBox, String WordAfterWhichIsIndexYouSearch, int Return2Excel, String ReturnTextToExcel1, String ReturnTextToExcel2){
		String SearchedReturnIndex = null;
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, RepoEucr.INFO_GREEN_BOX_UNV_XPATH);
		if (TestUtils.CheckForAnyMessagesGreenInfoBoxes(driver).equals(true)){
			ApplicationPageUtils.verifyTextPresentOnSuccessGreenMonit(driver, PartOfMessageSupposeToBeOnGBox);
			TakePhoto.takePhotoOnly(driver);
			String GreenMonitAfterTransferProposing = driver.findElement(RepoEucr.INFO_GREEN_BOX_UNV_XPATH).getText();
			if (!WordAfterWhichIsIndexYouSearch.isEmpty()){
				SearchedReturnIndex = StringPageUtils.getMonitWordAfterLetterLIST_spaceSeparated(GreenMonitAfterTransferProposing, WordAfterWhichIsIndexYouSearch);
			}
			SeleniumWebUtils.reportPass("Green Message Box with: "+PartOfMessageSupposeToBeOnGBox+", Searched index to return: "+SearchedReturnIndex);
			
			if (Return2Excel==1){
				ax.t_pass(ReturnTextToExcel1+"_"+ReturnTextToExcel2 +"_"+SearchedReturnIndex);
			}
			return true;
		}else{
			SeleniumWebUtils.reportFAIL(driver, "No confirmation Green InfoBox!!" );
			if (Return2Excel==1){
			ax.fail(ReturnTextToExcel1+"_"+ReturnTextToExcel2);
			ax.t_error(driver, ReturnTextToExcel1+"_"+ReturnTextToExcel2);
			}
			return false;
		}
	}
	
	
	
	
	
	
	public static String GET_ACCOUNT_OPENING_REQUEST_FROM_GREEN_MESSAGE(WebDriver driver){
		return HANDLE_SUCCESS_GREEN_MONIT_RETURN_REQNR(driver, "identifier");
	}
	
	
	public static String HANDLE_SUCCESS_GREEN_MONIT_RETURN_REQNR(WebDriver driver, String WordsBefore_REQNR){
//		ax.info("PERFORMANCE_MARKER[11]:ACCOUNT_CREATION_PROPOSAL:GREEN_MONIT");
		ax.f_perf_append(TransactionPage.PERFORMANCE_UNIQUE_ID, "PERFMRKR:ACCOUNT_CREATION_PROPOSAL:GREEN_MONIT");
		return ApplicationPageUtils.VerifyPresenceOfGreenMonit_AndGetRequestNumber_Multiwords(driver, WordsBefore_REQNR);
	}
	public static ArrayList<String> HANDLE_SUCCESS_GREEN_MONIT_RETURN_REQNR_AND_TRXNR(WebDriver driver, String WordsBefore_REQNR, String WordsBefore_TRXNR){
		ArrayList<String> RET = new ArrayList<String>();
		if(!ax.isDisplayedBy(driver, RepoEucr.INFO_GREEN_BOX_UNV_XPATH) && ax.isDisplayedBy(driver, RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_BY)){
			ax.t_error(driver, "There is not displayed confirmation GREEN BOX. There is instead ERROR BOX with: " + ax.getText(driver, RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_BY) );
			RET.add(null);  RET.add(null);
			return RET;
		}		
		RET.add(ApplicationPageUtils.VerifyPresenceOfGreenMonit_AndGetRequestNumber_Multiwords(driver, WordsBefore_REQNR));
		RET.add(ApplicationPageUtils.VerifyPresenceOfGreenMonit_AndGetRequestNumber_Multiwords(driver, WordsBefore_TRXNR));
		return RET;
	}	
	
	public static boolean WAIT_FOR_MONIT(WebDriver driver){
		Boolean MONIT_DISPLAYED = (ax.isDisplayedBy(driver, By.xpath(RepoEucr.INFO_GREEN_BOX_UNV_LOCATION)) || ax.isDisplayedBy(driver, By.xpath(RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_STRING)));
//		ax.log( "MONIT_DISPLAYED: " +MONIT_DISPLAYED );
		int n=0;
		while (!MONIT_DISPLAYED){
			ax.wait(1);
			ax.log("Message Box [-OK- OR -ERR-] Still Not displayed ["+n+"]");
			MONIT_DISPLAYED = (ax.isDisplayedBy(driver, By.xpath(RepoEucr.INFO_GREEN_BOX_UNV_LOCATION)) || ax.isDisplayedBy(driver, By.xpath(RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_STRING)));
//			ax.log( "MONIT_DISPLAYED: " +MONIT_DISPLAYED +"["+n+"]");
			n++;
			if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){return false;}
		}
		return true;
	}	
	
	
	  public static void ClickEURCR_LogoutLink(WebDriver driver){
		  WebElement eucrLogoutLink = SeleniumWebUtils.quickFindElementIgnoreExceptions(driver, By.xpath("//*[@id='localsForm']/span[contains(@id,'localsForm:j_idt')]/a"));
		  if (eucrLogoutLink.isDisplayed()){
//			  driver.findElement(By.linkText("logout")).click();
			  SeleniumWebUtils.FindAndClickElement(driver, By.xpath("//*[@id='localsForm']/span[contains(@id,'localsForm:j_idt')]/a"), ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
			  SeleniumWaiter.waitForPageLoaded(driver);
		  }
	  }	
	
//# [ACCOUNT ACTION LINKS ]############################################################	  
	  public static boolean ClickAccount_RestoreLink(WebDriver driver){
		  if(ax.isDisplayed_And_Enabled(driver, AM_AccountOperations.RestoreAccountButton_MAIN_TAB)){
			  return ax.click_button_and_wait_bool(driver, AM_AccountOperations.RestoreAccountButton_MAIN_TAB); }
		  else{
			  ax.t_error(driver, "Not displayed RESTORE button on Account Details View");return false; 		  }
	  }
	  public static boolean ClickAccount_SuspendLink(WebDriver driver){
		  if(ax.isDisplayed_And_Enabled(driver, AM_AccountOperations.SuspendAccountButton_MAIN_TAB)){
			  return ax.click_button_and_wait_bool(driver, AM_AccountOperations.SuspendAccountButton_MAIN_TAB); }
		  else{
			  ax.t_error(driver, "Not displayed SUSPEND button on Account Details View");return false; 		  }
	  }
	  public static boolean ClickAccount_BlockButtonLink(WebDriver driver){
		  if(ax.isDisplayed_And_Enabled(driver, AM_AccountOperations.BlockAccountButton_MAIN_TAB)){
			  return ax.click_button_and_wait_bool(driver, AM_AccountOperations.BlockAccountButton_MAIN_TAB); }
		  else{
			  ax.t_error(driver, "Not displayed [BLOCK] button on Account Details View");return false; 		  }
	  }
	  public static boolean ClickAccount_UnblockLink(WebDriver driver){
		  if(ax.isDisplayed_And_Enabled(driver, AM_AccountOperations.UblockAccountButton_MAIN_TAB)){
			  return ax.click_button_and_wait_bool(driver, AM_AccountOperations.UblockAccountButton_MAIN_TAB); }
		  else{
			  ax.t_error(driver, "Not displayed UNBLOCK button on Account Details View");return false; 		  }
	  }
	  
	  
	  public static boolean ClickAccount_CloseLink(WebDriver driver){
		  if(ax.isDisplayed_And_Enabled(driver, AM_AccountOperations.CloseAccountButton_MAIN_TAB)){
			  return ax.click_button_and_wait_bool(driver, AM_AccountOperations.CloseAccountButton_MAIN_TAB); }
		  else{
			  ax.t_error(driver, "Not displayed CLOSE button on Account Details View");return false;
		  }
	  }
	  public static boolean ClickAccount_UnreleaseLink(WebDriver driver){
		  if(ax.isDisplayed_And_Enabled(driver, AM_AccountOperations.UnreleaseAccountButton_MAIN_TAB)){
			  return ax.click_button_and_wait_bool(driver, AM_AccountOperations.UnreleaseAccountButton_MAIN_TAB); }
		  else{
			  ax.t_error(driver, "Not displayed UNRELEASE button on Account Details View");return false;
		  }
	  }
		
	  
//# [MONIT FUNCTIONS ]############################################################	  
	  public static String GetTextFromYellowMonit(WebDriver driver){
		//*[@id='mainForm:errors']/div/ul/li/span
		  // Please confirm! You are about to restore access to account 5025512. Representatives of this account holder will again be able to access this account.
		  return "";
	  }
	  public static String GetTextFromOrangeErrorMonit(WebDriver driver){
		  String t = ax.getText(driver, RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_BY);
		  return (ax.IS_NOT_NULLEMPTY(t))?t:null;
	  }
	  
	  
	  
//# [MONIT DIALOG HANDLING ]############################################################
	  public static void RadioDialogAddingAnother_AAR(WebDriver driver, String decisionNo){
	    WebElement getMonit = SeleniumWebUtils.findElementIgnoreExceptions(driver, By.xpath("//div[@id='mainForm:accountDetailsPanel_content']"));
	    String MonitWithRequestNr = getMonit.getText();
	    if (MonitWithRequestNr.contains("additional authorized representatives to your account.Do you wish to add another additional authorised representative to your account ?")){
	    	if (decisionNo.equals("Yes")){
	    		SeleniumWebUtils.FindAndClickElement(driver, By.xpath("//div[@id='mainForm:accountDetailsPanel_content']/table/tbody/tr[1]/td/input[contains(@id, ':0')]"), ProjectDataVariables.TIME_FOR_CLICK_FUNCTIONS);
	    	}else if(decisionNo.equals("No")){
	    		SeleniumWebUtils.FindAndClickElement(driver, By.xpath("//div[@id='mainForm:accountDetailsPanel_content']/table/tbody/tr[2]/td/input[contains(@id, ':1')]"), ProjectDataVariables.TIME_FOR_CLICK_FUNCTIONS);
	    	}
	    }
	  }

// ###[ PAGE ELEMENTS]############################################
public static String returnVersionOfApplication(WebDriver driver){ // version like: "version 6.2" 
	String versionOnFooter = SeleniumWebUtils.getTextForElement(driver, By.id("footer"));
	return versionOnFooter.trim().substring(8, 11);
}	  
public static String returnNAEXCLUSIVE_by_version(WebDriver driver){
	String versionOnFooter = SeleniumWebUtils.getTextForElement(driver, By.id("footer"));
	String ret="";
	if (versionOnFooter.trim().substring(0, 11).equals("version 6.2")){
    	ret= "NaExclusive";
    }else if(versionOnFooter.trim().substring(0, 11).equals("version 6.1")){
    	ret="NaExclusive"; // not handling this version anymore
    }else{
    	ret="NaExclusive"; // not handling this version anymore
    }
	return ret;
}
public static String GET_VERSION_OF_APP(WebDriver driver){
	String versionOnFooter = SeleniumWebUtils.getTextForElement(driver, By.id("footer"));
	String CutFromMe= "Page ref.";
	int last = versionOnFooter.indexOf(CutFromMe);
	return versionOnFooter.trim().substring(0, last);
}

public static String CHROME(WebDriver driver, String accountId){
	SeleniumWebUtils.reportInfo("Enter Function getFullAccountNumberByAccountIdentifier for account id="+accountId);		
	EUCR_MenuNavigation.menuGoAccAccounts(driver);
	SeleniumWaiter.waitForPageLoaded(driver);
	if(SeleniumWebUtils.elementPresent(driver, By.xpath("//*[contains(@id,'_header')]")) == 1 && driver.findElement(By.xpath("//*[contains(@id,'_header')]/span")).getText().equals("Account Search Criteria")){ //
		ax.infoDEBUG("I'm on page: Accounts, To check status");
	}
	SeleniumWebUtils.FindClearAndTypeValueInEditBox(driver, AM_AccountOperations.ASC_IDENTIFIER, accountId, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
	ax.wait(2);
	AppButtons.ClickPF_Button(driver, "mainForm:buttonSearch", "Search");	    
	ax.wait(2);
	SeleniumWaiter.waitForPageLoaded(driver);
	SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, By.xpath("//*[@id='mainForm:accountSearchResultsTable_row_0']/td[1]"));
	String readAccNumber = SeleniumWebUtils.simpleGetText(driver, By.xpath("//*[@id='mainForm:accountSearchResultsTable_row_0']/td[1]"));
	SeleniumWebUtils.reportInfo("EXIT Function getFullAccountNumberByAccountIdentifier RETURN WITH Full Account Number="+readAccNumber);		
	return readAccNumber;	
}

public static String getAccNrAccountRegCode(String FullAccountNumber){
	String[] accNrArr = FullAccountNumber.split("-");
	return accNrArr[0];
}
public static String getAccNrAccountType(String FullAccountNumber){
	String[] accNrArr = FullAccountNumber.split("-");
	return accNrArr[1];
}

public static String getAccNrPartFromFullAccountNumber(String FullAccountNumber){
	String[] accNrArr = FullAccountNumber.split("-");
	return accNrArr[2];
}
public static String getAccNrPeriodCode(String FullAccountNumber){
	String[] accNrArr = FullAccountNumber.split("-");
	return accNrArr[3];
}
public static String getCheckDigitsPartFromFullAccountNumber(String FullAccountNumber){
	String[] accNrArr = FullAccountNumber.split("-");
	return accNrArr[4];
}


public static String GET_GUI_HIST_TASK_TRXID_BY_REQUEST_NUMBER(WebDriver driver, String RequestNumber){
	driver = ax.LOGIN_NA1(driver);
	return ApprovingRequests.HIS_GET_TRANSACTIONID_BY_REQUEST_NR(driver, RequestNumber);
}

public static String getFullAccountNumberByAccountIdentifier(String AccountIdentifier){
//	 Old way - SQL way ////////////////////////////////////
	String CheckDigitPart = AppSqlQueries.get_CHECKDIGIT_NUMBER_ByAccountIdentifier(AccountIdentifier);
	if (!CheckDigitPart.isEmpty()){
		return "EU-100-"+AccountIdentifier+"-0-"+CheckDigitPart;
	}else{
		return null;
	}
////////////////////////////////////	
}
public static String getFullAccountNumberByAccountIdentifier(WebDriver driver, String AccountIdentifier){
	return GET_FULL_ACCOUNT_NUMBER(driver, AccountIdentifier);
}


/*
	if (versionOnFooter.trim().substring(0, 11).equals("version 6.2")){
    	this.NaExclusive="NaExclusive";
    }else if(versionOnFooter.trim().substring(0, 11).equals("version 6.1")){
    	this.NaExclusive="";
    }
    	*/
	  
	public static String GET_DOWNLOAD_OUTPUT_FILE_NAME(String fileType, String OperationType){
		return GET_DOWNLOAD_OUTPUT_FILE_NAME(fileType, OperationType, ProjectDataVariables.EUCR_REG);
	}

	public static String GET_DOWNLOAD_OUTPUT_FILE_NAME(String fileType, String OperationType, String REGISTRY){
		
		String inputFile_fullPathName;
		
		if(SeleniumDriver.getCurrentBrowserType().equals("FIREFOX")){
			inputFile_fullPathName = ProjectDataVariables.OUTPUT_PDF_DIRECTORY + OperationType+"_"+DateOperations.date_GetDay(0)+"_"+DateOperations.date_GetCurrentMonth(0)+"_"+DateOperations.date_GetCurrentYear()+"_"+REGISTRY+"."+fileType;
		}else{
			inputFile_fullPathName = ProjectDataVariables.OUTPUT_PDF_DIRECTORY + OperationType+"_"+DateOperations.date_GetDay(0)+"-"+DateOperations.date_GetCurrentMonth(0)+"-"+DateOperations.date_GetCurrentYear()+"_"+REGISTRY+"."+fileType;
		}		
		ax.wait(1);
		ax.log("DOWNLOAD FILE WILL BE HERE: "+inputFile_fullPathName);
		return inputFile_fullPathName;
		
	}


	public static ArrayList<String> GET_RANDOM_ACCOUNT_IDENTIFIERS(String AccountType /*[OHA|AOHA|PHA"]*/, int howManyAccounts){
		// we assume the account should be in status open
		
		
		
		return null;
	}



	public static boolean DISPLAY_NUMBER_OF_RESULTS(WebDriver driver, By ContainerWithRecords_BY, String PaginatorTopId_string, int NumberOfresults /*[10|20|50]*/){
		return DISPLAY_NUMBER_OF_RESULTS(driver, ContainerWithRecords_BY, PaginatorTopId_string, NumberOfresults, 10);
	}
	public static boolean DISPLAY_NUMBER_OF_RESULTS(WebDriver driver, By ContainerWithRecords_BY, String PaginatorTopId_string, int NumberOfresults /*[10|20|50]*/, int ActualRecordsFound){
		// we assume the initial number of records i 10
		By CMBX_WITH_RANGE_TO_SET = By.xpath(PaginatorTopId_string + "//select[@class='ui-paginator-rpp-options']");
        int SizeOfTable=ToolFunctions.GET_ROW_SIZE_OF_TABLE_COLLECTION(driver, ContainerWithRecords_BY);
		final int size_0 = SizeOfTable; 
		ActualRecordsFound = ApplicationPageUtils.GetNumberOfRowsFound_Paginator_Current(driver, PaginatorTopId_string);
		if(ActualRecordsFound == 0) {return false;}
		ax.log("Actual Number of records found: " + ActualRecordsFound);

		int NumberToSet = 0;
		if(ActualRecordsFound >=20 && NumberOfresults>=20){
			NumberToSet = 50;
		}else if(ActualRecordsFound >=20 && NumberOfresults>20){
			NumberToSet = 10;
		}		
		else if(ActualRecordsFound > 10 && ActualRecordsFound < 20){
			NumberToSet = 20;
		}else{
			NumberToSet = 10;
			ax.log("Actual number of results found ["+ActualRecordsFound+"] are less than [10]");
		}
		if(SeleniumWebUtils.isElementDisplayed(driver, CMBX_WITH_RANGE_TO_SET) && SeleniumWebUtils.isElementEnabled(driver, CMBX_WITH_RANGE_TO_SET)){
			SEL.select(driver, CMBX_WITH_RANGE_TO_SET, "text", ToolFunctions.IntegerToString(NumberToSet));
			SeleniumWaiter.waitForAyaxLoad(driver);
			return true;
		}
		
		int time_counter = 0;
		int new_size=0;
		do {
			ax.wait(1);
			new_size = ToolFunctions.GET_ROW_SIZE_OF_TABLE_COLLECTION(driver, ContainerWithRecords_BY);
			time_counter++;
			if(time_counter > ProjectDataVariables.TEST_RETRIAL_FACTOR_40){
				ax.log("Waiting to long for displaying more data.. Quitting");
				
			}
		} while ( new_size <= size_0);
		return false;
		
	}	
	

	public static ArrayList<String> GRAB_FROM_TABLE_NTH_COLS_TO_ARRAYLIST_STRING(WebDriver driver, By BY_OF_THE_ROWSCONTAINER, int NumberOfTheColumn){
		ArrayList<String> ar = new ArrayList<String>(); 
		WebElement table_element = driver.findElement(BY_OF_THE_ROWSCONTAINER);
	    List<WebElement> tr_collection=table_element.findElements(By.tagName("tr"));
	    int row_num,col_num;
	    row_num=1;
	    for(WebElement trElement : tr_collection)
	    {
	        List<WebElement> td_collection=trElement.findElements(By.tagName("td"));
//	        ax.log("NUMBER OF COLUMNS="+td_collection.size());
	        col_num=1;
	        for(WebElement tdElement : td_collection)
	        {
//	        	ax.log("row # "+row_num+", col # "+col_num+ ". Text="+tdElement.getText());
	            col_num++;
	            if(col_num==(NumberOfTheColumn+1)){
	            	ar.add(tdElement.getText());
	            }
	        
	        }
	        row_num++;
	    }	
		
		return ar;
	}	
	

	public static boolean RESULT_IN_SEARCH_TABLE_NOT_EXIST(WebDriver driver, String PAGINATOR_TOP_ELEMENT_DIV){
//		We assume this check is done on displayed search results...
	    String rowReturned = SeleniumWebUtils.getTextFromElement(driver, By.xpath(PAGINATOR_TOP_ELEMENT_DIV + "//span[@class='ui-paginator-current']"));
		int intReturnedRows = ApplicationPageUtils.howManyRowsFound_PFTable(rowReturned);
    	if (intReturnedRows<1){
    		ax.info("Search record really not exists..");
    		return true;
    		}else{
       		ax.t_error("Search record really not exists..");
       		return false;
    		}
	}	
	

	public static boolean REDBOX_HANDLER(WebDriver driver){
		if( driver.getTitle().contains(" / Unrecoverable Error") && ax.isDisplayedBy(driver, By.xpath("//div[@id='unrecoverableErrorBox']"))){
			String RedBoxContent = ax.getText(driver, By.xpath("//div[@id='unrecoverableErrorBox']"));
			ax.takePhoto(driver, "REDBOX Error " + StringPageUtils.TO_ONE_ROW(RedBoxContent) );
			SeleniumWaiter.sleeper(5);
			ax.click_button_and_wait(driver, By.linkText("the homepage."));
			return false;
		}
		return true;
	}
	
	public static boolean SET_DISPLAY_OF_50_RECORDS(WebDriver driver, By CMBX_WITH_RANGE_TO_SET){
		if(SeleniumWebUtils.isElementDisplayed(driver, CMBX_WITH_RANGE_TO_SET) && SeleniumWebUtils.isElementEnabled(driver, CMBX_WITH_RANGE_TO_SET)){
			SEL.select(driver, CMBX_WITH_RANGE_TO_SET, "text", ToolFunctions.IntegerToString(50));
//			ax.waitForAjax(driver);
			ax.wait_for_ayax_2(driver);
//			SeleniumWaiter.waitForAyaxLoad(driver);
			return true;
		}else{
			ax.t_error(driver, "RPP select element is not displayed nor enabled"); return false;
		}
	}
	public static boolean SET_DISPLAY_OF_NEXT_XX_RECORDS(WebDriver driver){
		if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath(PF_PageElements.UI_PAGINATOR_NEXT_LINK_STRING)) ){
			ax.click_button_and_ayax_wait(driver, By.xpath(PF_PageElements.UI_PAGINATOR_NEXT_LINK_STRING));
			ax.wait_for_ayax_2(driver);
			return true;
		}else{
			ax.t_error(driver, "Next link element is not displayed nor enabled"); return false;
		}
	}	
	
	
	public static boolean CHECK_IF_ELEMENT_GOT_TEXT(WebDriver driver, By by){
		if(!ax.isDisplayedBy(driver, by)){return false;}
		return (driver.findElement(by).getText().trim().length() >0)?true:false;}
	
	
	public static String GET_TITLE_OF_FIRST_PANEL_BOX_ON_PAGE(WebDriver driver){
		String FirstPanel_top = "//div[contains(concat(' ',@class,' '),'ui-panel')][1]/div[1][contains(@id,'_header')]/span[@class='ui-panel-title']";
		List<WebElement> FirstPanelElm = 				driver.findElements(By.xpath(FirstPanel_top));
		if(FirstPanelElm.size() < 1){		return null;}
		else if(FirstPanelElm.size() == 1){	return ax.getText(driver, By.xpath(FirstPanel_top));}
		else if(FirstPanelElm.size() > 1){	return ax.getText_from_WebElement(driver, FirstPanelElm.get(0));}
		else{								return null;		}
	}
	
	
	public static boolean isCurrentUserNA(WebDriver driver){
		// GUI checking if current user is NA or is AR. In case of NA he should see all 4 acordeon menus
		if(ax.isDisplayed_And_Enabled(driver, By.xpath(EUCR_MenuNavigation.MENU_ACRD_EU_ETS))
				&&
				ax.isDisplayed_And_Enabled(driver, By.xpath(EUCR_MenuNavigation.MENU_ACRD_KPPROTCL))
				&&
				ax.isDisplayed_And_Enabled(driver, By.xpath(EUCR_MenuNavigation.MENU_ACRD_ADMINSTR))
				){
			return true;
		}
		return false;
	}
	
	
	
//////////////////////////////////////////	
}
