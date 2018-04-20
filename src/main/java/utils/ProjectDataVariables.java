package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import utils.AppGetter;
import utils.ApplicationPageUtils;
import utils.ax;
import utils.ExcelJxl;
import utils.SeleniumDriver;

public class ProjectDataVariables{
//	public static String arg4run 									= clima.start.MainApps.Argumenter;
	public static final long startTestSetTime 						= System.currentTimeMillis();
	public static final String TEST_SESSION_ID 						= ToolFunctions.generateTestSessionId();
	public static final String TEST_SESSION_ID_SAVE					= TEST_SESSION_ID.replace("-", "");
	
	
//	FRAMEWORK - PATHS configuration ==============================================================================================================================================================
	public static final String SEPAR								= "\\";
	public static final String USER_OS_DIR						   	= "gumulkr"	+ SEPAR;
	public static final String DIR_TEMP_OS							= "C:"+SEPAR+"tmp"	+ SEPAR;
	public static final String DIR_C_USERS							= "C:"+SEPAR+"Users"	+ SEPAR;
	public static final String DIR_WORKSPACE						= "workspace"	+ SEPAR;
//	============================================================================================================================================================================================
	public static final String DIR_TEMP_OS_PATH						= DIR_TEMP_OS;
	public static final String DIR_C_USERS_PATH						= DIR_C_USERS;
	public static final String USER_OS_DIR_PATH					   	= DIR_C_USERS_PATH	+ USER_OS_DIR;
	public static final String DIR_WORKSPACE_PATH					= USER_OS_DIR_PATH 	+ DIR_WORKSPACE;
	public static final String EXE_BROWSER_FILE_DIRECTORY       	= DIR_WORKSPACE_PATH + "project_libs"+ SEPAR;												// work	
	public static final String EXE_EXTERNAL_FILE_DIRECTORY      	= DIR_WORKSPACE_PATH + "clima-eucr_lib"+ SEPAR;												// work
//	============================================================================================================================================================================================
//	============================================================================================================================================================================================
	// location below is only one i the project pointing to desired directory
	public static final String PROJECT_DIR							= DIR_WORKSPACE_PATH 	+ "eucr\\";
//	============================================================================================================================================================================================
//	============================================================================================================================================================================================
	public static final String PROJECT_CONFIG_DIR					= PROJECT_DIR 			+ "conf\\";
//	public static final String GRLOBAL_PROP_FILE					= PROJECT_CONFIG_DIR + "EUCR_AUTO_PROPERTIES_GLOBAL_"+System.getProperty("TEST_BROWSER")+".properties";
	public static final String GRLOBAL_PROP_FILE					= PROJECT_CONFIG_DIR + "EUCR_AUTO_PROPERTIES_GLOBAL.properties";
	public static final String TESTNG_START_XML_PATH				= PROJECT_CONFIG_DIR + "testng-start.xml"; 
//	============================================================================================================================================================================================
	public static final String SAVE_FILE_WITH_SESSION_DATA 			= DIR_TEMP_OS_PATH 			+ "SAVE_FILE_WITH_SESSION_DATA_"+ProjectDataVariables.TEST_SESSION_ID+".properties";
	//public static final String WORKSPACE_EUCR_OUT_DATA_DIR			= DIR_TEMP_OS_PATH 			+ "clima-eucr-out-data" + SEPAR;
	public static final String WORKSPACE_EUCR_OUT_DATA_DIR			= "H:"+SEPAR+"My Documents"+SEPAR+"tmp"+SEPAR+"clima-eucr-out-data" + SEPAR;
	
	
	public static final String WORKSPACE_EUCR_INP_DATA_DIR			= PROJECT_CONFIG_DIR 	+ "clima-eucr-inp-data" + SEPAR;
	
	public static String EUCR_REG 									= ax.READ_CONFIG_FILE("TEST_REGISTRY");
	public static void set_EUCR_REG(String NEW_REG){ 				EUCR_REG = NEW_REG;}
	public static String get_EUCR_REG(){							return EUCR_REG;}
	
	public static String get_EUCR_URL_HOMEPAGE(){					return applicationDoman+"/"+get_EUCR_REG()+"/index.xhtml"; }
	public static String get_EUCR_URL_HOMEPAGE(String REG){					return applicationDoman+"/"+REG+"/index.xhtml"; }

	public static final String USER_TEST_USER_FLOW					= ax.GET_USER_NAME("USR");
	
	public static final String EUCR_ENV 							= ax.READ_CONFIG_FILE("TEST_ENVRMENT");
	public static String get_EUCR_ENV(){							return EUCR_ENV;}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public static final String EUCR_BROWSER							= System.getProperty("TEST_BROWSER"); // read from commandline 
//	public static String BROWSER 									= ax.READ_CONFIG_FILE("TEST_BROWSER");
//	public static String EUCR_BROWSER									= ax.READ_CONFIG_FILE("TEST_BROWSER"); //BROWSER 
//	public static final String EUCR_BROWSER2							= ax.READ_CONFIG_FILE("TEST_BROWSER2"); // 
//	public static final String EUCR_BROWSER3							= ax.READ_CONFIG_FILE("TEST_BROWSER3"); //
//	public static final String EUCR_BROWSER4							= ax.READ_CONFIG_FILE("TEST_BROWSER4"); //
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
	public static final String LOGIN_SMS_ENABLED					= ax.READ_CONFIG_FILE("LOGIN_SMS_ENABLED"	);
	public static final String SIGNATURE_ENABLED					= ax.READ_CONFIG_FILE("SIGNATURE_ENABLED");
	
	public static final String TYPE_OF_RUN 							= ax.READ_CONFIG_FILE("TYPE_OF_RUN"); //"DEMO";//  "SANITY", "FULL"
	public static final String TIMEOUT_APPLY 						= "NO"; // [YES|NO]
//	public static final String registryTest 						= ax.READ_CONFIG_FILE("TEST_REGISTRY");
	public static final String EnvironmentType 						= ax.READ_CONFIG_FILE("TEST_ENVRMENT");
	public static final String EUCR_USER_DEFAULT_PASSWORD			= ax.READ_CONFIG_FILE("TEST_USER_PASS");	
	public static final String FRAMEWORK_TEST_MODE					= ax.READ_CONFIG_FILE("TEST_MODE"); // DEBUG, NODEBUG
	
	public static final String OUTPUT_PROP_FILE_DATA_OUT			= WORKSPACE_EUCR_OUT_DATA_DIR + "";
	public static final String OUTPUT_RUN_DIRECTORY					= OUTPUT_PROP_FILE_DATA_OUT + TEST_SESSION_ID + SEPAR;
	public static final String OUTPUT_CONSOLE_LOGGING_FILE			= OUTPUT_RUN_DIRECTORY +"CONSOLE_OUT_"+TEST_SESSION_ID+".log";
	public static final String REPORT_TESTNG_HTML_URL 				= OUTPUT_RUN_DIRECTORY + "TESTNG_REPORT" +SEPAR+ "index.html";	
	public static final String REGULAR_GRID							= "REGULAR";	// [REGULAR|GRID]
	public static final String HIDE_LOGGER_LEVEL					= "HIDE";	// [REGULAR|GRID]
//	DOWNLOAD - PATH configuration ================================
	public static final String OUTPUT_PDF_DIRECTORY 				= DIR_TEMP_OS_PATH + 	"selenium_download" + SEPAR;
	public static final String OUTPUT_FILE_NAME_ACCOUNT_STATEMENT 	= "accountStatement.pdf";
	public static final String OUTPUT_FILE_NAME_ACC_STAT_FULL_PATH 	= OUTPUT_PDF_DIRECTORY + OUTPUT_FILE_NAME_ACCOUNT_STATEMENT;  
//	OUTPUT DIR - XML files for uploading
	public static final String OS_TEMPORARY_DIR_OUTPUT				= DIR_TEMP_OS_PATH +	"eucr_out" + SEPAR;
	
	public static final String UNBLOCKING_ACCOUNT_FACTOR			= "NO";
	
//	==============================================================
	
//	[xyshio:hide line below in case of performance test]
//	public static final String TEST_FILE_RUN_PARAMS_PROPERTIES 	= DIR_FRAMEWORK_PATH + "\\runParams.properties";
//	public static final String TEST_FILE_RUN_PARAMS_PROPERTIES 	= DIR_FRAMEWORK_PATH + "\\runParams_"+browserType+".properties";
//	[xyshio:uncomment this line below in case of performance test]
//	public static final String TEST_FILE_RUN_PARAMS_PROPERTIES 	= DIR_FRAMEWORK_PATH + "\\runParams"+arg4run+".properties";
	
	public static final String ECAS_URL_LOGIN 					= AppGetter.get_ECAS_URL_LOGIN();  //"https://webgate.ec.europa.eu/ecas/login.cgi";
	public static final String ECAS_URL_LOGOUT 					= AppGetter.get_ECAS_URL_LOGOUT(); //"https://webgate.ec.europa.eu/ecas/logout.cgi";

	public static final String applicationDoman					= AppGetter.getApplicationURL(EUCR_ENV);
	public static final String TEST_SESSION_ID_DIR			    = OUTPUT_PROP_FILE_DATA_OUT + TEST_SESSION_ID ;
	public static final String TESTNG_REPORT_OUT_DIR			= OUTPUT_RUN_DIRECTORY + "TESTNG_REPORT";	
	public static final String LOCATION_TMP_DATA_FILE			= OUTPUT_RUN_DIRECTORY + "TMP_DATA_FILE_" + TEST_SESSION_ID + ".properies"; 
	public static final int TIME_FOR_CLICK_FUNCTIONS 			= 60;  
	public static final int TIME_FOR_WAITING_FOR_ELEMENT		= 50;  
	public static final int TIME_FOR_LOADING_ELEMENT			= 60;  
	public static final int TIME_FOR_CHECKING_ELEMENT			= 30;  
//  SCREENSHOT DIRECTORY OUT
//	=> Screenshot location in workspace	
//	public static final String SCREENSHOT_FILE_DIRECTORY        = WORKSPACE_ROOT_DIRECTORY + "test-output\\ScreenShotDirectory";				// work
//	=> Screenshot location in run output directory 	
	public static final String SCREENSHOT_FILE_DIRECTORY        = TESTNG_REPORT_OUT_DIR + SEPAR +"test-output"+SEPAR +"ScreenShotDirectory";				// work	
	
	public static final String WEBDRIVERS_DIRECTORY				= "C:"+SEPAR+"PGM"+SEPAR+"webdrivers"+SEPAR;
//	public static final String SCREENSHOT_FILE_DIRECTORY        = WORKSPACE_ROOT_DIRECTORY + "clima-eucr\\test-output\\ScreenShotDirectory";				// laptop	
//	public static final String EXE_BROWSER_FILE_DIRECTORY       = WORKSPACE_ROOT_DIRECTORY + "libs";														// laptop	
	
	
	public static final String ECAS_DOMAIN_EC 					= "ec";  
	public static final String ECAS_DOMAIN_EC_LABEL				= "European Commission";  
	public static final String ECAS_DOMAIN_EXTERNAL 			= "external";  
	public static final String ECAS_DOMAIN_EXTERNAL_LABEL		= "External";  
	public static final String EUCR_MOBILE_PHONE_NUMBER			= "+32487969822";  
//	public static final String EUCR_MOBILE_TWILIO_ONE			= ax.READ_CONFIG_FILE("EUCR_MOBILE_TWILIO_1"	); 
//	public static final String EUCR_MOBILE_TWILIO_TWO			= ax.READ_CONFIG_FILE("EUCR_MOBILE_TWILIO_2"	); 
//	public static final String EUCR_MOBILE_TWILIO_TRE			= ax.READ_CONFIG_FILE("EUCR_MOBILE_TWILIO_3"	); 
			  

	public static final String EUCR_URL_HOMEPAGE_EU				= applicationDoman+"/EU/index.xhtml";
	public static String EUCR_URL_HOMEPAGE 				= applicationDoman+"/"+get_EUCR_REG()+"/index.xhtml";
//	public static String EUCR_URL_TASKLIST 				= applicationDoman+"/"+get_EUCR_REG()+"/protected/tasks/tasklist.xhtml";
//	public static String EUCR_URL_ACCOUNTS 				= applicationDoman+"/"+get_EUCR_REG()+"/protected/accounts/accountsListView.xhtml";
//	public static String EUCR_URL_ACCOUNT_REQUESTS 		= applicationDoman+"/"+get_EUCR_REG()+"/public/accounts/requestAccountOpeningDetails.xhtml";
		
	
//#[ OUTPUT DATA PROPERTIES_FILES - CLEAR THEM BEFORE FRESH TEST RUN]#########################################################################################	
	public static final String TEST_FILE_OUT_OPEN_ACC_REQUESTS	= "openAccRequests";
	public static final String TEST_FILE_OUT_ACCOUNT_NUMBER 	= "approvedAccountNumbers";
	public static final String TEST_FILE_OUT_ACCOUNT_HOLDER_ID 	= "approvedAccountHolderId";
//#[ OUTPUT DATA PROPERTIES_FILES]#########################################################################################	
	
	public static final String TEST_CHANGE_REGISTRY_EU_SHORT 	= "EU";
	public static final String TEST_CHANGE_REGISTRY_EU2EU_LABEL = "EU";
	public static final String TEST_CHANGE_REGISTRY_PL_SHORT 	= "PL";
	public static final String TEST_CHANGE_REGISTRY_EU2PL_LABEL = "Poland";
	public static final String TEST_CHANGE_REGISTRY_DE_SHORT 	= "DE";
	public static final String TEST_CHANGE_REGISTRY_EU2DE_LABEL = "Germany";
	public static final String TEST_CHANGE_REGISTRY_GR_SHORT 	= "GR";
	public static final String TEST_CHANGE_REGISTRY_EU2GR_LABEL	= "Greece";
	
	public static final String EUCR_VERSION_NR = ax.READ_CONFIG_FILE("APP_VERSION_EUCR").trim();
	public static final String EUCR_ENVIRONMENT = ax.READ_CONFIG_FILE("TEST_ENVRMENT").trim();
	
	public static String EXCEL_FILE_LOCATION				= OUTPUT_RUN_DIRECTORY + EUCR_VERSION_NR+"_EUCR_RESULTS_SELENIUM_RUN_EUCR_"+EUCR_ENVIRONMENT+"_"+TEST_SESSION_ID+".xls";
	public static boolean EXCEL_FILE_INIT					= ExcelJxl.createFileBookAndWriteHeader(EXCEL_FILE_LOCATION, SeleniumDriver.getCurrentBrowserType(), "TITLE", "RESULTS", "COMMENTS");
	
	
//	public static final String TEST_FILE_NAME_OPEN_ACC_DATA 	= OUTPUT_PROP_FILE_DATA_COFIG + "addingNewAccount_"+EUCR_REG+"_"+EnvironmentType+".properties";
	

//	public static final String TEST_AM_URID_PL_ONE 				= "PL389163748032";
//	public static final String TEST_AM_URID_PL_TWO 				= "PL649741117960";
//	public static final String TEST_AM_URID_PL_THREE 			= "PL790247114083";
//
//	public static final String TEST_AM_URID_GR_ONE 				= "GR742470794774"; // 1st NA
//	public static final String TEST_AM_URID_GR_TWO 				= "GR869112767519"; // 2nd NA
//	public static final String TEST_AM_URID_GR_THREE 			= "GR502367148376"; // Regular - gumulkr
	
	public static final String TEST_AM_URID_EU_ONE 				= "EU---";
	public static final String TEST_AM_URID_EU_TWO 				= "EU---";
	public static final String TEST_AM_URID_EU_THREE 			= "EU---";
	
	// CREATING NEW ACCOUNTS
	public static final int TEST_COUNT_NR_FOR_NEW_OHAS			= 1;
	
	public static final String ACCOUNT_OPENING_GROUP = "AUTO";
//	public static final String ACCOUNT_OPENING_GROUP = "AUTO";
	
	public static final String PRPOFILE_NAME_ADDING_NEW_OHA_ACCOUNT 	= "ADDING_NEW_OHA_ACCOUNT";
	public static final String PRPOFILE_NAME_ADDING_NEW_AOHA_ACCOUNT 	= "ADDING_NEW_AOHA_ACCOUNT";
	public static final String PRPOFILE_NAME_ADDING_NEW_PHA_ACCOUNT 	= "ADDING_NEW_PHA_ACCOUNT";
	public static final String PRPOFILE_NAME_ADDING_NEW_VER_ACCOUNT 	= "ADDING_NEW_VER_ACCOUNT";
	
	
	public static final int TEST_WAITER__ 								= 10;
	public static final int TEST_CHROME_WAITER_							= 1;
	public static final int TEST_RETRIAL_FACTOR 						= 20;
	public static final int TEST_RETRIAL_FACTOR_5 						= 5;
	public static final int TEST_RETRIAL_FACTOR_10 						= 10;
	public static final int TEST_RETRIAL_FACTOR_20 						= 20;
	public static final int TEST_RETRIAL_FACTOR_30 						= 30;
	public static final int TEST_RETRIAL_FACTOR_40 						= 40;
	public static final int TEST_RETRIAL_FACTOR_60 						= 60;
	public static final int TEST_RETRIAL_FACTOR_100 					= 100;
	public static final int TEST_RETRIAL_FACTOR_120 					= 120;
	public static final int TEST_RETRIAL_FACTOR_180 					= 180;
	public static final int TEST_RETRIAL_FACTOR_240 					= 240;
	public static final int TEST_RETRIAL_FACTOR_300 					= 300;
	
	
	public static final long TEST_DEFAULT_1SEC_BREAK					= 1000;
	public static String EUCR_REGISTRY_TRADING_ACCOUNT 					= ax.READ_CONFIG_FILE("EUCR_REGISTRY_TRADING_ACCOUNT_"+get_EUCR_REG()+"_"+EUCR_ENV); 
	public static final String EUCR_REGISTRY_TRADING_ACCOUNT_IDENTIFIER	= ApplicationPageUtils.getAccNrPartFromFullAccountNumber(EUCR_REGISTRY_TRADING_ACCOUNT); 
	
	public static String PHA_NUMBER_FULL 	= ax.READ_CONFIG_FILE("EUCR_REGISTRY_PHA_NUMBER_"+ProjectDataVariables.get_EUCR_REG()+"_"+ProjectDataVariables.EUCR_ENV);
	
	public static final int TIMEX_FX_5	=	5;
	public static final int TIMEX_FX_8	=	8;
	public static final int TIMEX_FX_10	=	10;
	public static final int TIMEX_FX_15	=	15;
	public static final int TIMEX_FX_20	=	20;
	public static final int TIMEX_FX_25	=	25;
	public static final int TIMEX_FX_30	=	30;
	public static final int TIMEX_FX_45	=	45;
	public static final int TIMEX_FX_60	=	60;
	
	public static final int REPEATIVE_LIMITER = 50;
	
	public static final String EUTL_TABLE_PAGINATOR_LIMIT_ACTION 	= "OFF";
	public static final int EUTL_TABLE_PAGINATOR_LIMITER 		= 5;
	
	public static final int MINIMUM_VALUE_TO_DO_SURRENDERS				= 5;  
	public static final String ENTITLEMENT_VALUE_TO_AUTOMATION_TEST		= "200";  
	public static final String NAT_NAVAT_VALUE_TO_AUTOMATION_TEST		= "200";  

//	public static final String EUCR_FILEPATH_ACCS_TO_TEST_PERFORMANCE 	= "C:"+SEPAR +"tmp"+SEPAR +"AccountsToImport"+arg4run+".txt";
//	public static final String EUCR_FILEPATH_ACCS_TO_TEST_PERFORMANCE_1 = "C:\\temp\\AccountsToImport_1.txt";
//	public static final String EUCR_FILEPATH_ACCS_TO_TEST_PERFORMANCE_2 = "C:\\temp\\AccountsToImport_2.txt";
//	public static final String EUCR_FILEPATH_ACCS_TO_TEST_PERFORMANCE_3 = "C:\\temp\\AccountsToImport_3.txt";
//	public static final String EUCR_FILEPATH_ACCS_TO_TEST_PERFORMANCE_4 = "C:\\temp\\AccountsToImport_4.txt";
	
	public static final int MAX_REP_ACCOUNT							=6; // Value from properties file - defined per registry and the default!!
	public static final int MAX_ADD_REP_ACCOUNT						=10; // Value from properties file - defined per registry and the default!!

	
	
//	public static final String FIREFOX_EXE_LOCATION						= "C:\\temp\\firefox\\FirefoxPortable\\FirefoxPortable.exe"; 
	public static final String FIREFOX_EXE_LOCATION						= ax.READ_CONFIG_FILE("FIREFOX_EXE_LOCATION");  
	public static final String FIREFOX_EXE_PROC_NAME					= ax.READ_CONFIG_FILE("FIREFOX_EXE_PROC_NAME");
	public static final String FIREFOX_S3_EXE_LOCATION 					= ax.READ_CONFIG_FILE("FIREFOX_S3_EXE_LOCATION");
	public static final String FIREFOX_S3_EXE_PROC_NAME					= ax.READ_CONFIG_FILE("FIREFOX_S3_EXE_PROC_NAME");

	// THIS CHROMIUM VERSION WORKS WITHOUT CHROME-POPUP-UNZIP-EXTENTION
	public static final String CHROME_EXE_LOCATION						= ax.READ_CONFIG_FILE("CHROME_EXE_LOCATION"); //"C:\\temp\\chrome\\chrome-win32\\chrome.exe"; // ten dziala ok
	public static final String CHROME_EXE_PROC_NAME						= ax.READ_CONFIG_FILE("CHROME_EXE_PROC_NAME");//"chrome.exe"; 
	public static final String CHROMEDRIVER_EXE_PROC_NAME				= ax.READ_CONFIG_FILE("CHROMEDRIVER_EXE_PROC_NAME");//"chromedriver.exe"; 
	
	public static final String OPERA_EXE_LOCATION						= "C:\\Program Files (x86)\\Opera\\launcher.exe";
	public static final String OPERA_EXE_PROC_NAME						= "opera.exe";
	public static final String OPERADRIVER_EXE_PROC_NAME				= "operadriver.exe";
	
	public static final String FALLBACK_LOGIN_CLICK_ENABLE				= "YES";
	
	
	// LOGIN-SCENARIO
	public static final ArrayList<String> LGIN_PAIR__AR_NA  					= new ArrayList<String>(Arrays.asList("AR1"	,"NA2"));
	public static final ArrayList<String> LGIN_PAIR__AAR_NA  					= new ArrayList<String>(Arrays.asList("AAR"	,"NA2"));
	public static final ArrayList<String> LGIN_PAIR__NA_NA  					= new ArrayList<String>(Arrays.asList("NA1"	,"NA2"));
	public static final ArrayList<String> LGIN_PAIR__CA_CA  					= new ArrayList<String>(Arrays.asList("CA1"	,"CA2"));
	
	
	
//	USERS
	public static final String USER_USER_1 								= ax.READ_CONFIG_FILE("TEST_USER_USER_1"); // eucrauto_ar1
	public static final String USER_USER_2 								= ax.READ_CONFIG_FILE("TEST_USER_USER_2"); // eucrauto_ar1
//	public static String USER_USER_1 = UsersDataInput.GET_LOGIN_FROM_PROPERTIES("TEST_USER_USER_1");
	
//	##################################################################################
//	EUTL CONFIGURATION #########################################################
//	##################################################################################
	public static final String EUTL_LOGIN_PAGE						= 	AppGetter.get_EUTL_URL();
	public static final String EUTL_PUBL_LOGIN_PAGE					= 	AppGetter.get_EUTL_PUBL_URL();
	public static final String EUTL_LOGIN_LOGIN						= 	AppGetter.get_EUTL_LOGIN();
	public static final String EUTL_LOGIN_PASS						= 	AppGetter.get_EUTL_PASSWORD();
//	##################################################################################
//	#######################################################################################################################
//	###[ CHANGES RELATED FROM THE REGISTRY ]###############################################################################
	
	public static final ArrayList<String> EMISSION_CountryWithMultiEmissionInputForOHA 		= new ArrayList<String>(Arrays.asList("BG")); 
	public static final ArrayList<String> EMISSION_CountryWithSingleEmissionInputForOHA 		= new ArrayList<String>(Arrays.asList("HR")); 
	
//	Upload emission test case
//	BG
//	public static final ArrayList<String> VerifiedEmissionValues = new ArrayList<String>(Arrays.asList("2","2","2"));
//	HR
	public static final ArrayList<String> VerifiedEmissionValues = new ArrayList<String>(Arrays.asList("2","0","0"));
//	manualy enter emission test case
//	BG
//	public static final ArrayList<Integer> EMISSION_INPUT_OHA 		= new ArrayList<Integer>(Arrays.asList(1,1,3));
//	public static final ArrayList<Integer> EMISSION_UPD_INPUT_OHA 	= new ArrayList<Integer>(Arrays.asList(5,5,5));
//	public static final ArrayList<Integer> EMISSION_INPUT_AOHA 		= new ArrayList<Integer>(Arrays.asList(2,2));
//	public static final ArrayList<Integer> EMISSION_UPD_INPUT_AOHA 	= new ArrayList<Integer>(Arrays.asList(5,5));
//	HR
	public static final ArrayList<Integer> EMISSION_INPUT_OHA 		= new ArrayList<Integer>(Arrays.asList(2,0,0));
	public static final ArrayList<Integer> EMISSION_UPD_INPUT_OHA 	= new ArrayList<Integer>(Arrays.asList(5,0,0));
	public static final ArrayList<Integer> EMISSION_INPUT_ZERO_OHA	= new ArrayList<Integer>(Arrays.asList(0,0,0));
	public static final ArrayList<Integer> EMISSION_INPUT_AOHA 		= new ArrayList<Integer>(Arrays.asList(3,2));
	public static final ArrayList<Integer> EMISSION_UPD_INPUT_AOHA 	= new ArrayList<Integer>(Arrays.asList(4,2));
	public static final ArrayList<Integer> EMISSION_INPUT_ZERO_AOHA	= new ArrayList<Integer>(Arrays.asList(0,0));
//	BG-TEST

	
	
	// [CP2][KP2]
	public static final String KP_CER_TEST_BG = ax.READ_CONFIG_FILE("KP_CER_"+ ProjectDataVariables.EUCR_ENV +"_"+ get_EUCR_REG()); // MX39 
	// THIS PARAMETER IS FOR BG IN TEST ENVIRONMENT
	public static final String KP_CERS_PHA_BG = "CL20";
	public static final String KP_CER_TEST_HR = "GD46";
	
	public static final String KP_CER_DEV_BG = "CN873";
	public static final String KP_CER_DEV_HR = "???";

	public static String GET_KYOTO_CER_UNIT_PROJECT(String ENV, String REG){
		switch (ENV) {
		case "TEST":
		case "TEST2":
			if(REG.equals("BG")){ return KP_CER_TEST_BG;}
			if(REG.equals("HR")){ return KP_CER_TEST_HR;}
		case "LA":
			if(REG.equals("BG")){ return KP_CER_TEST_BG;}
			if(REG.equals("HR")){ return KP_CER_TEST_HR;}
		case "DEV":
			if(REG.equals("BG")){ return KP_CER_DEV_BG;}
			if(REG.equals("HR")){ return KP_CER_DEV_HR;}
		}
	return null;
	}
	
	
	public static final String FILE_LOCATION_FOR_ACCOUNTS2AUTO_TEST 		= ax.getProp("DIR_TMP_LOCATION") + "eucr_out\\accgen.txt";
	public static final String FILE_LOCATION_FOR_ACCOUNTS2AUTO_HAS_AAR 		= ax.getProp("DIR_TMP_LOCATION") + "eucr_out\\accgen_HAS_AAR.txt";
	public static final String FILE_LOCATION_FOR_ACCOUNTS2AUTO_NO_AAR 		= ax.getProp("DIR_TMP_LOCATION") + "eucr_out\\accgen_NO_AAR.txt";
	
	public static String KYOTO_CER_UNIT_PROJECT								= 	ProjectDataVariables.GET_KYOTO_CER_UNIT_PROJECT(ProjectDataVariables.EUCR_ENV, ProjectDataVariables.get_EUCR_REG());
//	BG-DEV
//	public static final String KYOTO_CER_UNIT_PROJECT						= 	"CN873";
//	HR-TEST
//	public static final String KYOTO_CER_UNIT_PROJECT						= 	"GD46";
	
	
	
	
//	###[ CHANGES RELATED FROM THE REGISTRY:END ]###############################################################################
//	#######################################################################################################################
	
/* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Other things to be prepared before running Selenium on different Environment
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  - Account Holder Name for "VERIFIER ACCOUNT" has to have name: ADMIN_VERIFIER_ACCOUNT_HOLDER [test: test_ETS_3105_AR_CANNOT_SEE_VER_DETAILS]
 *  	query: "select IDENTIFIER from account where ACCOUNT_HOLDER_ID = (select id from ACCOUNT_HOLDER WHERE name = 'ADMIN_VERIFIER_ACCOUNT_HOLDER' and REGISTRY_CODE = 'HR')" 
 *  	has to return AccountIdentifier
 *  - This "VERIFIER ACCOUNT" has assigned as it's AR user who is currently verifier users [AppGetter.GET_VERIFIER_ACC_NR]!
 * 
 * 
 * 
 * 
 * 
 * 
 * */	
	
	public static final String AUCTION_DELIVERY_ACCOUNT_DEV_IDENTIFIER						= "5026982"; 
	public static final String AUCTION_DELIVERY_ACCOUNT_TEST_IDENTIFIER						= "5000187"; 
	public static final String AUCTION_DELIVERY_ACCOUNT_QA2_IDENTIFIER						= "NOTSET"; 
	public static final String TEST_BULLET_STRING1		= ">>>"; 

	
    public static String GetExcelFileLocation_TestResults(String TestSessId){
    	return EXCEL_FILE_LOCATION;
    }
	
	
//#####[ ESD  ]###################################################	
	public static final String ESD_INITIAL_ISSUANCE_LIMIT 									= "1000000"; 
	
	public static final boolean IS_LOGIN_SMS_ENABLED(){
		if(ProjectDataVariables.LOGIN_SMS_ENABLED.equals("YES")){
			return true;
		} return false;
	}
	public static final boolean IS_SIGNATURE_ENABLED(){
		if(ProjectDataVariables.SIGNATURE_ENABLED.equals("YES")){
			return true;
		} return false;
	}
	
//	public static HashMap<String, String> xUSERS()	  {
//		HashMap<String, String> eucr_auto_users = new HashMap<String, String>();
//		eucr_auto_users.put("AR1","eucrauto_ar1");
//		eucr_auto_users.put("AR2","eucrauto_ar2");
//		eucr_auto_users.put("AR3","eucrauto_02");
//		eucr_auto_users.put("AR4","eucrauto_01");
//		eucr_auto_users.put("AR5","eucrauto_03");
//		eucr_auto_users.put("AR6","eucrauto_04");
//		eucr_auto_users.put("AR7","eucrauto_05");
//		eucr_auto_users.put("AAR1","eucrauto_02");
//		eucr_auto_users.put("NA","eucrauto_na1");
//		eucr_auto_users.put("NA1","eucrauto_na1");
//		eucr_auto_users.put("NA2","eucrauto_na2");
//		eucr_auto_users.put("CA1","eucrauto_ca1");
//		eucr_auto_users.put("CA2","eucrauto_ca2");
//		return eucr_auto_users; 
//	  }	 
	
	public static String GET_ACCOUNT_HOLDER_FOR_ENV_REG(){ 
		// Name of the Account Holder: ADMIN_HOLDER_FOR_HOLDING_ACCOUNT [OHA, OPEN]
		switch (ProjectDataVariables.EUCR_ENV) {
		case "TEST": // ==================================================
			switch (ProjectDataVariables.EUCR_REG) {
			case "BG": return "21801";
			default:break;
			}
		case "DEV": // ==================================================
			switch (ProjectDataVariables.EUCR_REG) {
			case "BG": return ""; // not set yet
			default:break;
			}
		}
		 // ==================================================
	return null;
	}	
	
//================================================	
//	Page Object Way
	
	public static final String EUCR_PASSWORD 	= "Welcome3333";
	//======[ EUCR-TEST ]==========================================
//	public static final String EUCR_USER_NA1 	= "nnaxonna|NA1|BG647696890865:GB562949279975:EU411181278893";
//	public static final String EUCR_USER_NA2 	= "ncoreeab|NA2|BG122880348193:GB869730895766:EU218768720910";
//	public static final String EUCR_USER_CA1 	= "nautomca|CA1|BG449173661479:GB350595339381:EU957758308958";
//	public static final String EUCR_USER_CA2 	= "nautocat|CA2";
//	public static final String EUCR_USER_CA3 	= "nautcath|CA3";
//	public static final String EUCR_USER_AR1 	= "n001z6an|AR1";
//	public static final String EUCR_USER_AR2 	= "n001z6cq|AR2";
//	public static final String EUCR_USER_AAR1 	= "n001z6d4|AAR1";
	//================================================================
	
	public static final String EUCR_USER_NA1		= "ntestnao|NA1	|BG828679869162";
	public static final String EUCR_USER_NA2		= "ntestnat|NA2	|BG213616347689";
	public static final String EUCR_USER_AR1		= "ntestaro|AR1	|BG335511629496";
	public static final String EUCR_USER_AR2		= "ntestart|AR2	|BG128351061905";
	public static final String EUCR_USER_AAR1		= "ntestaar|AAR1|BG317181899135";
	public static final String EUCR_USER_AAR2		= "ntesaart|AAR2|BG986091393065";
	public static final String EUCR_USER_CA1		= "ntestcao|CA1	|BG591298480369";
	public static final String EUCR_USER_CA2		= "ntestcat|CA2	|BG352732968611";
	public static final String EUCR_USER_CA3		= "ntescatr|CA3	|BG129982886078";
	public static final String EUCR_USER_VER		= "ntestver|VER1|BG944992835916";
	public static final String EUCR_USER_USR		= "eucruser|USR	|BG677095682288";
	
	
	
	
	public static HashMap<String, String> userMatrix(){
		HashMap<String, String> hmap = new HashMap<String, String>();	
		hmap.put("NA1", EUCR_USER_NA1);
		hmap.put("NA2", EUCR_USER_NA2);
		hmap.put("CA1", EUCR_USER_CA1);
		hmap.put("CA2", EUCR_USER_CA2);
		hmap.put("CA3", EUCR_USER_CA3);
		hmap.put("AR1", EUCR_USER_AR1);
		hmap.put("AR2", EUCR_USER_AR2);
		hmap.put("AAR1", EUCR_USER_AAR1);
		return hmap;
	}
	public static String getUserName(String shortName){
		return userMatrix().get(shortName).split("\\|")[0].trim();
	}
	
	public static String getUserUrid(String shortName){
		//return getUserUrid(shortName, ProjectDataVariables.EUCR_REG);
		return ax.GET_URID_BY_FX_MS(shortName, ProjectDataVariables.EUCR_REG);
		
		
	}
	
	public static String getUserUrid(String shortName, String eucrReg){
		String urids =  userMatrix().get(shortName).split("\\|")[2].trim();
		String[] uridsArray = urids.split(":");
		String firstResult = Arrays.stream(uridsArray)
                .filter(x -> x.startsWith(eucrReg))
                .findFirst()
                .orElse(null);
		return firstResult;
	}
	
public static String getAccountTypeByShortName(String shortName){
	HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("OHA", "Operator holding account");
		hmap.put("AOHA", "Aircraft operator holding account");
		hmap.put("PHA", "Party holding account");
		hmap.put("NAHA", "National holding account");
		hmap.put("FOHA", "Former Operator holding");
		hmap.put("PERS", "Person holding account");
		hmap.put("ADA", "Auction Delivery Account");
		hmap.put("TRAD", "Trading Account");
		hmap.put("VER", "Verifier Account");
		hmap.put("EXTRAD", "External Trading Platform Account");
		hmap.put("PPSR", "Previous Period Surplus Reserve Account (PPSR)");	  
		
		
		
		
		
      return hmap.get(shortName).trim();
}	
	
}