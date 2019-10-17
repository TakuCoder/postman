package thiyagu.postman.com.postmanandroid.PopupActivities;

import android.arch.persistence.room.Room;
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
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.UUID;

import thiyagu.postman.com.postmanandroid.Database.Body;
import thiyagu.postman.com.postmanandroid.Database.DAO.BodyDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;
import thiyagu.postman.com.postmanandroid.Database.DataPojoClass;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.Database.RoomDatabase;
import thiyagu.postman.com.postmanandroid.Database.parameters;
import thiyagu.postman.com.postmanandroid.R;

public class ParamPopUp extends AppCompatActivity {
    MaterialBetterSpinner materialBetterSpinner,material_value;
    EditText KeyField, value_field;
    Button addButton;

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);
        final String[] request = {"ACCEPT", "CONTENT-TYPE", "CUSTOM"};
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, request);
        materialBetterSpinner = findViewById(R.id.material_spinner11);
        material_value = findViewById(R.id.material_value);

        KeyField = findViewById(R.id.KeyField);
        value_field = findViewById(R.id.value_field);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {

                if(KeyField.getText().toString().equals(""))
                {

                    Toast.makeText(getApplicationContext(),"Please enter key",Toast.LENGTH_LONG).show();


                }
                else if(value_field.getText().toString().equals(""))
                {

                    Toast.makeText(getApplicationContext(),"Please enter Value",Toast.LENGTH_LONG).show();
                }
                else
                {

                    DataPojoClass pojoClass = new DataPojoClass(KeyField.getText().toString(), value_field.getText().toString());

                    RoomDatabase database = Room.databaseBuilder(getApplicationContext(), RoomDatabase.class, "data_db")
                            .allowMainThreadQueries()   //Allows room to do operation on main thread
                            .build();
                    ParametersDAO parametersDAO = database.getParametersDAO();
                    parameters parameters = new parameters();
                    parameters.setKey(KeyField.getText().toString());
                    parameters.setValue(value_field.getText().toString());
                    parameters.setFlag("true");
                    String uuid = UUID.randomUUID().toString();
                    parameters.setTag(uuid);
                    parametersDAO.insert(parameters);



                    //feedReaderDbHelper.addEntryParam(pojoClass);
                    intent.putExtra("editTextValue", "value_here");
                    setResult(RESULT_OK, intent);
                    finish();

                }

            }
        });
        materialBetterSpinner.setBackgroundColor(Color.parseColor("#464646"));
        materialBetterSpinner.setAdapter(arrayadapter);
        materialBetterSpinner.addTextChangedListener(new TextWatcher()
        {
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
                if (value.equals("CUSTOM"))
                {
                    KeyField.setEnabled(true);
                    KeyField.setText("");

                } else if (value.equals("CONTENT-TYPE")) {

                    KeyField.setText("CONTENT-TYPE");
                    KeyField.setEnabled(false);
                } else if (value.equals("ACCEPT")) {
                    KeyField.setEnabled(false);
                    KeyField.setText("ACCEPT");
                } else {


                }
            }
        });



        material_value.setBackgroundColor(Color.parseColor("#464646"));
        material_value.setAdapter(arrayadapter);
        material_value.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("Text", material_value.getText().toString());

                String value = material_value.getText().toString();
                if (value.equals("CUSTOM"))
                {
                    value_field.setEnabled(true);
                    value_field.setText("");

                } else if (value.equals("CONTENT-TYPE")) {

                    value_field.setText("CONTENT-TYPE");
                    value_field.setEnabled(false);
                } else if (value.equals("ACCEPT")) {
                    value_field.setEnabled(false);
                    value_field.setText("ACCEPT");
                } else {


                }
            }
        });

    }
}
