package edu.a1.borrow;

import java.util.ArrayList;
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

/**
 * Precondition:
 *  A reader with unpaid fines is logged in.
 * @author Guanyuming He, Kemu Xu
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
        unpaidBorrows = new ArrayList<>();
        getUnpaidBorrows();
    }

    /**
     * Ask the reader to pay the fine.
     * if he has paid, then allow him to borrow
     */
    @Override
    public void borrowBook(Book book, int numCopies) {

        // TODO: ask the reader to pay the fine.
        if(unpaidBorrows.isEmpty()){
            super.borrowBook(book, numCopies);
        }else{
            System.out.println("you have to pay all fines before borrow books");
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
            LibrarySystem.getIO().writeTo("You have to pay all fines before return books. yes/no");
            while(LibrarySystem.getIO().readLineFrom().equals("yes") && LibrarySystem.getIO().readLineFrom().equals("no")){
                String cmdLine = LibrarySystem.getIO().readLineFrom();
                if (cmdLine.equals("yes")){
                    payFine(unpaidBorrows);
                    LibrarySystem.getIO().writeTo("You have paid fines.");
                    super.returnBook(book, numCopies);
                    LibrarySystem.getIO().writeTo("You have returned borrowed book.");
                }else if (cmdLine.equals("no")){
                    LibrarySystem.getIO().writeTo("Return failed. You have to pay all fines before return books.");
                }else{
                    LibrarySystem.getIO().writeTo("The command is incorrent, please type yes/no.");
                }
            }
        }
    }

    public void payFine(List<Borrow> finedBorrows){
        for (Borrow borrow : finedBorrows){
            borrow.payFine();
        }
        getUnpaidBorrows();
    }

    public void getUnpaidBorrows(){
        for(Borrow borrow : borrowHistory){
            borrow.calculateFine();
            if (borrow.getFinePaid() == false && borrow.getFine() != 0){
                unpaidBorrows.add(borrow);
            }
        }
    }
}
