package com.bridgelabz.parkinglotsystem;

import com.bridgelabz.observer.*;

import java.util.List;

public class ParkingLotAttendant {

    private List<ParkingLotOwner> parkingSlots;
    private Vehicle vehicle;

    public ParkingLotAttendant(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
