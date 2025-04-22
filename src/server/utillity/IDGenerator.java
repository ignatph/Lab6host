package server.utillity;


/**
 * Class for generate id
 */

public class IDGenerator {
    private static long lastId = 0;

    public static Long generateUniqueId() {
        return ++lastId;
    }
}