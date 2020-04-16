package com.bridgelabz.parkinglotsystemtest;

import com.bridgelabz.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        boolean vehicleIsPark = parkingLotSystem.isPark(new Object());
        Assert.assertTrue(vehicleIsPark);
    }
}
