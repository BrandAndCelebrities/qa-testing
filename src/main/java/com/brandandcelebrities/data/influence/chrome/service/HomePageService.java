package com.brandandcelebrities.data.influence.chrome.service;

public class HomePageService {
    final WebdriverService webdriverService;

    public HomePageService(WebdriverService webdriverService) {
        this.webdriverService = webdriverService;
    }

    public void checkHomePageLocation() {
        this.webdriverService.waitUntilCheckedByUrlToBe(10000, "https://influence.kolsquare.com/");
    }
}
