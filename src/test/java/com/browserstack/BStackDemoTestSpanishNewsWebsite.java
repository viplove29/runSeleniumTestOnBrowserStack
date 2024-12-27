package com.browserstack;

import java.util.*;
import org.example.pages.SpanishNewsWebsitePage;
import org.example.utils.FileUtilsHelper;
import org.example.utils.WordProcessor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BStackDemoTestSpanishNewsWebsite extends SeleniumTest {
  @Test
  public void verifySpanishSite() throws Exception {
    SpanishNewsWebsitePage spanishNewsPage = new SpanishNewsWebsitePage(driver);

    spanishNewsPage.open();

    spanishNewsPage.handleCookiesPopup();

    Assert.assertFalse(
        spanishNewsPage.getWebsiteLanguage().contains("English"),
        "Page text is in English but not in spanish");

    spanishNewsPage.navigateToOpinionSection();
    List<WebElement> articles = spanishNewsPage.fetchArticles();
    List<String> titles = FileUtilsHelper.processArticles(articles);
    WordProcessor.translateTitles(titles);
  }
}
