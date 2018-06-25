package rashi.testngtatoc;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.google.common.annotations.VisibleForTesting;

public class TatocTestng {
	
	WebDriver driver;
	
	public TatocTestng(){
		System.setProperty("webdriver.chrome.driver", "C:/rashi/chromedriver/chromedriver.exe");
		this.driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc");	
		
	}
	
	@Test
	public void Tatoc_Home() {
		
		driver.findElement(By.linkText("Basic Course")).click();
		
	}
	
	@Test (dependsOnMethods= {"Tatoc_Home"})
	public void Grid_Gate() {
		driver.findElement(By.className("greenbox")).click();
	}
	
	@Test (dependsOnMethods= {"Grid_Gate"})
	public void Frame_Dungeon() {
		driver.switchTo().frame(0);
		WebElement box1 = driver.findElement(By.id("answer"));
		String Box1_color = box1.getAttribute("class");
		String Box2_color = "";
	   while(!Box1_color.equals(Box2_color)){
		  
		   driver.switchTo().defaultContent();
		    driver.switchTo().frame(0);
		    driver.findElement(By.cssSelector("a")).click();
		    driver.switchTo().frame(0);
		    Box2_color=driver.findElement(By.id("answer")).getAttribute("class");
		     }
	   driver.switchTo().defaultContent();
	   driver.switchTo().frame(0).findElement(By.linkText("Proceed")).click();
	   
	}
	
	@Test (dependsOnMethods= {"Frame_Dungeon"})
	public void Drag_Arround() {
		 
		WebElement dragbox = driver.findElement(By.id("dragbox"));
		WebElement dropbox = driver.findElement(By.id("dropbox"));
		Actions actions  = new Actions(driver);
		actions.dragAndDrop(dragbox, dropbox).build().perform();
		driver.findElement(By.linkText("Proceed")).click();
		
	}
	
	@Test (dependsOnMethods= {"Drag_Arround"})
	public void Popup_Windows() {
    driver.findElement(By.linkText("Launch Popup Window")).click();
	    
	    String parentwindow = driver.getWindowHandle();
	    String subWindow = null;
	    Set<String> windows = driver.getWindowHandles();
	    Iterator itr = windows.iterator();
	    while(itr.hasNext()){
	    	subWindow = (String) itr.next();
	    }
	    driver.switchTo().window(subWindow);
	    driver.findElement(By.id("name")).sendKeys("Rashi Gupta");
	    driver.findElement(By.id("submit")).click();
	    driver.switchTo().window(parentwindow);
	    driver.findElement(By.linkText("Proceed")).click();
	}
	
	@Test (dependsOnMethods= {"Popup_Windows"})
	public void Token()
	{
		 driver.findElement(By.linkText("Generate Token")).click();
		    String token_text = driver.findElement(By.id("token")).getText();
		    String token = token_text.substring(token_text.indexOf(":")+2);
		    Cookie cookie = new Cookie("Token",token);
		    driver.manage().addCookie(cookie);
		    driver.findElement(By.linkText("Proceed")).click();
	}
  
	@AfterMethod
	public void check() {
		Assert.assertFalse(driver.getCurrentUrl().equals("http://10.0.1.86/tatoc/error"),"Error found");
	   
	}
	
	

}



