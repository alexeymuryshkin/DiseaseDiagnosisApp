package muryshkin.alexey.diseasediagnosis.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import muryshkin.alexey.diseasediagnosis.Activity.TestActivity;
import muryshkin.alexey.diseasediagnosis.Adapter.GMQuestionAdapter;
import muryshkin.alexey.diseasediagnosis.Adapter.SingleQuestionAdapter;
import muryshkin.alexey.diseasediagnosis.Data.DataHolder;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupMultipleQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupMultipleQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // private static final String ARG_PARAM1 = "param1";
    //  private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private static JSONObject question;
    //private String mParam2;


    public GroupMultipleQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SingleQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupMultipleQuestionFragment newInstance() {
        GroupMultipleQuestionFragment fragment = new GroupMultipleQuestionFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, question);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //question = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_group_multiple_question, container, false);

        final JSONObject question = DataHolder.getInstance().getQuestion();
        final JSONObject request = DataHolder.getInstance().getRequest();
        final JSONArray items = DataHolder.getInstance().getItems();

        TextView questionTextView = (TextView) v.findViewById(R.id.questionTextView);
        try {
            questionTextView.setText( question.getString("text") );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Button nextButton = (Button) v.findViewById(R.id.nextButton);
        DataHolder.getInstance().setAnswerSet( items.length(), 0 );

        ListView answersListView = (ListView) v.findViewById(R.id.answersListView);

        final GMQuestionAdapter adapter = new GMQuestionAdapter( ((TestActivity)getActivity()), items );
        answersListView.setAdapter( adapter );

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton.setEnabled(false);

                JSONObject answer = new JSONObject();
                try {
                    String sex = request.getString("sex");
                    int age = request.getInt("age");
                    JSONArray evidence = request.getJSONArray("evidence");

                    List<Integer> answerSet = DataHolder.getInstance().getAnswerSet();

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = new JSONObject();
                        String id = items.getJSONObject(i).getString("id");
                        String choice_id = items.getJSONObject(i).getJSONArray("choices").getJSONObject( answerSet.get(i) ).getString("id");
                        item.put("id", id);
                        item.put("choice_id", choice_id);
                        evidence.put(item);
                    }
                    answer.put("sex", sex);
                    answer.put("age", age);
                    answer.put("evidence", evidence);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d( "GMQuestionFragment", answer.toString() );
                ((TestActivity)getActivity()).generateQuestion( answer );
            }
        });

        return v;
    }
}
