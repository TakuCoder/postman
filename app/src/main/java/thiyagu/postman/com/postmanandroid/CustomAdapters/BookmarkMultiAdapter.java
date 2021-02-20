package thiyagu.postman.com.postmanandroid.CustomAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Activities.RequestActivity;
import thiyagu.postman.com.postmanandroid.Database.Databases.CollectionDatabase;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.ExpandableRecyclerAdapter;
import thiyagu.postman.com.postmanandroid.Model.MovieCategory;
import thiyagu.postman.com.postmanandroid.Model.Movies;
import thiyagu.postman.com.postmanandroid.Model.ParentListItem;
import thiyagu.postman.com.postmanandroid.ParentViewHolder;
import thiyagu.postman.com.postmanandroid.R;

public class BookmarkMultiAdapter extends ExpandableRecyclerAdapter<BookmarkCategoryViewHolder, BookmarkViewHolder> {
    public static String ss;
    //
//    @Inject
//    CollectionDatabase collectionDatabase;
    Context context;

    CollectionDatabase collectionDatabase;
    TelleriumDataDatabase telleriumDataDatabase;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private LayoutInflater mInflator;

    public BookmarkMultiAdapter(Context mcontext, List<? extends ParentListItem> parentItemList, String s) {
        super(parentItemList);
        this.context = mcontext;
        mInflator = LayoutInflater.from(context);
        ss = s;
        collectionDatabase = Room.databaseBuilder(context, CollectionDatabase.class, "collection_db").allowMainThreadQueries().build();
        telleriumDataDatabase = Room.databaseBuilder(context, TelleriumDataDatabase.class, "data_db").allowMainThreadQueries().build();
        editor = RequestActivity.getContext().getApplicationContext().getSharedPreferences("Thiyagu", 0).edit();
        prefs = RequestActivity.getContext().getSharedPreferences("Thiyagu", 0);
    }

    @Override
    public BookmarkCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.collection_layout, parentViewGroup, false);
        return new BookmarkCategoryViewHolder(movieCategoryView, context);
    }

    @Override
    public BookmarkViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.child_collection_view, childViewGroup, false);
        return new BookmarkViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(BookmarkCategoryViewHolder collectionCategoryViewHolder, int position, ParentListItem parentListItem) {
        MovieCategory movieCategory = (MovieCategory) parentListItem;
        collectionCategoryViewHolder.bind(movieCategory);
    }

    @Override
    public void onBindChildViewHolder(BookmarkViewHolder collectionViewHolder, final int position, Object childListItem)
    {
        final Movies movies = (Movies) childListItem;

        movies.setPosition(position);
        collectionViewHolder.bind(movies);
        collectionViewHolder.itemView.findViewById(R.id.tv_movies).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Log.e("adsadasd", movies.getParentName() + " " + movies.getName() + " " + position);
//                Toast.makeText(context, movies.getParentName() + " " + movies.getName() + " " + position, Toast.LENGTH_LONG).show();
//                JSONObject url_object = null;
//                List<ItemTable> itemTables = collectionDatabase.getItemDAO().getItemByPostIdandName(movies.getParentName(), movies.getName());
//                Log.d("statuss", itemTables.size() + "");
//                String request = itemTables.get(0).getRequest();
//
//                Log.v("asdasdsadsad", request);
//                //List<parameters> parameters_data = telleriumDataDatabase.getParametersDAO().getParam();
//
//                List<InfoTable> infoTable = collectionDatabase.getInfoDAO().getInfo();
//
//                try {
//                    JSONObject object_request = new JSONObject(request);
//
//                    String request_method = object_request.get("method").toString();
//                    if (object_request.has("url")) {
//                        url_object = object_request.getJSONObject("url");
//
//
//                        String raw = url_object.getString("raw");
//                        System.out.println(raw);
//                        String[] url = raw.split("\\?");
//
//
//                        String protocol = url_object.getString("protocol");
//                        if (protocol.equalsIgnoreCase("http")) {
//                            editor.putBoolean("https_check", false);
//                        } else if (protocol.equalsIgnoreCase("https")) {
//                            editor.putBoolean("https_check", true);
//
//                        }
//
//                        JSONArray path = url_object.getJSONArray("path");
//                        StringBuilder stringBuilder = new StringBuilder();
//                        JSONArray host = url_object.getJSONArray("host");
//
//                        String url_full = host.getString(0) + "." + host.getString(1);
//                        url_full = url_full + "/" + path.getString(0);
//                        //
//                        editor.putString("url_value", url_full);
//                        System.out.println("=======================" + url_full + "======================");
//
//                        String req_method = path.getString(0);
//
//
//                        switch (req_method) {
//
//                            case "get":
//                                editor.putString("req_value", "GET");
//                                System.out.println("one");
//                                break;
//
//
//                            case "post":
//
//
//                                editor.putString("req_value", "POST");
//                                System.out.println("two");
//                                break;
//
//                        }
//                        System.out.println("dsdsdsdsdsdsdssd" + req_method);
//                        editor.putString("req_method", req_method);
//                        //https_check
//
//
////editor.putString("url_value",url[0]);
//                        editor.commit();
//                        editor.apply();
//
//                    }
//
//                    System.out.println(request_method + " request_method");
//
//                    if (object_request.has("header")) {
//                        HeaderDAO headerDAO = telleriumDataDatabase.getHeaderDAO();
//                        headerDAO.nukeHeader();
//
//                        PrintLog("found header");
//                        JSONArray request_header_array = object_request.getJSONArray("header");
//                        for (int i = 0; i < request_header_array.length(); i++) {
//                            Header headers = new Header();
//                            JSONObject header_object = request_header_array.getJSONObject(i);
//
//                            {
//
//
//                                String header_key = header_object.getString("key");
//                                String header_value = header_object.getString("value");
//
//
//                                String header_type = header_object.getString("type");
//                                System.out.println(header_key + " header_key");
//                                System.out.println(header_value + " header_value");
//                                System.out.println(header_type + " header_type");
//
//                                headers.setKey(header_key);
//
//                                headers.setTag(UUID.randomUUID().toString().substring(0, 7));
//                                headers.setValue(header_value);
//                                if (header_object.has("description")) {
//                                    String header_description = header_object.getString("description");
//                                    System.out.println(header_description + " header_description");
//
//                                } else {
//                                    System.out.println("header_description" + "no header_description");
//                                }
//
//
//                                if (header_object.has("disabled")) {
//                                    String disabled_status = header_object.getString("disabled");
//                                    headers.setFlag("true");
//                                    System.out.println("disabled_status" + disabled_status);
//
//                                } else {
//                                    headers.setFlag("false");
//                                    System.out.println("disabled_status" + "false");
//                                }
//                            }
//                            System.out.println("");
//                            headerDAO.insert(headers);
//                        }
//                    } else {
//
//                        PrintLog("no found header");
//                    }
//                    if (object_request.has("body")) {
//                        BodyDAO bodyDAO = telleriumDataDatabase.getbodyDAO();
//                        bodyDAO.nukeBody();
//                        PrintLog("found body");
//                        JSONObject object = object_request.getJSONObject("body");
//                        String mode = object.getString("mode");
//                        System.out.println(mode);
//                        Body body = new Body();
//                        switch (mode) {
//
//                            case "formdata":
//
//                                editor.putString("bodytypeflag", 1 + "");
//                                editor.apply();
//                                JSONArray formdata_Array = object.getJSONArray("formdata");
//                                body.setBodytype("MULTIFORM");
//                                // body.bodytype();
//                                for (int i = 0; i < formdata_Array.length(); i++) {
//                                    JSONObject formdata_object = formdata_Array.getJSONObject(i);
//                                    String formdata_key = formdata_object.getString("key");
//                                    body.setKey(formdata_key);
//                                    String formdata_type = formdata_object.getString("type");
//                                    if (formdata_type.equals("text")) {
//                                        String formdata_value = formdata_object.getString("value");
//                                        body.setValue(formdata_value);
//                                        System.out.println(formdata_value + "   formdata_value");
//                                    } else if (formdata_type.equals("file")) {
//
//                                        String formdata_value = formdata_object.getString("src");
//                                        System.out.println(formdata_value + "   formdata_value");
//                                    }
//                                    if (formdata_object.has("disabled")) {
//                                        if (formdata_object.get("disabled").equals(true)) {
//                                            body.setFlag("true");
//                                            System.out.println("Disabled");
//
//                                        }
//
//                                    } else {
//                                        body.setFlag("false");
//                                        System.out.println("not Disabled");
//                                    }
//                                    System.out.println(formdata_key + "   formdata_key");
//                                    System.out.println(formdata_type + "   formdata_type");
//                                    if (formdata_object.has("description")) {
//
//                                        String formdata_description = formdata_object.getString("description");
//                                        System.out.println(formdata_description + "   formdata_description");
//                                    } else {
//                                        System.out.println(" no  formdata_description");
//
//                                    }
//                                    bodyDAO.insert(body);
//
//                                }
//                                PrintLog("found formdata");
//                                break;
//
//                            case "urlencoded":
//                                editor.putString("bodytypeflag", 5 + "");
//                                editor.apply();
//                                PrintLog("found urlencoded");
//                                body.setBodytype("URLENCODED");
//                                formdata_Array = object.getJSONArray("urlencoded");
//                                for (int i = 0; i < formdata_Array.length(); i++) {
//                                    JSONObject urlencoded_object = formdata_Array.getJSONObject(i);
//                                    String urlencoded_key = urlencoded_object.getString("key");
//                                    String urlencoded_value = urlencoded_object.getString("value");
//                                    body.setKey(urlencoded_key);
//                                    body.setValue(urlencoded_value);
//                                    String urlencoded_type = urlencoded_object.getString("type");
//                                    if (urlencoded_object.has("disabled")) {
//
//                                        if (urlencoded_object.get("disabled").equals(true)) {
//                                            body.setFlag("true");
//                                            //System.out.println("Disabled");
//
//                                        }
//                                        System.out.println(urlencoded_object.get("disabled"));
//
//                                    } else {
//                                        body.setFlag("false");
//                                        System.out.println("not Disabled");
//                                    }
//                                    System.out.println(urlencoded_key + "   urlencoded_key");
//                                    System.out.println(urlencoded_value + "   urlencoded_value");
//
//                                    System.out.println(urlencoded_type + "   urlencoded_type");
//                                    if (urlencoded_object.has("description")) {
//
//                                        String formdata_description = urlencoded_object.getString("description");
//                                        System.out.println(formdata_description + "   urlencoded_description");
//                                    } else {
//                                        System.out.println(" no  urlencoded_description");
//
//                                    }
//                                    System.out.println();
//
//                                }
//                                break;
//
//                            case "raw":
//
//
//                                JSONObject options = object.getJSONObject("options");
//                                String raw_value = options.getString("raw");
//                                JSONObject raw = options.getJSONObject("raw");
//                                String language = raw.getString("language");
//
//                                switch (language) {
//                                    case "text":
//                                        break;
//
//                                    case "json":
//                                        editor.putString("bodytypeflag", 2 + "");
//                                        editor.putString("rawbody", raw_value);
//                                        editor.putString("rawbodytype", "json");
//                                        // editor.apply();
//                                        break;
//
//                                    case "xml":
//
//                                        editor.putString("bodytypeflag", "3");
//                                        editor.putString("rawbodytype", "XML");
//                                        // editor.apply();
//                                        break;
//
//
//                                }
//                                System.out.println(object.get("raw"));
//
//
//                                PrintLog("found raw");
//                                break;
//
//                            case "file":
//                                PrintLog("found file");
//                                break;
//
//                            default:
//                                break;
//
//
//                        }
//
//
//                        System.out.println(mode);
//                    } else {
//
//                        PrintLog("no body found");
//                    }
//
//                    if (url_object.has("query")) {
//                        ParametersDAO parametersDAO = telleriumDataDatabase.getParametersDAO();
//                        parametersDAO.nukeParams();
//                        parameters parameterss;
//                        PrintLog("has Querry");
//                        JSONArray query_array = url_object.getJSONArray("query");
//                        for (int i = 0; i < query_array.length(); i++) {
//                            JSONObject jsonObject2 = query_array.getJSONObject(i);
//                            String query_key = jsonObject2.getString("key");
//                            String query_value = jsonObject2.getString("value");
//
//                            parameterss = new parameters();
//                            System.out.println("query_key " + query_key);
//                            System.out.println("query_value " + query_value);
//                            parameterss.setKey(query_key);
//
//                            parameterss.setValue(query_value);
//                            parameterss.setTag(UUID.randomUUID().toString().substring(0, 7));
//
//                            if (jsonObject2.has("description")) {
//
//                                String query_description = jsonObject2.getString("description");
//                                System.out.println("query_description " + query_description);
//                            } else {
//                                //  String query_description = jsonObject2.getString("description");
//                                System.out.println("query_description " + "none");
//
//                            }
//
//
//                            if (jsonObject2.has("disabled")) {
//                                String disabled = jsonObject2.optString("disabled");
//                                System.out.println("disabled " + disabled);
//                                parameterss.setFlag("true");
//                            } else {
//                                System.out.println("disabled " + "false");
//                                parameterss.setFlag("false");
//                            }
//
//                            parametersDAO.insert(parameterss);
//
//                            System.out.println("");
//                        }
//
//                    } else {
//                        PrintLog("No Querry");
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Intent intent = new Intent(context, RequestActivity.class);
//                context.startActivity(intent);
//
//                // Log.e("asdasdsad",infoTable.size()+"");

            }
        });
    }


}

class BookmarkViewHolder extends RecyclerView.ViewHolder {
    String sss = HistoryMultiAdapter.ss;
    private TextView mMoviesTextView;

    public BookmarkViewHolder(View itemView) {
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

class BookmarkCategoryViewHolder extends ParentViewHolder {
    int i = 0;
    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 90f;

    private final ImageView mArrowExpandImageView;
    private TextView mMovieTextView;
    private TextView totalCollections;
    private ImageView options;
    Context context;
    private ImageView ic_baseline_more_vert_24;

    public BookmarkCategoryViewHolder(View itemView, Context context) {
        super(itemView);
        mMovieTextView = (TextView) itemView.findViewById(R.id.tv_movie_category);
        totalCollections = itemView.findViewById(R.id.total_collections);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.iv_arrow_expand);
        ic_baseline_more_vert_24 = itemView.findViewById(R.id.ic_baseline_more_vert_24);
        // options = itemView.findViewById(R.id.options);
        this.context = context;
        mArrowExpandImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isExpanded()) {
                    int i = collapseView();
                    System.out.println(i);
                } else {
                    int i = expandView();
                    System.out.println(i);

                }
            }
        });
        ic_baseline_more_vert_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded()) {
                    int i = collapseView();
                    System.out.println(i);


                } else {


                    showMenu(ic_baseline_more_vert_24, getAdapterPosition());
                }

            }
        });


    }

    public void showMenu(View anchor, final int position) {
        final PopupMenu popup = new PopupMenu(context, anchor);
        popup.getMenuInflater().inflate(R.menu.menu_collection, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getTitle().toString()) {

                    case "delete":
                        Log.v("statusstaus", "deleted" + position);
                        break;


                    case "export":
                        Log.v("statusstaus", "exported" + position);
                        break;

                }


                return false;
            }
        });
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