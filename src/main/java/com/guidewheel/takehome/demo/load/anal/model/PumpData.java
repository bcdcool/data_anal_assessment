package com.guidewheel.takehome.demo.load.anal.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class PumpData {
    private String deviceId;
    private LocalDateTime fromTS;
    private LocalDateTime toTS;
//    private Map<String, PumpPartMetrics> metrics;
    private Map<String, HashMap<String, Object>> metrics;

}
