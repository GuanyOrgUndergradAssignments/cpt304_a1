package edu.a1.database;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import edu.a1.borrow.Borrow;

public class BorrowManager {
    private List<Borrow> records;

    public BorrowManager() {
        records = new ArrayList<>();
        loadRecords("records.ser");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveRecords("records.ser")));
    }

    // save file
    private void saveRecords(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(records);
            System.out.println("Records saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load file
    private void loadRecords(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            records = (List<Borrow>) inputStream.readObject();
            System.out.println("Records loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found.");
        }
    }

    // add
    public void add(Borrow borrow) {
        records.add(borrow);
    }

    // delete
    public void delete(Borrow borrow) {
        records.remove(borrow);
    }

    // replace
    public void replace(Borrow originalBorrow, Borrow newBorrow) {
        int index = records.indexOf(originalBorrow);
        if (index != -1) {
            records.set(index, newBorrow);
            System.out.println("Borrow replaced successfully.");
        } else {
            System.out.println("Original borrow not found. Cannot replace.");
        }
    }

    // get all borrow record
    public List<Borrow> findAll() {
        return records;
    }

    // find by borrowID
    public Borrow findByBorrowID(int borrowID) {
        for (Borrow record : records) {
            if (record.getBorrowID() == borrowID) {
                return record;
            }
        }
        return null;
    }

    // find by username
    public List<Borrow> findByUsername(String username) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getUsername().equals(username)) {
                result.add(record);
            }
        }
        return result;
    }

    // find by ISBN
    public List<Borrow> findByISBN(String isbn) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getISBN().equals(isbn)) {
                result.add(record);
            }
        }
        return result;
    }

    // find by borrowedDate 
    public List<Borrow> findByBorrowedDate(Date borrowedDate) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getBorrowedDate().equals(borrowedDate)) {
                result.add(record);
            }
        }
        return result;
    }

    // find by returnedDate
    public List<Borrow> findByReturnedDate(Date returnedDate) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getReturnedDate() != null && record.getReturnedDate().equals(returnedDate)) {
                result.add(record);
            }
        }
        return result;
    }

    // find by declaredReturnDate
    public List<Borrow> findByDeclaredReturnDate(Date declaredReturnDate) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getDeclaredReturnDate() != null && record.getReturnedDate().equals(declaredReturnDate)) {
                result.add(record);
            }
        }
        return result;
    }

    // check existence
    public boolean existRecord(int borrowID) {
        for (Borrow record : records) {
            if (record.getBorrowID() == borrowID) {
                return true;
            }
        }
        return false;
    }
    
}