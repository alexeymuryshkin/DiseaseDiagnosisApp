package muryshkin.alexey.diseasediagnosis.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.Factory.UserFactory;
import muryshkin.alexey.diseasediagnosis.Model.User;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        final User curUser = Authentication.getInstance(getContext()).getCurrentUser();

        TextView usernameTextView = (TextView) v.findViewById(R.id.usernameTextView);
        usernameTextView.setText( curUser.getNickname() );

        TextView logoutTextView = (TextView) v.findViewById(R.id.logoutTextView);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogOutClick();
            }
        });

        final RadioButton maleRadioButton = (RadioButton) v.findViewById(R.id.maleRadioButton);
        final RadioButton femaleRadioButton = (RadioButton) v.findViewById(R.id.femaleRadioButton);
        final EditText ageEditText = (EditText) v.findViewById(R.id.ageEditText);

        if (curUser.getSex() != null) {
            if ( curUser.getSex().equals("male") ) {
                maleRadioButton.setChecked(true);
                femaleRadioButton.setChecked(false);
            } else if ( curUser.getSex().equals("female") ) {
                maleRadioButton.setChecked(false);
                femaleRadioButton.setChecked(true);
            }
        }

        if ( curUser.getAge() >= 0 && curUser.getAge() <= 150) {
            Log.d("ProfileFragment", "" + curUser.getAge());
            ageEditText.setText("" + curUser.getAge());
        }

        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex;

                if ( maleRadioButton.isChecked() )
                    sex = "male";
                else if ( femaleRadioButton.isChecked() )
                    sex = "female";
                else
                    sex = null;

                int age = -1;
                if ( !ageEditText.getText().toString().isEmpty() )
                    age = Integer.parseInt( ageEditText.getText().toString() );

                UserFactory userFactory = new UserFactory(getContext());
                userFactory.updateAge(curUser.getUserId(), age);
                userFactory.updateSex(curUser.getUserId(), sex);
            }
        });

        return v;
    }

    private void onLogOutClick() {
        Authentication.getInstance( getContext() ).logOut();
        getActivity().finish();
    }

}
