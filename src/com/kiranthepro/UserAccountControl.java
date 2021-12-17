package com.kiranthepro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static com.kiranthepro.BookUtils.initialiseFile;
import static com.kiranthepro.Main.*;

public class UserAccountControl {

	public static JSONObject login() {

		System.out.println("Enter your credentials: ");

		String username = getInputNNL("Username: ");
		String password = getInputNNL("Password: ");

		HashMap<String, String> creds = new HashMap<>();
		creds.put("user", username);
		creds.put("password", password);


		if (checkCredentials(creds)) {

			return getAccount(username);
		} else {
			System.out.println("Incorrect credentials - try again:");
			return login();
		}
				// anonymous function for recursive calling?
	}

	public static boolean checkCredentials(HashMap<String, String> credentials) {
		ArrayList<JSONObject> accounts = getAllAccountInfo("users.json");

		assert accounts != null;
		for (JSONObject account: accounts) {

			if ( ((String) account.get("user")).equalsIgnoreCase(credentials.get("user")) ) {
				if (((String) account.get("password")).equals( getHash(credentials.get("password")) )) {
					return true;
				}
			}
		}
		return false;
	}

	public static ArrayList<JSONObject> getAllAccountInfo(String accountsFilePath) {
		try {
			File accountsFile = getFile(accountsFilePath);
			String content = new Scanner(accountsFile).useDelimiter("\\Z").next();


			JSONArray accountsArray = new JSONArray(content);
			ArrayList<JSONObject> accountsArrayList = new ArrayList<>();

			for (Object account: accountsArray) {
				accountsArrayList.add((JSONObject) account);
			}

			return accountsArrayList;
		} catch (FileNotFoundException e) {
			return null;
		}

	}

	public static JSONObject getAccount(String user) {
		for (JSONObject account:getAllAccountInfo("users.json")) {
			if (((String) account.get("user")).equalsIgnoreCase(user)) {
				return account;
			}
		}
		return null;
	}

	public static void addUser() {

		JSONObject account = new JSONObject("""
				{
					"user": "",
					"password": "",
					"administrator": false,
					"privileges": [
				        "Add book",
						"view books"
					]
				}""");

		System.out.println("Create new user - enter the relevant information: ");
		String username = getInputNNL("Username: ");
		String password = getHash(getInputNNL("Password: "));
		Boolean administrator = getYesNo("Administrator? y/n");

		account.put("user", username);
		account.put("password", password);
		account.put("administrator", administrator);

		if (administrator) {
			account.put("privileges", new String[] {"add book",	"delete book", "modify book", "view books", "add user", "delete user"});
		}

		ArrayList<JSONObject> accountsArray = getAllAccountInfo("users.json");
		assert accountsArray != null;
		accountsArray.add(account);

		FileWriter writer = getFileWriter(initialiseFile("users.json"), false);

		try {
			assert writer != null;
			writer.write(accountsArray.toString());
			writer.flush();
			writer.close();
			System.out.println("New user " + username + " created successfully.");
		} catch (IOException e) {
			System.out.println("Something went wrong writing the new user to disk. User not added.");
		}

	}

	public static void deleteUser() {

	}

	public static String[] getUserPrivileges(String user) {
		return new String[0];
	}

	public static File getFile(String filePath) {
		return new File(filePath);
	}

	private static String getHash(String value) {
		try {
			MessageDigest digester = MessageDigest.getInstance("SHA-256");
			digester.update(value.getBytes());

			return bytesToHex(digester.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

}

