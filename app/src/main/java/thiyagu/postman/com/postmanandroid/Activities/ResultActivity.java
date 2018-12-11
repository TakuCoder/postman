package thiyagu.postman.com.postmanandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import thiyagu.postman.com.postmanandroid.Event.BusProvider;
import thiyagu.postman.com.postmanandroid.Fragment.AuthorizationFragment;
import thiyagu.postman.com.postmanandroid.Fragment.BodyFragment;
import thiyagu.postman.com.postmanandroid.Fragment.HeaderFragment;
import thiyagu.postman.com.postmanandroid.Fragment.JsonFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ParamFragment;
import thiyagu.postman.com.postmanandroid.Fragment.Preview;
import thiyagu.postman.com.postmanandroid.Fragment.RawFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ViewPagerAdapter;
import thiyagu.postman.com.postmanandroid.HistoryActivity;
import thiyagu.postman.com.postmanandroid.R;

public class ResultActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    ViewPagerAdapter viewPagerAdapter;
    LinearLayout bottom_sheet;
    Button toggle;
    TextView fullheader, url, response_code_text, tx_view_response_code;

    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String url_value = getIntent().getStringExtra("url");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.pager1);
        tabLayout = findViewById(R.id.tab_layout);
        setSupportActionBar(toolbar);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        fullheader = findViewById(R.id.fullheader);
        tx_view_response_code = findViewById(R.id.tx_view_response_code);
        url = findViewById(R.id.url);
        //response_code = findViewById(R.id.response_code);
        SharedPreferences prefs = this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String headers_full = prefs.getString("headers_full", null);
        final String duration = prefs.getString("time", null);
        final String response_code = prefs.getString("code", null);
        int responsecode = Integer.valueOf(response_code);

        switch (responsecode) {

            case 200:
                //200 (OK)
                tx_view_response_code.setText(responsecode + "(OK)");
                tx_view_response_code.setTextColor(getResources().getColor(R.color.green));
                break;
//green
            case 201:
                //201 (Created)
                tx_view_response_code.setTextColor(getResources().getColor(R.color.green));
                tx_view_response_code.setText(responsecode + "(Created)");
                break;

            case 202:
                //202 (Accepted)
                tx_view_response_code.setTextColor(getResources().getColor(R.color.green));
                tx_view_response_code.setText(responsecode + "(Accepted)");
                break;

            case 204:
                //204 (No Content)
                tx_view_response_code.setTextColor(getResources().getColor(R.color.green));
                tx_view_response_code.setText(responsecode + "(No Content)");
                break;

//blue
            case 301:
                // 301 (Moved Permanently)
                tx_view_response_code.setTextColor(getResources().getColor(R.color.blue));
                tx_view_response_code.setText(responsecode + "(Moved Permanently)");
                break;


            case 302:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.blue));
                // 302 (Found)
                tx_view_response_code.setText(responsecode + "(Found)");
                break;


            case 303:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.blue));
                // 303 (See Other)
                tx_view_response_code.setText(responsecode + "(See Other)");
                break;


            case 304:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.blue));
                // 304 (Not Modified)
                tx_view_response_code.setText(responsecode + "(Not Modified)");

            case 307:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.blue));
                // 307 (Temporary Redirect)
                tx_view_response_code.setText(responsecode + "(Temporary Redirect)");
                break;

            case 400:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.yellow));
                // 400 (Bad Request)
                tx_view_response_code.setText(responsecode + "(Bad Request)");
                break;

            case 401:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.yellow));
                // 401 (Unauthorized)
                tx_view_response_code.setText(responsecode + "(Unauthorized)");
                break;

            case 403:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.yellow));
                tx_view_response_code.setText(responsecode + "(Forbidden)");
                // 403 (Forbidden)
                break;

            case 404:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.yellow));
                tx_view_response_code.setText(responsecode + " (Not Found)");
                // 404 (Not Found)
                break;

            case 405:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.yellow));
                // 405 (Method Not Allowed)
                tx_view_response_code.setText(responsecode + "(Method Not Allowed)");
                break;


            case 406:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.yellow));
                // 406 (Not Acceptable)
                tx_view_response_code.setText(responsecode + "(Not Acceptable)");
                break;
//yellow
            case 412:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.yellow));
                // 412 (Precondition Failed)
                tx_view_response_code.setText(responsecode + "(Precondition Failed)");
                break;

            case 500:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.red));
                // 500 (Internal Server Error)
                tx_view_response_code.setText(responsecode + "Internal Server Error)");
                break;

            case 501:
                tx_view_response_code.setTextColor(getResources().getColor(R.color.red));
                // 501 (Not Implemented)
                tx_view_response_code.setText(responsecode + "(Not Implemented)");
                break;


        }

        fullheader.setText(headers_full);
        //response_code_text.setText(response_code);
        url.setText("URL : " + url_value);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        // btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_response, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.info) {
            // do something
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                // btnBottomSheet.setText("Close sheet");
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                // btnBottomSheet.setText("Expand sheet");
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RawFragment(), "RAW");
        adapter.addFragment(new JsonFragment(), "JSON");
        adapter.addFragment(new Preview(), "PREVIEW");
        // adapter.addFragment(new ResponseFragment(), "RESPONSE");
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onStop() {
        super.onStop();

    }
}
