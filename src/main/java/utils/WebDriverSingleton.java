package utils;

import java.util.ArrayList;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class WebDriverSingleton {

	private static WebDriver driver;
	private static WebDriver driver2;
	private static WebDriver driver3;
	public static ArrayList<WebDriver> allDrivers = new ArrayList<WebDriver>();
	private WebDriverSingleton() {}
	
	public static WebDriver getWebDriverInstance() {
		return getWebDriverInstance(SeleniumDriver.getCurrentBrowserType());
	}
	
	public static WebDriver getWebDriverInstance(String browser) {
		return getWebDriverInstance(1, browser);
	}
	
	public static WebDriver getWebDriverInstance(int driverNumber, String browser) {
        WebDriver runDriver = null;
		switch (driverNumber) {
		// main/first driver
		case 1:
			if (driver==null) {
	        	switch (browser) {
				case "FIREFOX":
					driver = WebDriverBuilder.firefoxDriver();
					break;
				case "CHROME":
					driver = WebDriverBuilder.chromeDriver();				
					break;
				case "MSIE":
					driver = WebDriverBuilder.ieDriver();
					break;
				}        	
	    		if(driver!=null){
	    			allDrivers.add(driver);
	    			ax.log("WebDriver instances size() = " + allDrivers.size());
	    		}	
			}	
			driver.manage().window().setPosition(new Point(0, 0));
			runDriver = driver;
			break;
		case 2:
			if (driver2==null) {
	        	switch (browser) {
				case "FIREFOX":
					driver2 = WebDriverBuilder.firefoxDriver();
					break;
				case "CHROME":
					driver2 = WebDriverBuilder.chromeDriver();				
					break;
				case "MSIE":
					driver2 = WebDriverBuilder.ieDriver();
					break;
				}        	
	    		if(driver2!=null){
	    			allDrivers.add(driver2);
	    			ax.log("WebDriver instances size() = " + allDrivers.size());
	    		}	
			}			
			runDriver = driver2;
			driver2.manage().window().setPosition(new Point(700, 0));
			break;
			
		case 3:
			if (driver3==null) {
	        	switch (browser) {
				case "FIREFOX":
					driver3 = WebDriverBuilder.firefoxDriver();
					break;
				case "CHROME":
					driver3 = WebDriverBuilder.chromeDriver();				
					break;
				case "MSIE":
					driver3 = WebDriverBuilder.ieDriver();
					break;
				}        	
	    		if(driver3!=null){
	    			allDrivers.add(driver3);
	    			ax.log("WebDriver instances size() = " + allDrivers.size());
	    		}	
			}			
			runDriver = driver3;
			driver3.manage().window().setPosition(new Point(1400, 0));
			break;
		}
		return runDriver;
    }

    public static void closeWebBrowser(){
        if (null != driver){
//            driver.quit();
            driver.close();
        }
        driver = null;
        SeleniumDriver.allDrivers.clear();
}
}
