package commands;


import collection.CollectionWorker;
import manager.UserManager;
import parse.XmlWriter;
import utillity.Printer;


/**
 * Class contains implementation of save command
 * Save commands in xml file on desktop
 */

public class Save extends Command {

    public Save(String description, boolean hasArgs,  CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            XmlWriter xmlWriter = new XmlWriter( printer,collection);
            String filePath =  "src/save.xml";
            xmlWriter.write(filePath);

        }
    }


    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды save нет аргументов! Попробуйте еще раз");
            return false;
        }
    }
}
