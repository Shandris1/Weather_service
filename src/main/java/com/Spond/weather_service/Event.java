package com.Spond.weather_service;

public class Event {
    private Long eventId;
    private double latitude;
    private double longitude;
    private long startTime; // UTC timestamp
    private long endTime;   // UTC timestamp


    public Event(Long eventId, double latitude, double longitude, long startTime, long endTime) {
        this.eventId = eventId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public long getStartTime () {
        return startTime;
    }
    public long getEndTime() {
        return endTime;
    }


}

