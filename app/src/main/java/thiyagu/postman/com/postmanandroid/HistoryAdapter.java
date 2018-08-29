package thiyagu.postman.com.postmanandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


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
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        //getting the historyClass of the specified position
        HistoryClass historyClass = historyClassList.get(position);

      //binding the data with the viewholder views
        holder.textViewTitle.setText(historyClass.getTitle());
        holder.textViewShortDesc.setText(historyClass.getShortdesc());
        holder.textViewRating.setText(String.valueOf(historyClass.getRating()));
        holder.textViewPrice.setText(String.valueOf(historyClass.getPrice()));

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(historyClass.getImage()));


    }


    @Override
    public int getItemCount() {
        return historyClassList.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}