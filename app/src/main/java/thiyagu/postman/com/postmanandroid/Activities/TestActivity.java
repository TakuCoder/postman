package thiyagu.postman.com.postmanandroid.Activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import thiyagu.postman.com.postmanandroid.R;

public class TestActivity extends AppCompatActivity {
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        new server().execute();


    }

    public class server extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

            try {
///storage/emulated/0/1921681110.crt
                String filename = "//storage/emulated/0/1921681110.crt";


                File file = new File(filename);
                FileInputStream fileInputStream = new FileInputStream(file);

                CertificateFactory cf = CertificateFactory.getInstance("X.509");

                java.security.cert.Certificate ca;
                try {
                    ca = cf.generateCertificate(fileInputStream);
                    System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                } finally {
                    fileInputStream.close();
                }


                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);


                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

//

                URL url = new URL("https://182.168.0.110:3000");
                Log.v("dsfdsfdsfsdf", url.toString());
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                urlConnection.setSSLSocketFactory(context.getSocketFactory());
                // InputStream in = urlConnection.getInputStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                response = bf.readLine();
                Log.d("jghklghljhdg", response);
                // return response;
            } catch (Exception e) {
                Log.d("jghklghljhdg", e.toString());
                //return  e.toString();
            }


            return null;
        }
    }
}
