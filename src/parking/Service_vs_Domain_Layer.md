# Service Layer vs Domain Layer - Complete Comparison

## Overview

In Domain-Driven Design (DDD), the **Service Layer** and **Domain Layer** serve different purposes and have distinct responsibilities. Understanding this separation is crucial for building maintainable and scalable applications.

## Service Layer (Application Services)

### **Purpose**
The Service Layer acts as a **facade** that orchestrates domain objects and handles application-specific logic.

### **Responsibilities**
1. **Orchestration**: Coordinates multiple domain objects
2. **Input Validation**: Validates incoming requests
3. **Transaction Management**: Manages application-level transactions
4. **Cross-cutting Concerns**: Handles logging, caching, security
5. **API Facade**: Provides clean interfaces for controllers

### **Characteristics**
- **Thin**: Contains minimal business logic
- **Stateless**: No business state, only coordinates
- **Application-specific**: Tailored to specific use cases
- **Orchestrates**: Calls domain objects and coordinates their interactions

## Domain Layer (Core Business Logic)

### **Purpose**
The Domain Layer contains the **core business entities** and rules that represent the real-world concepts.

### **Responsibilities**
1. **Business Rules**: Enforces domain-specific rules and constraints
2. **Entity Management**: Manages the lifecycle of domain entities
3. **Business Logic**: Contains the core business algorithms
4. **Invariants**: Ensures data consistency and business rules
5. **Domain Services**: Provides domain-specific operations

### **Characteristics**
- **Rich**: Contains substantial business logic
- **Stateful**: Manages business state
- **Domain-specific**: Represents real-world concepts
- **Self-contained**: Minimal dependencies on other layers

## Detailed Comparison

| Aspect | Service Layer | Domain Layer |
|--------|---------------|--------------|
| **Purpose** | Orchestration & Coordination | Core Business Logic |
| **State** | Stateless | Stateful |
| **Dependencies** | Depends on Domain Layer | Minimal external dependencies |
| **Business Logic** | Application-specific | Domain-specific |
| **Validation** | Input validation | Business rule validation |
| **Transactions** | Application transactions | Domain transactions |
| **Testing** | Integration testing | Unit testing |

## Code Examples

### **Service Layer Example**

```java
/**
 * Service Layer - Application Services
 * 
 * Responsibilities:
 * 1. Orchestrate domain objects
 * 2. Handle application-specific logic
 * 3. Coordinate between different domain objects
 * 4. Provide a clean API for the controller layer
 * 5. Handle cross-cutting concerns (validation, logging, etc.)
 */
public class ParkingService {
    
    /**
     * Service Layer: Orchestrates the parking process
     * - Validates input
     * - Creates domain objects
     * - Delegates to domain layer
     * - Handles application-specific logic
     */
    public ParkingTransaction parkVehicle(String licensePlate, VehicleType vehicleType) {
        // Service Layer: Input validation
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }

        // Service Layer: Create domain object
        Vehicle vehicle = new Vehicle(licensePlate, vehicleType);
        
        // Service Layer: Delegate to domain layer
        return parkingLot.parkVehicle(vehicle);
    }

    /**
     * Service Layer: Business operation that spans multiple domain objects
     * This is a good example of service layer orchestration
     */
    public ParkingTransaction transferVehicle(String licensePlate, String newSlotId) {
        // Service Layer: Input validation
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be null or empty");
        }

        // Service Layer: Business logic that requires coordination
        // 1. First unpark the vehicle
        ParkingTransaction currentTransaction = parkingLot.unparkVehicle(licensePlate);
        
        // 2. Find the new slot
        ParkingSlot newSlot = findSlotById(newSlotId);
        
        // 3. Repark the vehicle in the new slot
        Vehicle vehicle = currentTransaction.getVehicle();
        vehicle.setEntryTime(LocalDateTime.now()); // Reset entry time
        return parkingLot.parkVehicle(vehicle);
    }
}
```

### **Domain Layer Example**

```java
/**
 * Domain Layer - Core Business Logic
 * 
 * Responsibilities:
 * 1. Enforce business rules
 * 2. Manage entity lifecycle
 * 3. Ensure data consistency
 * 4. Provide domain-specific operations
 */
public class ParkingLot {
    
    /**
     * Domain Layer: Core business logic for parking a vehicle
     * - Enforces business rules
     * - Manages entity relationships
     * - Ensures data consistency
     */
    public ParkingTransaction parkVehicle(Vehicle vehicle) {
        // Domain Layer: Business rule validation
        if (parkedVehicles.containsKey(vehicle.getLicensePlate())) {
            throw new IllegalStateException("Vehicle " + vehicle.getLicensePlate() + " is already parked");
        }

        // Domain Layer: Business logic for finding available slot
        Optional<ParkingSlot> availableSlot = findAvailableSlot(vehicle);
        if (availableSlot.isEmpty()) {
            throw new IllegalStateException("No available parking slot for " + vehicle.getType().getDisplayName());
        }

        // Domain Layer: Business logic for parking
        ParkingSlot slot = availableSlot.get();
        slot.parkVehicle(vehicle);
        parkedVehicles.put(vehicle.getLicensePlate(), vehicle);

        // Domain Layer: Create domain object
        ParkingTransaction transaction = new ParkingTransaction(vehicle, slot);
        activeTransactions.put(vehicle.getLicensePlate(), transaction);

        return transaction;
    }

    /**
     * Domain Layer: Business logic for calculating parking lot status
     * - Aggregates data from multiple entities
     * - Enforces business rules
     * - Provides domain-specific information
     */
    public ParkingLotStatus getParkingLotStatus() {
        Map<VehicleType, Integer> availableSlotsByType = new HashMap<>();
        Map<VehicleType, Integer> occupiedSlotsByType = new HashMap<>();

        // Domain Layer: Business logic for counting slots
        for (VehicleType type : VehicleType.values()) {
            availableSlotsByType.put(type, 0);
            occupiedSlotsByType.put(type, 0);
        }

        for (ParkingFloor floor : floors) {
            for (ParkingSlot slot : floor.getParkingSlots()) {
                VehicleType slotType = slot.getSlotType();
                if (slot.isOccupied()) {
                    occupiedSlotsByType.put(slotType, occupiedSlotsByType.get(slotType) + 1);
                } else {
                    availableSlotsByType.put(slotType, availableSlotsByType.get(slotType) + 1);
                }
            }
        }

        return new ParkingLotStatus(availableSlotsByType, occupiedSlotsByType, parkedVehicles.size());
    }
}
```

## When to Use Each Layer

### **Use Service Layer For:**
- ✅ **Orchestration**: Coordinating multiple domain objects
- ✅ **Input Validation**: Validating API requests
- ✅ **Cross-cutting Concerns**: Logging, caching, security
- ✅ **Application Logic**: Use case specific operations
- ✅ **Transaction Management**: Application-level transactions
- ✅ **External Integration**: Calling external services

### **Use Domain Layer For:**
- ✅ **Business Rules**: Core business logic and constraints
- ✅ **Entity Management**: Managing domain entity lifecycle
- ✅ **Invariants**: Ensuring data consistency
- ✅ **Domain Services**: Domain-specific operations
- ✅ **Business Algorithms**: Core business calculations
- ✅ **Entity Relationships**: Managing relationships between entities

## Anti-patterns to Avoid

### **Service Layer Anti-patterns:**
❌ **Anemic Service Layer**: Service layer with no logic  
❌ **Fat Service Layer**: Too much business logic in service layer  
❌ **Service Layer Bypass**: Controllers calling domain directly  
❌ **Service Layer Coupling**: Service layer depending on infrastructure  

### **Domain Layer Anti-patterns:**
❌ **Anemic Domain Model**: Domain objects with no behavior  
❌ **Infrastructure Coupling**: Domain layer depending on infrastructure  
❌ **Service Layer Logic**: Application logic in domain layer  
❌ **External Dependencies**: Domain layer calling external services  

## Best Practices

### **Service Layer Best Practices:**
1. **Keep it thin**: Minimal business logic
2. **Validate inputs**: Handle all input validation
3. **Coordinate, don't implement**: Delegate to domain layer
4. **Handle exceptions**: Convert domain exceptions to application exceptions
5. **Be stateless**: No business state in service layer

### **Domain Layer Best Practices:**
1. **Rich domain models**: Entities with behavior
2. **Encapsulate business rules**: Keep business logic in domain
3. **Minimize dependencies**: Few external dependencies
4. **Use value objects**: Immutable objects for concepts
5. **Aggregate boundaries**: Clear aggregate boundaries

## Summary

| Layer | Primary Role | Key Responsibility | Example |
|-------|-------------|-------------------|---------|
| **Service Layer** | Orchestration | Coordinate domain objects | `parkVehicle()` validates input and delegates to domain |
| **Domain Layer** | Business Logic | Enforce business rules | `ParkingLot.parkVehicle()` contains core parking logic |

The key is to understand that:
- **Service Layer** = **HOW** to do something (orchestration)
- **Domain Layer** = **WHAT** the business rules are (core logic)

This separation ensures maintainable, testable, and scalable code that follows Domain-Driven Design principles. 