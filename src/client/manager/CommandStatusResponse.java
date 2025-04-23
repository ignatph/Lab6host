package client.manager;

import java.io.Serializable;

// 1. Класс для обертки ответа
public class CommandStatusResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String message;
    private final boolean status;

    private CommandStatusResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public static CommandStatusResponse ofString(String message,boolean status) {
        return new CommandStatusResponse(message, status);
    }

    public String getMessage() {
        return message;
    }
    public boolean isSuccess() {
        return status;
    }
}