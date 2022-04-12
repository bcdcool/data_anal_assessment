package com.guidewheel.takehome.demo.load.anal.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Dataset {
    private String label;
    private ArrayList<Double> data;
    private boolean fill;
    private String backgroundColor = "rgba(6, 156,51, .3)";
    private String borderColor = "#02b844";
}
