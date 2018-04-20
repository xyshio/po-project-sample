package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;



/**
 * Page object representing eucr account holder information page.
 *
 * @author kgumulak
 */
public class AccountRequestHolderInformation extends AbstractPage<AccountRequestHolderInformation> {

	@FindBy(how=How.XPATH, xpath="//label[contains(normalize-space(),'is already recorded in the registry')]/../input[@name='mainForm:accountHolderLinkedWithUserRadio']")
	WebElement radioAccountHolderInRegistry;	
	
	@FindBy(how=How.XPATH, xpath="//label[contains(normalize-space(),'is new')]/../input[@name='mainForm:accountHolderLinkedWithUserRadio']")
	WebElement radioAccountHolderIsNew;	
	
	@FindBy(id = "mainForm:accountHolderId")
	WebElement accountHolderIdInput;	

	@FindBy(id = "mainForm:accountHolderName")
	WebElement accountHolderNewNameInput;	
	
	@FindBy(id = "mainForm:accountHolderCountry")
	WebElement accountHolderNewCountrySelect;
	
	@FindBy(id = "mainForm:accountHolderCity")
	WebElement accountHolderNewCityInput;	
	
	@FindBy(id = "mainForm:accountHolderPostcode")
	WebElement accountHolderNewPostcodeInput;	
	
	@FindBy(id = "mainForm:accountHolderAddressLine1")
	WebElement accountHolderNewAddress1Input;	

	@FindBy(id = "mainForm:accountHolderAddressLine2")
	WebElement accountHolderNewAddress2Input;	
	
	@FindBy(id = "mainForm:accountHolderTelephone1")
	WebElement accountHolderNewTel1Input;	
	
	@FindBy(id = "mainForm:accountHolderTelephone2")
	WebElement accountHolderNewTel2Input;	
	
	@FindBy(id = "mainForm:accountHolderEmailAddress")
	WebElement accountHolderNewEmail1Input;	
	
	@FindBy(id = "mainForm:confirmAccountHolderEmailAddress")
	WebElement accountHolderNewEmail2Input;	
	
	@FindBy(id = "mainForm:preferredLanguage")
	WebElement accountHolderNewPrefLanguageSelect;	
	
	// ========================================================
	@FindBy(how=How.XPATH, using="//td/label[contains(normalize-space(),'Person')]/../input[contains(@id,'mainForm:accountHolderTypeRadio:')]")
	WebElement accountHolderNewPersonRadio;	
	
	@FindBy(how=How.XPATH, using="//td/label[contains(normalize-space(),'Company')]/../input[contains(@id,'mainForm:accountHolderTypeRadio:')]")
	WebElement accountHolderNewCompanyRadio;	
	// ========================================================
	@FindBy(id = "mainForm:dateOfBirth_input")
	WebElement accountHolderNewDateOfBirth;	
	
	@FindBy(id = "mainForm:placeOfBirth")
	WebElement accountHolderNewPlaceOfBirthInput;	
	
	@FindBy(id = "mainForm:countryOfBirth")
	WebElement accountHolderNewCountryOfBirthSelect;	
	
	@FindBy(id = "mainForm:typeOfIdDoc")
	WebElement accountHolderNewIdentDocTypeSelect;	
	
	@FindBy(id = "mainForm:identifierOfIdDoc")
	WebElement accountHolderNewIdentDocIdentifierInput;	
	// ========================================================
	@FindBy(id = "mainForm:companyRegistrationNumber")
	WebElement accountHolderNewCompanyRegistrationNumberInput;	

	@FindBy(id = "mainForm:vatRegistrationNumberWithCountryCode")
	WebElement accountHolderNewVatRegistrationNumberInput;	
	// ========================================================
	@FindBy(id = "mainForm:buttonNext")
	WebElement buttonNext;	
	@FindBy(id = "mainForm:buttonSubmit")
	WebElement buttonSubmit;	
	
	
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.titleContains("Account Holder Information");
	}
	@Override
	public ExpectedCondition<?> getPageLoadCondition(String titlePart) {
		return ExpectedConditions.titleContains(titlePart);
	}

	@Override
	public String getPageUrl() {
		return "";
	}
	
	public AccountBusinessDetails selectWhenHolderIsNewAndProvideHolderDeatils(boolean isPersonal /*true: Person radio on, false: Company radio on*/){
		radioAccountHolderIsNew.click();
		ax.wait_for_ayax_2(getDriver());
		ax.wait4element(getDriver(), accountHolderNewNameInput);
		accountHolderNewNameInput.sendKeys("Holder Name " + ax.GET_TIMESTAMP_SAVE());
		ax._selectMe(getDriver(), accountHolderNewCountrySelect, "value", "AT");
		accountHolderNewCityInput.sendKeys("Monrovia");
		accountHolderNewPostcodeInput.sendKeys("33234");
		accountHolderNewAddress1Input.sendKeys("Plochowice 7789");
		accountHolderNewAddress2Input.sendKeys("Woj Avengerskie");
		accountHolderNewTel1Input.sendKeys("77890987");
		accountHolderNewTel2Input.sendKeys("887698");
		accountHolderNewEmail1Input.sendKeys("l@l.pl");
		accountHolderNewEmail2Input.sendKeys("l@l.pl");
		ax._selectMe(getDriver(), accountHolderNewPrefLanguageSelect, "value", "EN");
		accountHolderNewCompanyRadio.click();
		ax.wait_for_ayax_2(getDriver());
		if(isPersonal){
			accountHolderNewPersonRadio.click();
			ax.wait_for_ayax_2(getDriver());
			accountHolderNewDateOfBirth.sendKeys("16/01/1978");
			accountHolderNewPlaceOfBirthInput.sendKeys("Dagiestancew");
			ax._selectMe(getDriver(), accountHolderNewCountryOfBirthSelect, "value", "AT");
			ax._selectMe(getDriver(), accountHolderNewIdentDocTypeSelect, "value", "NATIONAL_ID_CARD");
			accountHolderNewIdentDocIdentifierInput.sendKeys("777890");
		}else{
			accountHolderNewCompanyRegistrationNumberInput.sendKeys("8889000985643");
			accountHolderNewVatRegistrationNumberInput.sendKeys("BG 76777899098");
		}
		buttonNext.click();
		return new AccountBusinessDetails().loadPage(AccountBusinessDetails.class);
	}
	public AccountBusinessDetails provideAccountHolderDeatilsForUpdate(){
		ax._typeMeWithClear(getDriver(), accountHolderNewNameInput, "Name " + ax.randomString());
		ax._typeMeWithClear(getDriver(), accountHolderNewCityInput, "City " + ax.randomString());
		ax._typeMeWithClear(getDriver(), accountHolderNewAddress1Input, "Adres1 " + ax.randomString());
		buttonSubmit.click();
		return new AccountBusinessDetails().loadPageWithExpectedTitle(AccountBusinessDetails.class, "Account Holder Update");
	}	
	public AccountBusinessDetails selectWhenHolderIsInTheRegistryProvideId(String holderNumber){
		radioAccountHolderInRegistry.click();
		ax.wait_for_ayax_2(getDriver());
		ax.wait4element(getDriver(), accountHolderIdInput);
		accountHolderIdInput.sendKeys(holderNumber);
		buttonNext.click();
		return new AccountBusinessDetails().loadPage(AccountBusinessDetails.class);
	}

	
	public AccountRequestHolderInformation open() {
		return new AccountRequestHolderInformation().loadPage(AccountRequestHolderInformation.class);
	}
	@Override
	public String getExpectedPageTitle() {
		return "Account Holder Information";
	}

}
