package keywords;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends validateKeywords{
	
	public ApplicationKeywords() {
	String path=System.getProperty("user.dir")+"//src//test//resources//env.properties";
	prop=new Properties();
	envProp= new Properties();
	try {
		FileInputStream fs=new FileInputStream(path);
		prop.load(fs);
		String env = prop.getProperty("env")+".properties";
		path=System.getProperty("user.dir")+"//src//test//resources//"+env;
		fs=new FileInputStream(path);
		envProp.load(fs);
	}catch(Exception e) {
		e.printStackTrace();
	}
	softAssert = new SoftAssert();

	}
	
	
	public void login() {
		navigate("url");
		click("username_xpath");
		type("username_xpath", "username_data");
		click("password_xpath");
		type("password_xpath", "password_data");
		//app.reportFailure("Test is incorrect",true);
		click("loginButton_xpath");
	}
	public void selectDateFromCalendar() {
		
	}
	public void verifyStockAdded() {
		
	}
	public void setReport(ExtentTest test) {
		this.test=test;
	}
}
