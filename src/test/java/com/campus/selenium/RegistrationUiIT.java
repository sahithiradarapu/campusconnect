package com.campus.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Selenium UI tests. Run with:  mvn verify -Pselenium   (needs Google Chrome installed). */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationUiIT {
    @LocalServerPort int port;
    WebDriver driver;
    String base;

    @BeforeEach
    void setUp() {
        ChromeOptions o = new ChromeOptions();
        o.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(o);
        base = "http://localhost:" + port;
    }

    @AfterEach
    void tearDown() { if (driver != null) driver.quit(); }

    private void fill(String name, String roll) {
        driver.get(base + "/register");
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("rollNo")).sendKeys(roll);
        driver.findElement(By.id("email")).sendKeys("test@campus.edu");
        driver.findElement(By.id("submit")).click();
    }

    /** Explicit wait: returns the text of the #msg element once the result page has loaded. */
    private String msg() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msg"))).getText();
    }

    @Test
    void tc1_homePageShowsEvents() {
        driver.get(base + "/");
        assertTrue(driver.findElement(By.id("events")).getText().contains("Hackathon 2026"));
    }

    @Test
    void tc2_validRegistrationShowsConfirmation() {
        fill("Test User", "UI-" + System.nanoTime());
        assertTrue(msg().contains("Registered successfully"));
    }

    @Test
    void tc3_duplicateRegistrationShowsError() {
        String roll = "DUP-" + System.nanoTime();
        fill("Test User", roll);
        msg();                       // wait until the first registration is done
        fill("Test User", roll);
        assertTrue(msg().contains("Already registered"));
    }

    @Test
    void tc4_emptyNameShowsValidationMessage() {
        fill("", "EMPTY-" + System.nanoTime());
        assertTrue(msg().contains("Name is required"));
    }
}
