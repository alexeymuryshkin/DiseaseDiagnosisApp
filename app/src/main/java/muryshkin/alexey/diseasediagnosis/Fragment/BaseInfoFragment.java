package muryshkin.alexey.diseasediagnosis.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import muryshkin.alexey.diseasediagnosis.Activity.TestActivity;
import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.Model.User;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;


    public BaseInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BaseInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseInfoFragment newInstance() {
        BaseInfoFragment fragment = new BaseInfoFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base_info, container, false);

        final RadioButton maleRadioButton = (RadioButton) v.findViewById(R.id.maleRadioButton);
        final RadioButton femaleRadioButton = (RadioButton) v.findViewById(R.id.femaleRadioButton);
        final EditText ageEditText = (EditText) v.findViewById(R.id.ageEditText);
        final Button nextButton = (Button) v.findViewById(R.id.nextButton);

        if ( Authentication.getInstance( getActivity() ).isLoggedIn() ) {
            User curUser = Authentication.getInstance(getActivity()).getCurrentUser();

            if (curUser.getSex() != null) {
                if (curUser.getSex().equals("male")) {
                    maleRadioButton.setChecked(true);
                    femaleRadioButton.setChecked(false);
                } else if (curUser.getSex().equals("female")) {
                    maleRadioButton.setChecked(false);
                    femaleRadioButton.setChecked(true);
                }
            }

            if ( curUser.getAge() >= 0 && curUser.getAge() <= 150) {
                ageEditText.setText("" + curUser.getAge());
            }
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex;

                if ( maleRadioButton.isChecked() )
                    sex = "male";
                else if ( femaleRadioButton.isChecked() )
                    sex = "female";
                else
                    sex = null;

                if ( sex != null && !ageEditText.getText().toString().isEmpty() ) {
                    nextButton.setEnabled(false);

                    int age = Integer.parseInt( ageEditText.getText().toString() );
                    JSONObject jo = new JSONObject();
                    try {
                        jo.put("sex", sex);
                        jo.put("age", age);
                        jo.put("evidence", new JSONArray());
                        ((TestActivity) getActivity()).generateQuestion(jo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    ((TestActivity) getActivity()).showError("Incorrect input");
            }
        });

        return v;
    }
}
