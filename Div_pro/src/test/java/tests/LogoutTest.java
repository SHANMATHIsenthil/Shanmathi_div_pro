package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.HashMap;

public class LogoutTest {

    WebDriver driver;
    WebDriverWait wait;

    String username = "user_" + System.currentTimeMillis();
    String password = "Test@12345";

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/");
    }

    @Test
    public void loginAndLogout() {

        // ===== SIGNUP =====
        WebElement signBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("signin2")));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();", signBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("sign-username"))).sendKeys(username);
        driver.findElement(By.id("sign-password")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        System.out.println("Signup successful");

        // ===== LOGIN =====
        WebElement loginBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("login2")));
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();", loginBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("loginusername"))).sendKeys(username);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("nameofuser")));
        System.out.println("Login successful");

        // ===== LOGOUT =====
        wait.until(ExpectedConditions.elementToBeClickable(
            By.id("logout2"))).click();

        // ===== VERIFY LOGOUT =====
        boolean isLoggedOut = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("login2"))).isDisplayed();

        Assert.assertTrue(isLoggedOut, "Logout failed!");
        System.out.println("Logout successful");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}