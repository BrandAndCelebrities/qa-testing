package com.brandandcelebrities.data.influence;

import com.brandandcelebrities.data.influence.chrome.service.AuthenticationService;
import com.brandandcelebrities.data.influence.chrome.service.HomePageService;
import com.brandandcelebrities.data.influence.chrome.service.WebdriverService;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class AuthenticationTest {

    protected static WebdriverService webdriverService;
    protected static boolean isLocal = false;

    @BeforeAll
    public static void prepareOnceBefore() throws MalformedURLException {
        final WebDriver driver;
        if (isLocal) {
            driver = new ChromeDriver();
        } else {
            System.setProperty("webdriver.chrome.driver", "/selenium/chromedriver");
            // Spécifiez l'URL de la machine virtuelle distante
            String remoteUrl = "http://10.132.0.116:4444/wd/hub";

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
            chromeOptions.addArguments("--headless")
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-gpu")
                    .addArguments("--ignore-certificate-errors")
                    .addArguments("--remote-debugin-port=9222")
                    .addArguments("--screen-size=1366x768") // DO NOT CHANGE
                    .addArguments("--lang=en-US")
                    .setExperimentalOption("prefs", ImmutableMap.<String, Object>builder()
                            .put("profile.default_content_setting_values.notifications", 2)
                            .build());

            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            // Créez un pilote WebDriver distant pour lancer le navigateur sur la machine virtuelle distante
            driver = new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
        }
        webdriverService = new WebdriverService(driver);
        final AuthenticationService authenticationService = new AuthenticationService(webdriverService);
        authenticationService
                .injectSession()
                .goToAuthenticationPage()
                .performAuthentication("test+agence@kolsquare.com", "kolsquare1234");
    }

    @Test
    @Order(1)
    void testCheckAuthentication() {
        final HomePageService homePageService = new HomePageService(webdriverService);
        homePageService.checkHomePageLocation();
    }

    @AfterAll
    public static void teardownOnceAfter() {
        webdriverService.quit();
    }

}
