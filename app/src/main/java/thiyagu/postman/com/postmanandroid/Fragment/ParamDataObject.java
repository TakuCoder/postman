package thiyagu.postman.com.postmanandroid.Fragment;

/**
 * Created by thiyagu on 3/6/2018.
 */

public  class ParamDataObject {
    private String mText1;
    private String mText2;
    private String tag;

    public ParamDataObject(String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }
    public ParamDataObject(String tag,String text1, String text2){
        mText1 = text1;
        this.tag=tag;
        mText2 = text2;
    }
    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}