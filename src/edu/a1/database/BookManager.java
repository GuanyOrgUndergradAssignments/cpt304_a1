package edu.a1.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.a1.book.Book;

public class BookManager {
    private List<Book> books;

    public BookManager() {
        books = new ArrayList<>();
        loadBooks("books.ser");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveBooks("books.ser")));
    }

    // save file
    public void saveBooks(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(books);
            System.out.println("Books saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load file
    public void loadBooks(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            books = (List<Book>) inputStream.readObject();
            System.out.println("Books loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found.");
        }
    }

    // add book
    public void add(Book book) {
        if (!existBook(book.getISBN())) {
            books.add(book);
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Book with the same ISBN already exists. Cannot add.");
        }
    }

    // delete
    public void delete(Book book) {
        if (books.contains(book)) {
            books.remove(book);
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found. Cannot delete.");
        }
    }
    

    // replace
    public void replace(Book originalBook, Book newBook) {
        if (existBook(newBook.getISBN())) {
            System.out.println("New book has the same ISBN as an existing book. Cannot replace.");
        } else {
            int index = books.indexOf(originalBook);
            if (index != -1) {
                books.set(index, newBook);
                System.out.println("Book replaced successfully.");
            } else {
                System.out.println("Original book not found. Cannot replace.");
            }
        }
    }

    // get all books
    public List<Book> findAll() {
        return books;
    }

    // find book by ISBN
    public Book findByISBN(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    // find book by bookName
    public List<Book> findByBookName(String bookName) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookName().equals(bookName)) {
                result.add(book);
            }
        }
        return result;
    }

    // find book authorName
    public List<Book> findByBookAuthor(String authorName) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthorName().equals(authorName)) {
                result.add(book);
            }
        }
        return result;
    }

    // find book by bookName and authorName
    public Book findByBookNameAndAuthor(String bookName, String authorName) {
        for (Book book : books) {
            if (book.getBookName().equals(bookName) && book.getAuthorName().equals(authorName)) {
                return book;
            }
        }
        return null;
    }

    // find book by category
    public List<Book> findByBookCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getCategory().equals(category)) {
                result.add(book);
            }
        }
        return result;
    }

    // check existence
    public boolean existBook(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) {
                return true;
            }
        }
        return false;
    }
}
    


