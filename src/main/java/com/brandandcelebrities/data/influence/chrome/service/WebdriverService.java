package com.brandandcelebrities.data.influence.chrome.service;

import com.brandandcelebrities.data.influence.chrome.constants.IdentifierType;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class WebdriverService {
    private final WebDriver webDriver;
    private final Set<Cookie> cookies = new HashSet<>();

    public WebdriverService(final WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriver.manage().window().maximize();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebdriverService goTo(final String url) {
        if (!url.equals(webDriver.getCurrentUrl())) {
            webDriver.get(url);
        }
        return this;
    }

    public Set<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(final Set<Cookie> cookies) {
        this.cookies.addAll(cookies);
    }



    public Supplier<WebElement> findElementSupplier(final IdentifierType identifierType, final String selector) {
        final Supplier<WebElement> elementSupplier;
        switch (identifierType) {
            case CSS:
                elementSupplier = () -> webDriver.findElement(By.cssSelector(selector));
                break;
            case TAG_NAME:
                elementSupplier = () -> webDriver.findElement(By.tagName(selector));
                break;
            case XPATH:
                elementSupplier = () -> webDriver.findElement(By.xpath(selector));
                break;
            case ID:
            default:
                elementSupplier = () -> webDriver.findElement(By.id(selector));
                break;

        }
        return elementSupplier;
    }

    public Supplier<List<WebElement>> findElementsSupplier(final IdentifierType identifierType, final String selector) {
        final Supplier<List<WebElement>>  elementsSupplier;
        switch (identifierType) {
            case CSS:
                elementsSupplier = () -> webDriver.findElements(By.cssSelector(selector));
                break;
            case TAG_NAME:
                elementsSupplier = () -> webDriver.findElements(By.tagName(selector));
                break;
            case XPATH:
                elementsSupplier = () -> webDriver.findElements(By.xpath(selector));
                break;
            case ID:
            default:
                elementsSupplier = () -> webDriver.findElements(By.id(selector));
                break;

        }
        return elementsSupplier;
    }

    public WebdriverService waitUntilCheckedByPresenceOfSelector(final long millis, final String selector) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofMillis(millis));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
        return this;
    }

    public WebdriverService waitUntilCheckedByUrlToBe(final long millis, final String selector) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofMillis(millis));
        wait.until(ExpectedConditions.urlToBe(selector));
        return this;
    }

    public WebdriverService waitUntilCheckedByClickableOfSelector(final long millis, final String selector) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofMillis(millis));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
        return this;
    }

    public WebdriverService waitUntilCheckedByClickableOfSelector(final long millis, final Supplier<WebElement> elementSupplier) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofMillis(millis));
        wait.until(ExpectedConditions.elementToBeClickable(elementSupplier.get()));
        return this;
    }


    public WebdriverService waitUntilCheckedByInvisibilityOfSelector(final long millis, final String selector) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofMillis(millis));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(selector)));
        return this;
    }

    public WebdriverService clickElement(final Supplier<WebElement> elementSupplier) {
        waitUntilCheckedByClickableOfSelector(10000, elementSupplier);
        final Actions actions = new Actions(webDriver);
        actions.moveToElement(elementSupplier.get())
                .click()
                .build()
                .perform();
        return this;
    }

    public WebdriverService sendInputsKeys(final Supplier<WebElement> elementSupplier, final String text) {
        if (!text.equals(elementSupplier.get().getAttribute("value"))) {
            sendKeys(elementSupplier, text);
        }
        return this;
    }

    private void sendKeys(final Supplier<WebElement> elementSupplier, final String text) {
        text.chars().forEach(c -> elementSupplier.get().sendKeys(Character.toString((char) c)));
        final String insertedText = elementSupplier.get().getAttribute("value");
        if (!text.equals(insertedText)) {
            elementSupplier.get().clear();
            elementSupplier.get().sendKeys(text);
        }
    }


    public void quit() {

        close();

        try {
            webDriver.quit();
        } catch (final Exception e) {
            // NOP
        }
    }

    public void close() {

        try {
            webDriver.close();
        } catch (final Exception e) {
            // NOP
        }
    }

}
