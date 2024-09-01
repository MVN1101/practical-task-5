package org.ibstraining.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;

public class DriverManager {

    private static WebDriver driver;
    private static Properties props = MyProperties.createProperties();

    public static WebDriver initDriver() {

        if ("remote".equalsIgnoreCase(props.getProperty("type.driver"))) {

            try {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName(props.getProperty("type.browser"));
                capabilities.setVersion("109.0");
                capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                        "enableVNC", true,
                        "enableVideo", false
                ));
                URL selenoidUrl = new URL(props.getProperty("selenoid.url"));
                driver = new RemoteWebDriver(selenoidUrl, capabilities);
                driver.get(props.getProperty("selenoid.sandbox"));

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

        } else {
            driver = new ChromeDriver();
            driver.get(props.getProperty("local.sandbox"));
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;
    }

}
