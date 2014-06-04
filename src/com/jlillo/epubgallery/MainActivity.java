package com.jlillo.epubgallery;
 
import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.jlillo.epubgallery.R;
import com.google.gson.Gson;
import com.jlillo.epubgallery.controllers.BooksList;
import com.jlillo.epubgallery.models.Book;
import com.jlillo.epubgallery.util.DataManager;


import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/*TODO
 
 *  Revisar guardado de sesion-> probando con SharedPreferences
 *  Mejorar navegación
 *  Mostrar imágenes de portada de cada libro
 *  
 *  Future:
 *  Sustituir tap -> double tap 
 *  Adaptar pantalla detalle libros
 *  Mejorar iconos/backgrounds/estilos...
 *  Soporte otros idiomas
 *  Soporte versiones android antiguas
 *  Versión ipad

 * 
 * 
 * */
public class MainActivity extends Activity {

	Button mButton, mButton2;
	boolean mIsLoggedIn;
	
	//ProgressDialog barProgressDialog;

	final static private String APP_KEY = "f49tm0zs6rb5oxv";
	final static private String APP_SECRET = "ncqjaox0hwp27eo";
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;
	private DropboxAPI<AndroidAuthSession> mDBApi;
	DataManager dm = DataManager.getInstance();
	ArrayList<Book> allBooks = new ArrayList<Book>();
	ArrayList<Book> epubBooks = new ArrayList<Book>();

	final static public String ACCOUNT_PREFS_NAME = "Dropbox_Data";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
		    finish();
		} else {
		
		mButton = (Button) findViewById(R.id.bSend);
		 
		AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		AndroidAuthSession session = new AndroidAuthSession(appKeys,
				ACCESS_TYPE);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);

		// this is start authentication
		mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
		
		// check if we have an active session
		/*SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this.getApplicationContext());
		Gson gson = new Gson();
	
		String json = appSharedPrefs.getString("MyDropboxSessionSave", "");

		if (json == "") { // no data
			AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
			AndroidAuthSession session = new AndroidAuthSession(appKeys,
					ACCESS_TYPE);
			mDBApi = new DropboxAPI<AndroidAuthSession>(session);

			// this is start authentication
			mDBApi.getSession().startOAuth2Authentication(MainActivity.this);

		} else { // data Save
			AndroidAuthSession sessionStore = gson.fromJson(json,
					AndroidAuthSession.class);
			mDBApi = new DropboxAPI<AndroidAuthSession>(sessionStore);
			//new LongOperation().execute("");
			
		}*/

		//Botón iniciar sesión manual
		mButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dm.comeFromList = false;
				
				AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
				AndroidAuthSession session = new AndroidAuthSession(appKeys,
						ACCESS_TYPE);
				mDBApi = new DropboxAPI<AndroidAuthSession>(session);

				// this is start authentication
				mDBApi.getSession()
						.startOAuth2Authentication(MainActivity.this);

			}
		});
		}
/*
		// Clear session
		mButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());

				preferences.edit().remove("MyDropboxSessionSave").commit();
				Toast.makeText(getApplicationContext(), "Session clear",
						Toast.LENGTH_SHORT).show();

				DataManager dm = DataManager.getInstance();
				if(dm.userBooks.size()!=0){
				dm.userBooks.clear();
				}
				if(dm.userEpubBooks.size()!=0){
				dm.userEpubBooks.clear();
				}
				dm.isLogged = false;

			}
		});*/

	}

	// This get call after StartAuthentication..
	protected void onResume() {
		super.onResume();
	 
				// check if we have an active session
				/*SharedPreferences appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(this.getApplicationContext());
				Gson gson = new Gson();
			
				String json = appSharedPrefs.getString("MyDropboxSessionSave", "");

				if (json == "") { // no data
					AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
					AndroidAuthSession session = new AndroidAuthSession(appKeys,
							ACCESS_TYPE);
					mDBApi = new DropboxAPI<AndroidAuthSession>(session);

					// this is start authentication
					//mDBApi.getSession().startOAuth2Authentication(MainActivity.this);

				} else { // data Save
					AndroidAuthSession sessionStore = gson.fromJson(json,
							AndroidAuthSession.class);
					mDBApi = new DropboxAPI<AndroidAuthSession>(sessionStore);
					dm.isLogged = true;
					dm.comeFromList = false;
					
					//new LongOperation().execute("");
					
				}
				*/
				
				
				AndroidAuthSession session = mDBApi.getSession();

				if (session.authenticationSuccessful()) {
					//storeSession(mDBApi.getSession());
					dm.isLogged = true;
					dm.comeFromList = false;
					try {
						session.finishAuthentication();

						mDBApi.getSession().finishAuthentication();
						new LongOperation().execute("");

					} catch (IllegalStateException e) {

						Toast.makeText(getBaseContext(),
								e.getLocalizedMessage(), Toast.LENGTH_SHORT)
								.show();
						Log.i("Dropbox", "Error authenticating", e);
					}
				
				
			
		}
	}

	/*private void storeSession(AndroidAuthSession session) {
		// Save session
		SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this.getApplicationContext());
		Editor prefsEditor = appSharedPrefs.edit();
		Gson gson = new Gson();
		String json = gson.toJson(session);
		prefsEditor.putString("MyDropboxSessionSave", json);
		prefsEditor.commit();

	}
*/
	private class LongOperation extends AsyncTask<String, Void, String> {
		  
		@Override
		protected String doInBackground(String... params) {
		 
			checkSubLevels("/");
  
			dm.userBooks = allBooks;
			dm.userEpubBooks = epubBooks;

			Intent myIntent = new Intent(getBaseContext(), BooksList.class);
			startActivity(myIntent);
			return "";
			
		}

		@Override
		protected void onPostExecute(String result) {
			//Toast.makeText(getApplicationContext(), "Loaded",Toast.LENGTH_LONG).show();
			 
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	public void checkSubLevels(String dir) {
	 
		Entry dropboxDir = null;
		try {
			dropboxDir = mDBApi.metadata(dir, 0, null, true, null);
		} catch (DropboxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Entry e : dropboxDir.contents) {
			if (!e.isDeleted) {

				if (e.isDir) {
					checkSubLevels(e.path);

				} else {
					Book book = new Book(e.fileName(), e.modified, e.mimeType, e.size);
					Log.i("Item Name", e.fileName());
					Log.i("Item Date", e.modified);
					Log.i("Item Date", e.mimeType);
					Log.i("Item Date", e.icon);

					allBooks.add(book);
					if (e.mimeType.equals("application/epub+zip")) {
						if (!epubBooks.contains(book)) {
							epubBooks.add(book);
						}
					}

				}
			}
		}

	}
	
	@Override
	public void onBackPressed() { //suspend app
		 moveTaskToBack(true);
		 
	}
	

}
