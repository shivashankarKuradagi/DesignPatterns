package Singleton;

public class UserMain {
    public static void main(String[] args) {
        User user = User.getUserInstance("shiva", 20);
        User user2 = User.getUserInstance("vedu", 20);
        System.out.println(user.toString());
        System.out.println(user2.toString());
    }
}
