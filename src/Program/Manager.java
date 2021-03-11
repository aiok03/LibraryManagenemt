package Program;

import Entities.Library;
import Entities.Person;
import Entities.Student;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager {
    private static final Database database = Database.getInstance();   //setting con with db
    private Scanner scanner = new Scanner(System.in);

    public void setTables() {
        database.DDLStatements();
    }

    public void addStudent() throws InputMismatchException {    //realization of addStudent method
        System.out.println("Id");
        int id = scanner.nextInt();
        System.out.println("Name");
        scanner.nextLine();
        String name = scanner.nextLine();  //program will read entire string with spaces
        System.out.println("Password");
        String password = scanner.next();
        if (database.addStudent(id, name, password) == 1) {
            System.out.println("Success");     //success if everything inputted right
        } else {
            System.out.println("Fail");
        }
    }

    public void addBook() throws InputMismatchException {    //realization of addBook method
        System.out.println("Id");
        int id = scanner.nextInt();
        System.out.println("Title");
        scanner.nextLine();
        String title = scanner.nextLine();  //program will read entire string with spaces
        System.out.println("Author");
        String author = scanner.nextLine();
        System.out.println("Genre");
        String genre = scanner.next();
        if (database.addBook(id, title, author, genre) == 1) {
            System.out.println("Success");   //success if everything inputted right
        } else {
            System.out.println("Fail");
        }
    }

    public void updateBook() throws InputMismatchException {  //realization of updateBook method used to update info about book
        System.out.println("Book id to be changed");
        int bookId = scanner.nextInt();
        System.out.println("New title");
        scanner.nextLine();
        String title = scanner.nextLine();  //program will read entire string with spaces
        System.out.println("New author");
        String author = scanner.nextLine();
        System.out.println("New genre");
        String genre = scanner.next();
        if (database.updateBook(bookId, title, author, genre) == 1) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void deleteBook() throws InputMismatchException {   //method to delete book from library
        System.out.println("Book id to be deleted");
        int bookId = scanner.nextInt();
        if (database.deleteBook(bookId) == 1) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void borrowBook(Student student) throws InputMismatchException {   //method to borrow book
        int studentId = database.getStudentId(student.getName(), student.getPassword());
        System.out.println("Book id");
        int bookId = scanner.nextInt();
        if (database.borrowBook(bookId, studentId) == 1) {   //if insert was successfully done,
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void returnBook(Student student) throws InputMismatchException {   //method for student to return borrowed book
        int studentId = database.getStudentId(student.getName(), student.getPassword());
        System.out.println("Book id");
        int bookId = scanner.nextInt();
        if (database.returnBook(bookId, studentId) == 1) {
            student.getBooks().remove(database.getBook(bookId));  //if insert was successfully done
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void getBooksOfAuthor() throws InputMismatchException {   //method to get book of author
        System.out.println("Author");
        scanner.nextLine();
        String author = scanner.nextLine();
        System.out.println(database.getBooksOfAuthor(author));   //get books of inputted author
    }

    public void getBooksOfGenre() throws InputMismatchException {
        System.out.println("Genre");
        String genre = scanner.next();
        System.out.println(database.getBooksOfGenre(genre));  //get books by inputted genre
    }

    public Student studentLogin() throws InputMismatchException {   //when student want to use our library,
                                                                    // he/she should confirm that his/her info is in database
        System.out.println("Name");
        String name = scanner.nextLine();
        System.out.println("Password");
        String password = scanner.next();
        if (database.login(name, password) == 1) {
            return database.getStudent(name, password);
        }
        return null;
    }

    public Person librarianLogin() throws InputMismatchException {  //method for librarian where librarian should confirm his status
        System.out.println("Name");
        String name = scanner.next();
        System.out.println("Password");
        String password = scanner.next();
        if (database.login(name, password) == 2) {
            return new Person(name, password);
        }
        return null;
    }

    public void getStudentData(Student student) {  //method to get info about student
        System.out.println(student);
    }

    public void getLibraryData(Person person) {   //we can find all information(including books and registered students)
        Library library = new Library(person);
        library.setBooks(database.getBooks());
        library.setStudents(database.getStudents());
        System.out.println(library);
    }
}
