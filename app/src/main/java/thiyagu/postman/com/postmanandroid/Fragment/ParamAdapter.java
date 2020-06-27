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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Database.Databases.TelleriumDataDatabase;
import thiyagu.postman.com.postmanandroid.Database.parameters;
import thiyagu.postman.com.postmanandroid.R;

public class ParamAdapter extends RecyclerView
        .Adapter<ParamAdapter
        .DataObjectHolder> implements View.OnClickListener {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<parameters> mDataset;
    public Context mcontext;
    private static MyClickListener myClickListener;
    String Tag = this.getClass().getSimpleName();

     TelleriumDataDatabase database;
    public ParamAdapter(parameters dataObject, int i) {
        addItem(dataObject, i);


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

    public ParamAdapter(List<parameters> myDataset, Context context) {
        mDataset = myDataset;
        mcontext = context;

        database  = Room.databaseBuilder(context, TelleriumDataDatabase.class, "data_db")
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

        Log.v(Tag, mDataset.get(position).getFlag());
        if (mDataset.get(position).getFlag().equals("true")) {
            holder.checkBox.setChecked(true);

        } else {

            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(Tag, "CheckBoxStatus" + holder.checkBox.isChecked() + mDataset.get(position).getTag());
                database.getParametersDAO().updateParam(String.valueOf(holder.checkBox.isChecked()), mDataset.get(position).getTag());
            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


//                String sss = holder.card_view.getTag().toString();
//
//                Log.v("this is position",String.valueOf(position));
//
//
//                FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(mcontext);
//                feedReaderDbHelper.DeleteSingleRecParam(Integer.parseInt(sss));
//
//                //ParamFragment.refershView();
//
//                Log.v("deleting this",String.valueOf(position));
//                mDataset.remove(position);
//                notifyItemRemoved(position);
//
//                notifyItemRangeChanged(position, mDataset.size());


                Log.v("this is position", String.valueOf(position));
                for (int h = 0; h < mDataset.size(); h++) {

                    Log.v("this is data", mDataset.get(h).getKey() + "=======>" + h);

                }

                androidx.appcompat.app.AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new androidx.appcompat.app.AlertDialog.Builder(mcontext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new androidx.appcompat.app.AlertDialog.Builder(mcontext);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String sss = holder.card_view.getTag().toString();

                                Log.v("this is position", String.valueOf(position));


                                database.getParametersDAO().deleteParam(sss);
                                //  FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(mcontext);
                                //feedReaderDbHelper.DeleteSingleRecParam(Integer.parseInt(sss));

                                //ParamFragment.refershView();

                                Log.v("deleting this", String.valueOf(position));
                                mDataset.remove(position);
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
//
//
//
//
////                SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.getContext(), SweetAlertDialog.PROGRESS_TYPE);
////                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
////                pDialog.setTitleText("Loading");
////                pDialog.setCancelable(false);
////                pDialog.show();
//
//                // ParamFragment oneFragment = new ParamFragment();
//                //oneFragment.RefereshView();

            }
        });

    }

    public void addItem(parameters dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        try {

            mDataset.remove(index);
            notifyItemRemoved(index);
        } catch (Exception e) {

            Log.v("deleting this error", String.valueOf(index));
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}