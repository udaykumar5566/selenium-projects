package com.tests.base;

import org.testng.annotations.DataProvider;

public class BaseDataProvidor {

	/*@DataProvider(name="dataProvidor")
	public Object[][] loginData() {
		Object[][] arrayObject = getExcelData("D:/sampledoc.xls","Sheet1");
		return arrayObject;
	}
}*/


	//This test method declares that its data should be supplied by the Data Provider
	// "getdata" is the function name which is passing the data
	// Number of columns should match the number of input parameters

	@DataProvider(name="dataProvidor")
	public Object[][] getData()
	{
		//Rows - Number of times your test has to be repeated.
		//Columns - Number of parameters in test data.
		Object[][] data = new Object[3][2];

		// 1st row
		data[0][0] ="sampleuser1";
		data[0][1] = "abcdef";

		// 2nd row
		data[1][0] ="testuser2";
		data[1][1] = "zxcvb";

		// 3rd row
		data[2][0] ="guestuser3";
		data[2][1] = "pass123";

		return data;
	}
}