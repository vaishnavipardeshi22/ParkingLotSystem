package com.bridgelabz.parkinglotsystemtest;

import com.bridgelabz.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem;
    Object vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        vehicle = new Object();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        boolean vehicleIsPark = parkingLotSystem.isPark(new Object());
        Assert.assertTrue(vehicleIsPark);
    }

    @Test
    public void givenParkingLot_WhenParkedVehicleIsUnparked_ThenReturnTrue() {
        parkingLotSystem.isPark(vehicle);
        boolean isVehiclePark = parkingLotSystem.isUnPark(vehicle);
        Assert.assertTrue(isVehiclePark);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ShouldReturnFalse() {
        boolean isVehicleParked = parkingLotSystem.isUnPark(vehicle);
        Assert.assertFalse(isVehicleParked);
    }

    @Test
    public void givenParkingLot_WhenUnParkedVehicleWhichIsNotParked_ThenReturnFalse() {
        parkingLotSystem.isPark(vehicle);
        boolean isVehicleParked = parkingLotSystem.isUnPark(new Object());
        Assert.assertFalse(isVehicleParked);
    }
}
