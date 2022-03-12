package br.com.fiap.abctechservice.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class VersionService {

    public String getVersion() {
        Properties properties = new Properties();
        InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("application.yml");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getProperty("build.name") + " - " + properties.getProperty("build.version");
    }

}
