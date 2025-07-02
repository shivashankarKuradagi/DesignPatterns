# Parking Lot Management System - API Flow Diagram

## 1. System Architecture Overview

```mermaid
graph TB
    subgraph "Client Layer"
        Client[Client Application]
    end
    
    subgraph "API Layer"
        Controller[ParkingController]
        Response[ParkingResponse]
    end
    
    subgraph "Service Layer"
        Service[ParkingService]
    end
    
    subgraph "Domain Layer"
        ParkingLot[ParkingLot Aggregate]
        Vehicle[Vehicle Entity]
        Transaction[ParkingTransaction]
        Pricing[PricingStrategy]
    end
    
    subgraph "Infrastructure Layer"
        Repository[(Data Store)]
    end
    
    Client --> Controller
    Controller --> Service
    Service --> ParkingLot
    ParkingLot --> Vehicle
    ParkingLot --> Transaction
    ParkingLot --> Pricing
    ParkingLot --> Repository
    Service --> Response
    Controller --> Response
```

## 2. API 1: Park Vehicle Flow

```mermaid
sequenceDiagram
    participant Client
    participant Controller as ParkingController
    participant Service as ParkingService
    participant ParkingLot as ParkingLot
    participant Floor as ParkingFloor
    participant Slot as ParkingSlot
    participant Vehicle as Vehicle
    participant Transaction as ParkingTransaction

    Client->>Controller: POST /api/park<br/>{licensePlate, vehicleType}
    Controller->>Service: parkVehicle(licensePlate, vehicleType)
    Service->>Vehicle: new Vehicle(licensePlate, vehicleType)
    Service->>ParkingLot: parkVehicle(vehicle)
    
    ParkingLot->>ParkingLot: Check if vehicle already parked
    ParkingLot->>Floor: findAvailableSlot(vehicle)
    Floor->>Slot: canAccommodate(vehicle)
    Slot-->>Floor: true/false
    Floor-->>ParkingLot: Optional<ParkingSlot>
    
    alt Slot Available
        ParkingLot->>Slot: parkVehicle(vehicle)
        Slot->>Vehicle: setAssignedSlot(slot)
        ParkingLot->>Transaction: new ParkingTransaction(vehicle, slot)
        ParkingLot->>ParkingLot: Store transaction in activeTransactions
        ParkingLot-->>Service: ParkingTransaction
        Service-->>Controller: ParkingTransaction
        Controller-->>Client: Success Response with Transaction
    else No Slot Available
        ParkingLot-->>Service: Exception
        Service-->>Controller: Exception
        Controller-->>Client: Error Response
    end
```

## 3. API 2: Unpark Vehicle Flow

```mermaid
sequenceDiagram
    participant Client
    participant Controller as ParkingController
    participant Service as ParkingService
    participant ParkingLot as ParkingLot
    participant Slot as ParkingSlot
    participant Vehicle as Vehicle
    participant Transaction as ParkingTransaction
    participant Pricing as PricingStrategy

    Client->>Controller: POST /api/unpark<br/>{licensePlate}
    Controller->>Service: unparkVehicle(licensePlate)
    Service->>ParkingLot: unparkVehicle(licensePlate)
    
    ParkingLot->>ParkingLot: Get vehicle from parkedVehicles
    ParkingLot->>ParkingLot: Get transaction from activeTransactions
    
    alt Vehicle Found & Transaction Active
        ParkingLot->>Slot: unparkVehicle()
        Slot->>Vehicle: setAssignedSlot(null)
        ParkingLot->>Vehicle: setExitTime(now)
        ParkingLot->>Pricing: calculateFee(vehicle)
        Pricing-->>ParkingLot: fee
        ParkingLot->>Transaction: completeTransaction(fee)
        ParkingLot->>ParkingLot: Remove from activeTransactions
        ParkingLot-->>Service: ParkingTransaction
        Service-->>Controller: ParkingTransaction
        Controller-->>Client: Success Response with Completed Transaction
    else Vehicle Not Found
        ParkingLot-->>Service: Exception
        Service-->>Controller: Exception
        Controller-->>Client: Error Response
    end
```

## 4. API 3: Get Vehicle Info Flow

```mermaid
sequenceDiagram
    participant Client
    participant Controller as ParkingController
    participant Service as ParkingService
    participant ParkingLot as ParkingLot
    participant Vehicle as Vehicle
    participant Pricing as PricingStrategy
    participant VehicleInfo as VehicleInfo

    Client->>Controller: GET /api/vehicle/{licensePlate}
    Controller->>Service: getVehicleInfo(licensePlate)
    Service->>ParkingLot: getVehicleInfo(licensePlate)
    
    ParkingLot->>ParkingLot: Get vehicle from parkedVehicles
    
    alt Vehicle Found
        ParkingLot->>Pricing: calculateFee(vehicle)
        Pricing-->>ParkingLot: currentFee
        ParkingLot->>VehicleInfo: new VehicleInfo(vehicle, currentFee)
        ParkingLot-->>Service: VehicleInfo
        Service-->>Controller: VehicleInfo
        Controller-->>Client: Success Response with Vehicle Info
    else Vehicle Not Found
        ParkingLot-->>Service: Exception
        Service-->>Controller: Exception
        Controller-->>Client: Error Response
    end
```

## 5. API 4: Get Parking Lot Status Flow

```mermaid
sequenceDiagram
    participant Client
    participant Controller as ParkingController
    participant Service as ParkingService
    participant ParkingLot as ParkingLot
    participant Floor as ParkingFloor
    participant Slot as ParkingSlot
    participant Status as ParkingLotStatus

    Client->>Controller: GET /api/status
    Controller->>Service: getParkingLotStatus()
    Service->>ParkingLot: getParkingLotStatus()
    
    ParkingLot->>Floor: getParkingSlots()
    Floor-->>ParkingLot: List<ParkingSlot>
    
    loop For each slot
        ParkingLot->>Slot: getSlotType()
        ParkingLot->>Slot: isOccupied()
        ParkingLot->>ParkingLot: Count by type and status
    end
    
    ParkingLot->>Status: new ParkingLotStatus(available, occupied, total)
    ParkingLot-->>Service: ParkingLotStatus
    Service-->>Controller: ParkingLotStatus
    Controller-->>Client: Success Response with Status
```

## 6. Complete End-to-End Flow Example

```mermaid
sequenceDiagram
    participant Client
    participant API as API Layer
    participant Service as Service Layer
    participant Domain as Domain Layer
    participant DB as Data Store

    Note over Client,DB: Complete Parking Transaction Flow
    
    Client->>API: 1. Park Vehicle Request
    API->>Service: parkVehicle()
    Service->>Domain: Create Vehicle & Find Slot
    Domain->>DB: Store Transaction
    Domain-->>Service: ParkingTransaction
    Service-->>API: Transaction
    API-->>Client: Success Response
    
    Client->>API: 2. Get Vehicle Info Request
    API->>Service: getVehicleInfo()
    Service->>Domain: Calculate Current Fee
    Domain-->>Service: VehicleInfo
    Service-->>API: VehicleInfo
    API-->>Client: Current Fee & Duration
    
    Client->>API: 3. Unpark Vehicle Request
    API->>Service: unparkVehicle()
    Service->>Domain: Complete Transaction
    Domain->>DB: Update Transaction Status
    Domain-->>Service: Completed Transaction
    Service-->>API: Final Receipt
    API-->>Client: Final Fee & Receipt
```

## 7. Error Handling Flow

```mermaid
sequenceDiagram
    participant Client
    participant Controller as ParkingController
    participant Service as ParkingService
    participant Domain as Domain Layer

    Client->>Controller: API Request
    
    alt Service Layer Exception
        Service->>Controller: Exception
        Controller->>Controller: Create Error Response
        Controller-->>Client: Error Response
    else Domain Layer Exception
        Domain->>Service: Domain Exception
        Service->>Controller: Exception
        Controller->>Controller: Create Error Response
        Controller-->>Client: Error Response
    else Validation Error
        Controller->>Controller: Validation Failed
        Controller-->>Client: Validation Error Response
    end
```

## 8. Data Flow Architecture

```mermaid
graph LR
    subgraph "Request Flow"
        A[Client Request] --> B[Controller]
        B --> C[Service]
        C --> D[Domain]
    end
    
    subgraph "Response Flow"
        D --> E[Domain Objects]
        E --> F[Service Response]
        F --> G[API Response]
        G --> H[Client]
    end
    
    subgraph "Data Persistence"
        D --> I[Repository]
        I --> J[Database]
    end
    
    subgraph "Business Logic"
        D --> K[Pricing Strategy]
        D --> L[Slot Allocation]
        D --> M[Transaction Management]
    end
```

## 9. API Response Structure

### Success Response
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {
    "transactionId": "TXN-1234567890",
    "vehicle": {
      "licensePlate": "KA-01-HH-1234",
      "type": "CAR"
    },
    "slot": {
      "slotId": "F1-C5",
      "floorNumber": 1
    },
    "entryTime": "2024-01-15T10:30:00",
    "exitTime": "2024-01-15T12:30:00",
    "fee": 4.0,
    "status": "COMPLETED"
  }
}
```

### Error Response
```json
{
  "success": false,
  "message": "Vehicle KA-01-HH-1234 is not parked",
  "data": null
}
```

## 10. Key Design Patterns Used

1. **Layered Architecture**: Clear separation between API, Service, and Domain layers
2. **Domain-Driven Design**: Rich domain models with business logic
3. **Strategy Pattern**: PricingStrategy for different pricing algorithms
4. **Aggregate Pattern**: ParkingLot as the aggregate root
5. **Value Objects**: ParkingTransaction, VehicleInfo, ParkingLotStatus
6. **Repository Pattern**: Service layer abstracts data access

## 11. Performance Considerations

- **Slot Allocation**: O(n) where n is total number of slots
- **Vehicle Lookup**: O(1) using HashMap for parked vehicles
- **Transaction Management**: O(1) for active transaction lookup
- **Status Calculation**: O(n) for slot iteration (can be optimized with counters)

This API flow diagram provides a comprehensive view of how requests flow through the system, from the client through all layers to the domain and back, ensuring proper separation of concerns and maintainable code structure. 