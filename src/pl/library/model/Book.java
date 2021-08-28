package pl.library.model;

import java.util.Objects;

public class Book extends Publication{
    public static final String TYPE = "Book";
    private String author;
    private int pages;
    private String isbn;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book(String title, String author, String publisher, int year, int pages, String isbn) {
        super(title, publisher, year);
        this.author = author;
        this.pages = pages;
        this.isbn = isbn;
    }

    @Override
    public String toCsv() {
        return TYPE + ";"
                + getTitle()+ ";"
                + getPublisher()+ ";"
                + getYear()+ ";"
                + author+ ";"
                + pages+ ";"
                + isbn;
    }

    @Override
    public String toString() {
        return  super.toString()
                + author + ", "
                + pages + ", "
                + isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, pages, isbn);
    }
}
