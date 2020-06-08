package thiyagu.postman.com.postmanandroid.Fragment;

import android.arch.persistence.room.Room;
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
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

import thiyagu.postman.com.postmanandroid.Activities.RequestActivity;
import thiyagu.postman.com.postmanandroid.Database.Body;
import thiyagu.postman.com.postmanandroid.Database.DAO.BodyDAO;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Utils.JSONUtil;
import thiyagu.postman.com.postmanandroid.Utils.JSONUtils;
import thiyagu.postman.com.postmanandroid.CustomViews.MaterialBetterSpinner;
import thiyagu.postman.com.postmanandroid.PopupActivities.BodyPopUp;
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
        try {
            editor = RequestActivity.getContext().getApplicationContext().getSharedPreferences("Thiyagu", 0).edit();
            prefs = RequestActivity.getContext().getSharedPreferences("Thiyagu", 0);
            ParamLayoutManager = new LinearLayoutManager(getContext());
        } catch (Exception e) {


        }
        View view = inflater.inflate(R.layout.tab_fragment_body, container, false);
        context = view.getContext();
        Typeface roboto;
        AssetManager assetManager = context.getAssets();
        raw_text = view.findViewById(R.id.raw_textfield);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        ButtonAddRawText = view.findViewById(R.id.button_addRawText);
        AddBody = view.findViewById(R.id.AddBody);
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");
        body_spinner = view.findViewById(R.id.body_spinner);
        final String[] request = {"MULTIFORM", "JSON", "XML", "NONE"};
        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, request);
        body_spinner.setAdapter(arrayadapter);
        body_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                try {
                    bodytypeflag = prefs.getString("bodytypeflag", null);
                    rawbody = prefs.getString("rawbody", null);
                    switch (i) {

                        case 0:
                            //MULTIPART BODYFLAG 1
                            editor.putString("bodytypeflag", "1");
                            editor.putString("rawbodytype", "MULTIFORM");
                            editor.apply();
                            formData();
                            break;

                        case 1:
                            //MULTIPART BODYFLAG 2
                            editor.putString("bodytypeflag", "2");
                            editor.putString("rawbodytype", "JSON");
                            editor.apply();
                            raw();
                            break;

                        case 2:

                            //XML BODYFLAG 3
                            raw();
                            editor.putString("bodytypeflag", "3");
                            editor.putString("rawbodytype", "XML");
                            editor.apply();
                            break;

                        case 3:
                            //NONE BODYFLAG 4
                            None();
                            editor.putString("bodytypeflag", "4");
                            editor.putString("rawbodytype", "NONE");
                            editor.apply();
                            break;


                    }

                } catch (Exception e) {


                }


            }
        });
        try {
            bodytypeflag = prefs.getString("bodytypeflag", null);
            rawbody = prefs.getString("rawbody", null);
            Log.v("rawbody", bodytypeflag);
            switch (bodytypeflag) {
                case "1":
                    formData();
                    body_spinner.setText("MULTIFORM");
                    break;
                case "2":
                    body_spinner.setText("JSON");
                    raw();
                    break;
                case "3":
                    body_spinner.setText("XML");
                    break;
                case "4":
                    None();
                    body_spinner.setText("NONE");
                    break;
            }
        } catch (Exception e) {


        }


        ButtonAddRawText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String raw_text_value = raw_text.getText().toString();
                String body_type = body_spinner.getText().toString();
                switch (body_type) {
                    case "MULIFORM":
                        Apply(raw_text_value, "1", "multiform");
                        break;
                    case "JSON":
                        boolean firstStringValid = JSONUtils.isJSONValid(raw_text_value); //true
                        if (firstStringValid == true) {
                            Apply(raw_text_value, "2", "json");
                            Log.v("sdsdsdsd", "the above string is json");

                        } else {
                            Log.v("sdsdsdsd", "the above string is not json");
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

        AddBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BodyPopUp.class);
                String bodytype = body_spinner.getText().toString();
                intent.putExtra("type", bodytype);
                startActivityForResult(intent, 1);
            }
        });
        AddBody.setTypeface(roboto);
        recyclerView.setLayoutManager(ParamLayoutManager);
        ParamsAdapter = new BodyAdapter(getDataSet(), context);
        recyclerView.setAdapter(ParamsAdapter);
        return view;
    }

    public void Apply(String sss, String i, String type) {
//
//
//    TelleriumDataDatabase database = Room.databaseBuilder(this.getActivity(), TelleriumDataDatabase.class, "data_db").allowMainThreadQueries().build();
//
//    BodyDAO bodyDAO = database.getbodyDAO();
//
//    Body body = new Body();
//    body.setKey("");
//    body.setValue("");
//    body.setRawvalue(sss);
//    String uuid = UUID.randomUUID().toString();
//    body.setReferenceId(uuid);
//    body.setFlag("true");
//    //Log.v("Top-Secret", intent.getStringExtra("type"));
//    body.setBodytype(type);
//    bodyDAO.insert(body);



        editor.putString("bodytypeflag", i);
        editor.putString("rawbody", sss);
        editor.putString("rawbodytype", type);
        editor.apply();
//    TelleriumDataDatabase database = Room.databaseBuilder(getContext(), TelleriumDataDatabase.class,"data_db").allowMainThreadQueries().build();
//
//    BodyDAO bodyDAO = database.getbodyDAO();
//    Body body = new Body();
//
//    body.setBodytype(type);
//    body.setRawvalue(sss);
//    bodyDAO.insert(body);


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    private List<Body> getDataSet() {
        TelleriumDataDatabase database = Room.databaseBuilder(getContext(), TelleriumDataDatabase.class, "data_db").allowMainThreadQueries().build();
        BodyDAO bodyDAO = database.getbodyDAO();
        List<Body> dataa = bodyDAO.getBody();
        return dataa;
    }

    void raw() {
        recyclerView.setVisibility(View.GONE);
        AddBody.setVisibility(View.GONE);
        raw_text.setVisibility(View.VISIBLE);
        try {
            raw_text.setText(rawbody);
            ButtonAddRawText.setVisibility(View.VISIBLE);

        } catch (Exception e) {
        }
        ButtonAddRawText.setVisibility(View.VISIBLE);
    }

    void formData() {
        recyclerView.setVisibility(View.VISIBLE);
        AddBody.setVisibility(View.VISIBLE);
        raw_text.setVisibility(View.GONE);
    }

    void None() {
        recyclerView.setVisibility(View.GONE);
        AddBody.setVisibility(View.GONE);
        ButtonAddRawText.setVisibility(View.GONE);
        raw_text.setVisibility(View.GONE);

    }
}
