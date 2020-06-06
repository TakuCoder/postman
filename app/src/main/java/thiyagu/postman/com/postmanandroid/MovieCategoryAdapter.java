package thiyagu.postman.com.postmanandroid;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import thiyagu.postman.com.postmanandroid.Activities.RequestActivity;
import thiyagu.postman.com.postmanandroid.DI.DatabaseModule;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoTable;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemTable;
import thiyagu.postman.com.postmanandroid.Database.DAO.HeaderDAO;
import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;
import thiyagu.postman.com.postmanandroid.Database.Databases.CollectionDatabase;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Database.Header;
import thiyagu.postman.com.postmanandroid.Database.parameters;
import thiyagu.postman.com.postmanandroid.Model.MovieCategory;
import thiyagu.postman.com.postmanandroid.Model.Movies;
import thiyagu.postman.com.postmanandroid.Model.ParentListItem;

import static thiyagu.postman.com.postmanandroid.Utils.CollectionsParser.PrintLog;

public class MovieCategoryAdapter extends ExpandableRecyclerAdapter<MovieCategoryViewHolder, MoviesViewHolder> {
    public static String ss;
    //
//    @Inject
//    CollectionDatabase collectionDatabase;
    Context context;

    CollectionDatabase collectionDatabase;
    TelleriumDataDatabase telleriumDataDatabase;

    private LayoutInflater mInflator;

    public MovieCategoryAdapter(Context mcontext, List<? extends ParentListItem> parentItemList, String s) {
        super(parentItemList);
        this.context = mcontext;
        mInflator = LayoutInflater.from(context);
        ss = s;
        collectionDatabase = Room.databaseBuilder(context, CollectionDatabase.class, "collection_db").allowMainThreadQueries().build();
        telleriumDataDatabase = Room.databaseBuilder(context, TelleriumDataDatabase.class, "data_db").allowMainThreadQueries().build();
    }

    @Override
    public MovieCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.movie_category_view, parentViewGroup, false);
        return new MovieCategoryViewHolder(movieCategoryView);
    }

    @Override
    public MoviesViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.movies_view, childViewGroup, false);
        return new MoviesViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(MovieCategoryViewHolder movieCategoryViewHolder, int position, ParentListItem parentListItem) {
        MovieCategory movieCategory = (MovieCategory) parentListItem;
        movieCategoryViewHolder.bind(movieCategory);
    }

    @Override
    public void onBindChildViewHolder(MoviesViewHolder moviesViewHolder, final int position, Object childListItem) {
        final Movies movies = (Movies) childListItem;

        movies.setPosition(position);
        moviesViewHolder.bind(movies);
        moviesViewHolder.itemView.findViewById(R.id.tv_movies).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("adsadasd", movies.getParentName() + " " + movies.getName() + " " + position);
                Toast.makeText(context, movies.getParentName() + " " + movies.getName() + " " + position, Toast.LENGTH_LONG).show();
                JSONObject url_object = null;
                List<ItemTable> itemTables = collectionDatabase.getItemDAO().getItemByPostIdandName(movies.getParentName(), movies.getName());
                Log.d("statuss", itemTables.size() + "");
                String request = itemTables.get(0).getRequest();

                Log.v("asdasdsadsad", request);
                //List<parameters> parameters_data = telleriumDataDatabase.getParametersDAO().getParam();

                List<InfoTable> infoTable = collectionDatabase.getInfoDAO().getInfo();

                try {
                    JSONObject object_request = new JSONObject(request);

                    String request_method = object_request.get("method").toString();
                    if (object_request.has("url")) {
                        url_object = object_request.getJSONObject("url");

                    }

                    System.out.println(request_method + " request_method");

                    if (object_request.has("header")) {
                        HeaderDAO headerDAO = telleriumDataDatabase.getHeaderDAO();
                        headerDAO.nukeHeader();

                        PrintLog("found header");
                        JSONArray request_header_array = object_request.getJSONArray("header");
                        for (int i = 0; i < request_header_array.length(); i++) {
                            Header headers = new Header();
                            JSONObject header_object = request_header_array.getJSONObject(i);

                            {


                                String header_key = header_object.getString("key");
                                String header_value = header_object.getString("value");


                                String header_type = header_object.getString("type");
                                System.out.println(header_key + " header_key");
                                System.out.println(header_value + " header_value");
                                System.out.println(header_type + " header_type");

                                headers.setKey(header_key);

                                headers.setTag(UUID.randomUUID().toString().substring(0, 7));
                                headers.setValue(header_value);
                                if (header_object.has("description")) {
                                    String header_description = header_object.getString("description");
                                    System.out.println(header_description + " header_description");

                                } else {
                                    System.out.println("header_description" + "no header_description");
                                }


                                if (header_object.has("disabled")) {
                                    String disabled_status = header_object.getString("disabled");
                                    headers.setFlag("false");
                                    System.out.println("disabled_status" + disabled_status);

                                } else {
                                    headers.setFlag("true");
                                    System.out.println("disabled_status" + "false");
                                }
                            }
                            System.out.println("");
                            headerDAO.insert(headers);
                        }
                    } else {

                        PrintLog("no found header");
                    }


                    if (url_object.has("query")) {
                        ParametersDAO parametersDAO = telleriumDataDatabase.getParametersDAO();
                        parametersDAO.nukeParams();
                        parameters parameterss;
                        PrintLog("has Querry");
                        JSONArray query_array = url_object.getJSONArray("query");
                        for (int i = 0; i < query_array.length(); i++) {
                            JSONObject jsonObject2 = query_array.getJSONObject(i);
                            String query_key = jsonObject2.getString("key");
                            String query_value = jsonObject2.getString("value");

                            parameterss = new parameters();
                            System.out.println("query_key " + query_key);
                            System.out.println("query_value " + query_value);
                            parameterss.setKey(query_key);

                            parameterss.setValue(query_value);
                            parameterss.setTag(UUID.randomUUID().toString().substring(0, 7));

                            if (jsonObject2.has("description")) {

                                String query_description = jsonObject2.getString("description");
                                System.out.println("query_description " + query_description);
                            } else {
                                //  String query_description = jsonObject2.getString("description");
                                System.out.println("query_description " + "none");

                            }


                            if (jsonObject2.has("disabled")) {
                                String disabled = jsonObject2.optString("disabled");
                                System.out.println("disabled " + disabled);
                                parameterss.setFlag("false");
                            } else {
                                System.out.println("disabled " + "false");
                                parameterss.setFlag("true");
                            }

                            parametersDAO.insert(parameterss);

                            System.out.println("");
                        }

                    } else {
                        PrintLog("No Querry");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, RequestActivity.class);
                context.startActivity(intent);

                // Log.e("asdasdsad",infoTable.size()+"");

            }
        });
    }


}

class MoviesViewHolder extends RecyclerView.ViewHolder {
    String sss = MovieCategoryAdapter.ss;
    private TextView mMoviesTextView;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        mMoviesTextView = (TextView) itemView.findViewById(R.id.tv_movies);

    }

    public void bind(final Movies movies) {
        mMoviesTextView.setText(movies.getName());
//        mMoviesTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.v("sadsadsadsad", movies.getName() + movies.getParentName());
//
//
//            }
//        });
    }
}

class MovieCategoryViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 90f;

    private final ImageView mArrowExpandImageView;
    private TextView mMovieTextView;
    private TextView totalCollections;
    private ImageView options;

    public MovieCategoryViewHolder(View itemView) {
        super(itemView);
        mMovieTextView = (TextView) itemView.findViewById(R.id.tv_movie_category);
        totalCollections = itemView.findViewById(R.id.total_collections);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.iv_arrow_expand);
        options = itemView.findViewById(R.id.options);


    }

    public void bind(MovieCategory movieCategory) {


        mMovieTextView.setText(movieCategory.getName());

        totalCollections.setText(movieCategory.getNumber() + " Requests");
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);

        if (expanded) {
            mArrowExpandImageView.setRotation(ROTATED_POSITION);
        } else {
            mArrowExpandImageView.setRotation(INITIAL_POSITION);
        }

    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);

        RotateAnimation rotateAnimation;
        if (expanded) { // rotate clockwise
            rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                    INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        } else { // rotate counterclockwise
            rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                    INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }

        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        mArrowExpandImageView.startAnimation(rotateAnimation);

    }
}