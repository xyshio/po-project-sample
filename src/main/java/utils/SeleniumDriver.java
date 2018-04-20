package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;



/**
 * Selenium driver wrapper
 *
 * @author kgumulak
 */
public class SeleniumDriver {
    public static final String chromeBinary 			= "c:\\PGM\\chrome\\ChromiumPortable\\ChromiumPortable.exe";
    public static final String chromeDriverBinary 		= "c:\\PGM\\webdrivers\\chromedriver.exe";
    public static final String firefoxDriverBinary 		= "c:\\PGM\\webdrivers\\geckodriver.exe";
    
	public static int currentRunDriver = 1;
	public static String EUCR_BROWSER  = ax.getProp("TEST_BROWSER");
	public static String EUCR_BROWSER2 = ax.getProp("TEST_BROWSER2");
	public static String EUCR_BROWSER3 = ax.getProp("TEST_BROWSER3");
	public static String EUCR_BROWSER4 = ax.getProp("TEST_BROWSER4");
	
	
	public static ArrayList<WebDriver> allDrivers = new ArrayList<WebDriver>();
	public static WebDriver getDriver() {
		if(currentRunDriver==1){
			return WebDriverSingleton.getWebDriverInstance(1, SeleniumDriver.getCurrentBrowserType());	
		}else if(currentRunDriver==2){
			return WebDriverSingleton.getWebDriverInstance(2, SeleniumDriver.getCurrentBrowserType());
		}else if(currentRunDriver==3){
			return WebDriverSingleton.getWebDriverInstance(3, SeleniumDriver.getCurrentBrowserType());
		}
		return WebDriverSingleton.getWebDriverInstance(1, SeleniumDriver.getCurrentBrowserType());
	}
	
	public static WebDriver getDriver(int driverInstance) {
		return WebDriverSingleton.getWebDriverInstance(driverInstance, getCurrentBrowserType());
	}
	public static WebDriver getDriver(String browserType) {
		return WebDriverSingleton.getWebDriverInstance(1, browserType);
	}
	public static WebDriver getDriver(int driverInstance, String browserType) {
		return WebDriverSingleton.getWebDriverInstance(driverInstance, browserType);
	}

	public static void ClearWebDrivers(){
		WebDriverSingleton.closeWebBrowser();
//		if(driver!=null){
//			driver.close();
//		}
//		SeleniumDriver.allDrivers.clear();
	}
	
	public static void setCurrentDriverInstance(int driverInstance){
		currentRunDriver = driverInstance;
	}
	public static void setCurrentBrowserType(String browserType){
		EUCR_BROWSER = browserType;
	}
	public static String getCurrentBrowserType(){
		return getCurrentBrowserType(1);
	}
	public static String getCurrentBrowserType(int driverInstance){
		switch (driverInstance) {
		case 1: return EUCR_BROWSER;			
		case 2: return EUCR_BROWSER2;			
		case 3: return EUCR_BROWSER3;			
		case 4: return EUCR_BROWSER4;
		default: return EUCR_BROWSER;
		}
	}
	
}
