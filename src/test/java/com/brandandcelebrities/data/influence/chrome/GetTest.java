package com.brandandcelebrities.data.influence.chrome;

import com.brandandcelebrities.data.influence.AuthenticationTest;
import org.junit.jupiter.api.Test;

public class GetTest extends AuthenticationTest {

    public GetTest() {
        super();
    }

    @Test
    void getHomePage() {
        webdriverService
                .goTo("https://influence.kolsquare.com/")
                .waitUntilCheckedByClickableOfSelector(10000, "button[data-testid='select']");
    }

    @Test
    void getMyKOLsPage() {
        webdriverService
                .goTo("https://influence.kolsquare.com/my-kols/manage")
                .waitUntilCheckedByClickableOfSelector(10000, "div.MuiTabs-root");
    }

    @Test
    void getReport() {
        webdriverService
                .goTo("https://influence.kolsquare.com/dashboard/listening")
                .waitUntilCheckedByClickableOfSelector(10000, "div.Listening_content__3g2ub");
    }

    @Test
    void getSettings() {
        webdriverService
                .goTo("https://influence.kolsquare.com/account/infos")
                .waitUntilCheckedByClickableOfSelector(10000, "div.MuiTabs-root");
    }
}
