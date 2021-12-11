package com.kiranthepro;

import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        MainMenu menu = new MainMenu();
        // TODO: Add exit method for menu and link to method state (.isExited())
        while (true) {
            menu.display();
            menu.getAndExecuteAction();
        }

    }

    public static String getInput(String prompt) {
        System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        return scanner.next();
    }

}
