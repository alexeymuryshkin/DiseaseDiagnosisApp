package muryshkin.alexey.diseasediagnosis.Factory;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import muryshkin.alexey.diseasediagnosis.Model.User;

/**
 * Created by Alexey Muryshkin on 24.11.2016.
 */

public class UserFactory {

    private Context context;
    private Realm realm;

    public UserFactory(Context context) {
        this.context = context;
        // Initialize Realm
        Realm.init(context);

        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
    }

    public boolean createUser(String username, String password) {
        Number maxValue = realm.where(User.class).max("userId");
        int id = 0;
        if (maxValue != null)
            id = maxValue.intValue() + 1;
        final User user = new User(username, password, id);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
        return true;
    }

    public boolean deleteUser(String username) {
        final RealmResults<User> results = realm.where(User.class).equalTo("nickname", username).findAll();

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

    public User getUser(String username) {
        return realm.where(User.class).equalTo("nickname", username).findFirst();
    }

    public boolean updateUser(final User user) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
        return true;
    }

    public boolean updateAge(final int id, final int age) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.where(User.class).equalTo("userId", id).findFirst();
                user.setAge(age);
            }
        });
        return true;
    }

    public boolean updateSex(final int id, final String sex) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.where(User.class).equalTo("userId", id).findFirst();
                user.setSex(sex);
            }
        });
        return true;
    }

    public boolean updatePassword(final int id, final String password) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.where(User.class).equalTo("userId", id).findFirst();
                user.setPassword(password);
            }
        });
        return true;
    }
}
