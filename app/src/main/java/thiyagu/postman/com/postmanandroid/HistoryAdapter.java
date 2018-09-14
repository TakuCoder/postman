package thiyagu.postman.com.postmanandroid;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<HistoryClass> historyClassList;

    //getting the context and product list with constructor
    public HistoryAdapter(Context mCtx, List<HistoryClass> historyClassList) {
        this.mCtx = mCtx;
        this.historyClassList = historyClassList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cardview_layout, null);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, int position) {
        //getting the historyClass of the specified position
        final HistoryClass historyClass = historyClassList.get(position);

      //binding the data with the viewholder views


        holder.url.setText(historyClass.getUrl());
        holder.duration.setText(historyClass.getDuration());
        holder.size.setText(String.valueOf(historyClass.getSize()));
        holder.history_cardview.setTag(historyClass.getTag());
        holder.response_code.setText(String.valueOf(historyClass.getResponse_code()));
        holder.imageView.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        holder.time.setText(String.valueOf(historyClass.getTime()));
        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(historyClass.getResponse_code()));
//holder.history_cardview.setOnLongClickListener(new View.OnLongClickListener() {
//    @Override
//    public boolean onLongClick(View view) {
//        Toast.makeText(mCtx.getApplicationContext(),historyClass.getTag(),Toast.LENGTH_LONG).show();
//        return true;
//    }
//});

        Log.v("getdata",historyClass.getUrl()+" url"+"\n"+
                historyClass.getDuration()+" duration"+"\n"+
                historyClass.getSize()+" size"+"\n"+
                historyClass.getTime()+" time"+"\n"+
                historyClass.getResponse_code()+" responsecode"+"\n"+
                historyClass.getTag()+" tag"+"\n"+
                historyClass.getUrl()+" url"+"\n"
        );
        holder.history_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,holder.history_cardview.getTag().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return historyClassList.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView url, duration, size,response_code,time;
        RelativeLayout imageView;
        CardView history_cardview;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            history_cardview = itemView.findViewById(R.id.history_cardview);
            time = itemView.findViewById(R.id.time_view);
            url = itemView.findViewById(R.id.url);
            size = itemView.findViewById(R.id.size);
            duration = itemView.findViewById(R.id.duration);
            response_code = itemView.findViewById(R.id.responsecode);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}