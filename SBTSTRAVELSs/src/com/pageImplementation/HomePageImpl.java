package com.pageImplementation;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;

import com.page.HomePage;
import com.pageImpl.base.BasePageImpl;

public class HomePageImpl extends BasePageImpl implements HomePage {

	private static final String HOME_PAGE = "HomePage";

	public HomePageImpl(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public void verifyTitle() {
		String actualTitle = driver.getTitle();
		assertEquals(
				actualTitle,
				"Welcome - SBTS Travels");
	}

	@Override
	public void clickSignIn() {
		genericMethods.clickElement(signin, HOME_PAGE);
	}

	@Override
	public String getPageName() {
		return "HomePage";
	}

}
