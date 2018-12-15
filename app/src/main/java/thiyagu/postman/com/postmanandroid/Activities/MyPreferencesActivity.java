package thiyagu.postman.com.postmanandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;

import java.io.File;

import thiyagu.postman.com.postmanandroid.R;

public class MyPreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference filePicker = findPreference("filePicker");
            filePicker.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference)
                {
                    Intent intent = new Intent();
                    intent.setType("file/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);




                    return true;
                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

//            String newValue = ....;
//            SharedPreferences preferences = ......;
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("filePicker", newValue);
//            editor.commit();


            if (requestCode == 200 && resultCode == -1){
                Uri content_describer = data.getData();
                String src = content_describer.getPath();
                File  source = new File(src);
                Log.d("src is ", source.toString());
                String filename = content_describer.getLastPathSegment();

                Log.d("FileName is ",filename);
                //destination = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Test/TestTest/" + filename);
               // Log.d("Destination is ", destination.toString());
               // SetToFolder.setEnabled(true);
            }
        }
    }

}