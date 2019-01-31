package thiyagu.postman.com.postmanandroid.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import thiyagu.postman.com.postmanandroid.CustomTypefaceSpan;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.Event.BusProvider;
import thiyagu.postman.com.postmanandroid.Fragment.AuthorizationFragment;
import thiyagu.postman.com.postmanandroid.Fragment.BodyFragment;
import thiyagu.postman.com.postmanandroid.Fragment.HeaderFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ParamFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ViewPagerAdapter;
import thiyagu.postman.com.postmanandroid.HistoryActivity;
import thiyagu.postman.com.postmanandroid.MaterialBetterSpinner;
import thiyagu.postman.com.postmanandroid.MyDatabaseReference;
import thiyagu.postman.com.postmanandroid.R;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class NavDrawerActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "TAG";
    MaterialBetterSpinner materialBetterSpinner;
    Button sendButton;
    EditText UrlField;
    AlertDialog.Builder alert;
    FeedReaderDbHelper feedReaderDbHelper;
    Typeface roboto;
    public String ssss;
    public String Tag = "postman-trace";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout.Tab body;
    private TabLayout.Tab responsetab;
    //public static String flag;
    private static NavDrawerActivityMain instance;
    private ProgressDialog dialog;
    SharedPreferences prefs;
    private Toolbar toolbar;
    String IPaddress;
    NavigationView navigationView;
    DrawerLayout drawer;
    private View mHeaderView;
    private TextView mDrawerHeaderTitle;
    AssetManager assetManager;
    ActionBarDrawerToggle toggle;
    SharedPreferences.Editor editor;
    public long timeout;

    @Inject
    MyDatabaseReference myDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_main);

        intiview();
        setupSharedPreferences();

        // ((MyApplication)getApplicationContext()).getMyComponent().inject(this);

        feedReaderDbHelper = new FeedReaderDbHelper(this);
        alert = new AlertDialog.Builder(NavDrawerActivityMain.this);
        alert.setPo
        prefs = this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        editor = this.getApplicationContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
        setSupportActionBar(toolbar);
        instance = this;

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        dialog = new ProgressDialog(NavDrawerActivityMain.this);
        dialog.setCancelable(false);

        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("POSTMAN-ANDROID");
        SS.setSpan(new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        assetManager = this.getAssets();
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");


        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        final String[] request = {"GET", "POST", "DELETE", "PUT"};

        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, request);

        body = tabLayout.getTabAt(3);
        responsetab = tabLayout.getTabAt(4);

        NetwordDetect();

//


        materialBetterSpinner.setAdapter(arrayadapter);
        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {


                    case 0:

//                        editor.putString("bodytypeflag", "0");
//                        editor.apply();


                        break;

                    case 1:
                        body.select();
//                        editor.putString("bodytypeflag", "1");
//                        editor.apply();
                        break;


                }

            }
        });


        sendButton.setTypeface(roboto);
        UrlField.setTypeface(roboto);
        materialBetterSpinner.setTypeface(roboto);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {


                SharedPreferences sharedPreferences = NavDrawerActivityMain.this.getSharedPreferences("thiyagu.postman.com.postmanandroid_preferences", MODE_PRIVATE);
                String status = sharedPreferences.getString("CertPicker", "");
                Log.v("status", status);
                if (status == "DEFAULT") {

                    Log.v("status", "loading default cert");
                } else {
                    Log.v("status", "loading user cert");

                }

                Log.v("mypreference", status);
                editor.putString("url_value", UrlField.getText().toString());
                editor.putString("req_value", materialBetterSpinner.getText().toString());
                editor.apply();


                if (isValid(UrlField.getText().toString())) {


                    // feedReaderDbHelper = new FeedReaderDbHelper(NavDrawerActivityMain.this);
                    ArrayList<String> headerlist = feedReaderDbHelper.getAllHeader();
                    Headers.Builder headerBuilder = new Headers.Builder();
                    if (headerlist.size() > 0) {
                        Log.v(Tag, "=======================adding headers=========================");
                        for (int i = 0; i < headerlist.size(); i++)

                        {

                            String[] subvalue = headerlist.get(i).split("@@");
                            Log.v(Tag, subvalue[1] + subvalue[2]);
                            headerBuilder.add(subvalue[1], subvalue[2]);
                        }
                    }
                    Log.v(Tag, "=======================added headers=========================");

                    try

                    {
                        editor = getApplicationContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
                        editor.putString("response", "");
                        editor.putString("code", "");
                        editor.putString("time", "");
                        editor.apply();


                        String authdata = prefs.getString("Authorization", null);
                        try {
                            if (authdata.equals("No auth")) {
                                Log.v("postman", "auth value neglected");

                            } else {

                                headerBuilder.add("Authorization", authdata);
                                Log.v(Tag, "Authorization====================> " + authdata);

                            }
                        } catch (Exception e) {
                            editor.putString("Authorization", "No auth");
                            editor.apply();

                        }


                    } catch (Exception e)
                    {

                        Log.v(Tag, "Exception while clicking send button" + e.toString());

                        e.printStackTrace();

                        // Crashlytics.log(Log.ERROR, TAG, "NPE caught!");
                        // Crashlytics.logException(ex);
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
                                // new RequestMaker().execute("GET", Address, headerBuilder, urlencodedparams);
                                GetRequest("GET", Address, headerBuilder, urlencodedparams);
                            } else {

                                ShowNetError();

                            }


                            break;

                        case "POST":

                            Log.v(Tag, "Diving Into POST");
                            if (isOnline()) {

                                ArrayList<String> part = feedReaderDbHelper.getAllBody();
                                final String rawbody = prefs.getString("rawbody", null);
                                Log.v(Tag, "======================part size========================" + String.valueOf(part.size()));
                                if (part.size() > 0) {
                                    Log.v(Tag, "======================part size greater than 0========================");
                                    // new RequestMaker().execute("POST", Address, headerBuilder, urlencodedparams);
                                    GetRequest("POST", Address, headerBuilder, urlencodedparams);
                                } else {
                                    Log.v(Tag, "======================part size lseer or equal to 0========================");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run()

                                        {
                                            TabLayout.Tab tab = tabLayout.getTabAt(3);
                                            tab.select();

                                            String rawbodytype = prefs.getString("rawbodytype", "");

                                            switch (rawbodytype) {


                                                case "MULTIFORM":
                                                    HighLightButton(R.id.AddBody,"POST request must have atleast one part");
                                                    break;

                                                case "JSON":
                                                    HighLightButton(R.id.button_addRawText,"POST request must have atleast one part");
                                                    break;

                                                case "XML":
                                                    HighLightButton(R.id.button_addRawText,"POST request must have atleast one part");
                                                    break;
                                                    default:

                                                        //body_spinner.setSelection(0);

                                                        HighLightButton(R.id.body_spinner,"Please select the type of part");
                                                        Log.v("sdasdsd","this"+rawbodytype);
                                                        break;


                                            }

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
                                //new RequestMaker().execute("DELETE", Address, headerBuilder, urlencodedparams);
                                GetRequest("DELETE", Address, headerBuilder, urlencodedparams);

                            } else {

                                ShowNetError();

                            }
                            break;

                        case "PUT":

                            if (isOnline()) {

                                Log.v(Tag, "Diving Into PUT");
                                //new RequestMaker().execute("PUT", Address, headerBuilder, urlencodedparams);
                                GetRequest("PUT", Address, headerBuilder, urlencodedparams);
                            } else {
                                ShowNetError();

                            }
                            break;
                        default:
                            Log.v(Tag, "Nothing selected in request");
                            new MaterialTapTargetPrompt.Builder(NavDrawerActivityMain.this).setTarget(findViewById(R.id.req_type_spinner)).setPrimaryText("Select the type of request").setPromptBackground(new CirclePromptBackground()).setPromptFocal(new RectanglePromptFocal()).setBackgroundColour(getResources().getColor(R.color.buttonblue)).setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                @Override
                                public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                        Toast.makeText(getApplicationContext(), "presseddddd", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();


                    }


                } else {


                    Toasty.warning(NavDrawerActivityMain.this, "Please enter valid url", Toast.LENGTH_SHORT, true).show();


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

            FirebaseCrash.logcat(Log.ERROR, TAG, "NPE caught");
            FirebaseCrash.report(e);

        }


    }

    private void HighLightButton(final int resource,final String message) {


        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()


            {





                new MaterialTapTargetPrompt.Builder(NavDrawerActivityMain.this).setTarget(resource).setPrimaryText(message).setPromptBackground(new CirclePromptBackground()).setPromptFocal(new RectanglePromptFocal()).setBackgroundColour(getResources().getColor(R.color.buttonblue)).setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                            //toreg
                            //Toast.makeText(getApplicationContext(), "presseddddd", Toast.LENGTH_SHORT).show();
                            Toasty.warning(NavDrawerActivityMain.this, "Please enter valid url", Toast.LENGTH_SHORT, true).show();

                        }
                    }
                }).show();

            }
        }, 1000);
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void intiview() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mHeaderView = navigationView.getHeaderView(0);
        mDrawerHeaderTitle = mHeaderView.findViewById(R.id.headertitle);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        sendButton = findViewById(R.id.sendButton);
        materialBetterSpinner = findViewById(R.id.req_type_spinner);
        UrlField = findViewById(R.id.UrlField);

    }

    private void NetwordDetect() {
        boolean WIFI = false;

        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();

        for (NetworkInfo netInfo : networkInfo) {

            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))

                if (netInfo.isConnected())

                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))

                if (netInfo.isConnected())

                    MOBILE = true;
        }

        if (WIFI == true)

        {
            IPaddress = GetDeviceipWiFiData();
            mDrawerHeaderTitle.setText(IPaddress);
            Log.v("asdasdsadad", IPaddress);


        }

        if (MOBILE == true) {

            IPaddress = GetDeviceipMobileData();
            Log.v("asdasdsadad", IPaddress);
            mDrawerHeaderTitle.setText(IPaddress);

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public String GetDeviceipMobileData() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public String GetDeviceipWiFiData() {

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        @SuppressWarnings("deprecation")

        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        return ip;

    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void ShowNetError() {

        Toasty.warning(NavDrawerActivityMain.this, "No internet Found!", Toast.LENGTH_SHORT, true).show();


    }

    @Override
    protected void onStop() {
        super.onStop();


        Log.v(Tag, "stopppppp");
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
        BusProvider.getBus().unregister(this);
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.home) {


            Toast.makeText(this, "sdsad", Toast.LENGTH_LONG).show();
        }
        if (id == R.id.history) {
            // do something

            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "selected hos", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bookmark) {
//ABC123456789

            Toasty.warning(NavDrawerActivityMain.this, "Coming Soon!", Toast.LENGTH_SHORT, true).show();
        } else if (id == R.id.history) {

            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.settings) {

            Intent intent = new Intent(this, MyPreferencesActivity.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent = new Intent(this, AboutusActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.test) {
            Toasty.warning(NavDrawerActivityMain.this, "Coming Soon!", Toast.LENGTH_SHORT, true).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ParamFragment(), "PARAMETERS");
        adapter.addFragment(new AuthorizationFragment(), "AUTHORIZATION");
        adapter.addFragment(new HeaderFragment(), "HEADER");
        adapter.addFragment(new BodyFragment(), "BODY");
        // adapter.addFragment(new ResponseFragment(), "RESPONSE");
        viewPager.setAdapter(adapter);
    }

    public static boolean isValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onPause() {


        super.onPause();
        //if (dialog != null) dialog.dismiss();
    }

    public void GetRequest(Object... strings) {
        dialog.setMessage("Activating hyperdrive, please wait.");
        dialog.show();
        Log.v(Tag, "======================onPreExecute========================");
        Log.v(Tag, "======================onPreExecute done========================");
        Log.v(Tag, "======================DOINBACKGROUND========================");
        String method = (String) strings[0];
        String urlvalue = (String) strings[1];
        Headers.Builder headerbuilder = (Headers.Builder) strings[2];
        ArrayList<String> paramlist = (ArrayList<String>) strings[3];
        Headers customheader = headerbuilder.build();

        Log.v(Tag, "======================URL CHECK========================");
        Log.v(Tag, "======================BEFORE DETECTION========================");
        Log.v(Tag, urlvalue);
        Log.v(Tag, "======================BEFORE DETECTION========================");
//        if (urlvalue.contains("www") && urlvalue.contains("http://")) {
//            Log.v(Tag, "contains www and http");
//            //urlvalue = "https://" + urlvalue;
//        } else if (urlvalue.contains("www") && urlvalue.contains("https://")) {
//            Log.v(Tag, "contains www and https");
//            //urlvalue = "http://" + urlvalue;
//        } else if (!urlvalue.contains("www"))
//
//
//        {
//            Log.v(Tag, "dont contain www");
//            urlvalue = "http://www." + urlvalue;
//
//
//        } else {
//
//            urlvalue = "http://" + urlvalue;
//
//        }

        if (paramlist.size() > 0) {
            urlvalue = urlvalue + "?";
            for (int i = 0; i < paramlist.size(); i++) {
                urlvalue = urlvalue + paramlist.get(i);
            }


        }
        Log.v(Tag, "======================AFTER DETECTION========================");
        Log.v(Tag, urlvalue);
        Log.v(Tag, "======================AFTER DETECTION========================");


        if (method.equals("GET")) {


            Log.v(Tag, "======================GET========================");
            try {


                OkHttpClient client1 = new OkHttpClient();
                OkHttpClient client = client1.newBuilder().readTimeout(12, TimeUnit.SECONDS).writeTimeout(12, TimeUnit.SECONDS).connectTimeout(12, TimeUnit.SECONDS)

                        .build();

                Request request = new Request.Builder().url(urlvalue).get().headers(customheader).header("User-Agent", "Postman-Android").build();


                final String finalUrlvalue = urlvalue;
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, final IOException e) {

                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    }
                                });

                            }
                        });
                       // NavDrawerActivityMain.flag = "failure";
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!" + e.toString() + "!!!!!!!!!!!!!!");


                        // if (dialog != null) dialog.dismiss();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        NavDrawerActivityMain.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancel();
                            }
                        });


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

                           // NavDrawerActivityMain.flag = "success";
//                                        Bundle bundle = new Bundle();
//                                        bundle.putString("time", "" + (rx - tx));

                            // produceEvent();
                            Log.d(Tag, "===============writing data to shared preference==============this is body data===========>" + bodyy);

                            editor.putString("response", bodyy);
                            editor.putString("headers_full", Headers);
                            editor.putString("code", String.valueOf(responsecode));
                            editor.putString("time", "" + (rx - tx));

                            editor.apply();
                            Log.d(Tag, "===============writing data to shared preference done=========================>");

//                                    TabLayout.Tab tab = tabLayout.getTabAt(4);
//                                    tab.select();


                            Intent intent = new Intent(NavDrawerActivityMain.this, ResultActivity.class);
                            intent.putExtra("url", finalUrlvalue);
                            intent.putExtra("reqtype", "GET");
                            startActivity(intent);
                            // Log.v("amover","amover");


                        } catch (NetworkOnMainThreadException exception) {

                            exception.printStackTrace();
                            Toasty.warning(NavDrawerActivityMain.this, exception.toString(), Toast.LENGTH_SHORT, true).show();
                            //  if (dialog != null) dialog.dismiss();
                        } catch (Exception e) {

                            Log.v(Tag, "exception happened in onreseponse get erquest" + e.toString());

                            Toasty.warning(NavDrawerActivityMain.this, e.toString(), Toast.LENGTH_SHORT, true).show();

                        }


                    }
                });


            } catch (Exception e) {
                Log.v(Tag, "exception happened  get request" + e.toString());

                e.printStackTrace();
                Toasty.warning(NavDrawerActivityMain.this, e.toString(), Toast.LENGTH_SHORT, true).show();
            }

        } else if (method.equals("POST")) {
            try {
                Request request = null;
                Log.v(Tag, "======================POST========================");
                OkHttpClient client = new OkHttpClient();


                String bodyflag = prefs.getString("bodytypeflag", null);
                String rawbody = prefs.getString("rawbody", null);
                switch (bodyflag)
                {

                    case "1":


                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        RequestBody requestBody;
                        Log.v("statusofbodytype", "=============case 1 detected============");
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


                        }


                        requestBody = builder.build();
                        request = new Request.Builder().url(urlvalue).headers(customheader).header("content-type", "multipart/form-data").post(requestBody).build();
                        Log.v("statusofbodytype", "=============case 1 detected============");
                        break;


                    case "2":
                        if (rawbody.length() > 0) {
                            Log.v("statusofbodytype", "=============case 2 detected=======rawbody=====" + rawbody);
                            MediaType mediaType = MediaType.parse("application/json");

                            RequestBody newbody = RequestBody.create(mediaType, rawbody);
                            request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").post(newbody).build();


                        } else {

                            Log.v("error", "error here errorid 112232");

                        }
                        Log.v("statusofbodytype", "=============case 2 detected============");
                        break;


                    case "3":
                        if (rawbody.length() > 0) {
                            Log.v("statusofbodytype", "=============case 3 detected=======rawbody=====" + rawbody);
                            MediaType mediaType = MediaType.parse("application/xml");

                            RequestBody newbody = RequestBody.create(mediaType, rawbody);
                            request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").post(newbody).build();


                        } else {

                            Log.v("error", "error here errorid 112234");

                        }
                        break;


                }


                final String finalUrlvalue1 = urlvalue;
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {

                        //  if (dialog != null) dialog.dismiss();
                        Log.d(Tag, "failure");
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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


                                    Log.d(Tag, "===============writing data to shared preference=========================>" + bodyy);


                                    editor.putString("response", bodyy);
                                    editor.putString("Headers", Headers);
                                    editor.putString("code", String.valueOf(responsecode));
                                    editor.putString("time", "" + (rx - tx));
                                    editor.apply();
                                    Log.d(Tag, "===============writing data to shared preference done=========================>" + bodyy);
                                    //   if (dialog != null) dialog.dismiss();

                                    //here comes response activity
                                    // Intent intent = new Intent(NavDrawerActivityMain.this, ResultActivity.class);
                                    // intent.putExtra("url", finalUrlvalue);

                                    // startActivity(intent);
                                    Intent intent = new Intent(NavDrawerActivityMain.this, ResultActivity.class);
                                    intent.putExtra("url", finalUrlvalue1);
                                    intent.putExtra("reqtype", "POST");
                                    startActivity(intent);
                                } catch (Exception e) {


                                }

                            }
                        });


                    }
                });


            } catch (final Exception e) {


                e.printStackTrace();

            }


        } else if (method.equals("DELETE"))


        {
            try {

                Log.v(Tag, "======================DELETE========================");
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
                    Request request = new Request.Builder().url(urlvalue).headers(customheader).delete(requestBody).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {

                            // if (dialog != null) dialog.dismiss();
                            Log.d(Tag, "failure");
                            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DELETE FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DELETE FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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


                                        Log.d(Tag, "===============writing data to shared preference=========================>" + bodyy);


                                        editor.putString("response", bodyy);
                                        editor.putString("code", String.valueOf(responsecode));
                                        editor.putString("time", "" + (rx - tx));
                                        editor.apply();
                                        Log.d(Tag, "===============writing data to shared preference done=========================>" + bodyy);
                                        //     if (dialog != null) dialog.dismiss();
                                        TabLayout.Tab tab = tabLayout.getTabAt(4);
                                        tab.select();

                                    } catch (Exception e) {


                                    }

                                }
                            });


                        }
                    });


                }


            } catch (final Exception e) {


                e.printStackTrace();

            }

        } else if (method.equals("PUT")) {


            try {

                Log.v(Tag, "======================PUT========================");
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
                    Request request = new Request.Builder().url(urlvalue).headers(customheader).put(requestBody).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {

                            // if (dialog != null) dialog.dismiss();
                            Log.d(Tag, "failure");
                            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!PUT FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!PUT FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
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


                                        Log.d(Tag, "===============writing data to shared preference=========================>" + bodyy);


                                        editor.putString("response", bodyy);
                                        editor.putString("code", String.valueOf(responsecode));
                                        editor.putString("time", "" + (rx - tx));
                                        editor.apply();
                                        Log.d(Tag, "===============writing data to shared preference done=========================>" + bodyy);
                                        //     if (dialog != null) dialog.dismiss();
                                        TabLayout.Tab tab = tabLayout.getTabAt(4);
                                        tab.select();

                                    } catch (Exception e) {


                                    }

                                }
                            });


                        }
                    });


                }


            } catch (final Exception e) {


                e.printStackTrace();

            }


        }

        Log.v(Tag, "======================DOINBACKGROUND END========================");


    }

    public static Context getContext() {


        return instance;

    }

    public String getUrlData() {
        String sss = UrlField.getText().toString();
        return sss;

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (dialog != null) dialog.dismiss();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {


        Log.v("sadasdsadsad", s);

        if (s.equals("response")) {
            loadTimeoutFromPreference(sharedPreferences);

        }

    }

    class GetAsync extends AsyncTask<Object, Long, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setMessage("Activating hyperdrive, please wait.");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            dialog.cancel();
        }

        @Override
        protected Boolean doInBackground(final Object... voids) {


            OkHttpClient client1 = new OkHttpClient();
            OkHttpClient client = client1.newBuilder().readTimeout(12, TimeUnit.SECONDS).writeTimeout(12, TimeUnit.SECONDS).connectTimeout(12, TimeUnit.SECONDS)

                    .build();

            Request request = new Request.Builder().url((String) voids[0]).get().headers((Headers) voids[1]).header("User-Agent", "Postman-Android").build();
            Call call = client.newCall(request);

            try {

                Response response = call.execute();
                if (response.code() == 200) {


                    try {
                        String bodyy = response.body().string();
                        long target = response.body().contentLength();


                        int responsecode = response.code();
                        String Headers = response.headers().toString();
                        long tx = response.sentRequestAtMillis();
                        long rx = response.receivedResponseAtMillis();

                        Log.v(Tag, "======================BODY========================");
                        Log.d(Tag, "GET BODY CONTENT========================================>" + bodyy);
                        Log.d(Tag, "RESPONSE    CODE===========================================>" + String.valueOf(responsecode));
                        Log.d(Tag, "HEADERS         ===========================================>" + Headers);
                        Log.d(Tag, "RESPONSE TIME   ===========================================>" + (rx - tx) + " ms");

                    //    NavDrawerActivityMain.flag = "success";
//                                        Bundle bundle = new Bundle();
//                                        bundle.putString("time", "" + (rx - tx));

                        // produceEvent();
                        Log.d(Tag, "===============writing data to shared preference==============this is body data===========>" + bodyy);

                        editor.putString("response", bodyy);
                        editor.putString("headers_full", Headers);
                        editor.putString("code", String.valueOf(responsecode));
                        editor.putString("time", "" + (rx - tx));

                        editor.apply();
                        Log.d(Tag, "===============writing data to shared preference done=========================>");

//                                    TabLayout.Tab tab = tabLayout.getTabAt(4);
//                                    tab.select();


//                        Intent intent = new Intent(NavDrawerActivityMain.this, ResultActivity.class);
//                        intent.putExtra("url", (String) voids[0]);
//
//                        startActivity(intent);
                        // Log.v("amover","amover");
                        return true;

                    } catch (Exception e) {

                        return false;
                    }

                }


            } catch (Exception e) {
                return false;
            }


//            client.newCall(request).enqueue(new Callback()
//            {
//
//                @Override
//                public void onFailure(Call call, final IOException e) {
//                    Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            // Toast.makeText(NavDrawerActivityMain, e.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                    NavDrawerActivityMain.flag = "failure";
//                    Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!" + e.toString() + "!!!!!!!!!!!!!!");
//
//
//                    // if (dialog != null) dialog.dismiss();
//                }
//
//                @Override
//                public void onResponse(Call call, final Response response) throws IOException
//                {
//
//                    //dialog.setMessage("Activating hyperdrive, please wait.");
//                    // dialog.show();
//
//
//                    try {
//                        String bodyy = response.body().string();
//                        long target = response.body().contentLength();
//
//
//                        int responsecode = response.code();
//                        String Headers = response.headers().toString();
//                        long tx = response.sentRequestAtMillis();
//                        long rx = response.receivedResponseAtMillis();
//
//                        Log.v(Tag, "======================BODY========================");
//                        Log.d(Tag, "GET BODY CONTENT========================================>" + bodyy);
//                        Log.d(Tag, "RESPONSE    CODE===========================================>" + String.valueOf(responsecode));
//                        Log.d(Tag, "HEADERS         ===========================================>" + Headers);
//                        Log.d(Tag, "RESPONSE TIME   ===========================================>" + (rx - tx) + " ms");
//
//                        NavDrawerActivityMain.flag = "success";
////                                        Bundle bundle = new Bundle();
////                                        bundle.putString("time", "" + (rx - tx));
//
//                        // produceEvent();
//                        Log.d(Tag, "===============writing data to shared preference==============this is body data===========>" + bodyy);
//
//                        editor.putString("response", bodyy);
//                        editor.putString("headers_full", Headers);
//                        editor.putString("code", String.valueOf(responsecode));
//                        editor.putString("time", "" + (rx - tx));
//
//                        editor.apply();
//                        Log.d(Tag, "===============writing data to shared preference done=========================>");
//
////                                    TabLayout.Tab tab = tabLayout.getTabAt(4);
////                                    tab.select();
//
//
////                        Intent intent = new Intent(NavDrawerActivityMain.this, ResultActivity.class);
////                        intent.putExtra("url", (String) voids[0]);
////
////                        startActivity(intent);
//                        // Log.v("amover","amover");
//
//
//                    } catch (NetworkOnMainThreadException exception) {
//
//                        exception.printStackTrace();
//                        Toasty.warning(NavDrawerActivityMain.this, "Service Expecting SSL link", Toast.LENGTH_SHORT, true).show();
//                        //  if (dialog != null) dialog.dismiss();
//                    } catch (Exception e) {
//
//                        Log.v(Tag, "exception happened in onreseponse get erquest" + e.toString());
//
//                        Toasty.warning(NavDrawerActivityMain.this, e.toString(), Toast.LENGTH_SHORT, true).show();
//
//                    }
//
//
//                }
//
//            });

            return false;
        }
    }


    private void changeResponseTimeoutTime(int i) {
        Log.v("changedTimeOut", String.valueOf(i));
    }

    private void loadTimeoutFromPreference(SharedPreferences sharedPreferences) {
        int response_time = Integer.parseInt(sharedPreferences.getString("response", ""));
        changeResponseTimeoutTime(response_time);
    }

}



