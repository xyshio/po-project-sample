package api;

import static utils.SeleniumDriver.getDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import po.EucrHomePage;
import utils.AppGetter;
import utils.ApplicationPageUtils;
import utils.ExtentManager;
import utils.SeleniumDriver;
import utils.ax;

public class Run {
    public static ExtentReports extent 						= ExtentManager.extent;
    public static ExtentTest test 							= ExtentManager.test;
    public static boolean result 							= false;
    public static int 	i									= 0;
    public static String identifier;
    public static String loginPairNa1Na2					= "NA1:NA2";
    public static String loginPairAr1Aar2					= "AR1:AAR2";
    public static EucrHomePage eucr;
    public static String transferringAccountIdentifier 	= AppGetter.GET_TRAD_ACC_IDENTIFIER("ID");
    
	public static void main(String[] args) {
		extent = ExtentManager.Init();
		
		
	  	// SCENARIO: ACCOUNT-MANAGEMENT
    	// #########################################################################################################
    	String titleOfTest 						= "";
    	String commentsOfTest 					= "";      	
    	try {
//        	while(true){
    		titleOfTest = "SCENARIO: ACCOUNT-MANAGEMENT";
    		ax.fxenter_group(titleOfTest);
        		++i;
        		test = ax.reportHeader(extent, "["+SeleniumDriver.getCurrentBrowserType()+"] "+titleOfTest); 
            	// CREATING ACCOUNT
        		titleOfTest 					= "["+ ++i +"] CREATING OHA";
        		commentsOfTest 					= "create OHA account";
            	eucr 							= ax.LoginNA1();
            	identifier 						= Eucr.CreateAccount(loginPairNa1Na2, "OHA", "2018");
            	result 							= ax.IS_ACCOUNT_IDENTIFIER(identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	
            	if(!result) throw new ElementNotFoundException("Problem wih creating account for "+titleOfTest, "", "");
            	
            	// UPDATE OF INSTALLATION
            	titleOfTest 					= "["+ ++i +"] UPDATE OF INSTALLATION DATA ON ACCOUNT " + identifier;
        		commentsOfTest 					= "updating installation data for "+identifier;
            	result							= Eucr.UpdateAccountInstallationData("NA1:NA2", identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	// Suspending AR1 user
            	titleOfTest 					= "["+ ++i +"] SUSPENDING AR1 USER";
        		commentsOfTest 					= "suspending user ["+ax.GET_USER_URID("AR1")+"] on account "+identifier;
            	result							= Eucr.SuspendUserByUrid(identifier, ax.GET_USER_URID("AR1"));
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	// Restoring AR1 user
            	titleOfTest 					= "["+ ++i +"] RESTORING AR1 USER";
        		commentsOfTest 					= "restoring user ["+ax.GET_USER_URID("AR1")+"] on account "+identifier;
            	result							= Eucr.RestoreUserByUrid(identifier, ax.GET_USER_URID("AR1"));
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	
            	// Suspending AR2 user
            	titleOfTest 					= "["+ ++i +"] SUSPENDING AR2 USER";
        		commentsOfTest 					= "suspending user ["+ax.GET_USER_URID("AR2")+"] on account "+identifier;
            	result							= Eucr.SuspendUserByUrid(identifier, ax.GET_USER_URID("AR2"));
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	
            	// Restoring AR2 user
            	titleOfTest 					= "["+ ++i +"] RESTORING AR2 USER";
        		commentsOfTest 					= "restoring user ["+ax.GET_USER_URID("AR2")+"] on account "+identifier;
            	result							= Eucr.RestoreUserByUrid(identifier, ax.GET_USER_URID("AR2"));
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	
            	// UPDATING AR user
            	titleOfTest 					= "["+ ++i +"] UPDATING AR USER";
        		commentsOfTest 					= "updating user ["+ax.GET_USER_URID("AR1")+"] on account "+identifier;
            	result							= Eucr.UpdateArByUrid(loginPairNa1Na2, identifier, ax.GET_USER_URID("AR1"));
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);

            	// UPDATING AAR user
            	titleOfTest 					= "["+ ++i +"] UPDATING AAR USER";
        		commentsOfTest 					= "updating user ["+ax.GET_USER_URID("AAR1")+"] on account "+identifier;
            	result							= Eucr.UpdateAarByUrid(loginPairNa1Na2, identifier, ax.GET_USER_URID("AAR1"));
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);

            	// SUSPEND ACCOUNT
            	titleOfTest 					= "["+ ++i +"] SUSPEND ACCOUNT";
        		commentsOfTest 					= "suspending account "+identifier;
            	result							= Eucr.SuspendAccount(identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	
            	// RESTORE ACCOUNT
            	titleOfTest 					= "["+ ++i +"] RESTORE ACCOUNT";
        		commentsOfTest 					= "restore account "+identifier;
        		result							= Eucr.RestoreAccount(identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);            	
            	
            	// UPDATE ACCOUNT HOLDER
            	titleOfTest 					= "["+ ++i +"] UPDATE ACCOUNT HOLDER";
        		commentsOfTest 					= "update account holder for "+identifier;
            	result							= Eucr.UpdateAccountHolder(loginPairNa1Na2, identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );               	
            	
            	// UPDATE CONTACT PERSON
            	titleOfTest 					= "["+ ++i +"] UPDATE CONTACT PERSON";
        		commentsOfTest 					= "update contact person information for "+identifier;
            	result							= Eucr.UpdateContactPerson(loginPairNa1Na2, identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );        	
            	
            	// REPLACE ACCOUNT REPRESENTATIVE
            	titleOfTest 					= "["+ ++i +"] REPLACE_AR_ON_ACCOUNT";
        		commentsOfTest 					= "replace ar1 into ar-x on account "+identifier;
            	result							= Eucr.ReplaceArOnAccount(loginPairNa1Na2, "AR", ax.GET_USER_URID("AR2"), ax.GET_USER_URID("AR4"), identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );               	

            	// REPLACE ADDITIONAL ACCOUNT REPRESENTATIVE
            	titleOfTest 					= "["+ ++i +"] REPLACE_AAR_ON_ACCOUNT";
        		commentsOfTest 					= "replace aar1 into aar-x on account "+identifier;
            	result							= Eucr.ReplaceArOnAccount(loginPairNa1Na2, "AAR", ax.GET_USER_URID("AAR1"), ax.GET_USER_URID("AR7"), identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );                	
            	
            	// ADDING ACCOUNT REPRESENTATIVE
            	titleOfTest 					= "["+ ++i +"] ADDING_AR_ON_ACCOUNT";
        		commentsOfTest 					= "adding new ar into account "+identifier;
            	result							= Eucr.AddArOnAccount(loginPairNa1Na2, "AR", ax.GET_USER_URID("AR6"), identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );                	
           	
            	// ADDING ACCOUNT ADITIONAL REPRESENTATIVE
            	titleOfTest 					= "["+ ++i +"] ADDING_AAR_ON_ACCOUNT";
        		commentsOfTest 					= "adding new aar into account "+identifier;
            	result							= Eucr.AddArOnAccount(loginPairNa1Na2, "AAR", ax.GET_USER_URID("AR5"), identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );  
            	
            	// REMOVE ACCOUNT REPRESENTATIVE
            	titleOfTest 					= "["+ ++i +"] REMOVING_AR_FROM_ACCOUNT";
        		commentsOfTest 					= "removing ar on account "+identifier;
            	result							= Eucr.RemoveArOnAccount(loginPairNa1Na2, "AR", ax.GET_USER_URID("AR6"), identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );                	
           	
            	// REMOVE ACCOUNT ADITIONAL REPRESENTATIVE
            	titleOfTest 					= "["+ ++i +"] REMOVING_AAR_FROM_ACCOUNT";
        		commentsOfTest 					= "removing aar on account "+identifier;
            	result							= Eucr.RemoveArOnAccount(loginPairNa1Na2, "AAR", ax.GET_USER_URID("AR5"), identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );  

            	// RELEASE ACCOUNT
            	titleOfTest 					= "["+ ++i +"] RELEASE_ACCOUNT";
        		commentsOfTest 					= "releasing account "+identifier;
            	result							= Eucr.ReleaseAccount("OHA", identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );            	

            	// UNRELEASE ACCOUNT
            	titleOfTest 					= "["+ ++i +"] UNRELEASE_ACCOUNT";
        		commentsOfTest 					= "releasing account "+identifier;
        		result							= Eucr.UnreleaseAccount(identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest );            	
            	
            	// Claiming Account
            	titleOfTest 					= "["+ ++i +"] CLAIMING-ACCOUNT";
        		commentsOfTest 					= "claiming account "+identifier;
        	   	result 							= Eucr.Claim(loginPairNa1Na2, "OHA", identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	// CLOSING_OHA_ACCOUNT
        		titleOfTest 					= "["+ ++i +"] CLOSING OHA ACCOUNT";
        		commentsOfTest 					= "closing OHA account " + identifier;
        		result							= Eucr.CloseAccount(loginPairNa1Na2, identifier);
            	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
            	
//        	}
		} catch (Exception e) {
			ax.result(getDriver(), test, false, titleOfTest, commentsOfTest);
		
		}
    	
//    	// ##############################################################################
    	try {
    		titleOfTest = "SCENARIO: TRANSACTION-MANAGEMENT";
    		ax.fxenter_group(titleOfTest);
    		test = ax.reportHeader(extent, "["+SeleniumDriver.getCurrentBrowserType()+"] "+titleOfTest);
    		
    		titleOfTest 					= "["+ ++i +"] CREATE-ACCOUNT-FOR-TRANSFER-MANAGEMENT-SCENARIO";
    		commentsOfTest 					= "oha account for transfer management";
        	eucr 							= ax.LoginNA1();
        	identifier 						= Eucr.CreateAccount(loginPairNa1Na2, "OHA", "2018");
        	
        	if(!ax.IS_ACCOUNT_IDENTIFIER(identifier)) throw new ElementNotFoundException("Problem wih creating account for "+titleOfTest, "", "");
        	
    		titleOfTest 					= "["+ ++i +"] TRANSFER GA";
    		commentsOfTest 					= "transfer GA into OHA";
        	result							= Eucr.TransferOfAllowances("NA1:NA2", transferringAccountIdentifier, identifier, "OHA", "1");
        	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
        	// SURRENDER GA AT ACCOUNT
    		titleOfTest 					= "["+ ++i +"] SURRENDER GA";
    		commentsOfTest 					= "surrender units transferred onto new holding account";
        	String unitType 				= "GA";
        	result							= Eucr.SurrenderOfAllowances("NA1:NA2", identifier, unitType, "1");
        	ax.result(getDriver(), test, result, titleOfTest, commentsOfTest);
			
		} catch (Exception e) {
			ax.result(getDriver(), test, false, titleOfTest, commentsOfTest);
		}
//    	// ##############################################################################
		titleOfTest = "SCENARIO: USERS SCENARIO END-2-END";
		ax.fxenter_group(titleOfTest);
    	
    	try {
    		titleOfTest 					= "["+ ++i +"] USER-MANGEMENT-TEST";
    		commentsOfTest 					= "users scenario end to end";
    		boolean userManagementTest 		= Users.UsersScenarioEndToEnd(extent, test);
    		if(!userManagementTest) throw new ElementNotFoundException(titleOfTest, "", "");
    		
		} catch (Exception e) {
			ax.result(getDriver(), test, false, titleOfTest, commentsOfTest);
		}
    	
    	extent.flush();
    	System.out.println("END");
        return;	
	}

}
