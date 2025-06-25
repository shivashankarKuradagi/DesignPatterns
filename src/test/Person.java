package test;



public class Person {

    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }



    public static class PersonBuilder {
        private String name;
        private Integer age;

        public PersonBuilder name(String name){
            this.name = name;
            return this;
        }

        public PersonBuilder age(Integer age){
            this.age = age;
            return this;
        }

        public  Person build(){
            return  new Person(this.name, this.age);
        }

    }

}
