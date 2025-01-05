package com.Spond.weather_service;



public class WeatherData {
    private double temperature; // In Celsius
    private double windSpeed;   // In m/s

    // Constructor, getters, and setters
    public WeatherData(double temperature, double windSpeed) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
    }

    // Getters and setters
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }


}