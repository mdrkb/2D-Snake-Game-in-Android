package com.example.rakib;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

public class Welcome extends CustomActivity {
	MediaPlayer mp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		mp = MediaPlayer.create(Welcome.this, R.raw.welcome);
		mp.start();
		
		ImageView iv = (ImageView) findViewById(R.id.loading);
		iv.setBackgroundResource(R.animator.welcome_animation);
		AnimationDrawable anime = (AnimationDrawable) iv.getBackground();
		anime.start();
		
		Thread thread = new Thread(){
			public void run(){
				try{
					sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					startActivity(new Intent(Welcome.this, MainMenu.class));
				}
			}
		};
		thread.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
		finish();
	}
}
