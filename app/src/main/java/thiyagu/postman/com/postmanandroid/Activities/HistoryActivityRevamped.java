package thiyagu.postman.com.postmanandroid.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import thiyagu.postman.com.postmanandroid.CollectionMultiAdapter;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoDAO;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoTable;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemTable;
import thiyagu.postman.com.postmanandroid.Database.DAO.HistoryDAO;
import thiyagu.postman.com.postmanandroid.Database.Databases.CollectionDatabase;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Database.History;
import thiyagu.postman.com.postmanandroid.ExpandableRecyclerAdapter;
import thiyagu.postman.com.postmanandroid.Model.HistoryClass;
import thiyagu.postman.com.postmanandroid.Model.MovieCategory;
import thiyagu.postman.com.postmanandroid.Model.Movies;
import thiyagu.postman.com.postmanandroid.R;
import thiyagu.postman.com.postmanandroid.Utils.CollectionsParser;
import thiyagu.postman.com.postmanandroid.Utils.PublicShareCollectionParser;

public class HistoryActivityRevamped extends AppCompatActivity implements View.OnClickListener {
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    public static Bus bus = new Bus(ThreadEnforcer.MAIN);
    static private CollectionMultiAdapter mAdapter;
    public List<MovieCategory> movieCategories = new ArrayList<>();
    CollectionDatabase collectionDatabase;
    LayoutInflater inflater;
    LottieAnimationView animation_view;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private RecyclerView recyclerView;


    List<HistoryClass> historyClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_revamped);
        inflater = this.getLayoutInflater();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        collectionDatabase = Room.databaseBuilder(this, CollectionDatabase.class, "collection_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();

        getData();



//        builder = new AlertDialog.Builder(this);
//        bus.register(this);
//        inflater = this.getLayoutInflater();
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        collectionDatabase = Room.databaseBuilder(HistoryActivityRevamped.this, CollectionDatabase.class, "collection_db")
//                .allowMainThreadQueries()   //Allows room to do operation on main thread
//                .build();
//
//
//       // animation_view = findViewById(R.id.animation_view);
//        getData();
    }
    public void getData()
    {
        // mAdapter = null;
        movieCategories.clear();
        recyclerView.setAdapter(mAdapter);
        TelleriumDataDatabase database = Room.databaseBuilder(this, TelleriumDataDatabase.class, "data_db").allowMainThreadQueries().build();
        HistoryDAO historyDAO = database.getHistoryDAO();
        List<String> history_data = historyDAO.getDate();

        if (history_data.size() > 0)
        {
            //Log.e("lenghtttt", infoTable.size() + "");
//            List<MovieCategory> arrayList = new ArrayList<>();
//            for (int i = 0; i < history_data.size(); i++)
//            {
//                List<ItemTable> itemTables = collectionDatabase.getItemDAO().getItemByPostId(infoTable.get(i).get_postman_id());
//                List<Movies> moviesList = new ArrayList<>();
//                for (int h = 0; h < itemTables.size(); h++) {
//                    Movies movies = new Movies(itemTables.get(h).getName());
//                    movies.setParentName(infoTable.get(i).get_postman_id());
//                    moviesList.add(movies);
//                }
//                MovieCategory main_movie = new MovieCategory(infoTable.get(i).getName() + "", moviesList, moviesList.size());
//                movieCategories.add(main_movie);
//            }
//

            for (int k = 0; k < history_data.size(); k++)
            {


                List<History> data = historyDAO.getHistoryByDate(history_data.get(k));
                Log.v("sdsdssd",data.size()+"");
                List<Movies> moviesList = new ArrayList<>();
                MovieCategory main_movie=null;
                for (int u = 0; u < data.size(); u++)
                {

                    Log.v("sdsdsdsdds",u+"");


                    Movies movies = new Movies(data.get(u).getUrl());
                    movies.setParentName(data.get(u).getDate());
                    moviesList.add(movies);
                    main_movie = new MovieCategory(moviesList.get(u).getParentName() + "", moviesList, moviesList.size());

                }

                movieCategories.add(main_movie);
                mAdapter = new CollectionMultiAdapter(this, movieCategories, "asas");
                mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener()
                {
                    @Override
                    public void onListItemExpanded(int position) {
                        MovieCategory expandedMovieCategory = movieCategories.get(position);
                    }
                    @Subscribe
                    public void getMessage(String s) {
                        Toast.makeText(HistoryActivityRevamped.this, s, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void OnClickListener(int position) {
                        Log.v("asdsadsadsa", position + "");
                    }
                    @Override
                    public void onListItemCollapsed(int position) {
                        MovieCategory collapsedMovieCategory = movieCategories.get(position);
                    }
                });
                recyclerView.setAdapter(mAdapter);

            }

            mAdapter = new CollectionMultiAdapter(this, movieCategories, "asas");
            mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener()
            {
                @Override
                public void onListItemExpanded(int position) {
                    MovieCategory expandedMovieCategory = movieCategories.get(position);
                }
                @Subscribe
                public void getMessage(String s) {
                    Toast.makeText(HistoryActivityRevamped.this, s, Toast.LENGTH_LONG).show();
                }
                @Override
                public void OnClickListener(int position) {
                    Log.v("asdsadsadsa", position + "");
                }
                @Override
                public void onListItemCollapsed(int position) {
                    MovieCategory collapsedMovieCategory = movieCategories.get(position);
                }
            });
            recyclerView.setAdapter(mAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
           // Toast.makeText(getApplicationContext(), "Add collection!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //mAdapter.onSaveInstanceState(outState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //  mAdapter.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                //getData();
                //animateFAB();
                break;
            case R.id.fab_url:
                //Initiate Link Capture
                builder.setView(inflater.inflate(R.layout.dialog_download, null))
                        // Add action buttons
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                // sign in the user ...
                                EditText urll = alertDialog.findViewById(R.id.url);
                                Log.v("asdasdsa", urll.getText().toString());
                                final Request request = new Request.Builder()
                                        .url(urll.getText().toString())
                                        .build();
                                final OkHttpClient client = new OkHttpClient();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                    }
                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                String json = null;
                                                try {
                                                    json = response.body().string();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                PublicShareCollectionParser collectionsParser = new PublicShareCollectionParser(getApplication());
                                                try {
                                                    int status = collectionsParser.parse(json, "json");
                                                    if (status == 0) {
                                                        getData();
                                                    }
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                });
                                //   new DownloadJsonFromUrl().execute(urll.getText().toString(), CollectionsActivity.this);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // this.getDialog().cancel();
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.fab2:
                checkPermissionsAndOpenFilePicker();
                Log.d("Raj", "Fab 2");
                break;
        }
    }

    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            openFilePicker();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFilePicker();
        }
        Toast.makeText(getApplicationContext(), "sadasd" + requestCode + grantResults, Toast.LENGTH_LONG).show();
    }
    private void openFilePicker() {
        new MaterialFilePicker().withActivity(this).withRequestCode(FILE_PICKER_REQUEST_CODE).withHiddenFiles(true).withFilter(Pattern.compile(".*\\.json$")).withTitle("Sample title").start();
//                            Intent intent = new Intent();
//                    intent.setType("file/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);
    }
    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Log.v("dsdsd", path);
            CollectionsParser collectionsParser = new CollectionsParser(getApplication());
            try {
                collectionsParser.parse(path, "file");
                recyclerView.setVisibility(View.VISIBLE);
                animation_view.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class DownloadJsonFromUrl extends AsyncTask<Object, Context, Context> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Context s) {
            //CollectionsActivity.mAdapter = null;
            HistoryActivityRevamped collectionsActivity = (HistoryActivityRevamped) s;
            collectionsActivity.getData();
            super.onPostExecute(s);
        }
        @Override
        protected Context doInBackground(Object... strings) {
            final OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url((String) strings[0])
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    PublicShareCollectionParser collectionsParser = new PublicShareCollectionParser(getApplication());
                    collectionsParser.parse(json, "json");
                }
            });
            return (Context) strings[1];
        }
    }
}
