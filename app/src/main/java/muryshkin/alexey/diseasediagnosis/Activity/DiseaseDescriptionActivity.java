package muryshkin.alexey.diseasediagnosis.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
import muryshkin.alexey.diseasediagnosis.R;

public class DiseaseDescriptionActivity extends AppCompatActivity {

    private static final String TAG = "DiseaseDescription";
    private ImageButton backImageButton;
    private TextView nameTextView;
    private TextView probabilityTextView;
    private TextView categoriesTextView;
    private TextView prevalenceTextView;
    private TextView acutenessTextView;
    private TextView severityTextView;
    private TextView sexFilterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_description);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customized_action_bar);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        probabilityTextView = (TextView) findViewById(R.id.probabilityTextView);
        categoriesTextView = (TextView) findViewById(R.id.categoriesTextView);
        prevalenceTextView = (TextView) findViewById(R.id.prevalenceTextView);
        acutenessTextView = (TextView) findViewById(R.id.acutenessTextView);
        severityTextView = (TextView) findViewById(R.id.severityTextView);
        sexFilterTextView = (TextView) findViewById(R.id.sexFilterTextView);

        backImageButton = (ImageButton) findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String id = getIntent().getStringExtra("id");

        if ( id != null || !id.isEmpty() ) {
            setData();
        }
    }

    private void setData() {

        nameTextView.setText( "NAME: " + getIntent().getStringExtra("name") );
        probabilityTextView.setText( "PROBABILITY: " + getIntent().getStringExtra("probability") );

        String id = getIntent().getStringExtra("id");

        String url = getResources().getString(R.string.condition_id_url) + id;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                displayInfo(response);
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

    private void displayInfo(JSONObject jo) {
        Log.d(TAG, jo.toString());
        try {
            nameTextView.setText( "NAME: " + jo.getString("name") );
            prevalenceTextView.setText( "PREVALENCE: " + jo.getString("prevalence"));
            acutenessTextView.setText( "ACUTENESS: " + jo.getString("acuteness") );
            severityTextView.setText( "SEVERITY: " + jo.getString("severity"));
            sexFilterTextView.setText( "SEX FILTER: " + jo.getString("sex_filter") );

            String categories = "CATEGORIES: ";

            for ( int i = 0; i < jo.getJSONArray("categories").length(); i++) {
                categories = categories + jo.getJSONArray("categories").getString(i);

                if (i < jo.getJSONArray("categories").length() - 1)
                    categories = categories + ", ";
            }

            categoriesTextView.setText(categories);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
