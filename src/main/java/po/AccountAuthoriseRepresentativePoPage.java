package po;

import static utils.SeleniumDriver.getDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;



/**
 * Page object representing Account Authorise Representative Page home page.
 *
 * @author kgumulak
 */
public class AccountAuthoriseRepresentativePoPage extends AbstractPage<AccountAuthoriseRepresentativePoPage> { // Page when opening AR tab

	public static AccountAuthoriseRepresentativePoPage runEucr;
	
	
		
	@FindBy(id="mainForm:buttonConfirm")
	WebElement confirmSuspendUserButton;	
	
	@FindBy(how=How.XPATH, using="//div[@id='mainForm:restoreRepresentativePanel_content']//button[@id='mainForm:buttonConfirm']")
	WebElement confirmRestoreUserButton;	
	
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:representativeLinkedWithUserRadio']//td//label[contains(normalize-space(),'Representative is already related to the Account Holder')]/../input[@type='radio']")
	WebElement radioRepIsAlreadyYetRelated;	
	
	@FindBy(how=How.XPATH, using="//table[@id='mainForm:representativeLinkedWithUserRadio']//td//label[contains(normalize-space(),'Representative is not yet related to the Account Holder')]/../input[@type='radio']")
	WebElement radioRepIsNotYetRelated;	
	
	@FindBy(id="mainForm:buttonNext")
	WebElement nextButton;	

	
	
	
	@FindBy(how=How.XPATH, using="//table[@id='repTypeChoice:mainForm:accountHolderLinkedWithUserRadio']//td//label[contains(normalize-space(),'Representative is already related to the Account Holder')]/../input[@type='radio']")
	WebElement radioRepIsAlreadyYetRelated_adding;	
	
	@FindBy(how=How.XPATH, using="//table[@id='repTypeChoice:mainForm:accountHolderLinkedWithUserRadio']//td//label[contains(normalize-space(),'Representative is not yet related to the Account Holder')]/../input[@type='radio']")
	WebElement radioRepIsNotYetRelated_adding;	
	
	@FindBy(id="repTypeChoice:mainForm:buttonUpdateAccount")
	WebElement nextButton_adding;	
	
	
	
	@FindBy(id="mainForm:accountRepresentativeDetailsPanelGrid")
	WebElement replaceUserPanelGrid;	
	
	@FindBy(id="mainForm:URID")
	WebElement replaceUridInput;	
	
	@FindBy(id="mainForm:accountRepresentativeCountry")
	WebElement replaceCountrySelect;	
	
	@FindBy(id="mainForm:accountRepresentativeCity")
	WebElement replaceCityInput;	
	
	@FindBy(id="mainForm:accountRepresentativePostcode")
	WebElement replacePostInput;	

	@FindBy(id="mainForm:accountRepresentativeAddressLine1")
	WebElement replaceAddress1Input;	

	@FindBy(id="mainForm:accountRepresentativeAddressLine2")
	WebElement replaceAddress2Input;	

	@FindBy(id="mainForm:accountRepresentativeTelephone1")
	WebElement replaceTelefon1Input;	

	@FindBy(id="mainForm:accountRepresentativeTelephone2")
	WebElement replaceTelefon2Input;	
	
	@FindBy(id="mainForm:accountRepresentativeEmailAddress")
	WebElement replaceEmail1Input;	

	@FindBy(id="mainForm:confirmAccountRepresentativeEmailAddress")
	WebElement replaceEmail2Input;	
	
	@FindBy(how=How.XPATH, using="//span[@class='ui-messages-info-summary' and contains(normalize-space(),'Your request to replace an account representative has been submitted under identifier')]")
	WebElement greenBoxConfirmation;	
	 // ##############################################################
	// ADDING NEW AR
	
	@FindBy(id="repTypeNew:mainForm:accountRepresentativeDetailsPanelGrid")
	WebElement addingUserPanelGrid;	
	
	@FindBy(id="repTypeNew:mainForm:URID")
	WebElement addingUridInput;	
	
	@FindBy(id="repTypeNew:mainForm:accountRepresentativeCountry")
	WebElement addingCountrySelect;	
	
	@FindBy(id="repTypeNew:mainForm:accountRepresentativeCity")
	WebElement addingCityInput;	
	
	@FindBy(id="repTypeNew:mainForm:accountRepresentativePostcode")
	WebElement addingPostInput;	

	@FindBy(id="repTypeNew:mainForm:accountRepresentativeAddressLine1")
	WebElement addingAddress1Input;	

	@FindBy(id="repTypeNew:mainForm:accountRepresentativeAddressLine2")
	WebElement addingAddress2Input;	

	@FindBy(id="repTypeNew:mainForm:accountRepresentativeTelephone1")
	WebElement addingTelefon1Input;	

	@FindBy(id="repTypeNew:mainForm:accountRepresentativeTelephone2")
	WebElement addingTelefon2Input;	
	
	@FindBy(id="repTypeNew:mainForm:accountRepresentativeEmailAddress")
	WebElement addingEmail1Input;	

	@FindBy(id="repTypeNew:mainForm:confirmAccountRepresentativeEmailAddress")
	WebElement addingEmail2Input;	
	
	@FindBy(how=How.XPATH, using="//span[@class='ui-messages-info-summary' and contains(normalize-space(),'has been submitted under identifier')]")
	WebElement greenBoxConfirmationAdding;
	@FindBy(id="repTypeNew:mainForm:buttonUpdateAccount")
	WebElement submitButton;	

	
	//##############################################################
	
	@Override
	public String getExpectedPageTitle() {
		return "Account Main Details";
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
		return "/protected/accounts/accountsListView.xhtml";
	}
	public AccountAuthoriseRepresentativePoPage goToTaskPage() {
		return new AccountAuthoriseRepresentativePoPage().loadPage(AccountAuthoriseRepresentativePoPage.class);
	}
	
	public AccountAuthoriseRepresentativePoPage open() {
		return new AccountAuthoriseRepresentativePoPage().loadPage(AccountAuthoriseRepresentativePoPage.class);
	}

	public AccountAuthoriseRepresentativePoPage clickSuspenduserConfirmationButton(){
		try {
			confirmSuspendUserButton.click();
			return new AccountAuthoriseRepresentativePoPage().loadPageWithExpectedTitle(AccountAuthoriseRepresentativePoPage.class, "Account Main Details");
		} catch (Exception e) {
			return (AccountAuthoriseRepresentativePoPage) ax.returnWhenNull(getDriver(), "Problem with clicking confirm to suspend user");
		}
	}
	public AccountAuthoriseRepresentativePoPage clickRestoringConfirmationButton(){
		try {
			confirmRestoreUserButton.click();
			return new AccountAuthoriseRepresentativePoPage().loadPageWithExpectedTitle(AccountAuthoriseRepresentativePoPage.class, "Account Main Details");
		} catch (Exception e) {
			return (AccountAuthoriseRepresentativePoPage) ax.returnWhenNull(getDriver(), "Problem with clicking confirm to restore user");
		}
	}
	

	
//	clickAdArButton
	
	public String DoAddNewAr(String urid_new){
		
		radioRepIsNotYetRelated_adding.click();
		nextButton_adding.click();
		ax.wait_elm(getDriver(), addingUserPanelGrid);
		ax._typeMeWithClear(getDriver(), addingUridInput, urid_new);
		ax._selectMe(getDriver(), addingCountrySelect, "value","AT");
		ax._typeMeWithClear(getDriver(), addingCityInput, "Kerouter");
		ax._typeMeWithClear(getDriver(), addingPostInput, "54323454");
		ax._typeMeWithClear(getDriver(), addingAddress1Input, "Denverowo 7789");
		ax._typeMeWithClear(getDriver(), addingAddress2Input, "Chaber 774589");
		ax._typeMeWithClear(getDriver(), addingTelefon1Input, "88898098");
		ax._typeMeWithClear(getDriver(), addingTelefon2Input, "64774589");
		ax._typeMeWithClear(getDriver(), addingEmail1Input, "replacer@replacer.com");
		ax._typeMeWithClear(getDriver(), addingEmail2Input, "replacer@replacer.com");
		submitButton.click();
		ax.wait_elm(getDriver(), greenBoxConfirmationAdding);
		if(greenBoxConfirmationAdding.isDisplayed()){
			String monit = greenBoxConfirmationAdding.getText().trim();
			return ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monit, "submitted under identifier");
		}
		return null;
	}
	
	
	
	
	
	public String DoReplaceForNewAr(String urid_new){
		radioRepIsNotYetRelated.click();
		nextButton.click();
		ax.wait_elm(getDriver(), replaceUserPanelGrid);
		ax._typeMeWithClear(getDriver(), replaceUridInput, urid_new);
		ax._selectMe(getDriver(), replaceCountrySelect, "value","AT");
		ax._typeMeWithClear(getDriver(), replaceCityInput, "Kerouter");
		ax._typeMeWithClear(getDriver(), replacePostInput, "54323454");
		ax._typeMeWithClear(getDriver(), replaceAddress1Input, "Denverowo 7789");
		ax._typeMeWithClear(getDriver(), replaceAddress2Input, "Chaber 774589");
		ax._typeMeWithClear(getDriver(), replaceTelefon1Input, "88898098");
		ax._typeMeWithClear(getDriver(), replaceTelefon2Input, "64774589");
		ax._typeMeWithClear(getDriver(), replaceEmail1Input, "replacer@replacer.com");
		ax._typeMeWithClear(getDriver(), replaceEmail2Input, "replacer@replacer.com");
		nextButton.click();
		ax.wait_elm(getDriver(), greenBoxConfirmation);
		if(greenBoxConfirmation.isDisplayed()){
			String monit = greenBoxConfirmation.getText().trim();
			return ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(monit, "Your request to replace an account representative has been submitted under identifier");
		}
		return null;
	}
	
	
}
