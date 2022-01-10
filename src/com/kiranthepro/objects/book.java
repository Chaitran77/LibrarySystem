package com.kiranthepro.objects;

public class book {
    private String title;
    private String ISBN;
    private String author;
    private String genre;
    private String year;
    private String pageCount;
    private String language;
    private String coverType;

    public book(String title, String ISBN, String author, String genre, String year, String pageCount, String language, String coverType) {
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.pageCount = pageCount;
        this.language = language;
        this.coverType = coverType;
    }

    @Override
    public String toString() {
        return title + ", " + ISBN + ", " + author + ", " + genre + ", " + year + ", " + pageCount + ", " + language + ", " + coverType;
    }
}
