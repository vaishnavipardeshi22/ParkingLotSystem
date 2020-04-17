package com.bridgelabz.parkinglotsystemtest;

import com.bridgelabz.parkinglotsystem.AirportSecurity;
import com.bridgelabz.parkinglotsystem.ParkingLotException;
import com.bridgelabz.parkinglotsystem.ParkingLotOwner;
import com.bridgelabz.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    ParkingLotSystem parkingLotSystem;
    Object vehicle;
    ParkingLotOwner owner;
    AirportSecurity airportSecurity = new AirportSecurity();

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(2);
        vehicle = new Object();
        owner = new ParkingLotOwner();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        try {
            boolean vehicleIsPark = parkingLotSystem.isPark(new Object());
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
    public void givenParkingLot_WhenVehicleIsNotParked_ShouldReturnFalse() {
        try {
            boolean isVehicleParked = false;
            isVehicleParked = parkingLotSystem.isUnPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkedVehicleWhichIsNotParked_ThenReturnFalse() {
        try {
            parkingLotSystem.isPark(vehicle);
            boolean isVehicleParked = false;
            isVehicleParked = parkingLotSystem.isUnPark(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
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
        boolean isParkingLotFull;
        try {
            parkingLotSystem.ParkingHandler(owner);
            parkingLotSystem.isPark(vehicle);
            parkingLotSystem.isPark(vehicle);
            isParkingLotFull = owner.isParkingLotFull();
            parkingLotSystem.isPark(vehicle);
        } catch (ParkingLotException e) {
            isParkingLotFull = owner.isParkingLotFull();
            Assert.assertTrue(isParkingLotFull);
        }
    }

    @Test
    public void givenParkingLotIsFull_ShouldInformAirportSecurity() {
        try {
            parkingLotSystem.ParkingHandler(airportSecurity);
            parkingLotSystem.isPark(vehicle);
            parkingLotSystem.isPark(vehicle);
            parkingLotSystem.isPark(vehicle);
        } catch (ParkingLotException e) {
            boolean isParkingLotFull = airportSecurity.isParkingLotFull();
            Assert.assertTrue(isParkingLotFull);
        }
    }
}
