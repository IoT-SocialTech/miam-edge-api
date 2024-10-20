package com.miam.edgeApi.application.services.impl;

import com.miam.edgeApi.application.dto.response.AverageHeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.AverageTemperatureResponseDto;
import com.miam.edgeApi.application.dto.response.HeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.TemperatureResponseDto;
import com.miam.edgeApi.application.services.MetricsService;
import com.miam.edgeApi.domain.entities.Metrics;
import com.miam.edgeApi.enums.MetricsStatus;
import com.miam.edgeApi.infraestructure.repositories.MetricsRepository;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import com.miam.edgeApi.shared.model.enums.Estatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MetricsServiceImpl implements MetricsService {

    @Autowired
    private MetricsRepository metricsRepository;

    @Override
    @Transactional
    public void createMetrics(JSONObject jsonMetrics) {
        Metrics metrics = new Metrics();

        int temperature;
        int heartRate;
        int alertsGenerated;
        int patientId;
        int deviceId;
        boolean distanceDetector;

        try {

            temperature = (int) Float.parseFloat(jsonMetrics.getString("temperature"));
            heartRate = Integer.parseInt(jsonMetrics.getString("heartRate"));
            alertsGenerated = Integer.parseInt(jsonMetrics.getString("alertsGenerated"));
            patientId = Integer.parseInt(jsonMetrics.getString("patientId"));
            deviceId = Integer.parseInt(jsonMetrics.getString("deviceId"));
            distanceDetector = Boolean.parseBoolean(jsonMetrics.getString("distanceDetector"));

            metrics.setHeartRate(heartRate);
            metrics.setTemperature(temperature);
            metrics.setAlertsGenerated(alertsGenerated);
            metrics.setDistanceDetector(distanceDetector);
            metrics.setDate(LocalDateTime.now());
            metrics.setPatientId(patientId);
            metrics.setDeviceId(deviceId);
            metrics.setStatus(MetricsStatus.NORMAL.getStatus());

            if (temperature > 37 && temperature <= 39 || temperature >= 34 && temperature < 36 ) {
                metrics.setStatus(MetricsStatus.WARNING.getStatus() + " - Temperature");
            } else if (temperature > 39 || temperature < 34){
                metrics.setStatus(MetricsStatus.DANGER.getStatus() + " - Temperature");
            } else if (heartRate < 60 && heartRate >= 40|| heartRate > 100 && heartRate <= 120){
                metrics.setStatus(MetricsStatus.WARNING.getStatus() + " - Heart Rate");
            } else if (heartRate < 40 || heartRate > 120){
                metrics.setStatus(MetricsStatus.DANGER.getStatus() + " - Heart Rate");
            }

            metricsRepository.save(metrics);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

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

    @Override
    public ApiResponse<AverageHeartRateResponseDto> getAverageHeartRate(){

        double averageHeartRate = metricsRepository.findAverageHeartRate();
        AverageHeartRateResponseDto averageHeartRateResponseDto = new AverageHeartRateResponseDto();

        averageHeartRateResponseDto.setAverageHeartRate(averageHeartRate);
        averageHeartRateResponseDto.setDate(LocalDateTime.now());
        averageHeartRateResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

        if (averageHeartRate < 60 && averageHeartRate >= 40|| averageHeartRate > 100 && averageHeartRate <= 120){
            averageHeartRateResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
        } else if (averageHeartRate < 40 || averageHeartRate > 120){
            averageHeartRateResponseDto.setStatus(MetricsStatus.DANGER.getStatus());
        }

        System.out.println("Average Heart Rate: " + metricsRepository.findAverageHeartRate());

        return new ApiResponse<> ("Average Heart Rate fetched successfully", Estatus.SUCCESS, averageHeartRateResponseDto);

    }

    @Override
    public ApiResponse<AverageTemperatureResponseDto> getAverageTemperature(){

        double averageTemperature = metricsRepository.findAverageTemperature();
        AverageTemperatureResponseDto averageTemperatureResponseDto = new AverageTemperatureResponseDto();

        averageTemperatureResponseDto.setAverageTemperature(averageTemperature);
        averageTemperatureResponseDto.setDate(LocalDateTime.now());
        averageTemperatureResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

        if (averageTemperature > 37 && averageTemperature <= 39 || averageTemperature >= 34 && averageTemperature < 36 ) {
            averageTemperatureResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
        } else if (averageTemperature > 39 || averageTemperature < 34){
            averageTemperatureResponseDto.setStatus(MetricsStatus.DANGER.getStatus());
        }

        System.out.println("Average Temperature: " + metricsRepository.findAverageTemperature());

        return new ApiResponse<> ("Average Temperature fetched successfully", Estatus.SUCCESS, averageTemperatureResponseDto);
    }

}
