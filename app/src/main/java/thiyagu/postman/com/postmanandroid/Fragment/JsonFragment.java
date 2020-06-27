package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thiyagu.postman.com.postmanandroid.R;

import static android.content.Context.MODE_PRIVATE;

public class JsonFragment extends Fragment {
TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        com.yuyh.jsonviewer.library.JsonRecyclerView jsonRecyclerView ;


        View view = inflater.inflate(R.layout.fragment_json,container,false);;
        textView = view.findViewById(R.id.textView);
        jsonRecyclerView = view.findViewById(R.id.rv_json);
        Context context = view.getContext();

        SharedPreferences prefs = context.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String responsetext = prefs.getString("response", null);
//        final String duration = prefs.getString("time", null);
//        final String response_code = prefs.getString("code", null);


        try
        {

            jsonRecyclerView.bindJson(responsetext);
            textView.setVisibility(View.INVISIBLE);
        }
        catch (Exception e)
        {
            textView.setVisibility(View.VISIBLE);

            textView.setText("Not valid Json string found");

        }



        return view;



    }


}
