package edu.a1.database;
import java.util.List;

import edu.a1.book.Book;
public interface BookManager {
    void save(Book book);
    void delete(Book book);
    void replace(Book book);
    List<Book> findAll();
    Book findByISBN(String isbn);
    List<Book> findByBookName(String bookName);
    List<Book> findByBookAuthor(String author);
    Book findByBookNameAndAuthor(String bookName, String author);
    List<Book> findByBookCategory(String category);
    boolean existBook(String isbn);
}

