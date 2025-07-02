package parking.api;

public class GetVehicleInfoRequest {
    private String licensePlate;

    public GetVehicleInfoRequest(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    // Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public String toString() {
        return "GetVehicleInfoRequest{" +
                "licensePlate='" + licensePlate + '\'' +
                '}';
    }
} 