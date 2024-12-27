package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage {

  WebDriver driver;

  By searchBox = By.name("q");

  public GoogleHomePage(WebDriver driver) {
    this.driver = driver;
  }

  public void launchPage(String website) {
    driver.get(website);
  }

  public void search(String query) {
    driver.get("https://www.google.com");
    driver.findElement(searchBox).sendKeys(query);
    driver.findElement(searchBox).submit();
  }
}
