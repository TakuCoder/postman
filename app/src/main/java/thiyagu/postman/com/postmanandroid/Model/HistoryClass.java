package thiyagu.postman.com.postmanandroid.Model;

public class HistoryClass {

    public static final int DATE_TYPE = 0;
    public static final int RESPONSE_TYPE = 1;
    private int mType;
    private int tag;
    private String url;
    private String date;
    private String size;
    private String Duration;
    private String response_code;
    private String reqtype;
    private String time;
    public HistoryClass( String url, String date,String time, String size, String response_code, String Duration,String reqtype,int type) {

        this.url = url;
        this.date = date;
        this.time = time;
        this.size = size;
        this.response_code = response_code;
        this.Duration = Duration;
        this.reqtype = reqtype;
        this.mType = type;

    }



    public String getDuration() {
        return Duration;
    }


    public int getTag() {
        return tag;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getSize() {
        return size;
    }

    public String getTime() {
        return time;
    }

    public String getResponse_code() {
        return response_code;
    }

    public String getReqtype() {
        return reqtype;
    }

    public int getmType() {
        return mType;
    }
}