package builder;

public class UserBuilderMain {
    public static void main(String[] args) {
        // Demonstrate Builder Pattern
        System.out.println("=== Builder Pattern Demo ===");
        
        // Create user using builder pattern
        User user1 = new User.UserBuilder()
                .name("John Doe")
                .age(30)
                .build();
        
        System.out.println("Built User 1: " + user1.toString());
        
        // Create another user with different parameters
        User user2 = new User.UserBuilder()
                .name("Jane Smith")
                .age(25)
                .build();
        
        System.out.println("Built User 2: " + user2.toString());
        
        // Demonstrate fluent interface
        User user3 = new User.UserBuilder()
                .name("Bob Johnson")
                .age(35)
                .build();
        
        System.out.println("Built User 3: " + user3.toString());
    }
    
    // Static method to demonstrate builder pattern
    public static void demonstrateBuilder() {
        System.out.println("\n=== Builder Pattern from Singleton ===");
        
        // Create users using builder pattern
        User builderUser1 = new User.UserBuilder()
                .name("Builder User 1")
                .age(28)
                .build();
        
        User builderUser2 = new User.UserBuilder()
                .name("Builder User 2")
                .age(32)
                .build();
        
        System.out.println("Builder User 1: " + builderUser1.toString());
        System.out.println("Builder User 2: " + builderUser2.toString());
        System.out.println("Are they the same instance? " + (builderUser1 == builderUser2));
    }
}