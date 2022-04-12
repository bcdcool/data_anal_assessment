package com.guidewheel.takehome.demo.load.anal.service;

import com.guidewheel.takehome.demo.load.anal.model.LineData;
import com.guidewheel.takehome.demo.load.anal.model.PumpData;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PumpService {

    PumpData getPump(int index) throws IOException, JSONException;
    List<PumpData> getAllPumpData() throws IOException, JSONException;
    Map<String, Map<String, LineData>> getAllLineData() throws IOException, JSONException;

}
