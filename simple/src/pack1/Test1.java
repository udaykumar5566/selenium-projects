package pack1;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Test1 {

	
	@Test
	public void test(){
		
		
		System.setProperty("webdriver.chrome.driver", "E:\\JarFiles\\chromedriver.exe");
		
		WebDriver driver=new ChromeDriver();
		
		
		driver.get("https://sbtstravels.com/");
		
		
	}

}
