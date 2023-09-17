package ru.stazaev.api.services.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.stazaev.api.services.KinopoiskRequestService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class KinopoiskRequestServiceImpl implements KinopoiskRequestService {
    private final String URI = "https://api.kinopoisk.dev/v1.3/movie?name=";
    private final String TOKEN = "RW3WP4C-B6KMK0W-M2N29H2-AZT2R1M";

    @Override
    public double getMovieRatings(String name) {
        var reqUri = URI + name;
        var request = sendRequest(reqUri);
        if (request.body()!= null && request.statusCode() == 200) {
            return parseJsonResponse(request.body());
        }
        return 0;
    }

    private HttpResponse<String> sendRequest(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .header("X-API-KEY", TOKEN)
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private double parseJsonResponse(String value){
        JSONObject jsonObject = new JSONObject(value);
        JSONArray array = new JSONArray(jsonObject.getJSONArray("docs"));
        JSONObject film = (JSONObject) array.get(0);
        return (double) film.getJSONObject("rating").get("kp");
    }
}
