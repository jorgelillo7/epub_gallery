package com.example.books;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BooksList extends Activity {

	TextView mText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_screen);
		DataManager dm = DataManager.getInstance();
		  
		mText = (TextView) findViewById(R.id.textView1);
		mText.setText("" + dm.userEpubBooks.size());
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
			/*
			 * case R.id.other:
			 * 
			 * return true;
			 */
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void closeSession() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		preferences.edit().remove("MyDropboxSessionSave").commit();

		DataManager dm = DataManager.getInstance();
		dm.userBooks.clear();
		dm.userEpubBooks.clear();
		dm.isLogged = false;
		dm.comeFromList = true;

		Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
		startActivity(myIntent);

	}

	@Override
	public void onBackPressed() {
		// do something on back.
		DataManager dm =  DataManager.getInstance();
		  dm.comeFromList = true;
		  return;
		
	}

}