package Singleton;

import java.io.Closeable;
import java.io.Serializable;


public class User implements Serializable, Cloneable {

    public static volatile User  userInstance;
    private String name;
    private Integer age;

    private User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static User getUserInstance(String name, Integer age){
        if (userInstance == null){
            synchronized (User.class){
                if (userInstance == null){
                    return new User(name, age);
                }
            }
        }
        return userInstance;
    }


    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
