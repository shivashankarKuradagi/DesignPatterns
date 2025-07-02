package parking.domain;

import java.time.LocalDateTime;

public class ParkingTransaction {
    private String transactionId;
    private Vehicle vehicle;
    private ParkingSlot slot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double fee;
    private TransactionStatus status;

    public enum TransactionStatus {
        ACTIVE,    // Vehicle is parked
        COMPLETED  // Vehicle has been unparked and fee calculated
    }

    public ParkingTransaction(Vehicle vehicle, ParkingSlot slot) {
        this.transactionId = generateTransactionId();
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = LocalDateTime.now();
        this.status = TransactionStatus.ACTIVE;
        this.fee = 0.0;
    }

    public void completeTransaction(double fee) {
        this.exitTime = LocalDateTime.now();
        this.fee = fee;
        this.status = TransactionStatus.COMPLETED;
    }

    public long getParkingDurationInHours() {
        LocalDateTime endTime = exitTime != null ? exitTime : LocalDateTime.now();
        return java.time.Duration.between(entryTime, endTime).toHours();
    }

    public boolean isActive() {
        return status == TransactionStatus.ACTIVE;
    }

    public boolean isCompleted() {
        return status == TransactionStatus.COMPLETED;
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getFee() {
        return fee;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ParkingTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", vehicle=" + vehicle.getLicensePlate() +
                ", slot=" + slot.getSlotId() +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", fee=" + fee +
                ", status=" + status +
                '}';
    }
} 