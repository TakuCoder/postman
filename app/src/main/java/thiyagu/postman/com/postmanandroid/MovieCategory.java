package thiyagu.postman.com.postmanandroid;

import java.util.List;

public class MovieCategory implements ParentListItem {
    private String mName;
    private List<Movies> mMovies;
    public int number;

    public MovieCategory(String name, List<Movies> movies,int no) {
        mName = name;
        mMovies = movies;
        number = no;

    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<?> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}