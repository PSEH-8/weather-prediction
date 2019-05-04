package com.sapient.weatherprediction;

import com.sapient.weatherprediction.utils.WeatherPredictionProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WeatherPredictionProperties.class)
public class WeatherPredictionApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherPredictionApplication.class, args);
	}

}
