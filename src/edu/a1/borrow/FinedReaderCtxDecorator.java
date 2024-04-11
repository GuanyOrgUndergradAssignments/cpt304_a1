package edu.a1.borrow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.a1.book.Book;
import edu.a1.database.BookManagement;
import edu.a1.database.BorrowManagement;
import edu.a1.database.UserManagement;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;
import edu.a1.system.context.reader.NormalReaderContext;
import edu.a1.system.context.reader.ReaderContext;
import edu.a1.system.context.reader.ReaderCtxDecorator;

/**s
 * Precondition:
 *  A reader with unpaid fines is logged in.
 * @author Kemu Xu
 */
public class FinedReaderCtxDecorator extends ReaderCtxDecorator {
    NormalReaderContext normalReaderContext;
    protected final List<Borrow> borrowHistory;
    // borrow histories that have unpaid fines.
    List<Borrow> unpaidBorrows;

    /**
     * @param history loaded by the system from the database.
     */
    public FinedReaderCtxDecorator(List<Borrow> history) {
        super(new NormalReaderContext(history));
        normalReaderContext = (NormalReaderContext) super.getWrapped();
        borrowHistory = normalReaderContext.getBorrowHistory();

        // TODO: find unpaid borrows
        determineUnpaidBorrows();
    }

    /**
     * Ask the reader to pay the fine.
     * if he has paid, then allow him to borrow
     */
    @Override
    public void borrowBook(Book book, int numCopies, Date declaredReturnDate) {

        // TODO: ask the reader to pay the fine.
        if(unpaidBorrows.isEmpty()){
            super.borrowBook(book, numCopies, declaredReturnDate);
        }else{
            Boolean agreePayingFine = askPayFine();
            if (agreePayingFine){
                super.borrowBook(book, numCopies, declaredReturnDate);
                LibrarySystem.getIO().writeTo("You have borrowed book.");
            }else{
                LibrarySystem.getIO().writeTo("Failed to borrow book because you refuse to pay all fines.");
            }
        }
    }

    /**
     * Ask the reader to pay the fine.
     * if he has paid, then allow him to return
     */
    @Override
    public void returnBook(Book book, int numCopies) {

        // TODO: ask the reader to pay the fine.
        if (unpaidBorrows.isEmpty()){
            super.returnBook(book, numCopies);
        }else{
            Boolean agreePayingFine = askPayFine();
            if (agreePayingFine){
                super.returnBook(book, numCopies);
                LibrarySystem.getIO().writeTo("You have returned borrowed book.");
            }else{
                LibrarySystem.getIO().writeTo("Failed to return borrowed book because you refuse to pay all fines.");
            }
        }
    }

    private Boolean askPayFine(){
        LibrarySystem.getIO().writeTo("You have to pay all fines before return books. Pay now? yes/no");
        var input = LibrarySystem.getIO().readLineFrom();
        while (!input.equals("yes") && !input.equals("no")){
            LibrarySystem.getIO().writeTo("The command is incorrect, please type yes/no.");
        }
        switch (input){
            case "yes":{
                payFine(unpaidBorrows);
                LibrarySystem.getIO().writeTo("You have paid fines.");
                return true;
            }
            case "no":{
                LibrarySystem.getIO().writeTo("You have to pay all fines before borrowing/returning books.");
                return false;
            }
        }
        return null;
    }

    public void payFine(List<Borrow> finedBorrows){
        for (Borrow borrow : finedBorrows){
            borrow.payFine();
        }

        // update unpaid recording
        determineUnpaidBorrows();
        // the reader paid all, so it should be empty after the update
        assert(unpaidBorrows.isEmpty());
    }

    /**
     * find all unpaid borrows and make unpaidBorrows contain only them.
     */
    public void determineUnpaidBorrows(){

        if(unpaidBorrows == null) {
            unpaidBorrows = new ArrayList<>();
        }
        else {
            unpaidBorrows.clear();
        }

        for(Borrow borrow : borrowHistory){
            if (borrow.isFined() && !borrow.getFinePaid()){
                unpaidBorrows.add(borrow);
            }
        }
    }
}
