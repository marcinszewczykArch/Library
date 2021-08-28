package pl.library.app;

import pl.library.exception.*;
import pl.library.io.ConsolePrinter;
import pl.library.io.DataReader;
import pl.library.io.file.FileManager;
import pl.library.io.file.FileManagerBuilder;
import pl.library.model.*;

import java.util.Comparator;
import java.util.InputMismatchException;

public class LibraryControl {

    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private DataReader dataReader = new DataReader(consolePrinter);
    private FileManager fileManager;

    private Library library;

    public LibraryControl(){
        fileManager = new FileManagerBuilder(consolePrinter, dataReader).build();
        try {
            library = fileManager.importData();
            consolePrinter.printLine("database has been imported from the file");
        }catch (DataImportException | InvalidDataException e){
            consolePrinter.printLine(e.getMessage());
            consolePrinter.printLine("new database has been initialized");
            library = new Library();
        }
    }

    public void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();

            switch (option) {
                case EXIT:
                    exit();
                    break;
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case FIND_PUBLICATION:
                    findPublication();
                    break;
                default:
                    consolePrinter.printLine("wrong option selected. Please try again");
            }
        }while(option != Option.EXIT);

    }

    private void findPublication() {
        consolePrinter.printLine("type publication title:");
        String title = dataReader.getString();
        String notFoundPublicationMessage = "publication with this title does not exist in the library: " + title;
        library.findPublicationByTitle(title)
                .map(p -> p.toString())
                .ifPresentOrElse(consolePrinter::printLine, () -> consolePrinter.printLine(notFoundPublicationMessage));
    }

    private void printUsers() {
        consolePrinter.printUsers(library.getSortedUsers(
//                (o1, o2) -> o1.getLastName().compareToIgnoreCase(o2.getLastName())
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try{
            library.addUser(libraryUser);
        }catch (UserAlreadyExistsException e){
            consolePrinter.printLine(e.getMessage());
        }
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;

        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;

            } catch (NoSuchOptionException e) {
                consolePrinter.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                consolePrinter.printLine("input data is not a number. Please try again");
            }
        }
        return option;
    }

    private void exit() {
        try {
            fileManager.exportData(library);
            consolePrinter.printLine("database has been exported successfully");
        }catch (DataExportException e){
            consolePrinter.printLine(e.getMessage());
        }
        consolePrinter.printLine("bye bye");
        dataReader.close();
    }

    private void printBooks() {
        consolePrinter.printBooks(library.getSortedPublications(
//                (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle())
                Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    public  void printMagazines() {
        consolePrinter.printMagazines(library.getSortedPublications(
//                (o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle())
                Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void addBook() {
        try {
            Book book = dataReader.readerAndCreateBook();
            library.addPublication(book);
        }catch (InputMismatchException e){
            consolePrinter.printLine("incorrect data. Book can not be added to the library");
        }catch (ArrayIndexOutOfBoundsException e){
            consolePrinter.printLine("no more available space in the library");
        }
    }

    private void deleteBook() {
        Book book = dataReader.readerAndCreateBook();
        try {
            if (library.removePublication(book)) {
                consolePrinter.printLine("book has been removed\n" + book);
            } else {
                consolePrinter.printLine("there is no book in the library\n" + book);
            }
        }catch (InputMismatchException e){
            consolePrinter.printLine("incorrect data. Book can not be added to the library");
        }
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readerAndCreateMagazine();
            library.addPublication(magazine);
        }catch (InputMismatchException e){
            consolePrinter.printLine("incorrect data. Magazine can not be added to the library");
        }catch (ArrayIndexOutOfBoundsException e){
            consolePrinter.printLine("no more available space in the library");
        }
    }

    private void deleteMagazine() {
        Magazine magazine = dataReader.readerAndCreateMagazine();
        try {
            if (library.removePublication(magazine)) {
                consolePrinter.printLine("magazin has been removed\n" + magazine);
            } else {
                consolePrinter.printLine("there is no magazine in the library\n" + magazine);
            }
        }catch (InputMismatchException e){
        consolePrinter.printLine("incorrect data. Magazine can not be added to the library");
        }
    }

    private void printOptions() {
        consolePrinter.printLine("select option:");
        for(Option value : Option.values()){
            consolePrinter.printLine(value.toString());
        }
    }

    private enum Option {
        EXIT(0, "exit app"),
        ADD_BOOK(1, "add new book"),
        ADD_MAGAZINE(2, "add new magazine"),
        PRINT_BOOKS(3, "display all books"),
        PRINT_MAGAZINES(4, "display all magazines"),
        DELETE_BOOK(5, "delete book"),
        DELETE_MAGAZINE(6, "delete magazine"),
        ADD_USER(7, "add user" ),
        PRINT_USERS(8, "print users"),
        FIND_PUBLICATION(9, "find publication");

        private final int value;
        private final String description;

        @Override
        public String toString() {
            return value + " - " + description;
        }

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("No option with id " + option + ". Please try again");
            }

        }
    }
}
