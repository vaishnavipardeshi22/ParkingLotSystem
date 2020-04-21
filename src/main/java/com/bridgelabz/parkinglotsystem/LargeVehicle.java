package com.bridgelabz.parkinglotsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LargeVehicle implements ParkingStrategy {

    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) throws ParkingLotException {
        List<ParkingLot> lotList = new ArrayList<>(parkingLotList);
        Collections.sort(lotList, Comparator.comparing(parkingLot -> parkingLot.getVehicleCount()));
        return lotList.get(0);
    }
}
