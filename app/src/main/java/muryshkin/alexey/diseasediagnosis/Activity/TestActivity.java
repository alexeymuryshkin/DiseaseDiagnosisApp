package muryshkin.alexey.diseasediagnosis.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import muryshkin.alexey.diseasediagnosis.Data.Const;
import muryshkin.alexey.diseasediagnosis.Data.DataHolder;
import muryshkin.alexey.diseasediagnosis.Fragment.BaseInfoFragment;
import muryshkin.alexey.diseasediagnosis.Fragment.GroupMultipleQuestionFragment;
import muryshkin.alexey.diseasediagnosis.Fragment.GroupSingleQuestionFragment;
import muryshkin.alexey.diseasediagnosis.Fragment.SingleQuestionFragment;
import muryshkin.alexey.diseasediagnosis.R;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    private FrameLayout frameLayout;
    private android.app.FragmentManager mgr;
    private DataHolder dataHolder = DataHolder.getInstance();

    private ImageButton backImageButton;
    private ImageButton completeTestImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customized_test_action_bar);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHolder.getInstance().clear();
                finish();
            }
        });
        
        completeTestImageButton = (ImageButton) findViewById(R.id.completeTestImageButton);
        completeTestImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onComplete();
            }
        });

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        mgr = getFragmentManager();

        if ( DataHolder.getInstance().getRequest() == null ) {
            BaseInfoFragment baseInfoFragment = BaseInfoFragment.newInstance();

            FragmentTransaction trans = mgr.beginTransaction();
            trans.replace(R.id.frameLayout, baseInfoFragment);
            trans.commit();
            frameLayout.invalidate();
        } else
            generateQuestion( DataHolder.getInstance().getRequest() );
    }

    private void onComplete() {
        Intent intent = new Intent(this, TestResultActivity.class);
        intent.putExtra("isSaved", false);
        startActivity(intent);
    }

    public void generateQuestion(final JSONObject jo) {

        dataHolder.setRequest(jo);

        String url = getResources().getString(R.string.diagnosis_url);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                displayQuestion(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error occured: " + error.getMessage());
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("app_id", Const.APP_ID);
                headers.put("app_key", Const.APP_KEY);
                return headers;
            }
        };

        queue.add(request);
    }

    private void displayQuestion(JSONObject response) {
        JSONObject jo = dataHolder.getRequest();
        Log.d(TAG, jo.toString());
        Log.d(TAG, response.toString());

        if ( response.isNull("question") ) {
            onComplete();
            DataHolder.getInstance().clear();
            finish();
        } else {
            try {
                JSONObject question = response.getJSONObject("question");
                Log.d(TAG, question.toString());

                JSONArray conditions = response.getJSONArray("conditions");
                Log.d(TAG, conditions.toString());

                JSONArray items = question.getJSONArray("items");
                Log.d(TAG, items.toString());

                dataHolder.setConditions(conditions);
                dataHolder.setQuestion(question);
                dataHolder.setItems(items);
                String type = question.getString("type");
                Fragment fragment = null;

                if (type.equals("single"))
                    fragment = new SingleQuestionFragment().newInstance();
                else if (type.equals("group_multiple"))
                    fragment = new GroupMultipleQuestionFragment().newInstance();
                else if (type.equals("group_single"))
                    fragment = new GroupSingleQuestionFragment().newInstance();
                else {
                    onComplete();
                    DataHolder.getInstance().clear();
                    finish();
                }

                FragmentTransaction trans = mgr.beginTransaction();
                trans.replace(R.id.frameLayout, fragment);
                trans.commit();
                frameLayout.invalidate();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
