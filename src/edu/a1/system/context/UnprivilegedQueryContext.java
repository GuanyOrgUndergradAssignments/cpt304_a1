package edu.a1.system.context;

import java.util.List;

import edu.a1.book.Book;

/**
 * Defines all queries that anyone who uses this library can perform.
 * Because anyone can perform such queries, they are all static.
 */
public final class UnprivilegedQueryContext {
    
    /**
     * Anyone who uses the library can query about a book
     * @param isbn, the unique identifier of a book
     * @return all information about the book, stored in the Book class.
     * Or null if no book of isbn is found.
     */
    public Book queryBook(String isbn) {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Anyone who uses the library can query about a book
     * @param author (author, bookName) is unique
     * @param bookName (author, bookName) is unique
     * @return all information about the book, stored in the Book class.
     * Or null if no book of (author, bookName) is found.
     */
    public Book queryBook(String author, String bookName) {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Anyone who uses the library can query about books
     * @param category
     * @return all books that have the category, stored in the list.
     * Or an empty list if none is found.
     */
    public List<Book> queryBooks(String category) {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Anyone who uses the library can query about all categories of a book
     * @return all catogires, stored in the list.
     */
    public List<String> queryBookCategories() {
        throw new RuntimeException("Not implemented.");
    }
}
