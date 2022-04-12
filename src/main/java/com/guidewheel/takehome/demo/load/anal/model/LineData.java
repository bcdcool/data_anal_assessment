package com.guidewheel.takehome.demo.load.anal.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class LineData {
    String deviceId;
    String devicePart;
//    MetricType metricType;
    ArrayList<LocalDateTime> labels; //String
    ArrayList<Dataset> datasets; //DataSet

    public enum MetricType {
        AVG("avgvalue"),
        MAX("maxvalue"),
        MIN("minvalue");

        private final String value;

        MetricType(String value) {
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }
}
