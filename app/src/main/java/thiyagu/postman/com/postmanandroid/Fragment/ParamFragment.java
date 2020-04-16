package thiyagu.postman.com.postmanandroid.Fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;

import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Database.parameters;
import thiyagu.postman.com.postmanandroid.PopupActivities.ParamPopUp;
import thiyagu.postman.com.postmanandroid.R;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class ParamFragment extends Fragment {

    Button AddParamsButton;
    static RecyclerView recyclerView;
    static Context context;
    static RecyclerView.Adapter ParamsAdapter;
    static RecyclerView.LayoutManager ParamLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == -1) {
                String strEditText = data.getStringExtra("editTextValue");

                ParamsAdapter = new ParamAdapter(getDataSet(), context);
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
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.v("whichfragment", "ParamFragment");
        ParamLayoutManager = new LinearLayoutManager(getContext());

        // Toast.makeText(getContext(), "onCreate", Toast.LENGTH_LONG).show();
        View view = inflater.inflate(R.layout.tab_fragment_params, container, false);
        context = view.getContext();


        recyclerView = view.findViewById(R.id.my_recycler_view);
        AddParamsButton = view.findViewById(R.id.AddParams);
        AddParamsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ParamPopUp.class);
                startActivityForResult(intent, 1);

            }
        });
        recyclerView.setLayoutManager(ParamLayoutManager);
        ParamsAdapter = new ParamAdapter(getDataSet(), context);

        recyclerView.setAdapter(ParamsAdapter);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }



    private List<parameters> getDataSet() {


        TelleriumDataDatabase database = Room.databaseBuilder(getContext(), TelleriumDataDatabase.class, "data_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        ParametersDAO parametersDAO = database.getParametersDAO();
        List<parameters> parameters = parametersDAO.getParam();


        //FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(context);
        // ArrayList<String> dataa = feedReaderDbHelper.getAllParam();
        //  Log.v("asdasdsad", dataa.toString());
       // ArrayList<ParamDataObject> results = new ArrayList<>();

//        for (int i = 0; i < parameters.size(); i++) {
//
//           // String[] splitt = parameters.get(i).split("@@");
//            // ParamDataObject paramDataObject = new ParamDataObject(splitt[0], splitt[1]);
//            ParamDataObject paramDataObject = new ParamDataObject("", parameters.get(i).getKey(), parameters.get(i).getValue());
//
//
//            results.add(i, paramDataObject);
//            //DataPojoClass dataPojoClass = new DataPojoClass(splitt[0],splitt[1]);
//
//        }


        return parameters;
    }



}
