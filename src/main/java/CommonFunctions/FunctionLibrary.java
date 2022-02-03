package CommonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
	public static WebDriver driver;

	//method for launch browser
	public static WebDriver startBrowser() throws Throwable
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dileep.Challa\\OneDrive\\Desktop\\eclipse december 2021\\Hybrid_ERP\\CommonDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();

		}
		else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\Dileep.Challa\\OneDrive\\Desktop\\eclipse december 2021\\Hybrid_ERP\\CommonDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().deleteAllCookies();
		}
		else
		{
			Reporter.log("Browser value not matching",true);
		}
		return driver;
	}

	//method for url
	public static void openApplication(WebDriver driver) throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}

	//method for wait for element
	public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String testdata)
	{
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(testdata));
		if(locatortype.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}
	}

	//method for type action
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata)
	{
		if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
		}
		else if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
		}
	}

	//method for click action
	public static void clickAction(WebDriver driver,String locatortype,String locatorvalue)
	{
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}
		else if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
		}
	}

	//method for validating title
	public static void validateTitle(WebDriver driver,String expectedtitle)
	{
		String actualtitle = driver.getTitle();
		try {
			Assert.assertEquals(actualtitle, expectedtitle,"Title is not matching");
		}
		catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}

	//method for closing browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	
	//method for capture data into notepad
	public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable
	{
		String suppliernumber="";
		if(locatortype.equalsIgnoreCase("name"))
		{
			suppliernumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			suppliernumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("C:\\Users\\Dileep.Challa\\OneDrive\\Desktop\\eclipse december 2021\\Hybrid_ERP\\CaptureData\\supplier.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(suppliernumber);
		bw.flush();
		bw.close();
	}
	
	//method for suppliertable
	public static void suppliertable(WebDriver driver,String testdata) throws Throwable
	{
		FileReader fr = new FileReader("C:\\Users\\Dileep.Challa\\OneDrive\\Desktop\\eclipse december 2021\\Hybrid_ERP\\CaptureData\\supplier.txt");
		BufferedReader br = new BufferedReader(fr);
		String expectednumber = br.readLine();
		int colNum = Integer.parseInt(testdata);
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(expectednumber);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(5000);
		WebElement table = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("web-table")));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(int i=1;i<rows.size();i++)
		{
			String actualnumber = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+colNum+"]/div/span/span")).getText();
			Assert.assertEquals(actualnumber, expectednumber,"Supplier is not matching");
			System.out.println(actualnumber+"       "+expectednumber);
			break;
			
		}
		
	}
	
	//method for mouse over operation
	public static void mouseOver(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//body/div[2]/div[2]/div[1]/div[1]/ul[1]/li[2]/a[1]"))).perform();
		Thread.sleep(4000);
		ac.moveToElement(driver.findElement(By.xpath("//body/div[2]/div[2]/div[1]/div[1]/ul[1]/li[2]/ul[1]/li[1]/a[1]"))).click().perform();
		
		
	}
	
	//method for stock table
	public static void stockTable(WebDriver driver,String testData) throws Throwable
	{
		
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(testData);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		Thread.sleep(5000);
		WebElement table = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("web-table1")));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(int i=1;i<rows.size();i++)
		{
			String actualdata = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr/td[4]/div/span/span")).getText();
			Assert.assertEquals(testData, actualdata,"Category Name is not matching");
			break;
		}
	}
	
	//while generating report i want date and time
	//date and time method
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy_mm_dd    hh_mm_ss");
		return df.format(date);
		
	}
}


