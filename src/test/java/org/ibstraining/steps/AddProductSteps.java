package org.ibstraining.steps;

import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddProductSteps {

    private WebDriver driver;
    private static WebDriverWait explicitWait;

    String[] productInputArray = new String[3];

    @И("открыта стриница по адресу {string}")
    public void открыта_стриница_по_адресу(String string) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(string);
    }

    @И("выполнено нажатие на выпадающий список {string}")
    public void выполнено_нажатие_на_выпадающий_список(String string) {

//        Открытие выпадающего списка "Песочница"
        WebElement btnSendBox = driver.findElement(By.id("navbarDropdown"));
        btnSendBox.click();
//        Проверка открытия выпадающего списка
        Assertions.assertEquals(
                "navbarDropdown", btnSendBox.getAttribute("id"),
                "Не открылся выпадающий список " + string);
    }

    @И("нажата кнопка Товары")
    public void нажата_кнопка_товары() {

//        Клик по полю "Товары"
        WebElement itemProducts = driver.findElement(By.xpath("//a[text() ='Товары']"));
        itemProducts.click();
        //        Проверка отображения списка товаров
        WebElement titleTable = driver.findElement(By.xpath("//h5[text()='Список товаров']"));
        Assertions.assertEquals(
                "Список товаров",
                titleTable.getText(),
                "Не открылась таблица со списком товаров"
        );
    }


    @И("нажата кнопка Добавить")
    public void нажата_кнопка_добавить() {
//        Нажатие на кнопку "Добавить"
        WebElement btnAddProduct = driver.findElement(By.xpath("//button[text() = 'Добавить']"));
        btnAddProduct.click();

//        Проверка открытия окна добавления товара
        WebElement titleAddProduct = driver.findElement(By.id("editModalLabel"));
        Assertions.assertEquals(
                "editModalLabel",
                titleAddProduct.getAttribute("id"),
                "Не открылось окно добавления товара");
    }

    @И("заполняется поле наименование - {string}")
    public void заполняется_поле_наименование(String productName) {
        WebElement fieldProductName = driver.findElement(By.xpath("//input[@id='name']"));
        fieldProductName.sendKeys(productName);
        productInputArray[0] = productName;
    }

    @И("выбирается тип товара - {string}")
    public void выбирается_тип_товара(String type) {
        WebElement selectType = driver.findElement(By.xpath("//select[@id='type']"));
        selectType.sendKeys(type);
        productInputArray[1] = type;
    }

    @И("устанавливается чекбокс Экзотический - {string}")
    public void устанавливается_чекбокс_экзотический(String isExotic) {
        WebElement checkBoxExotic = driver.findElement(By.xpath("//input[@type='checkbox']"));
        if (isExotic.equals("true")) {
            checkBoxExotic.click();
        }
        productInputArray[2] = isExotic;
    }

    @И("нажата кнопка Сохранить")
    public void нажата_кнопка_сохранить() {
        WebElement btnSave = driver.findElement(By.id("save"));
        btnSave.click();
    }

    @И("проверка корректности добавления товара {string}")
    public void проверка_корректности_добавления_товара(String productName) {

//        Ожидание того, что таблица станет видимой после добавления товара
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")));

        WebElement tableProducts = driver.findElement(By.xpath("//tbody"));
        int countOfProducts = Integer.parseInt(tableProducts.getAttribute("childElementCount"));

//        Создание массива из фактически добавленных в таблицу значений
        int arrayLength = productInputArray.length;
        String[] productValuesArray = new String[arrayLength];
        for (int i = 1; i <= arrayLength; i++) {
            WebElement value = driver.findElement(By.xpath(
                    "//tr[" + countOfProducts + "]/td[" + i + "]"));
            productValuesArray[i - 1] = value.getText();
        }

//        Проверка корректности добавление в таблицу товара
        Assertions.assertArrayEquals(productInputArray, productValuesArray,
                "Товар " + productName + " не был добавлен или добавлен некорректно");
        System.out.println("Товар " + productName + " успешно доваблен");
    }

    @И("удаление добавленной строки")
    public void удаление_добавленной_строки() {
        //        Открытие выпадающего списка "Песочница"
        WebElement btnSendBox = driver.findElement(By.id("navbarDropdown"));
        btnSendBox.click();
        Assertions.assertEquals(
                "navbarDropdown", btnSendBox.getAttribute("id"),
                "Не открылся выпадающий список");

//        Ожидание появления выпадающего списка
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reset")));
        WebElement itemReset = driver.findElement(By.id("reset"));
//        Очистка таблица от внесенных изменений
        itemReset.click();
    }

    @И("закрытие сайта")
    public void закрытие_сайта() {
        driver.quit();
    }
}
