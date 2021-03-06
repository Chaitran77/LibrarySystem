package com.kiranthepro;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HexFormat;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        MainMenu menu = new MainMenu();
        while (!menu.exited) {
            menu.display();
            menu.getAndExecuteAction();
        }

    }

    public static String getInput(String prompt) {
        System.out.println(prompt);
        try {
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("\n");
            return scanner.next();
        } catch (Exception e) {
            System.out.println("An exception occurred - please input a valid sequence of characters:");
            return getInput(prompt);
        }
    }

    public static String getInputNNL(String prompt) {
        // prints prompt without newline
        System.out.print(prompt);
        try {
            Scanner scanner = new Scanner(System.in);
            scanner.useDelimiter("\n");
            return scanner.next();
        } catch (Exception e) {
            System.out.println("An exception occurred - please input a valid sequence of characters:");
            return getInput(prompt);
        }
    }

    public static Boolean getYesNo(String prompt) {
        String input = getInput(prompt);

        if (input.toLowerCase().contains("y") && !input.toLowerCase().contains("n")) {
            return true;
        } else if (input.toLowerCase().contains("n") && !input.toLowerCase().contains("y")) {
            return false;
        } else {
            System.out.println("Error reading input - please type either yes or no: ");
            return getYesNo(prompt);
        }
    }

    public static FileWriter getFileWriter(File file, boolean append) {
        try {
            return new FileWriter(file, append);
        } catch (IOException e) {
            return null;
        }
    }

    public static FileReader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (IOException e) {
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        // helper for getHash()
        return HexFormat.of().formatHex(bytes);
    }

}
