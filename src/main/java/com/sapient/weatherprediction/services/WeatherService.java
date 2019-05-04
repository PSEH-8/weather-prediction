package com.sapient.weatherprediction.services;

import com.sapient.weatherprediction.utils.WeatherPredictionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class WeatherService {

    private static final String FORECAST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?q={city},{country}&APPID={key}";
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;

    private final String apiKey;

    public WeatherService(RestTemplateBuilder restTemplateBuilder,
                          WeatherPredictionProperties properties) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = properties.getApi().getKey();
    }

    @Cacheable("forecast")
    public List<Forecast> getWeatherForecast(String country, String city) {
        logger.info("Requesting weather forecast for {}/{}", country, city);
        URI url = new UriTemplate(FORECAST_URL).expand(city, country, this.apiKey);
        WeatherForecast forecast =  invoke(url, WeatherForecast.class);

        List<WeatherEntry> entries = forecast.getEntries();
        Map<String, ForecastRecord> map = new LinkedHashMap<>();
        for(WeatherEntry entry : entries){
            Date date  = Date.from(entry.getTimestamp());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = formatter.format(date);
            map.put(formattedDate, new ForecastRecord(entry.getMsg(), entry.getTemperature()));
        }

        List<Forecast> forecasts = new ArrayList<>();
        for(Map.Entry<String, ForecastRecord> entry : map.entrySet()){
            if("Clouds".equals(entry.getValue().getMsg()) && ((entry.getValue().getTemp() - 273.15) < 40)){
                forecasts.add(new Forecast(entry.getKey(), "Carry Umbrella"));
            }else {
                forecasts.add(new Forecast(entry.getKey(), "Use Sunscreen Lotion"));
            }
        }
        return forecasts;
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate
                .exchange(request, responseType);
        return exchange.getBody();
    }


}
