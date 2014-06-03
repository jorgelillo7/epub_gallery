package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable{
	
	private String name;
	private String creationDate;
	private String type;
	private String size;
	
	public Book() {      
	}	
	
	public Book(String bookName, String date, String extension, String size) {
		super();
		this.name = bookName;
		this.creationDate = date;
		this.type = extension;
		this.size = size;
		 
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
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	
	
}
