package com.bridgelabz.parkinglotsystem;

public class Vehicle {

    private String numberPlate;
    private String modelName;
    private String color;

    public Vehicle() {
    }

    public Vehicle(String color) {
        this.color = color;
    }

    public Vehicle(String color, String modelName, String numberPlate) {
        this.color = color;
        this.modelName = modelName;
        this.numberPlate = numberPlate;
    }

    public String getColor() {
        return color;
    }

    public String getModelName() {
        return modelName;
    }

    public String getNumberPlate() {
        return numberPlate;
    }
}
