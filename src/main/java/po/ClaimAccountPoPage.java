package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;
import api.Eucr;



/**
 * Page object representing Account Claim home page.
 *
 * @author kgumulak
 */
public class ClaimAccountPoPage extends AbstractPage<ClaimAccountPoPage> {

	public static ClaimAccountPoPage runEucr;
	
	@FindBy(id="wizardForm:accountIdentifier")
	WebElement identifierInput;	
	
	@FindBy(how=How.XPATH, using="//button[contains(@id,'wizardForm:j_idt')]/span[contains(normalize-space(),'Next')]")
	WebElement nextWizardButton;	
	
	@FindBy(how=How.XPATH, using="//table[@id='wizardForm:accountHolderSelection']//td//label[contains(normalize-space(),'Account Holder is already linked to the user')]/../input[@type='radio']")
	WebElement radioAhIsAlreadyLinkedToTheUser;	
	
	@FindBy(how=How.XPATH, using="//table[@id='wizardForm:accountHolderSelection']//td//label[contains(normalize-space(),'Account Holder is already recorded in the registry')]/../input[@type='radio']")
	WebElement radioAhIsAlreadyRecorded;	

	@FindBy(how=How.XPATH, using="//table[@id='wizardForm:accountHolderSelection']//td//label[contains(normalize-space(),'Account Holder is new')]/../input[@type='radio']")
	WebElement radioAhIsNew;	
	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//table/tbody//label[@for='wizardForm:accountHolderName']/../../..")
	WebElement accountHolderPanel;	

	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderName']")
	WebElement accountHolderPanel_Name;	
	@FindBy(how=How.XPATH, using="//select[@id='wizardForm:accountHolderCountry']")
	WebElement accountHolderPanel_CountrySelect;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderCity']")
	WebElement accountHolderPanel_City;	
	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderPostcode']")
	WebElement accountHolderPanel_Postcode;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderAddressLine1']")
	WebElement accountHolderPanel_Address1;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderAddressLine2']")
	WebElement accountHolderPanel_Address2;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderTelephone1']")
	WebElement accountHolderPanel_Telefon1;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderTelephone2']")
	WebElement accountHolderPanel_Telefon2;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:accountHolderEmailAddress']")
	WebElement accountHolderPanel_Email1;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:confirmAccountHolderEmailAddress']")
	WebElement accountHolderPanel_Email2;	
	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//select[@id='wizardForm:preferredLanguage']")
	WebElement accountHolderPanel_PrefLanguage;	
	
	@FindBy(how=How.XPATH, using="//span[@id='wizardForm:accountHolderTypeGroup']")
	WebElement accountHolderPanel_RadiosPersCompany;	
	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//label[contains(normalize-space(),'Person')]/..//input[@type='radio']")
	WebElement accountHolderPanel_RadioPerson;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//label[contains(normalize-space(),'Company')]/..//input[@type='radio']")
	WebElement accountHolderPanel_RadioCompany;	

	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:companyRegistrationNumber']")
	WebElement accountHolderPanel_CompanyRegistrationNumber;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountHolderPanel']//input[@id='wizardForm:vatRegistrationNumberWithCountryCode']")
	WebElement accountHolderPanel_VatRegistrationNumber;	
	// ############################################################################
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountRepresentative_TablePanel_content']")
	WebElement claim_AddingArsPanel;	

	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountRepresentative_TablePanel_content']//button/span[@class='ui-button-text' and contains(normalize-space(),'Add')]")
	WebElement claim_AddNewArButton;	
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:additionalRepresentative_TablePanel_content']//button/span[@class='ui-button-text' and contains(normalize-space(),'Add')]")
	WebElement claim_AddNewAarButton;	
	// ###[ AR ]################################################
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:accountRepresentative_Panel']")
	WebElement claim_AddAccountHolderRepresentative;	
	@FindBy(how=How.XPATH, using="//table[@id='wizardForm:accountRepresentative_Selection']//label[contains(normalize-space(),'Representative is already related to the Account Holder')]/../input[@type='radio']")
	WebElement claim_RadioAddArRepIsAlreadyRelated;	
	@FindBy(how=How.XPATH, using="//table[@id='wizardForm:accountRepresentative_Selection']//label[contains(normalize-space(),'Representative is not yet related to the Account Holder')]/../input[@type='radio']")
	WebElement claim_RadioAddArRepIsNotRelated;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_urid']")
	WebElement claim_AddAr_Urid;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_companyName']")
	WebElement claim_AddAr_Company;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_companyDepartment']")
	WebElement claim_AddAr_CompanyDepartment;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_jobTitle']")
	WebElement claim_AddAr_JobTitle;
	@FindBy(how=How.XPATH, using="//select[@id='wizardForm:accountRepresentative_country']")
	WebElement claim_AddAr_Country;
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_region']")
	WebElement claim_AddAr_Region;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_city']")
	WebElement claim_AddAr_City;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_postcode']")
	WebElement claim_AddAr_Postcode;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_addressLine1']")
	WebElement claim_AddAr_Address1;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_addressLine2']")
	WebElement claim_AddAr_Address2;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_telephone1']")
	WebElement claim_AddAr_Telephone1;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_telephone2']")
	WebElement claim_AddAr_Telephone2;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:accountRepresentative_emailAddress']")
	WebElement claim_AddAr_Email1;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:confirmaccountRepresentative_emailAddress']")
	WebElement claim_AddAr_Email2;	
	// ###[ AR ]################################################
	@FindBy(how=How.XPATH, using="//div[@id='wizardForm:additionalRepresentative_Panel']")
	WebElement claim_AddAdditionalAccountHolderRepresentative;	
	@FindBy(how=How.XPATH, using="//table[@id='wizardForm:additionalRepresentative_Selection']//label[contains(normalize-space(),'Representative is already related to the Account Holder')]/../input[@type='radio']")
	WebElement claim_RadioAddAdditionalArRepIsAlreadyRelated;	
	@FindBy(how=How.XPATH, using="//table[@id='wizardForm:additionalRepresentative_Selection']//label[contains(normalize-space(),'Representative is not yet related to the Account Holder')]/../input[@type='radio']")
	WebElement claim_RadioAddAdditionalArRepIsNotRelated;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_urid']")
	WebElement claim_AddAdditionalAr_Urid;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_companyName']")
	WebElement claim_AddAdditionalAr_Company;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_companyDepartment']")
	WebElement claim_AddAdditionalAr_CompanyDepartment;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_jobTitle']")
	WebElement claim_AddAdditionalAr_JobTitle;
	@FindBy(how=How.XPATH, using="//select[@id='wizardForm:additionalRepresentative_country']")
	WebElement claim_AddAdditionalAr_Country;
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_region']")
	WebElement claim_AddAdditionalAr_Region;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_city']")
	WebElement claim_AddAdditionalAr_City;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_postcode']")
	WebElement claim_AddAdditionalAr_Postcode;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_addressLine1']")
	WebElement claim_AddAdditionalAr_Address1;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_addressLine2']")
	WebElement claim_AddAdditionalAr_Address2;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_telephone1']")
	WebElement claim_AddAdditionalAr_Telephone1;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_telephone2']")
	WebElement claim_AddAdditionalAr_Telephone2;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:additionalRepresentative_emailAddress']")
	WebElement claim_AddAdditionalAr_Email1;	
	@FindBy(how=How.XPATH, using="//input[@id='wizardForm:confirmadditionalRepresentative_emailAddress']")
	WebElement claim_AddAdditionalAr_Email2;		
	
	@FindBy(how=How.XPATH, using="//button/span[@class='ui-button-text' and contains(normalize-space(),'Save')]")
	WebElement claim_AddAr_SaveButton;	
	// ##[ Contact Person ]##########################################################################
	@FindBy(id="wizardForm:contactPersonFirstName")
	WebElement claimPers_FirstName;	
	@FindBy(id="wizardForm:contactPersonLastName")
	WebElement claimPers_LastName;	
	
	@FindBy(id="wizardForm:contactPersonTelephone1")
	WebElement claimPers_tel1;	
	@FindBy(id="wizardForm:contactPersonTelephone2")
	WebElement claimPers_tel2;	
	@FindBy(id="wizardForm:contactEmailAddress")
	WebElement claimPers_email1;	
	@FindBy(id="wizardForm:confirmContactEmailAddress")
	WebElement claimPers_email2;	
	// ##[ Summary ]##########################################################################
	@FindBy(id="wizardForm:submitBtn")
	WebElement submitSummaryButton;	
	@FindBy(how=How.XPATH, using="//span[@class='ui-messages-info-summary' and contains(normalize-space(),'Account claim request with id')]")
	WebElement greenBoxClaimConfirmation;	
	
	// ############################################################################
	
	@Override
	public String getExpectedPageTitle() {
		return "Account Claim";
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
		return "/protected/accounts/claim/wizard/accountClaim.xhtml";
	}
	public ClaimAccountPoPage goToTaskPage() {
		return new ClaimAccountPoPage().loadPage(ClaimAccountPoPage.class);
	}
	
	public ClaimAccountPoPage open() {
		return new ClaimAccountPoPage().loadPage(ClaimAccountPoPage.class);
	}

	

	
	public String ClaimDialog(String identifier){
		ClaimAccountPoPage claim = ClaimDialogAccountHolder(identifier);
		ax.waitForAjax(getDriver());
		claim = claim.ClaimDialogAddNewArs("AR", ax.GET_USER_URID("AR1"));
		ax.waitForAjax(getDriver());
		claim = claim.ClaimDialogAddNewArs("AR", ax.GET_USER_URID("AR2"));
		claim = clickNextButton();
		claim = claim.ClaimDialogAddNewArs("AAR", ax.GET_USER_URID("AAR1"));
		claim = clickNextButton();
		claim = ClaimDialogContactPerson();
		ax.waitForAjax(getDriver());
		claim = clickNextButton();
		ax.waitForAjax(getDriver());
		ax.wait4element(getDriver(), submitSummaryButton);
		submitSummaryButton.click();
		// Expected success monit: Account claim request with id 236372 has been submitted successfully.
		if(ax.wait4element(getDriver(), greenBoxClaimConfirmation)!=null){
			return ax.GET_WORD_AFTER_TEXT(greenBoxClaimConfirmation.getText().trim(), "Account claim request with id");
		}
		return null;
		
	}
	
	
	public ClaimAccountPoPage ClaimDialogAccountHolder(String identifier){
		ClaimAccountPoPage claim = setIdentifierAndNext(identifier);
		radioAhIsNew.click();
		ax.waitForAjax(getDriver());
//		ax.wait_for_ayax_2(getDriver());
		if(ax.wait_elm(getDriver(), accountHolderPanel)){
			// filling dialog
			accountHolderPanel_Name.sendKeys("New Holder Account");
			ax._selectMe(getDriver(), accountHolderPanel_CountrySelect, "value", "AT");
			accountHolderPanel_City.sendKeys("Holder City");
			accountHolderPanel_Postcode.sendKeys("5432");
			accountHolderPanel_Address1.sendKeys("Kloveringer 7789");
			accountHolderPanel_Address2.sendKeys("Marionex 9980");
			accountHolderPanel_Telefon1.sendKeys("66547789");
			accountHolderPanel_Telefon2.sendKeys("88790");
			accountHolderPanel_Email1.sendKeys("l@l.pl");
			accountHolderPanel_Email2.sendKeys("l@l.pl");
			ax._selectMe(getDriver(), accountHolderPanel_PrefLanguage, "value", "EN");
			ax.wait4element(getDriver(), accountHolderPanel_RadiosPersCompany);
			accountHolderPanel_RadioCompany.click();
			accountHolderPanel_CompanyRegistrationNumber.sendKeys("777890987");
			accountHolderPanel_VatRegistrationNumber.sendKeys("PL 777890987");
			nextWizardButton.click();
			return new ClaimAccountPoPage().loadPageWithExpectedTitle(ClaimAccountPoPage.class);
		}
		return null;
	}
	
	public ClaimAccountPoPage ClaimDialogAddNewArs(String arAarSwitcher, String urid){
		switch (arAarSwitcher) {
		case "AR":
			if(ax.wait_elm(getDriver(), claim_AddNewArButton)){
				claim_AddNewArButton.click();
				ax.waitForAjax(getDriver());
				ax.wait4element(getDriver(), claim_AddAccountHolderRepresentative);
				ax.wait4element(getDriver(), claim_RadioAddArRepIsNotRelated);
				claim_RadioAddArRepIsNotRelated.click();
				ax.waitForAjax(getDriver());
				ax.wait_elm(getDriver(), By.xpath("//label[@for='wizardForm:accountRepresentative_urid']/../../../..")); // table element
				ax._typeMeWithClear(getDriver(), claim_AddAr_Urid, urid);
				ax._typeMeWithClear(getDriver(), claim_AddAr_Company, "Cname_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAr_CompanyDepartment, "CnameDep_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAr_JobTitle, "JobTitle_" + ax.randomString());
				ax._selectMe(getDriver(), claim_AddAr_Country, "value", "AT");
				ax._typeMeWithClear(getDriver(), claim_AddAr_City, "City_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAr_Postcode, ""+ax.randomNumber(5));
				ax._typeMeWithClear(getDriver(), claim_AddAr_Address1, "Addr1_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAr_Address2, "Addr2_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAr_Telephone1, ""+ax.randomNumber(6));
				ax._typeMeWithClear(getDriver(), claim_AddAr_Telephone2, ""+ax.randomNumber(6));
				ax._typeMeWithClear(getDriver(), claim_AddAr_Email1, "l@l.pl");
				ax._typeMeWithClear(getDriver(), claim_AddAr_Email2, "l@l.pl");
			}	
			break;
		case "AAR":
			if(ax.wait_elm(getDriver(), claim_AddNewAarButton)){
				claim_AddNewAarButton.click();
				ax.waitForAjax(getDriver());
				ax.wait4element(getDriver(), claim_AddAdditionalAccountHolderRepresentative);
				ax.wait4element(getDriver(), claim_RadioAddAdditionalArRepIsNotRelated);
				claim_RadioAddAdditionalArRepIsNotRelated.click();
				ax.waitForAjax(getDriver());
				ax.wait_elm(getDriver(), By.xpath("//label[@for='wizardForm:additionalRepresentative_urid']/../../../..")); // table element
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Urid, urid);
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Company, "Cname_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_CompanyDepartment, "CnameDep_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_JobTitle, "JobTitle_" + ax.randomString());
				ax._selectMe(getDriver(), claim_AddAdditionalAr_Country, "value", "AT");
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_City, "City_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Postcode, ""+ax.randomNumber(5));
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Address1, "Addr1_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Address2, "Addr2_" + ax.randomString());
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Telephone1, ""+ax.randomNumber(6));
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Telephone2, ""+ax.randomNumber(6));
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Email1, "l@l.pl");
				ax._typeMeWithClear(getDriver(), claim_AddAdditionalAr_Email2, "l@l.pl");
			}
			break;
		}
		claim_AddAr_SaveButton.click();
			return new ClaimAccountPoPage().loadPageWithExpectedTitle(ClaimAccountPoPage.class);
	}	
	
	public ClaimAccountPoPage ClaimDialogContactPerson(){
		claimPers_FirstName.sendKeys(ax.randomString());
		claimPers_LastName.sendKeys(ax.randomString());
		claimPers_tel1.sendKeys(""+ax.randomNumber(7));
		claimPers_tel2.sendKeys(""+ax.randomNumber(7));
		claimPers_email1.sendKeys("l@l.pl");
		claimPers_email2.sendKeys("l@l.pl");
		clickNextButton();
			return new ClaimAccountPoPage().loadPageWithExpectedTitle(ClaimAccountPoPage.class);
	}	
	
	public ClaimAccountPoPage clickNextButton(){
		nextWizardButton.click();
		return new ClaimAccountPoPage().loadPageWithExpectedTitle(ClaimAccountPoPage.class); 
	}
	
	
	
	public ClaimAccountPoPage setIdentifierAndNext(String identifier){
		ax._typeMeWithClear(getDriver(), identifierInput, identifier);
		nextWizardButton.click();
		ax.wait_for_ayax_2(getDriver());
		return new ClaimAccountPoPage().loadPageWithExpectedTitle(ClaimAccountPoPage.class);
	}
	
	
}
