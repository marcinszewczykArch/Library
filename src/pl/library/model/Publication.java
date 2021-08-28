package pl.library.model;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

public abstract class Publication implements Serializable, Comparable<Publication>, CsvConvertable {

    private String title;
    private String publisher;
    private Year year;

    public Publication(String title, String publisher, int year) {
        this.title = title;
        this.publisher = publisher;
        this.year = Year.of(year);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return  getTitle() + ", "
                + getPublisher() + ", "
                + getYear() + ", ";
    }

    @Override
    public int compareTo(Publication p) {
        return title.compareToIgnoreCase(p.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(title, that.title) && Objects.equals(publisher, that.publisher) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, publisher, year);
    }
}
