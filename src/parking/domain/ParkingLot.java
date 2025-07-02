package parking.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParkingLot {
    private String parkingLotId;
    private List<ParkingFloor> floors;
    private Map<String, Vehicle> parkedVehicles; // licensePlate -> Vehicle
    private Map<String, ParkingTransaction> activeTransactions; // licensePlate -> Transaction
    private PricingStrategy pricingStrategy;

    public ParkingLot(String parkingLotId) {
        this.parkingLotId = parkingLotId;
        this.floors = new ArrayList<>();
        this.parkedVehicles = new HashMap<>();
        this.activeTransactions = new HashMap<>();
        this.pricingStrategy = new DefaultPricingStrategy();
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public ParkingTransaction parkVehicle(Vehicle vehicle) {
        // Check if vehicle is already parked
        if (parkedVehicles.containsKey(vehicle.getLicensePlate())) {
            throw new IllegalStateException("Vehicle " + vehicle.getLicensePlate() + " is already parked");
        }

        // Find available slot
        Optional<ParkingSlot> availableSlot = findAvailableSlot(vehicle);
        if (availableSlot.isEmpty()) {
            throw new IllegalStateException("No available parking slot for " + vehicle.getType().getDisplayName());
        }

        ParkingSlot slot = availableSlot.get();
        slot.parkVehicle(vehicle);
        parkedVehicles.put(vehicle.getLicensePlate(), vehicle);

        // Create transaction
        ParkingTransaction transaction = new ParkingTransaction(vehicle, slot);
        activeTransactions.put(vehicle.getLicensePlate(), transaction);

        return transaction;
    }

    public ParkingTransaction unparkVehicle(String licensePlate) {
        Vehicle vehicle = parkedVehicles.get(licensePlate);
        if (vehicle == null) {
            throw new IllegalStateException("Vehicle " + licensePlate + " is not parked");
        }

        ParkingTransaction transaction = activeTransactions.get(licensePlate);
        if (transaction == null || !transaction.isActive()) {
            throw new IllegalStateException("No active transaction found for vehicle " + licensePlate);
        }

        ParkingSlot slot = vehicle.getAssignedSlot();
        slot.unparkVehicle();
        parkedVehicles.remove(licensePlate);
        vehicle.setExitTime(java.time.LocalDateTime.now());

        // Complete transaction with fee calculation
        double fee = pricingStrategy.calculateFee(vehicle);
        transaction.completeTransaction(fee);
        activeTransactions.remove(licensePlate);

        return transaction;
    }

    public VehicleInfo getVehicleInfo(String licensePlate) {
        Vehicle vehicle = parkedVehicles.get(licensePlate);
        if (vehicle == null) {
            throw new IllegalStateException("Vehicle " + licensePlate + " is not parked");
        }

        double currentFee = pricingStrategy.calculateFee(vehicle);
        return new VehicleInfo(vehicle, currentFee);
    }

    public ParkingLotStatus getParkingLotStatus() {
        Map<VehicleType, Integer> availableSlotsByType = new HashMap<>();
        Map<VehicleType, Integer> occupiedSlotsByType = new HashMap<>();

        for (VehicleType type : VehicleType.values()) {
            availableSlotsByType.put(type, 0);
            occupiedSlotsByType.put(type, 0);
        }

        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getParkingSlots()) {
                VehicleType slotType = slot.getSlotType();
                if (slot.isOccupied()) {
                    occupiedSlotsByType.put(slotType, occupiedSlotsByType.get(slotType) + 1);
                } else {
                    availableSlotsByType.put(slotType, availableSlotsByType.get(slotType) + 1);
                }
            }
        }

        return new ParkingLotStatus(availableSlotsByType, occupiedSlotsByType, parkedVehicles.size());
    }

    private Optional<ParkingSlot> findAvailableSlot(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSlot> slot = floor.findAvailableSlot(vehicle);
            if (slot.isPresent()) {
                return slot;
            }
        }
        return Optional.empty();
    }

    // Getters
    public String getParkingLotId() {
        return parkingLotId;
    }

    public List<ParkingFloor> getFloors() {
        return new ArrayList<>(floors);
    }

    public Map<String, Vehicle> getParkedVehicles() {
        return new HashMap<>(parkedVehicles);
    }

    public Map<String, ParkingTransaction> getActiveTransactions() {
        return new HashMap<>(activeTransactions);
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }
} 