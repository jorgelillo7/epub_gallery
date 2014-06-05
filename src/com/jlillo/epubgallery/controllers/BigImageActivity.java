package com.jlillo.epubgallery.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlillo.epubgallery.R;
import com.jlillo.epubgallery.models.Book;

public class BigImageActivity extends Activity {
	ImageView imageView;
	Book bookSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.big_image);

		Bundle b = getIntent().getExtras();
		bookSelected = (Book) b.getSerializable("bookSelected");

		imageView = (ImageView) findViewById(R.id.imageView1);

		// TODO cuando tengan portada los libros cambiar imagen mock por su
		// correspondiente portada

		// imageView.setImageBitmap(bm);

	}
}