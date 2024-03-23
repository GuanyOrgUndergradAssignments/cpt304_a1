package edu.a1.book;
public class ConcreteBookBuilder implements BookBuilder {
    private Book book;

    public ConcreteBookBuilder() {
        this.book = new Book(null, null, 0, null, null, null, 0);
    }

   @Override
    public void setBookName(String bookName) {
        book.setBookName(bookName);
    }

    @Override
    public void setAuthorName(String authorName) {
        book.setAuthorName(authorName);
    }

    @Override
    public void setISBN(String ISBN) {
        book.setISBN(ISBN);
    }

    @Override
    public void setQuantity(int quantity) {
        book.setQuantity(quantity);
    }

    @Override
    public void setCategory(String category) {
        book.setCategory(category);
    }

    @Override
    public void setPublishedYear(String publishedYear) {
        book.setPublishedYear(publishedYear);
    }

    @Override
    public void setRetailPrice(double retailPrice) {
        book.setRetailPrice(retailPrice);
    }

    @Override
    public Book build() {
        return book;
    }

}