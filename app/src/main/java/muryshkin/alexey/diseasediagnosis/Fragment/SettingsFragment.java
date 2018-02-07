package muryshkin.alexey.diseasediagnosis.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.Factory.UserFactory;
import muryshkin.alexey.diseasediagnosis.Model.User;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;


    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        final EditText oldPassword = (EditText) v.findViewById(R.id.oldPasswordEditText);
        final EditText newPassword = (EditText) v.findViewById(R.id.newPasswordEditText);
        final EditText repeatNewPasswrd = (EditText) v.findViewById(R.id.repeatNewPasswordEditText);
        final User curUser = Authentication.getInstance( getContext() ).getCurrentUser();

        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !curUser.passwordCompare( oldPassword.getText().toString() ) ) {
                    Toast.makeText( getContext(), "Old password is incorrect!", Toast.LENGTH_SHORT).show();
                } else if ( !newPassword.getText().toString().equals( repeatNewPasswrd.getText().toString() ) ) {
                    Toast.makeText( getContext(), "New passwords are different!", Toast.LENGTH_SHORT).show();
                } else {
                    UserFactory userFactory = new UserFactory( getContext() );
                    userFactory.updatePassword( curUser.getUserId(), newPassword.getText().toString() );
                    Toast.makeText( getContext(), "New Password Is Successfully Saved", Toast.LENGTH_SHORT).show();
                }

                oldPassword.setText("");
                newPassword.setText("");
                repeatNewPasswrd.setText("");
            }
        });

        return v;
    }

}
