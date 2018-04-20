package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import utils.ax;

/**
 * Page object representing Registration home page.
 *
 * @author kgumulak
 */
public class RegistrationPage extends AbstractPage<RegistrationPage> {

	public static RegistrationPage runEucr;
	
	@FindBy(id="mainForm:firstName")
	WebElement firstName;	
	@FindBy(id="mainForm:lastName")
	WebElement lastName;	
	@FindBy(id="mainForm:title")
	WebElement title;	
	@FindBy(id="mainForm:dateOfBirth_input")
	WebElement dateOfBirth_input;	
	@FindBy(id="mainForm:placeOfBirth")
	WebElement placeOfBirth;	
	@FindBy(id="mainForm:countryOfBirth")
	WebElement countryOfBirth;
	@FindBy(id="mainForm:typeOfIdDoc")
	WebElement typeOfIdDoc;
	@FindBy(id="mainForm:otherTypeOfIdDoc")
	WebElement otherTypeOfIdDoc;
	@FindBy(id="mainForm:identifierOfIdDoc")
	WebElement identifierOfIdDoc;
	@FindBy(id="mainForm:expirationDateOfIdDoc_input")
	WebElement expirationDateOfIdDoc_input;
	@FindBy(id="mainForm:mobilePhoneNumber")
	WebElement mobilePhoneNumber;
	
	@FindBy(id="mainForm:preferredLanguage")
	WebElement preferredLanguage;
	
	@FindBy(id="mainForm:secretQuestion")
	WebElement secretQuestion;
	@FindBy(id="mainForm:secretAnswer")
	WebElement secretAnswer;
	@FindBy(id="mainForm:buttonNext")
	WebElement buttonNext;
	@FindBy(id="mainForm:buttonNext")
	WebElement buttonSubmit;
	
	@FindBy(linkText="Enter your enrolment key")
	WebElement enterEnrollmentKeyLink;
	
	@FindBy(id="mainForm:enrolmentKeyPart1")
	WebElement enrolmentKeyPart1;
	@FindBy(id="mainForm:enrolmentKeyPart2")
	WebElement enrolmentKeyPart2;
	@FindBy(id="mainForm:enrolmentKeyPart3")
	WebElement enrolmentKeyPart3;
	@FindBy(id="mainForm:enrolmentKeyPart4")
	WebElement enrolmentKeyPart4;
	@FindBy(id="mainForm:enrolmentKeyPart5")
	WebElement enrolmentKeyPart5;
	
	@FindBy(how=How.XPATH, xpath="//span[contains(@class,'ui-messages-info-summary') and contains(normalize-space(),'Please review your personal details')]")
	WebElement greenBoxRegistrationReview;
	@FindBy(how=How.XPATH, xpath="//span[contains(@class,'ui-messages-info-summary') and contains(normalize-space(),'You have now been registered')]")
	WebElement greenBoxRegistrationRegistered;
	@FindBy(how=How.XPATH, xpath="//span[contains(@class,'ui-messages-info-summary') and contains(normalize-space(),'Your access to the registry has been activated')]")
	WebElement greenBoxRegistrationActivated;
	
	@Override
	public String getExpectedPageTitle() {
		return "Registration";
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
	public RegistrationPage goToTaskPage() {
		return new RegistrationPage().loadPage(RegistrationPage.class);
	}
	
	public RegistrationPage open() {
		return new RegistrationPage().loadPage(RegistrationPage.class);
	}

	public RegistrationPage fillRegistrationForm(){
		ax._typeMeWithClear(getDriver(), firstName, "EUCR");
		ax._typeMeWithClear(getDriver(), lastName, "USER");
		ax._typeMeWithClear(getDriver(), title					, "Magister");
		ax._typeMeWithClear(getDriver(), dateOfBirth_input		, "13/02/1978");
		ax._typeMeWithClear(getDriver(), placeOfBirth			, "Kloringer");
		new Select(countryOfBirth).selectByValue("AT");
		new Select(typeOfIdDoc).selectByValue("NATIONAL_ID_CARD");
		ax._typeMeWithClear(getDriver()							, identifierOfIdDoc, "55432");
		ax._typeMeWithClear(getDriver()							, expirationDateOfIdDoc_input, "13/02/2025");
		new Select(preferredLanguage).selectByValue("EN");
		ax._typeMeWithClear(getDriver()							, mobilePhoneNumber, "+77789");
		ax._typeMeWithClear(getDriver()							, secretQuestion, "chatka");
		ax._typeMeWithClear(getDriver()							, secretAnswer, "niemcowa");
		buttonNext.click();
		if(greenBoxRegistrationReview.isDisplayed()){
			buttonSubmit.click();
			if(ax.wait4element(getDriver(), greenBoxRegistrationRegistered)!=null){
				return new RegistrationPage().loadPageWithExpectedTitle(RegistrationPage.class, "Confirm User Registration");		
			}
			return null;
		}
		return null;
	}
	
	public RegistrationPage enterEnrollmentKey(String key){
		enterEnrollmentKeyLink.click();
		String[] keys = key.split("-");
		ax._typeMeWithClear(getDriver(), enrolmentKeyPart1, keys[0]);
		ax._typeMeWithClear(getDriver(), enrolmentKeyPart2, keys[1]);
		ax._typeMeWithClear(getDriver(), enrolmentKeyPart3, keys[2]);
		ax._typeMeWithClear(getDriver(), enrolmentKeyPart4, keys[3]);
		ax._typeMeWithClear(getDriver(), enrolmentKeyPart5, keys[4]);
		buttonSubmit.click();
		if(ax.wait4element(getDriver(), greenBoxRegistrationActivated)!=null){
			return new RegistrationPage().loadPageWithExpectedTitle(RegistrationPage.class, "Confirm User Activation");
		}
		
		return null;
	}
	
}
