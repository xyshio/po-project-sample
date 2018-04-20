package po;

import static utils.SeleniumDriver.getDriver;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import utils.ProjectDataVariables;
import utils.ax;



/**
 * Page object representing AccountRequestOpeningDetials page.
 *
 * @author kgumulak
 */
public class AccountRequestOpeningDetails extends AbstractPage<AccountRequestOpeningDetails> {

	@FindBy(id = "mainForm:accountType")
	WebElement accountTypeSelect;	
	
	@FindBy(id = "mainForm:accountCommitmentPeriod")
	WebElement accountCommitmentPeriodSelect;	
	
	@FindBy(id = "mainForm:accountName")
	WebElement accountNameInput;	
	
	@FindBy(id = "mainForm:accountGroupId")
	WebElement accountGroupIdSelect;	
	
	@FindBy(id = "mainForm:buttonNext")
	WebElement buttonNext;	
	
	
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.titleContains("Account Opening Details");
	}
	@Override
	public ExpectedCondition<?> getPageLoadCondition(String titlePart) {
		return ExpectedConditions.titleContains(titlePart);
	}

	@Override
	public String getPageUrl() {
		return "/public/accounts/requestAccountOpeningDetails.xhtml";
	}
	
	public String accountRequestDialog(String accountType, String accountName, String yfe, String accountHolderId){
		AccountRequestHolderInformation accountRequestHolderInformation = fillDialogFields(accountType, "", accountName, "");
		ax.wait_for_ayax_2(getDriver());
		AccountBusinessDetails accountBusinessDetails = accountRequestHolderInformation.selectWhenHolderIsInTheRegistryProvideId(accountHolderId);
		ax.wait_for_ayax_2(getDriver());
		AccountContactPage accountContactPage = null;
		if(accountType.equals("OHA")){
			AccountInstallationInformationPage accountInstallationInformationPage = (AccountInstallationInformationPage) accountBusinessDetails.addAccountRepresentativeAlreadyRelated();
			accountContactPage = accountInstallationInformationPage.installationDialog(yfe);
		}else if(accountType.equals("AOHA")){
			AircraftOperatorPage aircraftOperatorPage = (AircraftOperatorPage) accountBusinessDetails.addAccountRepresentativeAlreadyRelated();
			accountContactPage = aircraftOperatorPage.airOpsDialog(yfe);
		}
		AccountSummaryPage accountSummaryPage = accountContactPage.contactPersonDialog();
		String stringFromMonit = accountSummaryPage.submitAccountSummary();
		// Output we expect: Your account opening request has been recorded with identifier 75988.
		String requestNumber = ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(stringFromMonit, "identifier");
		accountBusinessDetails.clickHomePageLink();
		return requestNumber;
	}
	
	public String accountRequestDialog(String accountType, String accountName, String yearOfFirstEmission /*YFE*/, ArrayList<String> urids /*URIDS*/){
		AccountRequestHolderInformation accountRequestHolderInformation = fillDialogFields(accountType, "", accountName, "");
		ax.wait_for_ayax_2(getDriver());
		AccountBusinessDetails accountBusinessDetails = accountRequestHolderInformation.selectWhenHolderIsNewAndProvideHolderDeatils(true);
		ax.wait_for_ayax_2(getDriver());
		AccountContactPage accountContactPage = null;
		if(accountType.equals("OHA") || accountType.equals(ProjectDataVariables.getAccountTypeByShortName("OHA"))){
			AccountInstallationInformationPage accountInstallationInformationPage = (AccountInstallationInformationPage) accountBusinessDetails.addAccountRepresentativeNew(urids, "installation");
			accountContactPage = accountInstallationInformationPage.installationDialog(yearOfFirstEmission);
	    }else if(accountType.equals("AOHA") || accountType.equals(ProjectDataVariables.getAccountTypeByShortName("AOHA"))){
	    	AircraftOperatorPage aircraftOperatorPage = (AircraftOperatorPage) accountBusinessDetails.addAccountRepresentativeNew(urids, "airops");
	    	accountContactPage = aircraftOperatorPage.airOpsDialog(yearOfFirstEmission);
	    }
		
		AccountSummaryPage accountSummaryPage = accountContactPage.contactPersonDialog();
		String stringFromMonit = accountSummaryPage.submitAccountSummary();
		// Output we expect: Your account opening request has been recorded with identifier 75988.
		String requestNumber = ax.GET_WORD_FROM_MONIT_PRECEED_BY_TEXT(stringFromMonit, "identifier");
		accountBusinessDetails.clickHomePageLink();
		return requestNumber;
	}
	
	
	public AccountRequestHolderInformation fillDialogFields(String AccountType, String Period, String Name, String Group){
		new Select(accountTypeSelect).selectByVisibleText(AccountType);
		ax.wait_for_ayax_2(getDriver());
//		if(accountCommitmentPeriodSelect.isDisplayed()){
//			new Select(accountCommitmentPeriodSelect).selectByIndex(0);	
//		}
		
		accountNameInput.sendKeys(Name);
		
		if(!Group.isEmpty()){
			new Select(accountGroupIdSelect).selectByVisibleText(Group);	
		}
		
		buttonNext.click();
		return new AccountRequestHolderInformation().loadPage(AccountRequestHolderInformation.class);
	}
	
	public AccountRequestOpeningDetails open() {
		return new AccountRequestOpeningDetails().loadPage(AccountRequestOpeningDetails.class);
	}
	@Override
	public String getExpectedPageTitle() {
		return "Account Opening Details";
	}

}
