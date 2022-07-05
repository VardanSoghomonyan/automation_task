package setup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
    static String BROWSER;

    static {
        try {
            Properties prop = new Properties();
            InputStream inputStream = Configurations.class.getClassLoader().getResourceAsStream("configurations.properties");
            prop.load(inputStream);
            BROWSER = System.getProperty("used.browser", prop.getProperty("used.browser"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
