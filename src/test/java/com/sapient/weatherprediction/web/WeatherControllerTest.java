package com.sapient.weatherprediction.web;

import com.sapient.weatherprediction.services.Forecast;
import com.sapient.weatherprediction.services.WeatherEntry;
import com.sapient.weatherprediction.services.WeatherForecast;
import com.sapient.weatherprediction.services.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mvc;


    @Test
    public void testWeatherForecast() throws Exception {
        Forecast forecast = new Forecast("01-05-2019", "cloudy");
        /*forecast.setName("Brussels");
        forecast.getEntries().add(createWeatherEntry(285.45, 600, "02d", Instant.ofEpochSecond(1234)));
        forecast.getEntries().add(createWeatherEntry(294.45, 800, "01d", Instant.ofEpochSecond(5678)));*/
        given(this.weatherService.getWeatherForecast("be", "brussels")).willReturn(Arrays.asList(forecast));
        this.mvc.perform(get("/api/weather/forecast/be/brussels"))
                .andExpect(status().isOk());
               /* .andExpect(jsonPath("$.day", is("01-05-2019")))
                .andExpect(jsonPath("$.msg", is("cloudy")));*/
        verify(this.weatherService).getWeatherForecast("be", "brussels");
    }

    private static WeatherEntry createWeatherEntry(double temperature, int id, String icon,
                                                   Instant timestamp) {
        WeatherEntry entry = new WeatherEntry();
        setWeatherEntry(entry, temperature, id, icon, timestamp);
        return entry;
    }

    private static void setWeatherEntry(WeatherEntry entry, double temperature, int id, String icon,
                                        Instant timestamp) {
        entry.setTemperature(temperature);
        entry.setWeatherId(id);
        entry.setWeatherIcon(icon);
        entry.setTimestamp(timestamp.getEpochSecond());
    }

}