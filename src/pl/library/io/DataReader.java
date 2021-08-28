package pl.library.io;

import pl.library.model.Book;
import pl.library.model.LibraryUser;
import pl.library.model.Magazine;

import java.util.Scanner;

public class DataReader {

    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter consolePrinter;

    public DataReader(ConsolePrinter consolePrinter) {
        this.consolePrinter = consolePrinter;
    }

    public Book readerAndCreateBook() {
        consolePrinter.printLine("title: ");
            String title = sc.nextLine();
        consolePrinter.printLine("author: ");
            String author = sc.nextLine();
        consolePrinter.printLine("publisher: ");
            String publisher = sc.nextLine();
        consolePrinter.printLine("year: ");
            int year = sc.nextInt();
            sc.nextLine();
        consolePrinter.printLine("pages: ");
            int pages = sc.nextInt();
            sc.nextLine();
        consolePrinter.printLine("isbn: ");
            String isbn = sc.nextLine();
        return new Book(title, author, publisher, year, pages, isbn);
    }

    public Magazine readerAndCreateMagazine() {
        consolePrinter.printLine("title: ");
            String title = sc.nextLine();
        consolePrinter.printLine("publisher: ");
            String publisher = sc.nextLine();
        consolePrinter.printLine("language: ");
            String language = sc.nextLine();
        consolePrinter.printLine("year: ");
            int year = sc.nextInt();
            sc.nextLine();
        consolePrinter.printLine("month: ");
            int month = sc.nextInt();
            sc.nextLine();
        consolePrinter.printLine("day: ");
            int day = sc.nextInt();
            sc.nextLine();

        return new Magazine(title, publisher, language, year, month, day);
    }

    public LibraryUser createLibraryUser() {
        consolePrinter.printLine("user name: ");
        String firstName = sc.nextLine();
        consolePrinter.printLine("user last name: ");
        String lastName = sc.nextLine();
        consolePrinter.printLine("user pesel: ");
        String pesel = sc.nextLine();

        return new LibraryUser(firstName, lastName, pesel);
    }

    public void close() {
        sc.close();
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public String getString(){
        return sc.nextLine();
    }

}
