package edu.a1.book;
public interface BookBuilder{ 
    public void setBookName(String bookName);
    public void setAuthorName(String authorName);
    public void setISBN(String ISBN);
    public void setQuantity(int quantity);
    public void setCategory(String category);
    public void setPublishedYear(String publishedYear);
    public void setRetailPrice(double retailPrice);

    public Book build();
}
