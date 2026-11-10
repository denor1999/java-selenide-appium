package tests;

import com.codeborne.selenide.Configuration;
import drivers.BrowserstackDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;


public abstract class TestBase {

    @BeforeAll
    static void configureSelenide() {
        Configuration.browser = BrowserstackDriver.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;
    }

    @BeforeEach
    void setup() {
        System.setProperty("platform", platform());
        open();
    }

    protected abstract String platform();

    @AfterEach
    void teardown() {
        closeWebDriver();
    }
}
