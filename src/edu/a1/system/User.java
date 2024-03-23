package edu.a1.system;

/**
 * Foundation of different kinds of users.
 * Contains all the fields that the database stores about a user.
 * Borrow history is stored instead independently in another table.
 * 
 * @author Guanyuming He, Kemu Xu
 */
public final class User {

    private String username;
    private String pwd;

    public String getUsername() {
        return username;
    }
    public String getPwd() {
        return pwd;
    }

}
