package pl.library.io.file;

import pl.library.exception.DataExportException;
import pl.library.exception.DataImportException;
import pl.library.exception.InvalidDataException;
import pl.library.model.*;

import java.io.*;
import java.util.*;

public class CsvFileManager implements FileManager {
    private static final String FILE_NAME_PUBLICATIONS = "LibraryPublications.csv";
    private static final String FILE_NAME_USERS = "LibraryUsers.csv";

    @Override
    public Library importData() {
        Library library = new Library();
        importPublications(library);
        importUsers(library);

        return library;
    }

    private void importPublications(Library library) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME_PUBLICATIONS))) {
            bufferedReader.lines()
                    .map(this::createPublicationObjectFromString)
                    .forEach(library::addPublication);
        } catch (FileNotFoundException e) {
            throw new DataImportException("there is no file with the name: " + FILE_NAME_PUBLICATIONS);
        } catch (IOException e) {
            throw new DataImportException("error while importing file with the name: " + FILE_NAME_PUBLICATIONS);
        }
    }

    private void importUsers(Library library) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME_USERS))) {
            bufferedReader.lines()
                    .map(this::createUserObjectFromString)
                    .forEach(library::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("there is no file with the name: " + FILE_NAME_USERS);
        } catch (IOException ioException) {
            throw new DataImportException("error while importing file with the name: " + FILE_NAME_USERS);
        }
    }

    @Override
    public void exportData(Library library) {
        exportPublications(library);
        exportUsers(library);
    }

    private void exportUsers(Library library) {
        Collection users = library.getUsers().values();
        exportToCsv(users, FILE_NAME_USERS);
    }

    private void exportPublications(Library library) {
        Collection publications = library.getPublications().values();
        exportToCsv(publications, FILE_NAME_PUBLICATIONS);
    }

    private <T extends CsvConvertable> void exportToCsv(Collection<T> collection, String fileName) {
        try(
                var fileWriter = new FileWriter(fileName);
                var bufferedWriter = new BufferedWriter(fileWriter);
        ){
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("this file could not be exported: " + fileName);
        }
    }

    private Publication createPublicationObjectFromString(String line) {
        List<String> publication = Arrays.asList(line.split(";"));
        String publicationType = publication.get(0);

        if(publicationType.equals(Book.TYPE)){
                String title = publication.get(1);
                String publisher = publication.get(2);
                String year = publication.get(3);
                String author = publication.get(4);
                String pages = publication.get(5);
                String isbn = publication.get(6);
            return new Book(title, author, publisher, Integer.parseInt(year), Integer.parseInt(pages), isbn);
        }
        else if(publicationType.equals(Magazine.TYPE)){
                String title = publication.get(1);
                String publisher = publication.get(2);
                String year = publication.get(3);
                String month = publication.get(4);
                String day = publication.get(5);
                String language = publication.get(6);
            return new Magazine(title, publisher, language, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        }
        else{
            throw new InvalidDataException("unknown publication type: " + publicationType);
        }
    }

    private LibraryUser createUserObjectFromString(String line) {
        List<String> user = Arrays.asList(line.split(";"));

            String firstName = user.get(0);
            String lastName = user.get(1);
            String pesel = user.get(2);

            return new LibraryUser(firstName, lastName, pesel);
        }

}
