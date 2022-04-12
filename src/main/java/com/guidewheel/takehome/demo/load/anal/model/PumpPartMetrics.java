package com.guidewheel.takehome.demo.load.anal.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class PumpPartMetrics {

    @Autowired
    ObjectMapper mapper;

    public PumpPartMetrics(String json){
        try {
            PumpPartMetrics pumpDataMetrics = mapper.readValue(json, PumpPartMetrics.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String maxValue;
    private String avgValue;
    private String minValue;
}
