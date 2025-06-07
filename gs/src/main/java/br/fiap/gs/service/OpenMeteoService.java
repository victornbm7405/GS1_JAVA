package br.fiap.gs.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
public class OpenMeteoService {

    // Instancio internamente, sem depender de bean externo
    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable(value = "temperaturas", key = "#latitude + '-' + #longitude")
    public double getTemperaturaAtual(double latitude, double longitude) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("current_weather", true)
                .queryParam("timezone", "auto")
                .toUriString();

        MeteoResponse response = restTemplate.getForObject(url, MeteoResponse.class);
        if (response != null && response.current_weather != null) {
            return response.current_weather.temperature;
        }
        throw new RuntimeException("Não foi possível obter temperatura para "
                + latitude + ", " + longitude);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CurrentWeather {
        public double temperature;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MeteoResponse {
        public CurrentWeather current_weather;
    }
}
