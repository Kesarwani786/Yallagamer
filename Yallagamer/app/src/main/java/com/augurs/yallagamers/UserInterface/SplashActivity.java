package com.augurs.yallagamers.UserInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.augurs.yallagamers.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_splash );
        new Handler ( ).postDelayed ( new Runnable ( ) {
            @Override
            public void run ( ) {
                Intent i = new Intent ( SplashActivity.this , MainActivity.class );
                i.putExtra("ScreenStatus",1);
                startActivity ( i );
                overridePendingTransition ( R.anim.slide_out_right,R.anim.slide_in_right );
                finish ( );
            }
        } , 2000 );
    }
}