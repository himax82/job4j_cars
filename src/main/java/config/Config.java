package config;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final Properties config = new Properties();

    private Config() {
        try (InputStream in
                     = Config.class.getClassLoader().getResourceAsStream("path.properties")) {
            config.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final class Lazy {
        private static final Config INST = new Config();
    }

    public static Config instOf() {
        return Config.Lazy.INST;
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }
}
