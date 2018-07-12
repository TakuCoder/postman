package thiyagu.postman.com.postmanandroid.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import thiyagu.postman.com.postmanandroid.R;

public class Splash extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = Splash.this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
                String check = prefs.getString("wizard", null);

try
{

    if(check.equals("passed"))
    {
        Intent intent = new Intent(Splash.this,MainActivity.class);
        startActivity(intent);

    }



}
catch (Exception e)
{


    Intent intent = new Intent(Splash.this,wizard.class);
    startActivity(intent);


}




            }
        },3000);

    }
}
