package pl.library.app;

import pl.library.exception.NoSuchOptionException;

public class LibraryApp {
    private static final String APP_NAME = "Library v1.8.2";

    public static void main(String[] args) throws NoSuchOptionException {

        System.out.println(APP_NAME + "\n");

        LibraryControl libraryControl = new LibraryControl();
        libraryControl.controlLoop();

    }
}
