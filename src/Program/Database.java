package Program;

import Entities.Student;
import Entities.Book;

import java.sql.*;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database { //Repository
    private static Database instance = new Database(); //Singleton
    private Connection connection = null;

    public static Database getInstance() {
        return instance;
    }

    private Database() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/libraryproject", "postgres", "123");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void DDLStatements() { // we prepare ddl statements to create tables and manipulate with them in postgresql
        String sql1 = "CREATE TABLE IF NOT EXISTS books (\n" +    //create table books, if it is not exists
                "  id integer NOT NULL,\n" +
                "  title varchar(255) NOT NULL,\n" +
                "  author varchar(255) NOT NULL,\n" +
                "  genre varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (id)\n" +
                ")";
        String sql2 = "CREATE TABLE IF NOT EXISTS students (\n" +  //table students
                "  id integer NOT NULL,\n" +
                "  name varchar(50) NOT NULL,\n" +
                "  password varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (id)\n" +
                ")";
        String sql3 = "CREATE TABLE IF NOT EXISTS librarian (\n" +   //table librarian
                "  name varchar(50) NOT NULL,\n" +
                "  password varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY (name)\n" +
                ")";
        String sql4 = "CREATE TABLE IF NOT EXISTS borrow (\n" +     //table borrow
                "  bookid integer NOT NULL,\n" +
                "  studentid integer NOT NULL\n" +
                ")";
        try {

            // PreparedStatement - для insert, update, delete
            PreparedStatement preparedStatement = connection.prepareStatement(sql1); //create PreparedStatements
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(sql3);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = connection.prepareStatement(sql4);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM librarian WHERE name='admin'");
            if (resultSet.next()) {
                System.out.println("Librarian already exists");
            } else {
                String sql5 = "INSERT INTO librarian VALUES('admin', 'admin')";
                preparedStatement = connection.prepareStatement(sql5);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Book getBook(int id) {   //method to get info about book
        Book book = null;
        String sql = "SELECT * FROM books WHERE id=" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                book = new Book(resultSet.getString("title"), resultSet.getString("author"), resultSet.getString("genre"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public Student getStudent(String name, String password) { //method to get Student info
        Student student = null; //firstly no info about the student
        int studentId = getStudentId(name, password); //to get studentid according to his/her name and password
        String sql = "SELECT * FROM borrow WHERE studentid=" + studentId; //recall sql statement using condition, where studentid presents...
        try {
            Statement statement = connection.createStatement(); //to create resultset
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Book> books = new ArrayList<>(); //new object of array list
            while (resultSet.next()) { //statement to make request to the db
                Book book = getBook(resultSet.getInt("bookid")); //access to the fields of the columns
                System.out.println(studentId);
                System.out.println(resultSet.getInt("bookid"));
                if (book != null) { //if book is borrowed, than add it to the student's borrow list
                    books.add(book);
                }
            }
            student = new Student(name, password, books); //student with name, password and books
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";   //we select all students
        try {
            Statement statement = connection.createStatement();  //Statement used for "select"
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Student student = getStudent(resultSet.getString("name"), resultSet.getString("password"));
                if (student != null) {
                    students.add(student);
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public ArrayList<Book> getBooks() {      //method to get all books
        ArrayList<Book> books = new ArrayList<>();  //using array list of books
        String sql = "SELECT * FROM books";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = getBook(resultSet.getInt("id"));
                if (book != null) {
                    books.add(book);
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public int getStudentId(String name, String password) {
        String sql = "SELECT id FROM students WHERE name='" + name + "' and password='" + password + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt("id");   //get student id from database
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Book> getBooksOfAuthor(String author) {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author='" + author + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = getBook(resultSet.getInt("id"));
                if (book != null) {
                    books.add(book);   //"books" contains books with inputted author
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public ArrayList<Book> getBooksOfGenre(String genre) {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE genre='" + genre + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = getBook(resultSet.getInt("id"));
                if (book != null) {
                    books.add(book);    //"books" contains books with inputted genre
                }
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public int login(String name, String password) {     //method to check if student is ib base
        String sql = "SELECT * FROM students WHERE name='" + name + "' and password='" + password + "'"; //sql statement
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return 1;
            }
            resultSet.close();
            statement.close();
            sql = "SELECT * FROM librarian WHERE name='" + name + "' and password='" + password + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return 2;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // sql injection
    public int addStudent(int id, String name, String password) {    //method to add student
        String sql = "insert into students(id, name, password) values(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql); //Use PreparedStatement
            // to add new data in student table
            preparedStatement.setInt(1, id);   //id column
            preparedStatement.setString(2, name);  //name column
            preparedStatement.setString(3, password);  //password column
            if (preparedStatement.executeUpdate() > 0) {
                return 1;  //success
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addBook(int id, String title, String author, String genre) {
        String sql = "insert into books(id, title, author, genre) values(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);  //Use PreparedStatement
            // to add new data in book table
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, author);
            preparedStatement.setString(4, genre);
            if (preparedStatement.executeUpdate() > 0) {
                return 1;  //success
            }
            preparedStatement.close();
        } catch (SQLException e) {      //exception handling
            e.printStackTrace();
        }
        return 0;
    }

    public int updateBook(int id, String title, String author, String genre) {
        String sql = "update books set title=?, author=?, genre=? where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);   //Use PreparedStatement
            //to update data in book table
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, genre);
            preparedStatement.setInt(4, id);
            if (preparedStatement.executeUpdate() > 0) {
                return 1;  //success
            }
            preparedStatement.close();
        } catch (SQLException e) {     //exception handling
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteBook(int id) {
        String sql = "delete from books where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);  //Use PreparedStatement
            // to delete book by id
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                return 1;    //success
            }
            preparedStatement.close();
        } catch (SQLException e) {   //exception handling
            e.printStackTrace();
        }
        return 0;
    }

    public int borrowBook(int bookId, int studentId) {
        String sql = "insert into borrow(bookid, studentid) values(?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql); //get connection to the db
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, studentId);
            if (preparedStatement.executeUpdate() > 0) {
                return 1;    //success
            }
            preparedStatement.close();
        } catch (SQLException e) {     //exception handling
            e.printStackTrace();
        }
        return 0;
    }

    public int returnBook(int bookId, int studentId) {
        String sql = "delete from borrow where bookid=? and studentid=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);   //Use PreparedStatement
            // to return book
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, studentId);
            if (preparedStatement.executeUpdate() > 0) {
                return 1;    //success
            }
            preparedStatement.close();
        } catch (SQLException e) {      //exception handling
            e.printStackTrace();
        }
        return 0;
    }
}
