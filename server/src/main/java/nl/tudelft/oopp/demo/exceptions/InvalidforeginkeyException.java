package nl.tudelft.oopp.demo.exceptions;

/**
 * This is an costumed exception class for handling violating foreign key constraints.
 */
public class InvalidforeginkeyException extends Exception {

    public InvalidforeginkeyException(String s) {
        super(s);
    }

}
