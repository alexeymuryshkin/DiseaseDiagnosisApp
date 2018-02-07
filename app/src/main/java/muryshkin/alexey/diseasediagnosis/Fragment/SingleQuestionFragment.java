package muryshkin.alexey.diseasediagnosis.Fragment;


import android.app.Fragment;
import android.app.admin.DeviceAdminInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import muryshkin.alexey.diseasediagnosis.Activity.TestActivity;
import muryshkin.alexey.diseasediagnosis.Adapter.SingleQuestionAdapter;
import muryshkin.alexey.diseasediagnosis.Data.DataHolder;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // private static final String ARG_PARAM1 = "param1";
    //  private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private static JSONObject question;
    //private String mParam2;


    public SingleQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SingleQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleQuestionFragment newInstance() {
        SingleQuestionFragment fragment = new SingleQuestionFragment();
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
        final View v = inflater.inflate(R.layout.fragment_single_question, container, false);

        final JSONObject question = DataHolder.getInstance().getQuestion();
        final JSONObject request = DataHolder.getInstance().getRequest();

        TextView questionTextView = (TextView) v.findViewById(R.id.questionTextView);
        try {
            questionTextView.setText( question.getString("text") );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Button nextButton = (Button) v.findViewById(R.id.nextButton);
        DataHolder.getInstance().setAnswerSet(1, -1);

        ListView answersListView = (ListView) v.findViewById(R.id.answersListView);

        JSONArray choices;

        try {
            choices = question.getJSONArray("items").getJSONObject(0).getJSONArray("choices");
        } catch (JSONException e) {
            e.printStackTrace();
            choices = new JSONArray();
        }

        Log.d("SingleQuestionFragment", choices.toString());
        Log.d("SingleQuestionFragment", "Number of elements in choices is " + choices.length());
        final SingleQuestionAdapter adapter = new SingleQuestionAdapter( ((TestActivity)getActivity()), choices );
        answersListView.setAdapter( adapter );

        answersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataHolder.getInstance().getAnswerSet().set(0, position);
                Log.d("SingleQuestionFragment", "Selected item number is " + position);
                adapter.notifyDataSetChanged();
            }
        });

        final JSONArray finalChoices = choices;

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ans = DataHolder.getInstance().getAnswerSet().get(0);

                if ( ans == -1 )
                    ((TestActivity)getActivity()).showError( "You did not answered the question" );
                else {
                    nextButton.setEnabled(false);

                    JSONObject answer = new JSONObject();
                    try {
                        String sex = request.getString("sex");
                        int age = request.getInt("age");
                        JSONArray evidence = request.getJSONArray("evidence");
                        JSONObject item = new JSONObject();
                        String id = question.getJSONArray("items").getJSONObject(0).getString("id");
                        String choice_id = finalChoices.getJSONObject(ans).getString("id");
                        item.put("id", id);
                        item.put("choice_id", choice_id);
                        evidence.put(item);
                        answer.put("sex", sex);
                        answer.put("age", age);
                        answer.put("evidence", evidence);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d( "SingleQuestionFragment", answer.toString() );
                    ((TestActivity)getActivity()).generateQuestion( answer );
                }
            }
        });

        return v;
    }

}
