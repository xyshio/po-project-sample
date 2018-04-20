package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;



/**
 * Page object representing Account Release home page.
 *
 * @author kgumulak
 */
public class AccountReleasePoPage extends AbstractPage<AccountReleasePoPage> {

	public static AccountReleasePoPage runEucr;
	
	
	
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:accountTypeRadio']//td//label[contains(normalize-space(),'Operator holding account')]/../input[@type='radio']")
	WebElement radioOperatorHoldingAccount;	
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:accountTypeRadio']//td//label[contains(normalize-space(),'Aircraft operator holding account')]/../input[@type='radio']")
	WebElement radioAircraftOperatorHoldingAccount;	

	@FindBy(id="mainForm:accountIdentifier")
	WebElement identifierInput;	
	@FindBy(id="mainForm:buttonSubmit")
	WebElement submitAccountReleaseButton;	
	
	@FindBy(how=How.XPATH, using="//span[contains(@class, 'ui-messages-warn-detail') and contains(normalize-space(),'You are about to release the ownership of your account holder')]")
	WebElement accountReleaseConfirmationWarningBox;	
	@FindBy(how=How.XPATH, using="//span[contains(@class, 'ui-messages-info-detail') and contains(normalize-space(),'has been released and can now be claimed for a new account holder')]")
	WebElement accountReleaseConfirmationGreenBox;	
	@FindBy(id="mainForm:buttonConfirm")
	WebElement confirmAccountReleaseButton;	
	
	
	@Override
	public String getExpectedPageTitle() {
		return "Account Release";
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
		return "/protected/accounts/accountRelease.xhtml";
	}
	public AccountReleasePoPage goToTaskPage() {
		return new AccountReleasePoPage().loadPage(AccountReleasePoPage.class);
	}
	
	public AccountReleasePoPage open() {
		return new AccountReleasePoPage().loadPage(AccountReleasePoPage.class);
	}

	public AccountReleasePoPage provideAccountInfoToRelease(String OhaAohaSwitcher, String accountIdentifier){
		switch (OhaAohaSwitcher) {
		case "OHA": radioOperatorHoldingAccount.click(); break;
		case "AOHA": radioAircraftOperatorHoldingAccount.click(); break;
		}
		identifierInput.sendKeys(accountIdentifier);
		submitAccountReleaseButton.click();
		AccountReleasePoPage page = new AccountReleasePoPage().loadPageWithExpectedTitle(AccountReleasePoPage.class, "EUCR");
		if(page.accountReleaseConfirmationWarningBox.isDisplayed()){
			confirmAccountReleaseButton.click();
			ax.wait4element(getDriver(), accountReleaseConfirmationGreenBox);
			if(accountReleaseConfirmationGreenBox.isDisplayed()){
				return new AccountReleasePoPage().loadPageWithExpectedTitle(AccountReleasePoPage.class, "EUCR");	
			}else{
				return (AccountReleasePoPage)ax.returnWhenNull(getDriver(), "Problem with Account Release Dialog - No green box after confirmation");
			}
		}
		return (AccountReleasePoPage)ax.returnWhenNull(getDriver(), "Problem with Account Release Dialog - No warning box before confirmation");
	}
	
	
	
}
