package thiyagu.postman.com.postmanandroid.Database;

/**
 * Created by thiyagu on 3/29/2018.
 */

public class ResponsePojoClass {

    private String Response;

    public ResponsePojoClass(String response) {


        this.Response = response;

    }


    public void setResponse(String response) {
        Response = response;
    }

    public String getResponse() {
        return Response;
    }
}
