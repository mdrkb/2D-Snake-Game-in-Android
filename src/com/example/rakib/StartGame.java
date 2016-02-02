package com.example.rakib;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartGame extends CustomActivity {
	public String name;
	public int score = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startgame);
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		final EditText et1 = (EditText) findViewById(R.id.editText1);
		Button b1 = (Button) findViewById(R.id.button1);
		Typeface font = Typeface.createFromAsset(getAssets(), "COMICBD.TTF");
		
		tv1.setTypeface(font);
		et1.setTypeface(font);
		b1.setTypeface(font);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				name = et1.getText().toString();
				startActivity(new Intent(StartGame.this, Snake.class));
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
