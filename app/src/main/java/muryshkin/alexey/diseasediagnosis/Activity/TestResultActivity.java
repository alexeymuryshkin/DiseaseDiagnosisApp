package muryshkin.alexey.diseasediagnosis.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import muryshkin.alexey.diseasediagnosis.Adapter.ConditionsAdapter;
import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.Data.DataHolder;
import muryshkin.alexey.diseasediagnosis.Factory.TestSessionFactory;
import muryshkin.alexey.diseasediagnosis.Model.TestSession;
import muryshkin.alexey.diseasediagnosis.Model.User;
import muryshkin.alexey.diseasediagnosis.R;

public class TestResultActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PLAY = 1;
    private static final String TAG = "TestResultActivity";

    private ImageButton backImageButton;
    private ImageButton homeImageButton;

    private ListView conditionsListView;
    private Button saveButton;

    private Boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customized_test_result_action_bar);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homeImageButton = (ImageButton) findViewById(R.id.homeImageButton);
        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeClick();
            }
        });

        if ( DataHolder.getInstance().getConditions() != null ) {
            conditionsListView = (ListView) findViewById(R.id.conditionsListView);
            ConditionsAdapter adapter = new ConditionsAdapter(this, DataHolder.getInstance().getConditions());
            conditionsListView.setAdapter(adapter);
            Log.d(TAG, DataHolder.getInstance().getConditions().toString() );

            final JSONArray conditions = DataHolder.getInstance().getConditions();
            conditionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        showDescription(conditions.getJSONObject(position));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClick();
            }
        });

        isSaved = getIntent().getBooleanExtra("isSaved", false);
        if ( isSaved ) {
            saveButton.setEnabled(false);
        }
    }

    private void showDescription(JSONObject jo) {
        try {
            Intent intent = new Intent(this, DiseaseDescriptionActivity.class);
            intent.putExtra("name", jo.getString("name"));
            intent.putExtra("id", jo.getString("id"));
            intent.putExtra("probability", jo.getString("probability"));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onSaveClick() {
        if ( !Authentication.getInstance(this).isLoggedIn() ) {
            Intent intent = new Intent(this, AuthenticationActivity.class);
            intent.putExtra("finish", true);
            startActivityForResult(intent, REQUEST_CODE_PLAY);
        } else
            saveSession();
    }

    private void saveSession() {
        if ( Authentication.getInstance(this).isLoggedIn()) {
            User user = Authentication.getInstance(this).getCurrentUser();
            JSONObject request = DataHolder.getInstance().getRequest();
            JSONArray conditions = DataHolder.getInstance().getConditions();
            TestSessionFactory testSessionFactory = new TestSessionFactory(this);
            testSessionFactory.createTestSession(user.getUserId(), Calendar.getInstance().getTime(), request, conditions);
            Toast.makeText(this, "Session has been created", Toast.LENGTH_SHORT).show();
            saveButton.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLAY) {

            if (resultCode == RESULT_OK) {
                saveSession();
            }
        }
    }

    private void onHomeClick() {
        DataHolder.getInstance().clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
