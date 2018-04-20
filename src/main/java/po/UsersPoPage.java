package po;

import static utils.SeleniumDriver.getDriver;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;



/**
 * Page object representing Users home page.
 *
 * @author kgumulak
 */
public class UsersPoPage extends AbstractPage<UsersPoPage> {

	public static UsersPoPage runEucr;
	
	@FindBy(linkText="Users")
	WebElement usersPageLink;	
	
	@FindBy(id="mainForm:urid")
	WebElement usersUridInput;	
	@FindBy(id="mainForm:login")
	WebElement usersLoginInput;	
	@FindBy(id="mainForm:firstName")
	WebElement usersFirstNameInput;	
	@FindBy(id="mainForm:lastName")
	WebElement usersLastNameInput;	

	@FindBy(id="mainForm:status")
	WebElement usersStatusSelect;	
	@FindBy(id="mainForm:role")
	WebElement usersRoleSelect;	
	
	@FindBy(id="mainForm:buttonSearch")
	WebElement usersSearchButton;	
	
	@FindBy(how=How.XPATH, using="//div[@id='mainForm:userSearchResultsTable']//tbody[@id='mainForm:userSearchResultsTable_data']/tr/td[position()=1]/a[contains(@onclick,'urid')]")
	WebElement usersFirstUserInTableLink;	
	
	@FindBy(how=How.XPATH, using="//div[@id='mainForm:userSearchResultsTable']//tbody[@id='mainForm:userSearchResultsTable_data']/tr/td[position()=2]")
	WebElement usersFirstUserInTableStatus;	
		
		
	
	@FindBy(how=How.XPATH, using="//button[contains(@id,'buttonUnEnrol')]")
	WebElement usersUnenrollButton;
	
	@FindBy(id="mainForm:unEnrolmentReason")
	WebElement unenrollmentReasonTextarea;
	
	@FindBy(id="mainForm:buttonSubmit")
	WebElement unenrollmentSubmitButton;
	
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-messages-info')]/ul/li[position()=2]/span[@class='ui-messages-info-summary']")
	WebElement greenBoxForProposedAction;

	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    // TABS-USER-DETAILS
	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #	
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li[contains(concat(' ',@class,' '),'ui-tabs-selected')]//a//em[contains(normalize-space(),'Personal Details')]/../..")
	WebElement userTabsPersonalDetailsActiveLi;
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li//a//em[contains(normalize-space(),'Personal Details')]/..")
	WebElement userTabsPersonalDetailsA;
	
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li[contains(concat(' ',@class,' '),'ui-tabs-selected')]//a//em[contains(normalize-space(),'Business Details')]/../..")
	WebElement userTabsBusinessDetailsActiveLi;
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li//a//em[contains(normalize-space(),'Business Details')]/..")
	WebElement userTabsBusinessDetailsA;
	
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li[contains(concat(' ',@class,' '),'ui-tabs-selected')]//a//em[contains(normalize-space(),'Administration roles')]/../..")
	WebElement userTabsAdminstrationRolesActiveLi;
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li//a//em[contains(normalize-space(),'Administration roles')]/..")
	WebElement userTabsAdminstrationRolesA;

	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li[contains(concat(' ',@class,' '),'ui-tabs-selected')]//a//em[contains(normalize-space(),'Accounts')]/../..")
	WebElement userTabsAccountsActiveLi;
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-tabs')]//ul[contains(concat(' ',@class,' '),'ui-tabs-nav')]//li//a//em[contains(normalize-space(),'Accounts')]/..")
	WebElement userTabsAccountsA;
	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
    // BUSINESS-DETAILS
	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
	@FindBy(how=How.XPATH, using="//button[contains(@id,'buttonEditBusinessDetails')]")
	WebElement bdUpdateButton;
	@FindBy(how=How.XPATH, using="//button[contains(@id,'buttonEditAdministrationRoles')]")
	WebElement bdUpdateAdminRole;
	
	@FindBy(id="mainForm:companyName")
	WebElement bdCompanyName;
	@FindBy(id="mainForm:companyDepartment")
	WebElement bdCompanyDepartment;
	@FindBy(id="mainForm:jobTitle")
	WebElement bdJobTitle;

	@FindBy(id="mainForm:addressLine1")
	WebElement bdAddressLine1;
	@FindBy(id="mainForm:addressLine2")
	WebElement bdAddressLine2;
	@FindBy(id="mainForm:postCode")
	WebElement bdPostCode;
	@FindBy(id="mainForm:city")
	WebElement bdCity;
	
	@FindBy(id="mainForm:region")
	WebElement bdRegioneOrState;
	@FindBy(id="mainForm:country")
	WebElement bdCountrySelect;
	
	@FindBy(id="mainForm:emailAddress")
	WebElement bdEmail1;
	@FindBy(id="mainForm:confirmEmailAddress")
	WebElement bdEmail2;
	@FindBy(id="mainForm:phoneNumber1")
	WebElement bdPhone1;
	@FindBy(id="mainForm:phoneNumber2")
	WebElement bdPhone2;
	
	@FindBy(id="mainForm:buttonNext")
	WebElement bdSubmitButton;
	
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:selectionsRadio']//td//label[contains(normalize-space(),'Service Desk')]/../input")
	WebElement admServiceDeskRadio;
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:selectionsRadio']//td//label[contains(normalize-space(),'SD Agent')]/../input")
	WebElement admSDAgentRadio;
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:selectionsRadio']//td//label[contains(normalize-space(),'Auditor For NA')]/../input")
	WebElement admANARadio;
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:selectionsRadio']//td//label[contains(normalize-space(),'National Administrator')]/../input")
	WebElement admNARadio;
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:selectionsRadio']//td//label[contains(normalize-space(),'Central Administrator')]/../input")
	WebElement admCARadio;
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:selectionsRadio']//td//label[contains(normalize-space(),'System Administrator')]/../input")
	WebElement admSARadio;
	
	@FindBy(how=How.XPATH, using="//div[contains(concat('',@class,''),'ui-messages-info') and contains(normalize-space(),'administration roles update request has been submitted for approval')]")
	WebElement greenBoxAfterPromoteUser;
	
	@FindBy(how=How.XPATH, using="//div[@id='mainForm:userSearchResultsTable_paginatortop']//span[@class='ui-paginator-current']")
	WebElement paginatorResultTextHolder;
	
	@FindBy(id="mainForm:buttonNext")
	WebElement admNextButton;
	@FindBy(id="mainForm:buttonNext")
	WebElement admSubmitButton;
	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
	// ADMIN ROLES UPDATE
	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
	
	
	// # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 
	@Override
	public String getExpectedPageTitle() {
		return "Search Users";
	}
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.titleContains(getExpectedPageTitle());
	}	
	@Override
	public ExpectedCondition<?> getPageLoadCondition(String titlePart) {
		return ExpectedConditions.titleContains(titlePart);
	}

	@Override
	public String getPageUrl() {
		return "/protected/users/search/index.xhtml";
	}
	public UsersPoPage goToTaskPage() {
		return new UsersPoPage().loadPage(UsersPoPage.class);
	}
	
	public UsersPoPage open() {
		return new UsersPoPage().loadPage(UsersPoPage.class);
	}

	public String proposeUnenrollmentOfUrid(UsersPoPage user, String URID){
		user.searchUserByUrid(URID);
		user.clickFirstUserLink();
		user.clickUnenrollButton();
		user.setUnenrollmentReason();
		user.clickSubmitButtonForUnenrollment();
		return getIdentifierForProposedAction();
		
	}
	
	public int getRowsFoundFromPaginator(){
		try {
			if(paginatorResultTextHolder.isDisplayed()){
				return ax.get_number_found_in_paginator(getDriver(), paginatorResultTextHolder);
			}
			return -1;	
		} catch (Exception e) {
			return -1;
		}
	}
	
	public String getUserUridFrom1x1(){
		return usersFirstUserInTableLink.getText().trim();
	}
	public String getUserStatusFrom1x1(){
		return usersFirstUserInTableStatus.getText().trim();
	}
	
	public UsersPoPage searchUserByUrid(String URID){
		ax._typeMeWithClear(getDriver(), usersUridInput, URID);
		ax._clickMe(getDriver(), usersSearchButton);
		return new UsersPoPage().loadPage(UsersPoPage.class);
	}
	public UsersPoPage searchUserByLogin(String loginName){
		ax._typeMeWithClear(getDriver(), usersLoginInput, loginName);
		ax._clickMe(getDriver(), usersSearchButton);
		return new UsersPoPage().loadPage(UsersPoPage.class);
	}
	public UsersPoPage searchUserByLoginStatus(String loginName, String status){
		ax._typeMeWithClear(getDriver(), usersLoginInput, loginName);
		ax._selectMe(getDriver(), usersStatusSelect, "text", status);
		ax._clickMe(getDriver(), usersSearchButton);
		return new UsersPoPage().loadPage(UsersPoPage.class);
	}	
	public UsersPoPage clickFirstUserLink(){
		usersFirstUserInTableLink.click();
		return new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "User Details");
	}
	
	public String selectRadioRoleAndClick(String role){
		switch (role) {
		case "SDA"	: this.admSDAgentRadio.click(); 		break;
		case "SD"	: this.admServiceDeskRadio.click(); 	break;
		case "ANA"	: this.admANARadio.click(); 	break;
		case "NA"	: this.admNARadio.click(); 	break;
		case "CA"	: this.admCARadio.click(); 	break;
		case "SA"	: this.admSARadio.click(); 	break;
		}
		this.admNextButton.click();
		UsersPoPage user = new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "Review Administrative Roles Updates");
		this.admSubmitButton.click();
		user = new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "Review Administrative Roles Updates");
		String text = ax.getText_from_WebElement(getDriver(), this.greenBoxAfterPromoteUser);
		return ax.GET_WORD_AFTER_TEXT(text, "identifier is");
	}
	
	public UsersPoPage clickUnenrollButton(){
		usersUnenrollButton.click();
		return new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "Un-Enrol User");
	}
	
	public void setUnenrollmentReason(){
		unenrollmentReasonTextarea.sendKeys("This is the reason for unenrolling user: He is soooo good" );
	}
	
	public UsersPoPage clickSubmitButtonForUnenrollment(){
		unenrollmentSubmitButton.click();
		return new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "Un-Enrol User");
	}
	
	public String getIdentifierForProposedAction(){
		if(greenBoxForProposedAction.isDisplayed()){
			String text = ax.getText_from_WebElement(getDriver(), greenBoxForProposedAction);
			return ax.GET_WORD_AFTER_TEXT(text, "identifier is");
		}
		return null;
	}

	
	public UsersPoPage clickTabOnUserDetails(String TabName){
		if(TabName.equals("PersonalDetails") && !tabDisplayed("PersonalDetails")){
			ax._clickMe(getDriver(), userTabsPersonalDetailsA);
		}
		if(TabName.equals("BusinessDetails") && !tabDisplayed("BusinessDetails")){
			ax._clickMe(getDriver(), userTabsBusinessDetailsA);
		}
		if(TabName.equals("AdministrationRoles") && !tabDisplayed("AdministrationRoles")){
			ax._clickMe(getDriver(), userTabsAdminstrationRolesA);
		}
		if(TabName.equals("Accounts") && !tabDisplayed("Accounts")){
			ax._clickMe(getDriver(), userTabsAccountsA);
		}
		return new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "User Details");
	}	
	
	public UsersPoPage clickUpdateBusinessDetails(){
		if(!tabDisplayed("BusinessDetails")){
			ax._clickMe(getDriver(), userTabsBusinessDetailsA);
		}
		bdUpdateButton.click();
		return new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "Update Administrative Roles");
	}
	public UsersPoPage clickAdministrationRoles(){
		if(!tabDisplayed("AdministrationRoles")){
			ax._clickMe(getDriver(), userTabsAdminstrationRolesA);
		}
		try {
			bdUpdateAdminRole.click();
			return new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "Update Administrative Roles");
		} catch (Exception e) {
			return null;
		}

	}
	
	public boolean tabDisplayed(String tabName){
		WebElement elm = null;
		switch (tabName) {
			case "PersonalDetails"		: elm = this.userTabsPersonalDetailsActiveLi; break;
			case "BusinessDetails"		: elm = this.userTabsBusinessDetailsActiveLi; break;
			case "AdministrationRoles"	: elm = this.userTabsAdminstrationRolesActiveLi; break;
			case "Accounts"				: elm = this.userTabsAccountsActiveLi; break;
		}
		try {
			ax.turnOff_time(getDriver());
			if(elm.isDisplayed()){
				ax.turnOn_time(getDriver());
				return true;
			}
		} catch (Exception e) {
			ax.turnOn_time(getDriver());
			return false;
		}
		return false;
		
	}
	
	public String proposeUpdateOfBusinessDetails(ArrayList<String> valueToUpdate){
		ax._typeMeWithClear(getDriver(), bdCompanyName					, valueToUpdate.get(0));
		ax._typeMeWithClear(getDriver(), bdCompanyDepartment			, valueToUpdate.get(1));
		ax._typeMeWithClear(getDriver(), bdJobTitle						, valueToUpdate.get(2));
		ax._typeMeWithClear(getDriver(), bdAddressLine1					, valueToUpdate.get(3));
		ax._typeMeWithClear(getDriver(), bdAddressLine2					, valueToUpdate.get(4));
		ax._typeMeWithClear(getDriver(), bdPostCode						, valueToUpdate.get(5));
		ax._typeMeWithClear(getDriver(), bdCity							, valueToUpdate.get(6));
		ax._typeMeWithClear(getDriver(), bdRegioneOrState				, valueToUpdate.get(7));
		ax._selectMe(getDriver(), bdCountrySelect	, "value"			, valueToUpdate.get(8));
		ax._typeMeWithClear(getDriver(), bdEmail1						, valueToUpdate.get(9));
		ax._typeMeWithClear(getDriver(), bdEmail2						, valueToUpdate.get(10));
		ax._typeMeWithClear(getDriver(), bdPhone1						, valueToUpdate.get(11));
		ax._typeMeWithClear(getDriver(), bdPhone2						, valueToUpdate.get(12));
		ax._clickMe(getDriver(), bdSubmitButton);
		UsersPoPage user = new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class, "Review Administrative Roles Updates");
		ax._clickMe(getDriver(), bdSubmitButton);
		return getIdentifierForProposedAction();
	}	
	
	public boolean checkUpdatedBusinessDetails(UsersPoPage user, String URID, ArrayList<String> updatedValues){
		UsersPoPage users 	= user.searchUserByUrid(URID);
		users 				= user.clickFirstUserLink();
		users 				= user.clickTabOnUserDetails("BusinessDetails");
		ArrayList<Boolean> REXB = new ArrayList<Boolean>();
		for (String value : updatedValues) {
				if(getDriver().getPageSource().contains(value)){
					REXB.add(true);
				}else{
					REXB.add(false);
				}
		}
		return !REXB.contains(false);
	}
	
	
}
