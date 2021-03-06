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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 主要在管理员界面进行测试
 * @author Administrator
 * 执行时间：80.596 seconds (17.5.7)
 */

public class LibTestAdmin {
//	private AppiumDriver<WebElement> driver;
	//AppiumDriver和AndroidDriver的区别？!
	private AndroidDriver<AndroidElement> driver;
	private Utils utils;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
//		driver.quit();
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
		//执行完测试后要退出，否则会出问题
		driver.quit();
	}

	//管理员界面测试
	@Test
	public void testAllTabAdmin() throws Exception {
		loginAdmin();
		viewAdminTabs();
		manBook();
		manStudent();
	}
	
	/**
	 * 管理学生界面下，点击学生“郑志锟”
	 * @throws Throwable
	 */
	private void manStudent() throws Exception {
		// TODO Auto-generated method stub
		driver.findElement(By.name("学生管理")).click();
		String asserts = driver.getPageSource();
		if(asserts.contains("郑志锟")){
			List<AndroidElement> elRecyclerList = driver.findElementsByClassName("android.support.v7.widget.RecyclerView");
			elRecyclerList.get(0).findElementByClassName("android.widget.RelativeLayout").click();
			Thread.sleep(5000);
		}else {
			System.out.println("没有找到郑志锟");
		}
	}
	
	/**
	 * 管理图书界面下，依次点击标题、作者和ISBN
	 * @throws Throwable
	 */
	private void manBook() throws Exception {
		// TODO Auto-generated method stub
		driver.findElement(By.name("图书管理")).click();
		driver.findElementById("id_manSpinnerSearch").click();
		
		driver.findElement(By.name("作者")).click();
		//点击输入框，确保光标在输入框中
		driver.findElementById("id_manSearchContent").click();
		//clear()清除以前的输入，否则当前的输出有可能接着之前的？！
		driver.findElementById("id_manSearchContent").clear();
		driver.findElementById("id_manSearchContent").sendKeys("东野圭吾");
		driver.findElementById("id_manSearchBtn").click();
		
		MySwipe ms = new MySwipe(driver);
		ms.swipe("up", 2000, 2);
		Thread.sleep(1000);
		//TODO 从下向上滑动没有成功，待解决！
		ms.swipe("down", 2000, 2);
		Thread.sleep(1000);
		
//		driver.findElementById("id_manSpinnerSearch").click();
//		driver.findElement(By.name("借阅者")).click();
//		driver.findElementById("id_manSearchContent").click();
//		driver.findElementById("id_manSearchContent").clear();
//		driver.findElementById("id_manSearchContent").sendKeys("郑志锟");
//		driver.findElementById("id_manSearchBtn").click();
//		List<AndroidElement> elRecyclerList = driver.findElementsByClassName("android.support.v7.widget.RecyclerView");
//		String asserts = driver.getPageSource();
//		if(asserts.contains("共找到0条结果")){
//			System.out.println("共找到0条结果");
//		}else{
//			elRecyclerList.get(0).findElementByClassName("android.widget.RelativeLayout").click();
//			Thread.sleep(2000);
//		}
	}

	/**
	 * 管理员登录
	 */
	private void loginAdmin(){
		//先清除帐号、密码中的Text
		driver.findElement(By.name("重置")).click();				
		List<AndroidElement> textFieldList = driver.findElementsByClassName("android.widget.EditText"); 
		textFieldList.get(0).sendKeys("whut");
		textFieldList.get(1).sendKeys("111");
		driver.findElementById("rBtnAdmin").click();
		driver.findElement(By.name("登录")).click();
	}
	
	/**
	 * 浏览管理员主界面所有标签，务必在刚登录管理员界面下执行（不要单独执行）
	 */
	private void viewAdminTabs(){
		driver.findElement(By.name("请求管理")).click();
		driver.findElement(By.name("图书管理")).click();
		driver.findElement(By.name("学生管理")).click();
		driver.findElement(By.name("管理全部")).click();
		driver.findElement(By.name("个人信息")).click();
		driver.findElement(By.name("关于")).click();		
	}
}
