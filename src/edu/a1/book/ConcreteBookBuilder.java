package edu.a1.book;
public class ConcreteBookBuilder implements BookBuilder {
    private Book book;

    public ConcreteBookBuilder() {
        this.book = new Book(null, null, 0, null, null, null, 0);
    }

   @Override
    public BookBuilder setBookName(String bookName) {
        book.setBookName(bookName);
    }

    @Override
    public BookBuilder setAuthorName(String authorName) {
        book.setAuthorName(authorName);
    }

    @Override
    public BookBuilder setISBN(String ISBN) {
        book.setISBN(ISBN);
    }

    @Override
    public BookBuilder setQuantity(int quantity) {
        book.setQuantity(quantity);
    }

    @Override
    public BookBuilder setCategory(String category) {
        book.setCategory(category);
    }

    @Override
    public BookBuilder setPublishedYear(String publishedYear) {
        book.setPublishedYear(publishedYear);
    }

    @Override
    public BookBuilder setRetailPrice(double retailPrice) {
        book.setRetailPrice(retailPrice);
    }

    @Override
    public Book build() {
        return book;
    }

}