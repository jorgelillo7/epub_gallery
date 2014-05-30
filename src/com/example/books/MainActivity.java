package com.example.books;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

Button   mButton;
EditText tUser;
EditText tPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		mButton = (Button)findViewById(R.id.bSend);
		tUser   = (EditText)findViewById(R.id.textUser);
		tPass   = (EditText)findViewById(R.id.textPass);
		
		mButton.setOnClickListener(new OnClickListener()
		    {
		      public void onClick(View v)
		      {
		    	  Toast.makeText(getApplicationContext(), tUser.getText() + ", " + tPass.getText(), 
		    			   Toast.LENGTH_LONG).show();
		    	  /* TODO dropbox login
		    	   * https://www.dropbox.com/developers/blog/45/using-oauth-20-with-the-core-api
		    	   * */
		    	   
		    	  
		      }
		    });
		 
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
