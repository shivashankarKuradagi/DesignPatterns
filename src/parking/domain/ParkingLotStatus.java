package parking.domain;

import java.util.Map;

public class ParkingLotStatus {
    private Map<VehicleType, Integer> availableSlotsByType;
    private Map<VehicleType, Integer> occupiedSlotsByType;
    private int totalParkedVehicles;

    public ParkingLotStatus(Map<VehicleType, Integer> availableSlotsByType, 
                           Map<VehicleType, Integer> occupiedSlotsByType, 
                           int totalParkedVehicles) {
        this.availableSlotsByType = availableSlotsByType;
        this.occupiedSlotsByType = occupiedSlotsByType;
        this.totalParkedVehicles = totalParkedVehicles;
    }

    // Getters
    public Map<VehicleType, Integer> getAvailableSlotsByType() {
        return availableSlotsByType;
    }

    public Map<VehicleType, Integer> getOccupiedSlotsByType() {
        return occupiedSlotsByType;
    }

    public int getTotalParkedVehicles() {
        return totalParkedVehicles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Parking Lot Status:\n");
        sb.append("Total Parked Vehicles: ").append(totalParkedVehicles).append("\n");
        
        for (VehicleType type : VehicleType.values()) {
            int available = availableSlotsByType.getOrDefault(type, 0);
            int occupied = occupiedSlotsByType.getOrDefault(type, 0);
            sb.append(type.getDisplayName()).append(": ")
              .append(available).append(" available, ")
              .append(occupied).append(" occupied\n");
        }
        
        return sb.toString();
    }
} 