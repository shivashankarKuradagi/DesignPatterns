package parking.domain;

import java.time.LocalDateTime;

public class ParkingTicket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot slot;
    private LocalDateTime issueTime;

    public ParkingTicket(Vehicle vehicle, ParkingSlot slot) {
        this.ticketId = generateTicketId();
        this.vehicle = vehicle;
        this.slot = slot;
        this.issueTime = LocalDateTime.now();
    }

    private String generateTicketId() {
        return "TKT-" + System.currentTimeMillis();
    }

    // Getters
    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "ticketId='" + ticketId + '\'' +
                ", vehicle=" + vehicle.getLicensePlate() +
                ", slot=" + slot.getSlotId() +
                ", issueTime=" + issueTime +
                '}';
    }
} 