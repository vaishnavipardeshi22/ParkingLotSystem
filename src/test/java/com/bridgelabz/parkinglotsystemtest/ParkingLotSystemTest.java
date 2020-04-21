package com.bridgelabz.parkinglotsystemtest;

import com.bridgelabz.parkinglotsystem.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSystemTest {
    ParkingLot parkingLot = null;
    Object vehicle = null;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(1);
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem(1);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            boolean vehicleIsPark = parkingLot.isVehiclePark(vehicle);
            Assert.assertTrue(vehicleIsPark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenParkedVehicleIsUnParked_ThenReturnTrue() {
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            boolean isVehiclePark = parkingLot.isUnPark(vehicle);
            Assert.assertTrue(isVehiclePark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ShouldThrowException() {
        parkingLot.initializeParkingLot();
        try {
            boolean isVehicleParked = parkingLot.isUnPark(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkedVehicleWhichIsNotParked_ThenReturnFalse() {
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            boolean isVehicleParked = parkingLot.isUnPark(new Object());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_CheckVehicleIsNotPresent_ShouldThrowException() {
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, null);
            boolean isVehiclePark = parkingLot.isVehiclePark(vehicle);
            Assert.assertFalse(isVehiclePark);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            Object secondVehicle = new Object();
            parkingLot.isPark(DriverType.NORMAL_DRIVER, secondVehicle);
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLotIsFull_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.registerParkingHandler(parkingLotOwner);
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
        } catch (ParkingLotException e) {
        }
        boolean isParkingLotFull = parkingLotOwner.isParkingLotFull();
        Assert.assertTrue(isParkingLotFull);

    }

    @Test
    public void givenParkingLotIsFull_ShouldInformAirportSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingHandler(airportSecurity);
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
        } catch (ParkingLotException e) {
        }
        boolean isParkingLotFull = airportSecurity.isParkingLotFull();
        Assert.assertTrue(isParkingLotFull);
    }

    @Test
    public void givenParkingLotHavingSpace_ShouldInformAirportSecurity() {
        parkingLot.setParkingLotCapacity(2);
        parkingLot.initializeParkingLot();
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingHandler(airportSecurity);
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
        } catch (ParkingLotException e) {
        }
        try {
            parkingLot.isUnPark(vehicle);
        } catch (ParkingLotException e) {
        }
        boolean isParkingAvailable = airportSecurity.isParkingLotEmpty();
        Assert.assertFalse(isParkingAvailable);
    }

    @Test
    public void givenParkingLotIsHavingSpace_ShouldInformOwner() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.setParkingLotCapacity(3);
        parkingLot.initializeParkingLot();
        parkingLot.registerParkingHandler(parkingLotOwner);
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
        try {
            parkingLot.isUnPark(vehicle);
        } catch (ParkingLotException e) {
        }
        boolean isParkingLotFull = parkingLotOwner.isParkingLotEmpty();
        Assert.assertFalse(isParkingLotFull);
    }

    @Test
    public void givenParkingLotSlot_ShouldAttendantParkCar() {
        parkingLot.initializeParkingLot();
        try {
            ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
            parkingLot.registerParkingHandler(parkingLotOwner);
            ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(vehicle);
            ParkingLotAttendant attendant = parkingLot.getParkingLotAttendant(parkingLotAttendant);
            Assert.assertEquals(attendant, parkingLotAttendant);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenParkingLtIsFull_AttendantShouldThrowException() {
        parkingLot.initializeParkingLot();
        try {
            ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
            parkingLot.registerParkingHandler(parkingLotOwner);
            ParkingLotAttendant attendant1 = new ParkingLotAttendant(vehicle);
            ParkingLotAttendant attendant2 = new ParkingLotAttendant(new Object());
            ParkingLotAttendant attendant3 = new ParkingLotAttendant(vehicle);
            parkingLot.getParkingLotAttendant(attendant1);
            parkingLot.getParkingLotAttendant(attendant2);
            parkingLot.getParkingLotAttendant(attendant3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_ShouldReturnAttendant() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.registerParkingHandler(parkingLotOwner);
        parkingLot.initializeParkingLot();
        try {
            ParkingLotAttendant attendant = new ParkingLotAttendant(vehicle);
            parkingLot.getParkingLotAttendant(attendant);
            ParkingLotAttendant myAttendant = parkingLot.getMyVehicle(attendant);
            Assert.assertEquals(attendant, myAttendant);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenAttendantNotAvailable_ThenThrowException() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.registerParkingHandler(parkingLotOwner);
        try {
            ParkingLotAttendant attendant = new ParkingLotAttendant(vehicle);
            parkingLot.getParkingLotAttendant(attendant);
            ParkingLotAttendant myAttendant = parkingLot.getMyVehicle(new ParkingLotAttendant(new Object()));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_ATTENDANT, e.type);
        }
    }

    @Test
    public void givenParkingLot_ShouldFindVehicle() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Object());
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            int slotNumber = parkingLot.findVehicle(this.vehicle);
            Assert.assertEquals(1, slotNumber);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingSlot_WhenTimeIsSet_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle);
            boolean isTimeSet = parkingLot.setTime(vehicle);
            Assert.assertTrue(isTimeSet);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_WhenAddLot_ReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);
        boolean lotAdded = parkingLotSystem.isLotAdd(parkingLot);
        Assert.assertTrue(lotAdded);
    }

    @Test
    public void givenParkingLotSystem_WhenLotNotAdded_ThenReturnFalse() {
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLotSystem.addLots(parkingLot);
        boolean isLotAdded = parkingLotSystem.isLotAdd(parkingLot1);
        Assert.assertFalse(isLotAdded);
    }

    @Test
    public void givenParkingLotSystem_WhenLotsAdded_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot.initializeParkingLot();
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);
        parkingLotSystem.addLots(parkingLot1);
        boolean isLotAdded = parkingLotSystem.isLotAdd(parkingLot1);
        Assert.assertTrue(isLotAdded);
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedOnLotPosition_ThenReturnTrue() {
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLotSystem.addLots(parkingLot1);
        parkingLot1.setParkingLotCapacity(1);
        parkingLot1.initializeParkingLot();
        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle);
            boolean isVehiclePark = parkingLotSystem.isVehiclePark(vehicle);
            Assert.assertTrue(isVehiclePark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedOnLotPosition_ThenReturnFalse() {
        parkingLotSystem.addLots(parkingLot);
        parkingLot.setParkingLotCapacity(1);
        parkingLot.initializeParkingLot();
        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle);
            boolean isVehiclePark = parkingLotSystem.isVehiclePark(new Object());
            Assert.assertFalse(isVehiclePark);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_ShouldParkVehicleEvenly() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);

        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingLot();

        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        parkingLotSystem.addLots(parkingLot1);
        
        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle);
            boolean isParked1 = parkingLotSystem.isVehiclePark(vehicle);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1);
            boolean isParked2 = parkingLotSystem.isVehiclePark(vehicle1);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2);
            boolean isParked3 = parkingLotSystem.isVehiclePark(vehicle2);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3);
            boolean isParked4 = parkingLotSystem.isVehiclePark(vehicle3);
            Assert.assertTrue(isParked1 && isParked2 && isParked3 && isParked4);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenEvenlyDistributed_ThenUnParkVehicle() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);
        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingLot();
        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        parkingLotSystem.addLots(parkingLot1);
        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1);
            boolean isUnPark = parkingLotSystem.isUnPark(vehicle);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkVehicleNotAvailable_ThenThrowException() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);

        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1);
            boolean isUnPark = parkingLotSystem.isUnPark(vehicle2);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenDriverTypeIsHandicap_ShouldReturnNearestLot() {
        ParkingStrategy parkingStrategy = ParkingFactory.getParkingStrategy(DriverType.HANDICAP_DRIVER);
        List<ParkingLot> lotList = new ArrayList<>();
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotCapacity(10);
        lot.initializeParkingLot();
        lotList.add(lot);
        ParkingLot parkingLot = null;
        try {
            parkingLot = parkingStrategy.getParkingLot(lotList);
            Assert.assertEquals(lot, parkingLot);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenDriverTypeIsHandicap_ShouldParkVehicleAtNearestPlace() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);

        ParkingLot parkingLot1 = new ParkingLot(10);
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotCapacity(10);
        parkingLot2.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot2);

        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();
        Object vehicle5 = new Object();

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3);
            parkingLotSystem.isPark(DriverType.HANDICAP_DRIVER, vehicle4);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle5);
            Assert.assertTrue(true);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenLargeVehicleParked_ThenReturnTrue() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotCapacity(10);
        parkingLot2.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot2);

        Object vehicle1 = new Object();
        Object vehicle2 = new Object();
        Object vehicle3 = new Object();
        Object vehicle4 = new Object();

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2);
            parkingLotSystem.isPark(VehicleType.LARGE_VEHICLE, vehicle3);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle4);
            boolean isVehiclePark = parkingLotSystem.isVehiclePark(vehicle3);
            Assert.assertTrue(isVehiclePark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
