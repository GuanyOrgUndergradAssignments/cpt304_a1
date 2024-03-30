package edu.a1.database;
import java.util.List;

import edu.a1.book.Book;
public interface BookManager {
    void saveBooks(String fileName);
    void loadBooks(String fileName);
    void save(Book book);
    void delete(Book book);
    void replace(Book originalBook, Book newBook);
    List<Book> findAll();
    Book findByISBN(String isbn);
    List<Book> findByBookName(String bookName);
    List<Book> findByBookAuthor(String author);
    Book findByBookNameAndAuthor(String bookName, String author);
    List<Book> findByBookCategory(String category);
    boolean existBook(String isbn);
}

