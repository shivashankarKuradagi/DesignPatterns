package parking.domain;

public enum VehicleType {
    MOTORCYCLE(1, "Motorcycle"),
    CAR(2, "Car"),
    BUS(3, "Bus");

    private final int id;
    private final String displayName;

    VehicleType(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean canAccommodate(VehicleType otherType) {
        switch (this) {
            case MOTORCYCLE:
                return otherType == MOTORCYCLE;
            case CAR:
                return otherType == MOTORCYCLE || otherType == CAR;
            case BUS:
                return otherType == MOTORCYCLE || otherType == CAR || otherType == BUS;
            default:
                return false;
        }
    }
} 