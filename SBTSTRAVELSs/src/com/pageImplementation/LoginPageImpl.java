package com.pageImplementation;

import java.io.FileNotFoundException;

import org.openqa.selenium.WebDriver;

import com.page.LoginPage;
import com.pageImpl.base.BasePageImpl;

public class LoginPageImpl extends BasePageImpl implements LoginPage  {
	private static final String LOGIN_PAGE = "LoginPage";
	public LoginPageImpl(WebDriver webDriver ) {
		super(webDriver);
	}

	public String getPageName() {
		return LOGIN_PAGE;
	}

	@Override
	public void loginToApplication(String recordId) throws FileNotFoundException {
		genericMethods.setValueInTextBox(userName, getPageName(), recordId);
		genericMethods.setValueInTextBox(password, getPageName(),recordId);
		genericMethods.clickElement(signInSubmit,  getPageName());
	}

}
