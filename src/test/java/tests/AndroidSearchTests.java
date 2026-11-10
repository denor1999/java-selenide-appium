package tests;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class AndroidSearchTests extends TestBase{

    @Override
    protected String platform() {
        return "android";
    }

    @Test
    void searchWikipedia() {
        SelenideElement skip = $(AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button"));
        if (skip.exists())
            skip.click();

        $(AppiumBy.accessibilityId("Search Wikipedia")).should(exist).click();
        $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Appium");
        $(AppiumBy.id("org.wikipedia.alpha:id/page_list_item_title")).should(exist);
    }
}
