package thiyagu.postman.com.postmanandroid.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import thiyagu.postman.com.postmanandroid.Activities.MainActivity;
import thiyagu.postman.com.postmanandroid.Database.AuthHolderData;
import thiyagu.postman.com.postmanandroid.R;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thiyagu on 4/6/2018.
 */

public class AuthorizationFragment extends Fragment {
    MaterialBetterSpinner materialBetterSpinner;
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

        Button updateauthrequest;
        final EditText username, password;


        final View view = inflater.inflate(R.layout.tab_fragment_authorization, container, false);
        context = view.getContext();
        materialBetterSpinner = view.findViewById(R.id.material_spinnerauth);
        final String[] authdata = {"No auth", "Basic Auth"};
        materialBetterSpinner.setBackgroundColor(Color.parseColor("#464646"));

        updateauthrequest = view.findViewById(R.id.updateauthrequest);
        username = view.findViewById(R.id.input_username);
        password = view.findViewById(R.id.input_password);
        materialBetterSpinner.setHideUnderline(true);

        updateauthrequest.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                if (!materialBetterSpinner.isSelected()) {




                    Log.v("asdasdasdsa", "nulllllll");
                    new MaterialTapTargetPrompt.Builder(getActivity())
                            .setTarget(view.findViewById(R.id.material_spinnerauth))
                            .setPrimaryText("Select the auth type")
                            .setPromptBackground(new CirclePromptBackground())
                            .setPromptFocal(new RectanglePromptFocal())
                            .setBackgroundColour(getResources().getColor(R.color.buttonblue))
                            .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                @Override
                                public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                        Toast.makeText(context, "presseddddd", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .show();

                }


                if (materialBetterSpinner.getText().toString().equals("Basic Auth")) {


                    if (username.getText().toString().equals("")) {


                    } else if (password.getText().toString().equals("")) {


                    } else {

                        AuthHolderData authHolderData = new AuthHolderData();
                        String Credentials = username.getText().toString() + ":" + password.getText().toString();
                        String authdata = "Basic " + Base64.encodeToString(Credentials.getBytes(), Base64.NO_WRAP);
                        authHolderData.setBasicAuth(authdata);
                        SharedPreferences.Editor editor = view.getContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
                        editor.putString("Authorization", authdata);
                        editor.apply();


                    }


                } else if (materialBetterSpinner.getText().toString().equals("No auth")) {

                    AuthHolderData authHolderData = new AuthHolderData();
                    String Credentials = "No auth";
                    String authdata = "No auth";
                    authHolderData.setBasicAuth(authdata);
                    SharedPreferences.Editor editor = view.getContext().getSharedPreferences("Thiyagu", MODE_PRIVATE).edit();
                    editor.putString("Authorization", authdata);
                    editor.apply();
                }
            }
        });


        ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, authdata);
        materialBetterSpinner.setAdapter(arrayadapter);
        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();


                if (String.valueOf(i).equals("1")) {


                    Log.v("sadsadsad", "1");


//                    String credentials = USERNAME+":"+PASSWORD;
//                    String auth = "Basic "
//                            + Base64.encodeToString(credentials.getBytes(),
//                            Base64.NO_WRAP);
//                    headers.put("Authorization", auth);


                }
            }
        });

        return view;
    }
}
