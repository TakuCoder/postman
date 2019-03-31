package thiyagu.postman.com.postmanandroid.PopupActivities;//package thiyagu.postman.com.postmanandroid.PopupActivities;
//
//import android.content.Context;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.List;
//
//import thiyagu.postman.com.postmanandroid.HistoryAdapter;
//import thiyagu.postman.com.postmanandroid.HistoryClass;
//import thiyagu.postman.com.postmanandroid.R;
//
//import static thiyagu.postman.com.postmanandroid.HistoryClass.DATE_TYPE;
//import static thiyagu.postman.com.postmanandroid.HistoryClass.RESPONSE_TYPE;
//
//public class DifferentRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private Context mCtx;
//
//    //we are storing all the products in a list
//    private List<HistoryClass> historyClassList;
//
//    public DifferentRowAdapter(List<HistoryClass> historyClassList) {
//        this.historyClassList = historyClassList;
//
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//    {
//        View view;
//
//        switch (viewType)
//        {
//
//
//            case DATE_TYPE:
//
////                inflater   = LayoutInflater.from(mCtx);
////                view = inflater.inflate(R.layout.date, null);
////                return new HistoryAdapter.DateViewHolder(view);
//
//                Log.v("whichview","1");
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date, parent, false);
//                return new HistoryViewHolder(view);
//
//            case RESPONSE_TYPE:
//                /**/
//                Log.v("whichview","2");
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
//                return new DateViewHolder(view);
//
//
//
////                inflater = LayoutInflater.from(mCtx);
////                view = inflater.inflate(R.layout.cardview_layout, null);
////                return new HistoryAdapter.HistoryViewHolder(view);
//
//
//        }
//        return null;
//    }
//
//    class HistoryViewHolder extends RecyclerView.ViewHolder {
//
//        TextView url, duration, size, response_code, time;
//        RelativeLayout imageView;
//        CardView history_cardview;
//
//        public HistoryViewHolder(View itemView) {
//            super(itemView);
//            history_cardview = itemView.findViewById(R.id.history_cardview);
//            time = itemView.findViewById(R.id.time_view);
//            url = itemView.findViewById(R.id.url);
//            size = itemView.findViewById(R.id.size);
//            duration = itemView.findViewById(R.id.duration);
//            response_code = itemView.findViewById(R.id.responsecode);
//            imageView = itemView.findViewById(R.id.imageView);
//        }
//    }
//
//
//    class DateViewHolder extends RecyclerView.ViewHolder {
//
//        TextView titleTextView;
//
//        public DateViewHolder(View itemView) {
//            super(itemView);
//
//
//
//            titleTextView = itemView.findViewById(R.id.titleTextView);
//
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//        //getting the historyClass of the specified position
//        final HistoryClass historyClass = historyClassList.get(position);
//
//        //binding the data with the viewholder views
//        switch (historyClass.getmType()) {
//            case DATE_TYPE:
//                ((DateViewHolder) holder).titleTextView.setText(historyClass.getUrl());
//                Log.v("whichview", "1");
//                break;
//
//            case RESPONSE_TYPE:
//                Log.v("whichview", "2");
//                ((HistoryViewHolder) holder).url.setText(historyClass.getUrl());
//                ((HistoryViewHolder) holder).duration.setText(historyClass.getDuration());
//                ((HistoryViewHolder) holder).size.setText(String.valueOf(historyClass.getSize()));
//                ((HistoryViewHolder) holder).history_cardview.setTag(historyClass.getTag());
//                ((HistoryViewHolder) holder).response_code.setText(String.valueOf(historyClass.getResponse_code()));
//                ((HistoryViewHolder) holder).imageView.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
//                ((HistoryViewHolder) holder).time.setText(String.valueOf(historyClass.getTime()));
//                //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(historyClass.getResponse_code()));
////holder.history_cardview.setOnLongClickListener(new View.OnLongClickListener() {
////    @Override
////    public boolean onLongClick(View view) {
////        Toast.makeText(mCtx.getApplicationContext(),historyClass.getTag(),Toast.LENGTH_LONG).show();
////        return true;
////    }
////});
//
//                Log.v("getdata", historyClass.getUrl() + " url" + "\n" +
//                        historyClass.getDuration() + " duration" + "\n" +
//                        historyClass.getSize() + " size" + "\n" +
//                        historyClass.getTime() + " time" + "\n" +
//                        historyClass.getResponse_code() + " responsecode" + "\n" +
//                        historyClass.getTag() + " tag" + "\n" +
//                        historyClass.getUrl() + " url" + "\n"
//                );
//                ((HistoryViewHolder) holder).history_cardview.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(mCtx, ((HistoryViewHolder) holder).history_cardview.getTag().toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//                break;
//
//
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thiyagu.postman.com.postmanandroid.HistoryClass;
import thiyagu.postman.com.postmanandroid.R;

import static thiyagu.postman.com.postmanandroid.Activities.ViewType.CITY_TYPE;
import static thiyagu.postman.com.postmanandroid.Activities.ViewType.EVENT_TYPE;

public class DifferentRowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HistoryClass> mList;
    public DifferentRowAdapter(List<HistoryClass> list) {
        this.mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CITY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
                return new CityViewHolder(view);
            case EVENT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requests, parent, false);
                return new EventViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistoryClass object = mList.get(position);
        if (object != null) {
            switch (object.getmType()) {
                case CITY_TYPE:
                    ((CityViewHolder) holder).url.setText(object.getDate());
                    break;
                case EVENT_TYPE:
                    ((EventViewHolder) holder).url.setText(object.getUrl());
                    ((EventViewHolder) holder).method.setText(object.getReqtype());
                    Log.v("asdsad", object.getReqtype());
                    if(object.getReqtype().contains("GET"))
                    {
                        ((EventViewHolder) holder).method.setTextColor(Color.parseColor("#64dd17"));

                    }
                    if(object.getReqtype().contains("POST"))
                    {
                        ((EventViewHolder) holder).method.setTextColor(Color.parseColor("#ffa000"));

                    }
                    break;
            }
        }
    }
    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            HistoryClass object = mList.get(position);
            if (object != null) {
                return object.getmType();
            }
        }
        return 0;
    }
    public static class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView url;
        public CityViewHolder(View itemView) {
            super(itemView);
            url = (TextView) itemView.findViewById(R.id.url);
        }
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView url;
        private TextView method;
        public EventViewHolder(View itemView) {
            super(itemView);
            url = (TextView) itemView.findViewById(R.id.url);
            method = (TextView) itemView.findViewById(R.id.method);
        }
    }
}