package com.pageImplementation;

import java.io.FileNotFoundException;

import org.openqa.selenium.WebDriver;

import com.page.LoginPage;
import com.page.RegisterPage;
import com.pageImpl.base.BasePageImpl;

 

	
	
	public class RegisterPgaeImpl extends BasePageImpl implements RegisterPage  {
		private static final String REGISTER_PAGE = "RegisterPage";
		public RegisterPgaeImpl(WebDriver webDriver ) {
			super(webDriver);
		}

		public String getPageName() {
			return REGISTER_PAGE ;
		}

		@Override
		public void RegisterUser(String recordId) throws FileNotFoundException {
			genericMethods.clickElement(signin, getPageName());
			genericMethods.clickElement(singup, getPageName());
			genericMethods.getPageDetails();
			
			//genericMethods.setValueInTextBox(name, getPageName(),recordId);
			
			genericMethods.setValueInTextBox(mobile, getPageName(),recordId);
			//genericMethods.setValueInTextBox(email, getPageName(),recordId);
			//genericMethods.setValueInTextBox(password, getPageName(),recordId);
			//genericMethods.setValueInTextBox(Retypepassword, getPageName(),recordId);
			//genericMethods.setValueInTextBox(terms, getPageName(),recordId);
		   // genericMethods.clickElement(RegisterButton,  getPageName());
		}
}
