package edu.a1.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.a1.system.IOInteraction;
import edu.a1.system.User;

public class UserManagement implements UserManager{
    private List<User> users;
    private static  IOInteraction ioInteraction;

    public UserManagement(IOInteraction ioInteraction) {
        this.ioInteraction = ioInteraction;
        users = new ArrayList<>();
        loadUsers("users.ser");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveUsers("users.ser")));
    }

    // save file
    @Override
    public void saveUsers(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(users);
            ioInteraction.writeTo("Users saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load file
    @Override
    public void loadUsers(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            users = (List<User>) inputStream.readObject();
            ioInteraction.writeTo("Users loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            ioInteraction.writeTo("No previous data found.");
        }
    }


    @Override
    public void save(User user) {
        if (!existUser(user.getUsername())) {
            users.add(user);
            ioInteraction.writeTo("User added successfully.");
        } else {
            ioInteraction.writeTo("User with the same username already exists. Cannot add.");
        }
    }

    @Override
    public void delete(User user) {
        if (users.contains(user)) {
            users.remove(user);
            ioInteraction.writeTo("User deleted successfully.");
        } else {
            ioInteraction.writeTo("User not found. Cannot delete.");
        }
    }

    @Override
    public void replace(User originalUser, User newUser) {
        if (existUser(newUser.getUsername())) {
            ioInteraction.writeTo("New user has the same username as an existing user. Cannot replace.");
        } else {
            int index = users.indexOf(originalUser);
            if (index != -1) {
                users.set(index, newUser);
                ioInteraction.writeTo("User replaced successfully.");
            } else {
                ioInteraction.writeTo("Original user not found. Cannot replace.");
            }
        }
    }

    // get all users
    @Override
    public List<User> findAll() {
        return users;
    }

    // find book by username
    @Override
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // check existence
    @Override
    public boolean existUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }    
}
