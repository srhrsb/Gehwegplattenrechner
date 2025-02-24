package com.brh;

import java.time.LocalDateTime;

public class CalcResult {
    private int plateType;
    private double price;
    private int platesCount;
    private LocalDateTime timeStamp;

    public CalcResult( double price, int platesCount, int plateType) {
        this.plateType = plateType;
        this.price = price;
        this.platesCount = platesCount;
        timeStamp = LocalDateTime.now();
    }

    public CalcResult(double price, int platesCount, int plateType, LocalDateTime timeStamp) {
        this.plateType = plateType;
        this.price = price;
        this.platesCount = platesCount;
        this.timeStamp = timeStamp;
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

    public static void example() {

    }
}
