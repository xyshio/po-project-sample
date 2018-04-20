package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ProjectDataVariables;
import utils.Selenide;
import utils.SeleniumWebUtils;
import utils.ax;
import utils.SeleniumDriver;


/**
 * Page object representing EucrHomePage home page.
 *
 * @author kgumulak
 */
public class EucrHomePage extends AbstractPage<EucrHomePage> {
	public static EucrHomePage runEucr;
	public static EucrHomePage runEucr2;
	public static EucrHomePage runEucr3;
	

	@FindBy(partialLinkText = "Login")
	WebElement eucrLoginLink;	
	
	@FindBy(partialLinkText = "Logout")
	WebElement eucrLogoutLink;	
	// ##[ MENU UPPER - NO GROUPPED ]###################################################################################
	@FindBy(linkText="Home page")
	WebElement homePageLink;	
	
	@FindBy(linkText="Kyoto Protocol Public Reports")
	WebElement kpPublicReportsLinkLink;	
	
	@FindBy(linkText="Task list")
	WebElement taskListLink;	
	// ##[ MENU BLOCKS ]###################################################################################
	@FindBy(how=How.XPATH, using = "//h3[contains(concat(' ',@class,' '),'ui-accordion-header')]/a[contains(normalize-space(),'Accounts')]")
	WebElement menuBlockAccountsLinkHeader;	
	@FindBy(id = "menuForm:accounts_submenu")
	WebElement menuBlockAccounts;	
	
	@FindBy(how=How.XPATH, using = "//h3[contains(concat(' ',@class,' '),'ui-accordion-header')]/a[contains(normalize-space(),'EU ETS')]")
	WebElement menuBlockEuEtsLinkHeader;	
	@FindBy(id = "menuForm:ets_submenu")
	WebElement menuBlockEuEts;	
	
	@FindBy(how=How.XPATH, using = "//h3[contains(concat(' ',@class,' '),'ui-accordion-header')]/a[contains(normalize-space(),'Kyoto Protocol')]")
	WebElement menuBlockKyotoProtocolsLinkHeader;	
	@FindBy(id = "menuForm:kyoto_protocol_submenu")
	WebElement menuBlockKyotoProtocols;	
	
	@FindBy(how=How.XPATH, using = "//h3[contains(concat(' ',@class,' '),'ui-accordion-header')]/a[contains(normalize-space(),'Administration')]/..")
	WebElement menuBlockAdministractionsLinkHeader;
	@FindBy(id = "menuForm:admin_submenu")
	WebElement menuBlockAdministractions;	
	// ##[ MENU LINKS ACCOUNT ]###################################################################################
	@FindBy(id="menuForm:view_accounts_menuitem")
	WebElement accountsLink;	
	@FindBy(id="menuForm:request_account_menuitem")
	WebElement accountRequestLink;	
	@FindBy(id="menuForm:claim_account_menuitem")
	WebElement claimAccountLink;
	@FindBy(id="menuForm:release_account_menuitem")
	WebElement releaseAccountLink;
	
	@FindBy(id="menuForm:view_requests_menuitem")
	WebElement listOfAccountsRequestsLink;	
	@FindBy(id="menuForm:search_transactions_menuitem")
	WebElement transactionsLink;	
	// ##[ MENU LINKS EU ETS ]###################################################################################
	@FindBy(id="menuForm:nap_menuitem")
	WebElement allocationPlansPhase2Link;	
	@FindBy(id="menuForm:phase3ap_menuitem")
	WebElement allocationTablesPhase3Link;	
	@FindBy(id="menuForm:phase3_allocations_menuitem")
	WebElement allocationPhase3Link;	
	@FindBy(id="menuForm:upload_emissions_menuitem")
	WebElement emissionUploadLink;	
	@FindBy(id="menuForm:accounting_clearing_menuitem")
	WebElement accountingAndClearingLink;	
	@FindBy(id="menuForm:iceUpload_menuitem")
	WebElement iceTableUploadLink;	
	@FindBy(id="menuForm:iceTable_menuitem")
	WebElement entitlementLink;	
	@FindBy(id="menuForm:etsClearingInformation_menuitem")
	WebElement etsAccountingAndClearingForCp2Link;	
	// ##[ MENU LINKS EU ETS ]###################################################################################
	@FindBy(id="menuForm:kyoto_issuance_management_menuitem")
	WebElement issuanceLink;	
	@FindBy(id="menuForm:kyoto_protocol_issuance_limits_menuitem")
	WebElement issuanceLimitLink;	
	@FindBy(id="menuForm:ji_projects_menuitem")
	WebElement JiPorjectsLink;	
	@FindBy(id="menuForm:kyoto_itl_notifications_menuitem")
	WebElement ItlNotificationLink;	
	@FindBy(id="menuForm:kyoto_entitlements_menuitem")
	WebElement Kp2EntitlementLink;	
	@FindBy(id="menuForm:kyoto_esdClearingInformation_menuitem")
	WebElement esdAccountingAndClearingForCp2Link;	
	// ##[ MENU LINKS ADMINISTRATION ]###################################################################################
	@FindBy(id="menuForm:list_unitBlocks_menuitem")
	WebElement unitBlocksLink;	
	@FindBy(id="menuForm:search_users_menuitem")
	WebElement usersLink;	
	@FindBy(id="menuForm:manage_roles_menuitem")
	WebElement rolesAndPermissionsLink;	
	@FindBy(id="menuForm:list_accountGroups_menuitem")
	WebElement listOfAccountGroupLink;	
	@FindBy(id="menuForm:send_accept_message_menuitem")
	WebElement sendMessageLink;	
	@FindBy(id="menuForm:holidays_menuitem")
	WebElement holidaysLink;	
	@FindBy(id="menuForm:reconciliations_menuitem")
	WebElement reconciliationsLink;	
	@FindBy(id="menuForm:messageLogs_menuitem")
	WebElement messageLogsLink;	
	@FindBy(id="menuForm:sefReporting_menuitem")
	WebElement sefReportingLink;	
	@FindBy(id="menuForm:adminWelcomeMsg_menuitem")
	WebElement updateFrontPageLink;	
	@FindBy(id="menuForm:bwListDetailsAdmin_menuitem")
	WebElement viewIchListLink;	
	@FindBy(id="menuForm:bwListLog_menuitem")
	WebElement viewIchListLogLink;	
	// ##[ MENU LINKS END ]###################################################################################
	@FindBy(linkText="Fill in your personal details")
	WebElement fillInYourPersonalDetails;
	
	
	
	@FindBy(how=How.XPATH,using ="//div[@id='topMenu']/form/span[contains(normalize-space(),'Logged in as ')]")
	WebElement userNameLabel;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Home Page";
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
		return "/index.xhtml";
	}
	public EucrHomePage openEucrHomePage() {
		homePageLink.click();
		ax.wait(1);
		return new EucrHomePage().loadPage(EucrHomePage.class);
	}
	public TaskPage openTaskPage() {
		taskListLink.click();
		ax.wait(1);
		return new TaskPage().loadPageWithExpectedTitle(TaskPage.class);
	}
	public EcasPage clickLoginLink(){
		eucrLoginLink.click();
		EcasPage ecasPage = new EcasPage().loadPage(EcasPage.class);
		return ecasPage;
	}
	public EucrHomePage clickLogoutLink(){
		eucrLogoutLink.click();
		return new EucrHomePage().loadPage(EucrHomePage.class);
	}
	public RegistrationPage clickFillInYourPersonalDetails(){
		if(fillInYourPersonalDetails.isDisplayed()){
			fillInYourPersonalDetails.click();
			return new RegistrationPage().loadPage(RegistrationPage.class);
		}
		return null;
	}
	
	public EucrHomePage clickHomePageLink(){
		if(homePageLink!=null){
			homePageLink.click();
			return new EucrHomePage().loadPage(EucrHomePage.class);
		}else{
			getDriver().findElement(By.linkText("Home page")).click();
			return new EucrHomePage().loadPage(EucrHomePage.class);
		}
	}
	public AccountRequestOpeningDetails clickAccountRequestLink(){
		accountRequestLink.click();
		return new AccountRequestOpeningDetails().loadPage(AccountRequestOpeningDetails.class);
	}
	
	
	public Object openEucrMenu(String MenuTitle){
		switch (MenuTitle) {
		case "Accounts": 
			openSubMenu("ACC");
			ax.wait_for(getDriver(), accountsLink);
			accountsLink.click(); 
			ax.wait(1);
			return new AccountsPage().loadPageWithExpectedTitle(AccountsPage.class);
		case "Account Request": 
			openSubMenu("ACC"); 
			ax.wait_elm(getDriver(), accountRequestLink);
			accountRequestLink.click();
			ax.wait(1);
			return new AccountRequestOpeningDetails().loadPageWithExpectedTitle(AccountRequestOpeningDetails.class);
		case "Release account": 
			openSubMenu("ACC"); 
			ax.wait_elm(getDriver(), releaseAccountLink);
			releaseAccountLink.click();
			ax.wait(1);
			return new AccountReleasePoPage().loadPageWithExpectedTitle(AccountReleasePoPage.class, "Account Release");
		case "Claim account": 
			openSubMenu("ACC"); 
			ax.wait_elm(getDriver(), claimAccountLink);
			claimAccountLink.click();
			ax.wait(1);
			return new ClaimAccountPoPage().loadPageWithExpectedTitle(ClaimAccountPoPage.class, "Account Claim");
		case "Transactions": 
			openSubMenu("ACC"); 
			ax.wait_elm(getDriver(), transactionsLink);
			transactionsLink.click();
			ax.wait(1);
			return new TransactionsPoPage().loadPageWithExpectedTitle(TransactionsPoPage.class);
		case "Entitlements": 
			openSubMenu("ETS"); 
			ax.wait_elm(getDriver(), entitlementLink);
			entitlementLink.click();
			ax.wait(1);
			return new EntitlementPoPage().loadPageWithExpectedTitle(EntitlementPoPage.class);
		case "Issuance": 
			openSubMenu("KPL"); 
			ax.wait_for(getDriver(), issuanceLink);
			issuanceLink.click();
			ax.wait(1);
			return new IssuancePoPage().loadPageWithExpectedTitle(IssuancePoPage.class);
		case "Users": 
			openSubMenu("ADM"); 
			ax.wait_for(getDriver(), usersLink);
			ax._clickMe(getDriver(), usersLink);
			ax.wait(1);
			return new UsersPoPage().loadPageWithExpectedTitle(UsersPoPage.class);
		}
		return null;
	}
	
	
	public void openSubMenu(String group){
		switch (group) {
		case "ACC":
			if(menuBlockAccounts.getAttribute("style").contains("display: none;")){
//				menuBlockAccountsLinkHeader.click();
				ax._clickMe(getDriver(), menuBlockAccountsLinkHeader);
				ax.wait_for_ayax_2(getDriver());
				ax.wait_for(getDriver(), menuBlockAccounts);
				ax.WAIT4Element_Got_CSS_VALUE(getDriver(), menuBlockAccounts, "display", "block");
			}
			break;
		case "ETS":
			if(menuBlockEuEts.getAttribute("style").contains("display: none;")){
//				menuBlockEuEtsLinkHeader.click();
				ax._clickMe(getDriver(), menuBlockEuEtsLinkHeader);
				ax.wait_for_ayax_2(getDriver());
				ax.wait_for(getDriver(), menuBlockEuEts);
				ax.WAIT4Element_Got_CSS_VALUE(getDriver(), menuBlockEuEts, "display", "block");
			}
			break;
		case "KPL":
			if(menuBlockKyotoProtocols.getAttribute("style").contains("display: none;")){
//				menuBlockKyotoProtocolsLinkHeader.click();
				ax._clickMe(getDriver(), menuBlockKyotoProtocolsLinkHeader);
				ax.wait_for_ayax_2(getDriver());
				ax.wait_for(getDriver(), menuBlockKyotoProtocols);
				ax.WAIT4Element_Got_CSS_VALUE(getDriver(), menuBlockKyotoProtocols, "display", "block");
			}
			break;
		case "ADM":
			if(menuBlockAdministractions.getAttribute("style").contains("display: none;")){
				ax._clickMe(getDriver(), menuBlockAdministractionsLinkHeader);
//				menuBlockAdministractionsLinkHeader.click();
				ax.wait_for_ayax_2(getDriver());
				ax.wait_for(getDriver(), menuBlockAdministractions);
				ax.WAIT4Element_Got_CSS_VALUE(getDriver(), menuBlockAdministractions, "display", "block");
			}
			break;
		}
	}
	
	
	public TransactionsPoPage clickTransactionsLink(){
		transactionsLink.click();
		return new TransactionsPoPage().loadPage(TransactionsPoPage.class);
	}
	
	public String getUserNameFromTop(){
		if(userNameLabel.isDisplayed()){
			String[] userName = userNameLabel.getText().trim().split(" ");
			return userName[3].trim();
		}
		return null;
	}
	
	public EucrHomePage logoutToHomePage(){ 
		// as result user is logout completely in Ecas and landing in Eucr HomePage
		// we assume to start this method when user is logged-in
		return new EucrHomePage().clickLogoutLink(); // landing in EucrHomePage with user un-loged-in
	}	
	
	public static EucrHomePage reloginAs(String login){
		return reloginAs(1, login);
	}
	public static EucrHomePage reloginAs(int driverInstance, String login){
		
		SeleniumDriver.setCurrentDriverInstance(driverInstance);
		
		EucrHomePage curRunEucr;
		
		switch (driverInstance) {
		case 1: 			curRunEucr = runEucr; 			break;
		case 2: 			curRunEucr = runEucr2; 			break;
		case 3: 			curRunEucr = runEucr3; 			break;
		default: 			curRunEucr = runEucr; 			break;
		}
		
		
		
		if(curRunEucr == null){
			curRunEucr 	= ax.LoginToEucr(driverInstance, login);
			switch (driverInstance) {
			case 1: runEucr = curRunEucr; break;
			case 2: runEucr2 = curRunEucr; break;
			case 3: runEucr3 = curRunEucr; break;
			default: runEucr = curRunEucr; break;
			}
			
			return curRunEucr;
		}
		String name = null;
		// case when cannot grab user name - page is not displayed
		try {
			name = curRunEucr.getUserNameFromTop();
		} catch (Exception e) {
			curRunEucr 	= ax.LoginToEucr(driverInstance, SeleniumDriver.getCurrentBrowserType(), login);
			switch (driverInstance) {
			case 1: runEucr = curRunEucr; break;
			case 2: runEucr2 = curRunEucr; break;
			case 3: runEucr3 = curRunEucr; break;
			default: runEucr = curRunEucr; break;
			}			
			return curRunEucr;
		}
		
		
		if(name!=null){
				if(name.contains(login)){
//					ax.log("Already logged in as: " + login);
					return curRunEucr;
				}
				
			}else{
				ax.log("=== looks like eucr page displayed but user is not logged-in");
			}
	new EucrHomePage().clickHomePageLink();
	curRunEucr = new EucrHomePage().loadPage(EucrHomePage.class).clickLogoutLink();
	EcasPage ecas = curRunEucr.clickLoginLink();
	ecas.ecasLogoutToBasic();
	return ecas.login(login, ProjectDataVariables.EUCR_PASSWORD);
	}
	
	public EucrHomePage open() {
		return new EucrHomePage().loadPage(EucrHomePage.class);
	}

}
