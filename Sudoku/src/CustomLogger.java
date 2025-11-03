import java.io.IOException;
import java.util.logging.*;

public class CustomLogger {

    public static Logger createLog(){
        Logger log;
        try {
            log = Logger.getLogger("SudokuApp.Log");
            LogManager.getLogManager().reset();
            Handler fileHandler = new FileHandler("./SudokuApp.log");
            LogFormat format = new LogFormat();
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(format);
            log.setLevel(Level.ALL);
            log.addHandler(fileHandler);
            log.config("Log created");
            return log;
        } catch (SecurityException | IOException e1) {
            //Auto-generated catch block
            e1.printStackTrace();
            System.out.println("Log creation failed!");
            System.exit(-1);
        }
        throw new Error("Failed to create a log without hitting the catch block?");
    }
}
