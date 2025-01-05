package com.Spond.weather_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {


    @Cacheable(value = "weatherCache")
    public JsonNode getWeatherData(double latitude, double longitude) throws JsonProcessingException {

        ResponseEntity<String> response = getMetData(latitude, longitude);

        String jsonString = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);


        JsonNode allTimeseries = rootNode.path("properties").path("timeseries");

        return allTimeseries;
    }

    public static WeatherData getNearestForecast(JsonNode inputJSON,long startTime, long endTime){
        Instant startInstant = Instant.ofEpochMilli(startTime);
        Instant endInstant = Instant.ofEpochMilli(endTime);

        List<WeatherData> forecasts = new ArrayList<>();

        for (JsonNode entry : inputJSON) {
            Instant entryInstant = Instant.parse(entry.get("time").asText());
            if (entryInstant.isAfter(startInstant) && entryInstant.isBefore(endInstant)) {
                JsonNode instantDetails = entry.get("data").get("instant").get("details");
                double temperature = instantDetails.get("air_temperature").asDouble();
                double windSpeed = instantDetails.get("wind_speed").asDouble();

                WeatherData weatherData = new WeatherData(temperature, windSpeed);
                forecasts.add(weatherData);
            }
        }

        if (forecasts.isEmpty()) {
            return null;
        }

        WeatherData nearestForecast;//todo: clarify what temperature to display
        //One idea would be to display the range of temperatures from the entire range
        //probably also needs to display weather based on API weather images
        if (forecasts.size() == 2){
            nearestForecast = forecasts.get(1);
        }else {
            nearestForecast = forecasts.getFirst();
        }


        return nearestForecast;
    }


    private static ResponseEntity<String> getMetData(double latitude, double longitude) {
        //todo: turn into config string to replace weather agent easily?
        String apiUrl = "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=" + latitude + "&lon=" + longitude;

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "WeatherDataDemo/1.0 (https://github.com/Shandris1)");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }
}