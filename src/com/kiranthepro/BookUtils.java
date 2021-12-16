package com.kiranthepro;

import de.vandermeer.asciitable.AsciiTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.stream.Stream;

import static com.kiranthepro.Main.getFileWriter;
import static com.kiranthepro.Main.getInput;

public class BookUtils {

	private static final String[] bookAttributes = {"title", "ISBN", "author", "genre", "year", "page count", "ISO language code", "cover type"};

	private static File initialiseFile(String fileName) {
		File fileObj = new File(fileName);

		try {
			if (fileObj.createNewFile()) {
				// write header row if new file has to be created
				FileWriter writer = getFileWriter(fileObj, false);
				assert writer != null;
				writer.write(String.join(",", bookAttributes) + "\n");
				writer.flush();
				writer.close();
				System.out.println("File " + fileObj.getName() + " created successfully.");
			}
		} catch (IOException e) {
			System.out.println("Something went wrong creating the file :(");
			e.printStackTrace();
		}

		return fileObj;
	}


	public static void viewBooks() {

		AsciiTable table = new AsciiTable();

		Stream<String> bookData = getBookData("books.csv").skip(1);

		if (bookData != null) {
			table.addRule();
			table.addRow(String.join(",", bookAttributes).toUpperCase().split(","));
			table.addRule();

			bookData.forEachOrdered((String line) -> {
				table.addRow(line.split(","));
				table.addRule();
			});

			table.setPaddingLeft(5);
			System.out.println(table.render(300));

		} else {
			System.out.println("Error reading book data :(");
		}
	}

	private static Stream<String> getBookData(String filePath) {
		try {
			return Files.lines(Paths.get(filePath));
		} catch (IOException e) {
			return null;
		}
	}

	public static void addBook() {
		File libraryFile = initialiseFile("books.csv");

		boolean done = false;
		while (!done) {
			writeToFile(libraryFile, getBookInfo());
			if (getInput("Would you like to add another book?").toLowerCase().contains("n")) {
				done = true;
			}
		}
	}


	private static LinkedHashMap<String, String> getBookInfo() {
		LinkedHashMap<String, String> bookInfo = new LinkedHashMap<>();

		for (String attribute: bookAttributes) {
			getAndPutBookAttribute(attribute, bookInfo);
		}

		return bookInfo;
	}

	private static void getAndPutBookAttribute(String attribute, LinkedHashMap<String, String> bookInfo) {
		String input = getInput("Type in the " + attribute + " of the book:");

		if (validateAttributeInput(attribute, input)) {
			bookInfo.put(attribute, input);
		} else {
			getAndPutBookAttribute(attribute, bookInfo);
		}
	}

	private static boolean validateAttributeInput(String attribute, String input) {
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
			System.out.println(errorMessage);
			return false;
		} else {
			return true;
		}

	}


	private static void writeToFile(File file, LinkedHashMap<String, String> data) {
		String[] values = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			values[i] = (String) data.values().toArray()[i];
		}
		try {
			FileWriter writer = getFileWriter(file, true);
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


}
