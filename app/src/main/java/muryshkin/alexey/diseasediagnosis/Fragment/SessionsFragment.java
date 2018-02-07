package muryshkin.alexey.diseasediagnosis.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import muryshkin.alexey.diseasediagnosis.Activity.TestResultActivity;
import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.Data.DataHolder;
import muryshkin.alexey.diseasediagnosis.Factory.TestSessionFactory;
import muryshkin.alexey.diseasediagnosis.Model.TestSession;
import muryshkin.alexey.diseasediagnosis.Model.User;
import muryshkin.alexey.diseasediagnosis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SessionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;


    public SessionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SessionsFragment newInstance() {
        SessionsFragment fragment = new SessionsFragment();
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
        View v = inflater.inflate(R.layout.fragment_sessions, container, false);

        ListView sessionsListView = (ListView) v.findViewById(R.id.sessionsListView);

        User curUser = Authentication.getInstance( getContext() ).getCurrentUser();
        TestSessionFactory testSessionFactory = new TestSessionFactory( getContext() );
        final List<TestSession> sessions = testSessionFactory.getAllUserSessions( curUser.getUserId() );
        List<String> dates = new ArrayList<>();
        for (int i = sessions.size() - 1; i >= 0; i--) {
            TestSession session = sessions.get(i);
            dates.add( session.getTimestamp().toString() );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dates);
        sessionsListView.setAdapter(adapter);

        sessionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSessionClick( sessions.get(position) );
            }
        });

        return v;
    }

    private void onSessionClick(TestSession session) {
        DataHolder.getInstance().setConditions( session.getConditionsJSONArray() );
        Intent intent = new Intent(getContext(), TestResultActivity.class);
        startActivity(intent);
    }

}
