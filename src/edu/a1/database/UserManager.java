package edu.a1.database;

import java.util.List;

import edu.a1.system.User;

public interface UserManager {
    void saveUsers(String fileName);
    void loadUsers(String fileName);
    void save(User user);
    void delete(User user);
    void replace(User originalUser, User newUser);
    List<User> findAll();
    User findByUsername(String username);
    boolean existUser(String username);
}