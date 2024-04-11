package edu.a1.system.cmd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.a1.book.Book;
import edu.a1.borrow.FinedReaderCtxDecorator;
import edu.a1.system.LibrarySystem;
import edu.a1.system.context.reader.NoReaderContext;
import edu.a1.system.context.reader.NormalReaderContext;
import edu.a1.system.context.reader.ReaderContext;

/**
 * Handles what a reader can do to books
 * @author Guanyuming He
 */
public class ReaderBookOpCommand implements Command {

    public static final String name = "reader-book-op";

    /***************************************** Helpers *****************************************/

    // All helpers here assume that the logged in user is already a reader.
    // Which means I must check that at the beginning of handle().

    /**
     * According to the login state, calculates the book operation context that 
     *  1. if no one or the admin is logged in, then NoReaderContext
     *  2. if a reader that has no fine is logged in, then NormalReaderContext
     *  3. otherwise, it's a reader with fines. Then FinedReaderCtxDecorator
     * 
     * @return the context calcualted
     */
    private static ReaderContext calculateBookOpCtx() {
        var auth = LibrarySystem.authenticator;

        // If he's not a reader
        if(!auth.loggedIn() || auth.isAdmin()) {
            return new NoReaderContext();
        }

        // Now he's a reader
        // Find out if he's fined.
        assert(auth.isReader());
        var reader = auth.getLoggedInUser();
        var borrows = LibrarySystem.borrowStorage.findByUsername(reader.getUsername());
        // Fined && not paid.
        boolean fined = false;
        for(var borrow : borrows) {
            // Calculate fines based on the current system state.
            borrow.calculateFine();
            if(borrow.isFined() && !borrow.getFinePaid()) {
                fined = true;
                // Don't break.
                // Complete calcualtion for all borrows.
            }
        }

        // If he's a not fined reader
        if(!fined) {
            return new NormalReaderContext(borrows);
        }

        // If he's a fined reader
        else {
            return new FinedReaderCtxDecorator(borrows);
        }

    }

    private void handleBorrow(ReaderContext ctx, String isbn, String numStr, String returnDateStr) {

        var bdb = LibrarySystem.bookStorage;

        // Obtain the book.
        Book b = bdb.findByISBN(isbn);
        if(b == null) {
            throw new IllegalArgumentException("No book with the ISBN exists.");
        }

        // Parse the arguments
        int num = -1;
        Date returnDate = null;
        try {
            num = Integer.parseInt(numStr);
            var dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            returnDate = dateFormat.parse(returnDateStr);
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException("Number format is incorrect: " + e.getMessage());
        }
        catch(ParseException e) {
            throw new IllegalArgumentException("Return date format is incorrect: " + e.getMessage());
        }
        if(num <= 0) {
            throw new IllegalArgumentException("The borrow number must be positive.");
        }

        // Now everything is fine, handle the borrow
        ctx.borrowBook(b, num, returnDate);

    }

    private void handleReturn(ReaderContext ctx, String isbn, String numStr) {
        
        var bdb = LibrarySystem.bookStorage;

        // Obtain the book.
        Book b = bdb.findByISBN(isbn);
        if(b == null) {
            throw new IllegalArgumentException("No book with the ISBN exists.");
        }

        // Parse the arguments
        int num = -1;
        try {
            num = Integer.parseInt(numStr);
        }
        catch(NumberFormatException e) {
            throw new IllegalArgumentException("Number format is incorrect: " + e.getMessage());
        }
        if(num <= 0) {
            throw new IllegalArgumentException("The return number must be positive.");
        }

        // Now everything is fine, handle the return
        ctx.returnBook(b, num);
    }

    /***************************************** override Command *****************************************/
    @Override
    public void handle(List<String> arguments) {

        if(arguments.size() < 3) {
            throw new IllegalArgumentException("Incorrect number of arguments for " + name);
        }

        ReaderContext readerCtx = calculateBookOpCtx();

        switch (arguments.get(0)) {

        case "--borrow":
            if(arguments.size() != 4) {
                throw new IllegalArgumentException("Incorrect number of arguments for --borrow");
            }
            handleBorrow(readerCtx, arguments.get(1), arguments.get(2), arguments.get(3));
            break;

        case "--return":
            if(arguments.size() != 3) {
                throw new IllegalArgumentException("Incorrect number of arguments for --return");
            }
            handleReturn(readerCtx, arguments.get(1), arguments.get(2));
            break;
    
        default:
            throw new UnsupportedOperationException("This option is not supported by " + name);
        }
    }

    @Override
    public String helpMessage() {
        return Command.generateHelpMessage(
            name + " [OPTIONS]", 
            "A reader uses this command to borrow/return books", 
            List.of(
                "--borrow <isbn> <num> <return-date>\n" +
                    "\t\t <isbn>: ISBN of the book to borrow\n" +
                    "\t\t <num>: number of copies to borrow\n" +
                    "\t\t <return-date>: when will you return the books",
                "--return <isbn> <num>\n" +
                    "\t\t <isbn>: ISBN of the book to return\n" +
                    "\t\t <num>: number of copies to return"
            ), 
            List.of()
        );
    }

}
