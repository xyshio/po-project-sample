package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ApplicationPageUtils;
import utils.ax;
import api.Eucr;



/**
 * Page object representing Account Closure home page.
 *
 * @author kgumulak
 */
public class AccountClosurePoPage extends AbstractPage<AccountClosurePoPage> {

	public static AccountClosurePoPage runEucr;
	
	@FindBy(how=How.XPATH, using="//span[contains(concat(' ',@class,' '),'ui-messages-error-detail') and contains(normalize-space(),'Holding Account cannot be closed as long as the Permit Revocation Date and the Last Year of Verification are not filled in')]") 
	WebElement accountClosureErrorMessage;
	
	@FindBy(how=How.XPATH, using="//span[@class='ui-messages-warn-detail' and contains(normalize-space(),'Do you wish to close the account with identifier')]") 
	WebElement accountClosureWarningMessage;
	
	@FindBy(how=How.XPATH, using="//span[contains(concat(' ',@class,' '),'ui-messages-info-summary') and contains(normalize-space(),'Your account closure request has been submitted under identifier')]") 
	WebElement accountClosureGreenBoxMessage;
	
	@FindBy(id="mainForm:buttonClose") 
	WebElement confirmAccountClosureButton;
	
	
	@Override
	public String getExpectedPageTitle() {
		return "Account Closure";
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
		return "";
	}
	public AccountClosurePoPage goToTaskPage() {
		return new AccountClosurePoPage().loadPage(AccountClosurePoPage.class);
	}
	public AccountClosurePoPage open() {
		return new AccountClosurePoPage().loadPage(AccountClosurePoPage.class);
	}
	public String CloseAccount(AccountClosurePoPage close, String identifier){
		boolean displayedError;
		try {
			displayedError = accountClosureErrorMessage.isDisplayed();
		} catch (Exception e) {
			displayedError = false;
		}
		
		if(displayedError){
			// Need to be updated  Permit Revocation and Last YearOfVerification
			if(Eucr.UpdateAccountInstallationData(ApplicationPageUtils.loginPairNa1Na2, identifier)){
				EucrHomePage eucr = ax.LoginNA1();
				AccountDetailsPoPage closure = Eucr.SearchAndOpenAcountMainTab(identifier);
				AccountClosurePoPage closurePage = closure.clickCloseAccountButton();
				return new AccountClosurePoPage().CloseAccount(closurePage, identifier);
			}else{
				// Problem with updating PRD and LYV for closure
				return null;
			}
		}else{ // everything ready to cloase account - no error message displayed..
			if(close.confirmAccountClosureButton.isDisplayed()){
				close.confirmAccountClosureButton.click();
				
				ax.wait4element(getDriver(), close.accountClosureGreenBoxMessage);
					return ax.GET_WORD_AFTER_TEXT(close.accountClosureGreenBoxMessage.getText().trim(), "Your account closure request has been submitted under identifier");
			}	else{ // no warning message
				return null;
			}
		}
	}
	
}
