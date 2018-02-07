package muryshkin.alexey.diseasediagnosis.Factory;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import muryshkin.alexey.diseasediagnosis.Data.Authentication;
import muryshkin.alexey.diseasediagnosis.Model.TestSession;
import muryshkin.alexey.diseasediagnosis.Model.User;

/**
 * Created by Alexey Muryshkin on 24.11.2016.
 */

public class TestSessionFactory {

    private Context context;
    private Realm realm;

    public TestSessionFactory(Context context) {
        this.context = context;
        // Initialize Realm
        Realm.init(context);

        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
    }

    public boolean createTestSession(int userId, Date timestamp, JSONObject request, JSONArray conditions) {
        Number maxValue = realm.where(TestSession.class).max("sessionId");
        int id = 0;
        if ( maxValue != null)
            id = maxValue.intValue() + 1;
        final User curUser = Authentication.getInstance(context).getCurrentUser();
        final TestSession testSession = new TestSession(curUser.getUserId(), id, timestamp);
        testSession.setRequestJSON( request );
        testSession.setConditionsJSONArray( conditions );

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(testSession);
            }
        });

        return true;
    }

    public TestSession getTestSession(int sessionId) {
        return realm.where(TestSession.class).equalTo("sessionId", sessionId).findFirst();
    }

    public List<TestSession> getAllUserSessions(int userId) {
        return realm.where(TestSession.class).equalTo("userId", userId).findAll();
    }

    public boolean deleteTestSession(int sessionId) {
        final RealmResults<TestSession> results = realm.where(TestSession.class).equalTo("sessionId", sessionId).findAll();

        // All changes to data must happen in a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // remove single match
                results.deleteFirstFromRealm();
            }
        });

        return true;
    }
}
