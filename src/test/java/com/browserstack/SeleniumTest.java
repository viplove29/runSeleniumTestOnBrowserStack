package com.browserstack;

import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class SeleniumTest {
  public WebDriver driver;

  @BeforeMethod(alwaysRun = true)
  @SuppressWarnings("unchecked")
  public void setUp() throws Exception {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("start-maximized");
    driver = new ChromeDriver(options);
    final JavascriptExecutor jse = (JavascriptExecutor) driver;
    JSONObject executorObject = new JSONObject();
    JSONObject argumentsObject = new JSONObject();
    argumentsObject.put("name", "Test Spanish News Website");
    executorObject.put("action", "setSessionName");
    executorObject.put("arguments", argumentsObject);
    jse.executeScript(String.format("browserstack_executor: %s", executorObject));
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult result) throws Exception {
    final JavascriptExecutor jse = (JavascriptExecutor) driver;
    JSONObject executorObject = new JSONObject();
    JSONObject argumentsObject = new JSONObject();

    if (result.getStatus() == ITestResult.SUCCESS) {
      argumentsObject.put("status", "passed");
      argumentsObject.put("reason", "Test passed successfully.");
    } else {
      argumentsObject.put("status", "failed");
      argumentsObject.put("reason", result.getThrowable().getMessage());
    }

    executorObject.put("action", "setSessionStatus");
    executorObject.put("arguments", argumentsObject);
    jse.executeScript(String.format("browserstack_executor: %s", executorObject));
    driver.quit();
  }
}
