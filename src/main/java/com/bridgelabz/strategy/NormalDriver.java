package com.bridgelabz.strategy;

import com.bridgelabz.parkinglotsystem.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NormalDriver implements ParkingStrategy {

    @Override
    public ParkingLot getParkingLot(List<ParkingLot> parkingLotList) throws ParkingLotException {
        List<ParkingLot> lotList = new ArrayList<>(parkingLotList);
        Collections.sort(lotList, Comparator.comparing(list -> list.getSlot().size(), Comparator.reverseOrder()));
        return lotList.get(0);
    }
}
