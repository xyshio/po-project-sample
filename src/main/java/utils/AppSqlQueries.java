package eu.ets.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.omg.CORBA.NVList;

import clima.start.ProjectDataVariables;
import eu.esd.eucr.globals.GLOBALS;
import eu.ets.eucr.compliance.VerifierOperations;
import eu.ets.eucr.lang.LANG_EN_APPLICATION_TITLES;
import eu.ets.eucr.login.LoginToEUCR;
import eu.ets.eucr.utils.AppGetter;
import eu.ets.eucr.utils.DateOperations;
import eu.ets.eucr.utils.SeleniumWaiter;
import eu.ets.eucr.utils.SeleniumWebUtils;
import eu.ets.eucr.utils.ax;
import eu.ets.test.utils.TwilioTest;

public class AppSqlQueries {
	/*
	 * select count(*) from ACCOUNT where account_status like '%BLOCKED%' and registry_code = 'GR'
	 * 
	 * 
	 */
	
	public static final String CONN_EUCR_ENV = ProjectDataVariables.EUCR_ENV;
	static ResultSet rs;

	public static Connection getConnectionItem() throws SQLException{
		Connection con;
		switch (CONN_EUCR_ENV) {
		case "TEST": 			con = DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVT", 	AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV)); 			break;
//		case "TEST2": 			con = DriverManager.getConnection("jdbc:oracle:thin:@//olrdev6.cc.cec.eu.int:1597/CIT2ENVT_TAF.cc.cec.eu.int", AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV)); 			break;
		case "TEST2": 			con = DriverManager.getConnection("jdbc:oracle:thin:@//olrdev9.cc.cec.eu.int:1597/EUCR_CLIMA_01_T_TAF.cc.cec.eu.int", AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV)); 			break;
		case "DEV": 			con = DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVD", 	AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
		case "UT": 				con = DriverManager.getConnection("jdbc:oracle:thin:@oraext1.cc.cec.eu.int:1598:CIT2ENVB", 	AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
		case "LA": 				con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.3.1:1521:EUCR", 				AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
		case "QA2":				con = DriverManager.getConnection("jdbc:oracle:thin:@d02di1429514env:1521:xe", 				AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
		default: 				con = DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVT", 	AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
		}
		return con;
	}
	public static Connection getConnection_AS_RO() throws SQLException{
		Connection con = null;
		switch (CONN_EUCR_ENV) {
		case "TEST": 			con = DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVT", 	"EUTL_RO_PUBLIC",	"ae3+2j6k"); 			break;
//		case "DEV": 			con = DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVD", 	AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
//		case "UT": 				con = DriverManager.getConnection("jdbc:oracle:thin:@oraext1.cc.cec.eu.int:1598:CIT2ENVB", 	AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
//		case "QA2":				con = DriverManager.getConnection("jdbc:oracle:thin:@d02di1429514env:1521:xe", 				AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
//		default: 				con = DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVT", 	AppGetter.GET_DB_LOGIN(CONN_EUCR_ENV),	AppGetter.GET_DB_PASS(CONN_EUCR_ENV));			break;
		}
		return con;
	}
	public static Connection getEUTLConnectionItem(){
			switch (CONN_EUCR_ENV) {
			case "TEST":
				try {
					return DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVT",
							AppGetter.GET_EUTL_DB_LOGIN(CONN_EUCR_ENV),
							AppGetter.GET_EUTL_DB_PASS(CONN_EUCR_ENV));
				} catch (SQLException | InterruptedException e1) {
					e1.printStackTrace();
					return null;
				}
			case "TEST2":
				try {
					      //return DriverManager.getConnection("jdbc:oracle:thin:@//olrdev6.cc.cec.eu.int:1597/CIT2ENVT_TAF.cc.cec.eu.int",
							return DriverManager.getConnection("jdbc:oracle:thin:@//olrdev9.cc.cec.eu.int:1597/EUTL_CLIMA_01_T_TAF.cc.cec.eu.int",
							AppGetter.GET_EUTL_DB_LOGIN(CONN_EUCR_ENV),
							AppGetter.GET_EUTL_DB_PASS(CONN_EUCR_ENV));
				} catch (SQLException | InterruptedException e1) {
					e1.printStackTrace();
					return null;
				}
			case "DEV":
				try {
					return DriverManager.getConnection("jdbc:oracle:thin:@devora6.cc.cec.eu.int:1597:CITLENVD",
							AppGetter.GET_EUTL_DB_LOGIN(CONN_EUCR_ENV),
							AppGetter.GET_EUTL_DB_PASS(CONN_EUCR_ENV));
				} catch (SQLException | InterruptedException e) {
					e.printStackTrace();
					return null;
				}
			case "LA":
				try {
					return DriverManager.getConnection("jdbc:oracle:thin:@192.168.3.1:1521:EUCR",
							AppGetter.GET_EUTL_DB_LOGIN(CONN_EUCR_ENV),
							AppGetter.GET_EUTL_DB_PASS(CONN_EUCR_ENV));
				} catch (SQLException | InterruptedException e1) {
					e1.printStackTrace();
					return null;
				}
				
			}
		return null;
	}
	
	
	public static ResultSet sqlRunQueryForResultset(Connection con, String sqlQuery) throws SQLException{
	    Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(sqlQuery);
	    return rs;
	}
	
	public static List<String> getBlockedAccountsId(String column2return, String registry) throws SQLException, InterruptedException{
		ResultSet r = sqlRunQueryForResultset(getConnectionItem(), "select * from ACCOUNT where account_status like '%BLOCKED%' and registry_code = '"+registry+"'");
		
		List<String> list = new ArrayList<String>();
		while (r.next()){
		    list.add(r.getString(column2return));
		}
		return list;
	}
	public static ArrayList<String> ESD_FIRST_LAST_NAME_BY_URID(String URID){
		ResultSet r = null;
		
		try {
			r = sqlRunQueryForResultset(getConnectionItem(), "select * from USER_DETAILS inner join USERS on USER_DETAILS.USER_DETAILS_id = USERS.USER_DETAILS_ID where USERS.URID = '"+URID+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (r.next()){
			    list.add(r.getString("FIRST_NAME"));
			    list.add(r.getString("LAST_NAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	
	
	
	public static ArrayList<String> GET_HOLD_ACC_IDENTIFIERS_FOR_TEST(String RegistryCode, String ACCTYPE/*[OHA|AOHA]*/, int MAXRESULT) throws SQLException, InterruptedException{
		
		String thisAccountType = (ACCTYPE.equals("OHA")) ? "OPERATOR_HOLDING" : "AIRCRAFT_OPERATOR";
		String sqlQuery = "select identifier from ACCOUNT " 
		+ "WHERE "
		+ "account_status like '%OPEN%' and "
		+ "status = 'ACTIVE' and "
		+ "registry_code = '"+RegistryCode+"' AND "
		+ "EU_ACCOUNT_TYPE = '"+thisAccountType+"' AND "
		+ "ROWNUM <= "+MAXRESULT+" "
		+ "ORDER BY IDENTIFIER";
				ResultSet r = sqlRunQueryForResultset(getConnectionItem(), sqlQuery);
				
				ArrayList<String> list = new ArrayList<String>();
				while (r.next()){
				    list.add(r.getString("IDENTIFIER"));
				}
				return list;				
	}			
			
			
	
	public static List<String> getSqlRecordSetIntoList(String column2return, String sqlQuery) throws SQLException, InterruptedException{
		ResultSet r = sqlRunQueryForResultset(getConnectionItem(), sqlQuery);
		Thread.sleep(1000);
		List<String> list = new ArrayList<String>();
		while (r.next()){
		    list.add(r.getString(column2return));
		}
		return list;
	}	
	
	public static String getRequestNumberByTransactionIdentifier(String transactionIdentifier){
		int max = -1;
		String sqlQuery = "select REQUEST_ID FROM TRANSACTION_IDENTIFIERS WHERE TRANSACTION_IDENTIFIER ='"+transactionIdentifier+"'";
    	Connection connection;
		try {
			connection = getConnectionItem();
		       Statement statement = connection.createStatement();
					ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					max = resultset.getInt(1);  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
				String RequestNumber = Integer.toString(max);	
				return RequestNumber; 
	}
	public static String getTransactionIdentifierByRequestNumber(String RequestNumber){
		String max = null;
		String sqlQuery = "select TRANSACTION_IDENTIFIER FROM TRANSACTION_IDENTIFIERS WHERE REQUEST_ID ='"+RequestNumber+"'";
    	Connection connection;
		try {
				connection = getConnectionItem();
				Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				max = resultset.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//				String TransactionIdentifier = Integer.toString(max);	
				return max; 
	}
	public static String getTransactionStatusByTransactionIdentifier(String TransactionIdentifier){
		String max = null;
		String sqlQuery = "select STATUS_CODE FROM TRANSACTIONS WHERE TRANSACTION_IDENTIFIER ='"+TransactionIdentifier+"'";
    	Connection connection;
		try {
				connection = getConnectionItem();
				Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				max = resultset.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//				String TransactionIdentifier = Integer.toString(max);	
				return max; 
	}
	
	public static boolean checkIfRequestIsForTranaction(String RequestNumber){
		
		String sqlQueryCount = "select count(*) from TRANSACTIONS WHERE REQUEST_ID = '"+RequestNumber+"'";
		
		int count = 0;
		try {
			Connection connection = getConnectionItem();
			Statement statementCount = connection.createStatement();
			Statement statement 		= connection.createStatement();
			ResultSet resultset_count = statementCount.executeQuery(sqlQueryCount);  
			   while (resultset_count.next()){
				   count = resultset_count.getInt(1);
				   }
			   if (count==0){
			       return false;  
					
			   }else{
				   return true;
			   }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public static String GET_ENROLLMENT_KEY_BY_URID(String URID){
		String max = null;
		if(ax.IS_NULL_OR_EMPTY_STRING(URID)){ax.t_error("URID IS EMPTY TO GRAB ENROLLMENT KEY");return null;}
		String sqlQuery = "select ENROLMENT_KEY from USERS WHERE URID ='"+URID+"'";
    	Connection connection;
		try {
				connection = getConnectionItem();
				Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				max = resultset.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				return max; 
	}

	public static String GET_PPSR_ACCOUNT_IDENTIFIER_BY_REG(String Registry){
		String max = null;
		if(ax.IS_NULL_OR_EMPTY_STRING(Registry)){ax.t_error("REGISTRY IS EMPTY TO GRAB PPSR ACCOUNT IDENTIFIER KEY");return null;}
		String sqlQuery = "select IDENTIFIER from ACCOUNT WHERE KYOTO_ACCOUNT_TYPE='PPSR_ACCOUNT' AND ACCOUNT_STATUS='OPEN' AND REGISTRY_CODE='"+Registry+"'";
    	Connection connection;
		try {
				connection = getConnectionItem();
				Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				max = resultset.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				return max; 
	}
	
	
	public static String get_ESD_TransactionIdentifierByRequestNumber(String RequestNumber){
		String max = null;
		String sqlQuery = "select TRANSACTION_IDENTIFIER FROM ESD_ENTITLEMENT_TRX_REQUEST WHERE REQUEST_ID ='"+RequestNumber+"'";
    	Connection connection;
		try {
				connection = getConnectionItem();
				Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				max = resultset.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
//				String TransactionIdentifier = Integer.toString(max);	
				return max; 
	}

	public static String getLastRequestNumberFromTransaction_RequestTable(){
//usage:		TestRunApplicationSQLQueries.getLastRequestNumberFromTransaction_RequestTable()
		System.out.println("Technical brake [3 sec..] to be sure if we grab from DB last transaction nr.");
		
		ax.wait(3);
		String todayDate = DateOperations.date_getDateInFormat_422();
		String sqlQuery = "select max(REQUEST_ID) from transaction_request where DATeTIME > TO_DATE('"+todayDate+"', 'YYYY-MM-DD')";
		
    	Connection connection;
    	Statement statement;
    	ResultSet resultset;
    	int max = 0;
		try {
			connection = getConnectionItem();
			statement = connection.createStatement();
			resultset = statement.executeQuery(sqlQuery);
			resultset.next();
			max = resultset.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
  
				String RequestNumber = Integer.toString(max);	
				System.out.println("GRABBED-SQL-REQUEST:"+RequestNumber);
				return RequestNumber; 
	}
	public static String get_LAST_REQUEST_NUMBER_FROM_TALS_ADDING(String REQUESTER_URID){
				String RequestNumber = null;
				System.out.println("Technical brake [3 sec..] to be sure if we grab from DB last TAL ADDING request for "+ REQUESTER_URID);
				ax.sleep(3);
				String todayDate = DateOperations.date_getDateInFormat_422();
				String sqlQuery = "select max(REQUEST_ID) from ADD_TRUSTED_ACCOUNT_REQUEST where DATETIME > TO_DATE('"+todayDate+"', 'YYYY-MM-DD') and REQUESTER_URID = '"+REQUESTER_URID+"'";
		    	int max;
				try {
					Connection connection = getConnectionItem();
					   Statement statement = connection.createStatement();
							ResultSet resultset = statement.executeQuery(sqlQuery);  
							resultset.next();  
							max = resultset.getInt(1);
							RequestNumber = Integer.toString(max);
				} catch (SQLException e) {
							e.printStackTrace();}  
				System.out.println("GRABBED-SQL-REQUEST:"+RequestNumber);
						return RequestNumber;}
	public static String get_LAST_REQUEST_NUMBER_FROM_TALS_DELETING(String REQUESTER_URID){
		System.out.println("Technical brake [3 sec..] to be sure if we grab from DB last TAL DELETING request for "+ REQUESTER_URID);
		
		ax.sleep(3);
		String todayDate = DateOperations.date_getDateInFormat_422();
		String sqlQuery = "select max(REQUEST_ID) from DELETE_TRUSTED_ACCOUNT_REQUEST where DATETIME > TO_DATE('"+todayDate+"', 'YYYY-MM-DD') and REQUESTER_URID = '"+REQUESTER_URID+"'";
    	
		try {
			Connection connection = getConnectionItem();
		       Statement statement = connection.createStatement();
					ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					int max = resultset.getInt(1);  
					String RequestNumber = Integer.toString(max);
					System.out.println("GRABBED-SQL-REQUEST:"+RequestNumber);
					return RequestNumber; 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String get_LAST_REQUEST_NUMBER_FROM_TRANSACTION(String REQUESTER_URID){
		String RequestNumber = null;
		System.out.println("Technical brake [3 sec..] to be sure if we grab from DB last TAL ADDING request for "+ REQUESTER_URID);
		ax.sleep(3);
		String todayDate = DateOperations.date_getDateInFormat_422();
		String sqlQuery = "select max(REQUEST_ID) from TRANSACTION_REQUEST where DATETIME > TO_DATE('"+todayDate+"', 'YYYY-MM-DD') and REQUESTER_URID = '"+REQUESTER_URID+"'";
    	int max;
		try {
			Connection connection = getConnectionItem();
			   Statement statement = connection.createStatement();
					ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					max = resultset.getInt(1);
					RequestNumber = Integer.toString(max);
		} catch (SQLException e) {
					e.printStackTrace();} 
		System.out.println("GRABBED-SQL-REQUEST:"+RequestNumber);
				return RequestNumber;}
	
	public static String getLastRequestNumberFrom_ALLOCATION_SETTINGS_REQUEST_Table() throws SQLException, InterruptedException{
		//usage:		TestRunApplicationSQLQueries.getLastRequestNumberFromTransaction_RequestTable()
				System.out.println("Technical brake [3 sec..] to be sure if we grab from DB::ALLOCATION_SETTINGS_REQUEST last transaction nr.");
				Thread.sleep(3000);
				String todayDate = DateOperations.date_getDateInFormat_422();
				String sqlQuery = "select max(REQUEST_ID) from ALLOCATION_SETTINGS_REQUEST where DATeTIME > TO_DATE('"+todayDate+"', 'YYYY-MM-DD')";
		    	Connection connection = getConnectionItem();
			       Statement statement = connection.createStatement();
						ResultSet resultset = statement.executeQuery(sqlQuery);  
						resultset.next();  
						int max = resultset.getInt(1);  
						String RequestNumber = Integer.toString(max);
						System.out.println("GRABBED-SQL-REQUEST:"+RequestNumber);
						return RequestNumber; 
			}
	
	public static String getLast_TRANSACTION_ID_AFTER_QUARZ_FOR_ALLOCATION(String GET_DATETIME_STAMP_NOW){
		return getLast_TRANSACTION_ID_AFTER_QUARZ_FOR_ALLOCATION(GET_DATETIME_STAMP_NOW, 0);	}
	public static String getLast_TRANSACTION_ID_AFTER_QUARZ_FOR_ALLOCATION(String GET_DATETIME_STAMP_NOW, int Trial){
				System.out.println("Technical brake [3 sec..] to be sure if we grab from DB::ALLOCATION_SETTINGS_REQUEST last transaction nr.");
				ax.wait(3);
				String sqlQuery = "select TRANSACTION_IDENTIFIER from transactions where ACQ_ADM_REGISTRY_CODE = '"+ProjectDataVariables.EUCR_REG+"' AND STATUS_CODE='COMPLETED' AND start_date_time > TO_DATE('"+GET_DATETIME_STAMP_NOW+"', 'YYYY-MM-DD HH24-MI-SS')";
//				System.out.println(sqlQuery);
		    	Connection connection;
		    	Statement statement;
		    	ResultSet resultset;
		    	String r = null;
				try {
					connection = getConnectionItem();
				    statement 	= connection.createStatement();
					resultset 	= statement.executeQuery(sqlQuery);
					resultset.next();
					r = resultset.getString(1);
					if(ax.IS_NULL_OR_EMPTY_STRING(r)){
						System.out.println("["+Trial+"] Looping Query for getLast_TRANSACTION_ID_AFTER_QUARZ_FOR_ALLOCATION");ax.sleep(3);
						if(Trial > ProjectDataVariables.TEST_RETRIAL_FACTOR_10){return null;}
						return getLast_TRANSACTION_ID_AFTER_QUARZ_FOR_ALLOCATION(GET_DATETIME_STAMP_NOW, Trial++);
						
					}else{
						System.out.println("GRABBED-SQL-TRANSACTION_ID:"+r);
						return r;	
					}
				} catch (SQLException e) {
					e.printStackTrace(); return null;	}
			}	
	
	public static void remove_ALL_VIEW_LOCKS(){
		String sqlQuery = "delete from UI_VIEW_LOCK";
    	Connection connection;
		try {
			connection = getConnectionItem();
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public static boolean USER_MGT_DOWNGRADE_SDA_TO_AR(String USER_URID){
				try {
					String sqlQuery2 = "delete accesses where role_id = (select role_id from roles where role_name = 'SERVICE_DESK_AGENT' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+USER_URID+"')) and profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+USER_URID+"')";
					String sqlQuery3 = "COMMIT";
			    	Connection connection = null;
					connection = getConnectionItem();
				    Statement statement = connection.createStatement();
					statement.executeUpdate(sqlQuery2);		
					statement.executeUpdate(sqlQuery3);	
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();return false;
				}
	}
	
	public static String getInstallationNumberByAccountIdentifier(String AccountIdentifier){
		String sqlQuery = "select identifier from verified_entity where verified_entity_id = (select verified_entity_id from ACCOUNT where identifier = '"+AccountIdentifier+"')";
    	Connection connection;
    	int max = -1;
		try {
			connection = getConnectionItem();
		    Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(sqlQuery);  
			resultset.next();  
			max = resultset.getInt(1);  
		} catch (SQLException e) {
			System.out.println("Problem with finding installation number for AccountIdentifier");
//			e.printStackTrace();
			return null;
		}
				String RequestNumber = Integer.toString(max);	
				return RequestNumber; 
	}
	public static String getLoginNameBy_URID(String URID){
		String sqlQuery = "Select LOGIN.ECAS_USERNAME from USERS, USER_DETAILS, LOGIN  WHERE USERS.user_details_id=user_DETAILS.user_details_id and LOGIN.LOGIN_ID=USERS.LOGIN_ID and URID = '"+URID+"'";
		try {
			Connection connection = getConnectionItem();
		       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				String RequestNumber = resultset.getString(1);  
				return RequestNumber; 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getURID_ByLoginName(String LoginName){
		return getURID_ByLoginName(LoginName, ProjectDataVariables.EUCR_REG);
	}
	
	public static String getURID_ByLoginName(String LoginName, String URID_MS_FACTOR){
		String LoginName1 = LoginName.replaceAll("\\s+","");
//		LoginName.replaceAll("\\s+","");
		String sqlQuery = "Select users.URID  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName1+"' and URID like '"+URID_MS_FACTOR+"%'";
		String sqlQueryCount = "Select count(*)  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName1+"' and URID like '"+URID_MS_FACTOR+"%'";
		int count = 0;
    	Connection connection;
    	Statement statement = null;
    	Statement statementCount = null;
    	String URID = null;
			try {
				connection 		= getConnectionItem();
			    statement 		= connection.createStatement();
			    statementCount 	= connection.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	       ResultSet resultset_count = null;
	       ResultSet resultset = null;
		try {
			resultset_count = statementCount.executeQuery(sqlQueryCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       try {
			while (resultset_count.next()){
				   count = resultset_count.getInt(1);
				   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	       if (count>0){
		       try {
				resultset = statement.executeQuery(sqlQuery);
				resultset.next();  
				URID = resultset.getString(1);
				
		       } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
				
	       }else{
	    	   return "";
	       }
		return URID;	       
	}

	
	public static String getANYURID_ByLoginName(String LoginName){ // USERS WHICH ARE NOT ENROLLED AND NOT UNENROLLED
		return getANYURID_ByLoginName(LoginName, ProjectDataVariables.EUCR_REG);
	}
	
	public static String getANYURID_ByLoginName(String LoginName, String URID_MS_FACTOR){
		String LoginName1 = LoginName.replaceAll("\\s+","");
//		LoginName.replaceAll("\\s+","");
		String sqlQuery = "Select users.URID  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE NOT IN ('UNENROLLED','UNENROLLEMENT_PENDING') AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName1+"' and URID like '"+URID_MS_FACTOR+"%'";
		String sqlQueryCount = "Select count(*)  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE NOT IN ('UNENROLLED','UNENROLLEMENT_PENDING') AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName1+"' and URID like '"+URID_MS_FACTOR+"%'";
		int count = 0;
    	Connection connection;
    	Statement statement = null;
    	Statement statementCount = null;
    	String URID = null;
			try {
				connection 		= getConnectionItem();
			    statement 		= connection.createStatement();
			    statementCount 	= connection.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	       ResultSet resultset_count = null;
	       ResultSet resultset = null;
		try {
			resultset_count = statementCount.executeQuery(sqlQueryCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       try {
			while (resultset_count.next()){
				   count = resultset_count.getInt(1);
				   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	       if (count>0){
		       try {
				resultset = statement.executeQuery(sqlQuery);
				resultset.next();  
				URID = resultset.getString(1);
				
		       } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
				
	       }else{
	    	   return "";
	       }
		return URID;	       
	}	
	
	
	
	public static String getMOBILE_NR_ByLoginName(String LoginName){
		return getMOBILE_NR_ByLoginName(LoginName, "");
	}
	
	public static String getMOBILE_NR_ByLoginName(String LoginName, String EUCR_REG){
		if(LoginName.contains("esd")||LoginName.contains("ESD")){
			EUCR_REG="ED";
		}
		String LoginName1 = LoginName.replaceAll("\\s+","");
		String REGS = (EUCR_REG.equals(""))?ProjectDataVariables.EUCR_REG:EUCR_REG;
		String sqlQuery = "Select USER_DETAILS.mobile_phone_number  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName1+"' and URID like '"+REGS+"%'";
		String sqlQueryCount = "Select count(*)  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName1+"' and URID like '"+REGS+"%'";
		int count = 0;
    	Connection connection;
    	Statement statement;
    	Statement statementCount;
    	
		try {
			connection = getConnectionItem();
			statement 		= connection.createStatement();
			statementCount = connection.createStatement();
	       ResultSet resultset_count = statementCount.executeQuery(sqlQueryCount);  
	       while (resultset_count.next()){
	    	   count = resultset_count.getInt(1);
	    	   }		

	       if (count>0){
		       ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				return resultset.getString(1);
	       }else{
	    	   return "";
	       }
	       
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); return "";
		}	       
	       
	}	
	public static String getMOBILE_NR_BY_URID(String URID){
		
		String sqlQuery = "Select USER_DETAILS.mobile_phone_number  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id  AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND URID = '"+URID+"'";
		String sqlQueryCount = "Select count(*)  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND URID = '"+URID+"'";
		int count = 0;
		
    		Connection connection;
			try {
				connection = getConnectionItem();
			       Statement statement 		= connection.createStatement();
			       Statement statementCount = connection.createStatement();

			       ResultSet resultset_count = statementCount.executeQuery(sqlQueryCount);  
			       while (resultset_count.next()){
			    	   count = resultset_count.getInt(1);
			    	   }		

			       if (count>0){
				       ResultSet resultset = statement.executeQuery(sqlQuery);  
						resultset.next();  
						return resultset.getString(1);
			       }else{
			    	   return "";
			       }	       

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
	}	

	
		
	
	
	public static String get_EU_ACCOUNT_TYPE_ByAccountIdentifier(String AccountIdentifier){
		String sqlQuery = "select EU_ACCOUNT_TYPE from ACCOUNT where identifier = '"+AccountIdentifier+"'";
		String sqlQueryCount = "select count(*) from ACCOUNT where identifier = '"+AccountIdentifier+"'";
		int count = 0;
		try {
			Connection connection = getConnectionItem();
			Statement statementCount = connection.createStatement();
			Statement statement 		= connection.createStatement();
			ResultSet resultset_count = statementCount.executeQuery(sqlQueryCount);  
			   while (resultset_count.next()){
				   count = resultset_count.getInt(1);
				   }
			   if (count>0){
			       ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					return resultset.getString(1);
			   }else{
				   return "";
			   }
		} catch (SQLException e) {
			e.printStackTrace();return "";
		}
	}
	
	public static String get_ACCOUNT_CHECK_DIGIT_BY_ByAccountIdentifier(String AccountIdentifier){
		String sqlQuery = "select CHECK_DIGITS from ACCOUNT where identifier = '"+AccountIdentifier+"'";
		String sqlQueryCount = "select count(*) from ACCOUNT where identifier = '"+AccountIdentifier+"'";
		int count = 0;
		try {
			Connection connection = getConnectionItem();
			Statement statementCount = connection.createStatement();
			Statement statement 		= connection.createStatement();
			ResultSet resultset_count = statementCount.executeQuery(sqlQueryCount);  
			   while (resultset_count.next()){
				   count = resultset_count.getInt(1);
				   }
			   if (count>0){
			       ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					return resultset.getString(1);
			   }else{
				   return "";
			   }
		} catch (SQLException e) {
			e.printStackTrace();return "";
		}
	}
	
	
	
		public static boolean CHECK_IF_TRUSTED_ACCOUNT_EXIST(String HOLD_ACC_Identifier, String ACQ_ACC_Identifier){
		String sqlQueryCount = "select count(*) from TRUSTED_ACCOUNT where HOLD_ACC_ID = (select ACCOUNT_ID from ACCOUNT WHERE IDENTIFIER = '"+HOLD_ACC_Identifier+"') AND ACQ_ACC_ID = (select ACCOUNT_ID from ACCOUNT WHERE IDENTIFIER = '"+ACQ_ACC_Identifier+"') AND STATUS = 'TRUSTED'";
//		String sqlQueryCount = "select count(*) from ACCOUNT where identifier = '"+AccountIdentifier+"'";
		int count = 0;
		Connection connection;
		try {
			connection = getConnectionItem();
		       Statement statementCount = connection.createStatement();
		       ResultSet resultset_count = statementCount.executeQuery(sqlQueryCount);  
		       while (resultset_count.next()){
		    	   count = resultset_count.getInt(1);
		    	   }
		       if (count>0){
					return true;
		       }else{
		    	   return false;
		       }
		} catch (SQLException e) {
			e.printStackTrace(); return false;
		}
	       
	}

		
		public static boolean CHECK_IF_ESD_ACCOUNT_EXIST(String MS, String YEAR){
		String sqlQueryCount = "select count(*) from ACCOUNT where ACCOUNT_NAME like 'ESD-%"+MS+"-"+YEAR+"%' AND STATUS = 'ACTIVE'";
//		String sqlQueryCount = "select count(*) from ACCOUNT where identifier = '"+AccountIdentifier+"'";
		int count = 0;
		Connection connection;
		try {
			connection = getConnectionItem();
		       Statement statementCount = connection.createStatement();
		       ResultSet resultset_count = statementCount.executeQuery(sqlQueryCount);  
		       while (resultset_count.next()){
		    	   count = resultset_count.getInt(1);
		    	   }
		       if (count>0){
					return true;
		       }else{
		    	   return false;
		       }
		} catch (SQLException e) {
			e.printStackTrace(); return false;
		}
	       
	}		
		
	
	public static String get_CHECKDIGIT_NUMBER_ByAccountIdentifier(String AccountIdentifier){
		String sqlQuery = "SELECT check_digits FROM ACCOUNT where  identifier = '"+AccountIdentifier+"'";
    			Connection connection;
				try {
					connection = getConnectionItem();
	    			Statement statement = connection.createStatement();
					ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					String rex = resultset.getString(1);
					return rex; 
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
	}
	
	public static String get_STATUS_OF_USER_BY_URID(String URID) throws SQLException, InterruptedException{
		String sqlQuery = "select state from USERS where URID = '"+URID+"'  ";
    	Connection connection = getConnectionItem();
	       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				String rex = resultset.getString(1);
				return rex; 
	}	
	
	public static String get_ROLE_NAME_BY_URID(String URID){
		String sqlQuery = "select role_name from roles where role_id = (select role_id from accesses where profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"'))";
		String rex = null;	
		try {
			Connection connection = getConnectionItem();
			Statement statement = connection.createStatement();
			ResultSet resultset = statement.executeQuery(sqlQuery);  
			resultset.next();  
			rex = resultset.getString(1);
		} catch (Exception e) {
			System.out.println("Error getting row count");
		    e.printStackTrace();
		    rex = null;
		}
				return rex; 
	}	
	
	
	
	
	
	public static boolean CHECK_IF_ENROLLED_USER_EXIST_BY_LOGINNAME(String LoginName, String REGISTRY){
	String sqlQueryCount = "select count(*) from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName+"' and URID like '"+REGISTRY+"%'";
	int count = 0;
	Connection connection;
	Statement statementCount;
	ResultSet resultset_count = null;
	try {
			connection = getConnectionItem();
			statementCount = connection.createStatement();
			resultset_count = statementCount.executeQuery(sqlQueryCount);  
	
       while (resultset_count.next()){
    	   count = resultset_count.getInt(1);
    	   }
       if (count>0){
    			return true;
           }else{
        	   return false;
           }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace(); return false;
	}   
}	
	public static boolean CHECK_IF_ANY_USER_EXIST_BY_LOGINNAME(String LoginName, String REGISTRY){ // EXCEPT UNENROLLED AND UNENROLLEMENT_PENDING
	String sqlQueryCount = "select count(*) from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND USERS.STATE NOT IN ('UNENROLLED','UNENROLLEMENT_PENDING') AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName+"' and URID like '"+REGISTRY+"%'";
	int count = 0;
	Connection connection;
	Statement statementCount;
	ResultSet resultset_count = null;
	try {
			connection = getConnectionItem();
			statementCount = connection.createStatement();
			resultset_count = statementCount.executeQuery(sqlQueryCount);  
	
       while (resultset_count.next()){
    	   count = resultset_count.getInt(1);
    	   }
       if (count>0){
    			return true;
           }else{
        	   return false;
           }
	} catch (SQLException e) {
		e.printStackTrace(); return false;
	}   
}	
	

	public static boolean CHECK_IF_USER_UNENROLL_PENDING_EXIST_BY_LOGINNAME(String LoginName, String REGISTRY){ // EXCEPT UNENROLLED AND UNENROLLEMENT_PENDING
	String sqlQueryCount = "select count(*) from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND USERS.STATE ='UNENROLLEMENT_PENDING' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName+"' and URID like '"+REGISTRY+"%'";
	int count = 0;
	Connection connection;
	Statement statementCount;
	ResultSet resultset_count = null;
	try {
			connection = getConnectionItem();
			statementCount = connection.createStatement();
			resultset_count = statementCount.executeQuery(sqlQueryCount);  
	
       while (resultset_count.next()){
    	   count = resultset_count.getInt(1);
    	   }
       if (count==1){
    			return true;
           }else{
        	   return false;
           }
	} catch (SQLException e) {
		e.printStackTrace(); return false;
	}   
}	


	
	public static String GET_USER_UNENROLL_PENDING_URID_BY_LOGINNAME(String LoginName, String REGISTRY){
		String sqlQuery = "select USERS.URID from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND USERS.STATE ='UNENROLLEMENT_PENDING' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+LoginName+"' and URID like '"+REGISTRY+"%'";
    	Connection connection;
    	Statement statement;
    	ResultSet resultset;
		try {
			connection = getConnectionItem();
		    statement = connection.createStatement();
			resultset = statement.executeQuery(sqlQuery);  
			resultset.next();  
			String rex = resultset.getString(1);return rex;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); return null;
		}
				 
	}	
	
	
	public static String GET_REQUEST_NUMBER_FOR_UNENROLLMENT_USER_URID(String URID){
		int max = -1;
		String sqlQuery = "select u.REQUEST_COMPONENT_ID FROM UNENROLMENT_REQUEST_USER u where u.URID = '"+URID+"'";
    	Connection connection;
		try {
			connection = getConnectionItem();
		       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				max = resultset.getInt(1);  
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
				String RequestNumber = Integer.toString(max);return RequestNumber; 
	}	
	
	
	
	public static int getListWithQueryResult(String query) throws SQLException{
		ResultSet r = null;
		r = sqlRunQueryForResultset(getConnectionItem(), query);
		
		List<String> list = new ArrayList<String>();
		while (r.next()){
		    list.add(r.getString(1));
		}
		return list.size();
	}
	
	
	
	public static int getNumberRows(){
	    try{
	    	Connection connection = getConnectionItem();
	       Statement statement = connection.createStatement();
	       ResultSet resultset = statement.executeQuery("select IDENTIFIER from ACCOUNT where account_status like '%BLOCKED%' and registry_code = 'PL'");
	       if(resultset.last()){
	          return resultset.getRow();
	       } else {
	           return 0; //just cus I like to always do some kinda else statement.
	       }
	    } catch (Exception e){
	       System.out.println("Error getting row count");
	       e.printStackTrace();
	    }
	    return 0;
	}
	
	
	public static int getNumberOfRecords() throws SQLException, InterruptedException{
		List<String> l = getBlockedAccountsId("IDENTIFIER", "GR");
		System.out.println(l.size());
		return 0;
	}

	public static int getResult_AR_attched_to_account(String URID, String accIdentifier) throws SQLException, InterruptedException{
		SeleniumWebUtils.reportInfo("ENTER Function getResult_AR_attched_to_account [URID="+URID+"][accIdentifier="+accIdentifier+"]");			
//		Thread.sleep(1000);
		String sql = "select STATE from accesses where account_id = (select ACCOUNT_ID from account where identifier = '"+accIdentifier+"') and accesses.profile_id = (select PROFILE_ID from PROFILE where URID = '"+URID+"')";
		List<String> l = getSqlRecordSetIntoList("STATE", sql);
		SeleniumWebUtils.reportInfo("SQL query: " + sql);			
		SeleniumWebUtils.reportInfo("EXIT Function getResult_AR_attched_to_account [URID="+URID+"][accIdentifier="+accIdentifier+"]");
		int rowNum = l.size();
		return rowNum;
	}
	
	
	public static String geInstallationForAccountIDENTIFIER(String AccountIdentifier) throws SQLException, InterruptedException{
		String AccounOHA_AOHA_Identifier = AppSqlQueries.getInstallationNumberByAccountIdentifier(AccountIdentifier);
		if (AccounOHA_AOHA_Identifier.isEmpty()){
			SeleniumWebUtils.reportFail("Return empty from query of OHA_AOHA identifier to account: "+AccountIdentifier);
			return null;
		}else{
			return AccounOHA_AOHA_Identifier;
		}
	}
	
	
	
//	public static void main(String[] args) throws SQLException, InterruptedException {
////		System.out.println(getResult_AR_attched_to_account("GR905538054392", "5001603"));
//		
//		
//		System.out.println(getRequestNumberByTransactionIdentifier("EU22889"));
//
//	}

	
	
	public static String getStatusOfTheAccountByIdentifier(String AccountIdentifier){
		String sqlQuery = "select account_status from account where identifier = '"+AccountIdentifier+"'";
    	Connection connection;
    	Statement statement;
    	ResultSet resultset;
    	String rex;
    	try {
			connection = getConnectionItem();
			statement = connection.createStatement();
			resultset = statement.executeQuery(sqlQuery);
			resultset.next();
			rex = resultset.getString(1);
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("CANNOT GRAB THE STATUS OF THE ACCOUNT: "+AccountIdentifier);
			return null;
		}
				return rex; 
	}				
	
	public static String get_BALANCE_OF_EU_DELETION_ACCOUNT(){
		String sqlQuery = "select BALANCE from ACCOUNT where EU_ACCOUNT_TYPE = 'UNION_ALLOWANCE_DELETION' and ACCOUNT_STATUS = 'OPEN'";
    	Connection connection;
		try {
			connection = getConnectionItem();
	       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					String rex = resultset.getString(1);
					return rex; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}		
	public static String get_BALANCE_OF_AUCTION_ACCOUNT() throws SQLException, InterruptedException{
		String sqlQuery = "select BALANCE from ACCOUNT where EU_ACCOUNT_TYPE = 'AUCTION_ACCOUNT' and ACCOUNT_STATUS = 'OPEN'";
    	Connection connection = getConnectionItem();
	       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				String rex = resultset.getString(1);
				return rex; 
	}		
	public static String get_BALANCE_OF_AVIATION_AUCTION_ACCOUNT() throws SQLException, InterruptedException{
		String sqlQuery = "select BALANCE from ACCOUNT where EU_ACCOUNT_TYPE = 'AVIATION_AUCTION_ACCOUNT' and ACCOUNT_STATUS = 'OPEN'";
    	Connection connection = getConnectionItem();
	       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				String rex = resultset.getString(1);
				return rex; 
	}		
	
	public static String get_BALANCE_OF_ACCOUNT_BY_IDENTIFIER(String AccountIdentifier){
		String sqlQuery = "select BALANCE from ACCOUNT where IDENTIFIER = '"+AccountIdentifier+"'";
    	Connection connection;
    	String rex;
		try {
				connection = getConnectionItem();
		        Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				rex = resultset.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return null;
		}
				return rex; 
	}				

	public static String get_BALANCE_OF_ACCOUNT_BY_IDENTIFIER_AND_UNIT_TYPE(String AccountIdentifier, String UNIT_TYPE){
		
		String sqlQuery = null;
		String unitType = null;
		switch (UNIT_TYPE) {
		case "CER"		: unitType = UNIT_TYPE; break;
		case "ERU"		: unitType = UNIT_TYPE; break;
		case "RMU"		: unitType = UNIT_TYPE; break;
		case "ERUFAAU"	: unitType = "ERU_FROM_AAU"; break;
		case "ERU from AAU": unitType = "ERU_FROM_AAU"; break;
		case "ERUFRMU"	: unitType = "ERU_FROM_RMU"; break;
		case "GA"		: unitType = "ALLOWANCE_CHAPTER3"; break;
		case "AA"		: unitType = "ALLOWANCE_CHAPTER2"; break;
		case "AAU"		: unitType = "AAU"; break;
		case "AAUSOP"	: unitType = "AAU"; break;
		case "AAUNSOP"	: unitType = "AAU"; break;
		}
		
		if(UNIT_TYPE.equals("AAUSOP") || UNIT_TYPE.equals("AAUNSOP")){
			int SOP_factor = -1;
			if(UNIT_TYPE.equals("AAUSOP")){
				SOP_factor = 1;
			}else if(UNIT_TYPE.equals("AAUNSOP")){
				SOP_factor = 0;
			}
			
			sqlQuery = "select SUM((END_ - START_)+1) from UNIT_BLOCK WHERE ACCOUNT_ID = (select ACCOUNT_ID from ACCOUNT where identifier = '"+AccountIdentifier+"') "
					+ "	AND UNIT_TYPE = '"+unitType+"'"
					+ "	AND APPLICABLE_PERIOD = 2"
					+ "	AND ORIGINAL_PERIOD = 2"
					+ "	AND RESERVED_FOR_TX IS null"
					+ " AND SOP = "+SOP_factor+"";
		}else{
			sqlQuery = "select SUM((END_ - START_)+1) from UNIT_BLOCK WHERE ACCOUNT_ID = (select ACCOUNT_ID from ACCOUNT where identifier = '"+AccountIdentifier+"') "
					+ "	AND UNIT_TYPE = '"+unitType+"'"
					+ "	AND APPLICABLE_PERIOD = 2"
					+ "	AND ORIGINAL_PERIOD = 2"
					+ "	AND RESERVED_FOR_TX IS null";
		}
		
		
		
		Connection connection;
    	String rex;
		try {
				connection = getConnectionItem();
		        Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				rex = resultset.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return null;
		}
				return rex; 
	}	
	
	public static String get_KP_BALANCE_OF_ACCOUNT_BY_IDENTIFIER_AND_UNIT_TYPE(String AccountIdentifier, String UNIT_TYPE, String CPs){
		
		String sqlQuery = null;
		String unitType = null;
		String orgPeriod = null;
		String appPeriod = null;
		if(!CPs.contains("-")){
			orgPeriod = "2";
			appPeriod = "2";			
			switch (UNIT_TYPE) {
			case "CER"		: unitType = UNIT_TYPE; break;
			case "ERU"		: unitType = UNIT_TYPE; break;
			case "RMU"		: unitType = UNIT_TYPE; break;
			case "ERUFAAU"	: unitType = "ERU_FROM_AAU"; break;
			case "ERU from AAU": unitType = "ERU_FROM_AAU"; break;
			case "ERUFRMU"	: unitType = "ERU_FROM_RMU"; break;
			case "GA"		: unitType = "ALLOWANCE_CHAPTER3"; break;
			case "AA"		: unitType = "ALLOWANCE_CHAPTER2"; break;
			case "AAU"		: unitType = "AAU"; break;
			case "AAUSOP"	: unitType = "AAU"; break;
			case "AAUNSOP"	: unitType = "AAU"; break;
			}
		}else{ // this condition checks for shape like: RMU-1-1 or CER-2-2 in UNITTYPE
			String[] cUnitParms = CPs.split("-"); 
			unitType = cUnitParms[0];
			orgPeriod = cUnitParms[1];
			appPeriod = cUnitParms[2];
		}
		
		if(UNIT_TYPE.equals("AAUSOP") || UNIT_TYPE.equals("AAUNSOP")){
			int SOP_factor = -1;
			if(UNIT_TYPE.equals("AAUSOP")){
				SOP_factor = 1;
			}else if(UNIT_TYPE.equals("AAUNSOP")){
				SOP_factor = 0;
			}
			
			sqlQuery = "select SUM((END_ - START_)+1) from UNIT_BLOCK WHERE ACCOUNT_ID = (select ACCOUNT_ID from ACCOUNT where identifier = '"+AccountIdentifier+"') "
					+ "	AND UNIT_TYPE = '"+unitType+"'"
					+ "	AND ORIGINAL_PERIOD = "+orgPeriod+""
					+ "	AND APPLICABLE_PERIOD = "+appPeriod+""
					+ "	AND RESERVED_FOR_TX IS null"
					+ " AND SOP = "+SOP_factor+"";
		}else{
			sqlQuery = "select SUM((END_ - START_)+1) from UNIT_BLOCK WHERE ACCOUNT_ID = (select ACCOUNT_ID from ACCOUNT where identifier = '"+AccountIdentifier+"') "
					+ "	AND UNIT_TYPE = '"+unitType+"'"
					+ "	AND ORIGINAL_PERIOD = "+orgPeriod+""
					+ "	AND APPLICABLE_PERIOD = "+appPeriod+""
					+ "	AND RESERVED_FOR_TX IS null";
		}
		
		
		
		Connection connection;
    	String rex;
		try {
				connection = getConnectionItem();
		        Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				rex = resultset.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return null;
		}
				return rex; 
	}				
	
	
	
	
	
	public static List<String> getAccIdentifiersWith_USER_OPEN_byREG_byURID(String RegistryCode, String URID, String AccountType /*[OHA|AOHA]*/, String UserRole /*[AR/AAR]*/) throws SQLException, InterruptedException{
		String thisAccountType = (AccountType.equals("OHA")) ? "OPERATOR_HOLDING" : "AIRCRAFT_OPERATOR";
		String thisUserRole = (UserRole.equals("AR")) ? "AUTHORISED_REPRESENTATIVE" : "ADDITIONAL_AUTHORISED_REPRESENTATIVE";
		
		String sqlQuery = "select ac.IDENTIFIER from account ac "
				+ "inner join accesses ax on ax.account_id = ac.account_id "
				+ "inner join roles r on r.role_id = ax.role_id  and r.role_name = '"+thisUserRole+"' "
				+ "inner join profile p on ax.profile_id = p.profile_id "
				+ "inner join users u on u.urid = p.urid "
				+ "where ac.registry_code ='"+RegistryCode+"' "
				+ "AND ac.eu_account_type = '"+thisAccountType+"' "
				+ "AND ac.account_status = 'OPEN' "
				+ "AND u.urid ='"+URID+"'";
				
				ResultSet r = sqlRunQueryForResultset(getConnectionItem(), sqlQuery);
				
				List<String> list = new ArrayList<String>();
				while (r.next()){
				    list.add(r.getString("IDENTIFIER"));
				}
				return list;				
	}
	
			public static String get_YFE_from_HoldingAccount(String AccountIdentifier){
				String sqlQuery = "select v.START_YEAR from VERIFIED_ENTITY v INNER JOIN ACCOUNT a ON v.VERIFIED_ENTITY_ID = a.VERIFIED_ENTITY_ID WHERE a.IDENTIFIER = "+AccountIdentifier;
				Connection connection;
				Statement statement;
				ResultSet resultset;
				String rex = null;
				try {
					connection = getConnectionItem();
					statement = connection.createStatement();
					resultset = statement.executeQuery(sqlQuery);
					resultset.next();
					rex = resultset.getString(1);
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
				return rex; 			
	}
	// ##################################################################################################
	
	public static boolean RUN_EUCR_ALLOCATION_QUARZ_TRIGGER(int minutes){
		ax.print("RUN_EUCR_ALLOCATION_QUARZ_TRIGGER");
		return RUN_QUARZ_TRIGGER("AllocationTriggers", "AutomaticAllocationTrigger", "EUCR", minutes);}		
	public static boolean RUN_EUCR_BLOCKING_ACCOUNT_QUARZ_TRIGGER(int minutes){
		ax.print("RUN_EUCR_BLOCKING_ACCOUNT_QUARZ_TRIGGER");
		return RUN_QUARZ_TRIGGER("ComplianceTriggers", "BlockAccountsTrigger", "EUCR", minutes);}		
	public static boolean RUN_EUCR_COMPL_STATUS_LOGGING_QUARZ_TRIGGER(int minutes){
		ax.print("RUN_EUCR_COMPL_STATUS_LOGGING_QUARZ_TRIGGER");
		return RUN_QUARZ_TRIGGER("ComplianceTriggers", "ComplianceStatusLoggingTrigger", "EUCR", minutes);}		
	public static boolean RUN_EUTL_COMPL_COMPUTATION_TASK_QUARZ_TRIGGER(int minutes){
		ax.print("RUN_EUTL_COMPL_COMPUTATION_TASK_QUARZ_TRIGGER");
		return RUN_QUARZ_TRIGGER("AdminProcessesTriggers", "ComplianceComputationTaskTrigger", "EUTL", minutes);}		
	public static boolean RUN_QUARZ_TRIGGER(String TriggerGroup, String TriggerName, String Connection, int minutes){
		ArrayList<String> add_hr_min =  DateOperations.ADD_MINUTES_TO_CURRENT_TIME(minutes);
		String hour2D 	= add_hr_min.get(0);
		String min2D 	= add_hr_min.get(1);
		String sqlQuery1 = "UPDATE qrtz_cron_triggers SET cron_expression = to_char(sysdate+"+minutes+"*(1/24/60),'\"0 \"MI\" \"HH24\" \"DD\" \"MM\" ?\"') WHERE trigger_group = '"+TriggerGroup+"' AND trigger_name = '"+TriggerName+"'";	
		String sqlQuery2 = "UPDATE qrtz_triggers SET next_fire_time = -1, trigger_state = 'MISFIRED' WHERE trigger_group = '"+TriggerGroup+"'  AND trigger_name = '"+TriggerName+"'";
		String sqlQuery3 = "COMMIT";
		Connection connection;
		try {
			connection 				= (Connection.equals("EUCR"))?getConnectionItem():Connection.equals("EUTL")?getEUTLConnectionItem():getConnectionItem();
			Statement statement 	= connection.createStatement();
			statement.executeUpdate(sqlQuery1);
			statement.executeUpdate(sqlQuery2);
			statement.executeQuery(sqlQuery3);			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		SeleniumWebUtils.reportInfo("ALLOCATION TRIGGER QUARZ ON "+hour2D+":"+min2D);
		int SecondsToWait = 130;
		ax.print("["+ax.GET_NOW()+"] Waiting " + SecondsToWait + " seconds for running quarz: [" + TriggerGroup + "][" + TriggerName + "] at  "+Connection);
		SeleniumWaiter.sleeper(SecondsToWait);
		ax.print("["+ax.GET_NOW()+"] Finished Waiting for running quarz: [" + TriggerGroup + "][" + TriggerName + "] at  "+Connection);
		return true;
	}
	// ##################################################################################################
	// ##################################################################################################
	// ##################################################################################################

	
	public static boolean RUN_COMPLIANCE_TRIGGERS_PRECONDITIONS_SUITE(String DateStamp){
		String sqlQuery2A = "CREATE TABLE TMP_ACCOUNT_"+DateStamp+" AS SELECT * FROM ACCOUNT";
		String sqlQuery2B = "CREATE TABLE TMP_COMP_STATUS_"+DateStamp+" AS SELECT * FROM Compliance_Status";
		String sqlQuery3 = "COMMIT";
    	Connection connection = null;
			try {
				connection = getConnectionItem();
			    Statement statement = connection.createStatement();
				statement.executeUpdate(sqlQuery2A);
				statement.executeUpdate(sqlQuery2B);
				System.out.println( statement.executeUpdate(sqlQuery3)>=0 ); 
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}

	
	
	public static boolean RUN_EUCR_BLOCKING_ACCOUNT_TRIGGERS_ROLLBACKER(String Prefix){
		ax.print("RUNNING: RUN_EUCR_BLOCKING_ACCOUNT_TRIGGERS_ROLLBACKER");
		String sqlQuery1A = "update Account a set a.account_Status = (select t.account_Status from  TMP_ACCOUNT_"+Prefix+" t where t.account_id = a.account_id),"
				+ "a.previous_account_Status = (select t.previous_account_Status from  TMP_ACCOUNT_"+Prefix+" t where t.account_id = a.account_id)";
		String sqlQuery1B = "update Compliance_Status cs set cs.compliance_status = (select t.compliance_Status from  TMP_COMP_STATUS_"+Prefix+" t "
				+ "where t.COMPLIANCE_STATUS_ID = cs.COMPLIANCE_STATUS_ID)";
		String sqlQuery1C = "delete compliance_status where COMPLIANCE_STATUS_ID not in (select COMPLIANCE_STATUS_ID from TMP_COMP_STATUS_"+Prefix+")";
		String commiter = "COMMIT";
    	Connection connection = null;
			try {
				connection = getConnectionItem();
			    Statement statement = connection.createStatement();
				statement.executeUpdate(sqlQuery1A);
				statement.executeUpdate(sqlQuery1B);
				statement.executeUpdate(sqlQuery1C);
				
//				System.out.println( statement.executeUpdate(sqlQuery3)); 
				return (statement.executeUpdate(commiter) >=0);
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}	

	public static boolean RUN_EUCR_COMPLIANCE_STAT_LOGGING_TRIGGER_CLEANER(String YEAR){
		ax.print("RUNNING: RUN_EUCR_COMPLIANCE_STAT_LOGGING_TRIGGER_CLEANER");
		String sqlQuery1 = "Delete compliance_log where year = '"+YEAR+"'";
		String commiter = "COMMIT";
    	Connection connection = null;
			try {
				connection = getConnectionItem();
			    Statement statement = connection.createStatement();
				statement.executeUpdate(sqlQuery1);
				return (statement.executeUpdate(commiter) >=0);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}	
	
	
	public static boolean RUN_EUCR_COMPLIANCE_TMP_ACCOUNTS_CLEANER(String DateStamp){
		ax.print("RUNNING: RUN_EUCR_COMPLIANCE_TMP_ACCOUNTS_CLEANER");
		String sqlQuery2A = "DROP TABLE TMP_ACCOUNT_"+DateStamp+"";
		String sqlQuery2B = "DROP TABLE TMP_COMP_STATUS_"+DateStamp+"";
		String committer = "COMMIT";
    	Connection connection = null;
			try {
				connection = getConnectionItem();
			    Statement statement = connection.createStatement();
				statement.executeUpdate(sqlQuery2A);
				statement.executeUpdate(sqlQuery2B);
//				System.out.println( statement.executeUpdate(sqlQuery3)); 
				return (statement.executeUpdate(committer) >=0);
				
			} catch (SQLException e) {
				ax.print("ORA-00942: table or view does not exist");
				e.printStackTrace();
				return false;
			}
	}
	
	public static boolean RUN_COMPLIANCE_TRIGGERS_QUARZ1(){
		boolean COMPL_EUCR_BLOCKING_QUARZ 							= AppSqlQueries.RUN_EUCR_BLOCKING_ACCOUNT_QUARZ_TRIGGER(2);
		System.out.println("RUN_COMPLIANCE_TRIGGERS_QUARZ1: " 			+ COMPL_EUCR_BLOCKING_QUARZ);
		return COMPL_EUCR_BLOCKING_QUARZ;
	}
	public static boolean RUN_COMPLIANCE_TRIGGERS_QUARZ2(){
		boolean COMPL_EUCR_COMPL_STAT_QUARZ 						= AppSqlQueries.RUN_EUCR_COMPL_STATUS_LOGGING_QUARZ_TRIGGER(2);
    	boolean COMPL_EUTL_COMPL_COMPUTATION_QUARZ 					= AppSqlQueries.RUN_EUTL_COMPL_COMPUTATION_TASK_QUARZ_TRIGGER(2);
    	System.out.println("COMPL_EUCR_COMPL_STAT_QUARZ: " 			+ COMPL_EUCR_COMPL_STAT_QUARZ);
    	System.out.println("COMPL_EUTL_COMPL_COMPUTATION_QUARZ: " 	+ COMPL_EUTL_COMPL_COMPUTATION_QUARZ);
    	return (COMPL_EUCR_COMPL_STAT_QUARZ && COMPL_EUTL_COMPL_COMPUTATION_QUARZ);
	}
	
	
	public static boolean RUN_COMPLIANCE_TRIGGERS_SUITE(){
    	boolean COMPL_EUCR_BLOCKING_QUARZ 							= AppSqlQueries.RUN_EUCR_BLOCKING_ACCOUNT_QUARZ_TRIGGER(2);
    	boolean COMPL_EUCR_COMPL_STAT_QUARZ 						= AppSqlQueries.RUN_EUCR_COMPL_STATUS_LOGGING_QUARZ_TRIGGER(2);
    	boolean COMPL_EUTL_COMPL_COMPUTATION_QUARZ 					= AppSqlQueries.RUN_EUTL_COMPL_COMPUTATION_TASK_QUARZ_TRIGGER(2);
    	System.out.println("COMPL_EUCR_BLOCKING_QUARZ: " 			+ COMPL_EUCR_BLOCKING_QUARZ);
    	System.out.println("COMPL_EUCR_COMPL_STAT_QUARZ: " 			+ COMPL_EUCR_COMPL_STAT_QUARZ);
    	System.out.println("COMPL_EUTL_COMPL_COMPUTATION_QUARZ: " 	+ COMPL_EUTL_COMPL_COMPUTATION_QUARZ);
    	return (COMPL_EUCR_BLOCKING_QUARZ && COMPL_EUCR_COMPL_STAT_QUARZ && COMPL_EUTL_COMPL_COMPUTATION_QUARZ); 
	}
	public static boolean RUN_COMPLIANCE_TRIGGERS_ROLLBACK_SUITE(String Prefix, String TimeStampOfLowerBorder /*format: 16-FEB-17 00.14.29*/){
		ax.print("RUN_COMPLIANCE_TRIGGERS_ROLLBACK_SUITE [older than "+TimeStampOfLowerBorder+"] ");
		boolean REVERT_BLOCKING_ACCOUNT_TRIGGER						= AppSqlQueries.RUN_EUCR_BLOCKING_ACCOUNT_TRIGGERS_ROLLBACKER(Prefix);
		boolean REVERT_COMPLIANCE_STAT_LOGGING_TRIGGER				= AppSqlQueries.RUN_EUCR_COMPLIANCE_STAT_LOGGING_TRIGGER_CLEANER(DateOperations.GET_YEAR_PREVIOUS());
    	boolean DEL_EUCR_TABLES 									= AppSqlQueries.RUN_EUCR_COMPLIANCE_TMP_ACCOUNTS_CLEANER(Prefix);
    	boolean REVERT_EUTL_COMPL_COMPUTATION_TASK_TRIGGER 			= AppSqlQueries.RUN_EUTL_COMPLIANCE_COMPUTATION_TASK_TRIGGER_CLEANER(TimeStampOfLowerBorder);
    	System.out.println("REVERT_BLOCKING_ACCOUNT_TRIGGER: " 				+ REVERT_BLOCKING_ACCOUNT_TRIGGER);
    	System.out.println("REVERT_COMPLIANCE_STAT_LOGGING_TRIGGER: " 		+ REVERT_COMPLIANCE_STAT_LOGGING_TRIGGER);
    	System.out.println("REVERT_EUTL_COMPL_COMPUTATION_TASK_TRIGGER: " 	+ REVERT_EUTL_COMPL_COMPUTATION_TASK_TRIGGER);
    	System.out.println("DEL_EUCR_TABLES: " 								+ DEL_EUCR_TABLES);
    	
    	return (DEL_EUCR_TABLES && REVERT_BLOCKING_ACCOUNT_TRIGGER && REVERT_COMPLIANCE_STAT_LOGGING_TRIGGER && REVERT_EUTL_COMPL_COMPUTATION_TASK_TRIGGER); 
	}
	
	
	public static boolean RUN_EUTL_COMPLIANCE_COMPUTATION_TASK_TRIGGER_CLEANER(String TimeStampOfLoverBorder /*format: 16-FEB-17 00.14.29 */){
		ax.print("RUNNING: RUN_EUTL_COMPLIANCE_COMPUTATION_TASK_TRIGGER_CLEANER");
		String sqlQuery1A = "Delete from COMPLIANCE_STATUS_HISTORY where COMPLIANCE_STATUS_BL_id in (select COMPLIANCE_STATUS_BL_id from COMPLIANCE_STATUS_BL where compliance_status_bl_datetime > '"+TimeStampOfLoverBorder+".174000000')";
		String sqlQuery2A = "Delete from COMPLIANCE_STATUS_BL where compliance_status_bl_datetime > '"+TimeStampOfLoverBorder+".174000000'";
		String committer = "COMMIT";
    	Connection connection = null;
			try {
				connection = getEUTLConnectionItem();
			    Statement statement = connection.createStatement();
				statement.executeUpdate(sqlQuery1A);
				statement.executeUpdate(sqlQuery2A);
				return (statement.executeUpdate(committer) >=0);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	
	public static void RUN_REPLICATION_SCRIPT(){
		String sqlQuery = "UPDATE qrtz_cron_triggers SET cron_expression = to_char(sysdate+2*(1/24/60),'\"0 \"MI\" \"HH24\" \"DD\" \"MM\" ?\"') WHERE trigger_group = 'AllocationTriggers' AND trigger_name = 'AutomaticAllocationTrigger'";		
    	Connection connection;
		try {
			connection = getConnectionItem();
		    Statement statement = connection.createStatement();
			statement.executeQuery(sqlQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ax.wait(1);
		SeleniumWebUtils.reportInfo("RUNN REPLICATION PROCEDURE");
	} 	
	
	
	public static String getVerifierADM_AccountIdentifier(){
		String VerACCHOLDERNAME=VerifierOperations.getVERIFIER_ACC_HOLDER_NAME();
		if (!VerACCHOLDERNAME.isEmpty()){
			String sqlQuery = "select IDENTIFIER from account where ACCOUNT_HOLDER_ID = (select id from ACCOUNT_HOLDER WHERE name = '"+VerACCHOLDERNAME+"' and REGISTRY_CODE = '"+ProjectDataVariables.EUCR_REG+"')";
	    	Connection connection = null;
	    	String rex = null;
	    	try {
			connection = getConnectionItem();
		       	Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();
				rex = resultset.getString(1);
	    	} catch (SQLException e){
	    		System.out.println("Problem with SQL getting VERIFIER ACCOUNT identifier");
	    		e.printStackTrace();
	    	}
					
		return rex; 		
		}else{
			SeleniumWebUtils.reportFail("NO ACCOUNT FOR VERIFIER DEFINED IN THIS REGISTRY");
			return null;
		}
	}
	
	public static String get_FULL_ACCOUNT_NUMBER_ByAccountIdentifier(String AccountIdentifier){
		String MainIdentifier = null;
		String[] acx;
		if(AccountIdentifier.contains("-")){
			acx = AccountIdentifier.split("-");
			MainIdentifier = acx[2]; 
		}else{
			MainIdentifier = AccountIdentifier;
		}
		String sqlQuery = "select HOSTING_REGISTRY_CODE||'-100-'||identifier ||'-0-'|| check_digits FROM ACCOUNT WHERE IDENTIFIER = '"+MainIdentifier+"'";
		String rex = null;
    			try {
					Connection connection = getConnectionItem();
					Statement statement = connection.createStatement();
					ResultSet resultset = statement.executeQuery(sqlQuery);  
					resultset.next();  
					rex = resultset.getString(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return rex; 
	}
	
	public static boolean UPDATE_MOBILE_ON_FOR_USER_URID(String USER_URID){
		String MOBILE_NR_TO_UPDATE=null;
		String USER_NAME;
		try {
			USER_NAME = getLoginNameBy_URID(USER_URID);
			if(LoginToEUCR.TELS_GR_1.contains(USER_NAME)){						MOBILE_NR_TO_UPDATE = TwilioTest.TWILIO_PHONE1;
			}else if(LoginToEUCR.TELS_GR_2.contains(USER_NAME)){				MOBILE_NR_TO_UPDATE = TwilioTest.TWILIO_PHONE2;
			}else if(LoginToEUCR.TELS_GR_3.contains(USER_NAME)){				MOBILE_NR_TO_UPDATE = TwilioTest.TWILIO_PHONE3;
			}
			
			String sqlQuery2 = "UPDATE USER_DETAILS set MOBILE_PHONE_NUMBER = '"+MOBILE_NR_TO_UPDATE+"' where USER_DETAILS_ID = (select user_details_id from USERS where URID = '"+USER_URID+"')";
			String sqlQuery3 = "COMMIT";
	    	Connection connection = null;
			connection = getConnectionItem();
		    Statement statement = connection.createStatement();
			statement.executeUpdate(sqlQuery2);		
			statement.executeUpdate(sqlQuery3);	
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();return false;
		}
	}
	public static void UPDATE_MOBILE_OFF_FOR_USER_URID(String USER_URID){
		
		if(ProjectDataVariables.IS_LOGIN_SMS_ENABLED()){return;} // &&ProjectDataVariables.IS_SIGNATURE_ENABLED()
		String sqlQuery2 = "UPDATE USER_DETAILS set MOBILE_PHONE_NUMBER = '' where USER_DETAILS_ID = (select user_details_id from USERS where URID = '"+USER_URID+"')";
		String sqlQuery3 = "COMMIT";
    	Connection connection = null;
			try {
				connection = getConnectionItem();
			    Statement statement = connection.createStatement();
				statement.executeUpdate(sqlQuery2);		
				statement.executeUpdate(sqlQuery3);		
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static boolean UPDATE_MOBILE_USERS_BY_USERNAME_UNENROLLED(String NewMobileNumber){
//		if(ProjectDataVariables.IS_LOGIN_SMS_ENABLED()){return;} // &&ProjectDataVariables.IS_SIGNATURE_ENABLED()
		String sqlQuery2 = "update USER_DETAILS UD set UD.MOBILE_PHONE_NUMBER = '"+NewMobileNumber+"' WHERE UD.FIRST_NAME = 'EUCR-TEST-USER-FLOW'";
		String sqlQuery3 = "COMMIT";
    	Connection connection = null;
			try {
				connection = getConnectionItem();
			    Statement statement = connection.createStatement();
				int ex1 = statement.executeUpdate(sqlQuery2);		
				int ex2 = statement.executeUpdate(sqlQuery3);
				if(ex1 >= 0 &&  ex2 >= 0) {
					return true;
				}else{
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}
	
	
	
	
	public static void ENROLL_NAS(String USER_NA_URID){
		String sqlQuery2 = "INSERT INTO ACCESSES (access_id, account_id, profile_id, role_id, state) VALUES (ACCESS_SEQ.NEXTVAL, -1, (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+USER_NA_URID+"'), (select role_id from roles where role_name = 'NATIONAL_ADMINISTRATOR' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+USER_NA_URID+"')), 'ACTIVE')";
		String sqlQuery3 = "UPDATE USERS SET previous_state='VALIDATED', state='ENROLLED' where urid = '"+USER_NA_URID+"'";
		String sqlQuery4 = "COMMIT";
    	Connection connection = null;
		try {
			connection = getConnectionItem();
		    Statement statement = connection.createStatement();
			statement.executeUpdate(sqlQuery2);		
			statement.executeUpdate(sqlQuery3);		
			statement.executeUpdate(sqlQuery4);		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void ENROLL_CAS(String USER_NA_URID){
		String sqlQuery2 = "update accesses set role_id = (select role_id from roles where role_name = 'CENTRAL_ADMINISTRATOR' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+USER_NA_URID+"'))"
				+ "where role_id = (select role_id from roles where role_name = 'NATIONAL_ADMINISTRATOR' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+USER_NA_URID+"'))"
				+ "and profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+USER_NA_URID+"')";
		String sqlQuery4 = "COMMIT";
    	Connection connection = null;
		try {
			connection = getConnectionItem();
		    Statement statement = connection.createStatement();
			statement.executeUpdate(sqlQuery2);		
			statement.executeUpdate(sqlQuery4);		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static boolean CHECK_REQUEST_STATUS_IF_APPROVED(String REQUEST_TABLE, String REQUESTER_URID){
		String sqlQueryCount = "select count(*) FROM REQUEST_STATE WHERE REQUEST_ID = (select max(REQUEST_ID) FROM "+REQUEST_TABLE+" WHERE REQUESTER_URID = '"+REQUESTER_URID+"') AND STATE='APPROVED'";
		int count = 0;
        ResultSet resultset_count = null;
        Statement statementCount;
   		Connection connection;
		try {
			connection = getConnectionItem();
			statementCount = connection.createStatement();
			resultset_count = statementCount.executeQuery(sqlQueryCount);
		       while (resultset_count.next()){
		    	   count = resultset_count.getInt(1);
		    	   }
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       if (count>0){
			return true;
       }else{
    	   return false;
       }
	}	
	
	public static boolean WAIT_FOR_REQUEST_STATUS_TO_BE_APPROVED(String REQUEST_TABLE, String REQUESTER_URID){
		int i=0;
		while(!AppSqlQueries.CHECK_REQUEST_STATUS_IF_APPROVED(REQUEST_TABLE, REQUESTER_URID)){
			System.out.println("Waiting 1.min on checking REQUEST STATUS FROM "+REQUEST_TABLE+" with REQUESTER: "+REQUESTER_URID);
			ax.wait(1);
			i++;
			if(i>ProjectDataVariables.TEST_RETRIAL_FACTOR_30){
				System.out.println("WAITING LONGER THAN "+ProjectDataVariables.TEST_RETRIAL_FACTOR_30+"... Stop on checking. Exit function.");
				return false;
			}
		}
		return true;
	}
	
	
	public static int GET_AUCTION_IDENTIFIER_MAX_FROM_AUCTION() throws SQLException, InterruptedException{
				String sqlQuery = "select MAX(IDENTIFIER) from AUCTION";
		    	Connection connection = getConnectionItem();
			       Statement statement = connection.createStatement();
						ResultSet resultset = statement.executeQuery(sqlQuery);  
						resultset.next();  
						int max = resultset.getInt(1);  
						return max; 
			}
	
 	public static String GET_SQL_ICE_FROM_XML_BY_IDENTIFIER(String AccountIdentifier) throws SQLException, InterruptedException{
	String sqlQuery = "select ICE_IN_XML from ACCOUNT_ENTITLEMENT where ACCOUNT_ID IN ( select ACCOUNT_ID from ACCOUNT where IDENTIFIER = '"+AccountIdentifier+"' )";
	String sqlQuery_count = "select count(*) from ACCOUNT_ENTITLEMENT where ACCOUNT_ID IN ( select ACCOUNT_ID from ACCOUNT where IDENTIFIER = '"+AccountIdentifier+"' )";
	int count = 0;
	Connection connection = getConnectionItem();
       Statement statement = connection.createStatement();
       Statement statement_count = connection.createStatement();
        ResultSet resultset_count = statement_count.executeQuery(sqlQuery_count);
		ResultSet resultset = statement.executeQuery(sqlQuery);
		
	       while (resultset_count.next()){
	    	   count = resultset_count.getInt(1);
	    	   }
	       
	      if(count!=0){
	    	  resultset.next();
	    	  String rex = resultset.getString(1);
//	    	  int max 	= resultset.getInt(1); 
	    	  
	    	  if(rex == null){ // resultset.wasNull()==false && Integer.valueOf(rex)==0
	    		  System.out.println("SQL[ICE_FROM_XML]: (NULL)");
	    		  return "NULL";
	    	  }
	    	  
	    	  System.out.println("SQL[ICE_FROM_XML]: "+rex);
	    	  return rex;
	      } else{
	    	  System.out.println("SQL[ICE_FROM_XML]: NULL");
	    	  return "NULL";
	      }
	       
 	} 

	public static String GET_SQL_DCS_BY_IDENTIFIER(String AccountIdentifier) throws SQLException, InterruptedException{
		String sqlQuery = "select COMPLIANCE_STATUS from COMPLIANCE_STATUS where VERIFIED_ENTITY_ID = (select VERIFIED_ENTITY_ID from ACCOUNT where IDENTIFIER = '"+AccountIdentifier+"')' )";
		Connection connection = getConnectionItem();
	       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				int max = resultset.getInt(1);  
				return String.valueOf(max); 
	}  	
	public static String GET_PPSR_IDENTIFIER_FOR_REGISTRY(String REGISTRY) throws SQLException, InterruptedException{
		String sqlQuery = "select identifier from ACCOUNT where KYOTO_ACCOUNT_TYPE = 'PPSR_ACCOUNT' AND REGISTRY_CODE = '"+REGISTRY+"' AND ACCOUNT_STATUS = 'OPEN'";
		Connection connection = getConnectionItem();
	       Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery(sqlQuery);  
				resultset.next();  
				int max = resultset.getInt(1);  
				return String.valueOf(max); 
	}  	
	

	
	
	
	
	public static boolean REPLACE_NOT_RELATED_ARS_TO_FAKE_ARS(String EUCR_REG, String AccountHolderId){
		ArrayList<String> NOT_RELATED_ARS 	= new ArrayList<String>(Arrays.asList("eucrauto_ar5","eucrauto_ar6","eucrauto_ar7")); 
		ArrayList<String> FAKED_ARS 		= new ArrayList<String>(Arrays.asList("eucrauto_03","eucrauto_04","eucrauto_05")); 
		String sqlQuery_loop;
		String sqlQuery_Update_AH_REP_TABLE;
		String sqlQuery4 = "COMMIT";
    	Connection connection = null;
    	int execute_resulter;
    	int execute_resulter_ah;
		try {
			connection = getConnectionItem();
		    Statement statement = connection.createStatement();
			int i=0;
		    for (String subject_ar : NOT_RELATED_ARS) {
		    	String fk_ar = FAKED_ARS.get(i);
		    	
		    	sqlQuery_Update_AH_REP_TABLE = "UPDATE ACCOUNT_HOLDER_REPRESENTATIVE SET STATUS = 'REJECTED' WHERE ACCOUNT_HOLDER_ID = (SELECT ID FROM ACCOUNT_HOLDER WHERE IDENTIFIER = '"+AccountHolderId+"' AND STATUS = 'ACTIVE') AND URID = (Select users.URID  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+subject_ar+"' and URID like 'BG%')";
		    	sqlQuery_loop = "UPDATE accesses set profile_id = (select PROFILE_ID from profile where urid = (Select users.URID  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+fk_ar+"' and URID like '"+EUCR_REG+"%')) where profile_id = (select PROFILE_ID from profile where urid = (Select users.URID  from  USERS, USER_DETAILS, LOGIN WHERE USERS.user_details_id=user_DETAILS.user_details_id AND users.STATE='ENROLLED' AND LOGIN.LOGIN_ID=USERS.LOGIN_ID AND LOGIN.ECAS_USERNAME = '"+subject_ar+"' and URID like 'BG%'))";//  -- // gumulak_1
//		    	execute_resulter_ah = statement.executeUpdate(sqlQuery_Update_AH_REP_TABLE);
//		    	execute_resulter 	= statement.executeUpdate(sqlQuery_loop);
		    	
//		    	if((execute_resulter<0) || (execute_resulter_ah<0)){ax.t_error("Problem with executing replace ARs update-query: "+sqlQuery_loop);return false;}
		    	System.out.println( sqlQuery_Update_AH_REP_TABLE  );
		    	System.out.println( sqlQuery_loop  );
				i++;
			}
//		    execute_resulter = statement.executeUpdate(sqlQuery4);		
//		    if(execute_resulter<0){ax.t_error("Problem with executing replace ARs update-commit: "+sqlQuery4);return false;}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	return true;	
		
	} 
	
	public static boolean UPDATE_AR_NA_SQL_MASS_USERS_BY_URID(String UPDATE_TYPE /*[AR2NA|NA2AR]*/, ArrayList<String> ARRAYS_WITH_URIDS){
		// AR2NA, NA2AR
		//>> AR2NA - ETS
		String SQL_QUERY = null;
		String SQL_COMMIT = null;
		Connection connection = null;
		int ret =0;
		switch (UPDATE_TYPE) {
		case "AR2NA":
			for (String URID : ARRAYS_WITH_URIDS) {
				if(URID.substring(0, 2).equals("ED")){
					SQL_QUERY="INSERT INTO ACCESSES  (access_id, account_id, profile_id, role_id, state) VALUES ( ACCESS_SEQ.NEXTVAL, -1, (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"' ), (SELECT role_id FROM roles WHERE role_name  = 'ESD_CENTRAL_ADMINISTRATOR' AND registry_code= (SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+URID+"' )), 'ACTIVE')";
					SQL_COMMIT="commit";
					
				}else{
					SQL_QUERY="insert into accesses values (ACCESS_SEQ.nextval, 'ACTIVE', -1, (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"'), (select role_id from roles where role_name = 'NATIONAL_ADMINISTRATOR' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid  = '"+URID+"')),(null))";
					SQL_COMMIT="commit";
				}
				
				//System.out.println( SQL_QUERY);
				
				try {
					connection = getConnectionItem();
				    Statement statement = connection.createStatement();
					ret=statement.executeUpdate(SQL_QUERY);		
					statement.executeUpdate(SQL_COMMIT);		
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		case "NA2AR":
			for (String URID : ARRAYS_WITH_URIDS) {
				if(URID.substring(0, 2).equals("ED")){
					SQL_QUERY="delete accesses where role_id = (select role_id from roles where role_name = 'ESD_CENTRAL_ADMINISTRATOR' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+URID+"')) and profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"')";
					SQL_COMMIT="commit";
				}else{
					SQL_QUERY="delete accesses where role_id = (select role_id from roles where role_name = 'NATIONAL_ADMINISTRATOR' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid  = '"+URID+"')) and profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"')";
					SQL_COMMIT="commit";
				}
				//System.out.println( SQL_QUERY);
				try {
					connection = getConnectionItem();
				    Statement statement = connection.createStatement();
				    ret=statement.executeUpdate(SQL_QUERY);		
					statement.executeUpdate(SQL_COMMIT);		
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			break;
		}
		if(ret==1 || ret==2){System.out.println("UPDATE: "+UPDATE_TYPE+" resulted with: "+ret);return true;}else{return false;}

		}
	
	public static boolean UPDATE_SQL_SINGLE_URID(String URID, String FX_BEFORE, String FX_AFTER){
		// AR-SYSTEM_ADMINISTRATOR
		// NATIONAL_ADMINISTRATOR-SYSTEM_ADMINISTRATOR
		
		FX_BEFORE 	= AppGetter.GET_USER_FUNCTION_NAME(FX_BEFORE, "AR");
		FX_AFTER 	= AppGetter.GET_USER_FUNCTION_NAME(FX_AFTER, "AR");
		
		/*
		if(FX_BEFORE.length() > 4 || FX_AFTER.length() > 4){
			FX_BEFORE = FX_BEFORE;
			FX_AFTER = FX_AFTER;
			}else{
				switch (FX_BEFORE) {
				case "NA":		FX_BEFORE = "NATIONAL_ADMINISTRATOR";break;
				case "CA":		FX_BEFORE = "CENTRAL_ADMINISTRATOR";	break;
				case "SA":		FX_BEFORE = "SYSTEM_ADMINISTRATOR";		break;
				case "SDA":		FX_BEFORE = "SERVICE_DESK_AGENT";		break;
				case "SD":		FX_BEFORE = "SERVICE_DESK";				break;
				case "AUD":		FX_BEFORE = "AUDITOR_FOR_NA";			break;
				case "AR":		FX_BEFORE = "AR";						break;
				case "TRAD":	FX_BEFORE = "TRADER";					break;
				default:	System.out.println("No delivered FX_BEFORE to function..");return false;
			}
			switch (FX_AFTER) {
				case "NA":		FX_AFTER = "NATIONAL_ADMINISTRATOR";break;
				case "CA":		FX_AFTER = "CENTRAL_ADMINISTRATOR";	break;
				case "SA":		FX_AFTER = "SYSTEM_ADMINISTRATOR";		break;
				case "SDA":		FX_AFTER = "SERVICE_DESK_AGENT";		break;
				case "SD":		FX_AFTER = "SERVICE_DESK";				break;
				case "AUD":		FX_AFTER = "AUDITOR_FOR_NA";			break;
				case "AR":		FX_AFTER = "AR";						break;
				case "TRAD":	FX_AFTER = "TRADER";					break;
				default:	System.out.println("No delivered FX_AFTER to function..");return false;
			}
		}
		*/
		
		String SQL_QUERY = null;
		String SQL_COMMIT = "commit";
		Connection connection = null;
		int ret =0;
		if(FX_BEFORE.equals("AR")){
			if(URID.substring(0, 2).equals("ED")){
				SQL_QUERY	="INSERT INTO ACCESSES  (access_id, account_id, profile_id, role_id, state) VALUES ( ACCESS_SEQ.NEXTVAL, -1, (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"' ), (SELECT role_id FROM roles WHERE role_name  = 'ESD_CENTRAL_ADMINISTRATOR' AND registry_code= (SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+URID+"' )), 'ACTIVE')";
			}	
			else{
				SQL_QUERY	="insert into accesses values (ACCESS_SEQ.nextval, 'ACTIVE', -1, (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"'), (select role_id from roles where role_name = '"+FX_AFTER+"' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid  = '"+URID+"')),(null))";
			}
		}else if(FX_AFTER.equals("AR")){
			if(URID.substring(0, 2).equals("ED")){
				SQL_QUERY	="delete accesses where role_id = (select role_id from roles where role_name = 'ESD_CENTRAL_ADMINISTRATOR' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+URID+"')) and profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"')";
			}else{
				SQL_QUERY	="delete accesses where role_id = (select role_id from roles where role_name = '"+FX_BEFORE+"' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid  = '"+URID+"')) and profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"')";	
			}
		}else{
			SQL_QUERY	="update accesses set role_id = (select role_id from roles where role_name = '"+FX_AFTER+"' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+URID+"')) where role_id = (select role_id from roles where role_name = '"+FX_BEFORE+"' and registry_code=(SELECT REGISTRY_CODE FROM USERS WHERE urid = '"+URID+"')) and profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"')"; 
		}
		try {
			connection = getConnectionItem();
		    Statement statement = connection.createStatement();
		    ret=statement.executeUpdate(SQL_QUERY);		
			statement.executeUpdate(SQL_COMMIT);		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(ret==1 || ret==2){System.out.println("UPDATE: "+FX_BEFORE+"-"+FX_AFTER+" resulted with: "+ret);return true;}else{return false;}
	
	}	

	public static boolean CHECKING_URID_IS_NA(String URID){
		return CHECKING_URID_HAS_FUNCTION(URID, "NA");
	}
	public static boolean CHECKING_URID_IS_CA(String URID){
		return CHECKING_URID_HAS_FUNCTION(URID, "CA");
	}
	
	public static boolean CHECKING_URID_HAS_FUNCTION(String URID, String FUNCTION){
				ArrayList<String> ARX = new ArrayList<String>();
				String sqlQuery = "select role_name from roles where role_id IN (select role_id from accesses where profile_id = (SELECT PROFILE_ID FROM PROFILE WHERE urid = '"+URID+"'))";
				Connection connection;
				try {
					connection = getConnectionItem();
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(sqlQuery); 
					while(rs.next()){
						ARX.add(  rs.getString("role_name") );}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); return false;}
						
						
				if(ARX.contains(AppGetter.GET_USER_FUNCTION_NAME(FUNCTION))){return true;}else{return false;}		
				
			}

}





/*
 * 
 * - Update Token flag in TMS (by SQL) 

- Assign User by URID to the account as AR/AAR
- Assign User by URID to the account as /AAR

- Remove AR by URID from the Account
- Remove AAR by URID from the Account

- Make URID user NONE
- Get Registry from URID (read first two letters)


 "BG276011579651","BG728730562581","BE116498555890","BE934712460428","ED112994721619","ED294215263702"

 * 
 * 
 * 		ArrayList<String> ARX = new ArrayList<String>(Arrays.asList("BG276011579651","BG728730562581","BE116498555890","BE934712460428","ED112994721619","ED294215263702"));
//		AppSqlQueries.UPDATE_AR_NA_SQL_MASS_USERS_BY_URID("NA2AR", ARX);
//		AppSqlQueries.UPDATE_AR_NA_SQL_MASS_USERS_BY_URID("AR2NA", ARX);
		
		AppSqlQueries.UPDATE_SQL_SINGLE_URID("BG000000703995", "NA", "AR"); 
		AppSqlQueries.UPDATE_SQL_SINGLE_URID("BG000000703995", "AR", "CA"); 
		AppSqlQueries.UPDATE_SQL_SINGLE_URID("BG000000703995", "CA", "SD"); 
		AppSqlQueries.UPDATE_SQL_SINGLE_URID("BG000000703995", "SD", "SDA"); 
		AppSqlQueries.UPDATE_SQL_SINGLE_URID("BG000000703995", "SDA", "AUD"); 
		AppSqlQueries.UPDATE_SQL_SINGLE_URID("BG000000703995", "AUD", "AR"); 
				
		
		
		System.out.println("dd");
 * 
 * 
 * 
 * 
 */


