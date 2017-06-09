package com.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.resource.PropertiesCache;

/*String elementName, String pageName // list of parameters for method when no input value required in parameter
WebElement element = getElement(elementName, pageName); // returns webelement

String elementName, String pageName,String recordId // list of parameters for method when input is required
WebElement element = getElement(elementName, pageName); // returns webelement
getPageDetailsData(pageName, elementName, recordId); // return string mentioned in xml
*/
public class GenericMethods {
	static Logger log = Logger.getLogger(GenericMethods.class.getName());
	private static  WebDriver driver = null;
	private static WebDriverWait driverWait;
	private static Map<String, Map<String, String>> locatorMap = new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, String>> locatorValuesMap = new HashMap<String, Map<String, String>>();

	public static HashMap<String, String> locators = null;
	public static HashMap<String, String> locatorsValue = null;

	public static Map<String, Map<String, String>> getLocatorMap() {
		return locatorMap;
	}

	public static Map<String, Map<String, String>> getLocatorValuesMap() {
		return locatorValuesMap;
	}

	public GenericMethods(WebDriver webdriver) {
		//getPageDetails();
		driver = webdriver;
	}

	public GenericMethods() {

	}

	public static void maxiWindow() {
		driver.manage().window().maximize();
		log.info("WebPage Maximized");
	}


	public void setValueInTextBox(String elementName, String pageName,
			String recordId) {
		WebElement element = getElement(elementName, pageName);
		String setText= getPageDetailsData(pageName, elementName, recordId);
	
		element.sendKeys(setText);
		takeScreenShot(elementName,pageName);
		log.info("Value (" + setText + ") "+"Entered in Page (" +pageName +") "+ "Element ("+elementName+")");
	}

	public void clickElement(String elementName, String pageName) {
		WebElement element = getElement(elementName, pageName);
		takeScreenShot(elementName,pageName);
		element.click();
		log.info("Clicked Page (" +pageName +") "+ "Element ("+elementName+")");
	}
	
	/**
     * @Description :Sets the implicit time out.
     * @param Stirng : seconds, webdriver 
    */
	public void setImplicitTimeOut() {
       driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
       log.info("Set implicit wait of 5000 ms");
    }
	
    /**
     * @Description : Method for switching to the child window
     */
    
	public void switchToChildWindow() {
        try {
           
            if(waitForPageLoad()){

            String parentWinID = driver.getWindowHandle();
            Set<String> allWinIDs = driver.getWindowHandles();

            for (String win : allWinIDs) {
                if (!win.equalsIgnoreCase(parentWinID)) {
                	driver.switchTo().window(win);
                    break;
                }
            }
            log.info("Switched to child window");
            
        }} catch (Exception e) {
            e.printStackTrace();
        }

    }
    
        /**
     * @Description : Gets the current window ID.
     * @return the current window ID
     */
     public String getCurrentWindowID() {
        try {
 
            String winID = driver.getWindowHandle();
            log.info("Window ID ("+winID+")");
            return winID;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

     /**
      * @Description : getAllWindowIDs : Get All Window IDs
      * @return : Set<String>
      */
      public Set<String> getAllWindowIDs() {
         try {
           
             log.info("Returned All Window IDs");
             return driver.getWindowHandles();

         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }

     }
      

     /**
      * @Description : Gets the windows count.
      * @return int
      */
     public int getWindowsCount() {

         try {
        	 log.info("Return Window Count");
             return (driver.getWindowHandles().size());
         } catch (Exception e) {
             e.printStackTrace();
             return 0;
         }

     }

     /**
	 * Forms WebElemnet based on the locator  
	 * @param elementName
	 * @param pageName
	 * @return
	 */
     
	public WebElement getElement(String elementName, String pageName) {
		String locator = locatorMap.get(pageName).get(elementName);
		String locatorValue = locatorValuesMap.get(pageName).get(elementName);
		
		if(waitForPageLoad()){
			
		if ("ID".equalsIgnoreCase(locator)) {
			return driver.findElement(ById.id(locatorValue));
		} else if ("XPATH".equalsIgnoreCase(locator)) {
			return driver.findElement(ById.xpath(locatorValue));
		} else if ("tagName".equalsIgnoreCase(locator)) {
			return driver.findElement(ById.tagName(locatorValue));
		} else if ("className".equalsIgnoreCase(locator)) {
			return driver.findElement(ByClassName.className(locatorValue));
		} else if ("LinkText".equalsIgnoreCase(locator)) {
			return driver.findElement(ByLinkText.linkText(locatorValue));
		} else if ("cssSelector".equalsIgnoreCase(locator)) {
			return driver.findElement(ByCssSelector.cssSelector(locatorValue));

		}
		
		}

		return null;
	}
     
     public WebElement getElementForScreenShot(String elementName, String pageName) {
 		String locator = locatorMap.get(pageName).get(elementName);
 		String locatorValue = locatorValuesMap.get(pageName).get(elementName);
 		
 			
 		if ("ID".equalsIgnoreCase(locator)) {
 			return driver.findElement(ById.id(locatorValue));
 		} else if ("XPATH".equalsIgnoreCase(locator)) {
 			return driver.findElement(ById.xpath(locatorValue));
 		} else if ("tagName".equalsIgnoreCase(locator)) {
 			return driver.findElement(ById.tagName(locatorValue));
 		} else if ("className".equalsIgnoreCase(locator)) {
 			return driver.findElement(ByClassName.className(locatorValue));
 		} else if ("LinkText".equalsIgnoreCase(locator)) {
 			return driver.findElement(ByLinkText.linkText(locatorValue));
 		} else if ("cssSelector".equalsIgnoreCase(locator)) {
 			return driver.findElement(ByCssSelector.cssSelector(locatorValue));

 		}
 		
 		

 		return null;
 	}

	/**
     * @Description : Wait for page load.
     * @return boolean
     */
public static boolean waitForPageLoad() {
		
		driverWait = new WebDriverWait(driver,10);
		
	    // wait for jQuery to load
	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	        	return ((Long)((JavascriptExecutor)driver).executeScript("return jQuery.active") == 0);
	        }
	        catch (Exception e) {
	          // no jQuery present
	          return true;
	        }
	      }
	    };

	    // wait for Javascript to load
	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	    	return ((JavascriptExecutor)driver).executeScript("return document.readyState")
	        .toString().equals("complete");
	      }
	    };
	  log.info("Loading Complete");
	  return driverWait.until(jQueryLoad) && driverWait.until(jsLoad);
	}
    /*public boolean waitForPageLoad() {
    	log.info("Waiting for Page Load");
        int waitTime = new Double(20).intValue();        
        ExpectedCondition<Boolean> pageLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(driver, waitTime);

        try {
            wait.until(pageLoad);
        } catch (Throwable pageLoadWaitError) {

            return false;

        }
        return true;
    }*/
	
	/**
	 * Reads Object repository and stores elements  of page as key value pair in map
	 */
	public static void getPageDetails() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db
					.parse(new File(
							"E:\\New folder\\SBTSTRAVELSs\\src\\com\\resource\\ObjectRepository.xml"));
			locators = new HashMap<String, String>();
			locatorsValue = new HashMap<String, String>();

			NodeList nodeList1 = document.getElementsByTagName("page");
			for (int i = 0; i < nodeList1.getLength(); i++) {
				Element question = (Element) nodeList1.item(i);

				NodeList optionList = question.getElementsByTagName("element");
				for (int j = 0; j < optionList.getLength(); ++j) {
					locators.put(optionList.item(j).getAttributes()
							.getNamedItem("name").getNodeValue(), optionList
							.item(j).getAttributes().getNamedItem("Descriptor")
							.getNodeValue());
					locatorsValue.put(optionList.item(j).getAttributes()
							.getNamedItem("name").getNodeValue(), optionList
							.item(j).getAttributes().getNamedItem("value")
							.getNodeValue());

				}
				getLocatorMap().put(nodeList1.item(i).getAttributes().getNamedItem("name").getNodeValue(), locators);
				getLocatorValuesMap().put(nodeList1.item(i).getAttributes().getNamedItem("name").getNodeValue(), locatorsValue);
			}

		} catch (Exception e) {
		}
	}

	/**
	 *  Creates instance of webdriver based on the passed browser parameter 
	 * @return WebDriver
	 */
	public static WebDriver getDriver(String browserType) {
		if (browserType.equals("chrome")) {
			driver = initChromeDriver();
		} else if (browserType.equals("ie")) {
			driver = initIEDriver();
		} else {
			driver = initFirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;

	}
	private static WebDriver initChromeDriver() {
		System.setProperty("webdriver.chrome.driver", PropertiesCache.getInstance().getProperty("service"));
		WebDriver driver = new ChromeDriver();
		log.info("Chrome Launched");
		return driver;
	}

	private static WebDriver initIEDriver() {
		System.setProperty("webdriver.ie.driver", PropertiesCache.getInstance().getProperty("service"));
		
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); 
		WebDriver driver = new InternetExplorerDriver(capabilities);
		log.info("IE Launched");
		return driver;
	}
	private static WebDriver initFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", PropertiesCache.getInstance().getProperty("service"));
		WebDriver driver = new FirefoxDriver();
		log.info("FireFox Launched");
		return driver;
	}
	
	//** Maximizing the browser window
	   public boolean window_maximize(String pageName) {
		   
		  if(pageName.isEmpty())
		  {
			throw new IllegalStateException( "There was no Page found" );
		  }
		  else
		  {
			  driver.manage().window().maximize();
			  return true;
		  }
	   }
	   

 //**  Focus on newly opened window
   public boolean focuson_openwindow()
   {
 	  try
 	  {
 		  //String handle = driver.getWindowHandle();
 		  for (String winHandle : driver.getWindowHandles()) 
 		  {
 		    	driver.switchTo().window(winHandle); 
 		  }
 		 log.info("Focus on Window");
		   return true;
 	  }
 	  catch(Exception e)
 	  {
 	   return false;
 	  }  
   }
  
   /**
    * @Description : Checks if is alert present.
    * @return boolean
    */
   public boolean isAlertPresent() {
       try {
           WebDriverWait wait = new WebDriverWait(driver, 10);
           wait.until(ExpectedConditions.alertIsPresent());
           log.info("Alert Present");
           return true;
       } // try
       catch (Exception Ex) {
           return false;
       } 
   } 
   

   //**Handling alerts
   public boolean Alert_handle(int popuptype, String message)
   { 
 	  //popuptype = 0 --> cancel
 	  //popuptype = 1 --> accept	
 	  //popuptype = 2 --> send_text
 	  //popuptype = 3 --> get_text
 	  try
 	  {
 	    if(driver.switchTo().alert() != null)
 	    {
				switch(popuptype)
 	    	{
 	    	case 0:
 	          driver.switchTo().alert().dismiss(); 
 	    	case 1:
 	    	  driver.switchTo().alert().accept();
 	    	case 2:
   	          driver.switchTo().alert().sendKeys(message);
   	    	case 3:
   	    	  driver.switchTo().alert().getText();
 	    	}	
 	    }
 	  } 
 	  catch(Exception e)
 	  {
 		  return false; 
 	  }
 	  return true;
   }
   
   /**
    *  @Description : Switch to alert.
    */
   public void switchToAlert() {
       try {
           driver.switchTo().alert();
       } 
       catch (Exception e) {
           e.printStackTrace();
       }

   }

   /**
    * @Description : Open URL.
    * @param String: url
    * @return boolean
    */
   public boolean openURL(String url) {
       try {

           driver.get(url);
           maxiWindow();

       } catch (Exception ex) {
           return false;
       }

       return true;
   }

   /**
    * @Description : closeBrowser : close Browser
    * @return void
    */
   public static void closeBrowser() {
       try {
           driver.close();

       } catch (Exception ex) {

       }

   }

     
   /**
    * @Description : Checks if is element enabled.
    * @param Element : elem
    * @return boolean
    */
   public boolean isElementEnabled(String elementName, String pageName) {
       try {
    	   WebElement element = getElement(elementName, pageName);
    	   takeScreenShot(elementName,pageName);
    	   element.isEnabled();

       } catch (Exception ex) {
           return false;
       }
       return true;
   }
   
   /**
    * @Description : Checks if is element displayed.
    * @param Element : elem
    * @return boolean
    */
   public boolean isElementDisplayed(String elementName, String pageName) {
       try {
    	   WebElement element = getElement(elementName, pageName);
    	   takeScreenShot(elementName,pageName);
    	   element.isDisplayed();

       } catch (Exception ex) {
           return false;
       }
       return true;
   }
   
   /**
    * @Description : Gets the all from dropdown.
    * @param Element : elem
    * @return the all from dropdown
    */
   public List<WebElement> getAllFromDropdown(String elementName, String pageName) {
	   WebElement element = getElement(elementName, pageName);
	   Select dropdown = new Select(element);
	   takeScreenShot(elementName,pageName);
	   log.info("Drop Down values returned");
       return dropdown.getOptions();
   }
   
   /**
    * @Description : Action press key.
    * @param key : key
    * @return boolean
    */
   public boolean actionPressKey(Keys key) {
       try {
           Actions actions = new Actions(driver);
           actions.sendKeys(key).perform();
           log.info("Enter Key Pressed");
           // actions.keyDown(key).keyDown(key).build().perform();
           // actions.click(webElement).build().perform();
       } catch (Exception ex) {
           return false;
       }
       return true;
   }
   
   /**
    * @Description :Gets the selected option.
    * @param Element : elem
    * @return String
    */
   public String getSelectedOption(String elementName, String pageName) {
       try {
    	   WebElement element = getElement(elementName, pageName);
    	   Select sel = new Select(element);
           String text = sel.getFirstSelectedOption().getText();
           takeScreenShot(elementName,pageName);
           log.info("Drop Down Value (" + text + ") "+"Selected in Page (" +pageName +") "+ "Element ("+elementName+")");
           return text;

       } catch (Exception ex) {
           return null;
       }

   }

   
   /**
    * @Description :Action mouse over.
    * @param Element :element
    * @return boolean
    */
   public boolean actionMouseOver(String elementName, String pageName) {
       try {
    	   WebElement element = getElement(elementName, pageName);
    	   Actions actions = new Actions(driver);
           actions.moveToElement(element).perform();
           takeScreenShot(elementName,pageName);
       } catch (Exception ex) {
           return false;
       }
       return true;

   }
   
   /**
    * @Description :Toggle check box.
    * @param Element : element
    * @param String : action
    * @return boolean
    */
   public boolean toggleCheckBox(String elementName, String pageName,String recordId) {
       try {
    	   WebElement element = getElement(elementName, pageName);
    	   takeScreenShot(elementName,pageName);
    	   String action = getPageDetailsData(pageName, elementName, recordId);
           if ((action.equalsIgnoreCase("Check") && !isSelected(elementName, pageName)) || (action.equalsIgnoreCase("Uncheck") && isSelected(elementName, pageName))) {
               element.click();
           }

           element.click();

       } 
       catch (Exception ex) {

           return false;
       }

       return true;
   }

   /**
    * @Description :Select values from dropdown.
    * @param Element : element
    * @param String elementName, String pageName,String recordId
    * @return boolean
    */
   public boolean selectValueFromDropdown(String elementName, String pageName,String recordId) {
       try {
    	   WebElement element = getElement(elementName, pageName);
    	   String subStrings = getPageDetailsData(pageName, elementName, recordId);
           String text;
           Select sel = new Select(element);
           List<WebElement> options = sel.getOptions();
           Actions act = new Actions(driver);
           //sel.deselectAll();
           act.keyDown(Keys.CONTROL).build().perform();
           
               for (WebElement opt : options) {
                   text = element.getText().trim();
                   if (text.equalsIgnoreCase(subStrings)) {
                	   takeScreenShot(elementName,pageName);
                	   opt.click();                       
                       log.info("Drop Down Value (" + text + ") "+"Selected in Page (" +pageName +") "+ "Element ("+elementName+")");
                       break;
                   }
               }
           
           act.keyUp(Keys.CONTROL).build().perform();
           

       } catch (Exception ex) {
           return false;
       }
       return true;

   }
 
   /**
    * @Description : Gets the text.
    * @param Element: element
    * @return String
    */
   public String getText(String elementName, String pageName) {
	   WebElement element = getElement(elementName, pageName);
	   takeScreenShot(elementName,pageName);
	   String text = element.getText().trim();      
	   log.info("Value (" + text + ") "+"fetched in Page (" +pageName +") "+ "Element ("+elementName+")");
       return text;
   }
   /**
    * @Description : Checks if is text displayed.
    * @param Element: elem
    * @return boolean
    */
   public boolean isTextDisplayed(String elementName, String pageName) {
       try {
    	   String text = getText(elementName, pageName);
           if (text.isEmpty() || text.length() == 0) {
               return false;
           } else {
               return true;
           }

       } catch (Exception ex) {

           return false;
       }

   }
   
   /**
    *  @Description :Close all windows.
    */
   public static void closeAllWindows() {
       try {

           driver.quit();

       } catch (Exception ex) {

       }

   }

   /**
    * @Description :Gets the title.
    * @return String
    */
   public String getTitle() {
       try {
    	   log.info("Page Title (" + driver.getTitle()+")");
           return (driver.getTitle());

       } catch (Exception ex) {
           return null;
       }

   }

   /**
    * @Description : Checks if is selected.
    * @param Element: elem
    * @return boolean
    */
   public boolean isSelected(String elementName, String pageName) {
	   WebElement element = getElement(elementName, pageName);
	   takeScreenShot(elementName,pageName);
    	   if (element.isSelected()){
           return true;
    	   }
        else {
           return false;
       }
   }

   /**
    * @Description : Checks if is enabled.
    * @param Element: elem
    * @return boolean
    */
   public boolean isEnabled(String elementName, String pageName) {
	   WebElement element = getElement(elementName, pageName);
	   takeScreenShot(elementName,pageName);
	   if (element.isEnabled()){
           return true;
    	   }
        else {
           return false;
       }
   }

 
   
   /**
    * @Description : Gets the innerHTML of a webelement.
    * @param Element: element
    * @return String
    */
   public String getinnerHTML(String elementName, String pageName) {
	   WebElement element = getElement(elementName, pageName);
	   takeScreenShot(elementName,pageName);
	   String text = element.getAttribute("innerHTML").trim();   
       return text;
   }
   
   /**
    * @Description : Gets the innerText of a webelement.
    * @param Element: element
    * @return String
    */
   public String getinnerText(String elementName, String pageName) {
	   WebElement element = getElement(elementName, pageName);
	   takeScreenShot(elementName,pageName);
	   String text = element.getAttribute("innerText").trim();   
       return text;
   }
   
   /**
    * @Description :Press enter.
    * @return boolean
    */
   public boolean pressEnter() {
       try {
           Actions act = new Actions(driver);
           act.keyDown(Keys.ENTER);
           act.keyUp(Keys.ENTER);
           log.info("Enter Pressed");
       } catch (Exception ex) {

           return false;
       }

       return true;
   }
   
   /**
    * @Description : Gets the text.
    * @param Element: element
    * @return String
    */
   public String getAttribute(String elementName, String pageName,String recordId) {
	   WebElement element = getElement(elementName, pageName); // returns webelement
	   String Attributename = getPageDetailsData(pageName, elementName, recordId); // return string mentioned in xml
	   
	   String retval = null;
   	try {
       String Atttext = element.getAttribute(Attributename).trim();
       if(Atttext!=null)
       	retval = Atttext;
   	}    	
   	catch(NullPointerException e ) {
   		
   	}
		return retval;
   }
	
   public void takeScreenShot(String elementName, String pageName ){
	   WebElement element = getElementForScreenShot(elementName, pageName); // returns webelement
	   // Get entire page screenshot
	   File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	   BufferedImage fullImg = null;
	   
	try {
		fullImg = ImageIO.read(screenshot);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	   // Get the location of element on the page
	   Point point = element.getLocation();

	   // Get width and height of the element
	   int eleWidth = element.getSize().getWidth();
	   int eleHeight = element.getSize().getHeight();

	   // Crop the entire page screenshot to get only element screenshot
	   BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
	       eleWidth, eleHeight);
	   try {
		ImageIO.write(eleScreenshot, "png", screenshot);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	   // Copy the element screenshot to disk
	   File screenshotLocation = new File("E:/New folder/SBTSTRAVELSs/screenshots/"+System.currentTimeMillis()+"_"+pageName+"_"+elementName+".png");
	   try {
		FileUtils.copyFile(screenshot, screenshotLocation);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   public  String getPageDetailsData(String pageName, String elementName , String recordId) {
		try {
			String value = "";
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db
					.parse(new File(
							"E:\\New folder\\SBTSTRAVELSs\\src\\com\\resource\\loginandverifydata.xml"));

			NodeList nodeList1 = document.getElementsByTagName("page");

			for (int i = 0; i < nodeList1.getLength(); i++) {
				if(pageName.equalsIgnoreCase(nodeList1.item(i).getAttributes().getNamedItem("name").getNodeValue()))
				{
					Element question = (Element) nodeList1.item(i);
					NodeList optionList = question.getElementsByTagName("record");
					for (int j = 0; j < optionList.getLength(); ++j) {
						if(recordId.equalsIgnoreCase(optionList.item(j).getAttributes().getNamedItem("id").getNodeValue())) {
							value = optionList.item(j).getAttributes().getNamedItem(elementName).getNodeValue();
						}
					}

				}
				return value;  

			} 
		}catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
