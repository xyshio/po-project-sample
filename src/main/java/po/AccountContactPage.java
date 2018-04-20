package po;

import static utils.SeleniumDriver.getDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;

/**
 * Page object representing xxxxxxx home page.
 *
 * @author kgumulak
 */
public class AccountContactPage extends AbstractPage<AccountContactPage> {

	@FindBy(id = "mainForm:contactPersonFirstName")
	WebElement contactPersonFirstName;	

	@FindBy(id = "mainForm:contactPersonLastName")
	WebElement contactPersonLastName;	

	@FindBy(id = "mainForm:contactPersonTelephone1")
	WebElement contactPersonTelephone1;	

	@FindBy(id = "mainForm:contactPersonTelephone2")
	WebElement contactPersonTelephone2;	

	@FindBy(id = "mainForm:accountHolderEmailAddress")
	WebElement accountHolderEmailAddress;	

	@FindBy(id = "mainForm:contactPersonCountry")
	WebElement contactPersonCountry;	
	
	@FindBy(id = "mainForm:confirmAccountHolderEmailAddress")
	WebElement confirmAccountHolderEmailAddress;	
	
	@FindBy(id = "mainForm:contactPersonAddressProvided")
	WebElement addressProviderCheckbox;	

	@FindBy(id = "mainForm:contactPersonCity")
	WebElement contactPersonCity;	
	
	@FindBy(id = "mainForm:contactPersonPostcode")
	WebElement contactPersonPostcode;	

	@FindBy(id = "mainForm:contactPersonAddressLine1")
	WebElement contactPersonAddressLine1;	
	
	@FindBy(id = "mainForm:contactPersonAddressLine2")
	WebElement contactPersonAddressLine2;	
	
	
	@FindBy(id = "mainForm:addressInfoPanelGrid")
	WebElement addressInfoPanelGrid;	
	
	@FindBy(id = "mainForm:buttonNext")
	WebElement buttonNext;	
	
	@FindBy(id = "mainForm:buttonSubmitUpdateContactPerson")
	WebElement buttonSubmit;	
	
	@FindBy(className="ui-messages-info-summary")
	WebElement greenBoxContactPersonUpdateSuccessMonit;		
	
	
	
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.titleContains("Contact");
	}
	@Override
	public ExpectedCondition<?> getPageLoadCondition(String titlePart) {
		return ExpectedConditions.titleContains(titlePart);
	}

	@Override
	public String getPageUrl() {
		return "/public/accounts/requestContactPersonInformation.xhtml";
	}
	
	public AccountSummaryPage contactPersonDialog(){
		contactPersonFirstName.sendKeys("Kris");
		contactPersonLastName.sendKeys("Danvier");
		contactPersonTelephone1.sendKeys("2341234");
		contactPersonTelephone2.sendKeys("5655432");
		accountHolderEmailAddress.sendKeys("l@l.pl");
		confirmAccountHolderEmailAddress.sendKeys("l@l.pl");
		buttonNext.click();
		return new AccountSummaryPage().loadPage(AccountSummaryPage.class);
	}
	public AccountContactPage contactPersonDialogUpdate(String hashedString){
		ax._typeMeWithClear(getDriver(), contactPersonFirstName, hashedString);
		ax._typeMeWithClear(getDriver(), contactPersonLastName, hashedString);
		ax._typeMeWithClear(getDriver(), contactPersonTelephone1, "111111111111");
		ax._typeMeWithClear(getDriver(), contactPersonTelephone2, "111111111111");
		ax._typeMeWithClear(getDriver(), accountHolderEmailAddress, "update@update.com");
		ax._typeMeWithClear(getDriver(), confirmAccountHolderEmailAddress, "update@update.com");
		clickAddressProvidedCheckbox();
		ax._selectMe(getDriver(), contactPersonCountry, "value", "AT");
		ax._typeMeWithClear(getDriver(), contactPersonCity, hashedString);
		ax._typeMeWithClear(getDriver(), contactPersonPostcode, "11111");
		ax._typeMeWithClear(getDriver(), contactPersonAddressLine1, hashedString);
		ax._typeMeWithClear(getDriver(), contactPersonAddressLine2, hashedString);
		
		buttonSubmit.click();
		return new AccountContactPage().loadPageWithExpectedTitle(AccountContactPage.class, "EUCR");
	}

	
	
	public AccountContactPage open() {
		return new AccountContactPage().loadPage(AccountContactPage.class);
	}
	@Override
	public String getExpectedPageTitle() {
		return "Contact";
	}

	public AccountContactPage clickSubmitButton(){
		buttonSubmit.click();
		return new AccountContactPage().loadPageWithExpectedTitle(AccountContactPage.class, "Contact Person Update");
	}
	public boolean greenBoxDisplayed(){
		return greenBoxContactPersonUpdateSuccessMonit.isDisplayed();
	}
	public String getRequestForContactPersonUpdate(){
		String monit = greenBoxContactPersonUpdateSuccessMonit.getText().trim();
		return ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monit, "Your request to update a contact person has been submitted under identifier");
	}
	
	public AccountContactPage clickAddressProvidedCheckbox(){
		addressProviderCheckbox.click();
		if(ax.wait4element(getDriver(), addressInfoPanelGrid)!=null){
			return new AccountContactPage().loadPageWithExpectedTitle(AccountContactPage.class, "Contact Person Update");
		}
		return null;
	}

}
