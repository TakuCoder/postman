package thiyagu.postman.com.postmanandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

import thiyagu.postman.com.postmanandroid.Params.DataObject;
import thiyagu.postman.com.postmanandroid.Params.MyRecyclerViewAdapter;

/**
 * Created by thiyagu on 3/5/2018.
 */

public class PopUpActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button floatingActionButton;
    private static String LOG_TAG = "CardViewActivity";
    ArrayList<DataObject> results = new ArrayList<>();
    ArrayList<String> mydata = new ArrayList<String>();
    StringBuilder tempvalue = new StringBuilder();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        Log.v("asdasdasdsadsadiniitial", String.valueOf(results.size()));
        Button fab = findViewById(R.id.fab);
        Button fab1 = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydata.clear();
                RecyclerView recyclerView1 = findViewById(R.id.my_recycler_view);


                for (int i = 0; i < recyclerView1.getChildCount(); i++) {
                    View vvv = recyclerView1.getChildAt(i);
                    getAllChildren(vvv);
                    //
                }

                mRecyclerView.setAdapter(null);
                Log.v("asdasdasdsadsad", String.valueOf(results.size()));
                mAdapter = new MyRecyclerViewAdapter(update(results.size(),tempvalue));
                mRecyclerView.setAdapter(mAdapter);


            }
        });
//        fab1.setOnClickListener(new View.OnClickListener()
//
//        {
//            @Override
//            public void onClick(View v)
//
//            {
//                mydata.clear();
//                RecyclerView recyclerView1 = findViewById(R.id.my_recycler_view);
//
//
//                for (int i = 0; i < recyclerView1.getChildCount(); i++) {
//                    View vvv = recyclerView1.getChildAt(i);
//                    getAllChildren(vvv);
//                   //
//                }
//
//            }
//
//
//        });


    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++)
        {

            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);

            if (child instanceof EditText)
            {
                EditText editText = (EditText) child;
               // Log.v("valueeeeeeeeeeeeeeeeeee", editText.getText().toString());


                tempvalue.append(editText.getText().toString()+"@@");


                //mydata.add(editText.getText().toString());
            }


            if (child instanceof CheckBox)


            {

                CheckBox checkBox = (CheckBox) child;
                if (checkBox.isChecked())

                {


                  //  Log.v("valueeeeeeeeeeeeeeeeeee", "1");
                    tempvalue.append("1"+"==");

                }
                else
                {
                    tempvalue.append("0"+"==");
                   // Log.v("valueeeeeeeeeeeeeeeeeee", "0");

                }
//                {
//
//                    mydata.add("1");
//
//                } else
//
//                    {
//
//
//                    mydata.add("0");
//
//                }


            }

            //mydata.add(tempvalue.toString());
            //tempvalue.setLength(0);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }

        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    private ArrayList<DataObject> getDataSet()

    {


        DataObject obj = new DataObject("",
                "");


        results.add(0, obj);
        return results;
    }


    public ArrayList<DataObject> update(int place,StringBuilder data) {

        mRecyclerView.setAdapter(null);
        results.clear();

        String[] split = data.toString().split("==");
for(int k=0;k<split.length;k++)
{


    Log.v("sizeofarray",split[k]);
    String temppp[] = split[k].split("@@");
    String val1,val2;

    val1=  temppp[0];
    val2=  temppp[1];


    DataObject obj = new DataObject(val1,
            val2);
    results.add(k, obj);


}


        Log.v("sizeofarray",String.valueOf(split.length));


        Log.v("asdasdasdasdwasadsadasd",String.valueOf(place));
        DataObject obj = new DataObject("",
                "");
        results.add(place, obj);
        Log.v("asdasdasdsadsadSizeee", String.valueOf(results.size()));
        Log.v("asdasdsadasdasdsad", tempvalue.toString());
        tempvalue.setLength(0);
        return results;


    }


}
