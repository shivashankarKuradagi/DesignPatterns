package parking.api;

import parking.domain.VehicleType;

public class ParkVehicleRequest {
    private String licensePlate;
    private VehicleType vehicleType;

    public ParkVehicleRequest(String licensePlate, VehicleType vehicleType) {
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
    }

    // Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return "ParkVehicleRequest{" +
                "licensePlate='" + licensePlate + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }
} 