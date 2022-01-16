package com.kiranthepro;

import com.kiranthepro.objects.Book;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.kiranthepro.Main.*;

public class BookUtils {

	private static final String[] bookAttributes = {"title", "ISBN", "author", "genre", "year", "page count", "ISO language code", "cover type"};


	// first methods are 'utility methods' (therefore private) which are used by the public methods nearer the bottom of the file which are called by the MainMenu class
	static File initialiseFile(String fileName) {
		// returns a file object for a csv file and
		File fileObj = new File(fileName);

		try {
			if (fileObj.createNewFile()) {
				// write header row if new file has to be created
				FileWriter writer = getFileWriter(fileObj, false);

				assert writer != null;
				if (fileName.equals("books.csv")) {
					writer.write(String.join(",", bookAttributes) + "\n");
				} else if (fileName.equals("users.json")) {
					writer.write("[]\n");
				}

				writer.flush();
				writer.close();
				System.out.println("File " + fileObj.getName() + " created successfully.");
			}
		} catch (IOException e) {
			System.out.println("Something went wrong opening the file :(");
			e.printStackTrace();
		}

		return fileObj;
	}

	private static void displayBooks(ArrayList<Book> books, boolean displayIndexNumbers) {
		// displays only passed books
		AsciiTable table = new AsciiTable();

		table.addRule();
		if (displayIndexNumbers) {
			// the following adds an empty element to the start of bookAttributes so a column with an empty header is created
			table.addRow(String.join(",", ArrayUtils.addAll(new String[] {" "}, bookAttributes)).toUpperCase().split(","));
		} else {
			table.addRow(String.join(",", bookAttributes).toUpperCase().split(","));
		}
		table.addRule();

		books.forEach((Book book) -> {
			ArrayList<String> lineComps = new ArrayList<>();
			if (displayIndexNumbers) {
				lineComps.add(String.valueOf(books.indexOf(book)+1));
				lineComps.addAll(book.getAttributeArrayList());
			} else {
				lineComps.addAll(book.getAttributeArrayList());
			}
			table.addRow(lineComps);
			table.addRule();
		});

		table.setPaddingLeft(5);
		System.out.println(table.render(300));
	}

	public static void viewBooks() {
		// displays all stored book data in a user-friendly table

		AsciiTable table = new AsciiTable();

		ArrayList<Book> books = getBookData("books.csv");

		if (books != null) {
			table.addRule();
			table.addRow(String.join(",", bookAttributes).toUpperCase().split(","));
			table.addRule();

			books.forEach(book -> {
				table.addRow(book.getAttributeArrayList());
				table.addRule();
			});

			table.setPaddingLeft(5);
			System.out.println(table.render(300));

		} else {
			System.out.println("Error reading book data :(");
		}
	}

	public static void displayBookAttributes(Book book, boolean displayIndexNumbers) {
		ArrayList<String> attributes = book.getAttributeArrayList(); // in order

		AsciiTable table = new AsciiTable();
		table.addRule();

		for (int i=0; i< attributes.size(); i++) {
			if (displayIndexNumbers) table.addRow(String.valueOf(i+1), bookAttributes[i], attributes.get(i)); else table.addRow(bookAttributes[i], attributes.get(i));
			table.addRule();
		}
		table.setPaddingLeft(5);
		System.out.println(table.render(100));
	}

	public static ArrayList<Book> searchBooks(String query) {
		// returns the book object(s) in which the provided string appears in any of their properties
		// yes this is a linear search
		ArrayList<Book> bookData = getBookData("books.csv");
		ArrayList<Book> matchedBooks = new ArrayList<>();

		assert bookData != null;
		bookData.forEach(book -> {
			if (book.toString().toLowerCase().contains(query.toLowerCase())) {
				matchedBooks.add(book);
			}
		});

		return matchedBooks;
	}

	public static Book selectBook(String query) {
		ArrayList<Book> possibleBooks = searchBooks(query);
		try {
			displayBooks(possibleBooks, true);
			int choice = Integer.parseInt(getInput("Type the number of the book you want to modify: "))-1;

			ArrayList<Book> bookArrayList = new ArrayList<>();
			bookArrayList.add(possibleBooks.get(choice));
			displayBooks(bookArrayList, false);
			if (getYesNo("Is the above the correct book to modify?")) {
				return possibleBooks.get(choice);
			} else {
				System.out.println("Okay, try again: ");
				return selectBook(query);
			}
		}  catch (NumberFormatException | ArrayIndexOutOfBoundsException | InputMismatchException e) {
			System.out.println("An error occurred; please type a number between 1 and " + possibleBooks.size());
			return selectBook(query);
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
		// performs different checks depending on attribute type
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

	private static ArrayList<Book> getBookData(String filePath) {
		try {
			ArrayList<com.kiranthepro.objects.Book> booksArrayList = new ArrayList<>();
			Files.lines(Paths.get(filePath)).skip(1).forEachOrdered(line -> {
				booksArrayList.add(new Book(line));
			});
			return booksArrayList;
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

	public static void deleteBook() {
		ArrayList<Book> books = getBookData("books.csv");
		Book selectedBook = selectBook(getInput("Search for a book you'd like to delete: "));
		ArrayList<String> bookLinesArray = new ArrayList<>();

		assert books != null;
		ArrayList<Book> booksToRemove = new ArrayList<>();
		books.forEach(book -> {
			if (book == selectedBook) {
				booksToRemove.add(book); // not strictly necessary
			} else {
				bookLinesArray.add(book.toString());
			}
		});
		books.removeAll(booksToRemove);

		try {
			FileWriter writer = getFileWriter(initialiseFile("books.csv"), false);
			assert writer != null;
			writer.write(String.join(",", bookAttributes) + "\n");
			writer.write(String.join("\n", bookLinesArray));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing to file: Book not deleted.");
		}
	}

	public static void modifyBook() {
		ArrayList<Book> books = getBookData("books.csv");
		Book selectedBook = selectBook(getInput("Search for a book you'd like to modify: "));
		ArrayList<String> bookLinesArray = new ArrayList<>();

		assert books != null;
		for (Book book : books) {
			if (book.toString().equals(selectedBook.toString())) {

				boolean done = false;
				while (!done) {
					try {
						displayBookAttributes(book, true);
						int choice = Integer.parseInt(getInput("Type the number of the attribute you want to modify: ")) - 1;
						Book modifiedBook = book;
						String newValue = getInputNNL("Type what you would like to replace the " + bookAttributes[choice] + " of this book with: ");

						while (!validateAttributeInput(bookAttributes[choice], newValue)) {
							System.out.println("You typed an invalid value. Try again:");
							newValue = getInputNNL("Type what you would like to replace the " + bookAttributes[choice] + " of this book with: ");
						}

						switch (bookAttributes[choice]) {
							case "title" -> modifiedBook.setTitle(newValue);
							case "ISBN" -> modifiedBook.setISBN(newValue);
							case "author" -> modifiedBook.setAuthor(newValue);
							case "genre" -> modifiedBook.setGenre(newValue);
							case "year" -> modifiedBook.setYear(Integer.parseInt(newValue));
							case "page count" -> modifiedBook.setPageCount(Integer.parseInt(newValue));
							case "ISO language code" -> modifiedBook.setLanguage(newValue);
							case "cover type" -> modifiedBook.setCoverType(newValue);
						}
						bookLinesArray.add(modifiedBook.toString());
						System.out.println("Successfully replaced " + bookAttributes[choice] + " with " + newValue);

						done = true;

					} catch (NumberFormatException | ArrayIndexOutOfBoundsException | InputMismatchException e) {
						System.out.println("An error occurred; please type a number between 1 and " + bookAttributes.length);
					}
				}

			} else {
				bookLinesArray.add(book.toString());
			}
		}

		try {
			FileWriter writer = getFileWriter(initialiseFile("books.csv"), false);
			assert writer != null;
			writer.write(String.join(",", bookAttributes) + "\n");
			writer.write(String.join("\n", bookLinesArray));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing to file: Book not modified.");
		}
	}

}
