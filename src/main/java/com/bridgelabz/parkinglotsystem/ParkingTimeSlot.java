package com.bridgelabz.parkinglotsystem;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingTimeSlot {

    protected LocalDateTime time;
    protected Object vehicle;
    private Enum driverType;

    public ParkingTimeSlot(Enum driverType, Object vehicle) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();
        this.driverType = driverType;
    }

    public ParkingTimeSlot(Object vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingTimeSlot that = (ParkingTimeSlot) o;
        return Objects.equals(vehicle, that.vehicle);
    }
}
