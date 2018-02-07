package muryshkin.alexey.diseasediagnosis.Model;

import android.graphics.Paint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Alexey Muryshkin on 22.11.2016.
 */

public class TestSession extends RealmObject {

    @PrimaryKey
    private int sessionId;

    private int userId;
    @Required
    private Date timestamp;

    private String request;
    private String conditions;

    public TestSession() {  }

    public TestSession(int userId, int sessionId, Date timestamp) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.timestamp = timestamp;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public JSONObject getRequestJSON() {
        try {
            return new JSONObject( request );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setRequestJSON(JSONObject request) {
        this.request = request.toString();
    }

    public JSONArray getConditionsJSONArray() {
        try {
            return new JSONArray( conditions );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setConditionsJSONArray(JSONArray conditions) {
        this.conditions = conditions.toString();
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
