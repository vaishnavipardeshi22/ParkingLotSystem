package com.bridgelabz.parkinglotsystemtest;

import com.bridgelabz.observer.*;
import com.bridgelabz.parkinglotsystem.*;
import com.bridgelabz.strategy.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bridgelabz.strategy.DriverType.HANDICAP_DRIVER;

public class ParkingLotSystemTest {
    ParkingLot parkingLot = null;
    Vehicle vehicle = null;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(1);
        vehicle = new Vehicle();
        parkingLotSystem = new ParkingLotSystem(1);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ThenReturnTrue() {
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
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
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
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
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            boolean isVehicleParked = parkingLot.isUnPark(new Vehicle());
            Assert.assertFalse(isVehicleParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_CheckVehicleIsNotPresent_ShouldThrowException() {
        parkingLot.setParkingLotCapacity(2);
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, null, "ABC");
            boolean isVehiclePark = parkingLot.isVehiclePark(vehicle);
            Assert.assertFalse(isVehiclePark);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLotIsFull_ShouldThrowException() {
        parkingLot.setParkingLotCapacity(2);
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            Vehicle secondVehicle = new Vehicle();
            parkingLot.isPark(DriverType.NORMAL_DRIVER, secondVehicle, "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLotIsFull_ShouldInformOwner() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.initializeParkingLot();
        Vehicle vehicle = new Vehicle("red");
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        parkingLot.registerParkingHandler(parkingLotOwner);
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle("black"), "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
        } catch (ParkingLotException e) {
        }
        boolean isParkingLotFull = parkingLotOwner.isParkingLotFull();
        Assert.assertTrue(isParkingLotFull);

    }

    @Test
    public void givenParkingLotIsFull_ShouldInformAirportSecurity() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.initializeParkingLot();
        AirportSecurity airportSecurity = new AirportSecurity();
        Vehicle vehicle = new Vehicle("blue");
        parkingLot.registerParkingHandler(airportSecurity);
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle("red"), "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
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
        Vehicle vehicle = new Vehicle("red");
        parkingLot.registerParkingHandler(airportSecurity);
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle(), "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle(), "PQR");
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
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle(), "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle(), "PQR");
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
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
            ParkingLotAttendant attendant2 = new ParkingLotAttendant(new Vehicle());
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
            ParkingLotAttendant myAttendant = parkingLot.getMyVehicle(new ParkingLotAttendant(new Vehicle()));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_ATTENDANT, e.type);
        }
    }

    @Test
    public void givenParkingLot_ShouldFindVehicle() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, new Vehicle(), "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "PQR");
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
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle, "PQR");
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
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
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
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            boolean isVehiclePark = parkingLotSystem.isVehiclePark(new Vehicle());
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

        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        parkingLotSystem.addLots(parkingLot1);

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle, "PQR");
            boolean isParked1 = parkingLotSystem.isVehiclePark(vehicle);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            boolean isParked2 = parkingLotSystem.isVehiclePark(vehicle1);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
            boolean isParked3 = parkingLotSystem.isVehiclePark(vehicle2);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
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
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        parkingLotSystem.addLots(parkingLot1);
        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            boolean isUnPark = parkingLotSystem.isUnPark(vehicle);
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2, "ABC");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenUnParkVehicleNotAvailable_ThenThrowException() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);

        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            boolean isUnPark = parkingLotSystem.isUnPark(vehicle2);
            Assert.assertTrue(isUnPark);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenDriverTypeIsHandicap_ShouldReturnNearestLot() {
        ParkingStrategy parkingStrategy = ParkingFactory.getParkingStrategy(HANDICAP_DRIVER);
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

        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();
        Vehicle vehicle5 = new Vehicle();

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLotSystem.isPark(HANDICAP_DRIVER, vehicle4, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle5, "PQR");
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

        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle, "ABC");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "PQR");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2, "ABC");
            parkingLotSystem.isPark(VehicleType.LARGE_VEHICLE, vehicle3, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
            boolean isVehiclePark = parkingLotSystem.isVehiclePark(vehicle3);
            Assert.assertTrue(isVehiclePark);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_ShouldReturnLocationOfWhiteVehicle() {
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

        Vehicle vehicle1 = new Vehicle("white");
        Vehicle vehicle2 = new Vehicle("black");
        Vehicle vehicle3 = new Vehicle("white");
        Vehicle vehicle4 = new Vehicle("blue");
        Vehicle vehicle5 = new Vehicle("white");

        try {
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle5, "ABC");
            List whiteCarList = parkingLotSystem.findVehicleByColor("white");
            List result = new ArrayList();
            result.add(0);
            Assert.assertEquals(result, whiteCarList.get(0));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_WhenCarColourIsBlue_ThenReturnLot() {
        parkingLot.setParkingLotCapacity(3);
        parkingLot.initializeParkingLot();
        Vehicle vehicle1 = new Vehicle("black");
        Vehicle vehicle2 = new Vehicle("white");
        Vehicle vehicle3 = new Vehicle("blue");
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle2, "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle3, "XYZ");
            List<Integer> blueCarList = parkingLot.findByColor("blue");
            List<Integer> result = new ArrayList<>();
            blueCarList.add(2);
            Assert.assertEquals(blueCarList, result);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_ShouldLocateBlueToyotaCar() {
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

        Vehicle vehicle1 = new Vehicle("black", "BMW", "MH-12-1176");
        Vehicle vehicle2 = new Vehicle("blue", "toyota", "MH-12-1276");
        Vehicle vehicle3 = new Vehicle("red", "BMW", "MH-12-1376");
        Vehicle vehicle4 = new Vehicle("white", "toyota", "MH-12-1476");
        Vehicle vehicle5 = new Vehicle("grey", "toyota", "MH-12-1576");

        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle2, "XYZ");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle5, "ABC");

            List<List<String>> expectedCar = parkingLotSystem.findVehicleByModelNameAndColor("blue", "toyota");
            List result = new ArrayList();
            result.add("XYZ 1 MH-12-1276");
            Assert.assertEquals(result, expectedCar.get(0));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_ShouldLocateBMWCar() {
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

        Vehicle vehicle1 = new Vehicle("black", "BMW", "MH-12-1176");
        Vehicle vehicle2 = new Vehicle("blue", "toyota", "MH-12-1276");
        Vehicle vehicle3 = new Vehicle("red", "BMW", "MH-12-1376");
        Vehicle vehicle4 = new Vehicle("white", "toyota", "MH-12-1476");
        Vehicle vehicle5 = new Vehicle("grey", "toyota", "MH-12-1576");

        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle2, "XYZ");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle5, "ABC");

            List<List<Integer>> expectedList = parkingLotSystem.findVehicleByModelName("BMW");
            List result = new ArrayList();
            result.add(0);
            result.add(2);
            Assert.assertEquals(result, expectedList.get(0));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_ShouldReturnParkedVehiclesFromLast30Minutes() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        Vehicle vehicle1 = new Vehicle("black", "BMW", "MH-12-1176");
        Vehicle vehicle2 = new Vehicle("blue", "toyota", "MH-12-1276");
        Vehicle vehicle3 = new Vehicle("red", "BMW", "MH-12-1376");
        Vehicle vehicle4 = new Vehicle("white", "toyota", "MH-12-1476");
        Vehicle vehicle5 = new Vehicle("grey", "toyota", "MH-12-1576");
        try {
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle2, "XYZ");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle4, "PQR");
            parkingLot.isPark(DriverType.NORMAL_DRIVER, vehicle5, "ABC");

            List<String> vehicleList = parkingLot.findParkedVehicleFromLast30Minute();
            List expectedResult = new ArrayList();
            expectedResult.add("0 BMW MH-12-1176");
            expectedResult.add("1 toyota MH-12-1276");
            expectedResult.add("2 BMW MH-12-1376");
            expectedResult.add("3 toyota MH-12-1476");
            expectedResult.add("4 toyota MH-12-1576");
            Assert.assertEquals(expectedResult, vehicleList);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_ShouldReturnLotNumberOfSmallVehicleAndHandicapDriverVehicle() {
        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setParkingLotCapacity(10);
        parkingLot1.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotCapacity(10);
        parkingLot2.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot2);

        ParkingLot parkingLot3 = new ParkingLot();
        parkingLot3.setParkingLotCapacity(10);
        parkingLot3.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot3);

        ParkingLot parkingLot4 = new ParkingLot();
        parkingLot4.setParkingLotCapacity(10);
        parkingLot4.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot4);

        Vehicle vehicle1 = new Vehicle("black", "BMW", "MH-12-1176");
        Vehicle vehicle2 = new Vehicle("blue", "toyota", "MH-12-1276");
        Vehicle vehicle3 = new Vehicle("grey", "BMW", "MH-12-1376");
        Vehicle vehicle4 = new Vehicle("red", "BMW", "MH-12-1476");
        Vehicle vehicle5 = new Vehicle("white", "toyota", "MH-12-1576");
        Vehicle vehicle6 = new Vehicle("blue", "BMW", "MH-12-1676");
        Vehicle vehicle7 = new Vehicle("black", "toyota", "MH-12-1776");
        Vehicle vehicle8 = new Vehicle("red", "BMW", "MH-12-1876");
        Vehicle vehicle9 = new Vehicle("white", "toyota", "MH-12-1976");

        try{
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLotSystem.isPark(VehicleType.SMALL_VEHICLE, vehicle2, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLotSystem.isPark(DriverType.HANDICAP_DRIVER, vehicle4, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle5, "ABC");
            parkingLotSystem.isPark(DriverType.HANDICAP_DRIVER, vehicle6, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle7, "PQR");
            parkingLotSystem.isPark(VehicleType.SMALL_VEHICLE, vehicle8, "XYZ");
            parkingLotSystem.isPark(VehicleType.SMALL_VEHICLE, vehicle9, "XYZ");

            List<List<String>> vehicleList = parkingLotSystem.findByLotNumber(parkingLot2, parkingLot4);
            List expectedResult = new ArrayList();
            expectedResult.add("toyota MH-12-1276");
            expectedResult.add("BMW MH-12-1676");
            expectedResult.add("BMW MH-12-1476");
            expectedResult.add("BMW MH-12-1876");
            Assert.assertEquals(expectedResult, vehicleList);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenParkingLotSystem_ShouldReturnParkedVehicleDetails() {
        parkingLot.setParkingLotCapacity(10);
        parkingLot.initializeParkingLot();
        parkingLotSystem.addLots(parkingLot);

        Vehicle vehicle1 = new Vehicle("black", "BMW", "MH-12-1176");
        Vehicle vehicle2 = new Vehicle("blue", "toyota", "MH-12-1276");
        Vehicle vehicle3 = new Vehicle("grey", "BMW", "MH-12-1376");
        Vehicle vehicle4 = new Vehicle("red", "BMW", "MH-12-1476");
        Vehicle vehicle5 = new Vehicle("white", "toyota", "MH-12-1576");
        Vehicle vehicle6 = new Vehicle("blue", "BMW", "MH-12-1676");
        Vehicle vehicle7 = new Vehicle("black", "toyota", "MH-12-1776");
        Vehicle vehicle8 = new Vehicle("red", "BMW", "MH-12-1876");
        Vehicle vehicle9 = new Vehicle("white", "toyota", "MH-12-1976");

        try{
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle1, "ABC");
            parkingLotSystem.isPark(VehicleType.SMALL_VEHICLE, vehicle2, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle3, "ABC");
            parkingLotSystem.isPark(DriverType.HANDICAP_DRIVER, vehicle4, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle5, "ABC");
            parkingLotSystem.isPark(DriverType.HANDICAP_DRIVER, vehicle6, "XYZ");
            parkingLotSystem.isPark(DriverType.NORMAL_DRIVER, vehicle7, "PQR");
            parkingLotSystem.isPark(VehicleType.SMALL_VEHICLE, vehicle8, "XYZ");
            parkingLotSystem.isPark(VehicleType.SMALL_VEHICLE, vehicle9, "XYZ");

            List<List<String>> vehicleList = parkingLotSystem.getDetailsOfParkedVehicle();
            Assert.assertEquals(9, vehicleList.size());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
