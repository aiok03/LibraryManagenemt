package Entities;
import java.util.Objects;
public class Book {
    private String title;
    private String author;
    private String genre;

    public Book(String title, String author, String genre) {  //constructor to set title, author and genre
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {  // override method equals from object class
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.getTitle()) && author.equals(book.getAuthor()) && genre.equals(book.getGenre());
        //if fields are equal then objects are equal
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
