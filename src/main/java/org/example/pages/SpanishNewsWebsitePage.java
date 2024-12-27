// src/main/java/org/example/pages/SpanishNewsWebsitePage.java
package org.example.pages;

import java.time.Duration;
import java.util.List;
import org.example.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SpanishNewsWebsitePage {
  private WebDriver driver;
  private WebDriverWait wait;

  public SpanishNewsWebsitePage(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
  }

  public void open() {
    driver.get(ConfigReader.get("websiteUrl"));
  }

  public void handleCookiesPopup() {
    try {
      WebElement acceptButton =
          wait.until(
              ExpectedConditions.elementToBeClickable(
                  By.xpath(
                      "//*[starts-with(translate(text(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ACCEPT')]")));
      acceptButton.click();
    } catch (Exception e) {
      System.out.println("Cookies popup not displayed or already handled.");
    }
  }

  public String getWebsiteLanguage() {
    WebElement languageButton = driver.findElement(By.xpath("//li[@id='edition_head']/a/span"));
    System.out.println("Language Button Text: " + languageButton.getText());
    return languageButton.getText();
  }

  public void navigateToOpinionSection() {
    driver.findElement(By.linkText("Opinión")).click();
  }

  public List<WebElement> fetchArticles() {
    return driver.findElements(By.cssSelector("article"));
  }

  public String extractTitle(WebElement article) {
    return article.findElement(By.cssSelector("h2")).getText();
  }
}
