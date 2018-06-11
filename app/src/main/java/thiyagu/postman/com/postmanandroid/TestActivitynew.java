package thiyagu.postman.com.postmanandroid;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import fr.ganfra.materialspinner.MaterialSpinner;

public class TestActivitynew extends AppCompatActivity {
    private static final String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

    private ArrayAdapter<String> adapter;

    MaterialSpinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activitynew);
        adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown, ITEMS);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        initSpinnerHintAndFloatingLabel();


    }
    private void initSpinnerHintAndFloatingLabel() {
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);
        spinner1.setPaddingSafe(0, 0, 0, 0);
    }
}
