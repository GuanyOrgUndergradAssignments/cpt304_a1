package edu.a1.database;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.a1.borrow.Borrow;

public class BorrowManagement implements BorrowManager {
    private List<Borrow> records;

    public BorrowManagement() {
        records = new ArrayList<>();
        loadRecords("records.ser");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveRecords("records.ser")));
    }

    // save file
    @Override
    public void saveRecords(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(records);
            System.out.println("Records saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load file
    @Override
    public void loadRecords(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            records = (List<Borrow>) inputStream.readObject();
            System.out.println("Records loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found.");
        }
    }

    // add
    @Override
    public void save(Borrow borrow) {
        if (!existRecord(borrow.getBorrowID())) {
            records.add(borrow);
            System.out.println("Record added successfully.");
        } else {
            System.out.println("Record with the same borrow ID already exists. Cannot add.");
        }
    }

    // delete
    @Override
    public void delete(Borrow borrow) {
        if (records.contains(borrow)) {
            records.remove(borrow);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found. Cannot delete.");
        }
    }

    // replace
    @Override
    public void replace(Borrow originalBorrow, Borrow newBorrow) {
        if (existRecord(newBorrow.getBorrowID())) {
            System.out.println("New record has the same borrow ID as an existing record. Cannot replace.");
        } else {
            int index = records.indexOf(originalBorrow);
            if (index != -1) {
                records.set(index, newBorrow);
                System.out.println("Record replaced successfully.");
            } else {
                System.out.println("Original record not found. Cannot replace.");
            }
        }
    }

    // get all borrow record
    @Override
    public List<Borrow> findAll() {
        return records;
    }

    // find by borrowID
    @Override
    public Borrow findByBorrowID(int borrowID) {
        for (Borrow record : records) {
            if (record.getBorrowID() == borrowID) {
                return record;
            }
        }
        return null;
    }

    // find by username
    @Override
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
    @Override
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
    @Override 
    public List<Borrow> findByBorrowedDate(LocalDateTime borrowedDate) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getBorrowedDate().equals(borrowedDate)) {
                result.add(record);
            }
        }
        return result;
    }

    // find by returnedDate
    @Override
    public List<Borrow> findByReturnedDate(LocalDateTime returnedDate) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getReturnedDate() != null && record.getReturnedDate().equals(returnedDate)) {
                result.add(record);
            }
        }
        return result;
    }

    // find by declaredReturnDate
    @Override
    public List<Borrow> findByDeclaredReturnDate(LocalDateTime declaredReturnDate) {
        List<Borrow> result = new ArrayList<>();
        for (Borrow record : records) {
            if (record.getDeclaredReturnDate() != null && record.getReturnedDate().equals(declaredReturnDate)) {
                result.add(record);
            }
        }
        return result;
    }

    @Override
    public Borrow findByStartTimeAndUsername(String username, LocalDateTime start){
        for (Borrow borrow : records) {
            if (borrow.getUsername().equals(username) && borrow.getBorrowedDate().equals(start)) {
                return borrow;
            }
        }
        return null;
    }

    @Override
    public Borrow findByEndTimeAndUsername(String username, LocalDateTime end){
        for (Borrow borrow : records) {
            if (borrow.getUsername().equals(username) && borrow.getReturnedDate().equals(end)) {
                return borrow;
            }
        }
        return null;
    }

    // check existence
    @Override
    public boolean existRecord(int borrowID) {
        for (Borrow record : records) {
            if (record.getBorrowID() == borrowID) {
                return true;
            }
        }
        return false;
    }
    
}