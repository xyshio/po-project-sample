package clima.start;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.output.TeeOutputStream;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;

import eu.ets.eucr.utils.DateOperations;
import eu.ets.eucr.utils.SeleniumWebUtils;
import eu.ets.eucr.utils.TestUtils;
import eu.ets.eucr.utils.ToolFunctions;
import utils.ax;
import po.SeleniumDriver;


public class Setuper {
	
	/* TODO AND RULES to consider before start testing:
	 * - Have TRADING account with lot of GA and CER units (best if valid ones)
	 * - If NA propose account creation, then approval as second NA is bypassed - setting in R&P Matrix
	 * - Auction delivery Account Identifiers: TEST: | DEV| - see projected 
	 * 
	 * 
	 * test
	 */
	public static final String downloadAttachementPath = ProjectDataVariables.OUTPUT_PDF_DIRECTORY;
	public static ExtentReports extent;
    public static String reportOutDirPath 				= ProjectDataVariables.OUTPUT_RUN_DIRECTORY + "EXTENT_REPORT\\";
    public static String reportFileOutPath 				= reportOutDirPath + "test-output\\STMExtentReport.html";

    public static final String chromeBinary 			= "c:\\PGM\\chrome\\ChromiumPortable\\ChromiumPortable.exe";
    public static final String chromeDriverBinary 		= "c:\\PGM\\webdrivers\\chromedriver.exe";
    public static final String firefoxDriverBinary 		= "c:\\PGM\\webdrivers\\geckodriver.exe";
    
    private static DesiredCapabilities cap = null;
    
	public static final String fileLocationWorkingSaveConfiguration 		= ax.getProp("DIR_TMP_LOCATION") + "out\\EUCR_SAVE_ACCOUNT_IDENTIFIERS2TEST.properties";
	
	public static final ArrayList<String> USER_PERS_NA1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("NA1"),		ax.GET_USER_TEL("NA1"),		ax.GET_USER_MAIL("NA1"))); 
	public static final ArrayList<String> USER_PERS_NA2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("NA2"),		ax.GET_USER_TEL("NA2"),		ax.GET_USER_MAIL("NA2"))); 
	public static final ArrayList<String> USER_PERS_AR1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("AR1"),		ax.GET_USER_TEL("AR1"),		ax.GET_USER_MAIL("AR1")));
	public static final ArrayList<String> USER_PERS_AR2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("AR2"),		ax.GET_USER_TEL("AR2"),		ax.GET_USER_MAIL("AR2"))); 
	public static final ArrayList<String> USER_PERS_AAR1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("AAR1"),		ax.GET_USER_TEL("AAR1"),	ax.GET_USER_MAIL("AAR1")));
	public static final ArrayList<String> USER_PERS_AAR2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("AAR2"),		ax.GET_USER_TEL("AAR2"),	ax.GET_USER_MAIL("AAR2"))); 
	public static final ArrayList<String> USER_PERS_CA1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("CA1"),		ax.GET_USER_TEL("CA1"),		ax.GET_USER_MAIL("CA1")));
	public static final ArrayList<String> USER_PERS_CA2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("CA2"),		ax.GET_USER_TEL("CA2"),		ax.GET_USER_MAIL("CA2")));
	public static final ArrayList<String> USER_PERS_CA3 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("CA3"),		ax.GET_USER_TEL("CA3"),		ax.GET_USER_MAIL("CA3")));
	public static final ArrayList<String> USER_PERS_VER = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("VER1"),		ax.GET_USER_TEL("VER1"),	ax.GET_USER_MAIL("VER1")));
	public static final ArrayList<String> USER_PERS_USR = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("USR"),		ax.GET_USER_TEL("USR"),		ax.GET_USER_MAIL("USR")));
	
	public static final ArrayList<String> USER_ESD_CA1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDCA1"),		ax.GET_USER_TEL("ESDCA1"),	ax.GET_USER_MAIL("ESDCA1")));
	public static final ArrayList<String> USER_ESD_CA2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDCA2"),		ax.GET_USER_TEL("ESDCA2"),	ax.GET_USER_MAIL("ESDCA2")));
	public static final ArrayList<String> USER_ESD_AR1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDAR1"),		ax.GET_USER_TEL("ESDAR1"),	ax.GET_USER_MAIL("ESDAR1")));
	public static final ArrayList<String> USER_ESD_AR2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDAR2"),		ax.GET_USER_TEL("ESDAR2"),	ax.GET_USER_MAIL("ESDAR2")));
	public static final ArrayList<String> USER_ESD_AAR1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDAAR1"),	ax.GET_USER_TEL("ESDAAR1"),	ax.GET_USER_MAIL("ESDAAR1")));
	public static final ArrayList<String> USER_ESD_AAR2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDAAR2"),	ax.GET_USER_TEL("ESDAAR2"),	ax.GET_USER_MAIL("ESDAAR2")));
	public static final ArrayList<String> USER_ESD_SD1 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDSD1"),		ax.GET_USER_TEL("ESDSD1"),	ax.GET_USER_MAIL("ESDSD1")));
	public static final ArrayList<String> USER_ESD_SD2 = 	new ArrayList<String>(Arrays.asList(ax.GET_USER_NAME("ESDSD2"),		ax.GET_USER_TEL("ESDSD2"),	ax.GET_USER_MAIL("ESDSD2")));
	
	public static String startTimeOfTest;
	public static String endTimeOfTest;
	
	// this param is against problem with Session Expired, f.ex: 3 hours
	//# Absolute session timeout
	//systemSettings.absolute-session-timeout-in-minutes	= 600 (=10 hours), we define our own for 8 hours 
	public static final int HoursToSafelyRestartBrowsers = 8; 
	
	public static final String USR_NA1_NAME 	= USER_PERS_NA1.get(0);
	public static final String USR_NA1_TEL 		= USER_PERS_NA1.get(1);
	public static final String USR_NA1_MAIL 	= USER_PERS_NA1.get(2);
	public static final String USR_NA2_NAME 	= USER_PERS_NA2.get(0);
	public static final String USR_NA2_TEL 		= USER_PERS_NA2.get(1);
	public static final String USR_NA2_MAIL 	= USER_PERS_NA2.get(2);
	
	
	public static final ArrayList<ArrayList<String>> USERS_PERSONAS = new ArrayList<ArrayList<String>>(Arrays.asList(
			USER_PERS_NA1, 	USER_PERS_NA2,	USER_PERS_CA1,	USER_PERS_CA2,	USER_PERS_CA3, 
			USER_PERS_AR1,	USER_PERS_AR2,	USER_PERS_AAR1,	USER_PERS_AAR2,
			USER_PERS_VER,	USER_PERS_USR, 
			USER_ESD_CA1, USER_ESD_CA2, USER_ESD_AR1, USER_ESD_AR2, USER_ESD_AAR1, USER_ESD_AAR2, USER_ESD_SD1, USER_ESD_SD2
			));
	
	public static final String PROXY_HOST = "147.67.117.13";
	public static final String PROXY_PORT = "8012";
	public static final String PROXY_USER = "gumulkr";
	public static final String PROXY_PASS = "Welcome4444";
	
    public static WebDriver driver 		= null; // NA1, CA1, USR
    public static WebDriver driver2 	= null; // NA2, CA2
    public static WebDriver driver3 	= null; // AR1, AAR1
    public static WebDriver driver4 	= null; // AR2
    
    public static String Driver1String;
    public static String Driver2String;
    public static String Driver3String;    
    public static String Driver4String;    
    public static String BrowserRunTimeName1;
    public static String BrowserRunTimeName2;
    public static String BrowserRunTimeName3;
    public static String BrowserRunTimeName4;
	private static WebDriver setDriver(WebDriver driver) {
		return driver;
	}	
	public static String TestTimeTaken(){
				 return DateOperations.TotalTimeTestCalculator(
						 	Setuper.startTimeOfTest.toString(), 
						 	Setuper.endTimeOfTest.toString()
						 );
	}
	public static String GET_USER_PERSONA_EMAIL_BY_USERNAME(String USERNAME){
		for (ArrayList<String> USER_PERSONA : USERS_PERSONAS) {
			if(USER_PERSONA.get(0).equals(USERNAME)){
				return USER_PERSONA.get(2);
			}
		}
		return null;
	}
	public static String GET_USER_PERSONA_PHONE_BY_USERNAME(String USERNAME){
		for (ArrayList<String> USER_PERSONA : USERS_PERSONAS) {
			if(USER_PERSONA.get(0).equals(USERNAME)){
				return USER_PERSONA.get(1);
			}
		}
		return null;
	}
	public static String GET_USER_PERSONA_USERNAME_BY_EMAIL(String EMAIL){
		for (ArrayList<String> USER_PERSONA : USERS_PERSONAS) {
			if(USER_PERSONA.get(2).equals(EMAIL)){
				return USER_PERSONA.get(0);
			}
		}
		return null;
	}
	public static WebDriver INITIATE_BROWSERS(ArrayList<String> Browsers){
		if(Browsers.size()==1){ // SAME BROWSER FOR ALL DRIVERS
			INITIATE_BROWSERS(Browsers.get(0), Browsers.get(0), Browsers.get(0), Browsers.get(0));
		}else if(Browsers.size()==4){
			INITIATE_BROWSERS(Browsers.get(0), Browsers.get(1), Browsers.get(2), Browsers.get(3));
		}else{
			INITIATE_BROWSERS(SeleniumDriver.getCurrentBrowserType(), SeleniumDriver.getCurrentBrowserType(2), SeleniumDriver.getCurrentBrowserType(3), SeleniumDriver.getCurrentBrowserType(4));	
		}
		return Setuper.driver;
	}
	public static WebDriver INITIATE_BROWSERS(){
		return INITIATE_BROWSERS(SeleniumDriver.getCurrentBrowserType(), SeleniumDriver.getCurrentBrowserType(2), SeleniumDriver.getCurrentBrowserType(3), SeleniumDriver.getCurrentBrowserType(4));
	}
	public static WebDriver INITIATE_BROWSERS(String Browser1){
		driver = InitiateBrowser(Browser1);
		Setuper.driver = driver;
		Setuper.BrowserRunTimeName1 = Browser1;
		return driver;
	}
	public static WebDriver INITIATE_BROWSERS(String Browser1, boolean ForAll){ 
		// if true: initiate 4 browsers, else only one/first instance of browser 
		return (ForAll)?INITIATE_BROWSERS(Browser1, Browser1, Browser1, Browser1):INITIATE_BROWSERS(Browser1);
	}
	public static WebDriver INITIATE_BROWSERS(String Browser1, String Browser2, String Browser3, String Browser4){
		setBrowsersNames(Browser1, Browser2, Browser3, Browser4);
    	ArrayList<WebDriver> DRIVERS = new ArrayList<WebDriver>(); 
//		######################################################################################################################
//    	DRIVERS = Setuper.SETUP_DRIVERS("FIREFOX","FIREFOX","FIREFOX"); // , "MSIE", "FIREFOX_OLD"
    	DRIVERS = Setuper.SETUP_4_DRIVERS(Browser1,Browser2,Browser3,Browser4); // , "MSIE", "FIREFOX_OLD"
//		######################################################################################################################
//    	DRIVERS = Setuper.SETUP_DRIVERS(Browser1,"",""); // , "MSIE", "FIREFOX_OLD"
    	
    	driver 	= DRIVERS.get(0);
    	Setuper.BrowserRunTimeName1 = Browser1;
    	driver2 = DRIVERS.get(1);
    	Setuper.BrowserRunTimeName2 = Browser2;
    	driver3 = DRIVERS.get(2);
    	Setuper.BrowserRunTimeName3 = Browser3;
    	driver4 = DRIVERS.get(3);
    	Setuper.BrowserRunTimeName4 = Browser4;
    	Setuper.SETUP_RUN_DRIVERS_WINDOW(0,200,400,600); //    	SETUP_DOUBLE_DRIVER_WINDOW(driver, driver2);
    	return driver;
	}
	
	public static void setBrowsersNames(String b1, String b2, String b3, String b4){
		BrowserRunTimeName1 = b1;
		BrowserRunTimeName2 = b2;
		BrowserRunTimeName3 = b3;
		BrowserRunTimeName4 = b4;
	}
	
	
	public static String getBrowserName(){
		String b1 = Setuper.BrowserRunTimeName1;
		String b2 = Setuper.BrowserRunTimeName2;
		String b3 = Setuper.BrowserRunTimeName3;
		String b4 = Setuper.BrowserRunTimeName4;
		if(b1.equals(b2) && b2.equals(b3) && b3.equals(b4)){
			return b1;	
		}else{
			return b1 + "," + b2 + "," +b3 + "," +b4;
		}
	}
	
	
	public static void CONSOLE_OUT(){ // throws IOException, InterruptedException
		try {
		    FileOutputStream fos 	= new FileOutputStream(new File(ProjectDataVariables.OUTPUT_CONSOLE_LOGGING_FILE));
		    FileOutputStream props 	= new FileOutputStream(new File(ProjectDataVariables.LOCATION_TMP_DATA_FILE));
		    TeeOutputStream myOut	= new TeeOutputStream(System.out, fos);
		    PrintStream ps 			= new PrintStream(myOut);
		    System.setOut(ps);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}	
	
	public static void CREATE_DIRECTORY(String dirlocation){
		File file = new File(dirlocation);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created! " + dirlocation);
            } else {
                System.out.println("Failed to create directory!" + dirlocation);
            }
        }
	}
	
	public static String RUN_DATA_GETTER(String info1, String info2){
		
		if(info1.contains("TEST_RUN_DATE")){ 				info2 =DateOperations.GET_TODATE_AS_AIRLINE_STANDARD(); }
		else if(info1.contains("EUCR_VERSION")){ 			info2 =ProjectDataVariables.EUCR_VERSION_NR; } 
		else if(info1.contains("EUTL_VERSION")){ 			info2 =ax.READ_CONFIG_FILE("APP_VERSION_EUTL");  }
		else if(info1.contains("EUTL_PUBLIC_VERSION")){ 	info2 =ax.READ_CONFIG_FILE("APP_VERSION_EUTL_PUBL"); }
		else if(info1.contains("TEST_ENVRMENT")){			info2 =ax.READ_CONFIG_FILE("TEST_ENVRMENT"); }
		else if(info1.contains("TEST_MODE")){				info2 =ax.READ_CONFIG_FILE("TEST_MODE"); }
		else if(info1.contains("SIGNATURE_ENABLED")){		info2 =ax.READ_CONFIG_FILE("SIGNATURE_ENABLED"); }
		else if(info1.contains("TEST_BROWSER")){			//info2 = Setuper.getBrowserName();
			info2 = SeleniumDriver.getCurrentBrowserType();
		}	
		
		return info2;
	}
	
	
	public static void TESTNG_FRAME_SETUP_N_START(){
		////////////////////////////////////////////////////////
			List<String> suitesList 			= new ArrayList<String>();
			TestListenerAdapter listener 		= new TestListenerAdapter();
			TestNG testng 						= new TestNG();
			
			String DIRECTORY_FOR_TEST_NG_OUT = ProjectDataVariables.TESTNG_REPORT_OUT_DIR; 
			testng.setOutputDirectory(DIRECTORY_FOR_TEST_NG_OUT);
			CREATE_DIRECTORY(DIRECTORY_FOR_TEST_NG_OUT);
			
			
			suitesList.add(ProjectDataVariables.TESTNG_START_XML_PATH);
			testng.setTestSuites(suitesList);
			testng.addListener(listener);
			testng.run();
	}	
	
	
	public static void TEST_USER_WARMING_UP(WebDriver driver){
//    	ax.excel_h2("ETS_USERS");
//		if(ax.LOGIN_NA1(driver)!=null){ax.excel_pass("NA1");}else{ax.excel_fail("NA1");}
//		if(ax.LOGIN_CA1(driver, "EU", 1)!=null){ax.excel_pass("CA1 EU");}else{ax.excel_fail("CA1 EU");}
//		if(ax.LOGIN_AR1(driver, "BG", 3)!=null){ax.excel_pass("AR1 BG");}else{ax.excel_fail("AR1 BG");}
//		if(ax.LOGIN_CA3(driver, "BG", 3)!=null){ax.excel_pass("CA1 EU");}else{ax.excel_fail("CA1 EU");}
//		if(ax.LOGIN_NA2(driver, "BG", 2)!=null){ax.excel_pass("NA2 BG");}else{ax.excel_fail("NA2 BG");}
//		if(ax.LOGIN_AR1(driver, "BG", 1)!=null){ax.excel_pass("AR1 BG");}else{ax.excel_fail("AR1 BG");}
//		if(ax.LOGIN_NA1(driver, "EU", 1)!=null){ax.excel_pass("NA1 EU");}else{ax.excel_fail("NA1 EU");}
//		if(ax.LOGIN_CA1(driver, "EU", 4)!=null){ax.excel_pass("CA1 EU");}else{ax.excel_fail("CA1 EU");}
//		if(ax.LOGIN_CA1(driver, "BG", 4)!=null){ax.excel_pass("CA1 BG");}else{ax.excel_fail("CA1 BG");}
//		if(ax.LOGIN_NA1(driver, "BG", 1)!=null){ax.excel_pass("NA1 BG");}else{ax.excel_fail("NA1 BG");}
//		if(ax.LOGIN_AR1(driver, "BG", 1)!=null){ax.excel_pass("AR1 BG");}else{ax.excel_fail("AR1 BG");}
    	ax.excel_h2("ESD_USERS");
    	if(ax.LOGIN_ESD_CA1(driver)!=null){		ax.pass("CA1 ED");}else{	ax.fail("CA1 ED");}
    	if(ax.LOGIN_ESD_CA2(driver)!=null){		ax.pass("CA2 ED");}else{	ax.fail("CA2 ED");}
    	if(ax.LOGIN_ESD_AR1(driver)!=null){		ax.pass("AR1 ED");}else{	ax.fail("AR1 ED");}
    	if(ax.LOGIN_ESD_AAR1(driver)!=null){	ax.pass("AAR1 ED");}else{	ax.fail("AAR1 ED");}
    	if(ax.LOGIN_ESD_CA1(driver)!=null){		ax.pass("CA1 ED");}else{	ax.fail("CA1 ED");}
    	if(ax.LOGIN_ESD_CA2(driver)!=null){		ax.pass("CA2 ED");}else{	ax.fail("CA2 ED");}
    	if(ax.LOGIN_ESD_AR1(driver)!=null){		ax.pass("AR1 ED");}else{	ax.fail("AR1 ED");}
		System.out.println("");
	}
	
	
	public static void Setup3BrowserSet(String Browser){
		ArrayList<WebDriver> DRIVERS = Setuper.SETUP_3_DRIVERS(Browser,Browser,Browser);
    	driver = DRIVERS.get(0);driver2 = DRIVERS.get(1);driver3 = DRIVERS.get(2);
    	Setuper.SETUP_RUN_DRIVERS_WINDOW(900,900,900);
	}
	

	
	public static ArrayList<WebDriver> SETUP_3_DRIVERS(String Browser1, String Browser2, String Browser3){
    	if(!Browser1.isEmpty()){   		
    		driver 		= Setuper.InitiateBrowser(Browser1);   	
    		Driver1String = driver.toString();
    	}
    	if(!Browser2.isEmpty()){   		
    		driver2		= Setuper.InitiateBrowser(Browser2);   	
    		Driver2String = driver2.toString();	
    	}
    	if(!Browser3.isEmpty()){   		
    		driver3		= Setuper.InitiateBrowser(Browser3);   	
    		Driver3String = driver3.toString();
    	}
    	
    	if(!Browser1.isEmpty() &&  Browser2.isEmpty() &&  Browser3.isEmpty()){
    		return new ArrayList<WebDriver>(Arrays.asList(driver, null, null));     				}
    	else if(!Browser1.isEmpty() &&  !Browser2.isEmpty() &&  Browser3.isEmpty()){
    		return new ArrayList<WebDriver>(Arrays.asList(driver, driver2, null));     		}
    	else if(!Browser1.isEmpty() &&  !Browser2.isEmpty() &&  !Browser3.isEmpty()){
    		return new ArrayList<WebDriver>(Arrays.asList(driver, driver2, driver3));   }
    	else{
    		return null;}
	}	
	
	
	public static ArrayList<WebDriver> SETUP_4_DRIVERS(){
		
		return SETUP_4_DRIVERS(
				SeleniumDriver.getCurrentBrowserType(), 
				SeleniumDriver.getCurrentBrowserType(2),
				SeleniumDriver.getCurrentBrowserType(3),
				SeleniumDriver.getCurrentBrowserType(4)
				);
	}

	public static ArrayList<WebDriver> SETUP_4_DRIVERS(String Browser1, String Browser2, String Browser3, String Browser4){
    	if(!Browser1.isEmpty()){   		
    		driver 		= Setuper.InitiateBrowser(Browser1.toUpperCase());   	
    		Driver1String = driver.toString();
    	}
    	if(!Browser2.isEmpty()){   		
    		driver2		= Setuper.InitiateBrowser(Browser2.toUpperCase());   	
    		Driver2String = driver2.toString();	
    	}
    	if(!Browser3.isEmpty()){   		
    		driver3		= Setuper.InitiateBrowser(Browser3.toUpperCase());   	
    		Driver3String = driver3.toString();
    	}
    	if(!Browser4.isEmpty()){   		
    		driver4		= Setuper.InitiateBrowser(Browser4.toUpperCase());   	
    		Driver4String = driver4.toString();
    	}
    	SETUP_RUN_DRIVERS_WINDOW();
    	
    	if(!Browser1.isEmpty() &&  Browser2.isEmpty() &&  Browser3.isEmpty()){
    		return new ArrayList<WebDriver>(Arrays.asList(driver, null, null, null));     				}
    	else if(!Browser1.isEmpty() &&  !Browser2.isEmpty() &&  Browser3.isEmpty()){
    		return new ArrayList<WebDriver>(Arrays.asList(driver, driver2, null, null));     		}
    	else if(!Browser1.isEmpty() &&  !Browser2.isEmpty() &&  !Browser3.isEmpty() && !Browser4.isEmpty()){
    		return new ArrayList<WebDriver>(Arrays.asList(driver, driver2, driver3, driver4));   }
    	else if(!Browser1.isEmpty() &&  !Browser2.isEmpty() &&  !Browser3.isEmpty()  &&  !Browser4.isEmpty()){
    		return new ArrayList<WebDriver>(Arrays.asList(driver, driver2, driver3, driver4));   }
    	else{
    		return null;}
	}
	
	public static int GET_BROWSER_ID_BY_DRIVER(WebDriver driver){
		if(driver.toString().equals(Setuper.Driver1String)){return 1;}
		else if(driver.toString().equals(Setuper.Driver2String)){return 2;}
		else if(driver.toString().equals(Setuper.Driver3String)){return 3;}
		else if(driver.toString().equals(Setuper.Driver4String)){return 4;}
		return -1;
	}
	
	
	public static WebDriver getDriverByDriverInstance(int driverInstance){
		switch (driverInstance) {
		case 1: return Setuper.driver;
		case 2: return Setuper.driver2;
		case 3: return Setuper.driver3;
		case 4: return Setuper.driver4;
		}
		return null;
	}
	
	public static WebDriver GET_DRIVER_BY_FX(String FX){
		if(Setuper.driver2==null && Setuper.driver3==null) return Setuper.driver;
		
		switch (FX) {
		case "1"	:
			return Setuper.driver;
		case "2"	:
			return Setuper.driver2;
		case "3"	:
			return Setuper.driver3;
		case "4"	:
			return Setuper.driver4;
		case "NA"	: 
		case "NA1"	: 
		case "ESDCA1":
			return Setuper.driver;
		case "NA2"	: 
		case "ESDCA2":
			return Setuper.driver2;
		case "CA"	: 
		case "CA1"	: 
			return Setuper.driver;
		case "CA2"	: 
			return Setuper.driver2;
		case "CA3"	: 
			return Setuper.driver3;
		case "AR"	: 
		case "AR1"	: 
		case "ESDAR1":
			return Setuper.driver3;
		case "AR2"	: 
		case "ESDAR2":
			return Setuper.driver4;
		case "AAR"	: 
		case "AAR1"	: 
		case "ESDAAR1":
			return Setuper.driver4;
		case "AAR2"	: 
		case "ESDAAR2":
			return Setuper.driver4;
		case "USR"	: 
			return Setuper.driver;
		case "VER"	: 
		case "VER1"	: 
			return Setuper.driver;
			
		case "EUTL"	: return Setuper.driver4;

		default:
			return Setuper.driver;
		}
	}
	
	public static void InitiateTestRunActions(){
//		 this will be done before whole test set run
//		 1. DELETING SWAP DATA PROP FILES
//		ToolFunctions.PurgeDirectory(new File(ProjectDataVariables.OUTPUT_PROP_FILE_DATA_ACCMGMT));
//		####################################################################
		TestUtils.DrawLoger_START_OF_TEST_RUN(ProjectDataVariables.startTestSetTime);
	}
	
	public static WebDriver Init(boolean initializeMany){ // initiating driver old-way (non-page-object)
		if(SeleniumDriver.getCurrentBrowserType().equals("CHROME")){
			return InitializeChrome(initializeMany);
		}else if(SeleniumDriver.getCurrentBrowserType().equals("FIREFOX")){
			return InitializeFirefox(initializeMany);
		}else if(SeleniumDriver.getCurrentBrowserType().equals("MSIE")){
			return InitializeInternetExplorer(initializeMany);
		}
		return null;
	}
	
//	public static ExtentReports Initer(){
//		extent = new ExtentReports(reportFileOutPath, true);
//		Setuper.CONSOLE_OUT();
//		extent	.addSystemInfo("Browser Type"				, SeleniumDriver.getCurrentBrowserType())
//				.addSystemInfo("Application Environment"	, ProjectDataVariables.EUCR_ENV)
//				.addSystemInfo("Application Version"		, ProjectDataVariables.EUCR_VERSION_NR)
//				.addSystemInfo("Date of running"			, DateOperations.GET_CURR_TIMESTAMP_FORMAT(""))
//				.addSystemInfo("Automator"					, "Gumulak Krzysztof");
//		extent.loadConfig(new File(System.getProperty("usr.dir") + "\\extent-config.xml"));
//		return extent;
//	}

	public static WebDriver InitializeFirefoxOld(boolean initializeMany){
//		
//		System.setProperty("webdriver.gecko.driver", "C://PGM//webdrivers//geckodriver.exe");
//		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//		capabilities.setCapability("marionette", true);
//		
//		if(initializeMany){
//			Setuper.driver = new FirefoxDriver(capabilities);
//			Setuper.driver2 = new FirefoxDriver(capabilities);
//			Setuper.driver3 = new FirefoxDriver(capabilities);
//			Setuper.driver4 = new FirefoxDriver(capabilities);
//		}else{
//			Setuper.driver = new FirefoxDriver(capabilities);
//		}
//		SETUP_RUN_DRIVERS_WINDOW();
//		
//		return Setuper.driver;
		
		
//		System.setProperty("webdriver.gecko.driver", "C://PGM//webdrivers//geckodriver.exe");
		System.setProperty("webdriver.gecko.driver", "C:\\PGM\\webdrivers\\geckodriver.exe");
//		driver = new FirefoxDriver();
		FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));
		Proxy proxy = new Proxy();
			proxy.setProxyType(Proxy.ProxyType.MANUAL);
			proxy.setHttpProxy(PROXY_HOST);
			proxy.setSocksUsername(PROXY_USER);
			proxy.setSocksPassword(PROXY_PASS);
			 
//		 proxy.setProxyType(Proxy.ProxyType.PAC);
//		 	proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
			
		 FirefoxProfile myprofile 			= new FirefoxProfile();
		 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
			myprofile.setPreference("browser.download.folderList", 2);
			myprofile.setPreference("browser.download.manager.showWhenStarting", false);
			myprofile.setPreference("browser.download.dir", downloadAttachementPath);
			myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
			myprofile.setPreference("pdfjs.disabled", true);
			myprofile.setPreference("plugin.scan.Acrobat", "99.0");
			myprofile.setPreference("plugin.scan.plid.all", false);			
		 
		DesiredCapabilities capabilities 	= new DesiredCapabilities();	
			capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
		 
//		driver = new FirefoxDriver(binary, myprofile, capabilities);
		driver = new FirefoxDriver();
		
		
		WebDriver driver = new FirefoxDriver();
		return driver;
	}	
	
	public static WebDriver getFirefoxDriver(){ //  throws MalformedURLException
		WebDriver driver = null;
		System.setProperty("webdriver.gecko.driver", firefoxDriverBinary);
		cap = DesiredCapabilities.firefox();
		cap.setCapability("marionette", false);
		driver = new FirefoxDriver(cap);
		ax.info("Starting Firefo Driver ["+driver.toString()+"]"  );
		return driver;
	}
	
	
	public static WebDriver InitFirefoxOld(){
		System.setProperty("webdriver.gecko.driver", "C://PGM//webdrivers//geckodriver.exe");
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "false");
//		DesiredCapabilities capabilities = DesiredCapabilities.firefox();//
//		capabilities.setCapability("marionette", true);
		
		FirefoxProfile profile 			= new FirefoxProfile();
		
		FirefoxOptions options = new FirefoxOptions();
		profile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
//		options.setBinary("C:\\PGM\\ffox\\f56\\FirefoxPortable\\FirefoxPortable.exe");
//		options.setBinary("C:\\PGM\\ffox\\fx50\\FirefoxPortable.exe");
		options.setBinary("C:\\PGM\\ffox\\FirefoxPortable_NO_POPUP\\FirefoxPortable\\Firefox.exe");
		
//		FirefoxBinary binary = new FirefoxBinary(new File("C:\\PGM\\ffox\\f56\\FirefoxPortable\\FirefoxPortable.exe"));
		
		options.setProfile(profile);
		return new FirefoxDriver(options);

	}
	
	
	public static WebDriver InitializeFirefox(boolean initializeMany){

		if(initializeMany){
			Setuper.driver4 = InitFirefoxOld();
			Setuper.driver3 = InitFirefoxOld();
			Setuper.driver2 = InitFirefoxOld();
			Setuper.driver = InitFirefoxOld();
			
		}else{
		
			Setuper.driver = InitFirefoxOld();
		}
		SETUP_RUN_DRIVERS_WINDOW();
		
		return Setuper.driver;
		
		
//		FirefoxOptions options = new FirefoxOptions();
//		options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"); //This is the location where you have installed Firefox on your machine
// 
//		FirefoxDriver driver = new FirefoxDriver(options);
//		ax.getDriver(driver, "http://www.google.com");
		
		
		
//		System.setProperty("webdriver.gecko.driver", "C://PGM//webdrivers//geckodriver.exe");
//		System.setProperty("webdriver.gecko.driver", "C:\\PGM\\webdrivers\\geckodriver.exe");
//		driver = new FirefoxDriver();
//		FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));
//		Proxy proxy = new Proxy();
//			proxy.setProxyType(Proxy.ProxyType.MANUAL);
//			proxy.setHttpProxy(PROXY_HOST);
//			proxy.setSocksUsername(PROXY_USER);
//			proxy.setSocksPassword(PROXY_PASS);
//			 
////		 proxy.setProxyType(Proxy.ProxyType.PAC);
////		 	proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
//			
//		 FirefoxProfile myprofile 			= new FirefoxProfile();
//		 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
//			myprofile.setPreference("browser.download.folderList", 2);
//			myprofile.setPreference("browser.download.manager.showWhenStarting", false);
//			myprofile.setPreference("browser.download.dir", downloadAttachementPath);
//			myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
//			myprofile.setPreference("pdfjs.disabled", true);
//			myprofile.setPreference("plugin.scan.Acrobat", "99.0");
//			myprofile.setPreference("plugin.scan.plid.all", false);			
//		 
//		DesiredCapabilities capabilities 	= new DesiredCapabilities();	
//			capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
//			capabilities.setCapability(CapabilityType.PROXY, proxy);
//			capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
//		 
//		driver = new FirefoxDriver(binary, myprofile, capabilities);
//		
//		
//		WebDriver driver = new FirefoxDriver();
//		return driver;
	}
	
	
	public static WebDriver InitializeChromeOld(){
		
//		String binary 		= "C:\\PGM\\chrome-win32\\chrome.exe";
//		String binary 		= "c:\\PGM\\chrome\\Win_317790_chrome-win32\\chrome-win32\\chrome.exe";
//		String webDriver 	= "c:\\PGM\\webdrivers\\chromedriver2.14.exe";
//		String webDriver 	= "c:\\PGM\\webdrivers\\chromedriver2.exe";
		
		
//		ChromeDriverService src 			= new ChromeDriverService.Builder().usingDriverExecutable(new File(webDriver)).usingAnyFreePort().build();
		ChromeOptions options 				=  new ChromeOptions();
		Map<String, Object> chromeOptions 	= new HashMap<String, Object>();
		DesiredCapabilities capabilities 	= DesiredCapabilities.chrome();
		chromeOptions.put("binary", chromeBinary);
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
//		options.setBinary(binary);
//		options.
//		options.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
//		chromeOptions.put("binary", "C:\\PGM\\chrome-win32\\chrome.exe");
//		System.setProperty("webdriver.chrome.driver", "c:\\PGM\\webdrivers\\chromedriver2.14.exe");
		System.setProperty("webdriver.chrome.driver", chromeDriverBinary); // chromedriver23.exe
		WebDriver driver = new ChromeDriver(capabilities);
		Setuper.driver = driver;
		return driver;
	}
	
	
	public static WebDriver InitializeChrome(boolean initializeMany){
		
		Map<String, Object> prefs 	= new HashMap<String, Object>(); //XX
//		prefs.put("profile.default_content_settings.popups", 0); //XX
//		prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 ); //XX
//		prefs.put("profile.default_content_setting_values.automatic_downloads",1);
		prefs.put("download.prompt_for_download", false); //XX
//		prefs.put("download.directory_upgrade", true); //XX
//		prefs.put("pdfjs.disabled", true);
		
		ChromeOptions options 		= new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
//		options.addArguments("--always-authorize-plugins=true");
		options.addArguments("--ignore-certificate-errors=true");
		options.addArguments("--test-type");
		
		options.setBinary(new File(chromeBinary));
		System.setProperty("webdriver.chrome.driver", chromeDriverBinary); // chromedriver23.exe
		
		if(initializeMany){
			
			Setuper.driver4 = new ChromeDriver(options);
			Setuper.driver3 = new ChromeDriver(options);
			Setuper.driver2 = new ChromeDriver(options);
			Setuper.driver = new ChromeDriver(options);
			
		}else{
			Setuper.driver = new ChromeDriver(options);
		}
		SETUP_RUN_DRIVERS_WINDOW();
		return Setuper.driver;		
		
	}
	
	public static WebDriver InitializeInternetExplorer(boolean InitializeMany){
		if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("IEDriverServer.exe");Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("iexplore.exe");}			
		
		File file = new File(ProjectDataVariables.WEBDRIVERS_DIRECTORY + "IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); 
		caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
		if(InitializeMany){
			Setuper.driver4 = new InternetExplorerDriver(caps);
			Setuper.driver3 = new InternetExplorerDriver(caps);
			Setuper.driver2 = new InternetExplorerDriver(caps);
			Setuper.driver = new InternetExplorerDriver(caps);
			
		}else{
			Setuper.driver = new InternetExplorerDriver(caps);
		}
		SETUP_RUN_DRIVERS_WINDOW();
		return Setuper.driver;	
	}
	
	
	@Test
	public static WebDriver InitiateBrowser(String browserType){
		browserType = browserType.toUpperCase(); 
		System.out.println(" + + + Starting New Session with Browser: "+browserType +" + + + ");
//		ArrayList<String> BRWSX = new ArrayList<String>(Arrays.asList("FIREFOX","CHROME","MSIE","FIREFOX_OLD","FIREFOX001","FIREFOX_P2","HTMLUNIT"));
//		if(!BRWSX.contains(browserType)){System.out.println("WRONG BROWSER TYPE DEFINED: "+browserType);}
		WebDriver driver = null;
		if (browserType.equals("FIREFOX_NOT_WORKING")){ // This version works for FF_PORT 4.5
			ax.debug("LOADING BROWSER PREFERENCES");
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				
					Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	
				}
			
			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));

			Proxy proxy = new Proxy();
			 proxy.setProxyType(Proxy.ProxyType.MANUAL);
			 DesiredCapabilities capabilities 	= new DesiredCapabilities();
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
				myprofile.setPreference("browser.download.folderList", 2);
				myprofile.setPreference("browser.download.manager.showWhenStarting", false);
				myprofile.setPreference("browser.download.dir", downloadAttachementPath);
				myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
				myprofile.setPreference("pdfjs.disabled", true);
				myprofile.setPreference("plugin.scan.Acrobat", "99.0");
				myprofile.setPreference("plugin.scan.plid.all", false);			
			 
				proxy.setHttpProxy(PROXY_HOST);
				proxy.setSocksUsername(PROXY_USER);
				proxy.setSocksPassword(PROXY_PASS);
				
				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
			 
//			driver = new FirefoxDriver(binary, myprofile, capabilities);
			driver = new FirefoxDriver();
		}
		else if(browserType.equals("FIREFOX")){ // - THIS IS WORKING
			ax.debug("LOADING BROWSER PREFERENCES");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				
				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	
				}
			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));
			System.setProperty("webdriver.gecko.driver", "C:\\PGM\\webdrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
//			Proxy proxy = new Proxy();
//				proxy.setProxyType(Proxy.ProxyType.MANUAL);
//				proxy.setHttpProxy(PROXY_HOST);
//				proxy.setSocksUsername(PROXY_USER);
//				proxy.setSocksPassword(PROXY_PASS);
//				 
////			 proxy.setProxyType(Proxy.ProxyType.PAC);
////			 	proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
//				
//			 FirefoxProfile myprofile 			= new FirefoxProfile();
//			 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
//				myprofile.setPreference("browser.download.folderList", 2);
//				myprofile.setPreference("browser.download.manager.showWhenStarting", false);
//				myprofile.setPreference("browser.download.dir", downloadAttachementPath);
//				myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
//				myprofile.setPreference("pdfjs.disabled", true);
//				myprofile.setPreference("plugin.scan.Acrobat", "99.0");
//				myprofile.setPreference("plugin.scan.plid.all", false);			
//			 
//			DesiredCapabilities capabilities 	= new DesiredCapabilities();	
//				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
//				capabilities.setCapability(CapabilityType.PROXY, proxy);
//				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
//			 
//			driver = new FirefoxDriver(binary, myprofile, capabilities);			
		}
		else if(browserType.equals("GECKO")){
			System.setProperty("webdriver.gecko.driver", "C:\\PGM\\webdrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			ax.driverGet(driver, "https://ets-registry-test2.tech.ec.europa.eu/euregistry/BG/index.xhtml");
		}
		else if(browserType.equals("FIREFOX_S3")){
			ax.debug("LOADING MARIONETTE BROWSER PREFERENCES - FIREFOX MARIONNETTE - SELENIM 3");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				
				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_S3_EXE_PROC_NAME);	
				}
			System.setProperty("webdriver.gecko.driver", "C:\\PGM\\webdrivers\\geckodriver.exe");
			FirefoxBinary binary 			= new FirefoxBinary(new File(ax.getProp("FIREFOX_S3_EXE_LOCATION")));
			Proxy proxy = new Proxy();
				 
			proxy.setProxyType(Proxy.ProxyType.MANUAL);
			proxy.setHttpProxy(PROXY_HOST);
			proxy.setSocksUsername(PROXY_USER);
			proxy.setSocksPassword(PROXY_PASS);
				 
//			 proxy.setProxyType(Proxy.ProxyType.PAC);
//			 	proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
			 
			 
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
				myprofile.setPreference("browser.download.folderList", 2);
				myprofile.setPreference("browser.download.manager.showWhenStarting", false);
				myprofile.setPreference("browser.download.dir", downloadAttachementPath);
				myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
				myprofile.setPreference("pdfjs.disabled", true);
				myprofile.setPreference("plugin.scan.Acrobat", "99.0");
				myprofile.setPreference("plugin.scan.plid.all", false);			
			 
			DesiredCapabilities capabilities 	= new DesiredCapabilities();	
				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
			 
//			driver = new FirefoxDriver(binary, myprofile, capabilities);
				driver = new FirefoxDriver();
		}		
		else if(browserType.equals("FIREFOX_48")){ // 
			ax.debug("LOADING BROWSER PREFERENCES: FIREFOX_48");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				
					Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	
				}
			
			FirefoxBinary binary 			= new FirefoxBinary(new File("C:\\Pgm\\ffox\\FirefoxPortable50\\FirefoxPortable.exe"));
			Proxy proxy = new Proxy();
				 
			proxy.setProxyType(Proxy.ProxyType.MANUAL);
			 proxy.setHttpProxy(PROXY_HOST);
			 proxy.setSocksUsername(PROXY_USER);
			 proxy.setSocksPassword(PROXY_PASS);
				 
//			proxy.setProxyType(Proxy.ProxyType.PAC);
//			 proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
			 
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
//				myprofile.setPreference("browser.download.folderList", 2);
//				myprofile.setPreference("browser.download.manager.showWhenStarting", false);
//				myprofile.setPreference("browser.download.dir", downloadAttachementPath);
//				myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
//				myprofile.setPreference("pdfjs.disabled", true);
//				myprofile.setPreference("plugin.scan.Acrobat", "99.0");
//				myprofile.setPreference("plugin.scan.plid.all", false);			
			 
			DesiredCapabilities capabilities 	= new DesiredCapabilities();	
				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
			 
//			driver = new FirefoxDriver(binary, myprofile, capabilities);	
				driver = new FirefoxDriver();
		}		
		else if(browserType.equals("FIREFOX_FX")){
			FirefoxBinary binary 			= new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"));
//			FirefoxBinary binary 			= new FirefoxBinary(new File("C:\\PGM\\ffox\\FirefoxPortable\\FirefoxPortable.exe"));

			ax.debug("LOADING BROWSER PREFERENCES");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}
			
			
			Proxy proxy = new Proxy();
				 proxy.setProxyType(Proxy.ProxyType.MANUAL);
				 proxy.setHttpProxy(PROXY_HOST);
				 proxy.setSocksUsername(PROXY_USER);
				 proxy.setSocksPassword(PROXY_PASS);
//			 proxy.setProxyType(Proxy.ProxyType.PAC);
//			 proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
				myprofile.setPreference("browser.download.folderList", 2);
				myprofile.setPreference("browser.download.manager.showWhenStarting", false);
				myprofile.setPreference("browser.download.dir", downloadAttachementPath);
				myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
				myprofile.setPreference("pdfjs.disabled", true);
				myprofile.setPreference("plugin.scan.Acrobat", "99.0");
				myprofile.setPreference("plugin.scan.plid.all", false);			
			 
			DesiredCapabilities capabilities 	= new DesiredCapabilities();	
				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
//			driver = new FirefoxDriver(binary, myprofile, capabilities);
				driver = new FirefoxDriver();
		}
		else if(browserType.equals("FIREFOX_P2")){
			System.out.println("LOADING BROWSER PREFERENCES FIREFOX_P2");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}
			FirefoxBinary binary2 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));

			Proxy proxy2 = new Proxy();
			 proxy2.setProxyType(Proxy.ProxyType.PAC);
			 DesiredCapabilities capabilities2 	= new DesiredCapabilities();
			 FirefoxProfile myprofile2 			= new FirefoxProfile();
			 	myprofile2.setPreference("capability.policy.strict.Window.alert",  "noAccess");
				myprofile2.setPreference("browser.download.folderList", 2);
				myprofile2.setPreference("browser.download.manager.showWhenStarting", false);
				myprofile2.setPreference("browser.download.dir", downloadAttachementPath);
				myprofile2.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
				myprofile2.setPreference("pdfjs.disabled", true);
				myprofile2.setPreference("plugin.scan.Acrobat", "99.0");
				myprofile2.setPreference("plugin.scan.plid.all", false);			
			 
				proxy2.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");	
				
				capabilities2.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities2.setCapability(CapabilityType.PROXY, proxy2);
				capabilities2.setCapability(FirefoxDriver.PROFILE, myprofile2);			 
			 
//			driver = new FirefoxDriver(binary2, myprofile2);
				driver = new FirefoxDriver();
//					(binary2, myprofile2, capabilities2);
		}
		else if(browserType.equals("FIREFOX_T1")){
			ax.debug("LOADING BROWSER PREFERENCES");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}
			
			FirefoxBinary binary 			= new FirefoxBinary(new File("C:\\PGM\\ffox\\FirefoxPortable46\\FirefoxPortable.exe"));

			Proxy proxy = new Proxy();
			 proxy.setProxyType(Proxy.ProxyType.MANUAL);
			 DesiredCapabilities capabilities 	= new DesiredCapabilities();
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
//				myprofile.setPreference("browser.download.folderList", 2);
//				myprofile.setPreference("browser.download.manager.showWhenStarting", false);
//				myprofile.setPreference("browser.download.dir", downloadAttachementPath);
//				myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
//				myprofile.setPreference("pdfjs.disabled", true);
//				myprofile.setPreference("plugin.scan.Acrobat", "99.0");
//				myprofile.setPreference("plugin.scan.plid.all", false);			
			 
				proxy.setHttpProxy(PROXY_HOST);
				proxy.setSocksUsername(PROXY_USER);
				proxy.setSocksPassword(PROXY_PASS);
				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
			 
//			driver = new FirefoxDriver(binary, myprofile, capabilities);
				driver = new FirefoxDriver();
		}
		else if(browserType.equals("FIREFOX_T2")){
			System.out.println("LOADING BROWSER PREFERENCES FIREFOX_P2");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}
			FirefoxBinary binary2 			= new FirefoxBinary(new File("C:\\temp\\firefox\\FirefoxPortable\\FirefoxPortable.exe"));

			Proxy proxy2 = new Proxy();
			 proxy2.setProxyType(Proxy.ProxyType.MANUAL);
			 DesiredCapabilities capabilities2 	= new DesiredCapabilities();
			 FirefoxProfile myprofile2 			= new FirefoxProfile();
			 	myprofile2.setPreference("capability.policy.strict.Window.alert",  "noAccess");
				myprofile2.setPreference("browser.download.folderList", 2);
				myprofile2.setPreference("browser.download.manager.showWhenStarting", false);
				myprofile2.setPreference("browser.download.dir", downloadAttachementPath);
				myprofile2.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
				myprofile2.setPreference("pdfjs.disabled", true);
				myprofile2.setPreference("plugin.scan.Acrobat", "99.0");
				myprofile2.setPreference("plugin.scan.plid.all", false);			
			 
				proxy2.setHttpProxy(PROXY_HOST);
				proxy2.setSocksUsername(PROXY_USER);
				proxy2.setSocksPassword(PROXY_PASS);
				
				capabilities2.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities2.setCapability(CapabilityType.PROXY, proxy2);
				capabilities2.setCapability(FirefoxDriver.PROFILE, myprofile2);			 
			 
			driver = new FirefoxDriver(capabilities2);
//					(binary2, myprofile2, capabilities2);
		}		
		
		
		else if (browserType.equals("FIREFOX_PPC")){ // This version works for FF_PORT 4.5
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}
			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));
			Proxy proxy = new Proxy();
			if(ProjectDataVariables.EUCR_ENV.equals("DEV")){
				proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");	
			}else{
				proxy.setProxyType(Proxy.ProxyType.MANUAL);	
//			    proxy.setHttpProxy("147.67.246.195:8012"); // new addr1 - works without popup authentication [TEST - but not works for EUTL]
//				proxy.setHttpProxy(PROXY_HOST+":8012"); // old adresses - UT, DEV
//				proxy.setHttpProxy("147.67.5.195:8012"); // not works / popup not exist
				
				proxy.setHttpProxy(PROXY_HOST); // works on TEST [EUCR&EUTL]
				proxy.setSocksUsername(PROXY_USER);
				proxy.setSocksPassword(PROXY_PASS);
			}
			
			 DesiredCapabilities capabilities 	= new DesiredCapabilities();
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
			 myprofile.setPreference("browser.cache.disk.enable", false);
			 myprofile.setPreference("browser.cache.memory.enable", false);
			 myprofile.setPreference("browser.cache.offline.enable", false);
			 myprofile.setPreference("network.http.use-cache", false);
				
			 capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			 capabilities.setCapability(CapabilityType.PROXY, proxy);
//			 capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
			 
//			 return new FirefoxDriver(binary, myprofile, capabilities);
			 driver = new FirefoxDriver();
//					 (capabilities);			

	
		
		}
		
		
		
		
		
//		else if(browserType.equals("HTMLUNIT")){
//			 driver = new HtmlUnitDriver(); 
//		}
		else if (browserType.equals("CHROME")){
			/*
			Matrix of compatibility:
			- Chromium[30.0.1573.0] and Chromedriver: 2.3 [https://chromedriver.storage.googleapis.com/index.html?path=2.3/]
			*/
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){	
				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROME_EXE_PROC_NAME);	
				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROMEDRIVER_EXE_PROC_NAME);
				}
			
			Map<String, Object> prefs 	= new HashMap<String, Object>(); //XX
			prefs.put("profile.default_content_settings.popups", 0); //XX
			prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 ); //XX
			prefs.put("profile.default_content_setting_values.automatic_downloads",1);
			prefs.put("download.prompt_for_download", false); //XX
//			prefs.put("download.directory_upgrade", true); //XX
			prefs.put("download.default_directory", downloadAttachementPath);
			prefs.put("pdfjs.disabled", true);
			
			ChromeOptions options 		= new ChromeOptions();
			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			options.addArguments("--always-authorize-plugins=true");
			options.addArguments("--test-type");
			options.setBinary(new File(ProjectDataVariables.CHROME_EXE_LOCATION));
//			options.setBinary(new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"));
			options.setExperimentalOption("prefs", prefs);
			
			DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
			capabilitiesChrome.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
			capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, options);
			
			if (ProjectDataVariables.REGULAR_GRID.contains("GRID")){
				try {
					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilitiesChrome);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} 
			}else{
//				System.setProperty("chrome.binary", ProjectDataVariables.CHROME_EXE_LOCATION);
				
				/*
				 * Chrome DRIVER: 
				 * Version 2.0 Works fine but loggs lots of log into console
				 * Version 2.1 Works fine, no extended loggin into console (pick for this driver as Chrome binary version of CHROMIUM !!! Win_x64-212887-chrome-win32.zip)
				 * Version 2.19, 2.20 Works BUT AFTER STARTING DRIVER there is blocking popup!!
				 * 
				 */
				System.setProperty("webdriver.chrome.driver", ProjectDataVariables.WEBDRIVERS_DIRECTORY + "\\"+ProjectDataVariables.CHROMEDRIVER_EXE_PROC_NAME+""); // chromedriver23.exe
				driver = new ChromeDriver(options);
			}
			
		}	
		
			else if (browserType.equals("CHROME_BKP")){
				/*
				Matrix of compatibility:
				- Chromium[30.0.1573.0] and Chromedriver: 2.3 [https://chromedriver.storage.googleapis.com/index.html?path=2.3/]
				*/
//				 KILLING
				if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){	Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROME_EXE_PROC_NAME);	Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROMEDRIVER_EXE_PROC_NAME);}
				
				Map<String, Object> prefs 	= new HashMap<String, Object>(); //XX
				prefs.put("profile.default_content_settings.popups", 0); //XX
				prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 ); //XX
				prefs.put("profile.default_content_setting_values.automatic_downloads",1);
				prefs.put("download.prompt_for_download", false); //XX
//				prefs.put("download.directory_upgrade", true); //XX
				prefs.put("download.default_directory", downloadAttachementPath);
				
				ChromeOptions options 		= new ChromeOptions();
				options.setBinary(new File(ProjectDataVariables.CHROME_EXE_LOCATION));
//				options.setBinary(new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"));
				options.setExperimentalOption("prefs", prefs);
				DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();  
				capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, options);
				
				if (ProjectDataVariables.REGULAR_GRID.contains("GRID")){
					try {
						driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilitiesChrome);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} 
				}else{
//					System.setProperty("chrome.binary", ProjectDataVariables.CHROME_EXE_LOCATION);
					
					/*
					 * Chrome DRIVER: 
					 * Version 2.0 Works fine but loggs lots of log into console
					 * Version 2.1 Works fine, no extended loggin into console (pick for this driver as Chrome binary version of CHROMIUM !!! Win_x64-212887-chrome-win32.zip)
					 * Version 2.19, 2.20 Works BUT AFTER STARTING DRIVER there is blocking popup!!
					 * 
					 */
					System.setProperty("webdriver.chrome.driver", ProjectDataVariables.WEBDRIVERS_DIRECTORY + "\\chromedriver23.exe");
					driver = new ChromeDriver(options);
				}
				
			}				
			
			
		
		else if (browserType.equals("CHROME2-WRONG")){
			/*
			Matrix of compatibility:
			- Chromium[30.0.1573.0] and Chromedriver: 2.3 [https://chromedriver.storage.googleapis.com/index.html?path=2.3/]
			*/
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){	Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROME_EXE_PROC_NAME);	Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROMEDRIVER_EXE_PROC_NAME);}
			ChromeOptions chromeOptions 			= new ChromeOptions();
			DesiredCapabilities capabilitiesChrome 	= DesiredCapabilities.chrome();
			Map<String, String> prefs 				= new Hashtable<>();
			prefs.put("download.prompt_for_download", "false");
			prefs.put("download.default_directory", downloadAttachementPath);                    
//			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//			capabilities.setCapability("chrome.prefs", prefs);
//			new File(ProjectDataVariables.CHROME_EXE_LOCATION)
			System.setProperty("webdriver.chrome.driver", "C:\\PGM\\webdrivers\\chromedriver.exe");
			chromeOptions.setBinary(new File(ProjectDataVariables.CHROME_EXE_LOCATION));
			chromeOptions.setExperimentalOption("prefs", prefs);
			capabilitiesChrome.setCapability("chrome.prefs", prefs);  
			capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new ChromeDriver(capabilitiesChrome);
			
//			chromePrefs.put("download.default_directory", downloadAttachementPath);
//			chromePrefs.put("profile.default_content_settings.popups", 0);
//			chromePrefs.put( "profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 );
//			//Turns off download prompt
//			chromePrefs.put("download.prompt_for_download", false);			
			
			
////			chromePrefs.put("profile.default_content_settings.popups", 0);
//			chromePrefs.put("download.default_directory", downloadAttachementPath);
//			chromePrefs.put("download.prompt_for_download", "false");
////			chromePrefs.put("download.extensions_to_open", "pdf");
//			chromePrefs.put("profile.default_content_settings.popups", 0);
//			chromePrefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 );			
//			chromeOptions.setBinary(new File(ProjectDataVariables.CHROME_EXE_LOCATION));
//			chromeOptions.setExperimentalOption("prefs", chromePrefs);
//			DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();  
//			capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			
			
//			if (ProjectDataVariables.REGULAR_GRID.contains("GRID")){
//				try {
//					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilitiesChrome);
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				} 
//			}else{
////				System.setProperty("chrome.binary", ProjectDataVariables.CHROME_EXE_LOCATION);
//				
//				/*
//				 * Chrome DRIVER: 
//				 * Version 2.0 Works fine but loggs lots of log into console
//				 * Version 2.1 Works fine, no extended loggin into console (pick for this driver as Chrome binary version of CHROMIUM !!! Win_x64-212887-chrome-win32.zip)
//				 * Version 2.19, 2.20 Works BUT AFTER STARTING DRIVER there is blocking popup!!
//				 * 
//				 */
//				System.setProperty("webdriver.chrome.driver", ProjectDataVariables.WEBDRIVERS_DIRECTORY + "\\chromedriver23.exe");
//				driver = new ChromeDriver(capabilitiesChrome);
//			}
			
		}
		else if(browserType.equals("FIREFOX001")){
			
			System.out.println("DDDDDD");
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}

			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));			
			FirefoxProfile myprofile 		= new FirefoxProfile();
			DesiredCapabilities dc 			= DesiredCapabilities.firefox();
			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy(); 

			proxy.setSslProxy("proxyurl"+":"+8080); 
			proxy.setFtpProxy("proxy url"+":"+8080); 
			proxy.setSocksUsername(PROXY_USER); 
			proxy.setSocksPassword(PROXY_PASS); 
			dc.setCapability(CapabilityType.PROXY, proxy); 
			dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			dc.setCapability(FirefoxDriver.PROFILE, myprofile);			
//			driver = new FirefoxDriver(binary,myprofile);
			driver = new FirefoxDriver();
		}else if(browserType.equals("FIREFOXER")){
			FirefoxProfile myprofile 		= new FirefoxProfile();
//			DesiredCapabilities dc 			= DesiredCapabilities.firefox();
//	        myprofile.setPreference("network.automatic-ntlm-auth.trusted-uris", "ets-registry-test.tech.ec.europa.eu");
//	        myprofile.setPreference("network.automatic-ntlm-auth.trusted-uris", "ecas.cc.cec.eu.int:7002");
//
//			   HttpHost proxy = new HttpHost(PROXY_HOST, 8012, "http");
//		       DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//		                CredentialsProvider credsProvider = new BasicCredentialsProvider();
////		        credsProvider.setCredentials(
////		                  new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
////		                  new UsernamePasswordCredentials(ACCOUNT_SID, AUTH_TOKEN));
//		        credsProvider.setCredentials(
//		                  new AuthScope(PROXY_HOST, 8012),
//		                  new UsernamePasswordCredentials("gumulkr", "Welcome4444"));        
//		        CloseableHttpClient httpclient = HttpClients.custom()
//		                     .setRoutePlanner(routePlanner)
//		                .setDefaultCredentialsProvider(credsProvider).build();   
////		        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
////		        client.setHttpclient(httpclient);
//		        dc.s
//		        
//		        driver = new FirefoxDrive
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}

			
			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));	
			
//			String PROXY = PROXY_HOST+":8012";
//
//			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
//			proxy.setHttpProxy(PROXY)
//			     .setFtpProxy(PROXY)
//			     .setSslProxy(PROXY);
//			proxy.setSocksUsername("gumulkr"); 
//			proxy.setSocksPassword("Welcome4444");			
//			DesiredCapabilities cap = new DesiredCapabilities();
//			cap.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
//			cap.setCapability(CapabilityType.PROXY, proxy);
//			cap.setCapability(FirefoxDriver.PROFILE, myprofile);
//			cap.setCapability(CapabilityType.PROXY, proxy);
////			WebDriver driver = new FirefoxDriver(cap);			
//			
//			
////			 DesiredCapabilities capabilities 	= new DesiredCapabilities();
////			 FirefoxProfile myprofile 			= new FirefoxProfile();
////			 Proxy proxy	 					= new Proxy();
////			 proxy.setProxyType(Proxy.ProxyType.MANUAL);
//////			 proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
//////			 proxy.setHttpProxy(PROXY_HOST+":8012");
//////			 proxy.setSocksUsername("gumulkr");
//////			 proxy.setSocksPassword("Welcome4444");
//////			 myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
////			 proxy.setHttpProxy(PROXY_HOST+":8012"); // old adresses - UT, DEV
//////			 proxy.setHttpProxy("147.67.246.195:8012"); // new addr1
//////			 proxy.setHttpProxy("147.67.5.195:8012");
////				proxy.setSocksUsername("gumulkr");
////				proxy.setSocksPassword("Welcome4444");
////				
////				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
////				capabilities.setCapability(CapabilityType.PROXY, proxy);
////				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
//			 
//			 driver = new FirefoxDriver(binary, myprofile, cap);		        
			
			FirefoxProfile profile = new FirefoxProfile();
			Proxy proxy = new Proxy();
			DesiredCapabilities cap = new DesiredCapabilities();
			String PROXY = PROXY_HOST+":"+PROXY_PORT;
			proxy.setSocksUsername(PROXY_USER).setSocksPassword(PROXY_PASS);
			cap.setCapability(CapabilityType.PROXY, proxy);
//			driver = new FirefoxDriver(binary, profile, cap);
			driver = new FirefoxDriver();
			
		        
		}		
		
		
		
//		else if (browserType.equals("OPERA")){
//			Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.OPERA_EXE_PROC_NAME);
//			Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.OPERADRIVER_EXE_PROC_NAME);
//			DesiredCapabilities capabilities = DesiredCapabilities.opera();
//			driver = new OperaDriver(capabilities);
//		}
		else if (browserType.equals("MSIE")){
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("IEDriverServer.exe");Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("iexplore.exe");}			
			
			File file = new File(ProjectDataVariables.WEBDRIVERS_DIRECTORY + "IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); 
			caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
			driver = new InternetExplorerDriver(caps);
			return driver;
			
			/* READ THIS BEFORE RUNNING IEXPLORE
			* It needs to set same Security level in all zones. To do that follow the steps below:
			* 1.Open IE
			* 2.Go to Tools -> Internet Options -> Security
			* 3.Set all zones to the same protected mode, enabled or disabled should not matter.
			* Finally, set Zoom level to 100% by right clicking on the gear located at the top right corner and enabling the status-bar. Default zoom level is now displayed at the lower right.
			*/
//			System.out.println("LOADING MSIE BROWSER PREFERENCES");
		}		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
//		SeleniumWebUtils.MaxWindow(driver);
		
		if(Setuper.driver == null){
			Setuper.driver = driver;
		}
		
		return driver;
	}
	
	public static boolean CHECK_IF_WIND_PROCES_IS_RUNNING(String ProcessExecutableName) throws IOException{
		String line;
		String pidInfo ="";
		Process p =Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
		BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null) {
		    pidInfo+=line; 
		}
		input.close();
			if(pidInfo.contains(ProcessExecutableName))
			{
			    return true;
			}else{
				return false;
			}
		}
		
	public static void KILL_PROCESS_IF_RUNNING_IN_WINDOWS(String ProcessNameEXE){
		try {
			if(Setuper.CHECK_IF_WIND_PROCES_IS_RUNNING(ProcessNameEXE)){
				Runtime.getRuntime().exec("taskkill /F /IM "+ProcessNameEXE);
//				WindowsUtils.tryToKillByName(ProcessNameEXE);	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public static void PRINT_INITIAL_TEST_SETTINGS(){
//		SeleniumWebUtils.reportInfo("####################################################");
//		ax.excel_note("TEST_RUN_PARAMETERS");
		ax.excel_data("[CONF]_TEST_RUN_DATE"		, DateOperations.date_get_NOW_IN_DateFormat_2MON2_DASHED());
		ax.excel_data("[CONF]_EUCR_VERSION"			, ax.READ_CONFIG_FILE("APP_VERSION_EUCR"));
		ax.excel_data("[CONF]_EUTL_VERSION"			, ax.READ_CONFIG_FILE("APP_VERSION_EUTL"));
		ax.excel_data("[CONF]_EUTL_PUBLIC_VERSION"	, ax.READ_CONFIG_FILE("APP_VERSION_EUTL_PUBL"));
		ax.excel_data("[CONF]_TEST_ENVRMENT"		, ax.READ_CONFIG_FILE("TEST_ENVRMENT"));
		ax.excel_data("[CONF]_TEST_REGISTRY"		, ax.READ_CONFIG_FILE("TEST_REGISTRY"));
		ax.excel_data("[CONF]_TEST_BROWSER"			, SeleniumDriver.getCurrentBrowserType());
		ax.excel_data("[CONF]_LOGIN_SMS_ENABLED"	, ax.READ_CONFIG_FILE("LOGIN_SMS_ENABLED"));
		ax.excel_data("[CONF]_SIGNATURE_ENABLED"	, ax.READ_CONFIG_FILE("SIGNATURE_ENABLED"));
		ax.excel_data("[CONF]_ECAS_VERSION"			, ax.READ_CONFIG_FILE("ECAS_VERSION"));
		ax.excel_data("[CONF]_TEST_MODE"			, ax.READ_CONFIG_FILE("TEST_MODE"));
		
		
//		SeleniumWebUtils.reportInfo("Browser Type: "+ SeleniumDriver.getCurrentBrowserType());
//		SeleniumWebUtils.reportInfo("Registry to change: "+ ProjectDataVariables.EUCR_REG);
//		SeleniumWebUtils.reportInfo("####################################################");
	}
	public static void SetECASLoginPassAuthRequiredPopup(WebDriver driver, String txt) throws IOException, InterruptedException{
		SeleniumWebUtils.reportInfo("ENTER FX:SetECASLoginPassAuthRequiredPopup "+txt);
		String Switcher=ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "runFrom");
  		if (Switcher.equals("clima")){
  			driver.switchTo().alert();
//  			Runtime.getRuntime().exec(ProjectDataVariables.EXE_BROWSER_FILE_DIRECTORY + "\\euproxyauth.exe");
  			Runtime.getRuntime().exec(ProjectDataVariables.EXE_EXTERNAL_FILE_DIRECTORY + "\\euproxyauth.exe");
  			ax.wait(3);		
  		}
		SeleniumWebUtils.reportInfo("EXIT FX:SetECASLoginPassAuthRequiredPopup ");
	}
	

	public static String getXURL(String registry){
		return ProjectDataVariables.applicationDoman+"/"+registry+"/index.xhtml";
	}	
	
	public static WebDriver GET_WEBDRVER_FIREFOX_PORTABLE(){
		WebDriver driver;
//		DesiredCapabilities capability=null;
		FirefoxBinary binary = new FirefoxBinary(new File("C:\\temp\\firefox\\FirefoxPortable\\FirefoxPortable.exe"));
		FirefoxProfile myprofile = new FirefoxProfile();
		myprofile.setPreference("network.proxy.http", "147.67.138.13");
		myprofile.setPreference("network.proxy.http_port", "8012");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
		dc.setCapability(FirefoxDriver.PROFILE, myprofile);
//		driver = new FirefoxDriver(binary,myprofile);	
		driver = new FirefoxDriver();
		return driver;
	}
	
	public static void GARBAGE_EPMTIFIER() throws IOException, InterruptedException{
		Setuper.KILL_BROWSER_IF_RUNNING();		
		System.out.println("WAITER 3 SEC...............................................................");
		ax.wait(3);
		System.out.println("WAITER 3 SEC DONE..........................................................");
		
	}
	
	
	public static void TEAR_DOWN_PATTERN(String REPORT_TESTNG_HTML_URL) throws IOException, InterruptedException{
//		  ApplicationPageUtils.ClickEURCR_LogoutLink(driver);
			TestUtils.DrawLoger_END_OF_TEST_RUN();
			System.out.println("");
			System.out.println("TESTNG REPORT: "+REPORT_TESTNG_HTML_URL);
			Setuper.GARBAGE_EPMTIFIER();
	}
	
	public static void RESTARTER_PATTERN(WebDriver driver) throws InterruptedException, IOException{
		driver.close();
		ax.wait(2);
		Setuper.KILL_BROWSER_IF_RUNNING();
		driver = Setuper.InitiateBrowser(SeleniumDriver.getCurrentBrowserType());
		setDriver(driver);
	}	
	public static void QUIT_PATTERN(WebDriver driver) throws InterruptedException, IOException{
		driver.quit();
		ax.wait(2);
		Setuper.KILL_BROWSER_IF_RUNNING();
	}	
	
	public static void KILL_BROWSER_IF_RUNNING() throws IOException{
		if(SeleniumDriver.getCurrentBrowserType().equals("CHROME")){
			Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROMEDRIVER_EXE_PROC_NAME);
		}
		
		if(SeleniumDriver.getCurrentBrowserType().equals("FIREFOX")){
			Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);
		}		

		
	}
	
	public static void CLOSE_DRIVERS(){ // Should be before DRIVER-QUIT
		if(Setuper.driver!=null){Setuper.driver.close();}
//		if(Setuper.driver1!=null){Setuper.driver1.close();}
		if(Setuper.driver2!=null){Setuper.driver2.close();}
		if(Setuper.driver3!=null){Setuper.driver3.close();}
		if(Setuper.driver4!=null){Setuper.driver4.close();}
	}
	
	public static void QUIT_DRIVERS(){ // Should be after DRIVER-CLOSE
		if(Setuper.driver!=null){Setuper.driver.quit();}
		//if(Setuper.driver1!=null){Setuper.driver1.quit();}
		if(Setuper.driver2!=null){Setuper.driver2.quit();}
		if(Setuper.driver3!=null){Setuper.driver3.quit();}
		if(Setuper.driver4!=null){Setuper.driver4.quit();}
	}
	
	
	
	public static void SETUP_DOUBLE_DRIVER_WINDOW(WebDriver driver1, WebDriver driver2){
//		driver1.manage().window().setPosition(new Point(0,0));
//		driver2.manage().window().setPosition(new Point(950,0));
//		driver1.manage().window().setSize(new Dimension(950,1000));
//		driver2.manage().window().setSize(new Dimension(950,1000));		
    	driver1.manage().window().setPosition(new Point(0,0)); 
    	driver2.manage().window().setPosition(new Point(1250,0));
    	driver1.manage().window().setSize(new Dimension(1250,1300));
    	driver2.manage().window().setSize(new Dimension(1250,1300));
		
		
	}
	public static void SETUP_RUN_DRIVERS_WINDOW(){
		SETUP_RUN_DRIVERS_WINDOW(1, 300, 600, 900);
	}
	public static void SETUP_RUN_DRIVERS_WINDOW(int dW1, int dW2, int dW3){
		if(Setuper.driver!=null){
			Setuper.driver.manage().window().setPosition(new Point(0,0));
			Setuper.driver.manage().window().setSize(new Dimension(1300,1000));		}
		if(Setuper.driver2!=null){
			Setuper.driver2.manage().window().setPosition(new Point(dW2,100)); // dW1
			Setuper.driver2.manage().window().setSize(new Dimension(1300,1000));		}
		if(Setuper.driver3!=null){
			Setuper.driver3.manage().window().setPosition(new Point(dW3,200)); // dW1+dW2
			Setuper.driver3.manage().window().setSize(new Dimension(1300,1000));		}
	}
	public static void SETUP_RUN_DRIVERS_WINDOW(int dW1, int dW2, int dW3, int dW4){
		int w 	= 1920;
		int b 	= 1500;
		int h 	= 1000;
		if(Setuper.driver3!=null){
			Setuper.driver3.manage().window().setPosition(new Point(dW3,1)); // dW1+dW2
			Setuper.driver3.manage().window().setSize(new Dimension(b-180,h));
		}
		if(Setuper.driver4!=null){
			Setuper.driver4.manage().window().setPosition(new Point(600, 1));
			Setuper.driver4.manage().window().setSize(new Dimension(b-160,h));
		}
		if(Setuper.driver2!=null){
			
			Setuper.driver2.manage().window().setPosition(new Point(w-1500,1)); // dW1
			Setuper.driver2.manage().window().setSize(new Dimension(b,h));		
		}
		if(Setuper.driver!=null){
			Setuper.driver.manage().window().setPosition(new Point(1,1));
			Setuper.driver.manage().window().setSize(new Dimension(b,h));		
		}
		
	}
	
	
	public static WebDriver focusDriver(WebDriver driver){
		JavascriptExecutor js = (JavascriptExecutor)driver;
//		js.executeScript("window.alert('one');");
		
		
		if(Setuper.BrowserRunTimeName1.equals("MSIE")){
			js.executeScript("window.focus();");	
		}else if(Setuper.BrowserRunTimeName1.equals("CHROME") || Setuper.BrowserRunTimeName1.equals("FIREFOX")){
			js.executeScript("window.alert('one');");
			driver.switchTo().alert().dismiss();
		}
		
		
//		driver.switchTo().alert().accept();
//		.dismiss();
		return driver;
	}
	
    public static boolean DBL_DRIVERS(){
    	return (Setuper.driver != null && Setuper.driver2!=null && Setuper.driver3!=null)?true:false; }
    
	public static WebDriver RUN_DRIVER(WebDriver driver, int driver_nr){
//		return driver;
		
		
		if(Setuper.DBL_DRIVERS()){
    			switch (driver_nr) {
				case 1: return Setuper.driver;
				case 2: return Setuper.driver2;
				case 3: return Setuper.driver3;
				case 4: return Setuper.driver4;
				
				}
			}else{
				return Setuper.driver;}
		return driver;	
	}    
   
	

	

	
	@Test
	public static WebDriver InitiateBrowser_BKP(String browserType){
		ArrayList<String> BRWSX = new ArrayList<String>(Arrays.asList("FIREFOX","CHROME","MSIE","FIREFOX_OLD","FIREFOX001","FIREFOX_P2","HTMLUNIT"));
		if(!BRWSX.contains(browserType)){System.out.println("WRONG BROWSER TYPE DEFINED: "+browserType);}
		WebDriver driver = null;

		if (browserType.equals("FIREFOX_OLD")){ // This version works for FF_PORT 4.5
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}
			
			Proxy proxy = new Proxy();
			if(ProjectDataVariables.EUCR_ENV.equals("DEV")){
				proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");	
			}else{
				proxy.setProxyType(Proxy.ProxyType.MANUAL);	
				proxy.setHttpProxy(PROXY_HOST); // works on TEST [EUCR&EUTL]
				proxy.setSocksUsername(PROXY_USER);
				proxy.setSocksPassword(PROXY_PASS);
			}
			
			 DesiredCapabilities capabilities 	= new DesiredCapabilities();
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
			 myprofile.setPreference("browser.cache.disk.enable", false);
			 myprofile.setPreference("browser.cache.memory.enable", false);
			 myprofile.setPreference("browser.cache.offline.enable", false);
			 myprofile.setPreference("network.http.use-cache", false);
				
			 capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			 capabilities.setCapability(CapabilityType.PROXY, proxy);
			 capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);	
			 
			 
			 return new FirefoxDriver(capabilities);			

			/*
			
			
//			FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"));
//			FirefoxBinary binary = new FirefoxBinary(new File("C:\\Users\\gumulkr\\Downloads\\FirefoxPortable\\FirefoxPortable.exe"));
			FirefoxBinary binary = new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));
//			FirefoxBinary binary = new FirefoxBinary(new File("C:\\xtemp\\firefox\\FirefoxPortable\\FirefoxPortable.exe"));
			
			
			
//			FirefoxProfile profile = new FirefoxProfile();
//			WebDriver driver = new FirefoxDriver(binary, profile);			
			
			
			FirefoxProfile myprofile = new FirefoxProfile();
//			FirefoxProfile myprofile = new ProfilesIni().getProfile("default");
//			FirefoxProfile myprofile = new FirefoxProfile();
			myprofile.setPreference("network.proxy.http", "147.67.138.13");
			myprofile.setPreference("network.proxy.http_port", "8012");
			
//			##[download configuration]###############################################
//			myprofile.setPreference("browser.download.folderList", 2); //# custom location
//			myprofile.setPreference("browser.download.manager.showWhenStarting", false);
//			myprofile.setPreference("browser.download.dir", downloadAttachementPath);
//			myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/pdf, text/plain, application/vnd.ms-excel, text/csv, text/comma-separated-values, application/octet-stream, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'");			

			
//			myprofile.setPreference("browser.download.dir", downloadAttachementPath); 
//			myprofile.setPreference("browser.download.folderList", 2); 
//			myprofile.setPreference("browser.helperApps.alwaysAsk.force", false); 
//			myprofile.setPreference("browser.download.manager.showWhenStarting",false); 
//			myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream"); 			
			

			myprofile.setPreference("browser.download.folderList", 2);
			myprofile.setPreference("browser.download.manager.showWhenStarting", false);
			myprofile.setPreference("browser.download.dir", downloadAttachementPath);
			myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
			myprofile.setPreference("pdfjs.disabled", true);
			// Use this to disable Acrobat plugin for previewing PDFs in Firefox (if you have Adobe reader installed on your computer)
			myprofile.setPreference("plugin.scan.Acrobat", "99.0");
			myprofile.setPreference("plugin.scan.plid.all", false);
			//			##[download configuration]###############################################
			
			
//			proxy.setHttpProxy(PROXY_HOST);
//			proxy.setSocksUsername(PROXY_USER);
//			proxy.setSocksPassword(PROXY_PASS);
			
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
//			dc.setCapability(CapabilityType.PROXY, proxy);
			dc.setCapability(FirefoxDriver.PROFILE, myprofile);
			
			
			if (ProjectDataVariables.REGULAR_GRID.contains("GRID")){
//  #############################################################################
//			info:Running SELENIUM GRID CONF		
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),dc);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			}else{
//  #############################################################################
//			info:Running Webdriver Single Functional Test		
//			driver = new FirefoxDriver(myprofile);			
			driver = new FirefoxDriver(binary,myprofile);			
//  #############################################################################
			}
			
		*/
		
		}
		
		else if(browserType.equals("FIREFOX001")){
			
			System.out.println("DDDDDD");
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}

			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));			
			FirefoxProfile myprofile 		= new FirefoxProfile();
			DesiredCapabilities dc 			= DesiredCapabilities.firefox();
			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy(); 

			proxy.setSslProxy("proxyurl"+":"+8080); 
			proxy.setFtpProxy("proxy url"+":"+8080); 
			proxy.setSocksUsername(PROXY_USER); 
			proxy.setSocksPassword(PROXY_PASS); 
			dc.setCapability(CapabilityType.PROXY, proxy); 
			dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			dc.setCapability(FirefoxDriver.PROFILE, myprofile);			
//			driver = new FirefoxDriver(binary,myprofile);
			driver = new FirefoxDriver();
		}else if(browserType.equals("FIREFOXER")){
			FirefoxProfile myprofile 		= new FirefoxProfile();
//			DesiredCapabilities dc 			= DesiredCapabilities.firefox();
//	        myprofile.setPreference("network.automatic-ntlm-auth.trusted-uris", "ets-registry-test.tech.ec.europa.eu");
//	        myprofile.setPreference("network.automatic-ntlm-auth.trusted-uris", "ecas.cc.cec.eu.int:7002");
//
//			   HttpHost proxy = new HttpHost(PROXY_HOST, 8012, "http");
//		       DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//		                CredentialsProvider credsProvider = new BasicCredentialsProvider();
////		        credsProvider.setCredentials(
////		                  new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
////		                  new UsernamePasswordCredentials(ACCOUNT_SID, AUTH_TOKEN));
//		        credsProvider.setCredentials(
//		                  new AuthScope(PROXY_HOST, 8012),
//		                  new UsernamePasswordCredentials("gumulkr", "Welcome4444"));        
//		        CloseableHttpClient httpclient = HttpClients.custom()
//		                     .setRoutePlanner(routePlanner)
//		                .setDefaultCredentialsProvider(credsProvider).build();   
////		        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
////		        client.setHttpclient(httpclient);
//		        dc.s
//		        
//		        driver = new FirefoxDrive
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}

			
			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));	
			
//			String PROXY = PROXY_HOST+":8012";
//
//			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
//			proxy.setHttpProxy(PROXY)
//			     .setFtpProxy(PROXY)
//			     .setSslProxy(PROXY);
//			proxy.setSocksUsername("gumulkr"); 
//			proxy.setSocksPassword("Welcome4444");			
//			DesiredCapabilities cap = new DesiredCapabilities();
//			cap.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
//			cap.setCapability(CapabilityType.PROXY, proxy);
//			cap.setCapability(FirefoxDriver.PROFILE, myprofile);
//			cap.setCapability(CapabilityType.PROXY, proxy);
////			WebDriver driver = new FirefoxDriver(cap);			
//			
//			
////			 DesiredCapabilities capabilities 	= new DesiredCapabilities();
////			 FirefoxProfile myprofile 			= new FirefoxProfile();
////			 Proxy proxy	 					= new Proxy();
////			 proxy.setProxyType(Proxy.ProxyType.MANUAL);
//////			 proxy.setProxyAutoconfigUrl("http://autoproxy.cec.eu.int/proxy.pac");
//////			 proxy.setHttpProxy(PROXY_HOST+":8012");
//////			 proxy.setSocksUsername("gumulkr");
//////			 proxy.setSocksPassword("Welcome4444");
//////			 myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
////			 proxy.setHttpProxy(PROXY_HOST+":8012"); // old adresses - UT, DEV
//////			 proxy.setHttpProxy("147.67.246.195:8012"); // new addr1
//////			 proxy.setHttpProxy("147.67.5.195:8012");
////				proxy.setSocksUsername("gumulkr");
////				proxy.setSocksPassword("Welcome4444");
////				
////				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
////				capabilities.setCapability(CapabilityType.PROXY, proxy);
////				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
//			 
//			 driver = new FirefoxDriver(binary, myprofile, cap);		        
			
			FirefoxProfile profile = new FirefoxProfile();
			Proxy proxy = new Proxy();
			DesiredCapabilities cap = new DesiredCapabilities();
			String PROXY = PROXY_HOST+":"+PROXY_PORT;
			proxy.setSocksUsername(PROXY_USER).setSocksPassword(PROXY_PASS);
			cap.setCapability(CapabilityType.PROXY, proxy);
//			driver = new FirefoxDriver(binary, profile, cap);
			driver = new FirefoxDriver();
			
		        
		}
		else if(browserType.equals("FIREFOX")){
			ax.debug("LOADING BROWSER PREFERENCES");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				
					Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	
				}
			
			FirefoxBinary binary 			= new FirefoxBinary(new File(ProjectDataVariables.FIREFOX_EXE_LOCATION));
			System.setProperty("webdriver.gecko.driver", "C:\\PGM\\webdrivers\\geckodriver.exe");
			
			Proxy proxy 					= new Proxy();
			 proxy.setProxyType(Proxy.ProxyType.MANUAL);
			 DesiredCapabilities capabilities 	= new DesiredCapabilities();
			 FirefoxProfile myprofile 			= new FirefoxProfile();
			 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
				myprofile.setPreference("browser.download.folderList", 2);
				myprofile.setPreference("browser.download.manager.showWhenStarting", false);
				myprofile.setPreference("browser.download.dir", downloadAttachementPath);
				myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
				myprofile.setPreference("pdfjs.disabled", true);
				myprofile.setPreference("plugin.scan.Acrobat", "99.0");
				myprofile.setPreference("plugin.scan.plid.all", false);			
			 
				proxy.setHttpProxy(PROXY_HOST);
				proxy.setSocksUsername(PROXY_USER);
				proxy.setSocksPassword(PROXY_PASS);
				
				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
				capabilities.setCapability(FirefoxDriver.PROFILE, myprofile);			 
			 
//			driver = new FirefoxDriver(binary, myprofile, capabilities);
				driver = new FirefoxDriver();
		}
		else if(browserType.equals("FIREFOX_P2")){
			System.out.println("LOADING BROWSER PREFERENCES FIREFOX_P2");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){				Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.FIREFOX_EXE_PROC_NAME);	}
			FirefoxBinary binary2 			= new FirefoxBinary(new File("C:\\temp\\firefox\\FirefoxPortable\\FirefoxPortable.exe"));

			Proxy proxy2 = new Proxy();
			 proxy2.setProxyType(Proxy.ProxyType.MANUAL);
			 DesiredCapabilities capabilities2 	= new DesiredCapabilities();
			 FirefoxProfile myprofile2 			= new FirefoxProfile();
			 	myprofile2.setPreference("capability.policy.strict.Window.alert",  "noAccess");
				myprofile2.setPreference("browser.download.folderList", 2);
				myprofile2.setPreference("browser.download.manager.showWhenStarting", false);
				myprofile2.setPreference("browser.download.dir", downloadAttachementPath);
				myprofile2.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
				myprofile2.setPreference("pdfjs.disabled", true);
				myprofile2.setPreference("plugin.scan.Acrobat", "99.0");
				myprofile2.setPreference("plugin.scan.plid.all", false);			
			 
				proxy2.setHttpProxy(PROXY_HOST);
				proxy2.setSocksUsername(PROXY_USER);
				proxy2.setSocksPassword(PROXY_PASS);
				
				capabilities2.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				capabilities2.setCapability(CapabilityType.PROXY, proxy2);
				capabilities2.setCapability(FirefoxDriver.PROFILE, myprofile2);			 
			 
			driver = new FirefoxDriver(capabilities2);
//					(binary2, myprofile2, capabilities2);
		}
		
//		else if(browserType.equals("HTMLUNIT")){
//			 driver = new HtmlUnitDriver(); 
//		}
		
		else if (browserType.equals("CHROME")){
			/*
			Matrix of compatibility:
			- Chromium[30.0.1573.0] and Chromedriver: 2.3 [https://chromedriver.storage.googleapis.com/index.html?path=2.3/]
			
			
			*/
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){	Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROME_EXE_PROC_NAME);	Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.CHROMEDRIVER_EXE_PROC_NAME);}
			ChromeOptions options = new ChromeOptions();
			options.setBinary(new File(ProjectDataVariables.CHROME_EXE_LOCATION));
//			options.setBinary(new File("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"));
			
			
			DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();  
			capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, options);
			
			if (ProjectDataVariables.REGULAR_GRID.contains("GRID")){
				try {
					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilitiesChrome);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} 
			}else{
//				System.setProperty("chrome.binary", ProjectDataVariables.CHROME_EXE_LOCATION);
				
				/*
				 * Chrome DRIVER: 
				 * Version 2.0 Works fine but loggs lots of log into console
				 * Version 2.1 Works fine, no extended loggin into console (pick for this driver as Chrome binary version of CHROMIUM !!! Win_x64-212887-chrome-win32.zip)
				 * Version 2.19, 2.20 Works BUT AFTER STARTING DRIVER there is blocking popup!!
				 * 
				 */
				System.setProperty("webdriver.chrome.driver", ProjectDataVariables.WEBDRIVERS_DIRECTORY + "\\chromedriver23.exe");
//				DesiredCapabilities chromecapabilities = DesiredCapabilities.chrome();
//				chromecapabilities.setCapability("chrome.binary", "C:\\Users\\gumulkr\\AppData\\Local\\Chromium\\Application\\chrome.exe");
				driver = new ChromeDriver(options);
				
//				DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
//				capabilities.setCapability("chrome.binary", "path\\chrome.exe");
								
//				driver = new ChromeDriver();
			}
			
		}
		// OPERA
//		else if (browserType.equals("OPERA")){
//			Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.OPERA_EXE_PROC_NAME);
//			Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS(ProjectDataVariables.OPERADRIVER_EXE_PROC_NAME);
//			DesiredCapabilities capabilities = DesiredCapabilities.opera();
//			driver = new OperaDriver(capabilities);
//			
//		}

		else if (browserType.equals("MSIE")){
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("IEDriverServer.exe");Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("iexplore.exe");}			
			
			File file = new File(ProjectDataVariables.WEBDRIVERS_DIRECTORY + "IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			driver = new InternetExplorerDriver();
			return driver;
			
			/* READ THIS BEFORE RUNNING IEXPLORE
			* It needs to set same Security level in all zones. To do that follow the steps below:
			* 1.Open IE
			* 2.Go to Tools -> Internet Options -> Security
			* 3.Set all zones to the same protected mode, enabled or disabled should not matter.
			* Finally, set Zoom level to 100% by right clicking on the gear located at the top right corner and enabling the status-bar. Default zoom level is now displayed at the lower right.
			*/
//			System.out.println("LOADING MSIE BROWSER PREFERENCES");
			

			
//			
//			
//			
//			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
//			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//			ieCapabilities.setBrowserName(SeleniumDriver.getCurrentBrowserType());
//			
////			InternetExplorer myprofile 			= new 
////		 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
////			myprofile.setPreference("browser.download.folderList", 2);
////			myprofile.setPreference("browser.download.manager.showWhenStarting", false);
////			myprofile.setPreference("browser.download.dir", downloadAttachementPath);
////			myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
////			myprofile.setPreference("pdfjs.disabled", true);
////			myprofile.setPreference("plugin.scan.Acrobat", "99.0");
////			myprofile.setPreference("plugin.scan.plid.all", false);			
//			
//			
//			
////			System.setProperty("webdriver.ie.driver",ProjectDataVariables.EXE_BROWSER_FILE_DIRECTORY + "\\IEDriverServer.exe");
//			System.setProperty("webdriver.ie.driver","C:\\Program Files (x86)\\Java\\jre6\\bin\\IEDriverServer.exe");
//			try {
////	 #############################################################################
////				info:Running SELENIUM GRID CONF		
////				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),ieCapabilities);
////  #############################################################################
////				info:Running Webdriver Single Functional Test
//				driver= new InternetExplorerDriver(ieCapabilities);	
////  #############################################################################
//			} catch (Exception e) {
//				System.out.println("Running IEDriver problem");
//			}
			
			
//  ############################################################################# 			
			
			
			
		}		
		else if (browserType.equals("MSIE2")){
			/* READ THIS BEFORE RUNNING IEXPLORE
			* It needs to set same Security level in all zones. To do that follow the steps below:
			* 1.Open IE
			* 2.Go to Tools -> Internet Options -> Security
			* 3.Set all zones to the same protected mode, enabled or disabled should not matter.
			* Finally, set Zoom level to 100% by right clicking on the gear located at the top right corner and enabling the status-bar. Default zoom level is now displayed at the lower right.
			*/
			System.out.println("LOADING MSIE BROWSER PREFERENCES");
//			 KILLING
			if(ax.READ_CONFIG_FILE("KILL_BROWSER").equals("YES")){Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("IEDriverServer.exe");Setuper.KILL_PROCESS_IF_RUNNING_IN_WINDOWS("iexplore.exe");}
			
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setBrowserName(SeleniumDriver.getCurrentBrowserType());
			
//			InternetExplorer myprofile 			= new 
//		 	myprofile.setPreference("capability.policy.strict.Window.alert",  "noAccess");
//			myprofile.setPreference("browser.download.folderList", 2);
//			myprofile.setPreference("browser.download.manager.showWhenStarting", false);
//			myprofile.setPreference("browser.download.dir", downloadAttachementPath);
//			myprofile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;application/csv;text/csv");
//			myprofile.setPreference("pdfjs.disabled", true);
//			myprofile.setPreference("plugin.scan.Acrobat", "99.0");
//			myprofile.setPreference("plugin.scan.plid.all", false);			
			
			
			
//			System.setProperty("webdriver.ie.driver",ProjectDataVariables.EXE_BROWSER_FILE_DIRECTORY + "\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver","C:\\Program Files (x86)\\Java\\jre6\\bin\\IEDriverServer.exe");
			try {
//	 #############################################################################
//				info:Running SELENIUM GRID CONF		
//				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),ieCapabilities);
//  #############################################################################
//				info:Running Webdriver Single Functional Test
				driver= new InternetExplorerDriver();
//						er(ieCapabilities);	
//  #############################################################################
			} catch (Exception e) {
				System.out.println("Running IEDriver problem");
			}
//  ############################################################################# 			
		}
		
		//if(!browserType.equals("MSIE")){
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		//}
		
		SeleniumWebUtils.MaxWindow(driver);
		return driver;
	}	

	public static WebDriver RestartReloginBrowsers(WebDriver driver){
		if(Setuper.driver !=null && Setuper.driver2 != null){
			driver = ax.LOGIN_NA2(driver, 1);
			driver = ax.LOGIN_NA1(driver, 1);
			driver = ax.LOGIN_NA1(driver, 2);
			driver = ax.LOGIN_NA2(driver, 2);
		}else if(Setuper.driver !=null && Setuper.driver2 ==null){
			driver = ax.LOGIN_NA2(driver, 1);
			driver = ax.LOGIN_NA1(driver, 1);
		}
		return driver;
	}
	
	public static boolean IS_LOGIN_SMS_ENABLED(){
		return (ax.getProp("LOGIN_SMS_ENABLED").equals("YES"))?true:false;
	}
	public static boolean IS_SIGNATURE_ENABLED(){
		return (ax.getProp("SIGNATURE_ENABLED").equals("YES"))?true:false;
	}
	public static String GET_ENVIRONMENT(){
		return ax.getProp("TEST_ENVRMENT");
	}
	public static String GET_USER_PASS(){
		return ax.IS_NOT_NULLEMPTY(ax.getProp("TEST_USER_PASS"))?ax.getProp("TEST_USER_PASS"):null;
	}
	
	public static String GET_MOBILE_FOR_USER_NAME(String UserName){
		String mobileNumber = ax.GET_USER_TEL(ax.GET_USER_FX_BY_USERNAME(UserName));
		return (ax.IS_NOT_NULLEMPTY(mobileNumber))?mobileNumber:null;
	}
	
}
