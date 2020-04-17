package com.bridgelabz.parkinglotsystem;

public class ParkingLotOwner implements ParkingLotHandler {

    private Boolean parkingIsFull;

    public boolean isParkingLotFull() {
        return parkingIsFull;
    }

    @Override
    public void parkingIsFull() {
        parkingIsFull = true;
    }
}
