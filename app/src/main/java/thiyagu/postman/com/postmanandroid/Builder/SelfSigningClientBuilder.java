package thiyagu.postman.com.postmanandroid.Builder;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import thiyagu.postman.com.postmanandroid.R;

public class SelfSigningClientBuilder {
    public  static OkHttpClient createClient(Context context,String Path)
    {
        OkHttpClient client = null;

        CertificateFactory cf = null;

        Certificate ca = null;
        SSLContext sslContext = null;
        try {

            File file = new File(Path);
            FileInputStream fileInputStream = new FileInputStream(file);
            cf = CertificateFactory.getInstance("X.509");
           // cert = context.getResources().openRawResource(R.raw.my_cert); // Place your 'my_cert.crt' file in `res/raw`

           ca = cf.generateCertificate(fileInputStream);
           // cert.close();

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | KeyManagementException e) {
            e.printStackTrace();
        }

        return client;

    }
}
