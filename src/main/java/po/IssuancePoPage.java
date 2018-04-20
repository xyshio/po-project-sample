package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;



/**
 * Page object representing Issuance home page.
 *
 * @author kgumulak
 */
public class IssuancePoPage extends AbstractPage<IssuancePoPage> {

	public static IssuancePoPage runEucr;
	
	@FindBy(linkText="Issuance")
	WebElement issuancePageLink;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Issuance";
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
		return "/protected/issuance/issuance.xhtml";
	}
	public IssuancePoPage goToTaskPage() {
		return new IssuancePoPage().loadPage(IssuancePoPage.class);
	}
	
	public IssuancePoPage open() {
		return new IssuancePoPage().loadPage(IssuancePoPage.class);
	}

}
