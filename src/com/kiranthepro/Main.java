package com.kiranthepro;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        MainMenu menu = new MainMenu();
        // TODO: Add exit method for menu and link to method state (.isExited())
        while (!menu.isExited()) {
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

}
