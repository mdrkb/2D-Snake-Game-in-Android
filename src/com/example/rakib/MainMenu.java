package com.example.rakib;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainMenu extends CustomActivity implements OnClickListener{
	
	ImageButton start, options, highscore, intro, about;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		Initialize();
		start.setOnClickListener(this);
		options.setOnClickListener(this);
		highscore.setOnClickListener(this);
		intro.setOnClickListener(this);
		about.setOnClickListener(this);
	}

	private void Initialize() {
		start = (ImageButton) findViewById(R.id.start);
		options = (ImageButton) findViewById(R.id.options);
		highscore = (ImageButton) findViewById(R.id.highscore);
		intro = (ImageButton) findViewById(R.id.intro);
		about = (ImageButton) findViewById(R.id.about);
	}

	@Override
	public void onClick(View view) {
		mp =  MediaPlayer.create(MainMenu.this,R.raw.food);
		switch(view.getId()){
		case R.id.start:
			mp.start();
			startActivity(new Intent(MainMenu.this, StartGame.class));
			break;
		case R.id.options:
			mp.start();
			startActivity(new Intent(MainMenu.this, Options.class));
			break;
		case R.id.highscore:
			mp.start();
			startActivity(new Intent(MainMenu.this, Highscore.class));
			break;
		case R.id.intro:
			mp.start();
			startActivity(new Intent(MainMenu.this, Intro.class));
			break;
		case R.id.about:
			mp.start();
			startActivity(new Intent(MainMenu.this, About.class));
			break;
		}
		
	}
}
