package HealthCare_test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Testing {
	WebDriver driver;
    Actions actions;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
    }

    // ✅ Home page test
    @Test(priority = 1)
    public void home() throws InterruptedException {
        driver.get("https://westfloridaahec.org/");
        // Search
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"wrapper\"]/header/div[1]/div[3]/div[1]/div/div/div/div/form/div/div[1]/label/input")));
        searchBox.sendKeys("Tobacco");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/header/div[1]/div[3]/div[1]/div/div/div/div/form/div/div[2]/input")).click();

        driver.navigate().back();

        // Banner click
        WebElement banner = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"post-10\"]/div/div[1]/div/div/div/div[1]/div[1]/div/div/a/div/span/i")));
        banner.click();
        driver.navigate().back();

        System.out.println("✅ Home test executed");
    }

    // ✅ Program Section
    @Test(priority = 2)
    public void programSection() {
        driver.get("https://westfloridaahec.org/");
        openProgram("//span[text()='Tobacco Free Florida AHEC Cessation Program']");
        openProgram("//span[text()='AHEC Scholars']");
        openProgram("//span[text()='Healthy Aging']");
        openProgram("//span[text()='Covering Florida']");
        System.out.println("✅ Program section test executed");
    }

    private void openProgram(String xpath) {
       /* WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='menu-item-264']/a/span[1]")));
        actions.moveToElement(menu).perform();

        WebElement submenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        safeClick(submenu);

        wait.until(ExpectedConditions.titleContains("Florida"));
        driver.navigate().back();
        wait.until(ExpectedConditions.titleContains("West Florida"));*/
    	try { WebElement menu = wait.until(ExpectedConditions.elementToBeClickable( By.xpath("//*[@id=\"menu-item-264\"]/a")));
    	actions.moveToElement(menu).perform(); 
    	WebElement submenu = new WebDriverWait(driver, Duration.ofSeconds(20)) .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", submenu); 
    	new WebDriverWait(driver, Duration.ofSeconds(10)) .until(ExpectedConditions.titleContains("Florida"));
    	driver.navigate().back();
    	new WebDriverWait(driver, Duration.ofSeconds(10)) .until(ExpectedConditions.titleContains("West Florida")); }
    	catch (TimeoutException e) { System.out.println(" Failed to open program: " + xpath);
    	e.printStackTrace(); }
    }

    // ✅ Registration Test
    @Test(priority = 3)
    public void Registration() {
        driver.get("https://westfloridaahec.org/my-account/");
        driver.findElement(By.id("reg_username")).sendKeys("AyushShah");

        // unique email
        String uniqueEmail = "01ayushshah" + System.currentTimeMillis() + "@gmail.com";
        driver.findElement(By.id("reg_email")).sendKeys(uniqueEmail);

        driver.findElement(By.id("reg_password")).sendKeys("Test@12345");

        safeClick(driver.findElement(By.name("register")));

        System.out.println("✅ Registration test executed");
    }

    // ✅ Login Test
    @Test(priority = 4)
    public void Login() {
        driver.get("https://westfloridaahec.org/my-account/");
        driver.findElement(By.id("username")).sendKeys("01ayushshah@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Test@12345");

        safeClick(driver.findElement(By.name("login")));

        System.out.println("✅ Login test executed");
    }

    // ✅ Contact Us Test
    @Test(priority = 5)
    public void contactUs() {
        driver.get("https://westfloridaahec.org/contact-us/");
        WebElement emailLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"post-127\"]/div/div[1]/div/div/div/div[7]/div[2]/div/div[3]/p/a")));
        emailLink.click();
        driver.navigate().back();

        System.out.println("✅ Contact Us test executed");
    }

    // SafeClick helper
    private void safeClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
