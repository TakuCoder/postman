package thiyagu.postman.com.postmanandroid.Params;

/**
 * Created by thiyagu on 3/6/2018.
 */

import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

        import java.util.ArrayList;

import thiyagu.postman.com.postmanandroid.R;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;




    public MyRecyclerViewAdapter(DataObject dataObject, int i)
    {


        addItem(dataObject,i);

    }
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        EditText label;
        EditText dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label =  itemView.findViewById(R.id.textView);
            dateTime = itemView.findViewById(R.id.textView2);
            Log.i(LOG_TAG, "Adding Listener");
            Log.v(LOG_TAG,label.getText().toString());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());
        holder.dateTime.setText(mDataset.get(position).getmText2());

Log.v("asdasdasdsadsadSizeee",mDataset.get(position).getmText1());
holder.dateTime.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v)
    {


        Log.v("asdasdasdasdsadsa",holder.dateTime.getText().toString());
    }
});

    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}