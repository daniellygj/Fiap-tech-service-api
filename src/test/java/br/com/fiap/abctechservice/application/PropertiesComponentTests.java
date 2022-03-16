package br.com.fiap.abctechservice.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class PropertiesComponentTests {

    private PropertiesComponent propertiesComponent;

    @BeforeEach
    void setUp() {
        propertiesComponent = new PropertiesComponent();
    }

    @Test
    void getVersionShouldSucceed() {
        Assertions.assertTrue(propertiesComponent.getVersion().contains("1.0"));
    }

    @Test
    void getNameShouldSucceed() {
        Assertions.assertEquals(propertiesComponent.getName(), "abc-tech-service");
    }

    @Test
    void getNameAndVersionShouldSucceed() {
        Assertions.assertTrue(propertiesComponent.getVersionAndName().contains("1.0"));
        Assertions.assertTrue(propertiesComponent.getVersionAndName().contains("abc-tech-service"));
    }
}
