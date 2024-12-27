package com.browserstack;

import java.util.*;
import org.example.pages.SpanishNewsWebsitePage;
import org.example.utils.FileUtilsHelper;
import org.example.utils.WordProcessor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BStackDemoSpanishNewsWebsiteTest extends SeleniumTest {
  @Test
  public void verifySpanishSite() throws Exception {
    SpanishNewsWebsitePage spanishNewsPage = new SpanishNewsWebsitePage(driver);
    // Open the  spanish news page
    spanishNewsPage.open();
    spanishNewsPage.handleCookiesPopup();

    // 1 .Ensure that the website's text is displayed in Spanish.
    Assert.assertFalse(
        spanishNewsPage.getWebsiteLanguage().contains("English"),
        "Page text is in English but not in spanish");

    // 2 . Scrape Articles from the Opinion Section
    spanishNewsPage.navigateToOpinionSection();
    List<WebElement> articles = spanishNewsPage.fetchArticles();

    // 3.Translate Article Headers
    // 4.Analyze Translated Headers
    List<String> titles = FileUtilsHelper.processArticles(articles);
    WordProcessor.translateTitles(titles);
  }
}
