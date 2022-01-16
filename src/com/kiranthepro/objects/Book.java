package com.kiranthepro.objects;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Book {
    private String title;
    private String ISBN;
    private String author;
    private String genre;
    private int year;
    private int pageCount;
    private String language;
    private String coverType;

    // ERRORS WILL OCCUR HERE IF DATA IN BOOKS FILE IS MALFORMED

    public Book(String bookCSVLine) {
        String[] bookCSVLineElems = bookCSVLine.split(",");
        this.title = bookCSVLineElems[0];
        this.ISBN = bookCSVLineElems[1];
        this.author = bookCSVLineElems[2];
        this.genre = bookCSVLineElems[3];
        this.year = Integer.parseInt(bookCSVLineElems[4]);
        this.pageCount = Integer.parseInt(bookCSVLineElems[5]);
        this.language = bookCSVLineElems[6];
        this.coverType = bookCSVLineElems[7];
    }

    @Override
    public String toString() {
        // not to be shown to the user; for comparing and writing to file only
        return title + "," + ISBN + "," + author + "," + genre + "," + year + "," + pageCount + "," + language + "," + coverType;
    }

    public ArrayList<String> getAttributeArrayList() {
        // returns arraylist with attributes in order, all as strings

        return Stream.of(
                this.title,
                String.valueOf(this.ISBN),
                this.author,
                this.genre,
                String.valueOf(this.year),
                String.valueOf(this.pageCount),
                this.language,
                this.coverType
        ).collect(Collectors.toCollection(ArrayList::new));
    }



    // getter and setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCoverType() {
        return coverType;
    }

    public void setCoverType(String coverType) {
        this.coverType = coverType;
    }
}
