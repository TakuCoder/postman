package thiyagu.postman.com.postmanandroid.Fragment;

/**
 * Created by thiyagu on 3/6/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import thiyagu.postman.com.postmanandroid.PopupActivities.DeletePopUp;
import thiyagu.postman.com.postmanandroid.R;

public class BodyAdapter extends RecyclerView
        .Adapter<BodyAdapter
        .DataObjectHolder> implements View.OnClickListener {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<BodyDataObject> mDataset;
    public Context mcontext;

    private static MyClickListener myClickListener;


    public BodyAdapter(BodyDataObject dataObject, int i)
    {
        addItem(dataObject,i);
    }

    @Override
    public void onClick(View v) {
        Log.d( "onClick " , String.valueOf(v.getId()));
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        TextView key;
        TextView value;
        CardView card_view;
        Typeface roboto;
        AssetManager assetManager;

        public DataObjectHolder(View itemView) {
            super(itemView);
            key =  itemView.findViewById(R.id.textView);
            value = itemView.findViewById(R.id.textView2);
            card_view = itemView.findViewById(R.id.card_view);
            assetManager = itemView.getContext().getAssets();
            roboto=Typeface.createFromAsset(assetManager,"fonts/Roboto-Regular.ttf");
            Log.i(LOG_TAG, "Adding Listener");
            Log.v(LOG_TAG,key.getText().toString());

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public BodyAdapter(ArrayList<BodyDataObject> myDataset, Context context) {
        mDataset = myDataset;
        mcontext = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_params, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position)


    {
        Typeface typeface = holder.roboto;
        holder.key.setTypeface(typeface);
        holder.value.setTypeface(typeface);
        holder.key.setText(mDataset.get(position).getmText1());
        holder.value.setText(mDataset.get(position).getmText2());
        holder.card_view.setTag(mDataset.get(position).getTag());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                //  FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(mcontext);
                // feedReaderDbHelper.DeleteSingleRecParam(position);
                // Log.v("asdasdasd",String.valueOf(position));

                String sss = holder.card_view.getTag().toString();
                Intent intent = new Intent(mcontext, DeletePopUp.class);
                intent.putExtra("deleteid",String.valueOf(sss));
                intent.putExtra("whichrecord","body");
                mcontext.startActivity(intent);


                // ParamFragment oneFragment = new ParamFragment();
                //oneFragment.RefereshView();

            }
        });

    }

    public void addItem(BodyDataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public  int getItemCount() {
        return mDataset.size();
    }




    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}