package po;

import static utils.SeleniumDriver.getDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ax;


/**
 * Page object representing TaskListPageObject page.
 *
 * @author kgumulak
 */
public class TaskListPoPage extends AbstractPage<TaskListPoPage>{
	/*
	 * This is page displayed when after searching task, 
	 *  user is clicking the bottom request link 
	 *  and is moved to Task Details Page to Approve or Reject
	 */
	
	@FindBy(how=How.XPATH, using="//div[@class='buttonContainer']/button/span[contains(normalize-space(),'Approve')]")
	WebElement approveButton;	

	@FindBy(how=How.XPATH, using="//div[@class='buttonContainer']/button/span[contains(normalize-space(),'Force Closure')]")
	WebElement forceClosureButton;	

	
	@FindBy(how=How.XPATH, using="//div[@class='buttonContainer']/button/span[contains(normalize-space(),'Reject')]")
	WebElement rejectButton;	
	
	@FindBy(how=How.XPATH, using="//fieldset[@id='accountDetails']")
	WebElement accountDetailsFieldset;	
	
	@FindBy(how=How.XPATH, using="//fieldset[@id='accountDetails']//dl[@class='detailsList']/dt[contains(normalize-space(),'ID')]/following-sibling::dd[1]")
	WebElement accountDetailsIdentifier;	

	@FindBy(how=How.XPATH, using="//textarea[contains(@id,'confirmationCommentApprove')]") 
	WebElement confirmationPoupCommentBox;	// taskCompletionConfirmationComments
	
	@FindBy(how=How.XPATH, using="//button[contains(@id,'confirmConfirmButtonId')]") 
	WebElement confirmationPoupConfirmButton;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Request Details";
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
		return "/protected/tasks/tasklist.xhtml";
	}	
	
	public String getIdentifierFromAccoutnOpeningDetails(){
		return accountDetailsIdentifier.getText().trim();
	}

	public TaskListPoPage clickApproveButton(){
		return clickApproveButton("","");
	}
	
	public TaskListPoPage clickApproveButton(String expectedTitle){
		return clickApproveButton(expectedTitle, "");
	}	
	
	public TaskListPoPage clickApproveButton(String expectedTitle, String buttonLabel){
		
	if(buttonLabel.isEmpty()){
		approveButton = approveButton;
	}	else{
		switch (buttonLabel) {
		case "Force Closure": 		approveButton = forceClosureButton; 			break;
		default: 			approveButton = approveButton; 			break;
		}
	}
		if(!approveButton.isDisplayed()||!approveButton.isEnabled()){
			ax.fail(getDriver(), "Not enable or desplayed Approve button");
			return null;	
		}
		try {
			ax.wait_elm(getDriver(), approveButton);
			ax._clickMe(getDriver(), approveButton);
			ax.wait_for_ayax_2(getDriver());
			if(!ax.wait_elm(getDriver(), confirmationPoupCommentBox)){
				ax.fail(getDriver(), "Problem with getting into Approval Comment Box");
				return null;
			}
			ax._typeMe(getDriver(), confirmationPoupCommentBox, "Approving Comment");
			ax._clickMe(getDriver(), confirmationPoupConfirmButton);
			ax.WAIT4PageLoad(getDriver());
			
			
			if(!expectedTitle.isEmpty()){
				return new TaskListPoPage().loadPageWithExpectedTitle(TaskListPoPage.class, expectedTitle);
			}else{
				return new TaskListPoPage().loadPage(TaskListPoPage.class);	
			}
			
			
			
			
			
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with Approve button");
			return null;
		}
	}
	
	
}
