package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;



/**
 * Page object representing Account Suspend home page.
 *
 * @author kgumulak
 */
public class AccountSuspendPage extends AbstractPage<AccountSuspendPage> {

	public static AccountSuspendPage runEucr;
	
	@FindBy(id="mainForm:reasonText")
	WebElement suspensionReasonTextarea;	
	
	@FindBy(id="mainForm:buttonConfirm")
	WebElement confirmButtonForSuspensionAccount;	
	
	@FindBy(id="mainForm:buttonConfirm")
	WebElement confirmButtonForRestoreAccount;	
	
	@FindBy(className="ui-messages-info-detail")
	WebElement greenBoxAccountSuspensionConfirmation;	

	@Override
	public String getExpectedPageTitle() {
		return "Account Suspend";
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
	public AccountSuspendPage goToTaskPage() {
		return new AccountSuspendPage().loadPage(AccountSuspendPage.class);
	}
	public AccountSuspendPage open() {
		return new AccountSuspendPage().loadPage(AccountSuspendPage.class);
	}
	public AccountSuspendPage ConfirmSuspendAccount(){
		suspensionReasonTextarea.sendKeys("Suspension Account Reason");
		confirmButtonForSuspensionAccount.click();
		return new AccountSuspendPage().loadPageWithExpectedTitle(AccountSuspendPage.class, "Account Suspend");
	}
	public AccountSuspendPage ConfirmRestoreAccount(){
		confirmButtonForRestoreAccount.click();
		return new AccountSuspendPage().loadPageWithExpectedTitle(AccountSuspendPage.class, "Account Restore");
	}	
	public boolean cofirmGreenBoxDisplayed(){
		return greenBoxAccountSuspensionConfirmation.isDisplayed();
	}

	
	
}
