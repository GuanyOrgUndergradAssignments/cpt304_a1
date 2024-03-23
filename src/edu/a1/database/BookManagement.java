package edu.a1.database;

import java.util.List;

import edu.a1.book.Book;

public class BookManagement implements BookManager {

    private DatabaseConnection databaseConnection;

    public BookManagement() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    // Save a book object in database
    @Override
    public void save(Book book) {
        
    }

    // Delete a book object from database
    @Override
    public void delete(Book book) {
        
    }

    // Replace an existed book in database
    @Override
    public void replace(Book book){

    }

    // Finds all book.
    @Override
    public List<Book> findAll(){

    }

    /**
     * Finds book by isbn.
     * 
     * @param isbn the isbn provided.
     * @return the specific book.
     */
    @Override
    public Book findByISBN(String isbn){

    }


    /**
     * Finds book by name.
     * 
     * @param bookName the name provided.
     * @return a list of book with the specific name.
     */
    @Override
    public List<Book> findByBookName(String bookName){

    }

    /**
     * Finds book by author.
     * 
     * @param auhtor the author provided.
     * @return a list of book with the specific author.
     */
    @Override
    public List<Book> findByBookAuthor(String author){

    }

    /**
     * Finds book by name and author.
     * 
     * @param bookName the name provided.
     * @param author the author provided.
     * @return the specific book.
     */
    @Override
    public Book findByBookNameAndAuthor(String bookName, String author){

    }

    /**
     * Finds book by category.
     * 
     * @param category the category provided.
     * @return a list of book with the specific category.
     */
    @Override
    public List<Book> findByBookCategory(String category){

    }

    /**
     * Check if book is existed.
     * 
     * @param isbn the username provided.
     * @return boolean 
     */
    @Override
    public boolean existBook(String isbn){
        return true;
    }
}
