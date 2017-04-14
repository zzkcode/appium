package com.zzk.test;


import io.appium.java_client.AppiumDriver;  
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
//import io.appium.java_client.remote.Appium;

import java.io.File;  
import java.net.URL;  
import java.util.List;  

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;  
import org.openqa.selenium.remote.CapabilityType;  
import org.openqa.selenium.remote.DesiredCapabilities;
  
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContactManagerTest {
    private AppiumDriver<WebElement> driver; 
    
    //在所有方法执行之前执行
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	//在所有方法执行之后执行
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//在每个测试方法执行之前执行
	@Before
	public void setUp() throws Exception {
        // set up appium 
        File classpathRoot = new File(System.getProperty("user.dir"));  
        File appDir = new File(classpathRoot, "/apps");  
        File app = new File(appDir, "ContactManager.apk");  
        DesiredCapabilities capabilities = new DesiredCapabilities();  
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");  
        capabilities.setCapability("platformName", "Android");  
        capabilities.setCapability("deviceName","Android Emulator");  
        capabilities.setCapability("platformVersion", "4.3");  //18
        capabilities.setCapability("app", app.getAbsolutePath());  
        capabilities.setCapability("appPackage", "com.example.android.contactmanager");  
        capabilities.setCapability("appActivity", ".ContactManager");  
        driver = new AppiumDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);  
	}

	//在每个测试方法执行之后执行
	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void addContact() {
        WebElement el = driver.findElement(By.name("Add Contact"));  
        el.click();
		List<WebElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");  
//        textFieldsList.get(0).sendKeys("Some Name");  
//        textFieldsList.get(2).sendKeys("Some@example.com");  
      textFieldsList.get(0).sendKeys("Some");  
      textFieldsList.get(2).sendKeys("Some@example.com");
//        driver.swipe(100, 500, 100, 100, 2);  
        driver.findElementByName("Save").click();  
//		fail("Not yet implemented");
	}

}
