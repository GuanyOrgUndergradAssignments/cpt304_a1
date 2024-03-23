package edu.a1.system.context.reader;

/**
 * Decorates the reader context to add more responsibilities/behaviours 
 * to borrowBook and returnBook
 * 
 * @author Guanyuming He, Kemu Xu
 */
public abstract class ReaderCtxDecorator implements ReaderContext {
    ReaderContext wrapped;

    public ReaderCtxDecorator(ReaderContext wrapped) {
        this.wrapped = wrapped;
    }
}
