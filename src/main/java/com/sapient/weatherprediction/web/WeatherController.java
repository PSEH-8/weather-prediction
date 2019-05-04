package com.sapient.weatherprediction.web;

import com.sapient.weatherprediction.services.Forecast;
import com.sapient.weatherprediction.services.WeatherForecast;
import com.sapient.weatherprediction.services.WeatherService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping("/forecast/{country}/{city}")
    public List<Forecast> getWeather(@PathVariable String country, @PathVariable String city){

        return this.weatherService.getWeatherForecast(country, city);

    }


}
