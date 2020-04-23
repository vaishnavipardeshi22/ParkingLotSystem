package com.bridgelabz.strategy;

public class ParkingFactory {

    public static ParkingStrategy getParkingStrategy(Enum type) {
        if (type.equals(DriverType.HANDICAP_DRIVER))
            return new HandicapDriver();
        else if (type.equals(VehicleType.LARGE_VEHICLE))
            return new LargeVehicle();
        else if (type.equals(VehicleType.SMALL_VEHICLE))
            return new SmallVehicle();
        return new NormalDriver();
    }
}
