package edu.a1.book;
public class BookDirector {
    private BookBuilder bookBuilder;

    public BookDirector(BookBuilder bookBuilder) {
        this.bookBuilder = bookBuilder;
    }

    public void addBook(String bookName, String authorName, String ISBN, int quantity, String category, String publishedYear, double retailPrice) {
        // You can define a specific sequence of steps to construct the Book object here
        bookBuilder.setBookName(bookName);
        bookBuilder.setAuthorName(authorName);
        bookBuilder.setISBN(ISBN);
        bookBuilder.setQuantity(quantity);
        bookBuilder.setCategory(category);
        bookBuilder.setPublishedYear(publishedYear);
        bookBuilder.setRetailPrice(retailPrice);
    }

    public void buildJKRowling() {
            bookBuilder.setBookName("Harry Potter and the Philosopher's Stone");
            bookBuilder.setAuthorName("J.K. Rowling");
            bookBuilder.setISBN("9780747532743");
            bookBuilder.setQuantity(1);
            bookBuilder.setCategory("Fantasy");
            bookBuilder.setPublishedYear("1997");
            bookBuilder.setRetailPrice(20.99);
    }

    public void buildCompTextbook() {
            bookBuilder.setBookName("Foundations of Computer Vision");
            bookBuilder.setAuthorName("Antonio Torralba, Phillip Isola and William T. Freeman");
            bookBuilder.setISBN("9780262048972");
            bookBuilder.setQuantity(1);
            bookBuilder.setCategory("Computer Science");
            bookBuilder.setPublishedYear("2024");
            bookBuilder.setRetailPrice(90.00);
    }

    public Book getBook() {
        return bookBuilder.build();
    }
}
