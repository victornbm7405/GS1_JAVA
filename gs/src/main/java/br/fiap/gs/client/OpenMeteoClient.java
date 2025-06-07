package br.fiap.gs.client;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenMeteoClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public Double obterTemperaturaAtual(Double latitude, Double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("current_weather", "true")
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        JSONObject json = new JSONObject(response);

        return json.getJSONObject("current_weather").getDouble("temperature");
    }
}
