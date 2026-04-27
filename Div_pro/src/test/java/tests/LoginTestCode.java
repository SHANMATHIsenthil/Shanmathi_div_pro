package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class LoginTestCode
{
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void loginTest() {

        // Click Login button
        WebElement loginBtn = driver.findElement(By.id("login2"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);

        // Wait for login modal to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        // Enter credentials — update these if account doesn't exist
        driver.findElement(By.id("loginusername")).sendKeys("bowbow");
        driver.findElement(By.id("loginpassword")).sendKeys("bowbow");

        // Click Log in
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        // Wait for welcome message to appear (up to 10 seconds)
        WebElement nameEl = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser"))
        );

        // Assert login success
        String text = nameEl.getText();
        Assert.assertTrue(text.contains("Welcome"), "Login Failed");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}