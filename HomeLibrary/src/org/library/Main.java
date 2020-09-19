/**
 * 
 */
package org.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Reza
 *
 */
public class Main {

	/**
	 * @param args
	 * 
	 */

	private static Library library;
	private static Scanner input;
	private static String menu;
	private static String deleteSubMenu;
	private static String searchSubMenu;

	/**
	 * 
	 * @return Main Menu which is passed into toString.
	 */
	public static String getMainMenu() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n## Welcome to the Home Library System ##\r\n")
				.append("Please Enter The following Options:\r\n").append("1. Add a new book\r\n")
				.append("2. Delete a book\r\n").append("3. Search for a book\r\n").append("4. Display all books\r\n")
				.append("5. Exit\r\n").append("Choice: ");
		return stringBuilder.toString();

	}

	/**
	 * 
	 * @return Delete Sub Menu which is passed into toString.
	 */
	public static String getDeleteSubMenu() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n## Search a book for deletion by ##\r\n").append("1. Title\r\n")
				.append("2. Author\r\n").append("3. Year of publication\r\n").append("4. ISBN number\r\n")
				.append("Choice: ");
		return stringBuilder.toString();

	}

	/**
	 * 
	 * @return Search Menu which is passed into toString.
	 */
	public static String getSearchSubMenu() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n## Search for a book by ##\r\n").append("1. Title\r\n").append("2. Author\r\n")
				.append("3. Year of publication\r\n").append("4. ISBN number\r\n").append("Choice: ");
		return stringBuilder.toString();

	}

	public static void main(String[] args) {
		try {
			input = new Scanner(System.in);
			library = Library.getInstance();

			menu = getMainMenu();
			deleteSubMenu = getDeleteSubMenu();
			searchSubMenu = getSearchSubMenu();

			boolean isClosedRequested = false;

			while (!isClosedRequested) {
				String userChoice = input(menu);

				if (userChoice.length() == 1 && isNumeric(userChoice)) {

					switch (userChoice) {
					case "1":
						addBook();

						break;

					case "2":
						String userDeleteChoice = input(deleteSubMenu);
						if (userDeleteChoice.length() == 1 && isNumeric(userDeleteChoice)) {
							switch (userDeleteChoice) {
							case "1":

								library.removeBookByTitle();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;

							case "2":

								library.removeBookByAuthor();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;

							case "3":

								library.removeBookByYear();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;

							case "4":

								library.removeBookByISBN();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;
							}
						} else {
							output("Please Make sure the choice is in numeric and correct value");
							userDeleteChoice = input(deleteSubMenu);
						}
						break;

					case "3":
						String userSearchChoice = input(searchSubMenu);
						if (userSearchChoice.length() == 1 && isNumeric(userSearchChoice)) {
							switch (userSearchChoice) {
							case "1":

								library.searchBookByTitle();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;

							case "2":

								library.searchBookByAuthor();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;

							case "3":
								library.searchBookByYear();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;

							case "4":
								library.searchBookByISBN();

								if (goBackMainMenu()) {
									break;
								} else {
									isClosedRequested = true;
								}
								break;
							}
						} else {
							output("Please Make sure the choice is in numeric and correct value");
							userSearchChoice = input(searchSubMenu);
						}
						break;

					case "4":
						listBooks();
						break;

					case "5":
						isClosedRequested = true;
						break;

					default:
						output("\nInvalid option\n");
						break;
					}

					Library.save();
				} else {
					output("\nPlease Make sure you are Entering numeric values.\n");
				}

			}

		} catch (RuntimeException e) {
			output(e);
		} catch (ISBNFormatError e) {
			output(e);
		}
		output("\nEnded\n");
	}

	/**
	 * Add Book function that will be used within switch statement basically adds
	 * the book into object.
	 * 
	 * @throws ISBNFormatError Custom error class
	 */
	private static void addBook() throws ISBNFormatError {
		boolean titleCheck = false;
		boolean authorCheck = false;
		boolean yearCheck = false;
		boolean isbnNumberCheck = false;

		String title = null;
		String author = null;
		String year = null;
		String isbnNumber = null;

		while (!titleCheck) {
			title = input("Title of the book: ");
			if (!title.isBlank() && !title.isEmpty()) {
				titleCheck = true;
			} else {
				System.err.println("Title cannot be blank or contains white spaces only");
				System.out.println();
			}
		}

		while (!authorCheck) {
			author = input("Author of the book: ");
			if (!author.isBlank() && !author.isEmpty()) {
				authorCheck = true;
			} else {
				System.err.println("Author cannot be blank or contains white spaces only");
				System.out.println();
			}
		}

		while (!yearCheck) {
			year = input("Year of publication: ");
			if (year.length() == 4 && isNumeric(year)) {
				yearCheck = true;
			} else {
				System.err.print("Year Cannot be less than 4 digit and only Numeric values accepted.\n");
			}
		}

		while (!isbnNumberCheck) {
			isbnNumber = input("ISBN number: ");
			if (isbnNumber.length() != 10) {
				System.err.println("ISBN Number must be 10 digit long and unique");
			} else if (!isNumeric(isbnNumber)) {
				System.err.println("ISBN must contain digit only and 10 digit long");
			} else if (library.checkIsbnUnique(Long.parseLong(isbnNumber))) {
				System.err.println("ISBN Must be unique the number you entered matched with the others.");
			} else {
				isbnNumberCheck = true;
			}

		}

		Book newBook = library.addBook(title, author, Integer.parseInt(year), Long.parseLong(isbnNumber));
		output("\n" + newBook + "\n");
		output("Sucess");
	}

	/**
	 * List Book iterates over the catalog and print them.
	 */
	private static void listBooks() {
		List<Book> booksInLibrary = library.sortBook((ArrayList<Book>) library.listBooks());
		output("");
		for (Book book : booksInLibrary) {
			output(book + "\n");
		}

	}

	/**
	 * Go Back to Main menu which asks the user if they would like to back to main
	 * menu.
	 * 
	 * @return Boolean
	 */
	private static boolean goBackMainMenu() {
		String goBackToMainMenu = input("Would you like to go back to main menu (y/n): ");
		boolean checkIfCorrectVal = false;
		while (!checkIfCorrectVal) {
			if (goBackToMainMenu.equals("y") || goBackToMainMenu.equals("Y")) {
				checkIfCorrectVal = true;
				return true;
			} else if (goBackToMainMenu.equals("n") || goBackToMainMenu.equals("N")) {
				checkIfCorrectVal = true;
				return false;
			} else {
				output("Please Make sure you enter the correct value Y/N ");
				goBackToMainMenu = input("Would you like to go back to main menu (y/n): ");
			}
		}
		return true;

	}

	/**
	 * 
	 * @param prompt the user for answer.
	 * @return accepts or takes back the answer.
	 */
	private static String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}

	/**
	 * 
	 * @param message Change the output into object.
	 */
	private static void output(Object message) {

		System.out.println(message);
	}

	/**
	 * 
	 * A basic matching or error checking if the input from the user is numeric or
	 * numbers only for the ISBN.
	 * 
	 * @param str String of number
	 * @return The matched item using regex
	 */
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

}
