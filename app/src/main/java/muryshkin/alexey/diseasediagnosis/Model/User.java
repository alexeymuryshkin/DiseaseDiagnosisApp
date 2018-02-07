package muryshkin.alexey.diseasediagnosis.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Alexey Muryshkin on 22.11.2016.
 */

public class User extends RealmObject {

    @PrimaryKey
    private int userId;

    @Required
    private String nickname;
    @Required
    private String password;

    private int age;
    private String sex;

    public User() { }

    public User(String nickname, String password, int userId) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        age = -1;
        sex = null;
    }

    public String getNickname() {
        return nickname;
    }

    public void setPassword(String password) { this.password = password; }

    public boolean passwordCompare(String cpassword) {
        return password.equals( cpassword );
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUserId() { return userId; }
}
