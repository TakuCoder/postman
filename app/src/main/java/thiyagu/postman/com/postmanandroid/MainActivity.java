package thiyagu.postman.com.postmanandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.Fragment.BodyFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ViewPagerAdapter;
import thiyagu.postman.com.postmanandroid.Fragment.OneFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ThreeFragment;
import thiyagu.postman.com.postmanandroid.Fragment.TwoFragment;


public class MainActivity extends AppCompatActivity {

    MaterialBetterSpinner materialBetterSpinner;
    Button  sendButton;
    EditText UrlField;
    FeedReaderDbHelper feedReaderDbHelper;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);

        setupViewPager(viewPager);
            tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        final String[] request = {"GET", "POST", "DELETE", "UNLOCK"};
        sendButton = findViewById(R.id.sendButton);
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, request);

        materialBetterSpinner = findViewById(R.id.material_spinner1);
        materialBetterSpinner.setBackgroundColor(Color.parseColor("#464646"));
        materialBetterSpinner.setAdapter(arrayadapter);
        UrlField = findViewById(R.id.UrlField);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedReaderDbHelper = new FeedReaderDbHelper(MainActivity.this);
                ArrayList<String> arrayList = feedReaderDbHelper.getAllCotacts();
                Headers.Builder headerBuilder = new Headers.Builder();
                if (arrayList.size() > 0) {

                    for (int i = 0; i < arrayList.size(); i++)

                    {

                        String[] subvalue = arrayList.get(i).split("@@");


                        Log.v("asdasdasdsa",subvalue[0]);
                        Log.v("asdasdasdsa",subvalue[1]);
                        headerBuilder.add(subvalue[1], subvalue[2]);
                    }
                }
                String seletecvalue = materialBetterSpinner.getText().toString();

                String Address = UrlField.getText().toString();
                switch (seletecvalue) {


                    case "GET":

                        Log.v("sdasdasdas", "GET");

                        new RequestMaker().execute(Address, headerBuilder);

                        break;

                    case "POST":

                        Log.v("sdasdasdas", "POST");
                        new RequestMaker().execute(Address,headerBuilder);

                        break;

                    case "DELETE":

                        Log.v("sdasdasdas", "DELETE");
                        new RequestMaker().execute(Address,headerBuilder);
                        break;

                    case "UNLOCK":

                        Log.v("sdasdasdas", "UNLOCK");
                        new RequestMaker().execute(Address,headerBuilder);
                        break;


                }


            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {


       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "PARAMS");
        adapter.addFragment(new TwoFragment(), "AUTHORIZATION");
        adapter.addFragment(new ThreeFragment(), "HEADER");
        adapter.addFragment(new BodyFragment(), "BODY");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class RequestMaker extends AsyncTask<Object, String, String> {


        @Override
        protected String doInBackground(Object... strings) {


            String urlvalue = (String) strings[0];
            Headers.Builder headerbuilder = (Headers.Builder) strings[1];
            if (urlvalue.contains("www") || urlvalue.contains("http")) {


            } else {

                urlvalue = "http://" + urlvalue;


            }
            Log.v("thisisurl", urlvalue);

            try

            {
               OkHttpClient client = new OkHttpClient();
//                Headers h = headerbuilder.build();
//                Request request = new Request.Builder()
//                        .url(urlvalue)
//                        .get().headers(h)
//                        .build();
//                Response response = client.newCall(request).execute();
//                Log.v("response", response.toString());







                                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("somParam", "someValue")
                                .build();

                        Request request = new Request.Builder()
                                .url(urlvalue)
                                .post(requestBody)
                                .build();
                Response response = client.newCall(request).execute();
            } catch (Exception e) {

                Log.v("asdsdasdad", e.toString());


            }
            return null;
        }


    }


}