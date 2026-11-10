package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.AuthConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
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
        String user = config.username();
        String key = config.key();
        String platform = System.getProperty("platform", "");

        return switch (platform) {
            case "android" -> androidDriver(user, key);
            case "ios" -> iosDriver(user, key);
            default -> throw new IllegalStateException("STOP: pass -Dplatform=android or -Dplatform=ios");
        };
    }

    private static WebDriver androidDriver(String user, String key) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAutomationName("UIAutomator2");
        options.setPlatformName("Android");
        options.setDeviceName("Samsung Galaxy S22 Ultra");
        options.setPlatformVersion("12.0");
        options.setApp(androidApp());
        options.setCapability("bstack:options", bstackOptions(user, key, "step-3", "Android Wikipedia search"));
        return new AndroidDriver(hubUrl(), options);
    }

    private static WebDriver iosDriver(String user, String key) {
        XCUITestOptions options = new XCUITestOptions();
        options.setAutomationName("XCUITest");
        options.setPlatformName("iOS");
        options.setDeviceName("iPhone 14");
        options.setPlatformVersion("16");
        options.setApp("bs://sample.app");
        options.setCapability("bstack:options", bstackOptions(user, key, "step-2", "iOS sample Text Button"));
        return new IOSDriver(hubUrl(), options);
    }

    private static String androidApp() {
        String app = firstNonBlank(System.getProperty("app"), config.app());
        if (app == null) {
            throw new IllegalStateException(
                    "STOP: set browserstack.app=bs://… in src/test/resources/browserstack.properties "
                            + "(gitignored; upload Wikipedia alpha APK), or pass -Dbrowserstack.app= / -Dapp=. "
                            + "Do not use bs://sample.app for Android.");
        }
        return app;
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }
        return null;
    }

    private static Map<String, Object> bstackOptions(String user, String key, String buildName, String sessionName) {
        Map<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", user);
        bstackOptions.put("accessKey", key);
        bstackOptions.put("appiumVersion", "2.6.0");
        bstackOptions.put("projectName", "QA.GURU Java Selenide Appium");
        bstackOptions.put("buildName", buildName);
        bstackOptions.put("sessionName", sessionName);
        return bstackOptions;
    }

    private static URL hubUrl() {
        try {
            return URI.create("https://hub.browserstack.com/wd/hub/").toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Browserstack hub URL", e);
        }
    }
}
