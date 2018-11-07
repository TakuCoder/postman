package thiyagu.postman.com.postmanandroid.Fragment;

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

import java.util.ArrayList;

import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;

import thiyagu.postman.com.postmanandroid.PopupActivities.ParamPopUp;
import thiyagu.postman.com.postmanandroid.R;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class ParamFragment extends Fragment{

    Button AddParams;
    static RecyclerView recyclerView;
    static Context context;
    static RecyclerView.Adapter ParamsAdapter;
    static RecyclerView.LayoutManager ParamLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
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
        Log.v("whichfragment","ParamFragment");
        ParamLayoutManager = new LinearLayoutManager(getContext());

        // Toast.makeText(getContext(), "onCreate", Toast.LENGTH_LONG).show();
        View view = inflater.inflate(R.layout.tab_fragment_params, container, false);
        context = view.getContext();


        recyclerView = view.findViewById(R.id.my_recycler_view);
        AddParams = view.findViewById(R.id.AddParams);
        AddParams.setOnClickListener(new View.OnClickListener() {
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

    public void RefereshView() {


        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(context);
        ArrayList<String> dataa = feedReaderDbHelper.getAllParam();
        Log.v("asdasdsad", dataa.toString());
        ArrayList<ParamDataObject> results = new ArrayList<>();

        for (int i = 0; i < dataa.size(); i++) {

            String[] splitt = dataa.get(i).split("@@");
            ParamDataObject paramDataObject = new ParamDataObject(splitt[0], splitt[1]);
            results.add(i, paramDataObject);
            //DataPojoClass dataPojoClass = new DataPojoClass(splitt[0],splitt[1]);

        }


        ParamsAdapter = new ParamAdapter(results, context);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(ParamsAdapter);


    }

    private ArrayList<ParamDataObject> getDataSet()

    {

        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(context);
        ArrayList<String> dataa = feedReaderDbHelper.getAllParam();
        Log.v("asdasdsad", dataa.toString());
        ArrayList<ParamDataObject> results = new ArrayList<>();

        for (int i = 0; i < dataa.size(); i++) {

            String[] splitt = dataa.get(i).split("@@");
            // ParamDataObject paramDataObject = new ParamDataObject(splitt[0], splitt[1]);
            ParamDataObject paramDataObject = new ParamDataObject(splitt[0], splitt[1], splitt[2]);


            results.add(i, paramDataObject);
            //DataPojoClass dataPojoClass = new DataPojoClass(splitt[0],splitt[1]);

        }


        return results;
    }


    public static void refershView() {
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(context);
        ArrayList<String> dataa = feedReaderDbHelper.getAllParam();
        Log.v("asdasdsad", dataa.toString());
        ArrayList<ParamDataObject> results = new ArrayList<>();

        for (int i = 0; i < dataa.size(); i++) {

            String[] splitt = dataa.get(i).split("@@");
            ParamDataObject paramDataObject = new ParamDataObject(splitt[0], splitt[1]);
            results.add(i, paramDataObject);
            //DataPojoClass dataPojoClass = new DataPojoClass(splitt[0],splitt[1]);

        }

Log.v("asdsdasd","refreshing...");
        ParamsAdapter = new ParamAdapter(results, context);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(ParamsAdapter);
    }
}
