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
import io.appium.java_client.android.AndroidKeyCode;
/**
 * 此Test实现管理员管理全部的功能
 * @author Administrator
 *
 */
public class LibTestAdminManAll {
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

	/**
	 * 管理全部中，备份删除恢复学生较为直观
	 * @throws Exception
	 */
	@Test
	public void testManAll() throws Exception {
		loginAdmin();

		addStudent();
		addBook();
		
		manAllStudents();
		manAll();
//		fail("Not yet implemented");
	}

	private void manAll() throws Exception {
		// TODO Auto-generated method stub
		//先确定在管理员主界面下才执行，但切记加break，以免进入死循环
		while(driver.currentActivity().equals(".admin.AdminMainActivity")) {
			driver.findElement(By.name("管理全部")).click();
			driver.findElement(By.name("删除全部图书")).click();
			driver.findElement(By.name("取消")).click();
			driver.findElement(By.name("删除全部学生")).click();
			driver.findElement(By.name("取消")).click();
			driver.findElement(By.name("恢复全部图书")).click();
			driver.findElement(By.name("取消")).click();
			driver.findElement(By.name("恢复全部学生")).click();
			driver.findElement(By.name("取消")).click();
			driver.findElement(By.name("备份全部图书")).click();
			driver.findElement(By.name("取消")).click();
			driver.findElement(By.name("备份全部学生")).click();
			driver.findElement(By.name("取消")).click();
			Thread.sleep(1000);
			break;
		}
	}

	private void addBook() throws Exception {
		// TODO Auto-generated method stub
		driver.findElement(By.name("管理全部")).click();
		driver.findElement(By.name("添加图书")).click();
		Thread.sleep(500);
		List<AndroidElement> editTextList = driver.findElementsByClassName("android.widget.EditText");
		editTextList.get(0).sendKeys("看见");
		editTextList.get(1).sendKeys("柴静");
		editTextList.get(2).sendKeys("广西师范大学出版社");
		editTextList.get(3).sendKeys("200");
		editTextList.get(4).sendKeys("《看见》是知名记者和主持人柴静讲述央视十年历程的自传性作品。");
		driver.findElement(By.name("保存")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.name("图书管理")).click();
		driver.findElementById("id_manSpinnerSearch").click();		
		driver.findElement(By.name("标题")).click();
		//点击输入框，确保光标在输入框中
		driver.findElementById("id_manSearchContent").click();
		//clear()清除以前的输入，否则当前的输出有可能接着之前的？！
		driver.findElementById("id_manSearchContent").clear();
		driver.findElementById("id_manSearchContent").sendKeys("看见");
		driver.findElementById("id_manSearchBtn").click();
		List<AndroidElement> elRecyclerList = driver.findElementsByClassName("android.support.v7.widget.RecyclerView");
		String asserts = driver.getPageSource();
		if(asserts.contains("共找到0条结果")){
			System.out.println("共找到0条结果");
		}else{
			elRecyclerList.get(0).findElementByClassName("android.widget.RelativeLayout").click();
			Thread.sleep(2000);
		}		
	}

	private void addStudent() throws Exception {
		// TODO Auto-generated method stub
		driver.findElement(By.name("管理全部")).click();
		driver.findElement(By.name("添加学生")).click();
		Thread.sleep(500);
		List<AndroidElement> editTextList = driver.findElementsByClassName("android.widget.EditText");
		editTextList.get(0).sendKeys("东野圭吾");
		editTextList.get(1).sendKeys("dygw");
		editTextList.get(2).sendKeys("111");
		driver.findElement(By.name("保存")).click();
		Thread.sleep(1000);
		while(!driver.currentActivity().equals(".login.LoginActivity")) {
			//java_client3.0版本以后使用pressKeyCode方法，之前的版本使用sendKeyEvent方法
			driver.pressKeyCode(AndroidKeyCode.BACK);
			System.out.println("点击返回一下");
		}
		loginStudent();
		driver.findElement(By.name("个人信息")).click();
		Thread.sleep(1000);
	}

	private void loginStudent() {
		// TODO Auto-generated method stub
		driver.findElement(By.name("取消")).click();				
		List<AndroidElement> editTextList = driver.findElementsByClassName("android.widget.EditText"); 
		editTextList.get(0).sendKeys("dygw");
		editTextList.get(1).sendKeys("111");
		driver.findElementById("rBtnStudent").click();
		driver.findElement(By.name("登录")).click();
	}

	private void manAllStudents() throws Exception {
		// TODO Auto-generated method stub
		backupStudents();
		// TODO 各种管理功能还未实现！！！
		driver.findElement(By.name("学生管理")).click();
		deleteStudents();
		driver.findElement(By.name("学生管理")).click();
		Thread.sleep(500);
		restoreStudents();
		driver.findElement(By.name("学生管理")).click();
		Thread.sleep(1000);
	}

	private void restoreStudents() {
		// TODO Auto-generated method stub
		driver.findElement(By.name("管理全部")).click();
		driver.findElement(By.name("恢复全部学生")).click();
		driver.findElement(By.name("确定")).click();			
	}

	private void deleteStudents() {
		// TODO Auto-generated method stub
		driver.findElement(By.name("管理全部")).click();
		driver.findElement(By.name("删除全部学生")).click();
		driver.findElement(By.name("确定")).click();	
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
