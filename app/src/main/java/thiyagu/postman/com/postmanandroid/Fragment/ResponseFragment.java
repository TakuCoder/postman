package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yuyh.jsonviewer.library.JsonRecyclerView;

import thiyagu.postman.com.postmanandroid.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class ResponseFragment extends Fragment {

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView textView;
        TextView time;
        TextView code;

        // Toast.makeText(getContext(), "onCreate", Toast.LENGTH_LONG).show();
        View view = inflater.inflate(R.layout.tab_fragment_response, container, false);
        Context context = view.getContext();
        textView = view.findViewById(R.id.textView);
        time = view.findViewById(R.id.time);
        code = view.findViewById(R.id.code);
        JsonRecyclerView mRecyclewView = view.findViewById(R.id.rv_json);
// bind json

       // StoreResponse storeResponse = new StoreResponse();
        //Log.v("adsdsdasd",storeResponse.getResponse());


        SharedPreferences prefs = context.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        String responsetext = prefs.getString("response", null);
        String timevalue = prefs.getString("time", null);
        String codevalue = prefs.getString("code", null);
        //textView.setText(responsetext);



        try
        {
            Log.v("sdsdsdsd",responsetext);
            Log.v("sdsdsdsd",timevalue);
            Log.v("sdsdsdsd",codevalue);
            mRecyclewView.bindJson(responsetext);
            textView.setVisibility(View.INVISIBLE);

        }
        catch(Exception e)
        {
            textView.setText(responsetext);
            mRecyclewView.setVisibility(View.GONE);


        }

        time.setText(timevalue);
        code.setText(codevalue);


        try
        {

            if(Integer.valueOf(codevalue)<=99)
            {

                time.setTextColor(Color.parseColor("#0a8108"));
                Log.v("amhere","1");

            }
            else if(Integer.valueOf(codevalue)>=100 && Integer.valueOf(codevalue)<=150)
            {
                time.setTextColor(Color.parseColor("#cddc39"));
                Log.v("amhere","2");

            }
            else  if(Integer.valueOf(codevalue)>=150 && Integer.valueOf(codevalue)<=200)
            {
                time.setTextColor(Color.parseColor("#c62828"));    Log.v("amhere","3");

            }

        }

        catch(Exception e)
        {



        }




        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

}
