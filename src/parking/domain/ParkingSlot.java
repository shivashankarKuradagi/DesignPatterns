package parking.domain;

public class ParkingSlot {
    private String slotId;
    private VehicleType slotType;
    private boolean isOccupied;
    private Vehicle parkedVehicle;
    private int floorNumber;

    public ParkingSlot(String slotId, VehicleType slotType, int floorNumber) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.floorNumber = floorNumber;
        this.isOccupied = false;
    }

    public boolean canAccommodate(Vehicle vehicle) {
        return !isOccupied && slotType.canAccommodate(vehicle.getType());
    }

    public void parkVehicle(Vehicle vehicle) {
        if (!canAccommodate(vehicle)) {
            throw new IllegalStateException("Slot cannot accommodate this vehicle type");
        }
        this.parkedVehicle = vehicle;
        this.isOccupied = true;
        vehicle.setAssignedSlot(this);
    }

    public Vehicle unparkVehicle() {
        if (!isOccupied) {
            throw new IllegalStateException("Slot is already empty");
        }
        Vehicle vehicle = this.parkedVehicle;
        this.parkedVehicle = null;
        this.isOccupied = false;
        vehicle.setAssignedSlot(null);
        return vehicle;
    }

    // Getters and Setters
    public String getSlotId() {
        return slotId;
    }

    public VehicleType getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public String toString() {
        return "ParkingSlot{" +
                "slotId='" + slotId + '\'' +
                ", slotType=" + slotType.getDisplayName() +
                ", isOccupied=" + isOccupied +
                ", floorNumber=" + floorNumber +
                '}';
    }
} 