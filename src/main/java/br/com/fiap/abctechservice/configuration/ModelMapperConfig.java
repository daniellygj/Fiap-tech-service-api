package br.com.fiap.abctechservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // Creating this bean not to violate principles like DRY (don't repeat yourself)
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}