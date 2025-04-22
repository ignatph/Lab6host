

package client.manager;

import java.io.Serializable;
// ClientCommand.java
import java.io.Serializable;

public class ClientCommand implements Serializable {
    private final String name;
    private  String argument;
    private WorkerDTO data; // Изменяем тип данных

    public ClientCommand(String name, String argument) {
        this.name = name;
        this.argument = argument;
    }
    public void setArgument(String argument) { this.argument = argument; }
    public String getName() { return name; }
    public String getArgument() { return argument; }
    public WorkerDTO getData() { return data; }
    public void setData(WorkerDTO data) { this.data = data; }
}