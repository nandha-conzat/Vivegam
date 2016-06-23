package rukina.vivegam.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import rukina.vivegam.android.R;
import rukina.vivegam.android.utils.PreferenceStorage;


/**
 * Created by Data Crawl 6 on 13-Jun-16.
 */
public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    String status;
    String pUserName;
    String pPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        status = PreferenceStorage.getUserLog(getApplicationContext());
        pUserName = PreferenceStorage.getUserName(getApplicationContext());
        pPassword = PreferenceStorage.getUserPassword(getApplicationContext());

        try {
            new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when
                 * you want to show case your app logo / company
                 */
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    try {
                        synchronized (this) {
                            // Wait given period of time or exit on touch
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    try {
                                        Thread.sleep(0);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            wait(1000);
                        }
                    } catch (InterruptedException ex) {
                    }

                    if (status.equalsIgnoreCase("yes")) {

                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("username", pUserName);
                        intent.putExtra("password", pPassword);
                        startActivity(intent);
                        finish();

                    } else {
                        Intent i = new Intent(SplashScreen.this,
                                Login.class);
                        startActivity(i);
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        } catch (OutOfMemoryError e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Your phone memory is too low, free your cache memory and start the application",
                    Toast.LENGTH_LONG).show();
        }
    }
}
