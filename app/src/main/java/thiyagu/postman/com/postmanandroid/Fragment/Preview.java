package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import thiyagu.postman.com.postmanandroid.R;

import static android.content.Context.MODE_PRIVATE;

public class Preview extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview,container,false);;
        Context context = view.getContext();
        SharedPreferences prefs = context.getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final String responsetext = prefs.getString("response", null);
        final String duration = prefs.getString("time", null);
        final String response_code = prefs.getString("code", null);
        Spanned htmlAsSpanned = Html.fromHtml(response_code);
        try {
    WebView webView = view.findViewById(R.id.webview);
    webView.loadData(responsetext, "text/html", "utf-8");
}
catch (Exception e)
{
    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();

}



        return view;



    }


}
