package thiyagu.postman.com.postmanandroid;

public class HistoryClass {
    private int tag;
    private String url;
    private String time;
    private String size;
    private String Duration;
    private String response_code;
    public HistoryClass( String url, String time, String size, String response_code, String Duration) {

        this.url = url;
        this.time = time;
        this.size = size;
        this.Duration = Duration;
        this.response_code = response_code;
    }
    public HistoryClass(int tag, String url, String time, String size, String response_code, String Duration) {
        this.tag = tag;
        this.url = url;
        this.time = time;
        this.size = size;
        this.Duration = Duration;
        this.response_code = response_code;
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
}