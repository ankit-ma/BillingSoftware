package Model;

import java.util.Objects;

public class User {
    private int userid;
    private String username;
    private String password;
    private String usertype;



    public User(int userid, String username, String password, String usertype) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String userType) {
        this.usertype = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", usertype='" + usertype + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userid == user.userid && username.equals(user.username) && password.equals(user.password) && usertype.equals(user.usertype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, username, password, usertype);
    }
}
