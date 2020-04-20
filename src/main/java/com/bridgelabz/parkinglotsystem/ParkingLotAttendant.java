package com.bridgelabz.parkinglotsystem;

import java.util.List;

public class ParkingLotAttendant {

    private List<ParkingLotOwner> parkingSlots;
    private Object vehicle;

    public ParkingLotAttendant(Object vehicle) {
        this.vehicle = vehicle;
    }

    public Object getVehicle() {
        return vehicle;
    }
}
