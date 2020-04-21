package com.bridgelabz.parkinglotsystem;

import java.util.*;

public class ParkingLotSystem {
    private int capacity;
    private List<ParkingLot> parkingLotList;

    public ParkingLotSystem(int capacity) {
        this.capacity = capacity;
        this.parkingLotList = new ArrayList<>();
    }

    public void addLots(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public boolean isLotAdd(ParkingLot parkingLot) {
        if (this.parkingLotList.contains(parkingLot)) return true;
        return false;
    }

    public void isPark(ParkingStrategy driverType, Object vehicle) throws ParkingLotException {
        ParkingLot lot = driverType.getParkingLot(this.parkingLotList);
        lot.isPark(driverType, vehicle);
    }

    public boolean isVehiclePark(Object vehicle) {
        if (this.parkingLotList.get(0).isVehiclePark(vehicle)) return true;
        return false;
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        for (int lot = 0; lot < this.parkingLotList.size(); lot++) {
            if (this.parkingLotList.get(lot).isUnPark(vehicle)) return true;
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle not found.");
    }
}
