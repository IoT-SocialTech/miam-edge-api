package com.miam.edgeApi.application.services.impl;

import lombok.Getter;
import org.json.JSONObject;

public class ParserJsonMeasures {

    String json;
    @Getter
    JSONObject jsonObject;

    public ParserJsonMeasures(String json){
        this.json = json;
        jsonObject = new JSONObject(json);
    }

    public String getHeartRate(){ return jsonObject.getString("heartRate"); }

    public String getTemperature(){
        return jsonObject.getString("temperature");
    }

    public String getAlertsGenerated(){
        return jsonObject.getString("alertsGenerated");
    }

    public String getDistanceDetector(){
        return jsonObject.getString("distanceDetector");
    }

    public String getPatientId(){
        return jsonObject.getString("patientId");
    }

    public String getDeviceId(){
        return jsonObject.getString("deviceId");
    }

}
