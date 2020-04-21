package com.bridgelabz.strategy;

import com.bridgelabz.parkinglotsystem.*;

import java.util.List;

public interface ParkingStrategy {

    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) throws ParkingLotException;
}
