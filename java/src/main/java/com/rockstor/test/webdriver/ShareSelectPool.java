package com.rockstor.test.webdriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.commons.io.FileUtils; // Screenshots
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot; 
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select; // Dropdown menu
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.List;
import com.rockstor.test.util.RSProps;


public class ShareSelectPool {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(
				Integer.parseInt(RSProps.getProperty("waitTimeout")), 
				TimeUnit.SECONDS);	
	}

	@Test
	public void testSelectPoolInShare() throws Exception {
		try{

			driver.get(RSProps.getProperty("RockstorVm"));

			// Login 
			WebElement username = driver.findElement(By.id("inputUsername"));
			username.sendKeys("admin");

			WebElement password = driver.findElement(By.id("inputPassword"));
			password.sendKeys("admin");

			WebElement submit = driver.findElement(By.id("sign_in"));
			submit.click();

			//Create 1st Pool

			// Add Pool with Raid 0
			WebElement poolsNav = driver.findElement(By.id("pools_nav"));
			poolsNav.click();

			WebElement addPool = driver.findElement(By.id("add_pool"));
			addPool.click();

			WebElement poolname = driver.findElement(By.id("pool_name"));
			poolname.sendKeys("pool1");

			// Raid Configuration Dropdown box 
			Select raidConfigDroplist = new Select(driver.findElement(
					By.id("raid_level")));   
			raidConfigDroplist.selectByIndex(0);

			//Select Disks CheckBox
			WebElement diskCheckBox1 = driver.findElement(By.id("sdb"));
			diskCheckBox1.click();
			WebElement diskCheckBox2 = driver.findElement(By.id("sdc"));
			diskCheckBox2.click();


			// Create Pool
			WebElement createPool = driver.findElement(By.id("create_pool"));
			createPool.click();


			//Create 2nd Pool

			// Add Pool with Raid 0
			WebElement addPool2 = driver.findElement(By.id("add_pool"));
			addPool2.click();

			WebElement poolname2 = driver.findElement(By.id("pool_name"));
			poolname2.sendKeys("pool2");

			// Raid Configuration Dropdown box 
			Select raidConfigDroplist2 = new Select(driver.findElement(
					By.id("raid_level")));   
			raidConfigDroplist2.selectByIndex(0);

			//Select Disks CheckBox
			WebElement diskCheckBox3 = driver.findElement(By.id("sdd"));
			diskCheckBox3.click();
			WebElement diskCheckBox4 = driver.findElement(By.id("sde"));
			diskCheckBox4.click();

			// Create Pool
			WebElement createPool2 = driver.findElement(By.id("create_pool"));
			createPool2.click();
			
			//wait for the pool to get created
			WebElement poolPage = driver.findElement(By.linkText("pool2"));
			poolPage.click();
			
			///// Create a share

			//Shares navigation bar
			WebElement shareNav = driver.findElement(By.id("shares_nav"));
			shareNav.click();

			//Add share
			WebElement addShareButton = driver.findElement(By.id("add_share"));
			addShareButton.click();

			WebElement shareName = driver.findElement(By.id("share_name"));
			shareName.sendKeys("share1");

			Select selectPoolDroplist = new Select(driver.findElement(By.id("pool_name"))); 
			
			WebElement verifyPoolSelect = driver. findElement(
					By.xpath("//div/form/select/option[contains(.,'pool1') or contains(.,'pool2')]"));
			assertTrue(verifyPoolSelect.getText(),true);
			selectPoolDroplist.selectByIndex(0); 
			
			
			WebElement shareSize = driver.findElement(By.id("share_size"));
			shareSize.sendKeys("100");


			Select selectSizeDroplist = new Select(driver.findElement(By.id("size_format")));   
			selectSizeDroplist.selectByIndex(0); // 0 is KB


			//Submit button to create share
			WebElement shareSubmitButton = driver.findElement(By.id("create_share"));
			shareSubmitButton.click();
			
			//Delete Share
			WebElement sharesNav = driver.findElement(By.id("shares_nav"));
			sharesNav.click();

			WebElement shareRow = driver.findElement(By.xpath("//*[@id='shares-table']/tbody/tr[td[contains(.,'share1')]]"));
			WebElement deleteShare = shareRow.findElement(By.xpath("td/button[contains(@data-name,'share1') and contains(@data-action,'delete')]"));
			deleteShare.click();

			
			//Delete Pool
			
			WebElement poolsNav2 = driver.findElement(By.id("pools_nav"));
			poolsNav2.click();
			
			WebElement poolRowToDelete = driver.findElement(By.xpath("//*[@id='pools-table']/tbody/tr[td[contains(.,'pool1')]]"));
			WebElement deletePool = poolRowToDelete.findElement(By.xpath("td/button[contains(@data-name,'pool1') and contains(@data-action,'delete')]"));
			deletePool.click();


			WebElement poolRow2ToDelete = driver.findElement(By.xpath("//*[@id='pools-table']/tbody/tr[td[contains(.,'pool2')]]"));
			WebElement deletePool2 = poolRow2ToDelete.findElement(By.xpath("td/button[contains(@data-name,'pool2') and contains(@data-action,'delete')]"));
			deletePool2.click();

			
			// Logout 
			WebElement logoutSubmit = driver.findElement(
					By.id("logout_user"));

			logoutSubmit.click();

		}
		catch(Exception e){
			File screenshotFile = ((TakesScreenshot)driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile,
					new File(RSProps.getProperty("screenshotDir") 
							+ "/" + this.getClass().getName()+".png"));
			throw e;

		}

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.quit();
	}


}









