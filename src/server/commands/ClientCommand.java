

// ClientCommand.java
package server.commands;

import java.io.Serializable;

public class ClientCommand implements Serializable {
    private final String name;
    private final String argument;
    private Object data;

    public ClientCommand(String name, String argument) {
        this.name = name;
        this.argument = argument;
    }

    public String getName() {
        return name;
    }

    public String getArgument() {
        return argument;
    }

    public Object getData() {
        return data;
    }

    ;
}