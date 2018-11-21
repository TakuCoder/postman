package thiyagu.postman.com.postmanandroid.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import thiyagu.postman.com.postmanandroid.Fragment.AuthorizationFragment;
import thiyagu.postman.com.postmanandroid.Fragment.BodyFragment;
import thiyagu.postman.com.postmanandroid.Fragment.HeaderFragment;
import thiyagu.postman.com.postmanandroid.Fragment.JsonFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ParamFragment;
import thiyagu.postman.com.postmanandroid.Fragment.Preview;
import thiyagu.postman.com.postmanandroid.Fragment.RawFragment;
import thiyagu.postman.com.postmanandroid.Fragment.ViewPagerAdapter;
import thiyagu.postman.com.postmanandroid.R;

public class ResultActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String value = getIntent().getStringExtra("url");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.pager1);
        tabLayout = findViewById(R.id.tab_layout);
        setSupportActionBar(toolbar);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);







    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RawFragment(), "RAW");
        adapter.addFragment(new JsonFragment(), "JSON");
        adapter.addFragment(new Preview(), "PREVIEW");
        // adapter.addFragment(new ResponseFragment(), "RESPONSE");
        viewPager.setAdapter(adapter);
    }

}
