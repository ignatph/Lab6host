package commands;

import collection.CollectionWorker;
import interfaces.Checkable;
import interfaces.Executable;
import manager.UserManager;
import utillity.Printer;

/**
 * Common abstract class for all commands
 */
public abstract class Command implements Executable, Checkable {
    protected final UserManager userManager;
    protected final CollectionWorker collection;
    private final String description;
    private final boolean hasArgs;
    private Object args;


    public Command(String description, boolean hasArgs, UserManager userManager, CollectionWorker collection) {
        this.description = description;
        this.hasArgs = hasArgs;
        this.userManager = userManager;
        this.collection = collection;
    }

    @Override
    public abstract void execute(Printer printer);

    @Override
    public abstract boolean checkArgument(Printer printer, Object inputArgs);


    public boolean isHasArgs() {
        return hasArgs;
    }


    public String getDescription() {
        return description;
    }


    public Object getArgs() {
        return args;
    }


    public void setArgs(Object args) {
        this.args = args;
    }


}