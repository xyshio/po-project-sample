package po;

import static utils.SeleniumDriver.getDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;



/**
 * Page object representing xxxxxxx home page.
 *
 * @author kgumulak
 */
public class TemplatePage extends AbstractPage<TemplatePage> {

	public static TemplatePage runEucr;
	
	@FindBy(linkText="Task list")
	WebElement taskPageLink;	
	
	@Override
	public String getExpectedPageTitle() {
		return "Task List";
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
	public TemplatePage goToTaskPage() {
		return new TemplatePage().loadPage(TemplatePage.class);
	}
	
	public TemplatePage open() {
		return new TemplatePage().loadPage(TemplatePage.class);
	}

}
