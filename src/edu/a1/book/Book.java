package edu.a1.book;

import java.io.Serializable;

// import package
public class Book implements Serializable{
    private String bookName;
    private String ISBN;
    private int quantity;
    private String category;
    private String publishedYear;
    private String authorName;
    private double retailPrice;

    // Constructor
    public Book(String bookName, String ISBN, int quantity, String category, String publishedYear, String authorName, double retailPrice) {
        this.bookName = bookName;
        this.ISBN = ISBN;
        this.quantity = quantity;
        this.category = category;
        this.publishedYear = publishedYear;
        this.authorName = authorName;
        this.retailPrice = retailPrice;
    }

    // Getters
    public String getBookName() {
        return bookName;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public String getAuthorName() {
        return authorName;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    // Setters
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    // toString
    @Override
    public String toString() {
        return "bookName= " + bookName+ '\n' +
                "ISBN= " + ISBN + '\n' +
                "quantity= " + quantity + '\n' +
                "category= " + category + '\n' +
                "publishedYear= " + publishedYear + '\n' +
                "authorName= " + authorName + '\n' +
                "retailPrice= " + retailPrice;
    }
}