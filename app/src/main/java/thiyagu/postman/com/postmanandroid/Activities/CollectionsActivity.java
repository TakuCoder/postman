package thiyagu.postman.com.postmanandroid.Activities;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoDAO;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoTable;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemTable;
import thiyagu.postman.com.postmanandroid.Database.Databases.CollectionDatabase;
import thiyagu.postman.com.postmanandroid.ExpandableRecyclerAdapter;
import thiyagu.postman.com.postmanandroid.Model.MovieCategory;
import thiyagu.postman.com.postmanandroid.MovieCategoryAdapter;
import thiyagu.postman.com.postmanandroid.Model.Movies;
import thiyagu.postman.com.postmanandroid.R;
import thiyagu.postman.com.postmanandroid.Utils.CollectionsParser;

public class CollectionsActivity extends AppCompatActivity implements View.OnClickListener {
    private MovieCategoryAdapter mAdapter;
    private RecyclerView recyclerView;
    public List<MovieCategory> movieCategories = new ArrayList<>();
    CollectionDatabase collectionDatabase;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        //  floatingActionButton = findViewById(R.id.fab);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        collectionDatabase = Room.databaseBuilder(CollectionsActivity.this, CollectionDatabase.class, "collection_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();


        getData();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);


    }

    private void getData() {
        // mAdapter = null;
        movieCategories.clear();
        InfoDAO info = collectionDatabase.getInfoDAO();
        List<InfoTable> infoTable = info.getInfo();
        Log.e("lenghtttt", infoTable.size() + "");
        List<MovieCategory> arrayList = new ArrayList<>();
        for (int i = 0; i < infoTable.size(); i++) {
            List<ItemTable> itemTables = collectionDatabase.getItemDAO().getItemByPostId(infoTable.get(i).get_postman_id());
            List<Movies> moviesList = new ArrayList<>();
            for (int h = 0; h < itemTables.size(); h++) {
                Movies movies = new Movies(itemTables.get(h).getName());
                movies.setParentName(infoTable.get(i).get_postman_id());
                moviesList.add(movies);
            }
            MovieCategory main_movie = new MovieCategory(infoTable.get(i).getName() + "", moviesList, moviesList.size());
            movieCategories.add(main_movie);
        }

        mAdapter = new MovieCategoryAdapter(this, movieCategories);

        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                MovieCategory expandedMovieCategory = movieCategories.get(position);

//                String toastMsg = getResources().getString(R.string.expanded, expandedMovieCategory.getName());
//
//                Toast.makeText(CollectionsActivity.this,
//                        toastMsg,
//                        Toast.LENGTH_SHORT)
//                        .show();
            }

            @Override
            public void OnClickListener(int position) {
                Log.v("asdsadsadsa", position + "");
            }

            @Override
            public void onListItemCollapsed(int position) {
                MovieCategory collapsedMovieCategory = movieCategories.get(position);

//                String toastMsg = getResources().getString(R.string.collapsed, collapsedMovieCategory.getName());
//                Toast.makeText(CollectionsActivity.this,
//                        toastMsg,
//                        Toast.LENGTH_SHORT)
//                        .show();
            }


        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                Log.d("Raj", "Fab 1");

                break;
            case R.id.fab2:
                checkPermissionsAndOpenFilePicker();
                Log.d("Raj", "Fab 2");
                break;
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

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
            collectionsParser.parse(path);


        }
    }
}
