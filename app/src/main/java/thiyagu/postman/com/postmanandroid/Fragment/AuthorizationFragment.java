package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import thiyagu.postman.com.postmanandroid.R;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class AuthorizationFragment extends Fragment {
   // MaterialBetterSpinner materialBetterSpinner;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.v("whichfragment", "AuthFragment");
        Button updateauthrequest;
        final TextInputEditText username, password;
       final AutoCompleteTextView outlined_exposed_dropdown_key;

        Typeface roboto;
        final View view = inflater.inflate(R.layout.tab_fragment_authorization, container, false);
        context = view.getContext();
        outlined_exposed_dropdown_key = view.findViewById(R.id.outlined_exposed_dropdown_key);
        final String[] authdata = {"No auth", "Basic Auth"};

        AssetManager assetManager = context.getAssets();
        roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");
        updateauthrequest = view.findViewById(R.id.updateauthrequest);
        username = view.findViewById(R.id.input_username);
        password = view.findViewById(R.id.input_password);

        username.setTypeface(roboto);
        password.setTypeface(roboto);
        updateauthrequest.setTypeface(roboto);
        outlined_exposed_dropdown_key.setTypeface(roboto);
        updateauthrequest.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {



                if (outlined_exposed_dropdown_key.getText().toString().equals("Auth type")) {


                    Log.v("asdasdasdsa", "nulllllll");
                    new MaterialTapTargetPrompt.Builder(getActivity()).setTarget(view.findViewById(R.id.outlined_exposed_dropdown_key)).setPrimaryText("Select the auth type").setPromptBackground(new CirclePromptBackground()).setPromptFocal(new RectanglePromptFocal()).setBackgroundColour(getResources().getColor(R.color.buttonblue)).setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                Toast.makeText(context, "presseddddd", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).show();


                } else if (outlined_exposed_dropdown_key.getText().toString().equals("Auth Type")) {

                    if (outlined_exposed_dropdown_key.getText().toString().equals("Basic Auth")) {

                        int username_length = username.getText().toString().length();
                        int password_length = password.getText().toString().length();
                        Log.v("qqqqwwww", String.valueOf(username_length) + String.valueOf(password_length));

                        if (username_length > 0 && password_length > 0) {


                            String Credentials = username.getText().toString() + ":" + password.getText().toString();
                            String authdata = "Basic " + Base64.encodeToString(Credentials.getBytes(), Base64.NO_WRAP);

                            SharedPreferences.Editor editor = view.getContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
                            editor.putString("Authorization", authdata);
                            editor.apply();
                            Toast.makeText(getActivity(), "Authorization data updated", Toast.LENGTH_LONG).show();

                        } else {


                            Toast.makeText(getActivity(), "Enter Fields", Toast.LENGTH_LONG).show();

                        }


                    } else if (outlined_exposed_dropdown_key.getText().toString().equals("No auth")) {


                        //String Credentials = "No auth";
                        String authdata = "No auth";

                        SharedPreferences.Editor editor = view.getContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
                        editor.putString("Authorization", authdata);
                        editor.apply();
                    }


                }
//
//
//
//

            }
        });


       final ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, authdata);
        outlined_exposed_dropdown_key.setAdapter(arrayadapter);
        outlined_exposed_dropdown_key.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();
                outlined_exposed_dropdown_key.setText(arrayadapter.getItem(i).toString(),false);

                if (String.valueOf(i).equals("0"))
                {

                    username.setEnabled(false);
                    password.setEnabled(false);
                    username.setText("");
                    password.setText("");
                    username.setHintTextColor(getResources().getColor(R.color.gray));
                    password.setHintTextColor(getResources().getColor(R.color.gray));
                    //Log.v("sadsadsad", "0");


//                    String credentials = USERNAME+":"+PASSWORD;
//                    String auth = "Basic "
//                            + Base64.encodeToString(credentials.getBytes(),
//                            Base64.NO_WRAP);
//                    headers.put("Authorization", auth);


                } else if (String.valueOf(i).equals("1")) {
                    username.requestFocus();
                    username.setEnabled(true);
                    password.setEnabled(true);


                }
            }
        });

        return view;
    }
}
