package com.sapient.weatherprediction.services;

public class Forecast {

    private final String day;
    private final String message;

    public Forecast(String day, String message) {
        this.day = day;
        this.message = message;
    }

    public String getDay() {
        return day;
    }

    public String getMessage() {
        return message;
    }
}
