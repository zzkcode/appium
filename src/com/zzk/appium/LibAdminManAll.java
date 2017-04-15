package com.zzk.appium;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
/**
 * 此Test实现管理员管理全部的功能
 * @author Administrator
 *
 */
public class LibAdminManAll {
	private AndroidDriver<AndroidElement> driver;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot,"/apps");
		File app = new File(appDir,"LibManSys.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME,"");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformVersion", "4.3");
		capabilities.setCapability("app", app.getAbsolutePath());
        /**
         * (重要)֧支持中文+隐藏键盘
         * 使用unicodeKeyboard键盘输入+设置键盘默认隐藏
         */
        capabilities.setCapability("unicodeKeyboard" ,"True");
        capabilities.setCapability("resetKeyboard", "True");
        
		capabilities.setCapability("appPackage", "com.example.administrator.libmansys");
		capabilities.setCapability("appActivity", ".login.LoginActivity");
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void testManAll() {
		loginAdmin();
		
		backupStudents();
		// TODO 各种管理功能还未实现！！！
//		deleteStudents();
//		restoreStudents();
//		fail("Not yet implemented");
	}

	private void restoreStudents() {
		// TODO Auto-generated method stub
	}

	private void deleteStudents() {
		// TODO Auto-generated method stub
		
	}

	private void backupStudents() {
		// TODO Auto-generated method stub
		driver.findElement(By.name("管理全部")).click();
		driver.findElement(By.name("备份全部学生")).click();
		driver.findElement(By.name("确定")).click();
	}

	private void loginAdmin() {
		// TODO Auto-generated method stub
		//先清除帐号、密码中的Text
		driver.findElement(By.name("取消")).click();				
		List<AndroidElement> textFieldList = driver.findElementsByClassName("android.widget.EditText"); 
		textFieldList.get(0).sendKeys("whut");
		textFieldList.get(1).sendKeys("222");
		driver.findElementById("rBtnAdmin").click();
		driver.findElement(By.name("登录")).click();
	}

}
