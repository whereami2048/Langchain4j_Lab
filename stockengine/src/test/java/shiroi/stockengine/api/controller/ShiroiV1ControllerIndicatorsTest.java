package shiroi.stockengine.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ShiroiV1ControllerIndicatorsTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void calculateIndicators_withSufficientBars_returnsIndicators() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("symbol", "TEST");
        body.put("timeframe", "1m");
        body.put("bars", generateBars(30, 60_000L));

        mockMvc.perform(post("/api/v1/indicators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("TEST"))
                .andExpect(jsonPath("$.timeframe").value("1m"))
                .andExpect(jsonPath("$.barCount").value(30))
                .andExpect(jsonPath("$.indicators.sma20.value").exists())
                .andExpect(jsonPath("$.indicators.ema50.value").exists())
                .andExpect(jsonPath("$.indicators.ema200.value").exists())
                .andExpect(jsonPath("$.indicators.rsi14.value").exists())
                .andExpect(jsonPath("$.indicators.stochK14.value").exists())
                .andExpect(jsonPath("$.indicators.macd.value").exists())
                .andExpect(jsonPath("$.indicators.macdSignal.value").exists());
    }

    @Test
    void calculateIndicators_withShortSeries_returnsOk() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("symbol", "TEST");
        body.put("timeframe", "1m");
        body.put("bars", generateBars(10, 60_000L));

        mockMvc.perform(post("/api/v1/indicators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barCount").value(10))
                .andExpect(jsonPath("$.symbol").value("TEST"));
    }

    @Test
    void analyze_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/v1/analyze").param("strategyId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    private List<Map<String, Object>> generateBars(int count, long stepMillis) {
        long now = Instant.now().toEpochMilli();
        long start = now - (count * stepMillis);
        List<Map<String, Object>> bars = new ArrayList<>();
        double base = 100.0;
        for (int i = 0; i < count; i++) {
            double open = base + i;
            double high = open + 1.0;
            double low = open - 1.0;
            double close = open + 0.5;
            double volume = 1000 + i * 10;

            Map<String, Object> bar = new HashMap<>();
            bar.put("epochMillis", start + (i + 1) * stepMillis);
            bar.put("open", open);
            bar.put("high", high);
            bar.put("low", low);
            bar.put("close", close);
            bar.put("volume", volume);
            bars.add(bar);
        }
        return bars;
    }
}
