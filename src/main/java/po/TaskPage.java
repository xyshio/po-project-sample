package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ProjectDataVariables;
import utils.SeleniumWebUtils;
import utils.ax;


/**
 * Page object representing xxxxxxx home page.
 *
 * @author kgumulak
 */
public class TaskPage extends AbstractPage<TaskPage> {

	public static TaskPage runEucr;
	
	@FindBy(linkText="Task list")
	WebElement taskPageLink;	
	
	@FindBy(id="tabViewTasks")
	WebElement taskPageGeneralTabsElement;	
	
	@FindBy(how=How.XPATH, using="//*[contains(concat(' ',@class,' '),'ui-state-default')]/*[contains(@href,'manageTaskListNaExclusiveTab')]/..")
	WebElement taskEtlTabLink;	
	
	@FindBy(how=How.XPATH, using="//*[contains(concat(' ',@class,' '),'ui-state-default')]/*[contains(@href,'manageTaskListNaGeneralTab')]/..")
	WebElement taskGtlTabLink;	

	@FindBy(how=How.XPATH, using="//*[contains(concat(' ',@class,' '),'ui-state-default')]/*[contains(@href,'manageHistoryTaskTab')]/..")
	WebElement taskHistTabLink;	
	
	@FindBy(id="manageNaExclusiveTaskListForm:requestId")
	WebElement taskEtlRequestIdInput;	

	@FindBy(id="manageNaGeneralTaskListForm:requestId")
	WebElement taskGtlRequestIdInput;	
	
	@FindBy(id="manageHistoryTaskListForm:requestId")
	WebElement taskHistRequestIdInput;	
	
	@FindBy(id="manageNaExclusiveTaskListForm:filterButtonId")
	WebElement taskEtlSearchSubmit;	

	@FindBy(id="manageNaGeneralTaskListForm:filterButtonId")
	WebElement taskGtlSearchSubmit;	
	
	@FindBy(id="manageHistoryTaskListForm:filterButtonId")
	WebElement taskHistSearchSubmit;	
	
	@FindBy(id="manageNaExclusiveTaskListForm:tasksTable")
	WebElement etlFilterResultBox;	
	
	@FindBy(how=How.XPATH, using="//div[@id='manageNaExclusiveTaskListForm:tasksTable']/div[@id='manageNaExclusiveTaskListForm:tasksTable_paginatortop']/span[@class='ui-paginator-current']")
	WebElement etlFilterResultsNumerOfRowsLabel;	
	
	@FindBy(how=How.XPATH, using="//div[@id='manageNaGeneralTaskListForm:tasksTable']/div[@id='manageNaGeneralTaskListForm:tasksTable_paginatortop']/span[@class='ui-paginator-current']")
	WebElement gtlFilterResultsNumerOfRowsLabel;	

	@FindBy(how=How.XPATH, using="//div[@id='manageHistoryTaskListForm:tasksTable']/div[@id='manageHistoryTaskListForm:tasksTable_paginatortop']/span[@class='ui-paginator-current']")
	WebElement histFilterResultsNumerOfRowsLabel;	

	@FindBy(how=How.XPATH, using = "//tbody[@id='manageNaExclusiveTaskListForm:tasksTable_data']")
	WebElement etlResultTbodyElement;	

	@FindBy(id="manageNaExclusiveTaskListForm:tasksTable:0:selectedTask")
	WebElement etlFirstResultCheckBox;	
	@FindBy(id="manageNaGeneralTaskListForm:tasksTable:0:selectedTask")
	WebElement gtlFirstResultCheckBox;	
	
	@FindBy(how=How.XPATH, using = "//tbody[@id='manageNaExclusiveTaskListForm:tasksTable_data']/tr/td[position()=2]/a")
	WebElement etlFirstResultNameLink; // Second column link	
	@FindBy(how=How.XPATH, using = "//tbody[@id='manageNaGeneralTaskListForm:tasksTable_data']/tr/td[position()=2]/a")
	WebElement gtlFirstResultNameLink; // Second column link	

	@FindBy(how=How.XPATH, using = "//tbody[@id='manageNaExclusiveTaskListForm:tasksTable_data']/tr/td[position()=3]/span")
	WebElement etlFirstResultClaimantTdSpan; // Third column link	
	@FindBy(how=How.XPATH, using = "//tbody[@id='manageNaGeneralTaskListForm:tasksTable_data']/tr/td[position()=3]/span")
	WebElement gtlFirstResultClaimantTdSpan; // Third column link	

	@FindBy(how=How.XPATH, using = "//tbody[@id='manageNaExclusiveTaskListForm:tasksTable_data']/tr/td[position()=6]")
	WebElement etlFirstResultRequestNumberTdCell; // Third column link	
	@FindBy(how=How.XPATH, using = "//tbody[@id='manageNaGeneralTaskListForm:tasksTable_data']/tr/td[position()=6]")
	WebElement gtlFirstResultRequestNumberTdCell; // Third column link	
	
	
	@FindBy(how=How.XPATH, using = "//tbody[@id='manageHistoryTaskListForm:tasksTable_data']/tr/td[position()=10]")
	WebElement histFirstResultOutcmeNumberTdCell; // Outcome cell for first row	
	@FindBy(how=How.XPATH, using = "//tbody[@id='manageHistoryTaskListForm:tasksTable_data']/tr/td[position()=11]")
	WebElement histFirstResultStatusNumberTdCell; // Status cell for first row	
	
	
	
	@FindBy(id="manageNaExclusiveTaskListForm:taskPg")
	WebElement manageNaExclusiveTaskListForm; // span block which keeps Details of The request after clicking Name link	
	@FindBy(id="manageNaGeneralTaskListForm:taskPg")
	WebElement manageNaGeneralTaskListForm; // span block which keeps Details of The request after clicking Name link	

	@FindBy(how=How.XPATH, using = "//table[@id='manageNaExclusiveTaskListForm:taskDetailsGridId']//span[@id='manageNaExclusiveTaskListForm:requestIdPanelGroup']//a[contains(@onclick,'doManageCompleteCommand')]")
	WebElement etlManageNaExclusiveTaskListFormTaskDetailsGridId; // request number link on the bottom	
	           
	@FindBy(how=How.XPATH, using = "//table[@id='manageNaGeneralTaskListForm:taskDetailsGridId']//span[@id='manageNaGeneralTaskListForm:requestIdPanelGroup']//a[contains(@onclick,'doManageCompleteCommand')]")
	WebElement gtlManageNaGeneralTaskListFormTaskDetailsGridId; // request number link on the bottom	
	
	@FindBy(id="manageNaExclusiveTaskListForm:claimButtonId")
	WebElement etlClaimButton;	
	@FindBy(id="manageNaGeneralTaskListForm:claimButtonId")
	WebElement gtlClaimButton;	
	
	@FindBy(id="manageNaGeneralTaskListForm:tasksTable")
	WebElement gtlFilterResultBox;	
	
	@FindBy(id="manageHistoryTaskListForm:tasksTable")
	WebElement histFilterResultBox;	
	// ###################################################################################################
//	 temporary switched off
//	@Override
//	protected ExpectedCondition<?> getPageLoadCondition() {
//		return ExpectedConditions.visibilityOf(taskPageGeneralTabsElement);
//	}
	@Override
	public ExpectedCondition<?> getPageLoadCondition(String titlePart) {
		return ExpectedConditions.titleContains(titlePart);
	}
	@Override
	public String getExpectedPageTitle() {
		return "Task List";
	}
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.titleContains(getExpectedPageTitle());
	}	
	
	@Override
	public String getPageUrl() {
		return "/protected/tasks/tasklist.xhtml";
	}
	
	public void clickEtlTab(TaskPage task){
		task.taskEtlTabLink.click();
	}
	public void clickGtlTab(TaskPage task){
		task.taskGtlTabLink.click();
	}
	public void clickHistTab(TaskPage task){
		task.taskHistTabLink.click();
	}
	
	public int getEtlRowsFoundInPaginatorCurrent(){ // we assume element if displayed
		if(etlFilterResultsNumerOfRowsLabel!=null){
			String paginatorText = etlFilterResultsNumerOfRowsLabel.getText().trim();
			return ax.getNumberOfRowsFromPaginatorLabel(paginatorText);
		}
		return -1;
	}
	public int getGtlRowsFoundInPaginatorCurrent(){ // we assume element if displayed
		if(gtlFilterResultsNumerOfRowsLabel!=null){
			String paginatorText = gtlFilterResultsNumerOfRowsLabel.getText().trim();
			return ax.getNumberOfRowsFromPaginatorLabel(paginatorText);
		}
		return -1;
	}
	public int getHistRowsFoundInPaginatorCurrent(){ // we assume element if displayed
		if(histFilterResultsNumerOfRowsLabel!=null){
			String paginatorText = histFilterResultsNumerOfRowsLabel.getText().trim();
			return ax.getNumberOfRowsFromPaginatorLabel(paginatorText);
		}
		return -1;
	}	
	
	public String getOutcomeFromHistoricFristRow(){
		if(histFirstResultOutcmeNumberTdCell!=null){
			return histFirstResultOutcmeNumberTdCell.getText().trim();
		}
		return null;
	}
	public String getStatusFromHistoricFristRow(){
		if(histFirstResultStatusNumberTdCell!=null){
			return histFirstResultStatusNumberTdCell.getText().trim();
		}
	return null;
	}
	
	
	public int searchEtlByRequestNumber(String requestNumber){
		TaskPage task = new TaskPage().loadPageWithUrl(TaskPage.class);
		new TaskPage().clickEtlTab(task);
		ax._typeMeWithClear(getDriver(), taskEtlRequestIdInput, requestNumber);
		ax.wait4element(getDriver(), taskEtlSearchSubmit);
		ax._clickMe(getDriver(), taskEtlSearchSubmit);
		ax.waitForAjax(getDriver());
		ax.wait4element(getDriver(), etlFilterResultsNumerOfRowsLabel);
		int resultsFound = getEtlRowsFoundInPaginatorCurrent();
		int i=0;
		while(resultsFound == 0){
			ax._clickMe(getDriver(), taskEtlSearchSubmit);
			ax.waitForAjax(getDriver());
			ax.wait4element(getDriver(), etlFilterResultsNumerOfRowsLabel);
			resultsFound = getEtlRowsFoundInPaginatorCurrent();
			i++;
			ax.wait(1);
			if(i > ProjectDataVariables.TIMEX_FX_20) {
				ax.log("Waiting too long for Task request ["+requestNumber+"] in ETL search, possible not exists on this tab, trying GTL....");
				
//				TaskPage task = new TaskPage().loadPageWithUrl(TaskPage.class);
//				new TaskPage().clickGtlTab(task);
//				int gtlI = searchGtlByRequestNumber(requestNumber);
//				if(gtlI > 0) return gtlI;
				
				return -1;
			}
		}
		ax.log("Found "+resultsFound+" records");
		return resultsFound;
	}
	public int searchGtlByRequestNumber(String requestNumber){
		TaskPage task = new TaskPage().loadPageWithUrl(TaskPage.class);
		new TaskPage().clickGtlTab(task);
		ax._typeMeWithClear(getDriver(), taskGtlRequestIdInput, requestNumber);
		ax.wait4element(getDriver(), taskGtlSearchSubmit);
		ax._clickMe(getDriver(), taskGtlSearchSubmit);
		ax.waitForAjax(getDriver());
		ax.wait4element(getDriver(), gtlFilterResultsNumerOfRowsLabel);
		int resultsFound = getGtlRowsFoundInPaginatorCurrent();
		int i=0;
		while(resultsFound == 0){
			ax._clickMe(getDriver(), taskGtlSearchSubmit);
			ax.waitForAjax(getDriver());
			ax.wait4element(getDriver(), gtlFilterResultsNumerOfRowsLabel);
			resultsFound = getGtlRowsFoundInPaginatorCurrent();
			i++;
			ax.wait(1);
			if(i > ProjectDataVariables.TIMEX_FX_20) {
				ax.log("Waiting too long for Task request ["+requestNumber+"] in GTL search, possible not exists, trying ETL....");
				
//				TaskPage task = new TaskPage().loadPageWithUrl(TaskPage.class);
//				new TaskPage().clickEtlTab(task);
//				int etlI = searchEtlByRequestNumber(requestNumber);
//				if(etlI > 0) return etlI;

				return -1;
			}
		}
		
		ax.log("Found "+resultsFound+" records");
		return resultsFound;
	}
	public int searchHistByRequestNumber(String requestNumber){
		ax._typeMeWithClear(getDriver(), taskHistRequestIdInput, requestNumber);
		ax.wait4element(getDriver(), taskHistSearchSubmit);
		ax._clickMe(getDriver(), taskHistSearchSubmit);
		ax.waitForAjax(getDriver());
		ax.wait4element(getDriver(), histFilterResultsNumerOfRowsLabel);
		int resultsFound = getHistRowsFoundInPaginatorCurrent();
		int i=0;
		while(resultsFound == 0){
			ax._clickMe(getDriver(), taskHistSearchSubmit);
			ax.waitForAjax(getDriver());
			ax.wait4element(getDriver(), histFilterResultsNumerOfRowsLabel);
			resultsFound = getHistRowsFoundInPaginatorCurrent();
			i++;
			ax.wait(1);
			if(i > ProjectDataVariables.TIMEX_FX_45) {
				ax.log("Waiting too long for Task request ["+requestNumber+"] in HIST search, possible not exists");
				return -1;
			}
		}
//		ax.log("Found "+resultsFound+" records");
		return resultsFound;
	}	
	public TaskListPoPage claimFirstEtlResult(String requestnumber){
		return claimFirstEtlResult(requestnumber, "");
	}
	public TaskListPoPage claimFirstEtlResult(String requestnumber, String expectedPageTitlePart){
		if(etlFirstResultCheckBox.isDisplayed() && etlFirstResultCheckBox.isEnabled()){
			if(!etlFirstResultCheckBox.isSelected()){
				etlFirstResultCheckBox.click();
				ax.waitForAjax(getDriver());
			}
			if(!etlFirstResultRequestNumberTdCell.getText().trim().contains(requestnumber)){
				ax.log("Result record found in ETL Task page is not matching Request Number " + requestnumber);
				return null;
			}
			etlClaimButton.click();
			ax.waitForAjax(getDriver());
			if(!SeleniumWebUtils.WaitForTextPresentInElement(getDriver(), etlFirstResultClaimantTdSpan)){
				ax.log("Cannot claim Request Number " + requestnumber + " in ETL Task Page");
				return null;
			}
			etlFirstResultNameLink.click();
			ax.waitForAjax(getDriver());
			if(!ax.wait_elm(getDriver(), manageNaExclusiveTaskListForm)){
				ax.log("Waiting too long got Request Number link on the bottom of ETL Task page");
				return null;
			}
			if(!ax._clickMe(getDriver(), etlManageNaExclusiveTaskListFormTaskDetailsGridId)){
				ax.log("Cannot click in Request Number link on the bottom of ETL Task page");
				return null;
			}
			ax.WAIT4PageLoad(getDriver());
			
		}
		
		if(!expectedPageTitlePart.isEmpty()){
			return new TaskListPoPage().loadPageWithExpectedTitle(TaskListPoPage.class, expectedPageTitlePart);
		}else{
			return new TaskListPoPage().loadPage(TaskListPoPage.class);	
		}
		
		
	}
	public TaskListPoPage claimFirstGtlResult(String requestnumber){
		return claimFirstGtlResult(requestnumber, "");
	}
	public TaskListPoPage claimFirstGtlResult(String requestnumber, String expectedPageTitlePart){
		if(gtlFirstResultCheckBox.isDisplayed() && gtlFirstResultCheckBox.isEnabled()){
			if(!gtlFirstResultCheckBox.isSelected()){
				gtlFirstResultCheckBox.click();
				ax.waitForAjax(getDriver());
			}
			if(!gtlFirstResultRequestNumberTdCell.getText().trim().contains(requestnumber)){
				ax.log("Result record found in GTL Task page is not matching Request Number " + requestnumber);
				return null;
			}
			gtlClaimButton.click();
			ax.waitForAjax(getDriver());
			if(!SeleniumWebUtils.WaitForTextPresentInElement(getDriver(), gtlFirstResultClaimantTdSpan)){
				ax.log("Cannot claim Request Number " + requestnumber + " in GTL Task Page");
				return null;
			}
			gtlFirstResultNameLink.click();
			ax.waitForAjax(getDriver());
			if(!ax.wait_elm(getDriver(), manageNaGeneralTaskListForm)){
				ax.log("Waiting too long got Request Number link on the bottom of GTL Task page");
				return null;
			}
			if(!ax._clickMe(getDriver(), gtlManageNaGeneralTaskListFormTaskDetailsGridId)){
				ax.log("Cannot click in Request Number link on the bottom of GTL Task page");
				return null;
			}
			ax.WAIT4PageLoad(getDriver());
			
		}
		
		if(!expectedPageTitlePart.isEmpty()){
			return new TaskListPoPage().loadPageWithExpectedTitle(TaskListPoPage.class, expectedPageTitlePart);
		}else{
			return new TaskListPoPage().loadPage(TaskListPoPage.class);
			
		}		
		
	}	
//	public TaskPage searchGtlByRequestNumber(String requestNumber){
//		
//	}
//	public TaskPage searchHistByRequestNumber(String requestNumber){
//		
//	}
	
	public TaskPage goToEtlTab(TaskPage task) {
		int i=0;
		do{
			task.clickEtlTab(task);
			ax.wait_for_ayax_2(getDriver());
			task = new TaskPage().loadPageWithExpectedTitle(TaskPage.class, "Task List");			
			i++;
			if(i > ProjectDataVariables.TIMEX_FX_30) {
				ax.log("Problem with opening ETL tab in TaskPage"); return null;
			}
		}while(!task.taskEtlRequestIdInput.isDisplayed());
		return task;
	}
	public TaskPage goToGtlTab(TaskPage task) {
		int i=0;
		do{
			task.clickGtlTab(task);
			ax.wait_for_ayax_2(getDriver());
			task = new TaskPage().loadPageWithExpectedTitle(TaskPage.class, "Task List");
			i++;
			if(i > ProjectDataVariables.TIMEX_FX_30) {
				ax.log("Problem with opening GTL tab in TaskPage"); return null;
			}
		}while(!task.taskGtlRequestIdInput.isDisplayed());
		return task;
	}
	public TaskPage goToHistTab(TaskPage task) {
		int i=0;
		do{
			task.clickHistTab(task);
			ax.wait_for_ayax_2(getDriver());
			task = new TaskPage().loadPageWithExpectedTitle(TaskPage.class, "Task List");
			i++;
			if(i > ProjectDataVariables.TIMEX_FX_30) {
				ax.log("Problem with opening HIST tab in TaskPage"); return null;
			}
		}while(!task.taskHistRequestIdInput.isDisplayed());
		return task;
	}	
	
	public TaskPage goToTaskHomePage() {
		return new TaskPage().loadPageWithUrl(TaskPage.class);
	}
	
	
	
	
	public TaskPage open() {
		return new TaskPage().loadPage(TaskPage.class);
	}

}
