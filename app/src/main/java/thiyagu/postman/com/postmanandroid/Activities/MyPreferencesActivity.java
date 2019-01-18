package thiyagu.postman.com.postmanandroid.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import thiyagu.postman.com.postmanandroid.R;

public class MyPreferencesActivity extends PreferenceActivity {
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        editor = this.getSharedPreferences("thiyagu.postman.com.postmanandroid_preferences", 0).edit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        static Preference filePicker;
        Preference holder;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            filePicker = findPreference("CertPicker");

            holder = findPreference("switch_preference_certificate");

            holder.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {


                    if (filePicker.isEnabled()) {
                        Log.v("dsfsdfdsf", "enabled");
                        String s = filePicker.getSummary().toString();
                        Log.v("dsfsdfdsf", s);

                    } else {
                        Log.v("dsfsdfdsf", "disabled");
                        // Log.v("dsfsdfdsf",filePicker.getSummary().toString());


                        editor.putString("CertPicker", "DEFAULT");
                        editor.apply();
                        editor.commit();
                    }

                    return false;
                }
            });


//            holder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//                @Override
//                public boolean onPreferenceChange(Preference preference, Object o) {
//
//                        filePicker.setEnabled(true);
//                    return false;
//                }
//            });
//
//
//            holder.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    filePicker.setEnabled(true);
//                    return false;
//                }
//            });
            filePicker.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
//                    Intent intent = new Intent();
//                    intent.setType("file/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);
//
//

                    checkPermissionsAndOpenFilePicker();

                    return true;
                }
            });
        }


        private void checkPermissionsAndOpenFilePicker() {
            String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                    showError();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, PERMISSIONS_REQUEST_CODE);
                }
            } else {
                openFilePicker();
            }
        }

        private void openFilePicker() {
            new MaterialFilePicker()
                    .withActivity(
                            getActivity()
                    )
                    .withRequestCode(FILE_PICKER_REQUEST_CODE)
                    .withHiddenFiles(true)
                    .withTitle("Sample title")
                    .start();
        }

        private void showError() {

            Toast.makeText(getActivity(), "Allow external storage reading", Toast.LENGTH_SHORT).show();
        }

        public Preference getMyPref() {
            return filePicker;
        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            Log.v("asdasd", "changing");

            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Log.v("dsdsd", path);
            MyPreferenceFragment.filePicker.setSummary(path);
            MyPreferenceFragment.filePicker.setDefaultValue(path);
            editor.putString("CertPicker", path);
            editor.apply();
            editor.commit();


        }
    }
}