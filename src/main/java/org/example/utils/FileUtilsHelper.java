package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FileUtilsHelper {

  public static List<String> processArticles(List<WebElement> articles) throws IOException {
    List<String> titles = new ArrayList<>();
    File targetDir = new File("target");
    if (!targetDir.exists()) {
      targetDir.mkdir();
    }

    String uniqueFolderName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    File imageDir = new File(targetDir, "images_" + uniqueFolderName);
    if (!imageDir.exists()) {
      imageDir.mkdir();
    }

    for (int i = 0; i < Math.min(articles.size(), 5); i++) {
      WebElement article = articles.get(i);
      String title = extractTitle(article);
      String content = extractContent(article);

      System.out.println("Title: " + title);
      System.out.println("Content: " + content);
      System.out.println("----------------------");

      titles.add(title);
      saveArticleImage(article, imageDir, i);
    }
    return titles;
  }

  private static String extractTitle(WebElement article) {
    return article.findElement(By.cssSelector("h2")).getText();
  }

  private static String extractContent(WebElement article) {
    try {
      return article.findElement(By.cssSelector("p")).getText();
    } catch (Exception e) {
      return "Content not found";
    }
  }

  private static void saveArticleImage(WebElement article, File imageDir, int index) {
    try {
      List<WebElement> imgElements = article.findElements(By.tagName("img"));
      if (!imgElements.isEmpty()) {
        String imgUrl = imgElements.get(0).getAttribute("src");
        System.out.println("Image URL: " + imgUrl);
        File imageFile = new File(imageDir, "cover" + index + ".jpg");

        // Download and save the image
        FileUtils.copyURLToFile(new URL(imgUrl), imageFile);
        System.out.println("Saved image: " + imageFile.getAbsolutePath());
        System.out.println("------------------------------------------");
      }
    } catch (Exception e) {
      System.out.println("Error saving article image: " + e.getMessage());
    }
  }
}
