package thiyagu.postman.com.postmanandroid;

public class Movies {

    private String mName;
    private String parentName;
    private int Position;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Movies(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public int getPosition() {
        return Position;
    }
}