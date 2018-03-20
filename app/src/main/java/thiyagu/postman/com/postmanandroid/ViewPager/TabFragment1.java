package thiyagu.postman.com.postmanandroid.ViewPager;

import android.support.v4.app.Fragment;

/**
 * Created by thiyagu on 3/19/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thiyagu.postman.com.postmanandroid.R;

public class TabFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_1, container, false);
    }
}