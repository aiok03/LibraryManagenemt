package Entities;

import java.util.ArrayList;

public class Student extends Person{  //inheritance
    private ArrayList<Book> books;   // array of books that were borrowed from library(through generic)

    public Student(String name, String password, ArrayList<Book> books) {
        super(name, password);
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }   //to get all books


    @Override  //to override the method of the Object class
    public String toString() {
        return "Student{\n" + super.toString() + //super keyword is used to keep method from the
                // superclass and add it to the child class
                "\nbooks=" + books +
                "\n}";
    }
}
