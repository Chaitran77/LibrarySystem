package com.kiranthepro;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final String[] bookAttributes = {"title", "ISBN", "author", "genre", "year", "page count", "ISO language code", "cover type"};

    public static void main(String[] args) {
        ArrayList<JSONObject> accounts = UAC.getAllAccountInfo("users.json");
//        System.out.println(hi.get(0).get("privileges"));
        assert accounts != null;
        for (JSONObject account: accounts) {
            System.out.println(account.get("user"));
        }

        MainMenu menu = new MainMenu(false);
        menu.display();

        File libraryFile = initialiseFile("books.csv");

        boolean done = false;
        while (!done) {
            writeToFile(libraryFile, getBookInfo());
            if (getInput("Would you like to add another book?").toLowerCase().contains("n")) {
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
            getAndPutBookAttribute(attribute, bookInfo);
        }

        return bookInfo;
    }

    public static void getAndPutBookAttribute(String attribute, LinkedHashMap<String, String> bookInfo) {
        String input = getInput("Type in the " + attribute + " of the book:");
        try {
            validateAttributeInput(attribute, input);
            bookInfo.put(attribute, input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            getAndPutBookAttribute(attribute, bookInfo);
        }
    }

    public static void validateAttributeInput(String attribute, String input) throws Exception {
        boolean inputValid = false;
        String errorMessage = "";

        switch (attribute) {
            case "title":
                if (input.length() <= 100) {
                    inputValid = true;
                } else {
                    errorMessage = "Type an input with <= 100 characters";
                }
                break;

            case "ISBN":
                try {
                    // both checks if number and 13 digits
                    Long.parseLong(input);
                    if (input.length() == 13) {
                        inputValid = true;
                    } else {
                        errorMessage = "Type an ISBN number (must be exclusively 13 integer digits):";
                    }
                } catch (NumberFormatException e) {
//                        inputValid = false;
                    errorMessage = "Type an ISBN number (must be exclusively 13 integer digits):";
                }
                break;

            case "author":
                if (input.length() <= 50) {
                    inputValid = true;
                } else {
                    errorMessage = "Type an input <= 50 characters:";
                }
                break;

            case "genre":
                if (input.length() <= 30) {
                    inputValid = true;
                } else {
                    errorMessage = "Type an input <= 30 characters:";
                }
                break;

            case "year":
                try {
                    Integer.parseInt(input);
                    if (input.length() == 4) {
                        inputValid = true;
                    } else {
                        errorMessage = "Type a number with exactly 4 digits (<=9999):";
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Type a number with exactly 4 digits (<=9999):";
                }
                break;

            case "page count":
                try {
                    if (Integer.parseInt(input) <= 1600) {
                        inputValid = true;
                    } else {
                        errorMessage = "Type a number <= 1600:";
                    }
                } catch (NumberFormatException e){
                    errorMessage = "Type a number <= 1600:";
                }
                break;

            case "ISO language code":
                String languageISOCodes = String.join(", ", Locale.getISOLanguages());
                if (Arrays.stream(Locale.getISOLanguages()).anyMatch(input::equalsIgnoreCase)) {
                    inputValid = true;
                } else {
                    errorMessage = "Please type one of the following codes: " + languageISOCodes;
                }
                break;

            case "cover type":
                if (input.equalsIgnoreCase("hardback") || input.equalsIgnoreCase("softback")) {
                    inputValid = true;
                } else {
                    errorMessage = "Please type one of hardback or softback:";
                }
                break;
        }

        if (!inputValid) {
            throw new Exception(errorMessage);
        }

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
        scanner.useDelimiter("\n");
        return scanner.next();
    }

}
