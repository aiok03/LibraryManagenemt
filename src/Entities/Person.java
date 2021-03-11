package Entities;

public class Person {
    private String name;
    private String password;

    public Person(String name, String password) {  //constructor to set name and password
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }  //getter

    public String getPassword() {
        return password;
    }

    public void setName(String name) {   //setter
        this.name = name;
    }

    public void setPassword(String password) {   //setter for password
        this.password = password;
    }

    @Override   //to override the method of the Object class
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';      //to return name and password of the student
    }
}
