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
 * Page object representing Account Search page.
 *
 * @author kgumulak
 */
public class AccountsPage extends AbstractPage<AccountsPage> {

	public static AccountsPage runEucr;
	
	@FindBy(id="menuForm:view_accounts_menuitem")
	WebElement accountsPageLink;	
	
	@FindBy(id="mainForm:filterAccountId")
	WebElement accountsIdentifierInput;	
	
	@FindBy(id="mainForm:accountName")
	WebElement accountsNameInput;	
	
	@FindBy(id="mainForm:accountType")
	WebElement accountsTypeSelect;	
	
	@FindBy(id="mainForm:buttonSearch")
	WebElement accountsSearchButton;	
	
	@FindBy(how=How.XPATH, using="//div[contains(concat(' ',@class,' '),'ui-panel')]/div[contains(concat(' ',@class,' '),'ui-panel-titlebar')]/span[contains(normalize-space(),'Account Search Result')]/../..")
	WebElement accountsSearchResultBox;	
	
	@FindBy(id="mainForm:accountSearchResultsTable")
	WebElement accountsSearchResultTable;	
	
	@FindBy(how=How.XPATH, using="//div[@id='mainForm:accountSearchResultsTable_paginatortop']/span[@class='ui-paginator-current']")
	WebElement accountsSearchResultTablePaginatorElement;	
	
	@FindBy(id="mainForm:accountSearchResultsTable_data")
	WebElement accountsSearchResultThead;	
	
	@FindBy(how=How.XPATH, using="//tbody[@id='mainForm:accountSearchResultsTable_data']/tr/td[position()=1]/a")
	WebElement accountsSearchResult1x1FullNumberLink;	
	@FindBy(how=How.XPATH, using="//tbody[@id='mainForm:accountSearchResultsTable_data']/tr/td[position()=7]")
	WebElement accountsSearchResult1x1AccountStatusCell;	
	@FindBy(how=How.XPATH, using="//tbody[@id='mainForm:accountSearchResultsTable_data']/tr/td[position()=6]/a")
	WebElement accountsSearchResult1x1AccountBalanceCellLink;	
	@FindBy(how=How.XPATH, using="//tbody[@id='mainForm:accountSearchResultsTable_data']/tr/td[position()=9]/a")
	WebElement accountsSearchResult1x1AccountInstallAiropsLink;	
	
	@FindBy(how=How.XPATH, using="//tbody[@id='mainForm:accountSearchResultsTable_paginatortop']/tr/td[contains(normalize-space(),'No records found')]")
	WebElement accountsSearchResultNothingFound;	

	@Override
	public String getExpectedPageTitle() {
		return "View Accounts List";
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
	
	public String getFullAccountFrom1x1(){
		return accountsSearchResult1x1FullNumberLink.getText().trim();
	}
	public String getAccountStatusFrom1x1(){
		return accountsSearchResult1x1AccountStatusCell.getText().trim();
	}
	
	
	
	
	
	public AccountsPage searchAccountById(String identifier){
//		if(ax.IS_NULL_OR_EMPTY_STRING(identifier)){
//			ax.log(getDriver(), "Account Identifier was not provided.. ");
//			return null;
//		}
//		ax.wait(1);
		ax._typeMeWithClear(getDriver(), accountsIdentifierInput, identifier);
		accountsSearchButton.click();
		ax.wait_for_ayax_2(getDriver());
		if(!ax.wait_elm(getDriver(), accountsSearchResultTablePaginatorElement)) {
			ax.log(getDriver(), "Search for account "+identifier+ " did not found result table");
			return null;
		}
		System.out.println("");
		if(accountsSearchResultTablePaginatorElement.isDisplayed()){
			int i=0;
			int found = ax.getNumberOfRowsFromPaginatorLabel(accountsSearchResultTablePaginatorElement.getText().trim());
			while (found < 1){
				System.out.print(i + " ");
				ax.wait(1);
				accountsSearchButton.click();
				ax.wait_for_ayax_2(getDriver());
				found = ax.getNumberOfRowsFromPaginatorLabel(accountsSearchResultTablePaginatorElement.getText().trim());
				if(i > ProjectDataVariables.TIMEX_FX_30){
					ax.log(getDriver(), "Nothing found after 30 seconds");
					return null;
				}
				i++;
			}
		}
		return new AccountsPage().loadPage(AccountsPage.class);
	}
	
	public AccountDetailsPoPage clickFullAccountNumber1x1(){
		accountsSearchResult1x1FullNumberLink.click();
		return new AccountDetailsPoPage().loadPage(AccountDetailsPoPage.class);
	}
	public AccountDetailsPoPage clickBalanceAccountLink(){
		accountsSearchResult1x1AccountBalanceCellLink.click();
		return new AccountDetailsPoPage().loadPage(AccountDetailsPoPage.class);
	}
	
	
	public AccountsPage goToAccountsPage() {
		return new AccountsPage().loadPageWithUrl(AccountsPage.class);
	}
	
	public AccountsPage open() {
		return new AccountsPage().loadPage(AccountsPage.class);
	}

	public int SearchAccountByIdentifier(String identifier){
		AccountsPage accounts = searchAccountById(identifier);
		if(accounts!=null){
			return ax.getNumberOfRowsFromPaginatorLabel(accounts.accountsSearchResultTablePaginatorElement.getText().trim());
		}else{
			return 0;
		}
	}
	
	
	
}
