package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;



/**
 * Page object representing Entitlements home page.
 *
 * @author kgumulak
 */
public class EntitlementPoPage extends AbstractPage<EntitlementPoPage> {

	public static EntitlementPoPage runEucr;
	
	@FindBy(linkText="Entitlements")
	WebElement entitlementsPageLink;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Entitlements";
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
		return "/protected/iceTable/iceTable.xhtml";
	}
	public EntitlementPoPage goToTaskPage() {
		return new EntitlementPoPage().loadPage(EntitlementPoPage.class);
	}
	
	public EntitlementPoPage open() {
		return new EntitlementPoPage().loadPage(EntitlementPoPage.class);
	}

}
