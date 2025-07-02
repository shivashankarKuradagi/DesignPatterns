package parking.api;

public class UnparkVehicleRequest {
    private String licensePlate;

    public UnparkVehicleRequest(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    // Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return "UnparkVehicleRequest{" +
                "licensePlate='" + licensePlate + '\'' +
                '}';
    }
} 