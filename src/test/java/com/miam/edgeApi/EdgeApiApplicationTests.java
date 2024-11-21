package com.miam.edgeApi;

import com.miam.edgeApi.application.services.MetricsService;
import com.miam.edgeApi.application.services.impl.MetricsServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EdgeApiApplicationTests {

	@Test
	void contextLoads() throws JSONException {

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

	}

}
