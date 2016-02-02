package com.example.rakib;

import android.app.Activity;
import android.os.Bundle;

public class CustomActivity extends Activity {
    int onStartCount = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStartCount = 1;
        if (savedInstanceState == null)
        {
            this.overridePendingTransition(R.animator.slide_in_left,
                    R.animator.slide_out_left);
        } else
        { 
            onStartCount = 2;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.animator.slide_in_right,
                    R.animator.slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }
}
