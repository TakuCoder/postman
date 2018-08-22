package thiyagu.postman.com.postmanandroid.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyh.jsonviewer.library.JsonRecyclerView;

import thiyagu.postman.com.postmanandroid.R;

public class ResponseActivity extends Activity {

    CardView responsetype;

    CardView responsedata;

    TextView status, time, size,data;
    LinearLayout frameLayout_responsetype, frameLayout_responsedata,main_type,jsonplaceholder;
    JsonRecyclerView jsonRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_activity);
        this.setFinishOnTouchOutside(false);
        responsetype = findViewById(R.id.card_view_responsetype);

        responsedata = findViewById(R.id.card_view_responsedata);
        frameLayout_responsetype = findViewById(R.id.frame_ResType);
    data=findViewById(R.id.datatext);
        main_type = findViewById(R.id.main_type);
        frameLayout_responsedata = findViewById(R.id.frame_ResData);
        jsonplaceholder = findViewById(R.id.jsonplaceholder);
        status = findViewById(R.id.status_textview);
        time = findViewById(R.id.time_textview);
        size = findViewById(R.id.size_textview);
        jsonRecyclerView = findViewById(R.id.rv_json);
        SharedPreferences prefs = this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String responsetext = prefs.getString("response", null);
        final String timevalue = prefs.getString("time", null);
        final String codevalue = prefs.getString("code", null);
        status.setText(codevalue+ " OK");
        time.setText("TIME " + timevalue + " ms");
        Log.v("asdsadasdasdsadasd",codevalue);
        size.setText("630 bytes");
        //data.setText(responsetext);

        try
        {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    jsonRecyclerView.setVisibility(View.VISIBLE);
                    Log.v("responsetext2","=====================================");
                    Log.v("responsetext2",responsetext);
                    jsonRecyclerView.bindJson(responsetext);
                    Log.v("responsetext2","=====================================");
                    jsonRecyclerView.setKeyColor(getResources().getColor(R.color.lawn_green));
                    jsonRecyclerView.setValueTextColor(getResources().getColor(R.color.keycolor));
                    jsonRecyclerView.setValueNumberColor(getResources().getColor(R.color.keycolor));
                    jsonRecyclerView.setValueUrlColor(getResources().getColor(R.color.keycolor));

                    data.setVisibility(View.GONE);
                }
            });



        }
        catch(Exception e)
        {

            Log.d("sadasdasdasd", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Exception in ResponseFrag,no valid json found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            data.setVisibility(View.VISIBLE);
            data.setText(responsetext);
            jsonRecyclerView.setVisibility(View.GONE);
            Log.d("sadasdasdasd", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Exception in ResponseFrag,no valid json found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        }




        responsetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (frameLayout_responsetype.getVisibility() == View.VISIBLE) {
                    frameLayout_responsetype.setVisibility(View.GONE);

                } else {

                    frameLayout_responsetype.setVisibility(View.VISIBLE);

                }

            }
        });


        responsedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jsonplaceholder.getVisibility() == View.VISIBLE) {
                    jsonplaceholder.setVisibility(View.GONE);

                } else {

                    jsonplaceholder.setVisibility(View.VISIBLE);

                }
            }
        });
        Log.v("codevalue is",codevalue);
        Log.v("codevalue is","200");
        if(String.valueOf(codevalue).trim().equals("200"))
        {
            main_type.setBackgroundColor(getResources().getColor(R.color.yellow_green));
Log.v("asdsadsadsa","amhere");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            //finish();
            return true;
        }

        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }
    }

