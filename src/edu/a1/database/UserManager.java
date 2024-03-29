package edu.a1.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.a1.system.User;

public class UserManager {
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
        loadUsers("users.ser");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveUsers("users.ser")));
    }

    // save file
    public void saveUsers(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(users);
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load file
    public void loadUsers(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            users = (List<User>) inputStream.readObject();
            System.out.println("Users loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found.");
        }
    }


    public void add(User user) {
        if (!existUser(user.getUsername())) {
            users.add(user);
            System.out.println("User added successfully.");
        } else {
            System.out.println("User with the same username already exists. Cannot add.");
        }
    }

    public void delete(User user) {
        if (users.contains(user)) {
            users.remove(user);
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found. Cannot delete.");
        }
    }

    public void replace(User originalUser, User newUser) {
        if (existUser(newUser.getUsername())) {
            System.out.println("New user has the same username as an existing user. Cannot replace.");
        } else {
            int index = users.indexOf(originalUser);
            if (index != -1) {
                users.set(index, newUser);
                System.out.println("User replaced successfully.");
            } else {
                System.out.println("Original user not found. Cannot replace.");
            }
        }
    }

    // get all users
    public List<User> findAll() {
        return users;
    }

    // find book by username
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // check existence
    public boolean existUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }    
}
