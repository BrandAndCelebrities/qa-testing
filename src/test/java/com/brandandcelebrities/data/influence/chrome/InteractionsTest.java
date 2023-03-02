package com.brandandcelebrities.data.influence.chrome;

import com.brandandcelebrities.data.influence.AuthenticationTest;
import com.brandandcelebrities.data.influence.chrome.constants.IdentifierType;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InteractionsTest extends AuthenticationTest {

    public InteractionsTest() {
        super();
    }

    @BeforeEach
    void goToHomePage() {
        webdriverService
                .goTo("https://influence.kolsquare.com/")
                .waitUntilCheckedByClickableOfSelector(10000, "button[data-testid='select']");
    }

    @Test
    @Order(2)
    void testHomePageToKolPage() {
        assertEquals("https://influence.kolsquare.com/", webdriverService.getWebDriver().getCurrentUrl());
        final Supplier<WebElement> selectOptionButtonSelector = webdriverService.findElementSupplier(IdentifierType.CSS, "button[data-testid='select']");
        final Supplier<WebElement> searchSelector = webdriverService.findElementSupplier(IdentifierType.CSS, "div.kol-search-bar-tags-container input[type='text']");
        final Supplier<WebElement> submitSelector = webdriverService.findElementSupplier(IdentifierType.CSS, "button.kol-search-bar-button-search[type='button']");
        final Supplier<WebElement> selectOptionPeopleSelector = webdriverService.findElementSupplier(IdentifierType.CSS, "ul li[value='people']");
        final Supplier<List<WebElement>> profileCardsSelector = webdriverService.findElementsSupplier(IdentifierType.CSS, ".engine-container_columns .result-item .Card_flexRow__3YAK1 a[href]");

        webdriverService
                .clickElement(selectOptionButtonSelector)
                .clickElement(selectOptionPeopleSelector)
                .waitUntilCheckedByInvisibilityOfSelector(10000, "div.bnc_field_select_options_mask.deployed")
                .clickElement(searchSelector)
                .sendInputsKeys(searchSelector, "therock")
                .clickElement(submitSelector)
                .waitUntilCheckedByPresenceOfSelector(10000, ".engine-container_columns .result-item .Card_flexRow__3YAK1 a[href]");
        Optional<WebElement> profileCardElement = profileCardsSelector.get().stream().filter(p -> p.getAttribute("href").contains(String.format("/profiles/%s?", "152728"))).findFirst();
        profileCardElement.ifPresent(WebElement::click);
        webdriverService.waitUntilCheckedByClickableOfSelector(10000, ".header_tabs__1TPcL button.header_noBtn__WPYn7.header_tab__1sZl7");
        assertTrue(webdriverService.getWebDriver().getCurrentUrl().contains("152728"));
    }

    @Test
    @Order(3)
    void testCreationOfNewCampaign() {
        assertEquals("https://influence.kolsquare.com/", webdriverService.getWebDriver().getCurrentUrl());
        final Supplier<WebElement> campaignsSelector = webdriverService.findElementSupplier(IdentifierType.ID, "campaigns");
        final Supplier<WebElement> campaignButtonSelector = webdriverService.findElementSupplier(IdentifierType.CSS, ".selections-list-new__create-list-container .Button2-module_button__3YIM1");
        final Supplier<WebElement> projectNameSelector = webdriverService.findElementSupplier(IdentifierType.ID, "create-new-project-name");
        final Supplier<WebElement> startDateSelector = webdriverService.findElementSupplier(IdentifierType.ID, "modal-campaign-start-date");
        final Supplier<WebElement> endDateSelector = webdriverService.findElementSupplier(IdentifierType.ID, "modal-campaign-end-date");
        final Supplier<WebElement> globalBudgetSelector = webdriverService.findElementSupplier(IdentifierType.ID, "create-new-project-global-budget");
        final Supplier<WebElement> globalTurnoverSelector = webdriverService.findElementSupplier(IdentifierType.ID, "create-new-project-goal");
        final Supplier<WebElement> mentionsSelector = webdriverService.findElementSupplier(IdentifierType.CSS, ".CampaignModaleForm_modalCampaignTags__2Qzo9 input");
        final Supplier<WebElement> hashtagsSelector = webdriverService.findElementSupplier(IdentifierType.CSS, "body > div.MuiDialog-root.Modal2-module_dialog__2AOHM.Modal2-module_default__KlwqE > div.MuiDialog-container.MuiDialog-scrollPaper > div > div > div.Modal2-module_body__2yX4u > div > div > div.CampaignModaleForm_modalCampaignTags__2Qzo9 > div:nth-child(2) > div > div > div > div > div > div.Input2-module_content__2PMI1 > input");
        final Supplier<WebElement> submitCampaignSelector = webdriverService.findElementSupplier(IdentifierType.CSS, "button[title='Create your campaign']");

        webdriverService
                .clickElement(campaignsSelector)
                .waitUntilCheckedByPresenceOfSelector(10000, ".selections-list-new__create-list-container .Button2-module_button__3YIM1")
                .clickElement(campaignButtonSelector)
                .clickElement(projectNameSelector)
                .sendInputsKeys(projectNameSelector, "Campaign test")
                .clickElement(startDateSelector)
                .sendInputsKeys(startDateSelector, "01/03/2023")
                .clickElement(endDateSelector)
                .sendInputsKeys(endDateSelector, "01/05/2023")
                .clickElement(globalBudgetSelector)
                .sendInputsKeys(globalBudgetSelector, "10000")
                .clickElement(globalTurnoverSelector)
                .sendInputsKeys(globalTurnoverSelector, "5000")
                .clickElement(mentionsSelector)
                .sendInputsKeys(mentionsSelector, "@therock")
                .clickElement(hashtagsSelector)
                .sendInputsKeys(hashtagsSelector, "#fitness")
                .clickElement(submitCampaignSelector);
        assertEquals("https://influence.kolsquare.com/projects", webdriverService.getWebDriver().getCurrentUrl());
    }

    @Test
    @Order(4)
    void testDeletionOfAllCampaigns() {
        assertEquals("https://influence.kolsquare.com/", webdriverService.getWebDriver().getCurrentUrl());
        final Supplier<WebElement> campaignsSelector = webdriverService.findElementSupplier(IdentifierType.ID, "campaigns");

        final Supplier<List<WebElement>> campaignsCreateSelector = webdriverService.findElementsSupplier(IdentifierType.CSS, ".selection-pane-list-new__list-container span[aria-describedby='mui-32386']");

       webdriverService
                .clickElement(campaignsSelector);
/*
        campaignsCreateSelector.get().forEach(c -> webdriverService.clickElement(() -> c));
*/
        assertEquals("https://influence.kolsquare.com/projects", webdriverService.getWebDriver().getCurrentUrl());
    }
}
