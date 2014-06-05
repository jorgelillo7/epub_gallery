package com.jlillo.epubgallery.controllers;

import java.io.Serializable;

import com.imagezoom.ImageViewTouch;
import com.imagezoom.ImageViewTouch.SwipeListener;
import com.imagezoom.ImageViewTouchBase.DisplayType;
import com.jlillo.epubgallery.R;
import com.jlillo.epubgallery.R.id;
import com.jlillo.epubgallery.R.layout;
import com.jlillo.epubgallery.models.Book;
import com.jlillo.epubgallery.util.DataManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {
	TextView mName, mDate, mFormat, mSize;

	GestureDetector gestureDetector;
	ImageView imageView;

	Book bookSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_screen);

		mName = (TextView) findViewById(R.id.tName);
		mDate = (TextView) findViewById(R.id.tDate);
		mFormat = (TextView) findViewById(R.id.tFormat);
		mSize = (TextView) findViewById(R.id.tSize);

		Bundle b = getIntent().getExtras();
		bookSelected = (Book) b.getSerializable("bookSelected");

		mName.setText(bookSelected.getBookName());
		mDate.setText(bookSelected.getCreationDate());
		mFormat.setText(bookSelected.getType());
		mSize.setText(bookSelected.getSize());

		imageView = (ImageView) findViewById(R.id.imageView1);

		gestureDetector = new GestureDetector(getBaseContext(),
				new GestureListener());
		imageView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return gestureDetector.onTouchEvent(event);
			}

		});

	}

	@Override
	public void onBackPressed() {
		// do something on back.
		Intent myIntent = new Intent(getBaseContext(), BooksList.class);
		startActivity(myIntent);

		return;

	}

	public class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {

			return true;
		}

		// event when double tap occurs
		@Override
		public boolean onDoubleTap(MotionEvent e) {

			/*Toast.makeText(getApplicationContext(),
					"Probandoooooo", Toast.LENGTH_SHORT)
					.show();*/
			Intent intent = new Intent(getBaseContext(), BigImageActivity.class);
			Bundle b = new Bundle();
			b.putSerializable("bookSelected", (Serializable) bookSelected);
			intent.putExtras(b); // Put your id to your next Intent
			startActivity(intent);

			return true;
		}
	}

}