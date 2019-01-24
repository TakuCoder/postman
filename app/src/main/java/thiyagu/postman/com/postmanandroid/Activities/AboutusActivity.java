package thiyagu.postman.com.postmanandroid.Activities;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import thiyagu.postman.com.postmanandroid.R;

/**
 * Created by dklap on 6/7/2017.
 */

public class AboutusActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_about);
        Element adsElement = new Element();

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.postmaniconsmall)
                .addItem(new Element().setTitle("Version 1.0.6"))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("thiyagucoder@gmail.com")
                .addWebsite("https://github.com/TakuCoder/")
                .addPlayStore("thiyagu.postman.com.postmanandroid")
                .addGitHub("TakuCoder/postman")
                .addItem(getCopyRightsElement())

                .setDescription("Postman Andorid is an app for interacting with HTTP APIs. It presents you with a friendly GUI for constructing requests and reading responses. Postman android also loaded with testing features")
                .create();


        setContentView(aboutPage);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.postmanicon);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutusActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}