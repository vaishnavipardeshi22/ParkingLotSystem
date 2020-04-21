package com.bridgelabz.parkinglotsystem;

public class ParkingFactory {

    public static ParkingStrategy getParkingStrategy(Enum type) {
        if (type.equals(DriverType.HANDICAP_DRIVER))
            return new HandicapDriver();
        else if (type.equals(VehicleType.LARGE_VEHICLE))
            return new LargeVehicle();
        return new NormalDriver();
    }
}
