package com.Spond.weather_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;

import static com.Spond.weather_service.WeatherService.getNearestForecast;


@RestController
@Component
@EnableCaching
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/api/v1/events/{eventId}/weather")
	public ResponseEntity<?> getEventWeather(@PathVariable Long eventId) throws JsonProcessingException {
		Event event = getEventLocation(eventId);
		if (event == null) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("error, Event with ID " + eventId + " not found.");
        }
		JsonNode forecastJson = weatherService.getWeatherData(event.getLatitude(),event.getLongitude());

		WeatherData forecast = getNearestForecast(forecastJson,event.getStartTime(),event.getEndTime());

		return ResponseEntity
				.status(200)
				.body(forecast);
	}


	@GetMapping("/api/v1/events/{eventId}/location")
	public Event getEventLocation(@PathVariable Long eventId) {
		Event event = null;

		//todo: Obviously need to be an API call to another system running in parallel

		if (eventId == 1) {
			event = new Event(1L, 59.9114, 10.7579, // Oslo Opera House
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000), // Start time in 2 days
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000) + (2 * 60 * 60 * 1000)); // End time 2 hours later
		} else if (eventId == 2) {
			event = new Event(2L, 59.9074, 10.7224, // Aker Brygge
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000), // Start time in 2 days
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000) + (1 * 60 * 60 * 1000)); // End time 1 hour later
		} else if (eventId == 3) {
			event = new Event(3L, 59.9138, 10.7387, // Royal Palace
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000), // Start time in 2 days
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000) + (2 * 60 * 60 * 1000)); // End time 2 hours later
		} else if (eventId == 4) {
			event = new Event(4L, 59.9441, 10.7200, // Vigeland Park
					System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000), // Start time in 1 day
					System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000) + (2 * 60 * 60 * 1000)); // End time 2 hours later
		} else if (eventId == 5) {
			event = new Event(5L, 59.9146, 10.7489, // Oslo Central Station
					System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000), // Start time in 1 day
					System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000) + (2 * 60 * 60 * 1000)); // End time 2 hours later
		} else if (eventId == 6) {
			event = new Event(6L, 59.8939, 10.7784, // Holmenkollen Ski Jump
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000), // Start time in 2 days
					System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000) + (2 * 60 * 60 * 1000)); // End time 2 hours later
		}

		return event;
	}

}
