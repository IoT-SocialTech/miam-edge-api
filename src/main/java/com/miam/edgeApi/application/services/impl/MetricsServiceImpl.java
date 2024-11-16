package com.miam.edgeApi.application.services.impl;

import com.miam.edgeApi.application.dto.response.AverageHeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.AverageTemperatureResponseDto;
import com.miam.edgeApi.application.dto.response.HeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.TemperatureResponseDto;
import com.miam.edgeApi.application.services.MetricsService;
import com.miam.edgeApi.domain.entities.Device;
import com.miam.edgeApi.domain.entities.Metrics;
import com.miam.edgeApi.enums.MetricsStatus;
import com.miam.edgeApi.infraestructure.repositories.DeviceRepository;
import com.miam.edgeApi.infraestructure.repositories.MetricsRepository;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import com.miam.edgeApi.shared.model.enums.Estatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Service
public class MetricsServiceImpl implements MetricsService {

    @Autowired
    private MetricsRepository metricsRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private final String url = "https://fir-embedded-miam-default-rtdb.firebaseio.com/.json";

    @Override
    @Transactional
    public void createMetrics(JSONObject jsonMetrics) {
        Metrics metrics = new Metrics();

        double temperature;
        double heartRate;
        double distance;
        String deviceId;

        try {
            deviceId = jsonMetrics.getString("MacAddress");

            Device device = deviceRepository.getDeviceById(deviceId);

            temperature = jsonMetrics.getDouble("Temperature");
            heartRate = jsonMetrics.getDouble("HeartRate");
            distance = jsonMetrics.getDouble("Distance");

            metrics.setHeartRate(heartRate);
            metrics.setTemperature(temperature);
            metrics.setDistance(distance);
            metrics.setPatientId(device.getPatientId());
            metrics.setDate(LocalDateTime.now());
            metrics.setDeviceId(deviceId);
            metrics.setDeviceId(deviceId);
            metrics.setStatus(MetricsStatus.NORMAL.getStatus());

            if (distance < device.getLimitDistance()){
                metrics.setDistanceDetector(true);
            } else {
                metrics.setDistanceDetector(false);
            }

            if (temperature > 37 && temperature <= 39 || temperature >= 34 && temperature < 36 ) {
                metrics.setStatus(MetricsStatus.WARNING.getStatus() + " - Temperature");
            } else if (temperature > device.getLimitTemperature() || temperature < 34){
                metrics.setStatus(MetricsStatus.DANGER.getStatus() + " - Temperature");
            } else if (heartRate < 60 && heartRate >= 40|| heartRate > 100 && heartRate <= 120){
                metrics.setStatus(MetricsStatus.WARNING.getStatus() + " - Heart Rate");
            } else if (heartRate < 40 || heartRate > device.getLimitHeartRate()){
                metrics.setStatus(MetricsStatus.DANGER.getStatus() + " - Heart Rate");
            }

            metricsRepository.save(metrics);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override
    public ApiResponse<HeartRateResponseDto> getHeartRate(int patientId) {

        double heartRate;
        HeartRateResponseDto heartRateResponseDto = null;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> metrics = client.send(request, HttpResponse.BodyHandlers.ofString());

            ParserJsonMeasures parserJsonMeasures = new ParserJsonMeasures(metrics.body());

            if (deviceRepository.existsById(parserJsonMeasures.getMacAddress())){
                Device device = deviceRepository.findById(parserJsonMeasures.getMacAddress()).get();
                if (patientId == device.getPatientId()){
                    try {
                        heartRateResponseDto = new HeartRateResponseDto();
                        heartRate = parserJsonMeasures.getHeartRate();

                        heartRateResponseDto.setHeartRate(heartRate);
                        heartRateResponseDto.setDate(LocalDateTime.now());
                        heartRateResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

                        if (heartRate < 60 && heartRate >= 40|| heartRate > 100 && heartRate <= device.getLimitHeartRate()){
                            heartRateResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
                        } else if (heartRate < 40 || heartRate > device.getLimitHeartRate()){
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
                } else {
                    return new ApiResponse<> ("Patient not found", Estatus.ERROR, null);
                }

            } else {
                return new ApiResponse<> ("Device not found", Estatus.ERROR, null);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ApiResponse<> ("Error fetching Heart Rate", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<TemperatureResponseDto> getTemperature(int patientId){

        double temperature;
        TemperatureResponseDto temperatureResponseDto = null;

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> metrics = client.send(request, HttpResponse.BodyHandlers.ofString());

            ParserJsonMeasures parserJsonMeasures = new ParserJsonMeasures(metrics.body());

            if (deviceRepository.existsById(parserJsonMeasures.getMacAddress())) {
                Device device = deviceRepository.findById(parserJsonMeasures.getMacAddress()).get();
                if (patientId == device.getPatientId()) {
                    try {
                        temperatureResponseDto = new TemperatureResponseDto();
                        temperature = parserJsonMeasures.getTemperature();

                        temperatureResponseDto.setTemperature(temperature);
                        temperatureResponseDto.setDate(LocalDateTime.now());
                        temperatureResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

                        if (temperature > 37 && temperature <= device.getLimitTemperature() || temperature >= 34 && temperature < 36 ) {
                            temperatureResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
                        } else if (device.getLimitTemperature() > 39 || temperature < 34){
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
                } else {
                    return new ApiResponse<> ("Patient not found", Estatus.ERROR, null);
                }
            } else {
                return new ApiResponse<> ("Device not found", Estatus.ERROR, null);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new ApiResponse<> ("Error fetching Heart Rate", Estatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<AverageHeartRateResponseDto> getAverageHeartRate(int patientId){

        double averageHeartRate = metricsRepository.findAverageHeartRate(patientId);
        AverageHeartRateResponseDto averageHeartRateResponseDto = new AverageHeartRateResponseDto();

        averageHeartRateResponseDto.setAverageHeartRate(averageHeartRate);
        averageHeartRateResponseDto.setDate(LocalDateTime.now());
        averageHeartRateResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

        if (averageHeartRate < 60 && averageHeartRate >= 40|| averageHeartRate > 100 && averageHeartRate <= 120){
            averageHeartRateResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
        } else if (averageHeartRate < 40 || averageHeartRate > 120){
            averageHeartRateResponseDto.setStatus(MetricsStatus.DANGER.getStatus());
        }

        System.out.println("Average Heart Rate: " + metricsRepository.findAverageHeartRate(patientId));

        return new ApiResponse<> ("Average Heart Rate fetched successfully", Estatus.SUCCESS, averageHeartRateResponseDto);

    }

    @Override
    public ApiResponse<AverageTemperatureResponseDto> getAverageTemperature(int patientId){

        double averageTemperature = metricsRepository.findAverageTemperature(patientId);
        AverageTemperatureResponseDto averageTemperatureResponseDto = new AverageTemperatureResponseDto();

        averageTemperatureResponseDto.setAverageTemperature(averageTemperature);
        averageTemperatureResponseDto.setDate(LocalDateTime.now());
        averageTemperatureResponseDto.setStatus(MetricsStatus.NORMAL.getStatus());

        if (averageTemperature > 37 && averageTemperature <= 39 || averageTemperature >= 34 && averageTemperature < 36 ) {
            averageTemperatureResponseDto.setStatus(MetricsStatus.WARNING.getStatus());
        } else if (averageTemperature > 39 || averageTemperature < 34){
            averageTemperatureResponseDto.setStatus(MetricsStatus.DANGER.getStatus());
        }

        System.out.println("Average Temperature: " + metricsRepository.findAverageTemperature(patientId));

        return new ApiResponse<> ("Average Temperature fetched successfully", Estatus.SUCCESS, averageTemperatureResponseDto);
    }

}
