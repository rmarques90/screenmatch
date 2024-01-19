package br.com.demo.screenmatch.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequesterAPI {

    private final String OMDB_ROOT_ADDRESS = "http://www.omdbapi.com/?";
    private final String OMDB_API = "d7f3ff4e";

    public String getDataFromOmdbAPI(String seriesName) {
        return getDataFromOmdbAPI(seriesName, null);
    }

    public String getDataFromOmdbAPI(String seriesName, Integer season) {
        seriesName = seriesName.replace(" ", "+");

        StringBuilder urlToRequest = new StringBuilder(OMDB_ROOT_ADDRESS);
        urlToRequest.append(String.format("t=%s", seriesName));
        if (season != null) {
            urlToRequest.append(String.format("&season=%d", season));
        }
        urlToRequest.append(String.format("&apikey=%s", OMDB_API));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlToRequest.toString()))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
}
