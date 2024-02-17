package testcases.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import testbase.BaseTest;

public class Session extends BaseTest{
	
	@Test
	public void doLogin(ITestContext context) {
		app.validateTitle("homePage_url");
		app.quit();

	}
	
	@Test
	public void doLogout(ITestContext context) {
		app.log("Logging Out ");
	}
}
