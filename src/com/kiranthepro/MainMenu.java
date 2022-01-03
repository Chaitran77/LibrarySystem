package com.kiranthepro;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.kiranthepro.BookUtils.*;
import static com.kiranthepro.UserAccountControl.addUser;
import static com.kiranthepro.UserAccountControl.deleteUser;

public class MainMenu {

	private final String[] actions;
	public boolean exited = false;

	public MainMenu() {

		// UAC.login returns user, hash --> stored in loggedInUserCredentials
		// in constructor as it needs to be run to produce the correct menu every time
		JSONObject loggedInUserAccount = UserAccountControl.login();

		String[] universalActions = new String[]{ "switch user", "exit"};

		List<Object> actionsList = loggedInUserAccount.getJSONArray("privileges").toList();
		actionsList.addAll(List.of(universalActions)); // where the universalActions elements will be
		// convert to array
		actions = Arrays.stream(actionsList.toArray()).toArray(String[]::new);
		// delete/modify book --> book name --> search for book() --> display book info() --> is this the book you'd like to delete/modify?
	}

	public void display() {
		System.out.println("\n====    M A I N        M E N U    ====\nThis is a uni-user system. Possible actions for your user: ");
		for (int i = 1; i <= actions.length; i++) {
			System.out.println(i + ": " + actions[i-1]);
		}
	}

	public void getAndExecuteAction() {
		System.out.println("Choose an action by typing its number:");
		Scanner scanner = new Scanner(System.in);
		try {
			execute(actions[scanner.nextInt()-1]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | InputMismatchException e) {
			System.out.println("An error occurred; please type a number between 1 and " + actions.length);
			getAndExecuteAction();
		}
	}

	private void exit() {
		this.exited = true;
	}

	// pv switch user() {}

	private void test() {
		System.out.println("test function");
	}

	private void execute(String action) {
		System.out.println(action);
		// TODO: Implement all these functions (in the BookUtils class)
		switch (action.toLowerCase()) {
			case "add book" -> addBook();
			case "delete book" -> deleteBook();
			case "modify book" -> modifyBook();
			case "view books" -> viewBooks();
			case "add user" -> addUser();
			case "delete user" -> deleteUser();
			case "switch user" -> test();
			case "exit" -> exit();
		}
	}
}
