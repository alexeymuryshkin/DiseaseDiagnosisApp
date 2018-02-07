package muryshkin.alexey.diseasediagnosis.Data;

import android.content.Context;

import muryshkin.alexey.diseasediagnosis.Factory.UserFactory;
import muryshkin.alexey.diseasediagnosis.Model.User;

/**
 * Created by Alexey Muryshkin on 22.11.2016.
 */

public class Authentication {

    private static Authentication instance = null;

    public static Authentication getInstance(Context context) {
        if (instance == null)
            instance = new Authentication(context);
        return instance;
    }

    private User curUser;
    private UserFactory userFactory;

    private Authentication(Context context) {
        curUser = null;
        userFactory = new UserFactory(context);
    }

    public boolean logIn(String nickname, String password) {
        User user = userFactory.getUser(nickname);
        if ( user == null )
            return false;
        else if ( !user.passwordCompare(password))
            return false;

        curUser = user;
        return true;
    }

    public boolean register(String nickname, String password) {
        userFactory.createUser(nickname, password);
        curUser = userFactory.getUser(nickname);
        return true;
    }

    public boolean logOut() {
        curUser = null;
        return true;
    }

    public User getCurrentUser() {
        return curUser;
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }
}
