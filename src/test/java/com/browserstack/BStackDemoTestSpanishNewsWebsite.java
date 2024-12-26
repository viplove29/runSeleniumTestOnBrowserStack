package com.browserstack;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BStackDemoTestSpanishNewsWebsite extends SeleniumTest {

  @Test
  public void addProductToCart() throws Exception {
    // Step 1: Visit the website
//    driver.get("https://elpais.com/");

    try {
      // Open the target website
      driver.get("https://elpais.com/"); // Replace with the target website's URL

//      Thread.sleep(5000);
//
//      Alert alert = driver.switchTo().alert(); // switch to alert
//
//      String alertMessage= driver.switchTo().alert().getText(); // capture alert message
//
//      System.out.println(alertMessage); // Print Alert Message
//      Thread.sleep(5000);
//      alert.accept();

      // Wait for the cookies popup to appear
      WebDriverWait wait =
          new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
      WebElement acceptButton =
          wait.until(
              ExpectedConditions.elementToBeClickable(
                  By.xpath("//*[@id=\"didomi-notice-agree-button\"]/span")));

      // Click the "Accept" button
      acceptButton.click();
      System.out.println("Cookies accepted.");

      // Perform further actions as needed

    } catch (Exception e) {
      System.out.println("Error handling cookies popup: " + e.getMessage());

      // Step 2: Verify the website's language
      String pageSource = driver.getPageSource();
      Assert.assertTrue(
          Pattern.compile("\\b(en español)\\b", Pattern.CASE_INSENSITIVE)
              .matcher(pageSource)
              .find(),
          "Website text is not in Spanish!");

      // Step 3: Navigate to the Opinion section
      driver.findElement(By.linkText("Opinión")).click();
      Thread.sleep(2000); // Wait for navigation

      // Fetch the first five articles
      List<WebElement> articles = driver.findElements(By.cssSelector(".headline a"));
      Map<String, String> articleContent = new LinkedHashMap<>();
      List<String> coverImageURLs = new ArrayList<>();

      for (int i = 0; i < Math.min(5, articles.size()); i++) {
        WebElement article = articles.get(i);
        String title = article.getText();
        article.click();
        Thread.sleep(2000);

        String content = driver.findElement(By.cssSelector("article")).getText();
        articleContent.put(title, content);

        WebElement coverImage = driver.findElement(By.cssSelector("figure img"));
        if (coverImage != null) {
          coverImageURLs.add(coverImage.getAttribute("src"));
        }
        driver.navigate().back();
        Thread.sleep(2000);
      }

      // Step 4: Download cover images
      for (int i = 0; i < coverImageURLs.size(); i++) {
        downloadImage(coverImageURLs.get(i), "cover_image_" + (i + 1) + ".jpg");
      }

      // Step 5: Translate titles using Google Translate API
      Map<String, String> translatedTitles = new LinkedHashMap<>();
      for (String title : articleContent.keySet()) {
        String translated = translateText(title, "es", "en");
        translatedTitles.put(title, translated);
      }

      // Print original and translated titles
      translatedTitles.forEach(
          (original, translated) -> {
            System.out.println("Original Title (Spanish): " + original);
            System.out.println("Translated Title (English): " + translated);
          });

      // Step 6: Analyze repeated words in translated headers
      Map<String, Integer> wordCount =
          analyzeRepeatedWords(new ArrayList<>(translatedTitles.values()));
      wordCount.forEach((word, count) -> System.out.println("Word: " + word + ", Count: " + count));
    }
  }

  private void downloadImage(String imageUrl, String outputFileName) throws Exception {
    URL url = new URL(imageUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    InputStream in = connection.getInputStream();
    FileOutputStream out = new FileOutputStream(outputFileName);
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytesRead);
    }
    in.close();
    out.close();
  }

  private String translateText(String text, String sourceLang, String targetLang) {
    // Use Google Translate API or another translation API
    // This is a placeholder. Implement the API call here.
    return text + " (translated)";
  }

  private Map<String, Integer> analyzeRepeatedWords(List<String> headers) {
    Map<String, Integer> wordCount = new HashMap<>();
    for (String header : headers) {
      String[] words = header.toLowerCase().split("\\W+");
      for (String word : words) {
        if (!word.isEmpty()) {
          wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
      }
    }
    // Filter words appearing more than twice
    wordCount.entrySet().removeIf(entry -> entry.getValue() <= 2);
    return wordCount;
  }
}
