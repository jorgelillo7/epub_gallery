package util;

import java.util.ArrayList;

import models.Book;
import android.util.Log;


public class DataManager {

	private static DataManager instance = null;
	public ArrayList<Book> userBooks;

	
	DataManager() {      
	}	
	
	public static DataManager getInstance() {
		if (instance == null) {
			if (instance == null) {
				instance = new DataManager ();
			}
		}
		return instance;
	}

	public ArrayList<Book> getBooks() {
		return userBooks;
	}

	public void setBooksArray(ArrayList<Book> books) {
		this.userBooks = books;
	}
	
	public void addBook(Book b) {
		this.userBooks.add(b);
	}
	
	
	//TODO get all epubs

/*	public ArrayList<Book> getBooksEpub() {
		
		return userBooks;
	}
	*/
	
}