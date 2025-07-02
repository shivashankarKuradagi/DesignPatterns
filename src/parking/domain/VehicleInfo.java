package parking.domain;

public class VehicleInfo {
    private Vehicle vehicle;
    private double currentFee;
    private long parkingDurationHours;

    public VehicleInfo(Vehicle vehicle, double currentFee) {
        this.vehicle = vehicle;
        this.currentFee = currentFee;
        this.parkingDurationHours = vehicle.getParkingDurationInHours();
    }

    // Getters
    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getCurrentFee() {
        return currentFee;
    }

    public long getParkingDurationHours() {
        return parkingDurationHours;
    }

    @Override
    public String toString() {
        return "VehicleInfo{" +
                "licensePlate='" + vehicle.getLicensePlate() + '\'' +
                ", vehicleType=" + vehicle.getType().getDisplayName() +
                ", entryTime=" + vehicle.getEntryTime() +
                ", parkingDurationHours=" + parkingDurationHours +
                ", currentFee=" + currentFee +
                '}';
    }
} 