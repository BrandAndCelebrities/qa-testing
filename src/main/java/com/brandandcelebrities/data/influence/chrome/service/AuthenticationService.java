package com.brandandcelebrities.data.influence.chrome.service;

import com.brandandcelebrities.data.influence.chrome.constants.IdentifierType;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

public class AuthenticationService {
    final WebdriverService webdriverService;
    boolean isAuthenticated = false;

    public AuthenticationService(final WebdriverService webdriverService) {
        this.webdriverService = webdriverService;
    }

    public AuthenticationService goToAuthenticationPage() {
        if (!isAuthenticated) {
            webdriverService
                    .goTo("https://influence.kolsquare.com/login")
                    .waitUntilCheckedByPresenceOfSelector(10000, "#auth-login-email-label");
        }
        return this;
    }

    public void performAuthentication(final String login, final String password) {
        if (!isAuthenticated) {
            final Supplier<WebElement> loginSelector = webdriverService.findElementSupplier(IdentifierType.ID, "auth-login-email-label");
            final Supplier<WebElement> passwordSelector = webdriverService.findElementSupplier(IdentifierType.ID, "auth-login-password-label");
            final Supplier<WebElement> submitSelector = webdriverService.findElementSupplier(IdentifierType.CSS, "button[type='submit']");

            webdriverService
                    .clickElement(loginSelector)
                    .sendInputsKeys(loginSelector, login)
                    .clickElement(passwordSelector)
                    .sendInputsKeys(passwordSelector, password)
                    .clickElement(submitSelector);
            webdriverService.setCookies(webdriverService.getWebDriver().manage().getCookies());
            isAuthenticated = true;
        }
    }

    public AuthenticationService injectSession() {
        if (!webdriverService.getCookies().isEmpty()) {
            webdriverService.setCookies(webdriverService.getWebDriver().manage().getCookies());
            webdriverService.goTo("https://influence.kolsquare.com/");
            this.isAuthenticated = true;
        }
        return this;
    }
}
