package thiyagu.postman.com.postmanandroid.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class NetChecker extends Service {

    // Constant
    public static String TAG_INTERVAL = "interval";
    public static String TAG_URL_PING = "url_ping";
    public static String TAG_ACTIVITY_NAME = "activity_name";
    private int interval;
    private String url_ping;
    private String activity_name;

    private Timer mTimer = null;
    ConnectionServiceCallback  mConnectionServiceCallback;
    public interface ConnectionServiceCallback {
        void hasInternetConnection();
        void hasNoInternetConnection();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        interval = intent.getIntExtra(TAG_INTERVAL, 10);
        url_ping = intent.getStringExtra(TAG_URL_PING);
        activity_name = intent.getStringExtra(TAG_ACTIVITY_NAME);

        try {
            mConnectionServiceCallback = (ConnectionServiceCallback) Class.forName(activity_name).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new CheckForConnection(), 0, interval * 1000);

        return super.onStartCommand(intent, flags, startId);
    }
    class CheckForConnection extends TimerTask {
        @Override
        public void run() {
            isNetworkAvailable();
        }
    }
    @Override
    public void onDestroy() {
        //mTimer.cancel();
        super.onDestroy();
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    private boolean isNetworkAvailable(){


        try
        {
            HttpURLConnection urlConnection = null;
            URL url = new URL(url_ping);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(7000);
            urlConnection.connect();

            urlConnection.getResponseCode();
            mConnectionServiceCallback.hasInternetConnection();
            return true;
        }

        catch (Exception e)
        {
            mConnectionServiceCallback.hasNoInternetConnection();
            return false;

        }




    }

}
