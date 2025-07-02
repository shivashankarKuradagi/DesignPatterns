package parking.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSlot> parkingSlots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.parkingSlots = new ArrayList<>();
    }

    public void addParkingSlot(ParkingSlot slot) {
        parkingSlots.add(slot);
    }

    public Optional<ParkingSlot> findAvailableSlot(Vehicle vehicle) {
        return parkingSlots.stream()
                .filter(slot -> slot.canAccommodate(vehicle))
                .findFirst();
    }

    public List<ParkingSlot> getAvailableSlots(VehicleType vehicleType) {
        return parkingSlots.stream()
                .filter(slot -> slot.getSlotType() == vehicleType && !slot.isOccupied())
                .collect(java.util.stream.Collectors.toList());
    }

    public List<ParkingSlot> getOccupiedSlots() {
        return parkingSlots.stream()
                .filter(ParkingSlot::isOccupied)
                .collect(java.util.stream.Collectors.toList());
    }

    public int getTotalSlots() {
        return parkingSlots.size();
    }

    public int getAvailableSlotsCount() {
        return (int) parkingSlots.stream().filter(slot -> !slot.isOccupied()).count();
    }

    public int getOccupiedSlotsCount() {
        return (int) parkingSlots.stream().filter(ParkingSlot::isOccupied).count();
    }

    // Getters
    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSlot> getParkingSlots() {
        return new ArrayList<>(parkingSlots);
    }

    @Override
    public String toString() {
        return "ParkingFloor{" +
                "floorNumber=" + floorNumber +
                ", totalSlots=" + getTotalSlots() +
                ", availableSlots=" + getAvailableSlotsCount() +
                ", occupiedSlots=" + getOccupiedSlotsCount() +
                '}';
    }
} 