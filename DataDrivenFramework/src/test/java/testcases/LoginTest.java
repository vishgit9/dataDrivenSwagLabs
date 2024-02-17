package testcases;

import org.testng.annotations.Test;

import keywords.ApplicationKeywords;

public class LoginTest {

	@Test
	public void validLoginTest() {
		ApplicationKeywords app=new ApplicationKeywords();
		app.openBrowser("chrome");
		app.navigate("url");
		app.click("username_xpath");
		app.type("username_xpath", "standard_user");
		app.click("password_xpath");
		app.type("password_xpath", "secret_sauce");
		app.click("loginButton_xpath");
		app.validateTitle("homePage_url");

	}
}
