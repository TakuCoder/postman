package thiyagu.postman.com.postmanandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import thiyagu.postman.com.postmanandroid.Activities.ViewType;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;
import thiyagu.postman.com.postmanandroid.PopupActivities.DifferentRowAdapter;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //a list to store all the products
    List<HistoryClass> historyClassList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


//
//        recyclerView = findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        historyClassList = new ArrayList<>();

        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this);
        ArrayList<String> aa = feedReaderDbHelper.getDate();

        Log.v("thisistoprintAA", aa.toString());
//       // HashMap<String, HistoryClass> hashMap = new HashMap<String, HistoryClass>();
//ArrayList<String> datelist = new ArrayList<>();
//        for(int i=0;i<aa.size();i++)
//        {
//            String value[] = aa.get(i).split("@@");
//            datelist.add(value[2]);
//
//        }


//        Set<String> non_dup_dates = new LinkedHashSet<>(datelist);
//
//        datelist.clear();
//        datelist.addAll(non_dup_dates);
//        Log.v("asdasdasdsad",datelist.toString());


        for (int k = 0; k < aa.size(); k++) {

            if (k == 0) {
                // historyClassList.add(new HistoryClass("https://www.google.com", "200ms", "680", "200","22.00.00"));

            } else {


            }

            ArrayList<String> data = feedReaderDbHelper.getAllhistory(aa.get(k));

            for (int u = 0; u < data.size(); u++) {


                String value[] = data.get(u).split("@@");

                Log.v("sdasdsasa", data.get(u));

                if (u == 0) {
                    historyClassList.add(new HistoryClass("", value[2], "", "", "", "", "", ViewType.CITY_TYPE));
                    historyClassList.add(new HistoryClass(value[1], value[2], value[3], value[4], value[5], value[6], value[7], ViewType.EVENT_TYPE));

                } else {
                    historyClassList.add(new HistoryClass(value[1], value[2], value[3], value[4], value[5], value[6], value[7], ViewType.EVENT_TYPE));

                }

            }


        }

        DifferentRowAdapter adapter = new DifferentRowAdapter(historyClassList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        //adding some items to our list
//        historyClassList.add(
//                new HistoryClass("asaaasass","","","","","","",DATE_TYPE));
//        historyClassList.add(
//                new HistoryClass("asaaasass","","","","","","",RESPONSE_TYPE));
        //adding some items to our list
//        historyClassList.add(
//                new HistoryClass(
//                        1,
//                        "https://www.google.com",
//                        "200ms",
//                        "680",
//
//                        "200","22.00.00"));
//
//
//        //adding some items to our list
//        historyClassList.add(
//                new HistoryClass(
//                        1,
//                        "https://www.google.com",
//                        "200ms",
//                        "680",
//
//                        "200","22.00.00"));


        //creating recyclerview adapter
        // DifferentRowAdapter adapter = new DifferentRowAdapter(getData());

        //setting adapter to recyclerview
        // recyclerView.setAdapter(adapter);


    }


    public static List<HistoryClass> getData() {
        List<HistoryClass> list = new ArrayList<>();
        list.add(new HistoryClass("London", "", "", "", "", "", "", ViewType.CITY_TYPE));
        list.add(new HistoryClass("London", "", "", "", "", "", "", ViewType.EVENT_TYPE));
        list.add(new HistoryClass("London", "", "", "", "", "", "", ViewType.EVENT_TYPE));
        list.add(new HistoryClass("London", "", "", "", "", "", "", ViewType.CITY_TYPE));
        list.add(new HistoryClass("London", "", "", "", "", "", "", ViewType.EVENT_TYPE));
        list.add(new HistoryClass("London", "", "", "", "", "", "", ViewType.CITY_TYPE));
        list.add(new HistoryClass("London", "", "", "", "", "", "", ViewType.EVENT_TYPE));
        return list;
    }


}
