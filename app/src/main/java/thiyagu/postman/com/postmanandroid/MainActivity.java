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

import java.net.URL;
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

        final String[] request = {"GET", "POST", "DELETE", "PUT"};
        sendButton = findViewById(R.id.sendButton);
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, request);

        materialBetterSpinner = findViewById(R.id.material_spinner1);
        materialBetterSpinner.setBackgroundColor(Color.parseColor("#464646"));
        materialBetterSpinner.setAdapter(arrayadapter);
        UrlField = findViewById(R.id.UrlField);
        UrlField.setText("http://192.168.1.110:8080/");
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

                        new RequestMaker().execute("GET",Address, headerBuilder);

                        break;

                    case "POST":

                        Log.v("sdasdasdas", "POST");
                        new RequestMaker().execute("POST",Address,headerBuilder);

                        break;

                    case "DELETE":

                        Log.v("sdasdasdas", "DELETE");
                        new RequestMaker().execute("DELETE",Address,headerBuilder);
                        break;

                    case "PUT":

                        Log.v("sdasdasdas", "PUT");
                        new RequestMaker().execute("UNLOCK",Address,headerBuilder);
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

            String method = (String)strings[0];
            String urlvalue = (String) strings[1];
            Headers.Builder headerbuilder = (Headers.Builder) strings[2];
            if (urlvalue.contains("www") || urlvalue.contains("http")) {


            } else {

                urlvalue = "http://" + urlvalue;


            }
            Log.v("thisisurl", urlvalue);


            if(method.equals("GET"))
            {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(urlvalue)
                            .get()
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "c17e5c97-0297-9916-616c-435fe2adff89")
                            .build();

                    Response response = client.newCall(request).execute();
                }
                catch (Exception e)
                {


                    e.printStackTrace();
                }



            }
            else if(method.equals("POST"))
            {
                try {

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(urlvalue)
                            .post(null)
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "40a4fac7-9aa4-8b7f-7972-db3441ba2bba")
                            .build();

                    Response response = client.newCall(request).execute();
                }
                catch (Exception e)
                {


                    e.printStackTrace();
                }



            }
            else if(method.equals("DELETE"))
            {
                try {

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(urlvalue)
                            .delete(null)
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "d3989091-6532-ceb2-a984-15dc10ec560c")
                            .build();

                    Response response = client.newCall(request).execute();
                }
                catch (Exception e)
                {


                    e.printStackTrace();
                }


            }
            else if(method.equals("PUT"))
            {

                try {

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(urlvalue)
                            .put(null)
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "c9593356-684e-ea02-1e47-16ec3a7e3761")
                            .build();

                    Response response = client.newCall(request).execute();
                }
                catch (Exception e)
                {


                    e.printStackTrace();
                }

            }



//            try
//
//            {
//               OkHttpClient client = new OkHttpClient();
////                Headers h = headerbuilder.build();
////                Request request = new Request.Builder()
////                        .url(urlvalue)
////                        .get().headers(h)
////                        .build();
////                Response response = client.newCall(request).execute();
////                Log.v("response", response.toString());
//
//
//
//
//
//
//
//                                RequestBody requestBody = new MultipartBody.Builder()
//                                .setType(MultipartBody.FORM)
//                                .addFormDataPart("somParam", "someValue")
//                                .build();
//
//                        Request request = new Request.Builder()
//                                .url(urlvalue)
//                                .post(requestBody)
//                                .build();
//                Response response = client.newCall(request).execute();
//            } catch (Exception e) {
//
//                Log.v("asdsdasdad", e.toString());
//
//
//            }
            return null;
        }


    }


}