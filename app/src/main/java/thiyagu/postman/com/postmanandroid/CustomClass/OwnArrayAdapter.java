package thiyagu.postman.com.postmanandroid.CustomClass;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;

import java.util.List;
public class OwnArrayAdapter<T> extends ArrayAdapter<T> {
    public List<T> items;
    private Filter filter = new NoFilter();
    public OwnArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);

    }
    public OwnArrayAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
    public OwnArrayAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);

    }
    public OwnArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull T[] objects) {
        super(context, resource, textViewResourceId, objects);

    }
    public OwnArrayAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        items = objects;
    }
    public OwnArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        items = objects;
    }
//    public OwnArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List objects) {
//        super(context, resource, textViewResourceId, objects);
//        items = objects;
//    }

    @NonNull
    @Override
    public Filter getFilter() {
        return null;
    }
    private class NoFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            result.values = items;
            result.count = items.size();
            return result;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notifyDataSetChanged();
        }
    }
}
