package com.miam.edgeApi;

import com.miam.edgeApi.application.services.impl.MetricsServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class TestMetrics {

    @Test
    public void test() throws JSONException {
        MetricsServiceImpl metricsService = new MetricsServiceImpl();

        JSONObject json = new JSONObject("{\n" +
                "  \"Distance\": 50,\n" +
                "  \"Led\": \"\",\n" +
                "  \"Message\": \"\",\n" +
                "  \"Pulse\": 80,\n" +
                "  \"Temperature\": 36,\n" +
                "  \"Time\": \"\",\n" +
                "  \"PanicButton\": \"\",\n" +
                "  \"MacAddress\": \"MAC982398\"\n" +
                "}");

        metricsService.createMetrics(json);
    }

}
