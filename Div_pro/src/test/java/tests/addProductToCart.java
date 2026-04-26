package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class addProductToCart {

    WebDriver driver;
    WebDriverWait wait;

    String productName = "Samsung galaxy s6";

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/");
    }

    @Test
    public void addProductToCart() {

        // ===== WAIT AND CLICK PRODUCT =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(productName)
        )).click();

        // ===== WAIT AND CLICK ADD TO CART =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='Add to cart']")
        )).click();

        // ===== HANDLE ALERT SAFELY =====
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        System.out.println("Product added to cart");

        // ===== GO TO CART =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("cartur")
        )).click();

        // ===== VERIFY PRODUCT IN CART =====
        boolean productPresent = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//td[text()='" + productName + "']")
                )
        ).isDisplayed();

        Assert.assertTrue(productPresent, "Product NOT found in cart!");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}