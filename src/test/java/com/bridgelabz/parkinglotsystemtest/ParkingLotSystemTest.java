package com.bridgelabz.parkinglotsystemtest;

import com.bridgelabz.parkinglotsystem.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem = null;
    Object vehicle = null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle = new Object();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        try {
            parkingLotSystem.isPark(vehicle);
            boolean vehicleIsPark = parkingLotSystem.isVehiclePark(vehicle);
            Assert.assertTrue(vehicleIsPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenParkedVehicleIsUnParked_ThenReturnTrue() {
        try {
            parkingLotSystem.isPark(vehicle);
            boolean isVehiclePark = parkingLotSystem.isUnPark(vehicle);
            Assert.assertTrue(isVehiclePark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ShouldThrowException() {
        try {
            boolean isVehicleParked = parkingLotSystem.isUnPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkedVehicleWhichIsNotParked_ThenReturnFalse() {
        try {
            parkingLotSystem.isPark(vehicle);
            boolean isVehicleParked = parkingLotSystem.isUnPark(new Object());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_CheckVehicleIsNotPresent_ShouldThrwException() {
        try {
            parkingLotSystem.isPark(null);
            boolean isVehiclePark = parkingLotSystem.isVehiclePark(vehicle);
            Assert.assertFalse(isVehiclePark);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLotSystem.isPark(vehicle);
            Object secondVehicle = new Object();
            parkingLotSystem.isPark(secondVehicle);
            parkingLotSystem.isPark(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotSystem.registerParkingHandler(parkingLotOwner);
        try {
            parkingLotSystem.isPark(vehicle);
            parkingLotSystem.isPark(new Object());
            parkingLotSystem.isPark(vehicle);
        } catch (ParkingLotException e) {
        }
            boolean isParkingLotFull = parkingLotOwner.isParkingLotFull();
            Assert.assertTrue(isParkingLotFull);

    }

    @Test
    public void givenParkingLotIsFull_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLotSystem.registerParkingHandler(airportSecurity);
        try {
            parkingLotSystem.isPark(vehicle);
            parkingLotSystem.isPark(new Object());
            parkingLotSystem.isPark(vehicle);
        } catch (ParkingLotException e) {
        }
            boolean isParkingLotFull = airportSecurity.isParkingLotFull();
            Assert.assertTrue(isParkingLotFull);
    }

    @Test
    public void givenParkingLotIsHavingSpace_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotSystem.setParkingLotCapacity(3);
        parkingLotSystem.registerParkingHandler(parkingLotOwner);
        try {
            parkingLotSystem.isPark(vehicle);
            parkingLotSystem.isPark(new Object());
            parkingLotSystem.isPark(vehicle);
            parkingLotSystem.isPark(new Object());
        } catch (ParkingLotException e) {
           e.printStackTrace();
        }
        try {
            parkingLotSystem.isUnPark(vehicle);
        } catch (ParkingLotException e) {
        }
        boolean isParkingLotFull = parkingLotOwner.isParkingLotEmpty();
        Assert.assertFalse(isParkingLotFull);
    }
}
