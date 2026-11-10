package tests;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class IosSampleTests extends TestBase{

    @Override
    protected String platform() {
        return "ios";
    }

    @Test
    void textInputOutput() {
        $(AppiumBy.accessibilityId("Text Button")).click();
        $(AppiumBy.accessibilityId("Text Input")).sendKeys("hello@browserstack.com\n");
        $(AppiumBy.accessibilityId("Text Output")).should(exist);
    }
}
