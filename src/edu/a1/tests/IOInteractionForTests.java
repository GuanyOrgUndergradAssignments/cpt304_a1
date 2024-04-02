package edu.a1.tests;

import edu.a1.system.IOInteraction;

public class IOInteractionForTests implements IOInteraction {

    StringBuilder myOwnedBuffer;
    StringBuilder userOwnedBuffer;

    /**
     * Create the two buffers and buffer readers.
     */
    public IOInteractionForTests(StringBuilder userOwnedBuffer) {

        myOwnedBuffer = new StringBuilder("");
        this.userOwnedBuffer = userOwnedBuffer;

    }

    /**
     * If no newline, then read all.
     * Otherwise, read a line.
     */
    @Override
    public String readLineFrom() {

        String ret = "";

        // find the 
        int newLineInd = userOwnedBuffer.indexOf("\n");
        if (newLineInd == -1) {
            ret = userOwnedBuffer.toString();
            // update the buffer.
            userOwnedBuffer.delete(0, userOwnedBuffer.length());
        }
        else {
            ret = userOwnedBuffer.substring(0, newLineInd);
            // update the buffer.
            userOwnedBuffer.delete(0, newLineInd+1);
        }

        return ret;
    }

    @Override
    public void writeTo(String msg) {

        myOwnedBuffer.append(msg + "\n");

    }
    
}
