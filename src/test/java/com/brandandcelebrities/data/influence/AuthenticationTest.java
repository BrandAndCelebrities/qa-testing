package com.brandandcelebrities.data.influence;

import com.brandandcelebrities.data.influence.chrome.service.AuthenticationService;
import com.brandandcelebrities.data.influence.chrome.service.HomePageService;
import com.brandandcelebrities.data.influence.chrome.service.WebdriverService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthenticationTest {

    protected static WebdriverService webdriverService;

    @BeforeAll
    public static void prepareOnceBefore() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        webdriverService = new WebdriverService(new ChromeDriver());
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
