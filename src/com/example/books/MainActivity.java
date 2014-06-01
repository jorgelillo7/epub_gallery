package com.example.books;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import models.Book;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONObject;

import util.DataManager;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

Button   mButton;
boolean mIsLoggedIn;
 
final static private String APP_KEY = "f49tm0zs6rb5oxv";
final static private String APP_SECRET = "ncqjaox0hwp27eo";
final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;
private DropboxAPI<AndroidAuthSession> mDBApi;

private String provider;
final static public String ACCOUNT_PREFS_NAME = "Dropbox_Data";
 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		mButton = (Button)findViewById(R.id.bSend);
	 
		  AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
		    AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
		    mDBApi = new DropboxAPI<AndroidAuthSession>(session);

		 //this is start authentication     
		    mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
	    	   
		    setLoggedIn(mDBApi.getSession().isLinked());
    	  
		mButton.setOnClickListener(new OnClickListener()
		    {
		      public void onClick(View v)
		      {
		   
		    	  /* TODO dropbox login
		    	   * https://www.dropbox.com/developers/blog/45/using-oauth-20-with-the-core-api
		    	   * */
		    	// And later in some initialization function:
		    	 
		    	  
		      }
		    });
		 
	    
	}

	
	
	//This get call after StartAuthentication..
	protected void onResume() {
	    super.onResume();
	    AndroidAuthSession session = mDBApi.getSession();
	    // The next part must be inserted in the onResume() method of the
	    // activity from which session.startAuthentication() was called, so
	    // that Dropbox authentication completes properly.
	    if (session.authenticationSuccessful()) {
	        try {
	            // Mandatory call to complete the auth
	            session.finishAuthentication();

	            // Store it locally in our app for later use
	            TokenPair tokens = session.getAccessTokenPair();
	            //storeKeys(tokens.key, tokens.secret);
	            setLoggedIn(true);
	            
	            mDBApi.getSession().finishAuthentication();
	            new LongOperation().execute("");
	        } catch (IllegalStateException e) {
	//Keep this toast.. It will show you the completed authentication..
	            Toast.makeText(getBaseContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
	            Log.i("Dropbox", "Error authenticating", e);
	        }
	    }
	}    
	
	private void storeKeys(String key, String secret) {
	    // Save the access key for later
	    SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	    Editor edit = prefs.edit();
	    edit.putString(APP_KEY, key);
	    edit.putString(APP_SECRET, secret);
	    edit.commit();
	}
	public void setLoggedIn(boolean loggedIn) {
	    mIsLoggedIn = loggedIn;
	}
	public boolean isLoggedIn() {
	    return mIsLoggedIn;
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {
		String usertoken;
        @Override
        protected String doInBackground(String... params) {
        	 // Required to complete auth, sets the access token on the session
           

            String accessToken = mDBApi.getSession().getOAuth2AccessToken();
            usertoken = accessToken;
            
            DataManager dm = DataManager.getInstance();
          
            
           /* Entry contact = null;
			try {
				contact = mDBApi.metadata("/", 0, null, true, null);
			} catch (DropboxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            List<Entry> CFolder = contact.contents;
            for (Entry entry : CFolder) {
            Log.i("DbExampleLog", "Filename: " + entry.fileName());}
            */
            
       
            checkSubLevels("/");
            	    
            
            	    Log.i("Finish!!!!!!!!!!!!!!!!!!","");
            mDBApi.getSession().finishAuthentication();
			return "";
           
        }

        @Override
        protected void onPostExecute(String result) {
        	  Toast.makeText(getApplicationContext(), usertoken, 
           		   Toast.LENGTH_LONG).show();
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
	
	public void checkSubLevels (String dir){
		DataManager dm = DataManager.getInstance();
		Entry dropboxDir = null;
		try {
			dropboxDir = mDBApi.metadata(dir, 0, null, true, null);
		} catch (DropboxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    for (Entry e : dropboxDir.contents) {
	        if (!e.isDeleted) {
	           
	            if(e.isDir){
	            	checkSubLevels(e.path);
	            	
	            } else { 
	            	Book book = new Book(e.fileName(), e.modified, e.mimeType);
	  	            Log.i("Item Name",e.fileName());
	  	            Log.i("Item Date",e.modified);
	  	            Log.i("Item Date",e.mimeType);
	  	           // dm.addBook(book);  //check!!!!!!!!!!!!!!!
	            }
	        }
	    }
		
	}

	  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	
	
}
