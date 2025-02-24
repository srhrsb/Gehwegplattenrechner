package com.brh;

import java.time.LocalDateTime;

public class CalcResult {
    private int plateType;
    private double price;
    private int platesCount;
    private String projectName;
    private LocalDateTime timeStamp;

    public CalcResult( double price, int platesCount, int plateType, String projectName) {
        this.plateType = plateType;
        this.price = price;
        this.platesCount = platesCount;
        timeStamp = LocalDateTime.now();
        this.projectName = projectName;
    }

    public CalcResult(double price, int platesCount, int plateType, String projectName, LocalDateTime timeStamp) {
        this.plateType = plateType;
        this.price = price;
        this.platesCount = platesCount;
        this.timeStamp = timeStamp;
        this.projectName = projectName;
    }

    public int getPlateType() {
        return plateType;
    }

    public double getPrice() {
        return price;
    }

    public int getPlatesCount() {
        return platesCount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getProjectName() {
        return projectName;
    }
}
