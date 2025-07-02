# Parking Lot Management System - UML Class Diagram

## Domain Layer

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              VehicleType (Enum)                            │
├─────────────────────────────────────────────────────────────────────────────┤
│ + MOTORCYCLE(id: int, displayName: String)                                 │
│ + CAR(id: int, displayName: String)                                        │
│ + BUS(id: int, displayName: String)                                        │
├─────────────────────────────────────────────────────────────────────────────┤
│ + getId(): int                                                             │
│ + getDisplayName(): String                                                 │
│ + canAccommodate(otherType: VehicleType): boolean                         │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                                 Vehicle                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│ - licensePlate: String                                                     │
│ - type: VehicleType                                                        │
│ - entryTime: LocalDateTime                                                 │
│ - exitTime: LocalDateTime                                                  │
│ - assignedSlot: ParkingSlot                                                │
├─────────────────────────────────────────────────────────────────────────────┤
│ + Vehicle(licensePlate: String, type: VehicleType)                        │
│ + getLicensePlate(): String                                               │
│ + getType(): VehicleType                                                  │
│ + getEntryTime(): LocalDateTime                                           │
│ + setEntryTime(entryTime: LocalDateTime): void                            │
│ + getExitTime(): LocalDateTime                                            │
│ + setExitTime(exitTime: LocalDateTime): void                              │
│ + getAssignedSlot(): ParkingSlot                                          │
│ + setAssignedSlot(slot: ParkingSlot): void                                │
│ + getParkingDurationInHours(): long                                       │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                               ParkingSlot                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ - slotId: String                                                           │
│ - slotType: VehicleType                                                    │
│ - isOccupied: boolean                                                      │
│ - parkedVehicle: Vehicle                                                   │
│ - floorNumber: int                                                         │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingSlot(slotId: String, slotType: VehicleType, floorNumber: int)     │
│ + canAccommodate(vehicle: Vehicle): boolean                               │
│ + parkVehicle(vehicle: Vehicle): void                                     │
│ + unparkVehicle(): Vehicle                                                │
│ + getSlotId(): String                                                     │
│ + getSlotType(): VehicleType                                              │
│ + isOccupied(): boolean                                                   │
│ + getParkedVehicle(): Vehicle                                             │
│ + getFloorNumber(): int                                                   │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                              ParkingFloor                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ - floorNumber: int                                                         │
│ - parkingSlots: List<ParkingSlot>                                         │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingFloor(floorNumber: int)                                          │
│ + addParkingSlot(slot: ParkingSlot): void                                 │
│ + findAvailableSlot(vehicle: Vehicle): Optional<ParkingSlot>              │
│ + getAvailableSlots(vehicleType: VehicleType): List<ParkingSlot>          │
│ + getOccupiedSlots(): List<ParkingSlot>                                   │
│ + getTotalSlots(): int                                                    │
│ + getAvailableSlotsCount(): int                                           │
│ + getOccupiedSlotsCount(): int                                            │
│ + getFloorNumber(): int                                                   │
│ + getParkingSlots(): List<ParkingSlot>                                    │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                              ParkingLot                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│ - parkingLotId: String                                                     │
│ - floors: List<ParkingFloor>                                               │
│ - parkedVehicles: Map<String, Vehicle>                                     │
│ - pricingStrategy: PricingStrategy                                        │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingLot(parkingLotId: String)                                        │
│ + addFloor(floor: ParkingFloor): void                                     │
│ + parkVehicle(vehicle: Vehicle): ParkingTicket                            │
│ + unparkVehicle(licensePlate: String): ParkingReceipt                    │
│ + getVehicleInfo(licensePlate: String): VehicleInfo                      │
│ + getParkingLotStatus(): ParkingLotStatus                                │
│ - findAvailableSlot(vehicle: Vehicle): Optional<ParkingSlot>             │
│ + getParkingLotId(): String                                               │
│ + getFloors(): List<ParkingFloor>                                         │
│ + getParkedVehicles(): Map<String, Vehicle>                               │
│ + setPricingStrategy(strategy: PricingStrategy): void                     │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                            PricingStrategy                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│ + calculateFee(vehicle: Vehicle): double                                  │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                        DefaultPricingStrategy                              │
├─────────────────────────────────────────────────────────────────────────────┤
│ + calculateFee(vehicle: Vehicle): double                                  │
│ - getHourlyRate(vehicleType: VehicleType): double                         │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Value Objects

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                            ParkingTicket                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│ - ticketId: String                                                         │
│ - vehicle: Vehicle                                                         │
│ - slot: ParkingSlot                                                        │
│ - issueTime: LocalDateTime                                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingTicket(vehicle: Vehicle, slot: ParkingSlot)                      │
│ - generateTicketId(): String                                              │
│ + getTicketId(): String                                                   │
│ + getVehicle(): Vehicle                                                   │
│ + getSlot(): ParkingSlot                                                  │
│ + getIssueTime(): LocalDateTime                                           │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                           ParkingReceipt                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│ - receiptId: String                                                        │
│ - vehicle: Vehicle                                                         │
│ - fee: double                                                              │
│ - exitTime: LocalDateTime                                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingReceipt(vehicle: Vehicle, fee: double)                           │
│ - generateReceiptId(): String                                             │
│ + getReceiptId(): String                                                  │
│ + getVehicle(): Vehicle                                                   │
│ + getFee(): double                                                        │
│ + getExitTime(): LocalDateTime                                            │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                             VehicleInfo                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│ - vehicle: Vehicle                                                         │
│ - currentFee: double                                                       │
│ - parkingDurationHours: long                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│ + VehicleInfo(vehicle: Vehicle, currentFee: double)                       │
│ + getVehicle(): Vehicle                                                   │
│ + getCurrentFee(): double                                                 │
│ + getParkingDurationHours(): long                                         │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                          ParkingLotStatus                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ - availableSlotsByType: Map<VehicleType, Integer>                         │
│ - occupiedSlotsByType: Map<VehicleType, Integer>                          │
│ - totalParkedVehicles: int                                                │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingLotStatus(availableSlotsByType: Map, occupiedSlotsByType: Map,   │
│                    totalParkedVehicles: int)                              │
│ + getAvailableSlotsByType(): Map<VehicleType, Integer>                    │
│ + getOccupiedSlotsByType(): Map<VehicleType, Integer>                     │
│ + getTotalParkedVehicles(): int                                           │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Service Layer

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                            ParkingService                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ - parkingLot: ParkingLot                                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingService(parkingLot: ParkingLot)                                  │
│ + parkVehicle(licensePlate: String, vehicleType: VehicleType): ParkingTicket │
│ + unparkVehicle(licensePlate: String): ParkingReceipt                    │
│ + getVehicleInfo(licensePlate: String): VehicleInfo                      │
│ + getParkingLotStatus(): ParkingLotStatus                                │
│ + getParkingLot(): ParkingLot                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## API Layer

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ParkingController                                │
├─────────────────────────────────────────────────────────────────────────────┤
│ - parkingService: ParkingService                                          │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingController(parkingService: ParkingService)                       │
│ + parkVehicle(request: ParkVehicleRequest): ParkingResponse               │
│ + unparkVehicle(request: UnparkVehicleRequest): ParkingResponse           │
│ + getVehicleInfo(request: GetVehicleInfoRequest): ParkingResponse         │
│ + getParkingLotStatus(): ParkingResponse                                  │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ParkingResponse                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ - success: boolean                                                         │
│ - message: String                                                          │
│ - data: Object                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkingResponse(success: boolean, message: String, data: Object)        │
│ + isSuccess(): boolean                                                    │
│ + getMessage(): String                                                    │
│ + getData(): Object                                                       │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │
┌─────────────────────────────────────────────────────────────────────────────┐
│                        ParkVehicleRequest                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│ - licensePlate: String                                                     │
│ - vehicleType: VehicleType                                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│ + ParkVehicleRequest(licensePlate: String, vehicleType: VehicleType)      │
│ + getLicensePlate(): String                                               │
│ + getVehicleType(): VehicleType                                           │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                       UnparkVehicleRequest                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│ - licensePlate: String                                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│ + UnparkVehicleRequest(licensePlate: String)                              │
│ + getLicensePlate(): String                                               │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                      GetVehicleInfoRequest                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│ - licensePlate: String                                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│ + GetVehicleInfoRequest(licensePlate: String)                             │
│ + getLicensePlate(): String                                               │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Design Patterns Used

1. **Domain-Driven Design (DDD)**: Clear separation of domain, service, and API layers
2. **Strategy Pattern**: PricingStrategy interface with DefaultPricingStrategy implementation
3. **Value Objects**: ParkingTicket, ParkingReceipt, VehicleInfo, ParkingLotStatus
4. **Aggregate Pattern**: ParkingLot as the aggregate root
5. **Repository Pattern**: Service layer acts as repository abstraction 