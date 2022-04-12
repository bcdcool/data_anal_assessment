package com.guidewheel.takehome.demo.load.anal.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guidewheel.takehome.demo.load.anal.model.PumpData;
import com.guidewheel.takehome.demo.load.anal.model.PumpPartMetrics;
import com.opencsv.CSVReader;
import lombok.Data;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Component
@Data
public class ReadPumpDayData {
    @Autowired
    ObjectMapper mapper;

    private PumpData lineToPumpData(String[] line) throws IOException, JSONException {
        PumpData pumpData = new PumpData();
        int i = 0;
        pumpData.setDeviceId(line[i++]);
        Timestamp fromTS = new Timestamp(Long.valueOf(line[i++]));
        pumpData.setFromTS(fromTS.toLocalDateTime()) ;
        Timestamp toTS = new Timestamp(Long.valueOf(line[i++]));
        pumpData.setToTS(toTS.toLocalDateTime());
        Map<String, HashMap<String, Object>> data = mapper.readValue(line[i++], HashMap.class);
        pumpData.setMetrics(data);
        return pumpData;
    }

    public PumpData getPump(int index) throws IOException, JSONException {
        CSVReader csvReader = new CSVReader(new FileReader("A:\\Projects\\Interview\\LoadAnalsys\\src\\main\\resources\\data\\demoPumpDayData.csv"));
        String[] line = csvReader.readNext(); //skip header
        for(int i = 0; i <= index; i++){
            line = csvReader.readNext();
        }
        return lineToPumpData(line);
    }

    public List<PumpData> getAllPumps() throws IOException, JSONException {
        CSVReader csvReader = new CSVReader(new FileReader("A:\\Projects\\Interview\\LoadAnalsys\\src\\main\\resources\\data\\demoPumpDayData.csv"));
        List<PumpData> pumpDataList = new ArrayList<>();
        Iterator<String[]> itr =  csvReader.iterator();

        itr.next();//skip header

        while(itr.hasNext()){
            String[] line = itr.next();
            pumpDataList.add(lineToPumpData(line));
        }

        return pumpDataList;
    }
}
