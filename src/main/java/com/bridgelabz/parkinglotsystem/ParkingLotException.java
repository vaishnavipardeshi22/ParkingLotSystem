package com.bridgelabz.parkinglotsystem;

public class ParkingLotException extends Exception {
    public enum ExceptionType{
        VEHICLE_NOT_FOUND, PARKING_IS_FULL, NO_SUCH_ATTENDANT;
    }

    public ExceptionType type;

    public ParkingLotException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
