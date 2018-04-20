package po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Page object representing xxxxxxx home page.
 *
 * @author kgumulak
 */
public class AccountInstallationInformationPage extends AbstractPage<AccountInstallationInformationPage> {

	@FindBy(id = "mainForm:permitId")
	WebElement installPermitId;	
	
	@FindBy(id = "mainForm:permitEntryIntoForceDate_input")
	WebElement permitEntryIntoForceDate_input;	
	
	@FindBy(id = "mainForm:permitExpiryDate_input")
	WebElement permitExpiryDate_input;	
	
	@FindBy(id = "mainForm:firstYearOfVerifiedEmissionsSubmission")
	WebElement firstYearOfVerifiedEmissionsSubmission;	
	
	@FindBy(id = "mainForm:installationName")
	WebElement installationName;	

	@FindBy(id = "mainForm:activityType")
	WebElement activityTypeSelect;	
	
	@FindBy(id = "mainForm:installationCity")
	WebElement installationCity;	
	
	@FindBy(id = "mainForm:installationPostcode")
	WebElement installationPostcode;	
	
	@FindBy(id = "mainForm:installationAddressLine1")
	WebElement installationAddressLine1;	

	@FindBy(id = "mainForm:installationTelephone1")
	WebElement installationTelephone1;	
	
	@FindBy(id = "mainForm:installationTelephone2")
	WebElement installationTelephone2;	
	
	@FindBy(id = "mainForm:installationEmailAddress")
	WebElement installationEmailAddress;	
	
	@FindBy(id = "mainForm:confirmInstallationEmailAddress")
	WebElement confirmInstallationEmailAddress;	
	
	@FindBy(id = "mainForm:buttonNext")
	WebElement buttonNext;	
	
	
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.titleContains("Installation Information");
	}
	@Override
	public ExpectedCondition<?> getPageLoadCondition(String titlePart) {
		return ExpectedConditions.titleContains(titlePart);
	}

	@Override
	public String getPageUrl() {
		return "/public/accounts/confirmAccountOpeningAddAddAuthRep.xhtml";
	}
	
	public AccountContactPage installationDialog(String yfe){
		installPermitId.sendKeys("6345634");
		permitEntryIntoForceDate_input.sendKeys("10/01/2018");
		permitExpiryDate_input.sendKeys("31/01/2019");
		firstYearOfVerifiedEmissionsSubmission.sendKeys(yfe);
		installationName.sendKeys("CALCIUM_INSTALLATION");
		new Select(activityTypeSelect).selectByIndex(2);
		installationCity.sendKeys("Monrovia");
		installationPostcode.sendKeys("4434");
		installationAddressLine1.sendKeys("MongoDB 78");
		installationTelephone1.sendKeys("4334523");
		installationTelephone2.sendKeys("77654456");
		installationEmailAddress.sendKeys("l@l.pl");
		confirmInstallationEmailAddress.sendKeys("l@l.pl");
		buttonNext.click();
		return new AccountContactPage().loadPage(AccountContactPage.class);
	}
	

	public AccountInstallationInformationPage open() {
		return new AccountInstallationInformationPage().loadPage(AccountInstallationInformationPage.class);
	}
	@Override
	public String getExpectedPageTitle() {
		return "Installation Information";
	}

}
