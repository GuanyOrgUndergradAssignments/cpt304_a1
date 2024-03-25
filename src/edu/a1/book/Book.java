package edu.a1.book;

import javax.persistence.*;
// import package
@Entity
@Table(name = "books")
public class Book{
    // Attributes
    // ACTUAL DATABASE MIGHT BE DIFFERENT

    // PRIMARY KEY
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISBN")
    private String ISBN;

    // UNIQUE
    @Column(name = "authorName", unique = true)
    private String authorName;

    @Column(name = "bookName")
    private String bookName;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "category")
    private String category;

    @Column(name = "publishedYear")
    private String publishedYear;

    @Column(name = "retailPrice")
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