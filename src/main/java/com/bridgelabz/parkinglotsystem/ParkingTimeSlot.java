package com.bridgelabz.parkinglotsystem;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingTimeSlot {

    protected String attendantName;
    protected LocalDateTime time;
    protected Vehicle vehicle;
    private Enum driverType;
    private int slot;

    public ParkingTimeSlot(Enum driverType, Vehicle vehicle, String attendantName) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
        this.driverType = driverType;
        this.attendantName = attendantName;
    }

    public ParkingTimeSlot(int slot) {
        this.slot = slot;
    }

    public ParkingTimeSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTimeSlot that = (ParkingTimeSlot) o;
        return Objects.equals(vehicle, that.vehicle);
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getAttendantName() {
        return attendantName;
    }
}
