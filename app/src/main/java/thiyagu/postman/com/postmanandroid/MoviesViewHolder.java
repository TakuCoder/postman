package thiyagu.postman.com.postmanandroid;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MoviesViewHolder extends ChildViewHolder {

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