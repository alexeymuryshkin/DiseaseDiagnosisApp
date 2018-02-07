package muryshkin.alexey.diseasediagnosis.Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey Muryshkin on 22.11.2016.
 */

public class DataHolder {

    private static DataHolder instance = null;

    public static DataHolder getInstance() {
        if (instance == null)
            instance = new DataHolder();
        return instance;
    }

    private JSONObject question;
    private JSONObject request;
    private List<Integer> answerSet;
    private JSONArray conditions;
    private JSONArray items;

    private DataHolder() {
        question = null;
        request = null;
        answerSet = null;
        conditions = null;
        items = null;
    }

    public JSONObject getQuestion() {
        return question;
    }

    public void setQuestion(JSONObject question) {
        this.question = question;
    }

    public JSONObject getRequest() {
        return request;
    }

    public void setRequest(JSONObject requestJSONObject) {
        this.request = requestJSONObject;
    }

    public List<Integer> getAnswerSet() {
        return answerSet;
    }

    public void setAnswerSet(int size, int defaultValue) {
        answerSet = new ArrayList<>();

        for (int i = 0; i < size; i++)
            answerSet.add(defaultValue);
    }

    public JSONArray getConditions() {
        return conditions;
    }

    public void setConditions(JSONArray conditions) {
        this.conditions = conditions;
    }

    public JSONArray getItems() {
        return items;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }

    public void clear() {
        question = null;
        request = null;
        answerSet = null;
        conditions = null;
        items = null;
    }
}
