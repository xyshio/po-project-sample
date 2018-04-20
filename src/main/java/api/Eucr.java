package api;

import static utils.SeleniumDriver.getDriver;

import java.util.ArrayList;
import java.util.Arrays;

import po.AccountAuthoriseRepresentativePoPage;
import po.AccountBusinessDetails;
import po.AccountClosurePoPage;
import po.AccountContactPage;
import po.AccountDetailsPoPage;
import po.AccountReleasePoPage;
import po.AccountRequestHolderInformation;
import po.AccountRequestOpeningDetails;
import po.AccountSuspendPage;
import po.AccountsPage;
import po.AuthorisedRepresentativeUpdatePoPage;
import po.ClaimAccountPoPage;
import po.EucrHomePage;
import po.InstallationUpdatePoPage;
import po.TaskListPoPage;
import po.TaskPage;
import po.TransactionsPoPage;
import utils.ProjectDataVariables;
import utils.ax;

public class Eucr {

	public static int i =0;
	public static String requestNumber;
	
	
	public static String CreateAccountWithAccountHolderNumber(EucrHomePage eucrPage, String AccountType, String accountName, String yfe, String accountHolderNumber){
		AccountType = ProjectDataVariables.getAccountTypeByShortName(AccountType);
		AccountRequestOpeningDetails accountRequestOpeningDetails = eucrPage.clickAccountRequestLink();
		return accountRequestOpeningDetails.accountRequestDialog(AccountType, accountName, yfe, accountHolderNumber);
	}
	public static String CreateAccountWithArs(EucrHomePage eucrPage, String AccountType, String accountName, String yearOfFirstEmission){
		// URIDS in 2D array are separated with colons ":", like AR1:AR2, AAR1:AAR2:AAR3
		ArrayList<String> usersUrids = new ArrayList<String>(Arrays.asList(ProjectDataVariables.getUserUrid("AR1")+":"+ProjectDataVariables.getUserUrid("AR2"),ProjectDataVariables.getUserUrid("AAR1")));
		AccountType = ProjectDataVariables.getAccountTypeByShortName(AccountType);
		AccountRequestOpeningDetails accountRequestOpeningDetails = eucrPage.clickAccountRequestLink();
		return accountRequestOpeningDetails.accountRequestDialog(AccountType, accountName, yearOfFirstEmission, usersUrids);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Menu
	//////////////////////////////////////////////////////////////////////////////////////
	public static EucrHomePage openHomePage(){
		return new EucrHomePage().loadPageWithUrl(EucrHomePage.class);
	}
	public static AccountsPage openAccountsPage(){
		return new AccountsPage().loadPageWithUrl(AccountsPage.class);
	}
	public static TransactionsPoPage openTransactionPage(){
		return new TransactionsPoPage().loadPageWithUrl(TransactionsPoPage.class);
	}
	public static TaskPage openTaskPage(){
		return new TaskPage().loadPageWithUrl(TaskPage.class);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	// TASKS
	//////////////////////////////////////////////////////////////////////////////////////
	
	public static boolean ApproveTransactionRequestsGtl(String accountRequestNumber){
		/* ETL accountOpening Approval
		 * @return Account Identifier or null
		 */
		TaskPage task = Eucr.openTaskPage();
		task = task.loadPageWithExpectedTitle(TaskPage.class, "Task List");
//		new TaskPage().goToEtlTab(task);
		new TaskPage().goToGtlTab(task);
    	if(task.searchGtlByRequestNumber(accountRequestNumber) < 0){
    		return false;
    	}
    	TaskListPoPage taskListDetail = task.claimFirstGtlResult(accountRequestNumber);
    	if(taskListDetail==null){
    		return false;
    	}
    	if(taskListDetail.clickApproveButton()==null){
    		ax.log("Problem with clicking Approve Button...");
    		return false;
    	}
    	return true;
	}
	public static boolean ApproveTransactionRequestsEtl(String accountRequestNumber){
		/* ETL accountOpening Approval
		 * @return Account Identifier or null
		 */
		TaskPage task = Eucr.openTaskPage();
		task = task.loadPageWithExpectedTitle(TaskPage.class, "Task List");
		new TaskPage().goToEtlTab(task);
//		new TaskPage().goToGtlTab(task);
    	if(task.searchEtlByRequestNumber(accountRequestNumber) < 0){
    		return false;
    	}
    	TaskListPoPage taskListDetail = task.claimFirstEtlResult(accountRequestNumber);
    	if(taskListDetail==null){
    		return false;
    	}
    	if(taskListDetail.clickApproveButton()==null){
    		ax.log("Problem with clicking Approve Button...");
    		return false;
    	}
    	return true;
	}
	public static String ApproveAccountOpeningRequests(String requestNumber){
		/* ETL accountOpening Approval
		 * @return Account Identifier or null
		 */
		TaskPage task = new TaskPage().loadPageWithUrl(TaskPage.class);
		new TaskPage().clickEtlTab(task);
    	if(task.searchEtlByRequestNumber(requestNumber) < 0){
    		return null;
    	}
    	TaskListPoPage taskListDetail = task.claimFirstEtlResult(requestNumber);
    	if(taskListDetail==null){
    		return null;
    	}
    	String identifier = null;
    	if(taskListDetail.clickApproveButton()!=null){
        	identifier = taskListDetail.getIdentifierFromAccoutnOpeningDetails(); 
        	if(identifier==null){
        		ax.log("Problem with getting Identifier for the account...");
        		return null;
        	}
    	}else{
    		ax.log("Problem with clicking Approve Button...");
    		return null;
    	}
    	ax.log((identifier!=null)?"Identifier is "+identifier:"Not grabbed Identifier");
    	return identifier;
	}
	public static boolean ApproveAccountOperationRequests(String requestNumber, String expectedTitle){
		return ApproveAccountOperationRequests(requestNumber, expectedTitle, "");
	}
	public static boolean ApproveAccountOperationRequests(String requestNumber, String expectedTitle, String approvaButtonLabel){
		/* 
		 * ETL account operations: f-ex: update installation details
		 * @return true/false
		 * 
		 */
		
		TaskListPoPage taskListDetail = searchingAndClaimingTask("ETL", requestNumber, expectedTitle);
		
    	if(taskListDetail==null){
    		return false;
    	}
    	
    	TaskListPoPage approve;
    	if(approvaButtonLabel.isEmpty()){
    		approve = taskListDetail.clickApproveButton(expectedTitle);
    	}else{
    		approve = taskListDetail.clickApproveButton(expectedTitle, approvaButtonLabel);
    	}
    	
    	if(approve==null){
    		ax.log("Problem with clicking Approve Button...");
    		return false;
    	}
    	return true;
	}	
	
	
	public static TaskListPoPage searchingAndClaimingTask(String preferredTab, String requestNumber, String expectedTitle){
		TaskPage task = new TaskPage().loadPageWithUrl(TaskPage.class);
		switch (preferredTab) {
		case "ETL":
				if(task.searchEtlByRequestNumber(requestNumber) > 0){
					return task.claimFirstEtlResult(requestNumber, expectedTitle);
				}else if(task.searchGtlByRequestNumber(requestNumber) > 0){
					return task.claimFirstGtlResult(requestNumber, expectedTitle);
				}
				break;
		case "GTL":
				if(task.searchGtlByRequestNumber(requestNumber) > 0){
					return task.claimFirstGtlResult(requestNumber, expectedTitle);
				}else if(task.searchEtlByRequestNumber(requestNumber) > 0){
					return task.claimFirstEtlResult(requestNumber, expectedTitle);
				}
				break;
		default:
				if(task.searchEtlByRequestNumber(requestNumber) > 0){
					return task.claimFirstEtlResult(requestNumber, expectedTitle);
				}else if(task.searchGtlByRequestNumber(requestNumber) > 0){
					return task.claimFirstGtlResult(requestNumber, expectedTitle);
				}
				break;
		}
		return null;
	}
	
	
	
	
	public static boolean CheckHistoryRequestOutcome(String requestNumber){
		TaskPage task = new TaskPage().loadPageWithUrl(TaskPage.class);
		new TaskPage().clickHistTab(task);
    	if(task.searchHistByRequestNumber(requestNumber) < 0){ // trying 30 times
    		return false;
    	}
    	String outcomeFromFirstRow = task.getOutcomeFromHistoricFristRow();
		i = 0;
    	while(outcomeFromFirstRow==null){
			// waiting to get outcome
			ax.wait(1);
			task.searchHistByRequestNumber(requestNumber);
			outcomeFromFirstRow = task.getOutcomeFromHistoricFristRow();
			if(i > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
				return ax.returnWhenFalse("Problem getting outcome in History First Row");
			}
			i++;
		}
    	i = 0;
    	while(!outcomeFromFirstRow.equals("Approved")){
    		ax.wait(1);
    		task.searchHistByRequestNumber(requestNumber);
    		outcomeFromFirstRow = task.getOutcomeFromHistoricFristRow();
    		
//    		ax.log("Outcome in history task for this request ["+requestNumber+"] is so far " + outcomeFromFirstRow);
    		if(i > ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
				return ax.returnWhenFalse("Problem waiting for \"Approved\" in outcome in History First Row");
			}
    		i++;
    	}
    	return true; // Assume the outcoome is Approved
	}
	
	public static String CreateAccount(String loginPair, String accountType, String yfe){
		EucrHomePage eucr		= null;
		eucr 					= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	String accountRequestNumber 	= Eucr.CreateAccountWithArs(eucr, accountType, accountType+"-AUTO-"+ax.GET_NOW_TIME(), yfe); 
    	eucr = ax.LoginNA2();
    	String identifier 				= Eucr.ApproveAccountOpeningRequests(accountRequestNumber);
    	boolean historyOutcomeApproved 	= CheckHistoryRequestOutcome(accountRequestNumber);
    	
    	eucr 					= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	AccountsPage accounts			= (AccountsPage) eucr.openEucrMenu("Accounts"); 
    	if(historyOutcomeApproved && accounts.SearchAccountByIdentifier(identifier) > 0){
    		return identifier;	
    	} else{
    		return null;
    	}
	}
	
	public static boolean UpdateAccountInstallationData(String loginPair/*(for examlple: "NA1:NA2")*/, String AccountIdentifier){
		EucrHomePage eucr		= null;
		eucr 					= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	String updateInstallationIdentifier = Eucr.InstallationUpdateProposal(AccountIdentifier);
    	System.out.println(updateInstallationIdentifier);
    	eucr 					= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean approval 					= Eucr.ApproveAccountOperationRequests(updateInstallationIdentifier, "Installation Information Update");
    	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(updateInstallationIdentifier);
    	return (approval && historyOutcomeApproved);
	}
	
	public static boolean SuspendUserByUrid(String accountIdentifier, String Urid){
    	EucrHomePage eucr 								 = ax.LoginNA1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "ARS");
        	AccountAuthoriseRepresentativePoPage suspend = accountDetailsPoPage.suspendAr(getDriver(), Urid);
    	    suspend 								 	= suspend.clickSuspenduserConfirmationButton();
    	    if(suspend!=null){
    	    	return true;
    	    }
    	    return false;
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing Suspending of the AR ["+Urid+"] in Account "+accountIdentifier);
			return false;
		}
	}
	public static boolean SuspendAccount(String accountIdentifier){
    	EucrHomePage eucr 								= ax.LoginNA1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "MAIN");
        	AccountSuspendPage suspend  				 = accountDetailsPoPage.suspendAccount();
        	suspend 									 = suspend.ConfirmSuspendAccount();
    	    if(suspend.cofirmGreenBoxDisplayed()){
    	    	return true;	
    	    }
    	    return false;
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing suspend of Account "+accountIdentifier);
			return false;
		}
	}	
	public static boolean RestoreAccount(String accountIdentifier){
    	EucrHomePage eucr 								= ax.LoginNA1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "MAIN");
        	AccountSuspendPage restore  				 = accountDetailsPoPage.restoreAccount();
        	restore 									 = restore.ConfirmRestoreAccount();
    	    if(restore.cofirmGreenBoxDisplayed()){
    	    	return true;	
    	    }
    	    return false;
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing restore of Account "+accountIdentifier);
			return false;
		}
	}	
	public static boolean UpdateAccountHolder(String loginPair/*(for example: "NA1:NA2")*/, String accountIdentifier){
		EucrHomePage eucr 								= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "MAIN");
        	AccountRequestHolderInformation updateAccHolder 		= accountDetailsPoPage.clickAccountHolderUpdateButton(); 
        	AccountBusinessDetails update 				 = updateAccHolder.provideAccountHolderDeatilsForUpdate();
    	    if(update.confirmAccountHolderUpdateGreenBox()){
    	    	requestNumber = update.getRequestForAccountHolderUpdate();	
    	    }else{
    	    	return ax.returnWhenFalse(getDriver(), "Problem with proposing account holder update for " + accountIdentifier);	
    	    }
    	    eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
        	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Account Holder Update - Approval");
        	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
        	return (approval && historyOutcomeApproved);  	    
    	    
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing Account Holder Update for "+accountIdentifier);
			return false;
		}
	}	
	public static boolean UpdateContactPerson(String loginPair/*(for example: "NA1:NA2")*/, String accountIdentifier){
		EucrHomePage eucr 								= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "CONT");
        	AccountContactPage updateContactPage		 = accountDetailsPoPage.clickContactPerfonInformationrUpdateButton();
        	String random 								 = ax.randomString();
        	updateContactPage 							 = updateContactPage.contactPersonDialogUpdate(random);
    	    if(!updateContactPage.greenBoxDisplayed()){
    	    	return ax.returnWhenFalse(getDriver(), "Problem with proposing contact person update for " + accountIdentifier);	
    	    }
    	    account 						 			 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	details 				 					 = account.clickFullAccountNumber1x1();  
        	accountDetailsPoPage 	 					 = details.openTab(details, "CONT");
        	String contactPersonInformationText 		 = accountDetailsPoPage.getContactPersonInformationText(getDriver());
        	i=0;
        	while (contactPersonInformationText == null){
        		ax.wait(1);
        		i++;
        		account 						 			 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
            	account 									 = account.searchAccountById(accountIdentifier); 
            	details 				 					 = account.clickFullAccountNumber1x1();  
            	accountDetailsPoPage 	 					 = details.openTab(details, "CONT");
        		contactPersonInformationText 		 = accountDetailsPoPage.getContactPersonInformationText(getDriver());
        		if(i > ProjectDataVariables.TEST_RETRIAL_FACTOR_40) return ax.returnWhenFalse(getDriver(), "Problem with getting info from Accounnt Contact Person Information after updating data");
        	}
        	i=0;
        	while(!contactPersonInformationText.contains(random) && contactPersonInformationText.contains("1111")){
        		ax.wait(1);
        		i++;
        		account 						 			 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
            	account 									 = account.searchAccountById(accountIdentifier); 
            	details 				 					 = account.clickFullAccountNumber1x1();  
            	accountDetailsPoPage 	 					 = details.openTab(details, "CONT");
        		contactPersonInformationText 		 = accountDetailsPoPage.getContactPersonInformationText(getDriver());
        		if(i > ProjectDataVariables.TEST_RETRIAL_FACTOR_40) return ax.returnWhenFalse(getDriver(), "Problem with getting expected info from Accounnt Contact Person Information after updating data");
        	}
    	    
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing Account Holder Update for "+accountIdentifier);
			return false;
		}
    	return true;
	}	
	
	
	
	
	public static boolean UpdateArByUrid(String loginPair/*(for examlple: "NA1:NA2")*/, String accountIdentifier, String Urid){
		String requestNumber = null;
		// Proposal - requestIs created only when there were updated fields with star (*)
    	EucrHomePage eucr 								= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "ARS");
        	AuthorisedRepresentativeUpdatePoPage update  = accountDetailsPoPage.updateAr(getDriver(), Urid);
        	requestNumber		 		 				 = update.dialogUpdateAuthoriseRepresentativeDataSubmitAndGetRequestNumber();
    	    if(requestNumber==null){
    	    	return false;	
    	    }
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing Update of the AR ["+Urid+"] in Account "+accountIdentifier);
			return false;
		}
    	// Approval - 
    	eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Business Details Update - Approval");
    	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
    	return (approval && historyOutcomeApproved);
	}
	public static boolean UpdateAarByUrid(String loginPair/*(for examlple: "NA1:NA2")*/, String accountIdentifier, String Urid){
		String requestNumber = null;
		// Proposal - requestIs created only when there were updated fields with star (*)
    	EucrHomePage eucr 								= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "AARS");
        	AuthorisedRepresentativeUpdatePoPage update  = accountDetailsPoPage.updateAr(getDriver(), Urid);
        	requestNumber		 		 				 = update.dialogUpdateAuthoriseRepresentativeDataSubmitAndGetRequestNumber();
    	    if(requestNumber==null){
    	    	return false;	
    	    }
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing Update of the AR ["+Urid+"] in Account "+accountIdentifier);
			return false;
		}
    	// Approval - 
    	eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Business Details Update - Approval");
    	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
    	return (approval && historyOutcomeApproved);
	}	
	public static boolean RestoreUserByUrid(String accountIdentifier, String Urid){
    	EucrHomePage eucr 								 = ax.LoginNA1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	AccountDetailsPoPage accountDetailsPoPage 	 = details.openTab(details, "ARS");
        	AccountAuthoriseRepresentativePoPage restore = accountDetailsPoPage.restoreAr(getDriver(), Urid);
        	restore 								 	= restore.clickRestoringConfirmationButton();
    	    if(restore!=null){
    	    	return true;
    	    }
    	    return false;
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing Restoring of the AR ["+Urid+"] in Account "+accountIdentifier);
			return false;
		}
	}
	public static boolean ReplaceArOnAccount(String loginPair, String arOrAarSwitcher, String urid_old, String urid_new, String accountIdentifier){
		// replace: AR2 -> AR4
		// replace: AAR1 -> AR7
		
		// adding:  AR6
		// removing: AR6
		AccountDetailsPoPage accountDetailsPoPage;
		EucrHomePage eucr 								= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	
        	if(arOrAarSwitcher.equals("AAR")){
        		accountDetailsPoPage 	 = details.openTab(details, "AARS");
        	}else{
        		accountDetailsPoPage 	 = details.openTab(details, "ARS");	
        	}
        	
        	AccountAuthoriseRepresentativePoPage restore = accountDetailsPoPage.replaceAr(getDriver(), urid_old);
        	requestNumber							 	 = restore.DoReplaceForNewAr(urid_new);
    	    if(requestNumber==null){
    	    	return false;
    	    }
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing replace of the "+arOrAarSwitcher+" ["+urid_old+"] into "+arOrAarSwitcher+" ["+urid_new+"] in Account "+accountIdentifier);
			return false;
		}
	    eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Replace");
    	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
    	return (approval && historyOutcomeApproved);     	
	}

	public static boolean AddArOnAccount(String loginPair, String arOrAarSwitcher, String urid_new, String accountIdentifier){
//		 adding AR:  AR6
//		 adding AAR:  AR5
		// removing: AR6
		AccountDetailsPoPage accountDetailsPoPage;
		AccountAuthoriseRepresentativePoPage addAr;
		EucrHomePage eucr 								= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	try {
    		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
        	account 									 = account.searchAccountById(accountIdentifier); 
        	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
        	
        	if(arOrAarSwitcher.equals("AAR")){
        		accountDetailsPoPage 	 = details.openTab(details, "AARS");
        		addAr = accountDetailsPoPage.clickAdAarButton();
        	}else{
        		accountDetailsPoPage 	 = details.openTab(details, "ARS");
        		addAr = accountDetailsPoPage.clickAdArButton();
        	}
        	
        	requestNumber							 	 = addAr.DoAddNewAr(urid_new);
        			
    	    if(requestNumber==null){
    	    	return false;
    	    }
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing adding of the "+arOrAarSwitcher+" ["+urid_new+"] in Account "+accountIdentifier);
			return false;
		}
	    eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Authorised Representative Approval");
    	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
    	return (approval && historyOutcomeApproved);     	
	}	
	
	public static boolean RemoveArOnAccount(String loginPair, String arOrAarSwitcher, String urid_new, String accountIdentifier){
//		 adding AR:  AR6
//		 adding AAR:  AR5
		// removing: AR6
		AccountDetailsPoPage accountDetailsPoPage;
		EucrHomePage eucr 								= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
   	try {
   		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
       	account 									 = account.searchAccountById(accountIdentifier); 
       	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
       	
       	if(arOrAarSwitcher.equals("AAR")){
       		accountDetailsPoPage 	 = details.openTab(details, "AARS");
       	}else{
       		accountDetailsPoPage 	 = details.openTab(details, "ARS");	
       	}
       	
       	requestNumber							   =  accountDetailsPoPage.removeAr(getDriver(), urid_new); 
       			
   	    if(requestNumber==null){
   	    	return false;
   	    }
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing removal of the "+arOrAarSwitcher+" ["+urid_new+"] in Account "+accountIdentifier);
			return false;
		}
	    eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
   	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Authorised Representative Approval");
   	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
   	return (approval && historyOutcomeApproved);     	
	}	
	
	
	
	
	
	public static boolean UnreleaseAccount(String accountIdentifier){
		AccountDetailsPoPage accountDetailsPoPage;
		EucrHomePage eucr 								= ax.LoginNA1();
  	try {
	  		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
	      	account 									 = account.searchAccountById(accountIdentifier); 
	      	AccountDetailsPoPage details 				 = account.clickFullAccountNumber1x1();  
	      	accountDetailsPoPage 	 	= details.openTab(details, "MAIN");	
	      	accountDetailsPoPage 		= accountDetailsPoPage.clickUnreleaseAccountButton();
	      	
	  	    if(accountDetailsPoPage==null){
	  	    	return false;
	  	    }
  	    
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing unrelesing of the Account "+accountIdentifier);
			return false;
		}
	    eucr 								= ax.LoginNA2();
		AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
	   	account 									 = account.searchAccountById(accountIdentifier); 
	   	String status 								 = account.getAccountStatusFrom1x1().trim();
   	
	   	if(accountDetailsPoPage!=null && !status.contains("Transfer Pending")){
	   		return true;
	   	}else{
	   		ax.fail(getDriver(), "Problem with Checking status account after unrelesing of the account "+accountIdentifier);
			return false;
	   	}
	}	
	
	
	
	public static boolean ReleaseAccount(String ohaAohaSwitcher, String accountIdentifier){
		AccountReleasePoPage accountRelease;
		EucrHomePage eucr 								= ax.LoginNA1();
		
   	try {
   		accountRelease = (AccountReleasePoPage) eucr.openEucrMenu("Release account");
   		accountRelease 									 	= accountRelease.provideAccountInfoToRelease(ohaAohaSwitcher, accountIdentifier);
       		
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing release of the account ["+ohaAohaSwitcher+"] "+accountIdentifier);
			return false;
		}
   		// Checking status in Accounts View, should be: Transfer Pending
	   	AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
	   	account 									 = account.searchAccountById(accountIdentifier); 
	   	String status 								 = account.getAccountStatusFrom1x1().trim();
   	
	   	if(accountRelease!=null && status.contains("Transfer Pending")){
	   		return true;
	   	}else{
	   		ax.fail(getDriver(), "Problem with Checking status account after release of the account ["+ohaAohaSwitcher+"] "+accountIdentifier);
			return false;
	   	}
   	
	}     	

	public static boolean ReleaseAndClaimAccount(String ohaAohaSwitcher, String accountIdentifier){
		AccountReleasePoPage accountRelease;
		EucrHomePage eucr 								= ax.LoginNA1();
   	try {
   		accountRelease 									= (AccountReleasePoPage) eucr.openEucrMenu("Release account");
   		accountRelease 									= accountRelease.provideAccountInfoToRelease(ohaAohaSwitcher, accountIdentifier);
       		
		} catch (Exception e) {
			ax.fail(getDriver(), "Problem with performing release of the account ["+ohaAohaSwitcher+"] "+accountIdentifier);
			return false;
		}
   		// Checking status in Accounts View, should be: Transfer Pending
	   	AccountsPage account 						 = new AccountsPage().loadPageWithUrl(AccountsPage.class);
	   	account 									 = account.searchAccountById(accountIdentifier); 
	   	String status 								 = account.getAccountStatusFrom1x1().trim();
   	
	   	if(accountRelease!=null && status.contains("Transfer Pending")){
	   		// RELEASED FOR CLAIMING
	   	}else{
	   		ax.fail(getDriver(), "Problem with Checking status account after release of the account ["+ohaAohaSwitcher+"] "+accountIdentifier);
			return false;
	   	}
   	
	   	// to be continued
	   	
	   	eucr 											= ax.LoginNA1();
   		accountRelease 									= (AccountReleasePoPage) eucr.openEucrMenu("Release account");
   		accountRelease 									= accountRelease.provideAccountInfoToRelease(ohaAohaSwitcher, accountIdentifier);
	   	
	   	
	   	return false;
	   	
	   	
	} 	
	
	
//	##[   TRANSACTIONS  ]###############################################################################################################
	public static boolean TransferOfAllowances(String loginPair, String transferingIdentifier, String acquiringIdentifier, String acquiringAccountType, String value){
		EucrHomePage eucr		= null;
		eucr 					= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	ArrayList<String> reqs 	= Eucr.ProposeTransaction(eucr, transferingIdentifier, acquiringIdentifier, acquiringAccountType, value);
    	eucr 					= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean	approval		= Eucr.ApproveTransactionRequestsGtl(reqs.get(0));
    	boolean	checking		= Eucr.CheckTransactionCompleted(eucr, reqs.get(1));
    	
    	return approval && checking;
	}
	
	public static boolean SurrenderOfAllowances(String loginPair, String transferingIdentifier, String unitType /*GA|AA*/, String value){
		EucrHomePage eucr		= null;
		eucr 					= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
    	ArrayList<String> reqs 	= Eucr.ProposeSurrender(eucr, transferingIdentifier, "1");
    	eucr 					= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean	approval		= Eucr.ApproveTransactionRequestsEtl(reqs.get(0));
    	boolean	checking		= Eucr.CheckTransactionCompleted(eucr, reqs.get(1));
    	
    	return approval && checking;
	}	
		
	
	public static boolean CheckTransactionCompleted(EucrHomePage eucr, String transferIdentifier){
		TransactionsPoPage transactions = (TransactionsPoPage) eucr.openEucrMenu("Transactions");
		boolean found = transactions.checkTransactionStatusForCompleted(transactions, transferIdentifier);
		int i=0;
		System.out.println("Waiting for transaction completed");
		while(!found){
			ax.wait(1);
			System.out.print(" .");
			found = transactions.checkTransactionStatusForCompleted(transactions, transferIdentifier);
			i++;
			if(i > ProjectDataVariables.TIMEX_FX_45){
				return false;
			}
		}
		System.out.print(" done");
		return true;
	}
	
	public static ArrayList<String> ProposeTransaction(EucrHomePage eucr, String transferringAccountIdentifier, String acquiringAccountIdentifier, String acquiringAccountType, String valueToTransfer){
		AccountsPage account = new AccountsPage().loadPageWithUrl(AccountsPage.class);
    	account.searchAccountById(acquiringAccountIdentifier); 
    	if(account==null) return null;
    	String acquiringfullAccountNumber = account.getFullAccountFrom1x1();
    	account.searchAccountById(transferringAccountIdentifier);
    	AccountDetailsPoPage details = account.clickFullAccountNumber1x1();  
    	details.openTab(details, "HOLD");
    	details.clickProposeTransactionButton();
    	details.clickTransferOfAllowances();
    	details.provideAccountDetailForTranferOfAllowances(acquiringAccountType, acquiringfullAccountNumber, valueToTransfer);
    	details.confirmOnTransferConfirmation();
    	ArrayList<String> reqs = details.getTransactionRequestAndTransactionNumbers();
    	eucr = eucr.clickHomePageLink().loadPageWithExpectedTitle(EucrHomePage.class, "Home Page");
    	return reqs;
	}
	public static ArrayList<String> ProposeSurrender(EucrHomePage eucr, String transferringAccountIdentifier, String valueToSurrender){
		AccountsPage account = new AccountsPage().loadPageWithUrl(AccountsPage.class);
    	account.searchAccountById(transferringAccountIdentifier); 
    	if(account==null) return null;
    	AccountDetailsPoPage details = account.clickBalanceAccountLink();  
    	details.clickProposeTransactionButton();
    	details = details.clickProposeSurrenderLink();
    	details.provideValueForSurrender(valueToSurrender);
    	
    	details.confirmOnSurrenderConfirmation();
    	ArrayList<String> reqs = details.getTransactionRequestAndTransactionNumbers();
    	eucr = eucr.clickHomePageLink().loadPageWithExpectedTitle(EucrHomePage.class, "Home Page");
    	return reqs;
	}
	
	public static String InstallationUpdateProposal(String AccountOhaIdentifier){
		AccountsPage account = new AccountsPage().loadPageWithUrl(AccountsPage.class);
    	account = account.searchAccountById(AccountOhaIdentifier); 
    	if(account==null) return null;
    	AccountDetailsPoPage details = account.clickFullAccountNumber1x1();  
    	AccountDetailsPoPage accountDetailsPoPage = details.openTab(details, "INST");
		InstallationUpdatePoPage installationPoPage = accountDetailsPoPage.clickUpdateInstallationButton();
		return installationPoPage.dialogUpdateInstallationDataSubmitAndGetRequestNumber();
	}
	public static boolean Claim(String loginPair, String ohaAohaSwitcher, String identifier){
		EucrHomePage eucr 					= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
		
		if(!Eucr.ReleaseAccount(ohaAohaSwitcher, identifier)){
			ax.fail("RELEASING ACCOUNT "+identifier+" FOR CLAIMING PROBLEM", "problem with relesing account for further claiming");
			return false;
		}
		
		ClaimAccountPoPage claimAccount					= (ClaimAccountPoPage) eucr.openEucrMenu("Claim account");
		String requestNumber 				= claimAccount.ClaimDialog(identifier);
    	eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Account Claim Approval");
    	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
    	return (approval && historyOutcomeApproved);		
	}	
	
	public static AccountDetailsPoPage SearchAndOpenAcountMainTab(String identifier){
  		AccountsPage account 	= new AccountsPage().loadPageWithUrl(AccountsPage.class);
      	account 				= account.searchAccountById(identifier); 
      	AccountDetailsPoPage details 	= account.clickFullAccountNumber1x1();  
      	details 	 					= details.openTab(details, "MAIN");
      	return details;
	}
	
	
	public static boolean CloseAccount(String loginPair, String identifier){
		EucrHomePage eucr 					= (loginPair.split(":")[0].contains("NA"))?ax.LoginNA1():ax.LoginAR1();
		String requestNumber 			= null;
		AccountDetailsPoPage details;
		AccountClosurePoPage close;
	  	try {
		  		AccountsPage account 	= new AccountsPage().loadPageWithUrl(AccountsPage.class);
		      	account 				= account.searchAccountById(identifier); 
		      	 details 				= account.clickFullAccountNumber1x1();  
		      	details 	 			= details.openTab(details, "MAIN");	
		      	close 					= details.clickCloseAccountButton();
		      	requestNumber 			= close.CloseAccount(close, identifier); 
	  	} catch (Exception e) {
	  		return ax.returnWhenFalse(getDriver(), "Problem with proposing Account Closure for " + identifier);
	  	}		      	
    	eucr 								= (loginPair.split(":")[1].contains("NA"))?ax.LoginNA2():ax.LoginAAR1();
    	boolean approval 					= Eucr.ApproveAccountOperationRequests(requestNumber, "Request Details", "Force Closure");
    	boolean historyOutcomeApproved 		= Eucr.CheckHistoryRequestOutcome(requestNumber);
    	return (approval && historyOutcomeApproved);		
	}	
	
	
}
