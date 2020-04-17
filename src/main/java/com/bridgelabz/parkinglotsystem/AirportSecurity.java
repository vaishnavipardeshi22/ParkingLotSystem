package com.bridgelabz.parkinglotsystem;

public class AirportSecurity implements ParkingLotHandler {

    private Boolean parkingIsFull;

    public Boolean isParkingLotFull() {
        return parkingIsFull;
    }

    @Override
    public void parkingIsFull() {
        parkingIsFull = true;
    }
}
