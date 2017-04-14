package com.zzk.appium;
/**
 * 滑动操作（上下左右）
 */
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
public class MySwipe {
	private AndroidDriver<AndroidElement> driver;
	int x;
	int y;
	
	public MySwipe(AndroidDriver<AndroidElement> driver){
		this.driver = driver;
		this.x = driver.manage().window().getSize().width;
		this.y = driver.manage().window().getSize().height;
	}
	
	/**
	 * 滑动（以String确定方向）
	 * @param direction 滑动方向
	 * @param duration 滑动次数
	 */
	public void swipe(String direction,int duration,int num){
		switch(direction.toLowerCase()){
		case "up":
			this.swipeToUp(duration, num);
			break;
		case "down":
			this.swipeToDown(duration, num);
			break;
		case "left":
			this.swipeToLeft(duration, num);
			break;
		case "right":
			this.swipeToRight(duration, num);
			break;
		default:
			System.out.println("up/down/left/right");
		}
	}
	
	public void swipeToUp(int duration, int num) {   
	    for (int i = 0; i < num; i++) {  
	        driver.swipe(x/2,7*y/8,x/2,y/8,duration);  
//	        Thread.sleep(2000); 
	    }  
	}  
	  
	public void swipeToDown(int duration , int num) {   
	    for (int i = 0; i < num; i++) {  
	    	driver.swipe(x/2,y/8,x/2,7*y/8,duration);   
//	        Thread.sleep(2000); 
	    }  
	}  
	  
	public void swipeToLeft(int duration, int num) {   
	    for (int i = 0; i < num; i++) {  
	    	driver.swipe(7*x/8,y/2,x/8,y/2,duration);   
//	        Thread.sleep(2000);
	    }  
	}  
	  
	public void swipeToRight(int duration, int num) {  
	    for (int i = 0; i < num; i++) {  
	    	driver.swipe(x/8,y/2,7*x/8,y/2,duration);  
//	        Thread.sleep(2000);  
	    }  
	}  
}
