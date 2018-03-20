package thiyagu.postman.com.postmanandroid;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import thiyagu.postman.com.postmanandroid.ViewPager.PagerAdapter;

public  class MainActivity extends AppCompatActivity {
    Spinner spinner;
    MaterialBetterSpinner materialBetterSpinner ;
Button params,sendButton;
    String[] SPINNER_DATA = {"ANDROID","PHP","BLOGGER","WORDPRESS"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Authorization"));
        tabLayout.addTab(tabLayout.newTab().setText("Headers"));
        tabLayout.addTab(tabLayout.newTab().setText("Body"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        String[] request = {"GET", "POST", "DELETE", "UNLOCK"};
        sendButton = findViewById(R.id.sendButton);
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, request);

        materialBetterSpinner = findViewById(R.id.material_spinner1);
        materialBetterSpinner.setBackgroundColor(Color.parseColor("#464646"));
        materialBetterSpinner.setAdapter(arrayadapter);

        params = findViewById(R.id.params);
        params.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PopUpActivity.class);
                startActivity(intent);
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String seletecvalue = materialBetterSpinner.getText().toString();


                switch (seletecvalue)
                {


                        case "GET":

                        Log.v("sdasdasdas","GET");
                        break;

                        case "POST":

                        Log.v("sdasdasdas","POST");
                        break;

                        case "DELETE":

                        Log.v("sdasdasdas","DELETE");
                        break;

                        case "UNLOCK":

                        Log.v("sdasdasdas","UNLOCK");
                        break;


                }




            }
        });

    }


}