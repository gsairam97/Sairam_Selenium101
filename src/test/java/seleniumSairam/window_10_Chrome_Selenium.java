package seleniumSairam;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.lambdatest.tunnel.Tunnel;

public class window_10_Chrome_Selenium{
    WebDriver driver;
    Tunnel t;
    String URL = "https://www.lambdatest.com/selenium-playground/";
    
    @Parameters({"browserVersion", "platform","username","access_key"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(String browserVersion,String platform,String username,String access_key ) throws Exception { 
    	
    	ChromeOptions browserOptions = new ChromeOptions();
    	browserOptions.addArguments("--ignore-certificate-errors");
		browserOptions.setPlatformName(platform);
		browserOptions.setBrowserVersion(browserVersion);
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username",username);
		ltOptions.put("accessKey", access_key);
		ltOptions.put("build", "Build Selenium java 101 Chrome - "+platform);
		ltOptions.put("name", "TC Run Selenium java 101 Chrome -" + platform);
		ltOptions.put("network",true);
		ltOptions.put("console",true);
		ltOptions.put("visual", true);
		ltOptions.put("w3c", true);
		browserOptions.setCapability("LT:Options", ltOptions);


		//create tunnel instance
		t = new Tunnel();
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user", username);
		options.put("key", access_key);

		//start tunnel
		t.start(options);
	
		driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), browserOptions);

		Thread.sleep(3000);
		System.out.println("Started session for Chrome" );
		
    }

    @Test(priority = 1, timeOut = 20000)
    public void testSimpleFormDemo() throws InterruptedException {
		System.out.println("Started senario 1");
		driver.get(URL);
		Thread.sleep(3000);
        // Step 1: Open Simple Form Demo
        driver.findElement(By.linkText("Simple Form Demo")).click();
        
        // Step 2: Validate URL contains "simple-form-demo"
        Assert.assertTrue(driver.getCurrentUrl().contains("simple-form-demo"));
        
        // Step 3: Enter message in text box and validate output
        String message = "Welcome to LambdaTest";
        WebElement inputBox = driver.findElement(By.id("user-message"));
        inputBox.sendKeys(message);
        driver.findElement(By.id("showInput")).click();
        WebElement displayMessage = driver.findElement(By.id("message"));
        Assert.assertEquals(displayMessage.getText(), message);
    }

    @Test(priority = 2 , timeOut = 20000)
    public void testDragAndDropSlider() throws InterruptedException {  
		System.out.println("Started senario 2");
		driver.get(URL);
		Thread.sleep(3000);
		// Step 1: Open Drag & Drop Sliders
        driver.findElement(By.linkText("Drag & Drop Sliders")).click();
        
        // Step 2: Drag slider to 95 and validate
        WebElement slider = driver.findElement(By.xpath("//input[@type='range'][@value='15']"));
        Actions move = new Actions(driver);
        move.dragAndDropBy(slider, 215, 0).perform();
        WebElement rangeValue = driver.findElement(By.id("rangeSuccess"));
        Assert.assertEquals(rangeValue.getText(), "95");
    }

    @Test(priority = 3 , timeOut = 20000)
    public void testInputFormSubmit() throws InterruptedException{
		System.out.println("Started senario 3");
		driver.get(URL);
		Thread.sleep(3000);
		// Step 1: Open Input Form Submit
        driver.findElement(By.linkText("Input Form Submit")).click();
        
        // Step 2: Click Submit without filling form and validate error
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        WebElement username = driver.findElement(By.xpath("//input[@id='name']"));
        String validationMessage = username.getAttribute("validationMessage");
        System.out.println("Error message is "+ validationMessage);
        Assert.assertEquals(validationMessage,"Please fill out this field.");
        
        // Step 3: Fill form details and submit
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Rohan Panda");
        driver.findElement(By.xpath("//input[@id='inputEmail4']")).sendKeys("rohan.panda@example.com");
        driver.findElement(By.xpath("//input[@id='inputPassword4']")).sendKeys("P@ssword!");
        driver.findElement(By.xpath("//input[@id='company']")).sendKeys("MBG Private Limited");
        driver.findElement(By.xpath("//input[@id='websitename']")).sendKeys("www.testing.com");
        driver.findElement(By.xpath("//input[@id='inputCity']")).sendKeys("Hyderabad");
        driver.findElement(By.xpath("//input[@id='inputAddress1']")).sendKeys("Hitech City");
        driver.findElement(By.xpath("//input[@id='inputAddress2']")).sendKeys("Madhapur Road");
        driver.findElement(By.xpath("//input[@id='inputState']")).sendKeys("Telangana");
        driver.findElement(By.xpath("//input[@id='inputZip']")).sendKeys("500 079");
        new Select(driver.findElement(By.xpath("//select[@name='country']"))).selectByVisibleText("India");
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        
        // Step 4: Validate success message
        String AlrtTxt1 = driver.findElement(By.xpath("//p[@class='success-msg hidden']")).getText();
        Assert.assertEquals(AlrtTxt1,"Thanks for contacting us, we will get back to you shortly.");
       
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
          }
    }
}



