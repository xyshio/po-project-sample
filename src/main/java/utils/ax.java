package utils;
import static utils.SeleniumDriver.getDriver;
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.common.base.Function;

import ElementRepository.EucrLabel;
import ElementRepository.RepoEucr;
import utils.ProjectDataVariables;
import clima.start.RunExtentReporting;
import clima.start.Setuper;
import eu.esd.eucr.accountmanagement.ESD_AccountOperations;
import eu.esd.eucr.login.LoginToESD;
import eu.ets.EUTLPUBL.general.EUTLPUBLIC_OPERATIONS;
import eu.ets.eucr.login.EcasAuthentication;
import eu.ets.eucr.login.LoginToEUCR;
import eu.ets.eucr.menunavigation.EUCR_MenuNavigation;
import eu.ets.eucr.utils.excel.ExcelJxl;
import eu.ets.eutl.general.EUTL_MainWindow;
import eu.ets.messages.DisplayMessages;
import eu.ets.sql.AppSqlQueries;
import eu.ets.test.utils.LogTestPrinter;
import eu.ets.test.utils.TESTERCLASS;
import po.AbstractPage;
import po.EcasPage;
import po.EucrHomePage;
import utils.SeleniumDriver;
import po.TransactionsPoPage;
import po.UsersPoPage;

public class ax {
	public static String hideLogger 					= ProjectDataVariables.HIDE_LOGGER_LEVEL;
	public static final int wait_element_hard_limit 	= 10;
	public static final String debuger 					= ProjectDataVariables.FRAMEWORK_TEST_MODE;
	public static final String FAIL_ERROR_EXPECTED		= "FAIL_ERROR_EXPECTED";
	public static final String EXCEL_FILE_REPORT 		= ProjectDataVariables.GetExcelFileLocation_TestResults(ProjectDataVariables.TEST_SESSION_ID);
	public static final String LA_DOMAIN_ADRES 			= "192.168.3.2:7001";
    public static String LA_EUCR_URL 					= "http://"+LA_DOMAIN_ADRES+"/euregistry/"+ProjectDataVariables.EUCR_REG+"/index.xhtml";
    public static final String ExternalDomanBox 		= "//a[@title='External']/span[@class='external']/..";
    public static final String userNameOrEmailEditBox 	= "//input[@title='Username or e-mail address' and @id='username']";
    public static final String userPasswordEditBox 		= "//input[@title='Password' and @id='password']";
    public static final String userMobilePhoneEditBox 		= "//input[@name='mobilePhoneNumber' and @id='mobilePhoneNumber']";
    
    public static final String userLoginButton 			= "//input[@title='Login!' and @value='Login!']";
	public static final String notCurrentUserButton		= "//div[@class='not-current-user']/a[@title='Logout']";
    public static final String loginLinkLI_PIPE			= "//li[@class='pipe']/a[@title='Login']";
	
    private static int screenShotCounter = 1;
    
    // console printer
    public static ArrayList<LogTestPrinter> ltp = new ArrayList<>();
    
    
	private static HashMap<String, String> swapData = new HashMap<String, String>(); 
	public static String getSwapData(String key){ 		return swapData.get(key);}
	public static void setSwapData(String key, String value){ 			swapData.put(key, value);}
    
    public static String getValue(String key){ return getSwapData(key);}
    public static void setValue(String key, String value){ setSwapData(key, value);}
	
    public static String getScreenshotName(){
    	return getScreenshotName("");
    }
    public static String getScreenshotName(String path){
		if(path.isEmpty()){
			return "screenShot"+GET_NOW()+"_"+screenShotCounter++;
		} 
		return path + "screenShot"+GET_NOW()+"_"+screenShotCounter++;
	}
    
	public static void print(String text){ ax.log(text); }
	public static void LOGGER_EUCR_ACTION(String text){ 
		ax.log("");
		ax.log("==>  ==>  ==>  ==>  ==>  ==>  ==>  ==>  ");
		ax.log("EUCR-ACTION: "+text);
		ax.log("");
		}
	public static void LOGGER_EUCR_SHOW_RESULT(String text){ 
		ax.log("");
		ax.log("==>  ==>  ==>  ==>  "+text);
		ax.log("");
		}
	
	public static void print_ars(ArrayList<String> ars){
		for(String a: ars){
			ax.log("- "+a.trim());
		}
	}
	public static String print_ars_one_line(ArrayList<String> ars){
		String out = "";
		for(String a: ars){
			out += a.trim()+". ";
		}
		return out;
	}
	public static String print_ars_as_parenthesed_params(ArrayList<String> ars){
		String f = "";
		for(String a: ars){
			f += "{"+a.trim()+"} ";
		}
		return f;
	}	
	public static void print_ars_double(ArrayList<ArrayList<String>> ars_double){
		for(ArrayList<String> ars_line: ars_double){
			for(String ars: ars_line){
			System.out.print(""+ars.trim()+ "\t");
			}
			ax.log("");
		}
	}
	
	public static String getBrowserCurrent(){
		return getBrowserCurrent(0);
	}
	public static String getBrowserCurrent(int which){
		switch (which) {
		case 0: return SeleniumDriver.getCurrentBrowserType();
		case 1: return SeleniumDriver.getCurrentBrowserType();
		case 2: return SeleniumDriver.getCurrentBrowserType(2);
		case 3: return SeleniumDriver.getCurrentBrowserType(3);
		case 4: SeleniumDriver.getCurrentBrowserType(4);
		default:		 return SeleniumDriver.getCurrentBrowserType();
		}
		
	}
	
	public static void print_ars_bools(ArrayList<Boolean> ars){
		ax.log("===[ boolean array start ]======================");
		for(boolean a: ars){
			ax.log("- "+a);
		}
		ax.log("===[ boolean array end ]======================");
	}
	
	public static String print_get_StringArray(String[] StringArray){
		String s = Arrays.toString(StringArray);
//		ax.log(s);
		return s;
	}
	
	public static void print_out_info_formatted_TOC(String info1, String info2){
		print_out_info_formatted_TOC(info1, 105, info2, 30);
	}
	
	public static void print_out_info_formatted_TOC(String info1, int info1length, String info2, int info2length){
		//org.apache.commons.lang3.StringUtils
		System.out.printf( "%-25s %25s %n", StringUtils.rightPad(info1+" ", info1length, '.').substring(0, info1length), StringUtils.leftPad(" "+info2, info2length, '.'));
	}
	
	public static void printLeads(String text, int longs, boolean secondPartTOC){
		printLeads(text, longs, ""+secondPartTOC);
	}
	  public static void printLeads(String text, int longs, String secondPartTOC){
		    int l = text.length();
		    String s  = text;
		    String ls = "";
		    if(l < longs){
		      int r = longs - l;
		      for(int i=0; i < r; i++){
		        ls+=".";
		      }
		    }
		    ax.log(s + ls + secondPartTOC); 
		  }
	
	
	public static ArrayList<ArrayList<String>> ExcelTo2DARLS(String fileName){
		return ExcelJxl.GetExcelTableInto2DArrayListString(fileName, false, true); // false=no-debug. true=skip1stRow
	}
	public static ArrayList<ArrayList<String>> ExcelTo2DARLS(String fileName, boolean skipp1stRow){
		return ExcelJxl.GetExcelTableInto2DArrayListString(fileName, false, skipp1stRow); // false=no-debug. true=skip1stRow
	}
	public static boolean ExcelCellUpdate(String FileLocation, int row, int col, String TextToPutInCELL){
		return ExcelJxl.UpdateExcelCellInFile(FileLocation, col, row,  TextToPutInCELL);
	}
	public static String randomString(){
		    return Long.toHexString(Double.doubleToLongBits(Math.random())).toUpperCase();
	}
	
	
	
	public static long randomNumber(int howManyChars){
		long i	=0;
		switch (howManyChars) {
		case 1: 			i=10; 			break;
		case 2: 			i=100; 			break;
		case 3: 			i=1000; 			break;
		case 4: 			i=10000; 			break;
		case 5: 			i=100000; 			break;
		case 6: 			i=1000000; 			break;
		case 7: 			i=10000000; 			break;
		case 8: 			i=100000000; 			break;
		case 9: 			i=1000000000; 			break;
		case 10: 			i=10000000000L;			break;
		default:
			break;
		}
		return  (0 + (int)(Math.random() * i));
	}
	public static String GET_CSV_ITEM_BY_INDEX(String CSVTEXT, int intex){
		String[] s = CSVTEXT.split(",");
		return s[intex-1].trim(); }
	public static void s(String text){ ax.log(text); }
	
	public static boolean click(WebDriver driver, By by) {
	    try {
	        
	    	(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
	        driver.findElement(by).click();
	        return true;
	        }
	    catch (StaleElementReferenceException sere) {
	        // simply retry finding the element in the refreshed DOM
	    	ax.log("Found stale element reference exception while clicking.. waiting 3 sec to click again");
	    	ax.wait(3);
	    	return ax.clickWhenReady(driver, by);
//	    	ax.click_StaleElement(driver, by);
//	        driver.findElement(by).click();
	    }
	    catch (TimeoutException toe) {
	        ax.debug("Element identified by " + by.toString() + " was not clickable after 10 seconds");
	        return false;
	    }
	}
	
	
	public static void clik(WebDriver driver, By by){
		clik(driver, by, hideLogger); 	}
	public static void clik(WebDriver driver, By by, String debugFactor){
		List<WebElement> e = driver.findElements(by);
		if(e.size()==0){
			int i=0;
			ax.log("Waiting for element "+by.toString());
				while(e.size()==0) {
					ax.wait(1);
					System.out.print("-->");
					e = driver.findElements(by);
					i++;
					if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_10) {
						ax.log("");
						SeleniumWebUtils.reportFAIL(driver, "..waiting too long for element "+by.toString()+", quiting...");
						ax.log("");
						return;
					}
			} 
				if(driver.findElements(by).size()==1){
					if(driver.findElement(by).isEnabled()){
//						driver.findElement(by).click();
						clickWhenReady(driver, by);
						if(!debugFactor.equals("HIDE")){
							ax.log("clicked "+by.toString());
						}
					}else{
						ax.log("");
						SeleniumWebUtils.reportFAIL(driver, "Element "+by.toString()+":1 exist, but is not enabled. Cannot do action");
						ax.log("");
						return;
					}
					return;
				}		
		}else if(e.size()==1){
			if(driver.findElement(by).isEnabled()){
				driver.findElement(by).click();
				if(!debugFactor.equals("HIDE")){
					ax.log("clicked "+by.toString());	
				}
			}else{
				ax.log("");
				SeleniumWebUtils.reportFAIL(driver, "Element "+by.toString()+":2 exist, but is not enabled. Cannot do action");
				ax.log("");
				return;
			}			
		}else if(e.size()>1){
			ax.log("more than 1 element "+by.toString()+" found, clicking first..");
			if(e.get(0).isEnabled()){
				e.get(0).click();	
				if(!debugFactor.equals("HIDE")){
					ax.log("clicked "+e.get(0).toString());
				}
			}else{
				ax.log("");
				SeleniumWebUtils.reportFAIL(driver, "First element  of "+e.get(0).toString()+":3 exist, but is not enabled. Cannot do action");
				ax.log("");
				return;
			}
		}
	}
	public static void click_StaleElement(WebDriver driver, By StaleElementBy){
		WebElement el= ax.getStaleElementByLocator2(driver, StaleElementBy);
		if(el.isDisplayed()){
			el.click();
		}else{
			ax.t_error(driver, "Element is not displayed.. cannot grabb it.. by: " +StaleElementBy+"");
		}
	}
	public static boolean click_webElement(WebDriver driver, WebElement element){
		if(element.isDisplayed()){
			element.click();
			return true;
		} else{
			ax.t_error(driver,"WebElement is not displayed! "+element.toString()+"");
			return false;
		}
	}
	public static boolean WAIT_FOR_LINK_ELEMENT_CLICK_AND_WAIT(WebDriver driver, By by){
		return CLICK_WEB_ELEMENT(driver, ax.WAIT4ElementDisplayed(driver, by));}
	
	public static boolean CLICK_WEB_ELEMENT(WebDriver driver, WebElement element){
		if(element==null){		return false;}
		if(element.isDisplayed() && element.isEnabled()){
			try {
				element.click();	return SeleniumWaiter.waitForPageLoad(driver);
									//waitForPageLoaded_boolean(driver);	
			} catch (Exception e) { return false;
			}
		}else{
			ax.log("ELEMENT NOT DISPLAYED-ENABLED");
			
			return false;}
	}
	
	public static boolean CLICK_ELEMENT_IF_ENABLED(WebDriver driver, By by){
		if(ax.isEnabledBy(driver, by)){
			return ax.click_button_and_wait_bool(driver, by);
		}else{return false;}
		
		
	}
	
	
	
//	public boolean waitForJStoLoad(WebDriver driver) {
//
//	    WebDriverWait wait = new WebDriverWait(driver, 30);
//
//	    // wait for jQuery to load
//	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
//	      @Override
//	      public Boolean apply(WebDriver driver) {
//	        try {
//	          return ((Long)executeJavaScript("return jQuery.active") == 0);
//	        }
//	        catch (Exception e) {
//	          return true;
//	        }
//	      }
//	    };
//
//	    // wait for Javascript to load
//	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
//	      @Override
//	      public Boolean apply(WebDriver driver) {
//	        return executeJavaScript("return document.readyState").toString().equals("complete");
//	      }
//	    };
//
//	  return wait.until(jQueryLoad) && wait.until(jsLoad);
//	}
	
	
	public static boolean clear(WebDriver driver, By by){
		if(!ax.isDisplayed_And_Enabled(driver, by)) return false;
		driver.findElement(by).clear();
		return true;
	}
	public static void type_clear(WebDriver driver, By type_by){
		if(driver.findElements(type_by).size()==1){
			driver.findElement(type_by).clear();
		}else if(driver.findElements(type_by).size()>1){
			driver.findElements(type_by).get(0).clear();
		}else{
			ax.info(  "no such type element to clear..");	
		}
	}
	
	public static boolean TRUE_IF_ELEMENT_DISPLAYED(WebDriver driver, By by){
		if(ax.isDisplayedBy(driver, by)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean type_with_clear(WebDriver driver, By type_by, String VALUE2TYPE){
		if(driver.findElements(type_by).size()==1){
			driver.findElement(type_by).clear();
//			ax.wait(1);
			driver.findElement(type_by).sendKeys(VALUE2TYPE);
			return true;
		}else if(driver.findElements(type_by).size()>1){
			driver.findElements(type_by).get(0).clear();
//			ax.wait(1);
			driver.findElements(type_by).get(0).sendKeys(VALUE2TYPE);
			return true;
		}else{
			ax.info(  "no such type element to type with clear..");
			return false;
		}
	}	
	public static void click_stale_link_and_wait(WebDriver driver, By link){
		WebElement wl = getStaleElementByLocator2(driver, link);
		if(ax.isDisplayedByWebElement(driver, wl)){
			ax.click_webElement(driver, wl);
			ax.WAIT4PageLoad(driver);
		} else{
			ax.t_error("button is not displayed!.. "+link.toString()+"");
		}
	}	
	

	
	
	
	public static boolean click_link_and_wait(WebDriver driver, By BY_LINK){
		return click_link_and_wait(driver, BY_LINK, 0);
	}
	public static boolean click_link_and_wait(WebDriver driver, By BY_LINK, int waitCount){
		int i=waitCount;
		
		if(ax.isDisplayed_And_Enabled(driver, BY_LINK)){
//			ax.clik(driver, BY_LINK);
			driver.findElement(BY_LINK).click();
			ax.WAIT4PageLoad(driver);
			return true;
		} else{
			if(i==0){
				ax.log("");
				System.out.print("Waiting for element [ "+BY_LINK.toString()+" ]");
			}	
			if(i>ProjectDataVariables.TEST_WAITER__){
				ax.log("");
				ax.t_error("LINK IS NOT DISPLAYED!.. "+BY_LINK.toString()+"");
				return false;	
			}else{
				
				System.out.print(".");
				ax.wait(1);
				i++;
				return click_link_and_wait(driver, BY_LINK, i);
			}
			
			
		}
	}
	
	public static boolean click_js(WebDriver driver, By by){
		return CLICK_JS_EXE_element_by(driver, by);
	}
	public static boolean click_js_wait_for_page(WebDriver driver, By by){
		return CLICK_JS_EXE_element_by_wait_for_page(driver, by);
	}
	public static boolean click_js_wait_for_ajax(WebDriver driver, By by){
		return CLICK_JS_EXE_element_by_wait_for_ayaxLoader(driver, by);
	}
	public static boolean type_js(WebDriver driver, By by, String text){
		return TYPE_JS_EXE_element_by(driver, by, text);
	}
	public static boolean select_js(WebDriver driver, String id, String index){
		// SO FAR WORKS ONLY FOR SELECTOR WITH ID...
		return SELECT_JS_EXE(driver, "id", id, index);
	}
//	public static boolean select_js_xpath(WebDriver driver, String xpath, String index){
//		return SELECT_JS_EXE_element_id(driver, id, index);
//	}
	
	
	
	
	public static boolean clickLink(WebDriver driver, By LinkBy) {
		if(driver.findElements(LinkBy).size() == 1){
			driver.findElement(LinkBy).click(); 
			return true;
		}else if(driver.findElements(LinkBy).size() > 1){
			driver.findElements(LinkBy).get(0).click();
			return true;
		}
		else{
			throw new NoSuchElementException("Link Not Found for: " + LinkBy.toString());
		}
			
	}
	
	
	public static void click_button_and_wait(WebDriver driver, By button){
		if(ax.isDisplayed_And_Enabled(driver, button)){
			ax.clickWhenReady(driver, button);
//			ax.clik(driver, button);
			ax.WAIT4PageLoad(driver);
		} else{
			ax.t_error("button is not displayed!.. "+button.toString()+"");
		}
	}
	public static boolean click_button_and_wait_bool(WebDriver driver, By button){
		if(ax.isDisplayedBy(driver, button)){
			try{
				driver.findElement(button).click();
			}catch(Exception e){
				if(e.getMessage().contains("Element is not clickable at point")){ 
					ax.info("Element is not clickable at point.. exception");
					int width = driver.manage().window().getSize().width;
					int height = driver.manage().window().getSize().height;
					driver.manage().window().maximize(); 
					ax.wait(1);
					driver.findElement(button).click();
					ax.wait(1);
					driver.manage().window().setSize(new Dimension(width, height));
					ax.wait(1);
				}
			}
			return true;
		} else{ 	ax.t_error(driver, "button is not displayed!.. "+button.toString()+""); return false; }
	}
	
	public static boolean click_button(WebDriver driver, By button){
		return ax.click_button_and_wait_bool(driver, button); }
	
	public static boolean click_button_if_displayed(WebDriver driver, By by){
		if(ax.isDisplayed_And_Enabled(driver, by)){
			return ax.click_button(driver, by);
		} else{
//			ax.log("Element to click not exists/displayed ["+by.toString()+"]");
			return false;	
		}
	}
	
	public static boolean click_button_and_ayax_wait(WebDriver driver, By button){
		WebElement w = getStaleElementByLocator2(driver, button);
		
		int i=0;
		while(!w.isDisplayed()){
			i++;ax.wait(1);
			if (i>ProjectDataVariables.TEST_RETRIAL_FACTOR_20){ax.t_error(driver, "Waiting too long to click "+button.toString());return false;}
		}
		w = getStaleElementByLocator2(driver, button);
		
		if( ax.WAIT4ElementDisplayed(driver, button).isDisplayed() ){ // ax.isDisplayedByWebElement(driver, w) &&
//			ax.wait(1);
			ax.debug("DEBUG.... waiting for stale element "+button.toString());
			clickByLocator(driver, button);
			ax.debug("DEBUG.... finished.. waiting for stale element "+button.toString());
//				ax.click_webElement(driver, w);
			
			
//			ax.click_webElement(driver, w);
			
			ax.WAIT4PageLoad(driver);
			ax.WAIT4AyaxLoad(driver);
			return true;
		} else{
			ax.t_error("element is not displayed!.. "+button.toString()+"");
			return false;
		}
	}
	
	public static void clickByLocator (WebDriver driver,  final By locator ) {
		  WebElement myDynamicElement = ( new WebDriverWait(driver, 10))
		    .until( ExpectedConditions.presenceOfElementLocated( locator ) );
		  myDynamicElement.click();
		}	
	
	public static WebElement staleElementWaiter(WebDriver driver, final By by){
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			    .withTimeout(30, TimeUnit.SECONDS)
			    .pollingEvery(2, TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
			WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			    public WebElement apply(WebDriver driver) {
			        return driver.findElement(by);
			    }
			});
			return null;
	}
	
	
//	public static final Condition hidden = new Condition("hidden", true) {
//	    public boolean apply(WebElement element) {
//	      try {
//	        return !element.isDisplayed();
//	      } catch (StaleElementReferenceException elementHasDisappeared) {
//	        return true;
//	      }
//	    }
//
//		@Override
//		public boolean isTrue(Context arg0) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//	  };	
	
	public static void type(WebDriver driver, By by, String Text){
		
		STALE_WAIT_FOR_BY(driver, by);
//		getStaleElementByLocator2(driver, by);
		int i=0;
		while(!ax.getStaleElementByLocator2(driver, by).isDisplayed()){
			ax.sleep(1);i++;
			if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_20);ax.t_error(driver, "Waiting too long for enter value in EDIT box "+by.toString());return;
		}
		
		
		// temporary replace type function
		type(driver, by, Text, hideLogger);
		
//		ax.TYPE_JS_EXE_element_by(driver, by, Text);
		
	}
	
	public static boolean type_bool(WebDriver driver, By by, String Text){
		return type_bool(driver, by, Text, 0);
	}
	
	public static boolean type_bool(WebDriver driver, By by, String Text, int trialNr){
		int i=0;
		
		if(trialNr > 5){
			ax.log("Too many trials because of StaleElementException error");
			return false;
		}
		try {
			while(!ax.WAIT4ElementDisplayed(driver, by).isDisplayed()){
				ax.sleep(1);i++;
				if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_20){
					return false;	
				}
			}
		} catch (Exception e) {
			ax.log("Cannot find element to type...");
			return false;
		}
		try{
			(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
			type(driver, by, Text, hideLogger);
	        return true;
		}
		catch (StaleElementReferenceException sere) {
	        // simply retry finding the element in the refreshed DOM
	    	ax.log("Found stale element reference exception while typing ["+Text+"]... waiting 3 sec to type again");
	    	ax.wait(3);
	    	return ax.type_bool(driver, by, Text, trialNr+1);
	    }
	    catch (TimeoutException toe) {
	        ax.debug("Element identified by " + by.toString() + " was not clickable after 10 seconds");
	        return false;
	    }
	}
	public static boolean type_clear_bool(WebDriver driver, By by, String Text){
		ax.wait_elm(driver, by);
		int i=0;
		while(!ax.WAIT4ElementDisplayed(driver, by).isDisplayed()){
			ax.sleep(1);
			ax.wait_elm(driver, by);
			if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_20) ax.returnWhenFalse(driver, "Field to clear is nor ready after 20 trials");
			i++;
		}
			ax.type_clear(driver, by);
//		clear(driver, by);
		return ax.type_bool(driver, by, Text);
	}	
	public static boolean x_VerifyByObjectPresent(WebDriver driver, By by) {
	    try {
	    	ax.turnOff_time(driver);
	    	boolean b = driver.findElement(by).isDisplayed();
	    	ax.turnOn_time(driver);
	    	return b;
	    } catch (Exception e) { 
	    	ax.turnOn_time(driver);
	    	return false;
	    }
	}
	
	public static boolean x_ClickIfExist(WebDriver driver, By by){
		if(ax.x_VerifyByObjectPresent(driver, by)){
			return ax.click_link_and_wait(driver, by, 5);
		} 	return false;
	}
	public static boolean x_TypeIfExist(WebDriver driver, By by, String txt){
		if(ax.x_VerifyByObjectPresent(driver, by)){
			return ax.type_bool(driver, by, txt);
		}	return false;
	}
	public static boolean x_SelectIfExist(WebDriver driver, By bySelect, String selectType, String IndexValueText){
			return ax.select(driver, bySelect, selectType, IndexValueText);
	}
	public static boolean x_SelectIfPartOfTextExist(WebDriver driver, By bySelect, String PartOfText){
		return ax.select_by_part_of_text_in_index(driver, bySelect, PartOfText);
}
	
	public static void type_in_webelement(WebElement elm, String Text){
		elm.sendKeys(Text);
	}
	
	public static boolean type_simple(WebDriver driver, By by, String text){
		if(driver.findElements(by).size()>0 && driver.findElement(by).isDisplayed() && driver.findElement(by).isEnabled()){
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(text);
			return true;
		} 
		ax.log("Element to simple type "+text+" is not displayed or blocked..... "+by.toString());
		return false;
	}
	
	
	public static void type(WebDriver driver, By by, String Text, String debugFactor){
		List<WebElement> e = driver.findElements(by);
		
		if(e.size()==0){
			int i=0;
			ax.log("Waiting for element "+by.toString());
				while(e.size()==0) {
					ax.sleep(1);
					System.out.print("-->");
					e = driver.findElements(by);
					i++;
					if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_10) {
						ax.log("");
						SeleniumWebUtils.reportFAIL(driver, "..waiting too long for element "+by.toString()+", quiting...");
						ax.log("");
						return;
					}
			} 
				if(driver.findElements(by).size()==1){
					if(driver.findElement(by).isEnabled()){
						driver.findElement(by).clear();
						driver.findElement(by).sendKeys(Text);
						if(!debugFactor.equals("HIDE")){
						ax.log( xLOG()+ "type in "+by.toString() + " [value: "+Text+"]");
						}
					}else{
						ax.log("");
						SeleniumWebUtils.reportFAIL(driver, "Element "+by.toString()+":1 exist, but is not enabled. Cannot do action");
						ax.log("");
						return;
					}
					return;
				}		
		}else if(e.size()==1){
			if(driver.findElement(by).isEnabled()){
				driver.findElement(by).clear();
				driver.findElement(by).sendKeys(Text);
				if(!debugFactor.equals("HIDE")){
					ax.log(xLOG()+ "typed in "+by.toString() + " [value: "+Text+"]");	
				}
				
				
			}else{
				ax.log("");
				SeleniumWebUtils.reportFAIL(driver, "Element "+by.toString()+":2 exist, but is not enabled. Cannot do action");
				ax.log("");
				return;
			}			
		}else if(e.size()>1){
			ax.log("more than 1 element "+by.toString()+" found, typing in first..");
			if(e.get(0).isEnabled()){
				e.get(0).clear();
				e.get(0).sendKeys(Text);
				if(!debugFactor.equals("HIDE")){
				ax.log(xLOG()+"typed in "+e.get(0).toString() + " [value: "+Text+"]");
				}
			}else{
				ax.log("");
				SeleniumWebUtils.reportFAIL(driver, "First element  of "+e.get(0).toString()+":3 exist, but is not enabled. Cannot do action");
				ax.log("");
				return;
			}
		}
	}	
	public static boolean checkon_if_displayed(WebDriver driver, By by){
		int n=0;
		while(!ax.isDisplayed_And_Enabled(driver, by)){
			ax.wait(1);
			n++;
			if( n > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ ax.t_error(driver, "Problem with Selecting element "+by.toString());  return false;}
			
		}
		ax.clik(driver, by);
		return true;
	}
	
	public static boolean checkoff_if_displayed(WebDriver driver, By by){
		if(ax.isSelectedCheckRadio(driver, by)){
			ax.clik(driver, by);
			return true;//ax.WAIT4Element_IS_UNCHECKED(driver, by);
		}else{
			return true;
		}
	}	
	
	
	public static WebElement STALE_WAIT_FOR_BY(WebDriver driver, By by){
		return ( new WebDriverWait(driver, 10)).until( ExpectedConditions.presenceOfElementLocated( by ) );
	}
	
	public static boolean select_if_displayed(WebDriver driver, By by, String type, String IndexValueText){
		try {
			STALE_WAIT_FOR_BY(driver, by); 	
		} catch (Exception e) {
			ax.t_error(driver, "Waiting too long for element "+by.toString());
			return false;
		}
		
		ax.WaitForAjaxToComplete_Quick(driver);
		
		int n=0;
		while(!ax.isDisplayed_And_Enabled(driver, by)){
			ax.wait(1);
			n++;
			if( n > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ ax.t_error(driver, "Problem with Selecting element "+by.toString());  return false;}
			
		}
			ax.select(driver, by, type, IndexValueText);
			ax.WaitForAjaxToComplete_Quick(driver);
		return true;
	}
	public static boolean type_if_displayed(WebDriver driver, By by, String typeContent){
		STALE_WAIT_FOR_BY(driver, by);
		int n=0;
		while(!ax.isDisplayed_And_Enabled(driver, by)){
			ax.wait(1);
			n++;
			if( n > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ ax.t_error(driver, "Problem with Selecting element "+by.toString());  return false;}
			
		}
			ax.type(driver, by, typeContent);
		return true;
	}
	public static void type_if_there_is_value(WebDriver driver, By by, String ValueToType){
		if(!ValueToType.equals("")){
			ax.type_with_clear(driver, by, ValueToType);
		}
	}
	public static void select_if_there_is_value(WebDriver driver, By by, String type, String IndexValueText){
		if(!IndexValueText.equals("")){
			ax.select_if_displayed(driver, by, type, IndexValueText);
		}
	}
	public static boolean select_check_if_there_option_exists(WebDriver driver, By selectBy, String type, String IndexValueText){
		return CHECK_SELECT_FOR_OPTION_PRESENT(driver, selectBy, type, IndexValueText);
	}
	
	
	
	public static void select_multi_select(WebDriver driver, By by, String type, ArrayList<String> IndexValueText_ARRAY){
		Select os = new Select(driver.findElement(by));
		os.deselectAll();
		for (String s1 : IndexValueText_ARRAY) {
			select(driver, by, type, s1);	
		}
	}
	
	public static boolean select_by_part_of_text_in_index(WebDriver driver, String SelectXPATH, String PartOftheOption){
		return select_by_part_of_text_in_index(driver, By.xpath(SelectXPATH), PartOftheOption);
	}
	
	public static boolean select_by_part_of_text_in_index(WebDriver driver, By bySelect, String PartOftheOption){
		int positionNumber = GET_POSITION_NUMBER_OF_SELECT_OPTION_THAT_CONTAINS_STRING(driver, bySelect, PartOftheOption);
		if( positionNumber < 0 ){return false;}
		return ax.selectBy_INDEX(driver, bySelect, String.valueOf(positionNumber));
	}
//	public static boolean select_by_part_of_text_in_text(WebDriver driver, String SelectXPATH, String PartOftheOption){
//		int positionNumber = GET_POSITION_NUMBER_OF_SELECT_OPTION_THAT_CONTAINS_STRING(driver, SelectXPATH, PartOftheOption);
//		if( positionNumber < 0 ){return false;}
//		return ax.select_if_displayed(driver, By.xpath(SelectXPATH),"text", String.valueOf(positionNumber));
//	}
//	public static boolean select_by_part_of_text_in_value(WebDriver driver, String SelectXPATH, String PartOftheOption){
//		int positionNumber = GET_POSITION_NUMBER_OF_SELECT_OPTION_THAT_CONTAINS_STRING(driver, SelectXPATH, PartOftheOption);
//		if( positionNumber < 0 ){return false;}
//		return ax.select_if_displayed(driver, By.xpath(SelectXPATH),"value", String.valueOf(positionNumber));
//	}

	
	public static int GET_POSITION_NUMBER_OF_SELECT_OPTION_THAT_CONTAINS_STRING(WebDriver driver, String SelectXPATH, String PartOftheOption){
		return GET_POSITION_NUMBER_OF_SELECT_OPTION_THAT_CONTAINS_STRING(driver, By.xpath(SelectXPATH), PartOftheOption);
	}
	
	public static int GET_POSITION_NUMBER_OF_SELECT_OPTION_THAT_CONTAINS_STRING(WebDriver driver, By by, String PartOftheOption){
		List<WebElement> select = driver.findElements( by ); 
		if(select.size()>0){
			List<WebElement> option_collection=select.get(0).findElements(By.xpath("option"));
			int i=0;
			for (WebElement option : option_collection) {
				if(option.getText().contains(PartOftheOption)){
//					ax.log("Found option element with ["+PartOftheOption+"] on position "+i); 
					return i;
				}
				i++;
			}
			ax.t_error("Cannot find option element with ["+PartOftheOption+"] in select element: ["+by.toString()+"]");
			return -1;
		}else{
			ax.t_error("Cannot find select element: ["+by.toString()+"]");
			return -1;
		}
	}
	
	public static String getProp(String KEY){
		return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, KEY);}	
	
	public static void select_multi_with_unselect(WebDriver driver, By by, String type, String IndexValueText){
		Select os = new Select(driver.findElement(by));
		os.deselectAll();
		select(driver, by, type, IndexValueText);
	}
	public static boolean select(WebDriver driver, By by, String type, String IndexValueText){
		
		if(!ax.CHECK_SELECT_FOR_OPTION_PRESENT(driver, by, type, IndexValueText)){
//			SeleniumWebUtils.reportFAIL(driver, "There is no such OPTION [type:"+type+" with value:"+IndexValueText+"] on SELECT element["+by.toString()+"]. In case of text checking this is strict check! not contains text");
			ax.takePhotoInLine(driver, "no option in select element found");
			return false;
		}
		List<WebElement> e = driver.findElements(by);
		if(e.size()==0){
			int i=0;
			ax.log("Waiting for element "+by.toString());
				while(e.size()==0) {
					ax.wait(1);
					System.out.print("-->");
					e = driver.findElements(by);
					i++;
					if(i>ax.wait_element_hard_limit) {
						ax.log("");
						SeleniumWebUtils.reportFAIL(driver, "..waiting too long for element "+by.toString()+", quiting...");
						ax.log("");
						return false;
					}
			} 
				if(driver.findElements(by).size()==1){
					if(driver.findElement(by).isEnabled()){
						switch (type) {
							case "index": return ax.selectBy_INDEX(driver, by, IndexValueText);
							case "value": return ax.selectBy_VALUE(driver, by, IndexValueText);
							case "text"	: return ax.selectBy_TEXT(driver, by, IndexValueText);
						}
							ax.debug("selecting "+by.toString()+" [selecting: "+IndexValueText+"]"); 
							return true;
						}else{
							ax.log("");
							SeleniumWebUtils.reportFAIL(driver, "Element "+by.toString()+":1 exist, but is not enabled. Cannot do action");
							ax.log("");
							return false;
					}
//					return;
				}		
		}else if(e.size()==1){
			if(driver.findElement(by).isEnabled()){
				switch (type) {
				case "index": return ax.selectBy_INDEX(driver, by, IndexValueText);
				case "value": return ax.selectBy_VALUE(driver, by, IndexValueText);
				case "text"	: return ax.selectBy_TEXT(driver, by, IndexValueText);
			}

				ax.debug("selecting "+by.toString()+" [selecting: "+IndexValueText+"]");
			}else{
				ax.log("");
				SeleniumWebUtils.reportFAIL(driver, "Element "+by.toString()+":2 exist, but is not enabled. Cannot do action");
				ax.log("");
				return false;
			}			
		}else if(e.size()>1){
			ax.log("more than 1 element "+by.toString()+" found, selecting first..");
			if(e.get(0).isEnabled()){
				switch (type) {
				case "index": new Select(e.get(0)).selectByIndex(Integer.parseInt(IndexValueText)); break;
				case "value": new Select(e.get(0)).selectByValue(IndexValueText); break;
				case "text"	: new Select(e.get(0)).selectByVisibleText(IndexValueText); break;
			}
				ax.debug("selecting "+e.get(0).toString()+" [selecting: "+IndexValueText+"]");
				
			}else{
				ax.log("");
				SeleniumWebUtils.reportFAIL(driver, "First element  of "+e.get(0).toString()+":3 exist, but is not enabled. Cannot do action");
				ax.log("");
				return false;
			}
		}
		return true;
	}	
	
	
	public static boolean selectBy_INDEX(WebDriver driver, By by, String IndexValueText){
		if(driver==null){return false;}
		if(driver.findElements(by).size()<1){return false;}
		WebElement s 	= driver.findElement(by);
		String tagName 	= s.getTagName();
		if(null != tagName || "select".equals(tagName.toLowerCase())){
			new Select(driver.findElement(by)).selectByIndex(Integer.parseInt(IndexValueText));
			return true;
		}
		return false;
	}
	public static boolean selectBy_VALUE(WebDriver driver, By by, String IndexValueText){
		if(driver==null){return false;}
		if(driver.findElements(by).size()<1){return false;}
		WebElement s 	= driver.findElement(by);
		String tagName 	= s.getTagName();
		if(null != tagName || "select".equals(tagName.toLowerCase())){
			new Select(driver.findElement(by)).selectByValue(IndexValueText);
			return true;
		}
		return false;
	}
	public static boolean selectBy_TEXT(WebDriver driver, By by, String IndexValueText){
		if(driver==null){return false;}
		if(driver.findElements(by).size()<1){return false;}
		WebElement s 	= driver.findElement(by);
		String tagName 	= s.getTagName();
		if(null != tagName || "select".equals(tagName.toLowerCase())){
			new Select(driver.findElement(by)).selectByVisibleText(IndexValueText);
			return true;
		}
		return false;
	}
	public static boolean CHECK_SELECT_FOR_OPTION_PRESENT(WebDriver driver, By selectBy, String typeOfSelectRecognising, String ElementSearched){
		String v 					= ElementSearched;
//		ax.wait_elm(driver, selectBy);
		ax.WAIT4ELEMENT_READY_STALEXCEPTION_PROFF(driver, selectBy);
		ax.turnOff_time(driver);
		if(driver.findElements(selectBy).size()<1) {
			ax.log("No such select element found ["+selectBy.toString()+"]");
			ax.turnOn_time(driver);
			return false;
		}
		WebElement selectx 			= driver.findElement(selectBy);
		List<WebElement> optionx	= selectx.findElements(By.tagName("option"));
		int option_nr = 1;
		for(WebElement optionone : optionx)
	    {
			if(optionone.getAttribute(typeOfSelectRecognising).equals(v)){
				ax.turnOn_time(driver);
				return true;
			}
			option_nr++;
	    }
		if(option_nr > optionx.size()){
			ax.log( "CANNOT find the position "+ElementSearched+" in the <<SELECT>> element ["+selectBy.toString()+"]" );
			ax.turnOn_time(driver);
			return false;	
		}
		ax.turnOn_time(driver);
		return false;
	}
	public static WebElement ffe(WebDriver driver, final By by){
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(ProjectDataVariables.TEST_RETRIAL_FACTOR_20, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		
		WebElement foo = wait.until(
				new Function <WebDriver, WebElement>(){
					public WebElement apply(WebDriver driver){
						return driver.findElement(by);
					}
				});
		return foo;
	}
	public static WebElement fluentWaitWith(WebDriver driver, final By by, int with, int pool){
	        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	                .withTimeout(with, TimeUnit.SECONDS)
	                .pollingEvery(pool, TimeUnit.SECONDS)
	                .ignoring(NoSuchElementException.class);

	        WebElement foo = wait.until(
	        		new Function<WebDriver, WebElement>() {
	        			public WebElement apply(WebDriver driver) {
	        				return driver.findElement(by);
	                				}
	                			}
	        			);
	         return  foo; 
	         }		
	
	
	public static boolean isFound(WebDriver driver, By by){
		return isFound(driver, by, 10);
	}
	
	public static boolean isFound(WebDriver driver, final By by, int howLong){
//		ax.log(ax.isDisplayed_And_Enabled(driver, by));
		
		try{
			new WebDriverWait(driver, howLong).until(
					new ExpectedCondition<Boolean>() {
						@Override
						public Boolean apply(WebDriver driver){
							return driver.findElement(by).isEnabled();
						}
					}
					);
			return true;
		}catch(Exception e){
			ax.log("Cannot find element");
			return false;
		}
	}


	
	
	public static WebElement getWhenVisible(WebDriver driver, By locator) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TEST_RETRIAL_FACTOR_10);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}
	public static boolean clickWhenReady(WebDriver driver, By locator) {
// #########[This also works !!] ####################################################		
//		WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TEST_RETRIAL_FACTOR_10);
//		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
//		if(ax.isDisplayed_And_Enabled(driver, locator, 0) && (!driver.findElement(locator).getTagName().isEmpty())){
//			retryingFindClick(driver, locator);
//		}else{
//			ax.log("Element is not fresh... StaleElementException...");ax.wait(3);
//			element.click();
//		}
// #########[This also works !!] ####################################################		
//		ax.log("Waiting " + waitForElementPresent(driver, locator, 10)+" for ["+locator.toString()+"]");
//		if(ax.wait_for_displayed_by(driver, locator)   &&  ax.waitForElementPresent(driver, locator, 10)){
//			retryingFindClick(driver, locator);
//		}else{
//			ax.t_error(driver, "Element is not displayed after 'stale' waiting for it...");
//		}
// #########[This also works !!] ####################################################		
		if(ax.WAIT4ELEMENT_READY_STALEXCEPTION_PROFF(driver, locator).isDisplayed()){
			driver.findElement(locator).click();
			return true;
		}else{
			ax.t_error(driver, "Element is not displayed after 'stale' waiting for it...");
			return false;
		}
		
		
	}
    // this works for Stale Exception	
	public static boolean retryingFindClick(WebDriver driver, By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
	}

	
	public static boolean retryingElementStale(WebDriver driver, By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 3) {
            try {
                driver.findElement(by).getAttribute("value");//getTagName();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
}	

    // this works for Stale Exception
	public static boolean waitForElementPresent(WebDriver driver, final By by, int timeout){ 
		  WebDriverWait wait = (WebDriverWait)new WebDriverWait(driver,timeout)
		                  .ignoring(StaleElementReferenceException.class); 
		  return wait.until(new ExpectedCondition<Boolean>(){ 
		    @Override 
		    public Boolean apply(WebDriver webDriver) { 
		      WebElement element = webDriver.findElement(by); 
		      return element != null && element.isDisplayed(); 
		    } 
		  }); 
		}
	

	public static WebElement get_elm(WebDriver driver, String xpath){
		if(ax.wait_elm(driver, xpath)){
			return driver.findElement(By.xpath(xpath));}
		return null;
	}
	public static WebElement get_elm(WebDriver driver, By by){
		if(ax.wait_elm(driver, by)){
			return driver.findElement(by);}
		return null;
	}
	
	public static boolean wait_elm(WebDriver driver, String xpath){
		return ax.wait4element(driver, By.xpath(xpath))!=null;
	}
	public static boolean wait_elm(WebDriver driver, WebElement elem){
		return ax.wait4element(driver, elem)!=null;
	}	
	public static boolean wait_elm(WebDriver driver, By by){
		return ax.wait4element(driver, by)!=null;
	}
	public static boolean wait_elm(WebDriver driver, By by, int timeLimit){
		return ax.wait4element(driver, by, timeLimit)!=null;
	}	
	public static boolean wait_elm(WebDriver driver, String xpath, int limitSeconds){
//		return ax.waitWhileStaleElement(driver, By.xpath(xpath));
		return ax.wait4element(driver, By.xpath(xpath), limitSeconds)!=null;
	}
	
	public static boolean wait_elm_click(WebDriver driver, String xpath){
		return wait_elm_click(driver, xpath, ax.wait_element_hard_limit);
	}
	public static boolean wait_elm_click(WebDriver driver, By by){
		return wait_elm_click(driver, by, ax.wait_element_hard_limit);
				//wait_elm_click(driver, by);
	}
	public static boolean wait_elm_click(WebDriver driver, String xpath, int limit){
		if(ax.wait4element(driver, By.xpath(xpath), limit)!=null){
			return ax.retryingFindClick(driver, By.xpath(xpath));
		}else{
			return false;
		}
	}
	public static boolean wait_elm_click(WebDriver driver, By by, int limit){
		if(ax.wait4element(driver, by, limit)!=null){
			return ax.retryingFindClick(driver, by);
		}else{
			return false;
		}
	}

	public static WebElement wait_elm(WebDriver driver, By by, boolean returnNow){
    	int limitWait = 5;
		
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    	int i=0;
    	while(i<limitWait){
    		try{
    			if(driver.findElements(by).size()>0){ // element exists
    				WebElement el = driver.findElement(by); 
    				// now check if element i displayed
    				if(el.isDisplayed()){
    					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    					return el;
    				}else{
    					// element not displayed
    					System.out.print(" "+i);
//    					break;
    				}
    			}else{
    				
    				if(returnNow) return null;
    				
    				// element not exists
    				System.out.print(" "+i);
//    				break;
    			}
    		}catch(Exception e){
    			if(e.getMessage().contains("element is not attached")){ 
					System.out.print("still element is stale.."); System.out.print(" > " + i );
    			}
    			System.out.print(" "+i);
    		}
    		wait(1);
    		i++;
    	}
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	return null;
    }
	
	public static boolean isOn(WebDriver driver, By by){
		return isOn(driver, by, false);
	}
	
	public static boolean isOn(WebDriver driver, By by, boolean checkNow){
		try{
			WebElement w = wait_elm(driver, by, checkNow);	
			if(w!=null){
					return true;
				}return false;
		}catch(Exception e){
			//ax.log("Problem finding element"); 
			return false;
		}
	}

	public static boolean heExists(WebDriver driver, By by, boolean CheckNow){
		if(CheckNow) ax.turnOff_time(driver);
		
		int howMuchWait = (CheckNow)?0:10;
		try{
			WebElement myDynamicElement = (new WebDriverWait(driver, howMuchWait)).until(ExpectedConditions.presenceOfElementLocated(by));
			if(CheckNow) ax.turnOn_time(driver);
			return myDynamicElement.isDisplayed();
		}catch(Exception e){
			if(CheckNow) ax.turnOn_time(driver);
			return false;
		}
	}
	
	public static boolean confirmPageTitleHeaderedDisplayed(WebDriver driver, String Title){
		return ax.WAIT_FOR_HEADER_PRESENT(driver, Title);
	}
	
	public static boolean WAIT_FOR_HEADER_PRESENT(WebDriver driver, String HEADER_TITLE){
		return ApplicationPageUtils.WAIT_FOR_PAGE_HEADER(driver, HEADER_TITLE);
	}
	
	public static boolean wait_elm_type(WebDriver driver, String xpath, String VALUE2TYPE){
		return ax.wait_elm_type(driver, xpath, VALUE2TYPE, ax.wait_element_hard_limit);
	}
	public static boolean wait_elm_type(WebDriver driver, String xpath, String VALUE2TYPE, int limit){
		if(ax.wait4element(driver, By.xpath(xpath), limit)!=null){
			return ax.type_with_clear(driver, By.xpath(xpath), VALUE2TYPE);
		}else{
			return false;
		}
	}
	
	public static boolean wait_elm_type(WebDriver driver, By by, String VALUE2TYPE){
		return wait_elm_type(driver, by, VALUE2TYPE, ax.wait_element_hard_limit);
	}
	public static boolean wait_elm_type(WebDriver driver, By by, String VALUE2TYPE, int limit){
		if(ax.wait4element(driver, by, limit)!=null){
			return ax.type_with_clear(driver, by, VALUE2TYPE);
		}else{
			return false;
		}
	}
	
	
	public static boolean wait_elm_select(WebDriver driver, String xpath, String selectValueType, String VALUE2SELECT){
		return ax.wait_elm_select(driver, xpath, selectValueType, VALUE2SELECT, ax.wait_element_hard_limit);
	}
	public static boolean wait_elm_select(WebDriver driver, String xpath, String selectValueType, String VALUE2SELECT, int limit){
		if(ax.wait4element(driver, By.xpath(xpath), limit)!=null){
			return ax.select(driver, By.xpath(xpath), selectValueType, VALUE2SELECT);
		}else{
			return false;
		}
	}
	
	
	public static boolean waitWhileStaleElement(WebDriver driver, By by){
		boolean break_it 	= true;
		boolean result 		= true;
		int i=0;
		while(true){
			break_it = true;
			try{
				//WebElement we = driver.findElement(by);
				ax.turnOffImplicitWaits(driver);
				if(driver.findElements(by).size()<1){
					break_it = false;
					
				}else{
					ax.debug("IS DISPLAYED: " + driver.findElements(by).get(0).isDisplayed());}
//					if(!we.isDisplayed()){break_it = false; 	System.out.print(" "+i); }
				ax.debug("Check for element: "+driver.findElements(by).get(0).getTagName());
				}
			catch(Exception e){
				if(e.getMessage().contains("element is not attached")){ 
					ax.debug("still element is stale.."); System.out.print(" > " + i ); 
					break_it = false;
				}else if(e.getMessage().contains("no such element")){
					ax.info("No such element "+by.toString()); 
					result = false;
					break_it = true;
				}
			}
			ax.turnOnImplicitWaits(driver);
			if(!result){return false;}
			if(break_it){ break;}
			ax.wait(1);
			i++;
			System.out.print(" "+i);
			if(i > ProjectDataVariables.TEST_RETRIAL_FACTOR_20){
				ax.t_error(driver, "Waiting too long for element to appear....["+by.toString()+"]");
				ax.log("");
			return false;
			}
		}
//		ax.log("");
		return break_it;
	}
	public static WebElement wait4element(WebDriver driver, By by){
		return wait4element(driver, by, 10); 
	}
	public static WebElement wait4element(WebDriver driver, By by, int limitWait){
    	int i=0;
    	while(i<limitWait){
    		try{
    			if(driver.findElements(by).size()>0){ // element exists
//    				WebElement el = driver.findElement(by); 
    				WebDriverWait wait = new WebDriverWait(driver, 10);
    				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    				if(element.isDisplayed()){
    					return element;
    				}else{
    					System.out.print(" "+i);
    				}
    			}else{
    				System.out.print(" "+i);
    			}
    		}catch(Exception e){
    			if(e.getMessage().contains("element is not attached")){ 
					System.out.print("still element is stale.."); System.out.print(" > " + i );
    			}
    			System.out.print(" "+i);
    		}
    		ax.wait(1);
    		i++;
    	}
    	return null;
    }	

	public static WebElement wait4element(WebDriver driver, WebElement element){
    	int limitWait = 10;
		SeleniumWebUtils.turnOffImplicitWaits(driver);
    	int i=0;
    	while(i<limitWait){
   				WebElement el = element; 
    				try{
       					if(el.isDisplayed()){
        					SeleniumWebUtils.turnOnImplicitWaits(driver);
        					return el;
        				}else{
        					System.out.print(" "+i);
        				}
    	    		}catch(Exception e){
    	    			if(e.getMessage().contains("element is not attached")){ 
    						System.out.print("still element is stale.."); System.out.print(" > " + i );
    	    			}
    	    			System.out.print(" "+i);
    	    		}
    				
    		ax.wait(1);
    		i++;
    	}
    	SeleniumWebUtils.turnOnImplicitWaits(driver);
    	return null;
    }	
	
	
//	public static WebElement getElementByLocatorX(final WebDriver driver, final By locator ) {
//		  ax.info( "Get element by locator: " + locator.toString() );  
//		  final long startTime = System.currentTimeMillis();
//		  Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
//		    .withTimeout(30, TimeUnit.SECONDS)
//		    .pollingEvery(5, TimeUnit.SECONDS)
//		    .ignoring( StaleElementReferenceException.class ) ;
//		  int tries = 0;
//		  boolean found = false;
//		  WebElement we = null;
//		  while ( (System.currentTimeMillis() - startTime) < 91000 ) {
//		   ax.info( "Searching for element. Try number " + (tries++) ); 
//		   try {
//		    we = wait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
//		    found = true;
//		    break;
//		   } catch ( StaleElementReferenceException e ) {      
//		    ax.info( "Stale element: \n" + e.getMessage() + "\n");
//		   }
//		  }
//		  long endTime = System.currentTimeMillis();
//		  long totalTime = endTime - startTime;
//		  if ( found ) {
//		   ax.info("Found element after waiting for " + totalTime + " milliseconds." );
//		  } else {
//		   ax.info( "Failed to find element after " + totalTime + " milliseconds." );
//		  }
//		  return we;
//		}	
//	public static WebElement getElementByLocatorY(WebDriver driver, By locator ) {
//		  ax.info( "Get element by locator: " + locator.toString() );  	
//		  long startTime = System.currentTimeMillis();
//		  driver.manage().timeouts().implicitlyWait( 9, TimeUnit.SECONDS );
//		  WebElement we = null;
//		  boolean unfound = true;
//		  int tries = 0;
//		  while ( unfound && tries < 20 ) {
//		    tries += 1;
//		    ax.info("Locating remaining time: " + (180-(9*(tries-1) )) + " seconds." );
//		    try {
//		      we = driver.findElement( locator );
//		      unfound = false; // FOUND IT
//		    } catch ( StaleElementReferenceException ser ) {						
//		      ax.info( "ERROR: Stale element. " + locator.toString() );
//		      unfound = true;
//		    } catch ( NoSuchElementException nse ) {						
//		      ax.info( "ERROR: No such element. " + locator.toString() );
//		      unfound = true;
//		    } catch ( Exception e ) {
//		      ax.info( e.getMessage() );
//		    }
//		  } 
//		  long endTime = System.currentTimeMillis();
//		  long totalTime = endTime - startTime;
//		  ax.info("Finished click after waiting for " + totalTime + " milliseconds.");
//		  driver.manage().timeouts().implicitlyWait( 5, TimeUnit.SECONDS );
//		  return we;
//		}	
	
	
	public static boolean waitE2E(WebDriver driver, By button, String elementToWaitForAfterCicking){
		return wait_for_button_click_it_and_wait_for_the_element(driver, button, elementToWaitForAfterCicking);
	}
	public static boolean wait_for_button_click_it_and_wait_for_the_element(WebDriver driver, By button, String elementToWaitForAfterCickingXpath){
		if(!ax.isDisplayed_And_Enabled(driver, button)) 		return false;
		if(!ax.click_button(driver, button)) 				return false;
		if(!ax.wait_elm(driver, elementToWaitForAfterCickingXpath)) 				return false;
		return true;
	}
	
	
	
	public static boolean waitAndClick(WebDriver driver, By by){
		if(ax.wait_elm(driver, by, true)!=null){
			ax.clickLink(driver, by);return true;
		}
		return false;
	}
	public static boolean waitAndEnterText(WebDriver driver, By by, String text){
		ax.wait4element(driver, by);
		WebElement w = ax.wait_elm(driver, by, true);
		if(w!=null){
			w.clear();
			w.sendKeys(text);
			return true;
		}
		return false;
	}
		
	protected static WebElement getElement(WebDriver driver, String locator) {
		boolean flag = false;
		if(locator.contains("/"))
			flag = true;
		if(driver.findElements(By.id(locator)).size() == 1){
			return driver.findElement(By.id(locator));
		}else if(driver.findElements(By.name(locator)).size() == 1 ){
			return driver.findElement(By.name(locator));
		}else if(!flag && driver.findElements(By.cssSelector(locator)).size() == 1){
			return driver.findElement(By.cssSelector(locator));
		}else if(driver.findElements(By.xpath(locator)).size() == 1){
			return driver.findElement(By.xpath(locator));
		}else
			throw new NoSuchElementException("No Such Element : " + locator);
		
	}
	
	public static void clickWhenReadyAndWaitForPageLoad(WebDriver driver, By locator) {
		ax.waitWhileStaleElement(driver, locator);
		WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TEST_RETRIAL_FACTOR_10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		if(ax.isDisplayed_And_Enabled(driver, locator, 0)){
//			element.click();
			clickWhenReady(driver, locator);
			ax.WAIT4PageLoad(driver);
		}else{ ax.info("Element is not ready to click: "+locator.toString());}
	}
	public static boolean clickLinkWhenReadyAndWaitForPageLoad(WebDriver driver, By locator) {
		ax.waitWhileStaleElement(driver, locator);
		WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TEST_RETRIAL_FACTOR_10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		if(ax.isDisplayed_And_Enabled(driver, locator, 0)){
//			element.click();
			clickWhenReady(driver, locator);
			ax.WAIT4PageLoad(driver); return true;
		}else{ ax.info("Element is not ready to click: "+locator.toString()); return false;}
	}
	
	public static boolean waitforAyaxJQuerySPINNER_CONTEINER(WebDriver driver){
		By spinner = By.id("spinner-conteiner");
		return ax.WAIT_4_ELEMENT_DISSAPEAR(driver, spinner);}
	
	public static void waitForAjax(WebDriver driver)  {
		waitForAjax(driver, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
	}
	public static void waitForAjax(WebDriver driver, int timeoutInSeconds)  {
//		  ax.log("Checking active ajax calls by calling jquery.active");
		    
		      if (driver instanceof JavascriptExecutor) {
			JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
		 
		        for (int i = 0; i< timeoutInSeconds; i++) 
		        {
			    Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
			    // return should be a number
			    if (numberOfAjaxConnections instanceof Long) {
			        Long n = (Long)numberOfAjaxConnections;
//			        ax.log("Number of active jquery ajax calls: " + n);
			        if (n.longValue() == 0L)
			       	  break;
			        }
		            ax.wait(1);
			    }
			}
			else {
				ax.log("Web driver: " + driver + " cannot execute javascript");
			}
		
		}	

    public static boolean isSelectedCheckRadio(WebDriver driver, By by) {
    	if(ax.isDisplayedBy(driver, by)){
    		if(driver.findElement(by).isSelected()){
    			return true;
    		}else{
    			//ax.log("Element "+by+" is not selected");
    			return false;
    		}
    	}else{
    		ax.info(driver, "Element "+by+" is not displayed");
    	return false;
    	}
    	
      }	
	

    public static boolean isError1(WebDriver driver, String MessageText) {
    	// TRUE - IF TEXT EXIST IN BY ELEMENT
    	// FALSE - IF TEXT NOT EXIST IN BY ELEMENT
    	List<WebElement> er = DisplayMessages.GET_ARRAY_MESSAGE_CHECK(driver, "error", MessageText);
    	if(er.size() > 0){
    		return true;
    	}else{
    		return false;
    	}
      }    
    public static boolean is_ERROR_DISPLAYED(WebDriver driver){
    	if (ax.isDisplayedBy(driver, RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_BY)){
    		return true; 
    		}
    	return false;}
    
    
    public static String ERROR_NUMBER_GETTER(String s){
    	return (s.contains(":"))?s.split("\\: ")[0]:null;
    }
    
    
    public static boolean isWarn1(WebDriver driver, String MessageText) {
    	// TRUE - IF TEXT EXIST IN BY ELEMENT
    	// FALSE - IF TEXT NOT EXIST IN BY ELEMENT
    	List<WebElement> er = DisplayMessages.GET_ARRAY_MESSAGE_CHECK(driver, "warn", MessageText);
    	if(er.size() > 0){
    		return true;
    	}else{
    		return false;
    	}
      }    
    public static boolean isInfo1(WebDriver driver, String MessageText) {
    	// TRUE - IF TEXT EXIST IN BY ELEMENT
    	// FALSE - IF TEXT NOT EXIST IN BY ELEMENT
    	List<WebElement> er = DisplayMessages.GET_ARRAY_MESSAGE_CHECK(driver, "info", MessageText);
    	if(er.size() > 0){
    		return true;
    	}else{
    		return false;
    	}
      }    
    
	public static boolean isPresentNow(WebDriver driver, WebElement elm) {
	    ax.turnOff_time(driver); 
		boolean s = elm.isDisplayed();  
		ax.turnOn_time(driver);
		return s;
	}
    
    public static boolean isEnabledBy(WebDriver driver, By by) {
    	List<WebElement> elements = driver.findElements(by);
    	int i = elements.size();
    	if(i==1){
    		if(driver.findElement(by).isEnabled()){
        		return true;
        	}else{
        		return false;
        	}	
    	}else if(i>1){
    		if( elements.get(0).isEnabled() ){
        		return true;
        	}else{
        		return false;
        	}	
    	}
    	else{
    		return false;
    	}
    }
    public static boolean isDisplayed_And_Enabled(WebDriver driver, By by){
    	return isDisplayed_And_Enabled(driver, by, 1);
    }
    public static boolean isDisplayed_And_Enabled(WebDriver driver, By by, int SkipLogging){
    	if(ax.isDisplayedBy(driver, by) && ax.isEnabledBy(driver, by)){
    		return true;
    	}else if(ax.isDisplayedBy(driver, by) && !ax.isEnabledBy(driver, by)){
    		if(SkipLogging==0){
    			if(ax.getProp("TEST_MODE").equals("DEBUG")){
    				ax.info(driver, "Element is displayed, but is not enabled! "+by.toString()+"");	
    			}
    		}
    		return false;
    	}else{
    		if(SkipLogging==1){
    			if(ax.getProp("TEST_MODE").equals("DEBUG")){
    				ax.info("Element is not displayed, and not enabled! "+by.toString()+"");
    			}
    		}else if(SkipLogging==0){
//    			ax.info("Element is not displayed, and not enabled! "+by.toString()+"");
    		}
    		return false;
    	}
    }
	public static boolean isDisplayedBy(WebDriver driver, By by) {
		return (!ax.isElementHiddenNow(driver, by))?true:false;
   	 	}
	public static boolean isDisplayedByXpath(WebDriver driver, String xpath) {
   	 	return (!ax.isElementHiddenNow(driver, By.xpath(xpath)))?true:false;}
	
	public static boolean isNotDisplayedBy(WebDriver driver, By by) {
   	 	return (ax.isElementHiddenNow(driver, by))?true:false;}
	
    public static boolean isDisplayedBySizeBigger(WebDriver driver,By by){
		return (driver.findElements(by).size()>0)?true:false;}  
	
	public static boolean WAIT_UNTIL_ELEMENT_GOT_DISSAPPEAR(WebDriver driver, By by){
		WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
		wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))); // ExpectedConditions.invisibilityOfElementLocated(by)
		if(!ax.isDisplayedBy(driver, by)){
			return true;
		}else{
			return false;
		}
	}
	
    public static boolean isElementHiddenNow(WebDriver driver, By by) {
        ax.turnOffImplicitWaits(driver);
        boolean result = false;
        try {
           result = ExpectedConditions.invisibilityOfElementLocated(by).apply(driver);
        }
        finally {
        	ax.turnOnImplicitWaits(driver);
        }
        return result;
    }    
    protected static void turnOffImplicitWaits(WebDriver driver) {
    	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }
    protected static void turnOnImplicitWaits(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }	
    public static boolean WAIT4PageLoad(WebDriver driver) {
    	return SeleniumWaiter.waitForPageLoaded_boolean(driver);
    }
    public static boolean WAIT4PageLoad(WebDriver driver, String titlePageContains) {
    	return SeleniumWaiter.waitForPageLoaded_boolean(driver, titlePageContains);
    }
    public static boolean WAIT4PageLoad_with_TargetPageTitle(WebDriver driver, String TargetPageTitle_part) {
    	if(ax.getBrowserCurrent().contains("MSIE")){
    		ax.wait(3);
    	}
    	WAIT4PageLoad(driver);
    	String title = driver.getTitle();
    	int i=0;
    	while(!driver.getTitle().contains(TargetPageTitle_part)){
    		ax.wait(1);
//    		System.out.print(" . ");
    		i++;
    		if(i> 20){
    			ax.log("[WAIT4PageLoad_with_TargetPageTitle] toot long, page: " + TargetPageTitle_part);
    			return false;
    		}
    	}
    	return true;
    }
    public static boolean wait_for(WebDriver driver, WebElement elm){
    	return SeleniumWebUtils.fluent_wait(driver, elm, 20, 1);
    }    
    public static boolean wait_for(WebDriver driver, String xpath){
    	return SeleniumWebUtils.fluent_wait(driver, By.xpath(xpath), 20, 1);
    }
    public static boolean wait_for(WebDriver driver, String xpath, int wait){
    	return SeleniumWebUtils.fluent_wait(driver, By.xpath(xpath), wait, 1);
    }
    public static boolean wait_for(WebDriver driver, By by, int wait){
    	return SeleniumWebUtils.fluent_wait(driver, by, wait, 1);
    }
    public static boolean wait_for(WebDriver driver, By by){
    	return SeleniumWebUtils.fluent_wait(driver, by, 20, 1);
    }  
    public static boolean wait_for_text_gone(WebDriver driver, String xp, int wait){
    	return SeleniumWebUtils.fluent_wait_text_gone(driver, xp, wait, 1);
    }  
    
    
    public static boolean waiterMsie(WebDriver driver){
		try {
	    	if(ax.getBrowserCurrent().contains("MSIE")){
				ax.wait(2);
				ax.wait_for(driver, RepoEucr.INFO_GREEN_BOX_UNV_XPATH);
			}
	    	return true;
		} catch (Exception e) {
			return false;
		}
    }
    
    public static boolean wait_for_element(WebDriver driver, By by){   	return waitWhileStaleElement(driver, by); }
    public static boolean wait_for_ayax_1(WebDriver driver){ return WaitForAjaxToComplete_Quick(driver);  } // SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
    public static boolean wait_for_ayax_2(WebDriver driver){ return WaitForAjaxToComplete_Slower(driver);  }// SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
	public static boolean WaitForAjaxToComplete_Quick(WebDriver driver) // SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
    {int i=0;
        while (true)
        {
            if ((Boolean) ((JavascriptExecutor)driver).executeScript("return jQuery.active == 0")){
                break;
            }
            if(i > ProjectDataVariables.TEST_RETRIAL_FACTOR_20) return ax.returnWhenFalse(driver, "Waiting for completing ayax-load");
            ax.sleep(1);
            i++;
        }
        return true;
    }
	public static boolean WaitForAjaxToComplete_Slower(WebDriver driver) { // SUPER-USEFULL - AYAX LOAD PAGE AND OTHER ELEMENTS
		Boolean we = null;
		Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
			    .withTimeout(30, TimeUnit.SECONDS)
			    .pollingEvery(5, TimeUnit.SECONDS)
			    .ignoring( StaleElementReferenceException.class ) ;
		try {
		ExpectedCondition<Boolean> isLoadingFalse = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
			Object obj = ((JavascriptExecutor) driver).executeScript("return !window.ajaxActive");
			Object jQueryActive = ((JavascriptExecutor) driver).executeScript("return jQuery.active;");
	
				if (obj != null && obj.toString().equals("true") && jQueryActive.toString().equals("0")){
				  return Boolean.valueOf(true);
				}else{
				  return false;
				}
			}
		};
			we = wait.until(isLoadingFalse);
		} catch (Exception e) {
			return false;
		}
		return we;
	}    
    
    
    public static boolean CLICK_IF_ELEMENT_IS_DISPLAYED(WebDriver driver, By by){
    	if(ax.isDisplayedBy(driver, by)){
			return ax.click_button_and_wait_bool(driver, by);
		} return true;}
    
//    public static void WAIT4PageLoad(WebDriver driver, final String debugFactor) {
//  	
//    	/*
//    	final long startTime = System.currentTimeMillis();
//  	     ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
//  	        public Boolean apply(WebDriver driver) {
//  	          return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//  	        }
//  	      };
//  	     Wait<WebDriver> wait = new WebDriverWait(driver,30);
//  	      try {
//  	              wait.until(expectation);
//  	      } catch(Throwable error) {
//  	              //assertFalse("Timeout waiting for Page Load Request to complete.",true);
//  	    	  SeleniumWebUtils.reportInfo("!!! Page was not loaded !!!");
//  	    	  TakePhoto.takePhoto(driver, "Page "+ driver.getTitle() +" could not be loaded in a timely fashion range");
//  	      }
//  	      long endTime   = System.currentTimeMillis();
//  	      long totalTime = endTime - startTime;
//  	      SeleniumWebUtils.reportInfo("CURRENT PAGE: ["+ driver.getTitle() +"].......[wait time takes: "+totalTime+"]");
//  	 */
//    	
////    	public void waitForPageLoad() {
//
//    	    Wait<WebDriver> wait = new WebDriverWait(driver, 30);
//    	    wait.until(new Function<WebDriver, Boolean>() {
//    	        public Boolean apply(WebDriver driver) {
//    	            if(!debugFactor.equals("HIDE")){
//    	            	ax.log("Current Window State       : "  + String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
//    	            }
//    	        	return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equals("complete");
//    	        }
//    	    });
////    	}
//    
//    
//    }      
	public static void WAIT4AyaxLoad(WebDriver driver) {
	    ExpectedCondition<Boolean> pageLoadCondition = new
	        ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	            }
	        };
	    WebDriverWait wait = new WebDriverWait(driver, 30);
	    wait.until(pageLoadCondition);
	}	
	
	
	
    public static WebElement WAIT4ElementReady(WebDriver driver, By by){
    ScheduledExecutorService s 	= ax.threadTimeWaiterStart();
  	  WebDriverWait wait 		= new WebDriverWait(driver, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
  	WebElement element;
  	  try {
  		element 		= wait.until(ExpectedConditions.elementToBeClickable(by));
	} catch (NoSuchElementException ne) {
		return null;
	} 
  	  ax.threadTimeWaiterStop(s);
  	  return element;
    }    
    public static boolean WAIT4Element_Got_CSS_class(WebDriver driver, By locatorname, String CSS_class_expected){
    	if(ax.isDisplayedBy(driver, locatorname)){
    		ax.log("Element is displayed.. checking for css class..");
    	}else{
    		ax.WAIT4ElementReady(driver, locatorname);
    	}
    	String classGrabbed = ax.getElementAttribute(driver, locatorname, "class");
    	String[] classGrabbedArray = classGrabbed.split(" ");
    	int n = 0;
    	while(!Arrays.asList(classGrabbedArray).contains(CSS_class_expected) ){
    		ax.wait(1);
    		n++;
    		classGrabbed = ax.getElementAttribute(driver, locatorname, "class");
    		classGrabbedArray = classGrabbed.split(" ");
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
    			ax.t_error(driver, "Waiting for css element - class.. too loong");
    			return false;
    		}
    	}
    	return true;
    }    

    public static boolean WAIT4Element_IS_UNCHECKED(WebDriver driver, By locatorname){
    	if( driver.findElements(locatorname).size() < 1 ){
    		ax.t_error(driver, "Element not exist"); return false;
    	}
    	ax.log("d");
    	
    	String     		CHECKED_ATRIBUTE = ax.getElementAttribute(driver, locatorname, "checked");
    	if(CHECKED_ATRIBUTE==null){return true;}
    	int n = 0;
    	while(CHECKED_ATRIBUTE.equals("checked") ||  CHECKED_ATRIBUTE.equals("")){
    		ax.wait(1);
    		n++;
//    		CHECKED_ATRIBUTE = driver.findElement(locatorname).getAttribute("checked");
    		CHECKED_ATRIBUTE = ax.getElementAttribute(driver, locatorname, "checked");
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
    			ax.t_error(driver, "Waiting for unchecked element .. too loong");
    			return false;
    		}
    	}
    	return true;
    }    
    
    public static boolean CSS_IS_ATRRIBUTE_VALUE_AT_ARRAYLIST_CSS(String CSS_ATRRIBUTES, String ATRUBUTE, String VALUE){
    	CSS_ATRRIBUTES = CSS_ATRRIBUTES.replaceAll("\\s", "");
    	ArrayList<String> axs = ax.GET_ARRAYLIST_BY_SEPARATOR( CSS_ATRRIBUTES, ";");
    	for(int i=0; i< axs.size(); i++){
    		if(axs.get(i).contains(ATRUBUTE)){
    			String x = axs.get(i); 
    			String EVERYTHING_AFTER = x.substring(ATRUBUTE.length());
    			if(EVERYTHING_AFTER.contains(VALUE)){
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public static boolean WAIT4Element_Got_CSS_VALUE(WebDriver driver, By locatorname, String CSS_Attribute, String CSS_value){
    	if( driver.findElements(locatorname).size() < 1 ){
    		ax.t_error(driver, "Element not exist"); return false;
    	}
//    	String CSS_ATRIBUTE = driver.findElement(locatorname).getCssValue(CSS_Attribute);
//    	String CSS_ATRIBUTE = driver.findElement(locatorname).getAttribute("class");
//    	String CSS_ATRIBUTE = 	ax.getCssClass(driver, locatorname);
    	String CSS_ATRIBUTE = ax.getCssStyle(driver, locatorname);
    	int n = 0;
    	while(!ax.CSS_IS_ATRRIBUTE_VALUE_AT_ARRAYLIST_CSS(CSS_ATRIBUTE, CSS_Attribute, CSS_value)){
    		ax.wait(1);
    		n++;
//    		CSS_ATRIBUTE = driver.findElement(locatorname).getCssValue(CSS_Attribute);
    		CSS_ATRIBUTE = ax.getCssStyle(driver, locatorname);
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
    			ax.t_error(driver, "Waiting for css element - class.. too loong");
    			return false;
    		}
    	}
    	return true;
    }      
    

    public static boolean isDisplayedByWebElement(WebDriver driver, WebElement element) {
    	if(element.isDisplayed()){
    		return true;
    	}else{
    		return false;
    	}
      } 
    
    public static boolean WAIT4Element_Got_CSS_VALUE(WebDriver driver, WebElement locatorname, String CSS_Attribute, String CSS_value){
    	if( !isDisplayedByWebElement(driver, locatorname)){
    		ax.t_error(driver, "Element not exist"); return false;
    	}
//    	String CSS_ATRIBUTE = driver.findElement(locatorname).getCssValue(CSS_Attribute);
//    	String CSS_ATRIBUTE = driver.findElement(locatorname).getAttribute("class");
//    	String CSS_ATRIBUTE = 	ax.getCssClass(driver, locatorname);
    	String CSS_ATRIBUTE = ax.getCssStyle(driver, locatorname);
    	int n = 0;
    	while(!ax.CSS_IS_ATRRIBUTE_VALUE_AT_ARRAYLIST_CSS(CSS_ATRIBUTE, CSS_Attribute, CSS_value)){
    		ax.wait(1);
    		n++;
//    		CSS_ATRIBUTE = driver.findElement(locatorname).getCssValue(CSS_Attribute);
    		CSS_ATRIBUTE = ax.getCssStyle(driver, locatorname);
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
    			ax.t_error(driver, "Waiting for css element - class.. too loong");
    			return false;
    		}
    	}
    	return true;
    }    
    public static boolean WAIT4Element_Got_CSS_PART_VALUE(WebDriver driver, By locatorname, String CSS_Attribute, String CSS_value){
    	if( driver.findElements(locatorname).size() < 1 ){
    		ax.t_error(driver, "Element not exist"); return false;
    	}
//    	String CSS_ATRIBUTE = driver.findElement(locatorname).getCssValue(CSS_Attribute);
//    	String CSS_ATRIBUTE = driver.findElement(locatorname).getAttribute("class");
    	String CSS_ATRIBUTE = ax.getCssClass(driver, locatorname);
    	int n = 0;
    	while(!(CSS_ATRIBUTE.contains(CSS_value)) ){
    		ax.wait(1);
    		n++;
    		CSS_ATRIBUTE = ax.getCssClass(driver, locatorname);
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
    			ax.t_error(driver, "Waiting for css element - class.. too loong");
    			return false;
    		}
    	}
    	return true;
    } 
    public static boolean WAIT4Element_GetRid_CSS_PART_VALUE(WebDriver driver, By locatorname, String CSS_Attribute, String CSS_value){
    	if( driver.findElements(locatorname).size() < 1 ){ ax.t_error(driver, "Element not exist"); return false;}
//    	String CSS_ATRIBUTE = driver.findElement(locatorname).getCssValue(CSS_Attribute);
    	String CSS_ATRIBUTE = ax.getCssClass(driver, locatorname);
    	
    	int n = 0;
    	while(CSS_ATRIBUTE.contains(CSS_value) ){
    		ax.wait(1);
    		n++;
    		CSS_ATRIBUTE = ax.getCssClass(driver, locatorname);
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
    			ax.t_error(driver, "Waiting for css element get rid of - "+CSS_value+" in css class.. too loong");
    			return false;
    		}
    	}
    	return true;
    } 
    
    
    
    public static boolean WAIT4Element_Got_TEXT_VALUE(WebDriver driver, By locatorname, String TEXT_VALUE){
    	ax.debug("WAIT4Element_Got_TEXT_VALUE: "+locatorname.toString());
    	ax.WAIT4ElementDisplayed(driver, locatorname);
    	if( driver.findElements(locatorname).size() < 1 ){
    		ax.t_error(driver, "Element not exist"); return false;
    	}
    	String text = ax.getText(driver, locatorname);
    	int n = 0;
    	while(text.trim().isEmpty() ){
    		ax.wait(1);
    		n++;
    		text = ax.getText(driver, locatorname);
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
    			ax.t_error(driver, "Waiting for element got the value ["+TEXT_VALUE+"] .. too loong");
    			return false;
    		}
    	}
    	if(TEXT_VALUE.equals("*")){    		ax.debug("Anny text displayed in " + locatorname.toString());return true;    	}
    	if(!text.contains(TEXT_VALUE)){
    		ax.t_error(driver, "Element has different value ["+text+"] than expected ["+TEXT_VALUE+"]");
    		return false;
    	}
    	System.out.print(".... TRUE");
    	return true;
    } 

    public static boolean WAIT4Element_Got_TEXT_VALUE_LONGER(WebDriver driver, By locatorname, int LONGER_THAN){
    	
    	ax.infoDEBUG("ENTER TO: WAIT4Element_Got_TEXT_VALUE_LONGER [than="+LONGER_THAN+"]: "+locatorname.toString());	
    	
    	if( driver.findElements(locatorname).size() < 1 ){ ax.t_error(driver, "Element not exist"); return false;}
    	String text = ax.getText(driver, locatorname);
    	int n = 0;
    	while(text.trim().isEmpty() ){
    		ax.wait(1);
    		n++;
    		text = ax.getText(driver, locatorname);
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ax.t_error(driver, "Waiting for element got the value longer [1] that ["+LONGER_THAN+"] .. too loong");return false;
    		}
    	}
    	if(text.trim().length() > LONGER_THAN){    		
    			ax.debug("Text displayed ["+text.trim()+"] in " + locatorname.toString());
    			return true;    	
    		}
    	int m = 0;
    	while(text.trim().length() <= LONGER_THAN ){
    		ax.wait(1);
    		m++;
    		text = ax.getText(driver, locatorname);
    		if(m>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ax.t_error(driver, "Waiting for element got the value longer [2] that ["+LONGER_THAN+"] .. too loong");return false;}
    	}
    	return true;
    } 
    
    public static boolean WAIT4Element_Got_SPECIFIC_TEXT_VALUE(WebDriver driver, By locatorname, String TEXT_VALUE){
    	ax.debug("ENTER TO WAIT4Element_Got_SPECIFIC_TEXT_VALUE ["+TEXT_VALUE+"]: in "+locatorname.toString());
    	if( driver.findElements(locatorname).size() < 1 ){
    		ax.t_error(driver, "Element not exist"); 
    		return false;
    		}
    	String text = ax.getText(driver, locatorname);
    	int n = 0;
    	ax.debug("TRIM: "+text.trim()+" isEmpty: "+text.trim().isEmpty());
    	while(text.trim().isEmpty() ){
    		ax.wait(1);
    		System.out.print("["+n+"]");
    		n++;
    		text = ax.getText(driver, locatorname);
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ax.t_error(driver, "Waiting for element got the value ["+TEXT_VALUE+"] .. too loong");return false;    		}
    	}
//    	if(TEXT_VALUE.equals("*")){    		ax.log("Anny text displayed in " + locatorname.toString());return true;    	}
    	int i=0;
    	while(!text.contains(TEXT_VALUE)){
    		ax.debug("text: "+text+"|TEXT_VALUE: "+TEXT_VALUE);
    		ax.wait(1);i++;
    		System.out.print("["+i+"]");
    		text = ax.getText(driver, locatorname);
    		if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_10){ax.t_error(driver, "Waiting for element got the specifc value ["+TEXT_VALUE+"] .. too loong");return false;    		}
    	}
    	
    	
//    	if(!text.contains(TEXT_VALUE)){
//    		ax.t_error(driver, "Element has different value ["+text+"] than expected ["+TEXT_VALUE+"]");
//    		return false;
//    	}
    	return true;
    } 
    
    
    public static WebElement WAIT4ElementDisplayed(WebDriver driver, By locatorname){
    	WebElement we = SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, locatorname);
    		if(ax.IS_NULL_OR_EMPTY_WEBELEMENT(we)){
    			return null;
    			}
    	return we; 
      }      
    public static WebElement WAIT4ElementDisplayed(WebDriver driver, By locatorname, int TimeForWaiting){
    	return SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, locatorname, TimeForWaiting);
      }      
    public static WebElement WAIT4ELEMENT_READY_STALEXCEPTION_PROFF(WebDriver driver, By by){
    	ax.staleElementWaiter(driver, by);
    	ax.wait(1);
    	int n=0;
    	while(!ax.getStaleElementByLocator2(driver, by).isDisplayed()){
    		ax.wait(1);
    		n++;
    		System.out.print(n+" ");
    		if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ 
    				ax.t_error(driver, "Element is not ready to grab.."); 
    				return null;
    			}
    	}
    	return ax.getStaleElementByLocator2(driver, by);
    	
    }
    
    
    public static boolean wait_for_button(WebDriver driver, String ContentXpath, String ButtonLabel ){
    	WebElement we = ax.WAIT4ElementDisplayed(driver, ApplicationPageUtils.BUTTON_ON_BUTTONCONTAINER(ContentXpath, ButtonLabel));
    	if(ax.IS_NULL_OR_EMPTY_WEBELEMENT(we)){return false;}
    	if(we.isDisplayed()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static boolean wait_for_displayed_by(WebDriver driver, By by){
    	try {
    		ax.WAIT4ElementDisplayed(driver, by).isDisplayed();
    		return true;
		} catch (Exception e) {
			return false;
		}
    }
    public static boolean wait_for_displayed_by(WebDriver driver, By by, int TimeForWaiting){
    	try {
    		ax.WAIT4ElementDisplayed(driver, by, TimeForWaiting).isDisplayed();
    		return true;
		} catch (Exception e) {
			return false;
		}
    }
    
	public static WebElement waitForVisibilityOfElementLocated(WebDriver driver, By by){
//		if(!ax.waitWhileStaleElement(driver, by)){return null;}
		try{
			WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TIMEX_FX_25);
			wait.until( ExpectedConditions.visibilityOfElementLocated(by));
			return driver.findElement(by);
  		} catch (Exception e) {
		      ax.fail(driver, "Function function [waitForVisibilityOfElementLocated] CANNOT FIND ELEMENT by: ["+by+"]   ");
		      return null;
		}
			
	}
    
	public static WebElement waitForVisibilityOfElementLocated(WebDriver driver, WebElement elm){
//		if(!ax.waitWhileStaleElement(driver, by)){return null;}
		try{
			WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TIMEX_FX_25);
			wait.until( ExpectedConditions.visibilityOf(elm));
			return elm;
  		} catch (Exception e) {
  			ax.fail(driver,  "Function function [waitForVisibilityOfElementLocated] cannot find the WebElement: ["+elm.getLocation()+"]   ");
		      return null;
		}
			
	} 
    public static boolean select_wait_until_select_got_selected(WebDriver driver, By by, String type, String IndexTextValue){
    	Select select = new Select(driver.findElement(by));
    	String h = select.getFirstSelectedOption().getText();
    	ax.log("Selected position... " + (ax.IS_NULL_OR_EMPTY_STRING(h)?"empty":h) );
    	int i=0;
    	while(!h.trim().toUpperCase().contains(IndexTextValue.toUpperCase())){
    		ax.sleep(1);
    		select = new Select(driver.findElement(by));
    		h = select.getOptions().get(i).getText().trim();
//    				.getFirstSelectedOption().getText();
    		ax.log("Selected position... " + (ax.IS_NULL_OR_EMPTY_STRING(h)?"empty":h) );
    		
    		if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_20){return false;}
    		i++;
    	}
    	return true;
    	
    }
    
    
    public static boolean WAIT_4_ELEMENT_DISSAPEAR(WebDriver driver, By by){
    	return SeleniumWaiter.waitUntilElementDissapear(driver, by);
    }
    
    
    
    
    public static boolean WHILE_WAIT_ON_ELEMENT_BY(WebDriver driver, By ByElement){
		Boolean waitForElement=ax.isDisplayedBy(driver, ByElement);
		int n=0;
		while(!waitForElement){
			ax.info("Not yet displayed element for: [["+ByElement.toString()+"]], We still waiting..");
			ax.wait(1);
			waitForElement=ax.isDisplayedBy(driver, ByElement);
			if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_40){
				ax.t_error(driver, "Waiting too long for this element [["+ByElement.toString()+"]]"); 
				return false;
				}
		}    	
		ax.info(" !!! Found Element [["+ByElement.toString()+"]]");
		return true;
    }
    public static boolean WHILE_WAIT_ON_ELEMENT_BY_AND_CLICK(WebDriver driver, By ByElement) throws InterruptedException{
		Boolean waitForElement=ax.isDisplayed_And_Enabled(driver, ByElement);
		int n=0;
		while(!waitForElement){
			ax.info("Not yet displayed element for: [["+ByElement.toString()+"]], We still waiting..");
			Thread.sleep(1000);
			waitForElement=ax.isDisplayed_And_Enabled(driver, ByElement);
			if(n>ProjectDataVariables.TEST_RETRIAL_FACTOR_40){
				ax.t_error(driver, "Waiting too long for this element [["+ByElement.toString()+"]]"); 
				return false;
				}
		}    	
		
		if(ax.isDisplayed_And_Enabled(driver, ByElement)){
			ax.clik(driver, ByElement);
			SeleniumWaiter.waitFor_AJAX_Dissapear(driver);
		}
		
		ax.info(" !!! Found And clicked element [["+ByElement.toString()+"]]");
		return true;
    }    
    public static void takePhoto(WebDriver driver, String withPhotoInfo){
    	TakePhoto.takePhoto(driver, withPhotoInfo);
    }
    public static void takePhotoInLine(WebDriver driver, String withPhotoInfo){
    	TakePhoto.takePhoto(driver, withPhotoInfo, "inline");
    }
    public static String t_error(String contx){
    	return SeleniumWebUtils.reportFail(xLOG()+contx);
    }
    public static void t_error_log_only(String contx){
    	ax.log("[ERR]: " + contx);
    }

    public static final boolean wait_for_home_page_eucr(WebDriver driver){
    	int i=0;
    	while(!PF_PageElements.IS_DISPLAYED_HOME_PAGE_EUCR(driver)){
    		ax.wait(1);
    		if(i > 30) return false;
    	}
    	return PF_PageElements.IS_DISPLAYED_HOME_PAGE_EUCR(driver);
    }
    
    
    
    
    public static boolean t_error_false(WebDriver driver, String contx){
    	SeleniumWebUtils.reportFAIL(driver, contx);
    	return false;
    }    
    
    public static String t_error(WebDriver driver, String contx){
    	return SeleniumWebUtils.reportFAIL(driver, contx);
    }
    public static String t_error_and_excel(WebDriver driver, String contx){
			ax.fail(contx);
    	return SeleniumWebUtils.reportFAIL(driver, contx);
    }
    public static String t_pass(String contx){
    	return SeleniumWebUtils.reportPass(xLOG()+contx);
    }
    public static String t_pass(WebDriver driver, String contx){
    	TakePhoto.takePhotoOnly(driver);
    	return SeleniumWebUtils.reportPass(xLOG()+contx);
    }
    public static void t_pass(String contx, int Excel){
    	if(Excel==1){
				ax.pass(contx);
    	}
    }
    public static String info(String info){
        return SeleniumWebUtils.reportInfo(info, 0);
    }
    public static void infoDEBUG(String info){
    	if(ax.getProp("TEST_MODE").equals("NORMAL")) return;
    	out.println(info);
    }
    public static boolean isDebugMode(){
    	return (ax.getProp("TEST_MODE").equals("DEBUG"));
    }
    
    
    public static void info(String info, int IMPORTANCE){
    	switch (IMPORTANCE) {
		case 0: ax.log(info); break;
		case 1: SeleniumWebUtils.reportInfo(info, 0); break;
		}
    }    
    public static void info(WebDriver driver, String info){
        SeleniumWebUtils.reportInfo(info, 0);
        takePhoto(driver, info);
    }    
    
    
///////////////////////////////////////////////////////////    
    public static void result(WebDriver driver, ExtentTest test, boolean result, String contx, String comment){
    	ltp = LogTestPrinter.addLTP(ltp, contx, result);
    	ax.log(contx + ": " + result);
    	if(result){
//    		ax.pass(driver, test, contx); 	// creating photo while reporting
    		ax.pass(test, contx);			// no photo
    	}else{
    		ax.fail(driver, test, contx, comment);
    	}
    }
    
///////////////////////////////////////////////////////////    
    public static void pass(String contx){
    	ExcelJxl.addRowPass(contx);
    }
    public static void pass(ExtentTest test, String contx){
    	ExcelJxl.addRowPass(contx);
    	if(test!=null){
    		test.pass(contx);	
    	}
    }
    public static void pass(WebDriver driver, ExtentTest test, String contx){
    	ExcelJxl.addRowPass(contx);
    	if(test!=null){
    		test.pass(contx + "&nbsp;&nbsp;&nbsp;" +ax.getPhoto(driver));
    	}
    }
    
    public static void report_pass(String info){
    	SeleniumWebUtils.report_TEST_PASSED(info);
    }    
////////////////////////////////////    
    public static void fail(ExtentTest test, String contx){
    	ExcelJxl.addRowFailComm(contx, "");
    	if(test!=null){
    		test.fail(contx);
    	}
    }
    public static void fail(WebDriver driver, String contx){
    	ax.log(contx + ax.getPhoto(driver));
    	ExcelJxl.addRowFail(contx);
    }
    
    public static void fail(ExtentTest test, String contx, String comment){
    	ExcelJxl.addRowFailComm(contx, comment);
    	if(test!=null){
    		test.fail(contx);
    	}
    }
    public static void fail(WebDriver driver, ExtentTest test, String contx){
    	if(test!=null){
    		fail(driver, test, contx, "");
    	}else{
    		ExcelJxl.addRowFail(contx);
    	}
    }
    public static void fail(WebDriver driver, ExtentTest test, String contx, String comment){
    	ExcelJxl.addRowFailComm(contx, comment);
    	if(test!=null){
    		test.fail(contx + "&nbsp;&nbsp;&nbsp;" +ax.getPhoto(driver));
    	}
    }
    public static void fail(String contx){
    	ExcelJxl.addRowFail(contx);
    }
    public static void fail(String contx, String comment){
    	ExcelJxl.addRowFailComm(contx, comment);
    }
    public static void report_fail(String info){
    	SeleniumWebUtils.report_TEST_FAIL(info);
    }
    public static void report_fail_group(String info){
    	SeleniumWebUtils.report_TEST_GROUP_FAIL(info);
    }    
////////////////////////////////////////    
	public static String getPhoto(WebDriver driver){
		String photo = "file:///" + TakePhoto.takePhoto(driver, "", "inline");
    	return "<a href='"+photo+"' target='_blind'><img src='"+photo+"' style='height:15px;width:50px;border:1px black solid'></a>";
	}
////////////////////////////////////////
    
    public static boolean returnWhenFalse(String info){
    	out.println("RETURN FALSE: Problem with: " + info); return false;
    } 
    public static boolean returnWhenFalse(WebDriver driver, String info){
    	ax.log("RETURN FALSE: returnWhenFalse with webdriverPhoto for " + info);
    	ax.takePhoto(driver, info);
    	return false;
    } 
    
    public static Object returnWhenNull(String info){
    	out.println("RETURN FALSE: Problem with: " + info); 
    	return null;
    } 
    public static Object returnWhenNull(WebDriver driver, String info){
    	ax.takePhoto(driver, "Problem with: " + info);
    	return null;
    } 
    public static void excel_fail(WebDriver driver, String contx){
    	TakePhoto.takePhoto(driver, contx);
    	ExcelJxl.addRowFail(contx);
    }
    public static void excel_h1(String contx){
    	ExcelJxl.addHeader1(contx);
    }
    public static void excel_h2(String contx){
    	ExcelJxl.addHeader2(contx);
    }
    public static ExtentTest reportHeader(ExtentReports extent, String contx){
    	ExcelJxl.addHeader2(contx);
    	return extent.createTest(contx);
    }
    public static void excel_h3(String contx){
    	ExcelJxl.addHeader3(contx);
    }
    
    public static ExtentTest test_group(ExtentReports extent, String mainTitle){
    	return test_group(extent, mainTitle, "", 1);
    }
    public static ExtentTest test_group(ExtentReports extent, String mainTitle, String secondTitle){
    	return test_group(extent, mainTitle, secondTitle, 1);
    }
    public static ExtentTest test_group(ExtentReports extent, String mainTitle, int headerLevel /*1,2,3*/){
    	return test_group(extent, mainTitle, "", headerLevel);
    }
    public static ExtentTest test_group(ExtentReports extent, String mainTitle, String secondTitle, int headerLevel /*1,2,3*/){
    	switch (headerLevel) {
		case 1: ExcelJxl.addHeader1(mainTitle); break;
		case 2: ExcelJxl.addHeader2(mainTitle); break;
		case 3: ExcelJxl.addHeader3(mainTitle); break;
		default: ExcelJxl.addHeader1(mainTitle); break;
			}
    	if(extent!=null){
    		return extent.createTest(mainTitle, secondTitle);	
    	}else{
    		return null;
    	}
    	
    }
    
    
    
    public static void excel_test_group(String contx){
    	excel_test_group(contx, 2);
    }
    
    public static void excel_test_group(String contx, int headerRange){
    	ax.spacer_small_start("TEST_GROUP: "+contx);

    	switch (headerRange) {
		case 1: ExcelJxl.addHeader1("TEST_GROUP: "+contx); break;
		case 2: ExcelJxl.addHeader2("TEST_GROUP: "+contx); break;
		case 3: ExcelJxl.addHeader3("TEST_GROUP: "+contx); break;
		default: ExcelJxl.addHeader2("TEST_GROUP: "+contx); 	break;
		}
    }
    
    public static void excel_note(String contx){
			ExcelJxl.addRow(contx, "");
    }
    public static void excel_data(String KEY, String VALUE){
    	ExcelJxl.addRow(KEY, VALUE);
    }
    public static void excel_data(String key, String value, String comments){
    	ExcelJxl.addRowData(key, value, comments);
    }
    
//    public static void excel_data(String KEY, String VALUE, String Comment){
//			ExcelTools.ADD_NOTE_DATA_TO_EXCELRESULT(EXCEL_FILE_REPORT, KEY, VALUE, Comment);
//    }
    public static boolean excel_report(String MESSAGE, boolean BooleanResult){
    	if(BooleanResult){
    		ax.pass(MESSAGE);
    		}else{
    		ax.fail(MESSAGE);
    		}
    	return BooleanResult;    	
    	}
    public static boolean excel_report_logger(String MESSAGE, boolean BooleanResult, ExtentTest logger){
    	if(BooleanResult){
    			RunExtentReporting.extentLogReporter(logger, MESSAGE, true);
    			ax.pass(MESSAGE);
    			return true;
    		}else{
    			RunExtentReporting.extentLogReporter(logger, MESSAGE, false);
    			ax.fail(MESSAGE);
    			return false;
    		}
    	}
    
    public static void excel_report_expected(String MESSAGE, boolean BooleanResult, String EXPECTED){
    	
    	if(BooleanResult){
    		 if(EXPECTED.equals("PASS")){
    			 ax.pass(MESSAGE); 
    		 }else{
    			 ax.fail(MESSAGE);	 
    		 }  
    	}else{
    		// failed result
    		if(EXPECTED.equals("PASS")){
    			 ax.fail(MESSAGE); 
   		 	}else{
   		 	 ax.pass(MESSAGE);
   		 	}
		}
    	ax.log("...");
    	ax.log("...");
    	ax.log("...");
    	ax.log("...");
    	ax.log("...");
    	ax.log("");
    	}
    
    
    public static WebDriver focusBrowser(int inst){
    	WebDriver driver;
    	switch (inst) {
		case 1: driver = Setuper.driver; 	break;
		case 2: driver = Setuper.driver2; 	break;
		case 3: driver = Setuper.driver3; 	break;
		case 4: driver = Setuper.driver4; 	break;
		default: driver = Setuper.driver; 	break;
		}
//    	return Setuper.focusDriver(driver);
    	return driver;
    }
    
    public static WebDriver focusDriver(WebDriver driver){
    	return Setuper.focusDriver(driver);
    }
    
    public static void fxenter(String info){
		ax.log("");
	    ax.log("#" + ax.xLOG()+"       START FUNCTION: " + info);
		ax.log("==================================================================================================================");
    }
    public static void fxenter_group(String info){
		ax.log("");
		ax.log("==================================================================================================================");
	    ax.log("#" + ax.xLOG()+"       START GROUP OF TESTS: " + info);
		ax.log("==================================================================================================================");
		ax.log("");
    }
    
    public static boolean fxresult(String info, boolean result){
    	if (result)
			return fxpass(info);
		else
			return fxfail(info);
    	}
    public static boolean fxresult(WebDriver driver, String info, boolean result){
    	if (result)
			return fxpass(info);
		else
			ax.takePhoto(driver, info);
			return fxfail(info);
    	}
    public static boolean fxresult_group(String info, boolean result){
    	if (result)
			return fxpass_group(info);
		else
			return fxfail_group(info);
    	}
    
    
    public static boolean fxresult_group_with_display(String info, TreeMap<String, Boolean> TREEMAP){
    	ax.fxenter_group(info);
    	return ax.fxresult_group("GROUP: "+info, TESTERCLASS.PRINT_TESTBOX_MAP(TREEMAP));
    }
    
    
    public static boolean fxpass(String info){
		ax.log("");
	    ax.log("#" + ax.xLOG()+"       EXIT FUNCTION [PASSED]: " + info);
		ax.log("// v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v ");
		ax.log("");
		return true;
    }
    public static boolean fxfail(String info){
		ax.log("");
	    ax.log("#" + ax.xLOG()+"       EXIT FUNCTION [FAILED]: " + info);
		ax.log("// x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x");
		ax.log("");
		return false;
    }
    public static boolean fxpass_group(String info){
		ax.log("");
	    ax.log("#" + ax.xLOG()+"       EXIT GROUP BLOCK WITH RESULT [PASSED]: " + info);
		ax.log("// v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v ");
		ax.log("// v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v v ");
		ax.log("");
		return true;
    }
    public static boolean fxfail_group(String info){
		ax.log("");
	    ax.log("#" + ax.xLOG()+"       EXIT GROUP BLOCK WITH RESULT [FAILED]: " + info);
		ax.log("// x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x");
		ax.log("// x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x");
		ax.log("");
		return false;
    }
    public static void fx_exit_group(String info){
		ax.log("");
	    ax.log("#" + ax.xLOG()+"       EXIT GROUP BLOCK " + info);
		ax.log("// x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x");
		ax.log("// x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x");
		ax.log("");
		ax.log("");
		
    }
    
    
    public static void F_ENTER(String FXNAME){
		  SeleniumWebUtils.report_FUNCTION_ENTER(FXNAME);
	}
    public static void F_ENTER_BLOCK_FX(String FXNAME){
		  SeleniumWebUtils.report_FUNCTION_ENTER_BLOCK_GROUP(FXNAME);
	}
    public static void F_EXIT_BLOCK_FX(String FXNAME){
		  SeleniumWebUtils.report_FUNCTION_EXIT(FXNAME);
	}
    public static void F_ENTER(String FXNAME, int Importance){
		  SeleniumWebUtils.report_FUNCTION_ENTER(FXNAME, Importance);
	}
	public static void F_EXIT(String FXNAME){
		  SeleniumWebUtils.report_FUNCTION_EXIT(FXNAME);
	}
    public static void spacer(String text){
	   SeleniumWebUtils.SHOW_SPACER(text);
   } 
    public static void spacer_small(String text){
	   SeleniumWebUtils.SHOW_SPACER_SMALL(text);
   } 
    public static void spacer_small_start(String text){
    	ax.log("");
    	ax.log("");
    	ax.log("");
    	ax.log("//##############################################################################################################");
  	   ax.log(" + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | ");
  	   ax.log("# -| + | + | + | + | + | +  ####[START >> "+text+"]#### | + | + | + | + | + | + | + | + | + | + | + | + | + | + |");
  	   ax.log(" + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | ");
   	ax.log("//##############################################################################################################");
  	   ax.log("");
    } 

    public static void spacer_small_end(String text){
    	ax.log("");
    	ax.log("//##############################################################################################################");
 	   ax.log(" + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | ");
 	   ax.log("# -| + | + | + | + | + | +  ####[END >> "+text+"]#### | + | + | + | + | + | + | + | + | + | + | + | + | + | + |");
 	   ax.log(" + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | + | ");
   	ax.log("//##############################################################################################################");
 	   ax.log("");
 	   ax.log("");
   } 
    public static void log_milestone(String text){
	   ax.log("----------------------------------------------------------------------------------------------------");
	   ax.log("# -||-------------------       ####["+text+"]####      -------------------");
	   ax.log("----------------------------------------------------------------------------------------------------");
   }
    public static void log(String text){
	   System.out.println("["+GET_TIME_COLON_STAMP_SAVE() +"] "+ text);
   } 
    public static void log(WebDriver driver, String text){
    	TakePhoto.takePhoto(driver, text);
	   System.out.println("["+GET_TIME_COLON_STAMP_SAVE() +"] "+ text);
   } 
    public static void spacer_small(String text, int Importance){
	   SeleniumWebUtils.SHOW_SPACER_SMALL(text, Importance);
   } 
   public static boolean isNumber(String number){
    	if(number==null){
    		return false;
    	}
		if (!number.isEmpty() && (ToolFunctions.isStringNumeric(number))){	
		return true;
			}else{
		return false;
	}
   }
    public static String getText(WebDriver driver, By by){
    	SeleniumWebUtils.WaitForTextPresentInElement(driver, by);
    	return SeleniumWebUtils.getTextForElement(driver, by);	    	
   }  

    public static String getHiddenText(WebDriver driver, WebElement element){
        return (String) ((JavascriptExecutor) driver).executeScript(
            "return jQuery(arguments[0]).text();", element);
    }  
    
    public static boolean getTextIfContainsExpectedPhrase(WebDriver driver, By by, String expectedText){
    	if(!ax.isDisplayedBy(driver, by)){					return false;}
    	String tx = ax.getText(driver, by).trim();
    	if(tx.length() < 1){ 						return false;}
    	if(!tx.contains(expectedText)){     		return false;}
    	return true;
    }
    
    
    public static String getText_from_WebElement(WebDriver driver, WebElement elm){
	       return elm.getText();	    	
	   }  
    public static String getText_from_InputValue(WebDriver driver, By by){
       return driver.findElement(by).getAttribute("value");
   }  
   
    public static String getCssClass(WebDriver driver, By by){
    	try{
    		return driver.findElement(by).getAttribute("class");	
    	}catch(Exception e){
    		ax.log("Cannot get Class for element "+by.toString());
    		return null;
    	}
    }
    public static String getCssStyle(WebDriver driver, By by){
    	try{
    		return driver.findElement(by).getAttribute("style");	
    	}catch(Exception e){
    		ax.log("Cannot get style for element "+by.toString());
    		return null;
    	}
    }
    public static String getCssStyle(WebDriver driver, WebElement elm){
    	try{
    		return elm.getAttribute("style");	
    	}catch(Exception e){
    		ax.log("Cannot get style for element "+elm.toString());
    		return null;
    	}
    }
    
  public static String getElementAttribute(WebDriver driver, By by, String attribute){
	  try{
		 return driver.findElement(by).getAttribute(attribute);	
  	}catch(Exception e){
  		ax.log("Cannot get Class for element "+by.toString());
  		return null;
  	}
	  
  }
    
    
    
    public static String GET_URID_FROM_LOGGED_IN_DISPLAY(WebDriver driver){
    	return PageCanvas.getAuthGrid_URID(driver);}
    public static String GET_USERNAME_FROM_LOGGED_IN_DISPLAY(WebDriver driver){
    	return PageCanvas.getApplicationLoggedIn_Username(driver);}
    
    public static String GET_WORD_AFTER_TEXT(String WholeTEXT, String WordsBeforeSearched){
	   return GET_WORD_AFTER_TEXT(WholeTEXT, WordsBeforeSearched, 0);
   }
    public static String GET_WORD_AFTER_TEXT(String WholeTEXT, String WordsBeforeSearched, int OFFSET){
	   return SeleniumWebUtils.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(WholeTEXT, WordsBeforeSearched, OFFSET);
   }
    
    public static String getWordBeforeMarker(String txt, String marker){
        String[] a = null;
        String code=null;
        if(txt.contains(marker)){
         a = txt.split(marker);  
         String[] ss = a[0].split("\\s+");
         code = ss[ss.length-1];
        }else{return null;}
        return  code;
      }
	public static String GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(String monit, String TEXT_BEFORE){
		return GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monit, TEXT_BEFORE, 0);
	}
	public static String GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(String monit, String TEXT_BEFORE, int OFFSET){
		
		monit = monit.replaceAll("\\. ", " ");
		monit = monit.replaceAll("\\.", " ");
		String[] ALL_MONIT_ARRAY = monit.split(" ");
		String[] TEXT_ARRAY = TEXT_BEFORE.split(" ");
		// Your unique Registry Identifier is BG587402353668. Please communicate
		// Registry Identifier is
		String graber = null;
		String RETURNER = null;
		ArrayList<String> ALL_MONIT_ARRAY_AL = new ArrayList<String>(Arrays.asList(ALL_MONIT_ARRAY));
		ArrayList<String> TEXT_ARRAY_AL = new ArrayList<String>(Arrays.asList(TEXT_ARRAY));
		int wNum=TEXT_ARRAY_AL.size();
		int indexFound;
			if(ALL_MONIT_ARRAY_AL.containsAll(TEXT_ARRAY_AL)){
//				ax.log("MAM");
//				indexFound = ALL_MONIT_ARRAY_AL.indexOf(TEXT_ARRAY_AL.get(OFFSET));
				indexFound = ALL_MONIT_ARRAY_AL.indexOf(TEXT_ARRAY_AL.get(0));
				try {
					graber = ALL_MONIT_ARRAY_AL.get(indexFound + OFFSET + TEXT_ARRAY_AL.size()  );	
				} catch (IndexOutOfBoundsException i) {
					ax.log("To big offset "+OFFSET);
					return null;
				}
				
				
				if(!graber.isEmpty()){
					RETURNER =  graber.replaceAll("\\.", "");
					ax.log("Grabbed number: "+RETURNER+"");
					return RETURNER;
				}else{
					return null;
				}
			}else{
				ax.log("NULL: Lack of such data for " + TEXT_BEFORE);
			}
		return null;
	}    
    
    
    
    public static int getIndexFromRowIndexString(String wholeText){
    	String index = ax.findTextInOtherText(wholeText, "[rowIndex:", "]");
    	if( isNumeric(index)  ){
    		return Integer.valueOf(index);
    	}
    	return -1;
    }
    
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
    public static String findTextInOtherText(String wholeText, String startingWord, String endingWord){
        int startTextPosEndLen = wholeText.indexOf(startingWord) + startingWord.length();
        int endText = wholeText.indexOf(endingWord, startTextPosEndLen);
        return wholeText.substring(startTextPosEndLen, endText);
      }
    

    public static WebElement findMe(WebDriver driver, String xp) {
    	return findMe(driver, By.xpath(xp));
    }
	 public static WebElement findMe(WebDriver driver, By by) {
		  List<WebElement> w = driver.findElements(by);
		  if(w.size() < 1) {
			  ax.log("not found element ["+by.toString()+"]");
			  return null;
		  }else if(w.size()>1) {
			  ax.log("Warning: there are more than 1 element found for ["+by.toString()+"]");
		  }
		  if(w.get(0)==null){
			  ax.log("found element ["+by.toString()+"], but is null");
			  return null;
		  }else if(w.get(0)!=null && (
				  !driver.findElement(by).isDisplayed()
				  ||
				  !driver.findElement(by).isEnabled()
				  )){
			  ax.log("element ["+by.toString()+"], is not Displayed or not Enabled!");
			  return null;
		  }else{
			  return w.get(0);
		  }
	 }
    
	 public static boolean _clickMe(WebDriver driver, String xp){
		 return _clickMe(driver, By.xpath(xp));
	 }
	 public static boolean _clickMe(WebDriver driver, By by){
		 WebElement w = ax.findMe(driver, by);
		 if(w!=null) {
			 	try {
				 	w.click();	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with clicking on ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2click on ["+by.toString()+"]");
	}
	 public static boolean _clickMe(WebDriver driver, WebElement elm){
		 if(elm!=null) {
			 	try {
			 		elm.click();	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with clicking on ["+elm.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2click on ["+elm.toString()+"]");
	}	 
	 public static boolean _clearMe(WebDriver driver, String xp){
		 return _clearMe(driver, By.xpath(xp));
	 }
	public static boolean _clearMe(WebDriver driver, By by){
		 WebElement w = ax.findMe(driver, by);
		 if(w!=null) {
			 	try {
				 	w.clear();	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with clearing in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2clear in ["+by.toString()+"]");
	}
	public static boolean _typeMe(WebDriver driver, String xp, String text){
		return _typeMe(driver, By.xpath(xp), text);
	}
	public static boolean _typeMe(WebDriver driver, By by, String text){
		 WebElement w = ax.findMe(driver, by);
		 if(w!=null) {
			 	try {
				 	w.sendKeys(text);	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with typing ["+text+"] in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2type ["+text+"] in ["+by.toString()+"]");
	}
	public static boolean _typeMe(WebDriver driver, WebElement elm, String text){
		 if(elm!=null) {
			 	try {
			 		elm.sendKeys(text);	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with typing ["+text+"] in ["+elm.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2type ["+text+"] in ["+elm.toString()+"]");
	}	
	public static boolean _typeMeWithClear(WebDriver driver, String xp, String text){
		return _typeMeWithClear(driver, By.xpath(xp), text);
	}
	public static boolean _typeMeWithClear(WebDriver driver, By by, String text){
		 WebElement w = ax.findMe(driver, by);
		 if(w!=null) {
			 	try {
			 		w.clear();
				 	w.sendKeys(text);	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with typing ["+text+"] in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2type ["+text+"] in ["+by.toString()+"]");
	}
	public static boolean _typeMeWithClear(WebDriver driver, WebElement elm, String text){
		 if(elm!=null) {
			 	try {
			 		elm.clear();
			 		elm.sendKeys(text);	
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with typing ["+text+"] in ["+elm.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2type ["+text+"] in ["+elm.toString()+"]");
	}	
	public static boolean _selectMe(WebDriver driver, String xp, String type /*text,value,index*/, String value){
		return _selectMe(driver, By.xpath(xp), type, value);
	}
	public static boolean _selectMe(WebDriver driver, By by, String type /*text,value,index*/, String value){
		 WebElement w = ax.findMe(driver, by);
		 if(w!=null) {
			 	try {
					switch (type) {
					case "index": 						
						new Select(driver.findElement(by)).selectByIndex(Integer.parseInt(value)); 
						return true;
					case "text": 						
						new Select(driver.findElement(by)).selectByVisibleText(value); 
						return true;
					case "value":						
						new Select(driver.findElement(by)).selectByValue(value); 
						return true;
					}
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with selecting ["+value+"] by ["+type+"] in ["+by.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2select ["+value+"] by ["+type+"] in ["+by.toString()+"]");
	}
	
	public static boolean _selectMe(WebDriver driver, final WebElement elm, String type /*text,value,index*/, String value){
		 if(elm!=null) {
			 	try {
					switch (type) {
					case "index": 						
						new Select(elm).selectByIndex(Integer.parseInt(value)); 
						return true;
					case "text": 						
						new Select(elm).selectByVisibleText(value); 
						return true;
					case "value":						
						new Select(elm).selectByValue(value); 
						return true;
					}
				 	return true;
				} catch (Exception e) {
					return ax.returnWhenFalse("Problem with selecting ["+value+"] by ["+type+"] in ["+elm.toString()+"]");
				}
		 }
		 return ax.returnWhenFalse("Problem with find2select ["+value+"] by ["+type+"] in ["+elm.toString()+"]");
	}
		 
	 
	 
	 public static int get_number_found_in_paginator(WebDriver driver, WebElement elm){
		 return ApplicationPageUtils.GetNumberOfRowsFoundPaginatorCurrentByWebElement(driver, elm);
	 }
    
    public static int get_number_found_in_paginator(WebDriver driver, String topBox){
	   return ApplicationPageUtils.GetNumberOfRowsFound_Paginator_Current(driver, topBox);
   }
    public static String xLOG(){
	   return "|"+DateOperations.LOG_NOW()+"| ";
   }
    public static String GET_CLASS_CSS_STRING_FOR_ELEMENT(WebDriver driver, By by){
	   if(ax.isDisplayedBy(driver, by)){
		   return driver.findElement(by).getAttribute("class");  
	   }else{
		   return null;
	   }
   }
    public static String GET_ATTRIB_STRING_FOR_ELEMENT(WebDriver driver, By by, String ATTRIB){
	   if(ax.isDisplayedBy(driver, by)){
		   String st = driver.findElement(by).getAttribute(ATTRIB);
		   ax.log(st);
		   return st;  
	   }else{
		   return null;
	   }
   }
    public static String GET_ATTRIB_STRING_FOR_ELEMENT_WEBELEMENT(WebDriver driver, WebElement element, String ATTRIB){
	   if(ax.isDisplayedByWebElement(driver, element)){
		   String st = element.getAttribute(ATTRIB);
		   ax.log("Attribute so far: " + st);
		   return st;  
	   }else{
		   return null;
	   }
   }   
    public static boolean GET_BOOL_IF_CHECKBOX_IS_CHECKED(WebDriver driver, By by){
	   if(driver.findElement(by).isSelected()){
		   return true;
	   }else{return false;}
   }
    public static boolean IF_ELEMENT_GOT_THE_CSS_CLASS(WebDriver driver, By by, String ClassForTheElement){
	   String cssclass = null; 
	   if(ax.isDisplayedBy(driver, by)){
		   cssclass = driver.findElement(by).getAttribute("class");
	    	String[] CLASS_GRAB_SPLIT = cssclass.split(" ");
	    	if(Arrays.asList(CLASS_GRAB_SPLIT).contains(ClassForTheElement) ){
	    		return true;
	    	}else{
	    		return false;
	    	}
	   }else{
		   ax.t_error_log_only("Element is not displayed "+by.toString());
		   return false;
	   }
   }
    public static boolean DO_UPLOAD_ACTION(WebDriver driver, By UPLOAD_FILE_ELEMENT, String UploadFileLocation, By HIT_BUTTON_TO_DO_UPLOAD){
	   if(ax.isDisplayed_And_Enabled(driver, UPLOAD_FILE_ELEMENT)){
		   SeleniumWebUtils.FindAndTypeValueInUploadBox(driver, UPLOAD_FILE_ELEMENT, UploadFileLocation, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);   
	   }else{
		   ax.t_error(driver, "Problem with selecting file to upload - no selection button");
		   return false;
	   }
	   if(ax.isDisplayed_And_Enabled(driver, HIT_BUTTON_TO_DO_UPLOAD)){
		   ax.click_button_and_wait(driver, HIT_BUTTON_TO_DO_UPLOAD);
		   return true;
	   }else{
		   ax.t_error(driver, "Problem with hitting button after selecting file to upload - no HIT button, or button is disabled");
		   return false;
	   }
   }
    public static WebElement getStaleElementByLocator( WebDriver driver, By locator ) throws StaleElementReferenceException {
		   //I realized that the try-catch doesn't really need to be within
		//the ExpectedCondition final block.  By moving the try-catch outside
		//I would have access to the WebElement returned from findElement().
		//So, I can create my own Boolean expected condition while I loop to
		//hopefully accomplish a similar thing as method3.
		ax.info("Get element by locator: " + locator.toString() );  	
	long startTime = System.currentTimeMillis();
	driver.manage().timeouts().implicitlyWait( 1, TimeUnit.SECONDS );
	WebElement we = null;
	boolean unfound = true;
	int tries = 0;
	while ( unfound && tries < 20 ) {
	 tries += 1;
	 ax.info("Locating remaining time: " + (200-(10*(tries-1) )) + " seconds." );
	 try {
	   we = driver.findElement( locator );
	   unfound = false; // FOUND IT
	 } catch ( StaleElementReferenceException ser ) {						
		 ax.info( "ERROR: Stale element. " + locator.toString() );
	   unfound = true;
	 } catch ( NoSuchElementException nse ) {						
		 ax.info( "ERROR: No such element. " + locator.toString() );
		 if(tries==19){
			 ax.t_error("waiting to long - element not exists...");
			 return null;
		 }
		 
		 
	   unfound = true;
	 } catch ( Exception e ) {
		 ax.info( e.getMessage() );
	 }
	} 
	long endTime = System.currentTimeMillis();
	long totalTime = endTime - startTime;
	ax.info("Finished click after waiting for " + totalTime + " milliseconds.");
	driver.manage().timeouts().implicitlyWait( ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT, TimeUnit.SECONDS );
	return we;
	}
	public static WebElement getStaleElementByLocator2(WebDriver driver,  final By locator ) {
		   // Another approach, after everything I have learned, that might
		// also be effective.  With this method, a wait timeout occurs 3 
		// times within the 90 second limit.  So, the method will run 
		// between 15-90 seconds, depending on the situation of failure.
//		ax.info( "Get element by locator: " + locator.toString() );  
	  final long startTime = System.currentTimeMillis();
	  Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
	    .withTimeout(30, TimeUnit.SECONDS)
	    .pollingEvery(5, TimeUnit.SECONDS)
	    .ignoring( StaleElementReferenceException.class ) ;
	  int tries = 0;
	  boolean found = false;
	  WebElement we = null;
	  while ( (System.currentTimeMillis() - startTime) < 91000 ) {
//		  ax.info( "Searching for element. Try number " + (tries++) ); 
	   try {
	    we = wait.until( ExpectedConditions.presenceOfElementLocated( locator ) );
	    found = true;
	    break;
	   } catch ( StaleElementReferenceException e ) {      
		   ax.info( "Stale element: \n" + e.getMessage() + "\n");
	   }
	  }
	  long endTime = System.currentTimeMillis();
	  long totalTime = endTime - startTime;
	  if ( found ) {
//		  ax.info("Found element after waiting for " + totalTime + " milliseconds." );
	  } else {
//		  ax.info( "Failed to find element after " + totalTime + " milliseconds." );
	  }
	  return we;
	}
	public static WebElement getElementByLocator3(WebDriver driver,  final By locator ) {
		   // An extension of method3 although I am unsure if I trust it
		// because it re-gets the element AND the element gets assigned
		// again after returning from the method. A few opportunities
		// to go stale.
		ax.info( "Get element by locator: " + locator.toString() );		
	  final long startTime = System.currentTimeMillis();
	  driver.manage().timeouts().implicitlyWait( 5, TimeUnit.SECONDS );
	  Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
	    .withTimeout(90000, TimeUnit.MILLISECONDS)
	    .pollingEvery(5500, TimeUnit.MILLISECONDS);
	  wait.until( new ExpectedCondition<Boolean>() { 
	    @Override 
	    public Boolean apply( WebDriver webDriver ) {
	    try {
	      webDriver.findElement( locator ).getTagName();
	        return true;
	      } catch ( StaleElementReferenceException e ) {						
	    	  ax.info( e.getMessage() + "\n");
	    	  ax.info("Trying again for availability of element...");
	        return false;
	      }		
	    } 
	  } );
	  WebElement we = null;
	  try {
	    we = driver.findElement( locator ); // is this error prone?
	  } catch ( StaleElementReferenceException e ) {						
		  ax.info( "Stale element: \n" + e.getMessage() + "\n");
	  }	
	  driver.manage().timeouts().implicitlyWait( ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT, TimeUnit.SECONDS );
	  long endTime   = System.currentTimeMillis();
	  long totalTime = endTime - startTime;
	  ax.info("Finished click after waiting for " + totalTime + " milliseconds.");
	  return we;
	}
	public void handleStaleElement(WebDriver driver, By by) {
		// This function will handle stalelement reference exception
	  int count = 0;
	  //It will try 4 times to find same element using name.
	  while (count < 4) {
	   try {
	    //If exception generated that means It Is not able to find element then catch block will handle It.
	    WebElement staledElement = driver.findElement(by);
	    //If exception not generated that means element found and element text get cleared.
	    staledElement.clear();    
	   } catch (StaleElementReferenceException e) {
	    e.toString();
	    ax.log("Trying to recover from a stale element :" + e.getMessage());
	    count = count + 1;
	   }
	   count = count + 4;
	  }
	
	 }
	  public static boolean STALE_EXCEPTION_WAITER(WebDriver driver, String XPATH_LOCATOR){
	    	int attempts 		= 0;
	    	int attempts_max 	= ProjectDataVariables.TIMEX_FX_10;
	    	ax.log( "IF_BEFORE_EXIST: "+ ax.isDisplayedBy(driver, By.xpath(XPATH_LOCATOR) ) );
	    	while(!ax.isDisplayedBy(driver, By.xpath(XPATH_LOCATOR))){
	    		ax.wait(1);
	    		attempts++;
	    		ax.log( "IF_INSIDE_EXIST: "+ ax.isDisplayedBy(driver, By.xpath(XPATH_LOCATOR) ) );
	    		if(attempts > attempts_max){
	    			return false;
	    		}
	    	}
	    	ax.log( "IF_AFTER_EXIST: "+ ax.isDisplayedBy(driver, By.xpath(XPATH_LOCATOR) ) );
	    	return true;
	    }
	  public static boolean STALE_EXCEPTION_WAITER_BY(WebDriver driver, By XPATH_LOCATOR){
	    	int attempts 		= 0;
	    	int attempts_max 	= ProjectDataVariables.TIMEX_FX_10;
	    	ax.log( "IF_BEFORE_EXIST: "+ ax.isDisplayedBy(driver, XPATH_LOCATOR ) );
	    	while(!ax.isDisplayedBy(driver, XPATH_LOCATOR)){
	    		ax.wait(1);
	    		attempts++;
	    		ax.log( "IF_INSIDE_EXIST: "+ ax.isDisplayedBy(driver, XPATH_LOCATOR ) );
	    		if(attempts > attempts_max){
	    			return false;
	    		}
	    	}
	    	ax.log( "IF_AFTER_EXIST: "+ ax.isDisplayedBy(driver, XPATH_LOCATOR) );
	    	return true;
	    }
	
	  

	  
	  public static boolean SAVE_REPLACE_DATAFILE(String KEY, String VALUE){
		  if(ax.IS_NULL_OR_EMPTY_STRING(VALUE)){return false;}
		  KEY = StringPageUtils.PROP_ENTRY_MAKER(KEY); 
		  return ToolFunctions.WritePropertiesFileWithReplace(ProjectDataVariables.LOCATION_TMP_DATA_FILE, KEY, VALUE);
	  }
	  
	  public static boolean SAVE_2_FILE(Object oKey, Object oValue){
		  String Key = String.valueOf(oKey);
		  String Value = String.valueOf(oValue);
		if(ax.IS_NULL_OR_EMPTY_STRING(Value)){return false;}
		 Key = StringPageUtils.PROP_ENTRY_MAKER(Key);
		 return ToolFunctions.WritePropertiesFile(ProjectDataVariables.LOCATION_TMP_DATA_FILE, Key, Value); 	 }
	  
	  public static boolean SAVE_2_FILE(String FileLocation, String Key, String Value){
		if(ax.IS_NULL_OR_EMPTY_STRING(Value)){return false;}
		
		String p = ax.READ_A_FILE(FileLocation, Key);
		if(p!=null){
			return ToolFunctions.ReplaceProperties(FileLocation, Key, Value);
		}else if(p==null){
			return ToolFunctions.WritePropertiesFile(FileLocation, Key, Value);
		}else if(!p.isEmpty()){
			return ToolFunctions.ReplaceProperties(FileLocation, Key, Value);
		}
		else{
			return ToolFunctions.WritePropertiesFile(FileLocation, Key, Value);	
		}
		  	 
	  }
	  
	  
	public static String READ_A_FILE(String key){ // TEMPORARY FILE
		 	 key = StringPageUtils.PROP_ENTRY_MAKER(key);
			 String r = ToolFunctions.ReadPropertiesFile(ProjectDataVariables.LOCATION_TMP_DATA_FILE, key);
			 // In case the property has fiew values separated by coma, we pick last one !!
			 return StringPageUtils.GET_LAST_FROM_CSV_LINE(r); 	 }
	
	public static String READ_A_FILE(String fileLocation, String key){ // TEMPORARY FILE
	 	 key = StringPageUtils.PROP_ENTRY_MAKER(key);
		 String r = ToolFunctions.ReadPropertiesFile(fileLocation, key);
		 // In case the property has fiew values separated by coma, we pick last one !!
		 return StringPageUtils.GET_LAST_FROM_CSV_LINE(r); 	 }
	
	
	public static String READ_CONFIG_FILE(String key){ // FILE: EUCR_AUTO_PROPERTIES_GLOBAL.properties in eucr_config 
		 return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE	,key); 	 }
	public static String READ_ANY_PROP_FILE(String location, String key){ // FILE: EUCR_AUTO_PROPERTIES_GLOBAL.properties in eucr_config 
		 return ToolFunctions.ReadPropertiesFile(location,key); 	 }
	public static String READ_CSV_INP(String searched_start_pattern, int WhichElementReturn /*except of startPattern: 1,2...*/){
		 String fileLocation = ProjectDataVariables.WORKSPACE_EUCR_INP_DATA_DIR + "INPX.txt";
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(new File(fileLocation));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			BufferedReader br = new BufferedReader(fileReader);
			String line = null;
			// if no more lines the readLine() returns null
			
			String[] nx = null; 
			try {
				while ((line = br.readLine()) != null) {
				     // reading lines until the end of the file
					if(line.contains(searched_start_pattern)){
						nx = line.split(",");
						return nx[WhichElementReturn];
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
	 }

	
	public static ArrayList<String> READ_FROM_CSV_1COL_INTO_ALSTRING(String fileLocation, int whichColumn /*provide: 1, 2, 3 => in array will be 0, 1, 2*/){
		ArrayList<String> result 		= new ArrayList<String>();
		ArrayList<ArrayList<String>> x 	= ax.READ_CSVFILE_2_ARRAYLIST_STRING(fileLocation, ",");
		for (ArrayList<String> line : x) {
			int whichColumnConverted 	= whichColumn-1;
			if(whichColumnConverted>line.size()){ ax.log("index to grab specific column "+whichColumn+" is higher than column number in the file " + line.size()+1); return null;}
			String ValueToGet 		= line.get(whichColumnConverted);
			result.add(ValueToGet);
		}
		return result;
	}
	
	
	
	public static ArrayList<ArrayList<String>> READ_CSVFILE_2_ARRAYLIST_STRING(String fileLocation, String splitChar){
		 ArrayList<ArrayList<String>> OUT 	= new ArrayList<ArrayList<String>>();
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(new File(fileLocation));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			BufferedReader br = new BufferedReader(fileReader);
			String line = null;
			String[] nx = null; 
			try {
				while ((line = br.readLine()) != null) {
					if(line.contains(splitChar)){
						String[] l = line.split(splitChar);
						OUT.add(new ArrayList<String>(Arrays.asList(l)));
					}else{
						OUT.add(new ArrayList<String>(Arrays.asList(line)));
					}
				}
				return OUT;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	 }
	
	public static ArrayList<String> ArrayList_FindAndReplaceWord(ArrayList<String> someList, String someString, String otherString)
    {
      ArrayList<String> newList = new ArrayList<String>();
      for(int i = 0; i < someList.size(); i++)
      {
          if(someList.get(i).contains(someString))
          {
              newList.add(someList.get(i).replace(someString, otherString));
          }else{
        	  newList.add(someList.get(i));
          }
       }
      return newList;
    }
	
	public static boolean FileAppendValueInALine(String fileLocation, String SearchedPattern, String txt){
		boolean fileExists = false;
		ArrayList<String> TMP = new ArrayList<String>();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File(fileLocation));
		} catch (FileNotFoundException e1) {
			ax.log("THERE IS NO SUCH FILE: " + fileLocation);
			ax.log("###########################################");
			e1.printStackTrace();
			ax.log("###########################################");
			return false;
		}
		BufferedReader br 		= new BufferedReader(fileReader);
//		if(br.lines().count() < 1){
//			ax.log("File "+fileLocation+" is empty"); 
//			return true;	
//		}

		String line = null;
		String[] nx = null; 
		try {
			while ((line = br.readLine()) != null) {
				if(line.contains(SearchedPattern)){
					line += ","+txt;
					TMP.add(line);
				}else{
					TMP.add(line);
				}
			}
		} catch (IOException e) {
			ax.log("Problem with reading file..." + fileLocation);
			ax.log("###########################################");
			e.printStackTrace();
			ax.log("###########################################");
			return false;
		}
		finally
		{
		    try
		    {
		        if ( br != null)
		        br.close( );
		    }
		    catch ( IOException e)
		    {
		    	ax.log("Problem with closing file after reading " + fileLocation);
		    	ax.log("###########################################");
		    	e.printStackTrace(); 			
		    	ax.log("###########################################");
		    	return false;
		    }
		}		
		BufferedWriter writer = null;
		try {
			writer 	= new BufferedWriter( new FileWriter(new File(fileLocation)));
			for (String line2Save : TMP) {
				writer.write(line2Save+ "\n");
			}
		} catch (IOException e1) {
			ax.log("Problem with reading file to update line..");
			ax.log("###########################################");
			e1.printStackTrace();
			ax.log("###########################################");
			return false;
		}
		finally
		{
		    try
		    {
		        if ( writer != null){
		        	writer.close( );
		        	return true;
		        }
		    }
		    catch ( IOException e)
		    {
		    	ax.log("Problem with closing file after updating line " + fileLocation);
		    	ax.log("###########################################");
		    	e.printStackTrace();
		    	ax.log("###########################################");
		    }
		}
		return false;		
	}	
	
	public static String[] REMOVE_PREDASH(String[] DASHED){
		String[] NEW_CLEANED = new String[DASHED.length];
		for(int i=0;i<DASHED.length;i++){
			NEW_CLEANED[i] = ax.GET_DASHED_AFTER_TEXT(DASHED[i]);
		}
		return NEW_CLEANED;
	}
	
	  public static String removePartOfTheString(String txt, String partToRemove){
	        return txt.replace(partToRemove,"");   
	  }
	
	
//	public static String[] REMOVE_FIRST_PREDASH(String[] DASHED){
//		String[] NEW_CLEANED = new String[DASHED.length];
//		for(int i=0;i<DASHED.length;i++){
//			NEW_CLEANED[i] = ax.GET_DASHED_AFTER_TEXT(DASHED[i]);
//		}
//		return NEW_CLEANED;
//	}
	
	
	
	public static String remove_parenthesis_square(String input_string){
		return remove_parenthesis(input_string, "[]");
	}
	public static String remove_parenthesis_curly(String input_string){
		return remove_parenthesis(input_string, "{}");
	}
	public static String remove_parenthesis_curved(String input_string){
		return remove_parenthesis(input_string, "()");
	}
	
	public static String remove_parenthesis(String input_string, String parenthesis_symbol){
		// removing parenthesis and everything inside them, works for (),[] and {}
		if(parenthesis_symbol.contains("[]")){
			return input_string.replaceAll("\\s*\\[[^\\]]*\\]\\s*", " ");
		}else if(parenthesis_symbol.contains("{}")){
			return input_string.replaceAll("\\s*\\{[^\\}]*\\}\\s*", " ");
		}else{
			return input_string.replaceAll("\\s*\\([^\\)]*\\)\\s*", " ");
		}
	}
	
	public static String READ_ACCOUNT_FROM_EXCEL_TRX_MATRIX(String...arguments){
		ArrayList<ArrayList<String>> IN_DATA = ExcelJxl.GetExcelTableInto2DArrayListString(ax.getProp("DIR_TMP_LOCATION") + "selenium_runs\\ACCOUNT_FOR_TRANSACTION_MATRIX_"+ax.getProp("TEST_ENVRMENT")+"_"+ax.getProp("TEST_REGISTRY")+".xlsx", false, true);
		int rowCount=0;
		outerLoop:for(ArrayList<String> row: IN_DATA){
			int cellCount=0;
			innerLoop:for (String cell: row) {
				cell = ax.remove_parenthesis_square(cell);
				if(cellCount==row.size()-1){
					String AccountIdentifier = cell.trim().toUpperCase();
//					ax.log("Found the line !!!. AccountIdentifier: " + AccountIdentifier);
					return AccountIdentifier;
				}
				if(!cell.trim().toUpperCase().equals(arguments[cellCount])){
					break innerLoop;
				}
				cellCount++;
				}
			rowCount++;
		}
		out.println("No Account Found in TRANSACION MATRIX ACCOUNT STORAGE");
		return null;
	}
	
	
	public static String READ_ACCOUNT_HOLDER_INP_DATA(String... arguments){ // String AccountType, String AAR_FACTOR, String YFE
		int ArgumentsNumber_exp = arguments.length;
		ArrayList<String> ARX = FilesUtils.READ_DFILE_TO_ARRAY(ax.getProp("DIR_TMP_LOCATION") + "eucr_out\\accgen.txt"); // OHA_NOAAR_2015_ST-OPEN_EX-NONE_VE-NONE_EM-NONE:5006678
		if(ARX.size()<1){
			ax.t_error("READ_ACCOUNT_HOLDER_INP_DATA ERROR: file is empty !! ");
			return null;
		}
		for (String line : ARX) {
			if(!line.contains(":")){
				ax.t_error("READ_ACCOUNT_HOLDER_INP_DATA ERROR: not contain proper accoun tinfo !! ");
				return null;
			}
//			else if(!line.contains(":")){
//				ax.t_error("READ_ACCOUNT_HOLDER_INP_DATA ERROR: not contain proper arguments info !! ");
//				return null;
//			}
			
			String argline 	= line.split(":")[0];
			String acci 	= line.split(":")[1];
			String[] args	= argline.split("_");
			
			if(acci.contains(" ")){
				String[] aSpaced;
				aSpaced 	= acci.split(" ");
				acci 		= aSpaced[0];
			}
			
			int howManyArguments_file = args.length;
			if(howManyArguments_file < ArgumentsNumber_exp){
				ax.t_error("READ_ACCOUNT_HOLDER_INP_DATA ERROR: arguments info are not complete!! ");
				return null;
			}
			String[] x_arguments;
			x_arguments 	= ax.REMOVE_FIRST_PREDASH(arguments);//ax.REMOVE_PREDASH(arguments);
			args 			= ax.REMOVE_FIRST_PREDASH(args);
					
			
			if(Arrays.equals(x_arguments, args)){
				return acci;
			}
			
			
		}
		ax.log("Not found stored accounts data for test");
		return null;
	
		
		/*
		 * 		
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "NOAAR", "2015","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "NOAAR", "2015","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "NOAAR", "2016","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "NOAAR", "2016","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "HASAAR", "2015","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "HASAAR", "2015","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "HASAAR", "2016","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "HASAAR", "2016","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "NOAAR", "2016","ST-CLOSED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "HASAAR", "2016","ST-CLOSED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "NOAAR", "2015","ST-SUSPENDED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("OHA", "HASAAR", "2016","ST-SUSPENDED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "NOAAR", "2015","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "NOAAR", "2015","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "NOAAR", "2016","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "NOAAR", "2016","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "HASAAR", "2015","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "HASAAR", "2015","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "HASAAR", "2016","ST-OPEN","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "HASAAR", "2016","ST-BLOCKED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "NOAAR", "2016","ST-CLOSED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "HASAAR", "2016","ST-CLOSED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "NOAAR", "2015","ST-SUSPENDED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log(   ax.READ_ACCOUNT_HOLDER_INP_DATA("AOHA", "HASAAR", "2016","ST-SUSPENDED","EX-NONE","VE-NONE","EM-NONE")      );
		ax.log("");
		 * 
		 * 
		 * */
		
	}
	
	
	public static boolean IS_NULL_OR_EMPTY_ARRAY_LIST(ArrayList Ars){
		 if(Ars==null || Ars.size() < 1){ 			 return true;} return false;}
	public static boolean IS_NULL_OR_EMPTY_ARRAY_LIST_BOOL(ArrayList<Boolean> Ars){
		 if(Ars==null || Ars.size() < 1){ 			 return true;} return false;}
	public static boolean IS_NULL_OR_EMPTY_ARRAY_LIST_STRING(ArrayList<String> Ars){
		 if(Ars==null || Ars.size() < 1 || (Ars.size()==1&&Ars.get(0).isEmpty())){ 			 
			 return true;
			 } 
		 return false;
			 }
	public static boolean IS_NULL_OR_EMPTY_ARRAY_LIST_WEBELEMENT(ArrayList<WebElement> Ars){
		 if(Ars==null || Ars.size() < 1){ 			 return true;} return false;}
	public static boolean IS_NULL_OR_EMPTY_LIST_WEBELEMENT(List<WebElement> Ars){
		 if(Ars==null || Ars.size() < 1){ 			 return true;} return false;}
	public static boolean IS_NULL_OR_EMPTY_WEBELEMENT(WebElement Ars){
		 if(Ars==null){ 			 return true;} return false;
		 }
	public static boolean IS_NULL_OR_EMPTY_WEBDRIVER(WebDriver driver){
		 if(driver==null){ 			 return true;} return false;
		 }
	
	public static boolean IS_NULL_OR_EMPTY_STRING(String s){
		 if(s==null || s.isEmpty()){
			 return true;
		 } return false; 	 }
	public static boolean IS_NULL_OR_EMPTY_STRING_ARRAY(String[] s){
		 if(s==null || s.length<1){
			 return true;
		 } return false; 	 }
	
	public static boolean IS_NOT_NULLEMPTY(String number){
		if(number==null){
			return false;
		}else if(number.equals("")){
			return false;
		}
		return true;
	}	
	
//	public static EucrHomePage LoginNA1(){ 		return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_NA1.split("\\|")[0].trim()); 	}
//	public static EucrHomePage LoginNA2(){ 		return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_NA2.split("\\|")[0].trim()); 	}
//	public static EucrHomePage LoginCA1(){ 		return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_CA1.split("\\|")[0].trim()); 	}
//	public static EucrHomePage LoginCA2(){ 		return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_CA2.split("\\|")[0].trim()); 	}
//	public static EucrHomePage LoginCA3(){ 		return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_CA3.split("\\|")[0].trim()); 	}
//	public static EucrHomePage LoginAR1(){ 		return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_AR1.split("\\|")[0].trim()); 	}
//	public static EucrHomePage LoginAR2(){ 		return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_AR2.split("\\|")[0].trim()); 	}
//	public static EucrHomePage LoginAAR1(){ 	return EucrHomePage.reloginAs(ProjectDataVariables.EUCR_USER_AAR1.split("\\|")[0].trim()); 	}

	public static EucrHomePage LoginNA1(){ 		return EucrHomePage.reloginAs(ax.GET_USER_NAME("NA1")); 	}
	public static EucrHomePage LoginNA2(){ 		return EucrHomePage.reloginAs(ax.GET_USER_NAME("NA2")); 	}
	public static EucrHomePage LoginCA1(){ 		return EucrHomePage.reloginAs(ax.GET_USER_NAME("CA1")); 	}
	public static EucrHomePage LoginCA2(){ 		return EucrHomePage.reloginAs(ax.GET_USER_NAME("CA2")); 	}
	public static EucrHomePage LoginCA3(){ 		return EucrHomePage.reloginAs(ax.GET_USER_NAME("CA3")); 	}
	public static EucrHomePage LoginAR1(){ 		return EucrHomePage.reloginAs(ax.GET_USER_NAME("AR1")); 	}
	public static EucrHomePage LoginAR2(){ 		return EucrHomePage.reloginAs(ax.GET_USER_NAME("AR2")); 	}
	public static EucrHomePage LoginAAR1(){ 	return EucrHomePage.reloginAs(ax.GET_USER_NAME("AAR1")); 	}
	public static EucrHomePage LoginUSR(){ 		return EucrHomePage.reloginAs(ax.GET_USER_EMAIL_BY_USER_FX("USR")); 	}
	
	public static EucrHomePage LoginNA1(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("NA1")); 	}
	public static EucrHomePage LoginNA2(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("NA2")); 	}
	public static EucrHomePage LoginCA1(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("CA1")); 	}
	public static EucrHomePage LoginCA2(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("CA2")); 	}
	public static EucrHomePage LoginCA3(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("CA3")); 	}
	public static EucrHomePage LoginAR1(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("AR1")); 	}
	public static EucrHomePage LoginAR2(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("AR2")); 	}
	public static EucrHomePage LoginAAR1(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_NAME("AAR1")); 	}
	public static EucrHomePage LoginUSR(int driverInstance){ 		return EucrHomePage.reloginAs(driverInstance, ax.GET_USER_EMAIL_BY_USER_FX("USR")); 	}
	
	public static EucrHomePage LoginToEucr(String userName){
		return LoginToEucr(1, userName);
	}
	public static EucrHomePage LoginToEucr(int driverInstance, String userName){
		getDriver(driverInstance).navigate().to(AbstractPage.BASE_URL+"/index.xhtml");
		EucrHomePage eucrPage = new EucrHomePage().open().openEucrHomePage();
		EcasPage ecasPage = eucrPage.clickLoginLink();
		return ecasPage.login(userName, ProjectDataVariables.EUCR_PASSWORD);
	}

	public static EucrHomePage LoginToEucr(String browserType, String userName){
		return LoginToEucr(1, browserType, userName);
	}
	
	public static EucrHomePage LoginToEucr(int driverInstance, String browserType, String userName){
//		SeleniumDriver.initDriver();
		getDriver(driverInstance, browserType).navigate().to(AbstractPage.BASE_URL+"/index.xhtml");
		EucrHomePage eucrPage = new EucrHomePage().open().openEucrHomePage();
		EcasPage ecasPage = eucrPage.clickLoginLink();
		ecasPage.clickFallBackLink();
		return ecasPage.login(userName, ProjectDataVariables.EUCR_PASSWORD);
	}	
	
	
	public static WebDriver LOGIN(WebDriver driver){
			 if(!ax.IAM_LOGGED(driver)){
				 return ax.LOGIN_NA1(driver);
			 } return driver; 	 
		 }
	public static WebDriver LOGIN(WebDriver driver, String FX /*[AR|NA1|NA2|CA1|CA2|AR]*/){
		// If user is not logged in or logged-in user in not the one we expected - than do relogin
		if(		!ax.IAM_LOGGED(driver) 
				|| 
				(ax.IAM_LOGGED(driver) && LoginToEUCR.LOGINNAME_BY_SYMBOL(FX)!=PageCanvas.getLoggedin_username(driver).trim())  
				){
				 switch (FX) {
					case "NA"	: return ax.LOGIN_NA1(driver);
					case "NA1"	: return ax.LOGIN_NA1(driver);
					case "NA2"	: return ax.LOGIN_NA2(driver);
					case "CA"	: return ax.LOGIN_CA1(driver);
					case "CA1"	: return ax.LOGIN_CA1(driver);
					case "CA2"	: return ax.LOGIN_CA2(driver);
					case "AR"	: return ax.LOGIN_AR1(driver);
					case "AR1"	: return ax.LOGIN_AR1(driver);
					case "AR2"	: return ax.LOGIN_AR2(driver);
					case "AAR"	: return ax.LOGIN_AAR1(driver);
					case "AAR1"	: return ax.LOGIN_AAR1(driver);
						default	: return ax.LOGIN_NA1(driver);
				}
		 	}
		return driver; 	 
		 }
	
	
	public static WebDriver LOGIN(WebDriver driver, String FX, int DriverInstance){ 			
			if(DriverInstance>0){
				ax.focusBrowser(DriverInstance);
			}
			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, FX, ProjectDataVariables.get_EUCR_REG(), DriverInstance);
		}
	public static WebDriver LOGIN(WebDriver driver, String FX, int DriverInstance, String MS){ 	return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, FX, MS, DriverInstance);}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static WebDriver LOGIN_UNV(WebDriver driver, String FX, String MS, int DriverInstance, boolean ForceRelogin){
		
		
		if(Setuper.driver2!=null && Setuper.driver3!=null && Setuper.driver4!=null){
			
			switch (DriverInstance) {
			case 1: 		driver = Setuper.driver; break;
			case 2: 		driver = Setuper.driver2; break;
			case 3: 		driver = Setuper.driver3; break;
			case 4: 		driver = Setuper.driver4; break;
			default:
				driver = Setuper.driver; break;
			}
			return ax.LG(driver, FX, MS, DriverInstance, ForceRelogin);
			
		}else{
			return ax.LG(driver, FX, MS, ForceRelogin);	
		}
		
		
		
//		ax.SET_REGISTRY(MS);
//		String ENV 			= Setuper.GET_ENVIRONMENT();
//		String UserName 	= ax.GET_USER_NAME(FX);
//		
//		if(ax.IS_NULL_OR_EMPTY_WEBDRIVER(driver)){
//			if(ax.IS_NULL_OR_EMPTY_WEBDRIVER(Setuper.driver2) && ax.IS_NULL_OR_EMPTY_WEBDRIVER(Setuper.driver3)){
//				driver = Setuper.driver;
//			}else{
//				driver = Setuper.GET_DRIVER_BY_FX(FX);
//			}
//		}else{
//			if(driver !=Setuper.GET_DRIVER_BY_FX(FX)){
//				driver = Setuper.GET_DRIVER_BY_FX(FX);
//			}
//		}
//		
//		if(ax.isDisplayedBy(driver, RepoEucr.EUCR_CANVAS_CURRENT_USERNAME_LOGGED_IN)){
//			String grabbedLoggedInUserName = PageCanvas.getApplicationLoggedIn_Username(driver);
//			
//			if(grabbedLoggedInUserName.contains(UserName) && !ForceRelogin){
//				ax.log("already logged in as "+FX); return driver;
//			}else if(grabbedLoggedInUserName.contains(UserName) && ForceRelogin){
//				Start.LOGOUT(driver);
//			}else{
//				Start.LOGOUT(driver);
//			}
//		}
//		
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.GET_DRIVER_BY_FX(FX), ax.GET_USER_NAME(FX), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD, ForceRelogin);	
//		default:
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, FX, MS, DriverInstance, ForceRelogin);
//		}
	}	

	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	public static WebDriver LOGIN_NA1(WebDriver driver){										return LOGIN_NA1(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_NA1, false); }
//	public static WebDriver LOGIN_NA1(WebDriver driver, boolean ForceRelogin){					return LOGIN_NA1(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_NA1, ForceRelogin); }
//	public static WebDriver LOGIN_NA1(WebDriver driver, String MS){ 							return LOGIN_NA1(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_NA1, false); }
//	public static WebDriver LOGIN_NA1(WebDriver driver, int DriverInstance){ 					return LOGIN_NA1(driver, ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
//	public static WebDriver LOGIN_NA1(WebDriver driver, String MS, int DriverInstance){			return LOGIN_NA1(driver, MS, DriverInstance, false); }
//	public static WebDriver LOGIN_NA1(WebDriver driver, String MS, boolean ForceRelogin){ 		return LOGIN_NA1(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_NA1, ForceRelogin); }
//	public static WebDriver LOGIN_NA1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 
//		ax.SET_REGISTRY(MS);
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver, ax.GET_USER_NAME("NA1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD, ForceRelogin);	
//		default:
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "NA1", MS, DriverInstance, ForceRelogin);
//		}
//	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_NA1(WebDriver driver){														return LOGIN_UNV(driver, "NA1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA1"), false); }
	public static WebDriver LOGIN_NA1(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "NA1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA1"), ForceRelogin); }
	public static WebDriver LOGIN_NA1(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "NA1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA1"), false); }
	public static WebDriver LOGIN_NA1(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "NA1", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_NA1(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "NA1", MS, DriverInstance, false); }
	public static WebDriver LOGIN_NA1(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "NA1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA1"), ForceRelogin); }
	public static WebDriver LOGIN_NA1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "NA1", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_NA2(WebDriver driver){														return LOGIN_UNV(driver, "NA2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA2"), false); }
	public static WebDriver LOGIN_NA2(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "NA2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA2"), ForceRelogin); }
	public static WebDriver LOGIN_NA2(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "NA2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA2"), false); }
	public static WebDriver LOGIN_NA2(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "NA2", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_NA2(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "NA2", MS, DriverInstance, false); }
	public static WebDriver LOGIN_NA2(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "NA2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA2"), ForceRelogin); }
	public static WebDriver LOGIN_NA2(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "NA2", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_CA1(WebDriver driver){														return LOGIN_UNV(driver, "CA1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA1"), false); }
	public static WebDriver LOGIN_CA1(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "CA1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA1"), ForceRelogin); }
	public static WebDriver LOGIN_CA1(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "CA1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA1"), false); }
	public static WebDriver LOGIN_CA1(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "CA1", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_CA1(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "CA1", MS, DriverInstance, false); }
	public static WebDriver LOGIN_CA1(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "CA1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA1"), ForceRelogin); }
	public static WebDriver LOGIN_CA1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "CA1", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_CA2(WebDriver driver){														return LOGIN_UNV(driver, "CA2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA2"), false); }
	public static WebDriver LOGIN_CA2(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "CA2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA2"), ForceRelogin); }
	public static WebDriver LOGIN_CA2(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "CA2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA2"), false); }
	public static WebDriver LOGIN_CA2(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "CA2", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_CA2(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "CA2", MS, DriverInstance, false); }
	public static WebDriver LOGIN_CA2(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "CA2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA2"), ForceRelogin); }
	public static WebDriver LOGIN_CA2(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "CA2", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_CA3(WebDriver driver){														return LOGIN_UNV(driver, "CA3", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA3"), false); }
	public static WebDriver LOGIN_CA3(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "CA3", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA3"), ForceRelogin); }
	public static WebDriver LOGIN_CA3(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "CA3", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA3"), false); }
	public static WebDriver LOGIN_CA3(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "CA3", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_CA3(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "CA3", MS, DriverInstance, false); }
	public static WebDriver LOGIN_CA3(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "CA3", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA3"), ForceRelogin); }
	public static WebDriver LOGIN_CA3(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "CA3", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_AR1(WebDriver driver){														return LOGIN_UNV(driver, "AR1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR1"), false); }
	public static WebDriver LOGIN_AR1(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "AR1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR1"), ForceRelogin); }
	public static WebDriver LOGIN_AR1(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "AR1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR1"), false); }
	public static WebDriver LOGIN_AR1(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "AR1", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_AR1(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "AR1", MS, DriverInstance, false); }
	public static WebDriver LOGIN_AR1(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "AR1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR1"), ForceRelogin); }
	public static WebDriver LOGIN_AR1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "AR1", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_AR2(WebDriver driver){														return LOGIN_UNV(driver, "AR2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR2"), false); }
	public static WebDriver LOGIN_AR2(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "AR2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR2"), ForceRelogin); }
	public static WebDriver LOGIN_AR2(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "AR2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR2"), false); }
	public static WebDriver LOGIN_AR2(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "AR2", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_AR2(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "AR2", MS, DriverInstance, false); }
	public static WebDriver LOGIN_AR2(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "AR2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR2"), ForceRelogin); }
	public static WebDriver LOGIN_AR2(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "AR2", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_AAR1(WebDriver driver){														return LOGIN_UNV(driver, "AAR1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR1"), false); }
	public static WebDriver LOGIN_AAR1(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "AAR1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR1"), ForceRelogin); }
	public static WebDriver LOGIN_AAR1(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "AAR1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR1"), false); }
	public static WebDriver LOGIN_AAR1(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "AAR1", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_AAR1(WebDriver driver, String MS, int DriverInstance){						return LOGIN_UNV(driver, "AAR1", MS, DriverInstance, false); }
	public static WebDriver LOGIN_AAR1(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "AAR1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR1"), ForceRelogin); }
	public static WebDriver LOGIN_AAR1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "AAR1", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_AAR2(WebDriver driver){														return LOGIN_UNV(driver, "AAR2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR2"), false); }
	public static WebDriver LOGIN_AAR2(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "AAR2", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR2"), ForceRelogin); }
	public static WebDriver LOGIN_AAR2(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "AAR2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR2"), false); }
	public static WebDriver LOGIN_AAR2(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "AAR2", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_AAR2(WebDriver driver, String MS, int DriverInstance){						return LOGIN_UNV(driver, "AAR2", MS, DriverInstance, false); }
	public static WebDriver LOGIN_AAR2(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "AAR2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR2"), ForceRelogin); }
	public static WebDriver LOGIN_AAR2(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "AAR2", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_USR(WebDriver driver){														return LOGIN_UNV(driver, "USR", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("USR"), false); }
	public static WebDriver LOGIN_USR(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "USR", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("USR"), ForceRelogin); }
	public static WebDriver LOGIN_USR(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "USR", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("USR"), false); }
	public static WebDriver LOGIN_USR(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "USR", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_USR(WebDriver driver, String MS, int DriverInstance){							return LOGIN_UNV(driver, "USR", MS, DriverInstance, false); }
	public static WebDriver LOGIN_USR(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "USR", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("USR"), ForceRelogin); }
	public static WebDriver LOGIN_USR(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "USR", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static WebDriver LOGIN_VER1(WebDriver driver){														return LOGIN_UNV(driver, "VER1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("VER1"), false); }
	public static WebDriver LOGIN_VER1(WebDriver driver, boolean ForceRelogin){									return LOGIN_UNV(driver, "VER1", ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("VER1"), ForceRelogin); }
	public static WebDriver LOGIN_VER1(WebDriver driver, String MS){ 											return LOGIN_UNV(driver, "VER1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("VER1"), false); }
	public static WebDriver LOGIN_VER1(WebDriver driver, int DriverInstance){ 									return LOGIN_UNV(driver, "VER1", ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
	public static WebDriver LOGIN_VER1(WebDriver driver, String MS, int DriverInstance){						return LOGIN_UNV(driver, "VER1", MS, DriverInstance, false); }
	public static WebDriver LOGIN_VER1(WebDriver driver, String MS, boolean ForceRelogin){ 						return LOGIN_UNV(driver, "VER1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("VER1"), ForceRelogin); }
	public static WebDriver LOGIN_VER1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 	return LOGIN_UNV(driver, "VER1", MS, DriverInstance, ForceRelogin); }
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	public static WebDriver LG_NA1(WebDriver driver){ 		return LG(driver, "NA1", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_NA1(WebDriver driver, boolean ForceLogin){ 		return LG(driver, "NA1", ProjectDataVariables.get_EUCR_REG(), ForceLogin); 	}
	public static WebDriver LG_NA1(WebDriver driver, String MS){ 		return LG(driver, "NA1", MS, false); 	}
	
	public static WebDriver LG_NA2(WebDriver driver, boolean ForceLogin){ 		return LG(driver, "NA2", ProjectDataVariables.get_EUCR_REG(), ForceLogin); 	}
	public static WebDriver LG_NA2(WebDriver driver){ 		return LG(driver, "NA2", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_NA2(WebDriver driver, String MS){ 		return LG(driver, "NA2", MS, false); 	}
	
	public static WebDriver LG_CA1(WebDriver driver){ 		return LG(driver, "CA1", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_CA1(WebDriver driver, String MS){ 		return LG(driver, "CA1", MS, false); 	}
	
	public static WebDriver LG_CA2(WebDriver driver){ 		return LG(driver, "CA2", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_CA2(WebDriver driver, String MS){ 		return LG(driver, "CA2", MS, false); 	}
	
	public static WebDriver LG_CA3(WebDriver driver){ 		return LG(driver, "CA3", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_CA3(WebDriver driver, String MS){ 		return LG(driver, "CA3", MS, false); 	}
	
	public static WebDriver LG_AR1(WebDriver driver){ 		return LG(driver, "AR1", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_AR1(WebDriver driver, boolean ForceLogin){ 		return LG(driver, "AR1", ProjectDataVariables.get_EUCR_REG(), ForceLogin); 	}
	
	public static WebDriver LG_AR1(WebDriver driver, String MS){ 		return LG(driver, "AR1", MS, false); 	}
	
	public static WebDriver LG_AR2(WebDriver driver){ 		return LG(driver, "AR2", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_AR2(WebDriver driver, String MS){ 		return LG(driver, "AR2", MS, false); 	}
	
	public static WebDriver LG_AAR1(WebDriver driver){ 		return LG(driver, "AAR1", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_AAR1(WebDriver driver, boolean ForceLogin){ 		return LG(driver, "AAR1", ProjectDataVariables.get_EUCR_REG(), ForceLogin); 	}
	public static WebDriver LG_AAR1(WebDriver driver, String MS){ 		return LG(driver, "AAR1", MS, false); 	}
	
	public static WebDriver LG_AAR2(WebDriver driver){ 		return LG(driver, "AAR2", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_AAR2(WebDriver driver, String MS){ 		return LG(driver, "AAR2", MS, false); 	}

	public static WebDriver LG_USR(WebDriver driver){		ax.SET_REGISTRY("BG");		return LG(driver, "USR", ProjectDataVariables.get_EUCR_REG(), false); 	}
	public static WebDriver LG_VER(WebDriver driver){ 		ax.SET_REGISTRY("BG");		return LG(driver, "VER1", ProjectDataVariables.get_EUCR_REG(), false); 	}
	
	
	public static WebDriver LG_PAIR(WebDriver driver, String CSV_PAIR, int whichOne){
		String FX = (whichOne==1)?CSV_PAIR.split(",")[0]:CSV_PAIR.split(",")[1];
		return LG(driver, FX, ProjectDataVariables.get_EUCR_REG(), false);}
	
	public static WebDriver LG_PAIR(WebDriver driver, ArrayList<String> CSV_PAIR, int whichOne){
		String FX = (whichOne==1)?CSV_PAIR.get(0):CSV_PAIR.get(1);
		return LG(driver, FX, ProjectDataVariables.get_EUCR_REG(), false);}
	
	public static WebDriver LG(WebDriver driver, String FX){
		return LG(driver, FX, ProjectDataVariables.get_EUCR_REG(), false);
	}
	
	public static WebDriver LG(WebDriver driver, String FX, String MS, boolean forceRelogin){
		return LG(driver, FX, MS, 1, forceRelogin);
	}
	
	public static WebDriver LG(WebDriver driver, String FX, String MS, int driverInstance, boolean forceRelogin){
		if(FX.contains("ESD")) 
			{		
				MS = "ED";
				ProjectDataVariables.set_EUCR_REG(MS);
			}else{	
				ProjectDataVariables.set_EUCR_REG(MS); 
			}
		
		if(driverInstance==1){
			driver 					= Setuper.GET_DRIVER_BY_FX(String.valueOf(LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX(FX)));
		}
		
		String newUserName 		= ax.GET_USER_NAME_BY_USER_FX(FX);
		if(ax.IS_LOGGED(driver) && forceRelogin){
			// always relogin
        	EcasAuthentication.LOGOUT_FROM_EUCR_TO_CLEAR_HOMEPAGE(driver);
		}else if(ax.IS_LOGGED(driver) && !forceRelogin && !PageCanvas.getApplicationLoggedIn_Username(driver).trim().toUpperCase().equals(newUserName.toUpperCase())){
			// different user - require relogin
        	EcasAuthentication.LOGOUT_FROM_EUCR_TO_CLEAR_HOMEPAGE(driver);
		}else if(ax.IS_LOGGED(driver) && !forceRelogin && PageCanvas.getApplicationLoggedIn_Username(driver).trim().toUpperCase().equals(newUserName.toUpperCase()) && !MS.equals( AppGetter.GET_REG_FROM_URL(driver.getCurrentUrl()))){
			EcasAuthentication.LOGOUT_FROM_EUCR_TO_CLEAR_HOMEPAGE(driver);
		}else if(ax.IS_LOGGED(driver) && !forceRelogin && PageCanvas.getApplicationLoggedIn_Username(driver).trim().toUpperCase().equals(newUserName.toUpperCase()) && MS.equals( AppGetter.GET_REG_FROM_URL(driver.getCurrentUrl()))){
			// relogin is not required
			ax.log("Already Logged in as " + newUserName);
			return driver;
		}
			boolean relogin = EcasAuthentication.NEW_RELOGIN(driver, newUserName, MS);
			if(driver==null || !ax.IS_LOGGED(driver)){
				driver = Setuper.GET_DRIVER_BY_FX(FX);
				driver = ax.OPEN_EUCR_HOME_PAGE_RET_DRIVER(driver, MS);
				driver = LG(driver, FX, MS, forceRelogin);
			}
			ax.log("Logged in as: " + PageCanvas.getApplicationLoggedIn_Username(driver) + " ["+FX+"] into "+MS + ": "+relogin);
			return driver;
	}
	
	
	
	
//	public static WebDriver LOGIN_AR1(WebDriver driver){										return LOGIN_AR1(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_AR1, false); }
//	public static WebDriver LOGIN_AR1(WebDriver driver, boolean ForceRelogin){					return LOGIN_AR1(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_AR1, ForceRelogin); }
//	public static WebDriver LOGIN_AR1(WebDriver driver, String MS){ 							return LOGIN_AR1(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_AR1, false); }
//	public static WebDriver LOGIN_AR1(WebDriver driver, int DriverInstance){ 					return LOGIN_AR1(driver, ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
//	public static WebDriver LOGIN_AR1(WebDriver driver, String MS, int DriverInstance){			return LOGIN_AR1(driver, MS, DriverInstance, false); }
//	public static WebDriver LOGIN_AR1(WebDriver driver, String MS, boolean ForceRelogin){ 		return LOGIN_AR1(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_AR1, ForceRelogin); }
//	public static WebDriver LOGIN_AR1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 
//		ax.SET_REGISTRY(MS);
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("AR1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD, ForceRelogin);	
//		default:
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AR1", MS, DriverInstance, ForceRelogin);
//		}
//	}	
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	public static WebDriver LOGIN_AR2(WebDriver driver){										return LOGIN_AR2(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_AR2, false); }
//	public static WebDriver LOGIN_AR2(WebDriver driver, boolean ForceRelogin){					return LOGIN_AR2(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_AR2, ForceRelogin); }
//	public static WebDriver LOGIN_AR2(WebDriver driver, String MS){ 							return LOGIN_AR2(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_AR2, false); }
//	public static WebDriver LOGIN_AR2(WebDriver driver, int DriverInstance){ 					return LOGIN_AR2(driver, ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
//	public static WebDriver LOGIN_AR2(WebDriver driver, String MS, int DriverInstance){			return LOGIN_AR2(driver, MS, DriverInstance, false); }
//	public static WebDriver LOGIN_AR2(WebDriver driver, String MS, boolean ForceRelogin){ 		return LOGIN_AR2(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_AR2, ForceRelogin); }
//	public static WebDriver LOGIN_AR2(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 
//		ax.SET_REGISTRY(MS);
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver4, ax.GET_USER_NAME("AR2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD, ForceRelogin);	
//		default:
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AR2", MS, DriverInstance, ForceRelogin);
//		}
//	}	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	public static WebDriver LOGIN_CA1(WebDriver driver){										return LOGIN_CA1(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_CA1, false); }
//	public static WebDriver LOGIN_CA1(WebDriver driver, boolean ForceRelogin){					return LOGIN_CA1(driver, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_CA1, ForceRelogin); }
//	public static WebDriver LOGIN_CA1(WebDriver driver, String MS){ 							return LOGIN_CA1(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_CA1, false); }
//	public static WebDriver LOGIN_CA1(WebDriver driver, int DriverInstance){ 					return LOGIN_CA1(driver, ProjectDataVariables.get_EUCR_REG(), DriverInstance, false); } 
//	public static WebDriver LOGIN_CA1(WebDriver driver, String MS, int DriverInstance){			return LOGIN_CA1(driver, MS, DriverInstance, false); }
//	public static WebDriver LOGIN_CA1(WebDriver driver, String MS, boolean ForceRelogin){ 		return LOGIN_CA1(driver, MS, LoginToEUCR.DRIVER_INSTANCE_FOR_CA1, ForceRelogin); }
//	public static WebDriver LOGIN_CA1(WebDriver driver, String MS, int DriverInstance, boolean ForceRelogin){ 
//		ax.SET_REGISTRY(MS);
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver, ax.GET_USER_NAME("CA1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD, ForceRelogin);	
//		default:
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA1", MS, DriverInstance, ForceRelogin);
//		}
//	}
	
//	public static WebDriver LOGIN_NA1(WebDriver driver){
//		String ENV = ax.GET_PROP("TEST_ENVRMENT");
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver, ax.GET_USER_NAME("NA1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);	
//		default: 
//			return ax.LOGIN(driver, "NA1", LoginToEUCR.DRIVER_INSTANCE_FOR_NA1);
//		}
//		}
//	public static WebDriver LOGIN_NA1(WebDriver driver, int DriverInstance){ 				
//		return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "NA1", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	
//		}
//	public static WebDriver LOGIN_NA1(WebDriver driver, String MS){ 
//		ax.SET_REGISTRY(MS);
//		return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "NA1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA1"));	
//		}
//	public static WebDriver LOGIN_NA1(WebDriver driver, String MS, int DriverInstance){ 	
//		ax.SET_REGISTRY(MS);
//		return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "NA1", MS, DriverInstance);	
//		}
	
	
	
	
	
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	public static WebDriver LOGIN_NA2(WebDriver driver){ 		
//		String ENV = ax.GET_PROP("TEST_ENVRMENT");
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver2, ax.GET_USER_NAME("NA2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);	
//		default: 	
//			return ax.LOGIN(driver, "NA2", LoginToEUCR.DRIVER_INSTANCE_FOR_NA2);
//		}
//		}
//	public static WebDriver LOGIN_NA2(WebDriver driver, int DriverInstance){ 				return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "NA2", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	}
//	public static WebDriver LOGIN_NA2(WebDriver driver, String MS, int DriverInstance){ 	
//		ax.SET_REGISTRY(MS);
//		return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "NA2", MS, DriverInstance);	}
//	public static WebDriver LOGIN_NA2(WebDriver driver, String MS){ 
//		ax.SET_REGISTRY(MS);
//		return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "NA2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("NA2"));	}
	

	
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	public static WebDriver LOGIN_AR2(WebDriver driver){
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("AR2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);	
//		default:
//			return ax.LOGIN(driver, "AR2", LoginToEUCR.DRIVER_INSTANCE_FOR_AR2);
//		}
//		}
//	public static WebDriver LOGIN_AR2(WebDriver driver, String MS){ 
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AR2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AR2"));	
//		}
//	public static WebDriver LOGIN_AR2(WebDriver driver, int DriverInstance){ 				
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AR2", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	
//		}
//	public static WebDriver LOGIN_AR2(WebDriver driver, String MS, int DriverInstance){ 	
//			return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AR2", MS, DriverInstance);	
//		}
//	
//	public static WebDriver LOGIN_AAR1(WebDriver driver){
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("AAR1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);	
//		default:
//			return ax.LOGIN(driver, "AAR1", LoginToEUCR.DRIVER_INSTANCE_FOR_AAR1);
//		}
//		}
//	public static WebDriver LOGIN_AAR1(WebDriver driver, int DriverInstance){ 				return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AAR1", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	}
//	public static WebDriver LOGIN_AAR1(WebDriver driver, String MS, int DriverInstance){ 	return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AAR1", MS, DriverInstance);	}
//	public static WebDriver LOGIN_AAR1(WebDriver driver, String MS){ return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "AAR1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("AAR1"));	}
//
//	public static WebDriver LOGIN_AAR2(WebDriver driver){
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver2, ax.GET_USER_NAME("AAR2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);	
//		default:
//			return ax.LOGIN(driver, "AAR2", LoginToEUCR.DRIVER_INSTANCE_FOR_AAR2);
//		}
//		}	
	// +++++++++++++++++++++++++++++++++++++++++++
//	public static WebDriver LOGIN_CA1(WebDriver driver){ 	
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver, ax.GET_USER_NAME("CA1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
//		default:
//			return ax.LOGIN(driver, "CA1", LoginToEUCR.DRIVER_INSTANCE_FOR_CA1);
//		}
//		}
//	public static WebDriver LOGIN_CA1(WebDriver driver, int DriverInstance){ 				return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA1", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	}
//	public static WebDriver LOGIN_CA1(WebDriver driver, String MS, int DriverInstance){ 	return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA1", MS, DriverInstance);	}
//	public static WebDriver LOGIN_CA1(WebDriver driver, String MS){ return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA1"));	}
	
//	public static WebDriver LOGIN_CA2(WebDriver driver){
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver2, ax.GET_USER_NAME("CA2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
//		default:
//			return ax.LOGIN(driver, "CA2", LoginToEUCR.DRIVER_INSTANCE_FOR_CA2);
//		}
//		}
//	public static WebDriver LOGIN_CA2(WebDriver driver, int DriverInstance){ 				return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA2", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	}
//	public static WebDriver LOGIN_CA2(WebDriver driver, String MS, int DriverInstance){ 	return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA2", MS, DriverInstance);	}
//	public static WebDriver LOGIN_CA2(WebDriver driver, String MS){ return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA2", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA2"));	}
	
//	public static WebDriver LOGIN_CA3(WebDriver driver){
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("CA3"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
//		default:
//			return ax.LOGIN(driver, "CA3", LoginToEUCR.DRIVER_INSTANCE_FOR_CA3);
//		}
//				
//		}
//	public static WebDriver LOGIN_CA3(WebDriver driver, int DriverInstance){ 				return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA3", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	}
//	public static WebDriver LOGIN_CA3(WebDriver driver, String MS, int DriverInstance){ 	return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA3", MS, DriverInstance);	}
//	public static WebDriver LOGIN_CA3(WebDriver driver, String MS){ return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "CA3", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("CA3"));	}
//	
//	public static WebDriver LOGIN_VER1(WebDriver driver){
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("VER1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
//		default:
//			return ax.LOGIN(driver, "VER1", LoginToEUCR.DRIVER_INSTANCE_FOR_AR1);
//		}
//		}
//	public static WebDriver LOGIN_VER1(WebDriver driver, int DriverInstance){ 				return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "VER1", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	}
//	public static WebDriver LOGIN_VER1(WebDriver driver, String MS, int DriverInstance){ 	return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "VER1", MS, DriverInstance);	}
//	public static WebDriver LOGIN_VER1(WebDriver driver, String MS){ return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "VER1", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("VER1"));	}
	
//	public static WebDriver LOGIN_USR(WebDriver driver){
//		String ENV = Setuper.GET_ENVIRONMENT();
//		switch (ENV) {
//		case "LA": 
//			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("USR"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
//		default:
//			return ax.LOGIN(driver, "USR", LoginToEUCR.DRIVER_INSTANCE_FOR_AR1);
//		}
//			
//		}
//	public static WebDriver LOGIN_USR(WebDriver driver, int DriverInstance){ 				return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "USR", ProjectDataVariables.get_EUCR_REG(), DriverInstance);	}
//	public static WebDriver LOGIN_USR(WebDriver driver, String MS, int DriverInstance){ 	return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "USR", MS, DriverInstance);	}
//	public static WebDriver LOGIN_USR(WebDriver driver, String MS){ return LoginToEUCR.LOGIN_EUCR_DRIVER_MS(driver, "USR", MS, LoginToEUCR.GET_DRIVER_INSTANCE_BY_FX("USR"));	}
//	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static WebDriver LG_CENTER_CA(WebDriver driver){
		return ax.LG_CA1(driver, "EU");
	}
	public static WebDriver LG_CENTER_CA(WebDriver driver, int CA_Number){
		switch (CA_Number) {
		case 1: return ax.LG_CA1(driver, "EU");
		case 2: return ax.LG_CA2(driver, "EU");
		case 3: return ax.LG_CA3(driver, "EU");
		}
		return ax.LG_CA1(driver, "EU");
	}
	
	
	
	public static WebDriver LOGIN_AS(WebDriver driver, String USERNAME){ 	
		return LoginToEUCR.LOGIN_EUCR_AS(driver, USERNAME, ProjectDataVariables.get_EUCR_REG(), LoginToEUCR.DRIVER_INSTANCE_FOR_NA1);}
	
	public static WebDriver LOGIN_AS(WebDriver driver, String USERNAME, int DriverInstance){ 	
		return LoginToEUCR.LOGIN_EUCR_AS(driver, USERNAME, ProjectDataVariables.get_EUCR_REG(), DriverInstance);}
	public static WebDriver LOGIN_AS(WebDriver driver, String USERNAME, int DriverInstance, boolean HardRelogin){
		
		switch (DriverInstance) {
		case 1: 	driver = Setuper.driver; 		break;
		case 2: 	driver = Setuper.driver2; 		break;
		case 3: 	driver = Setuper.driver3; 		break;
		case 4: 	driver = Setuper.driver4; 		break;
		default:
			driver = Setuper.driver; 		break;
		}
		
		
			if(LoginToEUCR.IS_LOGGED_EUCR_WINDOW_OPENED(driver) && HardRelogin){
				LoginToEUCR.LogoutEUCR_AND_ECAS(driver, DriverInstance);
			}
		return LoginToEUCR.LOGIN_EUCR_AS(driver, USERNAME, ProjectDataVariables.get_EUCR_REG(), DriverInstance);
		}
	
	public static WebDriver LOGIN_AS(WebDriver driver, String USERNAME, String MS, int DriverInstance){ 	
		return LoginToEUCR.LOGIN_EUCR_AS(driver, USERNAME, MS, DriverInstance);}
	
	public static WebDriver LOGIN_EUTL_DRIVER(WebDriver driver){ 		
		return EUTL_MainWindow.LOGIN_TO_EUTL(driver); 	}
	
	public static WebDriver LOGIN_ESD_CA1(WebDriver driver){ 							
		String ENV = Setuper.GET_ENVIRONMENT();
		switch (ENV) {
		case "LA": 
			return LA_LOGIN(Setuper.driver, ax.GET_USER_NAME("ESDCA1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
		default:
			return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDCA1", "ED", LoginToEUCR.DRIVER_INSTANCE_FOR_NA1); 	
		}	
		}
	public static WebDriver LOGIN_ESD_CA1(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDCA1", "ED", InstanceDriver); 	}
	public static WebDriver LOGIN_ESD_CA2(WebDriver driver){ 							
		String ENV = Setuper.GET_ENVIRONMENT();
		switch (ENV) {
		case "LA": 
			return LA_LOGIN(Setuper.driver2, ax.GET_USER_NAME("ESDCA2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
		default:		
			return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDCA2", "ED", LoginToEUCR.DRIVER_INSTANCE_FOR_NA2); 	
		}	
		}
	public static WebDriver LOGIN_ESD_CA2(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDCA2", "ED", InstanceDriver); 	}
	public static WebDriver LOGIN_ESD_AR1(WebDriver driver){ 							
		String ENV = Setuper.GET_ENVIRONMENT();
		switch (ENV) {
		case "LA": 
			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("ESDAR1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
		default:
			return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAR1", "ED", LoginToEUCR.DRIVER_INSTANCE_FOR_AR1); 	
			}
		}
	public static WebDriver LOGIN_ESD_AR1(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAR1", "ED", InstanceDriver); 	}
	public static WebDriver LOGIN_ESD_AR2(WebDriver driver){ 							
		String ENV = Setuper.GET_ENVIRONMENT();
		switch (ENV) {
		case "LA": 
			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("ESDAR2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
		default:
			return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAR2", "ED", LoginToEUCR.DRIVER_INSTANCE_FOR_AR2); 	
			}	
		}
	public static WebDriver LOGIN_ESD_AR2(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAR2", "ED", InstanceDriver); 	}
	public static WebDriver LOGIN_ESD_AAR1(WebDriver driver){ 							
		String ENV = Setuper.GET_ENVIRONMENT();
		switch (ENV) {
		case "LA": 
			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("ESDAAR1"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
		default:
			return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAAR1", "ED", LoginToEUCR.DRIVER_INSTANCE_FOR_AAR1); 	}
		}
	public static WebDriver LOGIN_ESD_AAR1(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAAR1", "ED", InstanceDriver); 	}
	public static WebDriver LOGIN_ESD_AAR2(WebDriver driver){ 							
		String ENV = Setuper.GET_ENVIRONMENT();
		switch (ENV) {
		case "LA": 
			return LA_LOGIN(Setuper.driver3, ax.GET_USER_NAME("ESDAAR2"), ProjectDataVariables.EUCR_USER_DEFAULT_PASSWORD);
		default:
			return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAAR2", "ED", LoginToEUCR.DRIVER_INSTANCE_FOR_AR2); 	
			}
		}
	public static WebDriver LOGIN_ESD_AAR2(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDAAR2", "ED", InstanceDriver); 	}
	
	public static WebDriver LOGIN_ESD_SD1(WebDriver driver){ 							return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDSD1", "ED", 3); 	}
	public static WebDriver LOGIN_ESD_SD1(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDSD1", "ED", InstanceDriver); 	}
	public static WebDriver LOGIN_ESD_SD2(WebDriver driver){ 							return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDSD2", "ED", 3); 	}
	public static WebDriver LOGIN_ESD_SD2(WebDriver driver, int InstanceDriver){ 		return LoginToEUCR.LOGIN_ESD_DRIVER_MS(driver, "ESDSD2", "ED", InstanceDriver); 	}
	
	public static WebDriver LOGIN_FX(WebDriver driver, String FX){
		switch (FX) {
		case "NA":
		case "NA1": 			return ax.LOGIN_NA1(driver);
		case "NA2": 			return ax.LOGIN_NA2(driver);
		case "AR": 
		case "AR1": 			return ax.LOGIN_AR1(driver);
		case "AR2": 			return ax.LOGIN_AR2(driver);
		case "AAR":
		case "AAR1": 			return ax.LOGIN_AAR1(driver);
		case "CA":
		case "CA1": 			return ax.LOGIN_CA1(driver);
		case "CA2": 			return ax.LOGIN_CA2(driver);
		case "CA3": 			return ax.LOGIN_CA3(driver);
		
		case "USR": 			return ax.LOGIN_USR(driver);
		case "VER": 			return ax.LOGIN_VER1(driver);
		
		default: 				return ax.LOGIN_NA1(driver);
		}
	}
	
	
	
	public static WebDriver LA_RETURN_DRIVER(WebDriver driver, String UserName){
		if(UserName.equals(ax.GET_USER_NAME("NA1"))){
    		return Setuper.driver;	
    	}else if(UserName.equals(ax.GET_USER_NAME("NA2"))){
    		return Setuper.driver2;
    	}else if(UserName.equals(ax.GET_USER_NAME("AR1"))){
    		return Setuper.driver3;
    	}else if(UserName.equals(ax.GET_USER_NAME("AR2"))){
    		return Setuper.driver3;
    	}else if(UserName.equals(ax.GET_USER_NAME("AAR1"))){
    		return Setuper.driver3;
    	}
		return driver;
	}
	public static WebDriver LA_LOGIN(WebDriver driver, String UserName, String Password){
		return LA_LOGIN(driver, UserName, Password, false);
	}
	
	public static String GET_LA_URL(String Registry){
		String url = "http://"+LA_DOMAIN_ADRES+"/euregistry/"+Registry+"/index.xhtml";
		if(ax.IS_NULL_OR_EMPTY_STRING(url)){
			ax.log("Problem with compiling LA HOME URL");
			return null;
		}
		return url;
	}
	
	
	public static WebDriver LA_LOGIN(WebDriver driver, String UserName, String Password, boolean ForceRelogin){
	    String LoggedUser;
	    
	    if(!ForceRelogin){
			if(ax.isDisplayed_And_Enabled(driver, RepoEucr.EUCR_CANVAS_CURRENT_USERNAME_LOGGED_IN)){
				LoggedUser = LoginToEUCR.GET_ETS_CURRENT_USERNAME_IF_DISPLAYED(driver);
				if (LoggedUser.trim().equals(UserName)){
						return LA_RETURN_DRIVER(driver, UserName);
				}
			}
	    }
	    
		LoginToEUCR.CLICK_LOGOUT_EUCR_TOP_LINK(driver);
		ax.driverGet(driver, GET_LA_URL(ProjectDataVariables.get_EUCR_REG()));
	    LoginToEUCR.CLICK_LOGIN_LEFT_LINK(driver);
	    ax.WAIT4PageLoad(driver);
	    // When login for the first time - WAYF is displayed
	    WAYF(driver);
	    if(!ax.isDisplayed_And_Enabled(driver, By.xpath(userNameOrEmailEditBox)) && ax.isDisplayed_And_Enabled(driver, By.xpath(notCurrentUserButton))){
	    	ax.clickLink(driver, By.xpath(notCurrentUserButton));
	    	ax.click_link_and_wait(driver, By.xpath(loginLinkLI_PIPE));
	    	ENTER_USER_PASS_SUBMIT(driver, UserName, Password);
	    }else{
	    	ENTER_USER_PASS_SUBMIT(driver, UserName, Password);	
	    }
	    // Los Alamos - case with login authentication ON
	    if(Setuper.IS_LOGIN_SMS_ENABLED() && 
	    		(ax.isDisplayedBy(driver, By.id("smsChallenge-0")) && ax.isDisplayedBy(driver, By.id("smsChallenge-1")  ))
	    		&& ax.isDisplayedBy(driver, By.name("_submit"))
	    				){
	    	ax.click_link_and_wait(driver, By.name("_submit"));
	    	
	    }
	    
	    ax.WAIT4PageLoad_with_TargetPageTitle(driver, "EUCR / "+ProjectDataVariables.get_EUCR_REG()+" / Home Page");
	    
	    
	    if(LoginToEUCR.IS_LOGGED_EUCR_WINDOW_OPENED(driver)){
	    	ax.log("Logged in as "+UserName);
	    		return LA_RETURN_DRIVER(driver, UserName);
	    }else{
//	    	ax.log("Problem with Log in in LosAlamos as "+UserName);
	    	ax.t_error(driver, "Problem with Log in in LosAlamos as "+UserName);
	    	return null;
	    }
	}
	public static void WAYF(WebDriver driver){
	    if(driver.getTitle().contains("Where are you from") && ax.isDisplayed_And_Enabled(driver, By.xpath(ExternalDomanBox))){
	    	ax.click_link_and_wait(driver, By.xpath(ExternalDomanBox));
	    	ax.WAIT4PageLoad_with_TargetPageTitle(driver, "Login");
	    }
	}
	public static void ENTER_USER_PASS_SUBMIT(WebDriver driver, String UserName, String Password){
		
	    if(ax.isDisplayed_And_Enabled(driver, By.xpath(userNameOrEmailEditBox)) &&  ax.isDisplayed_And_Enabled(driver, By.xpath(userPasswordEditBox))){
	    	ax.type_clear_bool(driver, By.xpath(userNameOrEmailEditBox), UserName);
	    	ax.type_clear_bool(driver, By.xpath(userPasswordEditBox), Password);
	    	if(Setuper.IS_LOGIN_SMS_ENABLED()){
	    		String mobileNumber = ax.GET_USER_TEL(ax.GET_USER_FX_BY_USERNAME(UserName));
	    		ax.type_clear_bool(driver, By.xpath(userMobilePhoneEditBox), mobileNumber); // LoginToEUCR.MOBILE_PHONE_LOS_ALAMOS
	    	}
	    	ax.click_link_and_wait(driver, By.xpath(userLoginButton));
	    	
	    }
	}
	
	public static WebDriver LOGIN_AS_GEN_NA_IF_CURRENT_IS_NOT_NA(WebDriver driver, String URID){
		if(ax.IS_NULL_OR_EMPTY_STRING(URID)){
				ax.t_error(driver, "NO URID PROVIDED TO [LOGIN_AS_GEN_NA_IF_CURRENT_IS_NOT_NA]");
		}
		if(!ax.IS_CURR_USER_NA(driver, URID)){
			return ax.LOGIN_NA1(driver);	
		}
		return driver;
	}
	
	public static WebDriver LOGIN_PAIR(WebDriver driver, ArrayList<String> LOGIN_PAIR_ARRAY, int PROP_APPR /*1-PROPOSER, 2-APPROVER*/){
		return LOGIN_PAIR(driver, LOGIN_PAIR_ARRAY, PROP_APPR, false);
	}
	
	public static WebDriver LOGIN_PAIR(WebDriver driver, ArrayList<String> LOGIN_PAIR_ARRAY, int PROP_APPR /*1-PROPOSER, 2-APPROVER*/, boolean ForceRelogin){
		String LOGIN_PAIR_CSV = ax.getArrayListStringIntoCSVString(LOGIN_PAIR_ARRAY);
		return LOGIN_PAIR(driver, LOGIN_PAIR_CSV, PROP_APPR, ProjectDataVariables.get_EUCR_REG(), ForceRelogin );
	}
	
	public static WebDriver LOGIN_PAIR(WebDriver driver, String LOGIN_PAIR_CSV, int PROP_APPR /*1-PROPOSER, 2-APPROVER*/){
		return LOGIN_PAIR(driver, LOGIN_PAIR_CSV, PROP_APPR, false);
	}
	public static WebDriver LOGIN_PAIR(WebDriver driver, String LOGIN_PAIR_CSV, int PROP_APPR /*1-PROPOSER, 2-APPROVER*/, boolean ForceRelogin){
		return LOGIN_PAIR(driver, LOGIN_PAIR_CSV, PROP_APPR, ProjectDataVariables.get_EUCR_REG(), ForceRelogin);
	}
	
	public static WebDriver LOGIN_PAIR(WebDriver driver, String LOGIN_PAIR_CSV, int PROP_APPR /*1-PROPOSER, 2-APPROVER, 3-THIRD*/, String REG){
		return LOGIN_PAIR(driver, LOGIN_PAIR_CSV, PROP_APPR, REG, false);
	}
	public static WebDriver LOGIN_PAIR(WebDriver driver, String LOGIN_PAIR_CSV, int PROP_APPR /*1-PROPOSER, 2-APPROVER, 3-THIRD*/, String REG, boolean ForceRelogin/*true-NoRelogin, false-Relogin*/){
		String PROP = ax.GET_CSV_ITEM_BY_INDEX(LOGIN_PAIR_CSV, 1);
		String APPR = ax.GET_CSV_ITEM_BY_INDEX(LOGIN_PAIR_CSV, 2);
		String THIRD = null;
		
		if(LOGIN_PAIR_CSV.split(",").length > 2){
			THIRD = LOGIN_PAIR_CSV.split(",")[2];
		}
		 
		switch (PROP_APPR) {
		case 1:
			if(PROP.equals("AR")){ 						driver= ax.LOGIN_AR1(driver, ForceRelogin); break; 			}
			else if(PROP.equals("AR1")){ 				driver= ax.LOGIN_AR1(driver, ForceRelogin); break; 			}
			else if(PROP.equals("AR2")){ 				driver= ax.LOGIN_AR2(driver); break; 			}
			else if(PROP.equals("NA")){ 				driver= ax.LOGIN_NA1(driver); break; 			}
			else if(PROP.equals("NA1")){ 				driver= ax.LOGIN_NA1(driver); break; 			}
			else if(PROP.equals("NA2")){ 				driver= ax.LOGIN_NA2(driver); break; 			}
			else if(PROP.equals("AAR1")){ 				driver= ax.LOGIN_AAR1(driver); break; 			}
			else if(PROP.equals("AAR2")){ 				driver= ax.LOGIN_AAR2(driver); break; 			}
			else if(PROP.equals("CA1")){ 				driver= ax.LOGIN_CA1(driver); break; 			}
			else if(PROP.equals("CA2")){ 				driver= ax.LOGIN_CA2(driver); break; 			}
			else if(PROP.equals("CA3")){ 				driver= ax.LOGIN_CA3(driver); break; 			}
			else if(PROP.equals("NULL")){ 				break; 			}
		case 2:
			if(APPR.equals("AR1")){ 					driver= ax.LOGIN_AR1(driver, ForceRelogin); break; 			}
			else if(APPR.equals("AR2")){ 				driver= ax.LOGIN_AR2(driver); break; 			}
			else if(APPR.equals("NA1")){ 				driver= ax.LOGIN_NA1(driver); break; 			}
			else if(APPR.equals("NA")){ 				driver= ax.LOGIN_NA2(driver); break; 			}
			else if(APPR.equals("NA2")){ 				driver= ax.LOGIN_NA2(driver); break; 			}
			else if(APPR.equals("AAR1")){ 				driver= ax.LOGIN_AAR1(driver); break; 			}
			else if(APPR.equals("AAR2")){ 				driver= ax.LOGIN_AAR2(driver); break; 			}
			else if(APPR.equals("CA1")){ 				driver= ax.LOGIN_CA1(driver); break; 			}
			else if(APPR.equals("CA2")){ 				driver= ax.LOGIN_CA2(driver); break; 			}
			else if(APPR.equals("CA3")){ 				driver= ax.LOGIN_CA3(driver); break; 			}
			else if(APPR.equals("NULL")){ 				break; 			}
		case 3:
			if(THIRD.equals("AR1")){ 					driver= ax.LOGIN_AR1(driver, ForceRelogin); break; 			}
			else if(THIRD.equals("AR2")){ 				driver= ax.LOGIN_AR2(driver); break; 			}
			else if(THIRD.equals("NA1")){ 				driver= ax.LOGIN_NA1(driver); break; 			}
			else if(THIRD.equals("NA")){ 				driver= ax.LOGIN_NA2(driver); break; 			}
			else if(THIRD.equals("NA2")){ 				driver= ax.LOGIN_NA2(driver); break; 			}
			else if(THIRD.equals("AAR1")){ 				driver= ax.LOGIN_AAR1(driver); break; 			}
			else if(THIRD.equals("AAR2")){ 				driver= ax.LOGIN_AAR2(driver); break; 			}
			else if(THIRD.equals("CA1")){ 				driver= ax.LOGIN_CA1(driver); break; 			}
			else if(THIRD.equals("CA2")){ 				driver= ax.LOGIN_CA2(driver); break; 			}
			else if(THIRD.equals("CA3")){ 				driver= ax.LOGIN_CA3(driver); break; 			}
			
			else if(THIRD.equals("NULL")){ 				break; 			}
			
		}
	return driver;
	}
	
	public static boolean RELOGIN_USER(WebDriver driver){
		if(LoginToEUCR.IS_LOGGED_EUCR_WINDOW_OPENED(driver)){
			return LoginToEUCR.CLICK_LOGOUT_EUCR_TOP_LINK(driver);
		} return true;
	}
	
	
	public static String GET_FULL_ACC_NUMBER(String AccountIdentifier){
		if(ax.IS_NULL_OR_EMPTY_STRING(AccountIdentifier)){return "";}
		if(AccountIdentifier.equals("5000000")){
			return "EU-100-5000000-0-01";
		}else{
			return ApplicationPageUtils.GET_FULL_ACCOUNT_NUMBER(Setuper.driver2, AccountIdentifier);
//			return AppSqlQueries.get_FULL_ACCOUNT_NUMBER_ByAccountIdentifier(AccountIdentifier);
			}	
		}
	
	public static String GET_FULL_NUMBER_IF_FULL_NOT_PROVIDED(String Account){
		return (!ax.IS_FULL_ACCOUNT_SHAPE(Account))?ax.GET_FULL_ACC_NUMBER(Account):Account;
	}
	
	
	public static String GET_TIMESTAMP_SAVE(){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
			 Date date = new Date();
			 String s=(dateFormat.format(date));
			 return s.replace("-", "_");
	}
	public static String GET_TIME_COLON_STAMP_SAVE(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
		 Date date = new Date();
		 String s=(dateFormat.format(date));
		 return s.replace("-", "_");
}
	public static String GET_DASHED_AFTER_TEXT(String g){ // from "ST-OPEN" will return "OPEN"
		if(!g.contains("-")){return g;}
		if(g.isEmpty()){return null;}
		String r = g.substring(g.indexOf("-")+1).trim();
		return r;
	}
	public static String GET_DASHED_BEFORE_TEXT(String g){// from "ST-OPEN" will return "ST"
		if(g.isEmpty()){return null;}
		if(!g.contains("-")){return g;}
	  	  return g.substring(0, g.indexOf("-")).trim();
	}
	
	public static String[] REMOVE_FIRST_PREDASH(String[] DASHED){
		String[] NEW_CLEANED = new String[DASHED.length];
		for(int i=0;i<DASHED.length;i++){
			NEW_CLEANED[i] = GET_TEXT_AFTER_FIRST_DASH(DASHED[i]);
		}
		return NEW_CLEANED;
	}	
	public static String GET_TEXT_AFTER_FIRST_DASH(String g){ // from "ST-OPEN" will return "OPEN"
		if(!g.contains("-")){return g;}
		if(g.isEmpty()){return null;}
		String r = g.substring(g.indexOf("-")+1).trim();
		return r;
	}	
	public static String GET_TEXT_BEFORE_MARKER(String markerSign, String text){ // from "ST-OPEN" will return "OPEN"
		if((text==null) || (markerSign==null)){return null;}
		if(text.isEmpty()){return null;}
		if(!text.contains(markerSign)){return text;}
		String r = text.substring(0,text.indexOf(markerSign)).trim();
		return r;
	}	
	public static String GET_TEXT_AFTER_MARKER(String markerSign, String text){ // from "ST-OPEN" will return "OPEN"
		if((text==null) || (markerSign==null)){return null;}
		if(!text.contains(markerSign)){return text;}
		if(text.isEmpty()){return null;}
		String r = text.substring(text.indexOf(markerSign)+markerSign.length()).trim();
		return r;
	}	
	public static String[] GET_STRING_ARRAY_FROM_DATA_CSV_TEXT(String INPUT_DATA){ // from txt like [OHA,NOAAR,2016,ST-OPEN,EX-NONE,VE-NONE,EM-NONE,SA-TRAD]
		if(ax.IS_NULL_OR_EMPTY_STRING(INPUT_DATA)){return null;}
		 String regex = "\\[|\\]";
		 INPUT_DATA = INPUT_DATA.replaceAll(regex, "");
		 INPUT_DATA = INPUT_DATA.replaceAll(" ","");
//		 ax.log(INPUT_DATA);
	  return ax.CSVTEXT_2_STRING_ARRAY(INPUT_DATA);
	}
	public static ArrayList<Integer> getArrayListIndexes(ArrayList<String> list, String searchedValue){
		if(list.size()<0){return null;}
		if(!list.contains(searchedValue)){return null;}
		ArrayList<Integer> outINT = new ArrayList<Integer>();
		for (int i=0;i<list.size();i++) { // counting from 1
			if(list.get(i).equals(searchedValue)){
				outINT.add(i+1);
			}
		}
		return outINT;
	}
	public static String GET_ACC_IDENTIFIER_NUMBER(String AccountFullNumber){
		if(AccountFullNumber.contains("-")){
			return ApplicationPageUtils.getAccNrPartFromFullAccountNumber(AccountFullNumber);
		}else{
			return AccountFullNumber;
		}
	}
	public static String GET_ACC_REG_CODE_1(String AccountFullNumber){
		return ApplicationPageUtils.getAccNrAccountRegCode(AccountFullNumber); 
	}
	public static String GET_ACC_TYPE_2(String AccountFullNumber){
		return ApplicationPageUtils.getAccNrAccountType(AccountFullNumber); 
	}
	public static String GET_ACC_IDENTIFIER_3(String AccountFullNumber){
		return ApplicationPageUtils.getAccNrPartFromFullAccountNumber(AccountFullNumber); 
	}
	public static String GET_ACC_IDENTIFIER_4(String AccountFullNumber){
		return ApplicationPageUtils.getAccNrPeriodCode(AccountFullNumber); 
	}
	public static String GET_ACC_CHECK_DIG_5(String AccountFullNumber){
		return ApplicationPageUtils.getCheckDigitsPartFromFullAccountNumber(AccountFullNumber);
	}
	
	public static String GET_ACC_CHECK_DIG_5_BY_IDENTIFIER(String AccountIdentifier){
		return AppSqlQueries.get_ACCOUNT_CHECK_DIGIT_BY_ByAccountIdentifier(AccountIdentifier);
	}
	
	
	
	
	public static String GET_ACCOUNT_TYPE_BY_IDENTIFIER(String AccountIdentifier){
		if(ax.IS_NULL_OR_EMPTY_STRING(AccountIdentifier)){ax.log("No AccountIdentifier provided..");return null;}
		String type = AppSqlQueries.get_EU_ACCOUNT_TYPE_ByAccountIdentifier(AccountIdentifier);
		if(type.equals("OPERATOR_HOLDING")){ 			return "OHA"; }
		else if(type.equals("AIRCRAFT_OPERATOR")){ 		return "AOHA"; }
		else if(type.equals("NATIONAL_HOLDING")){ 		return "NHA"; }
		else if(type.equals("PERSON_HOLDING")){ 		return "PERS"; }
		//else if(type.equals("PARTY_HOLDING")){ 		return "PHA"; }
		else if(type.equals("TRADING")){ 				return "TRAD"; }
		else{return null;}
	}
	
	
	public static boolean IS_THIS_ESD_PAGE(WebDriver driver){
		String CURR_URL = driver.getCurrentUrl();
		if(!CURR_URL.contains("/ED/")){
			return false;
		} return true;
	}

	
	public static WebDriver LOGIN_ESD(WebDriver driver){
		boolean IAM_LOGED = ax.IAM_LOGGED(driver);
			 if(!IAM_LOGED || (IAM_LOGED && !IS_THIS_ESD_PAGE(driver)  ) ){
				 return LoginToESD.LOGIN_AS_CA1(driver);
			 }return driver; 	 
		 }	
	public static WebDriver LOGIN_ESD_AS_CA(WebDriver driver){
		boolean IAM_LOGED = ax.IAM_LOGGED(driver);
			 if(
					 !IAM_LOGED 
					 || (IAM_LOGED && !IS_THIS_ESD_PAGE(driver) ) 
					 || ( IAM_LOGED && IS_THIS_ESD_PAGE(driver) 	 &&  !IAM_USER_WITH_NAME(driver, "ca"))  
					 )
			 {
				return LoginToESD.LOGIN_AS_CA1(driver);
			 }
		 return driver; 	
		 }	
	
	public static boolean IAM_USER_WITH_NAME(WebDriver driver, String USERNAME_PAT){
		String USER_NAME = PageCanvas.getLoggedin_username(driver);
		if(USER_NAME.contains(USERNAME_PAT)){return true;} return false;
	}
	
	public static WebDriver LOGIN_EUTL(WebDriver driver){
		 if(!ax.IAM_LOGGED_EUTL(driver)){
			 return EUTL_MainWindow.LOGIN_TO_EUTL(driver);
		 } 	 
		 return driver;
		 
	}	
	public static WebDriver LOGIN_EUTL_PUBLIC(WebDriver driver){
		EUTLPUBLIC_OPERATIONS.OPEN_EUTL_PUBLIC(driver);
		return driver;
	} 
	public static boolean IAM_LOGGED(WebDriver driver){
		 return LoginToEUCR.IS_LOGGED_EUCR_WINDOW_OPENED(driver); }
	public static boolean IS_LOGGED(WebDriver driver){
		 return LoginToEUCR.IS_LOGGED_EUCR_WINDOW_OPENED(driver); }
	
	
	
	public static boolean IAM_LOGGED_AS_USER(WebDriver driver, String USERNAME){
		// we assume someone is logged into ETS
		String GRABBED_USERNAME = PageCanvas.getLoggedin_username(driver);
		if(!ax.IS_NOT_NULLEMPTY(GRABBED_USERNAME)){return false;}
		return (GRABBED_USERNAME.trim().equals(USERNAME))?true:false;
		 }

	public static boolean IAM_AT_REGISTRY_PAGE(WebDriver driver, String REGISTRY){
		// we assume someone is logged into ETS and is on HOMEPAGE
		LoginToEUCR.OPEN_HOME_PAGE(driver, REGISTRY);
		if(!LoginToEUCR.SWITCH_REG_POPUP(driver)){ax.log("Switch Reg Error");return false;}
		String GRABBED_REGISTRY_FROM_URL = AppGetter.GET_REG_FROM_URL(driver.getCurrentUrl());
		if(ax.IS_NOT_NULLEMPTY(GRABBED_REGISTRY_FROM_URL)){
			if(GRABBED_REGISTRY_FROM_URL.equals(REGISTRY)){
				return true;
			}else{ax.t_error("IAM_LOGGED_TO_REGISTRY - REG conflict [FROM_URL:"+GRABBED_REGISTRY_FROM_URL+"][RegistryExpected:"+REGISTRY+"]");return false;}
		}else{
			ax.t_error(driver, "IAM_LOGGED_TO_REGISTRY - URL REG empty");return false;
		}
	 }
	
	public static boolean IAM_LOGGED_AS_USER_TO_REGISTRY(WebDriver driver, String USERNAME, String REGISTRY){
		return (IAM_LOGGED_AS_USER(driver, USERNAME) && IAM_AT_REGISTRY_PAGE(driver, REGISTRY))?true:false; }
	
	public static boolean IAM_LOGGED_EUTL(WebDriver driver){
		 return EUTL_MainWindow.IS_LOGGED_EUTL_WINDOW_OPENED(driver); }
	
	public static void wait(int seconds){
		int s;
		if(seconds==0){
			s = 500;
		}else{
			s = 1000 * seconds;	
		}
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 	} 
	
	public static void sleep(int seconds){
			SeleniumWaiter.sleeper(seconds); 	}
	
	public static ArrayList<String> UNSPACE_ARRAY_LIST_STRING(ArrayList<String> IN){
		ArrayList<String> OUT = new ArrayList<String>();
		for (String spaced : IN) {
			OUT.add(spaced.trim());
		}
		return OUT; }

	
  	public static String getArrayListStringIntoCSVString(ArrayList<String> ARX){
    	  if(ARX.size()<1) return null;
  		String out = "";
  		String comma = ",";
  		int i=0;
  		for (String string : ARX) {
  			if( i < ARX.size()-1){
  			out +=string+comma;  
  			}else{
  			  out +=string;
  			}
  			i++;
  		}
  		return out;
  	}
	
	public static ArrayList<String> GET_ARRAYLIST_BY_SEPARATOR_COUNT_EMPTY(String SEPARATED_TEXT, String SEPAR_CHAR){
		String[] d = SEPARATED_TEXT.split("\\,", -1);
		return new ArrayList<String>(Arrays.asList(d));
		
		
//		if(SEPAR_CHAR.isEmpty()){
//			list = new ArrayList<String>();	
//		}else{
//			list = new ArrayList<String>(Arrays.asList(SEPARATED_TEXT.split("\\"+SEPAR_CHAR+"", -1)));
//		}
//		ArrayList<String> listTrimmed = ax.trimArrayListString(list);
//		return ax.UNSPACE_ARRAY_LIST_STRING(listTrimmed);
		}	
	
	public static ArrayList<String> GET_ARRAYLIST_BY_SEPARATOR(String SEPARATED_TEXT, String SEPAR_CHAR){
		ArrayList<String> list = null;
		ArrayList<String> unspaced  = null;
		if(SEPAR_CHAR.isEmpty()){
			list = new ArrayList<String>(Arrays.asList(SEPARATED_TEXT.split("\\,")));	
		}else{
			list = new ArrayList<String>(Arrays.asList(SEPARATED_TEXT.split("\\"+SEPAR_CHAR+"")));
		}
		ArrayList<String> listTrimmed = ax.trimArrayListString(list);
		return ax.UNSPACE_ARRAY_LIST_STRING(listTrimmed);
		}	

	public static ArrayList<String> trimArrayListString(ArrayList<String> in){
		if(in.size()<0){return null;}
		ArrayList<String> n = new  ArrayList<String>();
		for (String tmp : in) {
			n.add(tmp.replaceAll("\\s",""));
		}
		return n;
	}
	
	public static ArrayList<String> splitStringIntoArrayList(String inputString, String splittingCharacter){
		String a[] = inputString.split("\\"+splittingCharacter);
		ArrayList<String> d = new  ArrayList<String>(Arrays.asList(a));
		return d;
	}
	
	public static String GET_ESD_MS(String ESDACC){ // from PL2015
		return ESD_AccountOperations.GET_MS_FROM_ESDACC(ESDACC); }

	public static String GET_ESD_YEAR(String ESDACC){
		return ESD_AccountOperations.GET_YR_FROM_ESDACC(ESDACC); }

	public static boolean WAIT_FOR_AYAX_DISSAPEAR(WebDriver driver){
		return ax.WAIT_4_ELEMENT_DISSAPEAR(driver, PF_PageElements.AJAX_LOADER); }
	
	public static void SEPAR(){
		ax.log("#################################################################################"); } 

	public static By INP_IN_TBL_TRTD_PAIR_HOLDER(WebDriver driver, String PARENT_XPATH, String ControlType, String LabelTitle){
//		SAMPLE OF THE HTML PATTERN
//		<tr>
//		<td><label for="manageNaExclusiveTaskListForm:filterByAccountId"> Account Identifier</label></td>
//		<td><input id="manageNaExclusiveTaskListForm:filterByAccountId" type="text" name="manageNaExclusiveTaskListForm:filterByAccountId" maxlength="20" size="50"></td>
//		</tr>
		String cell_with_input = PARENT_XPATH + "//tr/td/label[contains(normalize-space(),'"+LabelTitle+"')]/../..//td//"+ControlType+"";
		if(ax.isDisplayedBy(driver, By.xpath(cell_with_input))){
			return By.xpath(cell_with_input);
		}else{ ax.t_error(driver, "There is no displayed control //["+ControlType+"] for the label "+LabelTitle+". Parent path: ["+PARENT_XPATH+"]"); 
			return null;
		}
		}
		
		public static By INP_IN_TBL_DATEPICKER_PAIR_HOLDER(WebDriver driver, String PARENT_XPATH, String ControlType, String LabelTitle, String FROM_TO /*[From|To] - should be same like on display!!*/){
//			SAMPLE OF THE HTML PATTERN
//			<tr>
//			<td><label for="manageNaExclusiveTaskListForm:filterByAccountId"> Account Identifier</label></td>
//			<td><input id="manageNaExclusiveTaskListForm:filterByAccountId" type="text" name="manageNaExclusiveTaskListForm:filterByAccountId" maxlength="20" size="50"></td>
//			</tr>
			String cell_with_input = PARENT_XPATH + "//tr/td/label[contains(normalize-space(),'"+LabelTitle+"')]/../..//table//td[contains(normalize-space(),'"+FROM_TO+"')]/following-sibling::td[1]//"+ControlType+"";
			if(ax.isDisplayedBy(driver, By.xpath(cell_with_input))){
				return By.xpath(cell_with_input);
			}else{ ax.t_error(driver, "There is no displayed control //["+ControlType+"] for the label "+LabelTitle+". Parent path: ["+PARENT_XPATH+"]"); 
				return null;
			}
		}

		public static boolean CLICK_JS_EXE_element_by_wait_for_page(WebDriver driver, By by){
			List<WebElement> e = driver.findElements(by);
			WebElement e1 = null;
			if(e.size() > 0){
				e1 = e.get(0);
				JavascriptExecutor exe = (JavascriptExecutor)driver;
				exe.executeScript("arguments[0].click();", e1);
				ax.WAIT4PageLoad(driver);
				return true;
			}else{
				ax.t_error(driver, "No such element ["+by.toString()+"]");
				return false;
			}
		}
		public static boolean CLICK_JS_EXE_element_by_wait_for_ayaxLoader(WebDriver driver, By by){
			List<WebElement> e = driver.findElements(by);
			if(e.size()<1)return false;
			WebElement e1 = null;
			if(e.size() > 0){
				e1 = e.get(0);
				JavascriptExecutor exe = (JavascriptExecutor)driver;
				exe.executeScript("arguments[0].click();", e1);
				ax.WAIT_FOR_AYAX_DISSAPEAR(driver);
				return true;
			}else{
				ax.t_error(driver, "No such element ["+by.toString()+"]");
				return false;
			}
		}
		public static boolean CLICK_JS_EXE_element_by(WebDriver driver, By by){
			List<WebElement> e = driver.findElements(by);
			if(e.size()<1)return false;
			WebElement e1 = null;
			if(e.size() > 0){
				e1 = e.get(0);
				JavascriptExecutor exe = (JavascriptExecutor)driver;
				exe.executeScript("arguments[0].click();", e1);
				return true;
			}else{
				ax.t_error(driver, "No such element ["+by.toString()+"]");
				return false;
			}
		}
		public static boolean TYPE_JS_EXE_element_by(WebDriver driver, By by, String typedText){
			List<WebElement> e = driver.findElements(by);
			if(e.size()<1)return false;
			WebElement e1 = null;
			if(e.size() > 0){
				e1 = e.get(0);
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].setAttribute('value','"+typedText+"');", e1);
				return true;
			}else{
				ax.t_error(driver, "No such element ["+by.toString()+"]");
				return false;
			}
		}
		
		public static boolean SELECT_JS_EXE_element_id(WebDriver driver, String ValueOfSelection, String selectedIndex){
			return SELECT_JS_EXE(driver, "id", ValueOfSelection, selectedIndex);
		}
		
		
		public static boolean SELECT_JS_EXE(WebDriver driver, String selectionType, String selectionValue, String selectedIndex){ // only works for id element and selected Index type
			By by 				= null;
			String jsGetElement = null;
			switch (selectionType) {
			case "id": 			
				by = By.id(selectionValue);
				jsGetElement = "document.getElementById('"+selectionValue+"').selectedIndex = "+selectedIndex+";";
			break;
			case "name": 	// so far this is not supported		
				by = By.name(selectionValue);
				jsGetElement = "document.getElementsByName('"+selectionValue+"').selectedIndex = "+selectedIndex+";";
			break;

			default:
				break;
			}
			
			List<WebElement> e = driver.findElements(by);
			if(e.size()<1)return false;
			WebElement e1 = null;
			if(e.size() > 0){
				e1 = e.get(0);
				if(!ax.CHECK_SELECT_FOR_OPTION_PRESENT(driver, by, "index", selectedIndex)){
					ax.log("There is no such OPTION[with index:"+selectedIndex+"] on SELECT element["+by.toString()+"]");
					return false;
				}			
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript(jsGetElement); //"+selectedText+"
				return true;
			}else{
				ax.t_error(driver, "No such element ["+by.toString()+"]");
				return false;
			}
		}		
	
	public static String GRAB_OUTPUT_NR_GREEN_BOX(WebDriver driver, String WordsBefore_REQNR){
		return ApplicationPageUtils.HANDLE_SUCCESS_GREEN_MONIT_RETURN_REQNR(driver, WordsBefore_REQNR);
	} 	
	public static ArrayList<String> GRAB_OUTPUT_NUMBERS_GREEN_BOX_TRANSACTION_PROPOSAL(WebDriver driver){
		return ApplicationPageUtils.HANDLE_SUCCESS_GREEN_MONIT_RETURN_REQNR_AND_TRXNR(driver, "request with id", "assigned the identifier");
	} 	

	
	public static WebDriver logout(WebDriver driver){
		return LoginToEUCR.logoutUserCompletelyStartHomePage(driver);
	}
	
	
	public static void ARRAY_LIST_STRING_TO_TEXT(ArrayList<String> IN){
		for (String string : IN) {
			ax.log(string);
		}
	}
	public static void driverGet(WebDriver driver, String url){
		driver.navigate().to(url);
	}
	public static void saveArrayListInFileAsLine(String fileName, ArrayList<String> arx, boolean asComaParenthesed){
		for (String line : arx) {
			if(asComaParenthesed){
				ax.FILE_APPEND_AS_NEW_LINE(fileName, "'"+line+"',");
			}else{
				ax.FILE_APPEND_AS_NEW_LINE(fileName, line);	
			}
		}
	}
	public static boolean FILE_APPEND_AS_NEW_LINE(String fileName, String txt){
		boolean fileExists = false;
		File f = new File(fileName);
		fileExists = (f.exists() && !f.isDirectory())?true:false; 
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter(fileName, true));
			if(fileExists){
				output.append("\n"+txt);
			}else{
				output.append(txt);
			}	
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
			return true;	
	}
  
	public static void FILE_REPLACE_SELECTED_LINE_IN_FILE(String fileLocation, String key, String valueToAppend) {
	    try {
	    	File f = new File(fileLocation);
	    	if(!f.exists()) { 
	    		PrintWriter writer 	= new PrintWriter(fileLocation, "UTF-8");
	    	}
	        BufferedReader file = new BufferedReader(new FileReader(fileLocation));
	        String line;String input = "";
	        ArrayList<String> lines = new ArrayList<String>();	
	        while ((line = file.readLine()) != null){
	        	 lines.add(line);
	        }
	        file.close();
	        boolean found = false;
	        int i=0;
	        
	        if(lines.size() < 1){
	        	lines.add(key + " " + valueToAppend);
	        }else{
	        	for (String linia : lines) {
					if(linia.contains(key)){
						lines.set(i, linia +" "+ valueToAppend);
						found = true;
						break;
					}
					i++;
					if(i==lines.size() && !found){
						lines.add(key + " " + valueToAppend);
						break;
					}
				}	
	        }
	        
	        
	        ax.log(input); // check that it's inputted right
	        for (String newLine : lines) {
	        	input += newLine + '\n';
			}
	        FileOutputStream fileOut = new FileOutputStream(fileLocation);
	        fileOut.write(input.getBytes());
	        fileOut.close();
	    } catch (Exception e) {
	        ax.log("Problem reading file. ["+fileLocation+"]");
	    }
	}	
	
	
	public static ArrayList<String> FILE_LINES_2_ARRLIST(String FileLocation){
		return FilesUtils.READ_CSV_FILE_TO_ARRAYLIST(FileLocation, "NO");
	}
	
	public static ArrayList<String> CSVFILE_2_ARRLIST(String FileLocation, String SkipFirstLine /*YES|NO*/){
		return FilesUtils.READ_CSV_FILE_TO_ARRAYLIST(FileLocation, SkipFirstLine);
	}
	public static ArrayList<String> CSVTEXT_2_ARRLIST(String CSV_TEXT){
		if(ax.IS_NULL_OR_EMPTY_STRING(CSV_TEXT)){
			ax.info("INPUT TEXT IS EMPTY...");
			return null;
		}
		ArrayList<String> xx = new ArrayList<String>();
		if(!CSV_TEXT.contains(",")){ 
			ax.info("INPUT TEXT HAS NO COMMAS - Probabily one element array"); 
			xx.add(CSV_TEXT); return xx;
//			return null; 
			}
		String[] tx = CSV_TEXT.split(",");
		for(int i=0; i< tx.length;i++){
			xx.add(tx[i].trim());
		}
		return xx;
	}

	public static String[] CSVTEXT_2_STRING_ARRAY(String CSV_TEXT){
		
		if(ax.IS_NULL_OR_EMPTY_STRING(CSV_TEXT)){
			ax.info("INPUT TEXT IS EMPTY...");
			return null;}
		if(!CSV_TEXT.contains(",")){ 
			ax.info("INPUT TEXT HAS NO COMMAS - Probabily one element array"); 
			return null;}
		String[] tx = CSV_TEXT.split(",");
		String[] xx = new String[tx.length];
		for(int i=0; i< tx.length;i++){
			xx[i] = tx[i].trim(); 
		}
		return xx;
	}	
	
	public static boolean DEL_FILE(String FileLoc){
		return ToolFunctions.Delete_File(FileLoc);
	} 
	
	public static String GET_NOW(){
		return DateOperations.GET_CURR_TIMESTAMP();
	}
	public static String GET_NOW_TIME(){
		return DateOperations.GET_CURR_TIME();
	}

	public static String GET_YEAR(int y){
		switch (y) {
		case 0:			return DateOperations.GET_YEAR_CURRENT();
		case 1:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case 2:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case 3:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case 4:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case 5:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case 6:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case 7:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case 8:			return DateOperations.GET_YEAR_PREVIOUS(-y);
		case -1:		return DateOperations.GET_YEAR_PREVIOUS(1);
		case -2:		return DateOperations.GET_YEAR_PREVIOUS(2);
		case -3:		return DateOperations.GET_YEAR_PREVIOUS(3);
		case -4:		return DateOperations.GET_YEAR_PREVIOUS(4);
		case -5:		return DateOperations.GET_YEAR_PREVIOUS(5);
		case -6:		return DateOperations.GET_YEAR_PREVIOUS(6);
		case -7:		return DateOperations.GET_YEAR_PREVIOUS(7);
		case -8:		return DateOperations.GET_YEAR_PREVIOUS(8);
		default: 			return DateOperations.GET_YEAR_CURRENT();
		}
	}
	
	
	public static String GET_CURR_YEAR(){
		return DateOperations.GET_YEAR_CURRENT();
	}
	public static String GET_NEXT_YEAR(){
		return DateOperations.get_NEXT_YEAR(1);
	}
	public static String GET_NEXT_YEAR(int i){
		return DateOperations.date_YEAR_ADD_TO_CURENT(i);
	}
	
	public static String GET_PREV_YEAR(){
		return DateOperations.GET_YEAR_PREVIOUS();
	}
	public static String GET_PREV_YEAR(int i){
		return DateOperations.GET_YEAR_PREVIOUS(i);
	}

	public static boolean  TestTimeTakenIsExceeded(String StartTime, String EndTime, int howManyHoursToExceed){
		return TestTimeTakenIsExceeded(StartTime, EndTime, howManyHoursToExceed, "HH:mm:ss");
	}
	public static boolean  TestDateTimeTakenIsExceeded(String StartTime, String EndTime, int howManyHoursToExceed){
		return TestTimeTakenIsExceeded(StartTime, EndTime, howManyHoursToExceed, "MM/dd/yyyy HH:mm:ss");
	}
	
	public static boolean  TestTimeTakenIsExceeded(String StartTime, String EndTime, int howManyHoursToExceed, String DateTimeFormat){
		if(ax.IS_NULL_OR_EMPTY_STRING(StartTime) || ax.IS_NULL_OR_EMPTY_STRING(EndTime)){
			ax.t_error("Cannot Calculate if TestTimeTakenIsExceeded - empty arguments..."); return false;
		} 
		long hourLimit = (3600 * howManyHoursToExceed )*1000 ;
		SimpleDateFormat format = new SimpleDateFormat(DateTimeFormat);
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = format.parse(StartTime);
			date2 = format.parse(EndTime);
		} catch (ParseException e) {
			ax.log("Problem with parsing timestamps");
			return false;
		}
		long difference = (date2.getTime() - date1.getTime());/// (1000); 
		return (difference >= hourLimit );
	}
	
	
	
	public static void LOG_TO_EXCEL(boolean Result, String Subject, String LOG){
		if(Result){ // POSITIVE RESULT
			if(LOG.equals("YES")){
				ax.pass(Subject);
			}else{
				ax.t_pass(Subject);
			}
		}else{
			if(LOG.equals("YES")){
				ax.fail(Subject);
			}else{
				ax.t_error(Subject);
			}
		}
	}
	
	public static boolean IS_CURR_USER_NA(WebDriver driver, String URID){
		return IS_CURR_USER_NA(driver, URID, true);
	}
	public static boolean IS_CURR_USER_NA(WebDriver driver, String URID, boolean sqlWay){
		if(URID==null){ax.t_error(driver, "No current User available");return false;}
		boolean transaction;
		boolean unitBlocks;
		if(ax.isSQLEnabled() && sqlWay){
			return ax.IS_CURR_USER_NA_SQL(URID);	
		}else{
			ax.OPEN_EUCR_HOME_PAGE(driver);
			ax.wait_for_ayax_2(driver);
			if(
					//ax.isDisplayed_And_Enabled(driver, EUCR_MenuNavigation.GET_MENU_ACCORD_LINK(EucrLabel.expectedTitleForACC, EucrLabel.expectedTitleForACC_Transactions))
					EUCR_MenuNavigation.MenuGo_ACC_Transactions(driver)
					){
				
				transaction = ax.WAIT4PageLoad(driver, "Search Transactions");
			
			}else{
				transaction = false;
			}
			
			ax.wait_for_ayax_2(driver);
			if(EUCR_MenuNavigation.MenuGo_ADM_Users(driver)){
				unitBlocks = ax.WAIT4PageLoad(driver, "User search");
			}else{
				unitBlocks = false;
			}
			return (transaction && unitBlocks);
		}
	}

	public static boolean IS_CURR_USER_CA(WebDriver driver, String URID){
		if(URID==null){ax.t_error(driver, "No current User available");return false;}
		boolean transaction;
		boolean unitBlocks;
		if(ax.isSQLEnabled()){
			return ax.IS_CURR_USER_NA_SQL(URID);	
		}else{
			ax.OPEN_EUCR_HOME_PAGE(driver);
			EUCR_MenuNavigation.OPEN_ACCORDEON_MENU(driver, EucrLabel.expectedTitleForACC);
			if(ax.isDisplayed_And_Enabled(driver, EUCR_MenuNavigation.GET_MENU_ACCORD_LINK(EucrLabel.expectedTitleForACC, EucrLabel.expectedTitleForACC_Transactions))){
				transaction = true;
			}else{
				transaction = false;
			}
			
			EUCR_MenuNavigation.OPEN_ACCORDEON_MENU(driver, EucrLabel.expectedTitleForADM);
			if(ax.isDisplayed_And_Enabled(driver, EUCR_MenuNavigation.GET_MENU_ACCORD_LINK(EucrLabel.expectedTitleForADM, EucrLabel.expectedTitleForADM_UnitBlocks))){
				unitBlocks = true;
			}else{
				unitBlocks = false;
			}
			return (!transaction && unitBlocks);
		}
	}	
	
	
	
	public static boolean IS_CURR_USER_NA_SQL(String URID){
			return AppSqlQueries.CHECKING_URID_IS_NA(URID);
	}
	public static boolean IS_CURR_USER_CA_SQL(String URID){
		return AppSqlQueries.CHECKING_URID_IS_CA(URID);
}
	
	
	public static By GENERAL_PAGE_TEXT_ELEMENT(String ParentXpathLocator, String TextDisplayedInLastElement){
		return By.xpath(ParentXpathLocator + "//*[contains(normalize-space(),'"+TextDisplayedInLastElement+"')]");
//		if(ax.isDisplayedBy(driver, By.xpath(StringLocator))){
//			return By.xpath(StringLocator);	
//		}else{
//			return null;
//		}
	}
	
	
	public static String GET_NCS(String s){ // remove commas from string 
		return StringPageUtils.NO_COMMA_STRING(s);
	}
	public static boolean FileExist(String FileLocation){
		File f = new File(FileLocation);
		if(f.exists() && !f.isDirectory()) { 
			return true;
		} return false;
	}
	
	public static void SET_REGISTRY(String REG){ ProjectDataVariables.set_EUCR_REG(REG); }
	
	public static boolean TEST_CONTAINS_FALSE_IN_ARRAYLIST(String MESSAGE_TO_TESTRESULTS, ArrayList<Boolean> RESULTX){
		if(ax.IS_NULL_OR_EMPTY_ARRAY_LIST_BOOL(RESULTX)){
			ax.t_error("BOOLEAN ARRAY LIST HAS NULL");
			return false;
		}
		ax.log("TEST-TO-CONTAINS-FALSE:....");
		boolean t =TESTERCLASS.TEST_CONTAINING_FALSE_IN_OF_BOOLEAN_RESULTS_ARRAYLIST_NO_EXCEL(MESSAGE_TO_TESTRESULTS, RESULTX);
		ax.log("....["+RESULTX.toString()+"].... =>> "+t);return t;}
	
	public static boolean IS_ACCOUNT_IDENTIFIER(String AccountIdentifier){
		return IS_NUMERIC_IDENTIFIER(AccountIdentifier);
//		if(ax.IS_NULL_OR_EMPTY_STRING(AccountIdentifier)){ax.t_error("Value ["+AccountIdentifier+"] is not Account Identifier..");return false;}
//		else if(!ax.isNumber(AccountIdentifier)){ax.t_error("Value ["+AccountIdentifier+"] is not Numeric value..");return false;}
//		else{return true;}
	}
	
	public static boolean IS_REQUEST_NR(String REQUEST){
		if(REQUEST==null) return false;
		return IS_NUMERIC_IDENTIFIER(REQUEST);
		}
	
	public static boolean IS_NUMERIC_IDENTIFIER(String NumericIdentifier){
		if(ax.IS_NULL_OR_EMPTY_STRING(NumericIdentifier)){
			ax.t_error("Value ["+NumericIdentifier+"] is not NULL or EMPTY Identifier..");
			return false;
		}
		else if(!ax.isNumber(NumericIdentifier)){
			ax.t_error("Value ["+NumericIdentifier+"] is not Numeric value..");
			return false;
		}
		else{return true;}
	}
	
	
	public static boolean IS_ENROLLMENT_KEY(String EnrollmentKey){
		if(ax.IS_NULL_OR_EMPTY_STRING(EnrollmentKey)) return false;
		if(EnrollmentKey.indexOf("-")<1) return false;
		if(EnrollmentKey.split("\\-").length!=5) return false;
		return true;
	}
	
	public static boolean IS_URID(String URID){ // 14 digits in URID
		return IS_CORRECT_URID(URID);
	}
	public static boolean IS_FULL_ACCOUNT_SHAPE(String AccountNumber){
		return (AccountNumber.contains("-"))?true:false;
	}
	public static boolean IS_CORRECT_URID(String URID){ // 14 digits in URID
		String FirstTwo = URID.substring(0, 2);
		String RestURID = URID.substring(2, 14);
		
		if((!NumberUtils.isDigits(FirstTwo)) && (NumberUtils.isDigits(RestURID))  ){
			return true;
		}
		return false;
		
	}
	
	
	public static void hideDatePicker(WebDriver driver, By dpickerByLocator) { 
		WebElement delement = driver.findElement(dpickerByLocator);
		String d = driver.findElement(dpickerByLocator).getAttribute("style");
		if(d.contains("display: block")  || d.contains("display:block")){
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", delement, "display:none;");
		}
	} 	

    public static boolean isAlertPresent(WebDriver driver){
    	if(SeleniumDriver.getCurrentBrowserType().equals("HTMLUNIT")){return true;}
    	try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
    }

    public static String closeAlertAndGetItsText(WebDriver driver) {
    	if(!SeleniumDriver.getCurrentBrowserType().equals("CHROME")){
    		return null;
    		}
		String alertText = "";
		if(ax.isAlertPresent(driver)){
			Alert alert = driver.switchTo().alert();
			alertText = alert.getText();
			ax.log("Alert was presented with: "+alertText);
			try{
				alert.accept();
			}catch(UnhandledAlertException u){
				Alert alert2 = driver.switchTo().alert();
				alert2.accept();
			}
		}
		return alertText;
	}	
	
    public static boolean RED_BOX_CHECKER(WebDriver driver){
    	By REDBOX_PANEL = By.xpath(ApplicationPageUtils.GET_PANEL_BOX("Unrecoverable Error", "content"));
    	if(ax.isDisplayedBy(driver, REDBOX_PANEL)){ax.t_error(driver, "Found Unrecoverable Error...." + ax.getText(driver, REDBOX_PANEL)); return true;} return false;
    }
  
    
    public static boolean isAt(WebDriver driver, By UpperPageElementBy){
    	if(!ax.wait_elm(driver, UpperPageElementBy)){return false;}
    	if(!ax.isDisplayedBy(driver, UpperPageElementBy)){
    		ax.t_error(driver, "Action is not on expected page: "+UpperPageElementBy.toString());
    		return false;
    	}return true;
    }
    public static boolean isAt(WebDriver driver, String EucrHeader_Page){ // this function is for pages with element
    	By UpperPageElementBy = By.xpath(ApplicationPageUtils.GET_PANEL_BOX(EucrHeader_Page, "content"));
//    	if(!ax.wait_elm(driver, UpperPageElementBy)){return false;}
    	if(!ax.isDisplayedBy(driver, UpperPageElementBy)){
//    		ax.info(driver, "Action is not on expected page: "+EucrHeader_Page.toString());
    		return false;
    	}return true;
    }
    
    
    public static boolean IS_FIREFOX(){
    	if(SeleniumDriver.getCurrentBrowserType().contains("FIREFOX")){return true;}return false; }
    public static boolean IS_MSIE(){
    	if(SeleniumDriver.getCurrentBrowserType().equals("MSIE")){return true;}return false; }
    public static boolean IS_CHROME(){
    	if(SeleniumDriver.getCurrentBrowserType().equals("CHROME")){return true;}return false; }
    
    
  public static boolean WAITER4ELM(WebDriver driver, By by){
	  try{
		  int i=0;
		  while(!ax.WAIT4ElementDisplayed(driver, by).isDisplayed()){
			  ax.sleep(1); i++;
			  if(i> ProjectDataVariables.TEST_RETRIAL_FACTOR_10){ax.t_error(driver, "[WAITER4ELM] Waiting too long for element "+by.toString());return false;}
		  }
		  return true;
	  }catch(NullPointerException e){
		 ax.t_error(driver, "Element not displayed after waiting for " + by.toString() );
		  return false; 
	  }
  }  
   
  
  public static void debug(String message){
	  if(ax.READ_CONFIG_FILE("TEST_MODE").equals("DEBUG")){
		  ax.log(message);
	  }
  }
  public static boolean isDriverMulti(){
	 if(Setuper.driver!=null && Setuper.driver2!=null && Setuper.driver3!=null && Setuper.driver4!=null  ){
		 return true;
	 } else if(Setuper.driver!=null && Setuper.driver2==null  && Setuper.driver3==null && Setuper.driver4==null){
		return false; 
	 }
	 return false;
  }
  public static boolean IS_MULTIBROWSER(){
  	return (ax.getProp("MULTIBROWSER").equals("YES"))?true:false;}  
  
  public static boolean CHECK_IF_ARRAYLISTBOOLEAN_CONTAINS_FALSE(ArrayList<Boolean> B){ // if has false-> return false, else return true;
	  return (B.contains(false))?false:true;
  }
  
  public static String[] Separate_ColonStringToStringArray(String string){
	  return string.split(":");
}
  public static String Separate_ColonStringSearchToString(String string, int sw){
	  String[] s = string.split(":");
	  return s[sw-1];
}

  public static boolean CheckIfStringArrayContainsValue(String[] MyArray, String ValueToSearch){
	  return Arrays.asList(MyArray).contains(ValueToSearch);
  }
  public static String GET_EUCR_TOKEN(WebDriver driver){
	  String pageSource = driver.getPageSource();
	  if(pageSource.contains("EUCR_TOKEN")){
		   String s = "<input type=\"hidden\" name=\"EUCR_TOKEN\" value=\"1FB3-XJ1G-SOW9-Q7T3-D3KP-4ZJ2-VH36-GH7Q\" />";
		   String regex= "name=\"EUCR_TOKEN\" value=\"[^\\,]*\"";
		   Pattern p = Pattern.compile(regex);
		   Matcher m = p.matcher(s);
		   String ret;
		   if(m.find()) {
			   ret = m.group().replace("name=\"EUCR_TOKEN\" value=", "").replace("\"", "");
		       return ret;
		   }else{return null;}

	  }else{return null;}
  }
 
  public static String GET_USER_(String what/*[NAME,TEL,MAIL,SHORT]*/, String SHORT_USER){
	  String REG = ProjectDataVariables.get_EUCR_REG();
	  return GET_USER_(what, SHORT_USER, REG);
  }
  public static String GET_USER_(String what/*[NAME,TEL,MAIL,SHORT]*/, String SHORT_USER, String REG){
  	String DATA=ax.getProp("USR_DATA_"+SHORT_USER+"_"+ax.getProp("TEST_ENVRMENT"));
  	if(ax.IS_NULL_OR_EMPTY_STRING(DATA)){
  			ax.log("NO USR DATA GRABED FOR "+SHORT_USER);
  			return null;
  		}
  	String[] DATAS = DATA.split("\\|");
  	if(DATAS.length>5){ax.log("WRONG USR DATA SIZE FOR "+SHORT_USER);return null;}
  	switch (what) {
		case "NAME": 	return DATAS[0].trim(); 
		case "TEL": 	return DATAS[1].trim(); 
		case "MAIL": 	return DATAS[2].trim(); 
		case "SHORT": 	return DATAS[3].trim();
		case "URID": return getUridFromEucrProperties(DATAS[4].trim(), REG); 	
			
		// by default return username:
		default: return DATAS[0].trim();
		}
  }
  
  
  public static String getUridFromEucrProperties(String partOfUridInfo){
	  String reg = ProjectDataVariables.get_EUCR_REG();
	  return getUridFromEucrProperties(partOfUridInfo, reg);
  }
  public static String getUridFromEucrProperties(String partOfUridInfo, String reg){
	  if(ax.IS_NULL_OR_EMPTY_STRING(partOfUridInfo)) return null;
	  
	  if(partOfUridInfo.contains(":")){
		  String[] uridsArray = partOfUridInfo.split(":");
		for (String urid : uridsArray) {
			if(urid.substring(0,2).contains(reg)){
				return urid;
			}
		}  
		return (String) ax.returnWhenNull("not found urid for current registry " +reg);
	  }else{
		  return partOfUridInfo; // we assube only one registry data like BGxxxx
	  }
  }
  
  
  
  public static String GET_USER_(String SHORT){ 	  					return GET_USER_("", SHORT);    	}
  public static String GET_USER_NAME(String SHORT){ 	  				return GET_USER_("NAME", SHORT);    }  
  public static String GET_USER_TEL(String SHORT){ 	  					return GET_USER_("TEL", SHORT);    	}  
  public static String GET_USER_MAIL(String SHORT){ 	  				return GET_USER_("MAIL", SHORT);    }
  public static String GET_USER_URID(String SHORT){ 	  				return GET_USER_("URID", SHORT);    }
  
  public static String GET_URID_BY_FX_MS(String SHORT, String MS){ 	  	
		  if(ax.isSQLEnabled()){
			  return AppSqlQueries.getURID_ByLoginName(ax.GET_USER_NAME(SHORT), MS);  
		  }else{
			  return GET_USER_("URID", SHORT, MS);
		  }
	  }  
  public static String GET_URID_BY_NAME_MS(String NAME, String MS){ 	
		  if(ax.isSQLEnabled()){
			  return AppSqlQueries.getURID_ByLoginName(NAME, MS);  
		  }else{
			  String SHORT = GET_USER_FX_BY_USERNAME(NAME);
			  return GET_USER_("URID", SHORT, MS);
		  }
	  }  
  
  public static boolean f_perf_append(String key, String value){
	  return FileLogAppenderToIdentifier.F_APPEND(key, value);
  }

  
  public static String GET_USER_EMAIL_BY_USERNAME(String UserName){
	  String email = null;
	  try {
		  email = GET_USER_DATA_BY_VALUE_SEARCHED(UserName, 0, 2);
	} catch (Exception e) {
		email = null;
	}
	  if(email==null){
		  //ax.log("No email for this user defined.. returning username: " + UserName);
		  return UserName;
	  }
	  return email;
  }
  public static String GET_USER_FX_BY_USERNAME(String UserName){
	  return GET_USER_DATA_BY_VALUE_SEARCHED(UserName, 0, 3);
  }
  
  public static String GET_USER_NAME_BY_URID(String URID){
	  return GET_USER_DATA_BY_VALUE_SEARCHED(URID, 4, 0);
  }
  
  public static String GET_USER_NAME_BY_USER_FX(String UserFX){
	  return GET_USER_DATA_BY_VALUE_SEARCHED(UserFX, 3, 0);
  }
  public static String GET_USER_EMAIL_BY_USER_FX(String UserFX){
	  return GET_USER_DATA_BY_VALUE_SEARCHED(UserFX, 3, 2);
  }
  
  
  public static void setDriver(int driverInstance){
	  SeleniumDriver.setCurrentDriverInstance(driverInstance);
  }
  
  public static String GET_USER_DATA_BY_VALUE_SEARCHED(String valueSearched, int FieldToSearch, int FieldToReturn){
	  ArrayList<ArrayList<String>> box = GET_USER_MATRIX_BOX();
	  for (ArrayList<String> userLine : box) {
		if(userLine.get(FieldToSearch).trim().equals(valueSearched.trim())){
			return userLine.get(FieldToReturn).trim(); 
		}
	}
	  return null;
  }
  
  
  public static ArrayList<ArrayList<String>> GET_USER_MATRIX_BOX(){
    ArrayList<ArrayList<String>> UMBOX = new ArrayList<ArrayList<String>>();
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_NA1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_NA2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_CA1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_CA2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_CA3_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_AR1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_AR2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_AAR1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_AAR2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_USR_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_VER1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
    
    UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDCA1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDCA2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDAR1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDAR2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDAAR1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDAAR2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDSD1_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	UMBOX.add(ax.GET_ARRAYLIST_BY_SEPARATOR(ax.getProp("USR_DATA_ESDSD2_"+ProjectDataVariables.get_EUCR_ENV()), "|"));
	  return UMBOX;
  }
  public static boolean TEST_PASS_FAIL_EXCEL(String TestSubject, boolean truefalse){
		if (truefalse){
			ax.pass(TestSubject + "_"+AppGetter.GET_TIME_STAMP_FULL_NOW());
			return true;
		}else{
			ax.fail(TestSubject + "_"+AppGetter.GET_TIME_STAMP_FULL_NOW());
			return false;
		}
  }
	public static boolean xAssert(WebDriver driver, boolean result, String TITLE){
		try {
			Assert.assertTrue(result, TITLE);
		} catch (AssertionError a) {
			ax.info(driver, "ASSERTION: PROBLEM WITH ["+TITLE+"]");
			return false;
		}		
		ax.info(driver, "ASSERTION: PASSED TEST FOR ["+TITLE+"]");return true;
	}
	
  
	public static void dataProviderTester(String TITLE, boolean test_result, String SHOULDBE){
		if( ax.IS_NULL_OR_EMPTY_STRING(SHOULDBE) ){
				ax.t_error("No defined which result should be expected...");
				return;
			}
		if(SHOULDBE.equals("PASS")){
			ax.excel_report(TITLE, test_result);	
		}else if(SHOULDBE.contains("FAIL")){
			ax.excel_report(TITLE, !test_result);
		}
	}
	public static void turnOff_time(WebDriver driver){
		SeleniumWebUtils.turnOffImplicitWaits(driver);
	}	
	public static void turnOn_time(WebDriver driver){
		SeleniumWebUtils.turnOnImplicitWaits(driver);
	}	
	public static void SaveErrorCodeNumbers(String TextWithErrorCodes){
		String g = ax.GetErrorCodeNumbers(TextWithErrorCodes).toString();
//		ax.SAVE_2_FILE(ax.FAIL_ERROR_EXPECTED, g);
		ax.SAVE_REPLACE_DATAFILE(ax.FAIL_ERROR_EXPECTED, g);
	}
	
//	public static boolean SaveErrorCodeNumbers(String VALUE){// Duplicate of ax.SaveErrorCodeNumbers(Code);
//		  return SAVE_REPLACE_DATAFILE(FAIL_ERROR_EXPECTED, VALUE);
//	}	
	
	public static void SaveParamsInDataFile(String ParamName, String ParamValue){
//		String g = ax.GetErrorCodeNumbers(TextWithErrorCodes).toString();
//		ax.SAVE_2_FILE(ax.FAIL_ERROR_EXPECTED, g);
		ParamName = ParamName.replaceAll("\\s+","");
		ax.SAVE_REPLACE_DATAFILE(ParamName, ParamValue);
	}
	
	public static ArrayList<String> GetErrorCodeNumbers(String TextWithErrorCodes){
		ArrayList<String> out = new ArrayList<String>();
//		String TextWithErrorCodes = "7029: The acquiring account is not allowed to hold the units being transferred";
		String[] g = TextWithErrorCodes.split(" ");
		for (String st : g) {
			if(st.substring(st.length() - 1).equals(":")){
				out.add(st.replace(":", ""));
			}else if(StringUtils.isNumeric(st.trim())){
				out.add(st);
			}
		}
		return out;
	}
	
	public static WebDriver RestartBrowsers(WebDriver driver){
		ax.spacer("RESTARTING BROWSERS " + DateOperations.DATETIME_GET_NOW_IN_DATETIME_DASHED_422_222());
		ax.wait(3);
		return Setuper.RestartReloginBrowsers(driver);
	} 
	public static ArrayList<String> GET_EMISSION_EXCLUDING_FROM_STRING(String EmissionExcludings){
		ArrayList<String> SETUP_EMISSION_EXCLUDING = new ArrayList<String>(); 
		if(EmissionExcludings.contains("&"))
		{
			SETUP_EMISSION_EXCLUDING.addAll(Arrays.asList(EmissionExcludings.split("&")));
		}else{
			SETUP_EMISSION_EXCLUDING.add(EmissionExcludings.trim());
		}
		return SETUP_EMISSION_EXCLUDING;
	}
	  public static boolean beginsWith(String text, String begginingText){
		    if(text==null || text.isEmpty()){return false;}
		    if(begginingText==null || begginingText.isEmpty()){return false;}
		    return text.substring(0, begginingText.length()).equals(begginingText);
		  }
	  public static ScheduledExecutorService threadTimeWaiterStart(){
		  // Call like: ScheduledExecutorService exec = ax.threadTimeWaiterStart();
		  // Than in closing use exec like: ax.threadTimeWaiterStop(exec);
		  return threadTimeWaiterStart("");
	  }
	  
	  public static ScheduledExecutorService threadTimeWaiterStart(String infoMessage){
			System.out.print("["+ ax.GET_NOW_TIME()+"] "+infoMessage + " --> ");
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(new Runnable() {
			  @Override
			  public void run() {
			    System.out.print(". ");
			  }
			}, 1000, 1000, TimeUnit.MILLISECONDS);
			return exec;	
	  }
	  public static void threadTimeWaiterStop(ScheduledExecutorService exec){
		  exec.shutdown();
//	      System.out.print(". . . DONE ["+ ax.GET_NOW_TIME()+"] ");
	      ax.log("");
	  }
	 public static boolean IS_NEW_ACCOUNT_LINK(){
		 if(ax.getProp("VIEW_DETAILS_ACCOUNT_WAY").trim().equals("NEW")){
			 return true;
		 }
		 return false;
	 } 
	 public static String csvGetFirstItemFromCSV(String CSVPair){
		 if(CSVPair.contains(",")){
			 String[] p = CSVPair.split(",");
			 return p[0];
		 } return null;
	 }
	 public static String csvGetSecondItemFromCSV(String CSVPair){
		 if(CSVPair.contains(",")){
			 String[] p = CSVPair.split(",");
			 return p[1];
		 } return null;
	 }
	 public static String csvGetThirdItemFromCSV(String CSVPair){
		 if(CSVPair.contains(",")){
			 String[] p = CSVPair.split(",");
			 if(p.length >2){
				 return p[2];	 
			 }return null;
		 } return null;
	 }
	 
	public static boolean isSQLEnabled(){
		String sqlPossibility = ax.getProp("SQL_DATA_RETIREVE");
		if(sqlPossibility.contains("NO")){
			return false;
		} return true;
	} 
	public static boolean isNewEnterAccountDetailsWay(){
		String param = ax.getProp("VIEW_DETAILS_ACCOUNT_WAY");
		if(param.equals("OLD")){
			return false;
		} return true;
	} 
	public static void MaximizeWindow(WebDriver driver){
		driver.manage().window().maximize();
	}
	
	public static int getNumberOfRowsFromPaginatorLabel(String paginatorTextResult){
		/*
		 * @return: int value (here:5) from "Page 1 of 1 (5 rows found)"
		 * @return -1 if nothing found
		 */
			    String txt = paginatorTextResult; //;
			    if(txt==null) {
			    	log("Value provided as argument is null"); 
			    	return -1;
			    	}
			    String[] t = null;// = txt.split(" ");
			    // Both works fine
			    // \\{(.*?)\\}
			    // \\(([^)]+)\\)
			    String foundText = null;
			    Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(txt);
			    while(m.find()) {
			      foundText = m.group(1);    
			    }
			    if(foundText!=null){
			      return Integer.valueOf(foundText.split(" ")[0]);
			    }else{
			    	log("Not found result text in paginator");   return -1;  
			    }
	}
	
	
	
	public static String GetInputTypeFromBy(WebDriver driver, By by){
		if(driver.findElements(by).size() < 1)return null;
		ax.wait_elm(driver, by);
		String xpath = ax.GetElementXPath(driver, driver.findElement(by));
		if(xpath.contains("input")){
			return "input";
		}else if(xpath.contains("select")){
			return "select";
		}else if(xpath.contains("textarea")){
			return "textarea";
		}
		return null;
	}
	public static String GetElementXPath(WebDriver driver, WebElement element)
	{
	    return (String) ((JavascriptExecutor) driver).executeScript(
	    "getXPath=function(node)" +
	    "{" +
	        "if (node.id !== '')" +
	        "{" +
	            "return '//' + node.tagName.toLowerCase() + '[@id=\"' + node.id + '\"]'" +
	        "}" +

	        "if (node === document.body)" +
	        "{" +
	            "return node.tagName.toLowerCase()" +
	        "}" +

	        "var nodeCount = 0;" +
	        "var childNodes = node.parentNode.childNodes;" +

	        "for (var i=0; i<childNodes.length; i++)" +
	        "{" +
	            "var currentNode = childNodes[i];" +

	            "if (currentNode === node)" +
	            "{" +
	                "return getXPath(node.parentNode) +  '/' + node.tagName.toLowerCase() + '[' + (nodeCount+1) + ']'" +  "}" +

	            "if (currentNode.nodeType === 1 && " +
	                "currentNode.tagName.toLowerCase() === node.tagName.toLowerCase())" +
	            "{" +
	                "nodeCount++" +
	            "}" +
	        "}" +
	    "};" +

	    "return getXPath(arguments[0]);", element);
	}	
	  public static String getInputNameFromXpath(String xp){
	    int d = 0;
	    String s = "";
	    if(xp.contains("/input")){
	        d = xp.indexOf("input");
	        s = xp.substring(d);
	      }else if(xp.contains("/select")){
	        d = xp.indexOf("select");
	        s = xp.substring(d);
	      }else if(xp.contains("/textarea")){
	        d = xp.indexOf("textarea");
	        s = xp.substring(d);
	    }
	  return s;
	  }
	  public static String GET_PANEL_TITLE_XPATH(String TitleOfThePanel){
		  return ApplicationPageUtils.GET_PANEL_BOX(TitleOfThePanel, "content");
	  }
	  public static boolean START_AS_LOGGED_USER_READY(WebDriver driver){
		  // we assume that user if is not logged-in has to be logged as NA1
		  if(!ax.IS_LOGGED(driver)){
			  driver = ax.LG_NA1(driver);
		  }
		  return OPEN_EUCR_HOME_PAGE(driver);
	  }
	   public static boolean OPEN_EUCR_HOME_PAGE(WebDriver driver){
		   ax.driverGet(driver, ProjectDataVariables.get_EUCR_URL_HOMEPAGE());
//		   Add handler for case when session expired - blue screeen
		   if(ax.isDisplayedBy(driver, PF_PageElements.SESSION_EXPIRED) && ax.isDisplayed_And_Enabled(driver, PF_PageElements.SESSION_EXPIRED_HOME_PAGE_LINK)){
			  ax.click(driver, PF_PageElements.SESSION_EXPIRED_HOME_PAGE_LINK);
			  ax.driverGet(driver, ProjectDataVariables.get_EUCR_URL_HOMEPAGE());
		   }
		   return ax.wait_elm(driver, By.xpath("//div[@id='contentColumnPadding']"));
	   }	  
	   public static boolean OPEN_EUCR_HOME_PAGE(WebDriver driver, String MS){
		   ax.driverGet(driver, ProjectDataVariables.get_EUCR_URL_HOMEPAGE(MS));
		   return ax.wait_elm(driver, By.xpath("//div[@id='contentColumnPadding']"));
	   }	
	   public static WebDriver OPEN_EUCR_HOME_PAGE_RET_DRIVER(WebDriver driver, String MS){
//		   ax.getDriver(driver, ProjectDataVariables.get_EUCR_URL_HOMEPAGE(MS));
		   driver.navigate().to(ProjectDataVariables.get_EUCR_URL_HOMEPAGE(MS));
		   if(!ax.wait_elm(driver, By.xpath("//div[@id='contentColumnPadding']"))){
			   return null;
		   } return driver;
	   }	
	  public static String version_eucr(){
		  return ProjectDataVariables.EUCR_VERSION_NR;
	  } 
	  
	  
	  public static String getEucrTokenFromSource(String source){
		    String token = "?EUCR_TOKEN=";
		    int posOfEucrToken = source.indexOf(token);
		    int posOfEndEucrToken = source.indexOf("\"", posOfEucrToken);
		    return source.substring(posOfEucrToken+token.length(), posOfEndEucrToken).trim();
		  }	
	  
   public static void sout(String info){
	   System.out.println(info);
   }
	  
}
