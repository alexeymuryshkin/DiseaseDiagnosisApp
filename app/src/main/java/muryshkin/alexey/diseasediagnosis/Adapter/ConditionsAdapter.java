package muryshkin.alexey.diseasediagnosis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import muryshkin.alexey.diseasediagnosis.Data.DataHolder;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * Created by Alexey Muryshkin on 24.11.2016.
 */

public class ConditionsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private JSONArray conditions;

    public ConditionsAdapter(Context context, JSONArray conditions) {
        this.context = context;
        this.conditions = conditions;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return conditions.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_condition_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.conditionTitleTextView.setText( conditions.getJSONObject(position).getString("name") );
            viewHolder.conditionProbabilityTextView.setText( conditions.getJSONObject(position).getString("probability") );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView conditionTitleTextView;
        TextView conditionProbabilityTextView;

        public ViewHolder(View v) {
            conditionTitleTextView = (TextView) v.findViewById(R.id.conditionTitleTextView);
            conditionProbabilityTextView = (TextView) v.findViewById(R.id.conditionProbabilityTextView);
        }
    }
}
