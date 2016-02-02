package com.example.rakib;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class About extends CustomActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		TextView tv3 = (TextView) findViewById(R.id.textView3);
		TextView tv4 = (TextView) findViewById(R.id.textView4);
		TextView tv5 = (TextView) findViewById(R.id.textView5);
		TextView tv6 = (TextView) findViewById(R.id.textView6);
		TextView tv7 = (TextView) findViewById(R.id.textView7);
		TextView tv8 = (TextView) findViewById(R.id.textView8);
		TextView tv9 = (TextView) findViewById(R.id.textView9);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "COMICBD.TTF");
		tv1.setTypeface(font);
		tv2.setTypeface(font);
		tv3.setTypeface(font);
		tv4.setTypeface(font);
		tv5.setTypeface(font);
		tv6.setTypeface(font);
		tv7.setTypeface(font);
		tv8.setTypeface(font);
		tv9.setTypeface(font);
	}
}
