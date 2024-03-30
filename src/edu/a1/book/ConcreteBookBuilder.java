package edu.a1.book;
public class ConcreteBookBuilder implements BookBuilder {
    private Book book;

    public ConcreteBookBuilder() {
        this.book = new Book(null, null, 0, null, null, null, 0);
    }

   @Override
    public BookBuilder setBookName(String bookName) {
        book.setBookName(bookName);
        return this;
    }

    @Override
    public BookBuilder setAuthorName(String authorName) {
        book.setAuthorName(authorName);
        return this;
    }

    @Override
    public BookBuilder setISBN(String ISBN) {
        book.setISBN(ISBN);
        return this;
    }

    @Override
    public BookBuilder setQuantity(int quantity) {
        book.setQuantity(quantity);
        return this;
    }

    @Override
    public BookBuilder setCategory(String category) {
        book.setCategory(category);
        return this;
    }

    @Override
    public BookBuilder setPublishedYear(String publishedYear) {
        book.setPublishedYear(publishedYear);
        return this;
    }

    @Override
    public BookBuilder setRetailPrice(double retailPrice) {
        book.setRetailPrice(retailPrice);
        return this;
    }

    @Override
    public Book build() {
        return book;
    }

}