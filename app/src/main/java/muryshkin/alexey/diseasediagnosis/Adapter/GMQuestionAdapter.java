package muryshkin.alexey.diseasediagnosis.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import muryshkin.alexey.diseasediagnosis.Data.DataHolder;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * Created by Alexey Muryshkin on 24.11.2016.
 */

public class GMQuestionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private JSONArray items;

    public GMQuestionAdapter(Context context, JSONArray items) {
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
            convertView = inflater.inflate(R.layout.row_item_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            viewHolder.symptomTextView.setText( items.getJSONObject(position).getString("name"));
            JSONArray choices = items.getJSONObject(position).getJSONArray("choices");
            List<String> choicesNames = new ArrayList<>();
            for (int i = 0; i < choices.length(); i++)
                choicesNames.add( choices.getJSONObject(i).getString("label") );

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, choicesNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            viewHolder.choicesSpinner.setAdapter(adapter);
            final int pos = position;

            viewHolder.choicesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    DataHolder.getInstance().getAnswerSet().set(pos, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder {
        TextView symptomTextView;
        Spinner choicesSpinner;

        public ViewHolder(View v) {
            symptomTextView = (TextView) v.findViewById(R.id.symptomTextView);
            choicesSpinner = (Spinner) v.findViewById(R.id.choicesSpinner);
        }
    }
}
