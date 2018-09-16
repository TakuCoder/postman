package thiyagu.postman.com.postmanandroid;

public class HistoryClass {

    public static final int DATE_TYPE = 0;
    public static final int RESPONSE_TYPE = 1;
    private int mType;
    private int tag;
    private String url;
    private String time;
    private String size;
    private String Duration;
    private String response_code;
    private String reqtype;
    public HistoryClass( String url, String time, String size, String response_code, String Duration,String reqtype,int type) {

        this.url = url;
        this.time = time;
        this.size = size;
        this.Duration = Duration;
        this.response_code = response_code;
        this.reqtype = reqtype;
        this.mType = type;

    }
    public HistoryClass(int tag, String url, String time, String size, String response_code, String Duration,String reqtype,int type) {
        this.tag = tag;
        this.url = url;
        this.time = time;
        this.size = size;
        this.Duration = Duration;
        this.response_code = response_code;
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

    public String getTime() {
        return time;
    }

    public String getSize() {
        return size;
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