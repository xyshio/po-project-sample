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
public class AccountSummaryPage extends AbstractPage<AccountSummaryPage> {

	@FindBy(id = "mainForm:buttonSubmit")
	WebElement buttonSubmit;	
	
	@FindBy(how=How.XPATH, xpath="//span[@class='ui-messages-info-detail' and contains(normalize-space(),'Your account opening request has been recorded with identifier')]")
	WebElement greenBoxAddingRequestReturnNumber;

	@Override
	public String getExpectedPageTitle() {
		return "Summary";
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
		return "/public/accounts/requestContactPersonInformation.xhtml";
	}
	
	public String submitAccountSummary(){
		buttonSubmit.click();
		ax.wait4element(getDriver(), greenBoxAddingRequestReturnNumber);
		return greenBoxAddingRequestReturnNumber.getText();
	}

	public AccountSummaryPage open() {
		return new AccountSummaryPage().loadPage(AccountSummaryPage.class);
	}

}
