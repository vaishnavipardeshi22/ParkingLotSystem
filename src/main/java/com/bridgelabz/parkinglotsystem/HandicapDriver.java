package com.bridgelabz.parkinglotsystem;

import java.util.List;

public class HandicapDriver implements ParkingStrategy {

    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) throws ParkingLotException {
        ParkingLot lot = parkingLotList.stream()
                .filter(parkingLot -> parkingLot.getSlot().size() > 0)
                .findFirst()
                .orElseThrow(() -> new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle not found."));
        return lot;
    }
}
