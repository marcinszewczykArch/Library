package pl.library.io.file;

import pl.library.exception.NoSuchFileTypeException;
import pl.library.io.ConsolePrinter;
import pl.library.io.DataReader;

import java.util.Locale;

public class FileManagerBuilder {
    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build(){
        printer.printLine("Select data format:");
        FileType fileType = getFileType();
        switch (fileType){
            case SERIAL:
                return new SerializableFileManager();
            case CSV:
                return new CsvFileManager();
            default:
                throw new NoSuchFileTypeException("unsupported file type");
        }
}

    private FileType getFileType() {
        boolean typeOk = false;
        FileType result = null;

        do{
            printTypes();
            String type = reader.getString().toUpperCase(Locale.ROOT);
            try{
                result = FileType.valueOf(type);
                typeOk = true;
            }catch (IllegalArgumentException e){
                printer.printLine("wrong file type");
            }
        }while(typeOk==false);
        return result;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }

}
