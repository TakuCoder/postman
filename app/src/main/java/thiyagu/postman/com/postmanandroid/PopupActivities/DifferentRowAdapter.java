package thiyagu.postman.com.postmanandroid.PopupActivities;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import thiyagu.postman.com.postmanandroid.Model.HistoryClass;
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

                    if(object.getReqtype().contains("DELETE"))
                    {
                        ((EventViewHolder) holder).method.setTextColor(Color.parseColor("#ff0000"));

                    }

                    if(object.getReqtype().contains("PUT"))
                    {
                        ((EventViewHolder) holder).method.setTextColor(Color.parseColor("#364EB9"));

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