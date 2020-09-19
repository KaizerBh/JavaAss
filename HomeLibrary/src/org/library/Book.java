/**
 * 
 */
package org.library;

import java.io.Serializable;

/**
 * @author Reza A basic class of Book Object that has multiple attributes.
 */
public class Book implements Serializable {
	/**
	 * Book Object Attributes
	 */
	private static final long serialVersionUID = 3039751084860280227L;
	private String title;
	private String author;
	private int publication;
	private long isbnNumber;

	public Book(String title, String author, int publication, long isbnNumber) {
		super();
		this.title = title;
		this.author = author;
		this.publication = publication;
		this.isbnNumber = isbnNumber;
	}

	/**
	 * 
	 * @return Title of the book
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title Sets the Title of the Book.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return Author of the Book
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 
	 * @param author Sets the Author of the Book.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 
	 * @return Year of Book publication
	 */
	public int getPublication() {
		return publication;
	}

	/**
	 * 
	 * @param publication Sets the publication year or assign it.
	 */
	public void setPublication(int publication) {
		this.publication = publication;
	}

	/**
	 * 
	 * @return Unique Number of ISBN
	 */
	public long getIsbnNumber() {
		return isbnNumber;
	}

	/**
	 * 
	 * @param isbnNumber Sets the Unique ISBN Number
	 * @throws ISBNFormatError
	 */
	public void setIsbnNumber(int isbnNumber) {

		this.isbnNumber = isbnNumber;

	}

	/**
	 * Override the toString method that way it displays the object attributes
	 * instead of the memory location.
	 */
	@Override
	public String toString() {
		return "Title=" + title + "\nAuthor=" + author + "\nYear=" + publication + "\nISBN=" + isbnNumber;
	}

}
