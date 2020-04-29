package thiyagu.postman.com.postmanandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import thiyagu.postman.com.postmanandroid.DI.DatabaseModule;
import thiyagu.postman.com.postmanandroid.Database.Databases.CollectionDatabase;
import thiyagu.postman.com.postmanandroid.Model.MovieCategory;
import thiyagu.postman.com.postmanandroid.Model.Movies;
import thiyagu.postman.com.postmanandroid.Model.ParentListItem;

public class MovieCategoryAdapter extends ExpandableRecyclerAdapter<MovieCategoryViewHolder, MoviesViewHolder> {

    @Inject
    CollectionDatabase collectionDatabase;



    private LayoutInflater mInflator;

    public MovieCategoryAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
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
    public void onBindChildViewHolder(MoviesViewHolder moviesViewHolder, int position, Object childListItem) {
        Movies movies = (Movies) childListItem;

        movies.setPosition(position);
        moviesViewHolder.bind(movies);
    }


}

class MoviesViewHolder extends RecyclerView.ViewHolder {

    private TextView mMoviesTextView;

    public MoviesViewHolder(View itemView) {
        super(itemView);
        mMoviesTextView = (TextView) itemView.findViewById(R.id.tv_movies);

    }

    public void bind(final Movies movies) {
        mMoviesTextView.setText(movies.getName());
        mMoviesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("sadsadsadsad",movies.getName()+movies.getParentName());
            }
        });
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