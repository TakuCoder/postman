package thiyagu.postman.com.postmanandroid;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieCategoryViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 90f;

    private final ImageView mArrowExpandImageView;
    private TextView mMovieTextView;
    private TextView totalCollections;
    public MovieCategoryViewHolder(View itemView) {
        super(itemView);
        mMovieTextView = (TextView) itemView.findViewById(R.id.tv_movie_category);
totalCollections = itemView.findViewById(R.id.total_collections);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.iv_arrow_expand);
    }

    public void bind(MovieCategory movieCategory) {


        mMovieTextView.setText(movieCategory.getName());
        totalCollections.setText(movieCategory.getNumber()+ " Requests");
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