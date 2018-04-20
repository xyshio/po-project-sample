package po;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import utils.ax;

/**
 * Page object representing AircraftOperatorPage page.
 *
 * @author kgumulak
 */
public class AircraftOperatorPage extends AbstractPage<AircraftOperatorPage> {

	@FindBy(id = "mainForm:aircraftOperatorCode")
	WebElement uniqueCodeInput;	
	
	@FindBy(id = "mainForm:aircraftOperatorCallSign")
	WebElement callSignInput;	

	@FindBy(id = "mainForm:aircraftOperatorMonitoringPlanId")
	WebElement monitoringPlanIdInput;	

	@FindBy(id = "mainForm:firstYearOfApplicability")
	WebElement monitoringFirstYearApplicabilityInput;	
	
	@FindBy(id = "mainForm:monitoringPlanExpiryYear")
	WebElement monitoringPlanExpiryYear;	
	
	@FindBy(id = "mainForm:firstYearOfVerification")
	WebElement firstYearOfVerification;	
	
	@FindBy(id = "mainForm:buttonNext")
	WebElement buttonNext;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Aircraft Operator Information";
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
		return "/public/accounts/confirmAccountOpeningAddAddAuthRep.xhtml";
	}
	
	public AccountContactPage airOpsDialog(String yfe){
		uniqueCodeInput.sendKeys(ax.GET_TIMESTAMP_SAVE().substring(0,6));
		callSignInput.sendKeys("Sabre");
		monitoringPlanIdInput.sendKeys("1"+ax.GET_TIMESTAMP_SAVE());
		monitoringFirstYearApplicabilityInput.sendKeys(ax.GET_CURR_YEAR());
		monitoringPlanExpiryYear.sendKeys(ax.GET_NEXT_YEAR(5));
		firstYearOfVerification.sendKeys(yfe);
		buttonNext.click();
		return new AccountContactPage().loadPage(AccountContactPage.class);
	}
	

	public AircraftOperatorPage open() {
		return new AircraftOperatorPage().loadPage(AircraftOperatorPage.class);
	}

}
