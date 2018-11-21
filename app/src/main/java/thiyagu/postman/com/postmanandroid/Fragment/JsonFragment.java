package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thiyagu.postman.com.postmanandroid.R;

import static android.content.Context.MODE_PRIVATE;

public class JsonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        com.yuyh.jsonviewer.library.JsonRecyclerView jsonRecyclerView ;


        View view = inflater.inflate(R.layout.fragment_json,container,false);;

        jsonRecyclerView = view.findViewById(R.id.rv_json);
        Context context = view.getContext();

        SharedPreferences prefs = context.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String responsetext = prefs.getString("response", null);
        final String duration = prefs.getString("time", null);
        final String response_code = prefs.getString("code", null);


        try
        {

            jsonRecyclerView.bindJson(responsetext);
        }
        catch (Exception e)
        {


        }



        return view;



    }


}
