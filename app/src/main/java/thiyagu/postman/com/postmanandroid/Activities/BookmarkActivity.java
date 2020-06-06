package thiyagu.postman.com.postmanandroid.Activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Bookmarks;
import thiyagu.postman.com.postmanandroid.Database.DAO.BookmarkDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.HistoryDAO;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Database.History;
import thiyagu.postman.com.postmanandroid.Model.HistoryClass;
import thiyagu.postman.com.postmanandroid.PopupActivities.DifferentRowAdapter;
import thiyagu.postman.com.postmanandroid.R;

public class BookmarkActivity extends AppCompatActivity {
    List<HistoryClass> historyClassList;

RelativeLayout TextViewLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        TextViewLayout = findViewById(R.id.TextViewLayout);

        TelleriumDataDatabase database = Room.databaseBuilder(this, TelleriumDataDatabase.class, "data_db").allowMainThreadQueries().build();

        BookmarkDAO bookmarkDAO = database.getBookmarkDAO();

       // HistoryDAO historyDAO = database.getHistoryDAO();

        List<Bookmarks> bookmarks = bookmarkDAO.getBookMark();

        //Log.v("sadsadsad", history.size() + "xxxvxvxv");

        //   BodyDAO bodyDAO = database.getbodyDAO();
        //   List<Body> dataa = bodyDAO.getBody();
        historyClassList = new ArrayList<>();


        List<String> date_values = bookmarkDAO.getDate();

        if(date_values.size()==0)
        {
            TextViewLayout.setVisibility(View.VISIBLE);

        }

        Log.v("thisistoprintAA", date_values.toString());


        for (int k = 0; k < date_values.size(); k++)
        {


            List<Bookmarks> data = bookmarkDAO.getBookMarkByDate(date_values.get(k));
            // ArrayList<String> data = feedReaderDbHelper.getAllhistory(aa.get(k));

            for (int u = 0; u < data.size(); u++) {

                Bookmarks temp_history = data.get(u);
                //   String value[] = data.get(u).split("@@");

                // Log.v("sdasdsasa", data.get(u));

                if (u == 0) {
                    historyClassList.add(new HistoryClass("", temp_history.getDate(), "", "", "", "", "", ViewType.CITY_TYPE));
                    historyClassList.add(new HistoryClass(temp_history.getUrl(), temp_history.getDate(), temp_history.getTime(), temp_history.getSize(), temp_history.getResponse_code(), temp_history.getDuration(), temp_history.getReqtype(), ViewType.EVENT_TYPE));

                } else {
                    historyClassList.add(new HistoryClass(temp_history.getUrl(), temp_history.getDate(), temp_history.getTime(), temp_history.getSize(), temp_history.getResponse_code(), temp_history.getDuration(), temp_history.getReqtype(), ViewType.EVENT_TYPE));

                }

            }


        }

        DifferentRowAdapter adapter = new DifferentRowAdapter(historyClassList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);


    }


}
