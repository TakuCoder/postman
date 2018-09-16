package thiyagu.postman.com.postmanandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //a list to store all the products
    List<HistoryClass> historyClassList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        historyClassList = new ArrayList<>();

        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this);
       ArrayList<String> aa= feedReaderDbHelper.getAllhistory();

        Log.v("thisistoprintAA",aa.toString());
        HashMap<String,HistoryClass> hashMap = new HashMap<String,HistoryClass>();
for(int i =0;i<aa.size();i++)
{


    String value[] = aa.get(i).split("@@");
    Object dataa = hashMap.get(value[7]);
    if(dataa ==null)
    {
        hashMap.put(value[7],new HistoryClass(value[1],value[2],value[3],value[4],value[5],value[6]));

    }
    historyClassList.add(new );

}
        //adding some items to our list
//        historyClassList.add(
//                new HistoryClass(
//                        1,
//                        "https://www.google.com",
//                        "200ms",
//                        "680",
//
//                        "200","22.00.00"));

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
        HistoryAdapter adapter = new HistoryAdapter(this, historyClassList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }
}
