package thiyagu.postman.com.postmanandroid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import thiyagu.postman.com.postmanandroid.CustomTypefaceSpan;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.Fragment.AuthorizationFragment;
import thiyagu.postman.com.postmanandroid.Fragment.BodyFragment;
import thiyagu.postman.com.postmanandroid.Fragment.HeaderFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ParamFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ResponseFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ViewPagerAdapter;
import thiyagu.postman.com.postmanandroid.MaterialBetterSpinner;
import thiyagu.postman.com.postmanandroid.R;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;


public class MainActivity extends AppCompatActivity {

    MaterialBetterSpinner materialBetterSpinner;
    Button sendButton;
    EditText UrlField;
    FeedReaderDbHelper feedReaderDbHelper;
    Typeface roboto;
    public String ssss;
    public String Tag = "postman-trace";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout.Tab body;
    private TabLayout.Tab responsetab;

    private ProgressDialog dialog;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();

        dialog = new ProgressDialog(MainActivity.this);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("POSTMAN-ANDROID");
        SS.setSpan(new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        actionBar.setTitle(SS);

        AssetManager assetManager = this.getAssets();
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");

        viewPager = findViewById(R.id.pager);

        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        ////actionBar.setIcon(R.drawable.postmanicon);
        //actionBar.setDisplayShowHomeEnabled(true);
        final String[] request = {"GET", "POST", "DELETE", "PUT"};
        sendButton = findViewById(R.id.sendButton);
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, request);

        body = tabLayout.getTabAt(3);
        responsetab = tabLayout.getTabAt(4);
        materialBetterSpinner = findViewById(R.id.req_type_spinner);

        materialBetterSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                Log.v(Tag, "selecting material spinner" + "-------------->" + materialBetterSpinner.getText());

                return false;
            }
        });
        materialBetterSpinner.setAdapter(arrayadapter);
        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();


                if (String.valueOf(i).equals("1")) {


                    body.select();

                }
            }
        });


        UrlField = findViewById(R.id.UrlField);
        sendButton.setTypeface(roboto);
        UrlField.setTypeface(roboto);
        materialBetterSpinner.setTypeface(roboto);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout.Tab tab = tabLayout.getTabAt(0);
                tab.select();
                feedReaderDbHelper = new FeedReaderDbHelper(MainActivity.this);
                ArrayList<String> headerlist = feedReaderDbHelper.getAllHeader();
                Headers.Builder headerBuilder = new Headers.Builder();
                if (headerlist.size() > 0) {

                    for (int i = 0; i < headerlist.size(); i++)

                    {

                        String[] subvalue = headerlist.get(i).split("@@");


                        Log.v(Tag, "btn_send_selected" + subvalue[0]);
                        Log.v(Tag, "btn_send_selected" + subvalue[1]);
                        headerBuilder.add(subvalue[1], subvalue[2]);
                    }
                }


                try

                {


                    SharedPreferences prefs = MainActivity.this.getSharedPreferences("Thiyagu", MODE_PRIVATE);


                    String authdata = prefs.getString("Authorization", null);
                    if (authdata.equals("No auth")) {
                        Log.v("postman", "auth value neglected");

                    } else {

                        headerBuilder.add("Authorization", authdata);
                        Log.v(Tag, "Authorization====================> " + authdata);

                    }


                } catch (Exception e) {

                    Log.v(Tag, "Exception while clicking send button" + e.toString());
                    //Toasty.error(getApplicationContext(),e.toString());
                }

                ArrayList<String> paramlist = feedReaderDbHelper.getAllParam();
                ArrayList<String> urlencodedparams = new ArrayList<>();
                if (paramlist.size() > 0) {
                    Log.v(Tag, "Adding Params====================> ");
                    for (int i = 0; i < paramlist.size(); i++)

                    {

                        String[] subvalue = paramlist.get(i).split("@@");


                        Log.v(Tag, "param1=========>" + subvalue[0]);
                        Log.v(Tag, "param1=========>" + subvalue[1]);

                        if (i != paramlist.size() - 1) {
                            urlencodedparams.add(i, subvalue[1] + "=" + subvalue[2] + "&");

                        } else {
                            urlencodedparams.add(i, subvalue[1] + "=" + subvalue[2]);

                        }


                    }
                }

                Log.v(Tag, "Added Params====================> ");


                String seletecvalue = materialBetterSpinner.getText().toString();
                String Address = UrlField.getText().toString();
                switch (seletecvalue) {


                    case "GET":

                        Log.v(Tag, "Diving Into GET");
                        if (isOnline()) {
                            new RequestMaker().execute("GET", Address, headerBuilder, urlencodedparams);

                        } else {

                            ShowNetError();

                        }


                        break;

                    case "POST":

                        Log.v(Tag, "Diving Into POST");
                        if (isOnline()) {

                            ArrayList<String> part = feedReaderDbHelper.getAllBody();
                            Log.v(Tag, "======================part size========================" + String.valueOf(part.size()));
                            if (part.size() > 0) {
                                Log.v(Tag, "======================part size greater than 0========================");
                                new RequestMaker().execute("POST", Address, headerBuilder, urlencodedparams);

                            } else {
                                Log.v(Tag, "======================part size lseer or equal to 0========================");
                                runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()

                                    {
                                        TabLayout.Tab tab = tabLayout.getTabAt(3);
                                        tab.select();


                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                new MaterialTapTargetPrompt.Builder(MainActivity.this)
                                                        .setTarget(findViewById(R.id.AddBody))
                                                        .setPrimaryText("POST request must have atleast one part")
                                                        .setPromptBackground(new CirclePromptBackground())
                                                        .setPromptFocal(new RectanglePromptFocal())
                                                        .setBackgroundColour(getResources().getColor(R.color.buttonblue))
                                                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                                            @Override
                                                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                                                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                                                    //Toast.makeText(getApplicationContext(), "presseddddd", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        })
                                                        .show();

                                            }
                                        }, 1000);
                                    }
                                });


                            }


                        } else {

                            ShowNetError();

                        }
                        break;

                    case "DELETE":

                        Log.v(Tag, "Diving Into DELETE");

                        if (isOnline()) {
                            new RequestMaker().execute("DELETE", Address, headerBuilder, urlencodedparams);
                        } else {

                            ShowNetError();

                        }
                        break;

                    case "PUT":

                        if (isOnline()) {

                            Log.v(Tag, "Diving Into PUT");
                            new RequestMaker().execute("PUT", Address, headerBuilder, urlencodedparams);
                        } else {
                            ShowNetError();

                        }
                        break;
                    default:
                        Log.v(Tag, "Nothing selected in request");
                        new MaterialTapTargetPrompt.Builder(MainActivity.this)
                                .setTarget(findViewById(R.id.req_type_spinner))
                                .setPrimaryText("Select the type of request")
                                .setPromptBackground(new CirclePromptBackground())
                                .setPromptFocal(new RectanglePromptFocal())
                                .setBackgroundColour(getResources().getColor(R.color.buttonblue))
                                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                    @Override
                                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                            Toast.makeText(getApplicationContext(), "presseddddd", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .show();


                }


            }
        });
        try {

            Log.v(Tag, "==============================================");
            Log.v(Tag, "setting default value for url and req type");
            String url_value = prefs.getString("url_value", null);
            String req_value = prefs.getString("req_value", null);
            materialBetterSpinner.setText(req_value);
            UrlField.setText(url_value);
            Log.v(Tag, "=============================");
            Log.v(Tag, "setting value on on create");
            Log.v(Tag, "URL VALUE=========================> " + url_value);
            Log.v(Tag, "REQUEST VALUE=========================> " + req_value);
            Log.v(Tag, "setting value for url and req type done");
            Log.v(Tag, "==============================================");
        } catch (Exception e) {
            Log.v(Tag, e.toString());

        }

    }

    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ParamFragment(), "PARAMS");
        adapter.addFragment(new AuthorizationFragment(), "AUTHORIZATION");
        adapter.addFragment(new HeaderFragment(), "HEADER");
        adapter.addFragment(new BodyFragment(), "BODY");
        adapter.addFragment(new ResponseFragment(), "RESPONSE");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class RequestMaker extends AsyncTask<Object, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v(Tag, "======================onPreExecute========================");
            // progressDialog.show();
            dialog.setMessage("Activating hyperdrive, please wait.");
            dialog.show();
            Log.v(Tag, "======================onPreExecute done========================");
        }

        @Override
        protected String doInBackground(Object... strings) {
            Log.v(Tag, "======================DOINBACKGROUND========================");
            String method = (String) strings[0];
            String urlvalue = (String) strings[1];
            Headers.Builder headerbuilder = (Headers.Builder) strings[2];
            ArrayList<String> paramlist = (ArrayList<String>) strings[3];
            Headers customheader = headerbuilder.build();

            Log.v(Tag, "======================URL CHECK========================");
            if (urlvalue.contains("www") && urlvalue.contains("http://")) {
                Log.v(Tag, "contains www and http");
                //urlvalue = "https://" + urlvalue;
            } else if (urlvalue.contains("www") && urlvalue.contains("https://")) {
                Log.v(Tag, "contains www and https");
                //urlvalue = "http://" + urlvalue;
            } else if (!urlvalue.contains("www"))


            {
                Log.v(Tag, "dont contain www");
                urlvalue = "http://www." + urlvalue;


            } else {

                urlvalue = "http://" + urlvalue;

            }

            if (paramlist.size() > 0) {
                urlvalue = urlvalue + "?";
                for (int i = 0; i < paramlist.size(); i++) {
                    urlvalue = urlvalue + paramlist.get(i);
                }


            }


            if (method.equals("GET")) {
                Log.v(Tag, "======================GET========================");
                try {

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(urlvalue)
                            .get()
                            .headers(customheader)
                            .header("User-Agent", "Postman-Android")
                            .build();


                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });

                            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run()

                                {
                                    try {
                                        String bodyy = response.body().string();
                                        int responsecode = response.code();
                                        String Headers = response.headers().toString();
                                        long tx = response.sentRequestAtMillis();
                                        long rx = response.receivedResponseAtMillis();

                                        Log.v(Tag, "======================BODY========================");
                                        Log.d(Tag, "GET BODY CONTENT========================================>" + bodyy);
                                        Log.d(Tag, "RESPONSE    CODE===========================================>" + String.valueOf(responsecode));
                                        Log.d(Tag, "HEADERS         ===========================================>" + Headers);
                                        Log.d(Tag, "RESPONSE TIME   ===========================================>" + (rx - tx) + " ms");


//                                        Bundle bundle = new Bundle();
//                                        bundle.putString("time", "" + (rx - tx));


                                        Log.d(Tag, "===============writing data to shared preference=========================>" + bodyy);
                                        SharedPreferences.Editor editor = getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
                                        editor.putString("response", bodyy);
                                        editor.putString("code", String.valueOf(responsecode));
                                        editor.putString("time", "" + (rx - tx));
                                        editor.apply();
                                        Log.d(Tag, "===============writing data to shared preference done=========================>" + bodyy);


                                        Log.d(Tag, "===============selecting 4th tab=========================>" + bodyy);
                                        TabLayout.Tab tab = tabLayout.getTabAt(4);
                                        tab.select();

                                        Log.d(Tag, "===============tab selection done=========================>" + bodyy);


                                    } catch (Exception e) {

                                        Log.v(Tag, "exception happened in onreseponse get erquest" + e.toString());

                                        Toasty.warning(MainActivity.this, e.toString(), Toast.LENGTH_SHORT, true).show();
                                    }
                                }


                            });


                        }
                    });


                } catch (Exception e) {
                    Log.v(Tag, "exception happened  get request" + e.toString());

                    e.printStackTrace();
                    Toasty.warning(MainActivity.this, e.toString(), Toast.LENGTH_SHORT, true).show();
                }


            } else if (method.equals("POST")) {
                try {

                    Log.v(Tag, "======================POST========================");
                    OkHttpClient client = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    RequestBody requestBody = null;
                    builder.setType(MultipartBody.FORM);

                    ArrayList<String> part = feedReaderDbHelper.getAllBody();
                    Log.v(Tag, "======================part size========================" + String.valueOf(part.size()));
                    String[] subvalue = null;


                    if (part.size() > 0)


                    {

                        Log.v(Tag, "====================Adding Builder=========================================");


                        for (int i = 0; i < part.size(); i++)

                        {


                            try {
                                subvalue = part.get(i).split("@@");


                                Log.v(Tag, "builder" + i + subvalue[0]);
                                Log.v(Tag, "builder" + i + subvalue[1]);
                                builder.addFormDataPart(subvalue[1], subvalue[2]);


                            } catch (Exception e) {
                                Log.v(Tag, "exception happened while adding builder in post");
                                Log.v(Tag, e.toString());

                            }

                        }

                        requestBody = builder.build();
                        Request request = new Request.Builder()
                                .url(urlvalue)
                                .headers(customheader)
                                .post(requestBody)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, final IOException e) {
                                Log.d(Tag, "failure");
                                Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                                Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {

                                String bodyy = response.body().string();
                                int responsecode = response.code();

                                String Headers = response.headers().toString();

                                long tx = response.sentRequestAtMillis();
                                long rx = response.receivedResponseAtMillis();


                                Log.v(Tag, "======================BODY========================");
                                Log.d(Tag, "GET BODY CONTENT========================================>" + bodyy);
                                Log.d(Tag, "RESPONSE    CODE===========================================>" + String.valueOf(responsecode));
                                Log.d(Tag, "HEADERS         ===========================================>" + Headers);
                                Log.d(Tag, "RESPONSE TIME   ===========================================>" + (rx - tx) + " ms");


                                Log.d(Tag, "===============writing data to shared preference=========================>" + bodyy);


                                SharedPreferences.Editor editor = getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
                                editor.putString("response", bodyy);
                                editor.putString("code", String.valueOf(responsecode));
                                editor.putString("time", "" + (rx - tx));
                                editor.apply();
                                Log.d(Tag, "===============writing data to shared preference done=========================>" + bodyy);


                            }
                        });


                    }

//                    else
//
//                        {
//
//                        runOnUiThread(new Runnable()
//
//
//                        {
//                            @Override
//                            public void run()
//
//                            {
//                                TabLayout.Tab tab = tabLayout.getTabAt(3);
//                                tab.select();
//
//
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//
//                                        new MaterialTapTargetPrompt.Builder(MainActivity.this)
//                                                .setTarget(findViewById(R.id.AddBody))
//                                                .setPrimaryText("POST request must have atleast one part")
//                                                .setPromptBackground(new CirclePromptBackground())
//                                                .setPromptFocal(new RectanglePromptFocal())
//                                                .setBackgroundColour(getResources().getColor(R.color.buttonblue))
//                                                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
//                                                    @Override
//                                                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
//                                                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
//                                                            //Toast.makeText(getApplicationContext(), "presseddddd", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                })
//                                                .show();
//
//                                    }
//                                }, 1000);
//                            }
//                        });
//
//
//                    }


                } catch (final Exception e) {


                    e.printStackTrace();

                }


            } else if (method.equals("DELETE")) {
                try {


                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://httpbin.org/get")
                            .get()
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-token", "46ff633c-bac1-39f0-f8ec-366902d40c72")
                            .build();

                    Response response = client.newCall(request).execute();

                    Log.v(Tag, response.toString());

                } catch (Exception e) {


                    e.printStackTrace();
                }


            } else if (method.equals("PUT")) {

                try {

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(urlvalue)
                            .put(null)
                            .addHeader("cache-control", "no-cache")
                            .addHeader("postman-android-token", "c9593356-684e-ea02-1e47-16ec3a7e3761")
                            .build();

                    Response response = client.newCall(request).execute();

                } catch (Exception e) {


                    e.printStackTrace();
                }

            }
            try {
                Thread.sleep(1000);

            } catch (Exception e) {


            }
            Log.v(Tag, "======================DOINBACKGROUND END========================");
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v(Tag, "======================onPostExecute========================");
            if (dialog != null)
                dialog.dismiss();
//            TabLayout.Tab tab = tabLayout.getTabAt(4);
//            tab.select();
            Log.v(Tag, "======================onPostExecute END========================");
        }
    }

    public String getUrlData() {
        String sss = UrlField.getText().toString();
        return sss;

    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
        editor.putString("url_value", UrlField.getText().toString());
        editor.putString("req_value", materialBetterSpinner.getText().toString());

        editor.apply();
        Log.v(Tag, "am in onpause insatnce");


        super.onPause();
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("req_type", "thiyagu");
        Log.v(Tag, "am in on saved insatnce");


        ssss = getUrlData();


        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v(Tag, "am in restore saved insatnce");


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(Tag, "restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(Tag, "destroy");
    }


    @Override
    protected void onStop() {
        super.onStop();


        Log.v(Tag, "stopppppp");
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void ShowNetError() {

        Toasty.warning(MainActivity.this, "No internet Found!", Toast.LENGTH_SHORT, true).show();


    }


}
