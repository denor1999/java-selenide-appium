package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.AuthConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BrowserstackDriver implements WebDriverProvider {
    private static final AuthConfig config = ConfigFactory.create(AuthConfig.class, System.getProperties());

    @NonNull
    @Override
    public WebDriver createDriver(@NonNull Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAutomationName("UiAutomator2");
        options.setPlatformName("Android");
        options.setDeviceName("Samsung Galaxy S22 Ultra");
        options.setPlatformVersion("12.0");
        options.setApp("bs://sample.app");

        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", config.username());
        bstackOptions.put("accessKey", config.key());
        bstackOptions.put("appiumVersion", "2.6.0");
        bstackOptions.put("projectName", "QA.GURU Java Selenide Appium");
        bstackOptions.put("buildName", "step-1");
        bstackOptions.put("sessionName", "Android Wikipedia Search");
        options.setCapability("bstack:options", bstackOptions);

        return new AndroidDriver(hubUrl(), options);
    }

    private static URL hubUrl() {
        try {
            return URI.create("https://hub.browserstack.com/wd/hub/").toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Browserstack hub URL", e);
        }
    }
}
