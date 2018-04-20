package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;



/**
 * Page object representing Authorised Representative Update home page.
 *
 * @author kgumulak
 */
public class AuthorisedRepresentativeUpdatePoPage extends AbstractPage<AuthorisedRepresentativeUpdatePoPage> {

	public static AuthorisedRepresentativeUpdatePoPage runEucr;
	
	@FindBy(id="mainForm:companyName")
	WebElement companyName;	
	@FindBy(id="mainForm:companyDepartment")
	WebElement companyDepartment;	
	@FindBy(id="mainForm:jobTitle")
	WebElement jobTitle;
	@FindBy(id="mainForm:accountRepresentativeRegion")
	WebElement accountRepresentativeRegion;	
	@FindBy(id="mainForm:accountRepresentativeCity")
	WebElement accountRepresentativeCity;
	@FindBy(id="mainForm:accountRepresentativePostcode")
	WebElement accountRepresentativePostcode;	
	@FindBy(id="mainForm:accountRepresentativeAddressLine1")
	WebElement accountRepresentativeAddressLine1;
	@FindBy(id="mainForm:accountRepresentativeAddressLine2")
	WebElement accountRepresentativeAddressLine2;	
	@FindBy(id="mainForm:accountRepresentativeTelephone1")
	WebElement accountRepresentativeTelephone1;	
	@FindBy(id="mainForm:accountRepresentativeTelephone2")
	WebElement accountRepresentativeTelephone2;
	@FindBy(id="mainForm:accountRepresentativeEmailAddress")
	WebElement accountRepresentativeEmailAddress;	
	@FindBy(id="mainForm:confirmAccountRepresentativeEmailAddress")
	WebElement confirmAccountRepresentativeEmailAddress;
	@FindBy(id="mainForm:buttonSubmit")
	WebElement buttonSubmit;	
	
	@FindBy(how=How.XPATH, xpath="//span[@class='ui-messages-info-summary' and contains(normalize-space(),'Your request to update business details has been submitted under identifier')]")
	WebElement greenBoxElement;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Authorised Representative Update";
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
	public AuthorisedRepresentativeUpdatePoPage goToTaskPage() {
		return new AuthorisedRepresentativeUpdatePoPage().loadPage(AuthorisedRepresentativeUpdatePoPage.class);
	}
	
	public AuthorisedRepresentativeUpdatePoPage open() {
		return new AuthorisedRepresentativeUpdatePoPage().loadPage(AuthorisedRepresentativeUpdatePoPage.class);
	}
	
	public String dialogUpdateAuthoriseRepresentativeDataSubmitAndGetRequestNumber(){
		ax._typeMeWithClear(getDriver(), accountRepresentativeCity, "LOWER-"+ax.randomString());
		ax._typeMeWithClear(getDriver(), accountRepresentativeAddressLine1, "PO-BOX:-"+ax.randomString());
		buttonSubmit.click();
		if(!ax.WAIT4PageLoad(getDriver())){
			return null;
		}
		if(ax.wait4element(getDriver(), greenBoxElement)==null){
			return (String) ax.returnWhenNull(getDriver(), "Problem getting GreenBox confirmation for AR Update Proposal");
		}
		String monit = greenBoxElement.getText().trim();
		return ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monit, "Your request to update business details has been submitted under identifier");
	}
	
	

}
