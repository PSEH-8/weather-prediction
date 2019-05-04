package com.sapient.weatherprediction.services;

public class ForecastRecord {

    private String msg;
    private Double temp;

    public ForecastRecord(String msg, Double temp) {
        this.msg = msg;
        this.temp = temp;
    }

    public String getMsg() {
        return msg;
    }

    public Double getTemp() {
        return temp;
    }
}
