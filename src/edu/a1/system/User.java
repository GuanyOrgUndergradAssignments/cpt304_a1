package edu.a1.system;

import java.io.Serializable;

/**
 * Foundation of different kinds of users.
 * Contains all the fields that the database stores about a user.
 * Borrow history is stored instead independently in another table.
 * 
 * @author Guanyuming He, Kemu Xu
 */
public final class User implements Serializable{

    private String username;
    private String pwd;

    /**
     * For SQL
     */
    public User() {}

    /**
     * For us
     */
    public User(String name, String pwd) {
        this.username = name;
        this.pwd = pwd;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getUsername() {
        return username;
    }
    public String getPwd() {
        return pwd;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof User)) {
            return false;
        }

        var o = (User)other;

        if(o.username.equals(this.username)) {
            assert(o.pwd.equals(this.pwd));
            return true;
        }
        return false;
    }

}
