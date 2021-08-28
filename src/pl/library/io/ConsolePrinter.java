package pl.library.io;

import pl.library.model.*;

import java.util.Collection;
import java.util.Locale;

public class ConsolePrinter {

    public void printBooks(Collection<Publication> publications) {
        long countBooks = publications.stream()
                .filter(p -> p instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if(countBooks==0){
            printLine("no books in the library");
        }
    }

    public void printMagazines(Collection<Publication> publications) {
        long countMagazines = publications.stream()
                .filter(p -> p instanceof Magazine)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if(countMagazines==0){
            printLine("no magazines in the library");
        }
    }

    public void printUsers(Collection<LibraryUser> users) {
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
        if(users.size()==0){
            printLine("no users in the library");
        }
    }

    public void printLine(String text) {
        System.out.println(text.toUpperCase(Locale.ROOT));
    }

}
