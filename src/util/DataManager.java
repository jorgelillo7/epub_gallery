package util;

import java.util.ArrayList;

import models.Book;
import android.util.Log;

/*
 * Obtenemos todos los libros electronicos del usuario para futuras versiones (userBooks)
 * Para la esta primera versión usaremos la variable userEpubBooks
 * */
public class DataManager {

	private static DataManager instance = null;
	public ArrayList<Book> userBooks = new ArrayList<Book>();
	public ArrayList<Book> userEpubBooks = new ArrayList<Book>();;
	public boolean isLogged= false;
	public boolean comeFromList= false;

	
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
	
	
	public ArrayList<Book> getEpubBooks() {
		return userEpubBooks;
	}

	public void setEpubBooksArray(ArrayList<Book> books) {
		this.userEpubBooks = books;
	}
	
	public void addEpubBook(Book b) {
		this.userEpubBooks.add(b);
	}
	
	
}