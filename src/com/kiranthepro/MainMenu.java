package com.kiranthepro;

import java.util.Scanner;

import static com.kiranthepro.UAC.addUser;
import static com.kiranthepro.UAC.deleteUser;

public class MainMenu {

	private final String[] actions;

	public MainMenu(boolean administrator) {
		if (administrator) {
			actions = new String[] {"Add book", "Delete book", "Modify book", "View books", "Add user", "Delete user", "Switch user"};
		} else {
			actions = new String[] {"Add book", "View books", "Switch user"};
		}

		// delete/modify book --> book name --> search for book() --> display book info() --> is this the book you'd like to delete/modify?
	}

	public void display() {
		System.out.println("====    M A I N        M E N U    ====\nThis is a uni-user system. Possible actions for your user: ");
		for (int i = 1; i <= actions.length; i++) {
			System.out.println(i + ": " + actions[i-1]);
		}
	}

	public void getAction() {
		System.out.println("Choose an action by typing its number:");
		Scanner scanner = new Scanner(System.in);
		try {
			execute(actions[scanner.nextInt()]);
		} catch (NumberFormatException e) {
			System.out.println("An error occurred; please type a number between 1 and " + actions.length);
			getAction();
		}
	}

	private void test() {
		System.out.println("test function");
	}

	private void execute(String action) {
		System.out.println(action);
		switch (action) {
			case "Add book" -> test();
			case "Delete book" -> test();
			case "Modify book" -> test();
			case "View books" -> test();
			case "Add user" -> addUser();
			case "Delete user" -> deleteUser();
			case "Switch user" -> test();
		}
	}
}
