package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;

import es.dmoral.toasty.Toasty;
import thiyagu.postman.com.postmanandroid.Activities.MainActivity;
import thiyagu.postman.com.postmanandroid.JSONUtil;
import thiyagu.postman.com.postmanandroid.JSONUtils;
import thiyagu.postman.com.postmanandroid.MaterialBetterSpinner;
import thiyagu.postman.com.postmanandroid.PopupActivities.BodyPopUp;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.R;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class BodyFragment extends Fragment {
    JSONUtil jsonUtil = new JSONUtil();
    Button AddBody;
    String rawbody;
    String bodytypeflag;
    RecyclerView recyclerView;
    Context context;
    MaterialBetterSpinner body_spinner;
    EditText raw_text;
    SharedPreferences prefs;
    Button ButtonAddRawText;
    RecyclerView.Adapter ParamsAdapter;
    RecyclerView.LayoutManager ParamLayoutManager;
    //RadioButton radio_formdata, radio_raw, radio_binary;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == -1) {
                String strEditText = data.getStringExtra("editTextValue");

                ParamsAdapter = new BodyAdapter(getDataSet(), context);
                recyclerView.setAdapter(null);
                recyclerView.setAdapter(ParamsAdapter);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editor = MainActivity.getContext().getApplicationContext().getSharedPreferences("Thiyagu", 0).edit();
         prefs = MainActivity.getContext().getSharedPreferences("Thiyagu", 0);
        ParamLayoutManager = new LinearLayoutManager(getContext());
        Log.v("whichfragment", "BodyFragment");
        // Toast.makeText(getContext(), "onCreate", Toast.LENGTH_LONG).show();
        View view = inflater.inflate(R.layout.tab_fragment_body, container, false);
        context = view.getContext();
        Typeface roboto;
        AssetManager assetManager = context.getAssets();
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");
        raw_text = view.findViewById(R.id.raw_textfield);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        //radio_formdata = view.findViewById(R.id.radio_formdata);
        //radio_raw = view.findViewById(R.id.radio_raw);
        ButtonAddRawText = view.findViewById(R.id.button_addRawText);
        //radio_binary = view.findViewById(R.id.radio_binary);
        AddBody = view.findViewById(R.id.AddBody);
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");
        body_spinner = view.findViewById(R.id.body_spinner);
        //final String[] request = {"MULTIFORM", "JSON", "XML", "BINARY"};

        final String[] request = {"MULTIFORM", "JSON", "XML"};

        //radio_formdata.setChecked(true);
        //String rawbody = prefs.getString("bodytypeflag", null);
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, request);
      body_spinner.setAdapter(arrayadapter);
      body_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



              try
              {
                  bodytypeflag = prefs.getString("bodytypeflag", null);
                  rawbody = prefs.getString("rawbody", null);
                  switch (i)
                  {

                      case 0:

                          Toast.makeText(context,"MULTIFORM",Toast.LENGTH_LONG).show();



                          Log.v("statusofbodyfragment", "setting bodyflag 1");
                          editor.putString("bodytypeflag", "1");

                          editor.putString("rawbodytype", "MULTIFORM");
                          Log.v("statusofbodyfragment", "setting bodyflag "+"1"+" success");
                          editor.apply();

                          formData();

                          break;

                      case 1:
                          Toast.makeText(context,"JSON",Toast.LENGTH_LONG).show();
                         // body_spinner.setText("JSON");

                          Log.v("statusofbodyfragment", "setting bodyflag 2");
                          editor.putString("bodytypeflag", "2");

                          editor.putString("rawbodytype", "JSON");
                          Log.v("statusofbodyfragment", "setting bodyflag "+"2"+" success");
                          editor.apply();
                          raw();
                          break;

                      case 2:
                          Toast.makeText(context,"XML",Toast.LENGTH_LONG).show();
                          //body_spinner.setText("XML");

                          raw();



                          Log.v("statusofbodyfragment", "setting bodyflag 3");
                          editor.putString("bodytypeflag", "3");

                          editor.putString("rawbodytype", "XML");
                          Log.v("statusofbodyfragment", "setting bodyflag "+"3"+" success");
                          editor.apply();
                          break;

                      case 3:
                          Toast.makeText(context,"BINARY",Toast.LENGTH_LONG).show();
                         // body_spinner.setText("BINARY");
                          raw();



                          Log.v("statusofbodyfragment", "setting bodyflag 4");
                          editor.putString("bodytypeflag", "4");

                          editor.putString("rawbodytype", "BINARY");
                          Log.v("statusofbodyfragment", "setting bodyflag "+"4"+" success");
                          editor.apply();
                          break;



                  }

              }

              catch (Exception e)
              {


              }


          }
      });
        try
        {
            bodytypeflag = prefs.getString("bodytypeflag", null);
            rawbody = prefs.getString("rawbody", null);
            Log.v("rawbody",bodytypeflag);
            switch (bodytypeflag)
            {
                case "1":
                    //radio_formdata.setChecked(true);
                    formData();
                    body_spinner.setText("MULTIFORM");
                    //radio_raw.setChecked(false);
                    //radio_binary.setChecked(false);
                    break;

                case "2":
                    //radio_raw.setChecked(true);
                    body_spinner.setText("JSON");
                    raw();
                   // radio_formdata.setChecked(false);
                   // radio_binary.setChecked(false);
                    break;

                case "3":
                    body_spinner.setText("XML");
                    //radio_binary.setChecked(true);

                   // radio_formdata.setChecked(false);
                   // radio_raw.setChecked(false);

                    break;

            }
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.getContext(),e.toString(),Toast.LENGTH_LONG).show();

        }
//        radio_formdata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                formData();
//            }
//        });
//
//
//        radio_raw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//           raw();
//            }
//        });

        ButtonAddRawText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sss = raw_text.getText().toString();




                String bodytype = body_spinner.getText().toString();

                switch (bodytype)
                {

                    case "MULIFORM":

                        Apply(sss,"1","multiform");
                        break;


                    case "JSON":

                        boolean firstStringValid = JSONUtils.isJSONValid(sss); //true
                        if(firstStringValid == true)
                        {
                            Apply(sss,"2","json");
                            Log.v("sdsdsdsd","the above string is json");

                        }
                        else
                        {
                            Log.v("sdsdsdsd","the above string is not json");
                            Toasty.warning(context, "Not a valid json string found", Toast.LENGTH_SHORT, true).show();
                        }
                        break;



                    case "XML":
                        break;



                    case "BINARY":
                        break;





                }







            }
        });

//        radio_binary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //radio_formdata.setChecked(true);
//                radio_formdata.setChecked(false);
//                radio_raw.setChecked(false);
//
//
//
//                String rawbody = prefs.getString("rawbody", null);
//                String rawbodytype = prefs.getString("rawbodytype", null);
//
//                Log.v("sasdasdasddasd", rawbody + "==========>" + rawbodytype);
//
//
//            }
//        });


        AddBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BodyPopUp.class);

                startActivityForResult(intent, 1);
//                Log.v("statusofbodyfragment", "setting bodyflag 1");
//                editor.putString("bodytypeflag", "1");
//                editor.apply();
//                Log.v("statusofbodyfragment", "setting bodyflag 1 success");
            }
        });
        AddBody.setTypeface(roboto);
        recyclerView.setLayoutManager(ParamLayoutManager);
        ParamsAdapter = new BodyAdapter(getDataSet(), context);

        recyclerView.setAdapter(ParamsAdapter);
        return view;
    }
public void Apply(String sss,String i,String type)
{
    Log.v("asdasdasdsd", sss);
    Log.v("statusofbodyfragment", "setting bodyflag "+i);
    editor.putString("bodytypeflag", i);
    editor.putString("rawbody", sss);
    editor.putString("rawbodytype", type);
    Log.v("statusofbodyfragment", "setting bodyflag "+i+" success");
    editor.apply();



}
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //radio_raw.performClick();
    }

    private ArrayList<BodyDataObject> getDataSet()

    {

        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(context);
        ArrayList<String> dataa = feedReaderDbHelper.getAllBody();
        Log.v("asdasdsad", dataa.toString());
        ArrayList<BodyDataObject> results = new ArrayList<>();

        for (int i = 0; i < dataa.size(); i++) {

            String[] splitt = dataa.get(i).split("@@");
            // ParamDataObject paramDataObject = new ParamDataObject(splitt[0], splitt[1]);
            BodyDataObject paramDataObject = new BodyDataObject(splitt[0], splitt[1], splitt[2]);


            results.add(i, paramDataObject);
            //DataPojoClass dataPojoClass = new DataPojoClass(splitt[0],splitt[1]);

        }


        return results;
    }

    void raw()
    {


        //radio_formdata.setChecked(true);
        //radio_formdata.setChecked(false);
        //radio_binary.setChecked(false);
        recyclerView.setVisibility(View.GONE);
        AddBody.setVisibility(View.GONE);


        raw_text.setVisibility(View.VISIBLE);


        try
        {
           // raw_text.setText(jsonUtil.unescape(rawbody));
           raw_text.setText(rawbody);
            ButtonAddRawText.setVisibility(View.VISIBLE);

        }
        catch (Exception e)
        {


        }

        ButtonAddRawText.setVisibility(View.VISIBLE);
    }
    void formData()
    {





        //radio_formdata.setChecked(true);//radio_formdata.setChecked(true);
        //radio_raw.setChecked(false);
        //radio_binary.setChecked(false);


        recyclerView.setVisibility(View.VISIBLE);
        AddBody.setVisibility(View.VISIBLE);

        raw_text.setVisibility(View.GONE);

    }


}
