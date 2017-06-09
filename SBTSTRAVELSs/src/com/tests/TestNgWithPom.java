package com.tests;

import java.io.FileNotFoundException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.page.HomePage;
import com.page.LoginPage;
import com.page.RegisterPage;
import com.pageImplementation.HomePageImpl;
import com.pageImplementation.LoginPageImpl;
import com.pageImplementation.RegisterPgaeImpl;
import com.resource.PropertiesCache;
import com.tests.base.BaseDataProvidor;
import com.utils.GenericMethods;

public class TestNgWithPom  extends BaseDataProvidor {
	WebDriver driver = null;

	/*
	 *@ Initialize Driver and loading Object Repository 
	 */
	@BeforeMethod
	public void initialise() {
		driver = GenericMethods.getDriver(PropertiesCache.getInstance().getProperty("browser"));
		GenericMethods.getPageDetails();
	}

	/**
	 * 
	 */
	@Test
	public void loginAndVerifyTitle() throws FileNotFoundException {
		driver.get(PropertiesCache.getInstance().getProperty("baseUrl"));
		GenericMethods.maxiWindow();

		// declaration
		HomePage homePage = new HomePageImpl(driver);
		LoginPage loginPage = new LoginPageImpl(driver);
		RegisterPage RegisterPage=new RegisterPgaeImpl(driver);
		homePage.clickSignIn();
		//
		
		loginPage.loginToApplication("TC001");
		//homePage.verifyTitle();
		
		//RegisterPage.RegisterUser("TC001");
		
		
		
		
	}
	
	//@AfterMethod
	//public void closeInstance() {
		//GenericMethods.closeAllWindows();
	}

//}
