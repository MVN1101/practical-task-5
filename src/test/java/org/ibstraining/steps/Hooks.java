package org.ibstraining.steps;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.time.Duration;

public class Hooks {

    private static WebDriver driver;
    private static WebDriverWait explicitWait;


    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getExplicitWait() {
        return explicitWait;
    }

    @BeforeAll
    public static void createDriver() {


    }

    @Before(value ="@site")
    public static void openSite(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("http://localhost:8080");
    }

//    @After(value ="@site")
//    public static void closeSite(){
//        driver.close();
//    }


}

