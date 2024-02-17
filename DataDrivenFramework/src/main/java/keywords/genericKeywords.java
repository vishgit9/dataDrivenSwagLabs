package keywords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reports.ExtentManager;

public class genericKeywords {
	public WebDriver driver;
	public Properties prop;
	public Properties envProp;
	public ExtentTest test;
	public SoftAssert softAssert;
	
	public void openBrowser(String bName) {
		log("Opening Browser "+bName);
		if(prop.get("grid_run").equals("Y")){
			//hit the hub
			DesiredCapabilities cap=new DesiredCapabilities();
			if(bName.equals("Mozilla")){
				
				cap.setBrowserName("firefox");
				//cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}else if(bName.equals("Chrome")){
				 cap.setBrowserName("chrome");
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			
			try {
				// hit the hub
				driver = new RemoteWebDriver(new URL("http://localhost:4444"), cap);
			} catch (Exception e) {
			  e.printStackTrace();
			}
		}else {  //local machine
		if(bName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C://Users//Admin//Downloads//chromedriver-win64//chromedriver-win64//chromedriver.exe");
			driver=new ChromeDriver();
		}else if(bName.equalsIgnoreCase("Mozilla")) {
			System.setProperty("webdriver.gecko.driver", "C://Users//Admin//Downloads//geckodriver-v0.33.0-win64//geckodriver.exe");
			driver=new FirefoxDriver();
		}else if(bName.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.Edge.driver", "C://Users//Admin//Downloads//edgedriver_win64//msedgedriver.exe");
			driver=new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
	}
	public void navigate(String urlKey) {	
		log("Navigating to url ");
		driver.get(envProp.getProperty(urlKey));
	}
	
	public void quit() {
		log("closing browser and killing driver");
		driver.quit();
		
	}
	public void click(String locatorKey) {
		log("Clickin on Element "+locatorKey);
		getElement((locatorKey)).click();
	}
	public void type(String locatorKey, String data) {
		log("Typing "+data+" for locator "+locatorKey);
		getElement((locatorKey)).sendKeys(envProp.getProperty(data));
	}
	
	public void select(String locator, String data) {
		 
	}
	public WebElement getElement(String locator) {
		if(!isElementPresent(locator)) {
			log("Element not present "+locator);
		}
		if(!visibilityOfElement(locator)) {
			log("Element not visible "+locator);
		}
		WebElement e=driver.findElement(getLocator(locator));
		return e;
	}
	private boolean isElementPresent(String locator) {
		log("Checking presence of Element "+locator);
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locator)));
		}catch(Exception e){
			return false;
		}
		return true;
		// TODO Auto-generated method stub
		
	}
	private boolean visibilityOfElement(String locator) {
		log("Checking visibility of Element "+locator);
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		}catch(Exception e){
			return false;
		}
		return true;
		// TODO Auto-generated method stub
		
	}
	
	public By getLocator(String locatorKey) {
		By by=null;
		
		if(locatorKey.endsWith("_id"))
			by=by.id(envProp.getProperty(locatorKey));
		if(locatorKey.endsWith("_xpath"))
			by=by.xpath(envProp.getProperty(locatorKey));
		if(locatorKey.endsWith("_css"))
			by=by.cssSelector(envProp.getProperty(locatorKey));
		if(locatorKey.endsWith("_name"))
			by=by.name(envProp.getProperty(locatorKey));
		return by;
	}
	
	public void log(String msg) {
		System.out.println(msg);
		test.log(Status.INFO, msg);
	}
	public void reportFailure(String failureMsg, boolean stopOnFailure) {
		System.out.println(failureMsg);
		test.log(Status.FAIL, failureMsg);// failure in extent reports
		takeScreenShot();
		softAssert.fail(failureMsg);// failure in testNg reports
		if(stopOnFailure) {
		Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
		assertAll();}
	}
	public void assertAll() {
		softAssert.assertAll();
	}
	public void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
			//put screenshot file in reports
			test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void waitForPageToLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Wait for document.readyState to be 'complete'
        js.executeScript("return document.readyState").equals("complete");
	}
	
}
