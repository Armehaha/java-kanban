package com.armen.osipyan.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final HttpClient httpClient;
    private final String token;


    public KVTaskClient(URI uri) {
        httpClient  = HttpClient.newHttpClient();
         HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).build();
         try  {
             HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
             token = response.body();
         } catch (IOException | InterruptedException e) {
             throw new RuntimeException(e);
         }
    }

    public void put(String key, String json) {
    HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("http://localhost:8078/save/" + key + "/?API_TOKEN=" + token))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

    }

    public String load(String key) {
        HttpRequest requestLoad = HttpRequest.newBuilder()
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(URI.create("http://localhost:8078/save/load/" + key + "/?API_TOKEN=" + token))
                .build();
        try {
            HttpResponse<String> responseLoad = httpClient.send(requestLoad, HttpResponse.BodyHandlers.ofString());

            return responseLoad.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
