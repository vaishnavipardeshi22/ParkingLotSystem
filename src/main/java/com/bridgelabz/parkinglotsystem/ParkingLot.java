package com.bridgelabz.parkinglotsystem;

import com.bridgelabz.observer.ParkingLotHandler;
import com.bridgelabz.observer.ParkingLotOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    Map<Integer, Vehicle> vehicleSlotMap = new HashMap<>();
    private int vehicleCount;
    private int parkingLotCapacity;
    private List<ParkingTimeSlot> vehicles;
    private List<ParkingLotHandler> parkingLotHandler;

    public ParkingLot() {
        this.parkingLotHandler = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    public ParkingLot(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
        this.parkingLotHandler = new ArrayList<>();
        this.vehicles = new ArrayList();
    }

    public void registerParkingHandler(ParkingLotHandler handler) {
        this.parkingLotHandler.add(handler);
    }

    public void setParkingLotCapacity(int parkingLotCapacity) {
        this.parkingLotCapacity = parkingLotCapacity;
    }

    public void isPark(Enum driverType, Vehicle vehicle, String attendantName) throws ParkingLotException {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(driverType, vehicle, attendantName);
        if (isVehiclePark(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking is full.");
        int slot = getParkingSlot();
        parkingTimeSlot.setSlot(slot);
        this.vehicles.set(slot, parkingTimeSlot);
        vehicleCount++;
    }

    public boolean isVehiclePark(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot)) return true;
        return false;
    }

    public boolean isUnPark(Vehicle vehicle) throws ParkingLotException {
        boolean isVehiclePresent = this.vehicles.stream()
                .filter(parkingTimeSlot -> (vehicle) == parkingTimeSlot.getVehicle())
                .findFirst()
                .isPresent();
        for (ParkingLotHandler handler : parkingLotHandler)
            handler.parkingIsEmpty();
        if (isVehiclePresent) return true;
        return false;
    }

    public void isPark(int slot, Vehicle vehicle) throws ParkingLotException {
        if (this.vehicleSlotMap.size() == this.parkingLotCapacity) {
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsFull();
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking lot is full.");
        }
        vehicleSlotMap.put(slot, vehicle);
    }

    public int initializeParkingLot() {
        IntStream.range(0, this.parkingLotCapacity).forEach(slots -> this.vehicles.add(new ParkingTimeSlot(slots)));
        return vehicles.size();
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public int getParkingSlot() throws ParkingLotException {
        ArrayList<Integer> slotList = getSlot();
        for (int slot = 0; slot < slotList.size(); slot++) {
            if (slotList.get(0) == (slot))
                return slot;
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking is full.");
    }

    public ParkingLotAttendant getParkingLotAttendant(ParkingLotAttendant attendant) throws ParkingLotException {
        ParkingLotOwner parkingLotOwner = (ParkingLotOwner) parkingLotHandler.get(0);
        isPark(parkingLotOwner.getParkingSlot(), attendant.getVehicle());
        return attendant;
    }

    public ParkingLotAttendant getMyVehicle(ParkingLotAttendant attendant) throws ParkingLotException {
        if (vehicleSlotMap.containsValue(attendant.getVehicle()))
            return attendant;
        throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_ATTENDANT, "No attendant found.");
    }

    public int findVehicle(Vehicle vehicle) throws ParkingLotException {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return this.vehicles.indexOf(parkingTimeSlot);
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "No such vehicle found.");
    }

    public ArrayList getSlot() {
        ArrayList<Integer> slots = new ArrayList();
        IntStream.range(0, parkingLotCapacity)
                .filter(slot -> this.vehicles.get(slot).getVehicle() == null)
                .forEach(slot -> slots.add(slot));
        if (slots.size() == 0) {
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsFull();
        }
        return slots;
    }

    public boolean setTime(Vehicle vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        for (int i = 0; i < this.vehicles.size(); i++) {
            if (this.vehicles.get(i).time != null && this.vehicles.contains(parkingTimeSlot)) return true;
        }
        return false;
    }

    public List<Integer> findByColor(String color) {
        List<Integer> colourList = new ArrayList<>();
        colourList = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getColor().equals(color))
                .map(parkingTimeSlot -> parkingTimeSlot.getSlot())
                .collect(Collectors.toList());
        return colourList;
    }

    public List<String> findByModelAndColor(String color, String modelName) {
        List<String> list = new ArrayList<>();
        list = this.vehicles.stream()
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle() != null)
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getModelName().equals(modelName))
                .filter(parkingTimeSlot -> parkingTimeSlot.getVehicle().getColor().equals(color))
                .map(parkingTimeSlot -> (parkingTimeSlot.getAttendantName()) + " " + (parkingTimeSlot.getSlot()) + " " +
                                        (parkingTimeSlot.vehicle.getNumberPlate()))
                .collect(Collectors.toList());
        return list;
    }
}
