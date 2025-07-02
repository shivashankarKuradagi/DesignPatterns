package parking.domain;

public class DefaultPricingStrategy implements PricingStrategy {
    
    @Override
    public double calculateFee(Vehicle vehicle) {
        long hours = vehicle.getParkingDurationInHours();
        double hourlyRate = getHourlyRate(vehicle.getType());
        return hours * hourlyRate;
    }

    private double getHourlyRate(VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE:
                return 1.0; // $1 per hour
            case CAR:
                return 2.0; // $2 per hour
            case BUS:
                return 5.0; // $5 per hour
            default:
                return 0.0;
        }
    }
} 