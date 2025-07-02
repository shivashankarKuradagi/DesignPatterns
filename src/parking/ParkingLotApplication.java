package parking;

import parking.api.*;
import parking.domain.*;
import parking.service.ParkingService;

public class ParkingLotApplication {
    public static void main(String[] args) {
        // Initialize the parking lot system
        System.out.println("=== Parking Lot Management System (Combined Transaction) ===\n");
        
        // Create parking lot with multiple floors
        ParkingLot parkingLot = createParkingLot();
        ParkingService parkingService = new ParkingService(parkingLot);
        ParkingController parkingController = new ParkingController(parkingService);

        // Demonstrate API flow with combined transaction
        demonstrateAPIFlow(parkingController);
    }

    private static ParkingLot createParkingLot() {
        ParkingLot parkingLot = new ParkingLot("PL-001");

        // Create Floor 1
        ParkingFloor floor1 = new ParkingFloor(1);
        addSlotsToFloor(floor1, 1, 5, 10, 3); // 5 motorcycle, 10 car, 3 bus slots
        parkingLot.addFloor(floor1);

        // Create Floor 2
        ParkingFloor floor2 = new ParkingFloor(2);
        addSlotsToFloor(floor2, 2, 8, 15, 2); // 8 motorcycle, 15 car, 2 bus slots
        parkingLot.addFloor(floor2);

        System.out.println("Parking Lot created with 2 floors:");
        System.out.println("Floor 1: 5 motorcycle, 10 car, 3 bus slots");
        System.out.println("Floor 2: 8 motorcycle, 15 car, 2 bus slots\n");

        return parkingLot;
    }

    private static void addSlotsToFloor(ParkingFloor floor, int floorNumber, int motorcycleSlots, int carSlots, int busSlots) {
        // Add motorcycle slots
        for (int i = 1; i <= motorcycleSlots; i++) {
            String slotId = "F" + floorNumber + "-M" + i;
            ParkingSlot slot = new ParkingSlot(slotId, VehicleType.MOTORCYCLE, floorNumber);
            floor.addParkingSlot(slot);
        }

        // Add car slots
        for (int i = 1; i <= carSlots; i++) {
            String slotId = "F" + floorNumber + "-C" + i;
            ParkingSlot slot = new ParkingSlot(slotId, VehicleType.CAR, floorNumber);
            floor.addParkingSlot(slot);
        }

        // Add bus slots
        for (int i = 1; i <= busSlots; i++) {
            String slotId = "F" + floorNumber + "-B" + i;
            ParkingSlot slot = new ParkingSlot(slotId, VehicleType.BUS, floorNumber);
            floor.addParkingSlot(slot);
        }
    }

    private static void demonstrateAPIFlow(ParkingController controller) {
        System.out.println("=== API Flow Demonstration (Combined Transaction) ===\n");

        // API 1: Park Vehicle
        System.out.println("1. Parking a Car:");
        ParkVehicleRequest parkRequest = new ParkVehicleRequest("KA-01-HH-1234", VehicleType.CAR);
        ParkingResponse parkResponse = controller.parkVehicle(parkRequest);
        System.out.println("Request: " + parkRequest);
        System.out.println("Response: " + parkResponse);
        System.out.println();

        // API 2: Get Vehicle Info
        System.out.println("2. Getting Vehicle Info:");
        GetVehicleInfoRequest infoRequest = new GetVehicleInfoRequest("KA-01-HH-1234");
        ParkingResponse infoResponse = controller.getVehicleInfo(infoRequest);
        System.out.println("Request: " + infoRequest);
        System.out.println("Response: " + infoResponse);
        System.out.println();

        // API 3: Park Another Vehicle
        System.out.println("3. Parking a Motorcycle:");
        ParkVehicleRequest parkRequest2 = new ParkVehicleRequest("KA-02-HH-5678", VehicleType.MOTORCYCLE);
        ParkingResponse parkResponse2 = controller.parkVehicle(parkRequest2);
        System.out.println("Request: " + parkRequest2);
        System.out.println("Response: " + parkResponse2);
        System.out.println();

        // API 4: Get Parking Lot Status
        System.out.println("4. Getting Parking Lot Status:");
        ParkingResponse statusResponse = controller.getParkingLotStatus();
        System.out.println("Response: " + statusResponse);
        System.out.println();

        // API 5: Unpark Vehicle (Completes the transaction)
        System.out.println("5. Unparking the Car (Completing Transaction):");
        UnparkVehicleRequest unparkRequest = new UnparkVehicleRequest("KA-01-HH-1234");
        ParkingResponse unparkResponse = controller.unparkVehicle(unparkRequest);
        System.out.println("Request: " + unparkRequest);
        System.out.println("Response: " + unparkResponse);
        System.out.println();

        // API 6: Final Status
        System.out.println("6. Final Parking Lot Status:");
        ParkingResponse finalStatusResponse = controller.getParkingLotStatus();
        System.out.println("Response: " + finalStatusResponse);
    }
} 