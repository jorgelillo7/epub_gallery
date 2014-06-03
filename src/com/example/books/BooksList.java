package com.example.books;

import java.io.Serializable;
import java.util.Arrays;

import models.Book;
import util.CustomGrid;
import util.DataManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class BooksList extends Activity {
	GridView grid;
	String[] bookList;
	int[] imageArray;
	DataManager dm = DataManager.getInstance();
	View mText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_screen);
		DataManager dm = DataManager.getInstance();
  
		bookList = new String[dm.userEpubBooks.size()];
		for (int i = 0; i < dm.userEpubBooks.size(); i++) {
			Book aux = (Book) dm.userEpubBooks.get(i);
			bookList[i] = aux.getBookName() + " - " + aux.getCreationDate();
		}
		
		//mock for all the books 
		imageArray = new int[dm.userEpubBooks.size()];
		for (int i = 0; i < dm.userEpubBooks.size(); i++) {
			imageArray[i] = R.drawable.epub_mock;
		}

		CustomGrid adapter = new CustomGrid(BooksList.this, bookList,
				imageArray);
		grid = (GridView) findViewById(R.id.grid);
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			//	Toast.makeText(BooksList.this,"You Clicked at " + bookList[+position], Toast.LENGTH_SHORT).show();
				
				DataManager dm = DataManager.getInstance();
				Intent intent = new Intent(getBaseContext(), DetailActivity.class);
				Bundle b = new Bundle();
				b.putSerializable("bookSelected", (Serializable) dm.userEpubBooks.get(position)); //Your id
				intent.putExtras(b); //Put your id to your next Intent
				startActivity(intent);
				finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_settings:
			closeSession();
			return true;
		case R.id.titleOrder:
			orderByTitle();
			return true;

		case R.id.dateOrder:
			orderByDate();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void closeSession() {
		/*
		 * SharedPreferences preferences = PreferenceManager
		 * .getDefaultSharedPreferences(getApplicationContext());
		 * 
		 * preferences.edit().remove("MyDropboxSessionSave").commit();
		 * 
		 * DataManager dm = DataManager.getInstance(); dm.userBooks.clear();
		 * dm.userEpubBooks.clear(); dm.isLogged = false; dm.comeFromList =
		 * true;
		 * 
		 * Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
		 * startActivity(myIntent);
		 */

	}

	public void orderByTitle() {
		Arrays.fill(bookList, null);
		dm.orderBooksByTitle();

		bookList = new String[dm.userEpubBooks.size()];
		for (int i = 0; i < dm.userEpubBooks.size(); i++) {
			Book aux = (Book) dm.userEpubBooks.get(i);
			bookList[i] = aux.getBookName() + " - " + aux.getCreationDate();
		}

		CustomGrid adapter = new CustomGrid(BooksList.this, bookList,
				imageArray);
		grid = (GridView) findViewById(R.id.grid);
		grid.setAdapter(adapter);

	}

	public void orderByDate() {
		Arrays.fill(bookList, null);
		dm.orderBooksByDate();

		bookList = new String[dm.userEpubBooks.size()];
		for (int i = 0; i < dm.userEpubBooks.size(); i++) {
			Book aux = (Book) dm.userEpubBooks.get(i);
			bookList[i] = aux.getBookName() + " - " + aux.getCreationDate();
		}

		CustomGrid adapter = new CustomGrid(BooksList.this, bookList,
				imageArray);
		grid = (GridView) findViewById(R.id.grid);
		grid.setAdapter(adapter);

	}

	@Override
	public void onBackPressed() {
		// do something on back.
		DataManager dm = DataManager.getInstance();
		dm.comeFromList = true;
		return;

	}

}