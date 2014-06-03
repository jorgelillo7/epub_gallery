package com.example.books;

import models.Book;
import util.DataManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	TextView mName, mDate, mFormat, mSize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_screen); 
		
		mName = (TextView) findViewById(R.id.tName);
		mDate = (TextView) findViewById(R.id.tDate);
		mFormat = (TextView) findViewById(R.id.tFormat);
		mSize = (TextView) findViewById(R.id.tSize);
		
		Bundle b = getIntent().getExtras();
		Book bookSelected = (Book) b.getSerializable("bookSelected");
		
		mName.setText(bookSelected.getBookName());
		mDate.setText(bookSelected.getCreationDate());
		mFormat.setText(bookSelected.getType());
		mSize.setText(bookSelected.getSize());
		
		
	}
	
	@Override
	public void onBackPressed() {
		// do something on back.
		Intent myIntent = new Intent(getBaseContext(), BooksList.class);
		startActivity(myIntent);
		
		return;

	}
	
}