package com.guidewheel.takehome.demo.load.anal.controller;

import com.guidewheel.takehome.demo.load.anal.model.LineData;
import com.guidewheel.takehome.demo.load.anal.model.PumpData;
import com.guidewheel.takehome.demo.load.anal.service.PumpService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/guidewheel/loadanalysis")
public class LoadAnalysisController {

    @Autowired
    PumpService pumpService;

    @GetMapping("/")
    public String ping(){
        return "ping";
    }

    @GetMapping("/pumpTest/{index}")
    public PumpData pumpTest(@PathVariable int index){
        try {
            return pumpService.getPump(index);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getAllPumps")
    public List<PumpData> getAllPumps(){
        try {
            return pumpService.getAllPumpData();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getAllLineData")
    public Map<String, Map<String, LineData>> getAllLineData(){
        try {
            return pumpService.getAllLineData();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
