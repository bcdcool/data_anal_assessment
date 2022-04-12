package com.guidewheel.takehome.demo.load.anal.service;

import com.guidewheel.takehome.demo.load.anal.model.Dataset;
import com.guidewheel.takehome.demo.load.anal.model.LineData;
import com.guidewheel.takehome.demo.load.anal.model.PumpData;
import com.guidewheel.takehome.demo.load.anal.repo.ReadPumpDayData;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PumpServiceImpl implements PumpService {

    @Autowired
    ReadPumpDayData readPumpDayData;

    @Override
    public PumpData getPump(int index) throws IOException, JSONException {
        return readPumpDayData.getPump(index);
    }

    @Override
    public List<PumpData> getAllPumpData() throws IOException, JSONException {
        return readPumpDayData.getAllPumps();
    }

    @Override
    public Map<String, Map<String, LineData>> getAllLineData() throws IOException, JSONException {
        Map<String, Map<String, LineData>> finalMap = new HashMap<>();
        List<PumpData> pumpDataList = getAllPumpData();

        for(PumpData pumpData : pumpDataList){
            createPartMetrics(pumpData, finalMap);
        }

        return finalMap;
    }

    private void createPartMetrics(PumpData pumpData, Map<String, Map<String, LineData>> finalMap){
        final String deviceKey = pumpData.getDeviceId();
        Map<String, HashMap<String, Object>> partMetrics = pumpData.getMetrics();
        if(!finalMap.containsKey(deviceKey)){
            finalMap.put(deviceKey, new HashMap<>());
        }
        final Map<String, LineData> partMap = finalMap.get(deviceKey);
        for(String partKey : partMetrics.keySet()){
            if(!partMap.containsKey(partKey)){
                LineData lineData = new LineData();
                lineData.setDeviceId(deviceKey);
                lineData.setDevicePart(partKey);
                lineData.setLabels(new ArrayList<>());
                lineData.setDatasets(new ArrayList<>());
                partMap.put(partKey,lineData);
            }
            LineData lineData = partMap.get(partKey);

            List<LocalDateTime> labels = lineData.getLabels();
            if(!labels.contains(pumpData.getFromTS())){
                labels.add(pumpData.getFromTS());
            }
            if(!labels.contains(pumpData.getToTS())){
                labels.add(pumpData.getToTS());
            }

            List<Dataset> datasetList = lineData.getDatasets();
            createDataList(datasetList, pumpData, partKey);
        }
    }

    private void createDataList(List<Dataset> datasetList, PumpData pumpData, String partKey){
        if(datasetList.isEmpty()){
            Dataset avaSet = new Dataset();
            avaSet.setLabel(LineData.MetricType.AVG.toString());
            avaSet.setData(new ArrayList<>());
            avaSet.setBorderColor("yellow");
            datasetList.add(avaSet);

            Dataset minSet = new Dataset();
            minSet.setLabel(LineData.MetricType.MIN.toString());
            minSet.setData(new ArrayList<>());
            minSet.setBorderColor("red");
            datasetList.add(minSet);

            Dataset maxSet = new Dataset();
            maxSet.setLabel(LineData.MetricType.MAX.toString());
            maxSet.setData(new ArrayList<>());
            maxSet.setBorderColor("green");
            datasetList.add(maxSet);
        }

        for(Dataset ds : datasetList) {
            if(ds.getLabel().equals(LineData.MetricType.AVG.toString())){
                Double data = Double.valueOf(pumpData.getMetrics().get(partKey).get(LineData.MetricType.AVG.getValue()).toString());
                ds.getData().add(data);
            }
            if(ds.getLabel().equals(LineData.MetricType.MAX.toString())){
                Double max = Double.valueOf(pumpData.getMetrics().get(partKey).get(LineData.MetricType.MAX.getValue()).toString());
                ds.getData().add(max);
            }
            if(ds.getLabel().equals(LineData.MetricType.MIN.toString())){
                Double data = Double.valueOf(pumpData.getMetrics().get(partKey).get(LineData.MetricType.MIN.getValue()).toString());
                ds.getData().add(data);
            }
        }

    }

}
