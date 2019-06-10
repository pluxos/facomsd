package configurations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Properties 
{
	private static Properties ap;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static Properties getInstance()
    {
        return ap==null ? new Properties():ap;
    }

    public Properties loadProperties(){
        Properties prop = new Properties();
        InputStream input;
        String PROPNAME = "application.properties";
        try 
        {
            input = getClass().getClassLoader().getResourceAsStream(PROPNAME);
            if(input!=null)
                prop.load(input);
            else
                throw new FileNotFoundException("property file '" + PROPNAME + "' not found in the classpath");

        } 
        catch (IOException e) 
        {
            logger.warning(e.getMessage());
            return null;
        }

        return prop;
    }
}
