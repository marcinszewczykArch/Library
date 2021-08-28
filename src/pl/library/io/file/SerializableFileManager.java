package pl.library.io.file;

import pl.library.exception.DataExportException;
import pl.library.exception.DataImportException;
import pl.library.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager {
private static final String FILE_NAME = "Library.o";

    @Override
    public Library importData() {
        try (
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
        ){
            return (Library)ois.readObject();
        }
          catch (FileNotFoundException e) {
            throw new DataImportException("there is no file with the name: " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("this file could not be imported : " + FILE_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataImportException("wrong data type in the file : " + FILE_NAME);
        }
    }

    @Override
    public void exportData(Library library){
        try (
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(library);
        } catch (FileNotFoundException e) {
            throw new DataExportException("there is no file with the name: " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("this file could not be exported : " + FILE_NAME);
        }
    }
}
