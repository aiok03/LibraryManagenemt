package Program;

import Entities.Library;
import Entities.Person;
import Entities.Student;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager {
    private static final Database database = Database.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void setTables() {
        database.DDLStatements();
    }

    public void addStudent() throws InputMismatchException {
        System.out.println("Id");
        int id = scanner.nextInt();
        System.out.println("Name");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.println("Password");
        String password = scanner.next();
        if (database.addStudent(id, name, password) == 1) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void addBook() throws InputMismatchException {
        System.out.println("Id");
        int id = scanner.nextInt();
        System.out.println("Title");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Author");
        String author = scanner.nextLine();
        System.out.println("Genre");
        String genre = scanner.next();
        if (database.addBook(id, title, author, genre) == 1) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void updateBook() throws InputMismatchException {
        System.out.println("Book id to be changed");
        int bookId = scanner.nextInt();
        System.out.println("New title");
        scanner.nextLine();
        String title = scanner.nextLine();
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

    public void deleteBook() throws InputMismatchException {
        System.out.println("Book id to be deleted");
        int bookId = scanner.nextInt();
        if (database.deleteBook(bookId) == 1) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void borrowBook(Student student) throws InputMismatchException {
        int studentId = database.getStudentId(student.getName(), student.getPassword());
        System.out.println("Book id");
        int bookId = scanner.nextInt();
        if (database.borrowBook(bookId, studentId) == 1) {   //if insert was successfully done,
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void returnBook(Student student) throws InputMismatchException {
        int studentId = database.getStudentId(student.getName(), student.getPassword());
        System.out.println("Book id");
        int bookId = scanner.nextInt();
        if (database.returnBook(bookId, studentId) == 1) {
            student.getBooks().remove(database.getBook(bookId));
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }

    public void getBooksOfAuthor() throws InputMismatchException {
        System.out.println("Author");
        scanner.nextLine();
        String author = scanner.nextLine();
        System.out.println(database.getBooksOfAuthor(author));
    }

    public void getBooksOfGenre() throws InputMismatchException {
        System.out.println("Genre");
        String genre = scanner.next();
        System.out.println(database.getBooksOfGenre(genre));
    }

    public Student studentLogin() throws InputMismatchException {
        System.out.println("Name");
        String name = scanner.nextLine();
        System.out.println("Password");
        String password = scanner.next();
        if (database.login(name, password) == 1) {
            return database.getStudent(name, password);
        }
        return null;
    }

    public Person librarianLogin() throws InputMismatchException {
        System.out.println("Name");
        String name = scanner.next();
        System.out.println("Password");
        String password = scanner.next();
        if (database.login(name, password) == 2) {
            return new Person(name, password);
        }
        return null;
    }

    public void getStudentData(Student student) {
        System.out.println(student);
    }

    public void getLibraryData(Person person) {
        Library library = new Library(person);
        library.setBooks(database.getBooks());
        library.setStudents(database.getStudents());
        System.out.println(library);
    }
}