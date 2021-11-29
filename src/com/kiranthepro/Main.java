package com.kiranthepro;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File libraryFile = initialiseFile("books.csv");
        LinkedHashMap<String, String> bookInfo = getBookInfo();

        String result = "";
        bookInfo.forEach((key, value) -> System.out.println(key + ": " + value));
        writeToFile(libraryFile, bookInfo);

    }

    public static File initialiseFile(String fileName) {
        File fileObj = new File(fileName);

        try {
            if (fileObj.createNewFile()) {
                System.out.println("File " + fileObj.getName() + " created successfully.");
            } else {
                System.out.println(fileObj.getName() + " already exists.");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong creating the file :(");
            e.printStackTrace();
        }

        return fileObj;
    }

    public static LinkedHashMap<String, String> getBookInfo() {
        LinkedHashMap<String, String> bookInfo = new LinkedHashMap<>();
        String[] bookAttributes = {"title", "ISBN", "author", "genre", "year", "page count", "language", "cover type"};

        for (String attribute: bookAttributes) {
            bookInfo.put(attribute, getInput("Type in the " + attribute + " of the book:"));
        }

        return bookInfo;
    }

    public static void writeToFile(File file, LinkedHashMap<String, String> data) {


//        System.out.println(String.join(",", dataArray));
    }

    public static String getInput(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

}
