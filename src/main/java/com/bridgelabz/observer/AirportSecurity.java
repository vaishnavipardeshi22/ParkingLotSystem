package com.bridgelabz.observer;

public class AirportSecurity implements ParkingLotHandler {

    private boolean parkingCapacity;

    public boolean isParkingLotFull() {
        return this.parkingCapacity;
    }

    public boolean isParkingLotEmpty() {
        return this.parkingCapacity;
    }

    @Override
    public void parkingIsFull() {
        this.parkingCapacity = true;
    }

    @Override
    public void parkingIsEmpty() {
        this.parkingCapacity = false;
    }
}
