package thiyagu.postman.com.postmanandroid.PopupActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import thiyagu.postman.com.postmanandroid.Database.DataPojoClass;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.R;

public class ParamPopUp extends AppCompatActivity {
    MaterialBetterSpinner materialBetterSpinner;
    EditText KeyField, ValueField;
    Button addButton;
    FeedReaderDbHelper feedReaderDbHelper;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);
        final String[] request = {"ACCEPT", "CONTENT-TYPE", "CUSTOM"};
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, request);
        materialBetterSpinner = findViewById(R.id.material_spinner11);
        feedReaderDbHelper = new FeedReaderDbHelper(this);
        KeyField = findViewById(R.id.KeyField);
        ValueField = findViewById(R.id.ValueField);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPojoClass pojoClass = new DataPojoClass(KeyField.getText().toString(), ValueField.getText().toString());
                feedReaderDbHelper.addEntryParam(pojoClass);
                intent.putExtra("editTextValue", "value_here");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        materialBetterSpinner.setBackgroundColor(Color.parseColor("#464646"));
        materialBetterSpinner.setAdapter(arrayadapter);
        materialBetterSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("Text", materialBetterSpinner.getText().toString());

                String value = materialBetterSpinner.getText().toString();
                if (value.equals("CUSTOM")) {

                    KeyField.setText("");

                } else if (value.equals("Content-Type")) {

                    KeyField.setText("Content-Type");
                } else if (value.equals("ACCEPT")) {

                    KeyField.setText("CUSTOM");
                } else {


                }
            }
        });


    }
}
