package com.bridgelabz.parkinglotsystem;

import com.bridgelabz.strategy.ParkingFactory;
import com.bridgelabz.strategy.ParkingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    private int capacity;
    private List<ParkingLot> parkingLotList;

    public ParkingLotSystem(int capacity) {
        this.capacity = capacity;
        this.parkingLotList = new ArrayList<>();
    }

    public void addLots(ParkingLot parkingLot) {
        this.parkingLotList.add(parkingLot);
    }

    public boolean isLotAdd(ParkingLot parkingLot) {
        if (this.parkingLotList.contains(parkingLot)) return true;
        return false;
    }

    public void isPark(Enum driverType, Vehicle vehicle, String attendant) throws ParkingLotException {
        ParkingStrategy parkingStrategy = ParkingFactory.getParkingStrategy(driverType);
        ParkingLot lot = parkingStrategy.getParkingLot(this.parkingLotList);
        lot.isPark(driverType, vehicle, attendant);
    }

    public boolean isVehiclePark(Vehicle vehicle) {
        for (int i = 0; i < this.parkingLotList.size(); i++) {
            if (this.parkingLotList.get(i).isVehiclePark(vehicle)) return true;
        }
        return false;
    }

    public boolean isUnPark(Vehicle vehicle) throws ParkingLotException {
        for (int lot = 0; lot < this.parkingLotList.size(); lot++) {
            if (this.parkingLotList.get(lot).isUnPark(vehicle)) return true;
        }
        throw new ParkingLotException(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, "Vehicle not found.");
    }

    public List<List<Integer>> findVehicleByColor(String color) {
        List<List<Integer>> vehicleList = this.parkingLotList.stream()
                .map(lot -> lot.findByColor(color))
                .collect(Collectors.toList());
        return vehicleList;
    }

    public List<List<String>> findVehicleByModelNameAndColor(String color, String modelName) {
        List<List<String>> vehicleList = new ArrayList<>();
        for (ParkingLot list : this.parkingLotList) {
            List<String> lot = list.findByModelAndColor(color, modelName);
            vehicleList.add(lot);
        }
        return vehicleList;
    }

    public List<List<Integer>> findVehicleByModelName(String modelName) {
        List<List<Integer>> vehicleList = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot.findByModelName(modelName))
                .collect(Collectors.toList());
        return vehicleList;
    }

    public List<List<String>> findByLotNumber(ParkingLot parkingLot2, ParkingLot parkingLot4) {
        List<List<String>> vehicleList = this.parkingLotList.stream()
                .map(parkingLot -> parkingLot2.getVehicleDetailsByLotNumber())
                .map(parkingLot -> parkingLot4.getVehicleDetailsByLotNumber())
                .collect(Collectors.toList());
        return vehicleList;
    }
}
