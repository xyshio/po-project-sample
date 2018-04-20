package utils;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

import org.openqa.selenium.By;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import utils.Setuper;

public class Selenide {

	
	public void run(){
    	SelenideElement linkKpp 	= $(By.id("menuForm:publicReports"));
    	SelenideElement accRequest 	= $(By.id("menuForm:request_account_menuitem"));
    	SelenideElement footer 		= $(By.id("footer"));
    	
    	SelenideElement accName 	= $(By.id("mainForm:accountName"));
    	SelenideElement accCaptcha 	= $(By.id("mainForm:accountCreationCaptcha:captcha"));
    	
    	
    	System.setProperty("webdriver.chrome.driver", Setuper.chromeDriverBinary);
    	Configuration.browser = "Chrome";
    	Configuration.browserBinary = Setuper.chromeBinary;
    	
    	open("https://unionregistry.ec.europa.eu/euregistry/BG/index.xhtml");
    	Selenide.clickSel(linkKpp);
    	footer.waitUntil(visible, 5000);
    	SelenideElement se = footer.shouldHave(text("version 8.0.8-WLS12"));
    	System.out.println(se.exists());
    	Selenide.clickSel(accRequest);
    	Selenide.typeSel(accName, "Klover");
    	Selenide.typeSel(accCaptcha, "6754");
    	screenshot(ax.getScreenshotName());
	}
	
	public static boolean clickSel(SelenideElement se){
		if(!(se.waitUntil(exist, 5000)!=null) && (se.waitUntil(enabled, 5000)!=null)){
			System.out.println("Cannot find element to click: " + se.toString());
			return false;
		} 
		try {
			se.click();
			return true;
		}catch (Exception e) {
			System.out.println("Cannot click element: " + se.toString());
			return false;
		}
	}
	public static boolean typeSel(SelenideElement se, String text){
		if(!(se.waitUntil(exist, 5000)!=null) && (se.waitUntil(enabled, 5000)!=null)){
			System.out.println("Cannot find element to type: " + se.toString());
			return false;
		} 
		try {
			se.setValue(text);
			return true;
		}catch (Exception e) {
			System.out.println("Cannot type into element: " + se.toString());
			return false;
		}
	}
}
