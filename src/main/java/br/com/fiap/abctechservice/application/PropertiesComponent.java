package br.com.fiap.abctechservice.application;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class PropertiesComponent {

    private Properties properties;

    public String getVersionAndName() {
        properties = new Properties();
        InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("application.yml");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getProperty("build.name") + " - " + properties.getProperty("build.version");
    }

    public String getName() {
        return properties.getProperty("build.name");
    }

    public String getVersion() {
        return properties.getProperty("build.version");
    }

}
