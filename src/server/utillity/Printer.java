package server.utillity;

import server.interfaces.Printable;

/**
 * Contains logic for printing messages
 */
public class Printer implements Printable {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
