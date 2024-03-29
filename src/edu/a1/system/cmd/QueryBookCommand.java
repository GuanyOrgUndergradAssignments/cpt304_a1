package edu.a1.system.cmd;

import java.util.List;

import edu.a1.book.Book;
import edu.a1.system.ConsoleInteraction;
import edu.a1.system.LibrarySystem;

/**
 * Command to query information about a book/books in the library.
 * @author Guanyuming He
 */
public class QueryBookCommand implements Command {

    public static final String name = "query-book";
    public static final String usage = name + " [OPTIONS]";

    /***************************************** Helpers *****************************************/
    private void handleISBNQuery(String isbn) {
        if(!LibrarySystem.bookStorage.existBook(isbn)) {
            ConsoleInteraction.writeToConsole("No book has the ISBN.");
            return;
        }

        Book book = LibrarySystem.bookStorage.findByISBN(isbn);
        assert(book != null);
        ConsoleInteraction.writeToConsole("Book found:");
        ConsoleInteraction.writeToConsole(book.toString());
    }

    private void handleAuthorBookNameQuery(String author, String bookname) {
        Book book = LibrarySystem.bookStorage.findByBookNameAndAuthor(bookname, author);
        
        if(book == null) {
            ConsoleInteraction.writeToConsole("No book has the author and bookname.");
            return;
        }

        ConsoleInteraction.writeToConsole("Book found:");
        ConsoleInteraction.writeToConsole(book.toString());
    }

    private void handleCategoryQuery(String category) {
        List<Book> books = LibrarySystem.bookStorage.findByBookCategory(category);

        if(books.isEmpty()) {
            ConsoleInteraction.writeToConsole("No book has the category.");
            return;
        }

        ConsoleInteraction.writeToConsole("A total of " + books.size() + " books found.");
        for(int i = 0; i < books.size(); ++i) {
            ConsoleInteraction.writeToConsole("Book " + i + ":");
            ConsoleInteraction.writeToConsole(books.get(i).toString());
        }
    }

    /***************************************** override Command *****************************************/
    @Override
    public void handle(List<String> arguments) {

        if(arguments.size() < 2) {
            throw new IllegalArgumentException("Incorrect number of arguments.");
        }

        String option = arguments.get(0);
        switch(option) {

        case "--ISBN":
            if(arguments.size() != 2) {
                throw new IllegalArgumentException("Incorrect number of arguments.");
            }
            handleISBNQuery(arguments.get(1));
            break;

        case "--author-and-book-name":
            if(arguments.size() != 3) {
                throw new IllegalArgumentException("Incorrect number of arguments.");
            }
            handleAuthorBookNameQuery(arguments.get(1), arguments.get(2));
            break;

        case "--category":
            if(arguments.size() != 2) {
                throw new IllegalArgumentException("Incorrect number of arguments.");
            }
            handleCategoryQuery(arguments.get(1));
            break;

        default:
            throw new IllegalArgumentException("The option does not exist for this command.");
        }
    }

    @Override
    public String helpMessage() {
        return Command.generateHelpMessage(
            usage, 
            "query specific book(s) matching the given information",
            List.of(
                "--ISBN <isbn>, where <isbn> is the ISBN of the book",
                "--author-and-book-name <author> <bookname>, \n\t" +
                    "where <author> is the author name, <bookname> is the bookname",
                "--category <cat>, where <cat> is the category of the books"
            ), 
            List.of()
        );
    }
    
}
