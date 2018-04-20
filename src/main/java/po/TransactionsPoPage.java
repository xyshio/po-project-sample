package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.ProjectDataVariables;
import utils.ax;


/**
 * Page object representing Transactions page.
 *
 * @author kgumulak
 */
public class TransactionsPoPage extends AbstractPage<TransactionsPoPage> {

	public static TransactionsPoPage runEucr;
	
	@FindBy(id="searchForm:transactionid")
	WebElement transactionIdInput;	
	
	@FindBy(id="searchForm:buttonSearch")
	WebElement transactionPageSearchButton;	

	@FindBy(id="searchForm:resultsTable")
	WebElement transactionPageResultBox;	
	
	@FindBy(how=How.XPATH, using ="//div[@id='searchForm:resultsTable_paginatortop']/span[@class='ui-paginator-current']")
	WebElement transactionPageResultPaginatorCurrent;	
	
	@FindBy(id="searchForm:resultsTable_data")
	WebElement transactionPageResultTbody;	
	
	@FindBy(how=How.XPATH, using ="//tr[@id='searchForm:resultsTable_row_0']")
	WebElement transactionPageResultRow1x1;	
	
	@FindBy(how=How.XPATH, using ="//tr[@id='searchForm:resultsTable_row_0']/td[contains(normalize-space(),'No records found')]")
	WebElement transactionPageResultNoRecordsFound;	

	@FindBy(how=How.XPATH, using ="//tr[@id='searchForm:resultsTable_row_0']/td[position()='1']/a")
	WebElement transactionPageResultRow1x1TransactionIdLink;	
	
	@FindBy(how=How.XPATH, using ="//tr[@id='searchForm:resultsTable_row_0']/td[position()='5']")
	WebElement transactionPageResultRow1x1Status;	
	
	@FindBy(how=How.XPATH, using ="//tr[@id='searchForm:resultsTable_row_0']/td[position()='6']")
	WebElement transactionPageResultRow1x1Quantity;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Transactions List";
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
		return "/protected/transactions/searchTransactions.xhtml";
	}
	
	public TransactionsPoPage goToTransactionsPage() {
		return new TransactionsPoPage().loadPageWithUrl(TransactionsPoPage.class);
	}
	
	public TransactionsPoPage open() {
		return new TransactionsPoPage().loadPage(TransactionsPoPage.class);
	}

	public TransactionsPoPage searchByTransactionId(String TransactionIdentifier){
		if(ax.IS_NULL_OR_EMPTY_STRING(TransactionIdentifier)) return null;
		ax._typeMeWithClear(getDriver(), transactionIdInput, TransactionIdentifier);
		transactionPageSearchButton.click();
		ax.wait_for(getDriver(), transactionPageResultTbody);
		
		int resultsFound = ax.getNumberOfRowsFromPaginatorLabel(transactionPageResultPaginatorCurrent.getText().trim());
		int i=0;
		while(resultsFound < 1){
			ax.wait(1);
			transactionPageSearchButton.click();
			ax.wait_for(getDriver(), transactionPageResultTbody);
			resultsFound = ax.getNumberOfRowsFromPaginatorLabel(transactionPageResultPaginatorCurrent.getText().trim());
			i++;
			if(i > ProjectDataVariables.TIMEX_FX_30){
				ax.log(getDriver(), "Nothing found for Transaction " + TransactionIdentifier);
				return null;
			}
		}
		return new TransactionsPoPage().loadPageWithExpectedTitle(TransactionsPoPage.class);
	}
	public String getStatusOfTransaction(){
		return transactionPageResultRow1x1Status.getText().trim();
	}
	
	public boolean checkTransactionStatusForCompleted(TransactionsPoPage transactions, String transactionIdentifier){
		if(searchByTransactionId(transactionIdentifier)!=null){
			if(getStatusOfTransaction().contains("4-Completed")){
				return true;
			}
		}
		return false;
	}
	
}
