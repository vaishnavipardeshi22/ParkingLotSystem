package com.bridgelabz.parkinglotsystemtest;

import com.bridgelabz.parkinglotsystem.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

    @Test
    public void givenParkingLotSlot_ShouldAttendantParkCar() {
        try {
            ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
            parkingLotSystem.registerParkingHandler(parkingLotOwner);
            ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(vehicle);
            ParkingLotAttendant attendant = parkingLotSystem.getParkingLotAttendant(parkingLotAttendant);
            Assert.assertEquals(attendant, parkingLotAttendant);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLtIsFull_AttendantShouldThrowException() {
        try {
            ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
            parkingLotSystem.registerParkingHandler(parkingLotOwner);
            ParkingLotAttendant attendant1 = new ParkingLotAttendant(vehicle);
            ParkingLotAttendant attendant2 = new ParkingLotAttendant(new Object());
            ParkingLotAttendant attendant3 = new ParkingLotAttendant(vehicle);
            parkingLotSystem.getParkingLotAttendant(attendant1);
            parkingLotSystem.getParkingLotAttendant(attendant2);
            parkingLotSystem.getParkingLotAttendant(attendant3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_ShouldReturnAttendant() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotSystem.registerParkingHandler(parkingLotOwner);
        try {
            ParkingLotAttendant attendant = new ParkingLotAttendant(vehicle);
            parkingLotSystem.getParkingLotAttendant(attendant);
            ParkingLotAttendant myAttendant = parkingLotSystem.getMyVehicle(attendant);
            Assert.assertEquals(attendant, myAttendant);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenAttendantNotAvailable_ThenThrowException() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLotSystem.registerParkingHandler(parkingLotOwner);
        try {
            ParkingLotAttendant attendant = new ParkingLotAttendant(vehicle);
            parkingLotSystem.getParkingLotAttendant(attendant);
            ParkingLotAttendant myAttendant = parkingLotSystem.getMyVehicle(new ParkingLotAttendant(new Object()));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_ATTENDANT, e.type);
        }
    }

    @Test
    public void givenParkingLot_ShouldFindVehicle() {
        parkingLotSystem.setParkingLotCapacity(10);
        parkingLotSystem.initializeParkingLot();
        ArrayList<Integer> slot = parkingLotSystem.getSlot();
        try {
            parkingLotSystem.parkVehicle(slot.get(0), new Object());
            parkingLotSystem.parkVehicle(slot.get(1), vehicle);
            int slotNumber = parkingLotSystem.findVehicle(this.vehicle);
            Assert.assertEquals(1, slotNumber);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
