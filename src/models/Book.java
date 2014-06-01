package models;

import java.util.ArrayList;

public class Book {
	
	private String name;
	private String creationDate;
	private String type;
	
	public Book() {      
	}	
	
	public Book(String bookName, String date, String extension) {
		super();
		this.name = bookName;
		this.creationDate = date;
		this.type = extension;
		 
	}
	
	public String getBookName() {
		return name;
	}

	public void setBookName(String bookname) {
		this.name = bookname;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String date) {
		this.creationDate = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String extension) {
		this.type = extension;
	}

	
	
}
