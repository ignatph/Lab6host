package server.interfaces;

/**
 * Defines a contract for printing messages in various implementations.
 */
public interface Printable {
    void print(String message);

    default void printDef(String message) {
        System.out.println(message.toUpperCase());
    }
}