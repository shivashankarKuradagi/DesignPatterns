package test;

public class PersonTest {

    public static void main(String[] args) {
        Person person = new Person.PersonBuilder().name("shiv").age(20).build();
        System.out.println("person :: " + person.toString());
    }
}
