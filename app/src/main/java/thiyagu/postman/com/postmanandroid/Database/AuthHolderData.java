package thiyagu.postman.com.postmanandroid.Database;

import android.util.Log;

/**
 * Created by thiyagu on 6/1/2018.
 */

public class AuthHolderData {


    public  String BasicAuth;


    public String getBasicAuth() {
        return BasicAuth;
    }

    public void setBasicAuth(String basicAuth) {
        BasicAuth = basicAuth;


        Log.v("settingauthdata",basicAuth);
    }
}
