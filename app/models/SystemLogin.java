package models;


/**
 * Created by jeff on 2/14/14.
 */
public class SystemLogin {
    public String username;
    public String password;

    @Override
    public String toString() {
        return "{username:" + username + ", password:" + password + "}";
    }
}
