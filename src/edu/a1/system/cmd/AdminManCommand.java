package edu.a1.system.cmd;

import java.util.List;

import edu.a1.book.Book;
import edu.a1.book.ConcreteBookBuilder;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;
import edu.a1.system.auth.ReaderPwdChangeStrategy;

/**
 * Command used by the admin to manage the system.
 * @author Guanyuming He
 */
public class AdminManCommand implements Command {
    
    public static final String name = "admin-man";

    /***************************************** Helpers *****************************************/

    // All helpers here assume that the logged in user is already the admin.
    // Which means I must check that at the beginning of handle().

    private void handleRmUser(String usrname) {

        // Check if username == "admin"
        if(usrname.equals("admin")) {
            throw new IllegalArgumentException("username must not be admin");
        }

        var udb = LibrarySystem.userStorage;

        // Check if the user exists
        if(!udb.existUser(usrname)) {
            throw new IllegalArgumentException("No user has the username.");
        }

        User u = udb.findByUsername(usrname);
        assert(u != null);

        // Check if the user has unreturned books
        var bdb = LibrarySystem.borrowStorage;
        var borrows = bdb.findByUsername(usrname);
        for(var b : borrows) {
            if(!b.returnedAllBooks()) {
                throw new IllegalStateException("The user has unreturned books.");
            }
        }

        // Now everything is fine. Remove the user.
        udb.delete(u);
    }

    private void handleRmBook(String isbn) {

        var bookDb = LibrarySystem.bookStorage;

        // Check if the book exists
        if(!bookDb.existBook(isbn)) {
            throw new IllegalArgumentException("No book has the ISBN.");
        }

        Book book = bookDb.findByISBN(isbn);
        assert(book != null);

        // Check if the book has unreturned copies
        var borrowDb = LibrarySystem.borrowStorage;
        var borrows = borrowDb.findByISBN(isbn);
        for(var borrow : borrows) {
            if(!borrow.returnedAllBooks()) {
                throw new IllegalStateException("The book has unreturned copies.");
            }
        }

        // Now everything is fine, remove the book.
        bookDb.delete(book);
    }

    private void handleAddUser(String usrname, String pwd) {
        
        // Check if the user already exists
        var udb = LibrarySystem.userStorage;
        if(udb.existUser(usrname)) {
            throw new IllegalArgumentException("The user already exists.");
        }

        // Check if the pwd can be approved.
        // The user can only be a reader, because admin is always there.
        // Use the strategy for a reader.
        // newUser's pwd must different from the pwd given
        // or the strat will report that they are the same.
        var newUser = new User(usrname, "");
        var strat = new ReaderPwdChangeStrategy();
        if(!strat.approveNewPassword(newUser, pwd)) {
            throw new IllegalArgumentException("Illegal password:\n" + strat.getPasswordPolicy());
        }

        // Now everything is fine. add the user.
        // don't forget to change the pwd back
        newUser.setPwd(pwd);
        udb.save(newUser);

    }

    private void handleAddBook(
        String isbn,
        String numStr,
        String author, String bookname,
        String year,
        String category,
        String priceStr
    ) {
        
        // Check if the book already exists
        var bdb = LibrarySystem.bookStorage;
        if(bdb.existBook(isbn)) {
            throw new IllegalArgumentException("The book with the isbn already exists.");
        }
        // Note that (author, bookname) also needs to be unique
        if(bdb.findByBookNameAndAuthor(bookname, author) != null) {
            throw new IllegalArgumentException("The book with the author name and book name already exists.");
        }

        // Check the format of the string inputs
        int num = -1;
        double price = -1.0;
        try {
            num = Integer.parseInt(numStr);
            price = Double.parseDouble(priceStr);
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException("Argument has incorrect format:\n" + e.getMessage());
        }
        if(num < 0) {
            throw new IllegalArgumentException("Number of copies of the book cannot be negative.");
        }
        if(price < 0.0) {
            throw new IllegalArgumentException("Price for the book cannot be negative.");
        }

        // Now everything is fine, add the book.
        Book newBook = 
        new ConcreteBookBuilder()
            .setISBN(isbn)
            .setQuantity(num)
            .setAuthorName(author)
            .setBookName(bookname)
            .setPublishedYear(year)
            .setCategory(category)
            .setRetailPrice(price)
            .build();
        bdb.save(newBook);

    }

    private void handleListUsers() {
        List<User> users = LibrarySystem.userStorage.findAll();

        if(users.isEmpty()) {
            LibrarySystem.getIO().writeTo("No user is present");
            return;
        }

        for(var u : users) {
            LibrarySystem.getIO().writeTo(u.getUsername());
        }
    }

    /***************************************** override Command *****************************************/

    @Override
    public void handle(List<String> arguments) {

        // Check login status
        if(!LibrarySystem.authenticator.isAdmin()) {
            throw new IllegalStateException(name + " can only be executed by the admin.");
        }

        if(arguments.size() < 2) {
            throw new IllegalArgumentException("Incorrect number of arguments for " + name);
        }

        switch (arguments.get(0)) {

        case "--rm":
            if(arguments.size() != 3) {
                throw new IllegalArgumentException("Incorrect number of arguments for --rm.");
            }

            switch(arguments.get(1)) {
            case "user":
                handleRmUser(arguments.get(2));
                break;
            case "book":
                handleRmBook(arguments.get(2));
                break;
            default:
                throw new UnsupportedOperationException("--rm doesn't support this operation.");
            }
            break;
        
        case "--add":
            if(arguments.size() < 3) {
                throw new IllegalArgumentException("Incorrect number of arguments for --add.");
            }

            switch(arguments.get(1)) {
            case "user":
                if(arguments.size() != 4) {
                    throw new IllegalArgumentException("Incorrect number of arguments for --add user.");
                }
                handleAddUser(arguments.get(2), arguments.get(3));
                break;
            case "book":
                if(arguments.size() != 9) {
                    throw new IllegalArgumentException("Incorrect number of arguments for --add book.");
                }
                handleAddBook(
                    arguments.get(2), 
                    arguments.get(3),
                    arguments.get(4),
                    arguments.get(5),
                    arguments.get(6),
                    arguments.get(7),
                    arguments.get(8)
                );
                break;
            default:
                throw new UnsupportedOperationException("--add doesn't support this operation.");
            }
            break;
        
        case "--list":
            if(arguments.size() != 2) {
                throw new IllegalArgumentException("Incorrect number of arguments for --list.");
            }

            switch(arguments.get(1)) {
            case "user":
                handleListUsers();
                break;

            default:
                throw new UnsupportedOperationException("--list doesn't support this operation.");
            }

            break;

        default:
            throw new UnsupportedOperationException("Option not supported for this command.");
        }

        // No exception means success.
        LibrarySystem.getIO().writeTo("Administrative operation completed successfully.");
    }

    @Override
    public String helpMessage() {
        return Command.generateHelpMessage(
            name + " [OPTIONS]",
            "Manages the library system as admin.", 
            List.of(
                "--rm user <usrname>: removes a user\n" +
                    "\t\t <usrname>: name of the user",
                "--rm book <isbn>: removes a book\n" +
                    "\t\t <isbn>: ISBN of the book",
                "--add user <usrname> <pwd>\n" +
                    "\t\t <usrname>: username\n" +
                    "\t\t <pwd>: password",
                "--add book <isbn> <num> <author> <name> <year> <cat> <price>\n" +
                    "\t\t <isbn>: ISBN\n" +
                    "\t\t <num>: number of copies\n" +
                    "\t\t <author>: name of author\n" +
                    "\t\t <name>: book name\n" +
                    "\t\t <year>: published year\n" +
                    "\t\t <cat>: category\n" +
                    "\t\t <price>: retail price",
                "--list user\n" +
                    "\t\tLists all usernames"
            ), 
            List.of()
        );
    }
    
}
