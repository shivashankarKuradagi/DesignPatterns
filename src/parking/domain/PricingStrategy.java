package parking.domain;

public interface PricingStrategy {
    double calculateFee(Vehicle vehicle);
} 