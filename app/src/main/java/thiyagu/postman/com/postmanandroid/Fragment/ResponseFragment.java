package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.yuyh.jsonviewer.library.JsonRecyclerView;

import java.util.ArrayList;

import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class ResponseFragment extends Fragment {
    TextView textView;
    TextView time;
    TextView code;
    TextView status,timee,error;
    Typeface roboto;
    public String Tag = "postman-trace";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == -1) {

            }
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v("whichfragment","ResponseFragment");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Toast.makeText(getContext(), "onCreate", Toast.LENGTH_LONG).show();
        View view = inflater.inflate(R.layout.tab_fragment_response, container, false);
        Context context = view.getContext();
        textView = view.findViewById(R.id.textView);
        time = view.findViewById(R.id.time);
        code = view.findViewById(R.id.code);
        status=view.findViewById(R.id.status);
        timee=view.findViewById(R.id.timee);
        error= view.findViewById(R.id.error);
        final JsonRecyclerView mRecyclewView = view.findViewById(R.id.rv_json);
        AssetManager assetManager = context.getAssets();
        roboto = Typeface.createFromAsset(assetManager,"fonts/Roboto-Bold.ttf");

        time.setTypeface(roboto);
        code.setTypeface(roboto);
        status.setTypeface(roboto);
        timee.setTypeface(roboto);



        SharedPreferences prefs = context.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String responsetext = prefs.getString("response", null);
        final String timevalue = prefs.getString("time", null);
        final String codevalue = prefs.getString("code", null);
//        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
//        editor.putString("response", "");
//        editor.putString("code", "");
//        editor.putString("time", "");
//        editor.apply();
        //textView.setText(responsetext);



        try
        {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRecyclewView.setVisibility(View.VISIBLE);
                    Log.v("responsetext2","=====================================");
                    Log.v("responsetext2",responsetext);
                    mRecyclewView.bindJson(responsetext);
                    Log.v("responsetext2","=====================================");
                    mRecyclewView.setKeyColor(getResources().getColor(R.color.lawn_green));
                    mRecyclewView.setValueTextColor(getResources().getColor(R.color.keycolor));
                    mRecyclewView.setValueNumberColor(getResources().getColor(R.color.keycolor));
                    mRecyclewView.setValueUrlColor(getResources().getColor(R.color.keycolor));

                    textView.setVisibility(View.INVISIBLE);
                }
            });



        }
        catch(Exception e)
        {

            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Exception in ResponseFrag,no valid json found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            textView.setVisibility(View.VISIBLE);
            textView.setText(responsetext);
            mRecyclewView.setVisibility(View.GONE);
            Log.d(Tag, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Exception in ResponseFrag,no valid json found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        }

        time.setText(timevalue+ "ms");
        String val = time.getText().toString();
        if(val.contains("null"))
        {
            Log.v("statestate","its null");
            textView.setVisibility(View.INVISIBLE);
            code.setVisibility(View.INVISIBLE);
            time.setVisibility(View.INVISIBLE);
            status.setVisibility(View.INVISIBLE);
            timee.setVisibility(View.INVISIBLE);
            error.setVisibility(View.VISIBLE);
            error.setTypeface(roboto);



        }
        code.setText(codevalue);


        try
        {
            int msvalue=Integer.valueOf(timevalue);

            if(msvalue>=0 && msvalue<=99)
            {

                time.setTextColor(Color.parseColor("#0a8108"));
                Log.v("amhere","1"+msvalue);

            }
            else if(msvalue>=100 && msvalue<=199)
            {
                time.setTextColor(Color.parseColor("#cddc39"));
                Log.v("amhere","2"+msvalue);

            }
            else  if(msvalue>=200)
            {
                time.setTextColor(Color.parseColor("#c62828"));    Log.v("amhere","3"+msvalue);

            }

        }

        catch(Exception e)
        {



        }

        try
        {
            int responsecodevalue=Integer.valueOf(codevalue);

            if(responsecodevalue==200 ||responsecodevalue==201 ||responsecodevalue==202||responsecodevalue==203||responsecodevalue==204||responsecodevalue==205||responsecodevalue==206||responsecodevalue==207 || responsecodevalue==208 ||responsecodevalue==226)
            {

                code.setTextColor(getResources().getColor(R.color.lawn_green));
                Log.v("amhere","1"+responsecodevalue);

            }
            else if(responsecodevalue>=400 && responsecodevalue<=499)
            {
                code.setTextColor(Color.parseColor("#dc3838"));
                Log.v("amhere","2"+responsecodevalue);

            }
            else if(responsecodevalue>=400 && responsecodevalue<=499)
            {
                code.setTextColor(Color.parseColor("#dc3838"));
                Log.v("amhere","2"+responsecodevalue);

            }

        }

        catch(Exception e)
        {



        }


        return view;
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser)
//    {
//        super.setUserVisibleHint(isVisibleToUser);
//
//    }


}
