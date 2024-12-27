package org.example.utils;

public class ApiKeyUtil {
  public static String getGoogleApiKey() {
    String apiKey = System.getenv("GOOGLE_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      apiKey = "AIzaSyAF5dF7VoF3CBqy0qNn0htktgi3xzs503Y";
    }
    return apiKey;
  }
}
