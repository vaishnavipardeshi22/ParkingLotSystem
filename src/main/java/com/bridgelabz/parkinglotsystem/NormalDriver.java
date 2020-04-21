package com.bridgelabz.parkinglotsystem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum NormalDriver implements ParkingStrategy {

    NORMAL_DRIVER;

    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) throws ParkingLotException {
        List<ParkingLot> lotList = parkingLotList;
        Collections.sort(lotList, Comparator.comparing(list -> list.getSlot().size(), Comparator.reverseOrder()));
        return lotList.get(0);
    }
}
