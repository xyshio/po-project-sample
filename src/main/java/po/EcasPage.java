package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import utils.SeleniumWebUtils;
import utils.ax;



/**
 * Page object representing Ecas home page.
 *
 * @author kgumulak
 */
public class EcasPage extends AbstractPage<EcasPage> {
	@FindBy(id = "username")
	WebElement emailField;	
	
	@FindBy(name = "whoamiSubmit")
	WebElement emailSubmit;	
	
	@FindBy(name = "password")
	WebElement passwordField;

	
	@FindBy(id = "loginStrength")
	WebElement verificationSelect;

	@FindBy(name = "_submit")
	WebElement passwordSubmit;	
	
	@FindBy(className="user-logout-link")
	WebElement logoutEcasLink;
	
	@FindBy(how=How.XPATH, using="//a[@title='Login' and contains(concat(' ',@class,' '),'btn-primary')]")
	WebElement loginBtnPrimary;
	
	@FindBy(id="anonymize")
	WebElement signAsDifferentUser;

	@FindBy(how=How.XPATH, using="//h1/strong[contains(normalize-space(),'Sign in')]")
	WebElement signInToContinueHeader;

	@FindBy(id="fallbackLink")
	WebElement fallbackLink;
	
	
//	@FindBy(className = "flash-error")
//	WebElement errorBox;
	
	@Override
	public String getExpectedPageTitle() {
		return "Login";
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
		return "https://ecas.ec.europa.eu/cas/login";
	}

//	public GitHubLoginPage goToLoginPage() {
//		return new GitHubLoginPage().openPage(GitHubLoginPage.class);
//	}

	public EcasPage open() {
		return new EcasPage().loadPage(EcasPage.class);
	}

	public void clickFallBackLink(){
//		WebElement fallBack = new EcasPage().loadPage(EcasPage.class).fallbackLink;
		try {
			ax.turnOff_time(getDriver());
			if(fallbackLink.isDisplayed()){
				ax._clickMe(getDriver(), fallbackLink);
				ax.turnOn_time(getDriver());
				ax.WAIT4PageLoad(getDriver());
			}
		} catch (Exception e) {
			// element not displayed
			ax.turnOn_time(getDriver());
		}
	}
	
	
	public EucrHomePage login(String login, String password) {
//		EcasPage ecas = new EcasPage().open();
		enterEmail(login);
		enterPassword(password);
		clickFallBackLink();
		EucrHomePage eucr = new EucrHomePage().loadPage(EucrHomePage.class);
		if(eucr!=null){
			ax.log("");
			ax.log("Login as " + login);
		}
		return eucr;
	}
	
	public void enterEmail(String email){
			try {
				if(ax.isPresentNow(getDriver(), signAsDifferentUser)){
					signAsDifferentUser.click();	
				}
			} catch (Exception e) {
				
			}
		
		ax.wait4element(getDriver(), emailField);
		emailField.sendKeys(email);
		emailSubmit.click();
	}
	public void enterPassword(String password){
		ax.wait4element(getDriver(), passwordField);
		passwordField.sendKeys(password);
		Select dropdown = new Select(verificationSelect);
		dropdown.selectByVisibleText("Password");
		passwordSubmit.click();
	}
	

	

	
	public EcasPage ecasLogoutToBasic(){
		
		try {
			
			ax.wait_elm(getDriver(), logoutEcasLink);
			logoutEcasLink.click();
			ax.wait_elm(getDriver(), loginBtnPrimary);
			loginBtnPrimary.click();
			ax.wait_elm(getDriver(), signAsDifferentUser);
			signAsDifferentUser.click();
			ax.wait_elm(getDriver(), emailSubmit);
				if(emailSubmit.isEnabled() && emailSubmit.isEnabled()){
					return new EcasPage().loadPage(EcasPage.class);	
				}else{
					return null;
				}
		} catch (Exception e) {
			return null;
		}
		
		
		
//		
//		if(logoutEcasLink.isDisplayed()){
//			
////			ax.wait(1);
//			ax.waitForVisibilityOfElementLocated(getDriver(), loginBtnPrimary);
//		}
//		if(loginBtnPrimary.isDisplayed()){
//			loginBtnPrimary.click();
//			ax.waitForVisibilityOfElementLocated(getDriver(), signAsDifferentUser);
//		}else if(logoutEcasLink.isDisplayed()){
//			logoutEcasLink.click();
////			ax.wait(1);
//			ax.waitForVisibilityOfElementLocated(getDriver(), loginBtnPrimary);
//		}	
//		if(signAsDifferentUser.isDisplayed()){
//			signAsDifferentUser.click();
//			ax.waitForVisibilityOfElementLocated(getDriver(), signInToContinueHeader);
//			return new EcasPage().loadPage(EcasPage.class);
//		}else{
//			return null;
//		}
	}
}
