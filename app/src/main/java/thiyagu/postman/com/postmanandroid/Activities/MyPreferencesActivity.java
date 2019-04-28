package thiyagu.postman.com.postmanandroid.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.Map;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

import thiyagu.postman.com.postmanandroid.R;

public class MyPreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        editor = this.getSharedPreferences("thiyagu.postman.com.postmanandroid_preferences", 0).edit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
        static Preference timeout;
        static Preference toggle;
        static Preference CertPicker;
        static Preference sslverify;

        SharedPreferences sharedPreferences;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            sharedPreferences = getPreferenceManager().getSharedPreferences();
            PreferenceScreen prefScreen = getPreferenceScreen();
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);

            int count = prefScreen.getPreferenceCount();


            Preference p1 = prefScreen.getPreference(0);
//            Preference p2 = prefScreen.getPreference(1);
//            Preference p3 = prefScreen.getPreference(2);

            String value = sharedPreferences.getString(p1.getKey(), "");
            Log.v("starting", p1.getKey() + value);
            setPreferenceSummary(p1, value);
//            String value1 = sharedPreferences.getString(p3.getKey(), "");
//            Log.v("starting", p3.getKey() + value1);
//            setPreferenceSummary(p3, value1);

            timeout = findPreference("timeout");
            toggle = findPreference("toggle");
           // CertPicker = findPreference("CertPicker");
            sslverify = findPreference("sslverify");

//            toggle.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    if (CertPicker.isEnabled()) {
//                        Log.v("dsfsdfdsf", "enabled");
//                        String s = CertPicker.getSummary().toString();
//                        Log.v("dsfsdfdsf", s);
//
//                    } else {
//                        Log.v("dsfsdfdsf", "disabled");
//                        editor.putString("CertPicker", "DEFAULT");
//                        editor.apply();
//                        editor.commit();
//                    }
//
//                    return false;
//                }
//            });


//            CertPicker.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
////                    Intent intent = new Intent();
////                    intent.setType("file/*");
////                    intent.setAction(Intent.ACTION_GET_CONTENT);
////                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);
////
////
//
//                    checkPermissionsAndOpenFilePicker();
//
//                    return true;
//                }
//            });
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
            new MaterialFilePicker().withActivity(getActivity()).withRequestCode(FILE_PICKER_REQUEST_CODE).withHiddenFiles(true).withTitle("Sample title").start();
        }

        private void showError() {

            Toast.makeText(getActivity(), "Allow external storage reading", Toast.LENGTH_SHORT).show();
        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            Log.v("asdasd", "changing");

            return false;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {


            Preference preference = findPreference(s);
            if (null != preference) {
                if ((preference instanceof EditTextPreference)) {

                    String val = sharedPreferences.getString(preference.getKey(), "");
                    Log.v("onchanged", s);
                    setPreferenceSummary(preference, val);
                }

            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Log.v("dsdsd", path);
            MyPreferenceFragment.CertPicker.setSummary(path);
            MyPreferenceFragment.CertPicker.setDefaultValue(path);
            editor.putString("CertPicker", path);
            editor.apply();
            editor.commit();


        }
    }


    private static void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            Log.v("sdasdsadassd", value);
            // For EditTextPreferences, set the summary to the value's simple string representation.
            preference.setSummary(value);
            // setPreferenceSummary(preference, value);
        } else {
            Log.v("sdasdsadassd", value);
            // For EditTextPreferences, set the summary to the value's simple string representation.
            preference.setSummary(value);

        }
    }
}