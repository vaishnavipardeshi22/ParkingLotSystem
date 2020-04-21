package com.bridgelabz.observer;

public class ParkingLotOwner implements ParkingLotHandler {

    private boolean parkingCapacity;
    private int count = 0;

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

    public int getParkingSlot() {
        return count++;
    }
}
