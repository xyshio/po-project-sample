package utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ElementRepository.RepoEucr;
import utils.ProjectDataVariables;
import clima.testsets.TEST_RUN_DATA_ENTRY;
import eu.ets.eucr.accmgmt.AM_AccountOperations;
import eu.ets.eucr.allocations.AllocationOperations;
import eu.ets.eucr.compliance.VerifierOperations;
import eu.ets.eucr.login.LoginToEUCR;
import eu.ets.sql.AppSqlQueries;


public class AppGetter {
	public static final String EUCR_REG 							= ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE,"TEST_REGISTRY");
	public static final String EUCR_ENV 							= ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE,"TEST_ENVRMENT");

	
	public static HashMap<String, String> eucr_country_names(){
		HashMap<String, String> myMap = new HashMap<String, String>();
		myMap.put("BG",	"Bulgaria");
		myMap.put("EU",	"EU");
		myMap.put("PL",	"Poland");
		myMap.put("FI",	"Finland");
		myMap.put("AT",	"Austria");
		myMap.put("GR",	"Greece");
		return myMap;
	}
	
	
	public static final ArrayList<String> EUCRApplicationVersions_UPTO_6_3 = new ArrayList<String>(Arrays.asList("6.1","6.2","6.3"));
	public static final ArrayList<String> EUCRApplicationVersions_UPTO_6_4 = new ArrayList<String>(Arrays.asList("6.1","6.2","6.3","6.4"));
	
	public static final ArrayList<String> EUCR_APP_VERSIONS7 = new ArrayList<String>(Arrays.asList("7.0.1","7.0.2","7.0.3","7.0.4","7.0.5","7.0.6"));
	
	public static String GET_URID_OF_CURRENT_DISPLAYED_USER(WebDriver driver){
		if(ax.isDisplayedBy(driver, RepoEucr.EUCR_CANVAS_CURRENT_USER_URID_NR)){
			return PageCanvas.getAuthGrid_URID(driver);	
		}else{
			return null;
		}
	}
	
	public static String GET_YEAR_CURR(){
		return TEST_RUN_DATA_ENTRY.YEAR_OF_CURRENT_DATE;
	}
	public static String GET_YEAR_PREV(){
		return TEST_RUN_DATA_ENTRY.YEARS_BEFORE_CURRENT_DATE_1;
	}
	public static String GET_YEAR_PREV_PREV(){
		return TEST_RUN_DATA_ENTRY.YEARS_BEFORE_CURRENT_DATE_2;
	}
	
	public static ArrayList<String> GET_ARS_TO_TESTS(){
		return new ArrayList<String>(Arrays.asList(AppGetter.GET_URID_OF_AR1(),AppGetter.GET_URID_OF_AR2()));}
	public static ArrayList<String> GET_AARS_TO_TESTS(){
		return new ArrayList<String>(Arrays.asList(AppGetter.GET_URID_OF_AR3()));}
	
	public static ArrayList<ArrayList<String>> ARS_URID_INPUT 		= new ArrayList<ArrayList<String>>(Arrays.asList(AppGetter.GET_ARS_TO_TESTS(), AppGetter.GET_AARS_TO_TESTS()));
	
	
	public static String GET_URID_OF_AR1(){
		
		if(ax.IS_NOT_NULLEMPTY(	ax.GET_USER_URID("AR1"))){ 
			return ax.GET_USER_URID("AR1"); 
			}
		
		return AppSqlQueries.getURID_ByLoginName(
				LoginToEUCR.LOGIN_EUCR_LOGNAME_REG1
				//ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR1")
				);
	}
	public static String GET_URID_OF_AR2(){
		if(ax.IS_NOT_NULLEMPTY(ax.GET_USER_URID("AR2"))){ return ax.GET_USER_URID("AR2"); }
		return AppSqlQueries.getURID_ByLoginName(
				LoginToEUCR.LOGIN_EUCR_LOGNAME_REG2
				//ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR2")
				);
	}
	public static String GET_URID_OF_AAR1(){ // This guy is used as AAR !!!
		if(ax.IS_NOT_NULLEMPTY(ax.GET_USER_URID("AAR1"))){ return ax.GET_USER_URID("AAR1"); }
		return GET_URID_OF_AR3();
	}
	public static String GET_URID_OF_AR3(){ // This guy is used as AAR !!!
		if(ax.IS_NOT_NULLEMPTY(ax.GET_USER_URID("AAR1"))){ return ax.GET_USER_URID("AAR1"); }
		return AppSqlQueries.getURID_ByLoginName(
				LoginToEUCR.LOGIN_EUCR_LOGNAME_REG3
				//ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR3")
				);
	}

	
	
	public static String GET_URID_OF_AR10(){ // This guy is used as AR NOT RELATED
		return AppSqlQueries.getURID_ByLoginName(ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR10"));
	}
	public static String GET_URID_OF_AR11(){ // This guy is used as AR NOT RELATED
		return AppSqlQueries.getURID_ByLoginName(ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR11"));
	}
	public static String GET_URID_OF_AR12(){ // This guy is used as AAR NOT RELATED
		return AppSqlQueries.getURID_ByLoginName(ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR12"));
	}
	
	
	public static String GET_USER_NAME_FOR_AR(){
		return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR1");
	}
	public static String GET_USER_NAME_FOR_AAR(){
		return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "TEST_USER_AR3");
	}
	public static String GET_URID_OF_NA1(){ 		return AppSqlQueries.getURID_ByLoginName(LoginToEUCR.LOGIN_EUCR_LOGNAME_NA1); 	}
	public static String GET_URID_OF_NA2(){			return AppSqlQueries.getURID_ByLoginName(LoginToEUCR.LOGIN_EUCR_LOGNAME_NA2); 	}
	public static String GET_URID_OF_CA1(){			return AppSqlQueries.getURID_ByLoginName(LoginToEUCR.LOGIN_EUCR_LOGNAME_CA1); 	}
	public static String GET_URID_OF_CA2(){			return AppSqlQueries.getURID_ByLoginName(LoginToEUCR.LOGIN_EUCR_LOGNAME_CA2); 	}
	public static String GET_URID_OF_VER1(){		
//		if(ax.isSQLEnabled()){
//			return AppSqlQueries.getURID_ByLoginName(LoginToEUCR.LOGIN_EUCR_LOGNAME_VER1);
//		}else{
			return ax.GET_USER_URID("VER1");
//		}
		
	}
	
	public static String GET_VERIFIER_ACC_NR(){
		if(ax.isSQLEnabled()){
			return AppSqlQueries.getVerifierADM_AccountIdentifier();
		}else{
			return VerifierOperations.GET_VERIFIER_ACCOUNT_ID();
		}

	}
	
	public static String GET_DB_PASS(String ENV) throws SQLException {
		switch (ENV) {
		case "TEST":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_TEST");	
		case "TEST2":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_TEST2");	
		case "DEV":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_DEV");	
		case "SHS":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_SHS");	
		case "UT":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_UT");	
		case "LA":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_LA");	
		case "QA2":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_QA2");	
		default:
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUCR_TEST");	
		}
	}
	public static String GET_DB_LOGIN(String ENV) throws SQLException {
		switch (ENV) {
		case "TEST":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_TEST");	
		case "TEST2":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_TEST2");	
		case "DEV":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_DEV");	
		case "SHS":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_SHS");	
		case "UT":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_UT");	
		case "QA2":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_QA2");	
		case "LA": 
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_LA");	
		default:
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUCR_TEST");
		}
	}
	public static String GET_EUTL_DB_LOGIN(String ENV) throws SQLException, InterruptedException {
		switch (ENV) {
		case "TEST":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUTL_TEST");	
		case "DEV":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUTL_DEV");	
		case "SHS":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUTL_SHS");	
		case "UT":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUTL_UT");	
		case "LA":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUTL_LA");	
		case "QA2":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUTL_QA2");	
		default:
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBLOGIN_EUTL_TEST");	
		}
	}
	public static String GET_EUTL_DB_PASS(String ENV) throws SQLException, InterruptedException {
		switch (ENV) {
		case "TEST":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_TEST");	
		case "TEST2":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_TEST2");	
		case "DEV":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_DEV");	
		case "SHS":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_SHS");	
		case "UT":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_UT");	
		case "LA":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_LA");	
		case "QA2":
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_QA2");	
		default:
			return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "DBPASS_EUTL_TEST");	
		}
	}	
	public static final String getApplicationURL(String ENVIRONMENT){
		if (!ENVIRONMENT.isEmpty()){
			switch (ENVIRONMENT) {
			case "TEST"		: return "https://ets-registry-test.tech.ec.europa.eu/euregistry";
			case "TEST2"	: return "https://test.unionregistry.ec.europa.eu/euregistry";   //"https://ets-registry-test2.tech.ec.europa.eu/euregistry";
			case "SHS"		: return "https://acc.unionregistry.ec.europa.eu/euregistry";
			case "SHSPROD"	: return "https://unionregistry.ec.europa.eu/euregistry";
			case "SHSUT"	: return "https://ut.unionregistry.ec.europa.eu/euregistry";
			case "DEV"		: return "https://ets-registry-dev.tech.development.ec.europa.eu/euregistry"; //https://ets-registry-dev.tech.development.ec.europa.eu/euregistry
			case "ACC"		: return "https://ets-registry-acc.tech.ec.europa.eu/euregistry";
			case "UT"		: return "https://ets-registry-ut.tech.ec.europa.eu/euregistry";
			case "LA"		: return "http://192.168.3.2:7001/euregistry";
			case "QA2"		: return "http://d02di1429514env:7001/eucr-dev";
			
			}
		}else{
			SeleniumWebUtils.reportFail("Environment from property file NOT SET!");
			return null;
		}
		return null;
	}	
	
	public static final String get_ECAS_URL_LOGIN(){
		String ENVRMT = ProjectDataVariables.EUCR_ENV;
		switch (ENVRMT) {
		case "TEST": return "https://ecas.acceptance.ec.europa.eu/cas/login.cgi";		//"https://webgate.ec.europa.eu/ecas/login.cgi";
		case "TEST2": return "https://ecas.cc.cec.eu.int:7002/cas/login.cgi"; //"https://webgate.ec.europa.eu/ecas/logout.cgi";
		case "SHS" : return "https://ecas.ec.europa.eu/cas/login.cgi";
		case "DEV" : return "https://ecasa.cc.cec.eu.int:7002/cas/login.cgi";
		case "ACC" : return "https://webgate.ec.europa.eu/cas/login.cgi";
		case "UT" : return "https://webgate.ec.europa.eu/cas/login.cgi";
		case "QA2" : return "https://d02di1429514env:7101/cas/login.cgi";
		case "LA" : return "https://192.168.3.4:7002/cas/login.cgi";
		}
		return null;
	}
	public static final String get_ECAS_URL_LOGOUT(){
		String ENVRMT = ProjectDataVariables.EUCR_ENV;
		switch (ENVRMT) {
		case "TEST": return "https://ecas.ec.europa.eu/cas/logout.cgi"; //"https://webgate.ec.europa.eu/ecas/logout.cgi";	
		case "TEST2": return "https://ecas.cc.cec.eu.int:7002/cas/logout.cgi"; //"https://webgate.ec.europa.eu/ecas/logout.cgi";	
		case "DEV" : return "https://ecasa.cc.cec.eu.int:7002/cas/logout.cgi";//"https://ecast.cc.cec.eu.int:7002/cas/logout.cgi";
		case "SHS" : return "https://ecas.ec.europa.eu/cas/logout.cgi";
		case "ACC" : return "https://webgate.ec.europa.eu/cas/logout.cgi";
		case "UT" : return "https://webgate.ec.europa.eu/cas/logout.cgi";
		case "QA2" : return "https://d02di1429514env:7101/cas/logout.cgi";
		case "LA" : return "https://192.168.3.4:7002/cas/logout.cgi";
		
		
		
		}
		return null;
	}
	
	public static final String getHOMEPAGE_URL(){
		return AppGetter.getApplicationURL(ProjectDataVariables.EnvironmentType) +"/"+ProjectDataVariables.EUCR_REG + "/index.xhtml"; }
	public static final String getAPP_BASE_URL(){
		return AppGetter.getApplicationURL(ProjectDataVariables.EnvironmentType) +"/"+ProjectDataVariables.EUCR_REG + "/"; }
	public static final String getEUCR_HOMEPAGE_URL(String REG){
		return AppGetter.getApplicationURL(ProjectDataVariables.EnvironmentType) +"/"+REG + "/index.xhtml";
	}
	public static final String getSAVED_DATA_PROP_FILE(){
		return ProjectDataVariables.SAVE_FILE_WITH_SESSION_DATA;
	}	
//	@@[ EUTL-PUBLIC ]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public static final String get_EUTL_PUBL_URL(){
		String ENVRMT_CURR = ProjectDataVariables.EUCR_ENV;
		return get_EUTL_PUBL_URL(ENVRMT_CURR);
	}
	public static final String get_EUTL_PUBL_URL(String ENVRMT){
		switch (ENVRMT) {
		case "TEST"		: return "http://wltenv03.cc.cec.eu.int:1041/environment/ets/";	
		case "PROD" 	: return "http://ec.europa.eu/environment/ets/"; // PROD
		case "ACC" 		: return "http://clieut3a.cc.cec.eu.int:1041/environment/ets/";
		case "DEV" 		: return "http://clieut3a.cc.cec.eu.int:1041/environment/ets/"; // OLD:http://clieut3a.cc.cec.eu.int:1041/clima/ets/
		case "LA" 		: return "lack";
		case "UT" 		: return "lack";
		case "QA2" 		: return "lack";
		default			: return "http://ec.europa.eu/environment/ets/"; // PROD
		}
	}

//	@@[ EUTL ]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public static final String get_EUTL_URL(){
		String ENVRMT = ProjectDataVariables.EUCR_ENV;
		switch (ENVRMT) {
		case "TEST"		: return "http://158.167.206.201:1043/citladminweb/"; // 158.167.206.201:1043/	
		case "TEST2"	: return "http://10.57.40.40:1024/citladminweb/";//"http://intragate.test.ec.europa.eu/citladminweb-test2/"; // 158.167.206.201:1043/	
		case "DEV" 		: return "http://clieutld.cc.cec.eu.int:1041/citladminweb/";
		case "UT"  		: return "http://158.167.206.202:1053/citladminweb-ut/";
		case "LA"  		: return "http://192.168.3.3:7001/citladminweb/";
		
		
		
		case "ACC" 		: return "http://158.167.206.202:1043/citladminweb/";		case "QA2" 		: return "http://d02di1429514env:8001/citladminweb-dev/";
		case "PROD"		: return "http://158.167.242.144:1043/citladminweb-prod/";
		}
		return null;
	}
	
	
	public static final String get_EUTL_LOGIN(){
		return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE,"EUTL_LOGIN_"+ProjectDataVariables.EUCR_ENV);	
	}
	public static final String get_EUTL_PASSWORD(){
		return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE,"EUTL_PASS_"+ProjectDataVariables.EUCR_ENV);	
	}
	
	public static final String get_EMISSION_TYPE(){
		return ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE,"EMISSION_TYPE");	
	}	

	public static String GET_TIME_STAMP_FULL_NOW(){
		return DateOperations.GET_UNIQUE_FULL_TIMESTAMP_NOW();
	} 
	
	public static String GET_AUCTION_TABLE_NEXT_DENTIFIER(){
		int max = 0;
		try {
			max = AppSqlQueries.GET_AUCTION_IDENTIFIER_MAX_FROM_AUCTION();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int next = max + 1;
		return Integer.toString(next);
	}
	
	public static String GET_TRAD_ACC_IDENTIFIER(String FULL_OR_ID /* [FULL|ID] */){
		String TradingAccountIdentifierFull 	= ToolFunctions.ReadPropertiesFile(ProjectDataVariables.GRLOBAL_PROP_FILE, "EUCR_REGISTRY_TRADING_ACCOUNT_"+EUCR_REG+"_"+EUCR_ENV);
		String TradingAccountIdentifier 		= ApplicationPageUtils.getAccNrPartFromFullAccountNumber(TradingAccountIdentifierFull);
	if(FULL_OR_ID.contains("FULL")){
		return TradingAccountIdentifierFull; 
	}else{
		return TradingAccountIdentifier;
	}	
	
	}
		
	
	public static String GET_AUCTION_DELIVERY_ACCOUNT(){
		switch (ProjectDataVariables.EUCR_ENV) {
		case "TEST": 	return ProjectDataVariables.AUCTION_DELIVERY_ACCOUNT_TEST_IDENTIFIER;
		case "DEV":		return ProjectDataVariables.AUCTION_DELIVERY_ACCOUNT_DEV_IDENTIFIER;
		case "QA2":		return ProjectDataVariables.AUCTION_DELIVERY_ACCOUNT_QA2_IDENTIFIER;
		}
		return null;
	}
	
	public static String GET_ACCOUNT_TYPE_SHORT_LONG(String AccountType, String form /*SHORT|LONG*/){
		switch (AccountType) {
		case "Trading Account"					: return 	(form.equals("SHORT"))?"TRAD"	:"TRADING";			
		case "Operator holding account"			: return 	(form.equals("SHORT"))?"OHA"	:"OPERATOR_HOLDING";			
		case "Aircraft operator holding account": return 	(form.equals("SHORT"))?"AOHA"	:"AIRCRAFT_OPERATOR";
		case "Party holding account"			: return 	(form.equals("SHORT"))?"PHA"	:"PARTY_HOLDING"; // not exists !!
		default: 			return null;
		}
	}
	public static String GET_NAT_NAVAT(String AccountType){
		switch (AccountType) {
//		case "Trading Account"					: return 	(form.equals("SHORT"))?"TRAD"	:"TRADING";			
		case "Operator holding account"			: return 	"NAT";			
		case "Aircraft operator holding account": return 	"NAVAT";
//		case "Party holding account"			: return 	(form.equals("SHORT"))?"PHA"	:"PARTY_HOLDING"; // not exists !!
		default: 			return null;
		}
	}
	
	
	public static String GET_ACCOUNT_TYPE_BY_IDENTIFIER(String AccountIdentifier, String ShortLong){
		return GET_ACCOUNT_TYPE_BY_IDENTIFIER(null, AccountIdentifier, ShortLong);
	}
	public static String GET_ACCOUNT_TYPE_BY_IDENTIFIER(WebDriver driver, String AccountIdentifier, String ShortLong){
		String ACC_EU_TYPE = null;
		if(ax.isSQLEnabled()){
			ACC_EU_TYPE = AppSqlQueries.get_EU_ACCOUNT_TYPE_ByAccountIdentifier(AccountIdentifier);	
		}else{
			if(!AM_AccountOperations.FindTheAccountById_DisplayResultsInTable(driver, AccountIdentifier)) {
				return (String) ax.returnWhenNull(driver, "Problem with finding Account Type for " + AccountIdentifier);	
			}
			ACC_EU_TYPE = AM_AccountOperations.getTdCellFromAccount1x1(driver, "Type").getText();
			return GET_ACCOUNT_TYPE_SHORT_LONG(ACC_EU_TYPE, ShortLong);

		}
		/////////////////////////////////////////////////////////////////////////
			
		if(ShortLong.equals("SHORT")){
			return (ACC_EU_TYPE.equals("OPERATOR_HOLDING"))?"OHA": ((ACC_EU_TYPE.equals("AIRCRAFT_OPERATOR"))?"AOHA":"OTHER");
		}else{
			return ACC_EU_TYPE;
		}
	}

	public static String GET_NAT_NAVAT_BY_IDENTIFIER(WebDriver driver, String AccountIdentifier){
		String ACC_EU_TYPE = null;
		if(ax.isSQLEnabled()){
			return AllocationOperations.get_NAT_NAVAT_from_AccountIdentifier(AccountIdentifier);	
		}else{
			ax.LG_NA1(driver);
			if(!AM_AccountOperations.FindTheAccountById_DisplayResultsInTable(driver, AccountIdentifier)) {
				return (String) ax.returnWhenNull(driver, "Problem with finding Account Type for " + AccountIdentifier);	
			}
			ACC_EU_TYPE = AM_AccountOperations.getTdCellFromAccount1x1(driver, "Type").getText();
			
			return GET_NAT_NAVAT(ACC_EU_TYPE);
		}
	}
	
	public static String GET_INST_NUMBER_BY_ACC_IDENTIFIER(WebDriver driver, String AccountIdentifier){
	if(ax.isSQLEnabled()){
		return AppSqlQueries.getInstallationNumberByAccountIdentifier(AccountIdentifier);
	}else{
		boolean start = ax.START_AS_LOGGED_USER_READY(driver);
		boolean open = AM_AccountOperations.FindTheAccountById_DisplayResultsInTable(driver, AccountIdentifier);
		if(!start || !open) return (String) ax.returnWhenNull(driver, "Problem getting Installation number form Account Identifier for " + AccountIdentifier);
		String inst = AM_AccountOperations.getTdCellFromAccount1x1(driver, "Installation / Aircraft Operator Identifier").getText();
		return (inst==null || inst.trim().isEmpty()) ? null : inst; 
	}
	}
	
	
	
	public static String GET_ACCOUNT_STATUS(String AccountIdentifier){
		return AppSqlQueries.getStatusOfTheAccountByIdentifier(AccountIdentifier);}
	
	
	public static String GET_USER_FUNCTION_NAME(String FNAME){
		return GET_USER_FUNCTION_NAME(FNAME, "");
	}
	public static String GET_USER_FUNCTION_NAME(String FNAME, String SKIP){
		String ROLE = null;
		if(FNAME.length()>4){
			ROLE = FNAME;
		}else{
			switch (FNAME) {
			case "NONE"	: ROLE = "NONE"; break;
			case "AR"	: 
				if(SKIP.equals("AR")){ 	ROLE = "AR";
				}else{ 					ROLE = "AUTHORISED_REPRESENTATIVE";}
				break;
			case "AAR"	: ROLE = "ADDITIONAL_AUTHORISED_REPRESENTATIVE"; break;
			case "ARV"	: ROLE = "VIEW_ONLY_AR"; break;
			case "AUD"	: ROLE = "AUDITOR_FOR_NA"; break;
			case "CA"	: ROLE = "CENTRAL_ADMINISTRATOR"; break;
			case "SA"	: ROLE = "SYSTEM_ADMINISTRATOR"; break;
			case "NA"	: ROLE = "NATIONAL_ADMINISTRATOR"; break;
			case "SD"	: ROLE = "SERVICE_DESK"; break;
			case "SDA"	: ROLE = "SERVICE_DESK_AGENT"; break;
			case "TRD"	: ROLE = "TRADER"; break;
			case "VER"	: ROLE = "VERIFIER"; break;
			}
		}
		return ROLE;
	}
	
	public static String GET_USERNAME_IF_FX_GIVEN(String GIVEN_VALUE){
		// USERNAME=Should have length more than 4
		// FXNAMEShould have length < 5
		if(GIVEN_VALUE.length()<5){
			return GET_USERNAME_BY_FX(GIVEN_VALUE);
		}
		return GIVEN_VALUE;
	}
	
	public static String GET_USERNAME_BY_FX(String FX){
		HashMap<String, String> eucr_auto_users = new HashMap<String, String>( );
		eucr_auto_users.put("AR",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG1);
		eucr_auto_users.put("AR1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG1);
		eucr_auto_users.put("AR2",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG2);
		eucr_auto_users.put("AR3",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG3);
		eucr_auto_users.put("AR4","eucrauto_01");
		eucr_auto_users.put("AR5","eucrauto_03");
		eucr_auto_users.put("AR6","eucrauto_04");
		eucr_auto_users.put("AR7","eucrauto_05");
		eucr_auto_users.put("AAR1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG3);
		eucr_auto_users.put("VER",	LoginToEUCR.LOGIN_EUCR_LOGNAME_VER1);
		eucr_auto_users.put("VER1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_VER1);
		eucr_auto_users.put("NA",	LoginToEUCR.LOGIN_EUCR_LOGNAME_NA1);
		eucr_auto_users.put("NA1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_NA1);
		eucr_auto_users.put("NA2",	LoginToEUCR.LOGIN_EUCR_LOGNAME_NA2);
		eucr_auto_users.put("CA1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_CA1);
		eucr_auto_users.put("CA2",	LoginToEUCR.LOGIN_EUCR_LOGNAME_CA2);
		eucr_auto_users.put("CA3",	LoginToEUCR.LOGIN_EUCR_LOGNAME_CA3);
		eucr_auto_users.put("USR",	LoginToEUCR.LOGIN_EUCR_LOGNAME_USR);
		return eucr_auto_users.get(FX);
	}
	public static String GET_ESD_USERNAME_BY_FX(String FX){
		HashMap<String, String> eucr_auto_users = new HashMap<String, String>( );
		eucr_auto_users.put("AR",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_AR1"));
		eucr_auto_users.put("AR1",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_AR1"));
		eucr_auto_users.put("AR2",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_AR2"));
		eucr_auto_users.put("AAR",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_AAR1"));
		eucr_auto_users.put("AAR1",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_AAR1"));
		eucr_auto_users.put("AAR2",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_AAR2"));
		eucr_auto_users.put("CA1",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_CA1"));
		eucr_auto_users.put("CA2",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_CA2"));
		eucr_auto_users.put("SD1",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_SD1"));
		eucr_auto_users.put("SD2",	ax.READ_CONFIG_FILE("ESD_LOGIN_USER_SD2"));

		eucr_auto_users.put("ESDAR", 	ax.GET_USER_NAME("ESDAR1"));
		eucr_auto_users.put("ESDAR1",	ax.GET_USER_NAME("ESDAR1"));
		eucr_auto_users.put("ESDAR2",	ax.GET_USER_NAME("ESDAR2"));
		eucr_auto_users.put("ESDAAR",	ax.GET_USER_NAME("ESDAAR1"));
		eucr_auto_users.put("ESDAAR1",	ax.GET_USER_NAME("ESDAAR1"));
		eucr_auto_users.put("ESDAAR2",	ax.GET_USER_NAME("ESDAAR2"));
		eucr_auto_users.put("ESDCA1",	ax.GET_USER_NAME("ESDCA1"));
		eucr_auto_users.put("ESDCA2",	ax.GET_USER_NAME("ESDCA2"));
		eucr_auto_users.put("ESDSD1",	ax.GET_USER_NAME("ESDSD1"));
		eucr_auto_users.put("ESDSD2",	ax.GET_USER_NAME("ESDSD2"));
		
		return eucr_auto_users.get(FX);
	}
	
	public static Object getKeyFromValue(Map hm, Object value) {
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
	
	public static String GET_USER_FX_NAME_BY_USERNAME(String loginName){
		HashMap<String, String> eucr_auto_users = new HashMap<String, String>( );
		eucr_auto_users.put("AR1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG1);
		eucr_auto_users.put("AR2",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG2);
		eucr_auto_users.put("AR3",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG3);
		eucr_auto_users.put("AR4",	"eucrauto_01");
		eucr_auto_users.put("AR5",	"eucrauto_03");
		eucr_auto_users.put("AR6",	"eucrauto_04");
		eucr_auto_users.put("AR7",	"eucrauto_05");
		eucr_auto_users.put("AAR1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_REG3);
		eucr_auto_users.put("NA1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_NA1);
		eucr_auto_users.put("NA2",	LoginToEUCR.LOGIN_EUCR_LOGNAME_NA2);
		eucr_auto_users.put("CA1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_CA1);
		eucr_auto_users.put("CA2",	LoginToEUCR.LOGIN_EUCR_LOGNAME_CA2);
		eucr_auto_users.put("CA2",	LoginToEUCR.LOGIN_EUCR_LOGNAME_CA3);
		eucr_auto_users.put("NA",	LoginToEUCR.LOGIN_EUCR_LOGNAME_NA1);
		eucr_auto_users.put("USR",	LoginToEUCR.LOGIN_EUCR_LOGNAME_USR);
		eucr_auto_users.put("VER",	LoginToEUCR.LOGIN_EUCR_LOGNAME_VER1);
		eucr_auto_users.put("VER1",	LoginToEUCR.LOGIN_EUCR_LOGNAME_VER1);
		
		return getKeyFromValue(eucr_auto_users, loginName).toString();
		
		
//		Collection c = eucr_auto_users.values();
//	    Iterator itr = c.iterator();
//	    while (itr.hasNext()) {
//	    	String k=itr.next().toString();
//	    	if(k.equals(loginName)){
//	    		return itr.; }}
//		return loginName;
//	
//		for (eucr_auto_users : hashmap.entrySet()) {
//		    String key = e.getKey();
//		    Object value = e.getValue();
//		}
		
	}

	
	
	
	public static String getCountryNameByShort(String SHORT){
		HashMap<String, String> eucr_country_names = eucr_country_names();
		if(eucr_country_names.containsKey(SHORT)){
			return eucr_country_names.get(SHORT);	
		}
		return SHORT;
	}
	
	
	
	public static String GET_USERNAME_BY_USER_REF(String UserRef){
		HashMap<String, String> eucr_auto_users = new HashMap<String, String>( );
		eucr_auto_users.put("Victor_AR"		,LoginToEUCR.LOGIN_EUCR_LOGNAME_REG1);
		eucr_auto_users.put("Cecile_AR"		,LoginToEUCR.LOGIN_EUCR_LOGNAME_REG2);
		eucr_auto_users.put("Damien_AR"		,"eucrauto_02");
		eucr_auto_users.put("Marta_AR"		,"eucrauto_01");
		eucr_auto_users.put("Steven_AR"		,LoginToEUCR.LOGIN_EUCR_LOGNAME_REG3);
		eucr_auto_users.put("Anna_AR"		,"eucrauto_04");
		eucr_auto_users.put("Alexander_AR"	,"eucrauto_05");
//		eucr_auto_users.put("Marc_AAR"		,"eucrauto_02");
		eucr_auto_users.put("Peter_NA"		,LoginToEUCR.LOGIN_EUCR_LOGNAME_NA1);
		eucr_auto_users.put("Maria_NA"		,LoginToEUCR.LOGIN_EUCR_LOGNAME_NA2);
		eucr_auto_users.put("Paul_CA"		,LoginToEUCR.LOGIN_EUCR_LOGNAME_CA1);
		eucr_auto_users.put("Roselyn_CA"	,LoginToEUCR.LOGIN_EUCR_LOGNAME_CA2);
		return eucr_auto_users.get(UserRef);}
	
	public static String GET_URID_BY_PERSONA_NAME(String PersonaName){
		String UserName=GET_USERNAME_BY_USER_REF(PersonaName);
		return AppSqlQueries.getURID_ByLoginName(UserName);
	}
	
	public static boolean IS_CURRENT_USER_NA(WebDriver driver){
		String UserName = PageCanvas.getApplicationLoggedIn_Username(driver);
		return (UserName.contains("_NA"))?true:false;}
	
	public static boolean IS_CURRENT_USER_ADMIN(WebDriver driver){
		if(ax.isDisplayedBy(driver, By.xpath(RepoEucr.EUCR_MENU_ACCORD_ADMIN_H3))){
			return true; }return false;}
	
	public static String GET_USERROLE_BY_USER_ROLE_SHORT(String UserROLE){
		HashMap<String, String> eucr_auto_userroles = new HashMap<String, String>( );
		eucr_auto_userroles.put("NA"		,"NATIONAL_ADMINISTRATOR");
		eucr_auto_userroles.put("AR"		,"AUTHORISED_REPRESENTATIVE");
		eucr_auto_userroles.put("AAR"		,"ADDITIONAL_AUTHORISED_REPRESENTATIVE");
		eucr_auto_userroles.put("CA"		,"CENTRAL_ADMINISTRATOR");
		eucr_auto_userroles.put("SA"		,"SYSTEM_ADMINISTRATOR");
		return eucr_auto_userroles.get(UserROLE);}
	
	
	public static String GET_REG_FROM_URID(String URID){
		return URID.substring(0, 2);
	}
	
	public static String GET_REG_FROM_URL(WebDriver driver){
		return GET_REG_FROM_URL(driver.getCurrentUrl());
	}
	public static String GET_REG_FROM_URL(String RegistryURL){
		String f = RegistryURL;
		String parts[] = f.split("/");
		for (String stringx : parts) {
			if(stringx.length()==2){
				if( Character.isUpperCase(stringx.charAt(0)) && Character.isUpperCase(stringx.charAt(1))  ){
					return stringx;
				}}}return null;}
	
	
	
public static String GET_MOBILE_FOR_USER_TEST_FLOW(){
//	return TwilioTest.TWILIO_PHONE1;
	return ax.GET_USER_TEL("USR");
}

public static String TRANSLATE_KP_UNITS(String KP_UNIT_CODE){
	switch (KP_UNIT_CODE) {
	case "AAUSOP" 	: return "AAU (Subject to SOP)";
	case "AAUNSOP" 	: return "AAU (Not Subject to SOP)";
	case "CER"		: return "CER";
	case "ERUFAAU"	: return "ERU from AAU";
	case "ERUFRMU"	: return "ERU from RMU";
	case "RMU" 		: return "RMU";
	case "LCER"		: return "lCER";
	case "TCER"		: return "tCER";
	default			: return KP_UNIT_CODE;
	}
	
}


}
