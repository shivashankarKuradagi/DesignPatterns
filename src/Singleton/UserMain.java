package singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import builder.UserBuilderMain;

public class UserMain {
    public static void main(String[] args) {
        // Approach 1: Get default singleton instance
        User user1 = User.getInstance();
        System.out.println("Default User: " + user1.toString());
        
        // Approach 2: Get singleton with parameters (only works if instance is null)
        User user2 = User.getInstance("shiva", 25);
        System.out.println("Parameterized User: " + user2.toString());
        
        // Both should be the same instance
        System.out.println("Are they the same instance? " + (user1 == user2));
        
        // Try to get another instance with different parameters (should return existing instance)
        User user3 = User.getInstance("different", 30);
        System.out.println("Third User: " + user3.toString());
        System.out.println("Are all instances the same? " + (user1 == user2 && user2 == user3));
        
        // Demonstrate immutability - no setters available
        System.out.println("\nImmutable properties:");
        System.out.println("Name: " + user1.getName());
        System.out.println("Age: " + user1.getAge());
        // user1.setName("new name"); // This would cause compilation error

        // Demonstrate breaking singleton using reflection
        System.out.println("\n=== Breaking Singleton with Reflection ===");
        try {
            // Get the private constructor
            Constructor<User> constructor = User.class.getDeclaredConstructor();
            constructor.setAccessible(true); // Make private constructor accessible
            
            // Create a new instance using reflection
            User reflectionUser = constructor.newInstance();
            System.out.println("Reflection User: " + reflectionUser.toString());
            System.out.println("Is reflection user same as singleton? " + (user1 == reflectionUser));
            
            // Try to modify the singleton instance using reflection
            System.out.println("\n=== Modifying Singleton Fields with Reflection ===");
            Field nameField = User.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(user1, "Modified via Reflection");
            
            Field ageField = User.class.getDeclaredField("age");
            ageField.setAccessible(true);
            ageField.set(user1, 100);
            
            System.out.println("Modified User: " + user1.toString());
            System.out.println("All instances affected: " + user2.toString());
            
        } catch (Exception e) {
            System.err.println("Reflection error: " + e.getMessage());
            e.printStackTrace();
        }

        // Demonstrate cloning (should throw exception)
        System.out.println("\n=== Attempting to Clone Singleton ===");
        try {
            User clonedUser = (User) user1.clone();
            System.out.println("Cloned User: " + clonedUser.toString());
            System.out.println("Is cloned user same as original? " + (user1 == clonedUser));
        } catch (Exception e) {
            System.err.println("Cloning error: " + e.getMessage());
            System.out.println("✅ Singleton cloning protection working correctly!");
        }

        // Call builder pattern demonstration
        demonstrateBuilderPattern();
    }

    // Method to demonstrate builder pattern
    private static void demonstrateBuilderPattern() {
        System.out.println("\n=== Builder Pattern Demonstration ===");
        
        // Call the builder demonstration method
        UserBuilderMain.demonstrateBuilder();
        
        // Also demonstrate direct builder usage
        System.out.println("\n=== Direct Builder Usage ===");
        builder.User builderUser = new builder.User.UserBuilder()
                .name("Direct Builder User")
                .age(40)
                .build();
        
        System.out.println("Direct Builder User: " + builderUser.toString());
        
        // Compare with singleton
        User singletonUser = User.getInstance();
        System.out.println("Singleton User: " + singletonUser.toString());
        System.out.println("Are they the same? " + (builderUser == singletonUser));
    }
}
