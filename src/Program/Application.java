package Program;

import Entities.Person;
import Entities.Student;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {   //frontend of our console app
    private static final Scanner scanner = new Scanner(System.in);
    private static final Manager manager = new Manager();

    private static void librarianMenu(Person librarian) throws InputMismatchException {    //menu for librarian
        System.out.println("**************************************************");
        System.out.println("Welcome, " + librarian.getName());
        int menuChoice = 0;
        while (menuChoice != 6) {
            System.out.println("**************************************************");
            System.out.println("1. Add student");
            System.out.println("2. Add book");
            System.out.println("3. Update book");
            System.out.println("4. Delete book");
            System.out.println("5. Library data");
            System.out.println("6. Exit");
            menuChoice = scanner.nextInt();
            switch (menuChoice) {
                case 1 -> manager.addStudent();  //switch cases through lambdas
                case 2 -> manager.addBook();
                case 3 -> manager.updateBook();
                case 4 -> manager.deleteBook();
                case 5 -> manager.getLibraryData(librarian);
                case 6 -> System.out.println("Exit...");
                default -> System.out.println("Wrong choice...");  //if wrong
            }
        }
    }

    private static void studentMenu(Student student) throws InputMismatchException {    //menu for student
        System.out.println("**************************************************");
        System.out.println("Welcome, " + student.getName());
        int menuChoice = 0;
        while (menuChoice != 6) {
            System.out.println("**************************************************");
            System.out.println("1. Borrow book");
            System.out.println("2. Return book");
            System.out.println("3. Get books of author");
            System.out.println("4. Get books of genre");
            System.out.println("5. Student data");
            System.out.println("6. Exit");
            menuChoice = scanner.nextInt();
            switch (menuChoice) {
                case 1 -> manager.borrowBook(student);   //switch cases through lambdas
                case 2 -> manager.returnBook(student);
                case 3 -> manager.getBooksOfAuthor();
                case 4 -> manager.getBooksOfGenre();
                case 5 -> manager.getStudentData(student);
                case 6 -> System.out.println("Exit...");
                default -> System.out.println("Wrong choice...");
            }
        }
    }

    public static void main(String[] args) {
        manager.setTables();
        System.out.println("**************************************************");
        System.out.println("Welcome to library system!");
        System.out.println("Choose your authority type: librarian(l) / student(s)");
        char type = scanner.next().charAt(0);
        if (type == 'l') {
            Person librarian = null;
            while (librarian == null) {
                librarian = manager.librarianLogin();
            }
            librarianMenu(librarian);
        } else if (type == 's') {
            Student student = null;
            while (student == null) {
                student = manager.studentLogin();
            }
            studentMenu(student);
        }
    }
}