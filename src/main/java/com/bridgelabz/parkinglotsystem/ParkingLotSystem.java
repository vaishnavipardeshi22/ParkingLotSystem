package com.bridgelabz.parkinglotsystem;

public class ParkingLotSystem {
    private Object vehicle;

    public boolean isPark(Object vehicle) {
        this.vehicle = vehicle;
        return true;
    }

    public boolean isUnPark(Object vehicle) {
        if (this.vehicle != null && this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }
}
