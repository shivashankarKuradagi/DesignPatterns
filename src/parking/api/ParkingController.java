package parking.api;

import parking.domain.*;
import parking.service.ParkingService;

public class ParkingController {
    private ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    // API 1: Park Vehicle
    public ParkingResponse parkVehicle(ParkVehicleRequest request) {
        try {
            ParkingTransaction transaction = parkingService.parkVehicle(request.getLicensePlate(), request.getVehicleType());
            return new ParkingResponse(true, "Vehicle parked successfully", transaction);
        } catch (Exception e) {
            return new ParkingResponse(false, "Failed to park vehicle: " + e.getMessage(), null);
        }
    }

    // API 2: Unpark Vehicle
    public ParkingResponse unparkVehicle(UnparkVehicleRequest request) {
        try {
            ParkingTransaction transaction = parkingService.unparkVehicle(request.getLicensePlate());
            return new ParkingResponse(true, "Vehicle unparked successfully", transaction);
        } catch (Exception e) {
            return new ParkingResponse(false, "Failed to unpark vehicle: " + e.getMessage(), null);
        }
    }

    // API 3: Get Vehicle Info with Charges
    public ParkingResponse getVehicleInfo(GetVehicleInfoRequest request) {
        try {
            VehicleInfo vehicleInfo = parkingService.getVehicleInfo(request.getLicensePlate());
            return new ParkingResponse(true, "Vehicle info retrieved successfully", vehicleInfo);
        } catch (Exception e) {
            return new ParkingResponse(false, "Failed to get vehicle info: " + e.getMessage(), null);
        }
    }

    // Additional API: Get Parking Lot Status
    public ParkingResponse getParkingLotStatus() {
        try {
            ParkingLotStatus status = parkingService.getParkingLotStatus();
            return new ParkingResponse(true, "Parking lot status retrieved successfully", status);
        } catch (Exception e) {
            return new ParkingResponse(false, "Failed to get parking lot status: " + e.getMessage(), null);
        }
    }
} 