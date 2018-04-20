package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.base.Function;

import utils.ProjectDataVariables;
import utils.Setuper;

@Test
public class SeleniumWebUtils {
      private static final int MAX_ATTEMPTS = 10;
	  private static long AJAX_TIMEOUT = 35;	
      private static long DEFAULT_POLL_SLEEP = 1000;      
	  private static WebDriver driver;
	  private static String SCREENSHOT_FILE_DIRECTORY = "C:\\kosz\\screen";
	  public static String hideLogger = ProjectDataVariables.HIDE_LOGGER_LEVEL;	  
//	  static Logger log = Logger.getLogger(Launcher.class.getName());
	  
	  
	
	  public static boolean isStaleElementBy(WebDriver driver, final WebElement element) {
		  try {
		        element.isEnabled();
		        return false;
		    } catch (StaleElementReferenceException sere) {
		        return true;
		    }
		  
		  
//		    return new ExpectedCondition<Boolean>() {
//		      @Override
//		      public Boolean apply(WebDriver ignored) {
//		        try {
//		          // Calling any method forces a staleness check
//		          driver.findElement(by).isEnabled();
//		          return false;
//		        } catch (StaleElementReferenceException expected) {
//		          return true;
//		        }
//		      }
//
//		      @Override
//		      public String toString() {
//		        return String.format("element (%s) to become stale", driver.findElement(by));
//		      }
//		    };
		}	  
	  
	  
	  


//		public static WebDriver initWebDriver(){
//			ProfilesIni profile = new ProfilesIni();
//			FirefoxProfile myprofile = profile.getProfile("CE");
//			WebDriver driver = new FirefoxDriver(myprofile);
//			return driver;
//		}
	 
	 public static void quitWebDriver(WebDriver driver){
		 driver.quit();
		 Reporter.log("Quit webdirver <a hre=\"dupa.html\">exit webdriver</a>");
		 
	 }	
		
	  public static int elementPresent(WebDriver driver, By by){
		  List<WebElement> elements ;
		  elements = driver.findElements(by);
		  if(elements.size()!=0){
			  return 1;
		  }else{
			  SeleniumWebUtils.reportInfoWithPhoto(driver, "Element "+by+" was not found!");
			  return 0;
		  }
	  }

	public static WebElement waitForVisibilityOfElementLocated(WebDriver driver, By by){
//		if(!ax.waitWhileStaleElement(driver, by)){return null;}
		try{
			WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
			wait.until( ExpectedConditions.visibilityOfElementLocated(by));
			return driver.findElement(by);
  		} catch (Exception e) {
		      SeleniumWebUtils.reportFAIL(driver, "Function function [waitForVisibilityOfElementLocated] CANNOT FIND ELEMENT by: ["+by+"]   ");
		      return null;
		}
			
	}	
	
	public static WebElement waitForVisibilityOfElementLocated(WebDriver driver, By by, int timeOutInSeconds){
		ax.waitWhileStaleElement(driver, by);
		try{
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until( ExpectedConditions.visibilityOfElementLocated(by));
		return driver.findElement(by);
		} catch( Exception e){
				SeleniumWebUtils.reportFAIL(driver, "Function function [waitForVisibilityOfElementLocated] CANNOT FIND ELEMENT by: ["+by+"]");
				return null;
			}	
		}

		public static boolean waitForVisibilityOfElementLocatedBool(WebDriver driver, By by, int timeOutInSeconds){
			ax.waitWhileStaleElement(driver, by);
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until( ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		}catch(Exception e){
//		      SeleniumWebUtils.reportFAIL(driver, "CANNOT FIND ELEMENT ["+by+"] by function [waitForVisibilityOfElementLocated] ");
			return false;
		}
		}
	
	public static WebElement waitForVisibilityOfElementLocatedWithHeaderText(WebDriver driver, By by, String HeaderText){
		ax.waitWhileStaleElement(driver, by);
		try {
			WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
			wait.until( ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
		      SeleniumWebUtils.reportFAIL(driver, "CANNOT FIND ELEMENT ["+by+"] by function [waitForVisibilityOfElementLocatedWithHeaderText]. Header Text =  "+HeaderText);
				return null;
		}
		
		return driver.findElement(by);
}	
	
	  
	  public static WebElement FindElement(WebDriver driver, By by, int timeoutInSeconds)
	  {
	      WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
	      wait.until( ExpectedConditions.presenceOfElementLocated(by) ); //throws a timeout exception if element not present after waiting <timeoutInSeconds> seconds
	      return driver.findElement(by);
	  }

	  public static void FindAndClickElement(WebDriver driver, By by)
	  { 
	    SeleniumWebUtils.FindAndClickElement(driver, by, ProjectDataVariables.TIME_FOR_CLICK_FUNCTIONS, hideLogger);  
	  }
	  public static void FindAndClickElement(WebDriver driver, By by, int TimeOut)
	  { 
	    SeleniumWebUtils.FindAndClickElement(driver, by, TimeOut, hideLogger);  
	  }
	  
	  public static void FindAndClickElement(WebDriver driver, By by, int timeoutInSeconds, String debugFactor)
	  {
		  ax.waitWhileStaleElement(driver, by);
		  int s = driver.findElements(by).size();
		  CHECK_AND_POOL_ACTION(driver, by, s, debugFactor);
	  }
	  
	  public static void CHECK_AND_POOL_ACTION(WebDriver driver, By by, int WebElementsTableSize, String debugFactor){
		  if(WebElementsTableSize==1){
				  driver.findElement(by).click();
			      if(!debugFactor.equals("HIDE")){
			    	  ax.debug("||====>Clicked Element: "+by);  
			      }
			      
		  }else if(WebElementsTableSize>1){
			  Report("|more than 1 element|====>Clicking first element: "+by);
			  List<WebElement> e = driver.findElements(by);
			  e.get(0).click();
		  }else{
			  int a=0;
			  int t = driver.findElements(by).size();
			  while(t==0){
				  ax.wait(1);
				  ax.debug("Waiting for element ["+by.toString()+"] to click");
				  a++;
				  if(a>MAX_ATTEMPTS) break;
			  }
		      SeleniumWebUtils.findElementIgnoreExceptions(driver, by);
			  Report("|There are no element "+by+" displayed|====>Clicking nothing");
			  return;
		  }		  
		  
	  }
	  
	  public static void explicitWait(WebDriver driver, final String locator, final int type) {	 
		  WebElement myDynamicElement = (new WebDriverWait(driver, 5))  
				  .until(new ExpectedCondition<WebElement>(){	 
					  public WebElement apply(WebDriver d) { 
						  switch (type) {	 
					  case 1 : return d.findElement(By.cssSelector(locator)); 
					  case 2 : return d.findElement(By.xpath(locator));	 
					  case 3 : return d.findElement(By.id(locator)); 
					  case 4 : return d.findElement(By.name(locator));	 
					  case 5 : return d.findElement(By.linkText(locator)); 
					  default:	 return d.findElement(By.id(locator));	 
					  } 
						  }
					  });	
		  }	  

	  
	  public static boolean fluent_wait(WebDriver driver, final By by, int with, int pool){
		  WebElement element = null;
		  FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
			        .withTimeout(with, TimeUnit.SECONDS)
			        .pollingEvery(pool, TimeUnit.SECONDS)
			        .ignoring(TimeoutException.class, NoSuchElementException.class);
				  try {
					  element = (new WebDriverWait(driver, with)).until(ExpectedConditions.elementToBeClickable(by));
				  }catch (TimeoutException t){
					  System.out.println("Waiting longer than limit " + with + " seconds..");
					  element = null;
				} catch (Exception e) {
					element = null;
				}
		  	return element!=null;
	         }	  
	  public static boolean fluent_wait(WebDriver driver, final WebElement elm, int with, int pool){
		  WebElement element = null;
		  FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
			        .withTimeout(with, TimeUnit.SECONDS)
			        .pollingEvery(pool, TimeUnit.SECONDS)
			        .ignoring(TimeoutException.class, NoSuchElementException.class);
				  try {
					  element = (new WebDriverWait(driver, with)).until(ExpectedConditions.elementToBeClickable(elm));
				  }catch (TimeoutException t){
					  System.out.println("Waiting longer than limit " + with + " seconds..");
					  element = null;
				} catch (Exception e) {
					element = null;
				}
		  	return element!=null;
	         }	  
	  
	  public static String getTextEvenEmpty(WebDriver driver, By by){
		  List<WebElement> w = driver.findElements(by);
		  if(w.get(0)==null){
			  return null;
		  }else if(w.get(0)!=null && w.get(0).getText().isEmpty()){
			  return "";
		  }else{
			  return w.get(0).getText();
		  }
	  }
	  
	  public static boolean fluent_wait_text_gone(WebDriver driver, String xp, int with, int pool){
		  String text = SeleniumWebUtils.getTextEvenEmpty(driver, By.xpath(xp));
		  int i=0;
		  while(i < with){
			  if(text.trim().length() <1){
				  System.out.println("Text gone");
				  return true;
			  }
			  ax.wait(pool);
			  text = SeleniumWebUtils.getTextEvenEmpty(driver, By.xpath(xp));
			  System.out.println("again "+i);
			  i++;
			  
			  if(i > (with + 2)) return false;
		  }
		  
		  return text.trim().isEmpty();
	         }	  
	  
	  public static void FindClearAndTypeValueInEditBox(WebDriver driver, By by, String valueToType){
		  SeleniumWebUtils.FindClearAndTypeValueInEditBox(driver, by, valueToType, ProjectDataVariables.TIME_FOR_CLICK_FUNCTIONS, hideLogger);
	  }
	  public static void FindClearAndTypeValueInEditBox(WebDriver driver, By by, String valueToType, int TimeForVaiting){
		  SeleniumWebUtils.FindClearAndTypeValueInEditBox(driver, by, valueToType, TimeForVaiting, hideLogger);
	  }

	  public static void FindClearAndTypeValueInEditBox(WebDriver driver, By by, String valueToType, int timeoutInSeconds, String debugFactor){
//	      WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
//	      wait.until( ExpectedConditions.presenceOfElementLocated(by) );
//		    driver.findElement(by).clear();
//		    driver.findElement(by).sendKeys(valueToType);
		    
		  int WebElementsTableSize = driver.findElements(by).size();
		  if(WebElementsTableSize==1){
			    driver.findElement(by).clear();
			    driver.findElement(by).sendKeys(valueToType);		      
			    if(!debugFactor.equals("HIDE")){
				    SeleniumWebUtils.reportInfo("||====>Typed in Element: ["+by+"]. Value: ["+ valueToType+"]", hideLogger);
				    Report("||====>Typed in Element: ["+by+"]. Value: ["+ valueToType+"]");
			    }
			    
			    
		  }
		  else if(WebElementsTableSize > 1){
			  Report("|more than 1 element|====>Clicking first element: "+by);
			  List<WebElement> e = driver.findElements(by);
			  e.get(0).clear();
			  e.get(0).sendKeys(valueToType);
			    if(!debugFactor.equals("HIDE")){
			    	Report("||====>Typed in Element: ["+by+"][0]. Value: ["+ valueToType+"]");
			    }
		  }
		  else{
			  int a=0;
			  int t = driver.findElements(by).size();
			  while(t==0){
				  try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			  System.out.println("Waiting for element ["+by.toString()+"] to type");
			  a++;
			  if(a>MAX_ATTEMPTS) break;
		  }
	      SeleniumWebUtils.findElementIgnoreExceptions(driver, by);
		  Report("|There are no element "+by+" displayed|====>Nothing typed.");
		  return;
	  }			    
		    
//		    SeleniumWebUtils.reportInfo("||====>Typed in Element: ["+by+"]. Value: ["+ valueToType+"]", hideLogger);
		    
		    
		    
		    
	  }

//	public static void XType(WebDriver driver, By by, String Text2Type, String TextIfError){
//		
//		if (SeleniumWebUtils.isElementEnabled(driver, by)){
//			SeleniumWebUtils.FindClearAndTypeValueInEditBox(driver, by, Text2Type);
//		}else{
//			SeleniumWebUtils.reportFAIL(driver, TextIfError);
//		}
//	}  

		public static void XType(WebDriver driver, By by, String Text2Type, String TextIfError){
			
			//if (SeleniumWebUtils.isElementDisplayed(driver, by)){
				SeleniumWebUtils.FindClearAndTypeValueInEditBox(driver, by, Text2Type);
			//}else{
			//	SeleniumWebUtils.reportFAIL(driver, TextIfError);
			//}
		}  	  
	  
	  
	  
	public static boolean XClick(WebDriver driver, By by, String TextIfError){
		if (SeleniumWebUtils.isElementEnabled(driver, by)){
			return ax.x_ClickIfExist(driver, by);
		}else{
			SeleniumWebUtils.reportFAIL(driver, TextIfError);
			return false;
		}
	} 	 
	public static void XClick_if_DISPLAYED(WebDriver driver, By by, String TextIfError){
		if (SeleniumWebUtils.isElementDisplayed(driver, by)){ 
			SeleniumWebUtils.XClick(driver, by, TextIfError);
		}
		//else{
		//	SeleniumWebUtils.reportFAIL(driver, TextIfError);
		//}
	} 
	
	public static void XClick_AFTER_WAITING(WebDriver driver, By by, String TextIfError){
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, by);
		SeleniumWebUtils.XClick(driver, by, TextIfError);
	} 	 	
	
	
	public static void XSelect(WebDriver driver, By by, String ValueTextIndex, int SearcherFactor/*0-index, 1-value, 2-text*/, String TextIfError){
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until( ExpectedConditions.elementToBeSelected(driver.findElement(by)));      
		
//		if (SeleniumWebUtils.isElementEnabled(driver, by)   ){
			if (SeleniumWebUtils.isElementDisplayed(driver, by) ){
			switch (SearcherFactor) { 
			case 0: // BY INDEX
			SeleniumWebUtils.selectAndClickByIndex(driver, by, Integer.parseInt(ValueTextIndex));
				break;
			case 1: // BY VALUE
			SeleniumWebUtils.selectAndClickByValue(driver, by, ValueTextIndex);
				break;
			case 2: // BY TEXT
			SeleniumWebUtils.selectAndClickByText(driver, by, ValueTextIndex);
				break;
			default:
				SeleniumWebUtils.selectAndClickByText(driver, by, ValueTextIndex);
				break;
			}
			
		
		}else{
			SeleniumWebUtils.reportFAIL(driver, TextIfError);
		}
	} 	 

	
	  public static void FindClearAndTypeValueInEditPasswordBox(WebDriver driver, By by, String valueToType, int timeoutInSeconds){
	      WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
	      wait.until( ExpectedConditions.presenceOfElementLocated(by) );
//	      wait.until(ExpectedConditions.elementToBeSelected(by));	      
		    driver.findElement(by).clear();
		    driver.findElement(by).sendKeys(valueToType);		  
		    SeleniumWebUtils.reportInfo("||====>Typed in Element: ["+by+"]. Value: [******]");
	  }
	  
	  
	  public static boolean FindAndTypeValueInUploadBox(WebDriver driver, By by, String valueToType){
		  return SeleniumWebUtils.FindAndTypeValueInUploadBox(driver, by, valueToType, ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT);
	  }
	  public static boolean FindAndTypeValueInUploadBox(WebDriver driver, By by, String valueToType, int timeoutInSeconds){
	      WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
	      wait.until( ExpectedConditions.presenceOfElementLocated(by) );
		    driver.findElement(by).sendKeys(valueToType);		  
		    SeleniumWebUtils.reportInfo("||====>Typed in Upload Box ["+by+"]. Value: ["+ valueToType+"]");
		    return true;
	  }
	  
	  public static void AsserterForTextPresence(WebDriver driver, String expectedValues, By by){
		  SeleniumWebUtils.reportInfo("Enter Function AsserterForTextPresence for: By="+by+" and text:  "+expectedValues);
		  try {
			      AssertJUnit.assertEquals(expectedValues, driver.findElement(by).getText());
//			      SeleniumWebUtils.reportInfo("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			      SeleniumWebUtils.reportInfo("Matched values for: "+expectedValues);
//			      SeleniumWebUtils.reportInfo("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			    } catch (Error e) {
			    	SeleniumWebUtils.reportFAIL(driver,"Failed Matching values for: "+expectedValues);
				    SeleniumWebUtils.reportFAIL(driver,e.toString());
				    TakePhoto.takePhoto(driver, "Failed Matching values for: "+expectedValues);
			    }		  
		  SeleniumWebUtils.reportInfo("EXIT Function AsserterForTextPresence for: By="+by+" and text:  "+expectedValues);
	  }
	  public static void AsserterForTextMatchingAndPresence(WebDriver driver, String expectedValues, By by){
		  SeleniumWebUtils.reportInfo("Enter Function AsserterForTextMatchingAndPresence for: By="+by+" and MatchedPattern:  "+expectedValues);
		  String textFromGui = driver.findElement(by).getText();
		  try {
		  	 AssertJUnit.assertTrue(textFromGui.matches(expectedValues));
//			  assertEquals(expectedValues, driver.findElement(by).getText());
//			      SeleniumWebUtils.reportInfo("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			      SeleniumWebUtils.reportInfo("Matched values for: "+textFromGui);
//			      SeleniumWebUtils.reportInfo("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			    } catch (Error e) {
			    	SeleniumWebUtils.reportFAIL(driver,"Failed Matching values for: "+textFromGui);
				    SeleniumWebUtils.reportFAIL(driver,e.toString());
				    TakePhoto.takePhoto(driver, "Failed Matching values for: "+textFromGui);
			    }		  
		  SeleniumWebUtils.reportInfo("EXIT Function AsserterForTextMatchingAndPresence for: By="+by+" and text:  "+textFromGui);
	  }
	  
	  public static boolean AsserterForTextContains(WebDriver driver, String expectedValues, By by){
		  SeleniumWebUtils.reportInfo("Enter Function AsserterForTextContains for: By="+by+" and MatchedPattern:  "+expectedValues);
		  String textFromGui = driver.findElement(by).getText();
		  
		  if(textFromGui.contains(expectedValues)){
		      SeleniumWebUtils.reportInfo("Text from GUI ["+textFromGui+"] contains expected: "+expectedValues); return true;
		  }else{
		    	SeleniumWebUtils.reportFAIL(driver,"Text from GUI ["+textFromGui+"] not contains expected: "+expectedValues);
			    TakePhoto.takePhoto(driver, "Failed Matching values for: "+textFromGui); return false;
		  }
	  }
	  
	  
	  
	  public static void AsserterForTextPresenceToCompare(WebDriver driver, String StringCompare, String expectedValues){
		  SeleniumWebUtils.reportInfo("Enter Function AsserterForTextPresenceToCompare for text:  "+expectedValues);
		  try {
			      AssertJUnit.assertEquals(expectedValues, StringCompare);
			      SeleniumWebUtils.reportInfo("Matched values for: Compared text["+StringCompare+"] and Expected:["+expectedValues+"].");
			    } catch (Error e) {
			    	SeleniumWebUtils.reportFAIL(driver,"Failed Matching values for: Compared text["+StringCompare+"] and Expected:["+expectedValues+"].");
				    SeleniumWebUtils.reportFAIL(driver,e.toString());
				    TakePhoto.takePhoto(driver, "Failed Matching values for: Compared text["+StringCompare+"] and Expected:["+expectedValues+"].");
			    }		  
		  SeleniumWebUtils.reportInfo("EXIT Function AsserterForTextPresenceToCompare for text:  "+expectedValues);
	  }
	  
	  public static void AsserterForTitlePresence(WebDriver driver, String expectedValues){
		  SeleniumWebUtils.reportInfo("Enter Function AsserterForTitlePresence for: "+expectedValues);
		  try {
			      AssertJUnit.assertEquals(expectedValues, driver.getTitle());
//			      SeleniumWebUtils.reportInfo("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			      SeleniumWebUtils.reportInfo("Matched values for title : "+expectedValues);
//			      SeleniumWebUtils.reportInfo("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			    } catch (Error e) {
			    	SeleniumWebUtils.reportFAIL(driver,"Failed Matching values for title: "+expectedValues);
				    SeleniumWebUtils.reportFAIL(driver,e.toString());
				    TakePhoto.takePhoto(driver, "Failed Matching values for: "+expectedValues);
			    }		  
		  SeleniumWebUtils.reportInfo("EXIT Function AsserterForTitlePresence for title  "+expectedValues);
	  }	  
	  
	  
	  public static void Report(String monit){
		String info = "["+SeleniumWebUtils.Functionlogger() + "] Action: "+monit+" ===";
			ax.infoDEBUG(info);
			reportInfo(info, hideLogger);
	  }
	  
	  
	  public static WebElement fluentWait(WebDriver driver, final By locator) {
		    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		            .withTimeout(30, TimeUnit.SECONDS)
		            .pollingEvery(5, TimeUnit.SECONDS)
		            .ignoring(NoSuchElementException.class);

		    WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
		        public WebElement apply(WebDriver driver) {
		            return driver.findElement(locator);
		        }
		    });

		    return  foo;
		};	  

		public static WebElement fluentFindElement(final WebDriver driver, final By locator, final int timeoutSeconds) {
		    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		            .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
		            .pollingEvery(500, TimeUnit.MILLISECONDS)
		            .ignoring(NoSuchElementException.class);

		    return wait.until(new Function<WebDriver, WebElement>() {
		        public WebElement apply(WebDriver webDriver) {
		            return driver.findElement(locator);
		        }
		    });
		}		
		
	  public static void MaxWindow(WebDriver driver){
		  if(ax.READ_CONFIG_FILE("WINDOW_MAX").equals("YES") && (Setuper.driver2== null & Setuper.driver3== null)){
			  driver.manage().window().maximize();  
		  }
	  }
	  

		public static boolean isElementExist(WebDriver driver, By by) {
		    ax.turnOff_time(driver); 
			boolean s = driver.findElements(by).size() > 0;
			ax.turnOn_time(driver);
			return s;
		}
		
		// this works when returing presence!!! - takes time to wait
	    public static boolean isElementPresent(WebDriver driver, By by) {
	        try {
	          driver.findElement(by);
	          return true;
	        } catch (NoSuchElementException e) {
	          return false;
	        }
	      }	 

	    public static boolean isElementDisplayed(WebDriver driver, By by) {
/*
     // #########################################
	        try {
	        	driver.findElement(by).isDisplayed();
//	        	el.isDisplayed();
	          return true;
	        } catch (NoSuchElementException e) {
	          return false;
	        }
    // #########################################
*/
	    	if(!SeleniumWebUtils.isElementHiddenNow(driver, by)){
	    		return true;
	    	}else{
	    		return false;
	    	}
	      }	 

	    
	    public static boolean is_ID_DISPLAYED(WebDriver driver, String ID_OF_ELEMENT){
	    	//switchRegistryWarningText
	        try {
	        	driver.findElement(By.id(ID_OF_ELEMENT)).isDisplayed();
	          return true;
	        } catch (NoSuchElementException e) {
	          return false;
	        }
	    }
	    public static boolean is_XP_DISPLAYED(WebDriver driver, String XPATH_OF_ELEMENT){
	        try {
	        	driver.findElement(By.id(XPATH_OF_ELEMENT)).isDisplayed();
	          return true;
	        } catch (NoSuchElementException e) {
	          return false;
	        }
	    }
	    
	    public static boolean is_XBOOL_PresentElement(WebDriver driver, By by){
	    	if (!driver.findElements(by).isEmpty()){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }
	    
	    
	    public static boolean isWebElementDisplayed(WebDriver driver, WebElement el) {
	        try {
	        	el.isDisplayed();
	          return true;
	        } catch (NoSuchElementException e) {
	          return false;
	        }
	      }	 	    
	    
	    public static boolean isElementEnabled(WebDriver driver, By by) {
	        if(driver.findElement(by).isEnabled()){
	        	return true;
	        }else{
	          return false;
	        }
	      }	 
	    
	    public static boolean isElementHiddenNow(WebDriver driver, By by) {
	        turnOffImplicitWaits(driver);
	        boolean result = false;
	        try {
	           result = ExpectedConditions.invisibilityOfElementLocated(by).apply(driver);
	        }
	        finally {
	           turnOnImplicitWaits(driver);
	        }
	        return result;
	    }	
	    // Check if Element is displayed without waiting....
	    public static boolean isElementDisplayedNow(WebDriver driver, By by) {
	    	List<WebElement> elements;
	    	int elements_size;
//	        turnOffImplicitWaits(driver);
	        boolean result1 = false;
	        if(driver.findElements(by).size() > 0){
	        	elements = driver.findElements(by);
	        	elements_size = elements.size();
	        	if(elements_size==1 && driver.findElement(by).isDisplayed()){
	        		result1=true;
	        	}
	        	else if(elements_size==1 && !driver.findElement(by).isDisplayed()){
	        		System.out.println("element "+by.toString()+" exist, but is not displayed");
	        		result1=false;
	        	}
	        	else if(elements_size > 1 && elements.get(0).isDisplayed()){
	        		System.out.println("there are more than one element "+by.toString()+", and first is displayed");
	        		result1=true;
	        	}
	        	else if(elements_size > 1 && !elements.get(0).isDisplayed()){
	        		System.out.println("there are more than one element "+by.toString()+", and first is not displayed");
	        		result1=false;
	        	}
	        }else{
	        	result1 = false;
	        }
//	        finally {
//	           turnOnImplicitWaits(driver);
//	        }
	        return result1;
	    }		    

		public static boolean isDisplayedBy(WebDriver driver, By by) {
			return (!isElementHiddenNow(driver, by))?true:false;}	    
	    
	    public static void turnOffImplicitWaits(WebDriver driver) {
	        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	    }
	    public static void turnOnImplicitWaits(WebDriver driver) {
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    }
	    
	    
	    public static boolean isElement_CBX_RADIO_Selected(WebDriver driver, By by) {
	        if(driver.findElement(by).isSelected()){
	        	return true;
	        }else{
	          return false;
	        }
	      }	 
	    
	    public static WebElement getElementIfPresent(WebDriver driver, By locator) {
	        driver.manage().timeouts().implicitlyWait(ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT, TimeUnit.MILLISECONDS);
	        List<WebElement> elements = driver.findElements(locator);
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        if(!elements.isEmpty()) {
	            return elements.get(0);
	        }
	        else {
	            return null;
	        }
	    }	    
	    public static WebElement getElementIfDisplayed(WebDriver driver, By locator) {
	        driver.manage().timeouts().implicitlyWait(ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT, TimeUnit.MILLISECONDS);
	        WebElement elements = driver.findElement(locator);
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        if(!elements.isDisplayed()) {
	            return elements;
	        }
	        else {
	            return null;
	        }
	    }	    
	    
	    /**
	     * Private method that acts as an arbiter of implicit timeouts of sorts.. sort of like a Wait For Ajax method.
	     */
	    
	    public static WebElement waitForElementToBeDisplayed(WebDriver driver, By by) {
	        int attempts = 0;
	        int size = driver.findElements(by).size();

	        while (size == 0) {
	            size = driver.findElements(by).size();
	            if (attempts == MAX_ATTEMPTS) SeleniumWebUtils.reportFail(String.format("Could not find %s after %d seconds", by.toString(), MAX_ATTEMPTS));
	            attempts++;
	            try {
	                Thread.sleep(1000); // sleep for 1 second.
	            } catch (Exception x) {
	            	SeleniumWebUtils.reportFail("Failed due to an exception during Thread.sleep!");
	                x.printStackTrace();
	            }
	        }

	        if (size > 1) System.err.println("WARN: There are more than 1 " + by.toString() + " 's!");

	        return driver.findElement(by);
	    }	  
	    
	    
	    
public static boolean isFormInputErrorTextPresent(WebDriver driver) {
	    		// (driver,By.xpath("//*[contains(@id,'mainForm:j_idt')]/span[@class='ui-message-error-detail']"))
	    		if (SeleniumWebUtils.isElementDisplayed(driver,By.xpath("//*[contains(@id,'Form:j_idt')]/span[@class='ui-message-error-detail']"))){
	    			String error= SeleniumWebUtils.getTextForElement(driver, By.xpath("//*[contains(@id,'Form:j_idt')]/span[@class='ui-message-error-detail']")); 
	    			SeleniumWebUtils.reportFAIL(driver, error);	    			
	    			return true;
	    		}else{
	    			return false;
	    	}
//	    		driver.findElements(By.xpath("//*[contains(@id,'mainForm:j_idt')]/span[@class,'ui-message-error-detail']")).size();
//	    		List<WebElement> foundElements = driver.findElements(By.xpath("//*[contains(@id,'mainForm:j_idt')]/span[@class,'ui-message-error-detail']"));
//	    	    return foundElements.size() > 0;
	      }	 

	    	
	    	public static boolean isWizardFormInputErrorTextPresent(WebDriver driver) {
	    		if (SeleniumWebUtils.isDisplayedBy(driver,By.xpath("//*[contains(@id,'wizardForm:j_idt')]/span[@class='ui-message-error-detail']"))){
	    			String error= SeleniumWebUtils.getTextForElement(driver, By.xpath("//*[contains(@id,'wizardForm:j_idt')]/span[@class='ui-message-error-detail']")); 
	    			SeleniumWebUtils.reportFAIL(driver, error);	    			
	    			return true;
	    		}else{
	    		return false;
	    		}
	    	}

	    	public static String getMethodName(final int depth)
	    	{
	    	  final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	    	  //System. out.println(ste[ste.length-depth].getClassName()+"#"+ste[ste.length-depth].getMethodName());
	    	  // return ste[ste.length - depth].getMethodName();  //Wrong, fails for depth = 0
	    	  return ste[ste.length - 1 - depth].getMethodName(); //Thank you Tom Tresansky
	    	}	
	    	
	    	
	   public static String Functionlogger() {  
	            StackTraceElement caller = new Throwable().getStackTrace()[1];  
//	            System.out.println(new Date()  + " | " + caller.getClassName() + " " + caller.getMethodName()+ " line " + caller.getLineNumber() + " | " );  
	            String x =DateOperations.LOG_NOW()  + ""; //["+getMethodName(0)+"]  
	   return x;     
	   }  	    	
		
		public static String printCallingMethod() {
			String ret = null;
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				if (stackTrace[i].getClassName().contains("clima")) {
					String className = Thread.currentThread().getStackTrace()[i].getClassName();
					String methodName = Thread.currentThread().getStackTrace()[i].getMethodName();
					ret = "test: " + StringUtils.substringAfterLast(className, ".") + ": method: " + methodName;
				}
			}
			return ret;
		}

		public static String reportInfo(String info) {
			return SeleniumWebUtils.reportInfo(info, 0);
		}
		public static String reportInfo(String info, int level) {
			
			if(ax.getProp("TEST_MODE").equals("NORMAL")) return "";
			
			if (level==0){
				Reporter.log("<div style='background-color:#DEE8FC;color:black;font-style:italic;font-size:110%'>"+info+"</div>");
			}else if (level==1){
				Reporter.log("<div style='background-color:aqua;color:black;font-style:italic;font-size:110%'>"+info+"</div>");
			}else if (level==2){
				Reporter.log("<div style='background-color:tan;color:black;font-style:italic;font-size:110%'>"+info+"</div>");
			}else{
				Reporter.log("<div style='background-color:#DEE8FC;color:silver;font-style:normal;font-size:100%'>"+info+"</div>");
			}

			String callMeth = SeleniumWebUtils.printCallingMethod();
			String fxLog = SeleniumWebUtils.Functionlogger();
//			System.out.println(callMeth + ". :: ==>");
//			System.out.println("");
//			System.out.println("|" +  fxLog + "| " + info + "     ");
			System.out.println("=  =  =  =  =  =  =  = [" +  fxLog + "][    " + info + "    ] =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  =  ");
			Reporter.log("<HR>##[" +  fxLog + "] [ " + info + " ]###\n<BR><i>Parent method: "+ callMeth + ". Last method: " +getCurrMethodName(1)+"</i><BR>");


			
			
			return info + " " + callMeth + ". Last method: " +getCurrMethodName(1);
		}

		public static String reportInfo(String info, String hideLogger) {
			if(hideLogger.contains("HIDE")){
				return "";
			}else{
				return SeleniumWebUtils.reportInfo(info, 0);
			}
		}
		
		
		
		public static String reportInfoWithPhoto(WebDriver driver, String info) {
			System.out.print(printCallingMethod() + "::");
			System.out.println("//****************************************************************************************************");
			System.out.println(".### " + info + " ###");
			System.out.println("//****************************************************************************************************");
			TakePhoto.takePhoto(driver, info);			
			Reporter.log("<HR>### " + info + " ###\n<BR><i>Parent method: "+ printCallingMethod() + ". Last method: " +getCurrMethodName(3)+"</i><BR>");
			return info + " " + printCallingMethod() + ". Last method: " +getCurrMethodName(1);
		}
		
		public static String reportFAIL(WebDriver driver, String info) {
			Reporter.log("<div style='background-color:orange;color:black;font-weight:bold;border:1px black solid;padding:5px;'>"+info+"</div>");
			Reporter.log("<div style='background-color:red;color:white;font-weight:bold'> FAIL !!!</div>"); // in method: ["+printCallingMethod() + "]
			System.out.println("//****************************************************************************************************");
			System.out.println(".#[ERROR]##  Operation Failed ###: "+info + "__" +ax.GET_NOW());
			System.out.println("//****************************************************************************************************");
			TakePhoto.takePhoto(driver, info);			
			return info + " " + printCallingMethod();
		}
		public static String reportFail(String info) {
			Reporter.log("<div style='background-color:red;color:white;font-weight:bold;border:1px black solid;padding:5px;'> FAIL !!!  :: "+info+"</div>"); //in method: ["+printCallingMethod() + "]
			System.out.println("//****************************************************************************************************");
			System.out.println(".###  Operation Failed ###: "+info + "__" +ax.GET_NOW());
			System.out.println("//****************************************************************************************************");
//			TakePhoto.takePhoto(driver, "");
			return info + " " + printCallingMethod();
		}

		public static String report_TEST_PASSED(String info) {
			Reporter.log("<div style='background-color:lime;color:black;font-family:Arlial; font-weight:bold;border:3px black solid;padding:5px;margin:5px;'> TEST PASSED !!!  "+info+"</div>");
			System.out.println("// v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v *");
			System.out.println(".###  TEST CASE PASSED  ###: "+info + "__" +ax.GET_NOW());
			System.out.println("// v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v * v *");
//			TakePhoto.takePhoto(driver, "");
			return info;
		}				
		public static String report_TEST_FAIL(String info) {
			Reporter.log("<div style='background-color:red;color:yellow;font-family:Arlial; font-weight:bold;border:3px black solid;padding:5px;margin:5px;'> TEST FAILED !!!  "+info+"</div>");
			System.out.println(" x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x *");
			System.out.println(".###  TEST CASE FAILED  ###: "+info + "__" +ax.GET_NOW());
			System.out.println(" x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x *");
//			TakePhoto.takePhoto(driver, "");
			return info;
		}		
		public static String report_TEST_GROUP_FAIL(String info) {
			Reporter.log("<div style='background-color:red;color:yellow;font-family:Arlial; font-weight:bold;border:3px black solid;padding:5px;margin:5px;'> TEST FAILED !!!  "+info+"</div>");
			System.out.println(" x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x *");
			System.out.println(" x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x *");
			System.out.println(".###  TEST GROUP FAILED  ###: "+info + "__" +ax.GET_NOW());
			System.out.println(" x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x *");
			System.out.println(" x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x * x *");
//			TakePhoto.takePhoto(driver, "");
			return info;
		}		
		
		
		
		public static String reportFAIL_END_TEST(WebDriver driver, String info) {
			Reporter.log("<div style='border:1px black solid;padding:4px;background:white'>"+info+"<br><BR></div>");
			System.out.println("//****************************************************************************************************");
			System.out.println(".//###  Operation Failed ###: "+info + "__" +ax.GET_NOW());
			System.out.println("");
			System.out.println("");
			System.out.println("//****************************************************************************************************");
			TakePhoto.takePhoto(driver, "");
			return info + " " + printCallingMethod();
		}
		
		public static void reportFAIL_RETURN(WebDriver driver, String failInfo, int ScreenShot){
    		switch (ScreenShot) {
			case 1: SeleniumWebUtils.reportFAIL(driver, failInfo); break;
			case 0: SeleniumWebUtils.reportFail(failInfo); break;
			default: SeleniumWebUtils.reportFail(failInfo); break;
			}
			return;
		}
		
		
		public static String fail(String info) {
			Reporter.log("<div style='background-color:red;color:white;font-weight:bold'> FAIL !!!  :: "+info+"</div>"); //in method: ["+printCallingMethod() + "]
			System.out.println("//****************************************************************************************************");
			System.out.println(".[ !!!  FAIL !!!  : "+info+"  ::]"); //in method: ["+printCallingMethod() + "]
			System.out.println("//****************************************************************************************************");
			return info + " " + printCallingMethod();
		}

		public static String reportPass(String info) {
			Reporter.log("<div style='background-color:lime;color:black;font-weight:bold'> PASS !!!  :: "+info+"</div>"); //in method: ["+printCallingMethod() + "]
//			System.out.println("//****************************************************************************************************");
			System.out.println(".[[[ PASS ]  : "+info+"  ::]"); //in method: ["+printCallingMethod() + "]
//			System.out.println("//****************************************************************************************************");
			return info + " " + printCallingMethod();
		}
		
		public static void REPORT_FAIL_WITH_EXCEL(String TEST_CASE_NAME, int EndTest) throws IOException, InterruptedException{
			String TEST_SESSION_ID = ProjectDataVariables.TEST_SESSION_ID;
			if(EndTest==0){
				SeleniumWebUtils.reportFAIL_END_TEST(driver, TEST_CASE_NAME);	
			}else{
				SeleniumWebUtils.reportFail(TEST_CASE_NAME);
			}
			ax.fail(TEST_CASE_NAME+"_"+TEST_SESSION_ID);
		}
		
		public static void REPORT_FAIL_WITH_EXCEL(WebDriver driver, String TEST_CASE_NAME){
			String TEST_SESSION_ID = ax.GET_NOW();
			SeleniumWebUtils.reportFail(TEST_CASE_NAME);
			ax.fail(TEST_CASE_NAME+"_"+TEST_SESSION_ID);
		}
		public static void REPORT_PASS_WITH_EXCEL(String TEST_CASE_NAME){
			String TEST_SESSION_ID =  ax.GET_NOW();
			SeleniumWebUtils.reportPass(TEST_CASE_NAME);
			ax.pass(TEST_CASE_NAME+"_"+TEST_SESSION_ID);
		}
		
		public static String reportPASS_with_Photo(WebDriver driver) {
			return SeleniumWebUtils.reportPASS_with_Photo(driver, "Test Passed");
		}
		public static String reportPASS_with_Photo(WebDriver driver, String info) {
			Reporter.log("<div style='background-color:lime;color:black;font-weight:bold'> PASS !!!  :: "+info+"</div>"); //in method: ["+printCallingMethod() + "]
			System.out.println("//****************************************************************************************************");
			System.out.println("[ !!!  SUCCESS !!!  : "+info+"  ::]"); //in method: ["+printCallingMethod() + "]
			System.out.println("//****************************************************************************************************");
			TakePhoto.takePhoto(driver, info);			
			return info + " " + printCallingMethod();
		}

		public static void report_FUNCTION_ENTER(String FXNAME) {
			report_FUNCTION_ENTER(FXNAME, 1);
		}
		public static void report_FUNCTION_ENTER(String FXNAME, int Importance) {
			if(!ax.isDebugMode()) return;
			String s = "ENTER FUNCTION  => " + FXNAME + " < == # == # == # == # == # == # == # == # == # == # == # == # == ";
			Reporter.log("<div style='background-color:#99CCCC;color:blue;font-weight:bold;font-size:130%;border:1px silver solid;padding:2px'>"+s+"</div>");
			if(Importance==1){
				System.out.println("//##############################################################################################################");
			System.out.println("#" + ax.xLOG()+"       ENTER TO FUNCTION  => " + FXNAME);
				System.out.println("//##############################################################################################################");
			}else if(Importance==2){
				System.out.println("#" + ax.xLOG()+"       ENTER TO FUNCTION  => " + FXNAME);
			}else{
				System.out.println(" " + ax.xLOG()+"       ENTER TO FUNCTION  => " + FXNAME);
			}
		}
		public static void report_FUNCTION_ENTER_BLOCK_GROUP(String FXNAME) {
			if(!ax.isDebugMode()) return;
			String s = "ENTER FUNCTION  => " + FXNAME + " < == # == # == # == # == # == # == # == # == # == # == # == # == ";
			Reporter.log("<div style='background-color:#99CCCC;color:blue;font-weight:bold;font-size:130%;border:1px silver solid;padding:2px'>"+s+"</div>");
			System.out.println("");  System.out.println(""); System.out.println("");
			System.out.println("//########################################################################################################################   ");
			System.out.println("#"+ ax.xLOG()+" ENTER BLOCK TEST GROUP:  => " + FXNAME);
			System.out.println("//########################################################################################################################   ");
		}
		public static void report_FUNCTION_EXIT_BLOCK_GROUP(String FXNAME) {
			if(!ax.isDebugMode()) return;
			String s = "ENTER FUNCTION  => " + FXNAME + " < == # == # == # == # == # == # == # == # == # == # == # == # == ";
			Reporter.log("<div style='background-color:#99CCCC;color:blue;font-weight:bold;font-size:130%;border:1px silver solid;padding:2px'>"+s+"</div>");
			System.out.println("");  System.out.println(""); System.out.println("");
			System.out.println("//#xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx   ");
			System.out.println("#"+ ax.xLOG()+" ENTER BLOCK TEST GROUP:  => " + FXNAME);
			System.out.println("//#xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx   ");
			System.out.println(""); System.out.println(""); System.out.println("");
		}
		
		public static void report_FUNCTION_EXIT(String FXNAME) {
			String s = "EXIT FUNCTION  => " + FXNAME + " < == # == # == # == >";
			Reporter.log("<div style='background-color:#DEE8FC;color:black;font-weight:bold;font-size:130%;border:2px blue solid'>"+s+"</div>");
			//System.out.println("##############################################################################################################");
			System.out.println(ax.xLOG()+" -------->>> EXIT FUNCTION  => " + FXNAME);
			//System.out.println("##############################################################################################################");
		}
		
		
		
		public static String getRequestNumberFromMonit(String monit){
			final String identifier = "identifier";
			String monitor = monit.substring(monit.indexOf(identifier)+identifier.length()+1, monit.length()-1);
//			System.out.println(">>"+monitor+"<<");
			return monitor;
		}	  
		public static String getRequestNumberFromMonit(String monit, String wordBeforeNumber){
			final String identifier = wordBeforeNumber;
			String monitor = monit.substring(monit.indexOf(identifier)+identifier.length()+1, monit.length()-1);
//			System.out.println(">>"+monitor+"<<");
			return monitor;
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
//					System.out.println("MAM");
//					indexFound = ALL_MONIT_ARRAY_AL.indexOf(TEXT_ARRAY_AL.get(OFFSET));
					indexFound = ALL_MONIT_ARRAY_AL.indexOf(TEXT_ARRAY_AL.get(0));
					try {
						graber = ALL_MONIT_ARRAY_AL.get(indexFound + OFFSET + TEXT_ARRAY_AL.size()  );	
					} catch (IndexOutOfBoundsException i) {
						System.out.println("To big offset "+OFFSET);
						return null;
					}
					
					
					if(!graber.isEmpty()){
						RETURNER =  graber.replaceAll("\\.", "");
						System.out.println(">>"+RETURNER+"<<");
						return RETURNER;
					}else{
						return null;
					}
				}else{
					System.out.println("NULL: Lack of such data for " + TEXT_BEFORE);
				}
			return null;
		}	
		
		
		public static String getRequestNumberOnlyFromMonit(String monit, String wordBeforeNumber){
			String reqIdentifier = "";
			String[] wordsOfMonit = monit.split(" ");
			for(int i=0;i<wordsOfMonit.length;i++){
				if(wordsOfMonit[i].contains(wordBeforeNumber) ){
					reqIdentifier=wordsOfMonit[i+1];
				}
			}
			if(!reqIdentifier.isEmpty()){
				return reqIdentifier.replaceAll("\\.", "");
			}else{
				return null;
			}
		}
  
	  
//[MARCIN]//////////////////////////////////////////////////////////////////
		
	    /**
	     * Search webDriver web element in normal way (AJAX_TIMEOUT). Throws NPE if
	     * element was not found.
	     *
	     * @return web element
	     */
	    public static WebElement findElement(WebDriver driver, By by) {
	        WebElement element = null;

	        try {
	        	driver.switchTo().defaultContent();
	            element = testIfElementNull(driver, by);

	        } catch (InvalidSelectorException npe) {
	        	reportFAIL(driver, "VERIFY_FAILED (InvalidSelectorException):: " + StringUtils.substringBefore(npe.getMessage(), "\n"));
//	            reportInfo("VERIFY_FAILED:: " + StringUtils.substringBefore(npe.getMessage(), "\n"));
				TakePhoto.takePhoto(driver, "");		            
	        } catch (NoSuchElementException nsee) {
//	            reportInfo("VERIFY_FAILED:: " + StringUtils.substringBefore(nsee.getMessage(), "\n"));
	        	reportFAIL(driver, "VERIFY_FAILED (NoSuchElementException):: " + StringUtils.substringBefore(nsee.getMessage(), "\n"));
//				TakePhoto.takePhoto(driver, "");		            
	        } catch (StaleElementReferenceException sep){
	        	reportFAIL(driver, "VERIFY_FAILED (StaleElementReferenceException):: " + StringUtils.substringBefore(sep.getMessage(), "\n"));
	        	
	        }
	        

	        return element;
	    }

//	    public static WebElement findElementThrowException(WebDriver driver, By by) {
//	        WebElement element;
//	        driver.switchTo().defaultContent();
//	        try {
//	            element = findElement(driver, by);
//	        } catch (NoSuchElementException nse) {
//	            throw new ElementNotFoundException(StringUtils.substringBefore(nse.getMessage(), "\n"), SCREENSHOT_FILE_DIRECTORY, SCREENSHOT_FILE_DIRECTORY);
//	        }
//	        return element;
//	    }

	    public static void refreshPage(WebDriver driver) {
	        driver.navigate().refresh();
	    }

	    /**
	     * @return window title
	     */
	    public static String getTitle(WebDriver driver) {
	        return driver.getTitle();
	    }

	    public static boolean findMatchingElementOnPage(WebDriver driver, String arg0) {
	        return driver.getPageSource().contains(arg0) || driver.getPageSource().matches(arg0);
	    }

	    public static String getCurrentUrl(WebDriver driver) {
	        return driver.getCurrentUrl();
	    }		
		
	    private static Function<SearchContext, WebElement> elementLocated(final By by) throws TimeoutException {
	        return new Function<SearchContext, WebElement>() {

	            public WebElement apply(SearchContext context) {
	                return context.findElement(by);
	            }
	        };
	    }
	  
	    @SuppressWarnings("finally")
	    private static WebElement findElement(WebDriver driver, SearchContext context, By by, long timeoutSeconds, long sleepMilliseconds) {

	        FluentWait<SearchContext> wait = new FluentWait<SearchContext>(context).withTimeout(timeoutSeconds, TimeUnit.SECONDS)
	                .pollingEvery(sleepMilliseconds, TimeUnit.MILLISECONDS).ignoring(NotFoundException.class, StaleElementReferenceException.class);

	        WebElement element = null;

	        try {

	            driver.switchTo().defaultContent();
	            element = wait.until(elementLocated(by));

	        } finally {
	            return element;
	        }
	    }
	    
	    /**
	     * Search webDriver web element in normal way (AJAX_TIMEOUT). Throws NPE if
	     * element was not found.
	     * @param m 
	     * @param l 
	     *
	     * @return web element
	     */
	    public static WebElement findElement(WebDriver driver, By by, long l, long m) {
	        WebElement element = null;

	        try {
	            driver.switchTo().defaultContent();
	            element = testIfElementNull(driver, by);

	        } catch (InvalidSelectorException npe) {
//	            reportInfo("VERIFY_FAILED (InvalidSelectorException):: " + StringUtils.substringBefore(npe.getMessage(), "\n"));
	        	reportFAIL(driver, "VERIFY_FAILED:: " + StringUtils.substringBefore(npe.getMessage(), "\n"));
	            
	        } catch (NoSuchElementException nsee) {
//	            reportInfo("VERIFY_FAILED (NoSuchElementException):: " + StringUtils.substringBefore(nsee.getMessage(), "\n"));
	        	reportFAIL(driver, "VERIFY_FAILED:: " + StringUtils.substringBefore(nsee.getMessage(), "\n"));
	            
	        }

	        return element;
	    }	    
	    
	    public static WebElement getStaleElemById(WebDriver driver, By by) {
	        try {
	            return driver.findElement(by);
	        } catch (StaleElementReferenceException e) {
	            System.out.println("Attempting to recover from StaleElementReferenceException ...");
	            return getStaleElemById(driver, by);
	        }
	    }
	    
	    
		public static void FindButtonsPrimeFacesAndClickIt(WebDriver driver, By by){
			// [tip!] originally made for PrimeFaces buttons: By.xpath("//span[@class='ui-button-text' and contains(text(), 'Approve')]")
			WebElement d = driver.findElement(by);
			try {
				if(d.isDisplayed()){
					driver.findElement(by).click();
				}
			} catch (Exception e) {
				SeleniumWebUtils.reportFAIL(driver, "Cannot find the button [" + by + "]");
				e.printStackTrace();
			}
			
		}
		
		public static void ClickButtonSpanWithCssClassAndText(WebDriver driver, String cssClass, String textOnButton){
			if (driver.findElements(By.xpath("//span[@class='"+cssClass+"' and contains(text(), '"+textOnButton+"')]")).size() != 0){
				FindAndClickElement(driver, By.xpath("//span[@class='"+cssClass+"' and contains(text(), '"+textOnButton+"')]"));
			}				
		}
	    
	    
	    
	    
	    /**
	     * Method to find element in quick way, if that fails then search within
	     * AJAX_TIMEOUT time.
	     *
	     * @return web element
	     */
	    public static WebElement findNoException(By by) {
	        WebElement element = quickFindElementIgnoreExceptions(driver, by);

	        if (element == null)
	            element = findElementIgnoreExceptions(driver, by);

	        return element;
	    }	  

	    /**
	     * Search webDriver web element in fast way (AJAX_TIMEOUT / 10). Returns
	     * null even element was not found.
	     *
	     * @return web element
	     */
	    public static WebElement quickFindElementIgnoreExceptions(WebDriver driver, By by) {
	        long DIVIDE = 10;
	        return findElement(driver, by, AJAX_TIMEOUT / DIVIDE, DEFAULT_POLL_SLEEP / DIVIDE);
	    }	    

	    
	    /**
	     * Search webDriver web element in normal way (AJAX_TIMEOUT). Returns null
	     * even element was not found.
	     *
	     * @return web element
	     */
	    public static WebElement findElementIgnoreExceptions(WebDriver driver, By by) {
	        return findElement(driver, by, AJAX_TIMEOUT, DEFAULT_POLL_SLEEP);
	    }
	    


	    private static WebElement testIfElementNull(WebDriver driver, By by) throws NoSuchElementException, NullPointerException,
        StaleElementReferenceException {
	    	return driver.findElement(by);
	    }	    

		
	    /**
	     * Click on By locator, then search for errors element on page.
	     *
	     * @return clicked web element
	     */
	    public static WebElement click(WebDriver driver, By locator) {
	        WebElement element = findElement(driver, locator);
//	        highlight(element);
	        element.click();
	        return element;
	    }

	    public static WebElement click(WebElement element) throws NullPointerException {
//	        highlight(element);
	        element.click();
	        return element;
	    }

//	    public static void clickAndDoAction(WebElement element, Action action) throws NullPointerException {
//
//	        if (!PropertyManager.isEcasMockup()) {
////	            highlight(element);
//	        }
//	        element.click();
//	        action.onEndOfAction();
//	    }

//	    public static void clickAndDoAction(By by, Action action) throws NullPointerException {
//
//	        if (!PropertyManager.isEcasMockup()) {
////	            highlight(findElement(by));
//	        }
//	        findElement(by).click();
//	        action.onEndOfAction();
//	    }

	    public static WebElement clickRepeat(WebElement element, int TIMES) {
	        while (TIMES-- >= 1) {
	            element.click();
	        }
	        return element;
	    }

	    public static WebElement submit(WebDriver driver, By locator, String... name) { // notation String... name - means argument(s) are optional
	        WebElement element = findElement(driver, locator);
//	        highlight(element);
	        element.submit();
	        return element;
	    }

	    public static WebElement submit(WebElement element) {
	        element.submit();
	        return element;
	    }

	    public static WebElement type(WebDriver driver, By locator, String text) throws NoSuchElementException {
	        WebElement element = findElement(driver, locator);
//	        highlight(element);
	        element.clear();
	        element.sendKeys(text);
	        return element;
	    }

	    public static WebElement typeAndSendKey(WebDriver driver, By locator, String text, Keys key) throws NoSuchElementException {
	        WebElement element = findElement(driver, locator);
//	        highlight(element);
	        element.clear();
	        element.sendKeys(text);
	        element.sendKeys(key);
	        return element;
	    }

	    public static WebElement typeRetry(WebDriver driver, By locator, String text) throws NoSuchElementException {
	        WebElement element = quickFindElementIgnoreExceptions(driver, locator);

	        if (element == null) {
	            element = findElement(driver, locator);
	        }

	        element.clear();
	        element.sendKeys(text);

	        return element;
	    }

	    public static void typeIfTextPresent(WebDriver driver, By locator, String text) throws NoSuchElementException {
	        if (!text.isEmpty())
	            type(driver, locator, text);
	    }

	    public static WebElement selectAndClickByText(WebDriver driver, By selectLocator, String text) throws NoSuchElementException {
	    	WebElement element = findElement(driver, selectLocator);
	        new Select(element).selectByVisibleText(text);
	        return element;
	    }
	    public static WebElement selectAndClickByValue(WebDriver driver, By selectLocator, String Value) throws NoSuchElementException {
	        WebElement element = findElement(driver, selectLocator);
	        new Select(element).selectByValue(Value);
	        return element;
	    }
	    
	    public static void selectAndClickByTextIfTextPresent(WebDriver driver, By selectLocator, String text) throws NoSuchElementException {

	        if (!text.isEmpty())
	            selectAndClickByText(driver, selectLocator, text);

	    }

	    public static WebElement selectAndClickByIndex(WebDriver driver, By selectLocator, int id) throws NoSuchElementException {
	        WebElement element = findElement(driver, selectLocator);
	        new Select(element).selectByIndex(id);
	        return element;
	    }

	    public static void selectAndClickByIndexForm(WebDriver driver, By selectLocator, int id) throws NoSuchElementException {

	        if (Integer.valueOf(id) != null)
	            selectAndClickByIndex(driver, selectLocator, id);

	    }	    
	    
	    public static WebElement selectAndClickByTextThenWait(WebDriver driver, By selectLocator, String text) throws NoSuchElementException {
	        long wait = TimeUnit.SECONDS.toMillis(AJAX_TIMEOUT);
	        WebElement element = findElement(driver, selectLocator);
	        new Select(element).selectByVisibleText(text);
	        Boolean result = new WebDriverWait(driver, wait).until(textToBePresentInElement(driver, selectLocator, text));
	        return result == true ? element : null;
	    }

	    public static WebElement selectAndClickByIndexThenWait(WebDriver driver, By selectLocator, final int id, String text) throws NoSuchElementException {
	        long wait = TimeUnit.SECONDS.toMillis(AJAX_TIMEOUT);
	        final WebElement element = findElement(driver, selectLocator);

	        new Select(element).selectByIndex(id);
	        Boolean result = new WebDriverWait(driver, wait).until(textToBePresentInElement(driver, selectLocator, text));
	        return result == true ? element : null;
	    }

	    public static WebElement WaitForSpecificTextInElement(WebDriver driver, By selectLocator, String text){
	        long wait = TimeUnit.SECONDS.toMillis(AJAX_TIMEOUT);
	        final WebElement element = findElement(driver, selectLocator);
	        Boolean result = new WebDriverWait(driver, wait).until(textToBePresentInElement(driver, selectLocator, text));
	        return result == true ? element : null;
	    }
	    
	    public static boolean WaitForTextPresentInElement(WebDriver driver, final By by){
	    	if(driver.findElements(by).size() < 1){
	    		if(!ax.wait_elm(driver, by)) return ax.returnWhenFalse(driver, "fail waiting for element to check text-presence.. " + by.toString());
	    	}
	    	ax.wait(1);
	    	new WebDriverWait(driver,ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {                
	                String text = driver.findElement(by).getText().trim();
	                return !text.equals("");
	            }
	        });	 
	    	return true;
	    }
	    public static boolean WaitForTextPresentInElement(WebDriver driver, final WebElement elm){
	    	ax.wait(1);
	    	new WebDriverWait(driver,ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {                
	                String text = elm.getText().trim();
	                return !text.equals("");
	            }
	        });	 
	    	return true;
	    }	    
	    public static boolean WaitForTextGoneFromElement(WebDriver driver, final By by){
	    	System.out.println("enter fx: WaitForText GONE FromElement");
	    	if(driver.findElements(by).size() == 0){
	    		ax.WAIT4ELEMENT_READY_STALEXCEPTION_PROFF(driver, by);
//	    		WebDriverWait wait = new WebDriverWait(driver, ProjectDataVariables.TIME_FOR_CHECKING_ELEMENT);
//	    		wait.until(ExpectedConditions.stalenessOf(driver.findElement(by)));
	    		try {
	    			ax.getStaleElementByLocator(driver, by);	
				} catch (StaleElementReferenceException e) {
					ax.fail("STALE_EXCEPTION_ERROR_FOUND_"+AppGetter.GET_TIME_STAMP_FULL_NOW());
					ax.takePhoto(driver, "StaleException - something wrong with DOM structure...");
					e.printStackTrace();
					return false;
				}
	    	}
	    	
	    	new WebDriverWait(driver,ProjectDataVariables.TIME_FOR_WAITING_FOR_ELEMENT).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {                
	                String text = driver.findElement(by).getText();
	                return text.equals("");
	            }
	        });	    	
	    	return true;
	    }	    
	    public static boolean isPFButtonEnabled(WebDriver driver, By by){
	    	String ariadisabled = driver.findElement(by).getAttribute("aria-disabled");
	    	if (ariadisabled.contains("true")){
	    		return false;
	    	}else{
	    		return true;	
	    	}
	    }
	    
	    public static boolean isElementEnabled_by_DISABLED_PARAM(WebDriver driver, By by) {
	    	String disabled = driver.findElement(by).getAttribute("disabled");
	    	if (disabled.contains("true")){
	    		return false;
	    	}else{
	    		return true;	
	    	}
	      }	 
	    
	    public static boolean isElementHaving_ATTRIBUTE_VALUE(WebDriver driver, By by, String Attribute, String ExpectedValue) {
	    	String Attrib = driver.findElement(by).getAttribute(Attribute);
	    	
	    	if (Attrib.contains(ExpectedValue)){
	    		return true;
	    	}else{
	    		return false;	
	    	}
	      }	 
	    
	    
	    public static ExpectedCondition<Boolean> textToBePresentInElement(WebDriver driver, final By locator, final String text) {

	        return new ExpectedCondition<Boolean>() {

//	            @Override
	            public Boolean apply(WebDriver driver) {
	                try {

	                    String elementText = findElement(driver, locator).getText();
	                    return elementText.equalsIgnoreCase(text) || elementText.contains(text) || elementText.contains(StringUtils.upperCase(text))
	                            || elementText.matches(StringUtils.upperCase(text));

	                } catch (StaleElementReferenceException e) {
	                    return null;
	                }
	            }

//	            @Override
	            public String toString() {
	                return String.format("text ('%s') to be present in element found by %s", text, locator);
	            }

	        };
	    }

 //#####################################################
	    // Return element
	    public static String getTextForElement(WebDriver driver, By locator){
	    	return getTextForElement(driver, locator, false);
	    }
	    public static String getTextForElement(WebDriver driver, By locator, boolean now){
	    	if(!now) SeleniumWebUtils.WaitForTextPresentInElement(driver, locator);
            if(driver.findElement(locator).getText().isEmpty()){
            	return "";
            }else{
            	return driver.findElement(locator).getText();	
            }
	    }
	    
	    public static String getTextFromHintElement(WebDriver driver, By by){
	    	WebElement element = driver.findElement(by);
	    	return (String) ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", element);
	    }
	    
	    
	    // Return String
	    public static String simpleGetText(WebDriver driver, By by){
	    	   WebElement el = SeleniumWebUtils.findElementIgnoreExceptions(driver, by);
	    	   String resx=el.getText();
	    	   return resx;
	    }
	    // Return String
	    public static String getTextFromElement(WebDriver driver, By by){
	    	WebElement elem = ax.get_elm(driver, by);
	    	if(ax.IS_NULL_OR_EMPTY_WEBELEMENT(elem)) return null;
	        String text = "";
	        if (elem.isDisplayed()) {
	            text = elem.getText();		
	        }else{
	        	System.out.println("no text on element "+by);
	        	text = null;
	        }
	        return text;
		}
	    public static String getTextFromEditBoxElement(WebDriver driver, By by){
	    	WebElement elem = ax.get_elm(driver, by);
	    	if(ax.IS_NULL_OR_EMPTY_WEBELEMENT(elem)) return null;
	    	
//	        WebElement elem = driver.findElement(by);
	        String text = "";
	        if (elem.isDisplayed()) {
	            text = elem.getAttribute("value");		
	        }else{
	        	text = "Nothing There";
	        }
	        return text;
		}
	    public static String getTextFromElementIfExist(WebDriver driver, By by){
	    	if((driver.findElements(by).size() == 1) && (driver.findElement(by).getText().trim().length() > 0)){
	    		return driver.findElement(by).getText().trim();
	    	}else{
	    		return "";
	    	}
	    }
	    
	    
	    //#####################################################		
	    public static boolean isTextPresent(WebDriver driver, String txtValue){ // in PageSource
	         boolean b = false;
	     try{
	         b = driver.getPageSource().contains(txtValue);
	         return b;
	     }
	     catch (Exception e){
	         System.out.println("Problem finding text in function [isTextPresent]: " + e.getMessage());
	     }     
	     finally{
	      return b;
	     }
	}
	    public static boolean isTextPresentInElement(WebDriver driver, By by, String txtValue){
	         boolean b = false;
	     try{
	         b = driver.findElement(by).getText().contains(txtValue);
	         return b;
	     }
	     catch (Exception e){
	         System.out.println("Problem finding text in function [isTextPresent]: " + e.getMessage());
	     }     
	     finally{
	      return b;
	     }
	}
	    

	    
	    
	    public static WebElement type(WebElement element, String text) {
//	        highlight(element);
	        element.clear();
	        element.sendKeys(text);
	        return element;
	    }

	    /*public static void highlight(WebElement element) throws
	            NullPointerException {
	        try {
	            int wait = 100;
	            String orig = element.getAttribute("style");
	            setAttribute(element, "style", "color: yellow; border: 10px solid yellow; background-color: black;");
	            Thread.sleep(wait);
	            setAttribute(element, "style", "color: black; border: 10px solid black; background-color: yellow;");
	            Thread.sleep(wait);
	            setAttribute(element, "style", orig);
	        } catch
	                (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }*/		
		
//		public static void loopUntilElementPresent(WebDriver driver, By by) throws InterruptedException{
//		    do{
//		    	int wait = 1000;
//		    	System.out.println("Waiting for element: "+ by);
//		    	Thread.sleep(wait);
//		    }while(driver.findElements(by).size() != 0);
//		}
		
	    
   

	    
//	    public void waitForPageLoaded(WebDriver driver) {
//
//	        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
//	           public Boolean apply(WebDriver driver) {
//	             return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
//	           }
//	         };
//
//	        Wait<WebDriver> wait = new WebDriverWait(driver,30);
//	         try {
//	                 wait.until(expectation);
//	         } catch(Throwable error) {
//	                 Assert.assertFalse(true, "Timeout waiting for Page Load Request to complete.");
//	         }
//	    } 	    
//	    
	    
	    public static String getCurrMethodName(final int depth)
	    {
	      final StackTraceElement[] ste = Thread.currentThread().getStackTrace();

	      //System. out.println(ste[ste.length-depth].getClassName()+"#"+ste[ste.length-depth].getMethodName());
	      // return ste[ste.length - depth].getMethodName();  //Wrong, fails for depth = 0
	      return ste[ste.length - 1 - depth].getMethodName(); //Thank you Tom Tresansky
	    }
	    
	    public static void setAJAX_TIMEOUT(long aJAX_TIMEOUT) {
	        AJAX_TIMEOUT = aJAX_TIMEOUT;
	    }		
		
	    
	    public static void SHOW_SPACER(String TEXT){
	    	System.out.println("//########################################################");
	    	//System.out.println("###");
	    	//System.out.println("###");
//	    	System.out.println("      ");
	    	System.out.println("" + TEXT);
//	    	System.out.println("      ");
	    	//System.out.println("###");
	    	//System.out.println("###");
	    	System.out.println("//########################################################");
	    }
	    public static void SHOW_SPACER_SMALL(String TEXT){
	    	SHOW_SPACER_SMALL(TEXT, 1);
	    }
	    public static void SHOW_SPACER_SMALL(String TEXT, int Importance){
	    	if(Importance==1){
	    	System.out.println("//########################################################");
	    	System.out.println("..." + TEXT);
	    	System.out.println("//########################################################");
	    	}else{
	    		System.out.println("..." + TEXT);
	    	}
	    }	    
	   
	    
	    public static boolean LOOPER_WHILE(WebDriver driver, ArrayList<String> ACTION_TO_CHECK_TO, int STEP_OF_TIME){
	    	Object ob1 = null;
	    	Object eb1 = null;
	    	switch (ACTION_TO_CHECK_TO.get(0)) {
			case "COMPARE_INT":
				ob1 = ACTION_TO_CHECK_TO.get(1);
				eb1 = ACTION_TO_CHECK_TO.get(2);
				break;
			}
	    	
	    	
	    	
	    	
	    	int n=0;
	    	while(! (ob1==eb1)  ){
	    		try { Thread.sleep(STEP_OF_TIME); } catch (InterruptedException e) { e.printStackTrace(); }
//	    		ACTION_TO_POOL;
	    		n++;
	    		if(n > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){ return false;}
	    	}
	    	return true;
	    	
	    }
	    
	    
	    
/////##########################################################################	  
}

/*
 * //		  List<WebElement> elements ;
//		  elements = driver.findElements(By.name("proceed"));
//		  if(elements.size()!=0){
//			  driver.findElement(By.name("proceed")).click();
//		  }
 * 
 */
