package br.com.fiap.abctechservice.service.impl;

import br.com.fiap.abctechservice.service.HealthCheckService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {
    private Properties properties;

    public HealthCheckServiceImpl() {
        properties = new Properties();
        InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("application.yml");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersionAndName() {
        return properties.getProperty("build.name") + " - " + properties.getProperty("build.version");
    }

    public String getName() {
        return properties.getProperty("build.name");
    }

    public String getVersion() {
        return properties.getProperty("build.version");
    }
}
