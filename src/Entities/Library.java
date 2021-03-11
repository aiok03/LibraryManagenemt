package Entities;

import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books; // array of books
    private ArrayList<Student> students; // array of students
    private Person librarian;

    public Library(Person librarian) {    //to set books, students and librarian
        this.books = new ArrayList<>();
        this.students = new ArrayList<>();
        this.librarian = librarian;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }  //getter

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Person getLibrarian() {
        return librarian;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }  //setter

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setLibrarian(Person librarian) {
        this.librarian = librarian;
    }

    @Override   //to override the method of the Object class
    public String toString() {
        return "Library{" +
                "\n books=" + books +
                ",\n students=" + students +
                ",\n librarian=" + librarian +
                "\n}";
    }
}
