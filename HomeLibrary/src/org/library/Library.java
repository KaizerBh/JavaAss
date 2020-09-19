/**
 * 
 */
package org.library;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Basic Class of Library that will hold the books from the Book class.
 * 
 * @author Reza
 *
 */
public class Library implements Serializable {

	private static final long serialVersionUID = 4112025685363587808L;
	private static final String LIBRARY_FILE = "library.obj";

	private static Library self;
	private static Scanner input;

	private Map<Long, Book> catalog;

	private Library() {
		catalog = new HashMap<>();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the instance of the library
	 * 
	 * @return the Library instance if it exists, if not, returns new
	 */
	public static synchronized Library getInstance() {
		if (self == null) {
			Path PATH = Paths.get(LIBRARY_FILE);
			if (Files.exists(PATH)) {
				try {
					FileInputStream fileInput = new FileInputStream(LIBRARY_FILE);
					ObjectInputStream libraryFile = new ObjectInputStream(fileInput);

					self = (Library) libraryFile.readObject();

					libraryFile.close();
					fileInput.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				self = new Library();
			}
		}
		return self;
	}

	/**
	 * Save Library files to library object
	 */
	public static synchronized void save() {
		if (self != null) {
			try {
				FileOutputStream fileOut = new FileOutputStream(LIBRARY_FILE);
				ObjectOutputStream libraryFile = new ObjectOutputStream(fileOut);

				libraryFile.writeObject(self);
				libraryFile.flush();
				libraryFile.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * This function get's the book from the catalog (Hash Map) Then returns it into
	 * an ArrayList, Basically listing the books.
	 * 
	 * @return ArrayList of Book Object
	 */
	public List<Book> listBooks() {
		Collection<Book> bookValues = catalog.values();
		return new ArrayList<Book>(bookValues);
	}

	/**
	 * 
	 * @param booksInLibrary list of Book
	 * @return Sorted list of book by title, author, and year.
	 */
	public List<Book> sortBook(List<Book> booksInLibrary) {
		Collections.sort(booksInLibrary, Comparator.comparing(Book::getTitle).thenComparing(Book::getAuthor)
				.thenComparing(Book::getPublication));
		return booksInLibrary;
	}

	/**
	 * 
	 * @param title      Title of the book that will be added into the catalog or
	 *                   shelves
	 * @param author     Author of the book object that will be added into the
	 *                   catalog
	 * @param year       Publication Year of the book
	 * @param isbnNumber Unique 9 digit ISBN number which will be used as a key in
	 *                   the catalog
	 * @return the newbook that is added.
	 */
	public Book addBook(String title, String author, int year, long isbnNumber) {
		Book newBook = new Book(title, author, year, isbnNumber);
		long bookId = newBook.getIsbnNumber();
		catalog.put(bookId, newBook);

		return newBook;
	}

	/**
	 * Returns a book as specified by a book ISBN number
	 * 
	 * @param integer - Integer specific to the book
	 * @return the book instance
	 */
	public Book getBookByIsbn(Long integer) {
		if (catalog.containsKey(integer)) {
			return catalog.get(integer);
		}
		return null;
	}

	/**
	 * 
	 * Searches the list of books in an array list then goes through the hash map to
	 * remove the book object.
	 *
	 */
	public void removeBookByTitle() {
		input = new Scanner(System.in);
		List<Book> booksInLibrary = listBooks();
		ArrayList<Long> tempBook = new ArrayList<Long>();
		String titleOfBook = input("Enter The Title of the Book that you wish to remove: ");
		boolean titleCheck = false;
		while (!titleCheck) {
			if (!titleOfBook.isBlank() && !titleOfBook.isEmpty()) {
				titleCheck = true;
			} else {
				System.err.println("Title cannot be blank or contains white spaces only");
				System.out.println();
				titleOfBook = input("Enter The Title of the Book that you wish to remove: ");
			}
		}

		for (Book book : booksInLibrary) {
			if (book.getTitle() != null && book.getTitle().contains(titleOfBook)) {
				tempBook.add(book.getIsbnNumber());
			}
		}
		sortBook(booksInLibrary);

		for (Long integer : tempBook) {
			System.out.println();
			System.out.println(getBookByIsbn(integer));
			System.out.println();
		}

		if (tempBook.size() > 0) {
			System.out.println("Removing the Book\n");
			catalog.remove(tempBook.get(0));
			System.out.println("Book removed succesfully.\n");
		} else {
			System.err.println("No Book Found By That Title.");
		}
	}

	/**
	 * Searched the list of the book by Author then if it matches it will remove it
	 * from the catalog completely.
	 *
	 */
	public void removeBookByAuthor() {
		input = new Scanner(System.in);
		List<Book> booksInLibrary = listBooks();
		int originalBookSize = booksInLibrary.size();

		boolean authorCheck = false;
		String author = input("Enter The Author of the Book that you wish to remove: ");
		while (!authorCheck) {
			if (!author.isBlank() && !author.isEmpty()) {
				authorCheck = true;
			} else {
				System.err.println("Author cannot be blank or contains white spaces only");
				System.out.println();
				author = input("Enter The Author of the Book that you wish to remove: ");
			}
		}

		ArrayList<Long> tempISBN = new ArrayList<Long>();

		for (Book book : booksInLibrary) {
			if (book.getAuthor() != null && book.getAuthor().contains(author)) {
				long number = book.getIsbnNumber();
				tempISBN.add(number);
			}
		}
		sortBook(booksInLibrary);
		for (Long index : tempISBN) {
			System.out.println();
			System.out.println(getBookByIsbn(index));
			System.out.println();
		}
		System.out.println("Found " + tempISBN.size() + " Book By The Author:  " + author);
		for (Long integer : tempISBN) {
			catalog.remove(integer);
		}
		int addOriginalWithNew = booksInLibrary.size() - tempISBN.size();

		if (addOriginalWithNew < originalBookSize) {
			System.out.println("Success Book removed.\n");
		} else {
			System.out.println("Failed to remove the book\n");
		}
	}

	/**
	 * Remove Book by year.
	 *
	 */
	public void removeBookByYear() {
		input = new Scanner(System.in);
		List<Book> booksInLibrary = listBooks();
		int originalBookSize = booksInLibrary.size();
		String year = input("Enter The Year of the Book that you wish to remove: ");
		boolean yearCheck = false;

		while (!yearCheck) {
			if (year.length() == 4 && Main.isNumeric(year)) {
				yearCheck = true;
			} else {
				System.err.print("Year Cannot be less than 4 digit and only Numeric values accepted.\n");
				year = input("Enter The Year of the Book that you wish to remove: ");
			}
		}

		int converString = Integer.parseInt(year);
		ArrayList<Long> tempISBN = new ArrayList<Long>();

		for (Book book : booksInLibrary) {
			if (book.getPublication() == converString) {
				Long number = book.getIsbnNumber();
				tempISBN.add(number);
			}
		}

		sortBook(booksInLibrary);
		System.out.println("Found " + tempISBN.size() + " Book By The Date of Publication:  " + year);
		for (Long integers : tempISBN) {
			System.out.println();
			System.out.println(getBookByIsbn(integers));
			System.out.println();
		}

		for (Long integer : tempISBN) {
			catalog.remove(integer);
		}
		int addOriginalWithNew = booksInLibrary.size() - tempISBN.size();

		if (addOriginalWithNew < originalBookSize) {
			System.out.println("Success Book removed.\n");
		} else {
			System.out.println("Failed to remove the book\n");
		}
	}

	/**
	 * Remove book ByISBN Pretty straightforward.
	 *
	 */
	public void removeBookByISBN() {
		input = new Scanner(System.in);
		boolean isbnCheck = false;
		Long isbn = Long.parseLong(input("Enter The ISBN of the Book that you wish to remove: "));
		while (!isbnCheck) {
			try {
				if (String.valueOf(isbn).length() > 10 || String.valueOf(isbn).length() != 10) {
					System.err.println((new ISBNFormatError("The length of ISBN number must be 10 digit long")));
					isbn = Long.parseLong(input("Enter The ISBN of the Book that you wish to remove: "));
				} else {
					isbnCheck = true;
				}
			} catch (NumberFormatException nfe) {
				System.err.println("It must contain only numbers and 10 digit long.");
			}

		}

		if (getBookByIsbn(isbn) != null) {
			System.out.println("Found Book\n");
			System.out.println(getBookByIsbn(isbn));
			System.out.println("\nRemoving\n");
			catalog.remove(isbn);
			System.out.println("Successfully removed.\n");
		} else {
			System.err.println("\nNo matching found, Perhaps typo in ISBN?\n");
		}

	}

	/**
	 * Searches the book By matching title or sequence of character.
	 */
	public void searchBookByTitle() {
		input = new Scanner(System.in);
		List<Book> booksInLibrary = listBooks();
		ArrayList<Long> tempBook = new ArrayList<Long>();
		String titleOfBook = input("Enter The Title of the Book that you wish to search: ");
		boolean titleCheck = false;
		while (!titleCheck) {
			if (!titleOfBook.isBlank() && !titleOfBook.isEmpty()) {
				titleCheck = true;
			} else {
				System.err.println("Title cannot be blank or contains white spaces only");
				System.out.println();
				titleOfBook = input("Enter The Title of the Book that you wish to search: ");
			}
		}

		for (Book book : booksInLibrary) {
			if (book.getTitle() != null && book.getTitle().contains(titleOfBook)) {
				tempBook.add(book.getIsbnNumber());
			}
		}
		if (tempBook.size() > 0) {
			System.out.println("Found Book");
			Book b = getBookByIsbn(tempBook.get(0));
			System.out.println(b);
		} else {
			System.err.println("No Book Found By That Title.");
		}
	}

	/**
	 * Searches the book By matching Author or sequence of character.
	 */
	public void searchBookByAuthor() {
		input = new Scanner(System.in);
		List<Book> booksInLibrary = listBooks();
		boolean authorCheck = false;
		String author = input("Enter The Author of the Book that you wish to search: ");
		while (!authorCheck) {
			if (!author.isBlank() && !author.isEmpty()) {
				authorCheck = true;
			} else {
				System.err.println("Author cannot be blank or contains white spaces only");
				System.out.println();
				author = input("Enter The Author of the Book that you wish to search: ");
			}
		}
		ArrayList<Long> tempISBN = new ArrayList<Long>();

		for (Book book : booksInLibrary) {
			if (book.getAuthor() != null && book.getAuthor().contains(author)) {
				long number = book.getIsbnNumber();
				tempISBN.add(number);
			}
		}
		System.out.println("Found " + tempISBN.size() + " Book By The Author:  " + author);
		sortBook(booksInLibrary);
		sortBook(booksInLibrary);
		for (Long integer : tempISBN) {
			System.out.println();
			System.out.println(getBookByIsbn(integer));
			System.out.println();
		}
	}

	/**
	 * Searches the book by the publication year.
	 */
	public void searchBookByYear() {
		input = new Scanner(System.in);
		List<Book> booksInLibrary = listBooks();
		String year = input("Enter The Year of the Book that you wish to search: ");
		boolean yearCheck = false;

		while (!yearCheck) {
			if (year.length() == 4 && Main.isNumeric(year)) {
				yearCheck = true;
			} else {
				System.err.print("Year Cannot be less than 4 digit and only Numeric values accepted.\n");
				year = input("Enter The Year of the Book that you wish to search: ");
			}
		}

		int converString = Integer.parseInt(year);
		ArrayList<Long> tempISBN = new ArrayList<Long>();

		for (Book book : booksInLibrary) {
			if (book.getPublication() == converString) {
				long number = book.getIsbnNumber();
				tempISBN.add(number);
			}
		}
		System.out.println("Found " + tempISBN.size() + " Book By The Date of Publication:  " + year);
		sortBook(booksInLibrary);
		for (Long integer : tempISBN) {
			System.out.println();
			System.out.println(getBookByIsbn(integer));
			System.out.println();
		}
	}

	/**
	 * Searches book by 10 digit unique ISBN number
	 */
	public void searchBookByISBN() {
		input = new Scanner(System.in);
		boolean isbnCheck = false;
		Long isbn = Long.parseLong(input("Enter The ISBN of the Book that you wish to remove: "));
		while (!isbnCheck) {
			try {
				if (String.valueOf(isbn).length() > 10 || String.valueOf(isbn).length() != 10) {
					System.err.println((new ISBNFormatError("The length of ISBN number must be 10 digit long")));
					isbn = Long.parseLong(input("Enter The ISBN of the Book that you wish to remove: "));
				} else {
					isbnCheck = true;
				}
			} catch (NumberFormatException nfe) {
				System.err.println("It must contain only numbers and 10 digit long.");
			}

		}

		if (getBookByIsbn(isbn) != null) {
			System.out.println("Found Book\n");
			System.out.println(getBookByIsbn(isbn));
		} else {
			System.err.println("\nNo matching found, Perhaps typo in ISBN?\n");
		}
	}

	/**
	 * 
	 * @param ISBN Accept number that will be checked in hash map
	 * @return boolean
	 */
	public boolean checkIsbnUnique(long ISBN) {

		if (catalog.containsKey(ISBN)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param prompt messages to ask and input to be taken.
	 * @return
	 */
	private static String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}
}
