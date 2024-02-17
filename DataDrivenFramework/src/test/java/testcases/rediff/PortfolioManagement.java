package testcases.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import testbase.BaseTest;

public class PortfolioManagement extends BaseTest {
	
	@Test
	public void createPortfolio(ITestContext context) {
		test.log(Status.INFO,"Creating portfolio");
	}
	
	@Test
	public void deletePortfolio() {
		test.log(Status.INFO,"Deleting portfolio");
	}

}
