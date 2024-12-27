// src/main/java/org/example/utils/TranslationService.java
package org.example.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.example.config.ConfigReader;

public class TranslationService {
  public static String translateText(String text) throws IOException {
    String apiKey = ApiKeyUtil.getGoogleApiKey();
    //  String apiKey = ConfigReader.get("apiKey");
    String baseUri = ConfigReader.get("baseUri");
    String sourceLang = ConfigReader.get("sourceLanguage");
    String targetLang = ConfigReader.get("targetLanguage");

    String urlStr =
        String.format(
            "%s?key=%s&q=%s&source=%s&target=%s",
            baseUri,
            apiKey,
            java.net.URLEncoder.encode(text, StandardCharsets.UTF_8),
            sourceLang,
            targetLang);

    URL url = new URL(urlStr);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Content-Type", "application/json");

    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
        String response = scanner.useDelimiter("\\A").next();
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        return jsonResponse
            .getAsJsonObject("data")
            .getAsJsonArray("translations")
            .get(0)
            .getAsJsonObject()
            .get("translatedText")
            .getAsString();
      }
    } else {
      throw new IOException("Translation API response code: " + responseCode);
    }
  }
}
