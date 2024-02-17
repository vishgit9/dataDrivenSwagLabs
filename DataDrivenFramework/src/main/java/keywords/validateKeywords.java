package keywords;

import org.testng.Assert;

public class validateKeywords extends genericKeywords{
	public void validateTitle(String Expected) {
		String actualTitle=driver.getCurrentUrl();
		System.out.println("Actual Title= "+actualTitle);
		System.out.println("Expected Tile= "+prop.getProperty(Expected));
		log("Validating title :"+Expected);
		Assert.assertEquals(actualTitle, prop.getProperty(Expected));
		//reportFailure("Title is wrong",false);
	}
	public void validateTxt() {
		
	}
}
