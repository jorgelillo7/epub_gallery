package com.jlillo.epubgallery.controllers;

import java.io.Serializable;
import java.util.Arrays;

import com.jlillo.epubgallery.MainActivity;
import com.jlillo.epubgallery.R;
import com.jlillo.epubgallery.R.drawable;
import com.jlillo.epubgallery.R.id;
import com.jlillo.epubgallery.R.layout;
import com.jlillo.epubgallery.R.menu;
import com.jlillo.epubgallery.models.Book;
import com.jlillo.epubgallery.util.CustomGrid;
import com.jlillo.epubgallery.util.DataManager;

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
	TextView nobook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_screen);
		DataManager dm = DataManager.getInstance();
		grid = (GridView) findViewById(R.id.grid);

		if (dm.userEpubBooks.size() == 0) {
			nobook = (TextView) findViewById(R.id.textNoBooks);
			nobook.setVisibility(View.VISIBLE);
			grid.setVisibility(View.GONE);
		}

		bookList = new String[dm.userEpubBooks.size()];
		for (int i = 0; i < dm.userEpubBooks.size(); i++) {
			Book aux = (Book) dm.userEpubBooks.get(i);
			bookList[i] = aux.getBookName() + " - " + aux.getCreationDate();
		}

		// mock for all the books
		imageArray = new int[dm.userEpubBooks.size()];
		for (int i = 0; i < dm.userEpubBooks.size(); i++) {
			imageArray[i] = R.drawable.epub_mock;
		}

		CustomGrid adapter = new CustomGrid(BooksList.this, bookList,
				imageArray);

		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(BooksList.this,"You Clicked at " +
				// bookList[+position], Toast.LENGTH_SHORT).show();

				DataManager dm = DataManager.getInstance();
				Intent intent = new Intent(getBaseContext(),
						DetailActivity.class);
				Bundle b = new Bundle();
				b.putSerializable("bookSelected",
						(Serializable) dm.userEpubBooks.get(position)); // Your
																		// id
				intent.putExtras(b); // Put your id to your next Intent
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

		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("EXIT", true);
		startActivity(intent);

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