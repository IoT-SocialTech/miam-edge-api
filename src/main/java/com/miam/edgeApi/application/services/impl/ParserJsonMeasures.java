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

    public Double getHeartRate(){ return jsonObject.getDouble("HeartRate"); }

    public Double getTemperature(){
        return jsonObject.getDouble("Temperature");
    }

    public String getDistance() { return jsonObject.getString("Distance"); }

    public String getMacAddress() { return jsonObject.getString("MacAddress");}


}
