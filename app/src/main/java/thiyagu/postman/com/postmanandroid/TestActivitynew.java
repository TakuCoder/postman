package thiyagu.postman.com.postmanandroid;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class TestActivitynew extends AppCompatActivity {
Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activitynew);

    spinner = findViewById(R.id.spinner);

    spinner.setBackgroundResource(R.color.gray);
    String[] sss= new String[]{"select","one","two"};
        ArrayAdapter<String> aaa = new ArrayAdapter<String>(this,R.layout.dropdown,sss){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = null;

                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {

                    v = super.getDropDownView(position, null, parent);
                }

                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };
        spinner.setAdapter(aaa);

    }
}
