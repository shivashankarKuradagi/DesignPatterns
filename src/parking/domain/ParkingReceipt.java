package parking.domain;

import java.time.LocalDateTime;

public class ParkingReceipt {
    private String receiptId;
    private Vehicle vehicle;
    private double fee;
    private LocalDateTime exitTime;

    public ParkingReceipt(Vehicle vehicle, double fee) {
        this.receiptId = generateReceiptId();
        this.vehicle = vehicle;
        this.fee = fee;
        this.exitTime = LocalDateTime.now();
    }

    private String generateReceiptId() {
        return "RCP-" + System.currentTimeMillis();
    }

    // Getters
    public String getReceiptId() {
        return receiptId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getFee() {
        return fee;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    @Override
    public String toString() {
        return "ParkingReceipt{" +
                "receiptId='" + receiptId + '\'' +
                ", vehicle=" + vehicle.getLicensePlate() +
                ", fee=" + fee +
                ", exitTime=" + exitTime +
                '}';
    }
} 