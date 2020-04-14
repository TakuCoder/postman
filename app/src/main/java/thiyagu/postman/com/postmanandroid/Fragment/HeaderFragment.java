package thiyagu.postman.com.postmanandroid.Fragment;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Activities.RequestActivity;
import thiyagu.postman.com.postmanandroid.Database.DAO.HeaderDAO;
import thiyagu.postman.com.postmanandroid.Database.Header;
import thiyagu.postman.com.postmanandroid.Database.RoomDatabase;
import thiyagu.postman.com.postmanandroid.PopupActivities.HeaderPopUp;
import thiyagu.postman.com.postmanandroid.R;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class HeaderFragment extends Fragment {
    Button AddHeader;
    RecyclerView recyclerView;
    Context context;
    RecyclerView.Adapter ParamsAdapter;
    RecyclerView.LayoutManager ParamLayoutManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == -1) {
                String strEditText = data.getStringExtra("editTextValue");

                ParamsAdapter = new HeaderAdapter(getDataSet(), context);
                recyclerView.setAdapter(null);
                recyclerView.setAdapter(ParamsAdapter);
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ParamLayoutManager = new LinearLayoutManager(getContext());
        Log.v("whichfragment","HeaderFragment");
        // Toast.makeText(getContext(), "onCreate", Toast.LENGTH_LONG).show();
        View view = inflater.inflate(R.layout.tab_fragment_header, container, false);
        context = view.getContext();
        Typeface roboto;
        AssetManager assetManager = context.getAssets();
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");

        recyclerView = view.findViewById(R.id.my_recycler_view);
        AddHeader = view.findViewById(R.id.AddHeader);
        AddHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HeaderPopUp.class);
                intent.putExtra("urldata", ((RequestActivity) getActivity()).getUrlData());
                startActivityForResult(intent, 1);

            }
        });
        AddHeader.setTypeface(roboto);
        recyclerView.setLayoutManager(ParamLayoutManager);
        ParamsAdapter = new HeaderAdapter(getDataSet(), context);

        recyclerView.setAdapter(ParamsAdapter);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }
    private List<Header> getDataSet()

    {
        thiyagu.postman.com.postmanandroid.Database.RoomDatabase database = Room.databaseBuilder(getContext(), RoomDatabase.class, "data_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        HeaderDAO headerDAO = database.getHeaderDAO();
        List<Header> headers = headerDAO.getHeaders();


        return headers;
    }

}
