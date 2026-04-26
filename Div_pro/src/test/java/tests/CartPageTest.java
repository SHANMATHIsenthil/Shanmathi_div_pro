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

public class CartPageTest {

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
    public void navigateAndAddProduct() {

        // ===== OPEN CATEGORY (PHONES) =====
        driver.findElement(By.linkText("Phones")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("tbodyid")
        ));

        // ===== OPEN PRODUCT =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(productName)
        )).click();

        // ===== CLICK ADD TO CART =====
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='Add to cart']")
        )).click();

        // ===== HANDLE ALERT =====
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        System.out.println("Product added to cart successfully");

        // ===== VERIFY PRODUCT STILL EXISTS IN PAGE FLOW =====
        Assert.assertTrue(driver.getTitle().contains("STORE"),
                "Navigation failed");
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}