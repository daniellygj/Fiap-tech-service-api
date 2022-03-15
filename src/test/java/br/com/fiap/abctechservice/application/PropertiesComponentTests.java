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
        Assertions.assertNotNull(propertiesComponent.getVersion());
    }

    @Test
    void getNameShouldSucceed() {
        Assertions.assertNotNull(propertiesComponent.getName());
    }

    @Test
    void getNameAndVersionShouldSucceed() {
        Assertions.assertTrue(propertiesComponent.getVersionAndName().contains(propertiesComponent.getName()));
        Assertions.assertTrue(propertiesComponent.getVersionAndName().contains(propertiesComponent.getVersion()));
    }
}
