package po;

import static utils.SeleniumDriver.getDriver;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import utils.ax;



/**
 * Page object representing eucr account holder information page.
 *
 * @author kgumulak
 */
public class AccountBusinessDetails extends AbstractPage<AccountBusinessDetails> {

	@FindBy(linkText="Home page")
	WebElement homePageLink;	
	
	@FindBy(how=How.XPATH, xpath="//label[contains(normalize-space(),'Representative is already related to the Account Holder')]/../input[@name='mainForm:accountRepresentativeTypeRadio']")
	WebElement radioRepIsRelatedToAccountHolder;	

	@FindBy(how=How.XPATH, xpath="//label[contains(normalize-space(),'Representative is not yet related to the Account Holder')]/../input[@name='mainForm:accountRepresentativeTypeRadio']")
	WebElement radioRepIsNotRelatedToAccountHolder;	
	
	@FindBy(how=How.XPATH, xpath="//div[@id='mainForm:accountDetailsPanel_content']//label[contains(normalize-space(),'Yes')]/../input[@type='radio']")
	WebElement radioAddMoreArsYes;	
	
	@FindBy(how=How.XPATH, xpath="//div[@id='mainForm:accountDetailsPanel_content']//label[contains(normalize-space(),'No')]/../input[@type='radio']")
	WebElement radioAddMoreArsNo;	
	
	@FindBy(how=How.XPATH, xpath="//div[@id='mainForm:accountDetailsPanel_content' and contains(normalize-space(),'additional authorized representatives')]//label[contains(normalize-space(),'Yes')]/../input[@type='radio']")
	WebElement radioAddMoreAArsYes;	
	
	@FindBy(how=How.XPATH, xpath="//div[@id='mainForm:accountDetailsPanel_content' and contains(normalize-space(),'additional authorized representatives')]//label[contains(normalize-space(),'No')]/../input[@type='radio']")
	WebElement radioAddMoreAArsNo;	
	
	
	
	@FindBy(id="mainForm:accountRepresentativeRelatedWithHolderSelect")
	WebElement selectRepsAreRelatedToAccountHolder;	
	
	@FindBy(id="mainForm:submitARRelatedWithHolder")
	WebElement addButton;	
	
	@FindBy(how=How.XPATH, xpath="//span[@class='ui-messages-info-summary' and contains(normalize-space(),'Authorised representative request added')]")
	WebElement greenBoxAddedArToTable;

	@FindBy(how=How.XPATH, xpath="//span[@class='ui-messages-info-summary' and contains(normalize-space(),'Your request to update account holder information has been submitted under identifier')]")
	WebElement greenBoxAccountHolderUpdate;
	
	@FindBy(id="mainForm:buttonNext")
	WebElement buttonNext;	
	
	@FindBy(id="mainForm:buttonDialogueNext")
	WebElement buttonDialogueNext;	
	
	
	
	// #######################################################################################
	// Urid dialog controls
	@FindBy(id="mainForm:URID")
	WebElement uridInput;	

	@FindBy(id="mainForm:companyName")
	WebElement companyNameInput;	
	
	@FindBy(id="mainForm:companyDepartment")
	WebElement companyDepartmentInput;	
	
	@FindBy(id="mainForm:jobTitle")
	WebElement jobTitleInput;	

	@FindBy(id="mainForm:accountRepresentativeCountry")
	WebElement countrySelect;	

	@FindBy(id="mainForm:accountRepresentativeRegion")
	WebElement regionOrStateInput;	
	
	@FindBy(id="mainForm:accountRepresentativeCity")
	WebElement cityInput;	
	
	@FindBy(id="mainForm:accountRepresentativePostcode")
	WebElement postCodeInput;	
	
	@FindBy(id="mainForm:accountRepresentativeAddressLine1")
	WebElement adresLin1Input;	
	
	@FindBy(id="mainForm:accountRepresentativeAddressLine2")
	WebElement adresLin2Input;	
	
	@FindBy(id="mainForm:accountRepresentativeTelephone1")
	WebElement telephone1Input;	
	
	@FindBy(id="mainForm:accountRepresentativeTelephone2")
	WebElement telephone2Input;	
	
	@FindBy(id="mainForm:accountRepresentativeEmailAddress")
	WebElement email1Input;	
	
	@FindBy(id="mainForm:confirmAccountRepresentativeEmailAddress")
	WebElement email2Input;	
	            
	@FindBy(id="mainForm:submitARUnrelatedWithHolder") 
	WebElement buttonArAdd;	
	
	@FindBy(id="mainForm:submitAARUnrelatedWithHolder") 
	WebElement buttonAArAdd;	
	
	
	
	// #######################################################################################
	
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.titleContains("Business Details");
	}
	@Override
	public ExpectedCondition<?> getPageLoadCondition(String titlePart) {
		return ExpectedConditions.titleContains(titlePart);
	}

	@Override
	public String getPageUrl() {
		return "";
	}
	
	public Object addAccountRepresentativeAlreadyRelated(){
		int i=0;
		do{
			clickRepIsRelatedRadio();
			selectFirstArFromIsRelated();
			ax.wait(1);
			i++;
		}while(i<2);
		clickNextButton();
		confirmNoMoreArs();
		return confirmNoMoreAars("installation");
	}
	
	public Object addAccountRepresentativeNew(ArrayList<String> urid, String installationOrAcops){
		enterUridDataFor("AR", urid.get(0).split(":")[0]);
		ax.wait_for_ayax_2(getDriver());
		enterUridDataFor("AR", urid.get(0).split(":")[1]);
		ax.wait_for_ayax_2(getDriver());
		clickNextButton();
		confirmNoMoreArs();
		confirmAddMoreAArs();
		ax.wait(1);
		enterUridDataFor("AAR", urid.get(1).split(":")[0]);
		ax.wait_for_ayax_2(getDriver());
		clickNextButton();
		return confirmNoMoreAars(installationOrAcops);
	}
	public void clickRepIsRelatedRadio(){
		radioRepIsRelatedToAccountHolder.click();
		ax.wait4element(getDriver(), selectRepsAreRelatedToAccountHolder);
	}
	public void selectFirstArFromIsRelated(){
		new Select(selectRepsAreRelatedToAccountHolder).selectByIndex(1);
		addButton.click();
		ax.wait4element(getDriver(), greenBoxAddedArToTable);
	}
	public void clickNextButton(){
		buttonNext.click();
	}
	public void confirmNoMoreArs(){
		ax.wait4element(getDriver(), radioAddMoreArsNo);
		radioAddMoreArsNo.click();
		buttonDialogueNext.click();
	}
	
	
	public void confirmAddMoreArs(){
		ax.wait4element(getDriver(), radioAddMoreArsYes);
		radioAddMoreArsYes.click();
		buttonDialogueNext.click();
	}	
	
	public void confirmAddMoreAArs(){
		ax.wait4element(getDriver(), radioAddMoreAArsYes);
		radioAddMoreAArsYes.click();
		buttonDialogueNext.click();
	}	
	
	public Object confirmNoMoreAars(String InstallationOrAircraftOperator){
		ax.wait4element(getDriver(), radioAddMoreArsNo);
		radioAddMoreArsNo.click();
		buttonDialogueNext.click();
		if(InstallationOrAircraftOperator.contains("installation")){
			return new AccountInstallationInformationPage().loadPage(AccountInstallationInformationPage.class);	
		}else{
			return new AircraftOperatorPage().loadPage(AircraftOperatorPage.class); 
		}
	}
	public EucrHomePage clickHomePageLink(){
		homePageLink.click();
		return new EucrHomePage().loadPage(EucrHomePage.class);
	}
	
//	public AccountBusinessDetails fillDialogFields(String AccountType, String Period, String Name, String Group){
//		new Select(accountTypeSelect).selectByVisibleText(AccountType);
//		new Select(accountCommitmentPeriodSelect).selectByIndex(0);
//		accountNameInput.sendKeys(Name);
//		new Select(accountGroupIdSelect).selectByVisibleText(Group);
//		buttonNext.click();
//	}
	
	public AccountBusinessDetails open() {
		return new AccountBusinessDetails().loadPage(AccountBusinessDetails.class);
	}

	public void enterUridDataFor(String fx, String Urid){
		uridInput.sendKeys(Urid);
		companyNameInput.sendKeys("Brejwer");
		companyDepartmentInput.sendKeys("Department Company");
		jobTitleInput.sendKeys("Senior fixer");
		ax._selectMe(getDriver(), countrySelect, "value", "AT");
		regionOrStateInput.sendKeys("Brejwer");
		cityInput.sendKeys("Kajzerowo");
		postCodeInput.sendKeys("77890");
		adresLin1Input.sendKeys("Kosowska 8879");
		adresLin2Input.sendKeys("County Michigan");
		telephone1Input.sendKeys("+778901");
		telephone2Input.sendKeys("+778902");
		email1Input.sendKeys("l@l.pl");
		email2Input.sendKeys("l@l.pl");
		
		if(fx.contains("AAR")){
			buttonAArAdd.click();	
		}else{
			buttonArAdd.click();
		}
	}
	@Override
	public String getExpectedPageTitle() {
		return "Home Page";
	}
	public boolean confirmAccountHolderUpdateGreenBox(){
		return greenBoxAccountHolderUpdate.isDisplayed();
	}
	public String getRequestForAccountHolderUpdate(){
		String monit = greenBoxAccountHolderUpdate.getText().trim();
		return ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monit, "Your request to update account holder information has been submitted under identifier");
	}	
	
}
