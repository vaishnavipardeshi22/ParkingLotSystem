package com.bridgelabz.parkinglotsystem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystem {
    private int parkingLotCapacity;
    private int recentParkingLotSize;
    private Object vehicle;
    List<ParkingLotHandler> parkingLotHandler;

    public ParkingLotSystem(int parkingLotCapacity) {
        parkingLotHandler = new ArrayList();
    }

    public void ParkingHandler(ParkingLotHandler owner) {
        parkingLotHandler.add(owner);
    }

    public boolean isPark(Object vehicle) throws ParkingLotException {
        if (this.recentParkingLotSize == this.parkingLotCapacity) {
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsFull();
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking Lot is full.");
        }
        this.vehicle = vehicle;
        recentParkingLotSize++;
        return true;
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null && this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "No such vehicle found.");
    }
}
