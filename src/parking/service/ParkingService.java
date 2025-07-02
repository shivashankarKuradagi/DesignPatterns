package parking.service;

import parking.domain.*;
import java.util.Map;

/**
 * Service Layer - Application Services
 * 
 * Responsibilities:
 * 1. Orchestrate domain objects
 * 2. Handle application-specific logic
 * 3. Coordinate between different domain objects
 * 4. Provide a clean API for the controller layer
 * 5. Handle cross-cutting concerns (validation, logging, etc.)
 */
public class ParkingService {
    private ParkingLot parkingLot;

    public ParkingService(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    /**
     * Service Layer: Orchestrates the parking process
     * - Validates input
     * - Creates domain objects
     * - Delegates to domain layer
     * - Handles application-specific logic
     */
    public ParkingTransaction parkVehicle(String licensePlate, VehicleType vehicleType) {
        // Service Layer: Input validation
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }

        // Service Layer: Create domain object
        Vehicle vehicle = new Vehicle(licensePlate, vehicleType);
        
        // Service Layer: Delegate to domain layer
        return parkingLot.parkVehicle(vehicle);
    }

    /**
     * Service Layer: Orchestrates the unparking process
     * - Validates input
     * - Delegates to domain layer
     * - Handles application-specific logic
     */
    public ParkingTransaction unparkVehicle(String licensePlate) {
        // Service Layer: Input validation
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }

        // Service Layer: Delegate to domain layer
        return parkingLot.unparkVehicle(licensePlate);
    }

    /**
     * Service Layer: Orchestrates vehicle info retrieval
     * - Validates input
     * - Delegates to domain layer
     * - Transforms domain data for API consumption
     */
    public VehicleInfo getVehicleInfo(String licensePlate) {
        // Service Layer: Input validation
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }

        // Service Layer: Delegate to domain layer
        return parkingLot.getVehicleInfo(licensePlate);
    }

    /**
     * Service Layer: Orchestrates parking lot status retrieval
     * - Delegates to domain layer
     * - Could add caching, logging, etc.
     */
    public ParkingLotStatus getParkingLotStatus() {
        // Service Layer: Could add logging, caching, etc.
        return parkingLot.getParkingLotStatus();
    }

    /**
     * Service Layer: Business operation that spans multiple domain objects
     * This is a good example of service layer orchestration
     */
    public ParkingTransaction transferVehicle(String licensePlate, String newSlotId) {
        // Service Layer: Input validation
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (newSlotId == null || newSlotId.trim().isEmpty()) {
            throw new IllegalArgumentException("New slot ID cannot be null or empty");
        }

        // Service Layer: Business logic that requires coordination
        // 1. First unpark the vehicle
        ParkingTransaction currentTransaction = parkingLot.unparkVehicle(licensePlate);
        
        // 2. Find the new slot
        ParkingSlot newSlot = findSlotById(newSlotId);
        if (newSlot == null) {
            throw new IllegalArgumentException("Slot " + newSlotId + " not found");
        }
        
        // 3. Repark the vehicle in the new slot
        Vehicle vehicle = currentTransaction.getVehicle();
        vehicle.setEntryTime(java.time.LocalDateTime.now()); // Reset entry time
        return parkingLot.parkVehicle(vehicle);
    }

    /**
     * Service Layer: Utility method to find slot by ID
     * This is application-specific logic that doesn't belong in domain layer
     */
    private ParkingSlot findSlotById(String slotId) {
        for (ParkingFloor floor : parkingLot.getFloors()) {
            for (ParkingSlot slot : floor.getParkingSlots()) {
                if (slot.getSlotId().equals(slotId)) {
                    return slot;
                }
            }
        }
        return null;
    }

    /**
     * Service Layer: Business operation for bulk operations
     * This demonstrates service layer orchestration of multiple domain operations
     */
    public void closeParkingLot() {
        // Service Layer: Business logic for closing parking lot
        // This requires coordination across multiple domain objects
        
        // 1. Get all active transactions
        Map<String, ParkingTransaction> activeTransactions = parkingLot.getActiveTransactions();
        
        // 2. Complete all active transactions
        for (ParkingTransaction transaction : activeTransactions.values()) {
            Vehicle vehicle = transaction.getVehicle();
            // Note: This would need a method to get pricing strategy from parking lot
            // For now, we'll use a simple approach
            double fee = 0.0; // This would be calculated based on vehicle type and duration
            transaction.completeTransaction(fee);
        }
        
        // Service Layer: Could add logging, notifications, etc.
        System.out.println("Parking lot closed. All vehicles have been processed.");
    }

    // Getters for testing and debugging
    public ParkingLot getParkingLot() {
        return parkingLot;
    }
} 