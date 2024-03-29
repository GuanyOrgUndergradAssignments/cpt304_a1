package edu.a1.book;
public interface BookBuilder{ 
    public BookBuilder setBookName(String bookName);
    public BookBuilder setAuthorName(String authorName);
    public BookBuilder setISBN(String ISBN);
    public BookBuilder setQuantity(int quantity);
    public BookBuilder setCategory(String category);
    public BookBuilder setPublishedYear(String publishedYear);
    public BookBuilder setRetailPrice(double retailPrice);

    public Book build();
}
