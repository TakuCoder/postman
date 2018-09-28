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

import static thiyagu.postman.com.postmanandroid.HistoryClass.DATE_TYPE;
import static thiyagu.postman.com.postmanandroid.HistoryClass.RESPONSE_TYPE;
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


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
        View view;
        LayoutInflater inflater;
        switch (viewType)
        {

            case DATE_TYPE:

                inflater   = LayoutInflater.from(mCtx);
                 view = inflater.inflate(R.layout.date, null);
                return new HistoryViewHolder(view);


            case RESPONSE_TYPE:

               inflater = LayoutInflater.from(mCtx);
               view = inflater.inflate(R.layout.cardview_layout, null);
                return new HistoryViewHolder(view);




        }

return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //getting the historyClass of the specified position
        final HistoryClass historyClass = historyClassList.get(position);

      //binding the data with the viewholder views
switch (holder.getItemViewType())
{
    case DATE_TYPE:
        ((DateViewHolder) holder).titleTextView.setText(historyClass.getUrl());
        Log.v("whichview","1");
        break;

    case RESPONSE_TYPE:
        Log.v("whichview","2");
        ((HistoryViewHolder) holder).url.setText(historyClass.getUrl());
        ((HistoryViewHolder) holder).duration.setText(historyClass.getDuration());
        ((HistoryViewHolder) holder).size.setText(String.valueOf(historyClass.getSize()));
        ((HistoryViewHolder) holder).history_cardview.setTag(historyClass.getTag());
        ((HistoryViewHolder) holder).response_code.setText(String.valueOf(historyClass.getResponse_code()));
        ((HistoryViewHolder) holder).imageView.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        ((HistoryViewHolder) holder).time.setText(String.valueOf(historyClass.getTime()));

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
        ((HistoryViewHolder) holder).history_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,((HistoryViewHolder) holder).history_cardview.getTag().toString(),Toast.LENGTH_LONG).show();
            }
        });
        break;


}



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


    class DateViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;

        public DateViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (historyClassList != null) {
         HistoryClass   object = historyClassList.get(position);
            if (object != null) {
                return object.getmType();
            }
        }
        return 0;
    }
}