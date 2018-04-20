package po;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ProjectDataVariables;
import utils.Setuper;

import eu.ets.eucr.login.LoginToEUCR;
import eu.ets.eucr.utils.AppButtons;
import utils.AppGetter;
import utils.ApplicationPageUtils;
import utils.GUI_LABELS;
import eu.ets.eucr.utils.PF_PageElements;
import eu.ets.eucr.utils.PageCanvas;
import eu.ets.eucr.utils.SeleniumWaiter;
import utils.SeleniumWebUtils;
import utils.ToolFunctions;
import utils.ax;
import eu.ets.eucr.workflow.ApprovingRequests;
import utils.AppSqlQueries;

public class Registration_Page {

	public static final String ENROLLMENT_BOX_DIV = "//div[@id='enrolmentBox']";
	public static final By ENTER_YOUR_ENROLLMENT_KEY_LINK = By.xpath(ENROLLMENT_BOX_DIV + "//table[contains(@id,':authenticatedGrid')]//a[contains(@id,':enterYourEnrolmentKeyLink')]");
	
	public static final String FILL_YOUR_PERSONAL_DETAILS_STRING 	= "//a[contains(normalize-space(),'"+GUI_LABELS.USERS_PANEL_FILL_PERS_DETAILS+"')]";
	public static final String REGISTRATION_BOX_STRING 				= "//span[contains(normalize-space(),'"+GUI_LABELS.USERS_PANEL_REGISTRATION+"')]/../..";
	public static final String VALIDATEUSERS_BOX_STRING 			= "//span[contains(normalize-space(),'"+GUI_LABELS.USERS_PANEL_VALIDATE_USER+"')]/../..";
	public static final String ENROLMENT_KEY_ENTRY_BOX 				= "//span[contains(normalize-space(),'"+GUI_LABELS.USERS_PANEL_ENROLLMENT_KEY_ENTRY+"')]/../..";
	
	static String USER_STATUS = null;
	public static String USR_STAT_REGISTERED 	= "Registered";
	public static String USR_STAT_VALIDATED 	= "Validated";
	public static String USR_STAT_ENROLLED 	= "Enrolled";
	public static String USR_STAT_UNENROLLED 	= "Unenrolled";
	
//	public static final String LINK_URID_IN_SECOND_COL = "//tbody[@id='mainForm:userSearchResultsTable_data']/tr[1]/td[2]/a";
	public static final String TAB_USER_DETAILS 					= "//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]";
	public static final String USER_DETAILS_BOX_MAIN				= "//div[contains(concat(' ',@class,' '),'ui-tabs')]";
	
	public static final String USER_DETAILS_TAB_LI_PD =  TAB_USER_DETAILS + "/li/a/em[contains(normalize-space(),'Personal Details')]/../..";
	public static final String USER_DETAILS_TAB_LI_BD =  TAB_USER_DETAILS + "/li/a/em[contains(normalize-space(),'Business Details')]/../..";
	public static final String USER_DETAILS_TAB_LI_AR =  TAB_USER_DETAILS + "/li/a/em[contains(normalize-space(),'Administration roles')]/../..";
	public static final String USER_DETAILS_TAB_LI_AC =  TAB_USER_DETAILS + "/li/a/em[contains(normalize-space(),'Accounts')]/../..";
	
	
	public static final By TAB_BY_USER_DETAILS_PERSONAL_DETAILS 	= By.xpath(USER_DETAILS_TAB_LI_PD); 
	public static final By TAB_BY_USER_DETAILS_BUSINESS_DETAILS		= By.xpath(USER_DETAILS_TAB_LI_BD); 
	public static final By TAB_BY_USER_DETAILS_ADMIN_ROLES 			= By.xpath(USER_DETAILS_TAB_LI_AR); 
	public static final By TAB_BY_USER_DETAILS_ACCOUNTS 			= By.xpath(USER_DETAILS_TAB_LI_AC);
	public static final By TAB_BY_USER_DETAILS_PERSONAL_DETAILS_A 	= By.xpath(USER_DETAILS_TAB_LI_PD + "//a");
	public static final By TAB_BY_USER_DETAILS_BUSINESS_DETAILS_A	= By.xpath(USER_DETAILS_TAB_LI_BD + "//a");
	public static final By TAB_BY_USER_DETAILS_ADMIN_ROLES_A 		= By.xpath(USER_DETAILS_TAB_LI_AR + "//a"); 
	public static final By TAB_BY_USER_DETAILS_ACCOUNTS_A 			= By.xpath(USER_DETAILS_TAB_LI_AC + "//a");
	
	public static final String ROLES_TABLE_DIV 					= "//div[contains(concat(' ',@class,' '),'ui-datatable')]/table/thead/tr/th[@class='ui-state-default' and normalize-space()='Roles']/../../../..";
	public static final String ROLES_TABLE_TBODY 					= ROLES_TABLE_DIV + "//tbody[@class='ui-datatable-data']";
	public static final By ROLES_CKBX_SERV_DESK					    = By.xpath(ROLES_TABLE_TBODY + "//tr[1]/td[normalize-space()='Service Desk']/input[@type='checkbox']");
	public static final By ROLES_CKBX_SD_AGENT					    = By.xpath(ROLES_TABLE_TBODY + "//tr[2]/td[normalize-space()='SD Agent']/input[@type='checkbox']");
	public static final By ROLES_CKBX_AUDIT4NA					    = By.xpath(ROLES_TABLE_TBODY + "//tr[3]/td[normalize-space()='Auditor for NA']/input[@type='checkbox']");
	public static final By ROLES_CKBX_NA					   	 	= By.xpath(ROLES_TABLE_TBODY + "//tr[4]/td[normalize-space()='National Administrator']/input[@type='checkbox']");
	public static final By ROLES_CKBX_CA					   	 	= By.xpath(ROLES_TABLE_TBODY + "//tr[5]/td[normalize-space()='Central Administrator']/input[@type='checkbox']");
	public static final By ROLES_CKBX_SA					   	 	= By.xpath(ROLES_TABLE_TBODY + "//tr[6]/td[normalize-space()='System Administrator']/input[@type='checkbox']");
	
	public static final By ROLES_EDIT_CKBX_SD_AGENT = By.xpath("//div[@id='mainForm:rolesPanelId']/div[@id='mainForm:rolesPanelId_content']//table[contains(concat(' ',@id,' '),':selectionsRadio')]//td/label[contains(normalize-space(),'SD Agent')]/..//input[@type='radio']");
	public static final By ROLES_EDIT_CKBX_SD		= By.xpath("//div[@id='mainForm:rolesPanelId']/div[@id='mainForm:rolesPanelId_content']//table[contains(concat(' ',@id,' '),':selectionsRadio')]//td/label[contains(normalize-space(),'Service Desk')]/..//input[@type='radio']");
	public static final By ROLES_EDIT_CKBX_AUD 		= By.xpath("//div[@id='mainForm:rolesPanelId']/div[@id='mainForm:rolesPanelId_content']//table[contains(concat(' ',@id,' '),':selectionsRadio')]//td/label[contains(normalize-space(),'Auditor For NA')]/..//input[@type='radio']");
	public static final By ROLES_EDIT_CKBX_NA 		= By.xpath("//div[@id='mainForm:rolesPanelId']/div[@id='mainForm:rolesPanelId_content']//table[contains(concat(' ',@id,' '),':selectionsRadio')]//td/label[contains(normalize-space(),'National Administrator')]/..//input[@type='radio']");
	public static final By ROLES_EDIT_CKBX_CA 		= By.xpath("//div[@id='mainForm:rolesPanelId']/div[@id='mainForm:rolesPanelId_content']//table[contains(concat(' ',@id,' '),':selectionsRadio')]//td/label[contains(normalize-space(),'Central Administrator')]/..//input[@type='radio']");
	public static final By ROLES_EDIT_CKBX_SA 		= By.xpath("//div[@id='mainForm:rolesPanelId']/div[@id='mainForm:rolesPanelId_content']//table[contains(concat(' ',@id,' '),':selectionsRadio')]//td/label[contains(normalize-space(),'System Administrator')]/..//input[@type='radio']");
	
	public static final String EDIT_BUTTON_TO_CHANGE_ROLES = "//div[contains(concat(' ',@class,' '),'ui-panel-titlebar')]/span[@class='ui-panel-title' and normalize-space()='Administration Roles Update']/../..//div[@class='buttonContainer']/button[@id='mainForm:buttonNext']";
	
	public static final String PERSONAL_DETAILS_DIV_BOX_STRING 	=  USER_DETAILS_BOX_MAIN+  "/div[1]"; 
	public static final String BUSINESS_DETAILS_DIV_BOX_STRING 	=  USER_DETAILS_BOX_MAIN+  "/div[2]"; 
	public static final String ADMINISTRA_ROLES_DIV_BOX_STRING 	=  USER_DETAILS_BOX_MAIN+  "/div[3]"; 
	public static final String ACCOUNTS_THIRDS_DIV_BOX_STRING 	=  USER_DETAILS_BOX_MAIN+  "/div[4]"; 
	
	public static final String MOBILE_PHONE_NUMBER_GUI_USER_DETAILS = Registration_Page.PERSONAL_DETAILS_DIV_BOX_STRING +  "//dl[@class='detailsList']//dd/span[contains(@id,':mobilePhoneNumber')]";
	
	public static final By BUTTON_TO_UPDATE_MOBILE_ON_INFO_BOX_FOR_WRONG_MOBILE = By.xpath("//div[contains(concat(' ',@class,' '),'ui-messages-warn')]//button[@id='mainForm:buttonUpdate']/span[@class='ui-button-text' and normalize-space()='Update my number']/..");
	
	public static final String UserUserName = ax.GET_USER_("NAME", "USR");
	
	public static final String PERSONAL_DETAILS_UPDATE_PAGE_STRING 	= "//div/div[contains(@id,'_header')]/span[@class='ui-panel-title' and normalize-space()='Personal Details Update']/../.."; 
	public static final String BUSINESS_DETAILS_UPDATE_PAGE_STRING 	= "//div/div[contains(@id,'_header')]/span[@class='ui-panel-title' and normalize-space()='Business Details Update']/../.."; 
	
	public static void Click_FillInYourPersonalDetails_link(WebDriver driver){
		SeleniumWebUtils.XClick(driver, By.xpath(FILL_YOUR_PERSONAL_DETAILS_STRING), "Cannot find the link to fill personal details");
	}

	public static void Click_EDIT_BUTTON_ON_PERSONAL_DETAILS(WebDriver driver){
		SeleniumWebUtils.XClick(driver, By.xpath(PERSONAL_DETAILS_DIV_BOX_STRING + "//button[contains(@id,'buttonEditPersonalDetails')]"), "Cannot find the EDIT button on Personal Details");
		SeleniumWaiter.waitForPageLoaded(driver);
	}
	public static void Click_EDIT_BUTTON_ON_BUSINESS_DETAILS(WebDriver driver){
		SeleniumWebUtils.XClick(driver, By.xpath(BUSINESS_DETAILS_DIV_BOX_STRING + "//button[contains(@id,'buttonEditBusinessDetails')]"), "Cannot find the EDIT button on Business Details");
		SeleniumWaiter.waitForPageLoaded(driver);
	}

	public static boolean GoTo_PersonalDetails_TAB(WebDriver driver){
		if(ax.isDisplayed_And_Enabled(driver, By.xpath(RepoEucr.PAGE_HEAD_BOX_USER_DETAILS))){
			if (SeleniumWebUtils.isElementDisplayed(driver, TAB_BY_USER_DETAILS_PERSONAL_DETAILS)){
				String classTAB_GA=driver.findElement(TAB_BY_USER_DETAILS_PERSONAL_DETAILS).getAttribute("class");
				if (!classTAB_GA.contains("ui-tabs-selected")){
					return SeleniumWebUtils.XClick(driver, TAB_BY_USER_DETAILS_PERSONAL_DETAILS_A, "Cannot click in Personal Details tab link");	
				}
				return true;
			}
			System.out.println("Users Details Page displayed but cannot find the Tab element for UserDetails"); return false;
		}
		System.out.println("Not in Users Details Page"); return false;
	}
	public static boolean GoTo_BusinessDetails_TAB(WebDriver driver){
	if(ax.isDisplayed_And_Enabled(driver, By.xpath(RepoEucr.PAGE_HEAD_BOX_USER_DETAILS))){
		if (SeleniumWebUtils.isElementDisplayed(driver, TAB_BY_USER_DETAILS_BUSINESS_DETAILS)){
				String classTAB_GA=driver.findElement(TAB_BY_USER_DETAILS_BUSINESS_DETAILS).getAttribute("class");
				if (!classTAB_GA.contains("ui-tabs-selected")){
					return SeleniumWebUtils.XClick(driver, TAB_BY_USER_DETAILS_BUSINESS_DETAILS_A, "Cannot click in Business Details tab link");	
				}
				return true;
			}
			System.out.println("Users Details Page displayed but cannot find the Tab element for UserDetails"); return false;
		}
		System.out.println("Not in Users Details Page"); return false;
	}
	public static boolean GoTo_AdministrationRoles_TAB(WebDriver driver){
	if(ax.isDisplayed_And_Enabled(driver, By.xpath(RepoEucr.PAGE_HEAD_BOX_USER_DETAILS))){		
		if (SeleniumWebUtils.isElementDisplayed(driver, TAB_BY_USER_DETAILS_ADMIN_ROLES)){
				String classTAB_GA=driver.findElement(TAB_BY_USER_DETAILS_ADMIN_ROLES).getAttribute("class");
				if (!classTAB_GA.contains("ui-tabs-selected")){
					return SeleniumWebUtils.XClick(driver, TAB_BY_USER_DETAILS_ADMIN_ROLES_A, "Cannot click in ARoles tab link");	
				}
				return true;
			}
			System.out.println("Users Details Page displayed but cannot find the Tab element for UserDetails"); return false;
		}
		System.out.println("Not in Users Details Page"); return false;
	}
	public static boolean GoTo_Accounts_TAB(WebDriver driver){
		if(ax.isDisplayed_And_Enabled(driver, By.xpath(RepoEucr.PAGE_HEAD_BOX_USER_DETAILS))){
		if (SeleniumWebUtils.isElementDisplayed(driver, TAB_BY_USER_DETAILS_ACCOUNTS)){
				String classTAB_GA=driver.findElement(TAB_BY_USER_DETAILS_ACCOUNTS).getAttribute("class");
				if (!classTAB_GA.contains("ui-tabs-selected")){
					return SeleniumWebUtils.XClick(driver, TAB_BY_USER_DETAILS_ACCOUNTS_A, "Cannot click in Accounts tab link");	
				}
				return true;
			}
			System.out.println("Users Details Page displayed but cannot find the Tab element for UserDetails"); return false;
		}
		System.out.println("Not in Users Details Page"); return false;
	}
	public static boolean VERIFY_PAGE_HEADER(WebDriver driver, String TITLE_TEXT){
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, By.xpath("//div[contains(@id,'_header')]/span[contains(concat(' ',@class,' '),'ui-panel-title') and normalize-space()='"+TITLE_TEXT+"']"));
		
		try {
			ApplicationPageUtils.verifyPageHeaderPresentBoolean(driver, "_header", TITLE_TEXT, ""); return true;
		} catch (Exception e) {
			SeleniumWebUtils.reportFAIL(driver, "FAIL:: I'm NOT on "+TITLE_TEXT+" Page !!!"); return false; 
		}

	}
	

	public static void ENTER_USER_DETAILS_IN_REGISTRATION_FORM(WebDriver driver){
		ENTER_USER_DETAILS_IN_REGISTRATION_FORM(driver, true);
	}
	public static void ENTER_USER_DETAILS_IN_REGISTRATION_FORM(WebDriver driver, boolean EcasNames){
		
		String RegistrationMobileNumberField = REGISTRATION_BOX_STRING + "//input[@id='mainForm:mobilePhoneNumber']";
		
		if(!EcasNames){
			SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='"+PF_PageElements.USER_SEARCH_INP_ID_FNAME+"']"), "EUCR-TEST-USER-FLOW", "Cannot type in First name field");
			SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:lastName']"), "EUCR-AUTOR", "Cannot type in Last name field");
		}
		
		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:title']"), "Docteour", "Cannot type in Title field");
		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:dateOfBirth_input']"), "16/07/1978", "Cannot type in Date Of Birth field");
		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:placeOfBirth']"), "Savannah", "Cannot type in Place Of Birth");
		
		SeleniumWebUtils.selectAndClickByValue(driver, By.xpath(REGISTRATION_BOX_STRING + "//select[@id='mainForm:countryOfBirth']"), "BG");
		SeleniumWebUtils.selectAndClickByValue(driver, By.xpath(REGISTRATION_BOX_STRING + "//select[@id='mainForm:typeOfIdDoc']"), "NATIONAL_ID_CARD");
		
		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:identifierOfIdDoc']"), "1234563", "Cannot type in Identity document identifier:");
		SeleniumWebUtils.selectAndClickByValue(driver, By.xpath(REGISTRATION_BOX_STRING + "//select[@id='mainForm:preferredLanguage']"), "EN");
		
		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:defaultEmailAddress']"), "eucrauto04+ar1@gmail.com", "Cannot type in defaultEmailAddress");
		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:confirmDefaultEmailAddress']"), "eucrauto04+ar1@gmail.com", "Cannot type in defaultEmailAddress");
		
		
		if(ax.isDisplayed_And_Enabled(driver, By.xpath(RegistrationMobileNumberField))){
			SeleniumWebUtils.XType(driver, By.xpath(RegistrationMobileNumberField), ax.GET_USER_TEL("USR"), "Cannot type in mainForm:secretQuestion");
			
		}
		

		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:secretQuestion']"), "chatka", "Cannot type in mainForm:secretQuestion");
		SeleniumWebUtils.XType(driver, By.xpath(REGISTRATION_BOX_STRING + "//input[@id='mainForm:secretAnswer']"), "niemcowa", "Cannot type in mainForm:secretAnswer");
		
		AppButtons.ClickPF_Button_BY(driver, By.xpath( REGISTRATION_BOX_STRING + "//button[@id='mainForm:buttonNext']/span[@class='ui-button-text' and normalize-space()='Next']/.."));
	}
	
	
//	###################################################################
//	This function will return URID


	
	public static String registerNewUserApproval(WebDriver driver, String userName, String URID){ // Return Enrollment Key
		
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.FILTER_USERS_BY_URID(driver, URID);
		UserManagementOperations.Click_URID_linkinSecondCol(driver);
		UserManagementOperations.ClickValidateButtonOnUserDetailsPersonalDetails(driver);
		
		
		if(!ax.WAIT_FOR_HEADER_PRESENT(driver, GUI_LABELS.USERS_PANEL_VALIDATE_USER)) return null;
		
		if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath(VALIDATEUSERS_BOX_STRING + "//button[@id='mainForm:buttonConfirm']"))){
			AppButtons.ClickPF_Button_BY(driver, By.xpath(VALIDATEUSERS_BOX_STRING + "//button[@id='mainForm:buttonConfirm']"));
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot find and click Validate - Confirm button");
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}
		if(!Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_VALIDATE_USER)){return null;}
		
		if(ax.isError1(driver, "Invalid user details")){
			ax.t_error(driver, "Error occured: " + ax.getText(driver, RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_BY));
			return null;
		}
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("The users have been validated"));
		SeleniumWaiter.waitForPageLoaded(driver);
		
		return getEnrollmentKey(driver, URID);
		
		
//		Boolean u1 = UserManagementOperations.SearchUserByURID(driver, URID);
//		if(u1){
//			USER_STATUS = UserManagementOperations.GetUserStatusFromFound1Row(driver);
//			if(USER_STATUS.equals(USR_STAT_VALIDATED)){
////				ExcelTools.REPPORT_PASS("USER_STATUS_VALIDATED");
//				ax.t_pass( "USER_STATUS_VALIDATED");
//			}else{
////				ExcelTools.REPPORT_FAIL("USER_STATUS_VALIDATED");
//				ax.t_pass( "USER_STATUS_VALIDATED");
//				SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
//				return null;
//			}
//		}else{
//			SeleniumWebUtils.reportFAIL(driver, "Cannot find the user by URID: "+URID+" which should be "+USR_STAT_VALIDATED);
//			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
//			return null;
//		}
//		String ENROLLMENT_KEY = null;
//		By ENROLMENT_KEY_CELL = UserManagementOperations.GET_USERS_1x1_RESULT_CELL_ENROLLMENT_KEY(driver);
//		if(SeleniumWebUtils.isElementDisplayed(driver, ENROLMENT_KEY_CELL )){
//			return SeleniumWebUtils.getTextForElement(driver, ENROLMENT_KEY_CELL);	
//		}else{
//			SeleniumWebUtils.reportFAIL(driver, "Cannot grab Enrollment Key for user: " + URID);
//			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
//			return null;
//		}		
	}
	
	
	public static String getEnrollmentKey(WebDriver driver, String URID){
		Boolean u1 = UserManagementOperations.SearchUserByURID(driver, URID);
		if(u1){
			USER_STATUS = UserManagementOperations.GetUserStatusFromFound1Row(driver);
			if(USER_STATUS.equals(USR_STAT_VALIDATED)){
//				ExcelTools.REPPORT_PASS("USER_STATUS_VALIDATED");
				ax.t_pass( "USER_STATUS_VALIDATED");
			}else{
//				ExcelTools.REPPORT_FAIL("USER_STATUS_VALIDATED");
				ax.t_pass( "USER_STATUS_VALIDATED");
				SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
				return null;
			}
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot find the user by URID: "+URID+" which should be "+USR_STAT_VALIDATED);
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}
		String ENROLLMENT_KEY = null;
		By ENROLMENT_KEY_CELL = UserManagementOperations.GET_USERS_1x1_RESULT_CELL_ENROLLMENT_KEY(driver);
		if(SeleniumWebUtils.isElementDisplayed(driver, ENROLMENT_KEY_CELL )){
			return SeleniumWebUtils.getTextForElement(driver, ENROLMENT_KEY_CELL);	
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot grab Enrollment Key for user: " + URID);
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}		

	}
	
	
	public static boolean enterNewUserEnrollmentKey(WebDriver driver, String ENROLLMENT_KEY){
		// has to be logged-in as USR
		ax.clickWhenReady(driver, ENTER_YOUR_ENROLLMENT_KEY_LINK);
		SeleniumWaiter.waitForPageLoaded(driver);
		Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_ENROLLMENT_KEY_ENTRY);
		String ENROLL_GROUP = "//div[@class='enrolmentKeyContainer']";
			if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath(ENROLL_GROUP))){
				String[] EK = ENROLLMENT_KEY.split("-");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart1']"), EK[0], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart2']"), EK[1], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart3']"), EK[2], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart4']"), EK[3], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart5']"), EK[4], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XClick(driver, By.xpath(ENROLMENT_KEY_ENTRY_BOX + "//button[@id='mainForm:buttonNext']/span[normalize-space()='Submit']/.."), "Cannot click Submit button after typing enrollment Key");
				SeleniumWaiter.waitForPageLoaded(driver);
				
			}else{
				SeleniumWebUtils.reportFAIL(driver, "Enrollment Key fields are not displayed");
				SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
				return false;
			}

			Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_ENROLLMENT_KEY_ENTRY);
			SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("Your access to the registry has been activated."));
			return SeleniumWebUtils.AsserterForTextContains(driver, "Your access to the registry has been activated.", RepoEucr.INFO_GREEN_BOX_UNV_XPATH);	
		
	}
	
	
	
	
	public static String registerNewUserProposal(WebDriver driver, String userName){ 
		return registerNewUserProposal(driver, userName, false);
	}// return URID
	public static String registerNewUserProposal(WebDriver driver, String userName, boolean EcasNames){ // return URID
		if(ax.isDisplayed_And_Enabled(driver, By.xpath(FILL_YOUR_PERSONAL_DETAILS_STRING))){
			Registration_Page.Click_FillInYourPersonalDetails_link(driver);	
		}
		Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_REGISTRATION);
		Registration_Page.ENTER_USER_DETAILS_IN_REGISTRATION_FORM(driver, EcasNames);
//		SeleniumWaiter.AYAX(driver);
		SeleniumWaiter.waitForPageLoaded(driver);
		Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_REGISTRATION);
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("Please review your personal details."));
		if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath( REGISTRATION_BOX_STRING + "//button[@id='mainForm:buttonNext']/span[normalize-space()='Submit']/.."))){
			AppButtons.ClickPF_Button_BY(driver, By.xpath( REGISTRATION_BOX_STRING + "//button[@id='mainForm:buttonNext']/span[normalize-space()='Submit']/..") );
		}
		SeleniumWaiter.waitForPageLoaded(driver);
		Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_REGISTRATION);
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("You have now been registered"));
		String monitWithURID=SeleniumWebUtils.getTextForElement(driver, RepoEucr.GREEN_BOX_TWO_ROWS_WITH_REQUEST_NR);
		String URID = SeleniumWebUtils.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monitWithURID, "Registry Identifier is");
		if(!URID.isEmpty()){
			System.out.println("GRABBED URID FOR USER IS: " + URID);
			SeleniumWebUtils.reportPass("GRABBED URID number : " + URID);
			return URID;
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Problem with grabbing URID number");
			SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
			return null;
		}
}		
		

	
	
	public static String REGISTER_USER(WebDriver driver){
		return REGISTER_USER(driver, Registration_Page.UserUserName);
	}	
	public static String REGISTER_USER(WebDriver driver, String USER_NAME){
		return REGISTER_USER(driver, USER_NAME, "YES");
	}
	
	
	
	public static String REGISTER_USER(WebDriver driver, String USER_NAME, String LOG_EXCEL){
			
		boolean EnrolledUserExist 	= false;
		boolean AnyActiveUserExist 	= false;
		boolean UP_USER_Exist 		= false;
		String URID_ENROLLED 		= null;
		
		
		if(ax.isSQLEnabled()){
			EnrolledUserExist 	= AppSqlQueries.CHECK_IF_ENROLLED_USER_EXIST_BY_LOGINNAME(USER_NAME, ProjectDataVariables.EUCR_REG);
			AnyActiveUserExist 	= AppSqlQueries.CHECK_IF_ANY_USER_EXIST_BY_LOGINNAME(USER_NAME, ProjectDataVariables.EUCR_REG);
			UP_USER_Exist 		= AppSqlQueries.CHECK_IF_USER_UNENROLL_PENDING_EXIST_BY_LOGINNAME(USER_NAME, ProjectDataVariables.EUCR_REG);
		}else{
			
			EnrolledUserExist 	= CHECK_IF_ENROLLED_USER_EXIST_BY_LOGINNAME(driver, USER_NAME, ProjectDataVariables.EUCR_REG);
			AnyActiveUserExist 	= CHECK_IF_ANY_USER_EXIST_BY_LOGINNAME(driver, USER_NAME, ProjectDataVariables.EUCR_REG);
			UP_USER_Exist 		= CHECK_IF_USER_UNENROLL_PENDING_EXIST_BY_LOGINNAME(driver, USER_NAME, ProjectDataVariables.EUCR_REG);

		}
		
//		// [Case where there are no other userName than in status Unenrolled]
//		if(!EnrolledUserExist && !AnyActiveUserExist && !UP_USER_Exist){
//			driver 			= ax.LOGIN_USR(driver);
//			URID_ENROLLED 	= Registration_Page.registerNewUserProposal(driver, USER_NAME);
//			driver 			= ax.LOGIN_NA2(driver, 3);
//			String ENRL 	= Registration_Page.registerNewUserApproval(driver, USER_NAME, URID_ENROLLED);
//			
//			//[info] This cannot be used in the situation where SQL-Connection is off 
////			SetMobileNumberForUser(driver, AnyActiveUserExist, USER_NAME, URID_ENROLLED, AppGetter.GET_MOBILE_FOR_USER_TEST_FLOW(), false);
//			//[info] We assume the opened session with logged-in "USER_NAME" is still on - do not perform relogin !!
//			driver 			= ax.LOGIN_USR(driver, 1);
//			Registration_Page.registerNewUserEnrollmentKey(driver, ENRL);
//			driver			= ax.LOGIN_NA2(driver, 2);
//			return CHECK_IF_URID_USER_IS_ENROLLED(driver, URID_ENROLLED);
//		}
		
		
		
		// Updating mobile number for user for temporary purpose of registering user
//		SetMobileNumberForUser(driver, AnyActiveUserExist, USER_NAME, URID_ENROLLED, AppGetter.GET_MOBILE_FOR_USER_TEST_FLOW(), true);
		
		
		// UNENROLLMENT_PENDING REQUEST EXIST - NEED FOR UNENROLL
		if(UP_USER_Exist){ 
			String URID_USER_UP 	= AppSqlQueries.GET_USER_UNENROLL_PENDING_URID_BY_LOGINNAME(USER_NAME, ProjectDataVariables.EUCR_REG);
			String UP_REQUEST_NR 	= AppSqlQueries.GET_REQUEST_NUMBER_FOR_UNENROLLMENT_USER_URID(URID_USER_UP);
			driver = ax.LOGIN_NA2(driver, 3);
			EUCR_API.TASK_ETL_APPROVE(driver, UP_REQUEST_NR);
		}
		
		// There is Enrolled user - so unenroll him
		if(EnrolledUserExist){
			URID_ENROLLED = AppSqlQueries.getURID_ByLoginName(USER_NAME);
			System.out.println("Exist Enrolled User "+USER_NAME+"["+URID_ENROLLED+"].. Unenrolling him first");
			Registration_Page.UNENROLL_USER(driver, URID_ENROLLED);
		}else{
			ax.info("User might exist but still not be in ENROLLED status: for example, [registered or validated]");
			if(AnyActiveUserExist){
					URID_ENROLLED = AppSqlQueries.getANYURID_ByLoginName(USER_NAME);
					AppSqlQueries.UPDATE_MOBILE_ON_FOR_USER_URID(URID_ENROLLED);
				}
		}

		
		
		if(!ProjectDataVariables.IS_LOGIN_SMS_ENABLED()){
			AppSqlQueries.UPDATE_MOBILE_OFF_FOR_USER_URID(URID_ENROLLED);
		}else{
			AppSqlQueries.UPDATE_MOBILE_ON_FOR_USER_URID(URID_ENROLLED);
		}
		
		driver = ax.LOGIN_USR(driver);
		
		String URID_NOW = URID_ENROLLED;//ax.GET_URID_FROM_LOGGED_IN_DISPLAY(driver);
		String ENROLLMENT_KEY = AppSqlQueries.GET_ENROLLMENT_KEY_BY_URID(URID_NOW);
		if(driver==null && ENROLLMENT_KEY == null){
			System.out.println("Case where user exist and is Registered");
			ax.info("Case when user is registered or vallidated.......");
			driver = ax.LOGIN_USR(driver);
			String URID = GET_URID_FROM_AUTHENTICATED_GROUP(driver);
			Registration_Page.UNENROLL_USER(driver, URID);
			driver = ax.LOGIN_USR(driver);
			if(ax.isDisplayed_And_Enabled(driver, By.xpath(FILL_YOUR_PERSONAL_DETAILS_STRING))){
				Registration_Page.Click_FillInYourPersonalDetails_link(driver);	
			}
		
		
		}
		
//		if(ax.isDisplayed_And_Enabled(driver, BUTTON_TO_UPDATE_MOBILE_ON_INFO_BOX_FOR_WRONG_MOBILE)){
//			// There is problem with login, and you should off/on mobile number for this user...
//		}
		
			// IF USER IS UNENROLLED - FILL HIS DETAILS
			if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath(FILL_YOUR_PERSONAL_DETAILS_STRING)))			{
				Registration_Page.Click_FillInYourPersonalDetails_link(driver);	
			}else if(ax.isDisplayedBy(driver, ENTER_YOUR_ENROLLMENT_KEY_LINK)){ // 
				ax.info("Case when user is registered or vallidated.......");
				String URID = GET_URID_FROM_AUTHENTICATED_GROUP(driver);
				Registration_Page.UNENROLL_USER(driver, URID);
				driver = ax.LOGIN_USR(driver);
				if(ax.isDisplayed_And_Enabled(driver, By.xpath(FILL_YOUR_PERSONAL_DETAILS_STRING))){
					Registration_Page.Click_FillInYourPersonalDetails_link(driver);	
				}
			}
			
			else
			{
				SeleniumWebUtils.reportFAIL(driver, "This user might be enrolled already or There is existed Unenrollment pending tasks.. [Cannot continue untill no Pending Unenrollment exists]. continue to work on");
				return null;
			}
			
			
			Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_REGISTRATION);
			Registration_Page.ENTER_USER_DETAILS_IN_REGISTRATION_FORM(driver);
//			SeleniumWaiter.AYAX(driver);
			SeleniumWaiter.waitForPageLoaded(driver);
			Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_REGISTRATION);
			SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("Please review your personal details."));
			if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath( REGISTRATION_BOX_STRING + "//button[@id='mainForm:buttonNext']/span[normalize-space()='Submit']/.."))){
				AppButtons.ClickPF_Button_BY(driver, By.xpath( REGISTRATION_BOX_STRING + "//button[@id='mainForm:buttonNext']/span[normalize-space()='Submit']/..") );
			}
			SeleniumWaiter.waitForPageLoaded(driver);
			Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_REGISTRATION);
			SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("You have now been registered"));
			String monitWithURID=SeleniumWebUtils.getTextForElement(driver, RepoEucr.GREEN_BOX_TWO_ROWS_WITH_REQUEST_NR);
			String URID = SeleniumWebUtils.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monitWithURID, "Registry Identifier is");
			if(!URID.isEmpty()){
				//System.out.println("GRABBED URID FOR USER IS: " + URID);
				SeleniumWebUtils.reportPass("GRABBED URID number : " + URID);
				return URID;
			}else{
				SeleniumWebUtils.reportFAIL(driver, "Problem with grabbing URID number");
				SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
				return null;
			}
	}
	
	
	public static boolean SetMobileNumberForUser(WebDriver driver, boolean AnyActiveUserExist, String UserName, String URID, String MobilePhoneNumber, boolean SetOrClear/*T-set, F-clear*/){
		if(!AnyActiveUserExist){ 
			if(ax.isSQLEnabled()){
				return AppSqlQueries.UPDATE_MOBILE_USERS_BY_USERNAME_UNENROLLED(AppGetter.GET_MOBILE_FOR_USER_TEST_FLOW()); 
			}else{
				return ModifyMobilePhoneNumber(driver, UserName, URID, MobilePhoneNumber, SetOrClear);
			}
		}
		return true;
	}
	
	
	public static boolean ModifyMobilePhoneNumber(WebDriver driver, String UserName, String URID, String MobilePhoneNumber, boolean SetNumber /*[true - set new number, false = clear number]*/){
		
		if(Setuper.driver2!=null & Setuper.driver3!=null){
			driver = ax.LOGIN_NA1(driver, 2);
			UserManagementOperations.FindUserInUserSearch_byURID(driver, URID);
			Click_EDIT_BUTTON_ON_PERSONAL_DETAILS(driver);
			ax.type(driver, By.xpath("//input[@id='mainForm:mobilePhoneNumber']"), (SetNumber ? MobilePhoneNumber : ""));
			ax.click(driver, By.xpath("//button[@id='mainForm:buttonNext']"));
			ax.WAIT_FOR_HEADER_PRESENT(driver, "Personal Details Update");
			ax.click(driver, By.xpath("//button[@id='mainForm:buttonNext']"));
			String requestNumber = ax.GRAB_OUTPUT_NR_GREEN_BOX(driver, "The request identifier is");
			driver = ax.LOGIN_NA2(driver, 3);
			return EUCR_API.TASK_APPROVAL_BOTH_TABS_CHECK(driver, "ETL", requestNumber);
		}else{
			// write later
			return true;
		}
	}
	
	public static boolean CHECK_IF_ENROLLED_USER_EXIST_BY_LOGINNAME(WebDriver driver, String UserName, String REG){
		driver = ax.LOGIN_NA1(driver, 2);
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "ENROLLED");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return true; 
		}
		return false;
	}
	
	public static boolean CHECK_IF_URID_USER_IS_ENROLLED(WebDriver driver, String URID){
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.SEARCH_USER_BY_URID(driver, URID);
		// assume that searching by URID returns only one result
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			if(ax.getText(driver, UserManagementOperations.GET_USERS_1x1_RESULT_CELL_STATUS(driver)).trim().equals("Enrolled")){
				return true;
			} 
			// User URID is not enrolled;
			return false;
		}
		// in the case there are not exists enrolled, retrun false
		return false;
	}
	public static boolean CHECK_IF_URID_USER_IS_UNENROLLED(WebDriver driver, String URID){
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.SEARCH_USER_BY_URID(driver, URID);
		// assume that searching by URID returns only one result
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			if(ax.getText(driver, UserManagementOperations.GET_USERS_1x1_RESULT_CELL_STATUS(driver)).trim().equals("Unenrolled")){
				return true;
			} 
			// User URID is not Unenrolled;
			return false;
		}
		// in the case there are not exists enrolled, retrun false
		return false;
	}
	
	public static boolean CHECK_IF_ANY_USER_EXIST_BY_LOGINNAME(WebDriver driver, String UserName, String REG){
		driver = ax.LOGIN_NA1(driver, 2);
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);

		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "ENROLLED");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return true; 
		}
		// go further to registered
		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "REGISTERED");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return true; 
		}
		// go further to validated
		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "VALIDATED");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return true; 
		}
		// in the case there are not exists enrolled, validated, registered - retrun false (we not consider unenrolled and unenrolment pending users)
		return false;
	}
	
	public static boolean CHECK_IF_REGISTERED_USER_EXIST_BY_LOGINNAME(WebDriver driver, String UserName){
		driver = ax.LOGIN_NA1(driver, 2);
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "REGISTERED");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return true; 
		}
		return false;
	}	
	public static boolean CHECK_IF_VALIDATED_USER_EXIST_BY_LOGINNAME(WebDriver driver, String UserName){
		driver = ax.LOGIN_NA1(driver, 2);
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "VALIDATED");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return true; 
		}
		return false;
	}	
	
	public static boolean CHECK_IF_USER_UNENROLL_PENDING_EXIST_BY_LOGINNAME(WebDriver driver, String UserName, String REG){
			driver = ax.LOGIN_NA1(driver, 2);
			UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
			UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "UNENROLLEMENT_PENDING");
			if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
				return true; 
			}
			return false;
	}
	
	public static String getUridForUserEnrolled(WebDriver driver, String UserName){
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "ENROLLED");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return UserManagementOperations.GET_URID_FROM_1ST_ROW(driver); 
		}
		return null;
	}
	public static String getUridForUserWithUnenrollmentPending(WebDriver driver, String UserName){
		UserManagementOperations.OPEN_USER_SEARCH_PAGE(driver);
		UserManagementOperations.SearchUser_ByLOGIN_AND_STATUS(driver, UserName, "UNENROLLEMENT_PENDING");
		if(!ax.isDisplayedBy(driver, UserManagementOperations.NO_RECORDS_FOUND_FOR_USER_SEARCH)){
			return UserManagementOperations.GET_URID_FROM_1ST_ROW(driver); 
		}
		return null;
	}
	
	public static boolean IS_DISPLAYED_FILL_IN_YOUR_PERSONAL_DETAILS(WebDriver driver){
		return Start.heExists(driver, By.xpath(FILL_YOUR_PERSONAL_DETAILS_STRING), true);
	}
	
	
	public static String GET_URID_FROM_AUTHENTICATED_GROUP(WebDriver driver){
		//		We assume there is displayed 
		return PageCanvas.getAuthGrid_URID(driver);
	}
	
	
//	This function will return ENROLLMENT KEY
	
	public static String VALIDATE_USER(WebDriver driver, String URID){
		return VALIDATE_USER(driver, URID, "YES");
	}
	
	public static String VALIDATE_USER(WebDriver driver, String URID, String LOG_EXCEL){
		SeleniumWebUtils.report_FUNCTION_ENTER("VALIDATE_USER");
		driver = ax.LOGIN_NA1(driver);
//		LoginToEUCR.LoginAsNA(driver);
		Boolean u = UserManagementOperations.SearchUserByURID(driver, URID);
		if(u){
			USER_STATUS = UserManagementOperations.GetUserStatusFromFound1Row(driver);
			if(USER_STATUS.toUpperCase().equals(USR_STAT_REGISTERED.toUpperCase())){
//				ExcelTools.REPPORT_PASS("USER_STATUS_REGISTERED");
				ax.t_pass("USER_STATUS_REGISTERED");
			}else{
//				ExcelTools.REPPORT_FAIL("USER_STATUS_REGISTERED");
				ax.LOG_TO_EXCEL(false, "USER_STATUS: "+USER_STATUS, LOG_EXCEL);
				SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
				return null;
			}
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot find the user by URID: "+URID);
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}
		
		String MOBILE = AppSqlQueries.getMOBILE_NR_BY_URID(URID);
		
		if(MOBILE.isEmpty()){
			AppSqlQueries.UPDATE_MOBILE_ON_FOR_USER_URID(URID);
		}
		
		if(UserManagementOperations.GET_URID_FROM_1ST_ROW(driver).equals(URID)){
//			UserManagementOperations.SELECT_CHECKBOX_1ST_ROW(driver);
//			SeleniumWebUtils.XClick(driver, PF_PageElements.PF_USERMGM_VALIDATE_BUTTON, "Cannnot click button VALIDATE");

			
			if(ProjectDataVariables.EUCR_VERSION_NR.startsWith("8.")){
				// NEW VALIDATION WAY - since 8.0.3
				UserManagementOperations.Click_URID_linkinSecondCol(driver);
				GoTo_PersonalDetails_TAB(driver);
				UserManagementOperations.ClickValidateButtonOnUserDetailsPersonalDetails(driver);
				SeleniumWaiter.waitForPageLoaded(driver);
			}else{
				// OLD WAY - up to 8.0.2
				UserManagementOperations.SELECT_CHECKBOX_1ST_ROW(driver);
				SeleniumWebUtils.XClick(driver, PF_PageElements.PF_USERMGM_VALIDATE_BUTTON, "Cannnot click button Validate");	
			}
			
			
			
			
			
		}else{
			SeleniumWebUtils.reportFAIL(driver, "URID in first row is not: "+URID);
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}
		
		if(!ax.WAIT_FOR_HEADER_PRESENT(driver, GUI_LABELS.USERS_PANEL_VALIDATE_USER)) return null;
		
		if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath(VALIDATEUSERS_BOX_STRING + "//button[@id='mainForm:buttonConfirm']"))){
			AppButtons.ClickPF_Button_BY(driver, By.xpath(VALIDATEUSERS_BOX_STRING + "//button[@id='mainForm:buttonConfirm']"));
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot find and click Validate - Confirm button");
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}
		if(!Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_VALIDATE_USER)){return null;}
		
		if(ax.isError1(driver, "Invalid user details")){
			ax.t_error(driver, "Error occured: " + ax.getText(driver, RepoEucr.ERR_ORANGE_BOX_UNIV_XPATH_BY));
			return null;
		}
		
		SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("The users have been validated"));
		SeleniumWaiter.waitForPageLoaded(driver);
		Boolean u1 = UserManagementOperations.SearchUserByURID(driver, URID);
		if(u1){
			USER_STATUS = UserManagementOperations.GetUserStatusFromFound1Row(driver);
			if(USER_STATUS.equals(USR_STAT_VALIDATED)){
//				ExcelTools.REPPORT_PASS("USER_STATUS_VALIDATED");
				ax.t_pass( "USER_STATUS_VALIDATED");
			}else{
//				ExcelTools.REPPORT_FAIL("USER_STATUS_VALIDATED");
				ax.t_pass( "USER_STATUS_VALIDATED");
				SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
				return null;
			}
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot find the user by URID: "+URID+" which should be "+USR_STAT_VALIDATED);
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}
		String ENROLLMENT_KEY = null;
		By ENROLMENT_KEY_CELL = UserManagementOperations.GET_USERS_1x1_RESULT_CELL_ENROLLMENT_KEY(driver);
		if(SeleniumWebUtils.isElementDisplayed(driver, ENROLMENT_KEY_CELL )){
			ENROLLMENT_KEY = SeleniumWebUtils.getTextForElement(driver, ENROLMENT_KEY_CELL);	
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot grab Enrollment Key for user: " + URID);
			SeleniumWebUtils.report_FUNCTION_EXIT("VALIDATE_USER");
			return null;
		}
		
		if(ProjectDataVariables.IS_LOGIN_SMS_ENABLED() && ProjectDataVariables.IS_SIGNATURE_ENABLED()){
			AppSqlQueries.UPDATE_MOBILE_ON_FOR_USER_URID(URID);
		}else{
			AppSqlQueries.UPDATE_MOBILE_OFF_FOR_USER_URID(URID);	
		}
		
		
		return ENROLLMENT_KEY;
	}
	

//	This function will return TRUE/FALSE
	public static boolean ENROLL_USER(WebDriver driver, String URID, String ENROLLMENT_KEY){
		return Registration_Page.ENROLL_USER(driver, URID, ENROLLMENT_KEY, UserUserName);
	}

	public static boolean ENROLL_USER(WebDriver driver, String URID, String ENROLLMENT_KEY, String USER_NAME){
		return ENROLL_USER(driver, URID, ENROLLMENT_KEY, USER_NAME, "YES");
	}

	
	public static boolean ENROLL_USER_ONLY(WebDriver driver, String URID, String ENROLLMENT_KEY, String USER_NAME){
		
		if(!ProjectDataVariables.IS_LOGIN_SMS_ENABLED()){
			AppSqlQueries.UPDATE_MOBILE_OFF_FOR_USER_URID(URID);
		}else{
			AppSqlQueries.UPDATE_MOBILE_ON_FOR_USER_URID(URID);
		}
		
		driver = ax.LOGIN_USR(driver, 1);
		
		if(!ax.IS_NULL_OR_EMPTY_STRING(USER_NAME)){ // we assume that if USER_NAME is givenm than login as USER_NAME, if not than means we are already logged in as any user!
			SeleniumWebUtils.AsserterForTextContains(driver, "Logged in as "+USER_NAME, By.id("localsForm"));
			SeleniumWebUtils.AsserterForTextContains(driver, URID, By.xpath("//span[contains(@id,':uridGroup')]"));
		}
		
		ax.clickWhenReady(driver, ENTER_YOUR_ENROLLMENT_KEY_LINK);
		SeleniumWaiter.waitForPageLoaded(driver);
		Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_ENROLLMENT_KEY_ENTRY);
		String ENROLL_GROUP = "//div[@class='enrolmentKeyContainer']";
			if(SeleniumWebUtils.isElementDisplayed(driver, By.xpath(ENROLL_GROUP))){
				String[] EK = ENROLLMENT_KEY.split("-");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart1']"), EK[0], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart2']"), EK[1], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart3']"), EK[2], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart4']"), EK[3], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XType(driver, By.xpath( ENROLMENT_KEY_ENTRY_BOX + ENROLL_GROUP + "/input[@id='mainForm:enrolmentKeyPart5']"), EK[4], "Problem with typing part of enrollment key");
				SeleniumWebUtils.XClick(driver, By.xpath(ENROLMENT_KEY_ENTRY_BOX + "//button[@id='mainForm:buttonNext']/span[normalize-space()='Submit']/.."), "Cannot click Submit button after typing enrollment Key");
				SeleniumWaiter.waitForPageLoaded(driver);
				
			}else{
				SeleniumWebUtils.reportFAIL(driver, "Enrollment Key fields are not displayed");
				SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
				return false;
			}

			Registration_Page.VERIFY_PAGE_HEADER(driver, GUI_LABELS.USERS_PANEL_ENROLLMENT_KEY_ENTRY);
			SeleniumWebUtils.waitForVisibilityOfElementLocated(driver, PF_PageElements.INFO_GREEN_BOX_WITH_TEXT_XPATH("Your access to the registry has been activated."));
			return SeleniumWebUtils.AsserterForTextContains(driver, "Your access to the registry has been activated.", RepoEucr.INFO_GREEN_BOX_UNV_XPATH);	
	}
	
	public static boolean ENROLL_USER(WebDriver driver, String URID, String ENROLLMENT_KEY, String USER_NAME, String LOG_EXCEL){
		boolean enroll_user = ENROLL_USER_ONLY(driver, URID, ENROLLMENT_KEY, USER_NAME);
		if( !enroll_user ){ax.t_error(driver, "Problem with enrolling user "+USER_NAME);return false;}
		
//			LoginToEUCR.LoginAsNA(driver);
			driver = ax.LOGIN_NA1(driver);
			
			
			Boolean u2 = UserManagementOperations.SearchUserByURID(driver, URID);
			if(u2){
				USER_STATUS = UserManagementOperations.GetUserStatusFromFound1Row(driver);
				if(USER_STATUS.equals(USR_STAT_ENROLLED)){
//					ExcelTools.REPPORT_PASS("USER_STATUS_ENROLLED");
					ax.t_pass( "USER_STATUS_ENROLLED");
					return true;
				}else{
//					ExcelTools.REPPORT_FAIL("USER_STATUS_ENROLLED");
					ax.LOG_TO_EXCEL(false, "USER_STATUS_ENROLLED", LOG_EXCEL);
					SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
					return false;
				}
			}else{
				SeleniumWebUtils.reportFAIL(driver, "Cannot find the user by URID: "+URID+" which should be "+USR_STAT_ENROLLED);
				SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
				return false;
			}	
	}	

//	This function will return TRUE/FALSE
	public static boolean UNENROLL_USER(WebDriver driver, String URID){
		return Registration_Page.UNENROLL_USER(driver, URID, ProjectDataVariables.EUCR_REG);
	}
	public static boolean UNENROLL_USER(WebDriver driver, String URID, String REGISTRY){
				// driver = ax.LOGIN_NA1(driver);
				String UNENROLL_REQUEST_NUMBER = UNENROLL_USER_PROPOSAL(driver, URID);
				if(UNENROLL_REQUEST_NUMBER.equals("STATUS_UNENROLLED")){
						return true;
					}
				// driver = ax.LOGIN_NA2(driver);
				return UNENROLL_USER_APPROVAL(driver, UNENROLL_REQUEST_NUMBER, URID, "YES");
				
				
//				driver = ax.LOGIN_NA2(driver, REGISTRY);
//				
////				APPROVE TASKS .........
////				ApprovingRequests.ApproveRequest_ETL_ByTaskName_REquestNumber_SearchAndDisplay(driver, "Un-enrolment of user(s)", UNENROLL_REQUEST_NUMBER);
////				Boolean apprUEN =  ApprovingRequests.ApproveRequest_Universal_ClickApproveButtons_NoMonit(driver, "unEnrolmentRequestPanel_header", "Un-enrolment", "Approving Task for Un-enrolment");
//				
//				Boolean apprUEN =  ApprovingRequests.ETL_APPROVE_WITH_SIGNATURE(driver, UNENROLL_REQUEST_NUMBER, "Un-enrolment of user(s)", "GENERAL", "unEnrolmentRequestPanel_header", "Un-enrolment", "Approving Task for Un-enrolment");
//				
//				
//				if(apprUEN){
//						ExcelTools.REPPORT_PASS("APPROVED_USER_UNENROLLMENT");
//				}else{
//						ExcelTools.REPPORT_FAIL("APPROVED_USER_UNENROLLMENT");
//					SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
//					return false;
//				}			
//				
//				
//				LoginToEUCR.LoginAsNA(driver, REGISTRY);
//				Boolean u3 = UserManagementOperations.SearchUserByURID(driver, URID);
//				if(u3){
//					USER_STATUS = UserManagementOperations.GetUserStatusFromFound1Row(driver);
//					if(USER_STATUS.equals(USR_STAT_UNENROLLED)){
//						ExcelTools.REPPORT_PASS("USER_STATUS_UNENROLLED");
//						return true;
//					}else{
//						ExcelTools.REPPORT_FAIL("USER_STATUS_UNENROLLED");
//						SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
//						return false;
//					}
//				}else{
//					SeleniumWebUtils.reportFAIL(driver, "Cannot find the user by URID: "+URID+" which should be "+USR_STAT_UNENROLLED);
//					SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
//					return false;
//				}		
	}
	
	public static boolean UNENROLL_USER_APPROVAL(WebDriver driver, String UNENROLL_REQUEST_NUMBER, String URID, String LOG_TO_EXCEL){
		driver=ax.LOGIN_NA2(driver, 3);
		Boolean apprUEN =  ApprovingRequests.ETL_APPROVE(driver, UNENROLL_REQUEST_NUMBER, "Un-enrolment of user(s)", "unEnrolmentRequestPanel_header", "Un-enrolment", "Approving Task for Un-enrolment of user "+URID+", Request Number "+UNENROLL_REQUEST_NUMBER);
		if(apprUEN){
//				ExcelTools.REPPORT_PASS("APPROVED_USER_UNENROLLMENT");
				ax.t_pass("APPROVED_USER_UNENROLLMENT");
		}else{
//				ExcelTools.REPPORT_FAIL("APPROVED_USER_UNENROLLMENT");
				ax.LOG_TO_EXCEL(false, "APPROVED_USER_UNENROLLMENT", LOG_TO_EXCEL);
			SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
			return false;
		}			
		driver=ax.LOGIN_NA1(driver, 2);
		Boolean u3 = UserManagementOperations.SearchUserByURID(driver, URID);
		if(u3){
			USER_STATUS = UserManagementOperations.GetUserStatusFromFound1Row(driver);
			if(USER_STATUS.equals(USR_STAT_UNENROLLED)){
//				ExcelTools.REPPORT_PASS("USER_STATUS_UNENROLLED");
				ax.t_pass("USER_STATUS_UNENROLLED");
				return true;
			}else{
//				ExcelTools.REPPORT_FAIL("USER_STATUS_UNENROLLED");
				ax.LOG_TO_EXCEL(false, "USER_STATUS_UNENROLLED", LOG_TO_EXCEL);
				SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
				return false;
			}
		}else{
			SeleniumWebUtils.reportFAIL(driver, "Cannot find the user by URID: "+URID+" which should be "+USR_STAT_UNENROLLED);
			SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
			return false;
		}		
	}
	
	public static String getUserStatusByUrid(WebDriver driver, String URID){
		driver=ax.LOGIN_NA1(driver);
		Boolean u3 = UserManagementOperations.SearchUserByURID(driver, URID);
		if(u3){
			return UserManagementOperations.GetUserStatusFromFound1Row(driver);
		}
		return null;
	}
	
	
	
	public static String UNENROLL_USER_PROPOSAL(WebDriver driver, String URID){
		driver=ax.LOGIN_NA1(driver, 2);
		Boolean u2 = UserManagementOperations.SearchUserByURID(driver, URID);
				String UNENROLL_REQUEST_NUMBER 		= null;
				String monitWith_RN 				= null;
				Registration_Page.VERIFY_PAGE_HEADER(driver, "User search");
				if(UserManagementOperations.GET_URID_FROM_1ST_ROW(driver).equals(URID)){
					String status=UserManagementOperations.GetUserStatusFromFound1Row(driver);
					if(status.equals("Unenrolled")){
							ax.t_error(driver, "This user is already unenrolled..");
							return "STATUS_UNENROLLED";
						}
					
					
					if(ProjectDataVariables.EUCR_VERSION_NR.startsWith("8.")){
						// NEW WAY - from 8.0.3
						UserManagementOperations.Click_URID_linkinSecondCol(driver);
						UserManagementOperations.ClickUnenrollButtonOnUserDetailsPersonalDetails(driver);
					}else{
						// OLD WAY - up to 8.0.2
						UserManagementOperations.SELECT_CHECKBOX_1ST_ROW(driver);
						SeleniumWebUtils.XClick(driver, PF_PageElements.PF_USERMGM_UNENROLL_BUTTON, "Cannnot click button Un-Enroll");
					}
					
					SeleniumWaiter.waitForPageLoaded(driver);
					System.out.println("UNENROLLING STARTED - REQUIRE APPROVAL....");
					Registration_Page.VERIFY_PAGE_HEADER(driver, "Un-enrolment");
					SeleniumWebUtils.XType(driver, By.xpath("//textarea[@id='mainForm:unEnrolmentReason']"), "Justification for unenrollment of "+ URID, "Cannot type Reason for un-enrolloment");
					SeleniumWebUtils.XClick(driver, By.xpath("//button[@id='mainForm:buttonSubmit']/span[normalize-space()='Submit']/.."), "Cannot click Submit button");
					SeleniumWaiter.waitForPageLoaded(driver);
					Registration_Page.VERIFY_PAGE_HEADER(driver, "Un-enrolment");
					SeleniumWebUtils.AsserterForTextContains(driver, "Your un-enrolment request has been submitted for approval.", RepoEucr.INFO_GREEN_BOX_UNV_XPATH);
//					UNENROLL_REQUEST_NUMBER = ApplicationPageUtils.getNumberFromGreenMonit(driver, PF_PageElements.INFO_GREEN_BOX_UNV_LOCATION, "is");
					monitWith_RN=SeleniumWebUtils.getTextForElement(driver, By.xpath("//div[@id='mainForm:errors']/div[contains(concat(' ',@class,' '),'ui-messages-info')]/ul/li[2]/span[@class='ui-messages-info-summary']"));
					UNENROLL_REQUEST_NUMBER = SeleniumWebUtils.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monitWith_RN, "request identifier is");
					
					if( ToolFunctions.isNumberExist(UNENROLL_REQUEST_NUMBER)  ){
						SeleniumWebUtils.reportPass("Grabbed Requets Number for Unenerollment: "+UNENROLL_REQUEST_NUMBER);
						return UNENROLL_REQUEST_NUMBER;
					}else{
						SeleniumWebUtils.reportFAIL(driver, "Grabbing Requets Problem");
						return null;
					}
				}else{
					SeleniumWebUtils.reportFAIL(driver, "URID in first row is not: "+URID);
					SeleniumWebUtils.report_FUNCTION_EXIT("RUN_USER_SCENARIO");
					return null;
				}		
	}
	
	
	
	public static boolean FILL_ADMIN_ROLES_UPDATE_CONTACT_FIELDS(WebDriver driver, String MOBILENUMBER_TO_UPDATE){
		SeleniumWebUtils.XType(driver, By.id("mainForm:addressLine1"), "AddressLin123", "AddressLin123 error");
		SeleniumWebUtils.XType(driver, By.id("mainForm:addressLine2"), "AddressLin 332123", "mainForm:addressLine2 error");
		SeleniumWebUtils.XType(driver, By.id("mainForm:postCode"), "43223", "Postocode error");
		SeleniumWebUtils.XType(driver, By.id("mainForm:city"), "Barice", "mainForm:city error");
		SeleniumWebUtils.selectAndClickByValue(driver, By.id("mainForm:country"), "BE");
		SeleniumWebUtils.XType(driver, By.id("mainForm:emailAddress"), "l@l.pl", "mainForm:emailAddress error");
		SeleniumWebUtils.XType(driver, By.id("mainForm:confirmEmailAddress"), "l@l.pl", "mainForm:confirmEmailAddress error");
		SeleniumWebUtils.XType(driver, By.id("mainForm:phoneNumber1"), MOBILENUMBER_TO_UPDATE, "mainForm:phoneNumber1 error");
		SeleniumWebUtils.XType(driver, By.id("mainForm:phoneNumber2"), MOBILENUMBER_TO_UPDATE, "mainForm:phoneNumber2 error");
		return (!ax.is_ERROR_DISPLAYED(driver))?true:false;
	}
	public static boolean FILL_UPDATE_BUSINZESS_DETAILS_FIELDS(WebDriver driver, ArrayList<String> arx){
		ArrayList<Boolean> r = new ArrayList<Boolean>();
		r.add(ax.type_bool(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_ADDR1_XPATH), arx.get(0)));
		r.add(ax.type_bool(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_POSTCODE_XPATH), arx.get(1)));
		r.add(ax.type_bool(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_CITY_XPATH), arx.get(2)));
		r.add(ax.select(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_COUNTRY_XPATH), "value", arx.get(3)));
		r.add(ax.type_bool(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_EMAIL1_XPATH), arx.get(4)));
		r.add(ax.type_bool(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_EMAIL2_XPATH), arx.get(4)));
		r.add(ax.type_bool(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_PHONE1_XPATH), arx.get(5)));
		r.add(ax.type_bool(driver, By.xpath(UserManagementOperations.BUSS_DET_CONT_PHONE2_XPATH), arx.get(5)));
		return !r.contains(false);
	}

	
	public static boolean FILL_PERSONAL_DETAILS_UPDATE_FIELDS(WebDriver driver, String UPDATED_MOBILE_NUMBER){
		String HASH = ProjectDataVariables.TEST_SESSION_ID;
		String MOBILE_NUMBER_BOX_ID = "mainForm:mobilePhoneNumber";
		SeleniumWebUtils.XType(driver, By.id("mainForm:title"), HASH, "Cannot type in title");
		SeleniumWebUtils.XType(driver, By.id("mainForm:placeOfBirth"), "MONS "+HASH, "Cannot type in placeOfBirth");
		SeleniumWebUtils.XType(driver, By.id("mainForm:identifierOfIdDoc"), HASH, "Cannot type in identifierOfIdDoc");

		String mn = SeleniumWebUtils.getTextFromEditBoxElement(driver, By.id(MOBILE_NUMBER_BOX_ID));
		if(mn.isEmpty()){ 			SeleniumWebUtils.XType(driver, By.id(MOBILE_NUMBER_BOX_ID), UPDATED_MOBILE_NUMBER, "Entering mobile number "+UPDATED_MOBILE_NUMBER);
		}else{						SeleniumWebUtils.XType(driver, By.id(MOBILE_NUMBER_BOX_ID), UPDATED_MOBILE_NUMBER, "Entering mobile number "+UPDATED_MOBILE_NUMBER);			
		}
		SeleniumWebUtils.XClick(driver, By.xpath(PERSONAL_DETAILS_UPDATE_PAGE_STRING + "//div[@class='buttonContainer']/button[@id='mainForm:buttonNext']/span[@class='ui-button-text' and normalize-space()='Save']"), "Cannot click Save button on Personal Detail update");
		if(ax.is_ERROR_DISPLAYED(driver)){ 			ax.t_error(driver, "Displayed ERROR about: " +ax.getText(driver, RepoEucr.ERROR_VALIDATION_FORM_BOX)  ); 
													return false;}
		SeleniumWaiter.waitForPageLoaded(driver);
		//	Verification of Updated content
		String SearchedString = HASH.substring(0,8);
		String DIV_PACK_OF_UPDATED_TABLE = "//div[@id='mainForm:updatedDetailsPanelId_content']";
		///////////////////////
		WebElement table_element = driver.findElement(By.xpath( DIV_PACK_OF_UPDATED_TABLE +  "//table/tbody[@class='ui-datatable-data']"));
		List<WebElement> tr_collection=table_element.findElements(By.xpath(".//tr"));
        for(WebElement trElement : tr_collection)
        {
            List<WebElement> td_collection=trElement.findElements(By.xpath(".//td"));
            //for(int i=0; i<td_collection.size();i++){
            	String updated=td_collection.get(2).getText();
            	
            	if(updated.contains(SearchedString)){
            		System.out.println("Found updated value in GUI table - NEW VALUE with: "+ updated);return true;
            	}else{
            		System.out.println("Cannot find updated value in GUI table - for NEW VALUE with: "+ updated);return false;
            	}
//            	updated = "";
            //}
        }	
        ////////////////////////
		return false;
	}

	
	public static void factoryRegisterNewUserGetUrids(WebDriver driver, ArrayList<String> names){
//    	ArrayList<String> names = new ArrayList<String>(Arrays.asList("n00239fz",
//    	    	"n00239gi",
//    	    	"n00239gk",
//    	    	"n00239gm",
//    	    	"n00239gn",
//    	    	"n00239gq",
//    	    	"n00239gr",
//    	    	"n00239gs",
//    	    	"n00239gt",
//    	    	"n00239gu",
//    	    	"n00239hc",
//    	    	"n00239hf",
//    	    	"n00239hg",
//    	    	"n00239hi",
//    	    	"n00239hk",
//    	    	"n00239hm",
//    	    	"n00239ho",
//    	    	"n00239hp",
//    	    	"n00239hq",
//    	    	"n00239hs"));
    	
    	
    	
    	ArrayList<String> URIDS = new ArrayList<String>();
    	String URID = null;
    	// register new user by userName
    	for (String userName : names) {
        	driver = ax.LOGIN_AS(driver, userName);
        	// #############################################################
        	// Block for testing if user is NA
//        	URID = ApplicationPageUtils.GET_CURRENT_USER_URID(driver);
//        	System.out.println("CHECKING IF "+userName+" IS NA: " + ax.IS_CURR_USER_NA(driver, URID, false) );
//        	 #############################################################
        	// Block fo registration new user
        	URID = Registration_Page.registerNewUserProposal(driver, userName, true);
        	URIDS.add(URID);
//        	// #############################################################
        	driver = ax.logout(driver);
		}
//    	// checking if the user is NA
//    	URID = ApplicationPageUtils.GET_CURRENT_USER_URID(driver);
//    	System.out.println(  "IS "+userName+" NA: " + ax.IS_CURR_USER_NA(driver, URID, false) );
    	
    	
    		// saving output urid list into file like"
    	/*
    	 * 'BG634563456546',
    	 * 'BG234523453544',
    	 * 'BG267456745675',
    	 */
    		ax.saveArrayListInFileAsLine("C:\\tmp\\kosz\\URIDS_EUCR_TEST_BG.txt", URIDS, true);    
    		System.out.println("");
	}
	
	
	
}
