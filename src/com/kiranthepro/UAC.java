package com.kiranthepro;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.Scanner;

public class UAC {

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

	public static void addUser() {

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

	private static String bytesToHex(byte[] bytes) {
		// helper for getHash()
		return HexFormat.of().formatHex(bytes);
	}
}

