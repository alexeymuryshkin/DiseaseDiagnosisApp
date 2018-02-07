package muryshkin.alexey.diseasediagnosis.Adapter;

import android.content.Context;
import android.util.Log;
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
 * Created by Alexey Muryshkin on 25.11.2016.
 */

public class GSQuestionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private JSONArray items;

    public GSQuestionAdapter(Context context, JSONArray items) {
        this.context = context;
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.length();
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
            convertView = inflater.inflate(R.layout.row_answer_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.answerTextView.setText( items.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("GSQuestionAdapter", "Selected answer is " + DataHolder.getInstance().getAnswerSet().get(0));
        Log.d("GSQuestionAdapter", "Current item is " + position);
        if (DataHolder.getInstance().getAnswerSet().get(0) == position)
            viewHolder.answerTextView.setBackgroundResource(R.drawable.textview_design_test_answer_correct);
        else
            viewHolder.answerTextView.setBackgroundResource(R.drawable.textview_design_test_answer);

        return convertView;
    }

    private class ViewHolder {
        TextView answerTextView;

        public ViewHolder(View v) {
            answerTextView = (TextView) v.findViewById(R.id.answerTextView);
        }
    }
}
