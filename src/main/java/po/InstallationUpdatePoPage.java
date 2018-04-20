package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;



/**
 * Page object representing xxxxxxx home page.
 *
 * @author kgumulak
 */
public class InstallationUpdatePoPage extends AbstractPage<InstallationUpdatePoPage> {

	public static InstallationUpdatePoPage runEucr;
	
	@FindBy(id="mainForm:permitId")
	WebElement permitId;	
	@FindBy(id="mainForm:permitEntryIntoForceDate_input")
	WebElement permitEntryIntoForceDate_input;	
	@FindBy(id="mainForm:permitExpiryDate_input")
	WebElement permitExpiryDate_input;	
	@FindBy(id="mainForm:permitRevocationDate_input")
	WebElement permitRevocationDate_input;	
	@FindBy(id="mainForm:firstYearOfVerification")
	WebElement firstYearOfVerification;	
	@FindBy(id="mainForm:lastYearOfVerification")
	WebElement lastYearOfVerification;	
	@FindBy(id="mainForm:installationName")
	WebElement installationName;	
	@FindBy(id="mainForm:installationRegion")
	WebElement installationRegion;	
	@FindBy(id="mainForm:installationCity")
	WebElement installationCity;	
	@FindBy(id="mainForm:installationPostcode")
	WebElement installationPostcode;	
	@FindBy(id="mainForm:installationAddressLine1")
	WebElement installationAddressLine1;	
	@FindBy(id="mainForm:installationAddressLine2")
	WebElement installationAddressLine2;	
	@FindBy(id="mainForm:installationTelephone1")
	WebElement installationTelephone1;	
	@FindBy(id="mainForm:installationTelephone2")
	WebElement installationTelephone2;	
	@FindBy(id="mainForm:installationEmailAddress")
	WebElement installationEmailAddress;	
	@FindBy(id="mainForm:confirmInstallationEmailAddress")
	WebElement confirmInstallationEmailAddress;	
	@FindBy(id="mainForm:installationParentCompany")
	WebElement installationParentCompany;	
	@FindBy(id="mainForm:installationSubsidiaryCompany")
	WebElement installationSubsidiaryCompany;	
	@FindBy(id="mainForm:installationEPERIdentification")
	WebElement installationEPERIdentification;	
	@FindBy(id="mainForm:installationLongitude")
	WebElement installationLongitude;	
	@FindBy(id="mainForm:installationLatitude")
	WebElement installationLatitude;	
	
	@FindBy(id="mainForm:buttonSubmit")
	WebElement buttonSubmit;	
	
	@FindBy(how=How.XPATH, xpath="//span[@class='ui-messages-info-summary' and contains(normalize-space(),'Your request to update installation information has been submitted under identifier')]")
	WebElement greenBoxElement;	
	
	
	@Override
	public String getExpectedPageTitle() {
		return "Installation Update";
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
		return "/protected/accounts/accountOHADetailsView.xhtml";
	}
	public InstallationUpdatePoPage goToTaskPage() {
		return new InstallationUpdatePoPage().loadPage(InstallationUpdatePoPage.class);
	}
	
	public InstallationUpdatePoPage open() {
		return new InstallationUpdatePoPage().loadPage(InstallationUpdatePoPage.class);
	}
	
	public String dialogUpdateInstallationDataSubmitAndGetRequestNumber(){
		ax._typeMeWithClear(getDriver(), permitId, "7789098");
		ax._typeMeWithClear(getDriver(), permitRevocationDate_input, "11/01/2022");
		ax._typeMeWithClear(getDriver(), installationRegion, "Malopolskie Updated");
		ax._typeMeWithClear(getDriver(), lastYearOfVerification, "2022");
		
		
		buttonSubmit.click();
		if(!ax.WAIT4PageLoad(getDriver())){
			return null;
		}
		if(ax.wait4element(getDriver(), greenBoxElement)==null){
			return (String) ax.returnWhenNull(getDriver(), "Problem getting GreenBox confirmation for Installation Update Proposal");
		}
		String monit = greenBoxElement.getText().trim();
		return ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monit, "update installation information has been submitted under identifier");
	}
	
	

}
