package com.bridgelabz.parkinglotsystem;

import com.bridgelabz.observer.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLot {
    private int vehicleCount;
    private int parkingLotCapacity;
    private List<ParkingTimeSlot> vehicles;
    private List<ParkingLotHandler> parkingLotHandler;
    Map<Integer, Object> vehicleSlotMap = new HashMap<>();

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

    public void isPark(Enum driverType, Object vehicle) throws ParkingLotException {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(driverType, vehicle);
        if (!this.vehicles.contains(null)) {
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsFull();
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking lot is full.");
        }
        if (isVehiclePark(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,"Vehicle not found." );
        int slot = getParkingSlot();
        this.vehicles.set(slot, parkingTimeSlot);
        vehicleCount++;
    }

    public boolean isVehiclePark(Object vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot)) return true;
        return false;
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        for (int slotNumber = 0; slotNumber < this.vehicles.size(); slotNumber++ ) {
            if (this.vehicles.contains(parkingTimeSlot)) {
                this.vehicles.set(slotNumber, null);
                vehicleCount--;
                for (ParkingLotHandler handler : parkingLotHandler)
                    handler.parkingIsEmpty();
                return true;
            }
        }
        return false;
    }

    public void isPark(int slot, Object vehicle) throws ParkingLotException {
        if (this.vehicleSlotMap.size() == this.parkingLotCapacity) {
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsFull();
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking lot is full.");
        }
        vehicleSlotMap.put(slot, vehicle);
    }

    public int initializeParkingLot() {
        IntStream.range(0, this.parkingLotCapacity).forEach(slots -> vehicles.add(null));
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

    public int findVehicle(Object vehicle) throws ParkingLotException {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        if (this.vehicles.contains(parkingTimeSlot))
            return this.vehicles.indexOf(parkingTimeSlot);
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "No such vehicle found.");
    }

    public ArrayList getSlot() {
        ArrayList slots = new ArrayList();
        for (int slot = 0; slot < this.parkingLotCapacity; slot++) {
            if (this.vehicles.get(slot) == null)
                slots.add(slot);
        }
        return slots;
    }

    public boolean setTime(Object vehicle) {
        ParkingTimeSlot parkingTimeSlot = new ParkingTimeSlot(vehicle);
        for (int i = 0; i < this.vehicles.size(); i++) {
            if (this.vehicles.get(i).time != null && this.vehicles.contains(parkingTimeSlot))
                return true;
        }
        return false;
    }
}
