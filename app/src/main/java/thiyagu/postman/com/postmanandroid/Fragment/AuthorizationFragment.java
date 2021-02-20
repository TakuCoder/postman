package thiyagu.postman.com.postmanandroid.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import thiyagu.postman.com.postmanandroid.CustomClass.MyAdapter;
import thiyagu.postman.com.postmanandroid.R;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class AuthorizationFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    // MaterialBetterSpinner materialBetterSpinner;
    Context context;
    public String[] authdata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SharedPreferences.Editor editor = view.getContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();





    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.v("whichfragment", "AuthFragment");
        Button updateauthrequest;
        final TextInputEditText username, password;
        final AutoCompleteTextView auth_spinner;
        final TextInputLayout input_layout_username, input_layout_password;
        Typeface roboto;
        final View view = inflater.inflate(R.layout.tab_fragment_authorization, container, false);
        context = view.getContext();
        auth_spinner = view.findViewById(R.id.auth_spinner);
       // authdata = new String[]{"No auth", "Basic Auth", "API Key", "Bearer Token"};

        authdata = new String[]{"No auth", "Basic Auth"};

        AssetManager assetManager = context.getAssets();
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");
        updateauthrequest = view.findViewById(R.id.updateauthrequest);
        username = view.findViewById(R.id.input_username);
        password = view.findViewById(R.id.input_password);
        input_layout_username = view.findViewById(R.id.input_layout_username);
        input_layout_password = view.findViewById(R.id.input_layout_password);
        username.setTypeface(roboto);
        password.setTypeface(roboto);
        updateauthrequest.setTypeface(roboto);
        auth_spinner.setTypeface(roboto);
        final SharedPreferences.Editor editor = view.getContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
        SharedPreferences prefs = view.getContext().getSharedPreferences("Thiyagu", MODE_PRIVATE);
        final MyAdapter<String> arrayadapter = new MyAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, authdata);

        String auth_flag = prefs.getString("auth_flag","null");
        if(auth_flag.equalsIgnoreCase("0"))
        {
            editor.putString("auth_flag","0");
            editor.apply();
            input_layout_username.setVisibility(view.GONE);
            input_layout_password.setVisibility(view.GONE);

         Log.v(TAG,auth_flag);

            auth_spinner.setText(arrayadapter.getItem(0).toString());


        }
        else {
            Log.v(TAG,auth_flag);
            switch (auth_flag)
            {

                case "0":
                    auth_spinner.setText(arrayadapter.getItem(0).toString());
                    break;

                case "1":
                    auth_spinner.setText(arrayadapter.getItem(1).toString());
                    break;

                case "2":
                    auth_spinner.setText(arrayadapter.getItem(2).toString());
                    break;

                case "3":
                    auth_spinner.setText(arrayadapter.getItem(3).toString());
                    break;

            }


            Log.v(TAG,auth_flag);
        }


        updateauthrequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Log.v(TAG, auth_spinner.getText().toString());

                if (auth_spinner.getText().toString().equals("Auth type"))
                {


                    new MaterialTapTargetPrompt.Builder(getActivity()).setTarget(view.findViewById(R.id.auth_spinner)).setPrimaryText("Select the auth type").setPromptBackground(new CirclePromptBackground()).setPromptFocal(new RectanglePromptFocal()).setBackgroundColour(getResources().getColor(R.color.buttonblue)).setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                Toast.makeText(context, "presseddddd", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).show();


                } else if (!auth_spinner.getText().toString().equals("Auth Type"))
                {

                    if (auth_spinner.getText().toString().equals("Basic Auth"))
                    {

                        int username_length = username.getText().toString().length();
                        int password_length = password.getText().toString().length();
                        if (username_length > 0 && password_length > 0) {


                            String Credentials = username.getText().toString() + ":" + password.getText().toString();
                            String authdata = "Basic " + Base64.encodeToString(Credentials.getBytes(), Base64.NO_WRAP);


                            editor.putString("Authorization", authdata);
                            editor.putString("auth_flag", "1");
                            editor.apply();
                            Toast.makeText(getActivity(), "Authorization data updated", Toast.LENGTH_LONG).show();

                        } else {


                            Toast.makeText(getActivity(), "Enter Fields", Toast.LENGTH_LONG).show();

                        }


                    } else if (auth_spinner.getText().toString().equals("No auth")) {




                        //String Credentials = "No auth";
                        String authdata = "No auth";

                        editor.putString("auth_flag", "0");
                        editor.putString("Authorization", authdata);
                        editor.apply();
                    }


                }


            }
        });



        auth_spinner.setAdapter(arrayadapter);
        auth_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();
                auth_spinner.setText(arrayadapter.getItem(i).toString(), false);

                if (String.valueOf(i).equals("0")) {
                    Log.v(TAG, arrayadapter.getItem(i).toString());

                    username.setEnabled(false);
                    password.setEnabled(false);

                    input_layout_username.setVisibility(view.GONE);
                    input_layout_password.setVisibility(view.GONE);
                    username.setText("");
                    password.setText("");
                    //username.setHintTextColor(getResources().getColor(R.color.gray));
                    // password.setHintTextColor(getResources().getColor(R.color.gray));
                    //Log.v("sadsadsad", "0");


//                    String credentials = USERNAME+":"+PASSWORD;
//                    String auth = "Basic "
//                            + Base64.encodeToString(credentials.getBytes(),
//                            Base64.NO_WRAP);
//                    headers.put("Authorization", auth);


                } else if (String.valueOf(i).equals("1")) {
                    Log.v(TAG, arrayadapter.getItem(i).toString());
                    username.requestFocus();


                    input_layout_username.setVisibility(view.VISIBLE);
                    input_layout_password.setVisibility(view.VISIBLE);

                    username.setEnabled(true);
                    password.setEnabled(true);


                }
            }
        });

        return view;
    }

    public void updateUI(String action) {

        switch (action) {
            case "Basic auth":
                break;

            case "Basic Auth":
                break;


        }


    }
}
