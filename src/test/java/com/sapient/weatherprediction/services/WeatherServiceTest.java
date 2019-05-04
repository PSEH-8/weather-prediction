package com.sapient.weatherprediction.services;

import org.assertj.core.data.Offset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@RestClientTest(WeatherService.class)
@TestPropertySource(properties = "app.weather.api.key=test-ABC")
public class WeatherServiceTest {

    private static final String URL = "http://api.openweathermap.org/data/2.5/";

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void getWeatherForecast() {
        this.server.expect(
                requestTo(URL + "forecast?q=barcelona,es&APPID=test-ABC"))
                .andRespond(withSuccess(
                        "{\n" +
                                "  \"cod\": \"200\",\n" +
                                "  \"message\": 0.0032,\n" +
                                "  \"cnt\": 36,\n" +
                                "  \"list\": [\n" +
                                "    {\n" +
                                "      \"dt\": 1487246400,\n" +
                                "      \"main\": {\n" +
                                "        \"temp\": 286.67,\n" +
                                "        \"temp_min\": 281.556,\n" +
                                "        \"temp_max\": 286.67,\n" +
                                "        \"pressure\": 972.73,\n" +
                                "        \"sea_level\": 1046.46,\n" +
                                "        \"grnd_level\": 972.73,\n" +
                                "        \"humidity\": 75,\n" +
                                "        \"temp_kf\": 5.11\n" +
                                "      },\n" +
                                "      \"weather\": [\n" +
                                "        {\n" +
                                "          \"id\": 800,\n" +
                                "          \"main\": \"Clear\",\n" +
                                "          \"description\": \"clear sky\",\n" +
                                "          \"icon\": \"01d\"\n" +
                                "        }\n" +
                                "      ],\n" +
                                "      \"clouds\": {\n" +
                                "        \"all\": 0\n" +
                                "      },\n" +
                                "      \"wind\": {\n" +
                                "        \"speed\": 1.81,\n" +
                                "        \"deg\": 247.501\n" +
                                "      },\n" +
                                "      \"sys\": {\n" +
                                "        \"pod\": \"d\"\n" +
                                "      },\n" +
                                "      \"dt_txt\": \"2017-02-16 12:00:00\"\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"city\": {\n" +
                                "    \"id\": 6940463,\n" +
                                "    \"name\": \"Altstadt\",\n" +
                                "    \"coord\": {\n" +
                                "      \"lat\": 48.137,\n" +
                                "      \"lon\": 11.5752\n" +
                                "    },\n" +
                                "    \"country\": \"none\"\n" +
                                "  }\n" +
                                "}",
                        MediaType.APPLICATION_JSON));
        List<Forecast> forecastList = this.weatherService.getWeatherForecast("es", "barcelona");
        assertTrue(forecastList.size() == 1);
        assertEquals("16-02-2017", forecastList.get(0).getDay());
        assertEquals("Use Sunscreen Lotion", forecastList.get(0).getMessage());
        this.server.verify();
    }

}