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

import thiyagu.postman.com.postmanandroid.Database.Body;
import thiyagu.postman.com.postmanandroid.Database.FeedReaderDbHelper;

import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.R;

public class BodyAdapter extends RecyclerView
        .Adapter<BodyAdapter
        .DataObjectHolder> implements View.OnClickListener {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<Body> mDataset;
    public Context mcontext;
    TelleriumDataDatabase database;
    private static MyClickListener myClickListener;



    public BodyAdapter(Body dataObject, int i)
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
        CheckBox checkBox;
        public DataObjectHolder(View itemView) {
            super(itemView);
            key =  itemView.findViewById(R.id.textView);
            value = itemView.findViewById(R.id.textView2);
            card_view = itemView.findViewById(R.id.card_view);
            assetManager = itemView.getContext().getAssets();
            roboto=Typeface.createFromAsset(assetManager,"fonts/Roboto-Bold.ttf");
            checkBox = itemView.findViewById(R.id.flag);
            Log.i(LOG_TAG, "Adding Listener");
            Log.v(LOG_TAG,key.getText().toString());

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public BodyAdapter(List<Body> myDataset, Context context) {
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
    public void onBindViewHolder(final DataObjectHolder holder, final int position)


    {
        Typeface typeface = holder.roboto;
        holder.key.setTypeface(typeface);
        holder.value.setTypeface(typeface);

        holder.key.setText(mDataset.get(position).getKey());
        holder.value.setText(mDataset.get(position).getValue());
        holder.card_view.setTag(mDataset.get(position).getReferenceId());

        Log.v("customtag", mDataset.get(position).getFlag());
        if (mDataset.get(position).getFlag().equals("true")) {
            holder.checkBox.setChecked(false);

        } else {

            holder.checkBox.setChecked(true);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ASas","AasasaS");
                Log.v("sdsd",holder.checkBox.isChecked()+"" );
                //Log.v(Tag, "CheckBoxStatusheader" + holder.checkBox.isChecked() + mDataset.get(position).getTag());
                database.getbodyDAO().updateBody(String.valueOf(holder.checkBox.isChecked()), mDataset.get(position).getReferenceId());
            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                //  FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(mcontext);
                // feedReaderDbHelper.DeleteSingleRecParam(position);
                // Log.v("asdasdasd",String.valueOf(position));

//                Intent intent = new Intent(mcontext, DeletePopUp.class);
//                intent.putExtra("deleteid",String.valueOf(sss));
//                intent.putExtra("whichrecord","body");
//                mcontext.startActivity(intent);


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mcontext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mcontext);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String sss = holder.card_view.getTag().toString();

                                FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(mcontext);
                                feedReaderDbHelper.DeleteSingleRecBody(Integer.parseInt(sss));
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



            }
        });

    }

    public void addItem(Body dataObj, int index) {
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