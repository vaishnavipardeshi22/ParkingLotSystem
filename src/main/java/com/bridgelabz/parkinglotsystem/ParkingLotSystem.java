package com.bridgelabz.parkinglotsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotSystem {
    private int parkingLotCapacity;
    private ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
    private List<Object> vehicles;
    private List<ParkingLotHandler> parkingLotHandler;
    Map<Integer, Object> vehicleSlotMap = new HashMap<>();

    public ParkingLotSystem(int parkingLotCapacity) {
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

    public void isPark(Object vehicle) throws ParkingLotException {
        if (this.vehicles.size() == this.parkingLotCapacity) {
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsFull();
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking lot is full.");
        }
        if (isVehiclePark(vehicle))
            throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,"Vehicle not found." );
        this.vehicles.add(vehicle);
    }

    public boolean isVehiclePark(Object vehicle) {
        if (this.vehicles.contains(vehicle)) return true;
        return false;
    }

    public boolean isUnPark(Object vehicle) throws ParkingLotException {
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsEmpty();
            return true;
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle not found.");
    }

    public void isPark(int slot, Object vehicle) throws ParkingLotException {
        if (this.vehicleSlotMap.size() == this.parkingLotCapacity) {
            for (ParkingLotHandler handler : parkingLotHandler)
                handler.parkingIsFull();
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_IS_FULL, "Parking lot is full.");
        }
        vehicleSlotMap.put(slot, vehicle);
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
}
