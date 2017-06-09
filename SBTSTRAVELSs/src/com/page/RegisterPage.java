package com.page;

import java.io.FileNotFoundException;

import com.page.base.BasePage;

public interface RegisterPage extends BasePage {
	public String signin = "signin";
	public  String singup = "singup";
	public  String name = "name";
	public String mobile="mobile";
	public  String email = "email";
	public  String password = "password";
	public String Retypepassword="Retypepassword";
	public String terms="terms";
	public String RegisterButton="RegisterButton";
	
	public void RegisterUser(String recordId) throws FileNotFoundException;
	
	
	
	
	
	
	
	
	
	
}
