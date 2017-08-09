package SampleTestcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestCase1 {

@Test

// Here this parameters we will take from testng.xml
@Parameters("Browser")
public  void test1(String browser) {

if(browser.equalsIgnoreCase("FF")){

	System.setProperty("webdriver.firefox.marionette","E:\\JarFiles\\geckodriver.exe");
	
	
	
WebDriver driver=new FirefoxDriver();

driver.manage().window().maximize();

driver.get("http://www.facebook.com");

driver.quit();

}
else if(browser.equalsIgnoreCase("chrome")){

System.setProperty("webdriver.chrome.driver", "E:\\JarFiles\\chromedriver.exe");

WebDriver driver=new ChromeDriver();

driver.manage().window().maximize();

driver.get("http://www.facebook.com");

driver.quit();
}
}

}
