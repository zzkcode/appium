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
 * 实现学生借书和还书，管理员进行确认的功能
 * 注意：不要设置APP不重新安装，因为每次重新安装可以恢复到初始的数据库，便于数据库不容易混乱；
 * 但也需要注意，在需要利用更改数据库的测试用例上，要在一个测试用例上实现。
 * @author Administrator
 *
 */
public class BorrowReturn {
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
	public void testBorrowReturnBooks() throws Exception {
		loginStudent();
		borrowBooks();
		
//		loginAdmin();
//		returnBooks();
		
//		fail("Not yet implemented");
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

	private void loginStudent() {
		// TODO Auto-generated method stub
		//先清除帐号、密码中的Text
		driver.findElement(By.name("取消")).click();				
		List<AndroidElement> textFieldList = driver.findElementsByClassName("android.widget.EditText"); 
		textFieldList.get(0).sendKeys("zzk");
		textFieldList.get(1).sendKeys("111");
		driver.findElementById("rBtnStudent").click();
		driver.findElement(By.name("登录")).click();
	}

	private void returnBooks() {
		// TODO Auto-generated method stub
		
	}

	private void borrowBooks() throws Exception {
		// TODO Auto-generated method stub
		driver.findElement(By.name("馆藏查询")).click();
		
		driver.findElementById("id_stuSpinnerSearch").click();	
		driver.findElement(By.name("标题")).click();
		//点击输入框，确保光标在输入框中
		driver.findElementById("id_stuSearchContent").click();
		//clear()清除以前的输入，否则当前的输出有可能接着之前的？！
		driver.findElementById("id_stuSearchContent").clear();
		driver.findElementById("id_stuSearchContent").sendKeys("ROS机器人程序设计");
		driver.findElementById("id_stuSearchBtn").click();
		
		List<AndroidElement> elRecyclerList = driver.findElementsByClassName("android.support.v7.widget.RecyclerView");
		System.out.println("recyclerview:"+elRecyclerList.size());
		String asserts = driver.getPageSource();
		if(asserts.contains("共找到0条结果")){
			System.out.println("共找到0条结果");
		}else{
			elRecyclerList.get(0).findElementByClassName("android.widget.RelativeLayout").click();;
			Thread.sleep(2000);
			driver.findElement(By.name("借阅")).click();
			Thread.sleep(2000);
		}
	}

}
