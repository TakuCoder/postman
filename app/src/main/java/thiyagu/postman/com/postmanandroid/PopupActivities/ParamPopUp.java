package thiyagu.postman.com.postmanandroid.PopupActivities;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.UUID;

import thiyagu.postman.com.postmanandroid.Database.DAO.ParametersDAO;
import thiyagu.postman.com.postmanandroid.Database.DataPojoClass;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Database.parameters;
import thiyagu.postman.com.postmanandroid.R;
public class ParamPopUp extends AppCompatActivity {
    TextInputLayout outlinedTextField_value, outlinedTextField_key;
    AutoCompleteTextView outlined_exposed_dropdown_key, outlined_exposed_dropdown_value;

    TextInputEditText KeyField;
    TextInputEditText content_types;
    Button addButton;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);

        outlined_exposed_dropdown_key = findViewById(R.id.outlined_exposed_dropdown_key);
        outlined_exposed_dropdown_value = findViewById(R.id.outlined_exposed_dropdown_value);

        ArrayAdapter arrayAdapter_key = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getKeyArray());
        ArrayAdapter<String> arrayadapter_value = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getValueArray());

        outlinedTextField_value = findViewById(R.id.outlinedTextField_value);
        outlinedTextField_key = findViewById(R.id.outlinedTextField_key);
        content_types = findViewById(R.id.value_field);
        //feedReaderDbHelper = new FeedReaderDbHelper(this);
        KeyField = findViewById(R.id.KeyField);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (KeyField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter key", Toast.LENGTH_LONG).show();
                } else if (content_types.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter Value", Toast.LENGTH_LONG).show();
                } else {
                    DataPojoClass pojoClass = new DataPojoClass(KeyField.getText().toString(), content_types.getText().toString());
                    TelleriumDataDatabase database = Room.databaseBuilder(getApplicationContext(), TelleriumDataDatabase.class, "data_db")
                            .allowMainThreadQueries()   //Allows room to do operation on main thread
                            .build();
                    ParametersDAO parametersDAO = database.getParametersDAO();
                    parameters parameters = new parameters();
                    parameters.setKey(KeyField.getText().toString());
                    parameters.setValue(content_types.getText().toString());
                    parameters.setFlag("true");
                    String uuid = UUID.randomUUID().toString();
                    parameters.setTag(uuid);
                    parametersDAO.insert(parameters);
                    //feedReaderDbHelper.addEntryParam(pojoClass);
                    intent.putExtra("editTextValue", "value_here");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        //materialBetterSpinner.setBackgroundColor(Color.parseColor("#464646"));
        outlined_exposed_dropdown_key.setAdapter(arrayAdapter_key);
        outlined_exposed_dropdown_key.setText(arrayAdapter_key.getItem(0).toString(), false);
//        outlined_exposed_dropdown_key.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//
//            }
//        });
        outlined_exposed_dropdown_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.v("Text1", outlined_exposed_dropdown_key.getText().toString());
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("Text2", outlined_exposed_dropdown_key.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.v("Text3", outlined_exposed_dropdown_key.getText().toString());
                String value = outlined_exposed_dropdown_key.getText().toString();
                if (value.equals("CUSTOM")) {
                    KeyField.setText("");
                    outlinedTextField_key.setVisibility(View.VISIBLE);
                } else {
                    KeyField.setText(value);
                }
            }
        });
        //material_value.setBackgroundColor(Color.parseColor("#464646"));
        outlined_exposed_dropdown_value.setAdapter(arrayadapter_value);
        outlined_exposed_dropdown_value.setText(arrayadapter_value.getItem(0), false);
        outlined_exposed_dropdown_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.v("Text", outlined_exposed_dropdown_value.getText().toString());
                String value = outlined_exposed_dropdown_value.getText().toString();
                if (value.equals("CUSTOM")) {
                    outlinedTextField_value.setVisibility(View.VISIBLE);
                    content_types.setText("");
                } else {
                    content_types.setText(value);
                }
            }
        });
    }
    private String[] getKeyArray() {

        final String[] array1 = {"CUSTOM", "Content-Type", "Content-Length", "Accept", "Accept-Charset", "Accept-Encoding", "Accept-Language","Accept-Datetime","Authorization","Cache-Control","Cookie","Connection","Content-MD5","Date","Expect","Forwarded","From","Host","If-Match","If-Modified-Since","If-None-Match","If-Range","If-Unmodified-Since","Max-Forwards","Origin","Pragma","Proxy-Authorization","Range",
                "Referer", "TE", "User-Agent", "Upgrade", "Via", "Warning"};

        return array1;

    }
    private String[] getValueArray() {

        final String[] array2 =
                {"CUSTOM", "application/json","application/xml","application/x-www-form-urlencoded","multipart/form-data","multipart/byteranges","application/octet-stream","text/plain","application/javascript","application/pdf","text/html","image/png","image/jpeg","image/gif","image/webp","text/css","application/x-pkcs12","application/xhtml+xml","application/andrew-inset","application/applixware","application/atom+xml","application/atomcat+xml","application/atomsvc+xml","application/bdoc","application/cu-seeme","application/davmount+xml","application/docbook+xml","application/dssc+xml","application/ecmascript","application/epub+zip","application/exi","application/font-tdpfr","application/font-woff","application/font-woff2","application/geo+json","application/graphql","application/java-serialized-object","application/json5","application/jsonml+json","application/ld+json","application/lost+xml","application/manifest+json","application/mp4","application/msword","application/mxf","application/oda","application/ogg","application/pgp-encrypted","application/pgp-signature","application/pics-rules","application/pkcs10","application/pkcs7-mime",
                        "application/pkcs7-signature",
                        "application/pkcs8",
                        "application/postscript",
                        "application/pskc+xml",
                        "application/resource-lists+xml",
                        "application/resource-lists-diff+xml",
                        "application/rls-services+xml",
                        "application/rsd+xml",
                        "application/rss+xml",
                        "application/rtf",
                        "application/sdp",
                        "application/shf+xml",
                        "application/timestamped-data",
                        "application/vnd.android.package-archive",
                        "application/vnd.api+json",
                        "application/vnd.apple.installer+xml",
                        "application/vnd.apple.mpegurl",
                        "application/vnd.apple.pkpass",
                        "application/vnd.bmi",
                        "application/vnd.curl.car",
                        "application/vnd.curl.pcurl",
                        "application/vnd.dna",
                        "application/vnd.google-apps.document",
                        "application/vnd.google-apps.presentation",
                        "application/vnd.google-apps.spreadsheet",
                        "application/vnd.hal+xml",
                        "application/vnd.handheld-entertainment+xml",
                        "application/vnd.macports.portpkg",
                        "application/vnd.unity",
                        "application/vnd.zul",
                        "application/widget",
                        "application/wsdl+xml",
                        "application/x-7z-compressed",
                        "application/x-ace-compressed",
                        "application/x-bittorrent",
                        "application/x-bzip",
                        "application/x-bzip2",
                        "application/x-cfs-compressed",
                        "application/x-chrome-extension",
                        "application/x-cocoa",
                        "application/x-envoy",
                        "application/x-eva",
                        "font/opentype",
                        "application/x-gca-compressed",
                        "application/x-gtar",
                        "application/x-hdf",
                        "application/x-httpd-php",
                        "application/x-install-instructions",
                        "application/x-latex",
                        "application/x-lua-bytecode",
                        "application/x-lzh-compressed",
                        "application/x-ms-application",
                        "application/x-ms-shortcut",
                        "application/x-ndjson",
                        "application/x-perl",
                        "application/x-pkcs7-certificates",
                        "application/x-pkcs7-certreqresp",
                        "application/x-rar-compressed",
                        "application/x-sh",
                        "application/x-sql",
                        "application/x-subrip",
                        "application/x-t3vm-image",
                        "application/x-tads",
                        "application/x-tar",
                        "application/x-tcl",
                        "application/x-tex",
                        "application/x-x509-ca-cert",
                        "application/xop+xml",
                        "application/xslt+xml",
                        "application/zip",
                        "audio/3gpp",
                        "audio/adpcm",
                        "audio/basic",
                        "audio/midi",
                        "audio/mpeg",
                        "audio/mp4",
                        "audio/ogg",
                        "audio/silk",
                        "audio/wave",
                        "audio/webm",
                        "audio/x-aac",
                        "audio/x-aiff",
                        "audio/x-caf",
                        "audio/x-flac",
                        "audio/xm",
                        "image/bmp",
                        "image/cgm",
                        "image/sgi",
                        "image/svg+xml",
                        "image/tiff",
                        "image/x-3ds",
                        "image/x-freehand",
                        "image/x-icon",
                        "image/x-jng",
                        "image/x-mrsid-image",
                        "image/x-pcx",
                        "image/x-pict",
                        "image/x-rgb",
                        "image/x-tga",
                        "message/rfc822",
                        "text/cache-manifest",
                        "text/calendar",
                        "text/coffeescript",
                        "text/csv",
                        "text/hjson",
                        "text/jade",
                        "text/jsx",
                        "text/less",
                        "text/mathml",
                        "text/n3",
                        "text/richtext",
                        "text/sgml",
                        "text/slim",
                        "text/stylus",
                        "text/tab-separated-values",
                        "text/uri-list",
                        "text/vcard",
                        "text/vnd.curl",
                        "text/vnd.fly",
                        "text/vtt",
                        "text/x-asm",
                        "text/x-c",
                        "text/x-component",
                        "text/x-fortran",
                        "text/x-handlebars-template",
                        "text/x-java-source",
                        "text/x-lua",
                        "text/x-markdown",
                        "text/x-nfo",
                        "text/x-opml",
                        "text/x-pascal",
                        "text/x-processing",
                        "text/x-sass",
                        "text/x-scss",
                        "text/x-vcalendar",
                        "text/xml",
                        "text/yaml",
                        "video/3gpp",
                        "video/3gpp2",
                        "video/h261",
                        "video/h263",
                        "video/h264",
                        "video/jpeg",
                        "video/jpm",
                        "video/mj2",
                        "video/mp2t",
                        "video/mp4",
                        "video/mpeg",
                        "video/ogg",
                        "video/quicktime",
                        "video/webm",
                        "video/x-f4v",
                        "video/x-fli",
                        "video/x-flv",
                        "video/x-m4v"};

        return array2;
    }
}
