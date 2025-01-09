package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUppAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void shouldSendCardOrder() {
        driver.get("http://localhost:9999");
        WebElement formElement = driver.findElement(By.cssSelector("form"));
        formElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        formElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",resultElement.getText().trim());
    }

    @Test
    void shouldValidateName1() {
        driver.get("http://localhost:9999");
        WebElement findElement = driver.findElement(By.cssSelector("form"));
        findElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivanov Ivan");
        findElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",resultElement.getText().trim());
    }
    @Test
    void shouldValidateName2() {
        driver.get("http://localhost:9999");
        WebElement findElement = driver.findElement(By.cssSelector("form"));
        findElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        findElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Поле обязательно для заполнения",resultElement.getText().trim());
    }

    @Test
    void shouldValidatePhoneNomber1() {
        driver.get("http://localhost:9999");
        WebElement findElement = driver.findElement(By.cssSelector("form"));
        findElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        findElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Поле обязательно для заполнения",resultElement.getText().trim());
    }

    @Test
    void shouldValidatePhoneNomber2() {
        driver.get("http://localhost:9999");
        WebElement findElement = driver.findElement(By.cssSelector("form"));
        findElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        findElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("#79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",resultElement.getText().trim());
    }


    @Test
    void shouldValidateCheckBox() {
        driver.get("http://localhost:9999");
        WebElement findElement = driver.findElement(By.cssSelector("form"));
        findElement.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        findElement.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        driver.findElement(By.cssSelector("[role='button']")).click();
        WebElement resultElement = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        assertTrue(resultElement.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",resultElement.getText().trim());
    }
}
