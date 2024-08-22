package org.ibstraining.steps;

import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExperimentalSteps {

    private static WebDriver driver;
    private static WebDriverWait explicitWait;

//    @И("открыта стриница по адресу {string}")
//    public void открытие_сайта_по_адресу(String website){
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        driver.get(website);
//
//
////        Открытие выпадающего списка "Песочница"
//        WebElement btnSendBox = driver.findElement(By.id("navbarDropdown"));
//        btnSendBox.click();
//        Assertions.assertEquals(
//                "navbarDropdown", btnSendBox.getAttribute("id"),
//                "Не открылся выпадающий список Песочницы");
//
//    }

    @Допустим("открыта стриница по адресу {string}")
    public void открыта_стриница_по_адресу(String string) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(string);


//        Открытие выпадающего списка "Песочница"
        WebElement btnSendBox = driver.findElement(By.id("navbarDropdown"));
        btnSendBox.click();
        Assertions.assertEquals(
                "navbarDropdown", btnSendBox.getAttribute("id"),
                "Не открылся выпадающий список Песочницы");
    }
}
