import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private Logger logger;

    Log(){
        logger = Logger.getLogger("My Log");
        FileHandler fh;
        try{
            fh = new FileHandler("MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (SecurityException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(String str){
        logger.info(str);
    }

    public void warning(String str){
        logger.log(Level.WARNING, str);
    }
}
