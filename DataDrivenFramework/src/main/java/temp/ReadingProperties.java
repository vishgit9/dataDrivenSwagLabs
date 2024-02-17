package temp;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadingProperties {
	public static void main(String[]args){
		
	String path=System.getProperty(("user.dir")+"//DataDrivenFramework//src//test//resources//project.properties");
	Properties prop=new Properties();

	try {
		FileInputStream fs=new FileInputStream(path);
		prop.load(fs);
	}catch(Exception e) {
		e.printStackTrace();
	}
	System.out.println(prop.getProperty("url"));
}
}
