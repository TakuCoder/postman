package thiyagu.postman.com.postmanandroid;


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

    private ArrayAdapter<String> adapter;
EditText input_password;

MaterialBetterSpinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_test_activitynew);
        setContentView(R.layout.exampleusage);
       // String[] list = getResources().getStringArray(R.array.myarray);
        spinner1 = findViewById(R.id.your_labelled_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ITEMS);

        spinner1.setAdapter(adapter);

//        input_password = findViewById(R.id.input_password);
//        input_password.setClickable(false);
//      input_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//          @Override
//          public void onFocusChange(View view, boolean b) {
//
//          }
//      });
//        adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown, ITEMS);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//
//
//        initSpinnerHintAndFloatingLabel();
//
//
//    }
//    private void initSpinnerHintAndFloatingLabel() {
//        spinner1 = findViewById(R.id.spinner1);
//        spinner1.setAdapter(adapter);
//        spinner1.setPaddingSafe(0, 0, 0, 0);
//    }




    }

}
