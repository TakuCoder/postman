package thiyagu.postman.com.postmanandroid.Activities;

import androidx.room.Room;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import thiyagu.postman.com.postmanandroid.Database.Bookmarks;
import thiyagu.postman.com.postmanandroid.Database.DAO.BookmarkDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HistoryDAO;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.Database.History;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Fragment.JsonFragment;
import thiyagu.postman.com.postmanandroid.Fragment.Preview;
import thiyagu.postman.com.postmanandroid.Fragment.RawFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ViewPagerAdapter;
import thiyagu.postman.com.postmanandroid.R;

public class ResultActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
FloatingActionButton fab;
    LinearLayout bottom_sheet;
    TextView fullheader, url,tx_view_response_code;
    SharedPreferences prefs;
    BottomSheetBehavior sheetBehavior;
    FeedReaderDbHelper feedReaderDbHelper;
    private String Tag="onCreate";
    String method,url_value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final String url_value = getIntent().getStringExtra("url");
        final String req_type = getIntent().getStringExtra("reqtype");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        feedReaderDbHelper =  new FeedReaderDbHelper(this);
        fullheader = findViewById(R.id.fullheader);
        tx_view_response_code = findViewById(R.id.tx_view_response_code);
        viewPager = findViewById(R.id.pager1);
        tabLayout = findViewById(R.id.tab_layout);
        prefs = this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        url = findViewById(R.id.url);




        final String headers_full = prefs.getString("headers_full", null);
        final String duration = prefs.getString("time", null);
        final String response_code = prefs.getString("code", null);
        int responsecode = Integer.valueOf(response_code);



        final SimpleDateFormat timee = new SimpleDateFormat("HH:mm:ss");
        Log.v("asdadsa", timee.format(new Date()));


        final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Log.v("asdadsa", date.format(new Date()));







        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookMarkSave(url_value, date.format(new Date()), timee.format(new Date()),"630bytes", response_code, duration, req_type+" "+response_code, 1);

            }
        });



        switch (responsecode)
        {

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

        AutoSave(url_value, date.format(new Date()), timee.format(new Date()),"630bytes", response_code, duration, req_type+" "+response_code, 1);
        fullheader.setText(headers_full);
        url.setText("URL : " + url_value);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.v("Check-State",newState+"");
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED:


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
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onStop() {
        super.onStop();

    }
    public void AutoSave(String url, String date,String time, String size, String response_code, String Duration, String reqtype, int type) {

        Log.v("autosave", date +
                "\n" + response_code +
                "\n" + url +
                "\n" + size +
                "\n" + Duration +
                "\n");

        //feedReaderDbHelper.addEntryHistory(historyClass);

        TelleriumDataDatabase database = Room.databaseBuilder(ResultActivity.this, TelleriumDataDatabase.class,"data_db").allowMainThreadQueries().build();

        HistoryDAO historyDAO = database.getHistoryDAO();

        History history = new History();
        history.setUrl(url);
        history.setDate(date);
        history.setTime(time);
        history.setSize(size);
        history.setResponse_code(response_code);
        history.setDuration(Duration);
        history.setReqtype(reqtype);
        history.setType(type);
        historyDAO.insert(history);

    }



    public void BookMarkSave(String url, String date,String time, String size, String response_code, String Duration, String reqtype, int type) {


        TelleriumDataDatabase database = Room.databaseBuilder(ResultActivity.this, TelleriumDataDatabase.class,"data_db").allowMainThreadQueries().build();



        BookmarkDAO bookmarkDAO = database.getBookmarkDAO();

        Bookmarks bookmarks = new Bookmarks();
        bookmarks.setUrl(url);
        bookmarks.setDate(date);
        bookmarks.setTime(time);
        bookmarks.setSize(size);
        bookmarks.setResponse_code(response_code);
        bookmarks.setDuration(Duration);
        bookmarks.setReqtype(reqtype);
        bookmarks.setType(type);
        bookmarkDAO.insert(bookmarks);
        Toasty.warning(getApplicationContext(), "Bookmark saved!", Toast.LENGTH_SHORT, true).show();

    }
}
