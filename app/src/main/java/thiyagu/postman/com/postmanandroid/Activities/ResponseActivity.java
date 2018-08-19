package thiyagu.postman.com.postmanandroid.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import thiyagu.postman.com.postmanandroid.R;

public class ResponseActivity extends Activity {

    CardView responsetype;

    CardView responsedata;

    TextView status, time, size;
    LinearLayout frameLayout_responsetype, frameLayout_responsedata,main_type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_activity);

        responsetype = findViewById(R.id.card_view_responsetype);

        responsedata = findViewById(R.id.card_view_responsedata);
        frameLayout_responsetype = findViewById(R.id.frame_ResType);

        main_type = findViewById(R.id.main_type);
        frameLayout_responsedata = findViewById(R.id.frame_ResData);
        status = findViewById(R.id.status_textview);
        time = findViewById(R.id.time_textview);
        size = findViewById(R.id.size_textview);

        SharedPreferences prefs = this.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String responsetext = prefs.getString("response", null);
        final String timevalue = prefs.getString("time", null);
        final String codevalue = prefs.getString("code", null);
        status.setText(codevalue+ " OK");
        time.setText("TIME " + timevalue + " ms");
        Log.v("asdsadasdasdsadasd",codevalue);
        size.setText("630 bytes");
//        responsetype.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if (frameLayout_responsetype.getVisibility() == View.VISIBLE) {
//                    frameLayout_responsetype.setVisibility(View.GONE);
//
//                } else {
//
//                    frameLayout_responsetype.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//        });


        responsedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameLayout_responsedata.getVisibility() == View.VISIBLE) {
                    frameLayout_responsedata.setVisibility(View.GONE);

                } else {

                    frameLayout_responsedata.setVisibility(View.VISIBLE);

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
}
