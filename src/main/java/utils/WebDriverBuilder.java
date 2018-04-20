package utils;

import java.io.File;
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

public class WebDriverBuilder {
	
    public static final String chromeBinary 			= "c:\\PGM\\chrome\\ChromiumPortable\\ChromiumPortable.exe";
    public static final String chromeDriverBinary 		= "c:\\PGM\\webdrivers\\chromedriver.exe";
    public static final String firefoxDriverBinary 		= "c:\\PGM\\webdrivers\\geckodriver.exe";	
    
	public static WebDriver firefoxDriver(){
		System.setProperty("webdriver.gecko.driver", "C://PGM//webdrivers//geckodriver.exe");
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "false");
//				DesiredCapabilities capabilities = DesiredCapabilities.firefox();//
//				capabilities.setCapability("marionette", true);
		FirefoxProfile profile 			= new FirefoxProfile();
		FirefoxOptions options = new FirefoxOptions();
		profile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
//				options.setBinary("C:\\PGM\\ffox\\f56\\FirefoxPortable\\FirefoxPortable.exe");
//				options.setBinary("C:\\PGM\\ffox\\fx50\\FirefoxPortable.exe");
		options.setBinary("C:\\PGM\\ffox\\FirefoxPortable_NO_POPUP\\FirefoxPortable\\Firefox.exe");
//				FirefoxBinary binary = new FirefoxBinary(new File("C:\\PGM\\ffox\\f56\\FirefoxPortable\\FirefoxPortable.exe"));
		options.setProfile(profile);
		WebDriver driver = Setuper.driver = new FirefoxDriver(options);
		return driver;
	} 
	public static WebDriver chromeDriver(){
		Map<String, Object> prefs 	= new HashMap<String, Object>(); //XX
//				prefs.put("profile.default_content_settings.popups", 0); //XX
//				prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 ); //XX
//				prefs.put("profile.default_content_setting_values.automatic_downloads",1);
		prefs.put("download.prompt_for_download", false); //XX
//				prefs.put("download.directory_upgrade", true); //XX
//				prefs.put("pdfjs.disabled", true);
		ChromeOptions chromeOptions 		= new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
//				chromeOptions.addArguments("--always-authorize-plugins=true");
		chromeOptions.addArguments("--ignore-certificate-errors=true");
		chromeOptions.addArguments("--test-type");
		chromeOptions.setBinary(new File(chromeBinary));
		System.setProperty("webdriver.chrome.driver", chromeDriverBinary); // chromedriver23.exe
		WebDriver driver = Setuper.driver = new ChromeDriver(chromeOptions);
		return driver;
	} 
	public static WebDriver ieDriver(){
		File file = new File(ProjectDataVariables.WEBDRIVERS_DIRECTORY + "IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); 
		caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
		WebDriver driver = Setuper.driver = new InternetExplorerDriver(caps);
		return driver;
	} 
}
