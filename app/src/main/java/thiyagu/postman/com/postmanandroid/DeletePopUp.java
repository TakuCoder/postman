package thiyagu.postman.com.postmanandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;

public class DeletePopUp extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_pop_up);
        button = findViewById(R.id.delete);
        final Intent intent = getIntent();
        final String sss = intent.getStringExtra("deleteid");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(getApplication());
                feedReaderDbHelper.DeleteSingleRec(Integer.parseInt(sss));
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });
        Toast.makeText(getApplicationContext(), sss, Toast.LENGTH_LONG).show();

    }
}
