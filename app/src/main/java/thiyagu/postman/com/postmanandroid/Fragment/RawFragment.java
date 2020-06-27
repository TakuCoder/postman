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

public class RawFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView responsetext_edit;

        View view = inflater.inflate(R.layout.fragment_response,container,false);;
        Context context = view.getContext();
        responsetext_edit = view.findViewById(R.id.responsetext);
        SharedPreferences prefs = context.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String responsetext = prefs.getString("response", null);
        final String duration = prefs.getString("time", null);
        final String response_code = prefs.getString("code", null);
        responsetext_edit.setText(responsetext);


        return view;



    }


}
