package com.infy.WebComic_Backend.config;

//AppConfig.java

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // ModelMapper Bean with Set/List converters
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Add converter from Set to List
        Converter<Set<?>, List<?>> setToListConverter = new AbstractConverter<Set<?>, List<?>>() {
            @Override
            protected List<?> convert(Set<?> source) {
                return source == null ? null : new ArrayList<>(source);
            }
        };

        // Add converter from List to Set
        Converter<List<?>, Set<?>> listToSetConverter = new AbstractConverter<List<?>, Set<?>>() {
            @Override
            protected Set<?> convert(List<?> source) {
                return source == null ? null : new HashSet<>(source);
            }
        };

        modelMapper.addConverter(setToListConverter);
        modelMapper.addConverter(listToSetConverter);

        return modelMapper;
    }

    // Jackson ObjectMapper Bean for JSON serialization
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Handle Hibernate lazy loading - use Jakarta version for Spring Boot 3+
        Hibernate5JakartaModule hibernate5Module = new Hibernate5JakartaModule();
        
        // Configure Hibernate module to handle lazy loading properly
        hibernate5Module.disable(Hibernate5JakartaModule.Feature.USE_TRANSIENT_ANNOTATION);
        hibernate5Module.disable(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING);
        hibernate5Module.enable(Hibernate5JakartaModule.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
        
        mapper.registerModule(hibernate5Module);
        
        
        // Handle Java 8 time
        mapper.registerModule(new JavaTimeModule());
        
        // Configure serialization features
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        return mapper;
        
    }
}
