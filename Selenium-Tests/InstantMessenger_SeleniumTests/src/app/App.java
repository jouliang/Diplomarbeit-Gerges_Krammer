/**
 * 
 */
package app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Joulian This class tests the UI of the Ionic-Instantmessenger
 */
public class App {
	final static String USERNAME = "koko";
	final static String NEWUSERNAME = "neuerkoko";
	final static String PASSWORD = "koko2";
	final static String NEWPASSWORD = "koko1";
	final static String GROUPNAME = "HTL-Informatik";
	// GROUPMEMEBER stands for the user, who you can add to a group or remove from a
	// group
	final static String GROUPMEMBER = "test";

	final static String INPUTLOGINUSERNAME = "loginUsername";
	final static String INPUTLOGINPASSWORD = "loginPassword";
	
	final static String INPUTUSERNAME = "username";
	final static String INPUTOLDPASSWORD = "oldPassword";
	final static String INPUTNEWPASSWORD = "newPassword";
	final static String INPUTCONFIRMPASSWORD = "confirmPassword";
	final static String INPUTPASSWORDTODELETE = "passwordToDelete";
	
	
	final static String BTNLOGIN = "login";
	final static String BTNBACK = "back";
	final static String BTNNAVIGATETONEWGROUP = "navigatToNewGroup";
	final static String BTNCREATEGROUP = "createGroup";
	final static String BTNADDUSERTOGROUP = "addUserToGroup";
	final static String BTNREMOVEUSERFROMGROUPUSERSPAGE = "removeUserFromGroupUsersPage";
	final static String BTNREMOVEUSERFROMGROUP = "removeUserFromGroup";
	final static String BTNNAVIGATEUPDATEUSERNAMEPAGE = "navigateUpdateUsernamePage";
	final static String BTNUPDATEUSERNAME = "updateUsername";
	final static String BTNNAVIGATEUPDATEPASSWORDPAGE = "navigateUpdatePasswordPage";
	final static String BTNCHANGEPASSWORD = "changePassword";
	final static String BTNDELETEACCOUNT = "deleteAccount";
	final static String BTNNAVIGATEDELETEACCOUNT = "navigateDeleteAccountPage";
	
	final static String MENUNEWGROUP = "Neue Gruppe";
	final static String MENUADDUSERTOGROUP = "User zu Gruppe hinzufügen";
	final static String MENUDElETEUSERFROMGROUP = "User aus Gruppe löschen";
	final static String MENUSETTINGS = "Einstellungen";

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
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		driver.get(baseUrl);
		driver.manage().window().maximize();

		try {
			doRegister(driver);
			doLogin(driver, "", "");
			goToChat(driver);
			goBack(driver);
			addNewGroup(driver);
			addUserToGroup(driver);
			removeUserFromGroup(driver);
			goToSettings(driver);
			changeUsername(driver);
			goToSettings(driver);
			changePassword(driver);
			doLogout(driver);
			doLogin(driver, App.NEWUSERNAME, App.NEWPASSWORD);
			goToSettings(driver);
			deleteUser(driver);
			// scrollDown(driver);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method registers a new user
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void doRegister(WebDriver driver) throws InterruptedException {
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTLOGINUSERNAME + "']/input")).sendKeys(App.USERNAME);
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTLOGINPASSWORD + "']/input")).sendKeys(App.PASSWORD);

		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-button[@id='register']")).click();
		Thread.sleep(2000);
	}

	/**
	 * This method login a user in the Instant-Messenger app
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void doLogin(WebDriver driver, String username, String password) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTLOGINUSERNAME + "']/input")).sendKeys(username);
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTLOGINPASSWORD + "']/input")).sendKeys(password);

		Thread.sleep(2000);

		driver.findElement(By.xpath("//ion-button[@id='" + BTNLOGIN + "']")).click();
	}

	/**
	 * This method opens a chat
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void goToChat(WebDriver driver) throws InterruptedException {

		Thread.sleep(2000);

		driver.findElement(By.xpath("//ion-label[contains(.,'müllerm')]")).click();
	}

	/**
	 * This method go back to a page
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void goBack(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);

		driver.findElement(By.xpath("//ion-back-button[@id='" + BTNBACK + "']/button")).click();
	}

	/**
	 * This method adds a new group with the users
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void addNewGroup(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);

		driver.findElement(By.xpath("//ion-label[contains(.,'" + MENUNEWGROUP + "')]")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-list/ion-item/ion-label")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-button[@id='" + BTNNAVIGATETONEWGROUP + "']")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//input")).sendKeys(App.GROUPNAME);
		driver.findElement(By.xpath("//ion-button[@id='" + BTNCREATEGROUP + "']")).click();
	}

	/**
	 * This method adds an user to a group
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void addUserToGroup(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'" + MENUADDUSERTOGROUP + "')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'" + App.GROUPMEMBER + " [false]')]")).click();
		driver.findElement(By.xpath("//ion-label[contains(.,'" + GROUPNAME + " [false]')]")).click();
		driver.findElement(By.xpath("//ion-button[@id='" + BTNADDUSERTOGROUP + "']")).click();
	}

	/**
	 * This method removes an user from a group
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void removeUserFromGroup(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'" + MENUDElETEUSERFROMGROUP + "')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'" + App.GROUPNAME + " [false]')]")).click();
		driver.findElement(By.xpath("//ion-button[@id='" + BTNREMOVEUSERFROMGROUPUSERSPAGE + "']")).click();
		Thread.sleep(2000);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'" + App.GROUPMEMBER + " [false]')]")).click();
		driver.findElement(By.xpath("//ion-button[@id='" + BTNREMOVEUSERFROMGROUP + "']")).click();
	}

	/**
	 * This method goes to the setting page
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void goToSettings(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'" + MENUSETTINGS + "')]")).click();
	}

	/**
	 * This method changes the username of the user
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void changeUsername(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-button[@id='" + BTNNAVIGATEUPDATEUSERNAMEPAGE + "']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-input[@id='" + INPUTUSERNAME + "']/input")).sendKeys(NEWUSERNAME);
		driver.findElement(By.xpath("//ion-button[@id='" + BTNUPDATEUSERNAME + "']")).click();
	}

	/**
	 * This method changes the password of the user who is logged in
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void changePassword(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-button[@id='" + BTNNAVIGATEUPDATEPASSWORDPAGE + "']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTOLDPASSWORD + "']/input")).sendKeys(App.PASSWORD);
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTNEWPASSWORD + "']/input")).sendKeys(App.NEWPASSWORD);
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTCONFIRMPASSWORD + "']/input")).sendKeys(App.NEWPASSWORD);
		driver.findElement(By.xpath("//ion-button[@id='" + App.BTNCHANGEPASSWORD + "']")).click();
	}

	public static void deleteUser(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-button[@id='" + App.BTNNAVIGATEDELETEACCOUNT + "']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-input[@id='" + App.INPUTPASSWORDTODELETE + "']/input")).sendKeys(App.NEWPASSWORD);
		driver.findElement(By.xpath("//ion-button[@id='" + App.BTNDELETEACCOUNT + "']")).click();
	}
	
	/**
	 * This function does a logout
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void doLogout(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ion-label[contains(.,'Logout')]")).click();
	}

	/**
	 * This method scrolls down the page
	 * 
	 * @param driver
	 * @throws InterruptedException
	 */
	public static void scrollDown(WebDriver driver) throws InterruptedException {
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.xpath("//div/div/div")).getSize());
		// Actions actions = new Actions(driver);
		// actions.moveByOffset(50,
		// 50).build().perform();//(driver.findElement(By.xpath("//div/div/div")),
		// 1311,639).build().perform();
		// JavascriptExecutor jse = (JavascriptExecutor)driver;
		// jse.executeScript("window.scrollBy(0, 495)");
	}
}
