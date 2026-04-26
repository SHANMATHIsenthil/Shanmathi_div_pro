package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

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
    public void loginTest() throws InterruptedException {

        driver.findElement(By.id("login2")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("loginusername")).sendKeys("bowbow");
        driver.findElement(By.id("loginpassword")).sendKeys("bowbow");

        driver.findElement(By.xpath("//button[text()='Log in']")).click();
        Thread.sleep(3000);

        String text = driver.findElement(By.id("nameofuser")).getText();

        Assert.assertTrue(text.contains("Welcome"), "Login Failed");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}