package thiyagu.postman.com.postmanandroid.Fragment;

/**
 * Created by thiyagu on 3/6/2018.
 */

import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Header;
import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.R;

public class HeaderAdapter extends RecyclerView
        .Adapter<HeaderAdapter
        .DataObjectHolder> implements View.OnClickListener {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Header> mDataset;
    public Context mcontext;
    TelleriumDataDatabase database;
    private static MyClickListener myClickListener;
    String Tag ;

    public HeaderAdapter(Header dataObject, int i) {
        addItem(dataObject, i);
        Tag = this.getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick ", String.valueOf(v.getId()));
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView key;
        TextView value;
        CardView card_view;
        Typeface roboto;
        AssetManager assetManager;
        CheckBox checkBox;

        public DataObjectHolder(View itemView) {
            super(itemView);

            assetManager = itemView.getContext().getAssets();
            roboto = Typeface.createFromAsset(assetManager, "fonts/Roboto-Bold.ttf");
            key = itemView.findViewById(R.id.textView);
            value = itemView.findViewById(R.id.textView2);
            card_view = itemView.findViewById(R.id.card_view);
            checkBox = itemView.findViewById(R.id.flag);
            Log.i(LOG_TAG, "Adding Listener");
            Log.v(LOG_TAG, key.getText().toString());

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public HeaderAdapter(List<Header> myDataset, Context context) {
        mDataset = myDataset;
        mcontext = context;

        database = Room.databaseBuilder(mcontext, TelleriumDataDatabase.class, "data_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
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
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        Typeface typeface = holder.roboto;
        holder.key.setTypeface(typeface);
        holder.value.setTypeface(typeface);

        holder.key.setText(mDataset.get(position).getKey());
        holder.value.setText(mDataset.get(position).getValue());
        holder.card_view.setTag(mDataset.get(position).getTag());


        Log.v("customtag", mDataset.get(position).getFlag());
        if (mDataset.get(position).getFlag().equals("true")) {
            holder.checkBox.setChecked(true);

        } else {

            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ASas","AasasaS");
                Log.v("sdsd",holder.checkBox.isChecked()+"" );
                //Log.v(Tag, "CheckBoxStatusheader" + holder.checkBox.isChecked() + mDataset.get(position).getTag());
                database.getHeaderDAO().updateHeader(String.valueOf(holder.checkBox.isChecked()), mDataset.get(position).getTag());
            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                //  FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(mcontext);
                // feedReaderDbHelper.DeleteSingleRecParam(position);
                // Log.v("asdasdasd",String.valueOf(position));


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mcontext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mcontext);
                }
                builder.setTitle("Delete Header entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String tag = holder.card_view.getTag().toString();



                                database.getHeaderDAO().DeleteHeader(tag);
//                                FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(mcontext);
//                                feedReaderDbHelper.DeleteSingleRecHeader(Integer.parseInt(sss));


                                deleteItem(position);// continue with delete

                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, mDataset.size());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                // ParamFragment oneFragment = new ParamFragment();
                //oneFragment.RefereshView();

            }
        });

    }

    public void addItem(Header dataObj, int index) {
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