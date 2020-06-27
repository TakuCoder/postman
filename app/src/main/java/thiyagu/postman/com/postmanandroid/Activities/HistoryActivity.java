package thiyagu.postman.com.postmanandroid.Activities;

import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import thiyagu.postman.com.postmanandroid.Database.DAO.HistoryDAO;
import thiyagu.postman.com.postmanandroid.Database.History;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Model.HistoryClass;
import thiyagu.postman.com.postmanandroid.PopupActivities.DifferentRowAdapter;
import thiyagu.postman.com.postmanandroid.R;

public class HistoryActivity extends AppCompatActivity {
    List<HistoryClass> historyClassList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        TelleriumDataDatabase database = Room.databaseBuilder(this, TelleriumDataDatabase.class, "data_db").allowMainThreadQueries().build();
        HistoryDAO historyDAO = database.getHistoryDAO();

        List<History> history = historyDAO.getHistory();

        Log.v("sadsadsad", history.size() + "xxxvxvxv");

        //   BodyDAO bodyDAO = database.getbodyDAO();
        //   List<Body> dataa = bodyDAO.getBody();
        historyClassList = new ArrayList<>();


        List<String> date_values = historyDAO.getDate();

        Log.v("thisistoprintAA", date_values.toString());


        for (int k = 0; k < date_values.size(); k++)
        {


            List<History> data = historyDAO.getHistoryByDate(date_values.get(k));
            // ArrayList<String> data = feedReaderDbHelper.getAllhistory(aa.get(k));

            for (int u = 0; u < data.size(); u++) {

                History temp_history = data.get(u);
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
