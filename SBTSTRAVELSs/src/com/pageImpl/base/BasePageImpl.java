package com.pageImpl.base;

import org.openqa.selenium.WebDriver;

import com.page.base.BasePage;
import com.utils.GenericMethods;

public abstract class BasePageImpl implements BasePage {
	protected WebDriver driver = null;
	protected GenericMethods genericMethods = null;
	protected BasePageImpl(WebDriver driver) {
		this.driver = driver;
		this.genericMethods = new GenericMethods(driver);
	}
}
