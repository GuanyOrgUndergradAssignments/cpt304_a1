package edu.a1.system;

/**
 * Abstracts all the interaction between the system and the user.
 * 
 * Assumes the system and the user interacts through two buffers of String,
 * where both side owns a buffer.
 * Each side writes to the buffer it owns, and reads from that owned by the other side.
 * 
 * For real users, the two buffers are System.in and System.out.
 * For tests as users, the buffers are specially prepared.
 * 
 * Because the user's side is managed by the user,
 * in this interface, only the methods used by the system
 * i.e.
 *  1. write to the buffer it owns
 *  2. read from the buffer the user owns
 * are provided
 */
public interface IOInteraction {

    /**
     * Reads string from the buffer the user writes to until a new line character is encountered.
     * @return the string without the new line character.
     */
    public String readLineFrom();

    /**
     * Writes a string to the buffer that the user will read from.
     * @param msg the string may have new line characters. It doesn't matter.
     */
    public void writeTo(String msg);
    
}
