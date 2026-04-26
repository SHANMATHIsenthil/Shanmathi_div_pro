package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.HashMap;

public class PlaceOrderTestCode {

    WebDriver driver;
    WebDriverWait wait;

    String username = "user_" + System.currentTimeMillis();
    String password = "Test@12345";
    String productName = "Samsung galaxy s6";

    @BeforeClass
    public void setup() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Disable password popup
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/");
    }

    @Test
    public void placeOrderFlow() {

        // ===== SIGN UP =====
        wait.until(ExpectedConditions.elementToBeClickable(By.id("signin2"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")))
                .sendKeys(username);

        driver.findElement(By.id("sign-password")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        System.out.println("Signup successful: " + username);

        // ===== LOGIN =====
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login2"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")))
                .sendKeys(username);

        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        // ===== SELECT PRODUCT =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(productName)
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[@class='name']")
        ));

        // ===== ADD TO CART =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'Add to cart')]")
        )).click();

        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // ===== GO TO CART =====
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[text()='" + productName + "']")
        ));

        // ===== PLACE ORDER =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Place Order']")
        )).click();

        // ===== FILL FORM =====
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")))
                .sendKeys("Shanmathi");

        driver.findElement(By.id("country")).sendKeys("India");
        driver.findElement(By.id("city")).sendKeys("Chennai");
        driver.findElement(By.id("card")).sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("04");
        driver.findElement(By.id("year")).sendKeys("2026");

        // ===== PURCHASE =====
        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h2[text()='Thank you for your purchase!']")
                )
        );

        Assert.assertTrue(successMsg.isDisplayed());

        System.out.println("Order placed successfully");

        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}