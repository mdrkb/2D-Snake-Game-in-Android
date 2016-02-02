package com.example.rakib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Options extends CustomActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "COMICBD.TTF");
		tv1.setTypeface(font);
		tv2.setTypeface(font);
	}
}
