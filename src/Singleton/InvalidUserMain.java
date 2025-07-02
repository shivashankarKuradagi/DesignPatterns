package singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class InvalidUserMain {

    public static void main(String[] args) {
        InvalidUser user1 = new InvalidUser();
        System.out.println("Default User: " + user1.toString());

        try {
            // Method 1: Using reflection to access private constructor
            System.out.println("\n=== Using Reflection to Access Private Constructor ===");
            Constructor<InvalidUser> constructor = InvalidUser.class.getDeclaredConstructor(String.class, Integer.class);
            constructor.setAccessible(true); // Make private constructor accessible
            InvalidUser user2 = constructor.newInstance("shiva", 25);
            System.out.println("Reflection User: " + user2.toString());

            // Method 2: Using reflection to modify private fields
            System.out.println("\n=== Using Reflection to Modify Private Fields ===");
            InvalidUser user3 = new InvalidUser();
            System.out.println("Before modification: " + user3.toString());
            
            // Get the name field and make it accessible
            Field nameField = InvalidUser.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(user3, "Reflection Name");
            
            // Get the age field and make it accessible
            Field ageField = InvalidUser.class.getDeclaredField("age");
            ageField.setAccessible(true);
            ageField.set(user3, 30);
            
            System.out.println("After modification: " + user3.toString());

            // Method 3: Using reflection to call private methods
            System.out.println("\n=== Using Reflection to Call Private Methods ===");
            InvalidUser user4 = new InvalidUser();
            System.out.println("Original: " + user4.toString());
            
            // Call setName using reflection
            java.lang.reflect.Method setNameMethod = InvalidUser.class.getDeclaredMethod("setName", String.class);
            setNameMethod.invoke(user4, "Method Reflection");
            
            // Call setAge using reflection
            java.lang.reflect.Method setAgeMethod = InvalidUser.class.getDeclaredMethod("setAge", Integer.class);
            setAgeMethod.invoke(user4, 35);
            
            System.out.println("After method calls: " + user4.toString());

        } catch (Exception e) {
            System.err.println("Reflection error: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
