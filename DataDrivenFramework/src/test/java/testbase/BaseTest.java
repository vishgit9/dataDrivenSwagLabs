package testbase;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;
//acceptable failures, critical failures, unaccepted failures
public class BaseTest {

	public ApplicationKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	public SoftAssert softAssert;
	
	@BeforeTest(alwaysRun=true)
	public void beforeTest(ITestContext context) {
		System.out.println("------Before Test----");
		app=new ApplicationKeywords();
		context.setAttribute("app", app);
		
		//init the reporting for test
		rep=ExtentManager.getReports();
		test=rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Starting Test "+context.getCurrentXmlTest().getName());
		app.setReport(test);
		context.setAttribute("report", rep);
		context.setAttribute("test", test);
		app.openBrowser("Chrome");
		app.login();

	}
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(ITestContext context) {
		System.out.println("*****Before Method****");
		test=(ExtentTest)context.getAttribute("test");
		
		String criticalFailure=(String)context.getAttribute("criticalFailure");
		if(criticalFailure!=null&&criticalFailure.equals("Y")) {
			test.log(Status.SKIP, "Critical failure in previous Tests");
			throw new SkipException("Critical failure in previous Tests"); //skip in testNG
		}
		app=(ApplicationKeywords)context.getAttribute("app");
		rep=(ExtentReports)context.getAttribute("report");
	}
	@AfterTest(alwaysRun=true)
	public void afterTest(ITestContext context) {
		app=(ApplicationKeywords)context.getAttribute("app");
		if(app!=null)
		app.quit();
		System.out.println("*****After Method****");
		if(rep !=null)
			rep.flush();
	}
}
