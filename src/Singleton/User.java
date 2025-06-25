package singleton;

import java.io.Serializable;

public class User implements Serializable {

    // Private static volatile instance
    private static volatile User userInstance;
    
    // Final instance variables (immutable)
    private final String name;
    private final Integer age;

    // Private constructor to prevent instantiation
    private User() {
        // Prevent creation through reflection
        if (userInstance != null) {
            throw new RuntimeException("Use getInstance() method to create");
        }
        // Set default values
        this.name = "Default User";
        this.age = 0;
    }

    // Private constructor with parameters
    private User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // Public static method to get the singleton instance (default)
    public static User getInstance() {
        if (userInstance == null) {
            synchronized (User.class) {
                if (userInstance == null) {
                    userInstance = new User();
                }
            }
        }
        return userInstance;
    }

    // Alternative: Singleton with initialization (use this approach if you need parameters)
    public static User getInstance(String name, Integer age) {
        if (userInstance == null) {
            synchronized (User.class) {
                if (userInstance == null) {
                    userInstance = new User(name, age);
                }
            }
        }
        return userInstance;
    }

    // Getters only (no setters - immutable)
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // Prevent deserialization from creating new instances
    protected Object readResolve() {
        return getInstance();
    }

    // Prevent cloning by throwing exception
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned");
    }
}
