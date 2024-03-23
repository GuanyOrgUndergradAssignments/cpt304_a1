package edu.a1.database;

import edu.a1.system.User;

public interface UserManager {
    void save(User user);
    void delete(User user);
    void replace(User user);
    User findByUsername(String username);
    boolean existUser(String username);
}