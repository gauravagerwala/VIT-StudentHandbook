package vit.vithandbook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import vit.vithandbook.R;

/**
 * Created by Hemant on 10/24/2015.
 */
public class LaunchSplashActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle sp) {
        super.onCreate(sp);
        setContentView(R.layout.splash_layout);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(LaunchSplashActivity.this,MainActivity.class);
                LaunchSplashActivity.this.startActivity(mainIntent);
                LaunchSplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}