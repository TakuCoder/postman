package thiyagu.postman.com.postmanandroid;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import fr.ganfra.materialspinner.MaterialSpinner;

public class TestActivitynew extends AppCompatActivity{
    private static final String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};


MaterialBetterSpinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.exampleusage);

        spinner1 = findViewById(R.id.your_labelled_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ITEMS);
        spinner1.setBackgroundColor(Color.BLACK);
        spinner1.setHintTextColor(Color.WHITE);

        spinner1.setAdapter(adapter);





    }

}
