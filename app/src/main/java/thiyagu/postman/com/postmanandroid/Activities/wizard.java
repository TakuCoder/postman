package thiyagu.postman.com.postmanandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

import thiyagu.postman.com.postmanandroid.R;

public class wizard extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle("Welcome!");
        sliderPage1.setDescription("Powerful HTTP Client designed for android with \uD83D\uDC9A ");
        sliderPage1.setImageDrawable(R.drawable.postmanicon);
        sliderPage1.setBgColor(Color.TRANSPARENT);
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle("Built-in Tools");
        sliderPage2.setDescription("Everything a developer needs to work with APIs.");
        sliderPage2.setImageDrawable(R.drawable.getpost);
        sliderPage2.setBgColor(Color.TRANSPARENT);
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle("Import Your Certificate!");
        sliderPage3.setDescription("Using self-signed SSL certificates with Postman-Android");
        sliderPage3.setImageDrawable(R.drawable.certificate);
        sliderPage3.setBgColor(Color.TRANSPARENT);
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        SliderPage sliderPage4 = new SliderPage();
        sliderPage4.setTitle("Explore");
        sliderPage4.setDescription("Feel free to explore the rest of the library demo!");
        sliderPage4.setImageDrawable(R.drawable.postmanicon);
        sliderPage4.setBgColor(Color.TRANSPARENT);
        addSlide(AppIntroFragment.newInstance(sliderPage4));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        SharedPreferences.Editor editor = getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
        editor.putString("wizard", "passed");
        editor.apply();

        finish();

        Intent intent = new Intent(wizard.this,NavDrawerActivityMain.class);
        startActivity(intent);
    }
}
