package edu.a1.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.a1.book.Book;
import edu.a1.system.IOInteraction;
import edu.a1.system.LibrarySystem;

public class BookManagement implements BookManager {
    private List<Book> books;

    public BookManagement() {
        books = new ArrayList<>();
        loadBooks("books.ser");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveBooks("books.ser")));
    }

    // save file
    @Override
    public void saveBooks(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(books);
            LibrarySystem.getIO().writeTo("Books saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load file
    @Override
    public void loadBooks(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            books = (List<Book>) inputStream.readObject();
            LibrarySystem.getIO().writeTo("Books loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            LibrarySystem.getIO().writeTo("No previous data found.");
        }
    }

    // add book
    @Override
    public void save(Book book) {
        if (!existBook(book.getISBN())) {
            books.add(book);
            LibrarySystem.getIO().writeTo("Book added successfully.");
        } else {
            LibrarySystem.getIO().writeTo("Book with the same ISBN already exists. Cannot add.");
        }
    }

    // delete
    @Override
    public void delete(Book book) {
        if (books.contains(book)) {
            books.remove(book);
            LibrarySystem.getIO().writeTo("Book deleted successfully.");
        } else {
            LibrarySystem.getIO().writeTo("Book not found. Cannot delete.");
        }
    }
    

    // replace
    @Override
    public void replace(Book originalBook, Book newBook) {
        if (existBook(newBook.getISBN())) {
            LibrarySystem.getIO().writeTo("New book has the same ISBN as an existing book. Cannot replace.");
        } else {
            int index = books.indexOf(originalBook);
            if (index != -1) {
                books.set(index, newBook);
                LibrarySystem.getIO().writeTo("Book replaced successfully.");
            } else {
                LibrarySystem.getIO().writeTo("Original book not found. Cannot replace.");
            }
        }
    }

    // get all books
    @Override
    public List<Book> findAll() {
        return books;
    }

    // find book by ISBN
    @Override
    public Book findByISBN(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    // find book by bookName
    @Override
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
    @Override
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
    @Override
    public Book findByBookNameAndAuthor(String bookName, String authorName) {
        for (Book book : books) {
            if (book.getBookName().equals(bookName) && book.getAuthorName().equals(authorName)) {
                return book;
            }
        }
        return null;
    }

    // find book by category
    @Override
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
    @Override
    public boolean existBook(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) {
                return true;
            }
        }
        return false;
    }
}
    


