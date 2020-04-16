package thiyagu.postman.com.postmanandroid.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import okhttp3.logging.HttpLoggingInterceptor;
import thiyagu.postman.com.postmanandroid.Database.Body;
import thiyagu.postman.com.postmanandroid.Database.DAO.BodyDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HeaderDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.Database.Header;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Database.parameters;
import thiyagu.postman.com.postmanandroid.Event.BusProvider;
import thiyagu.postman.com.postmanandroid.Fragment.AuthorizationFragment;
import thiyagu.postman.com.postmanandroid.Fragment.BodyFragment;
import thiyagu.postman.com.postmanandroid.Fragment.HeaderFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ParamFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ViewPagerAdapter;
import thiyagu.postman.com.postmanandroid.MaterialBetterSpinner;
import thiyagu.postman.com.postmanandroid.R;
import thiyagu.postman.com.postmanandroid.Services.NetChecker;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

public class RequestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener,NetChecker.ConnectionServiceCallback{
//
//    enum RequestType
//    {
//        RED, GREEN, BLUE;
//    }

    private static final String TAG = "TAG";
    MaterialBetterSpinner materialBetterSpinner;
    Button sendButton;
    EditText UrlField;
    FeedReaderDbHelper feedReaderDbHelper;
    Typeface roboto;
    public String ssss;
    public String Tag = "postman-thiyagu";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayout.Tab body;
    private TabLayout.Tab responsetab;
    public static String flag;
    String weburl;
    Context context;
    private static RequestActivity instance;
    private ProgressDialog dialog;
    SharedPreferences prefs;
    private Toolbar toolbar;
    CheckBox checkbox_https;
    String IPaddress;
    NavigationView navigationView;
    DrawerLayout drawer;
    private View mHeaderView;
    private TextView mDrawerHeaderTitle;
    AssetManager assetManager;
    ActionBarDrawerToggle toggle;
    SharedPreferences.Editor editor;
    public int timeout;
    String fullurl;
    boolean https_check;
    boolean sslflag;
    TelleriumDataDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_main);

        intiview();
        context = this;
        setupSharedPreferences();

        Intent intent = new Intent(this, NetChecker.class);
        // Interval in seconds
        intent.putExtra(NetChecker.TAG_INTERVAL, 5);
        // URL to ping
        intent.putExtra(NetChecker.TAG_URL_PING, "http://www.google.com");
        // Name of the class that is calling this service
        intent.putExtra(NetChecker.TAG_ACTIVITY_NAME, this.getClass().getName());
        // Starts the service
        startService(intent);

        // ((MyApplication)getApplicationContext()).getMyComponent().inject(this);

        feedReaderDbHelper = new FeedReaderDbHelper(this);
        checkbox_https = findViewById(R.id.checkbox_https);

        database = Room.databaseBuilder(getApplicationContext(), TelleriumDataDatabase.class, "data_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        prefs = this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        https_check = prefs.getBoolean("https_check", false);
        if (https_check) {

            checkbox_https.setChecked(true);
        } else {
            checkbox_https.setChecked(false);

        }
        // Toast.makeText(getApplicationContext(),String.valueOf(https_check),Toast.LENGTH_LONG).show();
        editor = this.getApplicationContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
        setSupportActionBar(toolbar);
        instance = this;
        checkbox_https.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbox_https.isChecked()) {
                    editor.putBoolean("https_check", true);
                    editor.apply();
                    Log.v("status", "true");
                } else {

                    editor.putBoolean("https_check", false);
                    editor.apply();
                    Log.v("status", "false");
                }

            }
        });
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        dialog = new ProgressDialog(RequestActivity.this);
        dialog.setCancelable(false);

//        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
//        SpannableStringBuilder SS = new SpannableStringBuilder("POSTMAN-ANDROID");
//        SS.setSpan(new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        getSupportActionBar().setTitle(SS);

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
            public void onClick(View v) {
                RequestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setMessage("Activating hyperdrive, please wait.");
                        dialog.show();
                    }
                });

                SharedPreferences sharedPreferences = RequestActivity.this.getSharedPreferences("thiyagu.postman.com.postmanandroid_preferences", MODE_PRIVATE);
                String status = sharedPreferences.getString("CertPicker", "");
                sslflag = sharedPreferences.getBoolean("sslverify", false);

                //Log.v("sdfdsfdsf", String.valueOf(sslflag));
                timeout = Integer.valueOf(sharedPreferences.getString("timeout", ""));

                Log.v("status", status);
                if (status == "DEFAULT") {

                    Log.v("status", "loading default cert");
                } else {
                    Log.v("status", "loading user cert");

                }


//                checkbox_https.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            editor.putBoolean("https_check", true);
//                            editor.apply();
//                            Log.v("status","true");
//                        } else {
//
//                            editor.putBoolean("https_check", false);
//                            editor.apply();
//                            Log.v("status","false");
//                        }
//                    }
//                });
                Log.v("mypreference", status);
                weburl = UrlField.getText().toString();
                if (isHttps()) {
                    fullurl = "https://" + weburl;

                } else {
                    fullurl = "http://" + weburl;

                }


                Log.v("fdfdfd", weburl);
                editor.putString("url_value", weburl);
                editor.putString("req_value", materialBetterSpinner.getText().toString());
                editor.apply();

                if (isValid(fullurl)) {


                    // feedReaderDbHelper = new FeedReaderDbHelper(RequestActivity.this);
                    ArrayList<String> headerlist = feedReaderDbHelper.getAllHeader();
                    HeaderDAO headerDAO = database.getHeaderDAO();
                    List<Header> headers=  headerDAO.getHeaders();

                    Headers.Builder headerBuilder = new Headers.Builder();
                    if (headerlist.size() > 0)
                    {
                        Log.v(Tag, "=======================adding headers=========================");
                        for (int i = 0; i < headerlist.size(); i++) {

                            String[] subvalue = headerlist.get(i).split("@@");
                            Log.v(Tag+"thiyagu", subvalue[1] + subvalue[2]);
                            headerBuilder.add(subvalue[1], subvalue[2]);
                        }
                    }
                    Log.v(Tag, "=======================added headers=========================");

                    try {
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


                    } catch (Exception e) {

                        Log.v(Tag, "Exception while clicking send button" + e.toString());

                        e.printStackTrace();

                        // Crashlytics.log(Log.ERROR, TAG, "NPE caught!");
                        // Crashlytics.logException(ex);
                        //Toasty.error(getApplicationContext(),e.toString());
                    }




                    ParametersDAO bodyDAO = database.getParametersDAO();
                    List<parameters> parameters = bodyDAO.getParam();
                    ArrayList<String> urlencodedparams = new ArrayList<>();
                    if(parameters.size()>0)
                    {
                        Log.v(Tag, "Adding Params====================> ");
                        for (int i = 0; i < parameters.size(); i++)
                        {

                          //  S//tring[] subvalue = parameters.get(i).split("@@");


                           // Log.v(Tag, "param1=========>" + subvalue[0]);
                           // Log.v(Tag, "param1=========>" + subvalue[1]);

                            if (i != parameters.size() - 1)
                            {
                                urlencodedparams.add(i, parameters.get(i).getKey() + "=" + parameters.get(i).getValue() + "&");

                            } else {
                                urlencodedparams.add(i, parameters.get(i).getKey() + "=" + parameters.get(i).getValue());

                            }


                        }

                    }





//                    ArrayList<String> paramlist = feedReaderDbHelper.getAllParam();
//                    ArrayList<String> urlencodedparams = new ArrayList<>();
//                    if (paramlist.size() > 0)
//                    {
//                        Log.v(Tag, "Adding Params====================> ");
//                        for (int i = 0; i < paramlist.size(); i++)
//                        {
//
//                            String[] subvalue = paramlist.get(i).split("@@");
//
//
//                            Log.v(Tag, "param1=========>" + subvalue[0]);
//                            Log.v(Tag, "param1=========>" + subvalue[1]);
//
//                            if (i != paramlist.size() - 1)
//                            {
//                                urlencodedparams.add(i, subvalue[1] + "=" + subvalue[2] + "&");
//
//                            } else {
//                                urlencodedparams.add(i, subvalue[1] + "=" + subvalue[2]);
//
//                            }
//
//
//                        }
//                    }

                    Log.v(Tag, "Added Params====================> ");


                    String seletecvalue = materialBetterSpinner.getText().toString();
                    // String Address = UrlField.getText().toString();
                    switch (seletecvalue) {


                        case "GET":

                            Log.v(Tag, "Diving Into GET");
                            if (isOnline()) {
                                // new RequestMaker().execute("GET", Address, headerBuilder, urlencodedparams);
                                GetRequest("GET", fullurl, headerBuilder, urlencodedparams);
                            } else {

                                ShowNetError();

                            }


                            break;

                        case "POST":

                            Log.v(Tag, "Diving Into POST");
                            if (isOnline()) {

                               // ArrayList<String> part = feedReaderDbHelper.getAllBody();


                                //BodyDAO bodyDAO1 = database.getbodyDAO();
                                //List<Body>body= bodyDAO1.getBody();

                               // final String rawbody = prefs.getString("rawbody", null);
                               // Log.v(Tag, "======================part size========================" + String.valueOf(body.size()));
                                GetRequest("POST", fullurl, headerBuilder, urlencodedparams);


                            } else {

                                ShowNetError();

                            }
                            break;

                        case "DELETE":

                            Log.v(Tag, "Diving Into DELETE");

                            if (isOnline()) {
                                //new RequestMaker().execute("DELETE", Address, headerBuilder, urlencodedparams);
                                GetRequest("DELETE", fullurl, headerBuilder, urlencodedparams);

                            } else {

                                ShowNetError();

                            }
                            break;

                        case "PUT":

                            if (isOnline()) {

                                Log.v(Tag, "Diving Into PUT");
                                //new RequestMaker().execute("PUT", Address, headerBuilder, urlencodedparams);
                                GetRequest("PUT", fullurl, headerBuilder, urlencodedparams);
                            } else {
                                ShowNetError();

                            }
                            break;
                        default:
                            Log.v(Tag, "Nothing selected in request");
                            new MaterialTapTargetPrompt.Builder(RequestActivity.this).setTarget(findViewById(R.id.req_type_spinner)).setPrimaryText("Select the type of request").setPromptBackground(new CirclePromptBackground()).setPromptFocal(new RectanglePromptFocal()).setBackgroundColour(getResources().getColor(R.color.buttonblue)).setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                @Override
                                public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                        Toast.makeText(getApplicationContext(), "presseddddd", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();


                    }


                } else {


                    Toasty.warning(RequestActivity.this, "Please enter valid url", Toast.LENGTH_SHORT, true).show();
                    if (dialog != null) {
                        dialog.dismiss();
                    }

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

        if (WIFI) {
            IPaddress = GetDeviceipWiFiData();
            mDrawerHeaderTitle.setText(IPaddress);
            Log.v("asdasdsadad", IPaddress);


        }

        if (MOBILE) {

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

        Toasty.warning(RequestActivity.this, "No internet Found!", Toast.LENGTH_SHORT, true).show();


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
    public boolean onOptionsItemSelected(MenuItem _item) {
        int id = _item.getItemId();

        if (id == R.id.save_collections) {


            Toast.makeText(this, "sdsad", Toast.LENGTH_LONG).show();


            JsonObject info = new JsonObject();
            info.addProperty("_postman_id","942b9d0a-01db-48c4-a3e1-e52a5824681a");
            info.addProperty("name","sample");
            info.addProperty("schema","https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

            JsonArray item = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name","sample");
            item.add(jsonObject);

        }
//        if (id == R.id.history) {
//            // do something
//
//            Intent intent = new Intent(this, HistoryActivity.class);
//            startActivity(intent);
//            Toast.makeText(getApplicationContext(), "selected hos", Toast.LENGTH_LONG).show();
//        }
        return super.onOptionsItemSelected(_item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.Collections) {
//ABC123456789

            Intent intent = new Intent(this, CollectionsActivity.class);
            startActivity(intent);

        }

        if (id == R.id.bookmark) {
//ABC123456789

            Toasty.warning(RequestActivity.this, "Coming Soon!", Toast.LENGTH_SHORT, true).show();
        } else if (id == R.id.history) {

            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.settings) {

            Intent intent = new Intent(this, MyPreferencesActivity.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.test) {
            Toasty.warning(RequestActivity.this, "Coming Soon!", Toast.LENGTH_SHORT, true).show();
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

                Log.v("asdsadsadasdas", String.valueOf(timeout));
                OkHttpClient client = getClientbasedOnHttp(sslflag);
                OkHttpClient getClient = client.newBuilder().connectTimeout(timeout, TimeUnit.SECONDS)


                        .build();


                Request request = new Request.Builder().url(urlvalue).get().headers(customheader).header("User-Agent", "Postman-Android").build();


                final String finalUrlvalue = urlvalue;
                getClient.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, final IOException e) {
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!show popup here!!!!!!!!!!!!!!!!!!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                showPopup(e.toString());
                            }
                        });
                        RequestActivity.flag = "failure";
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!GET FAILURE!!!!!!!!!!!!!!!!!!" + e.toString() + "!!!!!!!!!!!!!!");


                        // if (dialog != null) dialog.dismiss();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        dialog.dismiss();

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

                            RequestActivity.flag = "success";
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


                            Intent intent = new Intent(RequestActivity.this, ResultActivity.class);
                            intent.putExtra("url", finalUrlvalue);
                            intent.putExtra("reqtype", "GET");
                            startActivity(intent);
                            // Log.v("amover","amover");


                        } catch (NetworkOnMainThreadException exception) {

                            exception.printStackTrace();
                            Toasty.warning(RequestActivity.this, "Service Expecting SSL link", Toast.LENGTH_SHORT, true).show();
                            //  if (dialog != null) dialog.dismiss();
                        } catch (Exception e) {

                            Log.v(Tag, "exception happened in onreseponse get erquest" + e.toString());

                            Toasty.warning(RequestActivity.this, e.toString(), Toast.LENGTH_SHORT, true).show();

                        }


                    }
                });


            } catch (Exception e) {
                Log.v(Tag, "exception happened  get request" + e.toString());

                e.printStackTrace();
                Toasty.warning(RequestActivity.this, e.toString(), Toast.LENGTH_SHORT, true).show();
            }

        } else if (method.equals("POST"))
        {
            try {
                Request request = null;
                Log.v(Tag, "======================POST========================");
                OkHttpClient client = getClientbasedOnHttp(sslflag);
                OkHttpClient postClient = client.newBuilder().connectTimeout(timeout, TimeUnit.SECONDS)


                        .build();


                String bodyflag = prefs.getString("bodytypeflag", null);
                String rawbody = prefs.getString("rawbody", null);

                if (bodyflag == null) {

                    bodyflag = "4";

                }
                switch (bodyflag) {

                    case "1":


                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        RequestBody requestBody;
                        Log.v("statusofbodytype", "=============case 1 detected============");
                        builder.setType(MultipartBody.FORM);









                        // ArrayList<String> part = feedReaderDbHelper.getAllBody();


                        BodyDAO bodyDAO1 = database.getbodyDAO();
                        List<Body>body= bodyDAO1.getBodyFlagged();


                      //   Log.v(Tag, "======================part size========================" + String.valueOf(body.size()));



                       // ArrayList<String> part = feedReaderDbHelper.getAllBody();
                        Log.v(Tag, "======================part size========================" + String.valueOf(body.size()));
                        String[] subvalue = null;


                        if (body.size() > 0) {

                            Log.v(Tag, "====================Adding Builder=========================================");


                            for (int i = 0; i < body.size(); i++) {


                                try {
                                  //  subvalue = part.get(i).split("@@");


                                    Log.v(Tag, "builder" + i + body.get(i).getKey());
                                    Log.v(Tag, "builder" + i + body.get(i).getValue());
                                    builder.addFormDataPart(body.get(i).getKey(), body.get(i).getValue());


                                } catch (Exception e) {
                                    Log.v(Tag, "exception happened while adding builder in post");
                                    Log.v(Tag, e.toString());

                                }

                            }


                        }

try
{

    requestBody = builder.build();
    request = new Request.Builder().url(urlvalue).headers(customheader).header("content-type", "multipart/form-data").post(requestBody).build();
    Log.v("statusofbodytype", "=============case 1 detected============");
    break;
}
catch(Exception e)
{
if(dialog!=null)
{

    dialog.dismiss();
}
    showPopup(e.toString());
}




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

                    case "4":

                        //   Log.v("statusofbodytype", "=============case 4 detected=======rawbody=====" + rawbody);
                        //MediaType mediaType = MediaType.parse("application/xml");

                        RequestBody newbody = RequestBody.create(null, "");
                        request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").post(newbody).build();
                        break;

                }


                final String finalUrlvalue1 = urlvalue;
                postClient.newCall(request).enqueue(new Callback()
                {
                    @Override
                    public void onFailure(Call call, final IOException e) {

                        //  if (dialog != null) dialog.dismiss();
                        Log.d(Tag, "failure");
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                showPopup(e.toString());
                            }
                        });
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        dialog.dismiss();
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
                                    // Intent intent = new Intent(RequestActivity.this, ResultActivity.class);
                                    // intent.putExtra("url", finalUrlvalue);

                                    // startActivity(intent);
                                    Intent intent = new Intent(RequestActivity.this, ResultActivity.class);
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


        }


        else if (method.equals("DELETE"))
        {
            try {
                Request request = null;
                Log.v(Tag, "======================POST========================");
                OkHttpClient client = getClientbasedOnHttp(sslflag);
                OkHttpClient postClient = client.newBuilder().connectTimeout(timeout, TimeUnit.SECONDS)


                        .build();


                String bodyflag = prefs.getString("bodytypeflag", null);
                String rawbody = prefs.getString("rawbody", null);

                if (bodyflag == null) {

                    bodyflag = "4";

                }
                switch (bodyflag) {

                    case "1":


                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        RequestBody requestBody;
                        Log.v("statusofbodytype", "=============case 1 detected============");
                        builder.setType(MultipartBody.FORM);

                        ArrayList<String> part = feedReaderDbHelper.getAllBody();

                      BodyDAO bodyDAO = database.getbodyDAO();
                      List<Body> bodylist = bodyDAO.getBody();


                        Log.v(Tag, "======================part size========================" + String.valueOf(bodylist.size()));
                        String[] subvalue = null;


                        if (part.size() > 0) {

                            Log.v(Tag, "====================Adding Builder=========================================");


                            for (int i = 0; i < part.size(); i++) {


                                try {
                                    subvalue = part.get(i).split("@@");


                                    Log.v(Tag, "builder" + i +bodylist.get(i).getKey());
                                    Log.v(Tag, "builder" + i + bodylist.get(i).getValue());
                                    builder.addFormDataPart(bodylist.get(i).getKey(), bodylist.get(i).getValue());


                                } catch (Exception e) {
                                    Log.v(Tag, "exception happened while adding builder in post");
                                    Log.v(Tag, e.toString());

                                }

                            }


                        }


                        requestBody = builder.build();
                        request = new Request.Builder().url(urlvalue).headers(customheader).header("content-type", "multipart/form-data").delete(requestBody).build();
                        Log.v("statusofbodytype", "=============case 1 detected============");
                        break;


                    case "2":
                        if (rawbody.length() > 0) {
                            Log.v("statusofbodytype", "=============case 2 detected=======rawbody=====" + rawbody);
                            MediaType mediaType = MediaType.parse("application/json");

                            RequestBody newbody = RequestBody.create(mediaType, rawbody);
                            request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").delete(newbody).build();


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
                            request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").delete(newbody).build();


                        } else {

                            Log.v("error", "error here errorid 112234");

                        }
                        break;

                    case "4":

                        //   Log.v("statusofbodytype", "=============case 4 detected=======rawbody=====" + rawbody);
                        //MediaType mediaType = MediaType.parse("application/xml");

                        RequestBody newbody = RequestBody.create(null, "");
                        request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").delete(newbody).build();
                        break;

                }


                final String finalUrlvalue1 = urlvalue;
                postClient.newCall(request).enqueue(new Callback()
                {
                    @Override
                    public void onFailure(Call call, final IOException e) {

                        //  if (dialog != null) dialog.dismiss();
                        Log.d(Tag, "failure");
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                showPopup(e.toString());
                            }
                        });
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        dialog.dismiss();
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
                                    // Intent intent = new Intent(RequestActivity.this, ResultActivity.class);
                                    // intent.putExtra("url", finalUrlvalue);

                                    // startActivity(intent);
                                    Intent intent = new Intent(RequestActivity.this, ResultActivity.class);
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


        } else if (method.equals("PUT"))
        {
            try {
                Request request = null;
                Log.v(Tag, "======================POST========================");
                OkHttpClient client = getClientbasedOnHttp(sslflag);
                OkHttpClient postClient = client.newBuilder().connectTimeout(timeout, TimeUnit.SECONDS)


                        .build();


                String bodyflag = prefs.getString("bodytypeflag", null);
                String rawbody = prefs.getString("rawbody", null);

                if (bodyflag == null) {

                    bodyflag = "4";

                }
                switch (bodyflag) {

                    case "1":


                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        RequestBody requestBody;
                        Log.v("statusofbodytype", "=============case 1 detected============");
                        builder.setType(MultipartBody.FORM);





                        ArrayList<String> part = feedReaderDbHelper.getAllBody();
                        Log.v(Tag, "======================part size========================" + String.valueOf(part.size()));
                        String[] subvalue = null;


                        if (part.size() > 0) {

                            Log.v(Tag, "====================Adding Builder=========================================");


                            for (int i = 0; i < part.size(); i++) {


                                try {
                                    subvalue = part.get(i).split("@@");

                                    BodyDAO bodyDAO = database.getbodyDAO();
                                    List<Body> bodylist = bodyDAO.getBody();

                                    Log.v(Tag, "builder" + i + bodylist.get(i).getKey());
                                    Log.v(Tag, "builder" + i + bodylist.get(i).getValue());
                                    builder.addFormDataPart(bodylist.get(i).getKey(),bodylist.get(i).getValue());


                                } catch (Exception e) {
                                    Log.v(Tag, "exception happened while adding builder in post");
                                    Log.v(Tag, e.toString());

                                }

                            }


                        }


                        requestBody = builder.build();
                        request = new Request.Builder().url(urlvalue).headers(customheader).header("content-type", "multipart/form-data").put(requestBody).build();
                        Log.v("statusofbodytype", "=============case 1 detected============");
                        break;


                    case "2":
                        if (rawbody.length() > 0) {
                            Log.v("statusofbodytype", "=============case 2 detected=======rawbody=====" + rawbody);
                            MediaType mediaType = MediaType.parse("application/json");

                            RequestBody newbody = RequestBody.create(mediaType, rawbody);
                            request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").put(newbody).build();


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
                            request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").put(newbody).build();


                        } else {

                            Log.v("error", "error here errorid 112234");

                        }
                        break;

                    case "4":

                        //   Log.v("statusofbodytype", "=============case 4 detected=======rawbody=====" + rawbody);
                        //MediaType mediaType = MediaType.parse("application/xml");

                        RequestBody newbody = RequestBody.create(null, "");
                        request = new Request.Builder().url(urlvalue).header("User-Agent", "Postman-Android").header("connection", "Keep-Alive").put(newbody).build();
                        break;

                }


                final String finalUrlvalue1 = urlvalue;
                postClient.newCall(request).enqueue(new Callback()
                {
                    @Override
                    public void onFailure(Call call, final IOException e) {

                        //  if (dialog != null) dialog.dismiss();
                        Log.d(Tag, "failure");
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                showPopup(e.toString());
                            }
                        });
                        Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!POST FAILURE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        dialog.dismiss();
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
                                    // Intent intent = new Intent(RequestActivity.this, ResultActivity.class);
                                    // intent.putExtra("url", finalUrlvalue);

                                    // startActivity(intent);
                                    Intent intent = new Intent(RequestActivity.this, ResultActivity.class);
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


        }

        Log.v(Tag, "======================DOINBACKGROUND END========================");


    }

    public static Context getContext() {


        return instance;

    }

    public String getUrlData() {
        //String sss = UrlField.getText().toString();
        return weburl;

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (dialog != null) dialog.dismiss();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {


        Log.v("sadasdsadsad", s);

        if (s.equals("timeout")) {
            loadTimeoutFromPreference(sharedPreferences);

        }
        if (s.equals("sslverify")) {
            loadSslStatus(sharedPreferences);


        }

    }

    private void loadSslStatus(SharedPreferences sharedPreferences) {
        sslflag = sharedPreferences.getBoolean("sslverify", false);
        // Log.v("sslstaus",sslStatusflag);

        //changeResponseTimeoutTime(response_time);
    }

    private void changeResponseTimeoutTime(int i) {
        Log.v("changedTimeOut", String.valueOf(i));
    }

    private void loadTimeoutFromPreference(SharedPreferences sharedPreferences) {
        int response_time = Integer.parseInt(sharedPreferences.getString("timeout", ""));
        Log.v("timeout", String.valueOf(response_time));
        timeout = response_time;
        changeResponseTimeoutTime(response_time);
    }


    public boolean isHttps() {
        if (checkbox_https.isChecked()) {
            return true;

        } else return false;

    }


    public OkHttpClient getClientbasedOnHttp(boolean flag) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        if (!flag) {


            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient okHttpClient = builder.addInterceptor(interceptor).build();
                return okHttpClient;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } else {
            //OkHttpClient client = new OkHttpClient();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.addInterceptor(interceptor).build();
            return okHttpClient;

        }


    }

    public void showPopup(String message) {


        final Dialog dialog = new Dialog(RequestActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(message);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    @Override
    public void hasInternetConnection() {
        Log.v("asdasdsd","yes");
    }

    @Override
    public void hasNoInternetConnection() {
        Log.v("asdasdsd","no");
    }
}




