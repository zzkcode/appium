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
 * 主要在学生界面下进行测试,并实现找书的功能
 * @author Administrator
 * 注意：Throwable会捕捉error，但Error是一种严重的问题，应用程序不应该捕捉它。
 * 	   因此，应该用Exception！
 */

public class LibTestStudent {
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

	//学生界面测试
	@Test
	public void testAllTabStudent() throws Exception {
		loginStudent();
		viewStudentTabs();
		findBooks();
	}
	
	/**
	 * 依次点击标题、作者和ISBN,并相应查找书籍
	 */
	private void findBooks() throws Exception {
		// TODO Auto-generated method stub
		driver.findElement(By.name("馆藏查询")).click();
		
		driver.findElementById("id_stuSpinnerSearch").click();	
		driver.findElement(By.name("标题")).click();
		//点击输入框，确保光标在输入框中
		driver.findElementById("id_stuSearchContent").click();
		//clear()清除以前的输入，否则当前的输出有可能接着之前的？！
		driver.findElementById("id_stuSearchContent").clear();
		driver.findElementById("id_stuSearchContent").sendKeys("嫌疑人X的献身");
		driver.findElementById("id_stuSearchBtn").click();
		
		/**
		 * 注意两者是否有区别？？
		 * driver.findElement(By.name(name));
		 * driver.findElementByName(using);
		 */
		
		driver.findElementById("id_stuSpinnerSearch").click();
		driver.findElement(By.name("作者")).click();
		driver.findElementById("id_stuSearchContent").click();
		driver.findElementById("id_stuSearchContent").clear();
		driver.findElementById("id_stuSearchContent").sendKeys("东野圭吾");
		driver.findElementById("id_stuSearchBtn").click();
		MySwipe ms = new MySwipe(driver);
		ms.swipe("up", 2000, 2);
		Thread.sleep(1000);
		//TODO 从下向上滑动没有成功，待解决！
		ms.swipe("down", 2000, 2);
		Thread.sleep(1000);
		
		driver.findElementById("id_stuSpinnerSearch").click();
		driver.findElement(By.name("ISBN")).click();
		driver.findElementById("id_stuSearchContent").click();
		driver.findElementById("id_stuSearchContent").clear();
		driver.findElementById("id_stuSearchContent").sendKeys("100");
		driver.findElementById("id_stuSearchBtn").click();
		
		//进入学生界面的借书端
		//获取的RecyclerView的元素只有一个？？
		List<AndroidElement> elRecyclerList = driver.findElementsByClassName("android.support.v7.widget.RecyclerView");
		System.out.println("recyclerview:"+elRecyclerList.size());
		String asserts = driver.getPageSource();
		if(asserts.contains("共找到0条结果")){
			System.out.println("共找到0条结果");
		}else{
//			System.out.println("Text:"+elRecyclerList.get(0).getId());
			elRecyclerList.get(0).findElementByClassName("android.widget.RelativeLayout").click();;
			Thread.sleep(2000);
		}
	}

	/**
	 * 学生登录
	 */
	private void loginStudent() {
		//先清除帐号、密码中的Text
		driver.findElement(By.name("取消")).click();				
		List<AndroidElement> textFieldList = driver.findElementsByClassName("android.widget.EditText"); 
		textFieldList.get(0).sendKeys("zzk");
		textFieldList.get(1).sendKeys("111");
		driver.findElementById("rBtnStudent").click();
		driver.findElement(By.name("登录")).click();
	}
	
	/**
	 * 浏览学生主界面所有标签，务必在刚登录管理员界面下执行（不要单独执行）
	 */
	private void viewStudentTabs() {
		driver.findElement(By.name("馆藏查询")).click();		
		driver.findElement(By.name("借阅归还")).click();
		driver.findElement(By.name("个人信息")).click();
		driver.findElement(By.name("关于")).click();		
	}
}
