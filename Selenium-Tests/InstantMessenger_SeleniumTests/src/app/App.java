/**
 * 
 */
package app;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author root
 *
 */
public class App {
	final static String username = "koko";
	final static String password = "koko2";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.setProperty("webdriver.gecko.driver",
				"/home/joulian/Desktop/Diplomarbeit-2018_19/Diplomarbeit-Gerges_Krammer/Selenium-Tests/geckodriver-v0.24.0-linux64/geckodriver");
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.cache.disk.enable", false);
		profile.setPreference("browser.cache.memory.enable", false);
		profile.setPreference("browser.cache.offline.enable", false);
		profile.setPreference("network.http.use-cache", false);
		profile.setPreference("security.fileuri.strict_origin_policy", false);
		capability.setCapability("firefox_profile", profile);
		
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://localhost:8000/index.html";
		String corsUrl = "https://addons.mozilla.org/de/firefox/addon/cors-everywhere/";
		driver.get(corsUrl);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		driver.get(baseUrl);
		driver.manage().window().maximize();
		
		try {
			doLogin(driver);
			goToChat(driver);
			goBack(driver);
			addNewGroup(driver);
			addUserToGroup(driver);
			//scrollDown(driver);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void doLogin(WebDriver driver) throws InterruptedException {
		driver.findElement(By.name("ion-input-0")).sendKeys(App.username);
		driver.findElement(By.name("ion-input-1")).sendKeys(App.password);

		Thread.sleep(2000);

		Actions actions = new Actions(driver);
		WebElement element = driver.findElement(By.xpath("//ion-button[@id='login']"));
		actions.moveToElement(element).click().build().perform();
	}

	public static void goToChat(WebDriver driver) throws InterruptedException {

		Thread.sleep(2000);

		driver.findElement(By.xpath("//ion-label[contains(.,'müllerm')]")).click();
	}
	
	public static void goBack(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//ion-back-button[@id='back']/button")).click();
	}
	
	public static void addNewGroup(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//ion-label[contains(.,'Neue Gruppe')]")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-list/ion-item/ion-label")).click();;
		//driver.findElement(By.xpath("//ion-item[2]/ion-label")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-button[@id='navigatToNewGroup']")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input")).sendKeys("HTL-Informatik");
		driver.findElement(By.xpath("//ion-button[@id='createGroup']")).click();
	}
	
	public static void addUserToGroup(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'User zu Gruppe hinzufügen')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-item[2]/ion-label")).click();
		driver.findElement(By.xpath("//ion-list[2]/ion-item[2]/ion-label")).click();
		driver.findElement(By.xpath("//ion-button[@id='addUserToGroup']")).click();
	}
	
	public static void scrollDown(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.xpath("//div/div/div")).getSize());
		Actions actions = new Actions(driver);
		//actions.moveByOffset(50, 50).build().perform();//(driver.findElement(By.xpath("//div/div/div")), 1311,639).build().perform();
		//JavascriptExecutor jse = (JavascriptExecutor)driver;
		//jse.executeScript("window.scrollBy(0, 495)");
	}
}
