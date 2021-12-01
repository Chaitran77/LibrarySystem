package com.kiranthepro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

    private static final String[] bookAttributes = {"title", "ISBN", "author", "genre", "year", "page count", "language", "cover type"};

    public static void main(String[] args) {

        File libraryFile = initialiseFile("books.csv");

        boolean done = false;
        while (!done) {
            writeToFile(libraryFile, getBookInfo());
            if (getInput("Would you like to add another file?").toLowerCase().contains("n")) {
                done = true;
            }
        }


    }

    public static File initialiseFile(String fileName) {
        File fileObj = new File(fileName);

        try {
            if (fileObj.createNewFile()) {
                FileWriter writer = getFileWriter(fileObj);
                assert writer != null;
                writer.write(String.join(",", bookAttributes) + "\n");
                writer.flush();
                writer.close();
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

        for (String attribute: bookAttributes) {
            String input = getInput("Type in the " + attribute + " of the book:");
            boolean inputValid = false;
            switch (attribute) {
                case "title":
                    if (input.length() <= 100) {
                        inputValid = true;
                    }
                    break;
                case "ISBN":
                    break;
                case "author":
                    break;
                case "genre":
                    break;
                case "year":
                    break;
                case "page count":
                    break;
                case "language":
                    break;
                case "cover type":
                    break;
            }
            bookInfo.put(attribute, input);
        }

        return bookInfo;
    }

    public static void writeToFile(File file, LinkedHashMap<String, String> data) {
        String[] values = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            values[i] = (String) data.values().toArray()[i];
        }
        try {
            FileWriter writer = getFileWriter(file);
            if (writer != null) {
                writer.write(String.join(",", values) + "\n");
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Couldn't write to file - something went wrong :(");
        }

        System.out.println();
    }

    public static FileWriter getFileWriter(File file) {
        try {
            return new FileWriter(file, true);
        } catch (IOException e) {
            return null;
        }
    }

    public static String getInput(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
