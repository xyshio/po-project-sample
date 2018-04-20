package api;

import static utils.SeleniumDriver.getDriver;

import java.util.ArrayList;
import java.util.Arrays;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import eu.ets.eucr.users.Registration_Page;
import utils.ApplicationPageUtils;
import utils.ax;
import utils.AppSqlQueries;
import po.EucrHomePage;
import po.RegistrationPage;
import po.UsersPoPage;

public class Users {

	public static int i = 0;
	public static String requestNumber;

	public static boolean UsersScenarioEndToEnd(ExtentReports extent, ExtentTest test) {

		test = ax.test_group(extent, "USER-MANAGEMENT", "user management", 1);
		boolean prepare = Users.prepareUsersScenario();
		ax.log("PREPARING - USER - TEST - " + prepare);
		// prepare test:
		// 1. open as NA1 and search for "nusersku" (enrolledvalidated, registered,
		// unenrollment-pending) in users
		// 2. get status, do action if status equals:
		// - enrolled => unenroll
		// - validated = > unenroll
		// - unenrollment-pending = > find the request and approve unenroll
		String newUserUrid = Users.usersEnrolUnenrolledUser(test);
		boolean enrollment = ax.IS_URID(newUserUrid);
		boolean unenrollment = Users.usersUnenrolEnrolledUser(test, newUserUrid);
		newUserUrid = Users.usersRegisterUser(test);
		boolean registration = ax.IS_URID(newUserUrid);
		String enrollmentKey = Users.usersValidateUser(test, newUserUrid);
		boolean valiation = ax.IS_ENROLLMENT_KEY(enrollmentKey);
		newUserUrid = Users.usersEnrollUser(test, newUserUrid, enrollmentKey);
		boolean secondEnrollment = ax.IS_URID(newUserUrid);
		boolean update = Users.usersUpdateUserPersonalDetailsProposal(test, newUserUrid);
		boolean rolesUpdate = Users.usersPromoteToSda(test, newUserUrid);
		boolean finalUnenrollment = Users.usersUnenrolEnrolledUser(test, newUserUrid);
		ArrayList<Boolean> results = new ArrayList<Boolean>(Arrays.asList(prepare, enrollment, unenrollment,
				registration, valiation, secondEnrollment, update, rolesUpdate, finalUnenrollment));
		return !results.contains(false);
	}

	public static String usersEnrolUnenrolledUser(ExtentTest test) {
		EucrHomePage eucr = ax.LoginUSR();
		RegistrationPage registr = eucr.clickFillInYourPersonalDetails();
		registr = registr.fillRegistrationForm();
		String newUserUrid = ApplicationPageUtils.GET_CURRENT_USER_URID(getDriver());
		ax.result(getDriver(), test, ax.IS_URID(newUserUrid), "USER-GOT-URID " + newUserUrid,
				"new registered user got URID number");
		ax.setDriver(2);
		EucrHomePage eucr2 = ax.LoginNA1(2);
		eucr2.openEucrMenu("Users");
		String enrollmentKey = Registration_Page.registerNewUserApproval(getDriver(2), "", newUserUrid);
		ax.setDriver(1);
		registr = registr.enterEnrollmentKey(enrollmentKey);
		ax.result(getDriver(), test, (registr != null), "USER-ENTER-ENROLLMENT-KEY",
				"new registered user entered enrollment key");
		AppSqlQueries.UPDATE_MOBILE_OFF_FOR_USER_URID(newUserUrid);
		eucr2 = ax.LoginNA1(2);
		boolean userStatusAfterEnrolment = Registration_Page.getUserStatusByUrid(getDriver(2), newUserUrid)
				.equals(Registration_Page.USR_STAT_ENROLLED);
		ax.result(getDriver(), test, userStatusAfterEnrolment, "USER-BECOME-ENROLLED", "new user is enrolled");
		return newUserUrid;
	}

	public static boolean usersUnenrolEnrolledUser(ExtentTest test, String newUserUrid) {
		ax.setDriver(2);
		EucrHomePage eucr3 = ax.LoginNA2(3);
		UsersPoPage users = (UsersPoPage) eucr3.openEucrMenu("Users");
		String requestNumber = users.proposeUnenrollmentOfUrid(users, newUserUrid);
		EucrHomePage eucr2 = ax.LoginNA1(2);
		boolean unenrollApproval = Eucr.ApproveTransactionRequestsEtl(requestNumber);
		if (test != null) {
			ax.result(getDriver(), test, unenrollApproval, "USER-UNENROLLMENT-APPROVED",
					"new user has approved unenrollment request");
		}
		ax.setDriver(3);
		eucr3 = ax.LoginNA2(3);
		boolean userStatusAfterEnrolment = Registration_Page.getUserStatusByUrid(getDriver(3), newUserUrid)
				.equals(Registration_Page.USR_STAT_UNENROLLED);
		if (test != null) {
			ax.result(getDriver(), test, userStatusAfterEnrolment, "USER-UNENROLLED", "new user is unenrolled");
		}
		return userStatusAfterEnrolment;
	}

	public static String usersRegisterUser(ExtentTest test) {
		EucrHomePage eucr = ax.LoginUSR();
		RegistrationPage registr = eucr.clickFillInYourPersonalDetails();
		registr = registr.fillRegistrationForm();
		String newUserUrid = ApplicationPageUtils.GET_CURRENT_USER_URID(getDriver());
		return newUserUrid;
	}

	public static String usersValidateUser(ExtentTest test, String newUserUrid) {
		ax.setDriver(2);
		EucrHomePage eucr2 = ax.LoginNA1(2);
		eucr2.openEucrMenu("Users");
		String enrollmentKey = Registration_Page.registerNewUserApproval(getDriver(2), "", newUserUrid);
		ax.result(getDriver(), test, ax.IS_URID(newUserUrid), "USER-" + newUserUrid + "-IS-VALIDATED",
				"user is validated with getting back enrollment key " + enrollmentKey);
		return enrollmentKey;
	}

	public static String usersEnrollUser(ExtentTest test, String newUserUrid, String enrollmentKey) {
		ax.setDriver(1);
		RegistrationPage registr = new RegistrationPage().loadPage(RegistrationPage.class);
		registr.enterEnrollmentKey(enrollmentKey);
		AppSqlQueries.UPDATE_MOBILE_OFF_FOR_USER_URID(newUserUrid);
		EucrHomePage eucr2 = ax.LoginNA1(2);
		boolean userStatusAfterEnrolment = Registration_Page.getUserStatusByUrid(getDriver(2), newUserUrid)
				.equals(Registration_Page.USR_STAT_ENROLLED);
		ax.result(getDriver(), test, userStatusAfterEnrolment, "USER-VALIDATED-BECOME-ENROLLED",
				"new user is enrolled");
		if (userStatusAfterEnrolment) {
			return newUserUrid;
		}
		return null;
	}

	public static boolean usersUpdateUserPersonalDetailsProposal(ExtentTest test, String newUserUrid) {
		ArrayList<String> valueToUpdate = new ArrayList<String>(Arrays.asList("AxDesign::Corp", "AxDesign::HR::Finanse",
				"Head-of-piwnica", "Domorowly grzes 4432", "Gynsi-Pympek", "77890", "Lesniewo", "SlaskiGor", "AT",
				"l@l.pl", "l@l.pl", "+8777890", "+8777890"));
		ax.setDriver(2);
		EucrHomePage eucr2 = ax.LoginNA1(2);
		UsersPoPage user = (UsersPoPage) eucr2.openEucrMenu("Users");
		user.searchUserByUrid(newUserUrid);
		user.clickFirstUserLink();
		user.clickUpdateBusinessDetails();
		String requestNumber = user.proposeUpdateOfBusinessDetails(valueToUpdate);
		ax.result(getDriver(), test, ax.IS_REQUEST_NR(requestNumber),
				"PROPOSAL-BUSINESS-DETAILS-UPDATE-" + requestNumber, "business details update");

		EucrHomePage eucr3 = ax.LoginNA2(3);
		boolean approve = Eucr.ApproveTransactionRequestsEtl(requestNumber);
		ax.result(getDriver(), test, approve, "APPROVAL-BUSINESS-DETAILS-UPDATE-" + requestNumber,
				"business details update approval");
		eucr3 = ax.LoginNA1(2);
		UsersPoPage users = (UsersPoPage) eucr3.openEucrMenu("Users");
		boolean check = users.checkUpdatedBusinessDetails(users, newUserUrid, valueToUpdate);
		ax.result(getDriver(), test, approve, "BUSINESS-DETAILS-UPDATE-CHECKING", "business details update check");
		return check;
	}

	public static boolean usersPromoteToSda(ExtentTest test, String newUserUrid) {
		ax.setDriver(2);
		EucrHomePage eucr2 = ax.LoginNA1(2);
		UsersPoPage user = (UsersPoPage) eucr2.openEucrMenu("Users");
		user.searchUserByUrid(newUserUrid);
		user.clickFirstUserLink();
		user.clickAdministrationRoles();
		String requestNumber = user.selectRadioRoleAndClick("SDA");
		ax.result(getDriver(), test, ax.IS_REQUEST_NR(requestNumber),
				"PROPOSAL-PROMOTE-USER-ROLE-UPDATE-" + requestNumber, "promote user role update");
		EucrHomePage eucr3 = ax.LoginNA2(3);
		boolean approve = Eucr.ApproveTransactionRequestsEtl(requestNumber);
		ax.result(getDriver(), test, approve, "APPROVAL-ADMIN-ROLES-PROMOTE-" + requestNumber, "admin-roles-approval");
		return approve;
	}

	public static boolean prepareUsersScenario() {
		String loginName = ax.GET_USER_NAME("USR");
		String enrolledUserUrid = getUserUridByLoginAndStatus(loginName, "Enrolled");
		String validatedUserUrid = getUserUridByLoginAndStatus(loginName, "Validated");
		if (enrolledUserUrid != null) {
			usersUnenrolEnrolledUser(null, enrolledUserUrid);
			return prepareUsersScenario();
		} else if (validatedUserUrid != null) {
			usersUnenrolEnrolledUser(null, validatedUserUrid);
			return prepareUsersScenario();
		} else {
			return true;
		}
	}

	public static String getUserUridByLoginAndStatus(String loginName, String status) { //
		ax.setDriver(2);
		EucrHomePage eucr2 = ax.LoginNA1(2);
		UsersPoPage user = (UsersPoPage) eucr2.openEucrMenu("Users");

		user.searchUserByLoginStatus(loginName, status);
		int rowsReturned = user.getRowsFoundFromPaginator();
		if (rowsReturned > 0) {
			// found such users, return URID
			return user.getUserUridFrom1x1();
		}
		// no records;
		return null;

	}

}
