package Singleton;

public class InvalidUser {

    // Instance variables
    private String name;
    private Integer age;

    // Default constructor
    public InvalidUser() {
        this.name = "Default User";
        this.age = 0;
    }

    // Constructor with name
    public InvalidUser(String name) {
        this.name = name;
        this.age = 0;
    }

    // Constructor with name and age
    public InvalidUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for age
    public Integer getAge() {
        return age;
    }

    // Setter for age
    public void setAge(Integer age) {
        this.age = age;
    }

    // toString method for better representation
    @Override
    public String toString() {
        return "InvalidUser{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // equals method for object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        InvalidUser that = (InvalidUser) obj;
        
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return age != null ? age.equals(that.age) : that.age == null;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }
}
