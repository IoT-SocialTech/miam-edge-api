package com.miam.edgeApi.application.services.impl;

import com.miam.edgeApi.application.dto.response.HeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.TemperatureResponseDto;
import com.miam.edgeApi.application.services.MetricsService;
import com.miam.edgeApi.enums.MetricsStatus;
import com.miam.edgeApi.infraestructure.repositories.MetricsRepository;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import com.miam.edgeApi.shared.model.enums.Estatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MetricsServiceImpl implements MetricsService {

    @Autowired
    private MetricsRepository metricsRepository;

    @Override
    public ApiResponse<HeartRateResponseDto> getHeartRate(){

        String metrics = "{\"heartRate\":\"120\", \"temperature\": \"36\", \"alertsGenerated\": \"0\", \"distanceDetector\":\"false\", \"patientId\": \"1\", \"deviceId\":\"1\"}";
        ParserJsonMeasures parserJsonMeasures = new ParserJsonMeasures(metrics);

        int heartRate;
        HeartRateResponseDto heartRateResponseDto = null;

        try {
            heartRateResponseDto = new HeartRateResponseDto();
            heartRate = Integer.parseInt(parserJsonMeasures.getHeartRate());

            heartRateResponseDto.setHeartRate(heartRate);
            heartRateResponseDto.setDate(LocalDateTime.now());
            heartRateResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

            if (heartRate < 60 && heartRate >= 40|| heartRate > 100 && heartRate <= 120){
                heartRateResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
            } else if (heartRate < 40 || heartRate > 120){
                heartRateResponseDto.setStatus(MetricsStatus.DANGER.getStatus());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (heartRateResponseDto != null) {
            return new ApiResponse<> ("Heart Rate fetched successfully", Estatus.SUCCESS, heartRateResponseDto);
        } else {
            return new ApiResponse<> ("Error fetching Heart Rate", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<TemperatureResponseDto> getTemperature(){

        String metrics = "{\"heartRate\":\"80\", \"temperature\": \"32.80\", \"alertsGenerated\": \"0\", \"distanceDetector\":\"false\", \"patientId\": \"1\", \"deviceId\":\"1\"}";
        ParserJsonMeasures parserJsonMeasures = new ParserJsonMeasures(metrics);

        float temperature;
        TemperatureResponseDto temperatureResponseDto = null;

        try {
            temperatureResponseDto = new TemperatureResponseDto();
            temperature = Float.parseFloat(parserJsonMeasures.getTemperature());

            temperatureResponseDto.setTemperature(temperature);
            temperatureResponseDto.setDate(LocalDateTime.now());
            temperatureResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

            if (temperature > 37 && temperature <= 39 || temperature >= 34 && temperature < 36 ) {
                temperatureResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
            } else if (temperature > 39 || temperature < 34){
                temperatureResponseDto.setStatus(MetricsStatus.DANGER.getStatus());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (temperatureResponseDto != null) {
            return new ApiResponse<> ("Temperature fetched successfully", Estatus.SUCCESS, temperatureResponseDto);
        } else {
            return new ApiResponse<> ("Error fetching Temperature", Estatus.ERROR, null);
        }
    }

}
